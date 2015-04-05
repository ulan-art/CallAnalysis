package com.smarttaxi.data.dao;

import com.smarttaxi.data.domain.District;
import com.smarttaxi.data.mapper.DistrictRowMapper;
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
 * Created by Iwan on 22.03.2015
 */

@Repository
public class DistrictDao {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    public List<District> getDistrictList() {
        String query = "SELECT * FROM district";
        return jdbcTemplate.query(query, new DistrictRowMapper());
    }

    public District getCityCentre() {
        String query = "SELECT * FROM district WHERE marker_type = 'CITY_CENTRE'";
        List<District> districtList = jdbcTemplate.query(query, new DistrictRowMapper());
        return districtList.size() != 0 ? districtList.get(0) : null;
    }

    public List<District> getWeightCentres() {
        String query = "SELECT * FROM district WHERE marker_type = 'WEIGHT_CENTRE'";
        return jdbcTemplate.query(query, new DistrictRowMapper());
    }

    @Transactional
    public void saveDistrict(District district) {
        String query = "INSERT INTO district (lat, lon, name, notes, weight, marker_type, groupn) " +
                "VALUES (:lat, :lon, :name, :notes, :weight, :marker_type, :group)";
        namedParameterJdbcTemplate.update(query, toParams(district));
    }

    @Transactional
    public void updateDistrict(District district) {
        String query = "UPDATE district SET " +
                "lat = :lat, " +
                "lon = :lon, " +
                "name = :name, " +
                "notes = :notes, " +
                "weight = :weight," +
                "marker_type = :marker_type, " +
                "groupn = :group " +
                "WHERE id = :id";
        namedParameterJdbcTemplate.update(query, toParams(district));
    }

    private Map<String, Object> toParams(District district) {
        Map<String, Object> params = new HashMap<>();
        if (district.getId() != 0) {
            params.put("id", district.getId());
        }
        params.put("lat", district.getLat());
        params.put("lon", district.getLon());
        params.put("name", district.getName());
        params.put("notes", district.getNotes());
        params.put("weight", district.getWeight());
        params.put("marker_type",
                district.getType() != null ? district.getType().toString() : null);
        params.put("group", district.getGroup());
        return params;
    }

    @Transactional
    public void deleteDistrict(District district) {
        String query = "DELETE FROM district WHERE id = ?";
        jdbcTemplate.update(query, district.getId());
    }
}
