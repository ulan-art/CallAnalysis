package com.smarttaxi.ui.service;

import com.vaadin.ui.Notification;

/**
 * Created by Iwan on 04.04.2015
 */

public class Notifications {

    public static void showWarning(String caption) {
        Notification.show(caption,
                Notification.Type.WARNING_MESSAGE);
    }

    public static void showWarning(String caption, String description) {
        Notification.show(caption + "\n", description,
                Notification.Type.WARNING_MESSAGE);
    }
}
