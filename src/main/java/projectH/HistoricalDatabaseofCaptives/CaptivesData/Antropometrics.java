package projectH.HistoricalDatabaseofCaptives.CaptivesData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.temporal.ChronoField;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class Antropometrics {
    public final CaptiveServices captiveServices;

    @Autowired
    public Antropometrics(CaptiveRecordRepository captiveRecordRepository, CaptiveServices captiveServices) {
        this.captiveServices = captiveServices;
    }

    /** one of the main thing would be to get the birth cohorts of the captives per town per sex
    * This could be a base for a lot of other function
    * the function needs to return the list of ids
    * {town : [[female], [male]]


    *  get a method what creates the list of cohorts
    * adding a range in year, two variant getCohortList /  getCohortListBySex



    *   at this moment a List<Map<Integer, List<Integer>>  model looks like the good way to present this data
    *    The outer List is the collection. The Nested Map represents the cohorts where the key is the first year of the period.
    *   The nested list will contain all the heights mapped on female/male keys and the actual heights
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

                    personWithYear.put( e.getDate_of_birth().get(ChronoField.YEAR), personSexAndHeight);
                    peopleList.add(personWithYear);
                        });
        List<Integer> cohortBase = getCohortsByFirstYear(5);
        Map<Integer, List<List<Integer>>> mapToCohortStartYears = new HashMap<>();

//      This loop makes sure each key has its own list.(no confusion with object references like in the prev version)
        for( int cohortStart : cohortBase){
            List<List<Integer>> listToAdd = new ArrayList<>();
            List<Integer> listA = new ArrayList<>();
            List<Integer> listB = new ArrayList<>();
            listToAdd.add(listA); listToAdd.add(listB);
            mapToCohortStartYears.put(cohortStart, listToAdd);
        }


        for(Map<Integer, Map<String, Integer>> person : peopleList ) {
                for(int i = cohortBase.size() -1; i >= 0; i--  )   {
                    int currentBrithYear = person.keySet().stream().toList().get(0);
                    Map<String, Integer>  currentPerson = person.get(person.keySet().stream().toList().get(0));
                    int targetBase = cohortBase.get(i);
//                    if the birth year larger than the current element of the cohortBase then we are good
                    if(currentBrithYear > targetBase){

                        if( null != currentPerson.get("n")) {
                            mapToCohortStartYears.get(targetBase).get(0).add(currentPerson.get("n"));
                            break;
                        } else if ( null != currentPerson.get("f")) {
                            mapToCohortStartYears.get(targetBase).get(1).add(currentPerson.get("f"));
                            break;
                        }
                    break;
                    }

                }
        }
// Just for fun section
//[1800, 1861, 1871, 1881,  1891,  1901, 1911,  1921]
//        System.out.println("Cohorts (with standard size of 5 years) :");
//        System.out.println(cohortBase);
//        for (int year : cohortBase){
//            System.out.println("average male height  : " +  mapToCohortStartYears.get(year).get(1).stream().reduce(0, Integer::sum) / mapToCohortStartYears.get(year).get(1).size() + "cm by birth year in the cohort of " + year) ;
//            System.out.println("average female height: " +  mapToCohortStartYears.get(year).get(0).stream().reduce(0, Integer::sum) / mapToCohortStartYears.get(year).get(0).size() + "cm by birth year in the cohort of " + year);
//        }
//
//
//        System.out.println("születési korcsoportok (with standard size of 5 years) :");
//        System.out.println(cohortBase);
//        for (int year : cohortBase){
//            int endYear = year + 5;
//            int maleAvS = mapToCohortStartYears.get(year).get(1).stream().reduce(0, Integer::sum) / mapToCohortStartYears.get(year).get(1).size();
//            int femaleAvS = mapToCohortStartYears.get(year).get(0).stream().reduce(0, Integer::sum) / mapToCohortStartYears.get(year).get(0).size();
//            int difInCm = Math.abs(maleAvS - femaleAvS);
//            double difInPErc = difInCm / (maleAvS / 100.00);
//            System.out.println("átlag testmagasság féfiaknál  :" +  maleAvS + "cm, a " + year + "-" + endYear + " között születettek csoportjában"  ) ;
//            System.out.println("átlag testmagasság nőknél     :" +  femaleAvS + "cm, a " + year + "-" + endYear + " között születettek csoportjában"  );
//            System.out.println("különbség cm-ben: " + Math.abs(maleAvS - femaleAvS) +    "cm, különség % ban: " + difInCm + "%"   );
//        }
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
