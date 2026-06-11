package com.thai.pham.storageroutingservice.service;

import com.thai.pham.storageroutingservice.entity.Ledger;
import com.thai.pham.storageroutingservice.repository.LedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LedgerService {
    private final LedgerRepository ledgerRepo;

    @Autowired
    public LedgerService(LedgerRepository ledgerRepo) {
        this.ledgerRepo = ledgerRepo;
    }

    public List<Ledger> getAllLedger() {
        return ledgerRepo.findAll();
    }
}