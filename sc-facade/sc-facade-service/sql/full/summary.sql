
-- 导出  表 scsit.ad_plan 结构
DROP TABLE IF EXISTS `ad_plan`;
CREATE TABLE IF NOT EXISTS `ad_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `plan_name` varchar(255) DEFAULT NULL COMMENT '方案名称',
  `pic_name1` varchar(255) DEFAULT NULL COMMENT '图片1名称',
  `pic_url1` varchar(255) DEFAULT NULL COMMENT '图片1存储url',
  `link_url1` varchar(255) DEFAULT NULL COMMENT '链接地址1',
  `pic_name2` varchar(255) DEFAULT NULL COMMENT '图片2名称',
  `link_url2` varchar(255) DEFAULT NULL COMMENT '链接地址2',
  `pic_url2` varchar(255) DEFAULT NULL COMMENT '图片2存储url',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(11) DEFAULT NULL COMMENT '1:启用,0:停用',
  `modify_time` datetime DEFAULT NULL COMMENT '更改时间',
  `modify_user_id` varchar(50) DEFAULT NULL COMMENT '修改者用户id',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建者id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='广告方案表';

-- 数据导出被取消选择。
-- 导出  表 scsit.category_goods_order 结构
DROP TABLE IF EXISTS `category_goods_order`;
CREATE TABLE IF NOT EXISTS `category_goods_order` (
  `pk_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `id` varchar(255) DEFAULT NULL COMMENT '商品编号',
  `name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `first_category_id` varchar(255) DEFAULT NULL COMMENT '一级分类id',
  `second_category_id` varchar(255) DEFAULT NULL COMMENT '二级分类id',
  `third_category_id` varchar(255) DEFAULT NULL COMMENT '三级分类id',
  `first_category_name` varchar(255) DEFAULT NULL COMMENT '一级分类名称',
  `second_category_name` varchar(255) DEFAULT NULL COMMENT '二级分类名称',
  `third_category_name` varchar(255) DEFAULT NULL COMMENT '三级分类名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8 COMMENT='分类下商品排序表（暂时保留）';

-- 数据导出被取消选择。
-- 导出  表 scsit.pm_purchase_order 结构
DROP TABLE IF EXISTS `pm_purchase_order`;
CREATE TABLE IF NOT EXISTS `pm_purchase_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_order_no` varchar(255) DEFAULT NULL COMMENT '采购单号',
  `sp_id` varchar(255) DEFAULT NULL COMMENT '供应商ID',
  `sp_no` varchar(255) DEFAULT NULL COMMENT '供应商编码',
  `sp_name` varchar(255) DEFAULT NULL COMMENT '供应商名称',
  `sp_adr_id` varchar(255) DEFAULT NULL COMMENT '供应商地点ID',
  `sp_adr_no` varchar(255) DEFAULT NULL COMMENT '供应商地点编码',
  `sp_adr_name` varchar(255) DEFAULT NULL COMMENT '供应商地点名称',
  `estimated_delivery_date` datetime DEFAULT NULL COMMENT '预计送货日期',
  `settlement_period` int(11) DEFAULT NULL COMMENT '账期',
  `pay_type` int(11) DEFAULT NULL COMMENT '供应商付款方式',
  `adr_type` int(2) DEFAULT '0' COMMENT '地点类型:0:仓库;1:门店',
  `adr_type_code` varchar(255) DEFAULT NULL COMMENT '地点类型编码',
  `adr_type_name` varchar(255) DEFAULT NULL COMMENT '地点类型名称',
  `second_category_id` varchar(55) DEFAULT NULL COMMENT '二级分类id(大类)',
  `currency_code` varchar(20) DEFAULT NULL COMMENT '货币种类代码,CNY',
  `branch_company_id` varchar(11) DEFAULT NULL COMMENT '子公司ID',
  `purchase_order_type` int(2) DEFAULT '0' COMMENT '类型:0:普通采购单',
  `status` int(2) DEFAULT '0' COMMENT '状态:0:草稿;1:待审核;2:已审核;3:已拒绝;4:已关闭',
  `failed_reason` varchar(500) DEFAULT NULL COMMENT '失败原因',
  `audit_user_id` varchar(20) DEFAULT NULL COMMENT '审核人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_user_id` varchar(20) DEFAULT NULL COMMENT '创建人',
  `modify_user_id` varchar(20) DEFAULT NULL COMMENT '修改人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核日期',
  `adr_name` varchar(255) DEFAULT NULL COMMENT '门店和仓库详细地址（收货地址）',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `second_category_name` varchar(255) DEFAULT NULL COMMENT '二级分类名称(大类)',
  `bar_code_url` varchar(255) DEFAULT NULL COMMENT '条码地址',
  `total_tax_amount` decimal(20,2) DEFAULT NULL COMMENT '总税额',
  `total_without_tax_amount` decimal(20,2) DEFAULT NULL COMMENT '不含税采购金额',
  `total_amount` decimal(20,2) DEFAULT NULL COMMENT '总采购金额，含税',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='采购单表';

