# 基础镜像：ARM64架构的Alpine 3.18
FROM arm64v8/alpine:3.18

# 设置时区
RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo Asia/Shanghai > /etc/timezone

# 安装OpenJDK 17（Alpine官方适配版）+ 必要依赖
RUN apk add --no-cache \
    openjdk17 \
    libstdc++ \
    zlib \
    tzdata \
    git  # 解决git未找到的警告

# 设置JAVA_HOME环境变量（Alpine的openjdk17默认路径）
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk
ENV PATH=$JAVA_HOME/bin:$PATH

# 创建应用目录
RUN mkdir -p /app && chmod -R 777 /app /dev/shm
WORKDIR /app

# 复制应用JAR包
COPY build/libs/catalog-service-0.0.1-SNAPSHOT.jar /app/catalog-service.jar

# 启动命令
ENTRYPOINT ["java", "-jar", "/app/catalog-service.jar"]