package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;

public record CreateTransferRequest(
        @JsonProperty("from_location_id") @NotNull UUID fromLocation,
        @JsonProperty("to_location_id") @NotNull UUID toLocation,
        @JsonProperty("items") List<TransferDetail> transferDetails
) {
}
