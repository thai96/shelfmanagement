package com.thai.pham.ledgerservice.feature.write.endpoint;

@Component
@Slf4j
public class MessageActionInvoker implements RecordInterceptor<?, MessageEnvelop<?>> {
    @Autowired
    private final MessageProcessingService msgProcessingService;

    @Autowired
    private IdempotencyService idempotencyService;

    @Override
    public ConsumerRecord<?, MessageEnvelop<?>> intercept(ConsumerRecord<?, MessageEnvelop<?>> record, Consumer<?, MessageEnvelop<?>> consumer) {
        MessageStatus currentStatus = request.getStatus();
        if(isSkippableStatus(currentStatus)) {
            return null;
        }
        msgProcessingService.processMessage(request.getEventId());
        return record; 
    }

    @Override
    public void success(ConsumerRecord<?, MessageEnvelop<?>> record, Consumer<?, MessageEnvelop<?>> consumer) {
        log.info("Listener processed successfully. Updating state to COMPLETED for offset: {}", record.offset());
        
        String messageId = extractMessageId(record);
        idempotencyService.markAsCompleted(messageId);
    }

    @Override
    public void failure(ConsumerRecord<?, MessageEnvelop<?>> record, Exception exception, Consumer<?, MessageEnvelop<?>> consumer) {
        log.error("Listener failed for offset: {}. Updating state to FAILED", record.offset());
        String messageId = extractMessageId(record);
        idempotencyService.markAsFailed(messageId, exception.getMessage());
    }

    private String extractMessageId(ConsumerRecord<?, MessageEnvelop<?>> record) {
        // Lấy từ Header hoặc Key hoặc parse từ payload
        return record.key() != null ? record.key() : record.topic() + "-" + record.partition() + "-" + record.offset();
    }

    private boolean isSkippableStatus(MessageStatus status) {
        return status != null && (status == MessageStatus.COMPLETED ||
            status == MessageStatus.QUARANTINED);
    }
}