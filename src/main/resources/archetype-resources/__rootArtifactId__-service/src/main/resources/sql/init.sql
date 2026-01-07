-- 创建数据库
CREATE DATABASE IF NOT EXISTS `your-database-name` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `your-database-name`;

-- 用户表
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
    `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `nickname` VARCHAR(30) NULL DEFAULT NULL COMMENT '昵称',
    `email` VARCHAR(50) NULL DEFAULT NULL COMMENT '邮箱',
    `phone` VARCHAR(20) NULL DEFAULT NULL COMMENT '手机号',
    `gender` TINYINT(1) NULL DEFAULT 2 COMMENT '性别（0-女，1-男，2-未知）',
    `avatar` VARCHAR(255) NULL DEFAULT NULL COMMENT '头像URL',
    `status` TINYINT(1) NULL DEFAULT 1 COMMENT '状态（0-禁用，1-启用）',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `create_by` BIGINT(20) NULL DEFAULT NULL COMMENT '创建人ID',
    `update_by` BIGINT(20) NULL DEFAULT NULL COMMENT '更新人ID',
    `deleted` TINYINT(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除标记（0-未删除，1-已删除）',
    `version` INT(11) NOT NULL DEFAULT 0 COMMENT '版本号（乐观锁）',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    KEY `idx_email` (`email`),
    KEY `idx_phone` (`phone`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 插入测试数据
INSERT INTO `sys_user` (`username`, `password`, `nickname`, `email`, `phone`, `gender`, `status`)
VALUES
    ('admin', 'e10adc3949ba59abbe56e057f20f883e', '管理员', 'admin@example.com', '13800138000', 1, 1),
    ('user', 'e10adc3949ba59abbe56e057f20f883e', '普通用户', 'user@example.com', '13800138001', 1, 1);
