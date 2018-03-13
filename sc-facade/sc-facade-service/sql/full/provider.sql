DROP TABLE `sp_adr_basic_info`;
DROP TABLE `sp_adr_contact_info`;
DROP TABLE `sp_adr_delivery_info`;
DROP TABLE `sp_bank_info`;
DROP TABLE `sp_basic_info`;
DROP TABLE `sp_license_info`;
DROP TABLE `sp_operating_taxation_info`;
DROP TABLE `sp_sale_region`;
DROP TABLE `supplier_info`;
DROP TABLE `supplier_adr_info`;

/*==============================================================*/
/* Table: "sp_adr_basic_info(供应商地点基础信息表)"                       */
/*==============================================================*/
CREATE TABLE `sp_adr_basic_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `provider_no` varchar(255) DEFAULT NULL COMMENT '供应商地点编码',
  `provider_name` varchar(255) DEFAULT NULL COMMENT '供应商地点名称',
  `goods_arrival_cycle` int(11) DEFAULT NULL COMMENT '到货周期',
  `org_id` varchar(50) DEFAULT NULL COMMENT '分公司ID',
  `grade` int(11) DEFAULT NULL COMMENT '供应地点级别:1:生产厂家;2:批发商;3:经销商;4:代销商;5:其他',
  `opera_status` int(2) DEFAULT '0' COMMENT '供应商地点经营状态0:禁用:1:启用',
  `settlement_period` int(11) DEFAULT NULL COMMENT '账期: 0:周结；1：半月结；2：月结；',
  `pay_condition` int(2) DEFAULT NULL COMMENT '付款条件(1:票到七天,2:票到十五天,3:票到三十天)',
  `belong_area` int(11) DEFAULT NULL COMMENT '所属区域',
  `belong_area_name` varchar(50) DEFAULT NULL COMMENT '所属区域名字',
  `pay_type` int(11) DEFAULT NULL COMMENT '供应商付款方式：0：网银，1：银行转账，2：现金，3：支票',
  `audit_person` varchar(30) DEFAULT NULL COMMENT '审核人姓名',
  `audit_date` datetime DEFAULT NULL COMMENT '审核时间',
  `modify_id` int(11) DEFAULT NULL COMMENT '修改信息id,如有修改操作关联到修改记录',
  `status` int(2) DEFAULT '0' COMMENT '0：原记录, 1：修改记录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: "sp_adr_contact_info(供应商地点联系信息表)"                     */
/*==============================================================*/
CREATE TABLE `sp_adr_contact_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `provider_name` varchar(255) DEFAULT NULL COMMENT '供应商姓名',
  `provider_phone` varchar(255) DEFAULT NULL COMMENT '供应商手机',
  `provider_email` varchar(255) DEFAULT NULL COMMENT '供应商邮箱',
  `purchase_name` varchar(255) DEFAULT NULL COMMENT '采购员姓名',
  `purchase_phone` varchar(255) DEFAULT NULL COMMENT '采购员电话',
  `purchase_email` varchar(255) DEFAULT NULL COMMENT '采购员邮箱',
  `modify_id` int(11) DEFAULT NULL COMMENT '修改信息id,如有修改操作关联到修改记录',
  `status` int(2) DEFAULT '0' COMMENT '0：原记录, 1：修改记录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=72 DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: "sp_adr_delivery_info(供应商地点送货信息)"                     */
/*==============================================================*/
CREATE TABLE `sp_adr_delivery_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sp_adr_id` int(11) DEFAULT NULL COMMENT '供应商地点id',
  `w_id` int(11) DEFAULT NULL COMMENT '仓库ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: "sp_bank_info(供应商银行信息表)"                              */
