/*
Navicat MySQL Data Transfer

Source Server         : 172.30.10.157_3306
Source Server Version : 50718
Source Host           : 172.30.10.157:3306
Source Database       : scsit

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2017-09-01 13:40:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for supplier_settled
-- ----------------------------
DROP TABLE IF EXISTS `supplier_settled`;
CREATE TABLE `supplier_settled` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `purchase_receipt_no` varchar(255) DEFAULT NULL COMMENT '收货单号',
  `asn` varchar(255) DEFAULT NULL COMMENT 'ASN号',
  `purchase_order_no` varchar(255) DEFAULT NULL COMMENT '采购单号',
  `sp_no` varchar(255) DEFAULT NULL COMMENT '供应商编号',
  `sp_name` varchar(255) DEFAULT NULL COMMENT '供应商名称',
  `sp_adr_no` varchar(255) DEFAULT NULL COMMENT '供应商地点编号',
  `branch_company_code` varchar(255) DEFAULT NULL COMMENT '子公司编码',
  `branch_company_name` varchar(255) DEFAULT NULL COMMENT '子公司名称',
  `adr_type` int(2) DEFAULT NULL COMMENT '地点类型（0:仓库;1:门店）',
  `adr_type_code` varchar(255) DEFAULT NULL COMMENT '地点编码',
  `adr_type_name` varchar(255) DEFAULT NULL COMMENT '地点名称',
  `settlement_period` int(11) DEFAULT NULL COMMENT '账期 (0:周结；1：半月结；2：月结；)',
  `pay_type` int(11) DEFAULT NULL COMMENT '付款方式 (0：网银，1：银行转账，2：现金，3：支票)',
  `groups` varchar(40) DEFAULT NULL COMMENT '部类',
  `groups_desc` varchar(255) DEFAULT NULL COMMENT '部类描述',
  `dept` varchar(40) DEFAULT NULL COMMENT '大类',
  `dept_desc` varchar(255) DEFAULT NULL COMMENT '大类描述',
  `product_code` varchar(20) DEFAULT NULL COMMENT '商品编号',
  `product_name` varchar(20) DEFAULT NULL COMMENT '商品名称',
  `input_tax_rate` decimal(4,2) DEFAULT NULL COMMENT '税率',
  `received_time` datetime DEFAULT NULL COMMENT '收货日期',
  `received_number` int(20) DEFAULT NULL COMMENT '已收货数量',
  `received_money_without_tax` decimal(20,2) DEFAULT NULL COMMENT '收货金额（未税)',
  `received_money_with_tax` decimal(20,2) DEFAULT NULL COMMENT '收货金额（含税）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='供应商结算数据逻辑描述';

-- ----------------------------
-- Records of supplier_settled （测试数据）
-- ----------------------------
INSERT INTO `supplier_settled` VALUES ('1', '1708290011', null, '1708290016', '10007', '四川福仁缘农业开发有限公司', '1000008', '10000', '成都子公司', '0', 'YT901', '测试', '2', '1', 'xcate4', '生鲜日配', 'xcate26', '面包主食', '1000404', '桃李吉士排面包(110g)', '17.00', '2017-08-29 16:41:20', '1000', '3320.00', '4000.00');
INSERT INTO `supplier_settled` VALUES ('2', '1708290012', null, '1708290017', '10008', '湖南福仁缘农业开发有限公司', '1000009', '10003', '湖南子公司', '1', 'YT902', '测试1', '0', '1', 'xcate4', '生鲜日配', 'xcate26', '面包主食', '1000404', '桃李吉士排面包(110g)', '17.00', '2017-08-31 16:41:20', '1000', '3320.00', '4000.00');
INSERT INTO `supplier_settled` VALUES ('3', '1708290013', null, '1708290018', '10009', '广东福仁缘农业开发有限公司', '1000010', '10004', '广东子公司', '1', 'YT902', '测试1', '1', '1', 'xcate4', '生鲜日配', 'xcate26', '面包主食', '1000404', '桃李吉士排面包(110g)', '17.00', '2017-09-01 16:41:20', '1000', '3320.00', '4000.00');
INSERT INTO `supplier_settled` VALUES ('4', '1708290014', null, '1708290019', '10010', '重庆福仁缘农业开发有限公司', '1000011', '10005', '重庆子公司', '1', 'YT902', '测试1', '2', '1', 'xcate4', '生鲜日配', 'xcate26', '面包主食', '1000404', '桃李吉士排面包(110g)', '17.00', '2017-09-05 16:41:20', '1000', '3320.00', '4000.00');
