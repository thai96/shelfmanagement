package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductAttributeDto {
    @JsonProperty("color")
    private String color;
    @JsonProperty("size")
    private String size;
}
