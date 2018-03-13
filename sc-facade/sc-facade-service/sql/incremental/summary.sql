alter table warehouse_logic_info add column branch_company_id varchar(11);
alter table warehouse_logic_info add column branch_company_code varchar(255);
alter table warehouse_logic_info add column branch_company_name varchar(255);
alter table warehouse_logic_info add column logic_type int(2) COMMENT '逻辑仓类型';
alter table warehouse_logic_info add column storage_type int(2) COMMENT '存储类型(1:常温;2:冷藏;3冷冻)';
ALTER TABLE `prod_purchase_info`
	CHANGE COLUMN `product_id` `product_id` VARCHAR(255) NULL DEFAULT NULL COMMENT '商品ID' AFTER `sp_adr_id`;
ALTER TABLE `prod_sell_info`
	CHANGE COLUMN `product_id` `product_id` VARCHAR(255) NULL DEFAULT NULL COMMENT '商品id' AFTER `id`;