package com.smarttaxi.spatial;

import com.smarttaxi.data.domain.Spot;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Stack;

/**
 * Created by Iwan on 05.04.2015
 */

@Service
public class PolygonService {

    @Autowired
    CoordinatesService coordinatesService;

    public GoogleMapPolygon getPolygon(List<Spot> spotList) {

        if (spotList.size() < 4) {
            return null;
        }

        Stack<Spot> pointsStack = new Stack<>();

        List<Spot> horizontallySorted = new ArrayList<>(spotList);
        List<Spot> verticallySorted = new ArrayList<>(spotList);

        horizontallySorted.sort(new SpotLonComparator());
        verticallySorted.sort(new SpotLatComparator());


        int i = 0;

        pointsStack.push(horizontallySorted.get(i++)); // 1
        pointsStack.push(horizontallySorted.get(i++)); // 2

        Spot stopPoint = verticallySorted.get(0); // top-stop

        while (horizontallySorted.get(i).getId() != stopPoint.getId()) {
            Spot preLastPoint = horizontallySorted.get(i - 2);
            Spot lastPoint = horizontallySorted.get(i - 1);
            Spot nextPoint = horizontallySorted.get(i);
            if (coordinatesService.isBelow(preLastPoint, nextPoint, lastPoint)) {
                pointsStack.pop();
            }
            pointsStack.push(nextPoint);
            i++;
        }
        pointsStack.push(horizontallySorted.get(i));

        List<LatLon> latLonList = new ArrayList<>(pointsStack.size());

        for (Spot spot : pointsStack) {
            latLonList.add(new LatLon(spot.getLat(), spot.getLon()));
        }

        GoogleMapPolygon polygon = new GoogleMapPolygon(latLonList,
                "#ae1f1f", 0.8, "#194915", 0.5, 3);

        return polygon;
    }


    private class SpotLatComparator implements Comparator<Spot> {

        @Override
        public int compare(Spot spot1, Spot spot2) {
            if (spot1.getLat() > spot2.getLat()) {
                return 1;
            }
            if (spot1.getLat() < spot2.getLat()) {
                return -1;
            }
            return 0;
        }
    }

    private class SpotLonComparator implements Comparator<Spot> {

        @Override
        public int compare(Spot spot1, Spot spot2) {
            if (spot1.getLon() > spot2.getLon()) {
                return 1;
            }
            if (spot1.getLon() < spot2.getLon()) {
                return -1;
            }
            return 0;
        }
    }
}
