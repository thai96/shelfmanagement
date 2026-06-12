package com.thai.pham.inventoryservice.entity;

import jakarta.persistence.AttributeConverter;

import java.util.HashMap;
import java.util.Map;

public enum ItemChangeReason {
    SALE("SALE"),
    INBOUND("INBOUND"),
    TRANSFER("TRANSFER"),
    LOST("LOST");

    private final String sqlValue;

    ItemChangeReason(String sqlValue) {
        this.sqlValue = sqlValue;
    }

    public static final class ItemChangeReasonConverter implements AttributeConverter<ItemChangeReason, String> {
        private final Map<String, ItemChangeReason> valueMapping = new HashMap<>();

        public ItemChangeReasonConverter() {
            valueMapping.put(ItemChangeReason.SALE.sqlValue, ItemChangeReason.SALE);
            valueMapping.put(ItemChangeReason.INBOUND.sqlValue, ItemChangeReason.INBOUND);
            valueMapping.put(ItemChangeReason.TRANSFER.sqlValue, ItemChangeReason.TRANSFER);
            valueMapping.put(ItemChangeReason.LOST.sqlValue, ItemChangeReason.LOST);
        }

        @Override
        public String convertToDatabaseColumn(ItemChangeReason changeReason) {
            return changeReason.sqlValue;
        }

        @Override
        public ItemChangeReason convertToEntityAttribute(String changeReasonString) {
            return valueMapping.getOrDefault(changeReasonString, null);
        }
    }
}