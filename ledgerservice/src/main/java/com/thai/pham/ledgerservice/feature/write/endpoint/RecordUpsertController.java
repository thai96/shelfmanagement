package com.thai.pham.ledgerservice.feature.write.endpoint;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.thai.pham.ledgerservice.feature.write.domain.RecordAppendService;

@Service
public class RecordUpsertController {
    private final RecordAppendService recordAppendService;
    private final MessageProcessingService msgProcessingService;

    public RecordUpsertController(RecordAppendService recordAppendService) {
        this.recordAppendService = recordAppendService;
    }

    @KafkaListener(topic = "record-append", groupId = "${app.kafka.consumer.record-upsert}")
    @IdempotentConsumer
    public void receiveLedgerRecordAppend(@IdempotentKey MessageEnvelop<CreateLedgerRecordRequest> request) {
        MessageStatus currentStatus = request.getStatus();
        if(isSkippableStatus(currentStatus)) {
            return;
        }
        msgProcessingService.processMessage(request);
        recordAppendService.appendLedgerRecord(request.getPayload());
    }
}