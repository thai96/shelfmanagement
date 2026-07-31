package com.thai.pham.inventoryservice.common.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.time.Instant;

import com.thai.pham.inventoryservice.common.ErrorCode;
import com.thai.pham.inventoryservice.common.ErrorResponse;

@Component
@Profile("prod")
public class ProdErrorResponseBuilder implements ErrorResponseBuilder {
    @Override
    public ErrorResponse build(ErrorCode code, Throwable ex, HttpServletRequest request, String traceId) {
        return ErrorResponse.builder()
            .errorCode(code.getCode())
            .message(code.getMessage())
            .traceId(traceId)
            .timestamp(Instant.now())
            .path(request.getRequestURI())
            .debugDetail(null)
            .fieldErrors(null)
            .debugStackTrace(null)
            .build();
    }
}