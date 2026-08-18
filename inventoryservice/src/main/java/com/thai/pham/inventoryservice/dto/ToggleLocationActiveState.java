package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;

public record ToggleLocationActiveState(
        @NonNull @JsonProperty("is_active") Boolean isActive
) {
    public ToggleLocationActiveState(Boolean isActive) {
        this.isActive = isActive != null && isActive;
    }
}
