package com.thai.pham.inventoryservice.controller;

import com.thai.pham.inventoryservice.dto.ReservationSuccessResult;
import com.thai.pham.inventoryservice.dto.ResourceCreatedResult;
import com.thai.pham.inventoryservice.dto.ReserveRequest;
import com.thai.pham.inventoryservice.service.ReservationManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/reservations/")
public class ReservationManagementController {
    private ReservationManagementService service;

    @PostMapping
    public ResponseEntity<ReservationSuccessResult> reserveStock(ReserveRequest request) {
        ResourceCreatedResult<ReservationSuccessResult> result = service.reserveInventory(request);
        return ResponseEntity.created(result.getResourceUri()).body(result.getResponseBodyContent());
    }

    @PostMapping("/{reservation_id}/confirm")
    public ResponseEntity<ReservationSuccessResult> issueInventory(ReserveRequest request) {
        ResourceCreatedResult<ReservationSuccessResult> result = service.issueStock(request);
        return ResponseEntity.created(result.getResourceUri()).body(result.getResponseBodyContent());
    }
}
