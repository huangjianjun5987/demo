ALTER TABLE scp_commerce_item ADD COLUMN `free_item_json` LONGTEXT NULL COMMENT '赠送的商品信息';
ALTER TABLE scp_commerce_item  ADD COLUMN `abnormal_goods` TINYINT DEFAULT 0 COMMENT '商品是否异常';
ALTER TABLE scp_commerce_item  ADD COLUMN `abnormal_resonse` VARCHAR(255) DEFAULT NULL COMMENT '商品异常原因';
