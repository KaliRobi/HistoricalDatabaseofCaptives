package projectH.HistoricalDatabaseofCaptives.GISData;

import jakarta.persistence.*;

import java.sql.Timestamp;


@Table(name="geological_locations")
@Entity(name="geological_location")
public class GeoLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp insert_time;

    @Column(name = "osv_name")
    private String osv_name;
    @Column( name = "source_name")
    private String source_name;
    private Double Latitude;

    private Double Longitude;

    @Column( name = "country")
    private String country;

    //convention is that longitude first then latitude //this.id = id removed
    public GeoLocation(String sourceName, String osvName,  Double longitude, Double latitude, String country) {
        this.source_name = sourceName;
        this.osv_name = osvName;
        this.Latitude = latitude;
        this.Longitude = longitude;
        this.country = country;
    }
    public GeoLocation(Double longitude ,Double latitude ) {
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
    public Timestamp getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(Timestamp insert_time) {
        this.insert_time = insert_time;
    }


    @Override
    public String toString() {
        return "GeoLocation{" +
                "id=" + id +
                ", osv_name='" + osv_name + '\'' +
                ", source_name='" + source_name + '\'' +
                ", Longitude=" + Longitude +
                ", Latitude=" + Latitude +
                ", country='" + country + '\'' +
                '}';
    }
}
