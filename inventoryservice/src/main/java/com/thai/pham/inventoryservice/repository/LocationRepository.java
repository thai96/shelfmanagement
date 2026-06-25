package com.thai.pham.inventoryservice.repository;

import com.thai.pham.inventoryservice.entity.Location;
import com.thai.pham.inventoryservice.entity.LocationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
    public Page<Location> findLocationByLocationType(LocationType locationType, Pageable pageable);

    public Page<Location> findLocationByNameContaining(String name, Pageable pageable);
}