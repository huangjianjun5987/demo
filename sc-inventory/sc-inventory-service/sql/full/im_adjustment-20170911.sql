
-- ----------------------------
-- Table structure for `im_adjustment`
-- ----------------------------
DROP TABLE IF EXISTS `im_adjustment`;
CREATE TABLE `im_adjustment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `adjustment_no` varchar(255) DEFAULT NULL COMMENT '单据编号',
  `type` int(2) NOT NULL COMMENT '类型:0:物流丢失（WLDS）、1:仓库报溢（CKBY）、2:仓库报损（CKBS）、3:业务调增（YWTZ）、4:业务调减（YWTJ）、5:仓库同步调增（CKTBZ）、6:仓库同步调减（CKTBJ）',
  `status` int(2) NOT NULL COMMENT '状态:0:制单;1:已生效',
  `adjustment_time` datetime DEFAULT NULL COMMENT '调整日期',
  `warehouse_code` varchar(20) DEFAULT NULL COMMENT '调整仓库,逻辑仓',
  `total_quantity` bigint(19) DEFAULT NULL COMMENT '调整数量合计',
  `total_adjustment_cost` decimal(20,3) DEFAULT NULL COMMENT '调整成本合计',
  `description` varchar(500) DEFAULT NULL COMMENT '备注',
  `external_bill_no` varchar(255) DEFAULT NULL COMMENT '外部单据号',
  `failed_reason` varchar(500) DEFAULT NULL COMMENT '失败原因',
  `audit_user_name` varchar(20) DEFAULT NULL COMMENT '修改人名称',
  `audit_user_id` varchar(20) DEFAULT NULL COMMENT '批准人ID',
  `audit_time` datetime DEFAULT NULL COMMENT '批准日期',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL,
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `create_user_id` varchar(20) DEFAULT NULL COMMENT '创建人id',
  `modify_user_id` varchar(20) DEFAULT NULL,
  `modify_user_name` varchar(19) DEFAULT NULL COMMENT '修改人名称',
  `branch_company_id` varchar(50) DEFAULT NULL COMMENT '逻辑仓所属子公司id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1000001 DEFAULT CHARSET=utf8 COMMENT='库存调整单表';

DROP TABLE IF EXISTS `im_adjustment_item`;
CREATE TABLE `im_adjustment_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `adjustment_id` varchar(255) NOT NULL COMMENT '库存调整单表',
  `product_id` varchar(255) DEFAULT NULL COMMENT '商品id',
  `product_code` varchar(20) DEFAULT NULL COMMENT '商品编号',
  `product_name` varchar(20) DEFAULT NULL COMMENT '商品名称',
  `av_cost` decimal(20,3) DEFAULT NULL COMMENT '平均成本',
  `stock_on_hand` bigint(19) DEFAULT '0' COMMENT '现有库存',
  `quantity` bigint(19) DEFAULT NULL COMMENT '调整数量',
  `adjustment_cost` decimal(20,3) DEFAULT NULL COMMENT '调整成本额',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `create_user_id` varchar(20) DEFAULT NULL,
  `modify_user_id` varchar(20) DEFAULT NULL,
  `product_date` datetime DEFAULT NULL COMMENT '商品生产日期',
  `expire_date` datetime DEFAULT NULL COMMENT '商品过期日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='库存调整单商品表';