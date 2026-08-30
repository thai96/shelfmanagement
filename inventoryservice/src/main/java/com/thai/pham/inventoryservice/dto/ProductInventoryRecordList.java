package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ProductInventoryRecordList(
        @JsonProperty("result")
        List<ProductInventoryRecord> records
) {
}
