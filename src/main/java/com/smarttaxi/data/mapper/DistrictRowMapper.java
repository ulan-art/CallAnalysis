package com.smarttaxi.data.mapper;

import com.smarttaxi.data.domain.District;
import com.smarttaxi.model.domain.MarkerType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Iwan on 22.03.2015
 */

public class DistrictRowMapper implements RowMapper<District> {

    @Override
    public District mapRow(ResultSet resultSet, int i) throws SQLException {
        District district = new District();

        district.setId(resultSet.getLong("id"));
        district.setName(resultSet.getString("name"));
        district.setNotes(resultSet.getString("notes"));

        String markerType = resultSet.getString("marker_type");
        district.setType(markerType != null ?
                MarkerType.valueOf(markerType) : MarkerType.UNKNOWN);

        district.setLat(resultSet.getDouble("lat"));
        district.setLon(resultSet.getDouble("lon"));
        district.setWeight(resultSet.getDouble("weight"));
        district.setCluster(resultSet.getInt("cluster"));

        return district;
    }
}
