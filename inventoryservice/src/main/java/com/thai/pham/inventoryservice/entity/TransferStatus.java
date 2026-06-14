package com.thai.pham.inventoryservice.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.HashMap;
import java.util.Map;

public enum TransferStatus {
    PENDING("PENDING"),
    IN_TRANSIT("IN_TRANSIT"),
    COMPLETED("COMPLETED"),
    DISCREPANCY("DISCREPANCY");

    private final String statusStringValue;

    TransferStatus(String statusStringValue) {
        this.statusStringValue = statusStringValue;
    }

    @Converter
    public static class TransferStatusConverter implements AttributeConverter<TransferStatus, String> {
        private final Map<String, TransferStatus> valueMapping = new HashMap<>();

        public TransferStatusConverter() {
            valueMapping.put(TransferStatus.PENDING.statusStringValue, TransferStatus.PENDING);
            valueMapping.put(TransferStatus.IN_TRANSIT.statusStringValue, TransferStatus.IN_TRANSIT);
            valueMapping.put(TransferStatus.COMPLETED.statusStringValue, TransferStatus.COMPLETED);
            valueMapping.put(TransferStatus.DISCREPANCY.statusStringValue, TransferStatus.DISCREPANCY);
        }

        @Override
        public String convertToDatabaseColumn(TransferStatus transferStatus) {
            return transferStatus.statusStringValue;
        }

        @Override
        public TransferStatus convertToEntityAttribute(String transferStatusString) {
            return valueMapping.getOrDefault(transferStatusString, null);
        }
    }
}