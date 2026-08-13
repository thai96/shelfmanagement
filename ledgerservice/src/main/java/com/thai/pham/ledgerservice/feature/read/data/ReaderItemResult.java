package com.thai.pham.ledgerservice.feature.read.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.UUID;
import java.time.LocalDateTime;

@Value
public record ReaderItemResult(
    @JsonProperty("id") UUID id,
    @JsonProperty("location_id") UUID locationId,
    @JsonProperty("product_id") UUID productId,
    @JsonProperty("qty_change") Integer qtyChange,
    @JsonProperty("reason") UpdateReason updateReason,
    @JsonProperty("ref_id") String refId,
    @JsonProperty("created_at") LocalDateTime createdAt
) {}