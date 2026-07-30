package com.thai.pham.inventoryservice.configs.dbconnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class ReadRoutingDataSource extends AbstractRoutingDataSource {
    private final DataSourceSelector selector;

    @Autowired
    public ReadRoutingDataSource(
        DataSourceSelector selector
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
        
        return selector.selectDatasource(true).orElse(DataSourceType.MASTER);
        
    }
}