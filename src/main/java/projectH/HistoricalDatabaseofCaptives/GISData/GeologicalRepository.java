package projectH.HistoricalDatabaseofCaptives.GISData;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeologicalRepository extends CrudRepository<GeologicalLocations, Long> {
}
