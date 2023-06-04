package projectH.HistoricalDatabaseofCaptives.CaptivesData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

// Repository to connect to the databse

public interface CaptiveRecordRepository extends CrudRepository<Captive, Long> {

}
