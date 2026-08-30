package com.thai.pham.inventoryservice.dto;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.BindParam;

import java.util.UUID;

public record ListInventoryRequest(
        @BindParam("location_id")
        @Parameter(name = "location_id")
        UUID locationId,
        @BindParam("product_id")
        @Parameter(name = "product_id")
        UUID productId,
        @BindParam("sku")
        @Parameter(name = "sku")
        String sku
) {
}
