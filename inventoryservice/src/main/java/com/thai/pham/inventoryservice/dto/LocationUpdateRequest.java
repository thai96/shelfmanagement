package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record LocationUpdateRequest(
        @NotBlank @JsonProperty("name") String locationName
) {
}
