package com.thai.pham.inventoryservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class ProductInventoryDto implements Serializable {
    private UUID productId;
    private String productName;
    private Integer totalQtyOnHand;
    private Integer totalQtyReserved;
    private Integer totalQtyAvailable;
    private Integer totalSkus;
}