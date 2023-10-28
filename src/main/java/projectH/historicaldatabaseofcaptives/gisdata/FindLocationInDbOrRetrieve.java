package projectH.historicaldatabaseofcaptives.gisdata;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
@Component
public class FindLocationInDbOrRetrieve {

    private  final OpenStreetViewInterface openStreetViewInterface;
//
    public FindLocationInDbOrRetrieve(OpenStreetViewInterface openStreetViewInterface) {
        this.openStreetViewInterface = openStreetViewInterface;
    }
// using a collection is better
    public void checkCaptiveLocationAgainstGeoEntity(String location){

        Set<String> locationSet = new HashSet<>();
        locationSet.add(location);
        openStreetViewInterface.getLocationData(locationSet);

    }



}
