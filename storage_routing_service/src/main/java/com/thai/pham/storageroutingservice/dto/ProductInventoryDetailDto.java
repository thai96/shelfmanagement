package com.thai.pham.storageroutingservice.dto;

@Data
public class ProductInventoryDto {
    private UUID productId;
    private String productName;
    private Integer qtyOnHand;
    private Integer qtyReserved;
    private Integer qtyAvailable;
    private LocationDto location;
}