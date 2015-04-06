package com.smarttaxi.ui.component;

import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Iwan on 06.04.2015
 */

public class MarkersContainer {

    private Map<Integer, List<GoogleMapMarker>> markerLists = new HashMap<>();
    private int i = 1;


    public int addMarkers(List<GoogleMapMarker> mapMarkers) {
        markerLists.put(i++, mapMarkers);
        return i;
    }

    public int removeMarkers(int index) {
        markerLists.remove(i);
        return 0;
    }
}
