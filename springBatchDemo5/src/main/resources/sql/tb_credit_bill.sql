/*
Navicat MySQL Data Transfer

Source Server         : root@mysql_127.0.0.1
Source Server Version : 50401
Source Host           : localhost:3306
Source Database       : test

Target Server Type    : MYSQL
Target Server Version : 50401
File Encoding         : 65001

Date: 2017-09-21 12:36:15
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for tb_credit_bill
-- ----------------------------
DROP TABLE IF EXISTS `tb_credit_bill`;
CREATE TABLE `tb_credit_bill` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `accountID` varchar(256) DEFAULT NULL,
  `name` varchar(256) DEFAULT NULL,
  `account` decimal(20,6) DEFAULT NULL,
  `date` varchar(56) DEFAULT NULL,
  `address` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
