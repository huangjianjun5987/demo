-- 导出  表 scsit.pm_purchase_refund 结构
DROP TABLE IF EXISTS `pm_purchase_refund`;
CREATE TABLE IF NOT EXISTS `pm_purchase_refund` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `purchase_refund_no` VARCHAR(255) DEFAULT NULL COMMENT '退货单号',
  `sp_id` VARCHAR(255) DEFAULT NULL COMMENT '供应商ID',
  `sp_no` VARCHAR(255) DEFAULT NULL COMMENT '供应商编码',
  `sp_name` VARCHAR(255) DEFAULT NULL COMMENT '供应商名称',
  `sp_adr_id` VARCHAR(255) DEFAULT NULL COMMENT '供应商地点ID',
  `sp_adr_no` VARCHAR(255) DEFAULT NULL COMMENT '供应商地点编码',
  `sp_adr_name` VARCHAR(255) DEFAULT NULL COMMENT '供应商地点名称',
  `total_refund_amount` INT(11) DEFAULT NULL COMMENT '合计退货数量',
  `total_refund_money` DECIMAL(20,2) DEFAULT NULL COMMENT '合计退货金额(含税)',
  `total_refund_cost` DECIMAL(20,2) DEFAULT NULL COMMENT '合计退货成本额',
  `total_real_refund_amount` INT(11) DEFAULT NULL COMMENT '合计实际退货数量',
  `total_real_refund_money` DECIMAL(20,2) DEFAULT NULL COMMENT '合计实际退货金额(含税)',
  `adr_type` INT(2) DEFAULT '0' COMMENT '退货地点类型:0:仓库;1:门店',
  `refund_adr_code` VARCHAR(255) DEFAULT NULL COMMENT '退货地点编码',
  `refund_adr_name` VARCHAR(255) DEFAULT NULL COMMENT '退货地点名称',
  `second_category_id` VARCHAR(55) DEFAULT NULL COMMENT '二级分类id(大类)',
  `second_category_name` VARCHAR(255) DEFAULT NULL COMMENT '二级分类名称(大类)',
  `currency_code` VARCHAR(20) DEFAULT NULL COMMENT '货币种类代码,CNY',
  `branch_company_id` VARCHAR(11) DEFAULT NULL COMMENT '子公司ID',
  `status` INT(2) DEFAULT '0' COMMENT '状态:0:制单;1:已提交;2:已审核;3:已拒绝;4:待退货;5:已退货;6:已取消;7:取消失败;8:异常',
  `refund_time` DATETIME DEFAULT NULL COMMENT '退货日期',
  `refund_time_early` DATETIME DEFAULT NULL COMMENT '退货日期早于(参数配置值加当前日期)',
  `failed_reason` VARCHAR(500) DEFAULT NULL COMMENT '失败原因',
  `process_id` BIGINT(20) DEFAULT NULL COMMENT '流程ID',
  `current_process_id` BIGINT(20) DEFAULT NULL COMMENT '当前节点流程ID',
  `create_user_id` VARCHAR(20) DEFAULT NULL COMMENT '创建人',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  `modify_user_id` VARCHAR(20) DEFAULT NULL COMMENT '修改人',  
  `modify_time` DATETIME DEFAULT NULL COMMENT '修改时间',
  `audit_user_id` VARCHAR(20) DEFAULT NULL COMMENT '审核人ID',
  `audit_time` DATETIME DEFAULT NULL COMMENT '审核日期',
  `remark` VARCHAR(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='退货单表';


-- 导出  表 scsit.pm_purchase_refund_item 结构
DROP TABLE IF EXISTS `pm_purchase_refund_item`;
CREATE TABLE IF NOT EXISTS `pm_purchase_refund_item` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `purchase_refund_id` BIGINT(20) DEFAULT NULL COMMENT '退货单ID',
  `purchase_order_no` VARCHAR(255) DEFAULT NULL COMMENT '采购单号',
  `product_id` VARCHAR(255) DEFAULT NULL COMMENT '商品id',
  `product_code` VARCHAR(20) DEFAULT NULL COMMENT '商品编号',
  `product_name` VARCHAR(255) DEFAULT NULL COMMENT '商品名称',
  `international_code` VARCHAR(20) DEFAULT NULL COMMENT '条码(国际码)',
  `packing_specifications` VARCHAR(20) DEFAULT NULL COMMENT '规格',
  `produce_place` VARCHAR(255) DEFAULT NULL COMMENT '产地',
  `purchase_inside_number` INT(11) DEFAULT NULL COMMENT '采购内装数(默认箱规)',
  `unit_explanation` VARCHAR(20) DEFAULT NULL COMMENT '单位',
  `input_tax_rate` DECIMAL(4,2) DEFAULT NULL COMMENT '税率,进项税率',
  `purchase_price` DECIMAL(20,2) DEFAULT NULL COMMENT '采购价格（含税）',
  `possible_num` INT(20) DEFAULT NULL COMMENT '可退库存',
  `refund_amount` INT DEFAULT NULL COMMENT '退货数量',
  `refund_money` DECIMAL(20,2) DEFAULT NULL COMMENT '退货金额(含税)',
  `refund_cost` DECIMAL(20,2) DEFAULT NULL COMMENT '退货成本额',
  `real_refund_amount` INT(11) DEFAULT NULL COMMENT '实际退货数量',
  `real_refund_money` DECIMAL(20,2) DEFAULT NULL COMMENT '实际退货金额(含税)',
  `refund_reason` BIGINT(20) DEFAULT NULL COMMENT '退货原因(0:破损;1:临期;2:库存过剩;3:其他)',
  `create_time` DATETIME DEFAULT NULL,
  `modify_time` DATETIME DEFAULT NULL,
  `create_user_id` VARCHAR(20) DEFAULT NULL,
  `modify_user_id` VARCHAR(20) DEFAULT NULL,  
  `is_valid` INT(1) DEFAULT '1' COMMENT '是否有效:0,无效,1:有效',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='退货单商品表';


-- 导出  表 scsit.process_definition 结构
DROP TABLE IF EXISTS `process_definition`;
CREATE TABLE IF NOT EXISTS `process_definition` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `process_node_code` VARCHAR(255) DEFAULT NULL COMMENT '流程节点编码',
  `process_node_name` VARCHAR(255) DEFAULT NULL COMMENT '流程节点名称',
  `next_node_id` BIGINT(20) DEFAULT NULL COMMENT '下一个流程节点ID',
  `branch_company_id` VARCHAR(11) DEFAULT NULL COMMENT '子公司ID',
  `type` BIGINT(2) DEFAULT NULL COMMENT '流程类型:0:采购单;1:退货单;2:商品采购维护;3商品销售维护',
  `create_time` DATETIME DEFAULT NULL,
  `modify_time` DATETIME DEFAULT NULL,
  `create_user_id` VARCHAR(20) DEFAULT NULL,
  `modify_user_id` VARCHAR(20) DEFAULT NULL,  
  `is_first_node` INT(1) DEFAULT '1' COMMENT '是否首节点:0,是,1:否',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='流程定义表';

-- 导出  表 scsit.audit_log 结构
DROP TABLE IF EXISTS `process_audit_log`;
CREATE TABLE IF NOT EXISTS `process_audit_log` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `business_id` BIGINT(20) DEFAULT NULL COMMENT '业务单号ID',
  `business_type` INT(1) DEFAULT NULL COMMENT '业务类型:0:采购单;1:退货单;2:商品采购维护;3商品销售维护',
  `process_id` BIGINT(20) DEFAULT NULL COMMENT '流程ID',
  `audit_user_id` VARCHAR(20) DEFAULT NULL COMMENT '审核人ID',
  `audit_user` VARCHAR(20) DEFAULT NULL COMMENT '审核人',
  `audit_time` DATETIME DEFAULT NULL COMMENT '审核日期',
  `audit_result` BIGINT(2) DEFAULT NULL COMMENT '审批结果:0:拒绝;1:通过',
  `audit_opinion` VARCHAR(255) DEFAULT NULL COMMENT '审批意见',
  PRIMARY KEY (`id`)
) ENGINE=INNODB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='流程审批日志表';