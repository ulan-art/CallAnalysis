package com.smarttaxi.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created by Iwan on 21.03.2015
 */

@Configuration
@ComponentScan(basePackages = {"com.smarttaxi"})
@PropertySource("mysql.properties")
public class SpringConfiguration {

    @Autowired
    private Environment environment;

//    @Bean
//    public DataSource restDataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
//        dataSource.setUrl(environment.getProperty("jdbc.url"));
//        dataSource.setUsername(environment.getProperty("jdbc.user"));
//        dataSource.setPassword(environment.getProperty("jdbc.pass"));
//        return dataSource;
//    }

    @Bean
    public BasicDataSource getBasicDataSource() {
        BasicDataSource dbcp = new BasicDataSource();
        dbcp.setDriverClassName(environment.getProperty("jdbc.driverClassName"));
        dbcp.setUrl(environment.getProperty("jdbc.url"));
        dbcp.setUsername(environment.getProperty("jdbc.user"));
        dbcp.setPassword(environment.getProperty("jdbc.pass"));
        return dbcp;
    }
}
