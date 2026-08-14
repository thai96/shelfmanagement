package com.thai.pham.ledgerservice.common.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import com.thai.pham.ledgerservice.common.data.entity.Ledger;
import com.thai.pham.ledgerservice.common.data.repositories.LedgerRepository;

public interface LedgerReadRepository extends LedgerRepository {
    @Query("SELECT l.id, l.locationId, l.productId, l.qtyChange, l.updateReason, l.refId, l.createdAt" +
    "FROM Ledger l" + 
    "WHERE l.locationId = :locationId AND l.productId = :productId" +
    "AND (:updateReason IS NULL OR l.updateReason = :updateReason)" +
    "AND (:startDate IS NULL OR l.createdAt >= :startDate)" +
    "AND (:endDate IS NULL OR l.createdAt < :endDate)")
    public Page<Ledger> findLedgerByLocationAndProductBetweenTime(
        @Param("locationId") UUID locationId,
        @Param("productId") UUID productId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("reason") UpdateReason updateReason,
        Pageable page
    );

    @Query("SELECT l.id, l.locationId, l.productId, l.qtyChange, l.updateReason, l.refId, l.createdAt" +
    "FROM Ledger l" +
    "WHERE l.refId = :refId")
    public Optional<List<Ledger>> findLedgerByRef(@Param("refId") String refId);
}