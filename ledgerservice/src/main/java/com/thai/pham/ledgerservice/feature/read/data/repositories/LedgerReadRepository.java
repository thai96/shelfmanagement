package com.thai.pham.ledgerservice.common.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;
import java.util.List;
import java.util.Optional;

import com.thai.pham.ledgerservice.common.data.entity.Ledger;
import com.thai.pham.ledgerservice.common.data.repositories.LedgerRepository;

public interface LedgerReadRepository extends LedgerRepository, JpaSpecificationExecutor<Ledger> {
    public Optional<List<Ledger>> findByRefId(String refId);
}