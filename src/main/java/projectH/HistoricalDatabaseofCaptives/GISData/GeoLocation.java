package projectH.HistoricalDatabaseofCaptives.GISData;

import jakarta.persistence.*;


@Table(name="geological_locations")
@Entity(name="geological_location")
public class GeoLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "osv_name")
    private String osv_name;
    @Column( name = "source_name")
    private String source_name;
    private Double Latitude;

    private Double Longitude;

    @Column( name = "country")
    private String country;

    public GeoLocation(String sourceName, String osvName, Double latitude, Double longitude, String country) {
        this.id = id;
        this.source_name = sourceName;
        this.osv_name = osvName;
        this.Latitude = latitude;
        this.Longitude = longitude;
        this.country = country;
    }

    public GeoLocation() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOsv_name() {
        return osv_name;
    }

    public void setOsv_name(String osv_name) {
        this.osv_name = osv_name;
    }

    public String getSource_name() {
        return this.source_name ;
    }

    public void setSource_name(String source_name) {

        this.source_name = source_name;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "GeoLocation{" +
                "id=" + id +
                ", osv_name='" + osv_name + '\'' +
                ", source_name='" + source_name + '\'' +
                ", Latitude=" + Latitude +
                ", Longitude=" + Longitude +
                ", country='" + country + '\'' +
                '}';
    }
}
