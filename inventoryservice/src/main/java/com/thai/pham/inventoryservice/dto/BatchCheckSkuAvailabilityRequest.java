package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BatchCheckSkuAvailabilityRequest(
        @JsonProperty("items")
        List<SkuAvailabilityRequest> items
) {
}
