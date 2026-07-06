package com.thai.pham.inventoryservice.mapper;

import com.thai.pham.inventoryservice.dto.PageDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

@Component
public class PageDtoMapper {
    public <T> PageDto<T> mapObject(Page<T> page) {
        return new PageDto<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                (long) page.getTotalPages(),
                page.isLast(),
                page.isFirst()
        );
    }
}