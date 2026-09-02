package com.thai.pham.inventoryservice.service;

import com.thai.pham.inventoryservice.common.Pair;
import com.thai.pham.inventoryservice.common.exception.InventoryException;
import com.thai.pham.inventoryservice.common.response.ErrorCode;
import com.thai.pham.inventoryservice.dto.OrderDetailItem;
import com.thai.pham.inventoryservice.dto.ReservationSuccessResult;
import com.thai.pham.inventoryservice.dto.ResourceCreatedResult;
import com.thai.pham.inventoryservice.dto.ReserveRequest;
import com.thai.pham.inventoryservice.entity.Inventory;
import com.thai.pham.inventoryservice.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReservationManagementService {
    private final InventoryRepository inventoryRepository;

    @Autowired
    public ReservationManagementService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public ResourceCreatedResult<ReservationSuccessResult> reserveInventory(ReserveRequest request) {
        Map<Pair<String, UUID>, Integer> decreaseMap = request.orderDetailItems().stream().collect(Collectors.groupingBy(
                orderDetailItem -> new Pair<>(orderDetailItem.sku(), orderDetailItem.locationId()),
                Collectors.summingInt(OrderDetailItem::reserveQty)
        ));
        List<Inventory> reserveInventory = decreaseMap.entrySet().stream().map((invInfo) -> {
            Pair<String, UUID> inventoryDetail = invInfo.getKey();
            Integer reserveQty = invInfo.getValue();
            Inventory inv = inventoryRepository.findInventoryForReserve(inventoryDetail.value(), inventoryDetail.first(), reserveQty)
                    .orElseThrow(() -> new InventoryException(ErrorCode.INSUFFICIENT_STOCK));
            inv.setQtyAvailable(inv.getQtyAvailable() - reserveQty);
            inv.setQtyReserved(inv.getQtyReserved() + reserveQty);
            return inv;
        }).toList();
        List<OrderDetailItem> updatedDetail = inventoryRepository.saveAllAndFlush(reserveInventory).stream().map(updateInv ->
                new OrderDetailItem(
                        updateInv.getProduct().getSku(),
                        decreaseMap.get(new Pair<>(updateInv.getProduct().getSku(), updateInv.getLocation().getId())),
                        updateInv.getLocation().getId()
                )
        ).toList();
        URI resourceUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(updatedDetail.getFirst()).toUri();
        return new ResourceCreatedResult<>(resourceUri, new ReservationSuccessResult(
                request.orderRef(),
                updatedDetail)
        );
    }

    public ResourceCreatedResult<ReservationSuccessResult> issueStock(ReserveRequest request) {
        Map<Pair<String, UUID>, Integer> decreaseMap = request.orderDetailItems().stream().collect(Collectors.groupingBy(
                orderDetailItem -> new Pair<>(orderDetailItem.sku(), orderDetailItem.locationId()),
                Collectors.summingInt(OrderDetailItem::reserveQty)
        ));
        List<Inventory> reserveInventory = decreaseMap.entrySet().stream().map((invInfo) -> {
            Pair<String, UUID> inventoryDetail = invInfo.getKey();
            Integer issueQty = invInfo.getValue();
            Inventory inv = inventoryRepository.findInventoryForReserve(inventoryDetail.value(), inventoryDetail.first(), issueQty)
                    .orElseThrow(() -> new InventoryException(ErrorCode.INSUFFICIENT_STOCK));
            inv.setQtyReserved(inv.getQtyReserved() - issueQty);
            inv.setQtyOnHand(inv.getQtyOnHand() - issueQty);
            return inv;
        }).toList();
        List<OrderDetailItem> updatedDetail = inventoryRepository.saveAllAndFlush(reserveInventory).stream().map(updateInv ->
                new OrderDetailItem(
                        updateInv.getProduct().getSku(),
                        decreaseMap.get(new Pair<>(updateInv.getProduct().getSku(), updateInv.getLocation().getId())),
                        updateInv.getLocation().getId()
                )
        ).toList();
        URI resourceUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(updatedDetail.getFirst()).toUri();
        return new ResourceCreatedResult<>(resourceUri, new ReservationSuccessResult(
                request.orderRef(),
                updatedDetail)
        );
    }
}
