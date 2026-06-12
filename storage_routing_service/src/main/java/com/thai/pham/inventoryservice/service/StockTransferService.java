package com.thai.pham.inventoryservice.service;

import com.thai.pham.inventoryservice.entity.StockTransfer;
import com.thai.pham.inventoryservice.repository.StockTransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockTransferService {
    private final StockTransferRepository stockTransferRepo;

    @Autowired
    public StockTransferService(StockTransferRepository stockTransferRepo) {
        this.stockTransferRepo = stockTransferRepo;
    }

    public List<StockTransfer> getAllStockTransfer() {
        return stockTransferRepo.findAll();
    }
}