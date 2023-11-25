package projectH.historicaldatabaseofcaptives.gisdata;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public interface IGeolocator {

    void getLocationData(Set<String> targetTownSet) throws InterruptedException, ExecutionException, IOException;

}
