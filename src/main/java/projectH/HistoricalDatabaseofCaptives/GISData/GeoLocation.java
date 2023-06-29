package projectH.HistoricalDatabaseofCaptives.GISData;

import jakarta.persistence.*;


@Table(name="geological_locations")
@Entity(name="geological_location")
public class GeoLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String Name;

    private Double Latitude;

    private Double Longitude;

    public GeoLocation(String name, Double latitude, Double longitude) {
        this.id = id;
        this.Name = name;
        this.Latitude = latitude;
        this.Longitude = longitude;
    }

    public GeoLocation() {
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        this.Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        this.Longitude = longitude;
    }

    @Override
    public String toString() {
        return "GeoLocations{" +
                "id=" + id +
                ", name='" + Name + '\'' +
                ", latitude=" + Latitude +
                ", longitude=" + Longitude +
                '}';
    }
}
