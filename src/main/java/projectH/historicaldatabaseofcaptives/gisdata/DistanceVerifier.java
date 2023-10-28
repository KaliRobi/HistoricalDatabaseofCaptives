package projectH.historicaldatabaseofcaptives.gisdata;

import org.springframework.stereotype.Component;
import projectH.historicaldatabaseofcaptives.applicationexceptions.GeolocationAttributeMissing;

import java.util.*;

/**
 * GeologicalOperations/Bulk are not so smart at the moment to tell if it actually matched  the right data.
 * In some cases like the Hungarian town Kaba , Kabul is pulled because it is listed on the top.
 * Because of historical reasons there is no way to limit these methods to find the entry where Hungary is the current country because many
 * of the locations are not on Hu territories anymore.
 *
 * The class will audit the distances between Budapest and the returned data and will add the outstanding location name to a new table
 *
 * the whole Spherical math is well described here although at this moment in this small section of the earth I am not sure how large the difference will be if
 * calculations are done via normal geometry
 * http://www.movable-type.co.uk/scripts/latlong.html
 *
 * Even if the class might look a bit overkill for now, it is better to use an approach which provides us a more fundamental base for further features
 *
 *
 */

@Component
public class DistanceVerifier {

    private final GeoServices geoServices;
    private final WithOrWithoutCoordinates withOrWithoutCoordinates;

    public DistanceVerifier(GeoServices geoServices, WithOrWithoutCoordinates withOrWithoutCoordinates) {
        this.geoServices = geoServices;
        this.withOrWithoutCoordinates = withOrWithoutCoordinates;
    }


    protected boolean isOutsideGreatHungarianRectangle(GeoLocation location ) {
        // rough estimation of the edges of Great Hungary
        // should be added to the database and queried from there.
        GeoLocation eastPoint = new GeoLocation("East", "East", 47.497891, 26.623009,  null);
        GeoLocation northPoint = new GeoLocation("North", "North", 50.125262,  19.040160, null);
        GeoLocation southPoint = new GeoLocation("South", "South", 44.545660, 19.040160,  null);
        GeoLocation westPoint = new GeoLocation("West", "West",47.497891,  13.244427,  null);
        GeoLocation budapest = geoServices.getALocationByName("Budapest");
        // x/y axes vectors
        Vector eastVector = vectorBetweenTwoPointsInVector(budapest, eastPoint);
        Vector westVector = vectorBetweenTwoPointsInVector(budapest, westPoint);

        GeoLocation northEastPoint = shiftLocationOnX(northPoint, eastVector);
        GeoLocation southWestPoint = shiftLocationOnX(southPoint, westVector);


        Map<String,Vector> baseVectors =  provideBaseVectorForTriangle(northEastPoint, southWestPoint, location);

        double northEastAngle = calculateAngleBetweenVectors(baseVectors.get("nEToLocationVector"), baseVectors.get("nEToSWVector") );
        double southEastAngle = calculateAngleBetweenVectors(baseVectors.get("sEToLocationVector"), baseVectors.get("sEToSWVector") );

        return (northEastAngle + southEastAngle) >= 90;

    }

    // could take only the location but that would limit flexibility
    private Map<String,Vector> provideBaseVectorForTriangle(GeoLocation northEastPoint, GeoLocation southWestPoint, GeoLocation location ){
        Map<String,Vector> vectors = new HashMap<>();
        vectors.put("nEToLocationVector", vectorBetweenTwoPointsInVector(northEastPoint, location ) );
        vectors.put("nEToSWVector", vectorBetweenTwoPointsInVector(northEastPoint, southWestPoint ));
        vectors.put("sEToLocationVector", vectorBetweenTwoPointsInVector(southWestPoint, location ));
        vectors.put("sEToSWVector", vectorBetweenTwoPointsInVector(southWestPoint, northEastPoint ));
        return vectors;
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

    private Vector vectorBetweenTwoPointsInCoordinates(GeoLocation geoFrom, GeoLocation geoTo ) throws GeolocationAttributeMissing {
        // this gives the vector applied on the base location so it fits into context
        double x = geoFrom.getLatitude() + (geoTo.getLatitude() - geoFrom.getLatitude() );
        double y = geoFrom.getLongitude() + (geoTo.getLongitude() - geoFrom.getLongitude());
        return new Vector(x, y);

    }
    private Vector vectorBetweenTwoPointsInVector(GeoLocation geoFrom, GeoLocation geoTo ) {
        // this gives the vector applied on the base location so it fits into context
        try {
            double x = geoTo.getLatitude() - geoFrom.getLatitude();
            double y = geoTo.getLongitude() - geoFrom.getLongitude();
            return new Vector(x, y);
        } catch (NullPointerException e){
            //should point to the concrete missing attribute
            if(geoFrom.getLongitude() == null || geoFrom.getLatitude() == null){
                throw  new GeolocationAttributeMissing("Geolocation (from) with coordinates " + geoFrom.getLongitude() + "/" + geoFrom.getLatitude()+ " is not allowed");
            }
            throw  new GeolocationAttributeMissing("Geolocation (to) with coordinates " + geoTo.getLongitude() + "/" + geoTo.getLatitude()+ " is not allowed");


        }

    }
    //this needs to be based on something
    private GeoLocation geolocationFromVectors(Vector vectorOnX, Vector vectorOnY){
        return new GeoLocation( vectorOnX.getX() ,  vectorOnY.getY());
    }

    private GeoLocation shiftLocationOnX(GeoLocation baseLocation, Vector relativeVector){
        return new GeoLocation(baseLocation.getLongitude(), baseLocation.getLatitude() + relativeVector.getX()  );
    }

    private Vector vectorAddition(Vector vecFrom, Vector vecTo){
        return new Vector(vecFrom.getX() + vecTo.getX(), vecFrom.getY() + vecTo.getY());
    }

    private double calculateLocationToLocationDistance(GeoLocation locationA, GeoLocation locationB) {
        /*
        convert to Radian is  degree * 3.1415926535 / 180;
         */

        double latA = Math.toRadians(locationB.getLatitude());
        double latB = Math.toRadians(locationB.getLatitude());
        double difLat = Math.toRadians(locationB.getLatitude() - locationA.getLatitude());
        double difLon = Math.toRadians(locationB.getLongitude() - locationA.getLongitude()) ;
//      Haversine formula
//         based on https://community.esri.com/t5/coordinate-reference-systems-blog/distance-on-a-sphere-the-haversine-formula/ba-p/902128
//         https://www.vcalc.com/wiki/vCalc/Haversine+-+Distance

        double atan2Base = Math.pow(Math.sin(difLat / 2),  2) + Math.cos(latA) * Math.cos(latB) * Math.pow(Math.sin(difLon / 2) ,2);

        double distanceInKm =  (Math.atan2(Math.sqrt(atan2Base), Math.sqrt(1 - atan2Base))) * 2 * 6371000 / 1000 ;
        return   distanceInKm;
    }


    //    Test method , could be useful later, probably better to return something like {geoLocation.getSource_name = {"locationName"="Debrecen", "Distance"=111}
    private Set<String> returnDistanceBetweenLocations(GeoLocation geoLocation) {
//        base
        GeoLocation x = geoServices.getALocationByName(geoLocation.getSourceName());
        Set<String> resultedDistanceString = new HashSet<>();

        withOrWithoutCoordinates.getLocationsWithCoordinates().forEach(e-> resultedDistanceString.add("The distance between " + e + " and " + geoLocation.getSourceName() + "is about " +
                calculateLocationToLocationDistance( x, geoServices.getALocationByName(e) ) + " km"));
        return resultedDistanceString;
    }
}
