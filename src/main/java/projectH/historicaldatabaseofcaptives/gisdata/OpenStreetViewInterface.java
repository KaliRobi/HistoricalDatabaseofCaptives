package projectH.historicaldatabaseofcaptives.gisdata;

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

/** a way to improve this is to extend osvobject and map the whole json file provided by osv
 * Then decide filter out the object by priority countries
 * 1. Hungary; 2 Slovenia, Austria, Slovakia, ukraine, Romania, Serbia; 3 all the rest.
 */

@Component
public class OpenStreetViewInterface implements IGeolocator{
    private final static  String DISPLAY_NAME = "display_name";
    private  GeologicalRepository geologicalRepository;
    private  WithOrWithoutCoordinates withOrWithoutCoordinates;


    @Autowired
    public OpenStreetViewInterface(GeologicalRepository geologicalRepository, WithOrWithoutCoordinates withOrWithoutCoordinates) {
        this.geologicalRepository = geologicalRepository;
        this.withOrWithoutCoordinates = withOrWithoutCoordinates;
    }

    public OpenStreetViewInterface() {

    }

    //    Application interface with openStreetView, what also send the retrieved coordinates to the  database.
//    This version of the class should be used for normal operations because it is supposedly faster than the bulk version.
    @Override
    public void getLocationData(Set<String> targetTownSet) {




        //filter out the already processed locations
        try {
            targetTownSet.removeAll(withOrWithoutCoordinates.getLocationsWithCoordinates());
        } catch (NullPointerException e){
            throw new NullPointerException("targetTownSet is empty");
        }
        if (!targetTownSet.isEmpty() && targetTownSet.toArray()[0] != null) {
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
                    collect.keySet().retainAll(List.of(DISPLAY_NAME, "lon", "lat"));
                    osvName = collect.get(DISPLAY_NAME).substring(0, collect.get(DISPLAY_NAME).indexOf(','));
                    country = collect.get(DISPLAY_NAME).substring(collect.get(DISPLAY_NAME).lastIndexOf(',') + 1);
                } catch (InterruptedException | ExecutionException ex) {
                    throw new RuntimeException(ex);
                }
                geologicalRepository.save(new GeoLocation(sourceName, osvName, Double.parseDouble(collect.get("lat")), Double.parseDouble(collect.get("lon")), country));
            }

        }
    }


}
