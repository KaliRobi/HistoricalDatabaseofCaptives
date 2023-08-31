package projectH.HistoricalDatabaseofCaptives.CaptivesData;

import org.springframework.stereotype.Component;
import projectH.HistoricalDatabaseofCaptives.GISData.GeoServices;
import projectH.HistoricalDatabaseofCaptives.Users.Visitor;

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

    private final CaptiveServices captiveServices;
        public CandidateFinder(CaptiveServices captiveServices) {
        this.captiveServices = captiveServices;
    }



    public Captive returnCandidate(Visitor visitor){

       return captiveServices.findACaptive(visitor);

    }


}
