/*
Navicat MySQL Data Transfer

Source Server         : study
Source Server Version : 50520
Source Host           : localhost:3306
Source Database       : springboot

Target Server Type    : MYSQL
Target Server Version : 50520
File Encoding         : 65001

Date: 2018-09-24 22:15:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `hospital`
-- ----------------------------
DROP TABLE IF EXISTS `haiNanTreament`;
CREATE TABLE `haiNanTreament` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `treamentNo` varchar(50) DEFAULT NULL,
  `hospitalLevel` char (10) DEFAULT NULL,
  `treamentName` varchar(200) DEFAULT NULL,
  `unit` char (10) DEFAULT NULL,
  `standardPrice` DECIMAL (10,2) DEFAULT NULL,
  `selfPayRatio` DECIMAL (10,2) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=286 DEFAULT CHARSET=utf8 comment="海南诊疗";

