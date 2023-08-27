package projectH.HistoricalDatabaseofCaptives.GISData;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Outstanding_Geological_Location")
public class OutstandingGeolocation {

    @Id
    private long id;
    private String osv_name;

    private String source_name;

    private long geological_location_id;

    public OutstandingGeolocation(long id, String osv_name, String source_name, long geological_location_id) {
        this.id = id;
        this.osv_name = osv_name;
        this.source_name = source_name;
        this.geological_location_id = geological_location_id;
    }

    public long getGeological_location_id() {
        return geological_location_id;
    }

    public void setGeological_location_id(long geological_location_id) {
        this.geological_location_id = geological_location_id;
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
