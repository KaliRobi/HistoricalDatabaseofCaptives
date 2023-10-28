package projectH.historicaldatabaseofcaptives.datacleaner;


/*
instead of column specific reviews instance specific ones are mor effective.

 */

import org.springframework.stereotype.Component;
import projectH.historicaldatabaseofcaptives.captivesdata.Captive;
import projectH.historicaldatabaseofcaptives.captivesdata.CaptiveServices;
import org.springframework.jdbc.core.JdbcTemplate;

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

    public void reviewColumns(){
     List<String> columnsWithAbbrebs = getMapsForColumnsAndAbbreviations().keySet().stream().toList();
    for(String column : columnsWithAbbrebs){

        Map<Long, String>  localRelevantCaptiveData = returnMapToReview(column) ;
        for(Map.Entry<Long, String> usedAbbreviationEntity : localRelevantCaptiveData.entrySet() ){
            List<String>  listOfAbbreviations = getMapsForColumnsAndAbbreviations().get(column);
            if(!listOfAbbreviations.contains(localRelevantCaptiveData.get(usedAbbreviationEntity.getKey()))){
                createReviewableEntity.registerReviewableEntity(usedAbbreviationEntity.getKey(),
                        "Captive",
                        localRelevantCaptiveData.get(usedAbbreviationEntity.getKey()) + " is not in " +  listOfAbbreviations + "," + "in column: " +column
                );
            }
        }
    }

 }
    private Map<String, List<String>> getMapsForColumnsAndAbbreviations(){

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

  public Map<Long, String> returnMapToReview(String related_table ) {
      //TODO
      // The thing is that there are like 20 similar cases when I do captiveServices.getAllTheCaptives().stream()
      // This should be just stored in a cache slot
      // So next project is to get back the cache part and implement it. Also, will check for further similar things.
      List<Captive> captiveList = captiveServices.getAllTheCaptives();

      switch (related_table) {
          case "build":
              return captiveList.stream().filter(e -> e.getBuild() != null)
                      .collect(Collectors.toMap(Captive::getId, Captive::getBuild));
          case "degree_of_punishment":
              return captiveList.stream().filter(e -> e.getDegree_of_punishment() != null)
                      .collect(Collectors.toMap(Captive::getId, Captive::getDegree_of_punishment));
          case "degree_of_crime":
              return captiveList.stream().filter(e -> e.getDegree_of_crime() != null)
                      .collect(Collectors.toMap(Captive::getId, Captive::getDegree_of_crime));
          case "sex":
              return captiveList.stream().filter(e -> e.getSex() != null)
                      .collect(Collectors.toMap(Captive::getId, Captive::getSex));
          case "dentition":
              return captiveList.stream().filter(e -> e.getDentition() != null)
                      .collect(Collectors.toMap(Captive::getId, Captive::getDentition));
          case "religion":
              return captiveList.stream().filter(e -> e.getReligion() != null)
                      .collect(Collectors.toMap(Captive::getId, Captive::getReligion));
          default:
              return new HashMap<>();
      }

  }

}
