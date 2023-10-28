package projectH.historicaldatabaseofcaptives.users;

import projectH.historicaldatabaseofcaptives.gisdata.GeoLocation;
import java.time.LocalDate;

public interface IPerson {

    LocalDate getDateOfBirth();

    int getAge();

    String getName();
    void setName(String name);

    GeoLocation getLocation();
    void setLocation(GeoLocation geoLocation );

    String getSex();

}
