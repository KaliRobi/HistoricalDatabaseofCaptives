package projectH.HistoricalDatabaseofCaptives.GISData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.Captive;

//TODO
    //Reconsider the purpose of the class
    // should be facing out , instead provides different things to classes in and out side of the the package
@Component
public class GeoServices {

    private final FindLocationInDbOrRetrieve findLocationInDbOrRetrieve;
    private final GeologicalRepository geologicalRepository;




    @Autowired
    public GeoServices(GeologicalRepository geologicalRepository, FindLocationInDbOrRetrieve findLocationInDbOrRetrieve1) {
        this.geologicalRepository = geologicalRepository;
        this.findLocationInDbOrRetrieve = findLocationInDbOrRetrieve1;
    }




    public GeoLocation getALocationByName(String name){
            return geologicalRepository.findByName(name);
    }

    public void findLocationOrFetchIt(String geolocation){
        findLocationInDbOrRetrieve.checkCaptiveLocationAgainstGeoEntity(geolocation);
    }

}
