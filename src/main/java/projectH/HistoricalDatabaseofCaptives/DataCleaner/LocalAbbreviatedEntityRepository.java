package projectH.HistoricalDatabaseofCaptives.DataCleaner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocalAbbreviatedEntityRepository extends JpaRepository< LocalAbbreviatedEntity, Long> {
}
