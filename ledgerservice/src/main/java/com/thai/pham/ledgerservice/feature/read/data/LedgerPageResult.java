package com.thai.pham.ledgerservice.feature.read.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public record LedgerPageResult(
    @JsonProperty("meta") PageMetadata pageMetadata,
    @JsonProperty("data") List<ReaderItemResult> data
) {}