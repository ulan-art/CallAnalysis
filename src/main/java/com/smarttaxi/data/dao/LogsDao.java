package com.smarttaxi.data.dao;

import com.smarttaxi.data.domain.LogRecord;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Iwan on 04.05.2015
 */

@Repository
@Transactional
public class LogsDao {

    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    public List<LogRecord> getLogRecordList() {
        Criteria criteria = getSession().createCriteria(LogRecord.class);
        return (List<LogRecord>) criteria.list();
    }

    public void saveLogRecord(LogRecord logRecord) {
        getSession().persist(logRecord);
    }

    public void deleteAll() {
        getSession().createQuery("delete from LogRecord").executeUpdate();
    }
}
