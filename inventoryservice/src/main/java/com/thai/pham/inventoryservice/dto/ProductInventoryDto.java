package com.thai.pham.inventoryservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductInventoryDto {
    private UUID productId;
    private String productName;
    private Integer totalQtyOnHand;
    private Integer totalQtyReserved;
    private Integer totalQtyAvailable;
    private Integer totalSkus;
}