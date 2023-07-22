package projectH.HistoricalDatabaseofCaptives.CaptivesData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.time.Year;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.util.*;

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

    /// one of the main thing would be to get the birth cohorts of the captives per town per sex
    // This could be a base for a lot of other function
    // the function needs to return the list of ids
    // {town : [[female], [male]]


    //  get a method what creates the list of cohorts
    // adding a range in year, two variant getCohortList /  getCohortListBySex



//   at this moment a List<Map<Integer, List<Integer>>  model looks like the good way to present this data
//    The outer List is the collection. The Nested Map represents the cohorts where the key is the first year of the period.
//    The nested list will contain all the heights mapped on femail/male keys and the actual heights
    public void getCohortList(int subRangeSize){

        // height , birth year
//        List<List<Integer>> heightBirthdayList = captiveServices.getAllTheCaptives().stream()
//                        .filter(a -> a.getDate_of_birth() != null  &&  a.getHeight() != 0)
//                        .map(e ->
//                            List.of (Integer.valueOf(String.valueOf(e.getDate_of_birth()).substring(0,4)),   e.getHeight())
//                                  )
//                        .toList();
        // creating Map<yearofbirt, Map<sex, height>>  this is the individual level

         List<Map<Integer, Map<String, Integer>>>  individualLink  = new ArrayList<>();
         captiveServices.getAllTheCaptives().stream()
                .filter(a -> a.getDate_of_birth() != null  &&  a.getHeight() != 0 )
                .forEach(e->
//                        {
//                    Map<Integer, Map<String, Integer>> personWithYear = new HashMap<>();
//                    Map<String, Integer> personSexAndHeight = new HashMap<>();
//                    personSexAndHeight.put(e.getSex()  , e.getHeight());
//                    personWithYear.put(e.getDate_of_birth().get(ChronoField.DAY_OF_WEEK), personSexAndHeight);
//                    individualLink.add(personWithYear);
//                        }
                                System.out.println(e.getDate_of_birth().get(ChronoField.YEAR) + " " + e.getDate_of_birth()));




//        List<Integer> yearRange = heightBirthdayList.stream().map(e -> e.get(0)).sorted().toList();
        // this is where the standard distribution will be needed

//        List<Integer> iteratorList = IntStream.range(1, yearRange.size() / subRangeSize + 1).map(e -> e * subRangeSize)
//                .boxed().toList();
//
//
//        List<List<Integer>>    cohortsList = iteratorList.stream().map(e -> yearRange.subList(e - subRangeSize, e)).toList();


        System.out.println(individualLink );

    }










}
