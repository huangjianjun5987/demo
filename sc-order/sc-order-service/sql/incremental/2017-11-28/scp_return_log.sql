CREATE TABLE `scp_return_log` (
`id`  int(20) NOT NULL AUTO_INCREMENT COMMENT '主键id' ,
`return_id`  varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '退货单id' ,
`return_state`  smallint(100) NULL DEFAULT NULL COMMENT '退货单总状态(1:待确认，2:已确认，3，已完成，4：已取消' ,
`operater`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作人id' ,
`create_date`  datetime NULL DEFAULT NULL COMMENT '操作时间' ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
COMMENT='退货确认日志记录表'
AUTO_INCREMENT=1
ROW_FORMAT=DYNAMIC
;

