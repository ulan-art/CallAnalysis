package com.smarttaxi.ui.page;

import com.smarttaxi.config.Application;
import com.smarttaxi.data.dao.DistrictDao;
import com.smarttaxi.data.domain.District;
import com.smarttaxi.ui.component.MapPanel;
import com.smarttaxi.ui.component.MethodsMenu;
import com.smarttaxi.ui.component.NavigationMenu;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Iwan on 15.03.2015
 */

public class RigaMapPage extends CustomComponent implements View {

    private MapPanel mapPanel;


    public RigaMapPage() {

        HorizontalLayout pageLayout = new HorizontalLayout();

        DistrictDao districtDao = Application.getBean(DistrictDao.class);
        District cityCentre = districtDao.getCityCentre();

        mapPanel = new MapPanel();
        if (cityCentre != null) {
            mapPanel.setCityCentre(cityCentre);
        } else {
            mapPanel.setCityCentre();
        }

        VerticalLayout menuLayout = new VerticalLayout();
        menuLayout.setSpacing(true);
        menuLayout.setMargin(true);

        menuLayout.addComponent(new ShowCallsButton());
        menuLayout.addComponent(new HideCallsButton());
        menuLayout.addComponent(new ShowCentresButton());
        menuLayout.addComponent(new HideCentresButton());
        menuLayout.addComponent(new ShowPolygonButton());

        pageLayout.addComponent(new NavigationMenu());
        pageLayout.addComponent(mapPanel);
        pageLayout.addComponent(menuLayout);
        pageLayout.addComponent(new MethodsMenu());

        setCompositionRoot(pageLayout);
    }


    public class ShowCallsButton extends Button {

        public ShowCallsButton() {
            setCaption("Show calls");
            addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent clickEvent) {
                    mapPanel.showCalls();
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
                    mapPanel.hideCalls();
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
                    mapPanel.showCentres();
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
                    mapPanel.hideCentres();
                }
            });
        }
    }

    public class ShowPolygonButton extends Button {

        public ShowPolygonButton() {
            setCaption("Show polygons");
            addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent clickEvent) {
                    for (int i = 0; i < 8; i++) {
                        mapPanel.showPolygon(i);
                    }
                }
            });
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
