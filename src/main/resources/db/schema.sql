-- ============================================================
-- 秒杀系统 数据库建表脚本
-- ============================================================

CREATE DATABASE IF NOT EXISTS `seckill` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

USE `seckill`;

-- -----------------------------------------------------------
-- 1. 用户表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
    `id`          BIGINT       NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `nickname`    VARCHAR(64)  NOT NULL DEFAULT '' COMMENT '昵称',
    `password`    VARCHAR(128) NOT NULL DEFAULT '' COMMENT '密码(MD5)',
    `salt`        VARCHAR(16)  NOT NULL DEFAULT '' COMMENT '盐',
    `head`        VARCHAR(256) NOT NULL DEFAULT '' COMMENT '头像URL',
    `register_date` DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
    `last_login_date` DATETIME NULL COMMENT '最后登录时间',
    `login_count` INT          NOT NULL DEFAULT 0 COMMENT '登录次数',
    `create_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_nickname` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- -----------------------------------------------------------
-- 2. 商品表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `t_goods`;
CREATE TABLE `t_goods` (
    `id`              BIGINT        NOT NULL AUTO_INCREMENT COMMENT '商品ID',
    `goods_name`      VARCHAR(256)  NOT NULL DEFAULT '' COMMENT '商品名称',
    `goods_title`     VARCHAR(512)  NOT NULL DEFAULT '' COMMENT '商品标题',
    `goods_img`       VARCHAR(512)  NOT NULL DEFAULT '' COMMENT '商品图片',
    `goods_detail`    TEXT          NULL COMMENT '商品详情',
    `goods_price`     DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '商品原价',
    `goods_stock`     INT           NOT NULL DEFAULT 0 COMMENT '商品库存',
    `create_time`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- -----------------------------------------------------------
-- 3. 秒杀商品表（记录秒杀活动的商品信息）
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `t_seckill_goods`;
CREATE TABLE `t_seckill_goods` (
    `id`              BIGINT        NOT NULL AUTO_INCREMENT COMMENT '秒杀商品ID',
    `goods_id`        BIGINT        NOT NULL COMMENT '关联商品ID',
    `seckill_price`   DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '秒杀价格',
    `stock_count`     INT           NOT NULL DEFAULT 0 COMMENT '秒杀库存',
    `start_date`      DATETIME      NOT NULL COMMENT '秒杀开始时间',
    `end_date`        DATETIME      NOT NULL COMMENT '秒杀结束时间',
    `create_time`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_goods_id` (`goods_id`),
    KEY `idx_start_date` (`start_date`),
    KEY `idx_end_date` (`end_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒杀商品表';

-- -----------------------------------------------------------
-- 4. 订单表
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `t_order`;
CREATE TABLE `t_order` (
    `id`              BIGINT        NOT NULL AUTO_INCREMENT COMMENT '订单ID',
    `user_id`         BIGINT        NOT NULL COMMENT '用户ID',
    `goods_id`        BIGINT        NOT NULL COMMENT '商品ID',
    `delivery_addr_id` BIGINT       NULL COMMENT '收货地址ID',
    `goods_name`      VARCHAR(256)  NOT NULL DEFAULT '' COMMENT '商品名称(冗余)',
    `goods_count`     INT           NOT NULL DEFAULT 0 COMMENT '购买数量',
    `goods_price`     DECIMAL(10,2) NOT NULL DEFAULT 0.00 COMMENT '商品单价',
    `order_channel`   TINYINT       NOT NULL DEFAULT 0 COMMENT '渠道: 0-PC 1-移动端',
    `status`          TINYINT       NOT NULL DEFAULT 0 COMMENT '订单状态: 0-新建未支付 1-已支付 2-已发货 3-已收货 4-已退款 5-已取消',
    `create_time`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `pay_time`        DATETIME      NULL COMMENT '支付时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_goods_id` (`goods_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- -----------------------------------------------------------
-- 5. 秒杀订单表（防止同一用户重复秒杀）
-- -----------------------------------------------------------
DROP TABLE IF EXISTS `t_seckill_order`;
CREATE TABLE `t_seckill_order` (
    `id`              BIGINT        NOT NULL AUTO_INCREMENT COMMENT '秒杀订单ID',
    `user_id`         BIGINT        NOT NULL COMMENT '用户ID',
    `order_id`        BIGINT        NOT NULL COMMENT '关联订单ID',
    `goods_id`        BIGINT        NOT NULL COMMENT '商品ID',
    `create_time`     DATETIME      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_goods` (`user_id`, `goods_id`) COMMENT '防止同一用户重复秒杀同一商品',
    KEY `idx_order_id` (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='秒杀订单表';
