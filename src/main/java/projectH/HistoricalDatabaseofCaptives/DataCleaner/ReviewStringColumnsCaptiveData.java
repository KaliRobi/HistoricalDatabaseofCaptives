package projectH.HistoricalDatabaseofCaptives.DataCleaner;


/*
instead of column specific reviews instance specific ones are mor effective.

 */

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ReviewStringColumnsCaptiveData {


    private final LocalAbbreviatedEntityRepository localAbbreviatedEntityRepository;

    public ReviewStringColumnsCaptiveData(LocalAbbreviatedEntityRepository localAbbreviatedEntityRepository) {
        this.localAbbreviatedEntityRepository = localAbbreviatedEntityRepository;
    }

     // need  a switch which loops though the keys (tables) and each table will loops though the columns

    public void goForIt(){
        Map<String, List<String>> ValidatingString  =  getListOfTablesUsingAbbr();


    }
// add custom queries for all the columns


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
