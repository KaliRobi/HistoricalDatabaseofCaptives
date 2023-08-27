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


//    will check for candidates if the source_name equals osv_name
//    isInGreatHungarianRectangle == true
//    insert_time >= sysdate-1 / 7 depends on the setup



    private void setAsOutstanding(OutstandingGeolocation oS){
        outstandingGeolocationRepository.save(oS);
    }


}
