package projectH.HistoricalDatabaseofCaptives.GISData;


import org.hibernate.mapping.Array;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveServices;

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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class HdcGeolocator {
    private CaptiveServices captiveServices;
    private final GeoServices geoServices;

    private GeologicalRepository geologicalRepository;

    @Autowired
    public HdcGeolocator(CaptiveServices captiveServices, GeoServices geoServices, GeologicalRepository geologicalRepository) {
        this.captiveServices = captiveServices;
        this.geoServices = geoServices;
        this.geologicalRepository = geologicalRepository;
    }

    public HashMap<String, HashMap<String, String>> getCityData() throws URISyntaxException, InterruptedException, ExecutionException {
        //maybe changing to google az openstreet view does not tolerate bulk requests very much
        // also the databse needs to be set up to store the already queried information so this will be the next getAllMentionedSettlement()

        HashMap<String, HashMap<String, String>> placesWiththeirLatLon = new HashMap<>();

        Set<String> targetTownSet = new HashSet<>(getAllSettlement());

//        Remove all when they are present in the geological_locations
        targetTownSet.removeAll(getPlacesWithLocationData());

        //creating uri list while dealing with the special Hungarian characters
        List<URI> targetUris = targetTownSet.stream().map(target ->
        {
            try {
                return new URI("https://nominatim.openstreetmap.org/search?format=json&limit=3&q=" + URLEncoder.encode(target, StandardCharsets.UTF_8));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }).toList();

//        the final goal is to get a hasmap where the city name is the key and the lat / lon data is the value in its own map, or a list
        // deal with the api request`

        HttpClient client = HttpClient.newHttpClient();
        List<CompletableFuture<String>> listOfLatLon = targetUris.stream().map(targetURi -> client
                .sendAsync(HttpRequest.newBuilder(targetURi).GET().build(), HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
        ).toList();
        Map<String, Map<String, String>> temList = new HashMap<>();


        // return value from open street view
        listOfLatLon.stream().toList().forEach(e -> {
            try {
                Map<String, String> collect = Arrays.stream(Arrays.asList(e.get().split("}")).get(1).split(","))
                        .map(part -> part.replaceAll("\"", "").split(":"))
                        .filter(p -> p.length > 1)
                        .collect(Collectors.toMap(s -> s[0], s -> s[1])
                        );
                collect.keySet().retainAll(List.of("display_name", "lon", "lat"));
                System.out.println(temList.get(collect.get("display_name")));
                temList.put(collect.get("display_name"), collect);
                geoServices.addGeographicalLocation(collect.get("display_name"), Double.parseDouble(collect.get("lat")), Double.parseDouble(collect.get("lon")));
            } catch (InterruptedException | ExecutionException ex) {
                throw new RuntimeException(ex);
            }
        });

        return placesWiththeirLatLon;

    }

    // move to services
    private Set<String> getAllSettlement() {
        Set<String> allThePlaces = captiveServices.getCitiesOfBirth();
        allThePlaces.addAll(captiveServices.getCitiesOfResidence());
        return allThePlaces;
    }
    // get the locations without lat / lon data
    private Set<String> getPlacesWithoutLocationData() {
        Set<String> placesWithoutLocationData = new HashSet<>();

        geologicalRepository.findAll().forEach(loca -> {
                    if (loca.getLatitude() == null || loca.getLongitude() == null) {
//                        both value need to be in the db to not get prepared for a new fetch
                        placesWithoutLocationData.add(loca.getName());
                    }
                }
        );

        return placesWithoutLocationData;
    }

    private Set<String> getPlacesWithLocationData() {
        Set<String> placesWithLocationData = new HashSet<>();

        geologicalRepository.findAll().forEach(loca -> {
                    if (loca.getLatitude() != null  && loca.getLongitude() != null  ) {
//                        both value need to be in the db to not get prepared for a new fetch
                        placesWithLocationData.add(loca.getName());
                    }
                }
        );

        return placesWithLocationData;
    }

    private GeoLocation getALocationByName(String name){
       return geologicalRepository.findByName(name);

    }


    //validate the distance from Budapest to check if the returned lat/lon data is ok

    public double getDistanceBetween(GeoLocation locationA, GeoLocation locationB) {
//        function calculates the distance between two point defined by coordinates (i.e. Cities) from the DB
//        GeoLocation locationA, GeoLocation locationB
//        convert to Radian is = degree * 3.1415926535 / 180;


        double latA = Math.toRadians(locationB.getLatitude());
        double latB = Math.toRadians(locationB.getLatitude());

        double difLat = Math.toRadians(locationB.getLatitude() - locationA.getLatitude());
        double difLon = Math.toRadians(locationB.getLongitude() - locationA.getLongitude()) ;


//      Haversine formula
        // based on https://community.esri.com/t5/coordinate-reference-systems-blog/distance-on-a-sphere-the-haversine-formula/ba-p/902128
        // https://www.vcalc.com/wiki/vCalc/Haversine+-+Distance
//
        double atan2Base = Math.pow(Math.sin(difLat / 2),  2) + Math.cos(latA) * Math.cos(latB) * Math.pow(Math.sin(difLon / 2) ,2);
//
        double distanceInKm =  (Math.atan2(Math.sqrt(atan2Base), Math.sqrt(1 - atan2Base))) * 2 * 6371000 / 1000 ;
        System.out.println(distanceInKm);
        return   distanceInKm;
    }


    public Set<String> returnDistancesFromBudapest() {
        //base
        GeoLocation x = getALocationByName("Budapest");
        Set<String> resultedDistanceString = new HashSet<>();
// get the places and compare ach to the base
         getPlacesWithLocationData().forEach( e-> resultedDistanceString.add("The distance between " + e + " and Budapest is about " +  getDistanceBetween( x, getALocationByName(e) ) + " km"));
        System.out.println(resultedDistanceString);
        return resultedDistanceString;
    }

    //tasks
//
    //TODO
    //    build an in memory cache so it will see what the we need to get from open street view



    /// temporrary feeder for mass coordinattes retrival so openstreet view wont close the conenction.


    private void bulkTownFeeder(List<String> listToProcess, int maxPartitionSize){


            int listSize = listToProcess.size();
            System.out.println(listSize);
            Integer partitionSize2 = IntStream.range(1, maxPartitionSize).filter(e-> listSize % e == 0).reduce(Integer::max).orElseThrow();
//       use the partition size to slice the array.
            List<List<String>> collectorList = new ArrayList<>();
            // increase the min and max index with partition size until it is larger than the size - 1 of the array.
            int subLinkAmount = listSize / partitionSize2;
            List<Integer> itList = IntStream.range(1, subLinkAmount + 1).map(e -> e * partitionSize2).boxed().toList();
            itList.forEach(e ->
                    collectorList.add(listToProcess.subList(e - partitionSize2, e))
            );

            System.out.println(collectorList);
        }
        public String justExecute(){

        Set<String> locations =  getAllSettlement();
//                locations.removeAll(getPlacesWithLocationData());
                ;

            bulkTownFeeder( locations.stream().toList(), 6);

            return null;


        }






}
