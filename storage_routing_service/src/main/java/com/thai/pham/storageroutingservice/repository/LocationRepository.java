package com.thai.pham.storageroutingservice.repository;

import com.thai.pham.storageroutingservice.entity.Location;
import com.thai.pham.storageroutingservice.entity.LocationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location, UUID> {
    public Page<Location> findLocationByLocationType(LocationType locationType, Pageable pageable);

    public Page<Location> findLocationByNameContaining(String name, Pageable pageable);
}