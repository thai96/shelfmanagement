package com.thai.pham.inventoryservice.controller;

import com.thai.pham.inventoryservice.dto.*;
import com.thai.pham.inventoryservice.service.LocationService;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/locations/")
public class LocationManagementController {
    private final LocationService locationService;

    @Autowired
    public LocationManagementController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<LocationResult> createLocation(@RequestBody LocationCreationRequest request) {
        LocationResult createdResult = locationService.createLocation(request);
        URI locationUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdResult.id())
                .toUri();
        return ResponseEntity.created(locationUri).body(createdResult);
    }

    @GetMapping
    public PageDto<LocationResult> getLocationPage(@ParameterObject @ModelAttribute ListLocationRequest listLocationRequest) {
        return locationService.findLocation(listLocationRequest);
    }

    @GetMapping("/{location-id}")
    public ResponseEntity<LocationResult> getLocationDetail(@PathVariable(name = "location-id") UUID locationId) {
        return ResponseEntity.ok(locationService.findLocationDetail(locationId));
    }

    @PutMapping("/{location-id}")
    public ResponseEntity<LocationResult> updateLocation(@PathVariable(name = "location-id") UUID locationId, @Valid @RequestBody LocationUpdateRequest locationUpdateRequest) {
        return ResponseEntity.ok(locationService.updateLocation(locationId, locationUpdateRequest));
    }

    @PatchMapping("/{location-id}/activate")
    public ResponseEntity<LocationResult> toggleLocationActiveState(@PathVariable(name = "location-id") UUID locationId, @RequestBody ToggleLocationActiveState toggleRequest) {
        return ResponseEntity.ok(locationService.toggleLocationActiveState(locationId, toggleRequest));
    }
}
