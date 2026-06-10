package com.thai.pham.storageroutingservice.entity;

enum TransferStatus {
    PENDING("PENDING"), 
    IN_TRANSIT("IN_TRANSIT"), 
    COMPLETED("COMPLETED"), 
    DISCREPANCY("DISCREPANCY");

    private final String statusStringValue;

    @Converter
    public static class TransferStatusConverter implements AttributeConverter<TransferStatus, String> {
        @Override
        public String convertToDatabaseColumn(TransferStatus transferStatus) {
            return transferStatus.statusStringValue;
        }

        @Override
        public TransferStatus convertToEntityAttribute(String transferStatusString) {
            return switch(transferStatusString.toUpperCase()) {
                LocationType.PENDING.sqlValue -> TransferStatus.PENDING;
                LocationType.IN_TRANSIT.sqlValue -> TransferStatus.IN_TRANSIT;
                LocationType.COMPLETED.sqlValue -> TransferStatus.COMPLETED;
                LocationType.DISCREPANCY.sqlValue -> TransferStatus.DISCREPANCY;
                else -> null;
            }
        }
    }    
}