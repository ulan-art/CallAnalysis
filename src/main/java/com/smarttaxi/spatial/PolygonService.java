package com.smarttaxi.spatial;

import com.smarttaxi.data.domain.Spot;
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

    // TODO refactor
    public GoogleMapPolygon getPolygon(List<? extends Spot> spotList, int cluster) {
        if (spotList.size() < 3) {
            return null;
        }

        PolygonStack pointsStack = new PolygonStack();

        List<Spot> horizontallySorted = new ArrayList<>(spotList);

        horizontallySorted.sort(new SpotLonComparator());

        int i = 0;

        pointsStack.push(horizontallySorted.get(i++)); // 1
        pointsStack.push(horizontallySorted.get(i++)); // 2

        Spot stopPoint = horizontallySorted.get(horizontallySorted.size() - 1); // right-stop

        while (horizontallySorted.get(i).getId() != stopPoint.getId()) {
            Spot nextPoint = horizontallySorted.get(i);

            while (pointsStack.size() >= 2 &&
                    coordinatesService.isBelow(
                            pointsStack.getPreLast(), nextPoint, pointsStack.getLast())) {
                pointsStack.pop();
            }

            pointsStack.push(nextPoint);
            i++;
        }
        pointsStack.push(stopPoint);

        i = horizontallySorted.size() - 2;
        pointsStack.push(horizontallySorted.get(i));

        int stackSize = pointsStack.size();

        stopPoint = horizontallySorted.get(0);

        while (horizontallySorted.get(i).getId() != stopPoint.getId()) {
            Spot nextPoint = horizontallySorted.get(i);

            while (pointsStack.size() >= stackSize &&
                    !coordinatesService.isBelow(
                            pointsStack.getPreLast(), nextPoint, pointsStack.getLast())) {
                pointsStack.pop();
            }

            pointsStack.push(nextPoint);
            i--;
        }

        List<LatLon> latLonList = new ArrayList<>(pointsStack.asList().size());

        for (Spot spot : pointsStack.asList()) {
            latLonList.add(new LatLon(spot.getLat(), spot.getLon()));
        }

        String fillColor = colorService.getColor(cluster);

        GoogleMapPolygon polygon = new GoogleMapPolygon(latLonList,
                fillColor, 0.8, "#194915", 0.5, 3);

        return polygon;
    }

    public GoogleMapPolygon getPolygon(List<? extends Spot> spotList) {
        return getPolygon(spotList, 0);
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
