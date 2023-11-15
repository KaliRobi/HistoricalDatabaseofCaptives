package projectH.historicaldatabaseofcaptives;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import projectH.historicaldatabaseofcaptives.applicationexceptions.NoSuchCaptiveIdFound;
import projectH.historicaldatabaseofcaptives.captivesdata.CandidateFinder;
import projectH.historicaldatabaseofcaptives.captivesdata.Captive;
import projectH.historicaldatabaseofcaptives.captivesdata.CaptiveServices;
import org.springframework.beans.factory.annotation.Autowired;
import projectH.historicaldatabaseofcaptives.datacleaner.DataCleanerService;
import projectH.historicaldatabaseofcaptives.datacleaner.LocalAbbreviatedEntity;
import projectH.historicaldatabaseofcaptives.datacleaner.ReviewAbbreviations;
import projectH.historicaldatabaseofcaptives.datacleaner.ReviewLocations;
import projectH.historicaldatabaseofcaptives.security.AuthenticationRequest;
import projectH.historicaldatabaseofcaptives.security.AuthenticationResponse;
import projectH.historicaldatabaseofcaptives.security.AuthenticationService;
import projectH.historicaldatabaseofcaptives.users.Visitor;


import java.util.*;



@RestController
public class FrontEndApiController {
    private final CandidateFinder candidateFinder;
    private final  DataCleanerService dataCleanerService ;
    private final CaptiveServices captiveServices;
    private final ReviewLocations reviewLocations;

    private final ReviewAbbreviations reviewAbbreviations;
    private final AuthenticationService authenticationService;





    @Autowired
    public FrontEndApiController(CandidateFinder candidateFinder, DataCleanerService dataCleanerService, CaptiveServices captiveServices, ReviewLocations reviewLocations, ReviewAbbreviations reviewAbbreviations, AuthenticationService authenticationService) {
        this.candidateFinder = candidateFinder;
        this.dataCleanerService = dataCleanerService;
        this.captiveServices = captiveServices;
        this.reviewLocations = reviewLocations;
        this.reviewAbbreviations = reviewAbbreviations;
        this.authenticationService = authenticationService;
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
   reviewLocations.reviewLocations();

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

@PutMapping(path = "/v1/updateCaptiveV2/{id}")
    public ResponseEntity<String> updateCaptiveV2(@PathVariable("id") long Id, @RequestBody Map<String, Object>  updateAttribute){
        try{
            captiveServices.updateCaptiveV2(Id, updateAttribute);
        } catch (NoSuchCaptiveIdFound e){
            return new ResponseEntity<>("Captive with id " + Id + "does not exists", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/v1/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
