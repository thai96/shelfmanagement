package com.thai.pham.inventoryservice.common.handler;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.tracing.Tracer;
import io.micrometer.tracing.Span;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.PessimisticLockingFailureException;
import org.springframework.dao.QueryTimeoutException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;

import com.thai.pham.inventoryservice.common.exception.BaseBusinessException;
import com.thai.pham.inventoryservice.common.ErrorCode;
import com.thai.pham.inventoryservice.common.ErrorResponse;

@RestControllerAdvice(basePackages = "com.thai.pham")
public class GlobalExceptionHandler {
   public static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
   private static final String DEFAULT_TRACE_ID = "n/a";
   private static final String DEFAULT_COUNTER_NAME = "app.errors";
   private static final String DEFAULT_COUNTER_TAG_PREFIX = "code";

   private final ErrorResponseBuilder responseBuilder;
   private final Tracer tracer;
   private final MeterRegistry meterRegistry;

    @Autowired
   public GlobalExceptionHandler(
        ErrorResponseBuilder responseBuilder,
        Tracer tracer,
        MeterRegistry meterRegistry
   ) {
        this.responseBuilder = responseBuilder;
        this.tracer = tracer;
        this.meterRegistry = meterRegistry;
   }

   @ExceptionHandler(BaseBusinessException.class)
   public ResponseEntity<ErrorResponse> handleBusiness(BaseBusinessException ex, HttpServletRequest req) {
        ErrorCode code = ex.getErrorCode();
        String traceId = currentTraceId();
        log.warn("[{}] bussiness error code={} path={}", traceId, code.getCode(), req.getRequestURI());
        countError(code);
        return ResponseEntity.status(code,getStatus()).body(responseBuilder.build(code, ex, req, traceId));
   }

   @ExceptionHandler({OptimisticLockingFailureException.class, PessimisticLockingFailureException.class})
   public ResponseEntity<ErrorResponse> handleLockConflict(DataAccessException ex, HttpServletRequest req) {
        ErrorCode code = ErrorCode.CONCURRENT_MODIFICATION;
        String traceId = currentTraceId();
        log.warn("[{}] lock conflict path={}", traceId, req.getRequestURI());
        countError(code);
        return ResponseEntity.status(code.getStatus()).header("Retry-After","1")
                    .body(responseBuilder.build(code, ex, req, traceId));
   }

   @ExceptionHandler(MethodArgumentNotValidException.class)
   public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        ErrorCode code = ErrorCode.INVALID_INPUT;
        String traceId = currentTraceId();
        List<ErrorResponse.FieldError> details = ex.getBindingResult().getFieldErrors().stream()
            .map(f -> new ErrorResponse.FieldError(f.getField(), f.getDefaultMessage())).toList();
        ErrorResponse base = responseBuilder.build(code, ex, req, traceId);
        ErrorResponse withDetails = ErrorResponse.builder()
            .errorCode(base.errorCode())
            .message(base.message())
            .traceId(base.traceId())
            .timestamp(Instant.now())
            .path(base.path())
            .debugDetail(details)
            .fieldErrors(base.debugException())
            .debugStackTrace(base.debugStackTrace())
            .build();
        countError(code);
        return ResponseEntity.status(code.getStatus()).body(withDetails);
   }

    @ExceptionHandler(QueryTimeoutException.class)
    public ResponseEntity<ErrorResponse> handleDbTimeout(QueryTimeoutException ex, HttpServletRequest req) {
        return infraError(ErrorCode.DATABASE_TIMEOUT, ex, req);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknown(Exception ex, HttpServletRequest req) {
        return infraError(ErrorCode.INTERNAL_SERVER_ERROR, ex, req);
    }

    private ResponseEntity<ErrorResponse> infraError(ErrorCode code, Exception ex, HttpServletRequest req) {
        String traceId = currentTraceId();
        log.error("[{}] infra error code={} path={}", traceId, code.getCode(), req.getRequestURI(), ex);
        countError(code);
        HttpHeaders headers = new HttpHeaders();
        if(code == ErrorCode.SERVICE_UNAVAILABLE) {
            headers.add("Retry-After", "5");
        }
        return ResponseEntity.status(code.getStatus()).headers(headers)
                .body(responseBuilder.build(code, ex, req, traceId));
    }

   private String currentTraceId() {
        Span span = tracer.currentSpan()
        return span != null ? span.context().traceId() : DEFAULT_TRACE_ID;
   }

   private void countError(ErrorCode code) {
        meterRegistry.counter(DEFAULT_COUNTER_NAME, DEFAULT_COUNTER_TAG_PREFIX, code.getCode()).increment();
   }
}