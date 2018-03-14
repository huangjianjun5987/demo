
/*==============================================================*/
/* table: 支付配置表                                            */
/*==============================================================*/
create table scp_payment_config
(
   id               int not null AUTO_INCREMENT,
   create_time      datetime not null comment '创建时间',
   pay_type         varchar(50) UNIQUE not null comment '支付类型',
   app_id           varchar(50)  comment 'APP ID',
   merc_id          varchar(50)  comment '商户ID',
   sign_key         varchar(100) comment '签名key',
   public_key       varchar(1000)  comment '私钥',
   private_key      varchar(3000) comment '私钥',
   sign_type        varchar(30) comment '签名类型',
   pay_server       varchar(150) not NULL comment '支付host',
   status   varchar(20) COMMENT '配置状态',
   pre_pay_api   varchar(150) comment '预支付网关',
   refund_api   varchar(150) comment '退款网关',
   query_pay_status_api varchar(150) comment '查询支付状态网关',
   query_refund_status_api varchar(150) comment '查询退款状态网关',
   asyn_notify_url varchar(150) comment '支付回调url',
   cert_password  varchar(150) comment 'CA 密码',
   cert_path  varchar(150) comment 'CA文件路径',
   max_try_refund_count INT COMMENT '最大尝试退款次数',
   primary key (id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;


/*==============================================================*/
/* table: 支付订单表                                            */
/*==============================================================*/
CREATE TABLE scp_payment_record(
   id               int not null AUTO_INCREMENT ,
   create_time      datetime not null comment '创建时间',
   pay_no  varchar(50) UNIQUE not null comment '支付流水',
   order_no varchar(50) not null comment '订单号',
   total_fee NUMERIC(10,4) NOT NULL COMMENT '订单金额(元)',
   pay_status varchar(20) not null COMMENT '支付状态',
   request_from VARCHAR(60) not NULL COMMENT '请求来源',
   pay_type_code VARCHAR(50) NOT NULL COMMENT '支付类型',
   pay_type_name VARCHAR(50) NOT NULL COMMENT '支付类型名称',
   pay_way_code VARCHAR(50) NOT NULL COMMENT '支付方式',
   pay_way_name VARCHAR(50) NOT NULL COMMENT '支付方式名',
   pay_trade_no VARCHAR(150) COMMENT '支付流水号',
   nonce_str VARCHAR(32) COMMENT '32位随机字符串',
   pay_account VARCHAR(50) COMMENT '支付账号',
   remark VARCHAR(200) COMMENT '备注',
   primary key (id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* table: 退款表                                            */
/*==============================================================*/
CREATE TABLE scp_refund (
   id INT NOT NULL AUTO_INCREMENT,
   create_time DATETIME NOT NULL COMMENT '创建时间',
   pay_trade_no  varchar(50)  not null comment '支付流水',
   pay_no  varchar(50)  not null comment '支付订单号',
   pay_type VARCHAR(50) NOT NULL COMMENT '支付类型',
   order_no  varchar(50)  not null comment '订单号',
   refund_no  varchar(50) UNIQUE comment '退款单号',
   refund_trade_no varchar(50) UNIQUE comment '退款流水号',
   total_paied_amount NUMERIC(10,4)  not null comment '总共支付金额',
   refund_amount NUMERIC(10,4)  not null comment '退款金额(元)',
   reason varchar(200) comment '退款原因',
   status varchar(20) not null comment '状态',
   request_user_id varchar(50) comment '申请退款人ID',
   request_user_name varchar(50)  comment '申请退款人名字',
   approved_time  DATETIME comment '审核通过时间',
   approved_operator_id varchar(50) comment '审核通过操作人ID',
   approved_operator_name varchar(50) comment '审核通过操作人名字',
   refund_time  DATETIME comment '退款完成时间',
   refund_operator_id varchar(50) comment '退款操作人ID',
   refund_operator_name varchar(50)  comment '退款操作人名字',
   try_refund_count INT DEFAULT 0 COMMENT '尝试退款次数',
   remark  VARCHAR(200) COMMENT '备注',
   primary key (id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;

/*==============================================================*/
/* table: 退款操作记录                                          */
/*==============================================================*/
CREATE TABLE scp_refund_records (
   id INT NOT NULL AUTO_INCREMENT,
   refund_id   INT NOT NULL COMMENT '退款记录ID',
   create_time DATETIME NOT NULL COMMENT '创建时间',
   operator_id varchar(50) comment '退款操作人ID',
   operator_name varchar(50)  comment '退款操作人名字',
   pre_status varchar(20) NOT NULL COMMENT '操作前状态',
   current_status varchar(20) NOT NULL COMMENT '操作后状态',
   remark  VARCHAR(200) COMMENT '备注',
  primary key (id)
)ENGINE=INNODB DEFAULT CHARSET=utf8;