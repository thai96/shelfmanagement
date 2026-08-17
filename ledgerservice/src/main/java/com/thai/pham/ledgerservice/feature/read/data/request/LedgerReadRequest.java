package com.thai.pham.ledgerservice.feature.read.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NonNull;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.core.annotations.ParameterObject;

import java.util.UUID;
import java.time.LocalDateTime;

import com.thai.pham.ledgerservice.common.data.entity.UpdateReason;

@Data
@ParameterObject
public class LedgerReadRequest {
    @Parameter(name = "location_id") 
    @NonNull
    UUID locationId,
    @Parameter(name = "product_id") 
    @NonNull
    UUID productId,
    @Parameter(name = "start_date")
    LocalDateTime startDate;
    @Parameter(name = "end_date")
    LocalDateTime endDate;
    @Parameter(name = "reason")
    UpdateReason updateReason;
    @Parameter(name = "page")
    @Min(0)
    Integer page = 0;
    @Parameter(name = "size")
    @Min(10)
    Integer size = 20;
}