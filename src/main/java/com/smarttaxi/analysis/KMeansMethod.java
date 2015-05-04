package com.smarttaxi.analysis;

import com.smarttaxi.config.Application;
import com.smarttaxi.data.domain.Call;
import com.smarttaxi.log.ApplicationLogger;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Iwan on 12.04.2015
 */

public class KMeansMethod {

    private final static Logger log = Logger.getLogger(KMeansMethod.class);

    private ApplicationLogger appLevelLog = Application.getBean(ApplicationLogger.class);

    private List<Call> items;
    private int k;
    private boolean clustersChanged = true;

    public KMeansMethod(List<Call> items, int k) {
        if (items != null) {
            this.items = items;
        } else {
            this.items = new LinkedList<>();
        }
        this.k = k;
    }

    public List<Call> perform() {
        appLevelLog.addRecord("K Means analysis performed for k = " + k);
        if (k == 1) {
            distributeItemsRandomly();
            return items;
        }

        List<Cluster> clusterList = distributeItemsRandomly();

        int i = 1;
        while(clustersChanged) {
            clustersChanged = false;
            List<Call> means = getMeans(clusterList);
            clusterList = rearrangeClusters(means);
            balanceClusters(clusterList);
            appLevelLog.addRecord("Performed step " + i++ + ", clusters changed: " + clustersChanged);
            for (Cluster cluster : clusterList) {
                appLevelLog.addFragment("Cluster: " + cluster.getId());
                appLevelLog.addFragment("Size: " + cluster.size());
                appLevelLog.addFragment(String.format("Inner variance: %.5f",
                        NumericParameters.empiricalVariance(cluster.getItems())));
                appLevelLog.flushBuffer();
            }
            appLevelLog.addRecord(String.format("Total variance: %.5f",
                    NumericParameters.empiricalVariance(means)));
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

    private List<Call> getMeans(List<Cluster> clusterList) {
        List<Call> means = new ArrayList<>(k);
        for (Cluster cluster : clusterList) {
            Call centre = cluster.getCentre();
            means.add(centre);
        }
        return means;
    }

    private List<Cluster> rearrangeClusters(List<Call> means) {
        List<Cluster> clusterList = createEmptyClusters();
        for (Call item : items) {
            int closestCluster = -1;
            double bestDistance = Double.MAX_VALUE;

            for (int i = 0; i < k; i++) {
                Call mean = means.get(i);
                if (mean != null) {
                    double newDistance = NumericParameters.getDistance(item, mean);
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

    private void balanceClusters(List<Cluster> clusterList) {
        Cluster emptyCluster = getEmptyCluster(clusterList);
        while (emptyCluster != null) {
            Cluster mostScatteredCluster = getMostScatteredCluster(clusterList);
            emptyCluster.addItem(mostScatteredCluster.removeLast());
            emptyCluster = getEmptyCluster(clusterList);
        }
    }

    private Cluster getEmptyCluster(List<Cluster> clusterList) {
        for (Cluster cluster : clusterList) {
            if (cluster.size() == 0) {
                return cluster;
            }
        }
        return null;
    }

    private Cluster getMostScatteredCluster(List<Cluster> clusterList) {
        Cluster mostScatteredCluster = null;
        double maximalVariance = 0;
        for (Cluster cluster : clusterList) {
            double empiricalVariance = NumericParameters.empiricalVariance(cluster.getItems());
            if (empiricalVariance > maximalVariance) {
                mostScatteredCluster = cluster;
                maximalVariance = empiricalVariance;
            }
        }
        return mostScatteredCluster;
    }
}
