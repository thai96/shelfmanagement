package com.thai.pham.inventoryservice.common.exception;

import com.thai.pham.inventoryservice.common.response.ErrorCode;

public class NotFoundException extends BaseBusinessException{
    public NotFoundException() {
        super(ErrorCode.RESOURCE_NOT_FOUND);
    }

    public NotFoundException(Throwable cause) {
        super(ErrorCode.RESOURCE_NOT_FOUND, cause);
    }
}
