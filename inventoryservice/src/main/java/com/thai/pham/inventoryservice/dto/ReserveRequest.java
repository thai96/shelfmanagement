package com.thai.pham.inventoryservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record ReserveRequest(
        @JsonProperty("order_ref") String orderRef,
        @JsonProperty("items") List<OrderDetailItem> orderDetailItems
) {
}
