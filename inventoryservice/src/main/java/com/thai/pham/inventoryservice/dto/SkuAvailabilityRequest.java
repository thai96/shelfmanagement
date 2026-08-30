package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.BindParam;

import java.util.UUID;

public record SkuAvailabilityRequest(
        @BindParam("sku")
        @Parameter(name = "sku")
        @JsonProperty("sku")
        String sku,
        @BindParam("location_id")
        @Parameter(name = "location_id")
        @JsonProperty("location_id")
        UUID locationId
) {
}
