package projectH.HistoricalDatabaseofCaptives.CaptivesData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
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

    //  get a method what creates the list of cohorts
    // adding a range in year, two variant getCohorList /  getCohorListBySex




    public void getCohorList(int subRangeSize){

        // height , birth year
        List<List<Integer>> heightBirthdayList = captiveServices.getAllTheCaptives().stream()
                        .filter(a -> a.getDate_of_birth() != null  &&  a.getHeight() != null)
                        .map(e -> List.of (Integer.valueOf(String.valueOf(e.getDate_of_birth()).substring(0,4)), e.getHeight())  )
                        .toList();

        List<Integer> yearRange = heightBirthdayList.stream().map(e -> e.get(0)).sorted().toList();
        // this is where the standard distribution will be needed

        List<Integer> iteratorList = IntStream.range(1, yearRange.size() / subRangeSize + 1).map(e -> e * subRangeSize)
                .boxed().toList();


        List<List<Integer>>    zsamo = iteratorList.stream().map(e -> yearRange.subList(e - subRangeSize, e)).toList();


        System.out.println(zsamo);

    }










}
