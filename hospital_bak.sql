/*
Navicat MySQL Data Transfer

Source Server         : claim
Source Server Version : 50723
Source Host           : 116.62.163.101:3306
Source Database       : claim_dev

Target Server Type    : MYSQL
Target Server Version : 50723
File Encoding         : 65001

Date: 2018-09-27 18:09:46
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for hospital_bak
-- ----------------------------
DROP TABLE IF EXISTS `hospital_bak`;
CREATE TABLE `hospital_bak` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `hospitalName` varchar(100) NOT NULL,
  `hospitalType` varchar(10) DEFAULT NULL,
  `hospitalLevel` char(10) DEFAULT NULL,
  `provinceId` char(10) DEFAULT NULL,
  PRIMARY KEY (`id`,`hospitalName`)
) ENGINE=InnoDB AUTO_INCREMENT=30760 DEFAULT CHARSET=utf8;
