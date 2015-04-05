package com.smarttaxi.ui.component;

import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by Iwan on 22.03.2015
 */

public class NavigationMenu extends CustomComponent {

    private VerticalLayout menuLayout;

    public NavigationMenu() {

        menuLayout = new VerticalLayout();
        menuLayout.setMargin(true);
        menuLayout.setSpacing(true);

        addButton("Riga", "RigaMap");
        addButton("Districts", "Districts");
        addButton("Calls", "Calls");

        setCompositionRoot(menuLayout);
    }

    private void addButton(String caption, final String view) {
        Button button = new Button();
        button.setCaption(caption);
        button.setWidth("100");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                UI.getCurrent().getNavigator().navigateTo(view);
            }
        });
        menuLayout.addComponent(button);
    }
}
