package projectH.HistoricalDatabaseofCaptives.CaptivesData;

import org.springframework.data.repository.CrudRepository;

// Repository to connect to the databse

public interface CaptiveRepository extends CrudRepository<Captive, Long> {

}
