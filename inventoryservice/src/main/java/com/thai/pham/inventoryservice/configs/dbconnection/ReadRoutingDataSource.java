package com.thai.pham.inventoryservice.configs.dbconnection;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.atomic.AtomicInteger;

public class ReadRoutingDataSource extends AbstractRoutingDataSource {
    private final DatasourceSelector selector;

    @Autowired
    public ReadRoutingDataSource(
        DatasourceSelector selector
    ) {
        this.selector = selector;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        // If @Transactional(readOnly = true), route to a slave
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();

        if(!isReadOnly) {
            return DataSourceType.MASTER;
        }
        
        System.out.println("Routing to -> " + slaveKey);
        return selector.selectDatasource(isReadOnly).orElse(DataSourceType.MASTER);
        
    }
}