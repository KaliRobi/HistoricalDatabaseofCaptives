package projectH.HistoricalDatabaseofCaptives.GISData;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name="geological_locations")
@Entity
public class GeologicalLocations {

    @Id
    private long Id;

    private String Name;

    private double Latitude;

    private double Longitude;

    public GeologicalLocations(long id, String name, double latitude, double longitude) {
        Id = id;
        Name = name;
        Latitude = latitude;
        Longitude = longitude;
    }

    public GeologicalLocations() {

    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    @Override
    public String toString() {
        return "geological_locations{" +
                "Id=" + Id +
                ", Name='" + Name + '\'' +
                ", Latitude=" + Latitude +
                ", Longitude=" + Longitude +
                '}';
    }
}
