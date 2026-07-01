package com.thai.pham.inventoryservice.configs;

import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {
    private static final Logger log = LoggerFactory.getLogger(DataSourceConfig.class);
    private final String masterUrl;
    private final String slave1Url;
//    private final String slave2Url;
//    private final String slave3Url;
//    private final String slave4Url;
//    private final String slave5Url;
    private final String userName;
    private final String password;
    private final String userNameSlave;
    private final String passwordSlave;
    private final String driverClassName;
    private final Integer maximumConnections; // Maximum NUMBER OF connection in the pool at the same time
    private final Integer leakThresholdInMillis; // Log possible leak connection

    @Autowired
    public DataSourceConfig(
        @Value("${database.master.url}") String masterUrl,
        @Value("${database.slaves.slave1.url}") String slave1Url,
        @Value("${spring.datasource.username}") String userName,
        @Value("${spring.datasource.password}") String password,
        @Value("${spring.datasource.slave.username}") String userNameSlave,
        @Value("${spring.datasource.slave.password}") String passwordSlave,
        @Value("${spring.datasource.driver-class-name}") String driverClassName,
        @Value("${spring.datasource.hikari.maximum-pool-size}") Integer maximumConnections,
        @Value("${spring.datasource.hikari.leak-detection-threshold}") Integer leakThresholdInMillis
    ) {
        this.masterUrl = masterUrl;
        this.slave1Url = slave1Url;
//        this.slave2Url = slave2Url;
//        this.slave3Url = slave3Url;
//        this.slave4Url = slave4Url;
//        this.slave5Url = slave5Url;
        this.userName = userName;
        this.password = password;
        this.userNameSlave = userNameSlave;
        this.passwordSlave = passwordSlave;
        this.driverClassName = driverClassName;
        this.maximumConnections = maximumConnections;
        this.leakThresholdInMillis = leakThresholdInMillis;
        log.info("_______Configuration log_______");
        log.info("master url {}", masterUrl);
        log.info("slave url {}", slave1Url);
        log.info("userName {}", userName);
        log.info("password {}", password);
        log.info("userNameSlave {}", userNameSlave);
        log.info("passwordSlave {}", passwordSlave);
        log.info("driverClassName {}", driverClassName);
        log.info("maximumConnections {}", maximumConnections);
        log.info("leakThresholdInMillis {}", leakThresholdInMillis);
    }

    @Bean
    public DataSource routingDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();
        log.info("_______DB Info Log_______");
        log.info("master url {}", masterUrl);
        log.info("slave url {}", slave1Url);
        log.info("userName {}", userName);
        log.info("password {}", password);
        log.info("userNameSlave {}", userNameSlave);
        log.info("passwordSlave {}", passwordSlave);
        log.info("driverClassName {}", driverClassName);
        log.info("maximumConnections {}", maximumConnections);
        log.info("leakThresholdInMillis {}", leakThresholdInMillis);

        DataSource master = createDataSource(masterUrl, userName, password);
        targetDataSources.put("master", master);
        targetDataSources.put("slave1", createDataSource(slave1Url, userNameSlave, passwordSlave));
//        targetDataSources.put("slave2", createDataSource(slave2Url));
//        targetDataSources.put("slave3", createDataSource(slave3Url));
//        targetDataSources.put("slave4", createDataSource(slave4Url));
//        targetDataSources.put("slave5", createDataSource(slave5Url));

        ReadRoutingDataSource routingDataSource = new ReadRoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(master);
        routingDataSource.setTargetDataSources(targetDataSources);
        routingDataSource.afterPropertiesSet();

        return routingDataSource;
    }

    @Bean
    @Primary
    public DataSource dataSource(DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);// Prevent Spring from grabbing db connection when execute transaction until execute SQL statement
    }

    private DataSource createDataSource(String dataSourceUrl, String userName, String password) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dataSourceUrl);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setMaximumPoolSize(maximumConnections);
        dataSource.setLeakDetectionThreshold(leakThresholdInMillis);
        dataSource.setDriverClassName(driverClassName);
        return dataSource;
    }
}