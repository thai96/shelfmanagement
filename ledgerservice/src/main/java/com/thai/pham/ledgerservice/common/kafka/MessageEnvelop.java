package com.thai.pham.ledgerservice.common.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class MessageEnvelop<Content> implements IdempotentDetail {
    @JsonProperty("event_id")
    private final UUID eventId;

    @JsonProperty("event_type")
    private final String eventType;

    @JsonProperty("timestamp")
    private final LocalDateTime timestamp;

    @JsonProperty("source")
    private final String source;

    @JsonProperty("payload")
    private final Content payload;

    @Override
    public String getIdempotentKey() {
        return eventId.toString();
    }
}