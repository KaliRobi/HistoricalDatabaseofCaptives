package projectH.HistoricalDatabaseofCaptives.GISData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

    private final GeoServices geoServices;

    private  final  GeologicalOperationsBulk geologicalOperationsBulk;

    @Autowired
    public GeologicalOperations(GeoServices geoServices, GeologicalOperationsBulk geologicalOperationsBulk) {
        this.geoServices = geoServices;
        this.geologicalOperationsBulk = geologicalOperationsBulk;
    }

//    Application interface with openStreetView, what also send the retrieved coordinates to the  database.
//    This version of the class should be used for normal operations as supposed to be faster than the bulk version.
    @Override
    public void getLocationData(Set<String> targetTownSet) {

        //filter out the already processed locations
        targetTownSet.removeAll(geoServices.getLocationsWithCoordinates());

        if (targetTownSet.size() > 0) {
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
                    .thenApply(e -> e.concat(URLDecoder.decode(targetURi.toString(), StandardCharsets.UTF_8).substring(targetURi.toString().lastIndexOf("="))))

            ).toList();
            Map<String, String> collect;
            // return value from open street view
            for (CompletableFuture<String> lonLat : listOfLatLon) {
                String sourceName;
                String osvName;
                String country;
//                could be rewritten to use ObjectMapper like on the bulk version, but String methods are also an option here
//                probably removed in the production version
                try {
                    sourceName = Arrays.stream(lonLat.get().split("=")).toList().subList(1, 2).get(0);
                    collect = Arrays.stream(Arrays.asList(lonLat.get().split("}")).get(0).split(","))
                            .map(part -> part.replaceAll("\"", "").split(":"))
                            .filter(p -> p.length > 1)
                            .collect(Collectors.toMap(s -> s[0], s -> s[1])
                            );
                    collect.keySet().retainAll(List.of("display_name", "lon", "lat"));
                    osvName = collect.get("display_name").substring(0, collect.get("display_name").indexOf(','));
                    country = collect.get("display_name").substring(collect.get("display_name").lastIndexOf(',') + 1);
                } catch (InterruptedException | ExecutionException ex) {
                    throw new RuntimeException(ex);
                }
                geoServices.addGeographicalLocation(sourceName, osvName, Double.parseDouble(collect.get("lat")), Double.parseDouble(collect.get("lon")), country);
            }

        }
    }  

}
