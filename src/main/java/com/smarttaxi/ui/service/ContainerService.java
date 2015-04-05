package com.smarttaxi.ui.service;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.IndexedContainer;

/**
 * Created by Iwan on 04.04.2015
 */

public class ContainerService {

    public static final String CAPTION_PROPERTY = "caption";

    public static <T extends Enum<T>> Container getContainer(Class<T> enumeration) {
        IndexedContainer container = new IndexedContainer();
        container.addContainerProperty(CAPTION_PROPERTY, String.class, "");

        for (T constant : enumeration.getEnumConstants()) {
            container.addItem(constant);
            container.getItem(constant).getItemProperty(CAPTION_PROPERTY)
                    .setValue(constant.toString());
        }

        return container;
    }
}
