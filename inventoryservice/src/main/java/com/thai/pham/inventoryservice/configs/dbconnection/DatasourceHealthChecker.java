package com.thai.pham.inventoryservice.configs.dbconnection;

import com.thai.pham.inventoryservice.common.DatabaseConfigConst;

@Component
public class DatasourceHealthChecker {
    private final DatasourceSelector datasourceSelector;
    private final Map<DataSourceType, DataSource> slaveDataSources;

    @Autowired
    public DatasourceHealthChecker(DatasourceSelector datasourceSelector, Map<DataSourceType, DataSource> slaveDataSources) {
        this.datasourceSelector = datasourceSelector;
        this.slaveDataSources = slaveDataSources;
    }

    @Scheduled(fixedDelay = DatabaseConfigConst.DATABASE_HEALTH_CHECK_PERIOD)
    public void checkDatasourceHealth() {
        slaveDataSources.forEach(type -> {
            CircuitBreaker cb = datasourceSelector.breakerOf(type);
            try {
                cb.executeSupplier(() -> ping(ds));
            } catch(Exception ignored) {
                
            }
        });
    }

    private boolean ping(DataSource ds) {
        try(Connection c = ds.getConnection();
            Statement st = c.createStatement()) {
            st.execute(DatabaseConfigConst.DATABASE_HEALTH_CHECK_QUERY);
            return true;
        } catch(SQLException e) {
            throw new IllegalStateException("Unhealthy slave", e);
        }
    }
}