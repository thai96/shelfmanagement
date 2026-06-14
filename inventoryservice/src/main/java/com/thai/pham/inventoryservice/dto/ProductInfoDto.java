package com.thai.pham.inventoryservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductInfoDto {
    //private UUID id;
    private String productName;
    private List<SkuDto> variations;
}