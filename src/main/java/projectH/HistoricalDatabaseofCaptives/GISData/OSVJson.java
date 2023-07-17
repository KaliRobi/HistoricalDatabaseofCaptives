package projectH.HistoricalDatabaseofCaptives.GISData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.*;
import java.util.Objects;

@Component
public class OSVJson implements Serializable {
 @JsonProperty("display_name")
 private  String display_name;
 @JsonProperty("lon")
  private   String lon;
 @JsonProperty("lat")
  private   String lat;
 @JsonProperty("place_id")
  private String place_id;

 @JsonProperty("licence")
 private String  licence;
 @JsonProperty("osm_type")
 private String osm_type;
 @JsonProperty("osm_id")
 private String osm_id;
 @JsonProperty("boundingbox")
 private String boundingbox;
 @JsonProperty("type")
 private String type;
 @JsonProperty("class")
 private String osm_class;
 @JsonProperty("importance")
 private String importance;
 @JsonProperty("icon")
 private String icon;



@JsonIgnore
 public OSVJson( String display_name,
                 String lon,
                 String lat,
                 String osm_type,
                 String osm_id,
                 String osm_class,
                 String boundingbox,
                 String type,
                 String importance,
                 String icon,
                 String place_id,
                 String  licence
                 ) {
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
  this.place_id = place_id;
  this.licence = licence;

 }

 public OSVJson() { }

 public OSVJson(String stringCostruct ) {}

 public String getDisplay_name() {
  return display_name;
 }

 public void setDisplay_name(String display_name) {
  this.display_name = display_name;
 }

 public String getLon() {
  return lon;
 }

 public void setLon(String lon) {
  this.lon = lon;
 }

 public String getLat() {
  return lat;
 }

 public void setLat(String lat) {
  this.lat = lat;
 }

 public String getPlace_id() {
  return place_id;
 }

 public void setPlace_id(String place_id) {
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

 public String getOsm_id() {
  return osm_id;
 }

 public void setOsm_id(String osm_id) {
  this.osm_id = osm_id;
 }

 public String getBoundingbox() {
  return boundingbox;
 }

 public void setBoundingbox(String boundingbox) {
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

 public String getImportance() {
  return importance;
 }

 public void setImportance(String importance) {
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
