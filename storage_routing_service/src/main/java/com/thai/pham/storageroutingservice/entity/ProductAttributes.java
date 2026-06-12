package com.thai.pham.storageroutingservice.entity;

import jakarta.persistence.AttributeConverter;
import lombok.Data;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

@Data
public class ProductAttributes {
    private String color;
    private String size;

    public static final class ProductAttributesConverter implements AttributeConverter<ProductAttributes, String> {
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public String convertToDatabaseColumn(ProductAttributes productAttribute) {
            try {
                return productAttribute == null ? null : objectMapper.writeValueAsString(productAttribute);
            } catch (JacksonException e) {
                throw new IllegalArgumentException("Error converting object to JSON string", e);
            }
        }


        @Override
        public ProductAttributes convertToEntityAttribute(String dbData) {
            try {
                return dbData == null ? null : objectMapper.readValue(dbData, ProductAttributes.class);
            } catch (JacksonException e) {
                throw new IllegalArgumentException("Error converting JSON string to object", e);
            }
        }
    }
}