/*修改表结构*/
ALTER TABLE `scp_commerce_item`  ADD COLUMN `sale_quantity` BIGINT(20) NULL COMMENT '销售数量';
ALTER TABLE `scp_commerce_item`  ADD COLUMN `unit_quantity` INT(11) NULL COMMENT '销售内装数';
ALTER TABLE `scp_commerce_item`  ADD COLUMN `sell_full_case` int(2) NULL COMMENT '0-否；1-是';

CREATE TABLE `mq_msg_log` (
  `msg_id` varchar(40) NOT NULL COMMENT '消息主键',
  `order_id` varchar(40)  COMMENT '消息主键',
  `msg_body` VARCHAR(10000) COMMENT '消息内容',
  `msg_type` VARCHAR(40) NOT  NULL COMMENT '消息类型',
  `process_result` TINYINT NOT NULL DEFAULT 0 COMMENT '处理结果',
  `comment` VARCHAR(300) COMMENT '备注',
  `reciv_date` DATETIME  NOT NULL COMMENT '消息接受时间',
  PRIMARY KEY (`msg_id`)
)