package projectH.HistoricalDatabaseofCaptives.CaptivesData;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;

// Repository to connect to the database
@Repository
public interface CaptiveRecordRepository extends JpaRepository<Captive, Long> {

    @Query("select cd from captive cd WHERE cd.place_of_residence = ?1 and cd.sex = ?2")
    List<Captive> getTargetGroupByLocationAndSex (String placeOfResidence, String sex);


}
