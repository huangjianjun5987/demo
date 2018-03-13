
-- 导出  表 item_loc_soh 结构
DROP TABLE IF EXISTS `item_loc_soh`;
CREATE TABLE IF NOT EXISTS `item_loc_soh` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `item_id` varchar(40) NOT NULL COMMENT '商品编码',
  `loc` varchar(40) NOT NULL COMMENT '仓库编码',
  `unit_cost` double(20,3) DEFAULT NULL COMMENT '商品成本',
  `av_cost` double(20,3) DEFAULT NULL COMMENT '移动加权平均成本',
  `stock_on_hand` bigint(19) DEFAULT '0' COMMENT '现有库存',
  `in_transit_qty` bigint(19) DEFAULT '0' COMMENT '在途数量',
  `order_reserved_qty` bigint(19) DEFAULT '0' COMMENT '销售保留',
  `tsf_reserved_qty` bigint(19) DEFAULT '0' COMMENT '调拨预留',
  `sell_in_transit_qty` bigint(19) DEFAULT '0' COMMENT '销售在途',
  `tsf_expected_qty` bigint(19) DEFAULT '0' COMMENT '预期到货',
  `rtv_qty` bigint(19) DEFAULT NULL COMMENT '退货预留',
  `ref_no_1` varchar(40) DEFAULT NULL,
  `ref_no_2` varchar(40) DEFAULT NULL,
  `ref_no_3` varchar(40) DEFAULT NULL,
  `ref_no_4` varchar(40) DEFAULT NULL,
  `ref_no_5` varchar(40) DEFAULT NULL,
  `last_update_datetime` timestamp NULL DEFAULT NULL COMMENT '最后一次更新时间',
  `last_update_id` varchar(40) NOT NULL COMMENT '最后一次更新人',
  `product_code` varchar(50) DEFAULT NULL COMMENT '商品code',
  PRIMARY KEY (`id`),
  KEY `item_id_and_loc` (`item_id`,`loc`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='商品地点库存表';

-- 数据导出被取消选择。
-- 导出  表 tran_data 结构
DROP TABLE IF EXISTS `tran_data`;
CREATE TABLE IF NOT EXISTS `tran_data` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `item` varchar(40) NOT NULL COMMENT '商品编码',
  `groups` varchar(40) DEFAULT NULL COMMENT '部类',
  `dept` varchar(40) DEFAULT NULL COMMENT '大类',
  `classs` varchar(40) DEFAULT NULL COMMENT '中类',
  `subclass` varchar(40) DEFAULT NULL COMMENT '小类',
  `location` varchar(40) DEFAULT NULL COMMENT '仓库编码',
  `tran_date` timestamp NULL DEFAULT NULL COMMENT '事务日期',
  `tran_code` varchar(10) DEFAULT NULL COMMENT '事务类型',
  `units` bigint(19) DEFAULT '0' COMMENT '交易数量',
  `total_cost` double(20,3) DEFAULT NULL COMMENT '总成本',
  `total_retail` double(20,3) DEFAULT NULL COMMENT '销售总额',
  `ref_no_1` varchar(40) DEFAULT NULL,
  `ref_no_2` varchar(40) DEFAULT NULL,
  `ref_no_3` varchar(40) DEFAULT NULL,
  `attr_1` varchar(40) DEFAULT NULL,
  `attr_2` varchar(40) DEFAULT NULL,
  `vat_rate` varchar(40) DEFAULT NULL COMMENT '销项税税率',
  `product_code` varchar(50) DEFAULT NULL COMMENT '商品code',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='库存交易流水表';

-- 数据导出被取消选择。
