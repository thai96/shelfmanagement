package com.thai.pham.inventoryservice.mapper;

import com.thai.pham.inventoryservice.dto.LocationDto;
import com.thai.pham.inventoryservice.entity.Location;
import org.springframework.stereotype.Component;

@Component
public class LocationDtoMapper {
    private LocationType.LocationTypeConverter typeConverter;

    @Autowired
    public LocationDtoMapper(LocationType.LocationTypeConverter typeConverter) {
        this.typeConverter = typeConverter;
    }

    public LocationDto mapObject(Location location) {
        return new LocationDto(
            location.getId(),
            location.getName(),
            typeConverter.convertToDatabaseColumn(location.getLocationType()),
            location.getIsActive()
        );
    }

    public Location mapEntity(LocationDto locationDto) {
        LocationType type = typeConverter.convertToEntityAttribute(locationDto.getLocationType());
        return new Location(
            locationDto.getId(),
        );   
    }
}