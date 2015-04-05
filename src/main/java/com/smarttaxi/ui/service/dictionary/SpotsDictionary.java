package com.smarttaxi.ui.service.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Iwan on 04.04.2015
 */

public class SpotsDictionary implements Dictionary {

    private Map<String, String> pairs = new HashMap<>();

    public SpotsDictionary() {
        pairs.put("id", "ID");
        pairs.put("lat", "Latitude");
        pairs.put("lon", "Longitude");
        pairs.put("name", "Name");
        pairs.put("notes", "Notes");
        pairs.put("weight", "Weight");
        pairs.put("type", "Type");
    }

    @Override
    public String getCaption(String property) {
        String caption = pairs.get(property);
        return caption != null ? caption : property;
    }
}
