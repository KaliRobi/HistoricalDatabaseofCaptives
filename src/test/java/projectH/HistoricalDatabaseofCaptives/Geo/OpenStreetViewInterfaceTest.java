package projectH.HistoricalDatabaseofCaptives.Geo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import projectH.HistoricalDatabaseofCaptives.GISData.OpenStreetViewInterface;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
@RunWith(SpringRunner.class)
public class OpenStreetViewInterfaceTest {
    OpenStreetViewInterface openStreetViewInterface = new OpenStreetViewInterface();

    @Test
    public void Get_Location_Data_Expected_Input_Test(){
        Set<String> testSet =  new HashSet<>();
        testSet.add("Debrecen");

        openStreetViewInterface.getLocationData(testSet);
    }
    @Test
    public void Get_Location_Data_NonExpected_Input_Test(){
        Set<String> testSet =  new HashSet<>();
        testSet.add("D3eb3r7e5c5en");
        openStreetViewInterface.getLocationData(testSet);
    }

    @Test(expected=NullPointerException.class)
    public void Get_Location_Data_Null_Input_Test(){
        Set<String> testSet =  new HashSet<>();
        openStreetViewInterface.getLocationData(testSet);
    }

    @Test
    public void Get_Location_Data_Multiple_Input_Test(){
        Set<String> testSet =  new HashSet<>();
        testSet.add("Debrecen");
        testSet.add("Miskolc");
        testSet.add("Budepest");
        openStreetViewInterface.getLocationData(testSet);
    }



}
