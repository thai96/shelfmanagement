package com.thai.pham.ledgerservice.common.idempotent.repository;

import com.example.idempotentconsumer.entity.IdempotencyRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface IdempotencyRecordRepository extends JpaRepository<IdempotencyRecord, Long> {
    boolean existsByIdempotencyKey(String idempotencyKey);
    Optional<IdempotencyRecord> findByIdempotencyKey(String idempotencyKey);
}