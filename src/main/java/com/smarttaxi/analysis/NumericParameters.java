package com.smarttaxi.analysis;

import com.smarttaxi.spatial.Point;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Iwan on 19.04.2015
 */

@Service
public class NumericParameters {

    public Point mean(List<Point> points) {
        if (!CollectionUtils.isEmpty(points)) {
            double latSum = 0;
            double lonSum = 0;
            for (Point p : points) {
                latSum += p.getLat();
                lonSum += p.getLon();
            }
            int n = points.size();
            Point mean = new Point();
            mean.setLat(latSum / n);
            mean.setLon(lonSum / n);
            return mean;
        }
        return null;
    }

    public double variance(List<Point> points, Point mean) {
        if (!CollectionUtils.isEmpty(points) && (mean != null)) {

            double s = 0;
            for (Point point : points) {
                double d = mean.getDistance(point);
                s += d * d;
            }
            int n = points.size();
            return s / n;
        }
        return Double.NaN;
    }
}
