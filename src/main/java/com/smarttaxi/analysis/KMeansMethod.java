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

        int i = 1;
        while(clustersChanged) {
            clustersChanged = false;
            List<Classifiable> means = getMeans(clusterList);
            clusterList = rearrangeClusters(means);
            log.info("Performed step " + i + ", clusters changed: " + clustersChanged);
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
            Classifiable centre = cluster.getCentre();
            means.add(centre);
        }
        return means;
    }

    private List<Cluster> rearrangeClusters(List<Classifiable> means) {
        List<Cluster> clusterList = createEmptyClusters();
        for (Classifiable item : items) {
            int closestCluster = -1;
            double bestDistance = Double.MAX_VALUE;

            for (int i = 0; i < k; i++) {
                Classifiable mean = means.get(i);
                if (mean != null) {
                    double newDistance = item.getDistance(mean);
                    if (newDistance < bestDistance) {
                        closestCluster = i;
                        bestDistance = newDistance;
                    }
                }
            }
            clustersChanged |= clusterList.get(closestCluster).addItem(item);
        }
        return clusterList;
    }
}
