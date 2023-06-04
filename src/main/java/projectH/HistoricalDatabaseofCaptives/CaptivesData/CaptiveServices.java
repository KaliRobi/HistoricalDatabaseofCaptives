package projectH.HistoricalDatabaseofCaptives.CaptivesData;

// The class must describe all the captive relating functionalities.
// 1. Database  crud operations
// 2. calculations and data preparations what later will eb pushed to the RestApi Controller class thus to the front end,
// the application will be server heavy meaning that each of the objects on the front end will be served with the appropriate data
// ReactJs will deal mostly with the data presentation


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CaptiveServices {

    private final CaptiveRecordRepository captiveRecordRepository;
    @Autowired
    public CaptiveServices(CaptiveRecordRepository captiveRecordRepository) {
        this.captiveRecordRepository = captiveRecordRepository;
    }

    // This will serve the record single page
    public Optional<Captive> GetCaptiveById(Long num){
     return captiveRecordRepository.findById(num);
    }


    public List<Captive> getAllTheCaptives(){
        // jparepo interface throws error
        List<Captive> captiveList = new ArrayList<>();
        captiveRecordRepository.findAll().forEach(captiveList::add);
        return captiveList;

    }

    // needs to be decided if I want to pull the


    public TreeSet<String> getCitiesOfResidence() {
        TreeSet<String> citiesOfResidence = new TreeSet<>();
        getAllTheCaptives().forEach(capt -> citiesOfResidence.add(capt.getPlace_of_residence()));
        return citiesOfResidence;
    }

    public Map<String, List<Long>> getSexDistribution(){
//      initiate a map with the nested map where the outer keys are the settlements in the database, the inner keys male / female
        Map<String, List<Long>> sexDistribution = new ConcurrentHashMap<>();
        Set<String> settlements = new TreeSet<>( getCitiesOfResidence() );
        List<Long> nestedList = new ArrayList<>();
        settlements.forEach(town -> sexDistribution.put(town, nestedList ));
        // count the numbers. Steps: 1 Get the all entries of the town,
//        2 separate the entries based on the  sex column and count them together
//        3 assign the values to the keys
// or a list of maps
//        sexDistribution.forEach( (town,v) -> v.put( "female", getAllTheCaptives().stream()
//                .filter(rec -> rec.getSex().equals("n") && rec.getPlace_of_residence().equals(town)).toList().size()   ));

        for( String town : settlements){

//            sexDistribution.get(town).add( getAllTheCaptives().stream()
//                            .filter(rec ->  rec.getPlace_of_residence().equals(town)).count());
            System.out.println(getAllTheCaptives().stream()
                    .filter(rec ->  rec.getPlace_of_residence().equals(town)).toList());
        }
//        rec.getSex().equals("n") &&




//        sexDistribution.forEach( (town,v) -> v.put("male", getAllTheCaptives().stream()
//                .filter(rec -> rec.getSex().equals("f") && rec.getPlace_of_residence().equals(town)).count()   ));
//        System.out.println(getAllTheCaptives().stream()
//                .filter(rec -> rec.getSex().equals("f") && rec.getPlace_of_residence().equals("Debrecen")).count());



//        System.out.println(
//        getAllTheCaptives().stream()
//                .filter(rec -> rec.getSex().equals("n") && rec.getPlace_of_residence().equals("Debrecen")).toList().size()  );

        ;
//        rec.getSex().equals("m") &&
//        getAllTheCaptives().stream().filter(rec -> rec.getSex().equals("m") && rec.getPlace_of_residence().equals("Debrecen"));

        // the nested map will contain mail:  and female: keys and this we also need to have from the db


    return sexDistribution;


    }
}
