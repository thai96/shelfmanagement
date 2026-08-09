package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductResult implements Serializable {
    @JsonProperty("id")
    private UUID id;
    @JsonProperty("name")
    private String productName;
    @JsonProperty("sku")
    private String sku;
    @JsonProperty("attributes")
    private ProductAttributeDto attributes;
    @JsonProperty("create_at")
    private String createAt;
    @JsonProperty("update_at")
    private String updateAt;
}