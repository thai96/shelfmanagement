package com.thai.pham.ledgerservice.feature.write.domain;

@Service 
public class MessageProcessingService {
    private MessageRepository msgRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void quanrantineMessage(
        UUID eventId,
        Integer errorCode,
        String errorMsg
    ) {
        MessageEnvelop<CreateLedgerRecordRequest> msgData = 
            msgRepository.findById(eventId).orElseThrow(() -> new BussinessException(errorCode, errorMsg));

        msg.markQuarantined(errorCode, errorMsg);

        msgRepository.save(msg);
    }

    @Transactional
    public void completeMessage(UUID eventId) {
        MessageEnvelop<CreateLedgerRecordRequest> msgData = 
            msgRepository.findById(eventId).orElseThrow(() -> new BussinessException(errorCode, errorMsg));
        msgData.markCompleted();
        msgRepository.save(msgData);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processMessage(UUID eventId) {
        MessageEnvelop<CreateLedgerRecordRequest> msgData = 
            msgRepository.findById(eventId).orElseThrow(() -> new BussinessException(errorCode, errorMsg));
        msgData.markProcessing();
        msgRepository.save(msgData);
    }
}