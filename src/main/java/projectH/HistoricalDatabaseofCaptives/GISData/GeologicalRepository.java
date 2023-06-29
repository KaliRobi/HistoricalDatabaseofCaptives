package projectH.HistoricalDatabaseofCaptives.GISData;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
import java.util.List;

@Repository
public interface GeologicalRepository extends JpaRepository<GeoLocation, Long> {
    @Query("select loc from geological_location loc WHERE loc.Name = ?1")
    GeoLocation findByName(String Name);

}

