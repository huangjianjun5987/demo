ALTER TABLE scp_order_give_coupon_to_store_log ADD COLUMN `discount` double(20,2)  NULL COMMENT '打折比率';
ALTER TABLE scp_order_give_coupon_to_store_log ADD COLUMN `give_amount` double(20,2)  NULL COMMENT '返券金额';