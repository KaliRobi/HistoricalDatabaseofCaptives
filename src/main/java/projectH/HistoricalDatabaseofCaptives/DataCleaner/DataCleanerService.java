package projectH.HistoricalDatabaseofCaptives.DataCleaner;


import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveRecordRepository;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveServices;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
Since the initial database was nothing else but merged Excel sheet on a couple of local machines written by 2-5 students the data is not really clean
This package will aim to create a facility what will run from time to time and review the entered data.


 self-defined values, nulls, occupations,locations, crimes, locations, military service
*/

public class DataCleanerService {

           private final CaptiveRecordRepository captiveRecordRepository;
            private  final CaptiveServices captiveServices;


    public DataCleanerService(CaptiveRecordRepository captiveRecordRepository, CaptiveServices captiveServices) {
        this.captiveRecordRepository = captiveRecordRepository;
        this.captiveServices = captiveServices;
    }


    public void reviewHeight(){
        // check if everything is number length max 3 digits no less than 2
        captiveServices.getAllTheCaptives().stream().map(e -> e.getHeight()).filter(h -> )

        // check for outstandings






    }


}
