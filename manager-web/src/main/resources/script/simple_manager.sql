/*
 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50722
 Source Host           : localhost:3306
 Source Schema         : simple_manager

 Target Server Type    : MySQL
 Target Server Version : 50722
 File Encoding         : 65001

 Date: 02/01/2019 18:00:20
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for pc_home_ad
-- ----------------------------
DROP TABLE IF EXISTS `pc_home_ad`;
CREATE TABLE `pc_home_ad`  (
  `id` bigint(18) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `image_url` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '图片地址',
  `link_url` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '链接地址',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `status` int(1) UNSIGNED NOT NULL DEFAULT 1 COMMENT '状态,0-正常,1-已下线',
  `is_delete` tinyint(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '是否删除,0-正常,1-删除',
  `gmt_start` datetime(0) NULL DEFAULT NULL COMMENT '开始时间, NULL表示永久',
  `gmt_end` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `operator` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '最后操作人(ERP账号)',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '主动创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '被动更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 66 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '首页轮播广告大图' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of pc_home_ad
-- ----------------------------
INSERT INTO `pc_home_ad` VALUES (1, '201901.jpg', 'http://www.example.com/', 'remark', 0, 0, '2019-01-17 11:46:15', NULL, 'root', '2019-01-15 17:54:22', '2019-03-27 18:12:38');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(18) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '用户id',
  `erp` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ERP账号',
  `password` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT 'ERP密码',
  `fullname` varchar(18) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户姓名',
  `gmt_create` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '主动创建时间',
  `gmt_modified` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '被动更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_erp`(`erp`) USING BTREE COMMENT 'erp普通索引'
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- test erp:root, password:123456
-- ----------------------------
INSERT INTO `user` VALUES (1, 'root', 'e10adc3949ba59abbe56e057f20f883e', 'root', '2018-12-27 19:01:22', '2018-12-28 10:46:06');

SET FOREIGN_KEY_CHECKS = 1;
