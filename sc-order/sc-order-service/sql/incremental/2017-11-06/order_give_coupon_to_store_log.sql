DROP TABLE IF EXISTS scp_order_give_coupon_to_store_log;
CREATE TABLE scp_order_give_coupon_to_store_log (
  `id` varchar(40) NOT NULL COMMENT 'ID',
  `price_info_id` bigint(20) DEFAULT NULL COMMENT '价格信息ID',
  `give_coupon_info` varchar(1000) DEFAULT NULL COMMENT '返券券列表',
  `success` tinyint(1) DEFAULT '0'  COMMENT '是否成功',
  `creationTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='订单返券日志信息';
