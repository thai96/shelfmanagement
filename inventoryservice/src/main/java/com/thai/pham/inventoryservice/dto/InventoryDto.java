package com.thai.pham.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
public class InventoryDto implements Serializable {
    private UUID id;
    private LocationDto location;
    private ProductDto product;
    private Integer qtyAvailable;
    private Integer qtyOnHand;
    private Integer qtyReserved;
}