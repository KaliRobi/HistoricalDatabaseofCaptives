package projectH.historicaldatabaseofcaptives.datacleaner;
/*
As it turns out to support all the scientific needs of the project the geoloaction part needed to be quite extensive
This is why Class will attempt to give a separate cleaning method
This will be a blueprint for now.
 */

import org.springframework.stereotype.Component;
import projectH.historicaldatabaseofcaptives.captivesdata.Captive;
import projectH.historicaldatabaseofcaptives.captivesdata.CaptiveServices;
import projectH.historicaldatabaseofcaptives.gisdata.GeologicalRepository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Component
public class ReviewLocations {


    private final GeologicalRepository geologicalRepository;
    private final CaptiveServices captiveServices;


    public ReviewLocations(GeologicalRepository geologicalRepository, CaptiveServices captiveServices) {
        this.geologicalRepository = geologicalRepository;
        this.captiveServices = captiveServices;
    }

    public void reviewLocations(){
        // first with one column
        Map<Long, String> locations = captiveServices.getAllTheCaptives()
                .stream().collect(Collectors.toMap(Captive::getId, Captive::getPlace_of_residence));
        locations.entrySet().stream().sorted();



    }

}
