package projectH.HistoricalDatabaseofCaptives.DataCleaner;


/*
instead of column specific reviews instance specific ones are mor effective.

 */

import org.springframework.stereotype.Component;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.Captive;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveServices;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class ReviewStringColumnsCaptiveData {


    private final LocalAbbreviatedEntityRepository localAbbreviatedEntityRepository;
    private final CaptiveServices captiveServices;
    private final CreateReviewableEntity createReviewableEntity;

    public ReviewStringColumnsCaptiveData(LocalAbbreviatedEntityRepository localAbbreviatedEntityRepository, CaptiveServices captiveServices, CreateReviewableEntity createReviewableEntity) {
        this.localAbbreviatedEntityRepository = localAbbreviatedEntityRepository;
        this.captiveServices = captiveServices;
        this.createReviewableEntity = createReviewableEntity;
    }

     // need  a switch which loops though the keys (tables) and each table will loops though the columns

    public void goForIt(){
        Map<String, List<String>> ValidatingString  =  getListOfTablesUsingAbbr();
        reviewColumns();

    }
    //TODO
    // The thing is that there are like 20 similar cases when I do captiveServices.getAllTheCaptives().stream()
    // This should be just stored in a cache slot
    // So next project is to get back the cache part and implement it. Also, will check for further similar things.
 public void reviewColumns(){
     List<Captive> captiveList = captiveServices.getAllTheCaptives();


     Map<Long, Integer> femaleIdHeightMap = captiveList.stream().filter(e -> e.getSex().equals("n"))
             .collect(Collectors.toMap(Captive::getId, Captive::getHeight));
 }



    // get the concept of id:entity fom the height


//     compare the db to the lists of ValidatingString

    // where something is wrong it goes to the reviewable table]
    // would be nice if all the check would finnish before it goes there
    // this needs a string what is always appending ReasonWriter string with static variable. it is used only once at the given time.
    //and after all the check this is added to this.reviewableEntity.reason



    private Map<String, List<String>> getListOfTablesUsingAbbr(){

        List<List<String>> listOfLists = localAbbreviatedEntityRepository.findAll()
                .stream().filter(e -> e.getRelated_table() != null)
                .map(a -> List.of(a.getTable_column(), a.getAbbr()))
                .toList();
        List<String> columnNames = listOfLists.stream().map(e -> e.get(0)).distinct().toList();
        Map<String, List<String>> mapForAbbrevsPerColumn = new HashMap<>();
        for (String name: columnNames) {
            mapForAbbrevsPerColumn.put(name, listOfLists.stream().filter(e -> e.get(0).equals(name)).map(t -> t.get(1)).toList());
        }

        return mapForAbbrevsPerColumn;


    }



}
