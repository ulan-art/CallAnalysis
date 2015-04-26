package com.smarttaxi.ui.component;

import com.smarttaxi.ui.methods.KMeansWindow;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Iwan on 26.04.2015
 */
public class MethodsMenu extends CustomComponent {

    private VerticalLayout menuLayout;

    public MethodsMenu() {

        menuLayout = new VerticalLayout();
        menuLayout.setMargin(true);
        menuLayout.setSpacing(true);

        menuLayout.addComponent(new KMeansButton());

        setCompositionRoot(menuLayout);
    }

    private class KMeansButton extends Button {

        public KMeansButton() {
            setCaption("K Means");
            addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent clickEvent) {
                    UI.getCurrent().addWindow(new KMeansWindow());
                }
            });
        }
    }
}
