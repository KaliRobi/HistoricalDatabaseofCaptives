package projectH.HistoricalDatabaseofCaptives.GISData;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * GeologicalOperations/Bulk are not so smart at the moment to tell if it actually matched  the right data.
 * In some cases like the Hungarian town Kaba , Kabul is pulled because it is listed on the top.
 * Because of historical reasons there is no way to limit these methods to find the entry where Hungary is the current country because many
 * of the locations are not on Hu territories anymore.
 *
 * The class will audit the distances between Budapest and the returned data and will add the outstanding location name to a new table
 *
 * the whole Spherical math is well described here although at this moment in this small section of the earth I am not sure how large the difference will be if
 * calculations are done via normal geometric way
 * http://www.movable-type.co.uk/scripts/latlong.html
 *
 * Even if the class might look a bit overkill for now, it is better to use an approach which provides us a more fundamental base for further features
 *
 *
 */

//TODO
    // add extra method to get the decision if the degree of the angle  is larger that 90.
    // save the location to ahe outstanding geolocations table if yes
    // run this method through the new entries in the geological_locations table, maybe insert_time column would be great for all table

@Component
public class DistanceVerifier {

    private final GeoServices geoServices;
    private final WithOrWithoutCoordinates withOrWithoutCoordinates;

    public DistanceVerifier(GeoServices geoServices, WithOrWithoutCoordinates withOrWithoutCoordinates) {
        this.geoServices = geoServices;
        this.withOrWithoutCoordinates = withOrWithoutCoordinates;
    }

    public void findOutstandingGeolocationCandidate(){
//        isInGreatHungarianRectangle();
    }
    private boolean isInGreatHungarianRectangle( GeoLocation location ) {
        // rough estimation of the edges of Great Hungary
        GeoLocation eastPoint = new GeoLocation("East", "East", 26.623009, 47.497891, null);
        GeoLocation northPoint = new GeoLocation("North", "North", 19.040160, 50.125262, null);
        GeoLocation southPoint = new GeoLocation("South", "South", 19.040160, 44.545660, null);
        GeoLocation westPoint = new GeoLocation("West", "West", 13.244427, 47.497891, null);
        GeoLocation Budapest = new GeoLocation("Budapest", "Budapest", 19.0401609, 47.4978918, "Hungary");
//        double distance = calculateLocationToLocationDistance(east, north);

        // x/y axes vectors
        Vector eastVector = vectorBetweenTwoPointsInVector(Budapest, eastPoint);
        Vector westVector = vectorBetweenTwoPointsInVector(Budapest, westPoint);
        GeoLocation northEastPoint = shiftLocationOnX(northPoint, eastVector);
        GeoLocation southWestPoint = shiftLocationOnX(southPoint, westVector);


        GeoLocation Debrecen = new GeoLocation(21.6259782, 47.531399);
        GeoLocation Bekescsaba = new GeoLocation(21.0985425, 46.6798003	);
        GeoLocation ttt = new GeoLocation(46.0148,	36.3206);
        GeoLocation Arad = new GeoLocation(21.3196342,	46.1753793);
        GeoLocation Brasso = new GeoLocation(25.6105654,	45.6525105);
        GeoLocation Brasso2 = new GeoLocation(28.84527583003865,	46.99769784507529);
        GeoLocation Kossice = new GeoLocation(21.271323360124264, 48.72849551597991 );
        GeoLocation Pozsony = new GeoLocation(17.07639933530841, 48.13908776119138 );


        // this needs to be get its own method
        Vector nEToLocationVector = vectorBetweenTwoPointsInVector(northEastPoint, ttt );

        Vector nEToSWVector = vectorBetweenTwoPointsInVector(northEastPoint, southWestPoint );

        Vector sEToLocationVector = vectorBetweenTwoPointsInVector(southWestPoint, ttt );

        Vector sEToSWVector = vectorBetweenTwoPointsInVector(southWestPoint, northEastPoint );


        double northEastAngle = calculateAngleBetweenVectors(nEToLocationVector, nEToSWVector );
        double southEastAngle = calculateAngleBetweenVectors(sEToLocationVector, sEToSWVector );
        System.out.println(northEastAngle + " ----- " + southEastAngle);
    return false;

    }
    private double calculateAngleBetweenVectors(Vector toLocation, Vector diagonalAxis){
        //magnitude of the vectors
        double nEToLocationMagnitude = Math.sqrt(Math.pow(toLocation.getX(),2) + Math.pow(toLocation.getY(), 2));
        double nEToSWMagnitude = Math.sqrt(Math.pow(diagonalAxis.getX(),2) + Math.pow(diagonalAxis.getY(), 2));

        // dotProduct
        double dotProductNO = (toLocation.getY() * diagonalAxis.getY()  +  toLocation.getX() * diagonalAxis.getX());
        //get the angle
        return Math.round(Math.toDegrees(Math.acos( dotProductNO / (nEToLocationMagnitude * nEToSWMagnitude) )));
    }

    private Vector vectorBetweenTwoPointsInCoordinates(GeoLocation geoFrom, GeoLocation geoTo ){
        // this gives the vector applied on the base location so it fits into context
        double x = geoFrom.getLatitude() + (geoTo.getLatitude() - geoFrom.getLatitude() );
        double y = geoFrom.getLongitude() + (geoTo.getLongitude() - geoFrom.getLongitude());
        return new Vector(x, y);

    }
    private Vector vectorBetweenTwoPointsInVector(GeoLocation geoFrom, GeoLocation geoTo ){
        // this gives the vector applied on the base location so it fits into context
        double x = geoTo.getLatitude() - geoFrom.getLatitude();
        double y = geoTo.getLongitude() - geoFrom.getLongitude();
        return new Vector(x, y);
    }
    //this needs to be based on something
    private GeoLocation geolocationFromVectors(Vector vectorOnX, Vector vectorOnY){
        return new GeoLocation( vectorOnX.getX() ,  vectorOnY.getY());
    }

    private GeoLocation shiftLocationOnX(GeoLocation baseLocation, Vector relativeVector){
        return new GeoLocation(baseLocation.getLatitude() + relativeVector.getX(), baseLocation.getLongitude() );
    }

    private Vector vectorAddition(Vector vecFrom, Vector vecTo){
        return new Vector(vecFrom.getX() + vecTo.getX(), vecFrom.getY() + vecTo.getY());
    }

    private double calculateLocationToLocationDistance(GeoLocation locationA, GeoLocation locationB) {
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


    //    Test method , could be useful later, probably better to return something like {geoLocation.getSource_name = {"locationName"="Debrecen", "Distance"=111}
    private Set<String> returnDistanceBetweenLocations(GeoLocation geoLocation) {
//        base
        GeoLocation x = geoServices.getALocationByName(geoLocation.getSource_name());
        Set<String> resultedDistanceString = new HashSet<>();

        withOrWithoutCoordinates.getLocationsWithCoordinates().forEach(e-> resultedDistanceString.add("The distance between " + e + " and " + geoLocation.getSource_name() + "is about " +
                calculateLocationToLocationDistance( x, geoServices.getALocationByName(e) ) + " km"));
        System.out.println(resultedDistanceString);
        return resultedDistanceString;
    }





}
