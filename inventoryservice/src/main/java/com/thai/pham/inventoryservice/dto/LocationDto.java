package com.thai.pham.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class LocationDto implements Serializable {
    String name;
    String locationType;
    Boolean isActive;
}
