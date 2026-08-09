package com.thai.pham.inventoryservice.advice;

import com.thai.pham.inventoryservice.common.exception.ProductDeletionConflictException;
import com.thai.pham.inventoryservice.common.response.ErrorCode;
import com.thai.pham.inventoryservice.controller.ProductManageController;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice(assignableTypes = ProductManageController.class)
public class ProductControllerFailureHandler {
    private static final URI PRODUCT_IN_USE_DOCUMENT_URI = URI.create("https://api.omnistock.techwear.vn/errors/product-in-use");

    @ExceptionHandler(ProductDeletionConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ProblemDetail handleDataIntegrityViolation(
            ProductDeletionConflictException ex,
            HttpServletRequest request) {
        ErrorCode currentErrorCode = ex.getErrorCode();

        ProblemDetail detail = ProblemDetail.forStatusAndDetail(currentErrorCode.getStatus(), ex.getErrorCode().getClientAction());
        detail.setType(PRODUCT_IN_USE_DOCUMENT_URI);
        detail.setTitle(currentErrorCode.getMessage());
        detail.setInstance(URI.create(request.getRequestURI()));
        return detail;
    }
}
