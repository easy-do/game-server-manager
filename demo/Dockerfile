# Simple Dockerfile adding Maven and GraalVM Native Image compiler to the standard
# 镜像版本号来自 graalvm 官网 https://github.com/graalvm/container/pkgs/container/graalvm-ce
FROM registry.cn-hangzhou.aliyuncs.com/gebilaoyu/graalvm-ce:java17-22.2.0-maven-native

ADD . /build
WORKDIR /build

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

