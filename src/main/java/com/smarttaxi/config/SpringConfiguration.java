package com.smarttaxi.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

/**
 * Created by Iwan on 21.03.2015
 */

@Configuration
@EnableTransactionManagement
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

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(getBasicDataSource());
        sessionFactory.setPackagesToScan("com.smarttaxi.data.domain");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    private Properties hibernateProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql", environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", environment.getRequiredProperty("hibernate.format_sql"));
        return properties;
    }

    @Bean
    @Autowired
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory);
        return txManager;
    }
}
