package com.smarttaxi.ui.page;

import com.smarttaxi.config.Application;
import com.smarttaxi.data.dao.DistrictDao;
import com.smarttaxi.data.domain.District;
import com.smarttaxi.model.domain.MarkerType;
import com.smarttaxi.ui.component.NavigationMenu;
import com.smarttaxi.ui.crud.CrudPanel;
import com.smarttaxi.ui.service.ContainerService;
import com.smarttaxi.ui.service.converter.MyStringToDoubleConverter;
import com.smarttaxi.ui.service.dictionary.SpotsDictionary;
import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;

/**
 * Created by Iwan on 22.03.2015
 */

public class SpotsTablePage extends CustomComponent implements View {


    public SpotsTablePage() {

        HorizontalLayout pageLayout = new HorizontalLayout();

        pageLayout.addComponent(new NavigationMenu());
        pageLayout.addComponent(new SpotCrudPanel());

        setCompositionRoot(pageLayout);
    }

    private class SpotCrudPanel extends CrudPanel<District> {

        private DistrictDao districtDao = Application.getContext().getBean(DistrictDao.class);


        public SpotCrudPanel() {
            super();
            setEntityName("spot");
            setDictionary(new SpotsDictionary());
            addCrudButtons();
            fetchTableContainer();
            setVisibleColumns("id", "name", "notes", "type", "lat", "lon", "weight");
            setItemFormType(DistrictForm.class);
        }


        public class DistrictForm extends ItemForm {

            public DistrictForm(BeanItem beanItem) {
                super(beanItem);

                Container spotTypes = ContainerService.getContainer(MarkerType.class);

                addTextField("name").setRequired();
                addTextField("notes");
                addTextField("lat").setRequired().setConverter(new MyStringToDoubleConverter());
                addTextField("lon").setRequired().setConverter(new MyStringToDoubleConverter());
                addTextField("weight").setConverter(new MyStringToDoubleConverter());
                addComboBox("type", spotTypes).setRequired();

                addSaveButton();
            }
        }


        @Override
        protected BeanItemContainer getTableContainer() {
            return new BeanItemContainer<>(
                    District.class, districtDao.getDistrictList());
        }

        @Override
        protected void saveItem(BeanItem beanItem) {
            districtDao.saveDistrict((District) beanItem.getBean());
        }

        @Override
        protected void updateItem(BeanItem beanItem) {
            districtDao.updateDistrict((District) beanItem.getBean());
        }

        @Override
        protected void deleteItem(BeanItem beanItem) {
            districtDao.deleteDistrict((District) beanItem.getBean());
        }
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
