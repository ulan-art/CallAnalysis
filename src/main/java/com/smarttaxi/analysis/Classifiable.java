package com.smarttaxi.analysis;

import java.util.List;

/**
 * Created by Iwan on 26.04.2015
 */

public interface Classifiable {

    int getCluster();
    boolean setCluster(int cluster);

    double getDistance(Classifiable object);

    // TODO refactor
    Classifiable getMean(List<Classifiable> list);
}
