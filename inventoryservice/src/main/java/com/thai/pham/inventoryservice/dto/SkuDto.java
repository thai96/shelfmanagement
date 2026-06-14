package com.thai.pham.inventoryservice.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class SkuDto implements Serializable {
    private String sku;
    private String color;
    private String size;
}