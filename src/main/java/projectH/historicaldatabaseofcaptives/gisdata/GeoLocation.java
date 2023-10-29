package projectH.historicaldatabaseofcaptives.gisdata;

import jakarta.persistence.*;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.sql.Timestamp;

@Entity
@Table(name="geological_locations")
public class GeoLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JdbcTypeCode(SqlTypes.VARCHAR)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "insert_time")
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    private Timestamp insertTime;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(name = "osv_name")
    private String osvName;
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column( name = "source_name")
    private String sourceName;
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column( name = "latitude")
    private Double latitude;

    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column( name = "longitude")
    private Double longitude;
//    @JdbcType
    @JdbcTypeCode(SqlTypes.VARCHAR)
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

    public String getOsvName() {
        return osvName;
    }

    public void setOsvName(String osvName) {
        this.osvName = osvName;
    }

    public String getSourceName() {
        return this.sourceName;
    }

    public void setSourceName(String sourceName) {

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
    public Timestamp getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Timestamp insertTime) {
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
