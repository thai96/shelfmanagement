package com.thai.pham.inventoryservice.common.handler;

import jakarta.servlet.http.HttpServletRequest;

import com.thai.pham.inventoryservice.common.ErrorCode;
import com.thai.pham.inventoryservice.common.ErrorResponse;

public interface ErrorResponseBuilder {
    ErrorResponse build(ErrorCode code, Throwable ex, HttpServletRequest request, String traceId);
}