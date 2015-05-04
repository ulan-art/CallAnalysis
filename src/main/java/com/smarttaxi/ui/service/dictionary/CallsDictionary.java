package com.smarttaxi.ui.service.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Iwan on 05.04.2015
 */

public class CallsDictionary implements Dictionary {

    private Map<String, String> pairs = new HashMap<>();

    public CallsDictionary() {
        pairs.put("id", "ID");
        pairs.put("lat", "Latitude");
        pairs.put("lon", "Longitude");
        pairs.put("notes", "Notes");
        pairs.put("phone", "Phone");
        pairs.put("cluster", "Cluster");
    }

    @Override
    public String getCaption(String property) {
        String caption = pairs.get(property);
        return caption != null ? caption : property;
    }
}
