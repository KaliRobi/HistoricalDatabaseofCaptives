package projectH.HistoricalDatabaseofCaptives.CaptivesData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


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




    public void getCohortList(int subRangeSize){

        // height , birth year
        List<List<Integer>> heightBirthdayList = captiveServices.getAllTheCaptives().stream()
                        .filter(a -> a.getDate_of_birth() != null  &&  a.getHeight() != null)
                        .map(e -> List.of (Integer.valueOf(String.valueOf(e.getDate_of_birth()).substring(0,4)), e.getHeight())  )
                        .toList();

        List<Integer> yearRange = heightBirthdayList.stream().map(e -> e.get(0)).sorted().toList();
        // this is where the standard distribution will be needed

        List<Integer> iteratorList = IntStream.range(1, yearRange.size() / subRangeSize + 1).map(e -> e * subRangeSize)
                .boxed().toList();


        List<List<Integer>>    tempList = iteratorList.stream().map(e -> yearRange.subList(e - subRangeSize, e)).toList();


        System.out.println(tempList );

    }










}
