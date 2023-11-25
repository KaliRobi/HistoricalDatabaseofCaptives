package projectH.historicaldatabaseofcaptives.datacleaner;

import org.springframework.stereotype.Component;


@Component
public class CreateReviewableEntity {

    private final ReviewableEntityRepository reviewableEntityRepository;

    public CreateReviewableEntity(ReviewableEntityRepository reviewableEntityRepository) {
        this.reviewableEntityRepository = reviewableEntityRepository;
    }
    public void registerReviewableEntity(long entityId, String entityType, String reason )  {
        if (!isReviewablePresent(entityId, entityType)) {
            ReviewableEntity reviewableEntity = new ReviewableEntity();
            reviewableEntity.setEntity_id(entityId);
            reviewableEntity.setEntity_type(entityType);
            reviewableEntity.setReason(reason);
            reviewableEntityRepository.save(reviewableEntity);
        }
    }

    private boolean isReviewablePresent(long entityId, String entityType){
        return reviewableEntityRepository.findByEntityTypeAndID(entityId, entityType).isPresent();
    }
}
