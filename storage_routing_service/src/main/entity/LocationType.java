package thai.pham.storageroutingservice.entity;

enum LocationType {
    STORE("STORE"),
    WARE_HOUSE("WAREHOUSE");

    private final String sqlValue;

    LocationType(String sqlValue) {
        this.sqlValue = sqlValue;
    }

    @Converter
    public static class LocationTypeConverter implements AttributeConverter<LocationType, String> {
        @Override
        public String convertToDatabaseColumn(LocationType locationType) {
            return locationType.sqlValue;
        }

        @Override
        public LocationType convertToEntityAttribute(String locationType) {
            return switch(locationType.toUpperCase()) {
                LocationType.STORE.sqlValue -> LocationType.STORE;
                LocationType.WARE_HOUSE.sqlValue -> LocationType.WARE_HOUSE;
                else -> null;
            }
        }
    }
}