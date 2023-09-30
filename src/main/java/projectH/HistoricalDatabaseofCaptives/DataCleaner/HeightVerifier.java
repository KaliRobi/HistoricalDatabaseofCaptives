package projectH.HistoricalDatabaseofCaptives.DataCleaner;

import org.springframework.stereotype.Component;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.Captive;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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


    // the method works but it is not efficient enough. I take the heights without the id and when I find what I want I get those ids again from
    // the db just to save them to the db again. Rework is needed, but ok first version.

    public void reviewHeight(){

        List<Captive> heightList = captiveServices.getAllTheCaptives().stream().distinct().toList();

        ArrayList<Integer> heightListOfMale = heightList.stream().filter(c -> c.getSex().equals("f"))
                .map(Captive::getHeight).filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Integer> heightListOfFemale = heightList.stream().filter(c -> c.getSex().equals("n"))
                .map(Captive::getHeight).filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));


        List<Integer> outliersFemale = findOutliers.findOuters(heightListOfFemale).stream().distinct().toList();
        List<Integer> outliersMale = findOutliers.findOuters(heightListOfMale).stream().distinct().toList();

//        outliersFemale.forEach (e -> captiveServices.getCaptiveIdFromProperty(e, PropertyType.HEIGHTF));

        for(int height : outliersFemale){
            captiveServices.getCaptiveIdFromProperty(height, PropertyType.HEIGHTF)
                    .forEach(ca -> createReviewableEntity.registerReviewableEntity(ca, "Captive", "Height value(" + height + ") was out of range" ) );

//
        }
//
        for(int height : outliersMale){
            captiveServices.getCaptiveIdFromProperty(height, PropertyType.HEIGHTM)
                    .forEach(e-> createReviewableEntity.registerReviewableEntity(e, "Captive", "Height value(" + height + ") was out of range" ) );
        }



    }
}