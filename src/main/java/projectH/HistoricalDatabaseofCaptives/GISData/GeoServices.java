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



    public void addGeographicalLocation(String locationName, double lon, double lat){

        geologicalRepository.save(new GeoLocations(locationName,lon, lat));


    }



}
