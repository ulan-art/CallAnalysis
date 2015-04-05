package com.smarttaxi.test;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Iwan on 05.04.2015
 */

@Service
public class MarkerIconService {

    public static final String ROOT_DIR = "VAADIN/image/";

    private List<String> smallPointsList;

    public MarkerIconService() {
        String[] smallPointFiles = {
                "small_red1",
                "small_green1",
                "small_blue1",
                "small_yellow1",
                "small_purple1",
                "small_red2",
                "small_green2",
                "small_blue2",
                "small_yellow2",
                "small_purple2",
        };

        for (int i = 0; i < smallPointFiles.length; i++) {
            smallPointFiles[i] = getFilePath(smallPointFiles[i], "png");
        }

        smallPointsList = new ArrayList<>(Arrays.asList(smallPointFiles));
    }

    public String getSmallPointerUrl(int group) {
        return smallPointsList.get(group % smallPointsList.size());
    }

    private String getFilePath(String filename, String extension) {
        return ROOT_DIR + filename + "." + extension;
    }
}
