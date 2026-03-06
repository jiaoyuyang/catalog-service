# 使用Alpine官方ARM64架构镜像（适配ARM64如M1/M2/M3 Mac、ARM服务器）
FROM arm64v8/alpine:3.18

# 一键完成：换阿里云源+装ARM64版JDK+设时区+建目录（合并步骤，最快构建）
RUN echo "https://mirrors.aliyun.com/alpine/v3.18/main/" > /etc/apk/repositories \
    && echo "https://mirrors.aliyun.com/alpine/v3.18/community/" >> /etc/apk/repositories \
    && apk add --no-cache openjdk17 tzdata libstdc++ zlib git \
    && ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone \
    && apk del tzdata \
    && mkdir -p /app && chmod -R 755 /app

# 修正环境变量写法（消除Docker Desktop语法警告，路径适配ARM64）
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk
ENV PATH=/usr/lib/jvm/java-17-openjdk/bin:$PATH
ENV TZ=Asia/Shanghai

# 工作目录+复制JAR包（最后复制，利用缓存）
WORKDIR /app
COPY build/libs/catalog-service-0.0.1-SNAPSHOT.jar app.jar

# 启动命令（带JVM容器优化，适配ARM64的JVM参数）
ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=80.0", "-jar", "app.jar"]