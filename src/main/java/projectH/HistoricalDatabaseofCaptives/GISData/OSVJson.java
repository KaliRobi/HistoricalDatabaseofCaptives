package projectH.HistoricalDatabaseofCaptives.GISData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.lang.*;
@Component
public class OSVJson implements Serializable {

  private  String display_name;

  private   String lon;

  private   String lat;

  private String place_id;


 private String  licence;

 private String osm_type;
 private String osm_id;
 private String boundingbox;
 private String type;
 private String osm_class;
 private String importance;
 private String icon;



@JsonIgnore
 public OSVJson( @JsonProperty("display_name") String display_name,
                 @JsonProperty("lon")String lon,
                 @JsonProperty("lat") String lat,
                 @JsonProperty("osm_type") String osm_type,
                 @JsonProperty("osm_id") String osm_id,
                 @JsonProperty("class") String osm_class,
                 @JsonProperty("boundingbox") String boundingbox,
                 @JsonProperty("type") String type,
                 @JsonProperty("importance") String importance,
                 @JsonProperty("icon") String icon
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

 }

 public OSVJson() { }

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
