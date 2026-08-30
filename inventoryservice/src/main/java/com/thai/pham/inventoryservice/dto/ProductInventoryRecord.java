package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;
import java.time.LocalDateTime;

public record ProductInventoryRecord(
        @JsonProperty("sku") String sku,
        @JsonProperty("location_id") UUID locationId,
        @JsonProperty("qty_available") Integer qtyAvailable,
        @JsonProperty("updated_at") LocalDateTime updatedAt
) {
}