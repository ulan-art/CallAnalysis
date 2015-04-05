package com.smarttaxi.spatial;

import com.smarttaxi.data.domain.Spot;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapPolygon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Iwan on 05.04.2015
 */

public class Polygon {

    public GoogleMapPolygon getPolygon(List<Spot> spotList) {

        Stack<LatLon> points = new Stack<>();

        List<Spot> horizontallySorted = new ArrayList<>(spotList);
        List<Spot> verticallySorted = new ArrayList<>(spotList);

        horizontallySorted.sort(new SpotLonComparator());
        verticallySorted.sort(new SpotLatComparator());

        GoogleMapPolygon polygon = new GoogleMapPolygon(points,
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
