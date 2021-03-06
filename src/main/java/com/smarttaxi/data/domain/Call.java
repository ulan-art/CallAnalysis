package com.smarttaxi.data.domain;

/**
 * Created by Iwan on 21.03.2015
 */

public class Call {

    private long id;

    private double lat;
    private double lon;

    private String notes;
    private String phone;

    private int cluster;


    public Call() {
        super();
    }


    public String getNotes() {
        return notes;
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

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCluster() {
        return cluster;
    }

    public void setCluster(int cluster) {
        this.cluster = cluster;
    }
}
