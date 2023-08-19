package projectH.HistoricalDatabaseofCaptives;


import org.springframework.web.bind.annotation.*;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.Antropometrics;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CandidateFinder;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.Captive;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveServices;
import org.springframework.beans.factory.annotation.Autowired;
import projectH.HistoricalDatabaseofCaptives.GISData.GeoServices;
import projectH.HistoricalDatabaseofCaptives.GISData.GeologicalOperations;
import projectH.HistoricalDatabaseofCaptives.Users.Visitor;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;



@RestController
public class FrontEndApiController {
    private final CandidateFinder candidateFinder;
    private final Antropometrics antropometrics;
    private final CaptiveServices captiveServices;

    private final GeoServices geoServices;
    private final GeologicalOperations geologicalOperations;
    @Autowired
    public FrontEndApiController(CandidateFinder candidateFinder, Antropometrics antropometrics, CaptiveServices captiveServices, GeoServices geoServices, GeologicalOperations geologicalOperations) {
        this.candidateFinder = candidateFinder;
        this.antropometrics = antropometrics;

        this.captiveServices = captiveServices;
        this.geoServices = geoServices;
        this.geologicalOperations = geologicalOperations;
    }


@GetMapping(path = "/v1/allTheCaptives")
public List<Captive> exposeCaptives(){
return captiveServices.getAllTheCaptives();
}
@GetMapping(path="/v1/CitiesOfLocation")
public Iterable<String> exposeCitiesOfResidence(){ return captiveServices.getCitiesOfResidence();
 }
@GetMapping(path="/v1/SexDistributionPerCities")
public Map<String, HashMap<String, Long>> exposeSexDistribution(){
    return captiveServices.getSexDistribution();
}

@GetMapping(path="/v1/relocations")
public List<List<String>> exposeRelocations(){
    return captiveServices.getTheRelocated();
}
//@GetMapping(path="/v1/locations")
//public Object settlementDetails() throws URISyntaxException,  InterruptedException, ExecutionException {
//        return geologicalOperations.getCityData();
//}

    // get one customer

@GetMapping(path="/v1/test")
public Captive testest()  {
    LocalDate visBirth =  LocalDate.of(1988, 06, 14);
    Visitor visitor =  new Visitor(geoServices.getALocationByName("Budapest"), "f", visBirth );
    System.out.println(visitor);
    return  candidateFinder.returnCandidate(visitor);
}


@PostMapping(path = "/v1/postNewCaptive/")
    public void postNewCaptive(@RequestBody Captive captive){
    captiveServices.addCaptive(captive);



}

@PutMapping(path = "/v1/updateCaptive/")
public void updateCaptive(@RequestBody Captive captive){
    captiveServices.updateCaptive( captive);



}



}
