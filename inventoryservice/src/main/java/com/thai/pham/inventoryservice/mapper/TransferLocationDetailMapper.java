package com.thai.pham.inventoryservice.mapper;

import com.thai.pham.inventoryservice.dto.TransferLocationDetail;
import com.thai.pham.inventoryservice.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class TransferLocationDetailMapper implements BaseMapper<Location, TransferLocationDetail> {
    @Override
    public Location mapEntity(TransferLocationDetail locationDetail) {
        return null;
    }

    @Override
    public TransferLocationDetail mapObject(Location location) {
        return TransferLocationDetail.builder()
                .locationId(location.getId())
                .locationType(location.getLocationType())
                .locationName(location.getName())
                .build();
    }
}
