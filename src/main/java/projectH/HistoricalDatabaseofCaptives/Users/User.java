package projectH.HistoricalDatabaseofCaptives.Users;

import jakarta.persistence.*;
import projectH.HistoricalDatabaseofCaptives.GISData.GeoLocation;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "hdc_user")
public class User implements IPerson{
// in the first release only manual user creation will be possible.

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final long user_id;

    private final Instant Insert_time;
    private final Instant Birth_Date;
    private final String Name;

    private final String Sex;
    private GeoLocation Location;
    private String Username;

    private String Password;

    private boolean IsActive;

    private String EmailAddress;

    public User(long userid, Instant insert_time, Instant birthDate, String name, GeoLocation geoLocation, String sex, String username, String emailAddress) {
        user_id = userid;
        Insert_time = insert_time;
        Birth_Date = birthDate;
        Name = name;
        Location = geoLocation;
        Sex = sex;
        Username = username;
        EmailAddress = emailAddress;
    }

    public long getUser_id() {
        return user_id;
    }

    public Instant getBirth_Date() {
        return Birth_Date;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    @Override
    public LocalDate getDateOfBirth() {
        return null;
    }

    @Override
    public int getAge() {
        return 0;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }


    @Override
    public GeoLocation getLocation() {
        return null;
    }

    @Override
    public void setLocation(GeoLocation geoLocation) {

    }

    @Override
    public String getSex() {
        return null;
    }


    @Override
    public String toString() {
        return "User{" +
                "Id=" + user_id +
                ", Insert_time=" + Insert_time +
                ", Birth_Date=" + Birth_Date +
                ", Name='" + Name + '\'' +
                ", Sex='" + Sex + '\'' +
                ", Location=" + Location +
                ", Username='" + Username + '\'' +
                ", IsActive=" + IsActive +
                ", EmailAddress='" + EmailAddress + '\'' +
                '}';
    }
}
