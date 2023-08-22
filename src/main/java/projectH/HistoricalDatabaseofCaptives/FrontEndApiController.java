package projectH.HistoricalDatabaseofCaptives;


import org.springframework.web.bind.annotation.*;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CandidateFinder;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.Captive;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveServices;
import org.springframework.beans.factory.annotation.Autowired;
import projectH.HistoricalDatabaseofCaptives.GISData.DistanceVerifier;
import projectH.HistoricalDatabaseofCaptives.Users.Visitor;


import java.util.*;



@RestController
public class FrontEndApiController {
    private final CandidateFinder candidateFinder;
    private final DistanceVerifier distanceVerifier;
    private final CaptiveServices captiveServices;


    @Autowired
    public FrontEndApiController(CandidateFinder candidateFinder, DistanceVerifier distanceVerifier, CaptiveServices captiveServices) {
        this.candidateFinder = candidateFinder;
        this.distanceVerifier = distanceVerifier;
        this.captiveServices = captiveServices;


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



@GetMapping(path="/v1/test")
public Captive testest()  { // expose internals, recors? DTO? java 11?
        distanceVerifier.findOutstandingGeolocationCandidate();
    return null;
}


@PostMapping(path = "/v1/postNewCaptive/")
    public void postNewCaptive(@RequestBody Captive captive){
    captiveServices.addCaptive(captive);
}

@PostMapping(path = "/v1/whoWasSimilarToMe/")
    public Captive findACandidate(@RequestBody Visitor visitor){
    return candidateFinder.returnCandidate(visitor);
    }

@PutMapping(path = "/v1/updateCaptive/{id}")
public void updateCaptive(@PathVariable("id") long Id,  @RequestBody Captive captive){
    captiveServices.updateCaptive(Id, captive);
    }
//    add not found exception
}
