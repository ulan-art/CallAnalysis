package com.smarttaxi.test;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Iwan on 05.04.2015
 */

public class FileManager {

    final static Logger log = Logger.getLogger(FileManager.class);

    private PrintWriter pw;

    public FileManager(String output) {

        try {
            File out = new File(output);
            pw = new PrintWriter(out);
        } catch (FileNotFoundException e) {
            log.info("FileNotFoundException in constructor", e);
        }
    }

    public void sendLine(String line) {
        pw.println(line);
    }

    public void finish() {
        pw.close();
    }
}
