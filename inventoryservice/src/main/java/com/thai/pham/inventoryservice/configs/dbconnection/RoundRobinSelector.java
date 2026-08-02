package com.thai.pham.inventoryservice.configs.dbconnection;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RoundRobinSelector implements DataSourceSelector {
    private final CircuitBreakerRegistry registry;
    private final List<DataSourceType> selectTypeList;
    private final AtomicInteger selectionCounter = new AtomicInteger(0);

    @Autowired
    public RoundRobinSelector(@Qualifier("roundRobinConfig") CircuitBreakerConfig config, List<DataSourceType> selectTypeList) {
        this.registry = CircuitBreakerRegistry.of(config);
        selectTypeList.forEach(t -> registry.circuitBreaker(t.name()));
        this.selectTypeList = selectTypeList;
    }
    
    @Override
    public Optional<DataSourceType> selectDatasource(boolean isReadOnly) {
        List<DataSourceType> healthy = selectTypeList.stream().filter(s -> {
            CircuitBreaker.State circuitState = registry.circuitBreaker(s.name()).getState();
            return circuitState != CircuitBreaker.State.OPEN;
        }).toList();
        if(healthy.isEmpty()) return Optional.empty();
        int idx = Math.abs(selectionCounter.getAndIncrement() % selectTypeList.size());
        return Optional.of(healthy.get(idx));
    }

    @Override
    public CircuitBreaker breakerOf(DataSourceType type) {
        return registry.circuitBreaker(type.name());
    }
}