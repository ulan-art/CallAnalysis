package com.smarttaxi.log;

import com.smarttaxi.data.dao.LogsDao;
import com.smarttaxi.data.domain.LogRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * Created by Iwan on 03.05.2015
 */

@Service
public class ApplicationLogger {

    @Autowired
    private LogsDao logsDao;

    private StringBuffer stringBuffer;


    public ApplicationLogger() {
    }

    @PostConstruct
    public void init() {
        addRecord("Here we go!");
    }


    public void addRecord(String message) {
        LogRecord logRecord = new LogRecord();
        logRecord.setCreationDate(new Date());
        logRecord.setMessage(message);
        logsDao.saveLogRecord(logRecord);
    }

    public void addFragment(String fragment) {
        if (stringBuffer == null) {
            stringBuffer = new StringBuffer();
        }
        stringBuffer.append(fragment);
        stringBuffer.append("; ");
    }

    public void flushBuffer() {
        addRecord(stringBuffer.toString());
        stringBuffer = null;
    }
}
