package com.thai.pham.ledgerservice.common.idempotent.entity;

public enum IdempotencyStatus {
    PROCESSING,
    COMPLETED,
    FAILED,
}