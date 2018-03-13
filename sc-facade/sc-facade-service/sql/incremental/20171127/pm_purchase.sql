ALTER TABLE pm_purchase_order MODIFY COLUMN `purchase_order_type`  INT(2) NULL DEFAULT 0 COMMENT '类型:0:普通采购单;1:赠品采购;2:促销釆购';
ALTER TABLE pm_purchase_order ADD COLUMN `business_mode`  INT(2) NULL DEFAULT 0 COMMENT '经营模式:0:经销;1:代销';
ALTER TABLE pm_purchase_order ADD COLUMN `purchasing_mode`  INT(2) NULL DEFAULT 0 COMMENT '采购模式:0:地采;1:统采';