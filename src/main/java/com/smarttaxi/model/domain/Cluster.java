package com.smarttaxi.model.domain;

import com.smarttaxi.data.domain.Spot;

import java.util.List;

/**
 * Created by Iwan on 06.04.2015
 */

public class Cluster {

    private List<Spot> spotList;


    public int getSize() {
        return spotList.size();
    }

    public List<Spot> getSpotList() {
        return spotList;
    }
}
