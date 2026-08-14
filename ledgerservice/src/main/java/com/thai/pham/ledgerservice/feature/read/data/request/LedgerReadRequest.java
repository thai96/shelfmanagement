package com.thai.pham.ledgerservice.feature.read.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NonNull;

import java.util.UUID;
import java.time.LocalDateTime;

import com.thai.pham.ledgerservice.common.data.entity.UpdateReason;

@Data
public class LedgerReadRequest {
    @JsonProperty("location_id") 
    @NonNull
    UUID locationId,
    @JsonProperty("product_id") 
    @NonNull
    UUID productId,
    @JsonProperty("start_date")
    LocalDateTime startDate;
    @JsonProperty("end_date")
    LocalDateTime endDate;
    @JsonProperty("reason")
    UpdateReason updateReason;
    @JsonProperty("page")
    @Min(0)
    Integer page = 0;
    @JsonProperty("size")
    @Min(10)
    Integer size = 20;
}