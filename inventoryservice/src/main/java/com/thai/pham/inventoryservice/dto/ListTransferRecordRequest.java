package com.thai.pham.inventoryservice.dto;

import com.thai.pham.inventoryservice.entity.TransferStatus;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.BindParam;

import java.time.LocalDateTime;
import java.util.UUID;

public record ListTransferRecordRequest(
        @BindParam("status")
        @Parameter(name = "status")
        TransferStatus transferStatus,
        @BindParam("from_location_id")
        @Parameter(name = "from_location_id")
        UUID fromLocationId,
        @BindParam("to_location_id")
        @Parameter(name = "to_location_id")
        UUID toLocationId,
        @BindParam("from_date")
        @Parameter(name = "from_date")
        LocalDateTime fromDate,
        @BindParam("to_date")
        @Parameter(name = "to_date")
        LocalDateTime toDate
) {
}
