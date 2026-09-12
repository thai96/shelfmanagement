package com.thai.pham.inventoryservice.controller;

import com.thai.pham.inventoryservice.dto.*;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

import com.thai.pham.inventoryservice.service.StockTransferService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("api/v1/stock-transfers/")
class StockTransferController {
    private final StockTransferService transferService;

    @Autowired
    public StockTransferController(StockTransferService transferService) {
        this.transferService = transferService;
    }


    @PostMapping
    public ResponseEntity<TransferActionResponse> createTransferRequest(@RequestBody @Valid CreateTransferRequest request) {
        TransferActionResponse createdResource = transferService.createTransfer(request);
        URI createdResourceURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(createdResource.id())
                .toUri();
        return ResponseEntity.created(createdResourceURI).body(createdResource);
    }

    @GetMapping
    public ResponseEntity<PageDto<TransferActionResponse>> listTransfer(@ModelAttribute @ParameterObject ListTransferRecordRequest request, @PageableDefault(page = 0, size = 20) Pageable pageable) {
        return ResponseEntity.ok(transferService.listTransfer(request, pageable));
    }

    @GetMapping("/{transfer-id}")
    public ResponseEntity<TransferActionResponse> transferDetail(@PathVariable("transfer-id") UUID transferId) {
        return ResponseEntity.ok(transferService.getTransferDetail(transferId));
    }

}