# 基础镜像：ARM64架构的Ubuntu 24.04 LTS
FROM arm64v8/ubuntu:24.04

# 设置非交互式安装，避免apt-get安装过程中弹出交互提示
ENV DEBIAN_FRONTEND=noninteractive

# 设置时区（Ubuntu系统方式）
RUN ln -snf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo Asia/Shanghai > /etc/timezone

# 更新软件源并安装OpenJDK 17 + 必要依赖
# Ubuntu默认源已包含openjdk-17-jdk，无需额外配置源
RUN apt-get update && apt-get install -y --no-install-recommends \
    openjdk-17-jdk \
    libstdc++6 \
    zlib1g \
    tzdata \
    git \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*  # 清理缓存，减小镜像体积

# 设置JAVA_HOME环境变量（Ubuntu的openjdk-17默认路径）
ENV JAVA_HOME=/usr/lib/jvm/java-17-openjdk-arm64
ENV PATH=$JAVA_HOME/bin:$PATH

# 创建应用目录并设置权限
RUN mkdir -p /app && chmod -R 777 /app /dev/shm
WORKDIR /app

# 复制应用JAR包
COPY build/libs/catalog-service-0.0.1-SNAPSHOT.jar /app/catalog-service.jar

# 恢复DEBIAN_FRONTEND默认值（可选，提升镜像规范性）
ENV DEBIAN_FRONTEND=

# 启动命令
ENTRYPOINT ["java", "-jar", "/app/catalog-service.jar"]