package com.thai.pham.inventoryservice.configs.dbconnection;

import com.thai.pham.inventoryservice.common.DatabaseConfigConst;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

@Component
public class DatasourceHealthChecker {
    private final DataSourceSelector datasourceSelector;
    private final Map<DataSourceType, DataSource> slaveDataSources;

    @Autowired
    public DatasourceHealthChecker(DataSourceSelector dataSourceSelector, Map<DataSourceType, DataSource> slaveDataSources) {
        this.datasourceSelector = dataSourceSelector;
        this.slaveDataSources = slaveDataSources;
    }

    @Scheduled(fixedDelay = DatabaseConfigConst.DATABASE_HEALTH_CHECK_PERIOD)
    public void checkDatasourceHealth() {
        slaveDataSources.forEach((type, ds) -> {
            CircuitBreaker cb = datasourceSelector.breakerOf(type);
            try {
                cb.executeSupplier(() -> ping(ds));
            } catch (Exception ignored) {

            }
        });
    }

    private boolean ping(DataSource ds) {
        try (Connection c = ds.getConnection();
             Statement st = c.createStatement()) {
            st.execute(DatabaseConfigConst.DATABASE_HEALTH_CHECK_QUERY);
            return true;
        } catch (SQLException e) {
            throw new IllegalStateException("Unhealthy slave", e);
        }
    }
}