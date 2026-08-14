package com.thai.pham.ledgerservice.feature.read.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import com.thai.pham.ledgerservice.common.data.entity.Ledger;

@Mapper(
    componentModel = "spring",
    uses = {LedgerResultMapper.class}
)
public interface LedgerPageResultMapper {
    @Mapping(source = "number", target = "pageMetadata.page")
    @Mapping(source = "size", target = "pageMetadata.size")
    @Mapping(source = "numberOfElements", target = "pageMetadata.totalRecords")
    @Mapping(source = "totalPages", target = "pageMetadata.totalPages")
    @Mapping(source = "content", target = "data")
    LedgerPageResult toResult(Page<Ledger> result);
}