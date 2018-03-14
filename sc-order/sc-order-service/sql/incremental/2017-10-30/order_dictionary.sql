INSERT INTO order_dictionary VALUES ('holdUpAmountThreshold','20000');
ALTER TABLE scp_order ADD COLUMN `pending_split` int(1) NOT NULL DEFAULT 0 COMMENT '是否在拆单：0 ,1';