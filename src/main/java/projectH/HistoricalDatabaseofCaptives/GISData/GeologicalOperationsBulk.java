package projectH.HistoricalDatabaseofCaptives.GISData;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.IntStream;


@Component
public class GeologicalOperationsBulk implements IGeolocator {


    private final GeologicalRepository geologicalRepository;
    private final WithOrWithoutCoordinates withOrWithoutCoordinates;

    public GeologicalOperationsBulk(GeologicalRepository geologicalRepository, WithOrWithoutCoordinates withOrWithoutCoordinates) {
        this.geologicalRepository = geologicalRepository;
        this.withOrWithoutCoordinates = withOrWithoutCoordinates;
    }

    // This version is for the larger mass of data retrieval. Timeout is needed to avoid closedConnectionException
    // Due to Historical reason the display name of many locations is written in ciril or romanian alphabet.
    // select * from geological_locations where regexp_like(name, '[а-яА-ЯёЁ]');
    // so in geological_locations table there is "source_name"  "osv_name" columns for names
    @Override
    public void getLocationData(Set<String> targetTownSet) throws InterruptedException, ExecutionException, IOException {
        targetTownSet.removeAll(withOrWithoutCoordinates.getLocationsWithCoordinates());
             //creating uri list while dealing with the special Hungarian characters
        List<List<String>> targetLocations = bulkTownFeeder( targetTownSet.stream().toList(), 6);
        produceCompleteables(targetLocations);


    }

    private void  produceCompleteables(List<List<String>>  targetLocations ) throws InterruptedException, ExecutionException, IOException {
        List<Map<String, OSVJson>> listOfCompletables = new ArrayList<>();

        for(List<String> list : targetLocations) {
            Map<String, OSVJson> copmMap = null;
            for (String target : list) {
                List<TargetLink> tempLinkList = new ArrayList<>();
                try {
                    tempLinkList.add(
                            new TargetLink(
                                    new URI("https://nominatim.openstreetmap.org/search?format=json&limit=3&q=" + URLEncoder.encode(target, StandardCharsets.UTF_8)),
                                    target
                            ));
                    copmMap = getCompleteables(tempLinkList);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }

            }

            listOfCompletables.add(copmMap);
            insertEntriesIntoGeologicalLocations(listOfCompletables);
            listOfCompletables.clear();
            Thread.sleep(6000);
        }

    }


    private Map<String, OSVJson> getCompleteables(List<TargetLink> targetUrls) throws InterruptedException, ExecutionException, IOException {
        HttpClient client = HttpClient.newHttpClient();
        Map<String, OSVJson> mapOfLatLon = new HashMap<>();
        for(TargetLink targetLink : targetUrls) {
            mapOfLatLon.put(targetLink.getTarget(),
                    processCompletables(client.sendAsync(HttpRequest.newBuilder(targetLink.getLink()).GET().build(), HttpResponse.BodyHandlers.ofString())
                            .thenApply(HttpResponse::body)));
        }
        return mapOfLatLon;
    }


    private OSVJson processCompletables(CompletableFuture<String> completableFuture) throws ExecutionException, InterruptedException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        List<OSVJson> osvJsons = mapper.readValue(completableFuture.get(), new TypeReference<>() {});
        OSVJson osvJson = new OSVJson();
        if (!osvJsons.isEmpty() ){
            osvJson = osvJsons.get(0);
        }
        return osvJson;
    }


    private void insertEntriesIntoGeologicalLocations(List<Map<String, OSVJson>> listOfLatLon ) {

        for (Map<String, OSVJson> latLonsMap : listOfLatLon) {
            try{
            for (String key : latLonsMap.keySet()) {
                OSVJson osvJson = latLonsMap.get(key);
                if (null != osvJson.getDisplay_name()) {
                   geologicalRepository.save(new GeoLocation(key,
                            osvJson.getDisplay_name().substring(0, osvJson.getDisplay_name().indexOf(',')),
                            osvJson.getLon(),
                            osvJson.getLat(),
                            osvJson.getDisplay_name().substring(osvJson.getDisplay_name().lastIndexOf(',')+1).trim()));

                }
            }
        } catch (StringIndexOutOfBoundsException ignored){


            }
        }
    }
//    to make the feeded information processing as fast as possible with openstreet view this method can be used to
//    create a list of list what defines the amount of data the GET request will fetch at once.
//    aim is to get as much as possible at  the given time, but avoid creating tails like 21 % 4 = 1 vs 21 % 3 = 0
    public List<List<String>> bulkTownFeeder(List<String> listToProcess, int maxPartitionSize){


        int listSize = listToProcess.size();

        Integer partitionSize = IntStream.range(1, maxPartitionSize).filter(e-> listSize % e == 0).reduce(Integer::max).orElseThrow();
//       use the partition size to slice the array.
        List<List<String>> collectorList = new ArrayList<>();
        // increase the min and max index with partition size until it is larger than the size - 1 of the array.
        int subListAmount = listSize / partitionSize;

        List<Integer> itList = IntStream.range(1, subListAmount + 1).map(e -> e * partitionSize).boxed().toList();
        itList.forEach(e ->
                collectorList.add(listToProcess.subList(e - partitionSize, e))
        );

        collectorList.forEach(System.out::println);

                return collectorList;
    }



}
