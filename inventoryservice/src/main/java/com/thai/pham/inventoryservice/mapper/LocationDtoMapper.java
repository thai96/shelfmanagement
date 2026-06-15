package com.thai.pham.inventoryservice.mapper;

import com.thai.pham.inventoryservice.dto.LocationDto;
import com.thai.pham.inventoryservice.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationDtoMapper {
    public LocationDto mapObject(Location location) {
        return new LocationDto(
            location.getName(),
            location.getLocationType().toString(),
            location.getIsActive()
        );
    }
}