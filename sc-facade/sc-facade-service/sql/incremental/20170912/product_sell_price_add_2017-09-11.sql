-- 商品按箱销售需要执行的sql脚本 yyx  2017-09-11

ALTER TABLE prod_sell_info ADD COLUMN pre_harvest_pin_status INT(1) COMMENT '是否先采后销  1：是  0：否';

ALTER TABLE prod_sell_info ADD COLUMN max_number INT(11) DEFAULT NULL COMMENT '最大销售数量';