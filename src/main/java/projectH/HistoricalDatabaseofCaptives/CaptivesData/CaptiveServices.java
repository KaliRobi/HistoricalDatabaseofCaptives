package projectH.HistoricalDatabaseofCaptives.CaptivesData;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import projectH.HistoricalDatabaseofCaptives.ApplicationExceptions.NoSuchCaptiveIdFound;
import projectH.HistoricalDatabaseofCaptives.GISData.GeoServices;
import projectH.HistoricalDatabaseofCaptives.Users.Visitor;
import java.util.*;

//TODO
//Recosider the purpose of the class
// should be facing out , instead provides different things to classes in and out side of the the package

@Component
public class CaptiveServices {

    private final CaptiveRecordRepository captiveRecordRepository;

    private final GeoServices geoServices;
    @Autowired
    public CaptiveServices(CaptiveRecordRepository captiveRecordRepository, GeoServices geoServices) {
        this.captiveRecordRepository = captiveRecordRepository;
        this.geoServices = geoServices;
    }



    public Optional<Captive> GetCaptiveById(Long num){
     return captiveRecordRepository.findById(num);
    }

    public void addCaptive(Captive captive){
        geoServices.findLocationOrFetchIt(captive);
        captiveRecordRepository.save(captive);

    }
    public void updateCaptive(long captiveId, Captive captiveNewData){
        ModelMapper modelMapper = new ModelMapper();
        geoServices.findLocationOrFetchIt(captiveNewData);
        try {
            Captive captiveToUpdate = captiveRecordRepository.findById( captiveId).get();
            modelMapper.map(captiveNewData, captiveToUpdate);
            captiveRecordRepository.save(captiveToUpdate);
        } catch (NoSuchCaptiveIdFound e){
            throw new NoSuchCaptiveIdFound("this id is not in the db" + captiveId, e);
        }


    }
    public List<Captive> getAllTheCaptives(){
        return captiveRecordRepository.findAll();

    }
    public TreeSet<String> getCitiesOfResidence() {
        TreeSet<String> citiesOfResidence = new TreeSet<>();
        getAllTheCaptives().forEach(capt -> citiesOfResidence.add(capt.getPlace_of_residence()));
        return citiesOfResidence;
    }

    public TreeSet<String> getCitiesOfBirth() {
        TreeSet<String> citiesOfBirth = new TreeSet<>();
        getAllTheCaptives().forEach(capt -> citiesOfBirth.add(capt.getPlace_of_birth()));
        return citiesOfBirth;
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


    public List<List<String>> getTheRelocated(){

        // get records where the location of birth and residence is not the same
        /// base for checking the relocation directions. This might be better as map? Org, dest, weight?
        List<List<String>> relocatedCandidateList  = getAllTheCaptives().stream().filter(captive -> !captive.getPlace_of_residence()
                        .equals(captive.getPlace_of_birth())).toList()
                        .stream().map(captive ->  Arrays.asList(captive.getPlace_of_birth(), captive.getPlace_of_residence()))
                        .toList();


        HashSet<List<String>> uniqueRelocations  = new HashSet<>(relocatedCandidateList);
        List<List<String>> relocationsWithWeight = new ArrayList<>();
        for (List<String> uniqueRelocation : uniqueRelocations) {
            long groupedValue = relocatedCandidateList.stream().filter(element -> element.equals(uniqueRelocation)).count();

            if (groupedValue > 10L) {
                System.out.println(uniqueRelocation + " : " + relocatedCandidateList.stream().filter(element -> element.equals(uniqueRelocation)).count());
                relocationsWithWeight.add(Arrays.asList(uniqueRelocation
                        .get(0), uniqueRelocation.get(1), String.valueOf(relocatedCandidateList.stream()
                        .filter(element -> element.equals(uniqueRelocation))
                        .count())));
            }
        }
        return relocationsWithWeight;
    }


    // returns a captive by its attributes
    public Captive findACaptive(Visitor visitor){

         List<Captive> captivesList =  captiveRecordRepository.getTargetGroupByLocationAndSex(visitor.getLocation().getSource_name(), visitor.getSex());

         List<Captive> candidateToPresentList = captivesList.stream().filter(e -> e.getAge() == visitor.getAge()).limit(1).toList();
         if(candidateToPresentList.size() > 0 ){
             Captive candidateToPresent = candidateToPresentList.get(0);
             return candidateToPresent;
         }
        else{
             return new Captive();
         }

    }

}
