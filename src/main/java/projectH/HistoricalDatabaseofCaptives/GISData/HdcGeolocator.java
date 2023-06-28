package projectH.HistoricalDatabaseofCaptives.GISData;


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
        targetTownSet.removeAll(getPlacesWithoutLocationData());

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
                collect.keySet().retainAll(List.of("display_name", "lat", "lon"));
                System.out.println(temList.get(collect.get("display_name")));
                temList.put(collect.get("display_name"), collect);
                geoServices.addGeographicalLocation(collect.get("display_name"), Double.parseDouble(collect.get("lon")), Double.parseDouble(collect.get("lat")));
            } catch (InterruptedException | ExecutionException ex) {
                throw new RuntimeException(ex);
            }
        });

        return placesWiththeirLatLon;

    }

    private Set<String> getAllSettlement() {
        Set<String> allThePlaces = captiveServices.getCitiesOfBirth();
        allThePlaces.addAll(captiveServices.getCitiesOfResidence());
        return allThePlaces;
    }
    // get the locations without lat / lon data
    public Set<String> getPlacesWithoutLocationData() {
        Set<String> placesWithoutLocationData = new HashSet<>();
        geologicalRepository.findAll().forEach(loca -> {
                    if (loca.getLatitude() != 0 || loca.getLongitude() != 0) {
//                        both value need to be in the db to not het prepared for a new fetch
                        placesWithoutLocationData.add(loca.getName());
                    }
                }
        );

        return placesWithoutLocationData;
    }

    //validate the distance from Budapest to check if the returned lat/lon data is ok

    private double getDistanceBetween(GeoLocations locationA, GeoLocations locationB) {
//        convert to Radian calc = degree * 3.1415926535 / 180;
        double latA = Math.toRadians(locationA.getLatitude());
        double lonA = Math.toRadians(locationA.getLongitude());
        double latB = Math.toRadians(locationB.getLatitude());
        double lonB = Math.toRadians(locationB.getLongitude());

        double radiusOfEarth = 6371000;
        double difLat = latB - latA;
        double difLon = lonB - lonA;
//      Haversine formula
        // based on https://community.esri.com/t5/coordinate-reference-systems-blog/distance-on-a-sphere-the-haversine-formula/ba-p/902128
        // https://www.vcalc.com/wiki/vCalc/Haversine+-+Distance
        double atan2Base = Math.sin(difLat / 2) * 2 + Math.cos(latA) * Math.cos(latB) * Math.sin(difLon / 2) * 2;

        double distanceInKm = (Math.atan2(Math.sqrt(atan2Base), Math.sqrt(1 - atan2Base))) * radiusOfEarth / 1000.0;

        return 0;
    }



    //tasks
//
    //TODO
    //    build an in memory cache so it will see what the we need to get from open street view







}
