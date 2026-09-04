package com.thai.pham.ledgerservice.feature.write.domain;

import com.thai.pham.ledgerservice.feature.write.data.request.CreateLedgerRecordRequest;
import com.thai.pham.ledgerservice.common.data.entity.Ledger;
import com.thai.pham.ledgerservice.feature.write.repositories.RecordAppendRepository;

@Service
public class RecordAppendService {
    private CreateRecordRequestMapper requestMapper;
    private RecordAppendRepository appendRepository;

    @Autowired
    public RecordAppendService(CreateRecordRequestMapper requestMapper) {
        this.requestMapper = requestMapper;
    }

    @Transaction(readOnly = false)
    public void appendLedgerRecord(CreateLedgerRecordRequest request) {
        Ledger newRecord = requestMapper.toEntity(request);
        appendRepository.saveAndFlush(newRecord);
    }
}