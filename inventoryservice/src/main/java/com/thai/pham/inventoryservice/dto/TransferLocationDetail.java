package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thai.pham.inventoryservice.entity.LocationType;
import lombok.Builder;

import java.util.UUID;

@Builder
public record TransferLocationDetail(
        @JsonProperty("id") UUID locationId,
        @JsonProperty("name") String locationName,
        @JsonProperty("type") LocationType locationType
) {
}
