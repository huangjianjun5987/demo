CREATE TABLE `sc_wish_list` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `branch_company_id` varchar(40) DEFAULT NULL COMMENT '分公司编码',
  `gb_code` varchar(40) DEFAULT NULL COMMENT '商品国标码',
  `product_code` varchar(40) DEFAULT NULL COMMENT '商品编码',
  `product_name` varchar(500) DEFAULT NULL COMMENT '商品名称',
  `total_quantity` bigint(19) DEFAULT '0' COMMENT '总预订量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `complete_time` datetime DEFAULT NULL COMMENT '完结时间',
  `status` varchar(10) DEFAULT NULL COMMENT '状态，init:待处理， complete:已完成， close：关闭',
  `product_img` varchar(500) DEFAULT NULL COMMENT '商品图片',
  `branch_company_name` varchar(255) DEFAULT NULL COMMENT '子公司名字',
  PRIMARY KEY (`id`),
  UNIQUE KEY `branch_company_id` (`branch_company_id`,`gb_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `sc_wish_detail` (
  `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `wish_list_id` BIGINT(19) NOT NULL COMMENT '心愿单id',
  `store_id` VARCHAR(40) DEFAULT NULL COMMENT '门店编号',
  `franchiser_id` VARCHAR(40) DEFAULT NULL COMMENT '加盟商编号',
  `store_name` VARCHAR(40) DEFAULT NULL COMMENT '门店名称',
  `quantity` BIGINT(19) DEFAULT 0 COMMENT '数量',
  `create_time` DATETIME DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;
