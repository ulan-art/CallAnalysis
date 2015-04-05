package com.smarttaxi.spatial;

import com.smarttaxi.data.domain.Spot;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * Created by Iwan on 05.04.2015
 */

@Service
public class CoordinatesService {

//    Latitude:     1 deg=110.54km
//    Longitude:    1 deg=111.320*cos(latitude)km

    public static final double LAT_DEGREE_COEFFICIENT = 110.54;
    public static final double LON_DEGREE_COEFFICIENT = 111.320;
    public static final double DEGREES = 360;

    private Random random = new Random();


    private double getLatDegree(double km) {
        return km / LAT_DEGREE_COEFFICIENT;
    }

    private double getLonDegree(double km, double lat) {
        return km / (LON_DEGREE_COEFFICIENT * Math.cos(lat));
    }


    public Point getRandomPoint(double lat, double lon, double r) {
        Point c = new Point();
        c.setLat(lat);
        c.setLon(lon);
        return getRandomPoint(c, r);
    }

    public Point getRandomPoint(Point c, double r) {
        // r = 1 * sigma ~ 0.683
        // r = 2 * sigma ~ 0.954
        // r = 3 * sigma ~ 0.997

        double rs = random.nextGaussian() * r;
        double g = random.nextDouble() * DEGREES;

        double x = rs * Math.cos(g);
        double y = rs * Math.sin(g);

        Point point = new Point();

        point.setLat(c.getLat() + getLatDegree(y));
        double midLat = (c.getLat() + point.getLat()) / 2;
        point.setLon(c.getLon() + getLonDegree(x, midLat));

        return point;
    }


    public boolean isBelow(Point startPoint, Point endPoint, Point point) {
        Point p1;
        Point p2;

        if (startPoint.getLon() < endPoint.getLon()) {
            p1 = startPoint;
            p2 = endPoint;
        } else {
            p1 = endPoint;
            p2 = startPoint;
        }

        double k = (p2.getLat() - p1.getLat()) / (p2.getLon() - p1.getLon());
        double b = p1.getLat() - p1.getLon() * k;

        return k * point.getLon() + b > point.getLat();
    }

    public boolean isBelow(Spot startPoint, Spot endPoint, Spot point) {
        return isBelow(getPoint(startPoint), getPoint(endPoint), getPoint(point));
    }


    private Point getPoint(Spot spot) {
        return new Point(spot.getLat(), spot.getLon());
    }
}
