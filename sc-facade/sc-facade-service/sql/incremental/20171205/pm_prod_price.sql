
ALTER TABLE prod_purchase_info ADD COLUMN `failed_reason`  VARCHAR(500) NULL COMMENT '失败原因';
ALTER TABLE prod_purchase_info ADD COLUMN `audit_user_id`  VARCHAR(20) NULL COMMENT '审核人ID';
ALTER TABLE prod_purchase_info ADD COLUMN `audit_time`  DATETIME NULL COMMENT '审核日期';
ALTER TABLE prod_purchase_info ADD COLUMN `audit_status`  INT(2) NULL DEFAULT 2 COMMENT '审核状态:1:已提交;2:已审核;3:已拒绝';
ALTER TABLE prod_purchase_info ADD COLUMN `newest_price`  DECIMAL(20,2) NULL COMMENT '最新价格';
ALTER TABLE prod_purchase_info ADD COLUMN `percentage`  DECIMAL(20,2) NULL DEFAULT 0 COMMENT '调价百分比';
ALTER TABLE prod_purchase_info ADD COLUMN `first_created`  INT(2) NULL DEFAULT 0 COMMENT '第一次创建使用:1:是;0:否';
ALTER TABLE prod_purchase_info ADD COLUMN `support_return`  INT(2) NULL DEFAULT 1 COMMENT '是否支持退货:1:支持;0:不支持';


ALTER TABLE prod_sell_info ADD COLUMN `failed_reason`  VARCHAR(500) NULL COMMENT '失败原因';
ALTER TABLE prod_sell_info ADD COLUMN `audit_user_id`  VARCHAR(20) NULL COMMENT '审核人ID';
ALTER TABLE prod_sell_info ADD COLUMN `audit_time`  DATETIME NULL COMMENT '审核日期';
ALTER TABLE prod_sell_info ADD COLUMN `audit_status`  INT(2) NULL DEFAULT 2 COMMENT '审核状态:1:已提交;2:已审核;3:已拒绝';
ALTER TABLE prod_sell_info ADD COLUMN `first_created`  INT(2) NULL DEFAULT 0 COMMENT '第一次创建使用:1:是;0:否';

DROP TABLE IF EXISTS `prod_sell_info_events`;
CREATE TABLE `prod_sell_info_events` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '数据自增ID',
  `price_id` BIGINT(20) NOT NULL COMMENT '售价ID',
  `serialized_payload` LONGTEXT DEFAULT NULL COMMENT '序列化',
  `create_time` DATETIME DEFAULT NULL COMMENT '操作时间',
  `create_user_id` VARCHAR(20) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='售价修改事件表';


DROP TABLE IF EXISTS `prod_price_change`;
CREATE TABLE `prod_price_change` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '数据自增ID',
  `change_type` INT(2) DEFAULT '0' COMMENT '变更类型:0:采购进价变更;1:售价变更',
  `price_id` BIGINT(20) NOT NULL COMMENT '采购价ID，售价区间ID',
  `sp_id` VARCHAR(255) DEFAULT NULL COMMENT '供应商ID',
  `sp_adr_id` VARCHAR(255) DEFAULT NULL COMMENT '供应商地点ID',
  `branch_company_id` VARCHAR(50) DEFAULT NULL COMMENT '子公司id',
  `product_id` VARCHAR(255) DEFAULT NULL COMMENT '商品ID',
  `product_code` VARCHAR(255) DEFAULT NULL COMMENT '商品编码',
  `price` DECIMAL(20,2) DEFAULT NULL COMMENT '当前价格',
  `newest_price` DECIMAL(20,2) DEFAULT NULL COMMENT '提交价格',
  `percentage` DECIMAL(20,2) DEFAULT NULL COMMENT '调价百分比',
  `gross_profit_margin` DECIMAL(20,2) DEFAULT NULL COMMENT '毛利率',
  `create_time` DATETIME DEFAULT NULL COMMENT '操作时间',
  `create_user_id` VARCHAR(20) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='价格变更记录表';

DROP TABLE IF EXISTS `prod_sell_info_imports`;
CREATE TABLE `prod_sell_info_imports` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '调价单ID(上传ID)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000000 DEFAULT CHARSET=utf8 COMMENT='商品售价导入表(调价单)';

DROP TABLE IF EXISTS `prod_sell_info_import`;
CREATE TABLE `prod_sell_info_import` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据自增ID',
  `imports_id` bigint(20) NOT NULL COMMENT '上传ID',
  `line_number` bigint(20) DEFAULT NULL COMMENT '行号',
  `handle_result` int(2) DEFAULT NULL COMMENT '处理结果:0:错误;1:已验证;2:已提交',
  `handle_information` varchar(500) DEFAULT NULL COMMENT '处理信息',
  `product_id` varchar(255) DEFAULT NULL COMMENT '商品id',
  `product_information` varchar(500) DEFAULT NULL COMMENT '商品信息',
  `start_number` int(20) DEFAULT NULL COMMENT '区间起始数量',
  `end_number` int(20) DEFAULT NULL COMMENT '区间结束数量',
  `newest_price` decimal(20,2) DEFAULT NULL COMMENT '最新售价',
  `branch_company_id` varchar(50) DEFAULT NULL COMMENT '子公司id',
  `price_id` bigint(20) DEFAULT NULL COMMENT '售价ID',
  `product_code` varchar(255) DEFAULT NULL COMMENT '商品编码',
  `section` varchar(255) DEFAULT NULL COMMENT '导入区间数据',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品售价导入记录表';


DROP TABLE IF EXISTS `prod_purchase_info_imports`;
CREATE TABLE `prod_purchase_info_imports` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '调价单ID(上传ID)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000000 DEFAULT CHARSET=utf8 COMMENT='商品采购价导入表(调价单)';

DROP TABLE IF EXISTS `prod_purchase_info_import`;
CREATE TABLE `prod_purchase_info_import` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据自增ID',
  `imports_id` bigint(20) NOT NULL COMMENT '上传ID',
  `line_number` bigint(20) DEFAULT NULL COMMENT '行号',
  `handle_result` int(2) DEFAULT NULL COMMENT '处理结果:0:错误;1:已验证;2:已提交',
  `handle_information` varchar(500) DEFAULT NULL COMMENT '处理信息',
  `product_id` varchar(255) DEFAULT NULL COMMENT '商品id',
  `product_information` varchar(500) DEFAULT NULL COMMENT '商品信息',
  `newest_price` decimal(20,2) DEFAULT NULL COMMENT '最新售价',
  `sp_id` varchar(255) DEFAULT NULL COMMENT '供应商id',
  `sp_adr_id` varchar(255) DEFAULT NULL COMMENT '供应商地点id',
  `price_id` bigint(20) DEFAULT NULL COMMENT '采购价ID',
  `sp_no` varchar(255) DEFAULT NULL COMMENT '供应商编码',
  `sp_adr_no` varchar(255) DEFAULT NULL COMMENT '供应商地点编码',
  `product_code` varchar(255) DEFAULT NULL COMMENT '商品编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品采购价导入记录表';


DROP TABLE IF EXISTS `prod_purchase_info_log`;
CREATE TABLE `prod_purchase_info_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `price_id` varchar(255) DEFAULT NULL COMMENT '采购价格ID',
  `purchase_price` decimal(20,2) DEFAULT NULL COMMENT '采购价格',
  `operate` varchar(255) DEFAULT NULL COMMENT '操作',
  `operate_date` datetime DEFAULT NULL COMMENT '操作时间',
  `operate_user_id` varchar(255) DEFAULT NULL COMMENT '操作用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
