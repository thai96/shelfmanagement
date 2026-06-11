package com.thai.pham.storageroutingservice.service;

import com.thai.pham.storageroutingservice.entity.StockTransfer;
import com.thai.pham.storageroutingservice.repository.StockTransferRepository;
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