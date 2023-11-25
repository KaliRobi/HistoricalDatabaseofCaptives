package projectH.historicaldatabaseofcaptives.datacleaner;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ReviewableEntityRepository extends JpaRepository<ReviewableEntity, Long> {

    @Query("select re.id from Reviewable_Entity re where re.entity_id = ?1  and re.entity_type = ?2")
    Optional<ReviewableEntity> findByEntityTypeAndID( long entityId, String entityType );

}
