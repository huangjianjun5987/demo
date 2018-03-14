ALTER TABLE scp_payment_group  ADD COLUMN `comment` VARCHAR(1000) NULL COMMENT '备注';

ALTER TABLE scp_payment_group  ADD COLUMN `last_operator` VARCHAR(30) NULL COMMENT '最近操作人';
ALTER TABLE scp_payment_group  ADD COLUMN `pay_account` VARCHAR(30) NULL COMMENT '支付账号';