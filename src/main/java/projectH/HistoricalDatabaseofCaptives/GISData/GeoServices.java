package projectH.HistoricalDatabaseofCaptives.GISData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class GeoServices {

    private final GeologicalRepository geologicalRepository;


    @Autowired
    public GeoServices(GeologicalRepository geologicalRepository) {
        this.geologicalRepository = geologicalRepository;

    }



    public void addGeographicalLocation(String sourceName, String OsvName, double lon, double lat){

        geologicalRepository.save(new GeoLocation(sourceName, OsvName,lon, lat));


    }



}
