package com.thai.pham.inventoryservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import com.thai.pham.inventoryservice.service.StockTransferService;
import com.thai.pham.inventoryservice.dto.StockTransferDto;
import com.thai.pham.inventoryservice.dto.PageDto;

@RestController
@RequestMapping("api/v1/transfer/")
class StockTransferController {
    private final StockTransferService transferService;

    @Autowired
    public StockTransferController(StockTransferService transferService) {
        this.transferService = transferService;
    }


    @GetMapping("/all")
    public ResponseEntity<PageDto<StockTransferDto>> getAllTransferStock(Pageable pageInfo) {
        return new ResponseEntity<>(transferService.getTransferStockPage(pageInfo), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<StockTransferDto> getTransferInformation(UUID id) {
        return new ResponseEntity<>(transferService.findTransferById(id), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<StockTransferDto> updateTransfer(@RequestBody StockTransferDto newInfomation) {
        return new ResponseEntity<>(transferService.updateTransferInformation(newInfomation), HttpStatus.OK);
    }

    @PutMapping("/new")
    public ResponseEntity<StockTransferDto> addTransfer(StockTransferDto insertInformation) {
        return new ResponseEntity<>(transferService.addTransfer(insertInformation), HttpStatus.OK);
    }
}