ALTER TABLE `scp_promotion`  ADD COLUMN `type` int(1) NULL COMMENT '类型：0 为促销,1为优惠券';

DROP TABLE IF EXISTS `scp_coupons`;
CREATE TABLE `scp_coupons` (
  `id` varchar(40) NOT NULL COMMENT 'ID',
  `total_quantity` bigint(10) DEFAULT NULL COMMENT '发放数量',
  `person_qty` bigint(10) DEFAULT NULL COMMENT '每人领取数量',
  `grant_qty` bigint(10) DEFAULT NULL COMMENT '领取数量',
  `used_qty` bigint(10) DEFAULT NULL COMMENT '使用数量',
  `grant_channel` varchar(20) DEFAULT NULL COMMENT '发放方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券';



DROP TABLE IF EXISTS `scp_coupon_activity`;
CREATE TABLE `scp_coupon_activity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `store_id` varchar(40) DEFAULT NULL COMMENT '门店ID',
  `promo_id` varchar(40) DEFAULT NULL COMMENT '优惠券',
  `activity_date` datetime DEFAULT NULL COMMENT '时间',
  `state` varchar(10) DEFAULT NULL COMMENT '状态(active,used)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券领取记录';




DROP TABLE IF EXISTS `scp_coupon_record`;
CREATE TABLE `scp_coupon_record` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `order_id` varchar(40) DEFAULT NULL COMMENT '订单ID',
  `promo_id` varchar(40) DEFAULT NULL COMMENT '优惠券',
  `discount_amount` double(20,2) DEFAULT NULL COMMENT '折扣',
  `record_date` datetime DEFAULT NULL COMMENT '使用时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券使用记录';















