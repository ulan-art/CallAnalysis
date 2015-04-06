package com.smarttaxi.ui.component;

import com.smarttaxi.config.Application;
import com.smarttaxi.data.dao.CallDao;
import com.smarttaxi.data.dao.DistrictDao;
import com.smarttaxi.data.domain.Call;
import com.smarttaxi.data.domain.District;
import com.smarttaxi.data.domain.Spot;
import com.smarttaxi.demo.ColorService;
import com.smarttaxi.spatial.PolygonService;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Iwan on 06.04.2015
 */

public class MapPanel extends CustomComponent {

    private CallDao callDao = Application.getBean(CallDao.class);
    private DistrictDao districtDao = Application.getBean(DistrictDao.class);

    private PolygonService polygonService = Application.getBean(PolygonService.class);
    private ColorService colorService = Application.getBean(ColorService.class);

    private GoogleMap googleMap;
    private GoogleMapMarker cityCentreMarker;

    private MarkersContainer markersContainer = new MarkersContainer();

    private List<GoogleMapMarker> weightCentreMarkers;
    private List<GoogleMapMarker> callMarkers;


    public MapPanel() {

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.setMargin(true);
        verticalLayout.setSpacing(true);

        googleMap = new GoogleMap(null, null, null);
        googleMap.setWidth("800");
        googleMap.setHeight("580");
        googleMap.setZoom(12); // TODO set proper zoom

        verticalLayout.addComponent(googleMap);
        setCompositionRoot(verticalLayout);
    }


    public void setCityCentre() {
        setCityCentre(new LatLon(56.9496487, 24.1051864));
    }

    public void setCityCentre(LatLon cityCentre) {
        googleMap.setCenter(cityCentre);
    }

    public void setCityCentre(District cityCentre) {
        googleMap.setCenter(new LatLon(
                cityCentre.getLat(), cityCentre.getLon()));
        if (cityCentreMarker != null) {
            googleMap.removeMarker(cityCentreMarker);
        }
        cityCentreMarker = new GoogleMapMarker(
                cityCentre.getName() + ",\ncity centre",
                new LatLon(
                        cityCentre.getLat(),
                        cityCentre.getLon()),
                false, null);
        googleMap.addMarker(cityCentreMarker);
    }


    private List<GoogleMapMarker> showSpots(List<? extends Spot> spotList, boolean visible) {
        if (!visible) {
            List<GoogleMapMarker> markerList = new ArrayList<>(spotList.size());
            for (Spot spot : spotList) {
                GoogleMapMarker marker = new GoogleMapMarker(
                        spot.getNotes(),
                        new LatLon(
                                spot.getLat(),
                                spot.getLon()),
                        false, colorService.getSmallPointerUrl(spot.getGroup()));
                markerList.add(marker);
                googleMap.addMarker(marker);
            }
            return markerList;
        }
        return new LinkedList<>();
    }

    private boolean deleteMarkers(List<GoogleMapMarker> markerList) {
        if (markerList != null) {
            for (GoogleMapMarker marker : markerList) {
                googleMap.removeMarker(marker);
            }
        }
        return false;
    }


    public void showCalls() {
        callMarkers = showSpots(callDao.getCallList(), false);
    }

    public void hideCalls() {
        deleteMarkers(callMarkers);
    }

    public void showCentres() {
        weightCentreMarkers = showSpots(districtDao.getWeightCentres(), false);
    }

    public void hideCentres() {
        deleteMarkers(weightCentreMarkers);
    }

    public void showPolygon(int cluster) {
        List<Call> someGroupCalls = callDao.getCallList(cluster);
        googleMap.addPolygonOverlay(polygonService.getPolygon(someGroupCalls));
    }
}
