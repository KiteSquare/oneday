/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3307
Source Database       : fanyongpeng

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-07-07 18:05:45
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `topic`
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `uid` bigint(20) DEFAULT NULL COMMENT '创建者用户id',
  `category` bigint(20) DEFAULT NULL COMMENT '分类id',
  `head` varchar(256) DEFAULT NULL COMMENT '创建者头像',
  `title` varchar(300) DEFAULT NULL COMMENT '标题',
  `content` text COMMENT '帖子内容',
  `images` varchar(500) DEFAULT NULL COMMENT '图片，json格式字符串',
  `uname` varchar(35) DEFAULT NULL COMMENT '创建者昵称',
  `weight` smallint(6) DEFAULT NULL COMMENT '权重',
  `sex` tinyint(4) DEFAULT NULL COMMENT '创建者性别 0男， 1 女',
  `lon` decimal(20,17) DEFAULT NULL COMMENT '经度，longitude',
  `lat` decimal(20,17) DEFAULT NULL COMMENT '维度，latitude',
  `geocode` char(12) DEFAULT NULL COMMENT '经纬度的gehash值',
  `city` varchar(20) DEFAULT NULL COMMENT '城市',
  `citycode` varchar(15) DEFAULT NULL COMMENT '城市代码',
  `count` int(10) unsigned DEFAULT '0' COMMENT '回复数量',
  `create` datetime DEFAULT NULL,
  `update` datetime DEFAULT NULL,
  `yn` tinyint(4) DEFAULT NULL COMMENT '0 有效 1 无效',
  PRIMARY KEY (`id`),
  KEY `index_uid` (`uid`) USING BTREE,
  KEY `index_weight_date` (`weight`,`update`) USING BTREE,
  KEY `index_date` (`update`) USING BTREE,
  KEY `index_geocode` (`geocode`) USING BTREE,
  KEY `index_city` (`citycode`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of topic
-- ----------------------------
INSERT INTO `topic` VALUES ('1', '24', '1', null, '我是帖子', '我是内容', null, null, '1', null, null, null, null, null, null, '1', '2017-06-21 15:43:27', '2017-06-27 16:36:00', '0');
INSERT INTO `topic` VALUES ('2', '24', '1', 'http://10.12.194.127/upload/20170612/4611497253635724.png', '我是帖子', '我是内容', null, '测试用户33', '1', '1', null, null, null, null, null, '8', '2017-06-21 16:27:18', '2017-06-28 16:24:53', '0');
INSERT INTO `topic` VALUES ('3', '24', '1', 'http://10.12.194.127/upload/20170612/4611497253635724.png', '我是帖子', '我是内容', null, '测试用户33', '3', '1', null, null, null, null, null, '2', '2017-06-21 16:28:38', '2017-06-28 17:29:27', '0');
INSERT INTO `topic` VALUES ('4', '24', '1', 'http://10.12.194.127/upload/20170612/4611497253635724.png', '我是帖子', '我是内容', null, '测试用户33', '0', '1', null, null, null, null, null, '1', '2017-06-21 17:49:30', '2017-06-28 16:33:18', '0');
INSERT INTO `topic` VALUES ('5', '24', '1', 'http://10.12.194.127/upload/20170612/4611497253635724.png', '我是帖子', '我是内容', null, '测试用户33', '0', '1', null, null, null, null, null, '0', '2017-06-21 17:49:33', '2017-06-21 17:49:33', '0');
INSERT INTO `topic` VALUES ('6', '24', '1', 'http://10.12.194.127/upload/20170612/4611497253635724.png', '我是帖子', '我是内容', null, '测试用户33', '0', '1', null, null, null, null, null, '0', '2017-06-22 18:01:50', '2017-06-22 18:01:50', '0');
INSERT INTO `topic` VALUES ('7', '24', '1', 'http://10.12.194.127/upload/20170612/4611497253635724.png', '我是帖子', '我是内容', null, '测试用户33', '0', '1', null, null, null, null, null, '0', '2017-06-22 18:01:51', '2017-06-22 18:01:51', '0');
INSERT INTO `topic` VALUES ('8', '3', '1', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '你到家大家你到家', '都觉得好好的', null, '呵呵哒昵称', '0', '1', null, null, null, null, null, '0', '2017-06-23 15:43:52', '2017-06-23 15:43:52', '0');
INSERT INTO `topic` VALUES ('9', '3', '1', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '大家好，我是护手霜', '就到家好的八点半\n\n都觉得就觉得几放假', null, '呵呵哒昵称', '0', '1', null, null, null, null, null, '0', '2017-06-23 15:46:44', '2017-06-23 15:46:44', '0');
INSERT INTO `topic` VALUES ('10', '3', '1', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '觉得就当减肥减肥', '觉得觉得\n\n\n\n\n\n\n\n\n\n惊喜交加都觉得', null, '呵呵哒昵称', '0', '1', null, null, null, null, null, '0', '2017-06-23 15:56:09', '2017-06-23 15:56:09', '0');
INSERT INTO `topic` VALUES ('11', '3', '1', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', 'b就地解决大家', '觉得接电话吃不吃你姐姐我', null, '呵呵哒昵称', '0', '1', null, null, null, null, null, '0', '2017-06-23 17:56:53', '2017-06-23 17:56:53', '0');
INSERT INTO `topic` VALUES ('12', '3', '1', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '继续继续觉得健康', 'n都觉得就觉得叫姐姐盆', '', '呵呵哒昵称', '0', '1', null, null, null, null, null, '0', '2017-06-26 17:10:19', '2017-06-26 17:10:19', '0');
INSERT INTO `topic` VALUES ('13', '3', '1', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '静电接地夹加', '度好的简简单单', '[{\"url\":\"http://10.12.194.127/upload/20170626/92301498469111511.jpg\"},{\"url\":\"http://10.12.194.127/upload/20170626/82281498469111511.jpg\"}]', '呵呵哒昵称', '0', '1', null, null, null, null, null, '0', '2017-06-26 17:25:13', '2017-06-26 17:25:13', '0');
INSERT INTO `topic` VALUES ('14', '3', '1', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '创建一个好看的帖子吧', '好的好的好的今晚', '[{\"url\":\"http://10.12.194.127/upload/20170626/49531498470202797.jpg\"}]', '呵呵哒昵称', '0', '1', null, null, null, null, null, '7', '2017-06-26 17:43:25', '2017-06-28 16:26:00', '0');
INSERT INTO `topic` VALUES ('15', '7', '1', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '来看看我发的帖子啊', '内容不重要，重要的是有内容<br/><br/><br/><br/><br/>你说对不对&lt;br /&gt;？<br/><br/><br/><br/>对', null, '呵呵哒', '0', '0', null, null, null, null, null, '2', '2017-06-28 16:38:51', '2017-06-28 17:04:01', '0');
INSERT INTO `topic` VALUES ('16', '9', '1', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', 'bdjdjdk男的女的你', '<br/>\njdjdjdj  jdjdjd ', null, '呵呵哒拉加寺', '0', '0', null, null, null, null, null, '1', '2017-06-28 17:26:13', '2017-06-28 17:28:34', '0');
INSERT INTO `topic` VALUES ('17', '9', '1', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '减肥减肥唧唧复唧<br/> jdjdjdj  jdjdjd ', '<br/>\njdjdjdj  jdjdjd \n\n就放假放假减肥', null, '呵呵哒拉加寺', '0', '0', null, null, null, null, null, '0', '2017-06-28 17:36:40', '2017-06-28 17:36:40', '0');
INSERT INTO `topic` VALUES ('18', '9', '1', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '&lt;br/&gt; jdjdjdj  jdjdjd &#25918;&#20551;&#25918;&#20551;', '<br/><br/>jdjdjdj  jdjdjd <br/><br/>附近的<br/><br/>个哦', null, '呵呵哒拉加寺', '0', '0', null, null, null, null, null, '0', '2017-06-28 17:39:15', '2017-06-28 17:39:15', '0');
INSERT INTO `topic` VALUES ('19', '9', '1', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '&lt;br/&gt; jdjdjdj  jdjdjd &#22823;&#23478;&#22823;&#23478;', '&#22238;&#30005;&#35805;&lt;br/&gt;<br/>jdjdjdj&nbsp&nbsp&nbsp&nbsp&nbsp  jdjdjd <br/><br/>      &#22823;&#23478;&#23601;&#24403;&#20943;&#32933;<br/>&#22810;&#28857;    &#21704;&#21704;', null, '呵呵哒拉加寺', '0', '0', '104.07439700000000000', '30.54835600000000000', 'wm6jb', null, null, '1', '2017-06-28 17:42:03', '2017-06-28 17:50:31', '0');
INSERT INTO `topic` VALUES ('20', '3', '1', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '慢慢吃那你才能才能吃', '&#22909;&#30340;&#23601;&#22320;&#35299;&#20915;&#37117;&#35273;&#24471;', '[{\"url\":\"http://10.12.194.127/upload/20170706/25831499329278253.jpg\"}]', '呵呵哒昵称', '0', '1', '104.06724300000000000', '30.54899400000000000', 'wm6jb', null, '', '0', '2017-07-06 16:21:20', '2017-07-06 16:21:20', '0');
INSERT INTO `topic` VALUES ('21', '3', '1', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '你猜减肥家门口', '&#24930;&#24930;&#21507;&#20111;&#21442;&#32771;&#21442;&#32771;<br/>v&#27809;v&#27809;v&#30475;', '[{\"url\":\"http://10.12.194.127/upload/20170706/41111499330288889.jpg\"}]', '呵呵哒昵称', '0', '1', '104.06724700000000000', '30.54898100000000000', 'wm6jb', '成都市', '510100', '3', '2017-07-06 16:38:11', '2017-07-06 17:28:12', '0');
INSERT INTO `topic` VALUES ('22', '4', '1', 'https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1494569873&di=080cae96c67fbff772640d9aabd535de&src=http://img4q.duitang.com/uploads/item/201505/23/20150523212026_fACJU.thumb.224_0.jpeg', '都叫你当借口的看法', '&#23478;&#24120;&#33756;&#20320;&#21457;&#33510;&#21654;&#21857;&#24247;&#22797;&#31185;<br/>&#23553;&#21475;&#36153;&#30475;&#30475;&#20998;&#24320;&#30340;&#30475;&#24320;&#28857;&#21345;&#21345;&#39277;&#21345;&#30475;&#27861;&#23601;&#21457;&#23601;&#21457;&#30475;&#27861;&#21345;&#21345;&#39277;&#21345;&#21453;&#29401;&#20987;&#39277;&#21345;&#23601;&#25918;&#20551;&#21457;&#37202;&#30127;&#29238;&#27597;&#25918;&#20551;<br/>&#20943;&#32933;&#21738;&#26041;&#38754;', null, '小小加加加', '0', '1', '104.06723500000000000', '30.54901400000000000', 'wm6jb', '成都市', '510100', '2', '2017-07-06 17:06:45', '2017-07-06 17:24:19', '0');
