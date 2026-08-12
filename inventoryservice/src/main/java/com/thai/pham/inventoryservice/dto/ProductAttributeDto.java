package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Data
@AllArgsConstructor
@Value
public class ProductAttributeDto {
    @JsonProperty("color")
    String color;
    @JsonProperty("size")
    String size;
}