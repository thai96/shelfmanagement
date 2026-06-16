package com.thai.pham.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
public class LocationDto implements Serializable {
    UUID id;
    String name;
    String locationType;
    Boolean isActive;
}
