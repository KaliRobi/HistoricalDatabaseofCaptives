package projectH.HistoricalDatabaseofCaptives.Users;

import projectH.HistoricalDatabaseofCaptives.GISData.GeoLocation;

import java.time.Instant;

public interface IPerson {

    public Instant getDateOfBirth();


    public int getAge();

    public String getName();
    public void setName(String name);

    public GeoLocation getLocation();
    public void setLocation(GeoLocation geoLocation );


    public String getSex();

}
