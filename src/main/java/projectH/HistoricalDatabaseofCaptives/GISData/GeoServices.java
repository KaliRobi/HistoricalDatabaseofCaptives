package projectH.HistoricalDatabaseofCaptives.GISData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import projectH.HistoricalDatabaseofCaptives.ApplicationExceptions.NoSuchGeolocationAvailableException;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveServices;

import java.util.HashSet;
import java.util.Set;

    //TODO
    //would be nice to control  everything in this package from here GeologicalOps for example.
@Component
public class GeoServices {


    private final GeologicalRepository geologicalRepository;

    private final CaptiveServices captiveServices;

    @Autowired
    public GeoServices(GeologicalRepository geologicalRepository, CaptiveServices captiveServices) {
        this.geologicalRepository = geologicalRepository;

        this.captiveServices = captiveServices;
    }

    public void addGeographicalLocation(String sourceName, String OsvName, double lon, double lat, String country){

        geologicalRepository.save(new GeoLocation(sourceName, OsvName,lon, lat, country));


    }
    public Set<String> getAllLocation() {
        Set<String> allLocations = captiveServices.getCitiesOfBirth();
        allLocations.addAll(captiveServices.getCitiesOfResidence());

        return allLocations;
    }
    // get the locations without lat / lon data
    public  Set<String> getLocationsWithoutCoordinates() {
        Set<String> locationsWithoutLocationData = new HashSet<>();

        geologicalRepository.findAll().forEach(location -> {
                    if (location.getLatitude() == null || location.getLongitude() == null) {
//                        both value need to be in the db to not get prepared for a new fetch
                        locationsWithoutLocationData.add(location.getSource_name());
                    }
                }
        );

        return locationsWithoutLocationData;
    }

    public Set<String> getLocationsWithCoordinates() {
        Set<String> locationsWithLocationData = new HashSet<>();

        geologicalRepository.findAll().forEach(l -> {
                    if (l.getLatitude() != null  && l.getLongitude() != null  ) {
//                        both value need to be in the db to not get prepared for a new fetch
                        locationsWithLocationData.add(l.getSource_name());
                    }
                }
        );

        return locationsWithLocationData;
    }

    public GeoLocation getALocationByName(String name){
        GeoLocation location  = geologicalRepository.findByName(name);
        return location != null ?  location : new GeoLocation();


    }



}
