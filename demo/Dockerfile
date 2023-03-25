# Simple Dockerfile adding Maven and GraalVM Native Image compiler to the standard
# 镜像版本号来自 graalvm 官网 https://github.com/graalvm/container/pkgs/container/graalvm-ce
FROM ghcr.io/graalvm/graalvm-ce:ol8-java17-22.2.0

ADD . /build
WORKDIR /build

RUN \
    # For SDKMAN to work we need unzip & zip
    # yum install -y unzip zip (graalvm/graalvm-c 默认没有yum所以改用rpm安装)
    rpm -ivh https://yum.oracle.com/repo/OracleLinux/OL8/baseos/latest/x86_64/getPackage/unzip-6.0-46.el8.x86_64.rpm && \
    rpm -ivh https://yum.oracle.com/repo/OracleLinux/OL8/baseos/latest/x86_64/getPackage/zip-3.0-23.el8.x86_64.rpm && \
    # Install SDKMAN
    curl -s "https://get.sdkman.io" | bash; \
    source "$HOME/.sdkman/bin/sdkman-init.sh"; \
    sdk install maven; \
    # Install GraalVM Native Image
    gu install native-image;

RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && mvn --version && native-image --version

RUN source "$HOME/.sdkman/bin/sdkman-init.sh" && mvn -Pnative clean package -DskipTests

# We use a Docker multi-stage build here in order to only take the compiled native Spring Boot App from the first build container
FROM oraclelinux:7-slim

MAINTAINER SHANHY

# Add Spring Boot Native app spring-boot-graal to Container
COPY --from=0 "/build/target/demo" demo

ENV PORT=8081

# Fire up our Spring Boot Native app by default
CMD [ "sh", "-c", "./demo -Dserver.port=$PORT" ]

