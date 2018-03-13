ALTER TABLE `pm_purchase_order` MODIFY COLUMN `status`  int(2) NULL DEFAULT 0 COMMENT '状态:0:草稿;1:待审核;2:已审核;3:已拒绝;4:已关闭;5:已取消' AFTER `purchase_order_type`,
ALTER TABLE `pm_purchase_order` MODIFY COLUMN `business_mode`  int(2) NULL DEFAULT 0 COMMENT '经营模式:0:经销;1:代销;2:寄售' AFTER `pay_condition`,
ALTER TABLE `pm_purchase_order` ADD COLUMN `sale_order_id`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '销售订单id' AFTER `purchasing_mode`,
ALTER TABLE `pm_purchase_order` ADD COLUMN `sp_accept_status`  int(2) NULL COMMENT '供应商接单状态:0未接单;1:已接单' AFTER `sale_order_id`;

ALTER TABLE `pm_purchase_order_item` ADD COLUMN `groups`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '部类' AFTER `return_type`,
ALTER TABLE `pm_purchase_order_item` ADD COLUMN `dept`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '大类' AFTER `groups`,
ALTER TABLE `pm_purchase_order_item` ADD COLUMN `classs`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '中类' AFTER `dept`,
ALTER TABLE `pm_purchase_order_item` ADD COLUMN `subclass`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小类' AFTER `classs`,
ALTER TABLE `pm_purchase_order_item` ADD COLUMN `brand`  varchar(40) NULL COMMENT '品牌' AFTER `subclass`;

ALTER TABLE `pm_purchase_receipt` ADD COLUMN `sale_order_id`  varchar(40) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '销售订单id' AFTER `modify_user_id`;
ALTER TABLE `pm_purchase_receipt` ADD COLUMN `ticket_url`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '签收小票URL' AFTER `sale_order_id`;



