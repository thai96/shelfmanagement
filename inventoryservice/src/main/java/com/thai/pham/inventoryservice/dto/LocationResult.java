package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thai.pham.inventoryservice.entity.LocationType;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record LocationResult(
        @JsonProperty("id") UUID id,
        @JsonProperty("name") String locationName,
        @JsonProperty("type") LocationType type,
        @JsonProperty("is_active") Boolean isActive,
        @JsonProperty("created_at") LocalDateTime createdAt,
        @JsonProperty("updated_at") LocalDateTime updatedAt
) {
}
