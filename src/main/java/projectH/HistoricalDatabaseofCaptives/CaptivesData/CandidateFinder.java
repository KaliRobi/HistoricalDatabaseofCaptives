package projectH.HistoricalDatabaseofCaptives.CaptivesData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * One of the ways how this mostly statistical site could be brought closer to the visitors is to show the "face" of the captives
 * Unfortunately there are no images were taken, but someone's face is much more than his/her skull covered by tissues
 * in same cases showing the user a person in the same age, from the same location could probably make the interaction with the site more personal
 *
 * user will be able to enter location, age, sex
 * app will return someone from the db based on these attributes.
 *
 */

@Component
public class CandidateFinder {
    @Autowired
    private CaptiveServices captiveServices;

    public void returnCandidate(Instant yearOfBirth, String visitorLocation, String maleOrFemale){



// captive services will provide a search method
//
        Captive cap = new Captive();
        cap.setDate_of_birth(yearOfBirth);
        cap.setPlace_of_birth(visitorLocation);
        cap.setSex(maleOrFemale);
        cap.getNonNullAttributes();
        captiveServices.findACaptive(cap);

        System.out.println(cap.getNonNullAttributes());



    }


}
