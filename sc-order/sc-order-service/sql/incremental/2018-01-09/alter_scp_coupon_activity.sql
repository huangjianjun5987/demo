

ALTER TABLE scp_coupon_activity  MODIFY COLUMN `modify_time` timestamp ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间（作废时间）';
