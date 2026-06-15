package com.thai.pham.inventoryservice.mapper;

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