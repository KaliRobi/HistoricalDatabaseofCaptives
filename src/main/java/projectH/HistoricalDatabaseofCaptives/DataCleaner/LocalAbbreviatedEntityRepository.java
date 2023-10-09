package projectH.HistoricalDatabaseofCaptives.DataCleaner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LocalAbbreviatedEntityRepository extends JpaRepository< LocalAbbreviatedEntity, Long> {

    @Query("select lae.abbr from local_abbreviated_entity lae where lae.abbr = ?1  and lae.related_table = ?2")
    Optional<LocalAbbreviatedEntity> findByColumnNameAndRelatedTable(String column, String related_table );

}
