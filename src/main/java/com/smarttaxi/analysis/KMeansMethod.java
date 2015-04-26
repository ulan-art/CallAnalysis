package com.smarttaxi.analysis;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Iwan on 12.04.2015
 */

public class KMeansMethod {

    private final static Logger log = Logger.getLogger(KMeansMethod.class);

    private List<? extends Classifiable> items;
    private int k;
    private boolean clustersChanged = true;

    public KMeansMethod(List<? extends Classifiable> items, int k) {
        if (items != null) {
            this.items = items;
        } else {
            this.items = new LinkedList<>();
        }
        this.k = k;
    }

    public List<? extends Classifiable> perform() {
        log.info("K Means analysis performed for k = " + k);
        if (k == 1) {
            distributeItemsRandomly();
            return items;
        }

        List<Cluster> clusterList = distributeItemsRandomly();

        while(clustersChanged) {
            clustersChanged = false;
            List<Classifiable> means = getMeans(clusterList);
            clusterList = rearrangeClusters(means);
        }

        return items;
    }


    private List<Cluster> createEmptyClusters() {
        List<Cluster> clusterList = new ArrayList<>(k);
        for (int i = 0; i < k; i++) {
            clusterList.add(new Cluster(i + 1));
        }
        return clusterList;
    }

    private List<Cluster> distributeItemsRandomly() {
        List<Cluster> clusterList = createEmptyClusters();
        for (int i = 0; i < items.size(); i++) {
            clusterList.get(i % k).addItem(items.get(i));
        }
        return clusterList;
    }

    private List<Classifiable> getMeans(List<Cluster> clusterList) {
        List<Classifiable> means = new ArrayList<>(k);
        for (Cluster cluster : clusterList) {
            means.add(cluster.getCentre());
        }
        return means;
    }

    private List<Cluster> rearrangeClusters(List<Classifiable> means) {
        List<Cluster> clusterList = createEmptyClusters();
        for (Classifiable item : items) {
            int closestCluster = 0;
            double bestDistance = item.getDistance(means.get(closestCluster));
            for (int i = 1; i < k; i++) {
                double newDistance = item.getDistance(means.get(i));
                if (newDistance < bestDistance) {
                    closestCluster = i;
                    bestDistance = newDistance;
                }
            }
            clustersChanged |= clusterList.get(closestCluster).addItem(item);
        }
        return clusterList;
    }
}
