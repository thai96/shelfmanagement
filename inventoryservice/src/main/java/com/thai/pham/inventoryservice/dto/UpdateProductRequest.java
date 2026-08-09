package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateProductRequest {
    @JsonProperty("name")
    private String name;
    @JsonProperty("attributes")
    private ProductAttributeDto attributes;
}
