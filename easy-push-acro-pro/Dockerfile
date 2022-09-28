FROM registry.cn-hangzhou.aliyuncs.com/gebilaoyu/bitnami:nginx-1.20.2
MAINTAINER gebilaoyu

ENV MANAGER_ADDRESS=http://game-server-manager-server:8888
ENV TZ=Asia/Shanghai



COPY build/ /html/
COPY audio/ /html/audio
COPY nginx.conf /opt/bitnami/nginx/conf/server_blocks/my_server_block.conf

EXPOSE 8080

RUN #sed -i 's/MANAGER_ADDRESS/${MANAGER_ADDRESS}/g' /opt/bitnami/nginx/conf/server_blocks/my_server_block.conf
