package projectH.HistoricalDatabaseofCaptives.GISData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class GeoServices {
    @Autowired
   GeologicalRepository geologicalRepository;
    GeologicalLocations geologicalLocations;
    @Autowired
    public GeoServices(GeologicalRepository geologicalRepository) {
        this.geologicalRepository = geologicalRepository;
    }


    public void addGeographicalLocation(String locationName, double lon, double lat){


        geologicalLocations.setName(locationName);
        geologicalLocations.setLongitude(lon);
        geologicalLocations.setLatitude(lat);


        geologicalRepository.save(geologicalLocations);
        System.out.println("here we are");

    }



}
