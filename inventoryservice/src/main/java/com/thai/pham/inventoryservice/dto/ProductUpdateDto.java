package com.thai.pham.inventoryservice.dto;

import lombok.Data;

import java.io.Serializable;

public class ProductUpdateDto implements Serializable {
    private String productName;
    private String sku;
    private String color;
    private String size;

    private List<InventoryUpdateDto> inventoriesData;
}