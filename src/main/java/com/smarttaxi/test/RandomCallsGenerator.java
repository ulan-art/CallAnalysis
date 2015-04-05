package com.smarttaxi.test;

import com.smarttaxi.data.dao.CallDao;
import com.smarttaxi.data.domain.Call;
import com.smarttaxi.data.domain.District;
import com.smarttaxi.spatial.CoordinatesService;
import com.smarttaxi.spatial.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Iwan on 05.04.2015
 */

@Service
public class RandomCallsGenerator {

    @Autowired
    private CallDao callDao;

    @Autowired
    private CoordinatesService coordinatesService;


    public RandomCallsGenerator() {
    }


    public void generateRandomCalls(List<District> weightCentres, int calls) {

        double totalWeight = 0;
        for (District district : weightCentres) {
            totalWeight += district.getWeight();
        }

        int group = 0;

        for (District district : weightCentres) {
            long callsFromHere = Math.round(calls * district.getWeight() / totalWeight);
            for (long i = 0; i < callsFromHere; i++) {
                Call randomCall = new Call();
                randomCall.setNotes("Random call from " + district.getName());
                Point point = coordinatesService.getRandomPoint(
                        district.getLat(), district.getLon(), 1);
                randomCall.setLat(point.getLat());
                randomCall.setLon(point.getLon());
                randomCall.setGroup(group);
                callDao.saveCall(randomCall);
            }
            group++;
        }
    }
}
