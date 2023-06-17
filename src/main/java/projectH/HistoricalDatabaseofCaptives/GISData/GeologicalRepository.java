package projectH.HistoricalDatabaseofCaptives.GISData;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@ComponentScan("projectH.HistoricalDatabaseofCaptives.GISData.GeologicalRepository")
public interface GeologicalRepository extends CrudRepository<GeologicalLocations, Long> {
}
