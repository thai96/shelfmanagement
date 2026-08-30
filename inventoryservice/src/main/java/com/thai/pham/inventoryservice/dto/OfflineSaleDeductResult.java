package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OfflineSaleDeductResult(
        @JsonProperty("reference") String reference,
        @JsonProperty("items") List<OfflineDeductItemResult> items
) {
}
