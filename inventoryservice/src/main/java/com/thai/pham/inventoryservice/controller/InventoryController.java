package com.thai.pham.inventoryservice.controller;

import com.thai.pham.inventoryservice.dto.*;
import com.thai.pham.inventoryservice.service.InventoryService;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/inventory/")
public class InventoryController {
    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public ResponseEntity<PageDto<InventoryDto>> getInventory(
            @ParameterObject @ModelAttribute ListInventoryRequest request,
            @PageableDefault(page = 0, size = 20) Pageable pageable
    ) {
        PageDto<InventoryDto> pageData = inventoryService.getAllInventory(request, pageable);
        return new ResponseEntity<>(pageData, HttpStatus.OK);
    }

    @GetMapping("/availability")
    public ResponseEntity<ProductInventoryRecord> checkAvailableSku(@ParameterObject @ModelAttribute SkuAvailabilityRequest request) {
        return ResponseEntity.ok(inventoryService.checkAvailableSku(request));
    }

    @GetMapping("/availability/batch")
    public ResponseEntity<ProductInventoryRecordList> batchCheckAvailableSku(@ParameterObject @ModelAttribute BatchCheckSkuAvailabilityRequest request) {
        return ResponseEntity.ok(inventoryService.batchCheckAvailableSku(request));
    }

    @PostMapping("/deduct")
    public ResponseEntity<OfflineSaleDeductResult> offlinePosDeduct(@RequestBody InventoryDeductRequest request) {
        return ResponseEntity.ok(inventoryService.deductOfflineSale(request));
    }

    @PostMapping("update")
    public ResponseEntity<List<InventoryDto>> updateInventory(@RequestBody List<InventoryChangeDto> dataChangeList, @RequestHeader("Idempotency-Key") String requestId) {
        List<InventoryDto> changedData = inventoryService.updateInventory(dataChangeList, requestId);
        return new ResponseEntity<>(changedData, HttpStatus.OK);
    }
}