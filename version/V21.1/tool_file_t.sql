/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50717
Source Host           : localhost:3306
Source Database       : doctorreimbursement

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2021-07-23 21:12:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tool_file_t`
-- ----------------------------
DROP TABLE IF EXISTS `tool_file_t`;
CREATE TABLE `tool_file_t` (
  `oid` int(19) NOT NULL AUTO_INCREMENT,
  `file` longblob COMMENT '文件流',
  `file_name` varchar(1000) DEFAULT NULL COMMENT '文件名',
  `pk_oid` varchar(32) DEFAULT NULL COMMENT '外键--业务id',
  `create_date` date DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tool_file_t
-- ----------------------------
