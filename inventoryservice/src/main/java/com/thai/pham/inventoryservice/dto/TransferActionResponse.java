package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thai.pham.inventoryservice.entity.TransferStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record TransferActionResponse(
        @JsonProperty("id") UUID id,
        @JsonProperty("from_location") TransferLocationDetail fromLocationDetail,
        @JsonProperty("to_location") TransferLocationDetail toLocationDetail,
        @JsonProperty("status") TransferStatus status,
        @JsonProperty("items") List<TransferProductDetail> items,
        @JsonProperty("created_at") LocalDateTime createdAt
) {
}