-- 数据导出被取消选择。
-- 导出  表 scsit.pm_purchase_order_item 结构
DROP TABLE IF EXISTS `pm_purchase_order_item`;
CREATE TABLE IF NOT EXISTS `pm_purchase_order_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_order_id` varchar(255) DEFAULT NULL COMMENT '采购单号',
  `product_id` varchar(255) DEFAULT NULL COMMENT '商品id',
  `product_code` varchar(20) DEFAULT NULL COMMENT '商品编号',
  `product_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `international_code` varchar(20) DEFAULT NULL COMMENT '条码(国际码)',
  `packing_specifications` varchar(20) DEFAULT NULL COMMENT '规格',
  `produce_place` varchar(255) DEFAULT NULL COMMENT '产地',
  `purchase_inside_number` int(11) DEFAULT NULL COMMENT '采购内装数(默认箱规)',
  `unit_explanation` varchar(20) DEFAULT NULL COMMENT '单位',
  `input_tax_rate` decimal(4,2) DEFAULT NULL COMMENT '税率,进项税率',
  `purchase_price` decimal(20,2) DEFAULT NULL COMMENT '采购价格（含税）',
  `purchase_number` int(20) DEFAULT NULL COMMENT '采购数量,必须为采购内装数的倍数',
  `total_amount` decimal(20,2) DEFAULT NULL COMMENT '采购金额，含税',
  `received_number` int(20) DEFAULT NULL COMMENT '已收货数量,收货数量<=采购数量',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `create_user_id` varchar(20) DEFAULT NULL,
  `modify_user_id` varchar(20) DEFAULT NULL,
  `prod_purchase_id` varchar(255) DEFAULT NULL COMMENT '商品采购关系主键',
  `tax_amount` decimal(20,2) DEFAULT NULL COMMENT '税额',
  `total_without_tax_amount` decimal(20,2) DEFAULT NULL COMMENT '不含税采购金额',
  `is_valid` int(1) DEFAULT '1' COMMENT '是否有效:0,无效,1:有效',
  `in_validate_reason` varchar(255) DEFAULT NULL COMMENT '失败原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='采购单商品表';

-- 数据导出被取消选择。
-- 导出  表 scsit.pm_purchase_receipt 结构
DROP TABLE IF EXISTS `pm_purchase_receipt`;
CREATE TABLE IF NOT EXISTS `pm_purchase_receipt` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_receipt_no` varchar(255) DEFAULT NULL COMMENT '收货单号',
  `purchase_order_id` varchar(255) DEFAULT NULL COMMENT '采购单id',
  `asn` varchar(255) DEFAULT NULL COMMENT 'ASN',
  `estimated_received_date` datetime DEFAULT NULL COMMENT '预计到货日期,系统自动默认，值为当前日期.注：预留与供应商系统对接预发货通知',
  `received_time` datetime DEFAULT NULL COMMENT '收货日期,系统自动默认，值为创建收货单时间',
  `branch_company_id` int(11) DEFAULT NULL COMMENT '子公司ID',
  `status` int(2) DEFAULT NULL COMMENT '状态:0:待下发，1：已下发，2:已收货，3:已取消，4：异常',
  `exception_reason` varchar(255) DEFAULT NULL COMMENT '异常原因',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `create_user_id` varchar(20) DEFAULT NULL,
  `modify_user_id` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='采购收货单\r\n采购收货单表';

