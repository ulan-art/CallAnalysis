package com.smarttaxi.analysis;

import com.smarttaxi.data.domain.Call;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Iwan on 26.04.2015
 */

public class Cluster {

    private int id;
    private List<Call> items;


    public Cluster(int id) {
        this(id, new LinkedList<Call>());
    }

    public Cluster(int id, List<Call> items) {
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

    public boolean addItem(Call item) {
        items.add(item);
        return item.setCluster(id);
    }

    public Call removeLast() {
        return items.remove(items.size() - 1);
    }

    public Call getCentre() {
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

    public List<Call> getItems() {
        return items;
    }
}
