package com.smarttaxi.ui.page;

import com.smarttaxi.config.Application;
import com.smarttaxi.data.dao.CallDao;
import com.smarttaxi.data.dao.DistrictDao;
import com.smarttaxi.data.domain.Call;
import com.smarttaxi.data.domain.District;
import com.smarttaxi.demo.RandomCallsGenerator;
import com.smarttaxi.ui.component.NavigationMenu;
import com.smarttaxi.ui.component.YesNoWindow;
import com.smarttaxi.ui.crud.CrudPanel;
import com.smarttaxi.ui.service.dictionary.CallsDictionary;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.converter.StringToIntegerConverter;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import java.util.List;

/**
 * Created by Iwan on 05.04.2015
 */

public class CallsTablePage extends CustomComponent implements View {

    private CallDao callDao = Application.getBean(CallDao.class);
    private DistrictDao districtDao = Application.getBean(DistrictDao.class);
    private RandomCallsGenerator callsGenerator = Application.getBean(RandomCallsGenerator.class);

    public CallsTablePage() {

        HorizontalLayout pageLayout = new HorizontalLayout();

        pageLayout.addComponent(new NavigationMenu());
        pageLayout.addComponent(new CallCrudPanel());

        setCompositionRoot(pageLayout);
    }

    private class CallCrudPanel extends CrudPanel<Call> {

        public CallCrudPanel() {
            super();
            setDictionary(new CallsDictionary());
            addCustomButton(new GenerateCallsButton());
            addCustomButton(new DeleteAllCallsButton());
            fetchTableContainer();
            setVisibleColumns("id", "notes", "lat", "lon", "cluster");
        }


        @Override
        protected BeanItemContainer getTableContainer() {
            return new BeanItemContainer<>(
                    Call.class, callDao.getCallList());
        }

        @Override
        protected void saveItem(BeanItem beanItem) {
        }

        @Override
        protected void updateItem(BeanItem beanItem) {
        }

        @Override
        protected void deleteItem(BeanItem beanItem) {
        }

        public class GenerateCallsButton extends Button {

            public GenerateCallsButton() {
                setCaption("Generate calls");
                addClickListener(new ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent clickEvent) {
                        UI.getCurrent().addWindow(new GenerateCallsForm());
                    }
                });
            }
        }

        private class DeleteAllCallsButton extends Button {

            public DeleteAllCallsButton() {
                setCaption("Delete all");
                addClickListener(new ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent clickEvent) {
                        YesNoWindow yesNoWindow = new YesNoWindow() {
                            @Override
                            public void onYesButtonClick() {
                                callDao.deleteAllCalls();
                                fetchTableContainer();
                            }
                        };
                        UI.getCurrent().addWindow(yesNoWindow);
                    }
                });
            }
        }

        private class GenerateCallsForm extends Window {

            private TextField textField;

            public GenerateCallsForm() {
                setCaption("Generate random calls");
                center();
                setResizable(false);
                setModal(true);

                FormLayout formLayout = new FormLayout();
                formLayout.setMargin(true);
                formLayout.setSpacing(true);

                textField = new TextField("Number of calls");
                textField.setNullRepresentation("");
                textField.setConverter(new StringToIntegerConverter());
                textField.setRequired(true);

                formLayout.addComponent(textField);
                formLayout.addComponent(new ConfirmGenerateButton());

                setContent(formLayout);
            }

            public class ConfirmGenerateButton extends Button {
                public ConfirmGenerateButton() {
                    setCaption("Generate");
                    addClickListener(new ClickListener() {
                        @Override
                        public void buttonClick(ClickEvent clickEvent) {
                            if (textField.getValue() != null) {
                                int calls = Integer.parseInt(textField.getValue());
                                List<District> weightCentres = districtDao.getWeightCentres();
                                callsGenerator.generateRandomCalls(weightCentres, calls);
                                fetchTableContainer();
                                close();
                            }
                        }
                    });
                }
            }
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