-- 数据导出被取消选择。
-- 导出  表 scsit.pm_purchase_receipt_items 结构
DROP TABLE IF EXISTS `pm_purchase_receipt_items`;
CREATE TABLE IF NOT EXISTS `pm_purchase_receipt_items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_receipt_id` varchar(255) DEFAULT NULL COMMENT '收货单id',
  `purchase_order_id` varchar(255) DEFAULT NULL COMMENT '采购单id',
  `purchase_order_items_id` varchar(20) DEFAULT NULL COMMENT '采购单明细id',
  `delivery_number` int(11) DEFAULT NULL COMMENT '供应商出库数量,默认等于采购数量,预留与供应商系统对接预发货通知',
  `received_number` int(11) DEFAULT NULL COMMENT '已收货数量,收货数量<=出货数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_user_id` varchar(20) DEFAULT NULL COMMENT '创建人',
  `modify_user_id` varchar(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='采购收货单商品表';

-- 数据导出被取消选择。
-- 导出  表 scsit.prod_purchase_info 结构
DROP TABLE IF EXISTS `prod_purchase_info`;
CREATE TABLE IF NOT EXISTS `prod_purchase_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'pk',
  `sp_id` varchar(255) DEFAULT NULL COMMENT '供应商ID',
  `sp_adr_id` varchar(255) DEFAULT NULL COMMENT '供应商地点id',
  `product_id` varchar(255) DEFAULT NULL COMMENT '商品ID',
  `branch_company_id` varchar(50) DEFAULT NULL COMMENT '子公司编码',
  `supplier_type` int(1) DEFAULT '0' COMMENT '供应商类型:0：一般供应商,1:主供应商',
  `purchase_inside_number` int(11) DEFAULT NULL COMMENT '采购内装数(默认箱规)',
  `purchase_price` decimal(20,2) DEFAULT NULL COMMENT '采购价格',
  `international_code` varchar(20) DEFAULT NULL COMMENT '条码(国际码)',
  `distribute_warehouse_id` bigint(20) DEFAULT NULL COMMENT '配送仓库id',
  `status` int(1) DEFAULT '0' COMMENT '采购关系的状态:0,,失效,1启用',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态:0,,未删除,1启删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_user_id` varchar(50) DEFAULT NULL COMMENT '修改人',
  `modify_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='商品采购关系';

-- 数据导出被取消选择。
-- 导出  表 scsit.prod_sell_info 结构
DROP TABLE IF EXISTS `prod_sell_info`;
CREATE TABLE IF NOT EXISTS `prod_sell_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '销售价格主键id',
  `product_id` varchar(255) DEFAULT NULL COMMENT '商品id',
  `lowest_price` decimal(20,2) DEFAULT NULL COMMENT '最低销售价',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(20) DEFAULT NULL COMMENT '创建人',
  `modify_time` datetime DEFAULT NULL COMMENT '删除或修改时间',
  `modify_user_id` varchar(20) DEFAULT NULL COMMENT '删除或修改人',
  `status` int(1) DEFAULT '1' COMMENT '0为失效状态1为正常使用状态',
  `delete_status` int(1) DEFAULT '0' COMMENT '0为未删除状态1为删除状态',
  `branch_company_id` varchar(50) DEFAULT NULL COMMENT '子公司id',
  `branch_company_name` varchar(255) DEFAULT NULL COMMENT '子公司名字',
  `suggest_price` decimal(20,2) DEFAULT NULL COMMENT '建议零售价',
  `delivery_day` int(11) DEFAULT NULL COMMENT '承诺最迟发货时间（天）',
  `sales_inside_number` int(11) DEFAULT NULL COMMENT '销售内装数',
  `min_number` int(11) DEFAULT NULL COMMENT '起订量',
  `sku_id` varchar(20) DEFAULT NULL COMMENT 'skuID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='商品销售价格定价信息表';

-- 数据导出被取消选择。
-- 导出  表 scsit.prod_sell_section_price 结构
DROP TABLE IF EXISTS `prod_sell_section_price`;
CREATE TABLE IF NOT EXISTS `prod_sell_section_price` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id主键',
  `price` decimal(20,2) DEFAULT NULL COMMENT '商品价格',
  `start_number` int(20) DEFAULT NULL COMMENT '区间起始数量',
  `end_number` int(20) DEFAULT NULL COMMENT '区间结束数量',
  `sell_price_id` bigint(20) DEFAULT NULL COMMENT '销售价格信息id',
  `delete_status` int(1) DEFAULT '0' COMMENT '0为未删除状态1为删除状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='商品价格区间表';

-- 数据导出被取消选择。
-- 导出  表 scsit.recommend_keywords 结构
DROP TABLE IF EXISTS `recommend_keywords`;
CREATE TABLE IF NOT EXISTS `recommend_keywords` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `content` varchar(255) DEFAULT NULL COMMENT '参数内容',
  `input_key` int(2) DEFAULT NULL COMMENT '是否搜索框的内容1-是 2-不是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8 COMMENT='搜索推荐页表';

-- 数据导出被取消选择。
-- 导出  表 scsit.service_commitments 结构
DROP TABLE IF EXISTS `service_commitments`;
CREATE TABLE IF NOT EXISTS `service_commitments` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'pk',
  `promise_content` varchar(100) DEFAULT NULL COMMENT '承诺内容',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `status` int(11) DEFAULT NULL COMMENT '启禁用状态:0禁用,1启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='服务承诺';

-- 数据导出被取消选择。
-- 导出  表 scsit.static_page_info 结构
DROP TABLE IF EXISTS `static_page_info`;
CREATE TABLE IF NOT EXISTS `static_page_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) DEFAULT NULL COMMENT '静态页名称',
  `link_url` varchar(100) DEFAULT NULL COMMENT '链接地址',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(19) DEFAULT NULL COMMENT '创建人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(19) DEFAULT NULL COMMENT '修改人id',
  `page_content` text COMMENT '编辑器编辑的静态页内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='静态页管理表';

-- 数据导出被取消选择。
-- 导出  表 scsit.sys_dictionary 结构
DROP TABLE IF EXISTS `sys_dictionary`;
CREATE TABLE IF NOT EXISTS `sys_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dictionary` varchar(50) DEFAULT NULL COMMENT '字典名称',
  `code` varchar(50) DEFAULT NULL COMMENT '字典编号',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `dictionaryType` int(4) DEFAULT '0' COMMENT '字典类型（0：业务级字典   1：系统级字段）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8 COMMENT='数据字典类型表';

-- 数据导出被取消选择。
-- 导出  表 scsit.sys_dictionary_content 结构
DROP TABLE IF EXISTS `sys_dictionary_content`;
CREATE TABLE IF NOT EXISTS `sys_dictionary_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dictionary_id` int(11) DEFAULT NULL,
  `contentName` varchar(50) DEFAULT NULL COMMENT '内容名称',
  `contentCode` varchar(25) DEFAULT NULL COMMENT '内容值',
  `state` int(4) DEFAULT '1' COMMENT '状态:0停用，1启用，默认启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8 COMMENT='数据字典内容表';

-- 数据导出被取消选择。
-- 导出  表 scsit.sys_home_area_ad 结构
DROP TABLE IF EXISTS `sys_home_area_ad`;
CREATE TABLE IF NOT EXISTS `sys_home_area_ad` (
  `id` varchar(255) NOT NULL COMMENT '数据ID',
  `code` varchar(255) DEFAULT NULL COMMENT '区域代码',
  `name` varchar(255) NOT NULL COMMENT '区域名称',
  `sequence` double DEFAULT NULL COMMENT '排序序号',
  `is_enabled` tinyint(4) DEFAULT NULL COMMENT '是否启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首页配置区域表';

-- 数据导出被取消选择。
-- 导出  表 scsit.sys_home_carousel_ad 结构
DROP TABLE IF EXISTS `sys_home_carousel_ad`;
CREATE TABLE IF NOT EXISTS `sys_home_carousel_ad` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sorting` int(11) DEFAULT NULL COMMENT '序号',
  `link_type` int(2) DEFAULT NULL COMMENT '链接类型: (1：商品链接；2：静态活动页)',
  `goods_id` varchar(20) DEFAULT NULL COMMENT '商品编号',
  `link_address` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `pic_address` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `status` int(2) DEFAULT NULL COMMENT '状态（0：停用，1：已启用）',
  `create_person` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_person` varchar(50) DEFAULT NULL COMMENT '修改人',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='轮播广告';

-- 数据导出被取消选择。
-- 导出  表 scsit.sys_home_carousel_param 结构
DROP TABLE IF EXISTS `sys_home_carousel_param`;
CREATE TABLE IF NOT EXISTS `sys_home_carousel_param` (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `carousel_interval` int(2) DEFAULT NULL COMMENT '轮播间隔（秒）',
  `status` int(2) DEFAULT NULL COMMENT '状态（0：未设置间隔，1：当前设置间隔）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='轮播参数';

-- 数据导出被取消选择。
-- 导出  表 scsit.sys_home_item_ad 结构
DROP TABLE IF EXISTS `sys_home_item_ad`;
CREATE TABLE IF NOT EXISTS `sys_home_item_ad` (
  `id` varchar(255) NOT NULL COMMENT '数据ID',
  `area_id` varchar(255) DEFAULT NULL COMMENT '区域ID',
  `name` varchar(255) NOT NULL COMMENT '广告位名称',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `sub_title` varchar(255) DEFAULT NULL COMMENT '副标题',
  `product_no` varchar(255) DEFAULT NULL COMMENT '链接',
  `url_type` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL COMMENT '链接',
  `icon` varchar(255) DEFAULT NULL COMMENT '广告图标',
  `ad_type` varchar(255) DEFAULT NULL COMMENT '广告类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首页配置横幅广告表';

-- 数据导出被取消选择。
-- 导出  表 scsit.sys_home_quick_navigation 结构
DROP TABLE IF EXISTS `sys_home_quick_navigation`;
CREATE TABLE IF NOT EXISTS `sys_home_quick_navigation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `navigation_position` varchar(20) DEFAULT NULL COMMENT '位置',
  `navigation_type` int(2) DEFAULT NULL COMMENT '类型：(1：商品链接；2：静态页面；3：功能链接)',
  `goods_id` varchar(20) DEFAULT NULL COMMENT '商品编号',
  `navigation_name` varchar(20) DEFAULT NULL COMMENT '名称',
  `link_address` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `pic_address` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `create_person` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_person` varchar(50) DEFAULT NULL COMMENT '修改人',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_update` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` int(2) DEFAULT '1' COMMENT '状态（0：停用，1：已启用）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='快捷导航';

-- 数据导出被取消选择。
-- 导出  表 scsit.warehouse_logic_info 结构
DROP TABLE IF EXISTS `warehouse_logic_info`;
CREATE TABLE IF NOT EXISTS `warehouse_logic_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '仓库id',
  `physical_warehouse_id` int(11) NOT NULL COMMENT '物理仓库id',
  `warehouse_code` varchar(55) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `branch_company_id` varchar(255) DEFAULT NULL COMMENT '子公司ID',
  `branch_company_code` varchar(255) DEFAULT NULL COMMENT '子公司code',
  `branch_company_name` varchar(255) DEFAULT NULL COMMENT '子公司名称',
  `logic_type` int(2)  COMMENT '逻辑仓类型',
  `storage_type` int(2)  COMMENT '存储类型(1:常温;2:冷藏;3冷冻)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_user` varchar(20) DEFAULT NULL,
  `modify_user` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='逻辑仓库表';

-- 数据导出被取消选择。
-- 导出  表 scsit.warehouse_physical_info 结构
DROP TABLE IF EXISTS `warehouse_physical_info`;
CREATE TABLE IF NOT EXISTS `warehouse_physical_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '仓库id',
  `warehouse_code` varchar(55) DEFAULT NULL COMMENT '仓库编码',
  `third_warehouse_code` varchar(55) DEFAULT NULL COMMENT '第三方仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `warehouse_service` varchar(255) DEFAULT NULL COMMENT '仓库服务方',
  `province_code` varchar(55) DEFAULT NULL COMMENT '省份编码',
  `province` varchar(55) DEFAULT NULL COMMENT '省份',
  `city_code` varchar(50) DEFAULT NULL COMMENT '城市编码',
  `city` varchar(50) DEFAULT NULL COMMENT '城市',
  `county_code` varchar(50) DEFAULT NULL COMMENT '区域编码',
  `county` varchar(50) DEFAULT NULL COMMENT '区域名称',
  `address` varchar(255) DEFAULT NULL COMMENT '仓库地址',
  `contact_person` varchar(20) DEFAULT NULL COMMENT '仓库联系人',
  `contact_mode` varchar(20) DEFAULT NULL COMMENT '仓库联系方式',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_user` varchar(20) DEFAULT NULL,
  `modify_user` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='物理仓库表';

-- 数据导出被取消选择。

