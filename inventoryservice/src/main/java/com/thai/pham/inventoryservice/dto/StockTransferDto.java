package com.thai.pham.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

import com.thai.pham.inventoryservice.entity.TransferStatus;

@Data
@AllArgsConstructor
public class StockTransferDto {
    UUID transferId;
    TransferStatus status;
    LocationDto from;
    LocationDto to;
}