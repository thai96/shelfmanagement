package com.thai.pham.inventoryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class PageDto<T> implements Serializable {
    private List<T> content;
    private Integer pageIndex;
    private Integer pageSize;
    private Long totalElements;
    private Long totalPages;
    private Boolean isLastPage;
    private Boolean isFirstPage;
}