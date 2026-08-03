package com.thai.pham.inventoryservice.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Cấu trúc trả lời lỗi của hệ thống")
public record ErrorResponse(
    @Schema(description = "Mã lỗi", example = "OUT_OF_STOCK")
    String errorCode,
    @Schema(description = "Message an toàn hiển thị cho người dùng")
    String message,
    @Schema(description = "TraceId để tra log/support", example = "1c2b3d")
    String traceId,
    @Schema(description = "Thời điểm lỗi")
    Instant timestamp,
    @Schema(description = "Đường dẫn request")
    String path,
    @Schema(description = "Chi tiết debug")
    String debugDetail,
    @Schema(description = "Danh sách lỗi validation theo field (nếu có)")
    List<FieldError> fieldErrors,
    @Schema(description = "Stacktrace lỗi")
    List<String> debugStackTrace
) {
    public record FieldError(String field, String constraint) {}   
}