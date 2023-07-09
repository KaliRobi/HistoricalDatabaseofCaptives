package projectH.HistoricalDatabaseofCaptives.GISData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveServices;

import java.util.HashSet;
import java.util.Set;


@Component
public class GeoServices {

    private final GeologicalRepository geologicalRepository;

    private CaptiveServices captiveServices;

    @Autowired
    public GeoServices(GeologicalRepository geologicalRepository) {
        this.geologicalRepository = geologicalRepository;

    }

    public void addGeographicalLocation(String sourceName, String OsvName, double lon, double lat){

        geologicalRepository.save(new GeoLocation(sourceName, OsvName,lon, lat));


    }
    public Set<String> getAllLocation() {
        Set<String> allThelocations = captiveServices.getCitiesOfBirth();
        allThelocations.addAll(captiveServices.getCitiesOfResidence());
        return allThelocations;
    }
    // get the locations without lat / lon data
    public  Set<String> getLocationsWithoutCoordinates() {
        Set<String> locationsWithoutLocationData = new HashSet<>();

        geologicalRepository.findAll().forEach(loca -> {
                    if (loca.getLatitude() == null || loca.getLongitude() == null) {
//                        both value need to be in the db to not get prepared for a new fetch
                        locationsWithoutLocationData.add(loca.getSource_name());
                    }
                }
        );

        return locationsWithoutLocationData;
    }

    public Set<String> getLocationsWithCoordinates() {
        Set<String> locationsWithLocationData = new HashSet<>();

        geologicalRepository.findAll().forEach(loca -> {
                    if (loca.getLatitude() != null  && loca.getLongitude() != null  ) {
//                        both value need to be in the db to not get prepared for a new fetch
                        locationsWithLocationData.add(loca.getSource_name());
                    }
                }
        );

        return locationsWithLocationData;
    }

    public GeoLocation getALocationByName(String name){
        return geologicalRepository.findByName(name);

    }



}
