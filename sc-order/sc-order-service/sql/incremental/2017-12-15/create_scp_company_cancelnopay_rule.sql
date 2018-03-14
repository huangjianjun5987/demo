CREATE TABLE `scp_company_cancelnopay_rule` (
  `id` int(50) NOT NULL COMMENT '主键id',
  `comany_id` varchar(40) NOT NULL COMMENT '子公司id',
  `company_name` varchar(40) NOT NULL COMMENT '子公司名称',
  `cancel_time` int(80) DEFAULT 24 COMMENT '未付款的时间上限(单位:分钟)',
  `state` smallint(5) DEFAULT 1 COMMENT '生效状态(1:生效,0:失效)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='子公司设置最大的未支付订单时间';