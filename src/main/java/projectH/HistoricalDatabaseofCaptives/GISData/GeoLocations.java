package projectH.HistoricalDatabaseofCaptives.GISData;

import jakarta.persistence.*;


@Table(name="geological_locations")
@Entity(name="geological_location")
public class GeoLocations {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String Name;

    private double Latitude;

    private double Longitude;

    public GeoLocations( String name, double latitude, double longitude) {
        this.id = id;
        this.Name = name;
        this.Latitude = latitude;
        this.Longitude = longitude;
    }

    public GeoLocations() {
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

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        this.Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
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
