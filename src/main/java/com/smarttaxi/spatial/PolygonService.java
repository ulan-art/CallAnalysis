package com.smarttaxi.spatial;

import com.smarttaxi.demo.ColorService;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Iwan on 05.04.2015
 */

@Service
public class PolygonService {

    @Autowired
    private CoordinatesService coordinatesService;

    @Autowired
    private ColorService colorService;

    // TODO review ? refactor
    public GoogleMapPolygon getPolygon(List<Point> pointList, int cluster) {
        if (pointList.size() < 3) {
            return null;
        }

        PolygonStack pointsStack = new PolygonStack();

        List<Point> horizontallySorted = new ArrayList<>(pointList);
        horizontallySorted.sort(new PointLonComparator());

        pointsStack.push(horizontallySorted.get(0));
        pointsStack.push(horizontallySorted.get(1));

        for (int i = 2; i < horizontallySorted.size(); i++) {
            Point nextPoint = horizontallySorted.get(i);

            while (pointsStack.size() >= 2 &&
                    coordinatesService.isBelow(
                            pointsStack.getPreLast(), nextPoint, pointsStack.getLast())) {
                pointsStack.pop();
            }

            pointsStack.push(nextPoint);
        }

        pointsStack.push(horizontallySorted.get(horizontallySorted.size() - 2));

        int stackSize = pointsStack.size();

        for (int i = horizontallySorted.size() - 3; i >= 0; i--) {
            Point nextPoint = horizontallySorted.get(i);

            while (pointsStack.size() >= stackSize &&
                    !coordinatesService.isBelow(
                            pointsStack.getPreLast(), nextPoint, pointsStack.getLast())) {
                pointsStack.pop();
            }

            if (i != 0) {
                pointsStack.push(nextPoint);
            }
        }

        List<LatLon> latLonList = new ArrayList<>(pointsStack.asList().size());

        for (Point point : pointsStack.asList()) {
            latLonList.add(new LatLon(point.getLat(), point.getLon()));
        }

        String fillColor = colorService.getColor(cluster);

        return new GoogleMapPolygon(latLonList,
                fillColor, 0.8, "#194915", 0.5, 3);
    }


    private class PointLonComparator implements Comparator<Point> {

        @Override
        public int compare(Point point1, Point point2) {
            if (point1.getLon() > point2.getLon()) {
                return 1;
            }
            if (point1.getLon() < point2.getLon()) {
                return -1;
            }
            return 0;
        }
    }
}