/*==============================================================*/
CREATE TABLE `sp_bank_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_name` varchar(255) DEFAULT NULL COMMENT '开户名',
  `open_bank` varchar(255) DEFAULT NULL COMMENT '开户行',
  `bank_account` varchar(255) DEFAULT NULL COMMENT '银行账号',
  `bank_account_license` varchar(255) DEFAULT NULL COMMENT '银行开户许可证电子版url',
  `bank_loc_province_code` varchar(30) DEFAULT NULL COMMENT '省份code',
  `bank_loc_province` varchar(50) DEFAULT NULL COMMENT '省份',
  `bank_loc_city_code` varchar(30) DEFAULT NULL COMMENT '城市code',
  `bank_loc_city` varchar(50) DEFAULT NULL COMMENT '城市',
  `bank_loc_county_code` varchar(30) DEFAULT NULL COMMENT '地区code',
  `bank_loc_county` varchar(50) DEFAULT NULL COMMENT '地区',
  `invoice_head` varchar(255) DEFAULT NULL COMMENT '供应商发票抬头',
  `modify_id` int(11) DEFAULT NULL COMMENT '修改信息id,如有修改操作关联到修改记录',
  `status` int(2) DEFAULT '0' COMMENT '0：原记录, 1：修改记录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: "sp_basic_info(供应商基本信息表)"                             */
/*==============================================================*/
CREATE TABLE `sp_basic_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `sp_no` varchar(255) DEFAULT NULL COMMENT '供应商编号',
  `grade` int(11) DEFAULT NULL COMMENT '供应商等级:1:战略供应商;2:核心供应商;3:可替代供应商',
  `settled_time` datetime DEFAULT NULL COMMENT '入驻时间',
  `modify_id` int(11) DEFAULT NULL COMMENT '修改信息id,如有修改操作关联到修改记录',
  `status` int(2) DEFAULT '0' COMMENT '0：原记录, 1：修改记录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: "sp_license_info(供应商公司营业执照信息)"                        */
/*==============================================================*/
CREATE TABLE `sp_license_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `regist_licence_number` varchar(255) DEFAULT NULL COMMENT '注册号(营业执照号)',
  `legal_representative` varchar(255) DEFAULT NULL COMMENT '法定代表人',
  `legal_repre_card_num` varchar(255) DEFAULT NULL COMMENT '法人身份证号',
  `legal_repre_card_pic1` varchar(255) DEFAULT NULL COMMENT '法人身份证电子版1',
  `legal_repre_card_pic2` varchar(255) DEFAULT NULL COMMENT '法人身份证电子版2',
  `license_loc_province_code` varchar(30) DEFAULT NULL COMMENT '省份code',
  `license_loc_province` varchar(50) DEFAULT NULL COMMENT '省份',
  `license_loc_city_code` varchar(30) DEFAULT NULL COMMENT '城市code',
  `license_loc_city` varchar(50) DEFAULT NULL COMMENT '城市',
  `license_loc_county_code` varchar(30) DEFAULT NULL COMMENT '地区code',
  `license_loc_county` varchar(80) DEFAULT NULL COMMENT '地区',
  `license_address` varchar(255) DEFAULT NULL COMMENT '营业执照详细地址',
  `establish_date` datetime DEFAULT NULL COMMENT '成立日期',
  `start_date` datetime DEFAULT NULL COMMENT '营业开始日期',
  `end_date` datetime DEFAULT NULL COMMENT '营业结束日期',
  `perpetual_management` int(2) DEFAULT '0' COMMENT '永续经营(0:否,1:是)',
  `registered_capital` decimal(10,2) DEFAULT NULL COMMENT '注册资本',
  `business_scope` varchar(500) DEFAULT NULL COMMENT '经营范围',
  `regist_licence_pic` varchar(255) DEFAULT NULL COMMENT '营业执照副本电子版',
  `guarantee_money` decimal(10,2) DEFAULT NULL COMMENT '保证金',
  `modify_id` int(11) DEFAULT NULL COMMENT '修改信息id,如有修改操作关联到修改记录',
  `status` int(2) DEFAULT '0' COMMENT '0：原记录, 1：修改记录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: "sp_operating_taxation_info(供应商经营及税务信息)"              */
