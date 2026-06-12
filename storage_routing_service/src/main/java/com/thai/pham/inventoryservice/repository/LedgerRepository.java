package com.thai.pham.inventoryservice.repository;

import com.thai.pham.inventoryservice.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LedgerRepository extends JpaRepository<Ledger, UUID> {}