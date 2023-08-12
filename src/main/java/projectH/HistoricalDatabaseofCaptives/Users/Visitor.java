package projectH.HistoricalDatabaseofCaptives.Users;

import projectH.HistoricalDatabaseofCaptives.GISData.GeoLocation;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Visitor implements IPerson{
//  do I want this to be changed ever?
    private final Instant dateOfBirth;
    private String name;
    private GeoLocation location;
    private String sex;
    //TODO
    // refactor to builder pattern
    public Visitor(String name, GeoLocation geoLocation, String sex, Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        this.name = name;
        this.location = geoLocation;
        this.sex = sex;

    }
    public Visitor( GeoLocation geoLocation, String sex, Instant dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        this.name = "Anonymous";
        this.location = geoLocation;
        this.sex = sex;

    }




    @Override
    public Instant getDateOfBirth() {
        return this.dateOfBirth;
    }


    @Override
    public int getAge() {
        Calendar calendar =  new GregorianCalendar();
        //TODO
        // UnsupportedTemporalTypeException: Unsupported field: Year again
        return calendar.get(Calendar.YEAR) - this.dateOfBirth.get(ChronoField.YEAR);
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
            this.name = name;
    }

    @Override
    public GeoLocation getLocation() {
      return  this.location;
    }

    @Override
    public void setLocation(GeoLocation geoLocation) {
        this.location = geoLocation;
    }

    @Override
    public String getSex() {
        return this.sex;
    }


    @Override
    public String toString() {
        return "Visitor{" +
                "dateOfBirth=" + dateOfBirth +
                ", name='" + name + '\'' +
                ", location=" + location +
                ", sex='" + sex + '\'' +
                '}';
    }
}
