package com.smarttaxi.spatial;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Iwan on 06.04.2015
 */

public class PolygonStack {

    private List<Point> stack = new LinkedList<>();
    private int top = -1;


    public void push(Point point) {
        top++;
        stack.add(point);
    }

    public void pop() {
        if (top >= 0) {
            stack.remove(top);
            top--;
        }
    }

    public Point getLast() {
        if (top >= 0) {
            return stack.get(top);
        }
        return null;
    }

    public Point getPreLast() {
        if (top >= 1) {
            return stack.get(top - 1);
        }
        return null;
    }

    public int size() {
        return stack.size();
    }

    public List<Point> asList() {
        return stack;
    }
}
