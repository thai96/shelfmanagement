package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record OrderDetailItem(
        @JsonProperty("sku") String sku,
        @JsonProperty("qty") Integer reserveQty,
        @JsonProperty("location_id") UUID locationId
) {
}
