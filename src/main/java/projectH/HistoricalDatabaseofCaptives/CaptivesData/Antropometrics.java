package projectH.HistoricalDatabaseofCaptives.CaptivesData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class Antropometrics {

    public final CaptiveRecordRepository captiveRecordRepository;

    public final CaptiveServices captiveServices;

    @Autowired
    public Antropometrics(CaptiveRecordRepository captiveRecordRepository, CaptiveServices captiveServices) {
        this.captiveRecordRepository = captiveRecordRepository;
        this.captiveServices = captiveServices;
    }

    /* one of the main thing would be to get the birth cohorts of the captives per town per sex
    * This could be a base for a lot of other function
    * the function needs to return the list of ids
    * {town : [[female], [male]]


    *  get a method what creates the list of cohorts
    * adding a range in year, two variant getCohortList /  getCohortListBySex



    *   at this moment a List<Map<Integer, List<Integer>>  model looks like the good way to present this data
    *    The outer List is the collection. The Nested Map represents the cohorts where the key is the first year of the period.
    *   The nested list will contain all the heights mapped on femail/male keys and the actual heights
*/
    public void getCohortList(){
        List<Captive> captiveList = captiveServices.getAllTheCaptives();

         List<Map<Integer, Map<String, Integer>>>  peopleList  = new ArrayList<>();
        captiveList.stream()
                .filter( a->  null != a.getHeight() && null != a.getDate_of_birth() )
                .forEach(e->
                        {
                    Map<Integer, Map<String, Integer>> personWithYear = new HashMap<>();
                    Map<String, Integer> personSexAndHeight = new HashMap<>();
                    personSexAndHeight.put(e.getSex(), e.getHeight());
//                    Ideal would be e.getDate_of_birth().get(Chronfield.YEAR)  but no matter what format I did isSupported(Chronfield.YEAR) is always false
//                     As it is not supported per documentation. Should go with NANO_OF_SECOND / MICRO_OF_SECON / MILLI_OF_SECOND
                    personWithYear.put( Integer.valueOf(e.getDate_of_birth().toString().substring(0,4 )), personSexAndHeight);
                    peopleList.add(personWithYear);
                        });
        List<Integer> cohortBase = getCohortsByFirstYear(10);
// ideal would be to have a map<String, List<Integer>> here
        Map<Integer, List<List<Integer>>> mapToCohortStartYears = new HashMap<>();
        System.out.println(cohortBase);
        List<List<Integer>> listTpAdd = new ArrayList<>();
        for( int cohortStart : cohortBase){
            Object List;
            mapToCohortStartYears.put(cohortStart, listTpAdd);
        }
        System.out.println(mapToCohortStartYears);
        for(Map<Integer, Map<String, Integer>> person : peopleList ) {
                for(Integer key : mapToCohortStartYears.keySet())   {
//                    [1921, 1891, 1861, 1911, 1800, 1881, 1901, 1871]
                    int currentBrithYear = person.keySet().stream().toList().get(0);
                    if(currentBrithYear > key)

//                    System.out.println(person.get(person.keySet().stream().toList().get(0)).get("n"));

//                    System.out.println(person.keySet());
//                    System.out.println(key)
                    ;
                }
        }



        System.out.println(cohortBase);


    }

    public List<List<Integer>> getCohorts(Integer cohortSize){

       List<Integer>  uniqueYearList = captiveServices.getAllTheCaptives().stream().filter(a -> null != a.getDate_of_birth())
               .map(e -> Integer.valueOf(e.getDate_of_birth().toString().substring(0, 4)))
               .collect(Collectors.toSet()).stream().sorted().toList();

        List<List<Integer>> cohortList =  new ArrayList<>();
       List<Integer> interatorList = IntStream.range(1 , uniqueYearList.size() / cohortSize + 1).map(e -> e * cohortSize ).boxed().toList();
        interatorList.forEach( e -> cohortList.add(uniqueYearList.subList( e - cohortSize , e      )));


        return cohortList;
    }
    public List<Integer> getCohortsByFirstYear(Integer cohortSize){

        List<Integer>  uniqueYearList = captiveServices.getAllTheCaptives().stream().filter(a -> null != a.getDate_of_birth())
                .map(e -> Integer.valueOf(e.getDate_of_birth().toString().substring(0, 4)))
                .collect(Collectors.toSet()).stream().sorted().toList();

        List<Integer> cohortList =  new ArrayList<>();
        List<Integer> interatorList = IntStream.range(1 , uniqueYearList.size() / cohortSize + 1).map(e -> e * cohortSize ).boxed().toList();
        interatorList.forEach( e -> cohortList.add(uniqueYearList.subList( e - cohortSize , e      ).stream().limit(1).toList().get(0)));


        return cohortList;
    }










}
