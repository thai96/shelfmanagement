package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.thai.pham.inventoryservice.entity.LocationType;

public record LocationCreationRequest(
        @JsonProperty("name") String locationName,
        @JsonProperty("type") LocationType type
) {
}
