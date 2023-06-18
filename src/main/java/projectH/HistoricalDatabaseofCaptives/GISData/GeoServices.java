package projectH.HistoricalDatabaseofCaptives.GISData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class GeoServices {

    private  GeologicalLocations geologicalLocations;
    private  GeologicalRepository geologicalRepository;



    public void addGeographicalLocation(String locationName, double lon, double lat){
        geologicalLocations.setLongitude(lon);
        geologicalLocations.setLatitude(lat);
        geologicalLocations.setName(locationName);

        geologicalRepository.save(geologicalLocations);



    }



}
