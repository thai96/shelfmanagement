package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OfflineDeductItemResult(
        @JsonProperty("sku") String sku,
        @JsonProperty("qty_deducted") Integer qtyDeducted,
        @JsonProperty("qty_on_hand_after") Integer qtyOnHandAfter,
        @JsonProperty("qty_available_after") Integer qtyAvailableAfter
) {
}
