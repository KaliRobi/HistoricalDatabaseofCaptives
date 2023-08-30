package projectH.HistoricalDatabaseofCaptives.CaptivesDataTest;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Test;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.ArgumentsSources;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import projectH.HistoricalDatabaseofCaptives.CaptivesData.CaptiveServices;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@RunWith(Parameterized.class)
public class CaptiveServicesTest {
    @Autowired
    CaptiveServices captiveServices;

    String actual;
    long id;


    public CaptiveServicesTest(long id, String actual){
        this.actual = actual;
        this.id = id;
    }



    @Parameterized.Parameters
    public static List<String> testConditions(){
        String[] expected =
                {"Balkus Mihály", "Cirják Róza Szabó Gáborné", "Bakó Zsófia", "Lakatos János"};

        return Arrays.asList(expected);
    }

    @Parameterized.Parameters
    public static List<long[]> testConditions_2(){
        long[] expected =
                {17, 55, 59, 2373};

        return Arrays.asList(expected);
    }
//

    @ParameterizedTest
    public void captiveServiceConstructorTest(){

        assertEquals(captiveServices.GetCaptiveById(id).get().getName(), actual  );
//        assertEquals(String.valueOf(captiveServices.GetCaptiveById(17l).get().getHeight()), 165 );



    }


}
