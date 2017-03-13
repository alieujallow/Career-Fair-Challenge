/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * This class holds information about an interest point.
 * it holds the latitude, longitude, radius and id of an interest point. 
 * @author Alieu Jallow
 */
class InterestPoint {

    //instance variables
    private double longitude;
    private double latitude;
    private double radius;
    private int id;

    /**
     * constructor
     *
     * @param i trip id
     * @param lati latitude
     * @param longi longitude
     * @param ra radius
     */
    public InterestPoint(int i, double lati, double longi, double ra) {
        id = i;
        latitude = lati;
        longitude = longi;
        radius = ra;
    }

    //GETTER METHODS
    /**
     * @return returns the longitude
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @return returns the latitude
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return returns the radius
     */
    public double getRadius() {
        return radius;
    }

    /**
     * @return returns the id
     */
    public int getId() {
        return id;
    }

}
