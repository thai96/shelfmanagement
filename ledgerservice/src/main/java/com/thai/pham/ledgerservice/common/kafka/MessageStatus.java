package com.thai.pham.ledgerservice.common.kafka;

public enum MessageStatus {
    RECEIVED,
    PROCESSING,
    COMPLETED,
    QUARANTINED;
}