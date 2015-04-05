package com.smarttaxi.spatial;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

/**
 * Created by Iwan on 05.04.2015
 */

public class CoordinatesServiceTest {

    @Test
    public void belowPointBelow() {

        Point startPoint = new Point(2, 2);
        Point endPoint = new Point(3, 4);

        Point belowPoint = new Point(2, 3);
        Point abovePoint = new Point(3, 3);

        CoordinatesService coordinatesService = new CoordinatesService();

        assertTrue(coordinatesService.isBelow(startPoint, endPoint, belowPoint));
        assertTrue(coordinatesService.isBelow(endPoint, startPoint, belowPoint));

        assertFalse(coordinatesService.isBelow(startPoint, endPoint, abovePoint));
        assertFalse(coordinatesService.isBelow(endPoint, startPoint, abovePoint));
    }
}
