package com.smarttaxi.ui.component;

import com.smarttaxi.config.Application;
import com.smarttaxi.data.dao.CallDao;
import com.smarttaxi.data.dao.DistrictDao;
import com.smarttaxi.data.domain.Call;
import com.smarttaxi.data.domain.District;
import com.smarttaxi.demo.ColorService;
import com.smarttaxi.spatial.Point;
import com.smarttaxi.spatial.PointConverter;
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

    private PointConverter pointConverter = Application.getBean(PointConverter.class);

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


    private List<GoogleMapMarker> showSpots(List<District> districtList, boolean visible) {
        if (!visible) {
            List<GoogleMapMarker> markerList = new ArrayList<>(districtList.size());
            for (District district : districtList) {
                GoogleMapMarker marker = new GoogleMapMarker(
                        district.getNotes() + "\n" +
                                "Cluster: " + district.getCluster(),
                        new LatLon(
                                district.getLat(),
                                district.getLon()),
                        false, colorService.getSmallPointerUrl(district.getCluster()));
                markerList.add(marker);
                googleMap.addMarker(marker);
            }
            return markerList;
        }
        return new LinkedList<>();
    }

    private List<GoogleMapMarker> showCalls(List<Call> callList, boolean visible) {
        if (!visible) {
            List<GoogleMapMarker> markerList = new ArrayList<>(callList.size());
            for (Call call : callList) {
                GoogleMapMarker marker = new GoogleMapMarker(
                        call.getNotes() + "\n" +
                                "Cluster: " + call.getCluster(),
                        new LatLon(
                                call.getLat(),
                                call.getLon()),
                        false, colorService.getSmallPointerUrl(call.getCluster()));
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


    // TODO fix
    public void showCalls() {
        callMarkers = showCalls(callDao.getCallList(), false);
    }

    // TODO fix
    public void hideCalls() {
        deleteMarkers(callMarkers);
    }

    // TODO fix
    public void showCentres() {
        weightCentreMarkers = showSpots(districtDao.getWeightCentres(), false);
    }

    // TODO fix
    public void hideCentres() {
        deleteMarkers(weightCentreMarkers);
    }

    public void showPolygon(int cluster) {
        List<Call> someGroupCalls = callDao.getCallList(cluster);
        List<Point> pointList = pointConverter.getPointList(someGroupCalls);
        googleMap.addPolygonOverlay(polygonService.getPolygon(pointList, cluster));
    }
}
