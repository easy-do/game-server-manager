FROM registry.cn-hangzhou.aliyuncs.com/gebilaoyu/openjdk:18.0.1-oraclelinux8
MAINTAINER gebilaoyu

ENV JVM_OPTS = ""
ENV PARAMS = ""
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

COPY /target/app.jar /app.jar

EXPOSE 8888

ENTRYPOINT ["sh","-c","java -jar $JAVA_OPTS /app.jar $PARAMS"]