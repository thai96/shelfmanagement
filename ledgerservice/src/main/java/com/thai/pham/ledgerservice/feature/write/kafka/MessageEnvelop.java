package com.thai.pham.ledgerservice.feature.write.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;
import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class MessageEnvelop<Content> {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty("event_id")
    private final UUID eventId;

    @JsonProperty("event_type")
    private final String eventType;

    @JsonProperty("timestamp")
    private final LocalDateTime timestamp;

    @JsonProperty("source")
    private final String source;

    @Column(name = "message_id", nullable = false, unique = true)
    private String messageId;

    @Enumerated(EnumType.STRING)
    @JsonProperty("status", nullable = false)
    private final MessageStatus status;

    @Column("first_received")
    private LocalDateTime firstReceived;

    @Column("last_received")
    private LocalDateTime lastReceived;

    @Column("completed_at")
    private LocalDateTime completedAt;

    @Column("quarantined_at")
    private LocalDateTime quarantinedAt;

    @Column("error_code")
    @ColumnDefault("NULL")
    private Integer errorCode;

    @Column("error_message")
    @ColumnDefault("NULL")
    private String errorMsg;

    @JsonProperty("payload")
    private final Content payload;

    public void markQuarantined(Integer errorCode, String errorMsg) {
        this.status = MessageStatus.QUARANTINED;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.quarantinedAt = LocalDateTime.now(ZoneOffset.UTC);
    }

    public void markCompleted() {
        this.completedAt = LocalDateTime.now(ZoneOffset.UTC);
        this.status = MessageStatus.COMPLETED;
    }

    public void markProcessing() {
        this.status = MessageStatus.PROCESSING;
        if(firstReceived == null) {
            this.firstReceived = LocalDateTime.now(ZoneOffset.UTC);
        }
        this.lastReceived = LocalDateTime.now(ZoneOffset.UTC);
    }
}