package com.smarttaxi.data.mapper;

import com.smarttaxi.data.domain.Call;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Iwan on 21.03.2015
 */

public class CallRowMapper implements RowMapper<Call> {

    @Override
    public Call mapRow(ResultSet resultSet, int i) throws SQLException {
        Call call = new Call();

        call.setId(resultSet.getLong("id"));
        call.setNotes(resultSet.getString("notes"));
        call.setPhone(resultSet.getString("phone"));
        call.setLat(resultSet.getDouble("lat"));
        call.setLon(resultSet.getDouble("lon"));
        call.setGroup(resultSet.getInt("groupn"));

        return call;
    }
}
