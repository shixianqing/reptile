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
DROP TABLE IF EXISTS `hospital`;
CREATE TABLE `hospital` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hospitalUrl` varchar(200) DEFAULT NULL,
  `hospitalName` varchar(100) DEFAULT NULL,
  `telephoneNo` varchar(200) DEFAULT NULL,
  `hospitalFax` varchar(50) DEFAULT NULL,
  `hospitalAddr` varchar(200) DEFAULT NULL,
  `hospitalPost` varchar(10) DEFAULT NULL,
  `hospitalLocation` varchar(10) DEFAULT NULL,
  `hospitalLeaderName` varchar(20) DEFAULT NULL,
  `buildTime` varchar(20) DEFAULT NULL,
  `hospitalType` varchar(10) DEFAULT NULL,
  `hospitalLevel` char(10) DEFAULT NULL,
  `isMedicalInsurance` char(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=286 DEFAULT CHARSET=utf8;

