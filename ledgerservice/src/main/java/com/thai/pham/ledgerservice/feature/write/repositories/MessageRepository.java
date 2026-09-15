package com.thai.pham.ledgerservice.feature.write.repositories;

public interface RecordAppendRepository extends JpaRepository<MessageEnvelop<CreateLedgerRecordRequest>, UUID> {

}