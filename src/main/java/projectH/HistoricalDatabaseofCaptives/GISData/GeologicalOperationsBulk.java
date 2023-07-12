package projectH.HistoricalDatabaseofCaptives.GISData;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
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

import static java.util.stream.Collectors.groupingBy;

@Component
public class GeologicalOperationsBulk implements IGeolocator {

    private final GeoServices geoServices;

    public GeologicalOperationsBulk(GeoServices geoServices) {
        this.geoServices = geoServices;
    }

    // This version is for the larger mass of data retrieval. Timeout is needed to avoid closedConnectionException
    // Due to Historical reason the display name of many locations is written in ciril or romanian alphabet.
    // select * from geological_locations where regexp_like(name, '[а-яА-ЯёЁ]');
    // so in geological_locations table there is "source_name"  "osv_name" columns for names

    public void getCityData() throws InterruptedException {
        Set<String> locations =  geoServices.getAllLocation();
        locations.removeAll(geoServices.getLocationsWithCoordinates());
        //creating uri list while dealing with the special Hungarian characters
        List<List<String>> targetLocations = bulkTownFeeder( locations.stream().toList(), 6);
        produceCompleteables(targetLocations);


    }

    private void  produceCompleteables(List<List<String>>  targetLocations ) throws InterruptedException {
        List<Map<String, CompletableFuture<String>>> listOfCompletables = new ArrayList<>();

        for(List<String> list : targetLocations) {
            List<TargetLink> tempList = new ArrayList<>();
            for(String target : list) {
                try {tempList.add(
                     new TargetLink(
                            new URI("https://nominatim.openstreetmap.org/search?format=json&limit=3&q=" + URLEncoder.encode(target, StandardCharsets.UTF_8)),
                            target
                    ));
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            }
            Map<String, CompletableFuture<String>> copmMap = getCompleteables(tempList);
            listOfCompletables.add(copmMap);
            completeCompletablesInToDatabase(listOfCompletables);
            Thread.sleep(6000);
        }

    }

    private Map<String, CompletableFuture<String>> getCompleteables(List<TargetLink> targetUrls) throws InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        Map<String, CompletableFuture<String>> mapOfLatLon = new HashMap<>();
        for(TargetLink targetLink : targetUrls) {
            mapOfLatLon.put(targetLink.getTarget(),
                        client.sendAsync(HttpRequest.newBuilder(targetLink.getLink()).GET().build(), HttpResponse.BodyHandlers.ofString())
                                .thenApply(HttpResponse::body));
        }
        System.out.println("from the getcompletables");
        System.out.println(mapOfLatLon);

        return mapOfLatLon;
    }


    private void completeCompletablesInToDatabase(List<Map<String, CompletableFuture<String>>> listOfLatLon ){
        for( Map<String, CompletableFuture<String>> latLonsMap : listOfLatLon){
            for(String key : latLonsMap.keySet()) {
                try {
                    if(!latLonsMap.get(key).get().isEmpty()){
                        //                        https://www.baeldung.com/jackson-collection-array
//                        Cannot deserialize value of type `java.lang.String` from Array value (token `JsonToken.START_ARRAY`)
//                        learn jackson this thing took almost 4 hours
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        OSVJson osvJson= mapper.readValue(latLonsMap.get(key).get(), OSVJson.class);
                        System.out.println(osvJson.toString());
                        geoServices.addGeographicalLocation(key,
                                osvJson.getDisplay_name(),
                                Double.parseDouble(osvJson.getLon()),
                                Double.parseDouble(osvJson.getLat()));

                    }
                } catch (ExecutionException | JsonProcessingException | InterruptedException ex) {
                    throw new RuntimeException(ex);
                }

            }

        }

    }

//    to make the feeded information processing as fast as possible with openstreet view this method can be used to
//    create a list of list what defines the amount of data the GET request will fetch at once.
//    aim is to get as much as possible at  the given time, but avoid creating tails like 21 % 4 = 1 vs 21 % 3 = 0
    public List<List<String>> bulkTownFeeder(List<String> listToProcess, int maxPartitionSize){


        int listSize = listToProcess.size();
        System.out.println(listSize);
        Integer partitionSize = IntStream.range(1, maxPartitionSize).filter(e-> listSize % e == 0).reduce(Integer::max).orElseThrow();
//       use the partition size to slice the array.
        List<List<String>> collectorList = new ArrayList<>();
        // increase the min and max index with partition size until it is larger than the size - 1 of the array.
        int subListAmount = listSize / partitionSize;

        List<Integer> itList = IntStream.range(1, subListAmount + 1).map(e -> e * partitionSize).boxed().toList();
        itList.forEach(e ->
                collectorList.add(listToProcess.subList(e - partitionSize, e))
        );

        System.out.println(collectorList);
        return collectorList;
    }


    //function calculates the distance between two point defined by coordinates (i.e. Cities) from the DB
    // to avoid false location definition Budapest will be the 0 km and all if a location is more than 800 km away that will alert the admin.

    public double calculateLocationToLocationDistance(GeoLocation locationA, GeoLocation locationB) {

//        GeoLocation locationA, GeoLocation locationB
//        convert to Radian is = degree * 3.1415926535 / 180;
        double latA = Math.toRadians(locationB.getLatitude());
        double latB = Math.toRadians(locationB.getLatitude());
        double difLat = Math.toRadians(locationB.getLatitude() - locationA.getLatitude());
        double difLon = Math.toRadians(locationB.getLongitude() - locationA.getLongitude()) ;
//      Haversine formula
//         based on https://community.esri.com/t5/coordinate-reference-systems-blog/distance-on-a-sphere-the-haversine-formula/ba-p/902128
//         https://www.vcalc.com/wiki/vCalc/Haversine+-+Distance

        double atan2Base = Math.pow(Math.sin(difLat / 2),  2) + Math.cos(latA) * Math.cos(latB) * Math.pow(Math.sin(difLon / 2) ,2);

        double distanceInKm =  (Math.atan2(Math.sqrt(atan2Base), Math.sqrt(1 - atan2Base))) * 2 * 6371000 / 1000 ;
        System.out.println(distanceInKm);
        return   distanceInKm;
    }


//    Test method , could be useful later, probably better to return the something like {geoLocation.getSource_name = {"locationName"="Debrecen", "Distance"=111}
    public Set<String> returnDistanceBetweenLocations(GeoLocation geoLocation) {
//        base
        GeoLocation x = geoServices.getALocationByName(geoLocation.getSource_name());
        Set<String> resultedDistanceString = new HashSet<>();

        geoServices.getLocationsWithCoordinates().forEach(e-> resultedDistanceString.add("The distance between " + e + " and " + geoLocation.getSource_name() + "is about " +
                calculateLocationToLocationDistance( x, geoServices.getALocationByName(e) ) + " km"));
        System.out.println(resultedDistanceString);
        return resultedDistanceString;
    }

}
