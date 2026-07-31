package com.thai.pham.inventoryservice.common.exception;

import lombok.Getter;

import com.thai.pham.inventoryservice.common.ErrorCode;

@Getter
public abstract class BaseBusinessException extends RuntimeException {
    private final ErrorCode errorCode;

    public BaseBusinessException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public BaseBusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}