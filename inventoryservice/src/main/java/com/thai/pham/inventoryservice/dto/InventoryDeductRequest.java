package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record InventoryDeductRequest(
        @JsonProperty("location_id") UUID locationId,
        @JsonProperty("reference") String ref,
        @JsonProperty("items") List<DeductDetailItemRequest> deductDetailItemRequests
) {
}
