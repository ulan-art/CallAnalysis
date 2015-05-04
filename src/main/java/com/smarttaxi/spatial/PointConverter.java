package com.smarttaxi.spatial;

import com.smarttaxi.data.domain.Call;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iwan on 04.05.2015
 */

@Service
public class PointConverter {

    public List<Point> getPointList(List<Call> callList) {
        List<Point> pointList = new ArrayList<>(callList.size());
        for (Call call : callList) {
            pointList.add(getPoint(call));
        }
        return pointList;
    }

    private Point getPoint(Call call) {
        Point point = new Point();
        point.setLat(call.getLat());
        point.setLon(call.getLon());
        return point;
    }
}
