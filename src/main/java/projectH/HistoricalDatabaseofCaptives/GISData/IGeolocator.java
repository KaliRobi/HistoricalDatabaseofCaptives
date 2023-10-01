package projectH.HistoricalDatabaseofCaptives.GISData;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface IGeolocator {

    void getLocationData(Set<String> targetTownSet) throws InterruptedException, ExecutionException, IOException;


    //    Application interface with openStreetView, what also send the retrieved coordinates to the  database.
    //    This version of the class should be used for normal operations as supposed to be faster than the bulk version.

}
