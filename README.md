一套游戏管理、开发、云部署的平台软件，摸索开发中。

gitee：https://gitee.com/yuzhanfeng/easy-do-plus


github：https://github.com/easy-do/easy-do-plus


在线预览：https://push.easydo.plus

### 功能简介

##### 主要功能
     开发工具
        代码快速生成
        数据库文档生成
     用户管理
        支持第三方登录
     字典管理
        比较完善
     讨论交流
        评论没做完
     通知公告
        非常完善
     菜单管理
        综合比较健全完善的菜单管理
     日志管理
        系统日志
        执行日志
     服务器管理
        远程执行脚本
     应用管理
        添加、编辑并远程安装docker应用
     客户端管理
        docker安装，占用内存极小
        可以远程管理内网机器的docker镜像、容器
     oauth2授权
        还没弄完
          

##### 主要技术
     spring boot 2.x
     客户端使用 spring boot3.x原生编译
     第三方登录 JustAuth https://www.justauth.cn/
     鉴权：Sa-Token 权限认证, 在线文档：http://sa-token.dev33.cn/
     mysql5.7+
     docker api
     redis
     6....
   


### 快速开始

#### 1.docker方式快速安装

##### 创建网络
```
docker network create game-manager

```

##### 安装数据库
```
 docker run -dit --net=game-manager -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456789 -v /data/mysql:/var/lib/mysql --name manager-mysql mysql:5.7
```

#### 创建数据库
```
数据库名称: game-manager  
字符集：utf8mb4
排序规则: utf8mb4_general_ci

```


##### 安装redis
```
docker run -dit --net=game-manager -p 6379:6379 -e REDIS_PASSWORD=123456789 --name manager-redis bitnami/redis:latest

```

##### 启动网关
```
docker run -dit --net=game-manager --name api-gateway registry.cn-hangzhou.aliyuncs.com/gebilaoyu/easy-do-plus:gateway-1.0.0

```

##### 启动用户中心
```
docker run -dit --net=game-manager -e MYSQL_HOST=manager-mysql -e MYSQL_PORT=3306 -e MYSQL_PASS=123456789 -e REDIS_HOST=manager-redis -e REDIS_PASSWORD=123456789 --name easy-do-plus-uc registry.cn-hangzhou.aliyuncs.com/gebilaoyu/easy-do-plus:uc-1.0.0

```

##### 启动主服务
```
docker run -dit --net=game-manager -e MYSQL_HOST=manager-mysql -e MYSQL_PORT=3306 -e MYSQL_PASS=123456789 -e REDIS_HOST=manager-redis -e REDIS_PASSWORD=123456789 --name easy-do-plus-server registry.cn-hangzhou.aliyuncs.com/gebilaoyu/easy-do-plus:server-1.0.0

```

##### 启动前端页面
```
docker run -dit --net=game-manager -p 8383:8080 --name easy-do-plus-web registry.cn-hangzhou.aliyuncs.com/gebilaoyu/easy-do-plus:web-1.0.0

```
##### 访问系统

```
服务器地址:8383

第三方登录需要自己注册相关平台使用环境变量替换授权配置，环境变量在uc模块的application.yml查看  使用组件: https://www.justauth.cn/guide/
邮箱登录 admin@admin.com 密码 admin
密钥 admin
正式部署请及时修改信息

```

##### 其他配置

以后再补充，详细信息看源码
#### 卸载

```

docker rm -f manager-mysql
docker rm -f manager-redis
docker rm -f api-gateway
docker rm -f easy-do-plus-uc
docker rm -f easy-do-plus-server
docker rm -f easy-do-plus-web
rm -f /data/mysql

```




