CREATE TABLE scp_provider_shipping_group
(
    id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    shipping_group_id BIGINT NOT NULL,
    sp_id VARCHAR(40) COMMENT '供应商ID',
    sp_no VARCHAR(40) COMMENT '供应商编码',
    sp_name VARCHAR(200) COMMENT '供应商名称',
    sp_adr_id VARCHAR(40) COMMENT '供应商地点ID',
    sp_adr_no VARCHAR(40) COMMENT '供应商地点编码',
    sp_adr_name VARCHAR(1000) COMMENT '供应商地点名称'
);
ALTER TABLE scp_provider_shipping_group COMMENT = '直配订单物流信息扩展';