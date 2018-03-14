ALTER TABLE scp_order_price ADD COLUMN `is_coupon_activity_reverted` TINYINT DEFAULT 0 COMMENT '是否已经回退过优惠券';
