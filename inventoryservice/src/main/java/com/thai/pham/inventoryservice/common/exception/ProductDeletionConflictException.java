package com.thai.pham.inventoryservice.common.exception;

import com.thai.pham.inventoryservice.common.response.ErrorCode;

public class ProductDeletionConflictException extends BaseBusinessException {

    public ProductDeletionConflictException() {
        super(ErrorCode.PRODUCTION_DELETE_CONFLICT);
    }

    public ProductDeletionConflictException(Throwable cause) {
        super(ErrorCode.PRODUCTION_DELETE_CONFLICT, cause);
    }
}
