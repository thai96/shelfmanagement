package com.thai.pham.inventoryservice.service;

import com.thai.pham.inventoryservice.entity.StockTransfer;
import com.thai.pham.inventoryservice.repository.StockTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class StockTransferService {
    private final StockTransferRepository stockTransferRepo;
    private final PageDtoMapper pageMapper;
    private final StockTransferDtoMapper transferMapper;

    @Autowired
    public StockTransferService(StockTransferRepository stockTransferRepo,PageDtoMapper pageMapper,
        StockTransferDtoMapper transferMapper) {
        this.stockTransferRepo = stockTransferRepo;
        this.pageMapper = pageMapper;
        this.transferMapper = transferMapper;
    }

    public List<StockTransfer> getAllStockTransfer() {
        return stockTransferRepo.findAll();
    }

    public PageDto<StockTransferDto> getTransferStockPage(Pageable pageInfo) {
        Page<StockTransfer> transferPage = stockTransferRepo.findAll(pageInfo);
        Page<StockTransferDto> transferPageDto = transferPage.map(transferMapper::mapObject);
        return pageMapper.mapObject(transferPageDto);
    }

    public StockTransferDto findTransferById(UUID id) {
        StockTransfer transferEntity = stockTransferRepo.getReferenceById(id);
        return transferMapper.mapObject(transferEntity);
    }

    @Transactional(readOnly = false)
    public StockTransferDto updateTransferInformation(StockTransferDto dto) {
        StockTransfer transferEntity = transferMapper.mapEntity(dto);
        return stockTransferRepo.saveAndFlush(transferEntity);
    }

    @Transactional(readOnly = false)
    public StockTransferDto  addTransfer(StockTransferDto insertInformation) {
        StockTransfer transferEntity = transferMapper.mapEntity(dto);
        transferEntity.setId(null);
        return stockTransferRepo.saveAndFlush(transferEntity);
    }
}