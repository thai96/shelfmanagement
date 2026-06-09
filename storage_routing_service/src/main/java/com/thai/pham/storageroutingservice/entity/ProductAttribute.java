package com.thai.pham.storageroutingservice.entity;

@Data
class ProductAttributes {
    private String color;
    private Size size;

    public static final class ProductAttributesConverter implements AttributeConverter<ProductAttributes, String> {
        private final ObjectMapper mapper = new ObjectMapper();
        
        @Override
        public String convertToDatabaseColumn(ProductAttributes productAttribute) {
            try {
                return productAttribute == null ? null : objectMapper.writeValueAsString(productAttribute);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Error converting object to JSON string", e);
            }
        }


        @Override
        public MyCustomObject convertToEntityAttribute(String dbData) {
            try {
                return dbData == null ? null : objectMapper.readValue(dbData, ProductAttributes.class);
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Error converting JSON string to object", e);
            }
        }
    }
}