package com.thai.pham.inventoryservice.mapper;

import com.thai.pham.inventoryservice.dto.TransferActionResponse;
import com.thai.pham.inventoryservice.entity.StockTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferResponseMapper implements BaseMapper<StockTransfer, TransferActionResponse> {
    private final TransferLocationDetailMapper locationDetailMapper;

    @Autowired
    public TransferResponseMapper(TransferLocationDetailMapper locationDetailMapper) {
        this.locationDetailMapper = locationDetailMapper;
    }

    @Override
    public StockTransfer mapEntity(TransferActionResponse transferResponseMapper) {
        return null;
    }

    @Override
    public TransferActionResponse mapObject(StockTransfer stockTransfer) {
        return TransferActionResponse.builder()
                .id(stockTransfer.getId())
                .fromLocationDetail(locationDetailMapper.mapObject(stockTransfer.getFromLocation()))
                .toLocationDetail(locationDetailMapper.mapObject(stockTransfer.getToLocation()))
                .status(stockTransfer.getTransferStatus())
                .createdAt(stockTransfer.getCreatedAt())
                .build();
    }
}
