package com.thai.pham.inventoryservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class InventoryUpdateDto implements Serializable {
    private UUID id;
    private LocationDto location;
    private Integer qtyAvailable;
    private Integer qtyOnHand;
    private Integer qtyReserved;
}