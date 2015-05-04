package com.smarttaxi.analysis;

/**
 * Created by Iwan on 04.05.2015
 */

public class Entity {

    private long id;
    private double lat;
    private double lon;
    private int cluster;


    public Entity() {
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

    public int getCluster() {
        return cluster;
    }

    public boolean setCluster(int cluster) {
        if (this.cluster != cluster) {
            this.cluster = cluster;
            return true;
        }
        return false;
    }
}
