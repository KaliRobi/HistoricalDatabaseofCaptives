package projectH.HistoricalDatabaseofCaptives.DataCleaner;

import org.springframework.stereotype.Component;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.Captive;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveServices;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class HeightVerifier {
    private final CaptiveServices captiveServices;
    private final FindOutliers findOutliers;
    private final CreateReviewableEntity createReviewableEntity;


    public HeightVerifier(CaptiveServices captiveServices, FindOutliers findOutliers, CreateReviewableEntity createReviewableEntity) {
        this.captiveServices = captiveServices;
        this.findOutliers = findOutliers;
        this.createReviewableEntity = createReviewableEntity;
    }

    public void reviewHeight() {

        // what I need here is a map where the height is the key and the ids are
        List<Captive> captiveList = captiveServices.getAllTheCaptives().stream().filter(c -> c.getHeight() != null).toList();

        Map<Long, Integer> femaleIdHeightMap = captiveList.stream().filter(e -> e.getSex().equals("n"))
                .collect(Collectors.toMap(Captive::getId, Captive::getHeight));

        Map<Long, Integer> maleIdHeightMap =  captiveList.stream().filter(e -> e.getSex().equals("f"))
                .collect(Collectors.toMap(Captive::getId, Captive::getHeight));

        ArrayList<Integer> heightListOfMale = captiveList.stream().filter(c -> c.getSex().equals("f"))
                .map(Captive::getHeight).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Integer> heightListOfFemale = captiveList.stream().filter(c -> c.getSex().equals("n"))
                .map(Captive::getHeight).collect(Collectors.toCollection(ArrayList::new));

        List<Integer> outliersFemale = findOutliers.findOuters(heightListOfFemale).stream().distinct().toList();
        List<Integer> outliersMale = findOutliers.findOuters(heightListOfMale).stream().distinct().toList();
        List<Map.Entry<Long, Integer>> mappedFemaleOutliers = mapOutliersToCaptiveIds(outliersFemale, femaleIdHeightMap);
        List<Map.Entry<Long, Integer>> mappedMaleOutliers  =  mapOutliersToCaptiveIds(outliersMale, maleIdHeightMap);



        // add an enum here as well;
        for(Map.Entry<Long, Integer> candidate : mappedFemaleOutliers){
                           createReviewableEntity.registerReviewableEntity(candidate.getKey(),
                        "Captive",
                        "Female, height value(" + candidate.getValue() + ") was out of range" );


        }

        for(Map.Entry<Long, Integer> candidate : mappedMaleOutliers){
              createReviewableEntity.registerReviewableEntity(candidate.getKey(),
                        "Captive",
                        "Male, height value(" + candidate.getValue() + ") was out of range" );
         }
// these collections can be quite large better to destroy them right after usage.
        femaleIdHeightMap.clear();
        maleIdHeightMap.clear();
        heightListOfMale.clear();
        heightListOfFemale.clear();
    }

    private List<Map.Entry<Long, Integer>> mapOutliersToCaptiveIds (List<Integer> outliers, Map<Long, Integer> heightMap)  {

    return         outliers.stream()
                .map(e -> heightMap.entrySet()
                        .stream().filter(t -> e.equals(t.getValue()))
                        .toList()).toList().stream()
                .flatMap(Collection::stream).toList();
    }




}