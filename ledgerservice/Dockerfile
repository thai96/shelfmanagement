# Sử dụng base image là Java 17 bản rút gọn (Alpine) cho nhẹ
FROM gradle:9.5.1-jdk21-alpine AS builder

ENV APP_HOME=/usr/app/
# Cấu hình thư mục làm việc bên trong container
WORKDIR $APP_HOME
ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8"
COPY build.gradle settings.gradle $APP_HOME
COPY ./ledgerservice/build.gradle $APP_HOME/ledgerservice/
COPY gradle $APP_HOME/gradle
COPY /ledgerservice/gradle $APP_HOME/ledgerservice/gradle
COPY --chown=gradle:gradle . /home/gradle/src
USER root
RUN chown -R gradle /home/gradle/src

COPY ./ledgerservice/ ./ledgerservice/
RUN gradle clean :ledgerservice:bootJar -x test

# Copy file .jar sau khi build vào trong container
# Đảm bảo bạn đã chạy 'mvn clean package' trước đó
FROM eclipse-temurin:21-jre-alpine
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY --from=builder $APP_HOME/ledgerservice/build/libs/*SNAPSHOT.jar app.jar
RUN apk add --no-cache curl
# Mở port 8080 để có thể truy cập từ bên ngoài
EXPOSE $INVENTORY_SERVER_PORT

# Lệnh để chạy ứng dụng khi container khởi động
ENTRYPOINT ["java", "-jar", "app.jar"]
