package com.thai.pham.ledgerservice.feature.write.kafka;

public enum MessageStatus {
    RECEIVED,
    PROCESSING,
    COMPLETED,
    QUARANTINED;
}