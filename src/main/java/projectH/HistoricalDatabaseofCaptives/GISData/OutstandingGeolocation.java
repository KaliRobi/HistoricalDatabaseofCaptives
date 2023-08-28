package projectH.HistoricalDatabaseofCaptives.GISData;

import jakarta.persistence.*;
import org.apache.logging.log4j.CloseableThreadContext;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Calendar;

@Entity
@Table(name = "Outstanding_Geological_Location")
public class OutstandingGeolocation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="insert_time", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Timestamp insert_time;

    private String osv_name;

    private String source_name;

    private long geological_location_id;

    public OutstandingGeolocation(long id, Timestamp insert_time, String osv_name, String source_name, long geological_location_id) {
        this.id = id;
        this.insert_time = insert_time;
        this.osv_name = osv_name;
        this.source_name = source_name;
        this.geological_location_id = geological_location_id;
    }

    public long getGeological_location_id() {
        return geological_location_id;
    }

    public static OutstandingGeolocation fromGeolocation(GeoLocation location){
        OutstandingGeolocation outstanding = new OutstandingGeolocation();
        outstanding.setGeological_location_id(location.getId());
        outstanding.setOsv_name(location.getOsv_name());
        outstanding.setSource_name(location.getSource_name());
        outstanding.setInsert_time(Timestamp.from(Instant.now()));

        return outstanding;
    }

    public void setGeological_location_id(long geological_location_id) {
        this.geological_location_id = geological_location_id;
    }

    public Timestamp getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(Timestamp insert_time) {
        this.insert_time = insert_time;
    }

    public OutstandingGeolocation() {}

    public String getOsv_name() {
        return osv_name;
    }

    public void setOsv_name(String osv_name) {
        this.osv_name = osv_name;
    }

    public String getSource_name() {
        return source_name;
    }

    public void setSource_name(String source_name) {
        this.source_name = source_name;
    }

    @Override
    public String toString() {
        return "OutstandingGeolocation{" +
                "id=" + id +
                ", osv_name='" + osv_name + '\'' +
                ", source_name='" + source_name + '\'' +
                '}';
    }
}
