package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;

import java.util.UUID;

public record TransferDetail(
        @JsonProperty("product_id") UUID productId,
        @JsonProperty("qty") @Min(1) Integer transferQty
) {
}
