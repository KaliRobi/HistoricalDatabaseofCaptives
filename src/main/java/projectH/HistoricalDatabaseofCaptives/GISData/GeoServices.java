package projectH.HistoricalDatabaseofCaptives.GISData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GeoServices {


    private GeologicalRepository geologicalRepository;

    private final GeologicalLocations geologicalLocations;
    @Autowired
    public GeoServices(GeologicalRepository geologicalRepository, GeologicalLocations geologicalLocations) {
        this.geologicalRepository = geologicalRepository;

        this.geologicalLocations = geologicalLocations;
    }


    public void addGeographicalLocation(String locationName, double lon, double lat){
//        GeologicalLocations geologicalLocations = new GeologicalLocations();

        geologicalLocations.setName(locationName);
        geologicalLocations.setLongitude(lon);
        geologicalLocations.setLatitude(lat);


        System.out.println(geologicalLocations);
        geologicalRepository.save(geologicalLocations);


    }



}
