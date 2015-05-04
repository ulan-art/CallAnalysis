package com.smarttaxi.ui.methods;

import com.smarttaxi.analysis.Entity;
import com.smarttaxi.analysis.EntityConverter;
import com.smarttaxi.analysis.KMeansMethod;
import com.smarttaxi.config.Application;
import com.smarttaxi.data.dao.CallDao;
import com.smarttaxi.data.domain.Call;
import com.smarttaxi.ui.service.Notifications;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Window;

import java.util.List;

/**
 * Created by Iwan on 26.04.2015
 */

public class KMeansWindow extends Window {

    private CallDao callDao = Application.getBean(CallDao.class);
    private EntityConverter entityConverter = Application.getBean(EntityConverter.class);

    private TextField textField;


    public KMeansWindow() {
        setCaption("K Means method");
        center();
        setResizable(false);

        FormLayout formLayout = new FormLayout();
        formLayout.setMargin(true);
        formLayout.setSpacing(true);

        textField = new TextField("Means");
        textField.setRequired(true);
        textField.setNullRepresentation("");
        textField.setImmediate(true);
        textField.setConverter(new StringToIntegerConverter());
        textField.addValidator(new IntegerRangeValidator(
                "K must be a positive integer", 1, Integer.MAX_VALUE));

        formLayout.addComponent(textField);
        formLayout.addComponent(new RunMethodButton());

        setContent(formLayout);
    }

    private class RunMethodButton extends Button {

        public RunMethodButton() {
            setCaption("Run");
            addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent clickEvent) {
                    if (textField.isValid()) {
                        int k = Integer.parseInt(textField.getValue());
                        List<Call> callList = callDao.getCallList();
                        List<Entity> entityList = entityConverter.geEntityList(callList);
                        KMeansMethod kMeansMethod = new KMeansMethod(entityList, k);
                        List<Entity> classification = kMeansMethod.perform();
                        entityConverter.updateClusters(callList, classification);
                        callDao.updateClusters(callList);
                        close();
                    } else {
                        Notifications.showWarning(
                                "Supplied data is wrong!",
                                "Please check method parameters.");
                    }
                }
            });
        }
    }
}
