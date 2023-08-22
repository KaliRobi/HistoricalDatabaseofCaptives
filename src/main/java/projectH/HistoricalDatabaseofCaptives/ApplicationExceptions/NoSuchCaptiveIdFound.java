package projectH.HistoricalDatabaseofCaptives.ApplicationExceptions;

import java.util.NoSuchElementException;

public class NoSuchCaptiveIdFound extends NoSuchElementException {
    public NoSuchCaptiveIdFound(String s, NoSuchCaptiveIdFound e)  {
        super(s, e);
    }
}
