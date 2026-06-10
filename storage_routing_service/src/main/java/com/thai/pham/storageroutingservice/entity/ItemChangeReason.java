package com.thai.pham.storageroutingservice.entity;

enum ItemChangeReason {
    SALE("SALE"), 
    INBOUND("INBOUND"), 
    TRANSFER("TRANSFER"), 
    LOST("LOST");

    private final String sqlValue;

    ItemChangeReason(String sqlValue) {
        this.sqlValue = sqlValue;
    }

    public static final class ItemChangeReasonConverter implements AttributeConverter<ItemChangeReason, String> {
        @Override
        public String convertToDatabaseColumn(ItemChangeReason changeReason) {
            return changeReason.sqlValue;
        }

        @Override
        public ItemChangeReason convertToEntityAttribute(String changeReasonString) {
            return switch(changeReasonString.toUpperCase()) {
                ItemChangeReason.SALE.sqlValue -> ItemChangeReason.SALE;
                ItemChangeReason.INBOUND.sqlValue -> ItemChangeReason.INBOUND;
                ItemChangeReason.TRANSFER.sqlValue -> ItemChangeReason.TRANSFER;
                ItemChangeReason.LOST.sqlValue -> ItemChangeReason.LOST;
            } 
        }
    }
}