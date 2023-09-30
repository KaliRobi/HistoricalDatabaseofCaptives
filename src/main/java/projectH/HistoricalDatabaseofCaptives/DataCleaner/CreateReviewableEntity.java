package projectH.HistoricalDatabaseofCaptives.DataCleaner;

import org.springframework.stereotype.Component;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveRecordRepository;

import java.util.ArrayList;
import java.util.List;

@Component
public class CreateReviewableEntity {

    private final ReviewableEntityRepository reviewableEntityRepository;

    public CreateReviewableEntity(ReviewableEntityRepository reviewableEntityRepository) {
        this.reviewableEntityRepository = reviewableEntityRepository;

    }


    public void registerReviewableEntity(Long entityId, String EntityType, String reason ){

        ReviewableEntity reviewableEntity = new ReviewableEntity();
        reviewableEntity.setEntity_id(entityId); reviewableEntity.setEntity_type(EntityType) ; reviewableEntity.setReason(reason);
    reviewableEntityRepository.save(reviewableEntity);


    }




}
