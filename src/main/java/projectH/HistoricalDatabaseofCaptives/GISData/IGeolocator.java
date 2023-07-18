package projectH.HistoricalDatabaseofCaptives.GISData;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface IGeolocator {

    public void getCityData() throws InterruptedException, ExecutionException, IOException;



}
