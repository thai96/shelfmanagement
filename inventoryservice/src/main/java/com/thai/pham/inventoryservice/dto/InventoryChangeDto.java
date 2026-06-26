package com.thai.pham.inventoryservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
public class InventoryChangeDto{
    UUID itemId;
    int changeQty;
    InventoryChangeType changeType;
}