package com.thai.pham.storageroutingservice.dto;

@Data
public class ProductInfoDto {
    //private UUID id;
    private String productName;
    private List<SkuDto> variations;
}