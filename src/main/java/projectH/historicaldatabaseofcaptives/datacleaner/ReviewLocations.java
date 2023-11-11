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

    public void reviewLocations(){
        Map<Long, String>  reviewableCandidates =  mergeNonMatchingLocations();
        saveReviewableEntities(reviewableCandidates);
    }


    private Map<Long, String>  reviewResidences(){
        Map<Long, String> placeOfResidence = captiveServices.getAllTheCaptives()
                .stream().filter(e -> e.getPlace_of_residence()!= null).collect(Collectors.toMap(Captive::getId, Captive::getPlace_of_residence));
        return extractReviewables(placeOfResidence);
    }
    private Map<Long, String>  reviewBirthPlaces(){
        Map<Long, String> placeOfBirth = captiveServices.getAllTheCaptives()
                .stream().filter(e -> e.getPlace_of_birth() != null).collect(Collectors.toMap(Captive::getId, Captive::getPlace_of_birth));
        return extractReviewables(placeOfBirth);
    }

    private Map<Long, String> reviewArrestSites(){
        Map<Long, String> arrestSites = captiveServices.getAllTheCaptives().stream().filter(e -> e.getArrest_site() != null)
                .collect(Collectors.toMap(Captive::getId, Captive::getArrest_site));
        return extractReviewables(arrestSites);

    }


    private Map<Long, String> extractReviewables(Map<Long, String> entryMap){
        Map<Long, String> reviewableLocations = new HashMap<>();
        Set<String> osvNameList = getOSVNames();
        for(Map.Entry<Long, String> entity : entryMap.entrySet()) {
            if (!osvNameList.contains(entity.getValue()) && entity.getValue() != null) {

                reviewableLocations
                        .put(entity.getKey(), entity.getValue());
            }
        }
        return reviewableLocations;
    }


    private Set<String> getOSVNames(){
//        this deals with the cyryllic and arabic letters, the former will be a different topic because there the locations are correct just the language is different
//        Same with Slovakian and Romanian places
       return geologicalRepository.findAll().stream().map(GeoLocation::getOsv_name)
               .filter(e -> e.matches("([A-ZÁÉÚÖŐÓÜŰÍ]([a-záéúöőóüűí.]+))")).collect(Collectors.toSet());

    }

    private Map<Long, String> mergeNonMatchingLocations(){
        Map<Long, String>  birthPlaceMap = reviewBirthPlaces();
        Map<Long, String>  residenceMap = reviewResidences();
        Map<Long, String>  arrestSiteMap = reviewArrestSites();
        Set<Map.Entry<Long, String>> arrestSitesToProcess = new HashSet<>(arrestSiteMap.entrySet());
        Set<Map.Entry<Long, String>> residenceToProcess = new HashSet<>(residenceMap.entrySet());
        residenceToProcess.forEach( e->
                birthPlaceMap.computeIfPresent(e.getKey(), (k, v) -> returnNewValue(v, v) )
        );
        residenceToProcess.forEach(e->
                birthPlaceMap.computeIfAbsent(e.getKey(), s-> e.getValue())
        );
        arrestSitesToProcess.forEach( e->
                birthPlaceMap.computeIfPresent(e.getKey(), (k, v) -> returnNewValue(v, v) )
        );
        arrestSitesToProcess.forEach(e->
                birthPlaceMap.computeIfAbsent(e.getKey(), s-> e.getValue())
        );

        return birthPlaceMap;

    }

    private String returnNewValue(String value, String valueToAdd){
         return value.equals(valueToAdd) ? value : value + ", " + valueToAdd;
    }

    private void saveReviewableEntities(Map<Long, String> candidateMap){

        for(Map.Entry<Long, String> entity : candidateMap.entrySet()){
            reviewableEntityRepository.save(
            new ReviewableEntity(entity.getKey(), entity.getValue(), "Invalid location name(s) "+entity.getValue()+" for captive with id "+ entity.getKey()));

        }

    }
}
