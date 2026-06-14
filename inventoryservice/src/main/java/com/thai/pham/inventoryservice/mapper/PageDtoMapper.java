package com.thai.pham.inventoryservice.utils;

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