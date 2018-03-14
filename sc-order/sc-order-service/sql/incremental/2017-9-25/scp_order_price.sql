ALTER TABLE `scp_order_price`  ADD COLUMN `user_discount_amount` double(20,2) NULL COMMENT '会员折扣';
ALTER TABLE `scp_order_price`  ADD COLUMN `coupon_discount_amount` double(20,2) NULL COMMENT '优惠券折扣';
ALTER TABLE `scp_price_adjustment`  ADD COLUMN `pricing_model_type` int(2) NULL COMMENT '促销类型';