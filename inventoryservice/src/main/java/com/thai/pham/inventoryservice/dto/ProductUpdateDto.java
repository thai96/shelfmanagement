package com.thai.pham.inventoryservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ProductUpdateDto implements Serializable {
    private UUID id;
    private String productName;
    private String sku;
    private String color;
    private String size;

    private List<InventoryUpdateDto> inventoriesData;
}