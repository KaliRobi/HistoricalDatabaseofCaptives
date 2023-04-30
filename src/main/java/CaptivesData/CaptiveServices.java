package CaptivesData;

// The class must describe all the captive relating functionalities.
// 1. Database  crud operations
// 2. calculations and data preparations what later will eb pushed to the RestApi Controller class thus to the front end,
// the application will be server heavy meaning that each of the objects on the front end will be served with the appropriate data
// ReactJs will deal mostly with the data presentation


import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Optional;

public class CaptiveServices {

    private final CaptiveRepository captiveRepository;
    @Autowired
    public CaptiveServices(CaptiveRepository captiveRepository) {
        this.captiveRepository = captiveRepository;
    }

    public Optional<Captive> GetCaptiveById(Long num){
     return captiveRepository.findById(num);
    }

    public Iterable<Captive> getAllTheCaptives(){
        return captiveRepository.findAll();
    }

    // needs to be decided if I want to pull the




}