/*==============================================================*/
CREATE TABLE `sp_operating_taxation_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_loc_province_code` varchar(30) DEFAULT NULL COMMENT '省份code',
  `company_loc_province` varchar(60) DEFAULT NULL COMMENT '省份',
  `company_loc_city_code` varchar(30) DEFAULT NULL COMMENT '城市code',
  `company_loc_city` varchar(60) DEFAULT NULL COMMENT '城市',
  `company_loc_county_code` varchar(30) DEFAULT NULL COMMENT '地区code',
  `company_loc_county` varchar(60) DEFAULT NULL COMMENT '地区',
  `company_detail_address` varchar(255) DEFAULT NULL COMMENT '公司详细地址',
  `registration_certificate` varchar(255) DEFAULT NULL COMMENT '商标注册证/受理通知书',
  `reg_cer_expiring_date` datetime DEFAULT NULL COMMENT '商标注册证/受理通知书到期日',
  `quality_identification` varchar(255) DEFAULT NULL COMMENT '食品安全认证',
  `qua_ide_expiring_date` datetime DEFAULT NULL COMMENT '食品安全认证到期日',
  `food_business_license` varchar(255) DEFAULT NULL COMMENT '食品经营许可证',
  `business_license_expiring_date` datetime DEFAULT NULL COMMENT '食品经营许可证到期日期',
  `general_taxpayer_qualifi_certi` varchar(255) DEFAULT NULL COMMENT '一般纳税人资格证电子版',
  `taxpayer_cert_expiring_date` datetime DEFAULT NULL COMMENT '一般纳税人资格证到期日',
  `modify_id` int(11) DEFAULT NULL COMMENT '修改信息id,如有修改操作关联到修改记录',
  `status` int(2) DEFAULT '0' COMMENT '0：原记录, 1：修改记录',
  `failed_reason` varchar(500) DEFAULT NULL COMMENT '失败原因',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: "sp_sale_region(供应商销售区域表)"                            */
/*==============================================================*/
CREATE TABLE `sp_sale_region` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `json` longtext COMMENT 'json树形结构数据(以json键值对方式方式存储)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: "supplier_adr_info(供应商地点主表)"                          */
/*==============================================================*/
CREATE TABLE `supplier_adr_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `parent_id` varchar(20) NOT NULL COMMENT '供应商主表ID',
  `basic_id` int(11) DEFAULT NULL COMMENT '基本信息ID',
  `cont_id` int(11) DEFAULT NULL COMMENT '联系信息ID',
  `status` int(2) DEFAULT '0' COMMENT '供应商状态:0：制单, 1:已提交,2:已审核,3:已拒绝,4:修改中',
  `failed_reason` varchar(500) DEFAULT NULL COMMENT '失败原因',
  `audit_user_id` varchar(20) DEFAULT NULL COMMENT '审核人ID',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_time` datetime DEFAULT NULL,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_user` varchar(20) DEFAULT NULL,
  `modify_user` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=56 DEFAULT CHARSET=utf8;


/*==============================================================*/
/* Table: "supplier_info(供应商主表)"                          */
/*==============================================================*/
CREATE TABLE `supplier_info` (
  `id` varchar(255) NOT NULL,
  `basic_id` int(11) NOT NULL COMMENT '供应商基本信息ID',
  `operat_taxat_id` int(11) NOT NULL COMMENT '供应商经营税务信息ID',
  `bank_id` int(11) NOT NULL COMMENT '供应商银行信息',
  `license_id` int(11) NOT NULL COMMENT '营业执照信息ID',
  `sr_id` int(11) DEFAULT NULL COMMENT '销售区域ID',
  `status` int(2) DEFAULT '0' COMMENT '供应商状态:0：制单, 1:已提交,2:已审核,3:已拒绝,4:修改中',
  `failed_reason` varchar(500) DEFAULT NULL COMMENT '失败原因',
  `audit_user_id` varchar(20) DEFAULT NULL COMMENT '审核人ID',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  `create_time` datetime DEFAULT NULL,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `create_user` varchar(20) DEFAULT NULL,
  `modify_user` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*==============================================================*/
/* Table: "store_permission(门店是否允许进入)"                     */
/*==============================================================*/

CREATE TABLE `store_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表 id',
  `franchisee_id` varchar(255) DEFAULT NULL COMMENT '加盟商id',
  `store_id` varchar(255) DEFAULT NULL COMMENT '门店id',
  `accessibled` int(2) DEFAULT NULL COMMENT '0-可进入 1-不可进入',
  PRIMARY KEY (`id`)
);




