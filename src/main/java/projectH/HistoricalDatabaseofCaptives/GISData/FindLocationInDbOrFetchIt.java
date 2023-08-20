package projectH.HistoricalDatabaseofCaptives.GISData;

import projectH.HistoricalDatabaseofCaptives.CaptivesData.Captive;

import java.util.HashSet;
import java.util.Set;

public class FindLocationInDbOrFetchIt {

    private  final GeologicalOperations geologicalOperations;

    public FindLocationInDbOrFetchIt(GeologicalOperations geologicalOperations) {
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
