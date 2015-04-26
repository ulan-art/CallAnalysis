package com.smarttaxi.analysis;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Iwan on 26.04.2015
 */

public class Cluster {

    private int id;
    private List<Classifiable> items;


    public Cluster(int id) {
        this(id, new LinkedList<Classifiable>());
    }

    public Cluster(int id, List<Classifiable> items) {
        this.id = id;
        if (items != null) {
            this.items = items;
        } else {
            this.items = new LinkedList<>();
        }
    }


    public boolean addItem(Classifiable item) {
        items.add(item);
        return item.setCluster(id);
    }

    public Classifiable getCentre() {
        int n = items.size();
        if (n == 0) {
            return null;
        }

        if (n == 1) {
            return items.get(0);
        }

        return items.get(0).getMean(items);
    }
}
