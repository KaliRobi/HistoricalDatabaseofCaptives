package projectH.historicaldatabaseofcaptives.applicationexceptions;

public class GeolocationAttributeMissing extends NullPointerException {
    public GeolocationAttributeMissing(String errorMessage) {
       super(errorMessage);
    }
}
