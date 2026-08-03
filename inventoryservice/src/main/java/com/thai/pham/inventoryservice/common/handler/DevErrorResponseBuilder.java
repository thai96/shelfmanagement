package com.thai.pham.inventoryservice.common.handler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import com.thai.pham.inventoryservice.common.response.ErrorCode;
import com.thai.pham.inventoryservice.common.ErrorResponse;

@Component
@Profile("debug")
public class DevErrorResponseBuilder implements ErrorResponseBuilder {
    public static final int MAX_STACK_LINES = 10;

    @Override
    public ErrorResponse build(ErrorCode code, Throwable ex, HttpServletRequest request, String traceId) {
        List<String> stack = Arrays.stream(ex.getStackTrace()).limit(MAX_STACK_LINES)
                                .map(StackTraceElement::toString).toList();
        return ErrorResponse.builder()
            .errorCode(code.getCode())
            .message(formatMessage(code, ex))
            .traceId(traceId)
            .timestamp(Instant.now())
            .path(request.getRequestURI())
            .debugDetail(ex.getClass().getName())
            .fieldErrors(null)
            .debugStackTrace(stack)
            .build();
    }

    private String formatMessage(ErrorCode errorCode, Throwable ex) {
        return errorCode.getMessage() + " | root: " + rootMessage(ex);
    }

    private String rootMessage(Throwable ex) {
        Thowable root = ex;
        while(root.getCause() != null) {
            root = root.getCause();
        }
        return root.getMessage();
    }
}