package com.thai.pham.ledgerservice.feature.read.domain;

import com.thai.pham.inventoryservice.keygenerator.InventoryKeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

import com.thai.pham.ledgerservice.common.data.repositories.LedgerReadRepository;
import com.thai.pham.ledgerservice.common.data.LedgerPageResult;
import com.thai.pham.ledgerservice.common.data.request.LedgerReadRequest;

@Service
@Transactional(readOnly = true)
public class LedgerReadService {
    private final LedgerReadRepository ledgerRepository;
    private final LedgerPageResultMapper pageResultMapper;
    private final LedgerResultMapper ledgerResultMapper;
    private final LedgerReadSpecification specificationBuilder;

    @Autowired
    public LedgerReadService(LedgerReadRepository ledgerRepository, LedgerPageResultMapper pageResultMapper, LedgerReadSpecification specificationBuilder, LedgerResultMapper ledgerResultMapper) {
        this.ledgerRepository = ledgerRepository;
        this.pageResultMapper = pageResultMapper;
        this.specificationBuilder = specificationBuilder;
        this.ledgerResultMapper = ledgerResultMapper;
    }

    public LedgerPageResult findLedgerPage(LedgerReadRequest request) {
        Pageable searchPage = PageRequest.of(request.getPage(), request.getSize());
        Specification spec = specificationBuilder.hasLocation(request.getLocationId())
                                .and(specificationBuilder.hasProduct(request.getProductId()))
                                .and(specificationBuilder.hasUpdateReason(request.getUpdateReason()))
                                .and(specificationBuilder.checkBetweenDate(request.getStartDate(), request.getEndDate()));
        Page<Ledger> queryResult = ledgerRepository.findAll(spec, searchPage);
        return pageResultMapper.toResult(queryResult);
    }

    public List<LedgerPageResult> findLedgerByRefId(String refId) {
        List<Ledger> ledgers = ledgerRepository.findByRefId(refId).orElse(Collections::emptyList);
        return ledgerResultMapper.toResultList(ledgers);
    }
}