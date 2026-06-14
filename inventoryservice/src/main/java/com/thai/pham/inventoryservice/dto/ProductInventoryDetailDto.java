package com.thai.pham.inventoryservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductInventoryDetailDto {
    private UUID productId;
    private String productName;
    private Integer qtyOnHand;
    private Integer qtyReserved;
    private Integer qtyAvailable;
    private LocationDto location;
}