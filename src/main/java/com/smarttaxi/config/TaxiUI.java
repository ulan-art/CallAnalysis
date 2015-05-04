package com.smarttaxi.config;

import com.smarttaxi.ui.page.CallsTablePage;
import com.smarttaxi.ui.page.LogsTablePage;
import com.smarttaxi.ui.page.SpotsTablePage;
import com.smarttaxi.ui.page.RigaMapPage;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

import javax.servlet.annotation.WebServlet;

@SuppressWarnings("serial")
public class TaxiUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = TaxiUI.class,
            widgetset = "com.smarttaxi.TaxiWidgetset")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {
        Navigator navigator = new Navigator(this, this);
        navigator.addView("RigaMap", RigaMapPage.class);
        navigator.addView("Districts", SpotsTablePage.class);
        navigator.addView("Calls", CallsTablePage.class);
        navigator.addView("Logs", LogsTablePage.class);
        navigator.navigateTo("RigaMap");
        setNavigator(navigator);
    }
}