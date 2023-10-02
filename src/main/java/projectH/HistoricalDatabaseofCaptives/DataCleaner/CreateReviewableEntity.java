package projectH.HistoricalDatabaseofCaptives.DataCleaner;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Component;
import projectH.HistoricalDatabaseofCaptives.ApplicationExceptions.UniqueConstraintViolationException;

import java.sql.SQLException;


@Component
public class CreateReviewableEntity {

    private final ReviewableEntityRepository reviewableEntityRepository;

    public CreateReviewableEntity(ReviewableEntityRepository reviewableEntityRepository) {
        this.reviewableEntityRepository = reviewableEntityRepository;

    }


    public void registerReviewableEntity(Long entityId, String EntityType, String reason )  {

        ReviewableEntity reviewableEntity = new ReviewableEntity();
        reviewableEntity.setEntity_id(entityId); reviewableEntity.setEntity_type(EntityType) ; reviewableEntity.setReason(reason);

        try {
            reviewableEntityRepository.save(reviewableEntity);
        } catch (RuntimeException e){
            throw new UniqueConstraintViolationException(e);
        }




    }




}
