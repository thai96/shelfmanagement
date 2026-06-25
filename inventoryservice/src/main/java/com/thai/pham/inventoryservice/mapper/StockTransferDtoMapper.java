package com.thai.pham.inventoryservice.mapper;

@Component
public class StockTransferDtoMapper {
    private final LocationDtoMapper locationMapper;

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
            stockTransferDto.getId(),
            stockTransferDto.getStatus(),
        );
        Optional.ofNullable(fromEntity).ifPresent(transfer::setFromLocation);
        Optional.ofNullable(toEntity).ifPresent(transfer::setToLocation);
        return transfer;
    }
}