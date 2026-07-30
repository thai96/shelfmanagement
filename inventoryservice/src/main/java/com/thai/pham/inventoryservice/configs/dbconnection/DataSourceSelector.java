package com.thai.pham.inventoryservice.configs.dbconnection;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;

import java.util.Optional;

public interface DataSourceSelector {
    public Optional<DataSourceType> selectDatasource(boolean isReadOnly);
    public CircuitBreaker breakerOf(DataSourceType type);
}