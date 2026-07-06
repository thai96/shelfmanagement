package com.thai.pham.inventoryservice.mapper;

import com.thai.pham.inventoryservice.dto.LocationDto;
import com.thai.pham.inventoryservice.dto.StockTransferDto;
import com.thai.pham.inventoryservice.entity.Location;
import com.thai.pham.inventoryservice.entity.StockTransfer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StockTransferDtoMapper {
    private final LocationDtoMapper locationMapper;

    @Autowired
    public StockTransferDtoMapper(LocationDtoMapper locationMapper) {
        this.locationMapper = locationMapper;
    }

    public StockTransferDto mapObject(StockTransfer stockTransferEntity) {
        LocationDto from = locationMapper.mapObject(stockTransferEntity.getFromLocation());
        LocationDto to = locationMapper.mapObject(stockTransferEntity.getToLocation());

        return new StockTransferDto(
            stockTransferEntity.getId(),
            stockTransferEntity.getTransferStatus(),
            from,
            to
        );
    }

    public StockTransfer mapEntity(StockTransferDto stockTransferDto) {
        Location fromEntity = locationMapper.mapEntity(stockTransferDto.getFrom());
        Location toEntity = locationMapper.mapEntity(stockTransferDto.getTo());

        StockTransfer transfer = new StockTransfer(
            stockTransferDto.getTransferId(),
            stockTransferDto.getStatus()
        );
        Optional.ofNullable(fromEntity).ifPresent(transfer::setFromLocation);
        Optional.ofNullable(toEntity).ifPresent(transfer::setToLocation);
        return transfer;
    }
}