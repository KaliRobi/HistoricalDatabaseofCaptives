package projectH.HistoricalDatabaseofCaptives.DataCleaner;

import org.springframework.stereotype.Component;


@Component
public class CreateReviewableEntity {

    private final ReviewableEntityRepository reviewableEntityRepository;

    public CreateReviewableEntity(ReviewableEntityRepository reviewableEntityRepository) {
        this.reviewableEntityRepository = reviewableEntityRepository;

    }
    public void registerReviewableEntity(long entityId, String EntityType, String reason )  {

        if (!isReviewablePresent(entityId, EntityType)){
            ReviewableEntity reviewableEntity = new ReviewableEntity();
            reviewableEntity.setEntity_id(entityId); reviewableEntity.setEntity_type(EntityType) ; reviewableEntity.setReason(reason);
            reviewableEntityRepository.save(reviewableEntity);
            System.out.println(entityId+ " " + EntityType + " was added "  );
        }
        System.out.println(entityId+ " " + EntityType + " is already present "  );

    }

    private boolean isReviewablePresent(Long entityId, String EntityType){
        // entity_ID column ended up being character varying for some reason
        return reviewableEntityRepository.findByEntityTypeAndID(String.valueOf(entityId), EntityType).isPresent();

    }
}
