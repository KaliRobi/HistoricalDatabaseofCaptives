package projectH.HistoricalDatabaseofCaptives.GISData;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.concurrent.ExecutionException;

public interface IGeolocator {

    public void getCityData() throws InterruptedException, ExecutionException, JsonProcessingException;



}
