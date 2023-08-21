package projectH.HistoricalDatabaseofCaptives.GISData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveServices;

import java.util.HashSet;
import java.util.Set;

    //TODO
    //Recosider the purpose of the class
    // should be facing out , instead provides different things to classes in and out side of the the package
@Component
public class GeoServices {


    private final GeologicalRepository geologicalRepository;




    @Autowired
    public GeoServices(GeologicalRepository geologicalRepository, CaptiveServices captiveServices, FindLocationInDbOrRetrieve findLocationInDbOrRetrieve) {
        this.geologicalRepository = geologicalRepository;
    }




    public GeoLocation getALocationByName(String name){
        GeoLocation location  = geologicalRepository.findByName(name);
        return location != null ?  location : new GeoLocation();


    }

}
