package projectH.historicaldatabaseofcaptives.datacleaner;


import org.springframework.stereotype.Component;

/**
Since the initial database was nothing else but merged Excel sheet on a couple of local machines written by 2-5 students the data is not really clean
This package will aim to create a utility what will run from time to time and review the entered data.

 first it will mostly detect and the information needs to be reviewed manually

 self correction will be done on cities and occupations;

 "self-defined" values, nulls, occupations,locations, crimes, locations, military service
*/
@Component
public class DataCleanerService {
    private final ReviewHeight reviewHeight;
    private final ReviewStringColumnsCaptiveData reviewStringColumnsCaptiveData;
    public DataCleanerService(ReviewHeight reviewHeight, ReviewStringColumnsCaptiveData reviewStringColumnsCaptiveData) {
        this.reviewHeight = reviewHeight;
        this.reviewStringColumnsCaptiveData = reviewStringColumnsCaptiveData;
    }
    public void startCleaning()  {
        reviewStringColumnsCaptiveData.reviewColumns();
    }

}
