package com.thai.pham.inventoryservice.dto;

@Data
@AllArgsConstructor
class StockTransferDto {
    UUID transferId;
    TransferStatus status;
    LocationDto from;
    LocationDto to;
}