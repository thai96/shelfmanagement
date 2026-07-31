package com.thai.pham.inventoryservice.common.exception;

import com.thai.pham.inventoryservice.common.ErrorCode;

public class OrderException extends BaseBusinessException {
    public OrderException(ErrorCode errorCode) {
        super(errorCode);
    }

    public OrderException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
    }
}