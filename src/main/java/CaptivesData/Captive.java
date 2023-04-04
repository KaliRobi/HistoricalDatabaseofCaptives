package CaptivesData;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Date;

// The clas needs to describe all the attributes of a captive.

@Table

public class Captive {
    @Id
    private long database_id;
    private String volume;
    private String captive_id;
    private String captive_name;
    private String sex;
    private Integer height;
    private String build;
    private String dentition;
    private String special_peculiarities;
    private Date date_of_birth;
    private String place_of_birth;
    private String address;
    private String religion;
    private String childhood_status;
    private String marital_status;
    private Integer number_of_children;
    private String occupation_1;
    private String occupation_2;
    private String occupation_3;
    private String military_service;
    private String literacy;
    private String education;
    private String criminal_history;
    private String current_crime;
    private Date sentence_start_date;
    private Date sentence_end_date;
    private Integer prison_term;
    private String ransom;
    private String associate;
    private String degree_of_crime;
    private String degree_of_punishment;
    private String notes;
    private String arrest_site;

    public Captive(long database_id, String volume, String captive_id, String captive_name, String sex, Integer height, String build, String dentition, String special_peculiarities, Date date_of_birth, String place_of_birth, String address, String religion, String childhood_status, String marital_status, Integer number_of_children, String occupation_1, String occupation_2, String occupation_3, String military_service, String literacy, String education, String criminal_history, String current_crime, Date sentence_start_date, Date sentence_end_date, Integer prison_term, String ransom, String associate, String degree_of_crime, String degree_of_punishment, String notes, String arrest_site) {
        this.database_id = database_id;
        this.volume = volume;
        this.captive_id = captive_id;
        this.captive_name = captive_name;
        this.sex = sex;
        this.height = height;
        this.build = build;
        this.dentition = dentition;
        this.special_peculiarities = special_peculiarities;
        this.date_of_birth = date_of_birth;
        this.place_of_birth = place_of_birth;
        this.address = address;
        this.religion = religion;
        this.childhood_status = childhood_status;
        this.marital_status = marital_status;
        this.number_of_children = number_of_children;
        this.occupation_1 = occupation_1;
        this.occupation_2 = occupation_2;
        this.occupation_3 = occupation_3;
        this.military_service = military_service;
        this.literacy = literacy;
        this.education = education;
        this.criminal_history = criminal_history;
        this.current_crime = current_crime;
        this.sentence_start_date = sentence_start_date;
        this.sentence_end_date = sentence_end_date;
        this.prison_term = prison_term;
        this.ransom = ransom;
        this.associate = associate;
        this.degree_of_crime = degree_of_crime;
        this.degree_of_punishment = degree_of_punishment;
        this.notes = notes;
        this.arrest_site = arrest_site;
    }

    public long getDatabase_id() {
        return database_id;
    }

    public void setDatabase_id(long database_id) {
        this.database_id = database_id;
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

    public String getCaptive_name() {
        return captive_name;
    }

    public void setCaptive_name(String captive_name) {
        this.captive_name = captive_name;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getOccupation_1() {
        return occupation_1;
    }

    public void setOccupation_1(String occupation_1) {
        this.occupation_1 = occupation_1;
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

    public String getCurrent_crime() {
        return current_crime;
    }

    public void setCurrent_crime(String current_crime) {
        this.current_crime = current_crime;
    }

    public Date getSentence_start_date() {
        return sentence_start_date;
    }

    public void setSentence_start_date(Date sentence_start_date) {
        this.sentence_start_date = sentence_start_date;
    }

    public Date getSentence_end_date() {
        return sentence_end_date;
    }

    public void setSentence_end_date(Date sentence_end_date) {
        this.sentence_end_date = sentence_end_date;
    }

    public Integer getPrison_term() {
        return prison_term;
    }

    public void setPrison_term(Integer prison_term) {
        this.prison_term = prison_term;
    }

    public String getRansom() {
        return ransom;
    }

    public void setRansom(String ransom) {
        this.ransom = ransom;
    }

    public String getAssociate() {
        return associate;
    }

    public void setAssociate(String associate) {
        this.associate = associate;
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
                "database_id=" + database_id +
                ", volume='" + volume + '\'' +
                ", captive_id='" + captive_id + '\'' +
                ", captive_name='" + captive_name + '\'' +
                ", sex='" + sex + '\'' +
                ", height=" + height +
                ", build='" + build + '\'' +
                ", dentition='" + dentition + '\'' +
                ", special_peculiarities='" + special_peculiarities + '\'' +
                ", date_of_birth=" + date_of_birth +
                ", place_of_birth='" + place_of_birth + '\'' +
                ", address='" + address + '\'' +
                ", religion='" + religion + '\'' +
                ", childhood_status='" + childhood_status + '\'' +
                ", marital_status='" + marital_status + '\'' +
                ", number_of_children=" + number_of_children +
                ", occupation_1='" + occupation_1 + '\'' +
                ", occupation_2='" + occupation_2 + '\'' +
                ", occupation_3='" + occupation_3 + '\'' +
                ", military_service='" + military_service + '\'' +
                ", literacy='" + literacy + '\'' +
                ", education='" + education + '\'' +
                ", criminal_history='" + criminal_history + '\'' +
                ", current_crime='" + current_crime + '\'' +
                ", sentence_start_date=" + sentence_start_date +
                ", sentence_end_date=" + sentence_end_date +
                ", prison_term=" + prison_term +
                ", ransome='" + ransom + '\'' +
                ", associate='" + associate + '\'' +
                ", degree_of_crime='" + degree_of_crime + '\'' +
                ", degree_of_punishment='" + degree_of_punishment + '\'' +
                ", notes='" + notes + '\'' +
                ", arrest_site='" + arrest_site + '\'' +
                '}';
    }
}
