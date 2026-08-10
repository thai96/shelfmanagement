package com.thai.pham.inventoryservice.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.media.ArraySchema;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.thai.pham.inventoryservice.common.response.ErrorCode;

@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("inventoryservice-public")
                .pathsToMatch("/**")
                .addOpenApiCustomizer(errorCodeTableRegister())
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot REST API")
                        .version("1.0")
                        .description("API documentation for your Spring Boot application"));
    }

    @Bean
    public OperationCustomizer globalErrorResponses() {
        return (operation, handlerMethod) -> {
            operation.getResponses()
                    .addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()), errorResponse("Dữ liệu đầu vào không hợp lệ (VAL-001)"))
                    .addApiResponse(String.valueOf(HttpStatus.CONFLICT.value()), errorResponse("Xung đột nghiệp vụ: hết hàng/âm kho/sửa dổi bất đồng bộ (INV-001..003)"
                            + "Với lỗi INV-003 nên thử lại ở client với header Retry-After"))
                    .addApiResponse(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.value()), errorResponse("Lỗi hệ thống (SYS-503) - Liên hệ support với id"))
                    .addApiResponse(String.valueOf(HttpStatus.SERVICE_UNAVAILABLE.value()), errorResponse("Service tạm thời không khả dụng (SYS-501)"));
            return operation;
        };
    }

    private ApiResponse errorResponse(String description) {
        Schema<?> schema = new Schema<>();
        schema.$ref("#/components/schemas/ErrorResponse");
        return new ApiResponse().description(description)
                .content(new Content().addMediaType("application/json", new MediaType().schema(schema)));
    }

    @Bean
    public OpenApiCustomizer errorSchemaRegister() {
        return openApi -> openApi.getComponents()
                .addSchemas("ErrorResponse", errorSchema());
    }

    private Schema<?> errorSchema() {
        return new Schema<>().type("object")
                .description("Error contract chuẩn")
                .addProperty("errorCode", new Schema<String>().type("string").example("INV-001"))
                .addProperty("message", new Schema<String>().type("string"))
                .addProperty("traceId", new Schema<String>().type("string"))
                .addProperty("timestamp", new Schema<String>().type("string").format("date-time"))
                .addProperty("path", new Schema<String>().type("string"))
                .addProperty("details", new ArraySchema().type("array")
                        .items(new Schema<>().type("object")));

    }

    @Bean
    public OpenApiCustomizer errorCodeTableRegister() {
        return openApi -> {
            String markdownTable = generateTableForErrorCode();
            String currentDescription = openApi.getInfo().getDescription();
            openApi.getInfo().setDescription(currentDescription + markdownTable);
        };
    }

    private String generateTableForErrorCode() {
        StringBuilder tableBuilder = new StringBuilder();
        tableBuilder.append("\n\n### Bảng mã lỗi \n");
        tableBuilder.append("| Code | HttpStatus | Message | ClientAction |\n");
        tableBuilder.append("|---|---|---|---|\n");

        for (ErrorCode code : ErrorCode.values()) {
            tableBuilder.append(String.format("| %s | %d - %s | %s | %s |\n",
                    code.getCode(),
                    code.getStatus().value(),
                    code.getStatus().name(),
                    code.getMessage(),
                    code.getClientAction()
            ));
        }
        return tableBuilder.toString();
    }
}
