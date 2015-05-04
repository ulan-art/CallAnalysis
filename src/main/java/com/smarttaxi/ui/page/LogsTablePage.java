package com.smarttaxi.ui.page;

import com.smarttaxi.config.Application;
import com.smarttaxi.data.dao.LogsDao;
import com.smarttaxi.data.domain.LogRecord;
import com.smarttaxi.ui.component.NavigationMenu;
import com.smarttaxi.ui.component.YesNoWindow;
import com.smarttaxi.ui.crud.CrudPanel;
import com.smarttaxi.ui.service.dictionary.LogsDictionary;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;

/**
 * Created by Iwan on 03.05.2015
 */

public class LogsTablePage extends CustomComponent implements View {

    private LogsDao logsDao = Application.getBean(LogsDao.class);

    public LogsTablePage() {
        HorizontalLayout pageLayout = new HorizontalLayout();

        pageLayout.addComponent(new NavigationMenu());
        pageLayout.addComponent(new LogsCrudPanel());

        setCompositionRoot(pageLayout);
    }

    private class LogsCrudPanel extends CrudPanel<LogRecord> {

        public LogsCrudPanel() {
            super();
            setDictionary(new LogsDictionary());
            addCustomButton(new DeleteAllLogsButton());
            fetchTableContainer();
            setVisibleColumns("id", "creationDate", "message");
        }


        @Override
        protected BeanItemContainer getTableContainer() {
            return new BeanItemContainer<>(
                    LogRecord.class, logsDao.getLogRecordList());
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

        private class DeleteAllLogsButton extends Button {

            public DeleteAllLogsButton() {
                setCaption("Delete all");
                addClickListener(new ClickListener() {
                    @Override
                    public void buttonClick(ClickEvent clickEvent) {
                        YesNoWindow yesNoWindow = new YesNoWindow() {
                            @Override
                            public void onYesButtonClick() {
                                logsDao.deleteAll();
                                fetchTableContainer();
                            }
                        };
                        UI.getCurrent().addWindow(yesNoWindow);
                    }
                });
            }
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
