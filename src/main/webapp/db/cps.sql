/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50521
Source Host           : localhost:3306
Source Database       : cps

Target Server Type    : MYSQL
Target Server Version : 50521
File Encoding         : 65001

Date: 2016-07-26 21:36:51
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `epg_element_img`
-- ----------------------------
DROP TABLE IF EXISTS `epg_element_img`;
CREATE TABLE `epg_element_img` (
  `modify_oper` int(11) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `epg_id` int(11) NOT NULL,
  `epg_content_type` varchar(20) NOT NULL,
  `img_id` int(11) NOT NULL,
  `content_type` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`epg_id`,`epg_content_type`,`img_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of epg_element_img
-- ----------------------------

-- ----------------------------
-- Table structure for `epg_element_video`
-- ----------------------------
DROP TABLE IF EXISTS `epg_element_video`;
CREATE TABLE `epg_element_video` (
  `modify_oper` int(11) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT '0000-00-00 00:00:00',
  `epg_id` int(11) NOT NULL,
  `video_id` int(11) NOT NULL,
  `epg_content_type` varchar(20) NOT NULL,
  PRIMARY KEY (`epg_id`,`video_id`,`epg_content_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of epg_element_video
-- ----------------------------

-- ----------------------------
-- Table structure for `epg_group`
-- ----------------------------
DROP TABLE IF EXISTS `epg_group`;
CREATE TABLE `epg_group` (
  `egroup_id` int(20) NOT NULL AUTO_INCREMENT,
  `eparent_id` int(20) DEFAULT NULL,
  `enode_order` int(5) DEFAULT NULL,
  `egroup_name` varchar(50) NOT NULL,
  `egroup_description` varchar(200) DEFAULT NULL,
  `modify_oper` int(11) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`egroup_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of epg_group
-- ----------------------------
INSERT INTO `epg_group` VALUES ('1', '0', null, '信息分组', '信息分组', null, '2013-10-11 17:08:41');

-- ----------------------------
-- Table structure for `epg_information`
-- ----------------------------
DROP TABLE IF EXISTS `epg_information`;
CREATE TABLE `epg_information` (
  `epg_id` int(11) NOT NULL AUTO_INCREMENT,
  `epg_name` varchar(60) NOT NULL,
  `epg_description` varchar(500) DEFAULT NULL,
  `modify_oper` int(11) DEFAULT NULL,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `play_time` int(5) NOT NULL,
  `epg_code` varchar(30) NOT NULL,
  `skippable` int(11) NOT NULL,
  `epg_status` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`epg_id`),
  UNIQUE KEY `epg_index` (`epg_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of epg_information
-- ----------------------------

-- ----------------------------
-- Table structure for `epg_information_history`
-- ----------------------------
DROP TABLE IF EXISTS `epg_information_history`;
CREATE TABLE `epg_information_history` (
  `epg_id` int(11) NOT NULL AUTO_INCREMENT,
  `epg_name` varchar(60) NOT NULL,
  `epg_description` varchar(500) DEFAULT NULL,
  `modify_oper` int(11) DEFAULT NULL,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `play_time` int(5) NOT NULL,
  `epg_code` varchar(30) NOT NULL,
  `skippable` int(11) NOT NULL,
  `epg_status` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`epg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of epg_information_history
-- ----------------------------

-- ----------------------------
-- Table structure for `epg_info_auditing`
-- ----------------------------
DROP TABLE IF EXISTS `epg_info_auditing`;
CREATE TABLE `epg_info_auditing` (
  `auditing_id` int(11) NOT NULL AUTO_INCREMENT,
  `auditing` int(11) DEFAULT NULL,
  `auditing_des` varchar(500) DEFAULT NULL,
  `auditing_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status_id` int(5) DEFAULT NULL,
  `Content_type` varchar(5) DEFAULT NULL,
  `epg_id` int(11) DEFAULT NULL,
  `is_last_auditing` int(5) DEFAULT NULL,
  PRIMARY KEY (`auditing_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of epg_info_auditing
-- ----------------------------

-- ----------------------------
-- Table structure for `epg_info_url`
-- ----------------------------
DROP TABLE IF EXISTS `epg_info_url`;
CREATE TABLE `epg_info_url` (
  `modify_time` timestamp NULL DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `epg_status` int(11) DEFAULT NULL,
  `content_type` varchar(5) NOT NULL,
  `epg_id` int(11) NOT NULL,
  `epg_url` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`content_type`,`epg_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of epg_info_url
-- ----------------------------

-- ----------------------------
-- Table structure for `epg_relation_group`
-- ----------------------------
DROP TABLE IF EXISTS `epg_relation_group`;
CREATE TABLE `epg_relation_group` (
  `epg_id` int(11) NOT NULL,
  `egroup_id` int(20) NOT NULL,
  PRIMARY KEY (`epg_id`,`egroup_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of epg_relation_group
-- ----------------------------

-- ----------------------------
-- Table structure for `iptvuser_frame`
-- ----------------------------
DROP TABLE IF EXISTS `iptvuser_frame`;
CREATE TABLE `iptvuser_frame` (
  `group_id` varchar(20) NOT NULL,
  `zte_frame` varchar(50) DEFAULT NULL,
  `hw_frame` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`group_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iptvuser_frame
-- ----------------------------
INSERT INTO `iptvuser_frame` VALUES ('*', 'frame162', 'bestv');
INSERT INTO `iptvuser_frame` VALUES ('103', null, 'bestvtest0');
INSERT INTO `iptvuser_frame` VALUES ('11', 'frame164', null);
INSERT INTO `iptvuser_frame` VALUES ('123', null, 'bestvhy');
INSERT INTO `iptvuser_frame` VALUES ('124', null, 'xfxnc');
INSERT INTO `iptvuser_frame` VALUES ('143', null, 'zhxqtest');
INSERT INTO `iptvuser_frame` VALUES ('163', null, 'xfxnctest');
INSERT INTO `iptvuser_frame` VALUES ('17', 'frame164', null);
INSERT INTO `iptvuser_frame` VALUES ('183', null, 'bestvjd');
INSERT INTO `iptvuser_frame` VALUES ('2', 'frame165', null);
INSERT INTO `iptvuser_frame` VALUES ('203', null, 'bestvtest');
INSERT INTO `iptvuser_frame` VALUES ('223', null, 'yingyeting');
INSERT INTO `iptvuser_frame` VALUES ('24', null, 'default');
INSERT INTO `iptvuser_frame` VALUES ('25', null, 'bestvjd');
INSERT INTO `iptvuser_frame` VALUES ('26', null, 'bestvjd');
INSERT INTO `iptvuser_frame` VALUES ('27', null, 'default');
INSERT INTO `iptvuser_frame` VALUES ('28', null, 'default');
INSERT INTO `iptvuser_frame` VALUES ('29', null, 'bestvgq');
INSERT INTO `iptvuser_frame` VALUES ('32', 'frame167', null);
INSERT INTO `iptvuser_frame` VALUES ('34', 'frame164', null);
INSERT INTO `iptvuser_frame` VALUES ('35', 'frame186', null);
INSERT INTO `iptvuser_frame` VALUES ('36', 'frame186', null);
INSERT INTO `iptvuser_frame` VALUES ('43', null, 'bestvtest');
INSERT INTO `iptvuser_frame` VALUES ('46', 'frame198', null);
INSERT INTO `iptvuser_frame` VALUES ('50', 'frame168', null);
INSERT INTO `iptvuser_frame` VALUES ('51', 'frame208', null);
INSERT INTO `iptvuser_frame` VALUES ('52', 'frame185', null);
INSERT INTO `iptvuser_frame` VALUES ('53', 'frame198', null);
INSERT INTO `iptvuser_frame` VALUES ('54', 'frame211', null);
INSERT INTO `iptvuser_frame` VALUES ('55', 'frame217', null);
INSERT INTO `iptvuser_frame` VALUES ('56', 'frame213', null);
INSERT INTO `iptvuser_frame` VALUES ('57', 'frame186', null);
INSERT INTO `iptvuser_frame` VALUES ('58', 'frame208', null);
INSERT INTO `iptvuser_frame` VALUES ('59', 'frame218', null);
INSERT INTO `iptvuser_frame` VALUES ('84', null, 'bestvhy');

-- ----------------------------
-- Table structure for `iptv_group`
-- ----------------------------
DROP TABLE IF EXISTS `iptv_group`;
CREATE TABLE `iptv_group` (
  `igroup_id` int(20) NOT NULL AUTO_INCREMENT,
  `igroup_code` varchar(50) NOT NULL,
  `igroup_name` varchar(50) NOT NULL,
  `igroup_description` varchar(500) DEFAULT NULL,
  `inode_order` int(11) DEFAULT NULL,
  `iparent_id` int(20) DEFAULT NULL,
  `modify_oper` int(11) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `igroup_priority` int(11) NOT NULL,
  PRIMARY KEY (`igroup_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iptv_group
-- ----------------------------
INSERT INTO `iptv_group` VALUES ('1', 'G0000', '默认分组', '默认分组', null, null, null, '2013-11-11 09:06:41', '2013-10-12 08:41:11', '0');

-- ----------------------------
-- Table structure for `iptv_group_history`
-- ----------------------------
DROP TABLE IF EXISTS `iptv_group_history`;
CREATE TABLE `iptv_group_history` (
  `igroup_id` int(20) NOT NULL AUTO_INCREMENT,
  `igroup_code` varchar(50) NOT NULL,
  `igroup_name` varchar(50) NOT NULL,
  `igroup_description` varchar(500) DEFAULT NULL,
  `inode_order` int(11) DEFAULT NULL,
  `iparent_id` int(20) DEFAULT NULL,
  `modify_oper` int(11) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`igroup_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iptv_group_history
-- ----------------------------

-- ----------------------------
-- Table structure for `iptv_user`
-- ----------------------------
DROP TABLE IF EXISTS `iptv_user`;
CREATE TABLE `iptv_user` (
  `user_id` int(15) NOT NULL AUTO_INCREMENT,
  `user_type` varchar(5) DEFAULT NULL,
  `last_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `user_level` varchar(10) DEFAULT '0' COMMENT '0����ͨ�û���1���������û���2���������û�',
  `user_code` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `district_id` varchar(20) DEFAULT NULL,
  `business_id` varchar(20) DEFAULT NULL,
  `terrace_type` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_index` (`user_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iptv_user
-- ----------------------------

-- ----------------------------
-- Table structure for `iptv_user_group`
-- ----------------------------
DROP TABLE IF EXISTS `iptv_user_group`;
CREATE TABLE `iptv_user_group` (
  `user_id` int(15) NOT NULL,
  `igroup_id` int(20) NOT NULL,
  `modify_oper` int(11) DEFAULT NULL,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`igroup_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of iptv_user_group
-- ----------------------------

-- ----------------------------
-- Table structure for `material_igroup`
-- ----------------------------
DROP TABLE IF EXISTS `material_igroup`;
CREATE TABLE `material_igroup` (
  `mig_id` int(11) NOT NULL AUTO_INCREMENT,
  `miparent_id` int(11) DEFAULT NULL,
  `minode_order` int(5) DEFAULT NULL,
  `mig_name` varchar(50) NOT NULL,
  `mig_description` varchar(200) DEFAULT NULL,
  `modify_oper` int(11) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`mig_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of material_igroup
-- ----------------------------
INSERT INTO `material_igroup` VALUES ('1', '0', null, '图片分组', '图片分组', null, '2013-10-11 17:06:31', '2013-10-11 17:06:36');

-- ----------------------------
-- Table structure for `material_image`
-- ----------------------------
DROP TABLE IF EXISTS `material_image`;
CREATE TABLE `material_image` (
  `img_id` int(11) NOT NULL AUTO_INCREMENT,
  `img_code` varchar(60) NOT NULL,
  `img_name` varchar(60) NOT NULL,
  `img_description` varchar(500) DEFAULT NULL,
  `img_type` varchar(5) NOT NULL,
  `modify_oper` int(11) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `status_id` int(5) DEFAULT NULL,
  PRIMARY KEY (`img_id`),
  UNIQUE KEY `img_code_index` (`img_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of material_image
-- ----------------------------

-- ----------------------------
-- Table structure for `material_image_auditing`
-- ----------------------------
DROP TABLE IF EXISTS `material_image_auditing`;
CREATE TABLE `material_image_auditing` (
  `auditing_id` int(11) NOT NULL AUTO_INCREMENT,
  `auditing` int(11) DEFAULT NULL,
  `auditing_des` varchar(500) DEFAULT NULL,
  `auditing_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status_id` int(5) DEFAULT NULL,
  `content_type` varchar(5) DEFAULT NULL,
  `img_id` int(11) DEFAULT NULL,
  `is_last_auditing` int(5) DEFAULT NULL,
  PRIMARY KEY (`auditing_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of material_image_auditing
-- ----------------------------

-- ----------------------------
-- Table structure for `material_image_url`
-- ----------------------------
DROP TABLE IF EXISTS `material_image_url`;
CREATE TABLE `material_image_url` (
  `modify_oper` int(11) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status_id` int(5) DEFAULT NULL,
  `img_url` varchar(300) DEFAULT NULL,
  `content_type` varchar(5) NOT NULL,
  `img_id` int(11) NOT NULL,
  PRIMARY KEY (`content_type`,`img_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of material_image_url
-- ----------------------------

-- ----------------------------
-- Table structure for `material_img_group`
-- ----------------------------
DROP TABLE IF EXISTS `material_img_group`;
CREATE TABLE `material_img_group` (
  `mig_id` int(11) NOT NULL,
  `img_id` int(11) NOT NULL,
  PRIMARY KEY (`mig_id`,`img_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of material_img_group
-- ----------------------------

-- ----------------------------
-- Table structure for `material_vgroup`
-- ----------------------------
DROP TABLE IF EXISTS `material_vgroup`;
CREATE TABLE `material_vgroup` (
  `mvg_id` int(11) NOT NULL AUTO_INCREMENT,
  `mvparent_id` int(11) DEFAULT NULL,
  `mvnode_order` int(5) DEFAULT NULL,
  `mvg_name` varchar(50) NOT NULL,
  `mvg_description` varchar(200) DEFAULT NULL,
  `modify_oper` int(11) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`mvg_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of material_vgroup
-- ----------------------------
INSERT INTO `material_vgroup` VALUES ('1', '0', null, '视频分组', '视频分组', null, '2013-10-11 17:43:08', '2013-10-11 17:43:11');

-- ----------------------------
-- Table structure for `material_video`
-- ----------------------------
DROP TABLE IF EXISTS `material_video`;
CREATE TABLE `material_video` (
  `video_id` int(11) NOT NULL AUTO_INCREMENT,
  `video_name` varchar(60) NOT NULL,
  `video_code` varchar(60) NOT NULL,
  `video_description` varchar(500) DEFAULT NULL,
  `modify_oper` int(11) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  PRIMARY KEY (`video_id`),
  UNIQUE KEY `video_code_index` (`video_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of material_video
-- ----------------------------

-- ----------------------------
-- Table structure for `material_video_auditing`
-- ----------------------------
DROP TABLE IF EXISTS `material_video_auditing`;
CREATE TABLE `material_video_auditing` (
  `auditing_id` int(11) NOT NULL AUTO_INCREMENT,
  `auditing` int(11) DEFAULT NULL,
  `auditing_des` varchar(500) DEFAULT NULL,
  `auditing_date` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status_id` int(5) DEFAULT NULL,
  `video_id` int(11) DEFAULT NULL,
  `is_last_auditing` int(5) DEFAULT NULL,
  PRIMARY KEY (`auditing_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of material_video_auditing
-- ----------------------------

-- ----------------------------
-- Table structure for `material_video_group`
-- ----------------------------
DROP TABLE IF EXISTS `material_video_group`;
CREATE TABLE `material_video_group` (
  `mvg_id` int(11) NOT NULL,
  `video_id` int(11) NOT NULL,
  PRIMARY KEY (`mvg_id`,`video_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of material_video_group
-- ----------------------------

-- ----------------------------
-- Table structure for `material_video_url`
-- ----------------------------
DROP TABLE IF EXISTS `material_video_url`;
CREATE TABLE `material_video_url` (
  `video_id` int(11) NOT NULL,
  `video_platform` varchar(50) NOT NULL,
  `video_url` varchar(300) DEFAULT NULL,
  `video_type` varchar(50) DEFAULT NULL,
  `modify_oper` int(11) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `status_id` int(5) DEFAULT NULL,
  PRIMARY KEY (`video_id`,`video_platform`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of material_video_url
-- ----------------------------

-- ----------------------------
-- Table structure for `operation_district`
-- ----------------------------
DROP TABLE IF EXISTS `operation_district`;
CREATE TABLE `operation_district` (
  `district_code` varchar(30) NOT NULL,
  `district_id` varchar(20) NOT NULL,
  `district_name` varchar(30) NOT NULL,
  `terrace_id` varchar(30) NOT NULL,
  `district_mark` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`district_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operation_district
-- ----------------------------
INSERT INTO `operation_district` VALUES ('DIS2013111900002', '1001', '武汉', 'TER2013110600002', '武汉');
INSERT INTO `operation_district` VALUES ('DIS2013111900003', '1006', '孝感', 'TER2013110600002', '孝感');
INSERT INTO `operation_district` VALUES ('DIS2013112000002', '1003', '襄樊', 'TER2013110600002', '襄樊\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000003', '1004', '黄冈', 'TER2013110600002', '黄冈\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000004', '1005', '宜昌', 'TER2013110600002', '宜昌\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000005', '1007', '鄂州', 'TER2013110600002', '鄂州\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000006', '1008', '咸宁', 'TER2013110600002', '咸宁\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000007', '1009', '十堰', 'TER2013110600002', '十堰\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000008', '1010', '荆门', 'TER2013110600002', '荆门\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000009', '1011', '黄石', 'TER2013110600002', '黄石\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000010', '1012', '随州', 'TER2013110600002', '随州\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000011', '1013', '恩施', 'TER2013110600002', '恩施\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000012', '1014', '仙桃', 'TER2013110600002', '仙桃\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000013', '1015', '天门', 'TER2013110600002', '天门\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000014', '1016', '潜江', 'TER2013110600002', '潜江\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000015', '1017', '林区', 'TER2013110600002', '林区\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000016', '1018', '荆州', 'TER2013110600002', '荆州\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000017', '10003', '武汉', 'TER2013110600001', '武汉\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000018', '10004', '襄樊', 'TER2013110600001', '襄樊\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000019', '10005', '黄冈', 'TER2013110600001', '黄冈\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000020', '10006', '宜昌', 'TER2013110600001', '宜昌\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000021', '10007', '孝感', 'TER2013110600001', '孝感\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000022', '10008', '鄂州', 'TER2013110600001', '鄂州\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000023', '10009', '咸宁', 'TER2013110600001', '咸宁\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000024', '10011', '十堰', 'TER2013110600001', '十堰\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000025', '10012', '荆门', 'TER2013110600001', '荆门\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000026', '10013', '黄石', 'TER2013110600001', '黄石\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000027', '10014', '随州', 'TER2013110600001', '随州\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000028', '10015', '恩施', 'TER2013110600001', '恩施\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000029', '10016', '仙桃', 'TER2013110600001', '仙桃\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000030', '10017', '天门', 'TER2013110600001', '天门\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000031', '10018', '潜江', 'TER2013110600001', '潜江\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000032', '10019', '林区', 'TER2013110600001', '林区\r\n');
INSERT INTO `operation_district` VALUES ('DIS2013112000033', '10020', '荆州', 'TER2013110600001', '荆州\r\n');

-- ----------------------------
-- Table structure for `operation_property_group`
-- ----------------------------
DROP TABLE IF EXISTS `operation_property_group`;
CREATE TABLE `operation_property_group` (
  `property_code` varchar(30) NOT NULL,
  `group_id` varchar(20) NOT NULL,
  `terrace_id` varchar(30) NOT NULL,
  `group_name` varchar(30) NOT NULL,
  `frame_name` varchar(50) DEFAULT NULL,
  `group_mark` varchar(300) DEFAULT NULL,
  `group_type` int(5) NOT NULL,
  PRIMARY KEY (`property_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operation_property_group
-- ----------------------------
INSERT INTO `operation_property_group` VALUES ('PRO2013101500001', '*', 'TER2013110600002', '中兴平台默认分组', 'frame162', '中兴平台默认分组', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013101500002', '*', 'TER2013110600001', '华为平台默认分组', 'bestv', '华为平台默认分组', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013111900003', '11', 'TER2013110600002', '黄金版用户组', 'frame164', '黄金版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013111900004', '35', 'TER2013110600002', '酒店版用户组', 'frame186', '酒店版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013111900005', '32', 'TER2013110600002', '高清版用户组', 'frame167', '高清版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000002', '34', 'TER2013110600002', '测试版用户组', 'frame164', '测试版用户组', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000003', '17', 'TER2013110600002', '经典版用户组', 'frame164', '经典版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000004', '57', 'TER2013110600002', '高清酒店版用户组', 'frame186', '高清酒店版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000005', '36', 'TER2013110600002', '体验版用户组', 'frame186', '体验版用户组', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000006', '46', 'TER2013110600002', '全省农行黄金版用户组', 'frame198', '全省农行黄金版用户组', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000007', '53', 'TER2013110600002', '智慧小区版用户组', 'frame198', '智慧小区版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000008', '55', 'TER2013110600002', '智慧小区测试版用户组', 'frame217', '智慧小区测试版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000009', '54', 'TER2013110600002', '幸福新农村版用户组', 'frame211', '幸福新农村版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000010', '56', 'TER2013110600002', '幸福新农村测试版用户组', 'frame213', '幸福新农村测试版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000011', '50', 'TER2013110600002', '测试用户分组1', 'frame168', '测试用户分组1\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000012', '51', 'TER2013110600002', '测试用户分组2', 'frame208', '测试用户分组2\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000013', '58', 'TER2013110600002', '酒店测试版用户组', 'frame208', '酒店测试版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000014', '2', 'TER2013110600002', '湖北党教用户组', 'frame165', '湖北党教用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000015', '52', 'TER2013110600002', '党教测试用户分组', 'frame185', '党教测试用户分组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000016', '59', 'TER2013110600002', '营业厅用户组', 'frame218', '营业厅用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000017', '28', 'TER2013110600001', '黄金版用户组', 'default', '黄金版用户组', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000018', '24', 'TER2013110600001', '测试版用户组', 'default', '测试版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000019', '27', 'TER2013110600001', '经典版用户组', 'default', '经典版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000020', '183', 'TER2013110600001', '高清酒店版用户组', 'bestvjd', '高清酒店版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000021', '25', 'TER2013110600001', '酒店版用户组', 'bestvjd', '酒店版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000022', '26', 'TER2013110600001', '体验版用户组', 'bestvjd', '体验版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000023', '29', 'TER2013110600001', '高清版用户组', 'bestvgq', '高清版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000024', '84', 'TER2013110600001', '全省农行黄金版用户组', 'bestvhy', '全省农行黄金版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000025', '123', 'TER2013110600001', '智慧小区版用户组', 'bestvhy', '智慧小区版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000026', '143', 'TER2013110600001', '智慧小区测试版用户组', 'zhxqtest', '智慧小区测试版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000027', '124', 'TER2013110600001', '幸福新农村版用户组', 'xfxnc', '幸福新农村版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000028', '163', 'TER2013110600001', '幸福新农村测试版用户组', 'xfxnctest', '幸福新农村测试版用户组', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000029', '103', 'TER2013110600001', '测试用户分组1', 'bestvtest0', '测试用户分组1\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000030', '43', 'TER2013110600001', '测试用户分组2', 'bestvtest', '测试用户分组2\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000031', '203', 'TER2013110600001', '酒店测试版用户组', 'bestvtest', '酒店测试版用户组\r\n', '0');
INSERT INTO `operation_property_group` VALUES ('PRO2013112000032', '223', 'TER2013110600001', '营业厅用户组', 'yingyeting', '营业厅用户组\r\n\r\n', '0');

-- ----------------------------
-- Table structure for `operation_terrace`
-- ----------------------------
DROP TABLE IF EXISTS `operation_terrace`;
CREATE TABLE `operation_terrace` (
  `terrace_code` varchar(30) NOT NULL,
  `terrace_id` varchar(20) NOT NULL,
  `terrace_name` varchar(30) NOT NULL,
  `terrace_mark` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`terrace_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operation_terrace
-- ----------------------------
INSERT INTO `operation_terrace` VALUES ('TER2013110600001', 'hw', '华为平台', '华为平台');
INSERT INTO `operation_terrace` VALUES ('TER2013110600002', 'zte', '中兴平台', '中兴平台');

-- ----------------------------
-- Table structure for `operation_url`
-- ----------------------------
DROP TABLE IF EXISTS `operation_url`;
CREATE TABLE `operation_url` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `url_address` varchar(255) NOT NULL,
  `url_name` varchar(30) NOT NULL,
  `url_provider` varchar(100) NOT NULL,
  `url_mark` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of operation_url
-- ----------------------------

-- ----------------------------
-- Table structure for `report_day`
-- ----------------------------
DROP TABLE IF EXISTS `report_day`;
CREATE TABLE `report_day` (
  `day_id` int(11) NOT NULL AUTO_INCREMENT,
  `day_value` int(2) NOT NULL,
  `req_count` int(30) NOT NULL,
  `reach_count` int(30) NOT NULL,
  `yearmonth` varchar(10) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`day_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_day
-- ----------------------------

-- ----------------------------
-- Table structure for `report_hour`
-- ----------------------------
DROP TABLE IF EXISTS `report_hour`;
CREATE TABLE `report_hour` (
  `time_id` int(11) NOT NULL AUTO_INCREMENT,
  `time_value` int(2) NOT NULL,
  `req_count` int(30) NOT NULL,
  `reach_count` int(30) NOT NULL,
  `time_date` date NOT NULL DEFAULT '0000-00-00',
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`time_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_hour
-- ----------------------------

-- ----------------------------
-- Table structure for `report_month`
-- ----------------------------
DROP TABLE IF EXISTS `report_month`;
CREATE TABLE `report_month` (
  `month_id` int(11) NOT NULL AUTO_INCREMENT,
  `month_value` int(2) NOT NULL,
  `req_count` int(30) NOT NULL,
  `reach_count` int(30) NOT NULL,
  `year` int(4) NOT NULL,
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`month_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_month
-- ----------------------------

-- ----------------------------
-- Table structure for `report_request_record`
-- ----------------------------
DROP TABLE IF EXISTS `report_request_record`;
CREATE TABLE `report_request_record` (
  `report_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(20) NOT NULL,
  `epg_id` varchar(30) NOT NULL,
  `request_date` date NOT NULL,
  `request_hour` int(2) NOT NULL,
  `skip_date` date NOT NULL,
  `skip_hour` int(2) NOT NULL,
  `skip_type` int(1) NOT NULL,
  `epg_name` varchar(50) NOT NULL DEFAULT '',
  `group_id` int(11) NOT NULL,
  PRIMARY KEY (`report_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of report_request_record
-- ----------------------------

-- ----------------------------
-- Table structure for `schedule_base_info`
-- ----------------------------
DROP TABLE IF EXISTS `schedule_base_info`;
CREATE TABLE `schedule_base_info` (
  `schedule_code` varchar(30) NOT NULL,
  `schedule_id` int(20) NOT NULL AUTO_INCREMENT,
  `schedule_name` varchar(50) DEFAULT NULL,
  `modify_oper` int(11) DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `schedule_description` varchar(200) DEFAULT NULL,
  `schedule_status` int(5) NOT NULL,
  `start_date` date NOT NULL,
  `start_time` int(10) NOT NULL,
  `end_time` int(10) NOT NULL,
  `igroup_id` int(20) DEFAULT NULL,
  `end_date` date NOT NULL,
  `epg_id` int(11) NOT NULL,
  `schedule_color` varchar(8) NOT NULL,
  `igroup_code` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`schedule_id`),
  UNIQUE KEY `schedule_index` (`schedule_code`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of schedule_base_info
-- ----------------------------

-- ----------------------------
-- Table structure for `schedule_groups`
-- ----------------------------
DROP TABLE IF EXISTS `schedule_groups`;
CREATE TABLE `schedule_groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `schedule_code` varchar(30) NOT NULL,
  `district_code` text,
  `business_code` text,
  `terrace_code` varchar(30) DEFAULT NULL,
  `priority_value` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of schedule_groups
-- ----------------------------

-- ----------------------------
-- Table structure for `system_action_log`
-- ----------------------------
DROP TABLE IF EXISTS `system_action_log`;
CREATE TABLE `system_action_log` (
  `aciton_id` int(20) NOT NULL AUTO_INCREMENT,
  `action_type` varchar(20) DEFAULT NULL,
  `action_content` varchar(128) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `detail_id` int(11) DEFAULT NULL,
  `action_uri` varbinary(256) DEFAULT NULL,
  `action_param` text,
  `action_oper` varchar(20) DEFAULT NULL,
  `role_name` varchar(50) DEFAULT NULL,
  `login_ip` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`aciton_id`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_action_log
-- ----------------------------
INSERT INTO `system_action_log` VALUES ('64', 'success', '系统登录页面', '2016-07-24 22:36:38', null, 0x2F426173655765622F73797374656D2F6C6F67696E5F696E6465782E646F, '', 'system', null, '0:0:0:0:0:0:0:1');
INSERT INTO `system_action_log` VALUES ('65', 'success', '登录系统', '2016-07-24 22:36:57', null, 0x73797374656D2F6C6F67696E, 'userid:admin<br/>password:***', 'admin', '系统管理员', '0:0:0:0:0:0:0:1');
INSERT INTO `system_action_log` VALUES ('66', 'success', '查询操作员列表', '2016-07-24 22:37:16', null, 0x2F426173655765622F73797374656D2F757365722F6C6973742E646F, 'act_menu:4<br/>mid2:225<br/>mid1:205<br/>', 'admin', '系统管理员', '0:0:0:0:0:0:0:1');
INSERT INTO `system_action_log` VALUES ('67', 'success', '查询角色', '2016-07-24 22:37:20', null, 0x2F426173655765622F73797374656D2F726F6C652F6C6973742E646F, 'act_menu:4<br/>mid2:230<br/>mid1:205<br/>', 'admin', '系统管理员', '0:0:0:0:0:0:0:1');

-- ----------------------------
-- Table structure for `system_code`
-- ----------------------------
DROP TABLE IF EXISTS `system_code`;
CREATE TABLE `system_code` (
  `code_name` varchar(50) NOT NULL,
  `prefix` varchar(50) DEFAULT NULL,
  `middle` varchar(50) DEFAULT NULL,
  `code` varchar(10) NOT NULL,
  `step` int(10) DEFAULT NULL,
  `code_length` int(5) NOT NULL DEFAULT '5',
  PRIMARY KEY (`code_name`),
  UNIQUE KEY `code_index` (`code_name`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_code
-- ----------------------------
INSERT INTO `system_code` VALUES ('DISTRICT_CODE', 'DIS', 'YYYYMMDD', '0', '1', '5');
INSERT INTO `system_code` VALUES ('INF_CODE', 'INF', 'YYYYMMDD', '0', '1', '5');
INSERT INTO `system_code` VALUES ('LIS_CODE', 'LIS', 'YYYYMMDD', '0', '1', '5');
INSERT INTO `system_code` VALUES ('PIC_CODE', 'PIC', 'YYYYMMDD', '0', '1', '5');
INSERT INTO `system_code` VALUES ('PROPERTY_CODE', 'PRO', 'YYYYMMDD', '2', '1', '5');
INSERT INTO `system_code` VALUES ('SELFGROUP_CODE', 'SEL', 'YYYYMMDD', '0', '1', '5');
INSERT INTO `system_code` VALUES ('TERANCE_CODE', 'TER', 'YYYYMMDD', '2', '1', '5');
INSERT INTO `system_code` VALUES ('VID_CODE', 'VID', 'YYYYMMDD', '0', '1', '5');

-- ----------------------------
-- Table structure for `system_log_detail`
-- ----------------------------
DROP TABLE IF EXISTS `system_log_detail`;
CREATE TABLE `system_log_detail` (
  `detail_id` int(20) NOT NULL AUTO_INCREMENT,
  `detail_text` text,
  PRIMARY KEY (`detail_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_log_detail
-- ----------------------------

-- ----------------------------
-- Table structure for `system_menu`
-- ----------------------------
DROP TABLE IF EXISTS `system_menu`;
CREATE TABLE `system_menu` (
  `menu_id` int(11) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(50) NOT NULL,
  `menu_description` varchar(200) DEFAULT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `node_order` int(5) DEFAULT NULL,
  `menu_uri` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=339 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_menu
-- ----------------------------
INSERT INTO `system_menu` VALUES ('202', 'SYSTEM_MENU_TREE', '系统功能菜单树', null, '0', null);
INSERT INTO `system_menu` VALUES ('205', '系统管理', null, '202', '5', '');
INSERT INTO `system_menu` VALUES ('211', '参数管理', null, '205', '4', 'system/param/list.do');
INSERT INTO `system_menu` VALUES ('215', '日志管理', null, '205', '5', 'system/log/list.do');
INSERT INTO `system_menu` VALUES ('216', '菜单管理', null, '205', '3', 'system/menu/list.do');
INSERT INTO `system_menu` VALUES ('225', '账号管理', null, '205', '0', 'system/user/list.do');
INSERT INTO `system_menu` VALUES ('227', '权限管理', null, '205', '2', 'system/privilege/list.do');
INSERT INTO `system_menu` VALUES ('230', '角色管理', null, '205', '1', 'system/role/list.do');
INSERT INTO `system_menu` VALUES ('231', '用户管理', null, '202', '4', '');
INSERT INTO `system_menu` VALUES ('232', '自定义群组', null, '231', '1', 'iptvgroup/list.do');
INSERT INTO `system_menu` VALUES ('233', '黑白名单', null, '231', '2', 'backwrite/list.do');
INSERT INTO `system_menu` VALUES ('317', '素材管理', null, '202', '1', '');
INSERT INTO `system_menu` VALUES ('318', '素材上传', null, '317', '0', 'materialImage/addUI.do');
INSERT INTO `system_menu` VALUES ('319', '素材列表', null, '317', '1', 'materialImage/list.do');
INSERT INTO `system_menu` VALUES ('320', '素材分组', null, '317', '2', 'imageGroup/groupUI.do');
INSERT INTO `system_menu` VALUES ('321', '排期管理', null, '202', '3', '');
INSERT INTO `system_menu` VALUES ('322', '管理排期', null, '321', '1', 'schedule/checkGroup.do');
INSERT INTO `system_menu` VALUES ('323', '信息管理', null, '202', '2', '');
INSERT INTO `system_menu` VALUES ('324', '页面制作', null, '323', '1', 'epginfo/make.do');
INSERT INTO `system_menu` VALUES ('325', '信息列表', null, '323', '2', 'epginfo/list.do');
INSERT INTO `system_menu` VALUES ('326', '信息分组', null, '323', '3', 'epggroup/groupManage.do');
INSERT INTO `system_menu` VALUES ('327', '报表管理', null, '202', '6', null);
INSERT INTO `system_menu` VALUES ('328', '整体报表', null, '327', '0', 'report/reporttest.do');
INSERT INTO `system_menu` VALUES ('329', '时间报表', null, '327', '1', 'hourreport/himgsearch.do');
INSERT INTO `system_menu` VALUES ('330', '单个报表', null, '327', '2', 'report/adlist.do');
INSERT INTO `system_menu` VALUES ('331', '用户列表', null, '231', '0', 'iptvuser/iptvuserlist.do');
INSERT INTO `system_menu` VALUES ('332', '运营管理', null, '202', '7', '');
INSERT INTO `system_menu` VALUES ('333', '区域群组', null, '332', '0', 'district/list.do');
INSERT INTO `system_menu` VALUES ('334', '平台群组', null, '332', '2', 'terrace/list.do');
INSERT INTO `system_menu` VALUES ('335', '业务群组', null, '332', '1', 'propertygroup/list.do');
INSERT INTO `system_menu` VALUES ('336', '超链接群组', null, '332', '3', 'operationUrl/list.do');
INSERT INTO `system_menu` VALUES ('337', '模板导出', null, '321', '2', 'schedule/listAll.do');
INSERT INTO `system_menu` VALUES ('338', '优先级管理', null, '321', '3', 'schedule/conflictManage.do');

-- ----------------------------
-- Table structure for `system_oper`
-- ----------------------------
DROP TABLE IF EXISTS `system_oper`;
CREATE TABLE `system_oper` (
  `oper_id` int(11) NOT NULL AUTO_INCREMENT,
  `oper_name` varchar(20) DEFAULT NULL,
  `oper_password` varchar(40) DEFAULT NULL,
  `oper_nikename` varchar(20) DEFAULT NULL,
  `oper_city` varchar(20) DEFAULT NULL,
  `oper_status` varchar(20) DEFAULT NULL,
  `last_login_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`oper_id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_oper
-- ----------------------------
INSERT INTO `system_oper` VALUES ('55', 'admin', '21232f297a57a5a743894a0e4a801fc3', '系统管理员', null, '0', '2016-07-24 22:36:57', '系统管理员');

-- ----------------------------
-- Table structure for `system_oper_log`
-- ----------------------------
DROP TABLE IF EXISTS `system_oper_log`;
CREATE TABLE `system_oper_log` (
  `id` int(18) NOT NULL AUTO_INCREMENT,
  `oper_id` varchar(20) DEFAULT NULL,
  `action_code` varchar(20) DEFAULT NULL,
  `operation_desc` varchar(7) DEFAULT NULL,
  `s_col_1` varchar(50) DEFAULT NULL,
  `s_col_2` varchar(50) DEFAULT NULL,
  `s_col_3` varchar(50) DEFAULT NULL,
  `s_col_4` varchar(50) DEFAULT NULL,
  `i_col_1` int(11) DEFAULT NULL,
  `i_col_2` int(11) DEFAULT NULL,
  `i_col_3` int(11) DEFAULT NULL,
  `d_col_1` datetime DEFAULT NULL,
  `d_col_2` datetime DEFAULT NULL,
  `d_col_3` datetime DEFAULT NULL,
  `input_params` varchar(600) DEFAULT NULL,
  `http_resp_code` varchar(3) DEFAULT NULL,
  `http_error_msg` varchar(600) DEFAULT NULL,
  `third_resp_data` varchar(600) DEFAULT NULL,
  `resp_data` varchar(1000) DEFAULT NULL,
  `business_result_code` int(3) DEFAULT NULL,
  `exception_info` varchar(6000) DEFAULT NULL,
  `using_time` int(11) DEFAULT NULL,
  `comment` varchar(300) DEFAULT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_oper_log
-- ----------------------------
INSERT INTO `system_oper_log` VALUES ('37', null, 'SL01S01', '管理员登录界面', null, null, null, null, null, null, null, null, null, null, '', null, null, null, null, null, null, null, '', '2016-07-24 22:36:38');
INSERT INTO `system_oper_log` VALUES ('38', null, 'SL02S01', '管理员登录', 'admin', null, null, null, null, null, null, null, null, null, 'userid=admin;', null, null, null, null, null, null, null, 's_col_1=userid;', '2016-07-24 22:36:56');
INSERT INTO `system_oper_log` VALUES ('39', 'admin', 'SU01S01', '查询用户列表', null, null, null, null, null, null, null, null, null, null, 'name=null;status=null;userid=null;', null, null, null, null, null, null, null, '', '2016-07-24 22:37:16');
INSERT INTO `system_oper_log` VALUES ('40', 'admin', 'SR01S01', '查询角色', null, null, null, null, null, null, null, null, null, null, 'name=null;', null, null, null, null, null, null, null, '', '2016-07-24 22:37:20');

-- ----------------------------
-- Table structure for `system_oper_privilege`
-- ----------------------------
DROP TABLE IF EXISTS `system_oper_privilege`;
CREATE TABLE `system_oper_privilege` (
  `privilege_id` int(11) NOT NULL,
  `op_id` int(11) NOT NULL AUTO_INCREMENT,
  `oper_name` varchar(20) NOT NULL,
  `oper_description` varchar(100) DEFAULT NULL,
  `oper_privilege_uri` varchar(256) NOT NULL,
  `oper_level` int(2) NOT NULL,
  PRIMARY KEY (`op_id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_oper_privilege
-- ----------------------------
INSERT INTO `system_oper_privilege` VALUES ('140', '1', '添加群组', '添加一个用户分组的操作权限haha', 'iptvgroup/add.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('140', '2', '编辑群组', '修改一个用户分组的操作权限', 'iptvgroup/update.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('141', '4', '修改状态', '此权限可以使IPTV用户在普通用户、黑名单和白名单的状态中进行转换', 'backwrite/update.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('141', '5', '批量删除', '批量的删除黑白名单记录的操作权限', 'backwrite/updateMore.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('134', '6', '添加', '添加一个系统操作员的操作权限', 'system/user/add.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('134', '7', '修改', '修改一个系统操作员的操作权限', 'system/user/update.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('134', '8', '删除', '删除一个系统操作员的操作权限', 'system/user/remove.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('135', '9', '添加', '添加一个菜单权限', 'system/privilege/add.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('135', '10', '修改', '修改一个菜单权限', 'system/privilege/update.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('135', '11', '删除', '删除一个菜单权限', 'system/privilege/remove.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('136', '12', '添加', '添加一个系统角色信息的操作权限', 'system/role/add.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('136', '13', '修改', '修改一个系统角色信息的操作权限', 'system/role/update.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('136', '14', '删除', '删除一个系统角色信息的操作权限', 'system/role/remove.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('140', '19', '删除群组', '删除一个分组记录信息', 'iptvgroup/remove.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('140', '20', '删除群组中的用户', '单个删除用户分组中的IPTV用户记录信息', 'iptvuser/remove.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('140', '21', '批量删除群组中的用户', '批量删除分组中的IPTV用户记录信息', 'iptvuser/removeAll.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('140', '22', '批量添加用户到群组', '批量导入IPTV用户到指定用户分组里', 'iptvuser/batchadd.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('141', '23', '批量添加', '批量的添加黑白名单记录的操作权限', 'backwrite/batchadd.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('143', '24', '批量删除视频', '批量删除视频', 'materialVideo/batchDelete.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('143', '25', '批量审核视频', '批量审核视频', 'materialVideo/batchAuditing.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('134', '26', '修改操作员状态', '修改操作员状态，设为正常或者停用', 'system/user/updateStatus.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('143', '27', '编辑视频', '编辑视频', 'materialVideo/videoUpdateUI.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('143', '28', '删除视频', '单个删除视频', 'materialVideo/delete.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('142', '30', '上传视频', '上传视频', 'materialVideo/add.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('142', '31', '上传图片', '上传图片', 'materialImage/add.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('143', '32', '编辑图片', '编辑图片', 'materialImage/updateUI.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('143', '33', '审核图片', '审核图片', 'materialImage/audit.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('143', '34', '删除图片', '删除图片', 'materialImage/delete.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('143', '35', '批量删除图片', '批量删除图片', 'materialImage/batchDelete.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('143', '36', '批量审核图片', '批量审核图片', 'materialImage/batchAuditing.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('143', '37', '审核视频', '审核视频', 'materialVideo/audit.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('145', '38', '添加排期', '添加排期 ', 'schedule/addSchedule.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('145', '39', '修改排期', '修改排期', 'schedule/updateSchedule.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('145', '40', '删除排期', '删除排期', 'schedule/deleteSchedule.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('144', '41', '创建图片分组', '创建图片分组', 'imageGroup/add.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('144', '42', '编辑图片分组', '编辑图片分组', 'imageGroup/update.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('144', '43', '删除图片分组', '删除图片分组', 'imageGroup/delete.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('144', '44', '创建视频分组', '创建视频分组', 'videoGroup/add.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('144', '45', '编辑视频分组', '编辑视频分组', 'videoGroup/update.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('144', '46', '删除视频分组', '删除视频分组', 'videoGroup/delete.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('147', '47', '单个审核', '', 'epginfo/auditingEpgInfo.do', '1');
INSERT INTO `system_oper_privilege` VALUES ('147', '48', '删除页面', '', 'epginfo/remove.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('147', '49', '撤销审核状态', '可以将信息页面待审核变为待提交状态，并可以重新编辑页面', 'epginfo/revert.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('147', '50', '批量删除', '', 'epginfo/batchRemove.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('147', '51', '批量审核', '', 'epginfo/batchAuditing.do', '1');
INSERT INTO `system_oper_privilege` VALUES ('148', '52', '创建信息分组', '创建信息分组', 'epggroup/addGroup.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('148', '53', '修改信息分组', '修改信息分组', 'epggroup/update.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('148', '54', '删除信息分组', '删除信息分组', 'epggroup/remove.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('152', '55', '单个删除用户', '可单个删除用户管理中用户列表的用户记录', 'iptvuser/userremove.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('152', '56', '批量删除用户', '可批量删除用户管理中用户列表的用户记录', 'iptvuser/userremoveAll.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('152', '57', '批量添加用户', '可批量添加IPTV用户信息到系统中', 'iptvuser/batchadduser.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('147', '58', '修改页面信息分组', '可以修改已编辑的页面信息的分组属性', 'epggroup/updateEpgInfoGroup.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('153', '59', '添加区域信息', '可以添加区域信息记录', 'district/add.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('153', '60', '修改区域信息', '可以修改区域信息记录', 'district/update.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('153', '61', '删除区域信息', '可以删除区域信息记录', 'district/remove.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('154', '62', '添加平台信息', '可以添加平台信息记录', 'terrace/add.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('154', '63', '修改平台信息', '可以修改平台信息记录', 'terrace/update.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('154', '64', '删除平台信息', '可以删除平台信息记录', 'terrace/remove.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('155', '65', '添加业务群组', '可以添加特性分组记录信息', 'propertygroup/add.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('155', '66', '修改业务群组', '可以修改特性分组记录信息', 'propertygroup/update.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('155', '67', '删除业务群组', '可以删除特性分组记录信息', 'propertygroup/remove.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('156', '68', '添加超链接', '可以添加超链接信息记录', 'operationUrl/preSave.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('156', '69', '修改超链接', '可以修改超链接信息记录', 'operationUrl/preUpdate.do', '0');
INSERT INTO `system_oper_privilege` VALUES ('156', '70', '删除超链接', '可以删除超链接信息记录', 'operationUrl/delete.do', '0');

-- ----------------------------
-- Table structure for `system_oper_role`
-- ----------------------------
DROP TABLE IF EXISTS `system_oper_role`;
CREATE TABLE `system_oper_role` (
  `oper_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL,
  PRIMARY KEY (`oper_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_oper_role
-- ----------------------------
INSERT INTO `system_oper_role` VALUES ('55', '33');

-- ----------------------------
-- Table structure for `system_parameter`
-- ----------------------------
DROP TABLE IF EXISTS `system_parameter`;
CREATE TABLE `system_parameter` (
  `param_id` int(11) NOT NULL AUTO_INCREMENT,
  `param_name` varchar(50) NOT NULL,
  `param_description` varchar(100) DEFAULT NULL,
  `is_init` varchar(2) DEFAULT NULL,
  PRIMARY KEY (`param_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_parameter
-- ----------------------------
INSERT INTO `system_parameter` VALUES ('1', 'log_name', '日志信息类型', '1');
INSERT INTO `system_parameter` VALUES ('2', 'oper_status', '用户状态', '1');
INSERT INTO `system_parameter` VALUES ('3', 'iptvuser_status', 'iptv用户的类型', '0');
INSERT INTO `system_parameter` VALUES ('4', 'defual_template', '配置当用户请求匹配不到epg页面时的默认模板编号，以及是否开启此默认模板', '0');
INSERT INTO `system_parameter` VALUES ('5', 'report_count_type', '时间报表中按时段、天份和月份的统计方式。', '0');
INSERT INTO `system_parameter` VALUES ('6', 'report_select_type', '单个报表查询类型', '0');
INSERT INTO `system_parameter` VALUES ('7', 'zip_name', 'zip压缩包的名称', '0');
INSERT INTO `system_parameter` VALUES ('8', 'memcached_control', '控制缓存是否启动，目前控制的只是用户开机时读取数据的方式', '0');

-- ----------------------------
-- Table structure for `system_param_item`
-- ----------------------------
DROP TABLE IF EXISTS `system_param_item`;
CREATE TABLE `system_param_item` (
  `item_name` varchar(100) DEFAULT NULL,
  `item_value` varchar(200) DEFAULT NULL,
  `item_id` int(11) NOT NULL AUTO_INCREMENT,
  `param_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`item_id`)
) ENGINE=InnoDB AUTO_INCREMENT=90 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_param_item
-- ----------------------------
INSERT INTO `system_param_item` VALUES ('2', '角色名', '21', '1');
INSERT INTO `system_param_item` VALUES ('0', '正常', '22', '2');
INSERT INTO `system_param_item` VALUES ('1', '停用', '64', '2');
INSERT INTO `system_param_item` VALUES ('0', '普通用户', '65', '3');
INSERT INTO `system_param_item` VALUES ('1', '黑名单', '66', '3');
INSERT INTO `system_param_item` VALUES ('2', '白名单', '67', '3');
INSERT INTO `system_param_item` VALUES ('3', '操作事件', '77', '1');
INSERT INTO `system_param_item` VALUES ('1', '账号名', '78', '1');
INSERT INTO `system_param_item` VALUES ('temp_code', 'dxtemplate001.jsp', '80', '4');
INSERT INTO `system_param_item` VALUES ('on_off', '0', '81', '4');
INSERT INTO `system_param_item` VALUES ('0', '按小时统计', '82', '5');
INSERT INTO `system_param_item` VALUES ('1', '按天数统计', '83', '5');
INSERT INTO `system_param_item` VALUES ('2', '按月份统计', '84', '5');
INSERT INTO `system_param_item` VALUES ('0', '单个用户', '85', '6');
INSERT INTO `system_param_item` VALUES ('1', '单个广告', '86', '6');
INSERT INTO `system_param_item` VALUES ('0', 'kjxx', '87', '7');
INSERT INTO `system_param_item` VALUES ('1', 'frame219', '88', '7');
INSERT INTO `system_param_item` VALUES ('on_off', '0', '89', '8');

-- ----------------------------
-- Table structure for `system_privilege`
-- ----------------------------
DROP TABLE IF EXISTS `system_privilege`;
CREATE TABLE `system_privilege` (
  `privilege_id` int(11) NOT NULL AUTO_INCREMENT,
  `privilege_name` varchar(20) NOT NULL,
  `privilege_description` varchar(200) DEFAULT NULL,
  `pgroup_name` varchar(20) DEFAULT NULL,
  `privilege_uri` varchar(256) NOT NULL,
  PRIMARY KEY (`privilege_id`)
) ENGINE=InnoDB AUTO_INCREMENT=159 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_privilege
-- ----------------------------
INSERT INTO `system_privilege` VALUES ('134', '账号管理', '', '系统管理', 'system/user/list.do');
INSERT INTO `system_privilege` VALUES ('136', '角色管理', null, '系统管理', 'system/role/list.do');
INSERT INTO `system_privilege` VALUES ('138', '日志管理', null, '系统管理', 'system/log/list.do');
INSERT INTO `system_privilege` VALUES ('140', '自定义群组', null, '用户管理', 'iptvgroup/list.do');
INSERT INTO `system_privilege` VALUES ('141', '黑白名单', '用户管理模块中的黑白名单管理权限', '用户管理', 'backwrite/list.do');
INSERT INTO `system_privilege` VALUES ('142', '素材上传', null, '素材管理', 'materialImage/addUI.do');
INSERT INTO `system_privilege` VALUES ('143', '素材列表', null, '素材管理', 'materialImage/list.do');
INSERT INTO `system_privilege` VALUES ('144', '素材分组', null, '素材管理', 'imageGroup/groupUI.do');
INSERT INTO `system_privilege` VALUES ('145', '管理排期', '', '排期管理', 'schedule/checkGroup.do');
INSERT INTO `system_privilege` VALUES ('146', '页面制作', null, '信息管理', 'epginfo/make.do');
INSERT INTO `system_privilege` VALUES ('147', '信息列表', null, '信息管理', 'epginfo/list.do');
INSERT INTO `system_privilege` VALUES ('148', '信息分组', '', '信息管理', 'epggroup/groupManage.do');
INSERT INTO `system_privilege` VALUES ('150', '时间报表', '时间报表', '报表管理', 'hourreport/himgsearch.do');
INSERT INTO `system_privilege` VALUES ('151', '单个报表', '', '报表管理', 'report/adlist.do');
INSERT INTO `system_privilege` VALUES ('152', '用户列表', 'IPTV用户列表信息', '用户管理', 'iptvuser/iptvuserlist.do');
INSERT INTO `system_privilege` VALUES ('153', '区域信息列表', '查看区域列表', '运营管理', 'district/list.do');
INSERT INTO `system_privilege` VALUES ('154', '平台信息列表', '平台信息列表', '运营管理', 'terrace/list.do');
INSERT INTO `system_privilege` VALUES ('155', '业务群组列表', '业务群组列表信息', '运营管理', 'propertygroup/list.do');
INSERT INTO `system_privilege` VALUES ('156', '超链接列表', '超链接列表', '运营管理', 'operationUrl/list.do');
INSERT INTO `system_privilege` VALUES ('157', '模板导出', '可以进行不同平台的模板导出', '排期管理', 'schedule/listAll.do');
INSERT INTO `system_privilege` VALUES ('158', '优先级管理', '优先级管理', '排期管理', 'schedule/conflictManage.do');

-- ----------------------------
-- Table structure for `system_role`
-- ----------------------------
DROP TABLE IF EXISTS `system_role`;
CREATE TABLE `system_role` (
  `role_id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) NOT NULL,
  `role_description` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_role
-- ----------------------------
INSERT INTO `system_role` VALUES ('33', '系统管理员', '拥有系统的所有权限');

-- ----------------------------
-- Table structure for `system_role_privilege`
-- ----------------------------
DROP TABLE IF EXISTS `system_role_privilege`;
CREATE TABLE `system_role_privilege` (
  `role_id` int(11) NOT NULL,
  `op_id` int(11) NOT NULL DEFAULT '0',
  `privilege_id` int(11) NOT NULL,
  PRIMARY KEY (`role_id`,`op_id`,`privilege_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_role_privilege
-- ----------------------------
INSERT INTO `system_role_privilege` VALUES ('33', '0', '134');
INSERT INTO `system_role_privilege` VALUES ('33', '0', '136');
INSERT INTO `system_role_privilege` VALUES ('33', '0', '138');
INSERT INTO `system_role_privilege` VALUES ('33', '0', '140');
INSERT INTO `system_role_privilege` VALUES ('33', '0', '141');
INSERT INTO `system_role_privilege` VALUES ('33', '0', '142');
INSERT INTO `system_role_privilege` VALUES ('33', '0', '143');
INSERT INTO `system_role_privilege` VALUES ('33', '0', '144');
INSERT INTO `system_role_privilege` VALUES ('33', '0', '145');
INSERT INTO `system_role_privilege` VALUES ('33', '0', '146');
INSERT INTO `system_role_privilege` VALUES ('33', '0', '147');
INSERT INTO `system_role_privilege` VALUES ('33', '0', '148');
INSERT INTO `system_role_privilege` VALUES ('33', '0', '150');
INSERT INTO `system_role_privilege` VALUES ('33', '0', '151');
INSERT INTO `system_role_privilege` VALUES ('33', '0', '152');
INSERT INTO `system_role_privilege` VALUES ('33', '0', '153');
INSERT INTO `system_role_privilege` VALUES ('33', '0', '154');
INSERT INTO `system_role_privilege` VALUES ('33', '0', '155');
INSERT INTO `system_role_privilege` VALUES ('33', '0', '156');
INSERT INTO `system_role_privilege` VALUES ('33', '0', '157');
INSERT INTO `system_role_privilege` VALUES ('33', '0', '158');
INSERT INTO `system_role_privilege` VALUES ('33', '1', '140');
INSERT INTO `system_role_privilege` VALUES ('33', '2', '140');
INSERT INTO `system_role_privilege` VALUES ('33', '4', '141');
INSERT INTO `system_role_privilege` VALUES ('33', '5', '141');
INSERT INTO `system_role_privilege` VALUES ('33', '6', '134');
INSERT INTO `system_role_privilege` VALUES ('33', '7', '134');
INSERT INTO `system_role_privilege` VALUES ('33', '8', '134');
INSERT INTO `system_role_privilege` VALUES ('33', '12', '136');
INSERT INTO `system_role_privilege` VALUES ('33', '13', '136');
INSERT INTO `system_role_privilege` VALUES ('33', '14', '136');
INSERT INTO `system_role_privilege` VALUES ('33', '19', '140');
INSERT INTO `system_role_privilege` VALUES ('33', '20', '140');
INSERT INTO `system_role_privilege` VALUES ('33', '21', '140');
INSERT INTO `system_role_privilege` VALUES ('33', '22', '140');
INSERT INTO `system_role_privilege` VALUES ('33', '23', '141');
INSERT INTO `system_role_privilege` VALUES ('33', '24', '143');
INSERT INTO `system_role_privilege` VALUES ('33', '25', '143');
INSERT INTO `system_role_privilege` VALUES ('33', '26', '134');
INSERT INTO `system_role_privilege` VALUES ('33', '27', '143');
INSERT INTO `system_role_privilege` VALUES ('33', '28', '143');
INSERT INTO `system_role_privilege` VALUES ('33', '30', '142');
INSERT INTO `system_role_privilege` VALUES ('33', '31', '142');
INSERT INTO `system_role_privilege` VALUES ('33', '32', '143');
INSERT INTO `system_role_privilege` VALUES ('33', '33', '143');
INSERT INTO `system_role_privilege` VALUES ('33', '34', '143');
INSERT INTO `system_role_privilege` VALUES ('33', '35', '143');
INSERT INTO `system_role_privilege` VALUES ('33', '36', '143');
INSERT INTO `system_role_privilege` VALUES ('33', '37', '143');
INSERT INTO `system_role_privilege` VALUES ('33', '38', '145');
INSERT INTO `system_role_privilege` VALUES ('33', '39', '145');
INSERT INTO `system_role_privilege` VALUES ('33', '40', '145');
INSERT INTO `system_role_privilege` VALUES ('33', '41', '144');
INSERT INTO `system_role_privilege` VALUES ('33', '42', '144');
INSERT INTO `system_role_privilege` VALUES ('33', '43', '144');
INSERT INTO `system_role_privilege` VALUES ('33', '44', '144');
INSERT INTO `system_role_privilege` VALUES ('33', '45', '144');
INSERT INTO `system_role_privilege` VALUES ('33', '46', '144');
INSERT INTO `system_role_privilege` VALUES ('33', '47', '147');
INSERT INTO `system_role_privilege` VALUES ('33', '48', '147');
INSERT INTO `system_role_privilege` VALUES ('33', '49', '147');
INSERT INTO `system_role_privilege` VALUES ('33', '50', '147');
INSERT INTO `system_role_privilege` VALUES ('33', '51', '147');
INSERT INTO `system_role_privilege` VALUES ('33', '52', '148');
INSERT INTO `system_role_privilege` VALUES ('33', '53', '148');
INSERT INTO `system_role_privilege` VALUES ('33', '54', '148');
INSERT INTO `system_role_privilege` VALUES ('33', '55', '152');
INSERT INTO `system_role_privilege` VALUES ('33', '56', '152');
INSERT INTO `system_role_privilege` VALUES ('33', '57', '152');
INSERT INTO `system_role_privilege` VALUES ('33', '58', '147');
INSERT INTO `system_role_privilege` VALUES ('33', '59', '153');
INSERT INTO `system_role_privilege` VALUES ('33', '60', '153');
INSERT INTO `system_role_privilege` VALUES ('33', '61', '153');
INSERT INTO `system_role_privilege` VALUES ('33', '62', '154');
INSERT INTO `system_role_privilege` VALUES ('33', '63', '154');
INSERT INTO `system_role_privilege` VALUES ('33', '64', '154');
INSERT INTO `system_role_privilege` VALUES ('33', '65', '155');
INSERT INTO `system_role_privilege` VALUES ('33', '66', '155');
INSERT INTO `system_role_privilege` VALUES ('33', '67', '155');
INSERT INTO `system_role_privilege` VALUES ('33', '68', '156');
INSERT INTO `system_role_privilege` VALUES ('33', '69', '156');
INSERT INTO `system_role_privilege` VALUES ('33', '70', '156');

-- ----------------------------
-- Table structure for `system_sequence`
-- ----------------------------
DROP TABLE IF EXISTS `system_sequence`;
CREATE TABLE `system_sequence` (
  `seq_name` varchar(50) NOT NULL,
  `current_value` bigint(20) DEFAULT NULL,
  `increment` int(5) DEFAULT NULL,
  PRIMARY KEY (`seq_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_sequence
-- ----------------------------
INSERT INTO `system_sequence` VALUES ('EPGINFO_GET_EPGID', '1', '1');
INSERT INTO `system_sequence` VALUES ('IPTVGROUP_GET_GROUPID', '1', '1');
INSERT INTO `system_sequence` VALUES ('SYSTEM_OPER_LOG', '1', '1');

-- ----------------------------
-- Table structure for `system_status`
-- ----------------------------
DROP TABLE IF EXISTS `system_status`;
CREATE TABLE `system_status` (
  `status_id` int(3) NOT NULL AUTO_INCREMENT,
  `status_name` varchar(20) NOT NULL,
  `status_level` int(2) NOT NULL,
  PRIMARY KEY (`status_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_status
-- ----------------------------
INSERT INTO `system_status` VALUES ('1', '待提交', '1');
INSERT INTO `system_status` VALUES ('2', '待审核', '1');
INSERT INTO `system_status` VALUES ('3', '审核通过', '1');
INSERT INTO `system_status` VALUES ('4', '审核未通过', '1');
INSERT INTO `system_status` VALUES ('5', '已占用', '1');
INSERT INTO `system_status` VALUES ('6', '已下线', '1');
INSERT INTO `system_status` VALUES ('7', '待上线', '1');
INSERT INTO `system_status` VALUES ('8', '已上线', '1');
INSERT INTO `system_status` VALUES ('9', '二级审核通过', '2');
INSERT INTO `system_status` VALUES ('10', '三级审核通过', '3');
INSERT INTO `system_status` VALUES ('11', 'end', '1');

-- ----------------------------
-- Table structure for `system_status_item`
-- ----------------------------
DROP TABLE IF EXISTS `system_status_item`;
CREATE TABLE `system_status_item` (
  `status_id` int(3) NOT NULL,
  `parent_statusid` int(3) NOT NULL,
  PRIMARY KEY (`status_id`,`parent_statusid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of system_status_item
-- ----------------------------
INSERT INTO `system_status_item` VALUES ('1', '2');
INSERT INTO `system_status_item` VALUES ('1', '3');
INSERT INTO `system_status_item` VALUES ('2', '1');
INSERT INTO `system_status_item` VALUES ('3', '2');
INSERT INTO `system_status_item` VALUES ('3', '9');
INSERT INTO `system_status_item` VALUES ('3', '10');
INSERT INTO `system_status_item` VALUES ('4', '2');
INSERT INTO `system_status_item` VALUES ('4', '5');
INSERT INTO `system_status_item` VALUES ('5', '4');
INSERT INTO `system_status_item` VALUES ('5', '9');
INSERT INTO `system_status_item` VALUES ('5', '10');
INSERT INTO `system_status_item` VALUES ('6', '8');
INSERT INTO `system_status_item` VALUES ('8', '7');
INSERT INTO `system_status_item` VALUES ('9', '4');
INSERT INTO `system_status_item` VALUES ('10', '9');
INSERT INTO `system_status_item` VALUES ('11', '6');

-- ----------------------------
-- Function structure for `currval`
-- ----------------------------
DROP FUNCTION IF EXISTS `currval`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `currval`(seq_id VARCHAR(50)) RETURNS int(11)
    READS SQL DATA
    DETERMINISTIC
BEGIN  
  
DECLARE VALUE INTEGER;  
  
SET VALUE = 0;  
  
SELECT current_value INTO VALUE FROM system_sequence WHERE seq_name = seq_id;  
  
RETURN VALUE;  
  
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `epgGetChildLst`
-- ----------------------------
DROP FUNCTION IF EXISTS `epgGetChildLst`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `epgGetChildLst`(rootId INT) RETURNS varchar(1000) CHARSET utf8
BEGIN 
       DECLARE sTemp VARCHAR(1000); 
       DECLARE sTempChd VARCHAR(1000); 
     
       SET sTemp = '$'; 
       SET sTempChd =cast(rootId as CHAR); 
     
       WHILE sTempChd is not null DO 
         SET sTemp = concat(sTemp,',',sTempChd); 
         SELECT group_concat(egroup_id) INTO sTempChd FROM epg_group where FIND_IN_SET(eparent_id,sTempChd)>0; 
       END WHILE; 
       RETURN sTemp; 
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `getBusinessIdByCode`
-- ----------------------------
DROP FUNCTION IF EXISTS `getBusinessIdByCode`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getBusinessIdByCode`(f_string VARCHAR(15000)) RETURNS varchar(15000) CHARSET utf8
BEGIN

   /* 判断字符串包含,的第一个位置*/
   DECLARE THE_CNT INT(15) DEFAULT 1;

   /* 业务分组序列号 */
   declare businessCode varchar(20) default '';
   
   /* 业务分组编号字符串 */
   DECLARE result varchar(15000) DEFAULT null;

   /* 业务分组编号 */
   DECLARE businessId varchar(50) DEFAULT '';

   /* 字符串包含,的第一个位置*/
   set THE_CNT =  LOCATE(',',f_string);

   /* 判断字符串包含,的第一个位置是否存在*/
   while (THE_CNT >= 0) do

   /* ,位置不存在的场合*/
   if THE_CNT = 0 then
      
      /* 业务分组序列号的设置*/
      set businessCode = f_string;

   else

     /* 字符串中获得业务分组序列号 */
     set businessCode = SUBSTRING_INDEX(SUBSTRING_INDEX(f_string, ',', 1), ',', -1);

   end if ;

    /* 根据业务分组序列号获得业务分组编号 */
    select (select group_id from operation_property_group where property_code = businessCode) into businessId;
      
      /* 返回业务分组序列号的字符串为空的场合*/
      if result is null then

         /* 根据业务分组序列号没有查询到业务分组编号的场合*/
         if businessId is null then

            /* 设置业务分组编号为空*/
            set businessId = '  ';

         end if;
            
            /* 业务分组编号追加到字符串*/
            set result = businessId;

      else

         /* 根据业务分组序列号没有查询到业务分组编号的场合*/
         if businessId is null then

            /* 设置业务分组编号为空*/
            set businessId = '　 ';

         end if;

            /* 业务分组编号追加到字符串*/
            set result = CONCAT(result,',',businessId);

      end if;
    
    /* ,位置不存在的场合*/
    if THE_CNT = 0 then
     
     /* 返回结果集*/
     return result;

    end if;

    /* 截取传入的字符串*/
    set f_string = right(f_string,length(f_string) - THE_CNT);

    /* 字符串包含,的第一个位置*/
    set THE_CNT =  LOCATE(',',f_string);

   /* 结束遍历*/
   end while;

   /* 返回结果集*/
   return result;

   END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `getDistrictIdByCode`
-- ----------------------------
DROP FUNCTION IF EXISTS `getDistrictIdByCode`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `getDistrictIdByCode`(f_string VARCHAR(15000)) RETURNS varchar(15000) CHARSET utf8
BEGIN

   /* 判断字符串包含,的第一个位置*/
   DECLARE THE_CNT INT(15) DEFAULT 1;

   /* 区域序列号 */
   declare districtCode varchar(20) default '';
   
   /* 返回的区域序列号 */
   DECLARE result varchar(15000) DEFAULT null;

   /* 区域编号 */
   DECLARE districtId varchar(50) DEFAULT '';

   /* 字符串包含,的第一个位置*/
   set THE_CNT =  LOCATE(',',f_string);

   /* 判断字符串包含,的第一个位置是否存在*/
   while (THE_CNT >= 0) do

   /* ,位置不存在的场合*/
   if THE_CNT = 0 then
      
      /* 区域序列号的设置*/
      set districtCode = f_string;

   else

     /* 字符串中获得区域序列号 */
     set districtCode = SUBSTRING_INDEX(SUBSTRING_INDEX(f_string, ',', 1), ',', -1);

   end if ;

    /* 根据区域序列号获得区域编号 */
    select (select district_id from operation_district where district_code = districtCode) into districtId;
      
      /* 返回区域编号的字符串为空的场合*/
      if result is null then

         /* 根据区域序列号没有查询到班级名称的场合*/
         if districtId is null then

            /* 设置区域编号为空 */
            set districtId = '  ';

         end if;
            
            /* 区域编号追加到字符串*/
            set result = districtId;

      else

         /* 根据区域序列号没有查询到区域编号的场合*/
         if districtId is null then

            /* 设置区域编号为空*/
            set districtId = '　 ';

         end if;

            /* 区域编号追加到字符串*/
            set result = CONCAT(result,',',districtId);

      end if;
    
    /* ,位置不存在的场合*/
    if THE_CNT = 0 then
     
     /* 返回结果集*/
     return result;

    end if;

    /* 截取传入的字符串*/
    set f_string = right(f_string,length(f_string) - THE_CNT);

    /* 字符串包含,的第一个位置*/
    set THE_CNT =  LOCATE(',',f_string);

   /* 结束遍历*/
   end while;

   /* 返回结果集*/
   return result;

   END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `ImgGetChildLst`
-- ----------------------------
DROP FUNCTION IF EXISTS `ImgGetChildLst`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `ImgGetChildLst`(rootId INT) RETURNS varchar(1000) CHARSET utf8
BEGIN 
       DECLARE sTemp VARCHAR(1000); 
       DECLARE sTempChd VARCHAR(1000); 
     
       SET sTemp = '$'; 
       SET sTempChd =cast(rootId as CHAR); 
     
       WHILE sTempChd is not null DO 
         SET sTemp = concat(sTemp,',',sTempChd); 
         SELECT group_concat(mig_id) INTO sTempChd FROM material_igroup where FIND_IN_SET(miparent_id,sTempChd)>0; 
       END WHILE; 
       RETURN sTemp; 
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `nextval`
-- ----------------------------
DROP FUNCTION IF EXISTS `nextval`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `nextval`(seq_id VARCHAR(50)) RETURNS int(11)
    DETERMINISTIC
BEGIN  
  
UPDATE system_sequence SET current_value = current_value + increment WHERE seq_name = seq_id;  
  
RETURN currval(seq_id);  
  
END
;;
DELIMITER ;

-- ----------------------------
-- Function structure for `videoGetChildLst`
-- ----------------------------
DROP FUNCTION IF EXISTS `videoGetChildLst`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` FUNCTION `videoGetChildLst`(rootId INT) RETURNS varchar(1000) CHARSET utf8
BEGIN 
       DECLARE sTemp VARCHAR(1000); 
       DECLARE sTempChd VARCHAR(1000); 
     
       SET sTemp = '$'; 
       SET sTempChd =cast(rootId as CHAR); 
     
       WHILE sTempChd is not null DO 
         SET sTemp = concat(sTemp,',',sTempChd); 
         SELECT group_concat(mvg_id) INTO sTempChd FROM material_vgroup where FIND_IN_SET(mvparent_id,sTempChd)>0; 
       END WHILE; 
       RETURN sTemp; 
END
;;
DELIMITER ;
