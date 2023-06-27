package projectH.HistoricalDatabaseofCaptives.GISData;

import jakarta.persistence.*;


@Table(name="geological_locations")
@Entity(name="geological_location")
public class GeologicalLocations {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    public  void setLongitude(double longitude) {
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
