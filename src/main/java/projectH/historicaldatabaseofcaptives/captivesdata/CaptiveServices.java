package projectH.historicaldatabaseofcaptives.captivesdata;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import projectH.historicaldatabaseofcaptives.applicationexceptions.NoSuchCaptiveIdFound;
import projectH.historicaldatabaseofcaptives.gisdata.GeoServices;
import projectH.historicaldatabaseofcaptives.users.Visitor;
import java.util.*;


@Component
public class CaptiveServices {

    private final CaptiveRecordRepository captiveRecordRepository;

    private final GeoServices geoServices;

    @Autowired
    public CaptiveServices(CaptiveRecordRepository captiveRecordRepository, GeoServices geoServices) {
        this.captiveRecordRepository = captiveRecordRepository;
        this.geoServices = geoServices;
    }

    public Optional<Captive> getCaptiveById(Long num) {
        return captiveRecordRepository.findById(num);
    }

    public void addCaptive(Captive captive) {
        //Temporary solution, this will get its own method
        geoServices.findLocationOrFetchIt(captive.getPlace_of_residence());
        geoServices.findLocationOrFetchIt(captive.getPlace_of_birth());
        geoServices.findLocationOrFetchIt(captive.getArrest_site());
        captiveRecordRepository.save(captive);

    }

    public void updateCaptiveV2(long captiveId, Map<String, Object> inboundMap){
        ModelMapper modelMapper = new ModelMapper();
        updateGeolocationsIfNeeded(inboundMap);
        Captive captiveToUpdate = captiveRecordRepository.findById( captiveId).orElseThrow( () -> new NoSuchCaptiveIdFound(captiveId));
        modelMapper.map(inboundMap, captiveToUpdate);
        captiveRecordRepository.save(captiveToUpdate);

    }
    // return which one is present
    private List<String> listPresentGeoRelatedAttributesToUpdate(Map<String, Object> inboundMap) {
        List<String> geoRelatedColumns = Arrays.asList("place_of_birth", "place_of_residence", "arrest_site");

       return inboundMap.keySet().stream().filter(geoRelatedColumns::contains).toList();
    }

    private void updateGeolocationsIfNeeded(Map<String, Object> inboundMap){
        listPresentGeoRelatedAttributesToUpdate(inboundMap).forEach(e -> geoServices.findLocationOrFetchIt(String.valueOf(inboundMap.get(e))));
    }

    public List<Captive> getAllTheCaptives(){
        return captiveRecordRepository.findAll();

    }
    public TreeSet<String> getLocationsOfResidence() {
        TreeSet<String> citiesOfResidence = new TreeSet<>();
        getAllTheCaptives().forEach(capt -> citiesOfResidence.add(capt.getPlace_of_residence()));
        return citiesOfResidence;
    }

    public TreeSet<String> getLocationsOfBirth() {
        TreeSet<String> citiesOfBirth = new TreeSet<>();
        getAllTheCaptives().forEach(capt -> citiesOfBirth.add(capt.getPlace_of_birth()));

        return citiesOfBirth;
    }

    public Map<String, HashMap<String, Long>> getSexDistribution(){
//      initiate a map with the nested map where the outer keys are the settlements in the database, the inner keys male / female
        Map<String, HashMap<String, Long>> sexDistribution = new HashMap<>();
        Set<String> settlements = new TreeSet<>( getLocationsOfResidence() );
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
                    sexDistribution.put(town, nestedList);
                }
        );
            return sexDistribution;
    }


    public List<List<String>> getTheRelocated(){
        // get records where the location of birth and residence is not the same
        /// base for checking the relocation directions. This might be better to be a map? Org, dest, weight?
        List<List<String>> relocatedCandidateList  = getAllTheCaptives().stream().filter(captive -> !captive.getPlace_of_residence()
                        .equals(captive.getPlace_of_birth())).toList()
                        .stream().map(captive ->  Arrays.asList(captive.getPlace_of_birth(), captive.getPlace_of_residence()))
                        .toList();

        HashSet<List<String>> uniqueRelocations  = new HashSet<>(relocatedCandidateList);
        List<List<String>> relocationsWithWeight = new ArrayList<>();
        for (List<String> uniqueRelocation : uniqueRelocations) {
            long groupedValue = relocatedCandidateList.stream().filter(element -> element.equals(uniqueRelocation)).count();
            if (groupedValue > 10L) {
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
         if(!candidateToPresentList.isEmpty() ){
             return candidateToPresentList.get(0);
         }
        else{
             return new Captive();
         }
    }
}
