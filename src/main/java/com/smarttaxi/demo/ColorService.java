package com.smarttaxi.demo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Iwan on 05.04.2015
 */

@Service
public class ColorService {

    public static final String ROOT_DIR = "VAADIN/image/";

    private List<String> colorsList;
    private List<String> smallPointsList;

    public ColorService() {
        String[] colors = {
                "#FF4040",
                "#FFFF40",
                "#40FF40",
                "#4040FF",
                "#FF40FF",
                "#FF8080",
                "#FFFF80",
                "#80FF80",
                "#8080FF",
                "#FF80FF"
        };

        String[] smallPointFiles = {
                "small_red1",
                "small_yellow1",
                "small_green1",
                "small_blue1",
                "small_purple1",
                "small_red2",
                "small_yellow2",
                "small_green2",
                "small_blue2",
                "small_purple2"
        };

        colorsList = new ArrayList<>(Arrays.asList(colors));

        for (int i = 0; i < smallPointFiles.length; i++) {
            smallPointFiles[i] = getFilePath(smallPointFiles[i], "png");
        }

        smallPointsList = new ArrayList<>(Arrays.asList(smallPointFiles));
    }

    public String getSmallPointerUrl(int group) {
        return smallPointsList.get(group % smallPointsList.size());
    }

    public String getColor(int group) {
        return colorsList.get(group % smallPointsList.size());
    }

    private String getFilePath(String filename, String extension) {
        return ROOT_DIR + filename + "." + extension;
    }
}
