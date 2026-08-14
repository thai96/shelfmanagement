package com.thai.pham.ledgerservice.feature.read.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.thai.pham.ledgerservice.common.data.entity.Ledger;

@Mapper(componentModel = "spring")
public class LedgerResultMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "locationId", target = "locationId")
    @Mapping(source = "productId", target = "productId")
    @Mapping(source = "qtyChange", target = "qtyChange")
    @Mapping(source = "updateReason", target = "updateReason")
    @Mapping(source = "refId", target = "refId")
    @Mapping(source = "createdAt", target = "createdAt")
    ReaderItemResult toResult(Ledger ledger);
}