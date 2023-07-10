package projectH.HistoricalDatabaseofCaptives.GISData;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
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

        List<List<URI>> targetUris =  targetLocations.stream().map(targetList -> targetList.stream().map(target ->  {
            try {
                return new URI("https://nominatim.openstreetmap.org/search?format=json&limit=3&q=" + URLEncoder.encode(target, StandardCharsets.UTF_8));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }).toList() ).toList();

        for(List<URI> UriList : targetUris) {
            HttpClient client = HttpClient.newHttpClient();
            List<CompletableFuture<String>> listOfLatLon = UriList.stream().map(targetURi -> client
                    .sendAsync(HttpRequest.newBuilder(targetURi).GET().build(), HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    // hun characters ale lost :(
                    .thenApply( e -> e.concat(URLDecoder.decode(targetURi.toString(), StandardCharsets.UTF_8).substring(targetURi.toString().lastIndexOf("="))   ))
            ).toList();
            Map<String, Map<String, String>> temList = new HashMap<>();

            // return value from open street view

//            String  t = "[{\"bookingid\":4},{\"bookingid\":9},{\"bookingid\":3},{\"bookingid\":2},{\"bookingid\":1},{\"bookingid\":6},{\"bookingid\":5},{\"bookingid\":10},{\"bookingid\":8},{\"bookingid\":7}]";
//            ObjectMapper mapper = new ObjectMapper();
//            mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
//            JavaType type = mapper.getTypeFactory().constructParametrizedType(List.class, List.class, BookingID.class);
//            List<BookingID> t2 = mapper.readValue(t,type);
// just for printing




            for( CompletableFuture<String> lonLat : listOfLatLon)
            {
                try {
                    if(lonLat.get().charAt(1) != ']') {
//                        https://www.baeldung.com/jackson-collection-array
//                        Cannot deserialize value of type `java.lang.String` from Array value (token `JsonToken.START_ARRAY`)
                        ObjectMapper mapper = new ObjectMapper();
                        List<OSVJson> listCar = mapper.readValue(lonLat.get(), new TypeReference<>(){});

                        System.out.println(listCar);
                    }

                } catch (ExecutionException | JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
            }

            Thread.sleep(6000);
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
