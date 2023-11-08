package projectH.historicaldatabaseofcaptives.datacleaner;
/*
As it turns out to support all the scientific needs of the project the geoloaction part needed to be quite extensive
This is why Class will attempt to give a separate cleaning method
This will be a blueprint for now.
 */


import org.springframework.stereotype.Component;
import projectH.historicaldatabaseofcaptives.captivesdata.Captive;
import projectH.historicaldatabaseofcaptives.captivesdata.CaptiveServices;
import projectH.historicaldatabaseofcaptives.gisdata.GeoLocation;
import projectH.historicaldatabaseofcaptives.gisdata.GeologicalRepository;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
        Set<String> osvNameList = geologicalRepository.findAll().stream().map(GeoLocation::getOsv_name)
//                this deals with the cyryllic and arabic letters, the former will be a different topic because there the locations are correct just the language is dufferent
//                Same with Slovakian and Romanian places
                .filter(e -> e.matches("([A-Z]([a-záéúőóüö.]+))")).collect(Collectors.toSet());
        System.out.println(osvNameList);
        System.out.println(geologicalRepository.findAll().stream().map(GeoLocation::getOsv_name).toList());
        // first with one column
        Map<Long, String> locations = captiveServices.getAllTheCaptives()
                .stream().collect(Collectors.toMap(Captive::getId, Captive::getPlace_of_residence));
        Set<Map.Entry<Long, String>> entities = new HashSet<>(locations.entrySet());

//        entities;



    }

}
