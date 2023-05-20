INSERT INTO application_version (id, application_id, application_name, version, status, description, heat, conf_data, create_time, update_time, create_by, update_by, del_flag) VALUES(3, 2, 'redis', '6.0', 1, 'Redis（Remote Dictionary Server )，即远程字典服务，是一个开源的使用ANSI C语言编写、支持网络、可基于内存亦可持久化的日志型、Key-Value数据库，并提供多种语言的API。', 0, '{"createNetworks":false,"subApps":[{"key":1,"name":"redis","version":"6.0","image":"redis:6.0","attachStdin":true,"stdinOpen":true,"tty":true,"privileged":false,"publishAllPorts":false,"networkMode":"bridge","restartPolicy":"no","portBinds":[{"description":"通信端口","containerPort":"6379","localPort":"6379","protocol":"tcp","editable":true}],"binds":[{"description":"数据持久化路径","containerPath":"/data","localPath":"/data/redis","editable":true}]}]}', '2023-04-03 09:24:52.000', '2023-04-10 15:20:53.000', 17, NULL, 0);
INSERT INTO application_version (id, application_id, application_name, version, status, description, heat, conf_data, create_time, update_time, create_by, update_by, del_flag) VALUES(4, 3, 'mariadb', '10.2.31', 1, 'MariaDB数据库管理系统是MySQL的一个分支，主要由开源社区在维护，采用GPL授权许可 MariaDB的目的是完全兼容MySQL，包括API和命令行，使之能轻松成为MySQL的代替品。在存储引擎方面，使用XtraDB来代替MySQL的InnoDB。 MariaDB由MySQL的创始人Michael Widenius主导开发，他早前曾以10亿美元的价格，将自己创建的公司MySQL AB卖给了SUN，此后，随着SUN被甲骨文收购，MySQL的所有权也落入Oracle的手中。MariaDB名称来自Michael Widenius的女儿Maria的名字。
MariaDB基于事务的Maria存储引擎，替换了MySQL的MyISAM存储引擎，它使用了Percona的 XtraDB，InnoDB的变体，分支的开发者希望提供访问即将到来的MySQL 5.4 InnoDB性能。这个版本还包括了 PrimeBase XT (PBXT) 和 FederatedX存储引擎。', 0, '{"createNetworks":false,"subApps":[{"key":1,"name":"mariadb-10.2.31","version":"0.0.1","image":"mariadb:10.2.31","attachStdin":true,"stdinOpen":true,"tty":true,"networkMode":"bridge","restartPolicy":"no","envs":[{"envName":"root密码","envKey":"MYSQL_ROOT_PASSWORD","envValue":"root","editable":true}],"portBinds":[{"description":"通信端口","containerPort":"3306","localPort":"3306","protocol":"tcp","editable":true}],"binds":[{"description":"数据存储路径","containerPath":"/var/lib/mysql","localPath":"/data/mysql","editable":true}]}]}', '2023-04-04 18:45:14.000', '2023-04-09 11:46:55.000', 17, NULL, 0);
INSERT INTO application_version (id, application_id, application_name, version, status, description, heat, conf_data, create_time, update_time, create_by, update_by, del_flag) VALUES(5, 4, 'DOF', 'centos7-latest', 0, '该服务占有内存较大，极有可能被系统杀死,当进程被杀死时则需要重启服务
docker restart dnf


部署完成后在window的host文件内添加一行
xx.xx.xx.xx start.dnf.tw
xx.xx.xx.xx对应的是服务器ip地址

一些说明:

需要放行的端口：

3306/tcp 7600/tcp 881/tcp 20303/tcp 20303/udp 20403/tcp 20403/udp 40403/tcp 40403/udp 7000/tcp 7000/udp 7001/tcp 7001/udp 7200/tcp 7200/udp 10011/tcp 31100/tcp 30303/tcp 30303/udp 30403/tcp 30403/udp 10052/tcp 20011/tcp 20203/tcp 20203/udp 30703/udp -11011/udp 2311-2313/udp 30503/udp 11052/udp

-------------------------------------------------------------------

网关端口: 881
通讯密钥: 763WXRBW3PFTC3IXPFWH
登录器版本: 20180307
登录器端口: 7600
GM账户: gm_user
GM密码: gm_pass

-------------------------------------------------------------------

如果进程不存在则需要重启docker服务(进程不存在会出现黑频道),不存在的原因是操作系统杀死该进程，可能是内存或cpu占有过高。多重启几次就好了

重启命令：docker restart dnf

镜像作者交流群: 852685848
客户端下载地址:
链接: https://pan.baidu.com/s/10RgXFtpEhvRUm-hA98Am4A 提取码: fybn

代码地址:
GitHub:
https://github.com/1995chen/dnf
Docker hub:
https://hub.docker.com/repository/docker/1995chen/dnf', 0, '{"createNetworks":false,"subApps":[{"key":1,"name":"dnf-init","version":"centos7-latest","image":"1995chen/dnf:centos7-latest","attachStdin":false,"stdinOpen":false,"tty":false,"privileged":true,"publishAllPorts":false,"cmd":"/home/template/init/init.sh","networkMode":"bridge","restartPolicy":"no","binds":[{"description":"日志保存路径","containerPath":"/home/neople/game/log","localPath":"/data/dnf/log","editable":true},{"description":"数据库保存路径","containerPath":"/var/lib/mysql","localPath":"/data/dnf/mysql","editable":true},{"description":"主数据保存路径","containerPath":"/data","localPath":"/data/dnf/data","editable":true}]},{"key":2,"name":"dnf","version":"centos7-latest","image":"1995chen/dnf:centos7-latest","attachStdin":true,"stdinOpen":true,"tty":true,"privileged":true,"publishAllPorts":false,"networkMode":"bridge","restartPolicy":"no","binds":[{"description":"日志保存路径","containerPath":"/home/neople/game/log","localPath":"/data/dnf/log","editable":true},{"description":"数据库保存路径","containerPath":"/var/lib/mysql","localPath":"/data/dnf/mysql","editable":true},{"description":"主数据保存路径","containerPath":"/data","localPath":"/data/dnf/data","editable":true}],"envs":[{"envName":"外网ip","envKey":"PUBLIC_IP","envValue":"127.0.0.1","editable":true},{"envName":"数据库密码","envKey":"DNF_DB_ROOT_PASSWORD","envValue":"root","editable":true},{"envName":"网关账号","envKey":"GM_ACCOUNT","envValue":"gm_user","editable":true},{"envName":"网关密码","envKey":"GM_PASSWORD","envValue":"gm_pass","editable":true}],"portBinds":[{"description":"DnfGateServe 网关","containerPort":"7600","localPort":"7600","protocol":"tcp"},{"description":"DnfGateServe 网关","containerPort":"881","localPort":"881","protocol":"tcp"},{"description":"df_dbmw_r","containerPort":"20303","localPort":"20303","protocol":"tcp"},{"description":"df_dbmw_r","containerPort":"20303","localPort":"20303","protocol":"udp"},{"description":"df_dbmw_r","containerPort":"20403","localPort":"20403","protocol":"tcp"},{"description":"df_dbmw_r","containerPort":"20403","localPort":"20403","protocol":"udp"},{"description":"df_manager_r","containerPort":"40403","localPort":"40403","protocol":"tcp"},{"description":"df_manager_r","containerPort":"40403","localPort":"40403","protocol":"udp"},{"description":"df_bridge_r","containerPort":"7000","localPort":"7000","protocol":"tcp"},{"description":"df_bridge_r","containerPort":"7000","localPort":"7000","protocol":"udp"},{"description":"df_channel_r","containerPort":"7001","localPort":"7001","protocol":"tcp"},{"description":"df_channel_r","containerPort":"7001","localPort":"7001","protocol":"udp"},{"description":"df_relay_r","containerPort":"7200","localPort":"7200","protocol":"tcp"},{"description":"df_relay_r","containerPort":"7200","localPort":"7200","protocol":"udp"},{"description":"df_game_r","containerPort":"10011","localPort":"10011","protocol":"tcp"},{"description":"df_community","containerPort":"31100","localPort":"31100","protocol":"tcp"},{"description":"df_monitor_r","containerPort":"30303","localPort":"30303","protocol":"tcp"},{"description":"df_monitor_r","containerPort":"30303","localPort":"30303","protocol":"udp"},{"description":"df_guild_r","containerPort":"30403","localPort":"30403","protocol":"tcp"},{"description":"df_guild_r","containerPort":"30403","localPort":"30403","protocol":"udp"},{"description":"df_game_r","containerPort":"10052","localPort":"10052","protocol":"tcp"},{"description":"df_game_r","containerPort":"20011","localPort":"20011","protocol":"tcp"},{"description":"df_dbmw_r","containerPort":"20203","localPort":"20203","protocol":"tcp"},{"description":"df_dbmw_r","containerPort":"20203","localPort":"20203","protocol":"udp"},{"description":"df_coserver_r","containerPort":"30703","localPort":"30703","protocol":"udp"},{"description":"df_game_r","containerPort":"11011","localPort":"11011","protocol":"udp"},{"description":"df_statics_r","containerPort":"30503","localPort":"30503","protocol":"udp"},{"description":"df_game_r","containerPort":"11052","localPort":"11052","protocol":"udp"},{"description":"df_stun_r","containerPort":"2311","localPort":"2311","protocol":"udp"},{"description":"df_stun_r","containerPort":"2312","localPort":"2312","protocol":"udp"},{"description":"df_stun_r","containerPort":"2313","localPort":"2313","protocol":"udp"}]}]}', '2023-04-06 08:55:41.000', '2023-05-07 18:35:35.000', 17, NULL, 0);
INSERT INTO application_version (id, application_id, application_name, version, status, description, heat, conf_data, create_time, update_time, create_by, update_by, del_flag) VALUES(6, 2, 'redis', '6.0-1', 0, 'Redis（Remote Dictionary Server )，即远程字典服务，是一个开源的使用ANSI C语言编写、支持网络、可基于内存亦可持久化的日志型、Key-Value数据库，并提供多种语言的API。', 0, '{"createNetworks":false,"subApps":[{"key":1,"name":"redis","version":"6.0","image":"redis:6.0","attachStdin":true,"stdinOpen":true,"tty":true,"privileged":false,"publishAllPorts":false,"networkMode":"bridge","restartPolicy":"no","portBinds":[{"description":"通信端口","containerPort":"6379","localPort":"6379","protocol":"tcp","editable":true}]}]}', '2023-04-03 09:24:52.000', '2023-04-10 15:31:53.000', 17, NULL, 0);
INSERT INTO application_version (id, application_id, application_name, version, status, description, heat, conf_data, create_time, update_time, create_by, update_by, del_flag) VALUES(11, 4, 'DOF', 'xanderye/dnf:centos7', 1, '服务端

https://hub.docker.com/r/xanderye/dnf-server


数据库

https://hub.docker.com/r/xanderye/dnf-mysql


（1）服务端连接不到数据库请使用root账户登录mysql执行以下命令：

``````sql
update user set host ="%" where user="game";
flush privileges;
``````


(2) 接收频道信息失败

192.168.123.129 start.dnf.tw

(3)灰频道

重新跑  docker restart dnfserver', 0, '{"createNetworks":true,"networks":[{"name":"dnf","ipam":{"Config":[{"Subnet":"172.30.0.0/16"}]}}],"subApps":[{"key":1,"name":"dnfmysql","version":"5.6","image":"registry.cn-hangzhou.aliyuncs.com/gebilaoyu/cache:dnf-mysql-5.6","attachStdin":true,"stdinOpen":true,"tty":true,"privileged":true,"networkMode":"dnf","restartPolicy":"no","envs":[{"envName":"时区","envKey":"TZ","envValue":"Asia/Shanghai"},{"envName":"game账户ip白名单","envKey":"ALLOW_IP","envValue":"172.30.0.%","editable":true},{"envName":"game账户密码","envKey":"GAME_PASSWORD","envValue":"uu5!^%jg","editable":true},{"envName":"数据库root账户密码","envKey":"MYSQL_ROOT_PASSWORD","envValue":"88888888","editable":true}],"portBinds":[{"description":"数据库端口","containerPort":"3306","localPort":"3000","protocol":"tcp","editable":true}]},{"key":2,"name":"dnfserver","version":"centos7","image":"registry.cn-hangzhou.aliyuncs.com/gebilaoyu/cache:dnf-server-centos7-latest","attachStdin":true,"stdinOpen":true,"tty":true,"privileged":true,"nanoCPUs":"2","memory":"4096","shmSize":"8192","memorySwap":"-1","networkMode":"dnf","restartPolicy":"no","envs":[{"envName":"时区","envKey":"TZ","envValue":"Asia/Shanghai","editable":false},{"envName":"自动获取mysql容器的ip","envKey":"AUTO_MYSQL_IP","envValue":"true","editable":true},{"envName":"mysql容器名称（主机名）","envKey":"MYSQL_NAME","envValue":"dnfmysql","editable":true},{"envName":"mysql的IP地址（使用时需要关闭AUTO_MYSQL_IP）","envKey":"MYSQL_IP","envValue":"172.20.0.2","editable":true},{"envName":"mysql的IP端口（使用时需要关闭AUTO_MYSQL_IP）","envKey":"MYSQL_PORT","envValue":"3306","editable":true},{"envName":"game账户密码","envKey":"GAME_PASSWORD","envValue":"uu5!^%jg","editable":true},{"envName":"自动获取公网ip","envKey":"AUTO_PUBLIC_IP","envValue":"false","editable":true},{"envName":"服务器ip地址","envKey":"PUBLIC_IP","envValue":"127.0.0.1","editable":true},{"envName":"开启DP2插件","envKey":"DP2","envValue":"false","editable":true},{"envName":"网关账号","envKey":"GM_ACCOUNT","envValue":"gm_user","editable":true},{"envName":"网关密码","envKey":"GM_PASSWORD","envValue":"gm_pass","editable":true},{"envName":"连接密钥","envKey":"GM_CONNECT_KEY","envValue":"763WXRBW3PFTC3IXPFWH","editable":true},{"envName":"登陆器版本","envKey":"GM_LANDER_VERSION","envValue":"20180307","editable":true}],"portBinds":[{"description":"DnfGateServe 网关","containerPort":"7600","localPort":"7600","protocol":"tcp"},{"description":"DnfGateServe 网关","containerPort":"881","localPort":"881","protocol":"tcp"},{"description":"df_dbmw_r","containerPort":"20303","localPort":"20303","protocol":"tcp"},{"description":"df_dbmw_r","containerPort":"20303","localPort":"20303","protocol":"udp"},{"description":"df_dbmw_r","containerPort":"20403","localPort":"20403","protocol":"tcp"},{"description":"df_dbmw_r","containerPort":"20403","localPort":"20403","protocol":"udp"},{"description":"df_manager_r","containerPort":"40403","localPort":"40403","protocol":"tcp"},{"description":"df_manager_r","containerPort":"40403","localPort":"40403","protocol":"udp"},{"description":"df_bridge_r","containerPort":"7000","localPort":"7000","protocol":"tcp"},{"description":"df_bridge_r","containerPort":"7000","localPort":"7000","protocol":"udp"},{"description":"df_channel_r","containerPort":"7001","localPort":"7001","protocol":"tcp"},{"description":"df_channel_r","containerPort":"7001","localPort":"7001","protocol":"udp"},{"description":"df_relay_r","containerPort":"7200","localPort":"7200","protocol":"tcp"},{"description":"df_relay_r","containerPort":"7200","localPort":"7200","protocol":"udp"},{"description":"df_game_r","containerPort":"10011","localPort":"10011","protocol":"tcp"},{"description":"df_community","containerPort":"31100","localPort":"31100","protocol":"tcp"},{"description":"df_monitor_r","containerPort":"30303","localPort":"30303","protocol":"tcp"},{"description":"df_monitor_r","containerPort":"30303","localPort":"30303","protocol":"udp"},{"description":"df_guild_r","containerPort":"30403","localPort":"30403","protocol":"tcp"},{"description":"df_guild_r","containerPort":"30403","localPort":"30403","protocol":"udp"},{"description":"df_game_r","containerPort":"10052","localPort":"10052","protocol":"tcp"},{"description":"df_game_r","containerPort":"20011","localPort":"20011","protocol":"tcp"},{"description":"df_dbmw_r","containerPort":"20203","localPort":"20203","protocol":"tcp"},{"description":"df_dbmw_r","containerPort":"20203","localPort":"20203","protocol":"udp"},{"description":"df_coserver_r","containerPort":"30703","localPort":"30703","protocol":"udp"},{"description":"df_game_r","containerPort":"11011","localPort":"11011","protocol":"udp"},{"description":"df_statics_r","containerPort":"30503","localPort":"30503","protocol":"udp"},{"description":"df_game_r","containerPort":"11052","localPort":"11052","protocol":"udp"},{"description":"df_stun_r","containerPort":"2311","localPort":"2311","protocol":"udp"},{"description":"df_stun_r","containerPort":"2312","localPort":"2312","protocol":"udp"},{"description":"df_stun_r","containerPort":"2313","localPort":"2313","protocol":"udp"}],"binds":[{"description":"服务端数据存储路径","containerPath":"/data","localPath":"/data/dnf/data","editable":true},{"description":"日志数据存储路径","containerPath":"/home/neople/game/log","localPath":"/data/dnf/log","editable":true}]}]}', '2023-04-09 18:46:07.000', '2023-05-13 20:33:35.000', 17, NULL, 0);
INSERT INTO application_version (id, application_id, application_name, version, status, description, heat, conf_data, create_time, update_time, create_by, update_by, del_flag) VALUES(12, 5, 'minio', 'latest', 1, 'https://hub.docker.com/r/bitnami/minio', 0, '{"createNetworks":false,"subApps":[{"key":1,"name":"bitnami-minio-latest","version":"bitnami-minio:latest","image":"bitnami/minio:latest","attachStdin":true,"stdinOpen":true,"tty":true,"networkMode":"bridge","restartPolicy":"no","portBinds":[{"description":"web端口","containerPort":"9000","localPort":"9000","protocol":"tcp","editable":true},{"description":"api端口","containerPort":"9001","localPort":"9001","protocol":"tcp","editable":true}],"binds":[{"description":"数据存储路径","containerPath":"/data","localPath":"/data/minio","editable":true}]}]}', '2023-04-15 16:49:57.000', '2023-04-15 16:52:02.000', 17, NULL, 0);
INSERT INTO application_version (id, application_id, application_name, version, status, description, heat, conf_data, create_time, update_time, create_by, update_by, del_flag) VALUES(13, 6, 'nginx', 'latest', 1, 'Nginx (engine x) 是一个高性能的HTTP和反向代理web服务器   ，同时也提供了IMAP/POP3/SMTP服务。Nginx是由伊戈尔·赛索耶夫为俄罗斯访问量第二的Rambler.ru站点（俄文：Рамблер）开发的，公开版本1.19.6发布于2020年12月15日。其将源代码以类BSD许可证的形式发布，因它的稳定性、丰富的功能集、简单的配置文件和低系统资源的消耗而闻名。2022年01月25日，nginx 1.21.6发布。

Nginx是一款轻量级的Web 服务器/反向代理服务器及电子邮件（IMAP/POP3）代理服务器，在BSD-like 协议下发行。其特点是占有内存少，并发能力强，事实上nginx的并发能力在同类型的网页服务器中表现较好。', 0, '{"createNetworks":false,"subApps":[{"key":1,"name":"nginx","version":"latest","image":"nginx:latest","attachStdin":true,"stdinOpen":true,"tty":true,"networkMode":"bridge","restartPolicy":"unless-stopped","portBinds":[{"description":"http访问端口","containerPort":"80","localPort":"80","protocol":"tcp"}],"binds":[{"description":"静态文件存放路径","containerPath":"/usr/share/nginx/html","localPath":"/data/nginx/html"}]}]}', '2023-04-17 09:22:56.000', '2023-04-17 09:23:04.000', 17, NULL, 0);
INSERT INTO application_version (id, application_id, application_name, version, status, description, heat, conf_data, create_time, update_time, create_by, update_by, del_flag) VALUES(14, 4, 'DOF', 'centos6-latest', 0, '该服务占有内存较大，极有可能被系统杀死,当进程被杀死时则需要重启服务
docker restart dnf


部署完成后在window的host文件内添加一行
xx.xx.xx.xx start.dnf.tw
xx.xx.xx.xx对应的是服务器ip地址

一些说明:

需要放行的端口：

3306/tcp 7600/tcp 881/tcp 20303/tcp 20303/udp 20403/tcp 20403/udp 40403/tcp 40403/udp 7000/tcp 7000/udp 7001/tcp 7001/udp 7200/tcp 7200/udp 10011/tcp 31100/tcp 30303/tcp 30303/udp 30403/tcp 30403/udp 10052/tcp 20011/tcp 20203/tcp 20203/udp 30703/udp -11011/udp 2311-2313/udp 30503/udp 11052/udp

-------------------------------------------------------------------

网关端口: 881
通讯密钥: 763WXRBW3PFTC3IXPFWH
登录器版本: 20180307
登录器端口: 7600
GM账户: gm_user
GM密码: gm_pass

-------------------------------------------------------------------

如果进程不存在则需要重启docker服务(进程不存在会出现黑频道),不存在的原因是操作系统杀死该进程，可能是内存或cpu占有过高。多重启几次就好了

重启命令：docker restart dnf

镜像作者交流群: 852685848
客户端下载地址:
链接: https://pan.baidu.com/s/10RgXFtpEhvRUm-hA98Am4A 提取码: fybn

代码地址:
GitHub:
https://github.com/1995chen/dnf
Docker hub:
https://hub.docker.com/repository/docker/1995chen/dnf', 5, '{"createNetworks":false,"subApps":[{"key":1,"name":"dnf-init","version":"centos6-latest","image":"1995chen/dnf:centos6-latest","attachStdin":false,"stdinOpen":false,"tty":false,"privileged":true,"publishAllPorts":false,"cmd":"/home/template/init/init.sh","networkMode":"bridge","restartPolicy":"no","binds":[{"description":"日志保存路径","containerPath":"/home/neople/game/log","localPath":"/data/dnf/log","editable":true},{"description":"数据库保存路径","containerPath":"/var/lib/mysql","localPath":"/data/dnf/mysql","editable":true},{"description":"主数据保存路径","containerPath":"/data","localPath":"/data/dnf/data","editable":true}]},{"key":2,"name":"dnf","version":"centos6-latest","image":"1995chen/dnf:centos6-latest","attachStdin":true,"stdinOpen":true,"tty":true,"privileged":true,"publishAllPorts":false,"networkMode":"bridge","restartPolicy":"no","binds":[{"description":"日志保存路径","containerPath":"/home/neople/game/log","localPath":"/data/dnf/log","editable":true},{"description":"数据库保存路径","containerPath":"/var/lib/mysql","localPath":"/data/dnf/mysql","editable":true},{"description":"主数据保存路径","containerPath":"/data","localPath":"/data/dnf/data","editable":true}],"envs":[{"envName":"外网ip","envKey":"PUBLIC_IP","envValue":"127.0.0.1","editable":true},{"envName":"数据库密码","envKey":"DNF_DB_ROOT_PASSWORD","envValue":"root","editable":true},{"envName":"网关账号","envKey":"GM_ACCOUNT","envValue":"gm_user","editable":true},{"envName":"网关密码","envKey":"GM_PASSWORD","envValue":"gm_pass","editable":true}],"portBinds":[{"description":"DnfGateServe 网关","containerPort":"7600","localPort":"7600","protocol":"tcp"},{"description":"DnfGateServe 网关","containerPort":"881","localPort":"881","protocol":"tcp"},{"description":"df_dbmw_r","containerPort":"20303","localPort":"20303","protocol":"tcp"},{"description":"df_dbmw_r","containerPort":"20303","localPort":"20303","protocol":"udp"},{"description":"df_dbmw_r","containerPort":"20403","localPort":"20403","protocol":"tcp"},{"description":"df_dbmw_r","containerPort":"20403","localPort":"20403","protocol":"udp"},{"description":"df_manager_r","containerPort":"40403","localPort":"40403","protocol":"tcp"},{"description":"df_manager_r","containerPort":"40403","localPort":"40403","protocol":"udp"},{"description":"df_bridge_r","containerPort":"7000","localPort":"7000","protocol":"tcp"},{"description":"df_bridge_r","containerPort":"7000","localPort":"7000","protocol":"udp"},{"description":"df_channel_r","containerPort":"7001","localPort":"7001","protocol":"tcp"},{"description":"df_channel_r","containerPort":"7001","localPort":"7001","protocol":"udp"},{"description":"df_relay_r","containerPort":"7200","localPort":"7200","protocol":"tcp"},{"description":"df_relay_r","containerPort":"7200","localPort":"7200","protocol":"udp"},{"description":"df_game_r","containerPort":"10011","localPort":"10011","protocol":"tcp"},{"description":"df_community","containerPort":"31100","localPort":"31100","protocol":"tcp"},{"description":"df_monitor_r","containerPort":"30303","localPort":"30303","protocol":"tcp"},{"description":"df_monitor_r","containerPort":"30303","localPort":"30303","protocol":"udp"},{"description":"df_guild_r","containerPort":"30403","localPort":"30403","protocol":"tcp"},{"description":"df_guild_r","containerPort":"30403","localPort":"30403","protocol":"udp"},{"description":"df_game_r","containerPort":"10052","localPort":"10052","protocol":"tcp"},{"description":"df_game_r","containerPort":"20011","localPort":"20011","protocol":"tcp"},{"description":"df_dbmw_r","containerPort":"20203","localPort":"20203","protocol":"tcp"},{"description":"df_dbmw_r","containerPort":"20203","localPort":"20203","protocol":"udp"},{"description":"df_coserver_r","containerPort":"30703","localPort":"30703","protocol":"udp"},{"description":"df_game_r","containerPort":"11011","localPort":"11011","protocol":"udp"},{"description":"df_statics_r","containerPort":"30503","localPort":"30503","protocol":"udp"},{"description":"df_game_r","containerPort":"11052","localPort":"11052","protocol":"udp"},{"description":"df_stun_r","containerPort":"2311","localPort":"2311","protocol":"udp"},{"description":"df_stun_r","containerPort":"2312","localPort":"2312","protocol":"udp"},{"description":"df_stun_r","containerPort":"2313","localPort":"2313","protocol":"udp"}]}]}', '2023-04-06 08:55:41.000', '2023-05-07 18:35:30.000', 17, NULL, 0);
