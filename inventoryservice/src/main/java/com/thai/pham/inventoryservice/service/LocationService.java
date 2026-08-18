package com.thai.pham.inventoryservice.service;

import com.thai.pham.inventoryservice.common.exception.InventoryException;
import com.thai.pham.inventoryservice.common.exception.NotFoundException;
import com.thai.pham.inventoryservice.common.response.ErrorCode;
import com.thai.pham.inventoryservice.dto.*;
import com.thai.pham.inventoryservice.entity.Location;
import com.thai.pham.inventoryservice.mapper.LocationResultMapper;
import com.thai.pham.inventoryservice.mapper.PageDtoMapper;
import com.thai.pham.inventoryservice.repository.InventoryRepository;
import com.thai.pham.inventoryservice.repository.LocationRepository;
import com.thai.pham.inventoryservice.repository.specification.LocationSpecification;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.UUID;


@Service
public class LocationService {
    private final LocationRepository locationRepo;
    private final InventoryRepository inventoryRepository;
    private final LocationResultMapper locationResultMapper;
    private final LocationSpecification locationSpecification;
    private final PageDtoMapper pageDtoMapper;

    @Autowired
    public LocationService(LocationRepository locationRepo, InventoryRepository inventoryRepository, LocationResultMapper locationResultMapper, LocationSpecification locationSpecification, PageDtoMapper pageDtoMapper) {
        this.locationRepo = locationRepo;
        this.locationResultMapper = locationResultMapper;
        this.locationSpecification = locationSpecification;
        this.pageDtoMapper = pageDtoMapper;
        this.inventoryRepository = inventoryRepository;
    }

    public LocationResult createLocation(LocationCreationRequest request) {
        Location newLocation = Location.builder().locationType(request.type()).name(request.locationName()).isActive(false).build();
        Location createdLocation = locationRepo.save(newLocation);
        return locationResultMapper.mapObject(createdLocation);
    }

    public PageDto<LocationResult> findLocation(@ModelAttribute @ParameterObject ListLocationRequest request) {
        Pageable pageRequest = PageRequest.of(request.page(), request.size());
        Specification<Location> querySpec = locationSpecification.containsName(request.searchParams())
                .and(locationSpecification.findWithActive(request.isActive()))
                .and(locationSpecification.findWithType(request.locationType()));
        return pageDtoMapper.mapObject(locationRepo.findAll(querySpec, pageRequest).map(locationResultMapper::mapObject));
    }

    public LocationResult findLocationDetail(UUID locationId) {
        return locationRepo.findById(locationId).map(locationResultMapper::mapObject).orElseThrow(NotFoundException::new);
    }

    public LocationResult updateLocation(UUID locationId, LocationUpdateRequest locationUpdateRequest) {
        Location location = locationRepo.findById(locationId).map(l -> {
            l.setName(locationUpdateRequest.locationName());
            return l;
        }).orElseThrow(NotFoundException::new);

        return locationResultMapper.mapObject(locationRepo.save(location));
    }

    public LocationResult toggleLocationActiveState(UUID locationId, ToggleLocationActiveState activeState) {
        inventoryRepository.checkAvailableInventoryByLocation(locationId).ifPresentOrElse(
                isAvailable -> {
                    if (isAvailable && !activeState.isActive()) {
                        throw new InventoryException(ErrorCode.LOCATION_DELETE_CONFLICT);
                    }
                },
                () -> {
                    throw new NotFoundException();
                }
        );

        Location location = locationRepo.findById(locationId).orElseThrow(NotFoundException::new);
        location.setIsActive(activeState.isActive());
        return locationResultMapper.mapObject(locationRepo.save(location));
    }
}