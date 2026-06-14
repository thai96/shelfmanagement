package com.thai.pham.inventoryservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
public class ProductInventoryDetailDto implements Serializable {
    private UUID productId;
    private String productName;
    private Boolean isAvailable;
    private String productColor;
    private String size;
    private List<LocationDto> location;
}