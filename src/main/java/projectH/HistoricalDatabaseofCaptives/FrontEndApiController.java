package projectH.HistoricalDatabaseofCaptives;


import projectH.HistoricalDatabaseofCaptives.CaptivesData.Captive;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FrontEndApiController {

    @Autowired
    private final CaptiveServices captiveServices;
    @Autowired
    public FrontEndApiController(CaptiveServices captiveServices) {
        this.captiveServices = captiveServices;
    }


    @GetMapping(path = "/v1/allTheCaptives")
public Iterable<Captive> GettingCaptives(){
    return captiveServices.getAllTheCaptives();


}





}
