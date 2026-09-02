package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ReservationSuccessResult(
        @JsonProperty("order_ref") String orderRef,
        @JsonProperty("items") List<OrderDetailItem> orderDetailItems
) {
}
