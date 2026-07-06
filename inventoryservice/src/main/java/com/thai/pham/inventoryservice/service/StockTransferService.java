package com.thai.pham.inventoryservice.service;

import com.thai.pham.inventoryservice.dto.PageDto;
import com.thai.pham.inventoryservice.dto.StockTransferDto;
import com.thai.pham.inventoryservice.entity.StockTransfer;
import com.thai.pham.inventoryservice.mapper.PageDtoMapper;
import com.thai.pham.inventoryservice.mapper.StockTransferDtoMapper;
import com.thai.pham.inventoryservice.repository.StockTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

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
        return transferMapper.mapObject(stockTransferRepo.saveAndFlush(transferEntity));
    }

    @Transactional(readOnly = false)
    public StockTransferDto  addTransfer(StockTransferDto insertInformation) {
        StockTransfer transferEntity = transferMapper.mapEntity(insertInformation);
        transferEntity.setId(null);
        return transferMapper.mapObject(stockTransferRepo.saveAndFlush(transferEntity));
    }
}