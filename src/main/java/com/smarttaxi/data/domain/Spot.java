package com.smarttaxi.data.domain;

/**
 * Created by Iwan on 05.04.2015
 */

public interface Spot {

    long getId();
    double getLat();
    double getLon();
    String getNotes();
    int getGroup();
}
