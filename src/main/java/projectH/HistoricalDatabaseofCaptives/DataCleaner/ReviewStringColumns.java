package projectH.HistoricalDatabaseofCaptives.DataCleaner;


/*
instead of column specific reviews instance specific ones are mor effective.

 */

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ReviewStringColumns {


    private final LocalAbbreviatedEntityRepository localAbbreviatedEntityRepository;

    public ReviewStringColumns(LocalAbbreviatedEntityRepository localAbbreviatedEntityRepository) {
        this.localAbbreviatedEntityRepository = localAbbreviatedEntityRepository;
    }

     // need  a switch which loops though the keys (tables) and each table will loops though the columns


    //get the map of tables registered in the local_abbreviated_entities and the belonging column

    private Map<String, String> getListOfTablesUsingAbbr(){

        return   localAbbreviatedEntityRepository.findAll()
                .stream().filter(e -> e.getRelated_table() != null).collect(Collectors.toMap(LocalAbbreviatedEntity::getRelated_table, LocalAbbreviatedEntity::getTable_column   ));

    }



}
