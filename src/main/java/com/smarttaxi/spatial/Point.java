package com.smarttaxi.spatial;

/**
 * Created by Iwan on 05.04.2015
 */

public class Point {

    private double lat;
    private double lon;


    public Point() {
    }

    public Point(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }


    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }


    public double getDistance(Point anotherPoint) {
        // TODO
        return 0;
    }
}
