package projectH.historicaldatabaseofcaptives.applicationexceptions;

import java.util.NoSuchElementException;

public class NoSuchCaptiveIdFound extends NoSuchElementException{
    public NoSuchCaptiveIdFound(long s)  {
        super(String.valueOf(s));
    }
}
