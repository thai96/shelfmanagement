package com.thai.pham.inventoryservice.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.HashMap;
import java.util.Map;

public enum LocationType {
    STORE("STORE"),
    WARE_HOUSE("WAREHOUSE");

    private final String sqlValue;

    LocationType(String sqlValue) {
        this.sqlValue = sqlValue;
    }

    @Converter
    public static class LocationTypeConverter implements AttributeConverter<LocationType, String> {
        private final Map<String, LocationType> valueMapping = new HashMap<>();

        public LocationTypeConverter() {
            valueMapping.put(LocationType.STORE.sqlValue, LocationType.STORE);
            valueMapping.put(LocationType.WARE_HOUSE.sqlValue, LocationType.WARE_HOUSE);
        }

        @Override
        public String convertToDatabaseColumn(LocationType locationType) {
            return locationType.sqlValue;
        }

        @Override
        public LocationType typeConverter(String locationType) {
            return valueMapping.getOrDefault(locationType, null);
        }
    }
}