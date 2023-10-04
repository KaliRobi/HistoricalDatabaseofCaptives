package projectH.HistoricalDatabaseofCaptives.DataCleaner;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SourceType;

import java.sql.Timestamp;


@Table(uniqueConstraints = {@UniqueConstraint(name = "uniqueEntityAndType", columnNames = {"entity_id", "entity_type" })})
@Entity(name="Reviewable_Entity")
public class ReviewableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @CreationTimestamp
    private Timestamp insert_time;

    private Long entity_id;

    private String entity_type; // joining table name;

    private  String reason; // reason to review the given entity.

    public ReviewableEntity(Long id, Timestamp insert_time, long entity_id, String entity_type, String reason) {
        Id = id;
        this.insert_time = insert_time;
        this.entity_id = entity_id;
        this.entity_type = entity_type;
        this.reason = reason;
    }
    public ReviewableEntity() {
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Timestamp getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(Timestamp insert_time) {
        this.insert_time = insert_time;
    }

    public Long getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(Long entity_id) {
        this.entity_id = entity_id;
    }

    public String getEntity_type() {
        return entity_type;
    }

    public void setEntity_type(String entity_type) {
        this.entity_type = entity_type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "ReviewableEntity{" +
                "Id=" + Id +
                ", insert_time=" + insert_time +
                ", entity_id='" + entity_id + '\'' +
                ", entity_type='" + entity_type + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
