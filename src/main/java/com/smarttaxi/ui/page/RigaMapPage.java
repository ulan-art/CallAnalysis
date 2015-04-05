package com.smarttaxi.ui.page;

import com.smarttaxi.config.Application;
import com.smarttaxi.data.dao.CallDao;
import com.smarttaxi.data.dao.DistrictDao;
import com.smarttaxi.data.domain.District;
import com.smarttaxi.data.domain.Spot;
import com.smarttaxi.test.MarkerIconService;
import com.smarttaxi.ui.component.NavigationMenu;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.tapio.googlemaps.GoogleMap;
import com.vaadin.tapio.googlemaps.client.LatLon;
import com.vaadin.tapio.googlemaps.client.overlays.GoogleMapMarker;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Iwan on 15.03.2015
 */

public class RigaMapPage extends CustomComponent implements View {

    private CallDao callDao = Application.getBean(CallDao.class);
    private DistrictDao districtDao = Application.getBean(DistrictDao.class);
    private MarkerIconService markerIconService = Application.getBean(MarkerIconService.class);

    private GoogleMap googleMap;
    private GoogleMapMarker cityCentreMarker;
    private List<GoogleMapMarker> weightCentreMarkers;
    private List<GoogleMapMarker> callMarkers;

    private boolean weightCentresVisible = false;
    private boolean callsVisible = false;

    public RigaMapPage() {

        HorizontalLayout pageLayout = new HorizontalLayout();

        VerticalLayout mapLayout = new VerticalLayout();
        mapLayout.setSpacing(true);
        mapLayout.setMargin(true);

        googleMap = new GoogleMap(null, null, null);
        googleMap.setWidth("800");
        googleMap.setHeight("580");

        DistrictDao districtDao = Application.getContext().getBean(DistrictDao.class);
        District cityCentre = districtDao.getCityCentre();
        setCityCentre(cityCentre);
        googleMap.setZoom(12);

        mapLayout.addComponent(googleMap);

        VerticalLayout menuLayout = new VerticalLayout();
        menuLayout.setSpacing(true);
        menuLayout.setMargin(true);

        menuLayout.addComponent(new ShowCallsButton());
        menuLayout.addComponent(new HideCallsButton());
        menuLayout.addComponent(new ShowCentresButton());
        menuLayout.addComponent(new HideCentresButton());

        pageLayout.addComponent(new NavigationMenu());
        pageLayout.addComponent(mapLayout);
        pageLayout.addComponent(menuLayout);

        setCompositionRoot(pageLayout);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }

    private void setCityCentre(District cityCentre) {
        if (cityCentre != null) {
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
        } else {
            googleMap.setCenter(new LatLon(56.9496487, 24.1051864));
        }
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
                        false, markerIconService.getSmallPointerUrl(spot.getGroup()));
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


    public class ShowCallsButton extends Button {

        public ShowCallsButton() {
            setCaption("Show calls");
            addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent clickEvent) {
                    callMarkers = showSpots(callDao.getCallList(), callsVisible);
                }
            });
        }
    }


    public class HideCallsButton extends Button {

        public HideCallsButton() {
            setCaption("Hide calls");
            addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent clickEvent) {
                    callsVisible = deleteMarkers(callMarkers);
                }
            });
        }
    }

    public class ShowCentresButton extends Button {

        public ShowCentresButton() {
            setCaption("Show centres");
            addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent clickEvent) {
                    weightCentreMarkers = showSpots(districtDao.getWeightCentres(), weightCentresVisible);
                }
            });
        }
    }


    public class HideCentresButton extends Button {

        public HideCentresButton() {
            setCaption("Hide centres");
            addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent clickEvent) {
                    weightCentresVisible = deleteMarkers(weightCentreMarkers);
                }
            });
        }
    }



}
