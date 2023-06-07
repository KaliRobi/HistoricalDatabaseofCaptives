package projectH.HistoricalDatabaseofCaptives;


import projectH.HistoricalDatabaseofCaptives.CaptivesData.Captive;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;


@RestController
public class FrontEndApiController {

    @Autowired
    private final CaptiveServices captiveServices;
    @Autowired
    public FrontEndApiController(CaptiveServices captiveServices) {
        this.captiveServices = captiveServices;
    }


    @GetMapping(path = "/v1/allTheCaptives")
public List<Captive> exposeCaptives(){
    return captiveServices.getAllTheCaptives();
}
    @GetMapping(path="/v1/CitiesOfLocation")
 public Iterable<String> exposeCitiesOfResidence(){

        return captiveServices.getCitiesOfResidence();
 }
 @GetMapping(path="/v1/SexDistributionPerCities")
 public Map<String, HashMap<String, Long>> exposeSexDistribution(){
        return captiveServices.getSexDistribution();
 }

    @GetMapping(path="/v1/relocations")
    public List<List<String>> exposeRelocations(){
        return captiveServices.getTheRelocated();
    }



}
