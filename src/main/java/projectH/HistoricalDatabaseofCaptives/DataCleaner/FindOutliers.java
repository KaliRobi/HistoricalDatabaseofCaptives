package projectH.HistoricalDatabaseofCaptives.DataCleaner;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/* Pretty basic but very useful statistical way to find wrongly entered data candidates
 */

@Component
public class FindOutliers {

    public ArrayList<Integer> findOuters(ArrayList<Integer> list){
        int listSize =  list.size();
        List<Integer> sortedList =  list.stream().sorted().collect(Collectors.toList());
//         Exclusive method to find outliers for even sized collections
         if(listSize % 2 == 0 ) {
            List<Integer> lowerHalf = sortedList.subList(0, listSize/2 );
            List<Integer> upperHalf = sortedList.subList(listSize/2 , listSize);
            double firstQuarter = lowerHalf.get(lowerHalf.size() / 2);
            double thirdQuarter = upperHalf.get(upperHalf.size() / 2);
            double innerQuarterRange = thirdQuarter - firstQuarter;
            double lowerLimit = firstQuarter - innerQuarterRange * 1.5;
            double upperLimit = thirdQuarter + innerQuarterRange * 1.5;
             return   sortedList.stream().filter(e -> !(e < upperLimit && e > lowerLimit) ).distinct().collect(Collectors.toCollection(ArrayList::new));
        }
//         inclusive method for the odd sized collections
        List<Integer> lowerHalf = sortedList.subList(0, listSize / 2 +1 );
        List<Integer> upperHalf = sortedList.subList(listSize / 2 , listSize);
        double firstQuarter =  lowerHalf.size() % 2 == 0 ?  lowerHalf.get(lowerHalf.size() / 2 ) - 0.5 : lowerHalf.get(lowerHalf.size() / 2 ) ;
        double thirdQuarter =   upperHalf.size() % 2 == 0 ? upperHalf.get(upperHalf.size() / 2) - 0.5 : upperHalf.get(upperHalf.size() / 2) ;
        double innerQuarterRange = thirdQuarter - firstQuarter;
        double lowerLimit = (firstQuarter - 0.5) - innerQuarterRange * 1.5;
        double upperLimit = (thirdQuarter -0.5) + innerQuarterRange * 1.5;
        return   sortedList.stream().filter(e -> !(e < upperLimit && e > lowerLimit) ).distinct().collect(Collectors.toCollection(ArrayList::new));
    }
}



//                     System.out.println("listsize : " + listSize);
//                     System.out.println("lowerLimit : " + lowerLimit);
//                     System.out.println("upperLimit : " + upperLimit);
//                     System.out.println("firstQuarter : " + firstQuarter);
//                     System.out.println("thirdQuarter : " + thirdQuarter);
//                     System.out.println("median :"  + median);
//                     System.out.println("innerQuarterRange :" + innerQuarterRange);
