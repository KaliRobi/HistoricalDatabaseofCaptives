package projectH.HistoricalDatabaseofCaptives.GISData;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveServices;

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
import java.util.stream.Collectors;

@Component
public class GeologicalOperations implements IGeolocator{
    private CaptiveServices captiveServices;
    private final GeoServices geoServices;

    private  final GeologicalOperationsBulk geologicalOperationsBulk;
    private GeologicalRepository geologicalRepository;

    @Autowired
    public GeologicalOperations(CaptiveServices captiveServices, GeoServices geoServices, GeologicalOperationsBulk geologicalOperationsBulk, GeologicalRepository geologicalRepository) {
        this.captiveServices = captiveServices;
        this.geoServices = geoServices;
        this.geologicalOperationsBulk = geologicalOperationsBulk;
        this.geologicalRepository = geologicalRepository;
    }

//    Application interface with openStreetView, what also send the retrieved coordinates to the  database.
//    This version of the class should be used for normal operations as supposed to be faster than the bulk version.
    @Override
    public void getCityData() {

        Set<String> targetTownSet = new HashSet<>(geoServices.getAllLocation());

//        Remove all when they are present in the geological_locations
        targetTownSet.removeAll(geoServices.getLocationsWithCoordinates());

        //creating uri list while dealing with the special Hungarian characters
        List<URI> targetUris = targetTownSet.stream().map(target ->
        {
            try {
                return new URI("https://nominatim.openstreetmap.org/search?format=json&limit=3&q=" + URLEncoder.encode(target, StandardCharsets.UTF_8));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }).toList();

        HttpClient client = HttpClient.newHttpClient();
        List<CompletableFuture<String>> listOfLatLon = targetUris.stream().map(targetURi -> client
                .sendAsync(HttpRequest.newBuilder(targetURi).GET().build(), HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply( e -> e.concat(URLDecoder.decode(targetURi.toString(), StandardCharsets.UTF_8).substring(targetURi.toString().lastIndexOf("="))   ))

        ).toList();
        Map<String, Map<String, String>> locationsWithCoordinates = new HashMap<>();
        listOfLatLon.forEach(e -> {
            try {
                System.out.println(e.get());  ;
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            } catch (ExecutionException ex) {
                throw new RuntimeException(ex);
            }
        });

        Map<String, String> collect = null;
        // return value from open street view
         for( CompletableFuture<String> lonLat : listOfLatLon ) {
             String locationName;
             try {
                 locationName = Arrays.stream(lonLat.get().split("=")).toList().subList(1, 2).get(0);
                 collect = Arrays.stream(Arrays.asList(lonLat.get().split("}")).get(0).split(","))
                         .map(part -> part.replaceAll("\"", "").split(":"))
                         .filter(p -> p.length > 1)
                         .collect(Collectors.toMap(s -> s[0], s -> s[1])
                         );
                 collect.keySet().retainAll(List.of("display_name", "lon", "lat"));
                 locationsWithCoordinates.put(collect.get("display_name"), collect);

             } catch (InterruptedException | ExecutionException ex) {
                 throw new RuntimeException(ex);
             }
             geoServices.addGeographicalLocation(locationName, collect.get("display_name"), Double.parseDouble(collect.get("lat")), Double.parseDouble(collect.get("lon")));
         }





    }






        public String justExecute() throws URISyntaxException, ExecutionException, InterruptedException {

            getCityData();

            return null;


        }






}
