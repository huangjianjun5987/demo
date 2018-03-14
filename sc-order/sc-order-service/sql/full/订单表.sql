

DROP TABLE IF EXISTS `invoice_info`;

CREATE TABLE `invoice_info` (
  `invoice_id` varchar(40) NOT NULL COMMENT '主键id',
  `profile_id` varchar(40) NOT NULL COMMENT '用户ID',
  `invoice_type` varchar(20) NOT NULL COMMENT '发票类型',
  `invoice_title` varchar(100) DEFAULT NULL COMMENT '发票抬头',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `taxpayer_identification_number` varchar(100) DEFAULT NULL COMMENT '纳税人识别码',
  `registered_address` varchar(255) DEFAULT '' COMMENT '注册地址',
  `company_phone` varchar(100) DEFAULT NULL COMMENT '公司电话',
  `deposit_bank` varchar(100) DEFAULT NULL COMMENT '开户银行',
  `account_number` varchar(100) DEFAULT NULL COMMENT '开户账号',
  `entry_datetime` datetime DEFAULT NULL COMMENT '创建时间',
  `update_datetime` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`invoice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `item_loc_soh` */

DROP TABLE IF EXISTS `item_loc_soh`;

CREATE TABLE `item_loc_soh` (
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

/*Table structure for table `mq_failed_msg` */

DROP TABLE IF EXISTS `mq_failed_msg`;

CREATE TABLE `mq_failed_msg` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `order_id` varchar(40) NOT NULL COMMENT '订单id',
  `message_type` varchar(40) NOT NULL COMMENT '消息类型',
  `resend_success` tinyint(1) DEFAULT '0' COMMENT '是否重发成功',
  `create_datetime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

/*Table structure for table `order_dictionary` */

DROP TABLE IF EXISTS `order_dictionary`;

CREATE TABLE `order_dictionary` (
  `property_name` varchar(255) NOT NULL,
  `property_value` varchar(255) NOT NULL,
  PRIMARY KEY (`property_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `scp_commerce_item` */

DROP TABLE IF EXISTS `scp_commerce_item`;

CREATE TABLE `scp_commerce_item` (
  `id` varchar(40) NOT NULL COMMENT '主键id',
  `catalog_id` varchar(40) DEFAULT NULL COMMENT '根类目id',
  `quantity` bigint(20) DEFAULT NULL COMMENT '下单时的数量',
  `state` smallint(6) DEFAULT NULL COMMENT '购物车商品状态',
  `state_detail` varchar(200) DEFAULT NULL COMMENT '状态描述',
  `item_price_info` varchar(40) DEFAULT NULL COMMENT '购物车商品级别的价格信息',
  `shipped_quantity` bigint(20) DEFAULT NULL COMMENT '配送的数量',
  `completed_quantity` bigint(20) DEFAULT NULL COMMENT '签收数量',
  `return_quantity` bigint(20) DEFAULT NULL COMMENT '发生了退换货的数量',
  `sku_id` varchar(40) DEFAULT NULL COMMENT 'sku产品id',
  `product_id` varchar(40) DEFAULT NULL COMMENT '产品id',
  `is_selected` bit(1) DEFAULT NULL COMMENT '是否选中',
  `type` varchar(80) DEFAULT NULL COMMENT '购物车商品java对象全路径',
  `creation_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `scp_invoice` */

DROP TABLE IF EXISTS `scp_invoice`;

CREATE TABLE `scp_invoice` (
  `invoice_id` varchar(40) NOT NULL COMMENT '主键id',
  `po_number` varchar(40) DEFAULT NULL COMMENT '发票号',
  `invoice_type` varchar(20) NOT NULL COMMENT '发票类型',
  `invoice_title` varchar(100) DEFAULT NULL COMMENT '发票抬头',
  `company_name` varchar(100) DEFAULT NULL COMMENT '公司名称',
  `taxpayer_identification_number` varchar(100) DEFAULT NULL COMMENT '纳税人识别码',
  `registered_address` varchar(255) DEFAULT '' COMMENT '注册地址',
  `company_phone` varchar(100) DEFAULT NULL COMMENT '公司电话',
  `deposit_bank` varchar(100) DEFAULT NULL COMMENT '开户银行',
  `account_number` varchar(100) DEFAULT NULL COMMENT '开户账号',
  PRIMARY KEY (`invoice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `scp_item_price` */

DROP TABLE IF EXISTS `scp_item_price`;

CREATE TABLE `scp_item_price` (
  `id` varchar(40) NOT NULL COMMENT '主键id',
  `amount` double(20,2) DEFAULT NULL COMMENT '订单商品的价格计算结果',
  `currency_code` varchar(20) DEFAULT NULL COMMENT '货币种类代码',
  `final_reason_code` varchar(200) DEFAULT NULL COMMENT '价格计算终止原因',
  `list_price` double(20,2) DEFAULT NULL COMMENT '基础价格',
  `order_discount_share` double(20,2) DEFAULT NULL COMMENT '订单级别促销所分摊的折扣价',
  `quantity_as_qualifier` bigint(20) DEFAULT NULL COMMENT '作为促销提供者所占的数量',
  `quantity_discounted` bigint(20) DEFAULT NULL COMMENT '作为促销接受者所占的数量',
  `raw_total_price` double(20,2) DEFAULT NULL COMMENT '未执行促销前的订单商品净额',
  `sale_price` double(20,2) DEFAULT NULL COMMENT '订单商品售价',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `scp_item_price_adj` */

DROP TABLE IF EXISTS `scp_item_price_adj`;

CREATE TABLE `scp_item_price_adj` (
  `id` varchar(40) NOT NULL COMMENT '主键id',
  `item_price_id` varchar(40) DEFAULT NULL COMMENT '商品价格对象id',
  `adjustment_id` varchar(40) DEFAULT NULL COMMENT '调价记录id',
  `sequence` tinyint(4) DEFAULT NULL COMMENT '序列',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `scp_order` */

DROP TABLE IF EXISTS `scp_order`;

CREATE TABLE `scp_order` (
  `id` varchar(40) NOT NULL COMMENT '主键id',
  `profile_id` varchar(40) NOT NULL COMMENT '用户id',
  `branch_company_id` varchar(80) NOT NULL COMMENT '分公司ID',
  `branch_company_arehouse` varchar(200) DEFAULT NULL COMMENT '出货仓',
  `franchisee_id` varchar(80) NOT NULL COMMENT '加盟商ID',
  `franchisee_store_id` varchar(80) NOT NULL COMMENT '加盟店ID',
  `order_type` varchar(80) DEFAULT NULL COMMENT '订单类型',
  `state` smallint(6) DEFAULT NULL COMMENT '订单总状态',
  `state_detail` varchar(500) DEFAULT NULL COMMENT '订单描述',
  `order_state` varchar(20) DEFAULT NULL COMMENT '订单状态',
  `shipping_state` varchar(20) DEFAULT NULL COMMENT '配送状态',
  `payment_state` varchar(20) DEFAULT NULL COMMENT '支付状态',
  `submit_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  `creation_time` timestamp NULL DEFAULT NULL COMMENT '创建时间',
  `last_modified_time` timestamp NULL DEFAULT NULL COMMENT '最后修改时间',
  `completed_time` timestamp NULL DEFAULT NULL COMMENT '完成时间',
  `price_info` varchar(40) DEFAULT NULL COMMENT '订单价格信息',
  `created_by_order_id` varchar(40) DEFAULT NULL COMMENT '当前订单附属的主订单id',
  `sales_channel` varchar(100) DEFAULT NULL COMMENT '销售渠道（如：主站、团购、抢购）',
  `agent_id` varchar(40) DEFAULT NULL COMMENT '客服id号（电话中心坐席id）',
  `site_id` varchar(100) DEFAULT NULL COMMENT '订单提交时所属站点',
  `description` varchar(500) DEFAULT NULL COMMENT '订单的描述',
  `version` bigint(19) DEFAULT NULL COMMENT '订单版本号',
  `invoice_info` varchar(40) DEFAULT NULL COMMENT '订单的发票信息',
  `shipping_group` varchar(40) DEFAULT NULL COMMENT '订单的配送信息',
  `payment_group` varchar(40) DEFAULT NULL COMMENT '订单的支付信息',
  `cancel_reason` varchar(250) DEFAULT NULL,
  `interactive_pending_res` tinyint(1) DEFAULT 0 COMMENT '订单与际联接口交互待回应标记',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `scp_order_items` */

DROP TABLE IF EXISTS `scp_order_items`;

CREATE TABLE `scp_order_items` (
  `id` varchar(40) NOT NULL COMMENT '主键id',
  `order_id` varchar(40) DEFAULT NULL COMMENT '购物车id',
  `commerce_item_id` varchar(40) DEFAULT NULL COMMENT '购物车商品id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `scp_order_log` */

DROP TABLE IF EXISTS `scp_order_log`;

CREATE TABLE `scp_order_log` (
  `id` bigint(40) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` varchar(40) DEFAULT NULL COMMENT '订单id',
  `order_state` smallint(6) DEFAULT NULL COMMENT '订单状态',
  `create_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '操作时间',
  `operater` varchar(20) DEFAULT NULL COMMENT '操作人',
  `description` varchar(200) DEFAULT NULL COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=188 DEFAULT CHARSET=utf8;

/*Table structure for table `scp_order_payments` */

DROP TABLE IF EXISTS `scp_order_payments`;

CREATE TABLE `scp_order_payments` (
  `id` varchar(40) NOT NULL COMMENT '主键id',
  `order_id` varchar(40) DEFAULT NULL COMMENT '订单id',
  `payment_group_id` varchar(40) DEFAULT NULL COMMENT 'payment group id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `scp_order_price` */

DROP TABLE IF EXISTS `scp_order_price`;

CREATE TABLE `scp_order_price` (
  `id` varchar(40) NOT NULL COMMENT '主键id',
  `raw_subtotal` double(20,2) DEFAULT NULL COMMENT '未执行任何折扣的净额',
  `amount` double(20,2) DEFAULT NULL COMMENT '计算结果最终总额',
  `currency_code` varchar(20) DEFAULT NULL COMMENT '货币种类code',
  `discount_amount` double(20,2) DEFAULT NULL COMMENT '折扣总额',
  `final_reason_code` varchar(200) DEFAULT NULL COMMENT '价格计算终止原因',
  `manual_adjustment_total` double(20,2) DEFAULT NULL COMMENT '手工调价总额',
  `shipping` double(20,2) DEFAULT NULL COMMENT '运费',
  `total` double(20,2) DEFAULT NULL COMMENT '总额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `scp_order_price_adj` */

DROP TABLE IF EXISTS `scp_order_price_adj`;

CREATE TABLE `scp_order_price_adj` (
  `id` varchar(40) NOT NULL COMMENT '主键id',
  `order_price_id` varchar(40) DEFAULT NULL COMMENT '订单价格对象id',
  `adjustment_id` varchar(40) DEFAULT NULL COMMENT '调价记录id',
  `sequence` tinyint(4) DEFAULT NULL COMMENT '序列',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `scp_payment_config` */

DROP TABLE IF EXISTS `scp_payment_config`;

CREATE TABLE `scp_payment_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `pay_type` varchar(50) NOT NULL COMMENT '支付类型',
  `app_id` varchar(50) DEFAULT NULL COMMENT 'APP ID',
  `merc_id` varchar(50) DEFAULT NULL COMMENT '商户ID',
  `sign_key` varchar(100) DEFAULT NULL COMMENT '签名key',
  `public_key` varchar(1000) DEFAULT NULL COMMENT '私钥',
  `private_key` varchar(3000) DEFAULT NULL COMMENT '私钥',
  `sign_type` varchar(30) DEFAULT NULL COMMENT '签名类型',
  `pay_server` varchar(150) NOT NULL COMMENT '支付host',
  `status` varchar(20) DEFAULT NULL COMMENT '配置状态',
  `pre_pay_api` varchar(150) DEFAULT NULL COMMENT '预支付网关',
  `refund_api` varchar(150) DEFAULT NULL COMMENT '退款网关',
  `query_pay_status_api` varchar(150) DEFAULT NULL COMMENT '查询支付状态网关',
  `query_refund_status_api` varchar(150) DEFAULT NULL COMMENT '查询退款状态网关',
  `asyn_notify_url` varchar(150) DEFAULT NULL COMMENT '支付回调url',
  `cert_password` varchar(150) DEFAULT NULL COMMENT 'CA 密码',
  `cert_path` varchar(150) DEFAULT NULL COMMENT 'CA文件路径',
  `max_try_refund_count` int(11) DEFAULT NULL COMMENT '最大尝试退款次数',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pay_type` (`pay_type`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

/*Table structure for table `scp_payment_group` */

DROP TABLE IF EXISTS `scp_payment_group`;

CREATE TABLE `scp_payment_group` (
  `id` varchar(40) NOT NULL COMMENT '主键id',
  `order_id` varchar(40) NOT NULL COMMENT '订单id',
  `amount` double(20,2) DEFAULT NULL COMMENT '当前支付方式所需支付总额',
  `currency_code` varchar(20) DEFAULT NULL COMMENT '货币种类编码',
  `amount_authorized` double(20,2) DEFAULT NULL COMMENT '当前支付方式所授权总额',
  `amount_credited` double(20,2) DEFAULT NULL COMMENT '当前支付方式(原路)返回的金额',
  `amount_debited` double(20,2) DEFAULT NULL COMMENT '当前支付方式已经扣款的总额',
  `credit_status` varchar(200) DEFAULT NULL COMMENT '原路返回的状态履历',
  `debit_status` varchar(200) DEFAULT NULL COMMENT '扣款履历',
  `payment_method` varchar(200) DEFAULT NULL COMMENT '当前支付方法',
  `trans_num` varchar(80) DEFAULT NULL COMMENT '交易号',
  `special_Instructions` varchar(200) DEFAULT NULL COMMENT '当前支付方式的特殊说明',
  `state` smallint(6) DEFAULT NULL COMMENT '当前支付方式的状态',
  `state_detail` varchar(200) DEFAULT NULL COMMENT '状态细节',
  `channel` varchar(20) DEFAULT NULL COMMENT '支付渠道',
  `pay_date` timestamp NULL DEFAULT NULL COMMENT '支付完成时间',
  `pay_record_no` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `scp_payment_record` */

DROP TABLE IF EXISTS `scp_payment_record`;

CREATE TABLE `scp_payment_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `pay_no` varchar(50) NOT NULL COMMENT '支付流水',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `total_fee` decimal(10,4) NOT NULL COMMENT '订单金额(元)',
  `pay_status` varchar(20) NOT NULL COMMENT '支付状态',
  `request_from` varchar(60) NOT NULL COMMENT '请求来源',
  `pay_type_code` varchar(50) NOT NULL COMMENT '支付类型',
  `pay_type_name` varchar(50) NOT NULL COMMENT '支付类型名称',
  `pay_way_code` varchar(50) NOT NULL COMMENT '支付方式',
  `pay_way_name` varchar(50) NOT NULL COMMENT '支付方式名',
  `pay_trade_no` varchar(150) DEFAULT NULL COMMENT '支付流水号',
  `nonce_str` varchar(32) DEFAULT NULL COMMENT '32位随机字符串',
  `pay_account` varchar(50) DEFAULT NULL COMMENT '支付账号',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `pay_no` (`pay_no`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8;

/*Table structure for table `scp_price_adjustment` */

DROP TABLE IF EXISTS `scp_price_adjustment`;

CREATE TABLE `scp_price_adjustment` (
  `id` varchar(40) NOT NULL COMMENT '主键id',
  `adjustment` double(20,2) DEFAULT NULL COMMENT '调价单条数额',
  `adjustment_description` varchar(200) DEFAULT NULL COMMENT '调价描述',
  `pricing_model` varchar(40) DEFAULT NULL COMMENT '关联的促销',
  `quantity_adjusted` bigint(20) DEFAULT NULL COMMENT '调价数量',
  `total_adjustment` double(20,2) DEFAULT NULL COMMENT '调价总额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `scp_refund` */

DROP TABLE IF EXISTS `scp_refund`;

CREATE TABLE `scp_refund` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `pay_trade_no` varchar(50) NOT NULL COMMENT '支付流水',
  `pay_no` varchar(50) NOT NULL COMMENT '支付订单号',
  `order_no` varchar(50) NOT NULL COMMENT '订单号',
  `refund_no` varchar(50) DEFAULT NULL COMMENT '退款单号',
  `refund_trade_no` varchar(50) DEFAULT NULL COMMENT '退款流水号',
  `refund_amount` decimal(10,4) NOT NULL COMMENT '退款金额(元)',
  `reason` varchar(200) DEFAULT NULL COMMENT '退款原因',
  `status` varchar(20) NOT NULL COMMENT '状态',
  `request_user_id` varchar(50) DEFAULT NULL COMMENT '申请退款人ID',
  `request_user_name` varchar(50) DEFAULT NULL COMMENT '申请退款人名字',
  `approved_time` datetime DEFAULT NULL COMMENT '审核通过时间',
  `approved_operator_id` varchar(50) DEFAULT NULL COMMENT '审核通过操作人ID',
  `approved_operator_name` varchar(50) DEFAULT NULL COMMENT '审核通过操作人名字',
  `refund_time` datetime DEFAULT NULL COMMENT '退款完成时间',
  `refund_operator_id` varchar(50) DEFAULT NULL COMMENT '退款操作人ID',
  `refund_operator_name` varchar(50) DEFAULT NULL COMMENT '退款操作人名字',
  `try_refund_count` int(11) DEFAULT '0' COMMENT '尝试退款次数',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `total_paied_amount` decimal(10,4) NOT NULL,
  `pay_type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `refund_no` (`refund_no`),
  UNIQUE KEY `refund_trade_no` (`refund_trade_no`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

/*Table structure for table `scp_refund_records` */

DROP TABLE IF EXISTS `scp_refund_records`;

CREATE TABLE `scp_refund_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `refund_id` int(11) NOT NULL COMMENT '退款记录ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `operator_id` varchar(50) DEFAULT NULL COMMENT '退款操作人ID',
  `operator_name` varchar(50) DEFAULT NULL COMMENT '退款操作人名字',
  `pre_status` varchar(20) NOT NULL COMMENT '操作前状态',
  `current_status` varchar(20) NOT NULL COMMENT '操作后状态',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=67 DEFAULT CHARSET=utf8;

/*Table structure for table `scp_shipping_group` */

DROP TABLE IF EXISTS `scp_shipping_group`;

CREATE TABLE `scp_shipping_group` (
  `id` varchar(40) NOT NULL COMMENT '主键id',
  `actual_ship_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '实际发货时间',
  `description` varchar(40) DEFAULT NULL COMMENT '配送方式描述（如:普通快递、EMS）',
  `shipping_price_Info` varchar(40) DEFAULT NULL COMMENT '运费的价格信息',
  `ship_on_date` date DEFAULT NULL COMMENT '指定发货时间',
  `type` varchar(80) DEFAULT NULL COMMENT '当前配送方式的java对象全路径',
  `shipping_method` varchar(500) DEFAULT NULL COMMENT '配送方式（普通快递、EMS）',
  `special_instructions` varchar(200) DEFAULT NULL COMMENT '特殊说明',
  `state` smallint(6) DEFAULT NULL COMMENT '配送方式的状态说明',
  `state_detail` varchar(500) DEFAULT NULL COMMENT '配送方式详情',
  `submit_date` timestamp NULL DEFAULT NULL COMMENT '配送方式提交时间',
  `province` varchar(20) DEFAULT NULL COMMENT '省',
  `city` varchar(20) DEFAULT NULL COMMENT '市',
  `district` varchar(50) DEFAULT NULL COMMENT '区',
  `detail_address` varchar(100) DEFAULT NULL COMMENT '详细地址',
  `postcode` varchar(10) DEFAULT NULL COMMENT '邮编',
  `consignee_name` varchar(20) DEFAULT NULL COMMENT '收货人',
  `cellphone` varchar(15) DEFAULT NULL COMMENT '手机',
  `shipping_no` varchar(40) DEFAULT NULL COMMENT '运单号',
  `estimated_arrival_date` date DEFAULT NULL COMMENT '预计到达日期',
  `deliveryer` varchar(40) DEFAULT NULL COMMENT '送货人',
  `deliveryer_phone` varchar(40) DEFAULT NULL COMMENT '送货人联系方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `scp_shipping_price` */

DROP TABLE IF EXISTS `scp_shipping_price`;

CREATE TABLE `scp_shipping_price` (
  `id` varchar(40) NOT NULL COMMENT '主键id',
  `amount` double(20,2) DEFAULT NULL COMMENT '购物车商品的价格计算结果',
  `currency_code` varchar(20) DEFAULT NULL COMMENT '货币种类代码',
  `final_reason_code` varchar(200) DEFAULT NULL COMMENT '价格计算终止原因',
  `raw_shipping` double(20,2) DEFAULT NULL COMMENT '未执行减免的运费净额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `scp_shipping_price_adj` */

DROP TABLE IF EXISTS `scp_shipping_price_adj`;

CREATE TABLE `scp_shipping_price_adj` (
  `id` varchar(40) NOT NULL COMMENT '主键id',
  `shipping_price_id` varchar(40) DEFAULT NULL COMMENT '运费价格对象id',
  `adjustment_id` varchar(40) DEFAULT NULL COMMENT '调价记录id',
  `sequence` tinyint(4) DEFAULT NULL COMMENT '序列',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `scp_user_contact_info` */

DROP TABLE IF EXISTS `scp_user_contact_info`;

CREATE TABLE `scp_user_contact_info` (
  `id` varchar(40) NOT NULL COMMENT '主键id',
  `user_id` varchar(40) DEFAULT NULL COMMENT '用户id',
  `province` varchar(20) DEFAULT NULL COMMENT '省',
  `city` varchar(20) DEFAULT NULL COMMENT '市',
  `district` varchar(50) DEFAULT NULL COMMENT '区',
  `detail_address` varchar(100) DEFAULT NULL COMMENT '详细地址',
  `contact_user` varchar(20) DEFAULT NULL COMMENT '联系人',
  `phone_num` varchar(15) DEFAULT NULL COMMENT '手机号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Table structure for table `shipping_method` */

DROP TABLE IF EXISTS `shipping_method`;

CREATE TABLE `shipping_method` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(40) NOT NULL COMMENT '物流名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

/*Table structure for table `tran_data` */

DROP TABLE IF EXISTS `tran_data`;

CREATE TABLE `tran_data` (
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
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;

