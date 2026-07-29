package com.thai.pham.inventoryservice.configs.dbconnection;

import org.springframework.beans.factory.annotation.Autowired;
import io.github.resilience4j.circuitbreaker.CircuitBreaker.State;

@Component
public class RoundRobinSelector implements DatasourceSelector {
    private final CircuitBreakerRegistry registry;
    private final List<DataSourceType> selectTypeList;
    private final AtomicInteger selectionCounter;

    @Autowired
    public RoundRobinSelector(CircuitBreakerConfig config, List<DataSourceType> selectTypeList) {
        this.registry = CircuitBreakerRegistry.of(config);
        selectTypeList.forEach(t -> registry.circuitBreaker(t.name()));
        this.selectTypeList = selectTypeList;
    }
    
    @Override
    public Optional<DataSource> selectDatasource(boolean isReadOnly) {
        List<DataSourceType> healthy = slaves.stream().filter(s -> {
            CircuitBreaker.State circuitState = registry.circuitBreaker(s.name()).getState();
            return state != CircuitBreaker.State.OPEN;
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