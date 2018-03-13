DROP TABLE IF EXISTS `prod_sell_info_log`;

CREATE TABLE `prod_sell_info_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `branch_company_id` varchar(255) DEFAULT NULL COMMENT '子公司ID',
  `product_Id` varchar(255) DEFAULT NULL COMMENT '商品id',
  `lowest_price` decimal(20,2) DEFAULT NULL COMMENT '最低价',
  `operate` varchar(255) DEFAULT NULL COMMENT '操作',
  `operate_date` datetime DEFAULT NULL COMMENT '操作时间',
  `operate_user_id` varchar(255) DEFAULT NULL COMMENT '操作用户id',
  `operate_user_name` varchar(255) DEFAULT NULL COMMENT '操作用户名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


