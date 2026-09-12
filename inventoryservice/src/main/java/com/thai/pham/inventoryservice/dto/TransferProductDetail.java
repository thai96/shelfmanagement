package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record TransferProductDetail(
        @JsonProperty("product_id") UUID productId,
        @JsonProperty("sku") String sku,
        @JsonProperty("product_name") String productName,
        @JsonProperty("shipped_qty") Integer shippedQty
) {
}
