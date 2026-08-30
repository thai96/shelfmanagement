package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record SaleDeductRequest(
        @NotNull @JsonProperty("location_id") UUID locationId,
        @NotBlank @JsonProperty("reference") String reference,
        @JsonProperty("items") List<SaleDeductItem> saleDeductItemList
) {
}
