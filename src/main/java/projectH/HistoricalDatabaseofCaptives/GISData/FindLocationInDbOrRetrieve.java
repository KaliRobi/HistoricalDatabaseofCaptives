package projectH.HistoricalDatabaseofCaptives.GISData;

import org.springframework.stereotype.Component;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.Captive;

import java.util.HashSet;
import java.util.Set;
@Component
public class FindLocationInDbOrRetrieve {

    private  final GeologicalOperations geologicalOperations;
//
    public FindLocationInDbOrRetrieve(GeologicalOperations geologicalOperations) {
        this.geologicalOperations = geologicalOperations;
    }

    public void checkCaptiveLocationAgainstGeoEntity(String location){
        // this pat is a bit wacky
        Set<String> locationSet = new HashSet<>();
        locationSet.add(location);
        geologicalOperations.getLocationData(locationSet);

    }


}
