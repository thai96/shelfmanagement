package com.thai.pham.inventoryservice.controller;

import com.thai.pham.inventoryservice.dto.InventoryChangeDto;
import com.thai.pham.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

import com.thai.pham.inventoryservice.dto.PageDto;
import com.thai.pham.inventoryservice.dto.InventoryDto;

@RestController
@RequestMapping("api/v1/inventory/")
public class InventoryController {
    private InventoryService inventoryService;

    @GetMapping("")
    public ResponseEntity<PageDto<InventoryDto>> getInventory(Pageable pageable) {
        PageDto<InventoryDto> pageData = inventoryService.getAllInventory(pageable);
        return new ResponseEntity<>(pageData, HttpStatus.OK);
    }

    @PostMapping("update")
    public ResponseEntity<List<InventoryDto>> updateInventory(@RequestBody List<InventoryChangeDto> dataChangeList, @RequestParam("requestId") String requestId) {
        List<InventoryDto> changedData = inventoryService.updateInventory(dataChangeList, requestId);
        return new ResponseEntity<>(changedData, HttpStatus.OK);
    }
}