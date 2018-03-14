
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
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='供应商结算数据逻辑描述';

