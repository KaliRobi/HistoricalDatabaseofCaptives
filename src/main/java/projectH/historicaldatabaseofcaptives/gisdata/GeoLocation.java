package projectH.historicaldatabaseofcaptives.gisdata;



import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity(name = "geological_location")
@Table(name="geological_location")
public class GeoLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Basic
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "insert_time")
    @Basic
    private Timestamp insertTime;

    @Basic
    @Column(name = "osv_name")
    private String osvName;
    @Basic
    @Column( name = "source_name")
    private String sourceName;
    @Basic
    @Column( name = "latitude")
    private Double latitude;

    @Basic
    @Column( name = "longitude")
    private Double longitude;
//    @JdbcType
    @Basic
    @Column( name = "country")
    private String country;

    //convention is that longitude first then latitude //this.id = id removed
    public GeoLocation(String sourceName, String osvName, Double longitude, Double latitude, String country) {
        this.sourceName = sourceName;
        this.osvName = osvName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
    }
    public GeoLocation(Double longitude , Double latitude ) {
        this.latitude = latitude;
        this.longitude = longitude;

    }


    public GeoLocation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOsv_name() {
        return osvName;
    }

    public void setOsv_name(String osvName) {
        this.osvName = osvName;
    }

    public String getSource_name() {
        return this.sourceName;
    }

    public void setSource_name(String sourceName) {

        this.sourceName = sourceName;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    public Timestamp getInsert_time() {
        return insertTime;
    }

    public void setInsert_time(Timestamp insertTime) {
        this.insertTime = insertTime;
    }


    @Override
    public String toString() {
        return "GeoLocation{" +
                "id=" + id +
                ", osv_name='" + osvName + '\'' +
                ", source_name='" + sourceName + '\'' +
                ", Longitude=" + longitude +
                ", Latitude=" + latitude +
                ", country='" + country + '\'' +
                '}';
    }
}
