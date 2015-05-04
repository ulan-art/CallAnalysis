package com.smarttaxi.analysis;

import com.smarttaxi.data.domain.Call;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Iwan on 19.04.2015
 */

@Service
public class NumericParameters {

    public static double getDistance(Call call1, Call call2) {
        return Math.sqrt(
                Math.pow(call1.getLat() - call2.getLat(), 2) +
                        Math.pow(call1.getLon() - call2.getLon(), 2));
    }

    public static Call getMean(List<Call> callList) {
        if (!CollectionUtils.isEmpty(callList)) {
            double latSum = 0;
            double lonSum = 0;
            for (Call call : callList) {
                latSum += call.getLat();
                lonSum += call.getLon();
            }
            int n = callList.size();
            Call mean = new Call();
            mean.setLat(latSum / n);
            mean.setLon(lonSum / n);
            return mean;
        }
        return null;
    }

    public static double empiricalVariance(List<Call> callsList) {
        if (!CollectionUtils.isEmpty(callsList)) {
            Call mean = getMean(callsList);
            double s = 0;
            for (Call call : callsList) {
                double d = getDistance(call, mean);
                s += d * d;
            }
            int n = callsList.size();
            return s / n;
        }
        return Double.NaN;
    }
}
