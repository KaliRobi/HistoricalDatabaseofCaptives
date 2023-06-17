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
    @Autowired
    private CaptiveServices captiveServices;


    private GeoServices geoServices;

    private GeologicalRepository geologicalRepository;
    public HashMap<String, HashMap<String, String>> getCityData  () throws URISyntaxException, InterruptedException, ExecutionException {
 //maybe changing to google az openstreet view does not tolerate bulk requests very much
        // also the databse needs to be set up to store the already queried information so this will be the next getAllMentionedSettlement()

        HashMap<String, HashMap<String, String>> placesWiththeirLatLon = new HashMap<>();

        Set<String> targetTownSet = new HashSet<>(getAllSettlement());


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
        // return value as expected
        System.out.println(listOfLatLon.get(1).get());

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

    private Set<String> getAllSettlement(){
        Set<String> allThePlaces =  captiveServices.getCitiesOfBirth();
        allThePlaces.addAll(captiveServices.getCitiesOfResidence());
// latter an overcharged version could check if geological_locations has them so it will generate only the list of the new places

        // to test and to not to ge baned from openstreetwiev there will be 5 towns selected
        Set<String> tempSet = new HashSet<>();
        tempSet.add("Balmazújváros"); tempSet.add("Szolnok"); tempSet.add("Sáránd"); tempSet.add("Karcag"); tempSet.add("Hajdúnánás");
            return tempSet;
//        return allThePlaces;


    }

    private Set<String> getPlacesWithoutLocationData(){
        Set<String> placesWithLocationData = new HashSet<>();
        geologicalRepository.findAll().forEach( loca -> {
                    // get the locations without lat / lon data
                    // this or getlatitude > 0
                    if(String.valueOf(loca.getLatitude()).equals("")){
                        placesWithLocationData.add(loca.getName() );
                    }
                }
        );
        return placesWithLocationData;
    }

    //validate the distance from Budapest to check if the returned lat/lon data is ok

    private double getDistanceBetween(GeologicalLocations locationA, GeologicalLocations locationB ){
//        convert to Radian calc = degree * 3.1415926535 / 180;
        double latA = Math.toRadians(locationA.getLatitude());
        double lonA = Math.toRadians(locationA.getLongitude());
        double latB = Math.toRadians( locationB.getLatitude());
        double lonB = Math.toRadians( locationB.getLongitude());

        double radiusOfEarth = 6371000;
        double difLat = latB - latA;
        double difLon = lonB - lonA;
//        math.sin(delta_phi / 2.0) ** 2 + math.cos(phi_1) * math.cos(phi_2) * math.sin(delta_lambda / 2.0) ** 2
        double sinatan2Base = Math.sin(difLat / 2) * 2 + Math.cos(latA) * Math.cos(latB) * Math.sin(difLon);
        return 0;
    }
//    Haversine formula
//    phi_1 = math.radians(lat1)
//    phi_2 = math.radians(lat2)//
//    delta_phi = math.radians(lat2 - lat1)
//    delta_lambda = math.radians(lon2 - lon1)
//
//    a = math.sin(delta_phi / 2.0) ** 2 + math.cos(phi_1) * math.cos(phi_2) * math.sin(delta_lambda / 2.0) ** 2
//
//    c = 2 * math.atan2(math.sqrt(a), math.sqrt(1 - a))
//
//    meters = R * c  # output distance in meters
//    km = meters / 1000.0  # output distance in kilometers
//
//    meters = round(meters, 3)
//    km = round(km, 3)


//    D= 3440.1 * arccos[(sin (latA) * sin(lat B)) + cos(lat A ) * cos(lat B) * cos(lat A - long B)] everything is expressed in radians
    // radian = xπ / 180

    //tasks

    // first is to enter the thing to the database







}
