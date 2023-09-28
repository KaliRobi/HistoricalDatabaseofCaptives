package projectH.HistoricalDatabaseofCaptives.DataCleaner;


import org.springframework.stereotype.Component;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.Captive;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveRecordRepository;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveServices;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
Since the initial database was nothing else but merged Excel sheet on a couple of local machines written by 2-5 students the data is not really clean
This package will aim to create a facility what will run from time to time and review the entered data.


 self-defined values, nulls, occupations,locations, crimes, locations, military service
*/
@Component
public class DataCleanerService {

           private final CaptiveRecordRepository captiveRecordRepository;
            private  final CaptiveServices captiveServices;


    public DataCleanerService(CaptiveRecordRepository captiveRecordRepository, CaptiveServices captiveServices) {
        this.captiveRecordRepository = captiveRecordRepository;
        this.captiveServices = captiveServices;
    }


    public void reviewHeight(){
// change this based on
        List<Captive> heightList = captiveServices.getAllTheCaptives();

        List<Integer> heightListOfMale = heightList.stream().filter(c -> c.getSex().equals("f"))
                .map(Captive::getHeight).filter(Objects::nonNull).toList();
        List<Integer> heightListOfFemale = heightList.stream().filter(c -> c.getSex().equals("n"))
                .map(Captive::getHeight).filter(Objects::nonNull).toList();
        // check if everything is number is between 100 and 200.
        List<Integer> NonInRageHeightsMale = heightList.stream()
                .filter(h -> h < 99 || h > 200).toList();

        List<Integer> NonInRageHeightsMale = heightList.stream()
                .filter(h -> h < 99 || h > 200).toList();

        // Find  interquartile range

        List<Integer> uniqueHeightValues = new HashSet<>(heightList).stream().toList();






    }


}
