package projectH.historicaldatabaseofcaptives.gisdata;
import org.springframework.stereotype.Component;
import java.util.Set;
import java.util.stream.Collectors;

/** This utility uses the DistanceVerifier and sends the data to OutstandingGeolocationRepository
 *
 *filters out candidates by mismatching names then using the triangle method to check the location
 *Also important to loop thought only the list of locations from the last period of time,
 *
 */
@Component
public class DistanceVerifierUtility {

    private final OutstandingGeolocationRepository outstandingGeolocationRepository;
    private final DistanceVerifier distanceVerifier;

    private  final GeologicalRepository geologicalRepository;

    public DistanceVerifierUtility(OutstandingGeolocationRepository outstandingGeolocationRepository, DistanceVerifier distanceVerifier, GeologicalRepository geologicalRepository) {
        this.outstandingGeolocationRepository = outstandingGeolocationRepository;
        this.distanceVerifier = distanceVerifier;
        this.geologicalRepository = geologicalRepository;
    }


    public Set<GeoLocation> inspectLocationsForOutstandings(){
         Set<GeoLocation> locationsWithDifferentNames = separateLocationsWithDifferentNames();
//        isOutsideGreatHungarianRectangle == true
        locationsWithDifferentNames.stream()
                .filter(distanceVerifier::isOutsideGreatHungarianRectangle)
                .toList()
                .forEach(this::setAsOutstanding);

        return locationsWithDifferentNames;
    }

//    insert_time >= sysdate-1 / 7 depends on the setup  wil be done with a @Scheduler

//    will check for candidates if the source_name !equals osv_name because of the difference in hungarian and surrounding nations.
    private Set<GeoLocation> separateLocationsWithDifferentNames(){
        return  geologicalRepository.findAll().stream().filter( e -> !e.getSource_name().equalsIgnoreCase(e.getOsv_name()))
                .collect(Collectors.toSet());
    }

    private void setAsOutstanding(GeoLocation oS){
        OutstandingGeolocation loc = OutstandingGeolocation.fromGeolocation(oS);
        outstandingGeolocationRepository.save(loc);
    }

    // to avoid duplication
    private boolean isNotRepeatedCandidate (GeoLocation location) {
       Set<Long> existingIds = outstandingGeolocationRepository.findAll().stream().map(OutstandingGeolocation::getGeological_location_id).collect(Collectors.toSet());
      return !existingIds.contains(location.getId());
    }


}
