package projectH.historicaldatabaseofcaptives.gisdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;
import java.io.Serializable;
import java.lang.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Component
public class OSVJson implements Serializable {
 @JsonProperty("display_name")
 private String display_name;
 @JsonProperty("lon")
 private double lon;
 @JsonProperty("lat")
 private double lat;
 @JsonProperty("place_id")
 private long place_id;
 @JsonProperty("licence")
 private String licence;
 @JsonProperty("osm_type")
 private String osm_type;
 @JsonProperty("osm_id")
 private long osm_id;
 @JsonProperty("boundingbox")
 private List<BigDecimal> boundingbox;
 @JsonProperty("type")
 private String type;
 @JsonProperty("class")
 private String osm_class;
 @JsonProperty("importance")
 private double importance;
 @JsonProperty("icon")
 private String icon;


 public OSVJson(long place_id,
                String licence,
                String display_name,
                double lon,
                double lat,
                String osm_type,
                long osm_id,
                String osm_class,
                List<BigDecimal> boundingbox,
                String type,
                double importance,
                String icon


 ) {
  this.place_id = place_id;
  this.licence = licence;
  this.display_name = display_name;
  this.lon = lon;
  this.lat = lat;
  this.osm_type = osm_type;
  this.osm_id = osm_id;
  this.osm_class = osm_class;
  this.boundingbox = boundingbox;
  this.type = type;
  this.importance = importance;
  this.icon = icon;


 }

 public OSVJson() {
 }

 public OSVJson(String stringCostruct, String display_name) {
  this.display_name = display_name;
 }

 public String getDisplay_name() {
  return display_name;
 }

 @JsonProperty("display_name")
 public void setDisplay_name(String display_name) {
  this.display_name = display_name;
 }

 public double getLon() {
  return lon;
 }

 public void setLon(double lon) {
  this.lon = lon;
 }

 public double getLat() {
  return lat;
 }

 public void setLat(double lat) {
  this.lat = lat;
 }

 public long getPlace_id() {
  return place_id;
 }

 public void setPlace_id(long place_id) {
  this.place_id = place_id;
 }

 public String getLicence() {
  return licence;
 }

 public void setLicence(String licence) {
  this.licence = licence;
 }

 public String getOsm_type() {
  return osm_type;
 }

 public void setOsm_type(String osm_type) {
  this.osm_type = osm_type;
 }

 public long getOsm_id() {
  return osm_id;
 }

 public void setOsm_id(long osm_id) {
  this.osm_id = osm_id;
 }

 public List<BigDecimal> getBoundingbox() {
  return boundingbox;
 }

 public void setBoundingbox(List<BigDecimal> boundingbox) {
  this.boundingbox = boundingbox;
 }

 public String getType() {
  return type;
 }

 public void setType(String type) {
  this.type = type;
 }

 public String getOsm_class() {
  return osm_class;
 }

 public void setOsm_class(String osm_class) {
  this.osm_class = osm_class;
 }

 public double getImportance() {
  return importance;
 }

 public void setImportance(double importance) {
  this.importance = importance;
 }

 public String getIcon() {
  return icon;
 }

 public void setIcon(String icon) {
  this.icon = icon;
 }

 @Override
 public boolean equals(Object o) {
  if (this == o) return true;
  if (o == null || getClass() != o.getClass()) return false;
  OSVJson osvJson = (OSVJson) o;
  return Objects.equals(display_name, osvJson.display_name) && Objects.equals(lon, osvJson.lon) && Objects.equals(lat, osvJson.lat) && Objects.equals(place_id, osvJson.place_id) && Objects.equals(licence, osvJson.licence) && Objects.equals(osm_type, osvJson.osm_type) && Objects.equals(osm_id, osvJson.osm_id) && Objects.equals(boundingbox, osvJson.boundingbox) && Objects.equals(type, osvJson.type) && Objects.equals(osm_class, osvJson.osm_class) && Objects.equals(importance, osvJson.importance) && Objects.equals(icon, osvJson.icon);
 }

 @Override
 public int hashCode() {
  return Objects.hash(display_name, lon, lat, place_id, licence, osm_type, osm_id, boundingbox, type, osm_class, importance, icon);
 }

 @Override
 public String toString() {
  return "OSVJson{" +
          "display_name='" + display_name + '\'' +
          ", lon='" + lon + '\'' +
          ", lat='" + lat + '\'' +
          ", place_id='" + place_id + '\'' +
          ", licence='" + licence + '\'' +
          ", osm_type='" + osm_type + '\'' +
          ", osm_id='" + osm_id + '\'' +
          ", boundingbox='" + boundingbox + '\'' +
          ", type='" + type + '\'' +
          ", osm_class='" + osm_class + '\'' +
          ", importance='" + importance + '\'' +
          ", icon='" + icon + '\'' +
          '}';
 }
}
