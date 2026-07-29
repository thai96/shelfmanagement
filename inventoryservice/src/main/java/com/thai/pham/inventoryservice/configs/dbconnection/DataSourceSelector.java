package com.thai.pham.inventoryservice.configs.dbconnection;

public interface DatasourceSelector {
    public Optional<DataSource> selectDatasource(boolean isReadOnly);
    public CircuitBreaker breakerOf(DataSourceType type);
}