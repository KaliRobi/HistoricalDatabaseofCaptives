package projectH.historicaldatabaseofcaptives.applicationservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import projectH.historicaldatabaseofcaptives.datacleaner.DataCleanerService;



@Component
public class Scheduler {
    @Autowired
    private DataCleanerService dataCleanerService;
    @Scheduled(cron = "0 5 * * 0")
    public void cleanData(){
        dataCleanerService.startCleaning();
    }


}
