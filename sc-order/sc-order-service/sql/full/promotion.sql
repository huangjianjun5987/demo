DROP TABLE IF EXISTS `scp_promotion`;
CREATE TABLE `scp_promotion` (
  `id` varchar(40) NOT NULL COMMENT '主键id',
  `status` varchar(10) NOT NULL COMMENT '状态',
  `promotion_type` varchar(20) NOT NULL COMMENT '促销类型（order_promo,item_promo,shipping_promo）',
  `promotion_name` varchar(200) DEFAULT NULL COMMENT '活动名称',
  `promotion_discription` varchar(500) DEFAULT NULL COMMENT '折扣描述',
  `start_date` datetime DEFAULT NULL COMMENT '开始时间',
  `end_date` datetime DEFAULT null COMMENT '结束时间',
  `discount_type` varchar(20) NOT NULL COMMENT '折扣类型(stander,percentage)',
  `quanify_amount` DECIMAL(10,2) DEFAULT NULL COMMENT '条件金额',
  `discount` DECIMAL(10,2) DEFAULT NULL COMMENT '折扣',
  `note` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销活动';

DROP TABLE IF EXISTS `scp_promo_companies`;
CREATE TABLE `scp_promo_companies` (
  `promo_id` varchar(40) NOT NULL COMMENT '促销id',
  `company_id` varchar(40) NOT NULL COMMENT '分公司ID',
  `company_name` varchar(40) NOT NULL COMMENT '分公司名称',
  PRIMARY KEY (`promo_id`,`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销区域';


DROP TABLE IF EXISTS `scp_promo_categories`;
CREATE TABLE `scp_promo_categories` (
  `promo_id` varchar(40) NOT NULL COMMENT '促销id',
  `category_id` varchar(40) NOT NULL COMMENT '分类ID',
  `category_name` varchar(50) NOT NULL COMMENT '类名',
  `category_level` int DEFAULT NULL COMMENT '分类级别',
  PRIMARY KEY (`promo_id`,`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销品类';


DROP TABLE IF EXISTS `scp_promo_stores`;
CREATE TABLE `scp_promo_stores` (
  `promo_id` varchar(40) NOT NULL COMMENT '促销id',
  `store_id` varchar(3000) NOT NULL COMMENT '门店ID',
  PRIMARY KEY (`promo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销指定门店';


DROP TABLE IF EXISTS `scp_promo_records`;
CREATE TABLE `scp_promo_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `promo_id` varchar(40) NOT NULL COMMENT '促销id',
  `order_id` varchar(40) NOT NULL COMMENT '订单ID',
  `discount` DECIMAL(10,2) DEFAULT NULL COMMENT '折扣金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='（促销活动）订单记录';