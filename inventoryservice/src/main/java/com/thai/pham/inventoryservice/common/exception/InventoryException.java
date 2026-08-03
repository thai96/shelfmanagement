package com.thai.pham.inventoryservice.common.exception;

import com.thai.pham.inventoryservice.common.response.ErrorCode;

public class InventoryException extends BaseBusinessException {
    public InventoryException(ErrorCode errorCode) {
        super(errorCode);
    }

    public InventoryException(ErrorCode errorCode, Throwable cause) {
        super(errorCode, cause);
    }

    public static InventoryException outOfStock() {
        return new InventoryException(ErrorCode.OUT_OF_STOCK);
    }

    public static InventoryException negativeStockRejected() {
        return new InventoryException(ErrorCode.NEGATIVE_STOCK_REJECTED);
    }
}