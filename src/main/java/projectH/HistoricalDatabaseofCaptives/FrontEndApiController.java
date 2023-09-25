package projectH.HistoricalDatabaseofCaptives;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectH.HistoricalDatabaseofCaptives.ApplicationExceptions.NoSuchCaptiveIdFound;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CandidateFinder;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.Captive;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveServices;
import org.springframework.beans.factory.annotation.Autowired;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CrimeStatistics;
import projectH.HistoricalDatabaseofCaptives.GISData.DistanceVerifierUtility;
import projectH.HistoricalDatabaseofCaptives.Users.Visitor;


import java.util.*;



@RestController
public class FrontEndApiController {
    private final CandidateFinder candidateFinder;
    private final DistanceVerifierUtility distanceVerifierUtility;
    private final CaptiveServices captiveServices;
    
    private final CrimeStatistics crimeStatistics;



    @Autowired
    public FrontEndApiController(CandidateFinder candidateFinder, DistanceVerifierUtility distanceVerifierUtility, CaptiveServices captiveServices, CrimeStatistics crimeStatistics) {
        this.candidateFinder = candidateFinder;
        this.distanceVerifierUtility = distanceVerifierUtility;
        this.captiveServices = captiveServices;


        this.crimeStatistics = crimeStatistics;
    }


@GetMapping(path = "/v1/allTheCaptives")
    public ResponseEntity<List<Captive>> exposeCaptives(){
    try {
        return new ResponseEntity<>(captiveServices.getAllTheCaptives(), HttpStatus.OK);
    } catch (Exception e){
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
@GetMapping(path="/v1/CitiesOfLocation")
// this iterable needs to be changed
    public ResponseEntity<Iterable<String>> exposeCitiesOfResidence(){
    try {
        return new ResponseEntity<>(captiveServices.getCitiesOfResidence(), HttpStatus.OK);
    } catch (Exception e){
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

 }
@GetMapping(path="/v1/SexDistributionPerCities")
    public ResponseEntity<Map<String, HashMap<String, Long>>> exposeSexDistribution(){
    try {
        return new ResponseEntity<>(captiveServices.getSexDistribution(), HttpStatus.OK);
    } catch (Exception e){
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}

@GetMapping(path="/v1/relocations")
    public ResponseEntity<List<List<String>>> exposeRelocations(){
    try{
        return new ResponseEntity<>(captiveServices.getTheRelocated(), HttpStatus.OK) ;
    } catch (Exception e){
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}



@GetMapping(path="/v1/test")
public Object testest()  {

    return crimeStatistics.frequencyOfCrimePerGender();
}


@PostMapping(path = "/v1/postNewCaptive/")
    public ResponseEntity<String> postNewCaptive(@RequestBody Captive captive){
    captiveServices.addCaptive(captive);
    return new ResponseEntity<>("Captive posted", HttpStatus.OK);
}

@PostMapping(path = "/v1/whoWasSimilarToMe/")
    public ResponseEntity<Captive> findACandidate(@RequestBody Visitor visitor){
    try {
     return  new ResponseEntity<>(candidateFinder.returnCandidate(visitor), HttpStatus.OK) ;
    } catch (Exception e){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    }

@PutMapping(path = "/v1/updateCaptive/{id}")
public ResponseEntity<String> updateCaptive(@PathVariable("id") long Id, @RequestBody Captive captive){
    try{
        captiveServices.updateCaptive(Id, captive);
    } catch (NoSuchCaptiveIdFound e){
        return new ResponseEntity<>("Captive with id " + Id + "does not exists", HttpStatus.NOT_FOUND);
    }
    return new ResponseEntity<>(HttpStatus.OK);
    }

}
