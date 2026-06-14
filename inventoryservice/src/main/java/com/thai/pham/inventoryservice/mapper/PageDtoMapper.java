package com.thai.pham.inventoryservice.mapper;

import com.thai.pham.inventoryservice.dto.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageDtoMapper {
    public <T> PageDto<T> toPageDto(Page<T> page) {
        return new PageDto<T> (
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isLast(),
            page.isFirst()
        );
    }
}