package com.smarttaxi.ui.crud;

import com.vaadin.ui.ComboBox;

/**
 * Created by Iwan on 04.04.2015
 */

public class ChainedComboBox {

    private ComboBox comboBox;


    public ChainedComboBox(ComboBox comboBox) {
        this.comboBox = comboBox;
    }


    public ComboBox get() {
        return comboBox;
    }


    public ChainedComboBox setRequired() {
        comboBox.setRequired(true);
        return this;
    }
}
