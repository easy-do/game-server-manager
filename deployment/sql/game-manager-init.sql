/*
 Navicat Premium Data Transfer

 Source Server         : 192.168.123.89
 Source Server Type    : MySQL
 Source Server Version : 100231
 Source Host           : 192.168.123.89:3306
 Source Schema         : game-manager-init

 Target Server Type    : MySQL
 Target Server Version : 100231
 File Encoding         : 65001

 Date: 03/09/2022 03:59:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `blob_data` blob NULL COMMENT '存放持久化Trigger对象',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Blob类型的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_BLOB_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日历名称',
  `calendar` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`sched_name`, `calendar_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '日历信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_CALENDARS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `cron_expression` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'cron表达式',
  `time_zone_id` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'Cron类型的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `entry_id` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度器实例id',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度器实例名',
  `fired_time` bigint NOT NULL COMMENT '触发的时间',
  `sched_time` bigint NOT NULL COMMENT '定时器制定的时间',
  `priority` int NOT NULL COMMENT '优先级',
  `state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务名称',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务组名',
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否并发',
  `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否接受恢复执行',
  PRIMARY KEY (`sched_name`, `entry_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '已触发的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_FIRED_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相关介绍',
  `job_class_name` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '执行任务类名称',
  `is_durable` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否持久化',
  `is_nonconcurrent` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否并发',
  `is_update_data` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否更新数据',
  `requests_recovery` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否接受恢复执行',
  `job_data` blob NULL COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '任务详细信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `lock_name` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`sched_name`, `lock_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '存储的悲观锁信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  PRIMARY KEY (`sched_name`, `trigger_group`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '暂停的触发器表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `instance_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '实例名称',
  `last_checkin_time` bigint NOT NULL COMMENT '上次检查时间',
  `checkin_interval` bigint NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`sched_name`, `instance_name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '调度器状态表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `repeat_count` bigint NOT NULL COMMENT '重复的次数统计',
  `repeat_interval` bigint NOT NULL COMMENT '重复的间隔时间',
  `times_triggered` bigint NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '简单触发器的信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_SIMPLE_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_name的外键',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_triggers表trigger_group的外键',
  `str_prop_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `str_prop_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `str_prop_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `int_prop_1` int NULL DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `int_prop_2` int NULL DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `long_prop_1` bigint NULL DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `long_prop_2` bigint NULL DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `dec_prop_1` decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `dec_prop_2` decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `bool_prop_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `bool_prop_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `trigger_name`, `trigger_group`) REFERENCES `QRTZ_TRIGGERS` (`sched_name`, `trigger_name`, `trigger_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '同步机制的行锁表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_SIMPROP_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS`  (
  `sched_name` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `trigger_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器的名字',
  `trigger_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器所属组的名字',
  `job_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_job_details表job_name的外键',
  `job_group` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'qrtz_job_details表job_group的外键',
  `description` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '相关介绍',
  `next_fire_time` bigint NULL DEFAULT NULL COMMENT '上一次触发时间（毫秒）',
  `prev_fire_time` bigint NULL DEFAULT NULL COMMENT '下一次触发时间（默认为-1表示不触发）',
  `priority` int NULL DEFAULT NULL COMMENT '优先级',
  `trigger_state` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器状态',
  `trigger_type` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器的类型',
  `start_time` bigint NOT NULL COMMENT '开始时间',
  `end_time` bigint NULL DEFAULT NULL COMMENT '结束时间',
  `calendar_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日程表名称',
  `misfire_instr` smallint NULL DEFAULT NULL COMMENT '补偿执行的策略',
  `job_data` blob NULL COMMENT '存放持久化job对象',
  PRIMARY KEY (`sched_name`, `trigger_name`, `trigger_group`) USING BTREE,
  INDEX `sched_name`(`sched_name`, `job_name`, `job_group`) USING BTREE,
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`sched_name`, `job_name`, `job_group`) REFERENCES `QRTZ_JOB_DETAILS` (`sched_name`, `job_name`, `job_group`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '触发器详细信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for app_env_info
-- ----------------------------
DROP TABLE IF EXISTS `app_env_info`;
CREATE TABLE `app_env_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `app_id` int NULL DEFAULT NULL COMMENT '所属app',
  `env_name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变量名称',
  `env_key` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变量key',
  `shell_key` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'shell脚本key',
  `env_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '变量参数-默认值',
  `env_type` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变量类型',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_appid`(`app_id`) USING BTREE,
  INDEX `index_creatBy`(`create_by`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '应用变量' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_env_info
-- ----------------------------

-- ----------------------------
-- Table structure for app_info
-- ----------------------------
DROP TABLE IF EXISTS `app_info`;
CREATE TABLE `app_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `app_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'app名称',
  `version` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版本',
  `state` int NULL DEFAULT 0 COMMENT '状态',
  `start_cmd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '启动命令',
  `stop_cmd` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '停止命令',
  `config_file_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '配置文件路径',
  `icon` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '图标',
  `picture` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '图片',
  `author` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者',
  `is_audit` int NULL DEFAULT 0 COMMENT '审核状态',
  `app_scope` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'app作用域',
  `deploy_type` int NULL DEFAULT 1 COMMENT '部署方式',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `heat` int NULL DEFAULT 0 COMMENT '热度',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'APP信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_info
-- ----------------------------

-- ----------------------------
-- Table structure for app_script
-- ----------------------------
DROP TABLE IF EXISTS `app_script`;
CREATE TABLE `app_script`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `adaptation_app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '适配应用名称',
  `adaptation_app_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '适配应用',
  `script_name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '脚本名称',
  `script_type` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '脚本类型',
  `script_scope` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT 'public' COMMENT '脚本作用域',
  `basic_script` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '基础脚本',
  `version` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版本',
  `script_file` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '脚本文件',
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '介绍',
  `heat` int NULL DEFAULT 0 COMMENT '热度',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人',
  `author` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of app_script
-- ----------------------------

INSERT INTO app_script (`id`, `adaptation_app_id`, `adaptation_app_name`, `script_name`, `script_type`, `script_scope`, `basic_script`, `version`, `script_file`, `description`, `heat`, `create_time`, `update_time`, `create_by`, `author`, `update_by`, `del_flag`) VALUES (50, '41', NULL, '官方客户端安装脚本', '部署', 'private', '35', '1.0.0', '#!/bin/bash\r\n#-----BEGIN 脚本取参-----\r\n#应用id 全局变量(勿删)-应用id\r\nexport APPLICATION_ID=\"no\"\r\n#appId 全局变量(勿删)-app的Id\r\nexport APP_ID=\"no\"\r\n#app版本 全局变量(勿删)-应用版本\r\nexport APP_VERSION=\"no\"\r\n#客户端id 全局变量(勿删)-客户端id\r\nexport CLIENT_ID=\"no\"\r\n#客户端版本 全局变量(勿删)-客户端版本\r\nexport CLIENT_VERSION=\"no\"\r\nwhile getopts {:a:b:c:d:e:} opt\r\ndo\r\n    case $opt in\r\n        a)\r\n            APPLICATION_ID=$OPTARG;;\r\n        b)\r\n            APP_ID=$OPTARG;;\r\n        c)\r\n            APP_VERSION=$OPTARG;;\r\n        d)\r\n            CLIENT_ID=$OPTARG;;\r\n        e)\r\n            CLIENT_VERSION=$OPTARG;;\r\n        ?)\r\n        echo \"向脚本传递了意外的参数, 第$OPTIND个 值为:$OPTARG\"\r\n        exit 1;;\r\n    esac\r\ndone\r\n#-----BEGIN 取参示例-----\r\necho APPLICATION_ID=$APPLICATION_ID\r\necho APP_ID=$APP_ID\r\necho APP_VERSION=$APP_VERSION\r\necho CLIENT_ID=$CLIENT_ID\r\necho CLIENT_VERSION=$CLIENT_VERSION\r\n#-----END 取参示例-----\r\n#脚本执行示例: sh xxx.sh -a \'no\' -b \'no\' -c \'no\' -d \'no\' -e \'no\'  \r\n#-----END 脚本取参-----\r\n\r\n\r\n	mkdir /data/easy-push\r\n	cd /data/easy-push\r\n	if ! command -v java; then\r\n		echo \"开始安装java,请耐心等待执行完毕\"\r\n		if [ ! -f OpenJDK17U-jdk_x64_linux_hotspot_17.0.4_8.tar.gz ] ; then\r\n		echo \"安装包不存在，开始下载\"\r\n		wget --no-check-certificate https://mirrors.tuna.tsinghua.edu.cn/Adoptium/17/jdk/x64/linux/OpenJDK17U-jdk_x64_linux_hotspot_17.0.4_8.tar.gz\r\n		fi\r\n		tar zxvf OpenJDK17U-jdk_x64_linux_hotspot_17.0.4_8.tar.gz\r\n		rm -rf /usr/local/java\r\n		mv jdk-17.0.4+8 /usr/local/java\r\n		# 检查原先是否已配置java环境变量\r\n		jdk1=$(grep -n \"export JAVA_HOME=.*\" /etc/profile | cut -f1 -d\':\')\r\n			if [ -n \"$jdk1\" ];then\r\n					echo \"JAVA_HOME已配置,删除内容\"\r\n					sed -i \"${jdk1}d\" /etc/profile\r\n			fi\r\n		jdk2=$(grep -n \"export CLASSPATH=.*\\$JAVA_HOME.*\" /etc/profile | cut -f1 -d\':\')\r\n			if [ -n \"$jdk2\" ];then\r\n					echo \"CLASSPATH路径已配置,删除内容\"\r\n					sed -i \"${jdk2}d\" /etc/profile\r\n			fi\r\n		jdk3=$(grep -n \"export PATH=.*\\$JAVA_HOME.*\" /etc/profile | cut -f1 -d\':\')\r\n			if [ -n \"$jdk3\" ];then\r\n					echo \"PATH-JAVA路径已配置,删除内容\"\r\n					sed -i \"${jdk3}d\" /etc/profile\r\n			fi\r\n		# 检查配置信息\r\n\r\n		echo \"正在配置jdk环境\"\r\n		sed -i \'$a export JAVA_HOME=/usr/local/java\' /etc/profile\r\n		sed -i \'$a export PATH=$PATH:$JAVA_HOME/bin\' /etc/profile\r\n		sed -i \'$a export CLASSPATH=.:$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar\' /etc/profile\r\n		\r\n		echo \"JAVA环境配置已完成.重新加载配置文件\"\r\n		source /etc/profile\r\n		echo \"配置版本信息如下：\"\r\n		java -version\r\n		echo \"安装java执行完成\"\r\n    fi\r\n\r\necho \"拉取客户端最新版本\"\r\nrm -rf client.jar\r\nrm -rf client.log\r\nwget --no-check-certificate https://gitee.com/yuzhanfeng/game-server-manager/releases/download/client-1.0.0/client.jar\r\necho \"拉取完毕。\"\r\necho \"检测客户端是否运行。。。。\"\r\nPROCESS=\"$(ps -ef | grep -E client.jar | grep -v grep | awk \'{print$2}\' )\"\r\nif [[ -n $PROCESS ]];\r\nthen\r\n  echo \"监测到客户端运行，停止进程。。\"\r\n  kill -15 $PROCESS\r\nelse\r\n  echo \"没有正在运行的客户端，直接启动。\"\r\nfi\r\nchmod -R 7777 /data/easy-push/*\r\nnohup java -jar -DCLIENT_ID=$CLIENT_ID -DAPP_ID=$APP_ID -DCLIENT_VERSION=$CLIENT_VERSION client.jar > client.log &\r\n# docker run -dit -e APPLICATION_ID=$applicationId -e CLIENT_ID=$CLIENT_ID -e APP_ID=$APP_ID -e CLIENT_VERSION=$CLIENT_VERSION --privileged=true -v /data/game-manager-client:/data -v /var/run/docker.sock:/var/run/docker.sock -v $(which docker):/usr/bin/docker -p 8000:9999 --name manager-client registry.cn-hangzhou.aliyuncs.com/gebilaoyu/game-server-manager-client:latest\r\n\r\necho \"产看客户端日志: tail -f /data/easy-push/client.log\"\r\n\r\necho \"执行运行命令完毕。\"', '官方客户端安装脚本', 44, '2022-08-05 14:25:16', '2022-09-03 14:31:34', 1, '隔壁老于', 1, 0);


-- ----------------------------
-- Table structure for application_info
-- ----------------------------
DROP TABLE IF EXISTS `application_info`;
CREATE TABLE `application_info`  (
  `application_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '应用id',
  `application_name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用名称',
  `user_id` bigint NULL DEFAULT NULL COMMENT '所属用户',
  `device_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属设备',
  `device_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备名称',
  `device_type` int NULL DEFAULT 0 COMMENT '设备类型',
  `app_id` bigint NULL DEFAULT NULL COMMENT '应用类型',
  `app_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'app名称',
  `status` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '应用状态',
  `public_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '通信公钥',
  `private_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '通信私钥',
  `app_env_cache` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'app变量缓存',
  `is_black` int NULL DEFAULT 0 COMMENT '黑名单',
  `plugins_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '插件同步信息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `last_up_time` datetime NULL DEFAULT NULL COMMENT '最后在线时间',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`application_id`) USING BTREE,
  INDEX `index_server`(`device_id`) USING BTREE,
  INDEX `index_id_user`(`application_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '应用信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of application_info
-- ----------------------------

-- ----------------------------
-- Table structure for authorization_code
-- ----------------------------
DROP TABLE IF EXISTS `authorization_code`;
CREATE TABLE `authorization_code`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权码',
  `state` int NULL DEFAULT 0 COMMENT '状态 0未使用 1已使用 2.可重复使用 3.废弃',
  `config` text CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL COMMENT '配置信息',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_code`(`code`) USING BTREE,
  INDEX `index_code_state`(`code`, `state`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '授权码' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of authorization_code
-- ----------------------------

-- ----------------------------
-- Table structure for blacklist
-- ----------------------------
DROP TABLE IF EXISTS `blacklist`;
CREATE TABLE `blacklist`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `ip` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP',
  `disable_time` datetime NULL DEFAULT NULL COMMENT '黑名单结束时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` tinyint(1) NULL DEFAULT 0 COMMENT '是否删除(1未删除；0已删除)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = 'IP黑名单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of blacklist
-- ----------------------------

-- ----------------------------
-- Table structure for client_info
-- ----------------------------
DROP TABLE IF EXISTS `client_info`;
CREATE TABLE `client_info`  (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `client_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属用户',
  `server_id` bigint NULL DEFAULT NULL COMMENT '所属服务器',
  `server_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务器名称',
  `user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属用户',
  `status` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态',
  `client_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '客户端信息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `last_up_time` datetime NULL DEFAULT NULL COMMENT '最后在线时间',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标记',
  `public_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '通信公钥',
  `private_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '通信私钥',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户端信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of client_info
-- ----------------------------

-- ----------------------------
-- Table structure for client_message
-- ----------------------------
DROP TABLE IF EXISTS `client_message`;
CREATE TABLE `client_message`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `client_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户端id',
  `message_type` int NULL DEFAULT 0 COMMENT '消息类型',
  `message` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '消息内容',
  `status` int NULL DEFAULT 0 COMMENT '状态 0发布 1接收 2消费 3成功 4失败',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '客户端消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of client_message
-- ----------------------------

-- ----------------------------
-- Table structure for comment_details
-- ----------------------------
DROP TABLE IF EXISTS `comment_details`;
CREATE TABLE `comment_details`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `business_id` int NOT NULL COMMENT '关联业务主键',
  `parent_id` int NOT NULL DEFAULT 0 COMMENT '所属一级评论id',
  `comment_id` int NOT NULL DEFAULT 0 COMMENT '回复的评论id',
  `type` int NOT NULL COMMENT '类型',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '正文',
  `agree` int NOT NULL DEFAULT 0 COMMENT '同意',
  `oppose` int NOT NULL DEFAULT 0 COMMENT '反对',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `user_id` bigint NOT NULL COMMENT '回复人',
  `user_name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '回复人用户名',
  `to_user_id` bigint NULL DEFAULT 0 COMMENT '被回复用户id',
  `to_user_name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '被回复的用户名',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 17 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评论信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment_details
-- ----------------------------
INSERT INTO `comment_details` VALUES (13, 26, 0, 0, 1, '沙发😜😜😜😜😜😜😜😜', 0, 0, NULL, '2022-07-11 23:14:34', 10, '小***车', 17, '隔壁老于', 0);

-- ----------------------------
-- Table structure for dict_data
-- ----------------------------
DROP TABLE IF EXISTS `dict_data`;
CREATE TABLE `dict_data`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `dict_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'key',
  `dict_value` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '参数',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of dict_data
-- ----------------------------

-- ----------------------------
-- Table structure for discussion_details
-- ----------------------------
DROP TABLE IF EXISTS `discussion_details`;
CREATE TABLE `discussion_details`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `title` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '正文',
  `status` int NOT NULL DEFAULT 0 COMMENT '状态 0草稿 1提交 2审核通过',
  `hop` bigint NULL DEFAULT 0 COMMENT '热度',
  `agree` int NOT NULL DEFAULT 0 COMMENT '同意',
  `oppose` int NOT NULL DEFAULT 0 COMMENT '反对',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `create_by` bigint NOT NULL COMMENT '创建人',
  `update_by` bigint NOT NULL COMMENT '更新人',
  `create_name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建用户名',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 38 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '常见问题' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of discussion_details
-- ----------------------------
INSERT INTO `discussion_details` VALUES (1, '如何获取授权码', '前往官方交流群公告获取', 2, 0, 0, 0, NULL, '2022-07-04 19:56:32', '2022-07-04 19:56:35', 1, 1, '隔壁老于', 0);
INSERT INTO `discussion_details` VALUES (2, '网站建立的初衷', '因个人兴趣爱好开发，目前处于个人开发阶段、目前非盈利项目，且用且珍惜。', 2, 0, 0, 0, NULL, '2022-07-04 19:56:32', '2022-07-04 19:56:35', 1, 1, '隔壁老于', 0);
INSERT INTO `discussion_details` VALUES (3, '送给小白的话', '目前虽然已经做到尽可能的傻瓜操作，某些问题需要有一定互联网基础知识。遇到不懂的问题可以多在群内请教。', 2, 0, 0, 0, NULL, '2022-07-04 19:56:32', '2022-07-23 19:39:22', 1, 1, '隔壁老于', 0);
INSERT INTO `discussion_details` VALUES (22, '话题内容展示示例', '## 😲 md-editor-v3\r\n\r\nMarkdown 编辑器，vue3 版本，使用 jsx 模板 和 typescript 开发，支持切换主题、prettier 美化文本等。\r\n\r\n### 🤖 基本演示\r\n\r\n**加粗**，<u>下划线</u>，_斜体_，~删除线~，上标<sup>26</sup>，下标<sub>[1]</sub>，`inline code`，[超链接](https://imzbf.cc)\r\n\r\n> 引用：这是一段引用。\r\n\r\n![mark and Emoji extension](https://imzbf.github.io/md-editor-v3/imgs/mark_emoji.gif)\r\n\r\n## 🤗 代码演示\r\n\r\n```vue\r\n<template>\r\n  <md-editor v-model=\"text\" />\r\n</template>\r\n\r\n<script setup>\r\nimport { ref } from \'vue\';\r\nimport MdEditor from \'md-editor-v3\';\r\nimport \'md-editor-v3/lib/style.css\';\r\n\r\nconst text = ref(\'Hello Editor!\');\r\n</script>\r\n```\r\n\r\n## 🖨 文本演示\r\n\r\n依照普朗克长度这项单位，目前可观测的宇宙的直径估计值（直径约 930 亿光年，即 8.8 × 10<sup>26</sup> 米）即为 5.4 × 10<sup>61</sup>倍普朗克长度。而可观测宇宙体积则为 8.4 × 10<sup>184</sup>立方普朗克长度（普朗克体积）。\r\n\r\n## 📈 表格演示\r\n\r\n| 昵称 | 猿龄（年） | 来自      |\r\n| ---- | ---------- | --------- |\r\n| 之间 | 4          | 中国-重庆 |\r\n\r\n## 📏 公式\r\n\r\n$$\r\n\\sqrt[3]{x}\r\n$$\r\n\r\n## ☘️ 占个坑@！', 2, 0, 0, 0, NULL, '2022-07-08 21:09:52', '2022-07-08 21:09:52', 1, 1, '隔壁老于', 0);
INSERT INTO `discussion_details` VALUES (26, '手动安装Docker教程', '## 使用官方安装脚本自动安装\n###### 安装命令如下：\n\n```language\ncurl -fsSL https://get.docker.com | bash -s docker --mirror Aliyun\n```\n\n###### 也可以使用国内 daocloud 一键安装命令：\n\n```language\ncurl -sSL https://get.daocloud.io/docker | sh\n```\n\n## 手动安装\n\n###### 卸载旧版本\n较旧的 Docker 版本称为 docker 或 docker-engine 。如果已安装这些程序，请卸载它们以及相关的依赖项。\n\n```language\n$ sudo yum remove docker \\\n                  docker-client \\\n                  docker-client-latest \\\n                  docker-common \\\n                  docker-latest \\\n                  docker-latest-logrotate \\\n                  docker-logrotate \\\n                  docker-engine\n```\n\n##### 安装 Docker Engine-Community\n使用 Docker 仓库进行安装\n在新主机上首次安装 Docker Engine-Community 之前，需要设置 Docker 仓库。之后，您可以从仓库安装和更新 Docker。\n\n设置仓库\n\n安装所需的软件包。yum-utils 提供了 yum-config-manager ，并且 device mapper 存储驱动程序需要 device-mapper-persistent-data 和 lvm2。\n\n```language\n$ sudo yum install -y yum-utils \\\n  device-mapper-persistent-data \\\n  lvm2\n```\n\n使用以下命令来设置稳定的仓库。\n\n使用官方源地址（比较慢）\n```language\n$ sudo yum-config-manager \\\n    --add-repo \\\n    https://download.docker.com/linux/centos/docker-ce.repo\n```\n\n可以选择国内的一些源地址：\n\n阿里云\n```language\n$ sudo yum-config-manager \\\n    --add-repo \\\n    http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo\n```\n\n清华大学源\n```language\n$ sudo yum-config-manager \\\n    --add-repo \\\n    https://mirrors.tuna.tsinghua.edu.cn/docker-ce/linux/centos/docker-ce.repo\n```\n\n安装 Docker Engine-Community\n安装最新版本的 Docker Engine-Community 和 containerd，或者转到下一步安装特定版本：\n\n```language\n$ sudo yum install docker-ce docker-ce-cli containerd.io\n```\n\n如果提示您接受 GPG 密钥，请选是。\n\n有多个 Docker 仓库吗？\n\n如果启用了多个 Docker 仓库，则在未在 yum install 或 yum update 命令中指定版本的情况下，进行的安装或更新将始终安装最高版本，这可能不适合您的稳定性需求。\n\nDocker 安装完默认未启动。并且已经创建好 docker 用户组，但该用户组下没有用户。\n\n要安装特定版本的 Docker Engine-Community，请在存储库中列出可用版本，然后选择并安装：\n\n1、列出并排序您存储库中可用的版本。此示例按版本号（从高到低）对结果进行排序。\n\n```language\n$ yum list docker-ce --showduplicates | sort -r\n\ndocker-ce.x86_64  3:18.09.1-3.el7                     docker-ce-stable\ndocker-ce.x86_64  3:18.09.0-3.el7                     docker-ce-stable\ndocker-ce.x86_64  18.06.1.ce-3.el7                    docker-ce-stable\ndocker-ce.x86_64  18.06.0.ce-3.el7                    docker-ce-stable\n```\n\n2、通过其完整的软件包名称安装特定版本，该软件包名称是软件包名称（docker-ce）加上版本字符串（第二列），从第一个冒号（:）一直到第一个连字符，并用连字符（-）分隔。例如：docker-ce-18.09.1。\n\n```language\n$ sudo yum install docker-ce-<VERSION_STRING> docker-ce-cli-<VERSION_STRING> containerd.io\n```\n\n启动 Docker。\n\n```language\n$ sudo systemctl start docker\n```\n\n通过运行 hello-world 映像来验证是否正确安装了 Docker Engine-Community 。\n\n```language\n$ sudo docker run hello-world\n```\n\n卸载 docker\n删除安装包：\n\n```language\nyum remove docker-ce\n```\n\n删除镜像、容器、配置文件等内容：\n\n```language\nrm -rf /var/lib/docker\n```\n', 2, 0, 0, 0, NULL, '2022-07-10 16:28:57', '2022-07-23 19:28:15', 1, 1, '隔壁老于', 0);
INSERT INTO `discussion_details` VALUES (32, '口袋部署视频教程', '<iframe src=\"//player.bilibili.com/player.html?bvid=BV1wU4y1y73Y&page=1\" scrolling=\"yes\" border=\"0\" frameborder=\"no\" framespacing=\"0\" allowfullscreen=\"true\" style=\"width: 100%; height: 800px; max-width: 100%\"> </iframe>\n', 2, 0, 0, 0, NULL, '2022-08-18 00:05:13', '2022-08-18 00:09:02', 1, 1, '隔壁老于', 0);
INSERT INTO `discussion_details` VALUES (33, '内网机器客户端安装和内网穿透的云部署演示', '<iframe src=\"//player.bilibili.com/player.html?aid=514271727&bvid=BV18g411y7vn&cid=798694500&page=1\" scrolling=\"yes\" border=\"0\" frameborder=\"no\" framespacing=\"0\" allowfullscreen=\"true\" style=\"width: 100%; height: 800px; max-width: 100%\"> </iframe>\n', 2, 0, 0, 0, NULL, '2022-08-18 00:10:51', '2022-08-18 00:10:59', 1, 1, '隔壁老于', 0);
INSERT INTO `discussion_details` VALUES (34, 'DNF云部署教程', '<iframe src=\"////player.bilibili.com/player.html?aid=812803523&bvid=BV1234y1W7fQ&cid=755676706&page=1\" scrolling=\"yes\" border=\"0\" frameborder=\"no\" framespacing=\"0\" allowfullscreen=\"true\" style=\"width: 100%; height: 800px; max-width: 100%\"> </iframe>\n\n', 2, 0, 0, 0, NULL, '2022-08-18 00:12:18', '2022-08-18 00:13:07', 1, 1, '隔壁老于', 0);
INSERT INTO `discussion_details` VALUES (35, '逍遥西游云部署演示', '<iframe src=\"//player.bilibili.com/player.html?aid=257445146&bvid=BV1tY411K7hf&cid=744705244&page=1\" scrolling=\"yes\" border=\"0\" frameborder=\"no\" framespacing=\"0\" allowfullscreen=\"true\" style=\"width: 100%; height: 800px; max-width: 100%\"> </iframe>\n\n\n', 2, 0, 0, 0, NULL, '2022-08-18 00:13:01', '2022-08-18 00:13:06', 1, 1, '隔壁老于', 0);
INSERT INTO `discussion_details` VALUES (36, '应用创作和脚本编写功能的使用教程', '<iframe src=\"//player.bilibili.com/player.html?aid=897034924&bvid=BV1NA4y1f7Y3&cid=733275559&page=1\" scrolling=\"yes\" border=\"0\" frameborder=\"no\" framespacing=\"0\" allowfullscreen=\"true\" style=\"width: 100%; height: 800px; max-width: 100%\"> </iframe>\n', 2, 0, 0, 0, NULL, '2022-08-18 00:14:59', '2022-08-18 00:15:05', 1, 1, '隔壁老于', 0);
INSERT INTO `discussion_details` VALUES (37, 'windows安装docker教程', '**微软官方教程：**\nhttps://docs.microsoft.com/zh-cn/virtualization/windowscontainers/quick-start/set-up-environment?tabs=Windows-Server\n\n请按照对应系统的安装教程进行操作。\n', 2, 0, 0, 0, NULL, '2022-08-29 14:33:40', '2022-08-29 14:33:48', 1, 1, '隔壁老于', 0);

-- ----------------------------
-- Table structure for execute_log
-- ----------------------------
DROP TABLE IF EXISTS `execute_log`;
CREATE TABLE `execute_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `application_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用id',
  `application_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '应用名称',
  `app_id` bigint NULL DEFAULT NULL COMMENT 'APPId',
  `app_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'APP名称',
  `script_id` bigint NULL DEFAULT NULL COMMENT '脚本id',
  `script_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '脚本名称',
  `device_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备id',
  `device_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '设备名称',
  `device_type` int NULL DEFAULT 0 COMMENT '设备类型',
  `start_time` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `log_data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '日志详情',
  `execute_state` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_application`(`application_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '执行日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of execute_log
-- ----------------------------

-- ----------------------------
-- Table structure for file_store
-- ----------------------------
DROP TABLE IF EXISTS `file_store`;
CREATE TABLE `file_store`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `file_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '文件名',
  `file_size` int NOT NULL COMMENT '文件大小',
  `file` longblob NOT NULL COMMENT '文件',
  `md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'md5',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of file_store
-- ----------------------------

-- ----------------------------
-- Table structure for server_info
-- ----------------------------
DROP TABLE IF EXISTS `server_info`;
CREATE TABLE `server_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `server_name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务名称',
  `address` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '服务地址',
  `port` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '端口',
  `user_name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_user`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '服务器信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of server_info
-- ----------------------------

-- ----------------------------
-- Table structure for sys_dict_data
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_data`;
CREATE TABLE `sys_dict_data`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `dict_type_id` bigint NOT NULL COMMENT '字典类型',
  `dict_sort` int NOT NULL DEFAULT 0 COMMENT '字典排序',
  `dict_label` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '显示标签',
  `dict_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '字典键值',
  `dict_value_type` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典参数类型',
  `is_default` int NULL DEFAULT 0 COMMENT '是否默认（0否 1是）',
  `status` int NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典数据表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES (1, 1, 0, '是', 'yes', '1', 'num', 0, 0, NULL, '2022-07-22 17:34:01', NULL, '2022-07-23 18:07:24', NULL);
INSERT INTO `sys_dict_data` VALUES (2, 1, 0, '否', 'no', '0', 'num', 0, 0, NULL, '2022-07-22 17:34:46', NULL, '2022-07-23 18:07:30', NULL);
INSERT INTO `sys_dict_data` VALUES (3, 2, 0, '开启', 'enable', '0', 'num', 1, 0, NULL, '2022-07-22 18:15:02', NULL, '2022-07-23 15:10:48', NULL);
INSERT INTO `sys_dict_data` VALUES (4, 2, 0, '关闭', 'disabled', '1', 'num', 0, 0, NULL, '2022-07-22 18:15:35', NULL, '2022-07-23 15:45:39', NULL);
INSERT INTO `sys_dict_data` VALUES (5, 3, 0, '整数', 'num', 'num', 'str', 0, 0, NULL, '2022-07-23 17:39:16', NULL, '2022-07-23 22:49:03', NULL);
INSERT INTO `sys_dict_data` VALUES (6, 3, 0, '字符', 'str', 'str', 'str', 0, 0, NULL, '2022-07-23 17:39:34', NULL, '2022-07-23 22:52:28', NULL);
INSERT INTO `sys_dict_data` VALUES (7, 4, 0, '邮箱', 'admin_email', '123@123.com', 'str', 0, 0, NULL, '2022-07-23 23:01:07', NULL, '2022-07-23 23:01:13', NULL);
INSERT INTO `sys_dict_data` VALUES (8, 5, 0, '代理证书', 'azClientCert', '/cert/mitmproxy-ca-cert.cer', 'str', 0, 0, NULL, '2022-07-30 18:02:44', NULL, '2022-07-30 18:02:44', '原神安卓代理证书');
INSERT INTO `sys_dict_data` VALUES (10, 6, 0, '客户端安装脚本', 'client_install_script', '50', 'str', 0, 0, NULL, '2022-08-05 12:45:41', NULL, '2022-08-05 14:35:20', NULL);
INSERT INTO `sys_dict_data` VALUES (11, 6, 0, '客户端APPID', 'client_app_id', '41', 'str', 0, 0, NULL, '2022-08-05 14:23:57', NULL, '2022-08-05 14:23:57', NULL);
INSERT INTO `sys_dict_data` VALUES (12, 6, 0, '客户端安装命令', 'client_install_cmd', 'wget -O client_install.sh https://manager.easydo.plus/server/client/installScript && sh client_install.sh', 'str', 0, 0, NULL, '2022-08-07 00:38:29', NULL, '2022-08-07 00:54:00', NULL);
INSERT INTO `sys_dict_data` VALUES (13, 6, 0, '客户端卸载脚本', 'client_uninstall_script', '70', 'str', 0, 0, NULL, '2022-08-10 21:42:17', NULL, '2022-08-10 21:42:17', NULL);
INSERT INTO `sys_dict_data` VALUES (14, 6, 0, '部署次数', 'deploy_count', '5051', 'num', 0, 0, NULL, '2022-09-01 10:56:32', NULL, '2022-09-01 10:56:32', NULL);

-- ----------------------------
-- Table structure for sys_dict_type
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '字典名称',
  `dict_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典编码',
  `status` int NULL DEFAULT 0 COMMENT '状态（0正常 1停用）',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典类型表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES (1, '是否', 'is_no_select', 0, NULL, '2022-07-22 17:29:37', NULL, '2022-07-23 18:17:31', NULL);
INSERT INTO `sys_dict_type` VALUES (2, '开启状态', 'status_select', 0, NULL, '2022-07-22 18:14:35', NULL, '2022-07-23 18:13:29', NULL);
INSERT INTO `sys_dict_type` VALUES (3, '字典参数类型', 'dict_value_type', 0, NULL, '2022-07-23 17:38:35', NULL, '2022-07-23 18:17:03', NULL);
INSERT INTO `sys_dict_type` VALUES (4, '管理员信息', 'admin_details', 0, NULL, '2022-07-23 22:38:01', NULL, '2022-07-23 22:38:01', NULL);
INSERT INTO `sys_dict_type` VALUES (5, '插件下载文件路径', 'plugin_config_file', 0, NULL, '2022-07-30 18:01:11', NULL, '2022-07-30 18:01:11', NULL);
INSERT INTO `sys_dict_type` VALUES (6, '系统配置', 'system_config', 0, NULL, '2022-08-04 23:20:40', NULL, '2022-08-04 23:20:40', NULL);

-- ----------------------------
-- Table structure for sys_job
-- ----------------------------
DROP TABLE IF EXISTS `sys_job`;
CREATE TABLE `sys_job`  (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串 class.method',
  `cron_expression` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `job_type` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `job_param` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '任务参数',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注信息',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`job_id`, `job_name`, `job_group`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job
-- ----------------------------

-- ----------------------------
-- Table structure for sys_job_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_job_log`;
CREATE TABLE `sys_job_log`  (
  `job_log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_id` bigint NOT NULL COMMENT '任务ID',
  `job_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日志信息',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `result` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL,
  `exception_info` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '异常信息',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调度日志表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_job_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_log
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `data` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统日志' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_log
-- ----------------------------

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu`  (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父菜单ID',
  `order_num` int NULL DEFAULT 0 COMMENT '显示顺序',
  `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '路由参数',
  `is_frame` int NULL DEFAULT 1 COMMENT '是否为外链（0是 1否）',
  `is_cache` int NULL DEFAULT 0 COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` int NULL DEFAULT 0 COMMENT '菜单状态（0显示 1隐藏）',
  `status` int NULL DEFAULT 0 COMMENT '菜单状态（0正常 1停用）',
  `perms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限标识',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单图标',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '备注',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 28 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES (1, '首页', 19, 0, '/home', '', '', 1, 1, 'C', 0, 0, 'home', 'IconHome', 17, '2022-07-20 00:06:46', 17, '2022-07-20 19:52:55', '首页', 0);
INSERT INTO `sys_menu` VALUES (2, '系统管理', 19, 99, '', '', '', 1, 0, 'M', 0, 0, 'systemManager', 'IconSetting', 17, '2022-07-20 00:18:22', 17, '2022-07-21 15:15:03', '', 0);
INSERT INTO `sys_menu` VALUES (3, '服务管理', 19, 2, '/server', '', '', 1, 0, 'C', 0, 0, 'serverManager', 'IconCloud', 17, '2022-07-20 00:22:30', 17, '2022-07-21 15:04:18', '', 0);
INSERT INTO `sys_menu` VALUES (4, '应用管理', 19, 3, '/application', '', '', 1, 0, 'C', 0, 0, 'application', 'IconAppCenter', 17, '2022-07-20 00:23:47', 17, '2022-07-21 15:08:01', '', 0);
INSERT INTO `sys_menu` VALUES (5, 'APP创作', 19, 4, '/app', '', '', 1, 0, 'C', 0, 0, 'appInfo', 'IconApps', 17, '2022-07-20 00:24:22', 17, '2022-07-21 15:08:10', '', 0);
INSERT INTO `sys_menu` VALUES (6, '脚本创作', 19, 5, '/appScript', '', '', 1, 0, 'C', 0, 0, 'appScript', 'IconComment', 17, '2022-07-20 00:24:53', 17, '2022-07-21 15:08:28', '', 0);
INSERT INTO `sys_menu` VALUES (7, '日志管理', 2, 0, '', '', '', 1, 0, 'M', 0, 0, 'logManager', 'IconArticle', 17, '2022-07-20 00:25:50', 17, '2022-07-23 15:31:54', '', 0);
INSERT INTO `sys_menu` VALUES (8, '任务日志', 7, 9, '/jobLog', '', '', 1, 0, 'C', 0, 0, 'jobLog', 'IconArticle', 17, '2022-07-20 00:26:28', 17, '2022-07-29 16:04:40', '', 0);
INSERT INTO `sys_menu` VALUES (9, '系统日志', 7, 1, '/sysLog', '', '', 1, 0, 'C', 0, 0, 'sysLog', 'IconArticle', 17, '2022-07-20 00:26:42', 17, '2022-07-23 15:30:43', '', 0);
INSERT INTO `sys_menu` VALUES (10, '执行日志', 7, 3, '/execLog', '', '', 1, 0, 'C', 0, 0, 'execLog', 'IconArticle', 17, '2022-07-20 00:26:56', 17, '2022-07-21 13:46:34', '', 0);
INSERT INTO `sys_menu` VALUES (11, '用户管理', 2, 0, '/userManager', '', '', 1, 0, 'C', 0, 0, 'userManager', 'IconUser', 17, '2022-07-20 09:52:34', 17, '2022-07-21 15:08:49', '', 0);
INSERT INTO `sys_menu` VALUES (12, '角色管理', 2, 0, '/roleManager', '', '', 1, 0, 'C', 0, 0, 'roleManager', 'IconUserCircle', 17, '2022-07-20 09:53:27', 17, '2022-07-21 15:08:58', '', 0);
INSERT INTO `sys_menu` VALUES (13, '菜单管理', 2, 0, '/menuManager', '', '', 1, 0, 'C', 0, 0, 'menuManager', 'IconListView', 17, '2022-07-20 09:54:02', 17, '2022-07-21 15:19:01', '', 0);
INSERT INTO `sys_menu` VALUES (14, '话题管理', 2, 0, '/discussionManager', '', '', 1, 0, 'C', 0, 0, 'discussionManager', 'IconComment', 17, '2022-07-20 09:54:16', 17, '2022-07-20 19:58:28', '', 0);
INSERT INTO `sys_menu` VALUES (15, '文件存储', 2, 0, '/fileStoreManager', '', '', 1, 0, 'C', 0, 0, 'fileStoreManager', 'IconFolder', 17, '2022-07-20 09:54:32', 17, '2022-07-20 19:58:48', '', 0);
INSERT INTO `sys_menu` VALUES (16, '授权码', 2, 6, '/authCode', '', '', 1, 0, 'C', 0, 0, 'authCode', 'IconKey', 17, '2022-07-20 09:54:46', 17, '2022-07-20 19:55:11', '', 0);
INSERT INTO `sys_menu` VALUES (17, '定时任务', 2, 7, '/job', '', '', 1, 0, 'C', 0, 0, 'job', 'IconClock', 17, '2022-07-20 09:54:54', 17, '2022-07-21 15:17:35', '', 0);
INSERT INTO `sys_menu` VALUES (18, '账户设置', 19, 1, '/setting', '', '', 1, 0, 'C', 0, 0, NULL, 'IconUser', 17, '2022-07-20 09:56:04', 17, '2022-07-21 15:19:51', '', 0);
INSERT INTO `sys_menu` VALUES (19, '侧边导航', 0, 0, '', '', '', 1, 0, 'M', 0, 0, NULL, '', 17, '2022-07-20 17:46:11', NULL, NULL, '', 0);
INSERT INTO `sys_menu` VALUES (20, '顶部导航', 0, 0, '', '', '', 1, 0, 'M', 0, 0, NULL, '', 17, '2022-07-20 17:48:09', NULL, NULL, '', 0);
INSERT INTO `sys_menu` VALUES (21, '首页', 20, 0, '/home', '', '', 1, 0, 'C', 0, 0, NULL, 'IconHome', 17, '2022-07-20 17:48:32', 17, '2022-07-21 15:09:30', '', 0);
INSERT INTO `sys_menu` VALUES (22, 'APP市场', 20, 0, '/appStore', '', '', 1, 0, 'C', 0, 0, NULL, 'IconShoppingBag', 17, '2022-07-20 17:48:46', 17, '2022-07-23 15:30:00', '', 0);
INSERT INTO `sys_menu` VALUES (23, '讨论交流', 20, 0, '/discussion', '', '', 1, 0, 'C', 0, 0, NULL, 'IconComment', 17, '2022-07-20 17:49:04', 17, '2022-07-21 15:16:44', '', 0);
INSERT INTO `sys_menu` VALUES (24, '官方Q群', 20, 0, 'https://jq.qq.com/?_wv=1027&k=OfQIQC2Y', '', '', 1, 0, 'C', 0, 0, NULL, 'IconUserGroup', 17, '2022-07-20 17:49:35', 17, '2022-07-21 15:16:51', '', 0);
INSERT INTO `sys_menu` VALUES (25, '字典管理', 2, 0, '/dictType', NULL, '', 1, 0, 'C', 0, 0, 'dictType', 'IconArticle', 17, '2022-07-22 15:37:02', NULL, NULL, '', 0);
INSERT INTO `sys_menu` VALUES (26, '客户端管理', 19, 0, '/client', NULL, '', 1, 0, 'C', 0, 0, 'client', 'IconSetting', 17, '2022-08-04 22:28:00', NULL, '2022-08-05 19:50:51', '', 0);
INSERT INTO `sys_menu` VALUES (27, '开发工具', 19, 0, '', NULL, '', 1, 0, 'M', 0, 0, 'dev_utils', 'IconSetting', 17, '2022-08-31 10:17:06', 17, '2022-08-31 10:19:14', '', 0);

-- ----------------------------
-- Table structure for sys_notice
-- ----------------------------
DROP TABLE IF EXISTS `sys_notice`;
CREATE TABLE `sys_notice`  (
  `notice_id` int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告标题',
  `notice_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob NULL COMMENT '公告内容',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '通知公告表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_notice
-- ----------------------------

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role`  (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL DEFAULT 0 COMMENT '显示顺序',
  `is_default` int NOT NULL DEFAULT 0 COMMENT '是否默认角色,0否 1是',
  `status` int NOT NULL DEFAULT 0 COMMENT '角色状态（0正常 1停用）',
  `create_by` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES (1, '超级管理员', 'super_admin', 0, 0, 0, NULL, '2022-07-20 17:38:29', NULL, NULL, '所有权限', 0);
INSERT INTO `sys_role` VALUES (2, '普通用户', 'role_basic', 0, 1, 0, NULL, '2022-07-20 17:40:46', NULL, '2022-07-22 14:24:47', '普通用户', 0);

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu`  (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`, `menu_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色和菜单关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------
INSERT INTO `sys_role_menu` VALUES (1, 0);
INSERT INTO `sys_role_menu` VALUES (1, 1);
INSERT INTO `sys_role_menu` VALUES (1, 2);
INSERT INTO `sys_role_menu` VALUES (1, 3);
INSERT INTO `sys_role_menu` VALUES (1, 4);
INSERT INTO `sys_role_menu` VALUES (1, 5);
INSERT INTO `sys_role_menu` VALUES (1, 6);
INSERT INTO `sys_role_menu` VALUES (1, 7);
INSERT INTO `sys_role_menu` VALUES (1, 8);
INSERT INTO `sys_role_menu` VALUES (1, 9);
INSERT INTO `sys_role_menu` VALUES (1, 10);
INSERT INTO `sys_role_menu` VALUES (1, 11);
INSERT INTO `sys_role_menu` VALUES (1, 12);
INSERT INTO `sys_role_menu` VALUES (1, 13);
INSERT INTO `sys_role_menu` VALUES (1, 14);
INSERT INTO `sys_role_menu` VALUES (1, 15);
INSERT INTO `sys_role_menu` VALUES (1, 16);
INSERT INTO `sys_role_menu` VALUES (1, 17);
INSERT INTO `sys_role_menu` VALUES (1, 18);
INSERT INTO `sys_role_menu` VALUES (1, 19);
INSERT INTO `sys_role_menu` VALUES (1, 20);
INSERT INTO `sys_role_menu` VALUES (1, 21);
INSERT INTO `sys_role_menu` VALUES (1, 22);
INSERT INTO `sys_role_menu` VALUES (1, 23);
INSERT INTO `sys_role_menu` VALUES (1, 24);
INSERT INTO `sys_role_menu` VALUES (1, 25);
INSERT INTO `sys_role_menu` VALUES (1, 26);
INSERT INTO `sys_role_menu` VALUES (2, 0);
INSERT INTO `sys_role_menu` VALUES (2, 1);
INSERT INTO `sys_role_menu` VALUES (2, 3);
INSERT INTO `sys_role_menu` VALUES (2, 4);
INSERT INTO `sys_role_menu` VALUES (2, 5);
INSERT INTO `sys_role_menu` VALUES (2, 6);
INSERT INTO `sys_role_menu` VALUES (2, 18);
INSERT INTO `sys_role_menu` VALUES (2, 19);
INSERT INTO `sys_role_menu` VALUES (2, 20);
INSERT INTO `sys_role_menu` VALUES (2, 21);
INSERT INTO `sys_role_menu` VALUES (2, 22);
INSERT INTO `sys_role_menu` VALUES (2, 23);
INSERT INTO `sys_role_menu` VALUES (2, 24);
INSERT INTO `sys_role_menu` VALUES (2, 26);

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role`  (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`, `role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和角色关联表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES (1, 1);

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `union_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'unionId',
  `secret` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户密钥',
  `platform` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '第三方平台',
  `nick_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `avatar_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像链接',
  `authorization` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '授权信息',
  `points` int NULL DEFAULT 0 COMMENT '积分',
  `state` int NULL DEFAULT 0 COMMENT '状态',
  `password` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `salt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '盐',
  `login_ip` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录ip',
  `last_login_time` datetime NULL DEFAULT NULL COMMENT '最后登陆时间',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_by` bigint NULL DEFAULT NULL COMMENT '更新人',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `index_unid_platform`(`union_id`, `platform`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO user_info (`id`, `union_id`, `secret`, `platform`, `nick_name`, `email`, `avatar_url`, `authorization`, `points`, `state`, `password`, `salt`, `login_ip`, `last_login_time`, `create_time`, `update_time`, `update_by`, `del_flag`) VALUES (17, '5024355', '123', 'GITEE', '隔壁老于', 'gebilaoyu@foxmail.com', 'https://gitee.com/assets/no_portrait.png', '{\"appCreationNum\":99,\"appNum\":99,\"description\":\"自己用\",\"expires\":\"2023-05-31 10:10:04\",\"genNum\":99,\"scriptCreationNum\":99,\"serverNum\":99,\"state\":3,\"id\":2}', 5237, 0,'123', '123', '192.168.31.127 |0|0|0|内网IP|内网IP', '2022-09-03 19:15:07', '2022-05-23 11:47:27', '2022-09-03 19:15:07', NULL, 0);

-- ----------------------------
-- Table structure for user_message
-- ----------------------------
DROP TABLE IF EXISTS `user_message`;
CREATE TABLE `user_message`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `type` int NOT NULL DEFAULT 1 COMMENT '消息类型',
  `status` int NOT NULL DEFAULT 0 COMMENT '状态：0未读，1已读',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息详情',
  `create_time` datetime NOT NULL DEFAULT current_timestamp COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户消息' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_message
-- ----------------------------

-- ----------------------------
-- Table structure for user_points_log
-- ----------------------------
DROP TABLE IF EXISTS `user_points_log`;
CREATE TABLE `user_points_log`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `user_id` bigint NOT NULL COMMENT '用户id',
  `points` int NOT NULL COMMENT '分数',
  `current_points` int NOT NULL COMMENT '当前积分',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `del_flag` int NULL DEFAULT 0 COMMENT '删除标记',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户积分记录' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_points_log
-- ----------------------------

SET FOREIGN_KEY_CHECKS = 1;
