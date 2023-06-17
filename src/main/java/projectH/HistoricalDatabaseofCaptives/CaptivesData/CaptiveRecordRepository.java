package projectH.HistoricalDatabaseofCaptives.CaptivesData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// Repository to connect to the databse
@Repository
public interface CaptiveRecordRepository extends CrudRepository<Captive, Long> {

}
