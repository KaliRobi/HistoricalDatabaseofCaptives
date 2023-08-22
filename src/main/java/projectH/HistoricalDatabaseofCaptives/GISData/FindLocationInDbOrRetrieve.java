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

    public void checkCaptiveLocationAgainstGeoEntity(Captive captive){
        Set<String> locationSet = new HashSet<>();
        locationSet.add(captive.getPlace_of_birth());
        locationSet.add(captive.getPlace_of_residence());
        locationSet.add(captive.getArrest_site());
        geologicalOperations.getLocationData(locationSet);

    }


}
