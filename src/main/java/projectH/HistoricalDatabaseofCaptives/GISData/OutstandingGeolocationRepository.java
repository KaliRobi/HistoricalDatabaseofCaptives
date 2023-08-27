package projectH.HistoricalDatabaseofCaptives.GISData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutstandingGeolocationRepository extends JpaRepository<OutstandingGeolocation, Long> {
}
