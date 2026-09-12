package com.thai.pham.inventoryservice.service;

import com.thai.pham.inventoryservice.common.exception.BusinessException;
import com.thai.pham.inventoryservice.common.exception.NotFoundException;
import com.thai.pham.inventoryservice.common.response.ErrorCode;
import com.thai.pham.inventoryservice.dto.*;
import com.thai.pham.inventoryservice.entity.Location;
import com.thai.pham.inventoryservice.entity.StockTransfer;
import com.thai.pham.inventoryservice.entity.TransferStatus;
import com.thai.pham.inventoryservice.mapper.PageDtoMapper;
import com.thai.pham.inventoryservice.mapper.TransferResponseMapper;
import com.thai.pham.inventoryservice.repository.LocationRepository;
import com.thai.pham.inventoryservice.repository.StockTransferRepository;
import com.thai.pham.inventoryservice.repository.specification.StockTransferSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class StockTransferService {
    private final StockTransferRepository stockTransferRepo;
    private final PageDtoMapper pageMapper;
    private final StockTransferSpecification stockTransferSpecification;
    private final LocationRepository locationRepository;
    private final ProductService productService;
    private final TransferResponseMapper transferResponseMapper;

    @Autowired
    public StockTransferService(StockTransferRepository stockTransferRepo, PageDtoMapper pageMapper,
                                StockTransferSpecification stockTransferSpecification, LocationRepository locationRepository, ProductService productService, TransferResponseMapper transferResponseMapper) {
        this.stockTransferRepo = stockTransferRepo;
        this.pageMapper = pageMapper;
        this.stockTransferSpecification = stockTransferSpecification;
        this.locationRepository = locationRepository;
        this.productService = productService;
        this.transferResponseMapper = transferResponseMapper;
    }

    @Transactional
    public TransferActionResponse createTransfer(CreateTransferRequest request) {
        Location fromLocation = locationRepository.findById(request.fromLocation()).orElseThrow(NotFoundException::new);
        Location toLocation = locationRepository.findById(request.toLocation()).orElseThrow(NotFoundException::new);
        if (fromLocation.getId().equals(toLocation.getId())) {
            throw new BusinessException(ErrorCode.INVALID_TRANSFER_LOCATION);
        }
        List<UUID> productCheckList = request.transferDetails().stream().map(TransferDetail::productId).toList();
        if (!productService.checkProductExisted(productCheckList)) {
            throw new BusinessException(ErrorCode.INVALID_TRANSFER_PRODUCT);
        }
        StockTransfer transfer = StockTransfer.builder()
                .fromLocation(fromLocation)
                .toLocation(toLocation)
                .transferStatus(TransferStatus.PENDING)
                .build();
        return transferResponseMapper.mapObject(stockTransferRepo.save(transfer));
    }

    public PageDto<TransferActionResponse> listTransfer(ListTransferRecordRequest request, Pageable pageable) {
        Specification<StockTransfer> transferSpecification = stockTransferSpecification.addLocationQuery(request.fromLocationId(), request.toLocationId())
                .and(stockTransferSpecification.addPeriodQuery(request.fromDate(), request.toDate()))
                .and(stockTransferSpecification.addStatusQuery(request.transferStatus()));
        Page<StockTransfer> stockTransfers = stockTransferRepo.findAll(transferSpecification, pageable);
        return pageMapper.mapObject(stockTransfers.map(transferResponseMapper::mapObject));
    }

    public TransferActionResponse getTransferDetail(UUID transferId) {
        return transferResponseMapper.mapObject(stockTransferRepo.findById(transferId).orElseThrow(NotFoundException::new));
    }
}