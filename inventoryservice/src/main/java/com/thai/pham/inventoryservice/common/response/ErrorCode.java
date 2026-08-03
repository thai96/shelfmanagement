package com.thai.pham.inventoryservice.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    OUT_OF_STOCK("INV-001", HttpStatus.CONFLICT, "Sản phẩm hết hàng", "Hiển thị hết hàng, gợi ý sản phẩm khác."),
    NEGATIVE_STOCK_REJECTED("INV-002", HttpStatus.CONFLICT, "Thao tác bị từ chối do sẽ gây âm kho", "Refresh số lượng tồn và thử lại"),
    CONCURRENT_MODIFICATION("INV-003", HttpStatus.CONFLICT, "Dữ liệu tồn kho vừa bị thay đổi bởi giao dịch khác", "Client retry với backoff (tối đa 3 lần)"),

    TRANSFER_SOURCE_INSUFFICIENT("TRF-001", HttpStatus.CONFLICT, "Kho nguồn không đủ hàng để luân chuyển", "Kiểm tra lại số lượng kho nguồn"),
    
    INVALID_INPUT("VAL-001", HttpStatus.BAD_REQUEST, "Dữ liệu đầu vào không hợp lệ", "Sửa các field lỗi trong 'detail' và gửi lại"),
    RESOURCE_NOT_FOUND("GEN-404", HttpStatus.NOT_FOUND, "Không tìm thấy tài nguyên", "Kiểm tra lại đường dẫn/ID"),
    
    SERVICE_UNAVAILABLE("SYS-501", HttpStatus.SERVICE_UNAVAILABLE, "Dịch vụ tạm thời không khả dụng", "Retry sau theo header Retry-After"),
    DATABASE_TIMEOUT("SYS-502", HttpStatus.GATEWAY_TIMEOUT, "Truy vấn dữ liệu quá thời gian cho phép", "Retry sau, Liên hệ support với id nếu cần"),
    INTERNAL_ERROR("SYS-503", HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi hệ thống", "Liên hệ support với id nếu cần");

    private final String code;
    private final HttpStatus status;
    private final String message;
    private final String clientAction;
}