package com.smarttaxi.data.domain;

import com.smarttaxi.analysis.Classifiable;
import com.smarttaxi.model.domain.MarkerType;

import java.util.List;

/**
 * Created by Iwan on 22.03.2015
 */

public class District implements Spot, Classifiable {

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


    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public double getLat() {
        return lat;
    }

    @Override
    public double getLon() {
        return lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
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

    @Override
    public int getCluster() {
        return cluster;
    }

    @Override
    public boolean setCluster(int cluster) {
        this.cluster = cluster;
        return false;
    }

    @Override
    public double getDistance(Classifiable object) {
        return 0;
    }

    @Override
    public Classifiable getMean(List<Classifiable> list) {
        return null;
    }
}
