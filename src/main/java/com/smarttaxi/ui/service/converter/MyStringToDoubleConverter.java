package com.smarttaxi.ui.service.converter;

import com.vaadin.data.util.converter.StringToDoubleConverter;

import java.text.NumberFormat;
import java.util.Locale;


/**
 * Created by Iwan on 04.04.2015
 */

public class MyStringToDoubleConverter extends StringToDoubleConverter {

    @Override
    protected NumberFormat getFormat(Locale locale) {
        Locale currentLocale = Locale.getDefault();
        return super.getFormat(currentLocale);
    }
}
