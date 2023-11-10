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

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
@Component
public class ReviewLocations {


    private final GeologicalRepository geologicalRepository;
    private final CaptiveServices captiveServices;
    private final ReviewableEntityRepository reviewableEntityRepository;


    public ReviewLocations(GeologicalRepository geologicalRepository, CaptiveServices captiveServices, ReviewableEntityRepository reviewableEntityRepository) {
        this.geologicalRepository = geologicalRepository;
        this.captiveServices = captiveServices;
        this.reviewableEntityRepository = reviewableEntityRepository;
    }

    public void callTem(){
        trycompute();
    }


    public void reviewResidenceLocation(){
        Set<String> osvNameList = getOSVNames();
        List<ReviewableEntity> reviewableLocations = new ArrayList<>();

        Map<Long, String> placeOfResidence = captiveServices.getAllTheCaptives()
                .stream().collect(Collectors.toMap(Captive::getId, Captive::getPlace_of_residence));
        Set<Map.Entry<Long, String>> entitiesOfResidence = new HashSet<>(placeOfResidence.entrySet());

        for(Map.Entry<Long, String> entity : entitiesOfResidence){
            if(!osvNameList.contains(entity.getValue())){
                reviewableLocations
                        .add(new ReviewableEntity(entity.getKey(), entity.getValue(), "the locationOfResidence name "+entity.getValue()+"is not valid for captive with id "+ entity.getKey()));
            }

        }


    }
    private void reviewBirthPlaces(){
        Set<String> osvNameList = getOSVNames();
        List<ReviewableEntity> reviewableLocations = new ArrayList<>();
        Map<Long, String> placeOfBirth = captiveServices.getAllTheCaptives()
                .stream().collect(Collectors.toMap(Captive::getId, Captive::getPlace_of_birth));
        Set<Map.Entry<Long, String>> entitiesOfResidence = new HashSet<>(placeOfBirth.entrySet());
        for(Map.Entry<Long, String> entity : entitiesOfResidence) {
            if (!osvNameList.contains(entity.getValue())) {
                reviewableLocations
                        .add(new ReviewableEntity(entity.getKey(), entity.getValue(), "the locationOfResidence name " + entity.getValue() + "is not valid for captive with id " + entity.getKey()));
            }

        }
    }

    private void reviewArrestSites(){
        Map<Long, String> arrestSites = captiveServices.getAllTheCaptives()
                .stream().collect(Collectors.toMap(Captive::getId, Captive::getArrest_site));


    }


    private Set<String> getOSVNames(){
       return geologicalRepository.findAll().stream().map(GeoLocation::getOsv_name)
//                this deals with the cyryllic and arabic letters, the former will be a different topic because there the locations are correct just the language is different
//                Same with Slovakian and Romanian places
               .filter(e -> e.matches("([A-Z]([a-záéúőóüö.]+))")).collect(Collectors.toSet());

    }



    private void trycompute(){


        Map<Long, String> placeOfBirth = captiveServices.getAllTheCaptives()
                .stream().collect(Collectors.toMap(Captive::getId, Captive::getPlace_of_birth));
        Map<Long, String> placeOfResidence = captiveServices.getAllTheCaptives()
                .stream().collect(Collectors.toMap(Captive::getId, Captive::getPlace_of_residence));

//        Map<Long, String> sak = new HashMap<>(placeOfBirth);
        Set<Map.Entry<Long, String>> Ab = new HashSet<>(placeOfBirth.entrySet());
//        Set<Map.Entry<Long, String>> CD = new HashSet<>(placeOfResidence.entrySet());

        Ab.forEach( e->
                        placeOfResidence.computeIfPresent(e.getKey(), (k, v) -> v + v)

                );

    }

}
