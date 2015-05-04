package com.smarttaxi.analysis;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Iwan on 26.04.2015
 */

public class Cluster {

    private int id;
    private List<Entity> items;


    public Cluster(int id) {
        this(id, new LinkedList<Entity>());
    }

    public Cluster(int id, List<Entity> items) {
        this.id = id;
        if (items != null) {
            this.items = items;
        } else {
            this.items = new LinkedList<>();
        }
    }


    public int getId() {
        return id;
    }

    public boolean addItem(Entity item) {
        items.add(item);
        return item.setCluster(id);
    }

    public Entity removeLast() {
        return items.remove(items.size() - 1);
    }

    public Entity getCentre() {
        int n = items.size();
        if (n == 0) {
            return null;
        }

        if (n == 1) {
            return items.get(0);
        }

        return NumericParameters.getMean(items);
    }

    public int size() {
        return items.size();
    }

    public List<Entity> getItems() {
        return items;
    }
}
