package com.thai.pham.ledgerservice.common.data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

import com.thai.pham.ledgerservice.common.data.entity.Ledger;

public interface LedgerRepository extends JpaRepository<Ledger, UUID> {

}