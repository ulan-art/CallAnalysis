package com.smarttaxi.data.dao;

import com.smarttaxi.data.domain.Call;
import com.smarttaxi.data.mapper.CallRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Iwan on 21.03.2015
 */

@Repository
public class CallDao {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<Call> getCallList() {
        String query = "select * from calls";
        return jdbcTemplate.query(query, new CallRowMapper());
    }

    @Transactional
    public void saveCall(Call call) {
        Map<String, Object> params = new HashMap<>();
        params.put("lat", call.getLat());
        params.put("lon", call.getLon());
        params.put("notes", call.getNotes());
        params.put("phone", call.getPhone());
        params.put("cluster", call.getCluster());
        String query = "insert into calls (lat, lon, notes, phone, cluster) " +
                "values (:lat, :lon, :notes, :phone, :cluster)";
        namedParameterJdbcTemplate.update(query, params);
    }

    @Transactional
    public void deleteAllCalls() {
        String query = "delete from calls";
        jdbcTemplate.update(query);
    }

    public List<Call> getCallList(int groupId) {
        String query = "select * from calls where cluster = ?";
        return jdbcTemplate.query(query, new CallRowMapper(), groupId);
    }


    @Transactional
    public void updateClusters(List<Call> callList) {
        for (Call call : callList) {
            updateCluster(call);
        }
    }

    private void updateCluster(Call call) {
        String query = "UPDATE calls SET " +
                "cluster = ? " +
                "WHERE id = ?";
        jdbcTemplate.update(query, call.getCluster(), call.getId());
    }
}
