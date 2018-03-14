ALTER TABLE `scp_promotion`  ADD COLUMN `is_superpose_user_discount` TINYINT(1) NULL COMMENT '是否与会员折扣叠加:1是0否';
ALTER TABLE `scp_promotion`  ADD COLUMN `is_superpose_proOrCou_discount` TINYINT(1) NULL DEFAULT 0 COMMENT '是否与促销折扣或者优惠券折扣叠加:1是0否';
UPDATE scp_promotion SET TYPE =0