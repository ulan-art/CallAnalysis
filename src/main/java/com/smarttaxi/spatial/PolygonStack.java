package com.smarttaxi.spatial;

import com.smarttaxi.data.domain.Spot;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Iwan on 06.04.2015
 */

public class PolygonStack {

    private List<Spot> stack = new LinkedList<>();
    private int top = -1;


    public void push(Spot spot) {
        top++;
        stack.add(spot);
    }

    public void pop() {
        if (top >= 0) {
            stack.remove(top);
            top--;
        }
    }

    public Spot getLast() {
        if (top >= 0) {
            return stack.get(top);
        }
        return null;
    }

    public Spot getPreLast() {
        if (top >= 1) {
            return stack.get(top - 1);
        }
        return null;
    }

    public int size() {
        return stack.size();
    }

    public List<Spot> asList() {
        return stack;
    }
}
