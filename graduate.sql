/*
Navicat MySQL Data Transfer

Source Server         : aliyun
Source Server Version : 50648
Source Host           : localhost:3306
Source Database       : graduate

Target Server Type    : MYSQL
Target Server Version : 50648
File Encoding         : 65001

Date: 2020-10-03 14:01:06
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for customer
-- ----------------------------
DROP TABLE IF EXISTS `customer`;
CREATE TABLE `customer` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(25) NOT NULL,
  `email` varchar(50) NOT NULL,
  `phone_number` varchar(11) NOT NULL,
  `city` varchar(20) DEFAULT NULL,
  `address` varchar(50) DEFAULT NULL,
  `position_lat` varchar(50) DEFAULT NULL,
  `position_lng` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of customer
-- ----------------------------

-- ----------------------------
-- Table structure for food
-- ----------------------------
DROP TABLE IF EXISTS `food`;
CREATE TABLE `food` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(50) NOT NULL,
  `food_name` varchar(20) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `price` double(5,2) NOT NULL,
  `food_group` varchar(10) NOT NULL,
  `state` int(11) NOT NULL,
  `picture_name` varchar(100) NOT NULL,
  `month_sales` int(11) DEFAULT '0',
  `score` double(3,2) DEFAULT '0.00',
  `store_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `food_store` (`store_id`),
  CONSTRAINT `food_store` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of food
-- ----------------------------

-- ----------------------------
-- Table structure for orders
-- ----------------------------
DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uuid` varchar(50) NOT NULL,
  `store_name` varchar(50) NOT NULL,
  `store_phone_number` varchar(11) NOT NULL,
  `customer_phone_number` varchar(11) NOT NULL,
  `wechat_picture` varchar(150) NOT NULL,
  `zhifubao_picture` varchar(150) NOT NULL,
  `payment_code` varchar(6) NOT NULL,
  `customer_name` varchar(50) NOT NULL,
  `food_name` varchar(100) NOT NULL,
  `unit_price` double(5,2) NOT NULL,
  `quantity` int(11) NOT NULL,
  `time` varchar(50) NOT NULL,
  `state` int(11) NOT NULL,
  `store_id` int(11) NOT NULL,
  `customer_id` int(11) NOT NULL,
  `grade` int(11) DEFAULT NULL,
  `evaluate` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `orders_store` (`store_id`),
  KEY `orders_customer` (`customer_id`),
  CONSTRAINT `orders_customer` FOREIGN KEY (`customer_id`) REFERENCES `customer` (`id`),
  CONSTRAINT `orders_store` FOREIGN KEY (`store_id`) REFERENCES `store` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of orders
-- ----------------------------

-- ----------------------------
-- Table structure for store
-- ----------------------------
DROP TABLE IF EXISTS `store`;
CREATE TABLE `store` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `phone_number` varchar(11) NOT NULL,
  `store_name` varchar(25) NOT NULL,
  `capacity` int(11) NOT NULL,
  `equipment_eat_time` int(11) NOT NULL,
  `state` int(11) NOT NULL,
  `store_picture` varchar(150) NOT NULL,
  `license_picture` varchar(150) NOT NULL,
  `identity_picture` varchar(150) NOT NULL,
  `wechat_picture` varchar(150) NOT NULL,
  `zhifubao_picture` varchar(150) NOT NULL,
  `city` varchar(10) NOT NULL,
  `address` varchar(25) NOT NULL,
  `position_lat` varchar(50) NOT NULL,
  `position_lng` varchar(50) NOT NULL,
  `notice` varchar(500) DEFAULT NULL,
  `license` varchar(1) DEFAULT '0',
  `month_sales` int(11) DEFAULT '0',
  `score` double(3,2) DEFAULT '0.00',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of store
-- ----------------------------
