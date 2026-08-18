package com.thai.pham.inventoryservice.mapper;

import com.thai.pham.inventoryservice.dto.LocationResult;
import com.thai.pham.inventoryservice.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationResultMapper implements BaseMapper<Location, LocationResult> {

    @Override
    public Location mapEntity(LocationResult locationResult) {
        return Location.builder()
                .id(locationResult.id())
                .name(locationResult.locationName())
                .locationType(locationResult.type())
                .createdAt(locationResult.createdAt())
                .updatedAt(locationResult.updatedAt())
                .isActive(locationResult.isActive())
                .build();
    }

    @Override
    public LocationResult mapObject(Location location) {
        return LocationResult.builder()
                .id(location.getId())
                .locationName(location.getName())
                .type(location.getLocationType())
                .createdAt(location.getCreatedAt())
                .updatedAt(location.getUpdatedAt())
                .isActive(location.getIsActive())
                .build();
    }
}
