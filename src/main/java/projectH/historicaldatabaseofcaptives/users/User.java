package projectH.historicaldatabaseofcaptives.users;

import jakarta.persistence.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import projectH.historicaldatabaseofcaptives.gisdata.GeoLocation;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity(name = "hdc_user")
@Table(name = "hdc_user")
public class User implements IPerson , UserDetails {

// in the first release only manual user creation will be possible.

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long user_id;
    @Temporal(TemporalType.TIMESTAMP)
    private final Timestamp Insert_time;
    private final Instant Birth_Date;
    private String Name;

    private final String Sex;
    @ManyToOne(targetEntity = GeoLocation.class, fetch = FetchType.LAZY)
    private final GeoLocation Location;
    private  String Username;

    private String Password;

    private  boolean IsActive;


    private  String EmailAddress;


    private final String role;

    public User(long user_id, Timestamp insert_time,
                Instant birthDate, String name,
                GeoLocation geoLocation, String sex,
                String username, String password,
                boolean isActive, String emailAddress,
                String role) {
        this.user_id = user_id;
        this.Insert_time = insert_time;
        this.Birth_Date = birthDate;
        this.Name = name;
        this.Location = geoLocation;
        this.Sex = sex;
        this.Username = username;
        Password = password;
        IsActive = isActive;
        this.EmailAddress = emailAddress;
        this.role = role;
    }

    public long getUser_id() {
        return user_id;
    }

    public Instant getBirth_Date() {
        return Birth_Date;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return Password;
    }

    @Override
    public String getUsername() {
        return Username;
    }


    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
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
        return Name;
    }

    @Override
    public void setName(String name) {
        this.Name = name;
    }


    @Override
    public GeoLocation getLocation() {
        return Location;
    }

    @Override
    public void setLocation(GeoLocation geoLocation) {

    }

    @Override
    public String getSex() {
        return Sex;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
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
