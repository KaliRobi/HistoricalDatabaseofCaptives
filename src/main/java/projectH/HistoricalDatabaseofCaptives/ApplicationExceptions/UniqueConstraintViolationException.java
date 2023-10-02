package projectH.HistoricalDatabaseofCaptives.ApplicationExceptions;

import java.sql.SQLException;

public class UniqueConstraintViolationException extends  RuntimeException {

    public UniqueConstraintViolationException(RuntimeException e){
        super(e);
    }


}
