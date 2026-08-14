package com.thai.pham.ledgerservice.feature.read.domain;

import com.thai.pham.inventoryservice.keygenerator.InventoryKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import com.thai.pham.ledgerservice.common.data.repositories;

@Service
@Transactional(readOnly = true)
public class LedgerReadService {
    private final LedgerReadRepository ledgerRepository;
    private final LedgerPageResultMapper pageResultMapper;

    @Autowired
    public LedgerReadService(LedgerReadRepository ledgerRepository, LedgerPageResultMapper pageResultMapper) {
        this.ledgerRepository = ledgerRepository;
        this.pageResultMapper = pageResultMapper;
    }

    
}