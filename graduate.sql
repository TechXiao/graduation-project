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
INSERT INTO `customer` VALUES ('2', '肖进兵的爸爸', '936294813@qq.com', '17363831766', '长沙市', '雨花区', '28.142233', '113.042422');
INSERT INTO `customer` VALUES ('5', '管理员', 'jinbing.xiao@foxmail.com', '18274831673', '衡阳市', '南华大学-东门', '26.90489', '112.59733');
INSERT INTO `customer` VALUES ('22', '肖进兵', '960254430@qq.com', '18274831673', '深圳市', '深圳市', '22.548457', '114.064552');
INSERT INTO `customer` VALUES ('23', '肖进兵', '1124252059@qq.com', '18274831673', '长沙市', '长沙市', '28.234889', '112.945473');
INSERT INTO `customer` VALUES ('24', '123123', '821392377@qq.com', '15136358628', '北京市', '百度大厦-A座', '40.056056', '116.308102');
INSERT INTO `customer` VALUES ('25', 'kjx', '2813392731@qq.com', '17775605389', '深圳市', '深圳市', '22.548457', '114.064552');

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
INSERT INTO `food` VALUES ('2', 'b6294e7630954115a72ae044e9a09687', '宫保鸡丁', '原材料：鸡肉，蒜', '13.88', '本店招牌', '0', 'foodPictureFile_9ae6fd0b0fd842349fd552ce1b28e5a4_宫保鸡丁.jpg', '0', '0.00', '1');
INSERT INTO `food` VALUES ('3', '34861969ac8f4896a920fb3453f18b22', '皮蛋茄子', '', '8.00', '本店热销', '1', 'foodPictureFile_f7fe8de65a99484ea7b8a6c89a9a2fde_timg (2).jpg', '0', '0.00', '1');
INSERT INTO `food` VALUES ('4', '88ecf07a03264a17b34c8e71b9c0d1de', '皮蛋茄子', '', '8.00', '本店热销', '0', 'foodPictureFile_7caaaebeeac9497f989a5bdf1fcecccf_timg (4).jpg', '0', '0.00', '3');
INSERT INTO `food` VALUES ('5', 'afebd1f4d7ba4b73bb415a19d3819e74', '老胡烧饼', '', '3.00', '本店招牌', '0', 'foodPictureFile_d2b2f0f0fc6c4ec38f45872a8e10dd89_timg (5).jpg', '0', '0.00', '4');
INSERT INTO `food` VALUES ('6', 'bcd3209b84044f928a0be8312fab98bb', '宫保鸡丁', '原材料：鸡肉、蒜', '13.00', '本店热销', '0', 'foodPictureFile_8e2970ff3d184f0db65c774b45260a7a_宫保鸡丁.jpg', '0', '0.00', '5');
INSERT INTO `food` VALUES ('7', '2f64c5c123c5405aae2f5d01db75fccb', '辣椒炒肉', '原材料：辣椒肉', '12.00', '本店招牌', '0', 'foodPictureFile_fdebd173668541889277583964553adf_timg1212.jpg', '0', '0.00', '5');
INSERT INTO `food` VALUES ('8', 'dbd7bcd1acea4c2fb23bdcb15af5072c', '皮蛋茄子', '', '8.00', '本店热销', '0', 'foodPictureFile_9cc2c0c9be8d437285cc9005440b4329_timg (4).jpg', '0', '0.00', '5');
INSERT INTO `food` VALUES ('9', '905727793f0444d1b69ab8f2464cb261', '红烧肉', '', '15.00', '本店招牌', '0', 'foodPictureFile_8f8b1874cd034af7bdcf5782f7a17531_红烧肉.jpg', '0', '0.00', '5');
INSERT INTO `food` VALUES ('10', '9eed0a9acf934f348abd9d37ef82c235', '鱼香肉丝', '原材料：肉丝、木耳', '12.88', '本店招牌', '0', 'foodPictureFile_2325e6ee3e69457791ba4d3112484f99_timg (3).jpg', '0', '0.00', '5');
INSERT INTO `food` VALUES ('48', '665570cf9e60407caa7e077d382bf67a', '红烧肉', '', '15.00', '本店招牌', '0', 'foodPictureFile_fdd3863bb341447899e77c72f4e0c357_红烧肉.jpg', '0', '0.00', '19');
INSERT INTO `food` VALUES ('50', 'f6574b493ba847539b8c1d8ee58847df', '皮蛋茄子', '', '8.80', '本店热销', '0', 'foodPictureFile_2adc1500c480490fa198db4c807c2eec_timg (4).jpg', '0', '0.00', '19');
INSERT INTO `food` VALUES ('51', 'a3b0c00fb0624697a2c1576ef42c12e8', '辣椒炒肉', '原材料：辣椒、肉', '12.88', '本店招牌', '1', 'foodPictureFile_a3e3090e999345ee87b086accb89dbae_timg1212.jpg', '0', '0.00', '1');
INSERT INTO `food` VALUES ('52', '02413d9f9ce140cfa5211c147144a18a', '红烧肉', '', '15.00', '本店招牌', '0', 'foodPictureFile_fe22055c85464a48ae6582feb0dbc2ce_红烧肉.jpg', '0', '0.00', '1');
INSERT INTO `food` VALUES ('53', 'fa663d7fb912446798e35b24d6b545bd', '辣椒炒肉', '原材料：辣椒、肉', '12.88', '本店招牌', '0', 'foodPictureFile_e7b2128179fe4f5aaea89911390098b7_timg1212.jpg', '0', '0.00', '19');
INSERT INTO `food` VALUES ('54', '9da146f637cf4c10a0bddf47ac13e76e', '鱼香肉丝', '', '13.90', '本店招牌', '0', 'foodPictureFile_37cfb50a85ae462caf6a5fa6ab94228a_timg (3).jpg', '0', '0.00', '19');

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
INSERT INTO `orders` VALUES ('48', '45676d92cb784a17b27f5143de3b4c37', '测试餐厅', '15575522523', '18274831673', 'wechatPicture_0488166fd8c54f47b1b9b3f5c8bcfd88_mm_facetoface_collect_qrcode_1588629229088.png', 'zhifubaoPicture_b048eaf3fb874477a88a8437949b7922_1588626186.jpg', '336276', '肖进兵', '红烧肉', '15.00', '1', '2020-05-22 23:29:23', '3', '19', '22', null, null);
INSERT INTO `orders` VALUES ('49', '45676d92cb784a17b27f5143de3b4c37', '测试餐厅', '15575522523', '18274831673', 'wechatPicture_0488166fd8c54f47b1b9b3f5c8bcfd88_mm_facetoface_collect_qrcode_1588629229088.png', 'zhifubaoPicture_b048eaf3fb874477a88a8437949b7922_1588626186.jpg', '336276', '肖进兵', '辣椒炒肉', '12.88', '1', '2020-05-22 23:29:23', '3', '19', '22', null, null);
INSERT INTO `orders` VALUES ('50', '45676d92cb784a17b27f5143de3b4c37', '测试餐厅', '15575522523', '18274831673', 'wechatPicture_0488166fd8c54f47b1b9b3f5c8bcfd88_mm_facetoface_collect_qrcode_1588629229088.png', 'zhifubaoPicture_b048eaf3fb874477a88a8437949b7922_1588626186.jpg', '336276', '肖进兵', '皮蛋茄子', '8.80', '1', '2020-05-22 23:29:23', '3', '19', '22', null, null);
INSERT INTO `orders` VALUES ('51', 'd77df519c6684eba975feaa79319df16', '测试餐厅', '15575522523', '18274831673', 'wechatPicture_0488166fd8c54f47b1b9b3f5c8bcfd88_mm_facetoface_collect_qrcode_1588629229088.png', 'zhifubaoPicture_b048eaf3fb874477a88a8437949b7922_1588626186.jpg', '093803', '肖进兵', '红烧肉', '15.00', '1', '2020-05-22 23:29:36', '3', '19', '22', null, null);
INSERT INTO `orders` VALUES ('53', '87b8481951734f2e8ec77b9398117240', '测试', '18274831673', '17775605389', 'wechatPicture_89d5abde37b24cd998a2d6daa40a83a4_mm_facetoface_collect_qrcode_1588629229088.png', 'zhifubaoPicture_9f643da632b6442bb86769277fc16f72_1588626186.jpg', '638835', 'kjx', '宫保鸡丁', '13.88', '1', '2020-09-21 11:11:39', '3', '1', '25', null, null);
INSERT INTO `orders` VALUES ('54', '87b8481951734f2e8ec77b9398117240', '测试', '18274831673', '17775605389', 'wechatPicture_89d5abde37b24cd998a2d6daa40a83a4_mm_facetoface_collect_qrcode_1588629229088.png', 'zhifubaoPicture_9f643da632b6442bb86769277fc16f72_1588626186.jpg', '638835', 'kjx', '辣椒炒肉', '12.88', '1', '2020-09-21 11:11:39', '3', '1', '25', null, null);
INSERT INTO `orders` VALUES ('55', '87b8481951734f2e8ec77b9398117240', '测试', '18274831673', '17775605389', 'wechatPicture_89d5abde37b24cd998a2d6daa40a83a4_mm_facetoface_collect_qrcode_1588629229088.png', 'zhifubaoPicture_9f643da632b6442bb86769277fc16f72_1588626186.jpg', '638835', 'kjx', '红烧肉', '15.00', '1', '2020-09-21 11:11:39', '3', '1', '25', null, null);
INSERT INTO `orders` VALUES ('56', '87b8481951734f2e8ec77b9398117240', '测试', '18274831673', '17775605389', 'wechatPicture_89d5abde37b24cd998a2d6daa40a83a4_mm_facetoface_collect_qrcode_1588629229088.png', 'zhifubaoPicture_9f643da632b6442bb86769277fc16f72_1588626186.jpg', '638835', 'kjx', '皮蛋茄子', '8.00', '1', '2020-09-21 11:11:39', '3', '1', '25', null, null);
INSERT INTO `orders` VALUES ('61', '8e76365b296b4c70a373e0b9e323d193', '测试', '18274831673', '18274831673', 'wechatPicture_89d5abde37b24cd998a2d6daa40a83a4_mm_facetoface_collect_qrcode_1588629229088.png', 'zhifubaoPicture_9f643da632b6442bb86769277fc16f72_1588626186.jpg', '399736', '肖进兵', '红烧肉', '15.00', '1', '2020-09-24 19:10:56', '3', '1', '22', null, null);

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
INSERT INTO `store` VALUES ('1', '960254430@qq.com', '18274831673', '测试', '0', '0', '0', 'storePicture_e7ade190b8b5419782a6027167b728cd_timg.jpg', 'licensePicture_7832153921e6441bb9a46c071e8b02a8_timg (1).jpg', 'identityPicture_2bfb6fd99ab2456a9ce038aa31ee8704_IMG_20200205_160621_mh1589383617770.jpg', 'wechatPicture_89d5abde37b24cd998a2d6daa40a83a4_mm_facetoface_collect_qrcode_1588629229088.png', 'zhifubaoPicture_9f643da632b6442bb86769277fc16f72_1588626186.jpg', '深圳市', '深圳市深大地铁站C口', '22.54322694898077', '113.95034049516273', '欢迎大家来到中国特色社会主义先行示范区深圳市。', '1', '0', '0.00');
INSERT INTO `store` VALUES ('2', '936294813@qq.com', '11111111111', '小夫子餐厅', '10', '20', '1', 'storePicture_9d54ab0d3cca4fdeb94ccba3fb88a609_20120411120916939.jpg', 'licensePicture_a4bec21866ee43308e28886ebecd507b_timg (1).jpg', 'identityPicture_c38548ba01264337801ba13006370812_IMG_20200205_160621_mh1590041544328.jpg', 'wechatPicture_48b5c92f262b469599dbe8907901012b_mm_facetoface_collect_qrcode_1588629229088.png', 'zhifubaoPicture_8739d9186d2e4ba4b3e42e6bf965557d_1588626186.jpg', '衡阳市', '南华大学东门', '26.904922208585837', '112.59745778071908', '欢迎大家到店品尝', '1', '0', '0.00');
INSERT INTO `store` VALUES ('3', '894318228@qq.com', '22222222222', '川湘菜馆', '20', '20', '0', 'storePicture_07bda0ccf4ec481a9ef8381d7e56a064_c97ecdd7ccd9af9c3ade3b39e0052484447955.jpg', 'licensePicture_2b2ecc6e6aee47fca2587f9ffb0a6741_IMG_20200205_160621_mh1590041501917.jpg', 'identityPicture_81ad76db60c64100955869b517b396a5_IMG_20200205_160621_mh1590041528534.jpg', 'wechatPicture_98ee3c96e6e64103a807725e8be9725a_Screenshot_20200507_194722_com.tencent.mm.png', 'zhifubaoPicture_617362b17207495b9d19166bcd73a6c6_1588843707120.jpg', '衡阳市', '南华大学出西门右转100米', '26.9042089795138', '112.58488067792146', '', '1', '0', '0.00');
INSERT INTO `store` VALUES ('4', '371605912@qq.com', '23333333333', '小辣椒湘菜馆', '15', '30', '0', 'storePicture_d8bd5644fa94456093d6f0766b016431_0894d8e1bb1945cfbaf3eb2cbbae303b377804.jpg', 'licensePicture_c01177c6a5e847eea6858758b8805e6c_timg (1).jpg', 'identityPicture_05da1bf07d8f41e5a41b6fc293d60f6a_IMG_20200205_160621_mh1590041501917.jpg', 'wechatPicture_db32e06553eb4ed3b64c01ab03460335_mm_facetoface_collect_qrcode_1588629229088.png', 'zhifubaoPicture_f589f0f0ffa6415793c2c543740cc014_1588626186.jpg', '衡阳市', '南华大学红湘校区北门外', '26.907761253323795', '112.59692328894081', '', '1', '0', '0.00');
INSERT INTO `store` VALUES ('5', '1056118357@qq.com', '66666999999', '阿城饭店', '15', '30', '0', 'storePicture_0bd7768164a5459b953bee5ae3c9bed2_timg (2).jpg', 'licensePicture_6b4cc63e42d44f66b0976c02a7ab6ddf_timg (1).jpg', 'identityPicture_339d447b00f8498698a8e5af27543ed6_IMG_20200205_160621_mh1589383707091.jpg', 'wechatPicture_f8c9fa2ba3ad4727b99a76dcff9325c4_mm_facetoface_collect_qrcode_1588629229088.png', 'zhifubaoPicture_d7aa6137405e473393eab89b1e7d27d6_1588626186.jpg', '衡阳市', '南华大学后街', '26.904931032688495', '112.58768874645321', '致远园后街', '1', '0', '0.00');
INSERT INTO `store` VALUES ('19', '1124252059@qq.com', '15575522523', '测试餐厅', '15', '20', '0', 'storePicture_769e9dfa8ac44c02887a9b2a81af8d41_timg (2).jpg', 'licensePicture_8fa4caa68c0c43a3baef6c4b08849aee_timg (1).jpg', 'identityPicture_39cc7881b0f041e6a6a6821561b7acae_IMG_20200205_160621_mh1589383617770.jpg', 'wechatPicture_a588e27ebafb4af487dfbb415c72e1ab_mm_facetoface_collect_qrcode_1588629229088.png', 'zhifubaoPicture_61b42e0a9c8a4444aedf1b329f8a495e_1588626186.jpg', '上海市', '上海市地铁2号线唐镇站1号口附近', '31.2207042403549', '121.66347005121095', '欢迎大家到店就餐。请诚信下单，本店将会对在此平台产生的订单逐一核对，实际未付款订单一律不理。', '1', '0', '0.00');
INSERT INTO `store` VALUES ('20', '2427970264@qq.com', '15575522523', '熊大的餐厅', '20', '20', '1', 'storePicture_37cad83f9d8c43598eb9e434e7e34a06_timg.jpg', 'licensePicture_069dab8da4a44c2db9780c9b52cc747d_timg (1).jpg', 'identityPicture_66b4f39f8f6b425c987f14038f50b59a_IMG_20200205_160621_mh1589383617770.jpg', 'wechatPicture_0c5e96c0349046ec947136f8b277485f_mm_facetoface_collect_qrcode_1588629229088.png', 'zhifubaoPicture_c0b3288387b04065b06a4d6ca15ec677_1588626186.jpg', '长沙市', '长沙市橘子洲地铁站2号出口', '28.200935691245608', '112.96945266135259', '此店休息中，可注册餐厅做测试。', '1', '0', '0.00');
