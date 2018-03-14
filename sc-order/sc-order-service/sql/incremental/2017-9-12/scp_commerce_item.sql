/*修改表结构*/
ALTER TABLE `scp_commerce_item`  ADD COLUMN `sale_quantity` BIGINT(20) NULL COMMENT '销售数量';
ALTER TABLE `scp_commerce_item`  ADD COLUMN `unit_quantity` INT(11) NULL COMMENT '销售内装数';
ALTER TABLE `scp_commerce_item`  ADD COLUMN `sell_full_case` int(2) NULL COMMENT '0-否；1-是';

UPDATE `scp_commerce_item`
SET sale_quantity = quantity
WHERE sale_quantity IS NULL