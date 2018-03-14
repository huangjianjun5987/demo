

ALTER TABLE scp_coupon_activity  ADD COLUMN `modify_user` varchar(20) DEFAULT NULL COMMENT '修改人id';

ALTER TABLE scp_coupon_activity  ADD COLUMN `modify_time` timestamp DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间（作废时间）';

ALTER TABLE scp_coupon_activity  MODIFY COLUMN `state` varchar(10) DEFAULT NULL COMMENT '状态(active:已领取,used:已使用,canceled:已作废)';


