package com.thai.pham.ledgerservice.feature.write.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import com.thai.pham.ledgerservice.common.data.entity.Ledger;
import com.thai.pham.ledgerservice.feature.write.data.request.CreateLedgerRecordRequest;

@Mapper(componentModel = "spring")
public class CreateRecordRequestMapper {
    @Mapping(source = "locationId", target = "locationId")
    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "qtyChange", target = "qtyChange")
    @Mapping(source = "reason", target = "updateReason")
    @Mapping(source = "refId", target = "refId")
    @Mapping(source = "createdAt", target = "createdAt")
    Ledger toEntity(CreateLedgerRecordRequest request);
}