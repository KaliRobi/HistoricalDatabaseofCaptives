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
    public TreeSet<String> getCitiesOfResidence() {
        TreeSet<String> citiesOfResidence = new TreeSet<>();
        getAllTheCaptives().forEach(capt -> citiesOfResidence.add(capt.getPlace_of_residence()));
        return citiesOfResidence;
    }

    public Map<String, HashMap<String, Long>> getSexDistribution(){
//      initiate a map with the nested map where the outer keys are the settlements in the database, the inner keys male / female
        Map<String, HashMap<String, Long>> sexDistribution = new HashMap<>();
        Set<String> settlements = new TreeSet<>( getCitiesOfResidence() );
        List<Captive> captiveList =   getAllTheCaptives();
        settlements.forEach(
                town -> {
                    HashMap<String, Long> nestedList = new HashMap<>();
                    nestedList.put("female", captiveList.stream()
                            .filter(rec -> rec.getSex().equals("n") && rec.getPlace_of_residence().equals(town))
                            .count());
                    nestedList.put("male", captiveList.stream()
                            .filter(rec -> rec.getSex().equals("f") && rec.getPlace_of_residence().equals(town))
                            .count());
                    System.out.println(town + ": " + nestedList);
                    sexDistribution.put(town, nestedList);
                }
        );
            return sexDistribution;
    }
    /// one of the main thing would be to get the birth cohorts of the captives per town per sex
    // This could be a base for a lot of other function
    // the function needs to return the list of ids
    // {town : [[femaile], [male]]

    public Map<String, ArrayList<ArrayList<String>>> getBirthCohorts(){

        // get towns

        // get records by town

        // get the cohorts using the birth year

//         define cohorts getting the fisr or the first two digits of the difference between the youngest and oldest
        // check if any of that is extreme and if yes find the correct base numbers by checking the normal distribution
        return null;
    }

    public ArrayList<ArrayList<String>> getTheRelocated(){

        /// base for checking the relocation directions.
        //grouped/aggregated value at the 3 index will indicate the weight [birthplace, residence, how many of them moved ]


        return null;
    }




}
