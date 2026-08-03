package com.thai.pham.inventoryservice.common.handler;

import jakarta.servlet.http.HttpServletRequest;

import com.thai.pham.inventoryservice.common.response.ErrorCode;
import com.thai.pham.inventoryservice.common.response.ErrorResponse;

public interface ErrorResponseBuilder {
    ErrorResponse build(ErrorCode code, Throwable ex, HttpServletRequest request, String traceId);
}