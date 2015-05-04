package com.smarttaxi.ui.service.dictionary;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Iwan on 04.05.2015
 */

public class LogsDictionary implements Dictionary {

    private Map<String, String> pairs = new HashMap<>();

    public LogsDictionary() {
        pairs.put("id", "ID");
        pairs.put("creationDate", "Creation date");
        pairs.put("message", "Message");
    }

    @Override
    public String getCaption(String property) {
        String caption = pairs.get(property);
        return caption != null ? caption : property;
    }
}
