package projectH.HistoricalDatabaseofCaptives;


import CaptivesData.Captive;
import CaptivesData.CaptiveServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class RestApiController {

    private final CaptiveServices captiveServices;
    @Autowired
    public RestApiController(CaptiveServices captiveServices) {
        this.captiveServices = captiveServices;
    }


    @GetMapping(path = "/v1/allTheCaptives")
public Iterable<Captive> GettingCaptives(){
    return captiveServices.getAllTheCaptives();


}





}
