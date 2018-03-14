
/*
  主键自增修改除scp_order
*/
ALTER TABLE invoice_info CHANGE invoice_id invoice_id BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键id'; 
ALTER TABLE invoice_info AUTO_INCREMENT=1000000;

ALTER TABLE item_loc_soh CHANGE id id BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键id'; 
ALTER TABLE item_loc_soh AUTO_INCREMENT=1000000;

ALTER TABLE scp_commerce_item CHANGE id id BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键id'; 
ALTER TABLE scp_commerce_item AUTO_INCREMENT=1000000;
ALTER TABLE scp_commerce_item MODIFY COLUMN item_price_info BIGINT(19) COMMENT '购物车商品级别的价格信息';

ALTER TABLE scp_invoice CHANGE invoice_id invoice_id BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键id'; 
ALTER TABLE scp_invoice AUTO_INCREMENT=1000000;

ALTER TABLE scp_item_price CHANGE id id BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键id'; 
ALTER TABLE scp_item_price AUTO_INCREMENT=1000000;

ALTER TABLE scp_item_price_adj CHANGE id id BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键id'; 
ALTER TABLE scp_item_price_adj MODIFY COLUMN item_price_id BIGINT(19) COMMENT '商品价格对象id';
ALTER TABLE scp_item_price_adj MODIFY COLUMN adjustment_id BIGINT(19) COMMENT '调价记录id';
ALTER TABLE scp_item_price_adj AUTO_INCREMENT=1000000;

ALTER TABLE scp_order MODIFY COLUMN price_info BIGINT(19) COMMENT '订单价格信息';
ALTER TABLE scp_order MODIFY COLUMN invoice_info BIGINT(19) COMMENT '订单的发票信息';

ALTER TABLE scp_order_items CHANGE id id  BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键id';
ALTER TABLE scp_order_items MODIFY COLUMN commerce_item_id BIGINT(19) COMMENT '购物车商品id';
ALTER TABLE scp_order_items AUTO_INCREMENT=1000000;

ALTER TABLE scp_order_payments CHANGE id id  BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键id'; 
ALTER TABLE scp_order_payments AUTO_INCREMENT=1000000;

ALTER TABLE scp_order_price CHANGE id id  BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键id'; 
ALTER TABLE scp_order_price AUTO_INCREMENT=1000000;

ALTER TABLE scp_order_price_adj CHANGE id id  BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键id';
ALTER TABLE scp_order_price_adj MODIFY COLUMN order_price_id BIGINT(19) COMMENT '订单价格对象id';
ALTER TABLE scp_order_price_adj MODIFY COLUMN adjustment_id BIGINT(19) COMMENT '调价记录id';
ALTER TABLE scp_order_price_adj AUTO_INCREMENT=1000000;

ALTER TABLE scp_price_adjustment CHANGE id id  BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键id'; 
ALTER TABLE scp_price_adjustment AUTO_INCREMENT=1000000;

ALTER TABLE scp_shipping_group MODIFY COLUMN shipping_price_Info BIGINT(19) COMMENT '运费的价格信息';

ALTER TABLE scp_shipping_price CHANGE id id  BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键id'; 
ALTER TABLE scp_shipping_price AUTO_INCREMENT=1000000;

ALTER TABLE scp_shipping_price_adj CHANGE id id  BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT '主键id';
ALTER TABLE scp_shipping_price_adj MODIFY COLUMN shipping_price_id BIGINT(19) COMMENT '运费价格对象id';
ALTER TABLE scp_shipping_price_adj MODIFY COLUMN adjustment_id BIGINT(19) COMMENT '调价记录id';
ALTER TABLE scp_shipping_price_adj AUTO_INCREMENT=1000000;







