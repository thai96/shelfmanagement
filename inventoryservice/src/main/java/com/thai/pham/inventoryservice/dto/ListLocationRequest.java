package com.thai.pham.inventoryservice.dto;

import com.thai.pham.inventoryservice.entity.LocationType;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.BindParam;

public record ListLocationRequest(
        @BindParam("type")
        @Parameter(name = "type")
        LocationType locationType,
        @BindParam("is_active")
        @Parameter(name = "is_active")
        Boolean isActive,
        @BindParam("search")
        @Parameter(name = "search")
        String searchParams,
        @BindParam("page")
        @Parameter(name = "page")
        Integer page,
        @BindParam("size")
        @Parameter(name = "size")
        Integer size
) {
    public ListLocationRequest {
        if (page == null) page = 0;
        if (size == null) size = 20;
    }
}