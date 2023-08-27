package projectH.HistoricalDatabaseofCaptives.CaptivesData;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


// The clas needs to describe all the attributes of a captive.
@Table(name = "captives_data")
@Entity(name="captive")
public class Captive {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp insert_time;
    private int volume;
    private String captive_id;
    private String name;
    private String sex;
    private Integer height;
    private String build;
    private String dentition;
    private String special_peculiarities;
    private LocalDate date_of_birth;
    private String place_of_birth;
    private String place_of_residence;

    private String residence;

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
    private LocalDate sentence_begins;
    private LocalDate sentence_expires;
    private Integer prison_term_days;
    private int ransom;
    private String associates;
    private String degree_of_crime;
    private String degree_of_punishment;
    private String notes;
    private String arrest_site;

    //who inserted the captive to the db, not sure of the conventional insert_time, insert_user, update_time, update_user is needed in this small project
    private  String username;



    Captive(long id, int volume, String captive_id, String name, String sex, Integer height, String build, String dentition, String special_peculiarities, LocalDate dateOfBirth, String place_of_birth, String place_of_residence, String residence, String religion, String childhood_status, String marital_status, Integer number_of_children, String occupation, String occupation_2, String occupation_3, String military_service, String literacy, String education, String criminal_history, String crime, LocalDate sentence_begins, LocalDate sentence_expires, Integer prison_term_days, int ransom, String associates, String degree_of_crime, String degree_of_punishment, String notes, String arrest_site, String username) {
        this.id = id;
        this.volume = volume;
        this.captive_id = captive_id;
        this.name = name;
        this.sex = sex;
        this.height = height;
        this.build = build;
        this.dentition = dentition;
        this.special_peculiarities = special_peculiarities;
        this.date_of_birth = dateOfBirth;
        this.place_of_birth = place_of_birth;
        this.place_of_residence = place_of_residence;
        this.residence = residence;
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
        this.username = username;
    }

    public Captive() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getCaptive_id() {
        //this number does not exist in the historical source
        return captive_id != null ? captive_id : "0001";
    }

    public Timestamp getInsert_time() {
        return insert_time;
    }

    public void setInsert_time(Timestamp insert_time) {
        this.insert_time = insert_time;
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

    public LocalDate getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(LocalDate date_of_birth) {
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
    public String getResidence() {
        return residence;
    }

    public void setResidence(String residence) {
        this.residence = residence;
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

    public LocalDate getSentence_begins() {
        return sentence_begins;
    }

    public void setSentence_begins(LocalDate sentence_begins) {
        this.sentence_begins = sentence_begins;
    }

    public LocalDate getSentence_expires() {
        return sentence_expires;
    }

    public void setSentence_expires(LocalDate sentence_expires) {
        this.sentence_expires = sentence_expires;
    }

    public Integer getPrison_term_days() {
        return prison_term_days;
    }

    public void setPrison_term_days(Integer prison_term_days) {
        this.prison_term_days = prison_term_days;
    }

    public int getRansom() {
        return ransom;
    }

    public void setRansom(int ransom) {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge(){
      int birthYear =  0;
        if (getDate_of_birth() != null) {
            birthYear = getDate_of_birth().get(ChronoField.YEAR);
        }
        //based on the historical source this cannot be null
        int recordYear = Integer.parseInt(getCaptive_id().substring(0,4));
      return birthYear != 0 ? (recordYear - birthYear) : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Captive captive = (Captive) o;
        return id == captive.id && Objects.equals(volume, captive.volume) && Objects.equals(captive_id, captive.captive_id) && Objects.equals(name, captive.name) && Objects.equals(sex, captive.sex) && Objects.equals(height, captive.height) && Objects.equals(build, captive.build) && Objects.equals(dentition, captive.dentition) && Objects.equals(special_peculiarities, captive.special_peculiarities) && Objects.equals(date_of_birth, captive.date_of_birth) && Objects.equals(place_of_birth, captive.place_of_birth) && Objects.equals(place_of_residence, captive.place_of_residence) && Objects.equals(residence, captive.residence) && Objects.equals(religion, captive.religion) && Objects.equals(childhood_status, captive.childhood_status) && Objects.equals(marital_status, captive.marital_status) && Objects.equals(number_of_children, captive.number_of_children) && Objects.equals(occupation, captive.occupation) && Objects.equals(occupation_2, captive.occupation_2) && Objects.equals(occupation_3, captive.occupation_3) && Objects.equals(military_service, captive.military_service) && Objects.equals(literacy, captive.literacy) && Objects.equals(education, captive.education) && Objects.equals(criminal_history, captive.criminal_history) && Objects.equals(crime, captive.crime) && Objects.equals(sentence_begins, captive.sentence_begins) && Objects.equals(sentence_expires, captive.sentence_expires) && Objects.equals(prison_term_days, captive.prison_term_days) && Objects.equals(ransom, captive.ransom) && Objects.equals(associates, captive.associates) && Objects.equals(degree_of_crime, captive.degree_of_crime) && Objects.equals(degree_of_punishment, captive.degree_of_punishment) && Objects.equals(notes, captive.notes) && Objects.equals(arrest_site, captive.arrest_site );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, volume, captive_id, name, sex, height, build, dentition, special_peculiarities, date_of_birth, place_of_birth, place_of_residence, residence, religion, childhood_status, marital_status, number_of_children, occupation, occupation_2, occupation_3, military_service, literacy, education, criminal_history, crime, sentence_begins, sentence_expires, prison_term_days, ransom, associates, degree_of_crime, degree_of_punishment, notes, arrest_site);
    }

    public Set<String> getNonNullAttributes(){
        Set<String> attribs = new HashSet<>();

        if (id != 0 ) {
            attribs.add("id");
        }
        if (volume >= 0) {
            attribs.add("volume");
        }
        if (captive_id != null) {
            attribs.add("captive_id");
        }
        if (name != null) {
            attribs.add("name");
        }
        if (sex != null) {
            attribs.add("sex");
        }
        if (height != null) {
            attribs.add("height");
        }
        if (build != null) {
            attribs.add("build");
        }
        if (date_of_birth != null) {
            attribs.add("date_of_birth");
        }
        if (place_of_birth!= null) {
            attribs.add("place_of_birth");
        }
        if (place_of_residence != null) {
            attribs.add("place_of_residence");
        }
        if (religion != null) {
            attribs.add("religion");
        }
        if (childhood_status != null) {
            attribs.add("childhood_status");
        }
        if (marital_status != null) {
            attribs.add("marital_status");
        }
        if (number_of_children != null) {
            attribs.add("number_of_children");
        }
        if (occupation != null) {
            attribs.add("occupation");
        }
        if (occupation_2 != null) {
            attribs.add("occupation_2");
        }
        if (military_service != null) {
            attribs.add("military_service");
        }
        return attribs;

//            ...
//

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
                ", residence='" + residence + '\'' +
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
