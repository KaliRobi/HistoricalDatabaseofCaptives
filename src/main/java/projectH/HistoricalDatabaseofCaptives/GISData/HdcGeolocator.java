package projectH.HistoricalDatabaseofCaptives.GISData;


import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveServices;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Component
public class HdcGeolocator {
    @Autowired
    private CaptiveServices captiveServices;
    public HashMap<String, HashMap<String, String>> getCityData  () throws URISyntaxException, InterruptedException, ExecutionException {



        // get a hasmap where the keyset is the set of the towns in the db
//        keyset should be =  getAllMentionedSettlement();

        HashMap<String, HashMap<String, String>> placesWiththeirLatLon = new HashMap<>();
        placesWiththeirLatLon.keySet().addAll(getAllMentionedSettlement());
        // will need to generate a list of new uri s then do clear out tha  data like below

//        the final goal is to get a hasmap where the city name is the key and the lat / lon data is the value in its own map, or a list

        // deal with the api request`
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("https://nominatim.openstreetmap.org/search?format=json&limit=3&q=Balmazujvaros"))
                .version(HttpClient.Version.HTTP_2).GET().build();

        CompletableFuture<HttpResponse<String>> response = HttpClient.newBuilder()
                .build()
                .sendAsync(request, HttpResponse.BodyHandlers.ofString());

        // normalising the incoming string into a map
        // get the first object of the return string
        String latLonString = Arrays.stream(response.get().body().split("}")).toList().get(1);
        // this object van be converted to a map by creating smaller arrays splitting the string two times,
        HashMap<String, String> lanLonMap = (HashMap<String, String>) Arrays.stream(latLonString.split(","))
                .map(part -> part.split(":"))
                // since there are undesirable arrays in this stream due to the "," splitting we need to get rid off them
                .filter(p -> p.length > 1 )
                .collect(Collectors.toMap(e->e[0], e->e[1]));
        List<String> validKeys = List.of("\"display_name\"", "\"lat\"", "\"lon\"" );
        lanLonMap.keySet().retainAll(validKeys);
        placesWiththeirLatLon.put(lanLonMap.get("\"display_name\""), lanLonMap );
        System.out.println(placesWiththeirLatLon);



        return  placesWiththeirLatLon;

    }

    private Set<String> getAllMentionedSettlement(){
        Set<String> allThePlaces =  captiveServices.getCitiesOfBirth();
        allThePlaces.addAll(captiveServices.getCitiesOfResidence());
// latter an overcharged version could check if geological_locations has them so it will generate only the list of the new places
        return allThePlaces;


    }


}
