# Sử dụng base image là Java 17 bản rút gọn (Alpine) cho nhẹ
FROM eclipse-temurin:17-jdk-alpine AS builder

# Cấu hình thư mục làm việc bên trong container
WORKDIR /builder
COPY . .
ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"

RUN ./gradlew bootJar -x test

# Copy file .jar sau khi build vào trong container
# Đảm bảo bạn đã chạy 'mvn clean package' trước đó
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=builder /builder/build/libs/*SNAPSHOT.jar app.jar

# Mở port 8080 để có thể truy cập từ bên ngoài
EXPOSE 8080

# Lệnh để chạy ứng dụng khi container khởi động
ENTRYPOINT ["java", "-jar", "app.jar"]
