package projectH.HistoricalDatabaseofCaptives.GISData;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface IGeolocator {

    public void getCityData() throws InterruptedException, ExecutionException, IOException;



}
