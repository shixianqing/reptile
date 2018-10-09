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
DROP TABLE IF EXISTS `chongqingTreament`;
CREATE TABLE `chongqingTreament` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `seq_no` varchar(50) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `level` varchar(10) DEFAULT NULL,
  `current_flag` varchar(50) DEFAULT NULL,
  `project_include` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=286 DEFAULT CHARSET=utf8;

