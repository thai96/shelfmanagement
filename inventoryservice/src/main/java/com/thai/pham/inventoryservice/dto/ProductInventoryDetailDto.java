package com.thai.pham.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ProductInventoryDetailDto implements Serializable {
    private UUID productId;
    private String productName;
    private Boolean isAvailable;
    private String productColor;
    private String size;
    private String sku;
    private List<LocationDto> location;
}