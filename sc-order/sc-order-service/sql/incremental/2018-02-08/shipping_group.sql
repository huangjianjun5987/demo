ALTER TABLE scp_shipping_group ADD COLUMN shipping_modes VARCHAR(15) DEFAULT 'unified' COMMENT '配送模式';
ALTER TABLE scp_shipping_group ADD COLUMN singed_cert_img VARCHAR(500)COMMENT '签收凭证图片路径';
ALTER TABLE scp_shipping_group ADD COLUMN remarks VARCHAR(500) COMMENT '备注';