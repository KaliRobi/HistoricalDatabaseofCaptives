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

    public OutstandingGeolocation(long id, String osv_name, String source_name) {
        this.id = id;
        this.osv_name = osv_name;
        this.source_name = source_name;
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
