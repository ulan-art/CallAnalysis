package com.smarttaxi.ui.component;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Window;

/**
 * Created by Iwan on 22.03.2015
 */

public abstract class YesNoWindow extends Window {

    public YesNoWindow() {
        setCaption("Are you sure?");
        setSizeUndefined();
        setResizable(false);
        setModal(true);
        center();

        HorizontalLayout buttonsLayout = new HorizontalLayout();
        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);

        Button yesButton = new Button("Yes");
        Button noButton = new Button("No");

        yesButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                onYesButtonClick();
                close();
            }
        });

        noButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                onNoButtonClick();
                close();
            }
        });

        buttonsLayout.addComponent(yesButton);
        buttonsLayout.addComponent(noButton);

        setContent(buttonsLayout);
    }

    public abstract void onYesButtonClick();

    public void onNoButtonClick() {
    }
}
