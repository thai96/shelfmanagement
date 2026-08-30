package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DeductDetailItemRequest(
        @JsonProperty("sku") String sku,
        @JsonProperty("qtu") Integer qty
) {
}
