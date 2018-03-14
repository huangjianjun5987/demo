

ALTER TABLE scp_promotion  ADD COLUMN `create_date` datetime DEFAULT NULL COMMENT '创建时间';

ALTER TABLE scp_promotion  ADD COLUMN `create_user_id` varchar(11) DEFAULT NULL COMMENT '创建人id';

ALTER TABLE scp_promotion  ADD COLUMN `publish_date` datetime DEFAULT NULL COMMENT '发布时间';

ALTER TABLE scp_promotion  ADD COLUMN `publish_user_id` varchar(255) DEFAULT NULL COMMENT '发布人id';


DROP TABLE IF EXISTS scp_coupon_purchase_record;
CREATE TABLE scp_coupon_purchase_record (
  `id` bigint(20) NOT NULL auto_increment COMMENT 'ID',
  `commerce_item_id` bigint(20) DEFAULT NULL COMMENT 'commerce item id',
  `order_id` varchar(40) DEFAULT NULL COMMENT 'order id',
  `quantity` bigint(10) DEFAULT NULL COMMENT 'quantity',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券购买记录';