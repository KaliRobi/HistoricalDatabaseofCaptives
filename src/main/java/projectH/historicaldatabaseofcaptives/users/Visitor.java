package projectH.historicaldatabaseofcaptives.users;

import projectH.historicaldatabaseofcaptives.gisdata.GeoLocation;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Visitor implements IPerson{
//  do I want this to be changed ever?
    private final LocalDate dateOfBirth;
    private String name;
    private GeoLocation location;
    private final String sex;

    public Visitor(String name, GeoLocation geoLocation, String sex, LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        this.name = name;
        this.location = geoLocation;
        this.sex = sex;

    }
    public Visitor(GeoLocation geoLocation, String sex, LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        this.name = "Anonymous";
        this.location = geoLocation;
        this.sex = sex;

    }




    @Override
    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }


    @Override
    public int getAge() {
        Calendar calendar =  new GregorianCalendar();

        return calendar.get(Calendar.YEAR)- this.dateOfBirth.get(ChronoField.YEAR);
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
