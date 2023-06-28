package projectH.HistoricalDatabaseofCaptives.GISData;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeologicalRepository extends JpaRepository<GeoLocations, Long> {
}

