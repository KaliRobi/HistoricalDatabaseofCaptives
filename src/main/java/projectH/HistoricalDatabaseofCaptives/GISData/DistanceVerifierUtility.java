package projectH.HistoricalDatabaseofCaptives.GISData;

import org.springframework.stereotype.Component;

/** This utility uses the DistanceVerifier and sends the data to OutstandingGeolocationRepository
 *
 *
 * NEXT step, each Entity should get its insert_time
 */
@Component
public class DistanceVerifierUtility {

    private final OutstandingGeolocationRepository outstandingGeolocationRepository;
    private final DistanceVerifier distanceVerifier;

    public DistanceVerifierUtility(OutstandingGeolocationRepository outstandingGeolocationRepository, DistanceVerifier distanceVerifier) {
        this.outstandingGeolocationRepository = outstandingGeolocationRepository;
        this.distanceVerifier = distanceVerifier;
    }






    private void setAsOutstanding(OutstandingGeolocation oS){
        outstandingGeolocationRepository.save(oS);
    }


}
