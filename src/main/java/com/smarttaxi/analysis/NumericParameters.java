package com.smarttaxi.analysis;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Iwan on 19.04.2015
 */

@Service
public class NumericParameters {

    public static double getDistance(Entity entity1, Entity entity2) {
        return Math.sqrt(
                Math.pow(entity1.getLat() - entity2.getLat(), 2) +
                        Math.pow(entity1.getLon() - entity2.getLon(), 2));
    }

    public static Entity getMean(List<Entity> list) {
        if (!CollectionUtils.isEmpty(list)) {
            double latSum = 0;
            double lonSum = 0;
            for (Entity entity : list) {
                latSum += entity.getLat();
                lonSum += entity.getLon();
            }
            int n = list.size();
            Entity mean = new Entity();
            mean.setLat(latSum / n);
            mean.setLon(lonSum / n);
            return mean;
        }
        return null;
    }

    public static double empiricalVariance(List<Entity> list) {
        if (!CollectionUtils.isEmpty(list)) {
            Entity mean = getMean(list);
            double s = 0;
            for (Entity entity : list) {
                double d = getDistance(entity, mean);
                s += d * d;
            }
            int n = list.size();
            return s / n;
        }
        return Double.NaN;
    }
}
