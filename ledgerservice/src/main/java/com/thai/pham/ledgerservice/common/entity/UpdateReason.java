package com.thai.pham.ledgerservice.common.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.HashMap;
import java.util.Map;

public enum UpdateReason {
    SALE("SALE"),
    INBOUND("INBOUND"),
    TRANSFER("TRANSFER"),
    LOST("LOST");

    private final String reasonStringValue;

    public UpdateReason(String reasonStringValue) {
        this.reasonStringValue = reasonStringValue;
    }

    @Converter
    public static class UpdateReasonConverter implements AttributeConverter<UpdateReason, String> {
        private final Map<String, UpdateReason> valueMapping = new HashMap<>();

        public UpdateReasonConverter() {
            valueMapping.put(UpdateReason.SALE.reasonStringValue, UpdateReason.SALE);
            valueMapping.put(UpdateReason.INBOUND.reasonStringValue, UpdateReason.INBOUND);
            valueMapping.put(UpdateReason.TRANSFER.reasonStringValue, UpdateReason.TRANSFER);
            valueMapping.put(UpdateReason.LOST.reasonStringValue, UpdateReason.LOST);
        }

        @Override
        public String convertToDatabaseColumn(UpdateReason updateReason) {
            return updateReason.reasonStringValue;
        }

        @Override
        public UpdateReason convertToEntityAttribute(String updateReasonString) {
            return valueMapping.getOrDefault(updateReasonString, null);
        }
    }
}