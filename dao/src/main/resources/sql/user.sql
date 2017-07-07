/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50717
Source Host           : localhost:3307
Source Database       : fanyongpeng

Target Server Type    : MYSQL
Target Server Version : 50717
File Encoding         : 65001

Date: 2017-07-07 18:05:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `name` varchar(35) DEFAULT NULL COMMENT '名称',
  `password` varchar(50) DEFAULT NULL COMMENT '密码',
  `sex` tinyint(1) DEFAULT '0' COMMENT '0 男，1 女',
  `birth` date DEFAULT NULL COMMENT '出生日期',
  `phone` varchar(15) NOT NULL COMMENT '电话',
  `idcard` varchar(18) DEFAULT NULL COMMENT '身份证号',
  `signature` varchar(100) DEFAULT NULL COMMENT '签名',
  `provinceCode` varchar(20) DEFAULT NULL COMMENT '省份代码',
  `province` varchar(20) DEFAULT NULL COMMENT '省份',
  `cityCode` varchar(20) DEFAULT NULL COMMENT '城市代码',
  `city` varchar(20) DEFAULT NULL,
  `lon` decimal(20,17) DEFAULT NULL COMMENT '经度，longitude',
  `lat` decimal(20,17) DEFAULT NULL COMMENT '维度，latitude',
  `geocode` char(12) DEFAULT NULL COMMENT '经纬度的geohash值',
  `industry` varchar(35) DEFAULT NULL COMMENT '所处行业',
  `education` tinyint(4) DEFAULT '0' COMMENT '学历 0 小学以下，1 小学， 2 初中， 3 高中，4 中专，5 大专，6 本科，7 硕士，8 博士',
  `income` tinyint(4) DEFAULT NULL COMMENT '收入范围 ',
  `height` smallint(6) DEFAULT '0' COMMENT '身高cm',
  `weight` mediumint(9) DEFAULT NULL COMMENT '重量 单位kg',
  `marriage` tinyint(4) DEFAULT NULL COMMENT '婚姻状态',
  `head` varchar(256) DEFAULT NULL COMMENT '头像图片',
  `detail` varchar(8192) DEFAULT NULL COMMENT '个人简介详情',
  `status` tinyint(4) DEFAULT '0' COMMENT '当前状态',
  `count` int(11) DEFAULT '0' COMMENT '数量，当为男性时候表示发送(send)的数量，当为女性的时候表示接收(receive)的数量',
  `deviceId` varchar(60) DEFAULT NULL COMMENT '设备id',
  `images` varchar(3000) DEFAULT NULL COMMENT '用户图片，json格式字符串',
  `requirement` varchar(2000) DEFAULT NULL COMMENT '交友要求',
  `level` tinyint(4) DEFAULT NULL COMMENT '用户等级',
  `create` datetime DEFAULT NULL COMMENT '创建日期',
  `update` datetime DEFAULT NULL COMMENT '更新日期',
  `yn` tinyint(1) DEFAULT '0' COMMENT '0 有效 1 无效',
  PRIMARY KEY (`id`),
  UNIQUE KEY `index_phone` (`phone`) USING BTREE,
  KEY `index_idcard` (`idcard`) USING BTREE,
  KEY `index_geocode` (`geocode`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'Abel nam', '424488d5b10ba15a00b8785108ce8167cb85a5df4c286403', '0', null, '18244273480', null, '世界那么大，我要回家看看', null, null, null, null, null, null, null, null, '0', null, '0', null, null, 'http://bbs.netease.im/images/face/7.jpg?1487598442', null, '4', '0', null, null, null, null, null, '2017-05-16 14:23:52', '0');
