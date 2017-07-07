/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3307
Source Database       : fanyongpeng

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-07-07 18:06:03
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `comment`
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `topicid` bigint(20) unsigned DEFAULT NULL COMMENT '帖子id',
  `uid` bigint(20) unsigned DEFAULT NULL COMMENT '评论用户id',
  `floor` int(10) unsigned DEFAULT NULL COMMENT '楼层数',
  `replyfloor` int(10) unsigned DEFAULT NULL COMMENT '回复楼层',
  `replyuid` bigint(20) unsigned DEFAULT NULL COMMENT '被回复用户id',
  `uname` varchar(35) DEFAULT NULL COMMENT '评论用户昵称',
  `head` varchar(256) DEFAULT NULL COMMENT '评论用户头像',
  `sex` tinyint(3) unsigned DEFAULT NULL COMMENT '评论用户性别',
  `content` varchar(2048) DEFAULT NULL COMMENT '评论内容',
  `images` varchar(512) DEFAULT NULL COMMENT '图片',
  `lat` decimal(20,17) DEFAULT NULL COMMENT 'lat',
  `lon` decimal(20,17) DEFAULT NULL COMMENT 'lon',
  `create` datetime DEFAULT NULL COMMENT '创建时间',
  `update` datetime DEFAULT NULL COMMENT '更新时间',
  `yn` tinyint(3) unsigned DEFAULT NULL COMMENT '0 正常 1 删除',
  PRIMARY KEY (`id`),
  KEY `index_topic_uid` (`topicid`,`uid`) USING BTREE,
  KEY `index_topic_replyuid` (`topicid`,`replyuid`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('1', '1', '24', null, null, null, '测试用户33', 'http://10.12.194.127/upload/20170612/4611497253635724.png', '1', '我是内容', null, null, null, '2017-06-22 16:25:33', '2017-06-22 16:25:33', '0');
INSERT INTO `comment` VALUES ('2', '1', '24', null, null, null, '测试用户33', 'http://10.12.194.127/upload/20170612/4611497253635724.png', '1', '我是评论2', null, null, null, '2017-06-22 16:29:59', '2017-06-22 16:29:59', '0');
INSERT INTO `comment` VALUES ('3', '1', '24', null, null, null, '测试用户33', 'http://10.12.194.127/upload/20170612/4611497253635724.png', '1', '我是评论3', null, null, null, '2017-06-22 16:30:02', '2017-06-22 16:30:02', '0');
INSERT INTO `comment` VALUES ('4', '1', '24', null, null, null, '测试用户33', 'http://10.12.194.127/upload/20170612/4611497253635724.png', '1', '我是评论4', null, null, null, '2017-06-22 16:30:04', '2017-06-22 16:30:04', '0');
INSERT INTO `comment` VALUES ('5', '1', '24', null, null, null, '测试用户33', 'http://10.12.194.127/upload/20170612/4611497253635724.png', '1', '我是评论5', null, null, null, '2017-06-22 16:30:08', '2017-06-22 16:30:08', '0');
INSERT INTO `comment` VALUES ('6', '1', '24', null, null, null, '测试用户33', 'http://10.12.194.127/upload/20170612/4611497253635724.png', '1', '我是评论6', null, null, null, '2017-06-22 16:30:11', '2017-06-22 16:30:11', '0');
INSERT INTO `comment` VALUES ('7', '1', '24', '1', null, null, '测试用户33', 'http://10.12.194.127/upload/20170612/4611497253635724.png', '1', '我是评论6', null, null, null, '2017-06-27 16:36:00', '2017-06-27 16:36:00', '0');
INSERT INTO `comment` VALUES ('8', '14', '24', '1', null, null, '测试用户33', 'http://10.12.194.127/upload/20170612/4611497253635724.png', '1', '我是评论6111', null, null, null, '2017-06-27 16:37:13', '2017-06-27 16:37:13', '0');
INSERT INTO `comment` VALUES ('9', '14', '24', '2', null, null, '测试用户33', 'http://10.12.194.127/upload/20170612/4611497253635724.png', '1', '我是评论6111', null, null, null, '2017-06-27 16:37:13', '2017-06-27 16:37:13', '0');
INSERT INTO `comment` VALUES ('10', '14', '24', '3', null, null, '测试用户33', 'http://10.12.194.127/upload/20170612/4611497253635724.png', '1', '我是评论6111', null, null, null, '2017-06-27 16:37:14', '2017-06-27 16:37:14', '0');
INSERT INTO `comment` VALUES ('11', '14', '24', '4', null, null, '测试用户33', 'http://10.12.194.127/upload/20170612/4611497253635724.png', '1', '我是评论6111', null, null, null, '2017-06-27 16:48:30', '2017-06-27 16:48:30', '0');
INSERT INTO `comment` VALUES ('12', '14', '24', '5', null, null, '测试用户33', 'http://10.12.194.127/upload/20170612/4611497253635724.png', '1', '我是评论6111', null, null, null, '2017-06-27 16:48:32', '2017-06-27 16:48:32', '0');
INSERT INTO `comment` VALUES ('13', '2', '4', '1', null, null, '小小加加加', 'https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1494569873&di=080cae96c67fbff772640d9aabd535de&src=http://img4q.duitang.com/uploads/item/201505/23/20150523212026_fACJU.thumb.224_0.jpeg', '1', '静电接地夹，带你飞南方基金，带回家放假', null, null, null, '2017-06-28 15:22:01', '2017-06-28 15:22:01', '0');
INSERT INTO `comment` VALUES ('14', '2', '4', '2', null, null, '小小加加加', 'https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1494569873&di=080cae96c67fbff772640d9aabd535de&src=http://img4q.duitang.com/uploads/item/201505/23/20150523212026_fACJU.thumb.224_0.jpeg', '1', '觉得今年烦恼', null, null, null, '2017-06-28 15:23:15', '2017-06-28 15:23:15', '0');
INSERT INTO `comment` VALUES ('15', '14', '24', '6', null, null, '测试用户33', 'http://10.12.194.127/upload/20170612/4611497253635724.png', '1', '我是评论6111', null, null, null, '2017-06-28 16:03:54', '2017-06-28 16:03:54', '0');
INSERT INTO `comment` VALUES ('16', '2', '6', '3', null, null, '幸福的小企鹅', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494580115432&di=9bcebb4fd9272406fd0c46d337af4937&imgtype=0&src=http%3A%2F%2Fwww.cnxmld.com%2Ftupians%2Fbd16447582.jpg', '1', '韭菜鸡蛋姐姐', null, null, null, '2017-06-28 16:05:19', '2017-06-28 16:05:19', '0');
INSERT INTO `comment` VALUES ('17', '2', '6', '4', null, null, '幸福的小企鹅', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494580115432&di=9bcebb4fd9272406fd0c46d337af4937&imgtype=0&src=http%3A%2F%2Fwww.cnxmld.com%2Ftupians%2Fbd16447582.jpg', '1', '打开看到没', null, null, null, '2017-06-28 16:06:42', '2017-06-28 16:06:42', '0');
INSERT INTO `comment` VALUES ('18', '2', '6', '5', null, null, '幸福的小企鹅', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494580115432&di=9bcebb4fd9272406fd0c46d337af4937&imgtype=0&src=http%3A%2F%2Fwww.cnxmld.com%2Ftupians%2Fbd16447582.jpg', '1', '第几节', null, null, null, '2017-06-28 16:07:42', '2017-06-28 16:07:42', '0');
INSERT INTO `comment` VALUES ('19', '2', '6', '6', null, null, '幸福的小企鹅', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494580115432&di=9bcebb4fd9272406fd0c46d337af4937&imgtype=0&src=http%3A%2F%2Fwww.cnxmld.com%2Ftupians%2Fbd16447582.jpg', '1', '就到家', null, null, null, '2017-06-28 16:07:58', '2017-06-28 16:07:58', '0');
INSERT INTO `comment` VALUES ('20', '3', '6', '1', null, null, '幸福的小企鹅', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494580115432&di=9bcebb4fd9272406fd0c46d337af4937&imgtype=0&src=http%3A%2F%2Fwww.cnxmld.com%2Ftupians%2Fbd16447582.jpg', '1', '就到家', null, null, null, '2017-06-28 16:08:35', '2017-06-28 16:08:35', '0');
INSERT INTO `comment` VALUES ('21', '2', '7', '7', null, null, '呵呵哒', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '0', '打开减肥', null, null, null, '2017-06-28 16:23:57', '2017-06-28 16:23:57', '0');
INSERT INTO `comment` VALUES ('22', '2', '7', '8', null, null, '呵呵哒', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '0', 'j大家好', null, null, null, '2017-06-28 16:24:53', '2017-06-28 16:24:53', '0');
INSERT INTO `comment` VALUES ('23', '14', '7', '7', null, null, '呵呵哒', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '0', '美女你好', null, null, null, '2017-06-28 16:26:00', '2017-06-28 16:26:00', '0');
INSERT INTO `comment` VALUES ('24', '4', '7', '1', null, null, '呵呵哒', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '0', '你猜觉得', null, null, null, '2017-06-28 16:33:18', '2017-06-28 16:33:18', '0');
INSERT INTO `comment` VALUES ('25', '15', '7', '1', null, null, '呵呵哒', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '0', '就放假就到家', null, null, null, '2017-06-28 17:03:47', '2017-06-28 17:03:47', '0');
INSERT INTO `comment` VALUES ('26', '15', '7', '2', null, null, '呵呵哒', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '0', '都叫你<br/><br/>方法', null, null, null, '2017-06-28 17:04:01', '2017-06-28 17:04:01', '0');
INSERT INTO `comment` VALUES ('27', '16', '9', '1', null, null, '呵呵哒拉加寺', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '0', '结算测试<br/><br/>测试<br/><br/>测试', null, null, null, '2017-06-28 17:28:34', '2017-06-28 17:28:34', '0');
INSERT INTO `comment` VALUES ('28', '3', '9', '2', null, null, '呵呵哒拉加寺', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '0', '测试\n\n测试\n观光\n\n\n观光', null, null, null, '2017-06-28 17:29:27', '2017-06-28 17:29:27', '0');
INSERT INTO `comment` VALUES ('29', '19', '6', '1', null, null, '幸福的小企鹅', 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494580115432&di=9bcebb4fd9272406fd0c46d337af4937&imgtype=0&src=http%3A%2F%2Fwww.cnxmld.com%2Ftupians%2Fbd16447582.jpg', '1', '&#27979;&#35797;<br/><br/>&#22238;&#22797;<br/><br/>&#21644;&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&#31354;&#26684;', null, null, null, '2017-06-28 17:50:31', '2017-06-28 17:50:31', '0');
INSERT INTO `comment` VALUES ('30', '21', '3', '1', null, null, '呵呵哒昵称', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '1', '&#20016;&#23500;&#30340;', null, null, null, '2017-07-06 16:41:37', '2017-07-06 16:41:37', '0');
INSERT INTO `comment` VALUES ('31', '21', '3', '2', null, null, '呵呵哒昵称', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '1', '&#23601;&#25918;&#20551;&#20943;&#32933;', null, '30.54899300000000000', '104.06724800000000000', '2017-07-06 16:53:33', '2017-07-06 16:53:33', '0');
INSERT INTO `comment` VALUES ('32', '22', '4', '1', null, null, '小小加加加', 'https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1494569873&di=080cae96c67fbff772640d9aabd535de&src=http://img4q.duitang.com/uploads/item/201505/23/20150523212026_fACJU.thumb.224_0.jpeg', '1', '&#27809;&#20154;&#30475;&#21527;&#65311;', null, '30.54901400000000000', '104.06723500000000000', '2017-07-06 17:07:37', '2017-07-06 17:07:37', '0');
INSERT INTO `comment` VALUES ('33', '22', '4', '2', null, null, '小小加加加', 'https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1494569873&di=080cae96c67fbff772640d9aabd535de&src=http://img4q.duitang.com/uploads/item/201505/23/20150523212026_fACJU.thumb.224_0.jpeg', '1', '&#20943;&#32933;&#20943;&#32933;&#21799;&#21799;&#22797;&#21799;&#21799;', null, '30.54899800000000000', '104.06724500000000000', '2017-07-06 17:24:19', '2017-07-06 17:24:19', '0');
INSERT INTO `comment` VALUES ('34', '21', '4', '3', null, null, '小小加加加', 'https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1494569873&di=080cae96c67fbff772640d9aabd535de&src=http://img4q.duitang.com/uploads/item/201505/23/20150523212026_fACJU.thumb.224_0.jpeg', '1', '&#20320;&#21457;&#25918;&#20551;', null, '30.54899800000000000', '104.06724500000000000', '2017-07-06 17:28:12', '2017-07-06 17:28:12', '0');
