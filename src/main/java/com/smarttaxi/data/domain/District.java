package com.smarttaxi.data.domain;

import com.smarttaxi.model.domain.MarkerType;

/**
 * Created by Iwan on 22.03.2015
 */

public class District  {

    private long id;

    private double lat;
    private double lon;

    private String name;
    private String notes;

    private double weight;

    private MarkerType type;

    private int cluster;


    public District() {
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public MarkerType getType() {
        return type;
    }

    public void setType(MarkerType type) {
        this.type = type;
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }
}
