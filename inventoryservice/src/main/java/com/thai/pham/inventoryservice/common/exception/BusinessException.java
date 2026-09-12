package com.thai.pham.inventoryservice.common.exception;

import com.thai.pham.inventoryservice.common.response.ErrorCode;

public class BusinessException extends BaseBusinessException{
    public BusinessException(ErrorCode errorCode) {
        super(errorCode);
    }

    public BusinessException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
