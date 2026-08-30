package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SaleDeductItem(
        @NotBlank @JsonProperty("sku") String sku,
        @NotNull @JsonProperty("qty") Integer qty
) {
}
