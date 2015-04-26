package com.smarttaxi.data.domain;

import com.smarttaxi.analysis.Classifiable;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by Iwan on 21.03.2015
 */

public class Call implements Spot, Classifiable {

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

    @Override
    public int getCluster() {
        return cluster;
    }

    @Override
    public boolean setCluster(int cluster) {
        if (this.cluster == cluster) {
            return false;
        }
        this.cluster = cluster;
        return true;
    }


    @Override
    public double getDistance(Classifiable object) {
        Call that = (Call) object;
        return Math.sqrt(
                Math.pow(lat - that.getLat(), 2) +
                        Math.pow(lon - that.getLon(), 2));
    }

    @Override
    public Call getMean(List<Classifiable> list) {
        if (!CollectionUtils.isEmpty(list)) {
            double latSum = 0;
            double lonSum = 0;
            for (Classifiable c : list) {
                latSum += ((Call) c).getLat();
                lonSum += ((Call) c).getLon();
            }
            int n = list.size();
            Call mean = new Call();
            mean.setLat(latSum / n);
            mean.setLon(lonSum / n);
            return mean;
        }
        return null;
    }
}
