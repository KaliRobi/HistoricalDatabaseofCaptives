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
import projectH.HistoricalDatabaseofCaptives.DataCleaner.DataCleanerService;
import projectH.HistoricalDatabaseofCaptives.DataCleaner.FindOutliers;
import projectH.HistoricalDatabaseofCaptives.DataCleaner.LocalAbbreviatedEntity;
import projectH.HistoricalDatabaseofCaptives.DataCleaner.ReviewAbbreviations;
import projectH.HistoricalDatabaseofCaptives.Users.Visitor;


import java.util.*;



@RestController
public class FrontEndApiController {
    private final CandidateFinder candidateFinder;
    private final  DataCleanerService dataCleanerService ;
    private final CaptiveServices captiveServices;

    private final ReviewAbbreviations reviewAbbreviations;





    @Autowired
    public FrontEndApiController(CandidateFinder candidateFinder, DataCleanerService dataCleanerService, CaptiveServices captiveServices, ReviewAbbreviations reviewAbbreviations) {
        this.candidateFinder = candidateFinder;
        this.dataCleanerService = dataCleanerService;

        this.captiveServices = captiveServices;
        this.reviewAbbreviations = reviewAbbreviations;

    }

@GetMapping(path = "/v1/allTheCaptives")
    public ResponseEntity<List<Captive>> exposeCaptives(){
    try {
        return new ResponseEntity<>(captiveServices.getAllTheCaptives(), HttpStatus.OK);
    } catch (Exception e){
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
@GetMapping(path="/v1/LocationsOfResidence")
// this iterable needs to be changed
    public ResponseEntity<Iterable<String>> exposeLocationsOfResidence(){
    try {
        return new ResponseEntity<>(captiveServices.getLocationsOfResidence(), HttpStatus.OK);
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
public void testest()  {
    dataCleanerService.startCleaning();

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

    @PostMapping(path="/v1/AddAbbrevs")
    public ResponseEntity<List<LocalAbbreviatedEntity>> addAbbreviations(@RequestBody List<LocalAbbreviatedEntity> list ) {
        try{
            reviewAbbreviations.addAbbreviations(list);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
// keeping the old version for now.
//@PutMapping(path = "/v1/updateCaptive/{id}")
//public ResponseEntity<String> updateCaptive(@PathVariable("id") long Id, @RequestBody Captive captive){
//    try{
//        captiveServices.updateCaptive(Id, captive);
//    } catch (NoSuchCaptiveIdFound e){
//        return new ResponseEntity<>("Captive with id " + Id + "does not exists", HttpStatus.NOT_FOUND);
//    }
//    return new ResponseEntity<>(HttpStatus.OK);
//    }

@PutMapping(path = "/v1/updateCaptiveV2/{id}")
    public ResponseEntity<String> updateCaptiveV2(@PathVariable("id") long Id, @RequestBody Map<String, Object>  updateAttribute){
        try{
            captiveServices.updateCaptiveV2(Id, updateAttribute);
        } catch (NoSuchCaptiveIdFound e){
            return new ResponseEntity<>("Captive with id " + Id + "does not exists", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
