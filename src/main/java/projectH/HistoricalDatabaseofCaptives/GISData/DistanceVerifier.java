package projectH.HistoricalDatabaseofCaptives.GISData;

import java.util.HashSet;
import java.util.Set;

/**
 * GeologicalOperations/Bulk are not so smart at the moment to tell if it actually matched  the right data.
 * In some cases like the Hungarian town Kaba , Kabul is pulled because it is listed on the top.
 * Because of historical reasons there is no way to limit these methods to find the entry where Hungary is the current country because many
 * of the locations are not on Hu territories anymore.
 *
 * The class will audit the distances between Budapest and the returned data and will add the outstanding location name to a new table
 */


public class DistanceVerifier {

    GeoServices geoServices;

    private double calculateLocationToLocationDistance(GeoLocation locationA, GeoLocation locationB) {

//        GeoLocation locationA, GeoLocation locationB
//        convert to Radian is = degree * 3.1415926535 / 180;
        double latA = Math.toRadians(locationB.getLatitude());
        double latB = Math.toRadians(locationB.getLatitude());
        double difLat = Math.toRadians(locationB.getLatitude() - locationA.getLatitude());
        double difLon = Math.toRadians(locationB.getLongitude() - locationA.getLongitude()) ;
//      Haversine formula
//         based on https://community.esri.com/t5/coordinate-reference-systems-blog/distance-on-a-sphere-the-haversine-formula/ba-p/902128
//         https://www.vcalc.com/wiki/vCalc/Haversine+-+Distance

        double atan2Base = Math.pow(Math.sin(difLat / 2),  2) + Math.cos(latA) * Math.cos(latB) * Math.pow(Math.sin(difLon / 2) ,2);

        double distanceInKm =  (Math.atan2(Math.sqrt(atan2Base), Math.sqrt(1 - atan2Base))) * 2 * 6371000 / 1000 ;
        System.out.println(distanceInKm);
        return   distanceInKm;
    }


    //    Test method , could be useful later, probably better to return the something like {geoLocation.getSource_name = {"locationName"="Debrecen", "Distance"=111}
    private Set<String> returnDistanceBetweenLocations(GeoLocation geoLocation) {
//        base
        GeoLocation x = geoServices.getALocationByName(geoLocation.getSource_name());
        Set<String> resultedDistanceString = new HashSet<>();

        geoServices.getLocationsWithCoordinates().forEach(e-> resultedDistanceString.add("The distance between " + e + " and " + geoLocation.getSource_name() + "is about " +
                calculateLocationToLocationDistance( x, geoServices.getALocationByName(e) ) + " km"));
        System.out.println(resultedDistanceString);
        return resultedDistanceString;
    }



}
