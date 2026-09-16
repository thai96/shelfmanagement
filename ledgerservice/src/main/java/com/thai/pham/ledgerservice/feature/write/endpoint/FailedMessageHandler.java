package com.thai.pham.ledgerservice.feature.write.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;

import com.thai.pham.ledgerservice.feature.write.domain.MessageProcessingService;
import com.thai.pham.ledgerservice.feature.write.data.request.CreateLedgerRecordRequest;
import com.thai.pham.ledgerservice.common.kafka.MessageEnvelop;

@Slf4j
public class FailedMessageHandler extends DeadLetterPublishingRecoverer {
    private final MessageProcessingService msgProcessingService;

    @Autowired
    public FailedMessageHandler(MessageProcessingService msgProcessingService) {
        this.msgProcessingService = msgProcessingService;
    }

    @Override
    public void accept(ConsumerRecord<?, ?> record, Exception exception) {
        try {
            Object payload = record.value();

            if (payload instanceof MessageEnvelop<?> ms) {
                msgProcessingService.quanrantineMessage(msg.getEventId(), msg.getErrorCode(), msg.getErrorMsg());
            }
        } catch (Exception ex) {
            log.error("Failed to persist audit info to database before sending to DLT", ex);
        }
        super.accept(record, exception);
    }
}