INSERT INTO `user` VALUES ('3', '呵呵哒昵称', '424488d5b10ba15a00b8785108ce8167cb85a5df4c286403', '1', '1988-07-01', '18244273481', null, '世界到底有多大，我到底走多远', '130000', '河北省', '130100', '石家庄市', null, null, null, null, '5', '3', '168', '60', '1', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', '度假酒店开始看拒绝度假酒店,我重来都不相信命运，命运是你给的？不是你给的？命运在自己手中，生活在自己手中', '2', '0', null, '[{\"url\":\"upload/20170619/29081497863668762.jpg\"},{\"url\":\"upload/20170619/19321497865536087.jpg\"},{\"url\":\"upload/20170619/86761497865569113.jpg\"},{\"url\":\"upload/20170619/96611497866514152.jpg\"}]', '匠心独具，好的好的呵呵', null, '2016-10-12 17:35:41', '2017-06-19 18:01:55', '0');
INSERT INTO `user` VALUES ('4', '小小加加加', '424488d5b10ba15a00b8785108ce8167cb85a5df4c286403', '1', '1991-08-01', '18244273482', null, '世界那么大，我要回家看看', null, null, null, '110101', null, null, null, null, '3', '2', '0', '50', '1', 'https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1494569873&di=080cae96c67fbff772640d9aabd535de&src=http://img4q.duitang.com/uploads/item/201505/23/20150523212026_fACJU.thumb.224_0.jpeg', '大家好，我来自日本，一个你想象不到的大陆，但是我为什么来到这里，是因为看不到吗？', '8', '0', null, null, '简单点就好了，我没有太多要求', null, '2016-11-02 17:45:56', '2016-11-02 17:45:56', '0');
INSERT INTO `user` VALUES ('6', '幸福的小企鹅', '424488d5b10ba15a00b8785108ce8167cb85a5df4c286403', '1', null, '18244273483', null, '时间就像乳沟，一躺下就没了', null, null, null, null, null, null, null, null, '0', null, '0', null, null, 'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1494580115432&di=9bcebb4fd9272406fd0c46d337af4937&imgtype=0&src=http%3A%2F%2Fwww.cnxmld.com%2Ftupians%2Fbd16447582.jpg', null, '4', '0', null, null, null, null, '2016-11-02 17:48:48', '2016-11-02 17:48:48', '0');
INSERT INTO `user` VALUES ('7', '呵呵哒', '424488d5b10ba15a00b8785108ce8167cb85a5df4c286403', '0', '1990-01-01', '18244273484', null, '世界那么大，我要回家看看', null, null, null, '110101', null, null, null, null, '0', '3', '170', '49', '1', 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', null, '4', '0', null, null, '人，女', null, '2016-11-03 18:21:27', '2017-04-20 18:50:43', '0');
INSERT INTO `user` VALUES ('8', '时间', '424488d5b10ba15a00b8785108ce8167cb85a5df4c286403', '0', '1990-09-01', '18244273485', null, '世界那么大，我要回家看看', null, null, null, '110101', null, null, null, null, '7', '3', '179', '80', '1', 'https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1494569873&di=080cae96c67fbff772640d9aabd535de&src=http://img4q.duitang.com/uploads/item/201505/23/20150523212026_fACJU.thumb.224_0.jpeg', null, '4', '0', null, '[{\"url\":\"upload/20170629/10681498723560091.jpg\"}]', null, null, '2016-11-03 18:22:11', '2017-06-29 16:06:05', '0');
INSERT INTO `user` VALUES ('9', '呵呵哒拉加寺', '424488d5b10ba15a00b8785108ce8167cb85a5df4c286403', '0', '2001-10-10', '18244273486', '520577895612586420', '世界那么大，我要回家看看', null, null, null, null, null, null, null, null, '0', null, '0', null, null, 'http://img2.imgtn.bdimg.com/it/u=2898232289,3380909461&fm=21&gp=0.jpg', null, '2', '0', null, null, null, null, '2016-11-04 10:10:07', '2017-05-16 14:23:52', '0');
INSERT INTO `user` VALUES ('15', '客气的小白楼', 'd1fa5a66bc5ba7978295d77f130244c5169a52971636532a', '1', '1990-01-01', '18244576438', '520577895612586426', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', '0', null, null, null, null, '2017-06-06 18:47:49', '2017-06-06 18:48:42', '0');
INSERT INTO `user` VALUES ('16', '静电接地夹', '75f99633ad0ba6e14b576b63e2702e205c5f049e8177494c', '0', '1991-01-01', '18244567358', '578906694572435865', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', '0', null, null, null, null, '2017-06-06 18:50:12', '2017-06-06 18:50:34', '0');
INSERT INTO `user` VALUES ('17', '奖学金你才能', 'e8085f857d7c32bd1d637192a97e2cd1d24827010b100175', '1', '1990-01-01', '18523456752', '518966643578564856', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', '0', null, null, null, null, '2017-06-06 18:57:43', '2017-06-06 18:57:52', '0');
INSERT INTO `user` VALUES ('18', '回电话八点半', 'f7f55c50d652378b53a9882754db81431372b50f6e78c57d', '1', '1996-01-01', '18522435684', '518544456782354680', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', '0', null, null, null, null, '2017-06-07 13:49:49', '2017-06-07 13:49:59', '0');
INSERT INTO `user` VALUES ('19', '看美女女', '51333655338f538179e55358098c6cb4111e05475366a82e', '1', '1984-01-01', '18233645862', '518644452356946751', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', '0', null, null, null, null, '2017-06-07 13:51:59', '2017-06-07 13:52:14', '0');
INSERT INTO `user` VALUES ('20', 'i好v我', '386517464b62b8ff2871664ca9a968431b6735547094bf00', '0', '1986-01-01', '12364585253', '454535546358516454', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', '0', null, null, null, null, '2017-06-07 13:53:40', '2017-06-07 14:01:33', '0');
INSERT INTO `user` VALUES ('21', '麻将吵架', '869a8cd0ef7e94279c697b8d85897b48748e212071939d0e', '0', '1982-01-01', '12543365842', '546188554236488546', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', '0', null, null, null, null, '2017-06-07 14:02:41', '2017-06-07 14:02:51', '0');
INSERT INTO `user` VALUES ('22', '今年带你去', 'a35658b15f9ed9fb83933d86d5bb6a841204c76576638f15', '1', '1990-01-01', '15852213546', '510864623861956656', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', '0', null, null, null, null, '2017-06-07 14:13:54', '2017-06-07 14:14:03', '0');
INSERT INTO `user` VALUES ('23', '荒岛惊魂', '975f0198cb5949352b21bc0945321bc6ef40775c9d622229', '0', '1983-01-05', '13946555553', '645275534685243164', null, '510000', '四川省', '510100', '成都市', '104.06732700000000000', '30.54892400000000000', null, null, null, null, null, null, null, 'http://10.12.194.127/upload/20170615/35891497517011940.png', null, '0', '0', '868024024397748,868024024397755', null, null, null, '2017-06-07 14:24:27', '2017-06-15 16:57:11', '0');
INSERT INTO `user` VALUES ('24', '测试用户33', '17e393859e7241bc23d43269d2408615e157e5235204d301', '1', '1992-02-01', '18244273456', '512544465254876452', '计算机就到家剪发娜娜', '510000', '四川省', '510100', '成都市', '104.06738900000000000', '30.54886300000000000', null, null, '6', '1', '164', '58', '1', 'http://10.12.194.127/upload/20170612/4611497253635724.png', '哼哼唧唧，他刚刚跟一个人都在哪了？', '0', '0', null, null, null, null, '2017-06-08 17:40:34', '2017-06-08 17:41:12', '0');
INSERT INTO `user` VALUES ('25', null, '424488d5b10ba15a00b8785108ce8167cb85a5df4c286403', null, null, '18244567523', '518099965254258053', null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', '0', null, null, null, null, '2017-06-12 15:55:55', '2017-06-12 15:55:55', '0');
INSERT INTO `user` VALUES ('26', '接电话电话', '775e0be8cc9e48839ad4736fc3e07ab6e08586632461ba45', '0', '1990-01-01', '18233654256', '536688854236556552', null, '510000', '四川省', '510100', '成都市', '104.06735900000000000', '30.54888600000000000', null, null, null, null, null, null, null, null, null, '0', '0', null, null, null, null, '2017-06-12 16:07:27', '2017-06-12 16:08:19', '0');
INSERT INTO `user` VALUES ('27', '静电接地夹', '04431a95d551194e1762983ac73a4fb14f6354ef16c79c14', '1', '1990-01-01', '15433624586', '546788845235698563', null, '510000', '四川省', '510100', '成都市', '104.06720700000000000', '30.54874500000000000', null, null, null, null, null, null, null, 'http://10.12.194.127/upload/20170612/53801497256171799.png', null, '0', '0', null, null, null, null, '2017-06-12 16:29:18', '2017-06-12 16:29:45', '0');
INSERT INTO `user` VALUES ('28', '哼哼唧唧', '05b95899472fa7829848524a21b02fb0c101435435f6d305', '0', '1990-01-01', '12369854275', '586855254156658054', '干活回家', '510000', '四川省', '510100', '成都市', '104.06732500000000000', '30.54892000000000000', null, null, '1', '1', null, '50', '1', 'http://10.12.194.127/upload/20170614/89601497430876158.png', '红红火火恍恍惚惚', '0', '0', '868024024397748,868024024397755', null, null, null, '2017-06-14 17:00:33', '2017-06-14 17:10:14', '0');
