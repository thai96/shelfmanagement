package com.thai.pham.inventoryservice.service;

import com.thai.pham.inventoryservice.entity.Location;
import com.thai.pham.inventoryservice.entity.LocationType;
import com.thai.pham.inventoryservice.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class LocationService {
    private final LocationRepository locationRepo;

    @Autowired
    public LocationService(LocationRepository locationRepo) {
        this.locationRepo = locationRepo;
    }

    public List<Location> getAllLocation() {
        return locationRepo.findAll();
    }

    public Page<Location> getWareHouseLocation(Pageable pageable) {
        return locationRepo.findLocationByLocationType(LocationType.WARE_HOUSE, pageable);
    }

    public Page<Location> getStoreLocation(Pageable pageable) {
        return locationRepo.findLocationByLocationType(LocationType.STORE, pageable);
    }

    public Page<Location> getLocationByName(String input, Pageable pageable) {
        return locationRepo.findLocationByNameContaining(input, pageable);
    }
}