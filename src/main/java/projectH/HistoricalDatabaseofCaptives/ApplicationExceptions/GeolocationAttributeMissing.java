package projectH.HistoricalDatabaseofCaptives.ApplicationExceptions;

public class GeolocationAttributeMissing extends NullPointerException {

    public GeolocationAttributeMissing(String errorMessage) {
       super(errorMessage);
    }
}
