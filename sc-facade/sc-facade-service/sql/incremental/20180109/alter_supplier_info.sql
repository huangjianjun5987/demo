

ALTER TABLE supplier_info MODIFY COLUMN `modify_time` timestamp ON UPDATE CURRENT_TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间';