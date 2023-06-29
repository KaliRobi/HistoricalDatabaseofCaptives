package projectH.HistoricalDatabaseofCaptives;


import projectH.HistoricalDatabaseofCaptives.CaptivesData.Captive;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import projectH.HistoricalDatabaseofCaptives.GISData.GeoServices;
import projectH.HistoricalDatabaseofCaptives.GISData.HdcGeolocator;

import java.net.URISyntaxException;
import java.util.*;
import java.util.concurrent.ExecutionException;


@RestController
public class FrontEndApiController {


    private final CaptiveServices captiveServices;

    private final HdcGeolocator hdcGeolocator;
    @Autowired
    public FrontEndApiController(CaptiveServices captiveServices, HdcGeolocator hdcGeolocator) {
        this.captiveServices = captiveServices;
        this.hdcGeolocator = hdcGeolocator;
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
@GetMapping(path="/v1/locations")
public Object settlementDetails() throws URISyntaxException,  InterruptedException, ExecutionException {
        return hdcGeolocator.getCityData();
}

    @GetMapping(path="/v1/test")
    public Object testest()  {
        return hdcGeolocator.getDistanceBetween();
    }

}
