package projectH.historicaldatabaseofcaptives.gisdata;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
@Component
public class WithOrWithoutCoordinates {

    private final GeologicalRepository geologicalRepository;

    public WithOrWithoutCoordinates(GeologicalRepository geologicalRepository) {
        this.geologicalRepository = geologicalRepository;
    }

    // get the locations without lat / lon data
    public Set<String> getLocationsWithoutCoordinates() {
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
}
