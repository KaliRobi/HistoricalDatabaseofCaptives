package projectH.HistoricalDatabaseofCaptives.DataCleaner;

import org.springframework.stereotype.Component;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.Captive;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveServices;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class HeightVerifier {
    private final CaptiveServices captiveServices;
    private final FindOutliers findOutliers;

    public HeightVerifier(CaptiveServices captiveServices, FindOutliers findOutliers) {
        this.captiveServices = captiveServices;
        this.findOutliers = findOutliers;
    }

    public void reviewHeight(){
// change this based on

        List<Captive> heightList = captiveServices.getAllTheCaptives().stream().distinct().toList();

        ArrayList<Integer> heightListOfMale = heightList.stream().filter(c -> c.getSex().equals("f"))
                .map(Captive::getHeight).filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Integer> heightListOfFemale = heightList.stream().filter(c -> c.getSex().equals("n"))
                .map(Captive::getHeight).filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
        // check if everything is number is between 100 and 200.
        List<Integer> NonInRageHeightsFemale = heightListOfFemale.stream()
                .filter(h -> h < 99 || h > 200).toList();

        List<Integer> NonInRageHeightsMale = heightListOfMale.stream()
                .filter(h -> h < 99 || h > 200).toList();

        // this group can be listed
        heightListOfFemale.removeAll(NonInRageHeightsMale);
        heightListOfFemale.removeAll(NonInRageHeightsFemale);

        // Find  interquartile range
        System.out.println( NonInRageHeightsMale );
        System.out.println( NonInRageHeightsFemale );
//        ArrayList<Integer> testList = new ArrayList<>(Arrays.asList(-2, -1,0,1,2,3,4,5,7,8,9,10,11,12,13, 35, 44, -4, -3));
//        findOutliers.findOuters(heightListOfFemale);
        System.out.println("***********************************");
        System.out.println(findOutliers.findOuters(heightListOfFemale));


    }
}