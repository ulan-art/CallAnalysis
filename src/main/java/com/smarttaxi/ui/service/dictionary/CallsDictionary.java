package com.smarttaxi.ui.service.dictionary;

/**
 * Created by Iwan on 05.04.2015
 */

public class CallsDictionary implements Dictionary {

    @Override
    public String getCaption(String property) {
        return property;
    }
}
