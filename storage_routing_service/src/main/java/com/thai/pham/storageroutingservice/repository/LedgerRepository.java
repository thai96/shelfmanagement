package com.thai.pham.storageroutingservice.repository;

import com.thai.pham.storageroutingservice.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LedgerRepository extends JpaRepository<Ledger, UUID> {}