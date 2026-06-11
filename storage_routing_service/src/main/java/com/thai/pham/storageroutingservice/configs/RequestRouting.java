package com.thai.pham.storageroutingservice.configs;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class ReadRoutingDataSource extends AbstractRoutingDataSource {
    private final AtomicInteger counter = new AtomicInteger(0);
    private final int slaveCount = 5;

    @Override
    protected Object determineCurrentLookupKey() {
        // If @Transactional(readOnly = true), route to a slave
        if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
            int index = Math.abs(counter.getAndIncrement() % slaveCount) + 1;
            String slaveKey = "slave" + index;
            System.out.println("Routing to -> " + slaveKey);
            return slaveKey;
        }
        
        // Otherwise, route to Master (Writes)
        System.out.println("Routing to -> master");
        return "master";
    }
}