package projectH.HistoricalDatabaseofCaptives.GISData;


import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveServices;

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
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Component
public class HdcGeolocator {
    @Autowired
    private CaptiveServices captiveServices;
    public HashMap<String, HashMap<String, String>> getCityData  () throws URISyntaxException, InterruptedException, ExecutionException {
 //maybe changing to google az openstreet view does not tolerate bulk requests very much
        // also the databse needs to be set up to store the already queried information so this will be the next getAllMentionedSettlement()

        HashMap<String, HashMap<String, String>> placesWiththeirLatLon = new HashMap<>();
        Set<String> placesKeyset = getAllMentionedSettlement();

        Set<String> targetTownSet = new HashSet<>(getAllMentionedSettlement());

        //creating uri list while dealing with the special Hungarian characters
        List<URI> targetUris = targetTownSet.stream().map(target ->
        {
            try {
                return  new URI("https://nominatim.openstreetmap.org/search?format=json&limit=3&q=" + URLEncoder.encode(target, StandardCharsets.UTF_8) );
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }).toList();

//        the final goal is to get a hasmap where the city name is the key and the lat / lon data is the value in its own map, or a list
        System.out.println(targetUris);
        // deal with the api request`
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://nominatim.openstreetmap.org/search?format=json&limit=3&q=Balmazujvaros"))
                .version(HttpClient.Version.HTTP_2).GET().build();

        CompletableFuture<HttpResponse<String>> response = HttpClient.newBuilder()
                .build()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());

        HttpClient client = HttpClient.newHttpClient();
        List<CompletableFuture<String>> listOfLatLon = targetUris.stream().map(targetURi -> client
                .sendAsync(HttpRequest.newBuilder(targetURi).GET().build(), HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
        ).toList();

        System.out.println(listOfLatLon.get(0).isDone());

        // normalising the incoming string into a map
        // get the first object of the return string
        String latLonString = Arrays.stream(response.get().body().split("}")).toList().get(1);
        // this object van be converted to a map by creating smaller arrays splitting the string two times,
        HashMap<String, String> latLonMap = (HashMap<String, String>) Arrays.stream(latLonString.split(","))
                .map(part -> part.replaceAll("\"", "").split(":"))
                // since there are undesirable arrays in this stream due to the "," splitting we need to get rid off them
                .filter(p -> p.length > 1 )
                .collect(Collectors.toMap(e->e[0], e->e[1]));
        System.out.println(latLonMap);
        latLonMap.keySet().retainAll(List.of("display_name", "lat", "lon" ));
        placesWiththeirLatLon.put(latLonMap.get("display_name"), latLonMap );

        return  placesWiththeirLatLon;

    }

    private Set<String> getAllMentionedSettlement(){
        Set<String> allThePlaces =  captiveServices.getCitiesOfBirth();
        allThePlaces.addAll(captiveServices.getCitiesOfResidence());
// latter an overcharged version could check if geological_locations has them so it will generate only the list of the new places
        return allThePlaces;


    }


}
