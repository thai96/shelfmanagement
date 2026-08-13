package com.thai.pham.ledgerservice.feature.read.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public record PageMetadata(
    @JsonProperty("page") Long page,
    @JsonProperty("size") Long size,
    @JsonProperty("total_records") Long totalRecords,
    @JsonProperty("total_pages") Long totalPages
) {}