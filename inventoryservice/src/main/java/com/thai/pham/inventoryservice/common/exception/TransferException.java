package com.thai.pham.inventoryservice.common.exception;

import com.thai.pham.inventoryservice.common.ErrorCode;

public class TransferException extends BaseBusinessException {
    public TransferException(ErrorCode errorCode) {
        super(errorCode);
    }

    public TransferException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
    }
}