package com.thai.pham.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.net.URI;

@Data
@Value
@AllArgsConstructor
public class ResourceCreatedResult<T> {
    URI resourceUri;
    T responseBodyContent;
}
