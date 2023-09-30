package projectH.HistoricalDatabaseofCaptives.DataCleaner;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewableEntityRepository extends JpaRepository<ReviewableEntity, Long> {
}
