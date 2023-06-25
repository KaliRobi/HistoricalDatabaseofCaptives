package projectH.HistoricalDatabaseofCaptives.CaptivesData;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Entity;
import java.util.Date;

// The clas needs to describe all the attributes of a captive.

@Table(name = "captives_data")
@Entity
public class Captive {
    @Id
    private long id;
    private String volume;
    private String captive_id;
    private String name;
    private String sex;
    private Integer height;
    private String build;
    private String dentition;
    private String special_peculiarities;
    private Date date_of_birth;
    private String place_of_birth;
    private String place_of_residence;

    private String religion;
    private String childhood_status;
    private String marital_status;
    private Integer number_of_children;
    private String occupation;
    private String occupation_2;
    private String occupation_3;
    private String military_service;
    private String literacy;
    private String education;
    private String criminal_history;
    private String crime;
    private Date sentence_begins;
    private Date sentence_expires;
    private Integer prison_term_days;
    private String ransom;
    private String associates;
    private String degree_of_crime;
    private String degree_of_punishment;
    private String notes;
    private String arrest_site;

    Captive(long id, String volume, String captive_id, String name, String sex, Integer height, String build, String dentition, String special_peculiarities, Date date_of_birth, String place_of_birth, String place_of_residence, String religion, String childhood_status, String marital_status, Integer number_of_children, String occupation, String occupation_2, String occupation_3, String military_service, String literacy, String education, String criminal_history, String crime, Date sentence_begins, Date sentence_expires, Integer prison_term_days, String ransom, String associates, String degree_of_crime, String degree_of_punishment, String notes, String arrest_site) {
        this.id = id;
        this.volume = volume;
        this.captive_id = captive_id;
        this.name = name;
        this.sex = sex;
        this.height = height;
        this.build = build;
        this.dentition = dentition;
        this.special_peculiarities = special_peculiarities;
        this.date_of_birth = date_of_birth;
        this.place_of_birth = place_of_birth;
        this.place_of_residence = place_of_residence;
        this.religion = religion;
        this.childhood_status = childhood_status;
        this.marital_status = marital_status;
        this.number_of_children = number_of_children;
        this.occupation = occupation;
        this.occupation_2 = occupation_2;
        this.occupation_3 = occupation_3;
        this.military_service = military_service;
        this.literacy = literacy;
        this.education = education;
        this.criminal_history = criminal_history;
        this.crime = crime;
        this.sentence_begins = sentence_begins;
        this.sentence_expires = sentence_expires;
        this.prison_term_days = prison_term_days;
        this.ransom = ransom;
        this.associates = associates;
        this.degree_of_crime = degree_of_crime;
        this.degree_of_punishment = degree_of_punishment;
        this.notes = notes;
        this.arrest_site = arrest_site;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getCaptive_id() {
        return captive_id;
    }

    public void setCaptive_id(String captive_id) {
        this.captive_id = captive_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getBuild() {
        return build;
    }

    public void setBuild(String build) {
        this.build = build;
    }

    public String getDentition() {
        return dentition;
    }

    public void setDentition(String dentition) {
        this.dentition = dentition;
    }

    public String getSpecial_peculiarities() {
        return special_peculiarities;
    }

    public void setSpecial_peculiarities(String special_peculiarities) {
        this.special_peculiarities = special_peculiarities;
    }

    public Date getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(Date date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getPlace_of_birth() {
        return place_of_birth;
    }

    public void setPlace_of_birth(String place_of_birth) {
        this.place_of_birth = place_of_birth;
    }

    public String getPlace_of_residence() {
        return place_of_residence;
    }

    public void setPlace_of_residence(String place_of_residence) {
        this.place_of_residence = place_of_residence;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public String getChildhood_status() {
        return childhood_status;
    }

    public void setChildhood_status(String childhood_status) {
        this.childhood_status = childhood_status;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public Integer getNumber_of_children() {
        return number_of_children;
    }

    public void setNumber_of_children(Integer number_of_children) {
        this.number_of_children = number_of_children;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getOccupation_2() {
        return occupation_2;
    }

    public void setOccupation_2(String occupation_2) {
        this.occupation_2 = occupation_2;
    }

    public String getOccupation_3() {
        return occupation_3;
    }

    public void setOccupation_3(String occupation_3) {
        this.occupation_3 = occupation_3;
    }

    public String getMilitary_service() {
        return military_service;
    }

    public void setMilitary_service(String military_service) {
        this.military_service = military_service;
    }

    public String getLiteracy() {
        return literacy;
    }

    public void setLiteracy(String literacy) {
        this.literacy = literacy;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getCriminal_history() {
        return criminal_history;
    }

    public void setCriminal_history(String criminal_history) {
        this.criminal_history = criminal_history;
    }

    public String getCrime() {
        return crime;
    }

    public void setCrime(String crime) {
        this.crime = crime;
    }

    public Date getSentence_begins() {
        return sentence_begins;
    }

    public void setSentence_begins(Date sentence_begins) {
        this.sentence_begins = sentence_begins;
    }

    public Date getSentence_expires() {
        return sentence_expires;
    }

    public void setSentence_expires(Date sentence_expires) {
        this.sentence_expires = sentence_expires;
    }

    public Integer getPrison_term_days() {
        return prison_term_days;
    }

    public void setPrison_term_days(Integer prison_term_days) {
        this.prison_term_days = prison_term_days;
    }

    public String getRansom() {
        return ransom;
    }

    public void setRansom(String ransom) {
        this.ransom = ransom;
    }

    public String getAssociates() {
        return associates;
    }

    public void setAssociates(String associates) {
        this.associates = associates;
    }

    public String getDegree_of_crime() {
        return degree_of_crime;
    }

    public void setDegree_of_crime(String degree_of_crime) {
        this.degree_of_crime = degree_of_crime;
    }

    public String getDegree_of_punishment() {
        return degree_of_punishment;
    }

    public void setDegree_of_punishment(String degree_of_punishment) {
        this.degree_of_punishment = degree_of_punishment;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getArrest_site() {
        return arrest_site;
    }

    public void setArrest_site(String arrest_site) {
        this.arrest_site = arrest_site;
    }

    @Override
    public String toString() {
        return "Captive{" +
                "id=" + id +
                ", volume='" + volume + '\'' +
                ", captive_id='" + captive_id + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", height=" + height +
                ", build='" + build + '\'' +
                ", dentition='" + dentition + '\'' +
                ", special_peculiarities='" + special_peculiarities + '\'' +
                ", date_of_birth=" + date_of_birth +
                ", place_of_birth='" + place_of_birth + '\'' +
                ", place_of_residence='" + place_of_residence + '\'' +
                ", religion='" + religion + '\'' +
                ", childhood_status='" + childhood_status + '\'' +
                ", marital_status='" + marital_status + '\'' +
                ", number_of_children=" + number_of_children +
                ", occupation='" + occupation + '\'' +
                ", occupation_2='" + occupation_2 + '\'' +
                ", occupation_3='" + occupation_3 + '\'' +
                ", military_service='" + military_service + '\'' +
                ", literacy='" + literacy + '\'' +
                ", education='" + education + '\'' +
                ", criminal_history='" + criminal_history + '\'' +
                ", current='" + crime + '\'' +
                ", sentence_begins=" + sentence_begins +
                ", sentence_expires=" + sentence_expires +
                ", prison_term=" + prison_term_days +
                ", ransom='" + ransom + '\'' +
                ", associates='" + associates + '\'' +
                ", degree_of_crime='" + degree_of_crime + '\'' +
                ", degree_of_punishment='" + degree_of_punishment + '\'' +
                ", notes='" + notes + '\'' +
                ", arrest_site='" + arrest_site + '\'' +
                '}';
    }
}
