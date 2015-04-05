package com.smarttaxi.ui.crud;

import com.vaadin.data.Property;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.ui.TextField;

/**
 * Created by Iwan on 23.03.2015
 */

public class ChainedTextField {

    private TextField textField;


    public ChainedTextField(String caption, Property dataSource) {
        textField = new TextField(caption, dataSource);
        textField.setNullRepresentation("");
    }

    public ChainedTextField(TextField textField) {
        this.textField = textField;
        this.textField.setNullRepresentation("");
    }


    public TextField get() {
        return textField;
    }


    public ChainedTextField setRequired() {
        textField.setRequired(true);
        return this;
    }

    public ChainedTextField setDisabled() {
        textField.setEnabled(false);
        return this;
    }

    public ChainedTextField setInvisible() {
        textField.setVisible(false);
        return this;
    }

    public ChainedTextField setConverter(Converter<String, ?> converter) {
        textField.setConverter(converter);
        return this;
    }
}
