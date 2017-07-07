/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3307
Source Database       : fanyongpeng

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-07-07 18:06:19
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `hunter_receiver`
-- ----------------------------
DROP TABLE IF EXISTS `hunter_receiver`;
CREATE TABLE `hunter_receiver` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hunter` int(11) NOT NULL COMMENT '追求者用户id',
  `receiver` int(11) NOT NULL COMMENT '接受者用户id',
  `message` varchar(500) DEFAULT NULL COMMENT '信息',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态，1 承认(admit), 2 接受(accept), 4 发送(send)，8 拒绝(reject)',
  `create` int(11) NOT NULL,
  `update` int(11) NOT NULL,
  `yn` tinyint(4) DEFAULT '0' COMMENT '0 正常，1 无效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_hunter_receiver` (`hunter`,`receiver`) USING BTREE,
  KEY `index_hunter_update_status` (`hunter`,`update`,`status`) USING BTREE,
  KEY `index_receiver_update_status` (`receiver`,`update`,`status`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of hunter_receiver
-- ----------------------------
INSERT INTO `hunter_receiver` VALUES ('3', '1', '3', null, '8', '1478080170', '1497522999', '0');
INSERT INTO `hunter_receiver` VALUES ('4', '7', '3', null, '4', '1478080508', '1497525156', '0');
INSERT INTO `hunter_receiver` VALUES ('5', '9', '3', null, '2', '1492765960', '1494915831', '0');
INSERT INTO `hunter_receiver` VALUES ('6', '8', '3', null, '8', '1495078126', '1497525559', '0');
