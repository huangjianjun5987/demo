ALTER TABLE scp_order ADD COLUMN receipt_no VARCHAR (40) COMMENT '收货单号';
ALTER TABLE scp_order ADD COLUMN purchase_order_no VARCHAR (40) COMMENT '采购单号';
ALTER TABLE scp_order ADD COLUMN `from` VARCHAR (40) DEFAULT 'default' COMMENT '订单产生来源';