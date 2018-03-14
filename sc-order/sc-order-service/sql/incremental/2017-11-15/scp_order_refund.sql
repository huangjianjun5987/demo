CREATE TABLE `scp_return_refund` (
`id`  int(19) NOT NULL AUTO_INCREMENT COMMENT '主键id自动增长' ,
`return_order_id`  varchar(50) NULL COMMENT '退货单id' ,
`refund_id`  varchar(20) NULL COMMENT '退款单id' ,
PRIMARY KEY (`id`)
)
COMMENT='退货单与退款单关系表'
;

-- ----------------------------
-- Table structure for scp_return_request
-- ----------------------------
DROP TABLE IF EXISTS `scp_return_request`;
CREATE TABLE `scp_return_request` (
  `id` varchar(40) NOT NULL COMMENT '主键id',
  `profile_id` varchar(40) NOT NULL COMMENT '用户id',
  `branch_company_id` varchar(80) NOT NULL COMMENT '分公司ID',
  `branch_company_arehouse` varchar(80) DEFAULT NULL COMMENT '逻辑仓CODE',
  `franchisee_id` varchar(80) NOT NULL COMMENT '加盟商ID',
  `franchisee_store_id` varchar(80) NOT NULL COMMENT '加盟店ID',
  `return_request_type` varchar(80) DEFAULT NULL COMMENT '退货单类型:ZCTH(正常退货),JSTH(拒收退货),ZCHH(正常换货)',
  `completed_time` timestamp NULL DEFAULT NULL COMMENT '完成时间',
  `order_id` varchar(40) DEFAULT NULL COMMENT '当前订单附属的主订单id',
  `description` varchar(500) DEFAULT NULL COMMENT '订单的描述',
  `state` smallint(6) DEFAULT NULL COMMENT '退货单总状态(1:待确认，2:已确认，3，已完成，4：已取消',
  `state_detail` varchar(500) DEFAULT NULL COMMENT '退货单总状态描述',
  `product_state` smallint(6) DEFAULT NULL COMMENT '商品状态',
  `product_state_detail` varchar(500) DEFAULT NULL COMMENT '商品状态描述',
  `creation_time` timestamp NULL DEFAULT NULL COMMENT '创建时间,申请日期',
  `return_reason_type` smallint(6) DEFAULT NULL COMMENT '退货原因类型',
  `return_reason` varchar(250) DEFAULT NULL COMMENT '退换货原因(其他)',
  `shipping_state` smallint(6) DEFAULT NULL COMMENT '收货总状态',
  `shipping_state_detail` varchar(200) DEFAULT NULL COMMENT '收货状态描述',
  `shipping_group` varchar(40) DEFAULT NULL COMMENT '退货单的收货信息',
  `amount` double(20,2) DEFAULT NULL COMMENT '退货总额',
  `actual_amount` double(20,2) DEFAULT NULL COMMENT '实际退换货总额',
  `refund_amount` double(20,2) DEFAULT NULL COMMENT '退款金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='销售退换货单表';

-- ----------------------------
-- Table structure for scp_return_request_item
-- ----------------------------
DROP TABLE IF EXISTS `scp_return_request_item`;
CREATE TABLE `scp_return_request_item` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `return_id` varchar(255) NOT NULL COMMENT '销售退换货单表',
  `catalog_id` varchar(40) DEFAULT NULL COMMENT '根类目id',
  `quantity` bigint(20) DEFAULT NULL COMMENT '下单时的数量',
  `item_price_info` bigint(19) DEFAULT NULL COMMENT '购物车商品级别的价格信息',
  `shipped_quantity` bigint(20) DEFAULT NULL COMMENT '配送的数量',
  `completed_quantity` bigint(20) DEFAULT NULL COMMENT '签收数量',
  `return_quantity` bigint(20) DEFAULT NULL COMMENT '退换货数量',
  `actual_return_quantity` bigint(20) DEFAULT NULL COMMENT '实际退换货的数量',
  `product_code` varchar(40) DEFAULT NULL COMMENT '产品code',
  `product_id` varchar(40) DEFAULT NULL COMMENT '产品id',
  `creation_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `sale_quantity` bigint(20) DEFAULT NULL COMMENT '销售数量',
  `unit_quantity` int(11) DEFAULT NULL COMMENT '销售内装数',
  `state` smallint(6) DEFAULT NULL COMMENT '收货状态',
  `state_detail` varchar(200) DEFAULT NULL COMMENT '收货状态描述',
  `product_name` varchar(255) DEFAULT NULL COMMENT '产品id',
  `actual_raw_total_price` double(20,2) DEFAULT NULL COMMENT '实际退换货总额',
  `raw_total_price` double(20,2) DEFAULT NULL COMMENT '退换货总额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=223 DEFAULT CHARSET=utf8 COMMENT='销售退换货单商品表';
