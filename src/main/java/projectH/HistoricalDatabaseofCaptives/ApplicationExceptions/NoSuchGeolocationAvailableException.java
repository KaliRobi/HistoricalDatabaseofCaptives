package projectH.HistoricalDatabaseofCaptives.ApplicationExceptions;

public class NoSuchGeolocationAvailableException extends Exception{
    public NoSuchGeolocationAvailableException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
