package com.smarttaxi.ui.crud;

import com.smarttaxi.ui.component.YesNoWindow;
import com.smarttaxi.ui.service.ContainerService;
import com.smarttaxi.ui.service.Notifications;
import com.smarttaxi.ui.service.dictionary.Dictionary;
import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.apache.log4j.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by Iwan on 22.03.2015
 */

public abstract class CrudPanel extends CustomComponent {

    private final static Logger log = Logger.getLogger(CrudPanel.class);

    private String entityName;
    private Dictionary dictionary;

    private HorizontalLayout buttonsLayout;

    private Button newButton;
    private Button editButton;
    private Button deleteButton;

    private Table table;

    private Class<? extends ItemForm> itemFormType;

    public CrudPanel() {

        VerticalLayout commonLayout = new VerticalLayout();

        buttonsLayout = new HorizontalLayout();
        buttonsLayout.setMargin(true);
        buttonsLayout.setSpacing(true);

        newButton = new NewButton();
        editButton = new EditButton();
        deleteButton = new DeleteButton();

        toggleButtons(false);

        table = new Table();
        table.setSelectable(true);
        table.addValueChangeListener(new TableItemClickListener());
        table.setPageLength(20);
        table.setContainerDataSource(new BeanItemContainer(getEntityType()));

        commonLayout.addComponent(buttonsLayout);
        commonLayout.addComponent(table);

        setCompositionRoot(commonLayout);
    }

    // Crud interface

    protected abstract Class getEntityType(); // TODO generify

    protected abstract BeanItemContainer getTableContainer();

    protected abstract void saveItem(BeanItem beanItem);

    protected abstract void updateItem(BeanItem beanItem);

    protected abstract void deleteItem(BeanItem beanItem);


    protected void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    protected void setDictionary(Dictionary dictionary) {
        this.dictionary = dictionary;
        Object[] visibleColumns = table.getVisibleColumns();
        for (Object propertyId : visibleColumns) {
            table.setColumnHeader(propertyId, dictionary.getCaption((String) propertyId));
        }
    }

    protected void addNewButton() {
        newButton.setCaption("New " + entityName);
        buttonsLayout.addComponent(newButton);
    }

    protected void addEditButton() {
        editButton.setCaption("Edit " + entityName);
        buttonsLayout.addComponent(editButton);
    }

    protected void addDeleteButton() {
        deleteButton.setCaption("Delete " + entityName);
        buttonsLayout.addComponent(deleteButton);
    }

    protected void addCrudButtons() {
        addNewButton();
        addEditButton();
        addDeleteButton();
    }

    protected void addCustomButton(Button button) {
        buttonsLayout.addComponent(button);
    }

    protected void toggleButtons(boolean enabled) {
        editButton.setEnabled(enabled);
        deleteButton.setEnabled(enabled);
    }

    protected void setVisibleColumns(Object... visibleColumns) {
        table.setVisibleColumns(visibleColumns);
    }

    protected void fetchTableContainer() {
        table.setContainerDataSource(getTableContainer());
    }

    protected void setItemFormType(Class<? extends ItemForm> itemFormType) {
        this.itemFormType = itemFormType;
    }

    private void showForm(BeanItem beanItem) {
        try {
            Constructor<? extends ItemForm> constructor =
                    itemFormType.getConstructor(this.getClass(), BeanItem.class);
            Window window = constructor.newInstance(this, beanItem);
            UI.getCurrent().addWindow(window);
        } catch (NoSuchMethodException e) {
            log.error("NoSuchMethodException when creating form", e);
        } catch (InvocationTargetException e) {
            log.error("InvocationTargetException when creating form", e);
            log.error("CAUSED BY: ", e.getCause());
        } catch (InstantiationException e) {
            log.error("InstantiationException when creating form", e);
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException when creating form", e);
        }
    }


    private class NewButton extends Button {

        public NewButton() {
            addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent clickEvent) {
                    showForm(null);
                }
            });
        }
    }

    private class EditButton extends Button {

        public EditButton() {
            addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent clickEvent) {
                    BeanItem tableItem = (BeanItem) table.getItem(table.getValue());
                    showForm(tableItem);
                }
            });
        }
    }

    private class DeleteButton extends Button {

        public DeleteButton() {
            addClickListener(new ClickListener() {
                @Override
                public void buttonClick(ClickEvent clickEvent) {
                    final BeanItem tableItem = (BeanItem) table.getItem(table.getValue());
                    YesNoWindow yesNoWindow = new YesNoWindow() {
                        @Override
                        public void onYesButtonClick() {
                            deleteItem(tableItem);
                            fetchTableContainer();
                        }
                    };
                    UI.getCurrent().addWindow(yesNoWindow);
                }
            });
        }
    }

    private class TableItemClickListener implements Property.ValueChangeListener {
        @Override
        public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
            toggleButtons(table.getValue() != null);
        }
    }


    public abstract class ItemForm extends Window {

        private FormLayout formLayout;
        private FieldGroup fieldGroup;

        private boolean itemIsNew = false;

        public ItemForm(BeanItem beanItem) {
            setSizeUndefined();
            setResizable(false);
            setModal(true);
            center();

            if (beanItem == null) {
                Class beanItemClass = ((BeanItemContainer) table.getContainerDataSource()).getBeanType();
                try {
                    itemIsNew = true;
                    beanItem = new BeanItem(beanItemClass.newInstance());
                    setCaption("Create " + entityName);
                } catch (InstantiationException e) {
                    log.error("InstantiationException when creating new BeanItem");
                } catch (IllegalAccessException e) {
                    log.error("IllegalAccessException when creating new BeanItem");
                }
            } else {
                setCaption("Edit " + entityName);
            }

            formLayout = new FormLayout();
            formLayout.setMargin(true);
            formLayout.setSpacing(true);

            fieldGroup = new FieldGroup(beanItem);

            setContent(formLayout);
        }

        protected ChainedTextField addTextField(String propertyId) {
            String caption = dictionary.getCaption(propertyId);
            return addTextField(caption, propertyId);
        }

        protected ChainedTextField addTextField(String caption, String propertyId) {
            TextField textField = new TextField(caption);
            formLayout.addComponent(textField);
            fieldGroup.bind(textField, propertyId);
            return new ChainedTextField(textField);
        }

        protected ChainedComboBox addComboBox(String propertyId, Container dataSource) {
            String caption = dictionary.getCaption(propertyId);
            return addComboBox(caption, propertyId, dataSource);
        }

        protected ChainedComboBox addComboBox(String caption, String propertyId, Container dataSource) {
            ComboBox comboBox = new ComboBox(caption, dataSource);
            comboBox.setItemCaptionPropertyId(ContainerService.CAPTION_PROPERTY);
            formLayout.addComponent(comboBox);
            fieldGroup.bind(comboBox, propertyId);
            return new ChainedComboBox(comboBox);
        }

        protected void addSaveButton() {
            Button saveButton = new Button("Save");
            formLayout.addComponent(saveButton);
            saveButton.addClickListener(new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    if (fieldGroup.isValid()) {
                        try {
                            fieldGroup.commit();
                            BeanItem beanItem = (BeanItem) fieldGroup.getItemDataSource();
                            if (itemIsNew) {
                                saveItem(beanItem);
                            } else {
                                updateItem(beanItem);
                            }
                            fetchTableContainer();
                            close();
                        } catch (FieldGroup.CommitException e) {
                            log.error("FieldGroup.CommitException when committing FiledGroup", e);
                        }
                    } else {
                        Notifications.showWarning(
                                "Cannot save data!",
                                "Please check the input.");
                    }
                }
            });
        }
    }
}
