package projectH.HistoricalDatabaseofCaptives.CaptivesData;

// The class must describe all the captive relating functionalities.
// 1. Database  crud operations
// 2. calculations and data preparations what later will eb pushed to the RestApi Controller class thus to the front end,
// the application will be server heavy meaning that each of the objects on the front end will be served with the appropriate data
// ReactJs will deal mostly with the data presentation


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.TreeSet;

@Component
public class CaptiveServices {

    private final CaptiveRepository captiveRepository;
    @Autowired
    public CaptiveServices(CaptiveRepository captiveRepository) {
        this.captiveRepository = captiveRepository;
    }

    // This will serve the record single page
    public Optional<Captive> GetCaptiveById(Long num){
     return captiveRepository.findById(num);
    }


    public Iterable<Captive> getAllTheCaptives(){
        return captiveRepository.findAll();
    }

    // needs to be decided if I want to pull the


    public TreeSet<String> getCitiesOfResidence() {
        TreeSet<String> citiesOfResidence = new TreeSet<>();
        getAllTheCaptives().forEach(capt -> citiesOfResidence.add(capt.getPlace_of_residence()));
        return citiesOfResidence;
    }

}
