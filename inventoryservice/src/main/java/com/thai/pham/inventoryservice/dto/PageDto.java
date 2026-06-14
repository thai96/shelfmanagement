package com.thai.pham.inventoryservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageDto<T> {
    private List<T> content;
    private Integer pageIndex;
    private Integer pageSize;
    private Long totalElements;
    private Long totalPages;
    private Boolean isLastPage;
    private Boolean isFirstPage;
}