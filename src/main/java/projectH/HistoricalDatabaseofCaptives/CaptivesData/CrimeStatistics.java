package projectH.HistoricalDatabaseofCaptives.CaptivesData;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * one more data to the location: total population in 1930 based on the cenzus
 * Probably based on some pdf reader service
 *  crime categories, average punishment, ransom
 * crime type per location
 *
 *
 *
 *crime pyramid, from most popular to the rarest
 *crime pyramid the ransom and the sentence period
 */
@Component
public class CrimeStatistics {

    private final CaptiveRecordRepository captiveRecordRepository;

    public CrimeStatistics(CaptiveRecordRepository captiveRecordRepository) {
        this.captiveRecordRepository = captiveRecordRepository;

    }

    // first very basic location / gender / crime
    public Map<String, Long> frequencyOfCrimePerGender(){
        List<String> captiveList = captiveRecordRepository.getTargetGroupByLocationAndSex("Debrecen", "n")
                         .stream().map(e -> e.getCrime()).toList();
        System.out.println(captiveList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting() )));
        return captiveList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting() ));
    }





}
