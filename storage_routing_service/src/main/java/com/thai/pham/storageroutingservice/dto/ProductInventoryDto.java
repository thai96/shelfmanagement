package com.thai.pham.storageroutingservice.dto;

@Data
public class ProductInventoryDto {
    private UUID productId;
    private String productName;
    private Integer totalQtyOnHand;
    private Integer totalQtyReserved;
    private Integer totalQtyAvailable;
    private Integer totalSkus;
}