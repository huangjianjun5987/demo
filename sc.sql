/*
SQLyog Ultimate v11.26 (32 bit)
MySQL - 5.7.18-log : Database - scsit
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`scsit` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `scsit`;

/*Table structure for table `ad_plan` */

DROP TABLE IF EXISTS `ad_plan`;

CREATE TABLE `ad_plan` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `plan_name` varchar(255) DEFAULT NULL COMMENT '方案名称',
  `pic_name1` varchar(255) DEFAULT NULL COMMENT '图片1名称',
  `pic_url1` varchar(255) DEFAULT NULL COMMENT '图片1存储url',
  `link_url1` varchar(255) DEFAULT NULL COMMENT '链接地址1',
  `pic_name2` varchar(255) DEFAULT NULL COMMENT '图片2名称',
  `link_url2` varchar(255) DEFAULT NULL COMMENT '链接地址2',
  `pic_url2` varchar(255) DEFAULT NULL COMMENT '图片2存储url',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `status` int(11) DEFAULT NULL COMMENT '1:启用,0:停用',
  `modify_time` datetime DEFAULT NULL COMMENT '更改时间',
  `modify_user_id` varchar(50) DEFAULT NULL COMMENT '修改者用户id',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建者id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='广告方案表';

/*Data for the table `ad_plan` */

insert  into `ad_plan`(`id`,`plan_name`,`pic_name1`,`pic_url1`,`link_url1`,`pic_name2`,`link_url2`,`pic_url2`,`create_time`,`status`,`modify_time`,`modify_user_id`,`create_user_id`) values (3,'达到','的','/group1/M00/01/AC/rB4KPFob6IeAMa8-AAAvgEjkxtE638.jpg','http://sitxcsc.yatang.com.cn/','的','http://sitxcsc.yatang.com.cn/','/group1/M00/01/45/rB4KPlmk11yAdQXDAAALVE4-wl0993.png','2017-08-29 10:53:54',1,'2017-11-27 18:27:43',NULL,'10018');

/*Table structure for table `category_goods_order` */

DROP TABLE IF EXISTS `category_goods_order`;

CREATE TABLE `category_goods_order` (
  `pk_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `id` varchar(255) DEFAULT NULL COMMENT '商品编号',
  `name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `first_category_id` varchar(255) DEFAULT NULL COMMENT '一级分类id',
  `second_category_id` varchar(255) DEFAULT NULL COMMENT '二级分类id',
  `third_category_id` varchar(255) DEFAULT NULL COMMENT '三级分类id',
  `first_category_name` varchar(255) DEFAULT NULL COMMENT '一级分类名称',
  `second_category_name` varchar(255) DEFAULT NULL COMMENT '二级分类名称',
  `third_category_name` varchar(255) DEFAULT NULL COMMENT '三级分类名称',
  `order_num` int(11) DEFAULT NULL COMMENT '排序号',
  PRIMARY KEY (`pk_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='分类下商品排序表（暂时保留）';

/*Data for the table `category_goods_order` */

/*Table structure for table `cf_area_warehouse` */

DROP TABLE IF EXISTS `cf_area_warehouse`;

CREATE TABLE `cf_area_warehouse` (
  `id` bigint(21) NOT NULL AUTO_INCREMENT,
  `province` varchar(20) DEFAULT NULL COMMENT '省份',
  `warehouse_code` varchar(20) DEFAULT NULL COMMENT '仓库编码',
  `branch_company_id` varchar(20) DEFAULT NULL COMMENT '分公司id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `province` (`province`,`warehouse_code`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;

/*Data for the table `cf_area_warehouse` */

insert  into `cf_area_warehouse`(`id`,`province`,`warehouse_code`,`branch_company_id`) values (1,'四川','WHH01','10000'),(2,'四川省','WHH01','10000'),(3,'重庆','WLL','10004'),(4,'重庆市','WLL','10004'),(5,'湖北','YT901','10014'),(6,'湖北省','YT901','10014'),(7,'湖南','903200451','10014'),(8,'湖南省','903200451','10004'),(9,'广东','905200461','10021'),(10,'广东省','905200461','10021'),(11,'山东','906200471','10007'),(12,'山东省','906200471','10007'),(13,'浙江','908200551','10020'),(14,'浙江省','908200551','10020'),(15,'河北','909200441','10008'),(16,'河北省','909200441','10008'),(17,'河南','911200561','10012'),(18,'河南省','911200561','10012'),(19,'陕西','912200521','10010'),(20,'陕西省','912200521','10010'),(21,'北京','907200431','10006'),(22,'北京省','907200431','10006'),(23,'江西','913200611','100045'),(24,'江西省','913200611','100045'),(25,'香港特别行政区','WLL','10004'),(26,'香港','WLL','10004'),(27,'台湾特别行政区','YT901','10000'),(28,'台湾省','YT901','10000'),(29,'台湾','YT901','10000'),(30,'西藏自治区','TJXQ','10003'),(31,'西藏','TJXQ','10003');

/*Table structure for table `franchisee_payment` */

DROP TABLE IF EXISTS `franchisee_payment`;

CREATE TABLE `franchisee_payment` (
  `id` bigint(40) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` varchar(40) DEFAULT NULL COMMENT '订单编号',
  `order_type` varchar(80) DEFAULT NULL COMMENT '订单类型',
  `shipping_state` varchar(20) DEFAULT NULL COMMENT '物流状态',
  `payment_state` varchar(20) DEFAULT NULL COMMENT '支付状态',
  `branch_company_id` varchar(80) NOT NULL COMMENT '分公司ID',
  `franchisee_id` varchar(80) NOT NULL COMMENT '加盟商ID',
  `franchisee_name` varchar(80) NOT NULL COMMENT '加盟商名称',
  `branch_company_arehouse_code` varchar(200) DEFAULT NULL COMMENT '出货仓编号',
  `branch_company_arehouse_name` varchar(200) DEFAULT NULL COMMENT '出货仓名称',
  `pay_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '付款时间',
  `pay_amount` double(20,3) DEFAULT NULL COMMENT '付款金额',
  `pay_method` varchar(200) DEFAULT NULL COMMENT '付款方式',
  `trans_no` varchar(80) DEFAULT NULL COMMENT '付款流水号',
  `refund_date` timestamp NULL DEFAULT NULL COMMENT '退款日期',
  `refund_amount` double(20,3) DEFAULT NULL COMMENT '退款金额',
  `refund_method` varchar(200) DEFAULT NULL COMMENT '退款方式',
  `refund_trade_no` varchar(80) DEFAULT NULL COMMENT '退款流水号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `franchisee_payment` */

/*Table structure for table `franchisee_settlement` */

DROP TABLE IF EXISTS `franchisee_settlement`;

CREATE TABLE `franchisee_settlement` (
  `id` bigint(40) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `order_id` varchar(40) DEFAULT NULL COMMENT '订单编号',
  `order_type` varchar(80) DEFAULT NULL COMMENT '订单类型',
  `shipping_state` varchar(20) DEFAULT NULL COMMENT '物流状态',
  `payment_state` varchar(20) DEFAULT NULL COMMENT '支付状态',
  `branch_company_id` varchar(80) NOT NULL COMMENT '分公司ID',
  `franchisee_id` varchar(80) NOT NULL COMMENT '加盟商ID',
  `franchisee_name` varchar(80) NOT NULL COMMENT '加盟商名称',
  `branch_company_arehouse_code` varchar(200) DEFAULT NULL COMMENT '出货仓编号',
  `branch_company_arehouse_name` varchar(200) DEFAULT NULL COMMENT '出货仓名称',
  `groups_code` varchar(40) DEFAULT NULL COMMENT '出货仓编码',
  `groups_name` varchar(40) DEFAULT NULL COMMENT '出货仓名称',
  `dept_code` varchar(40) DEFAULT NULL COMMENT '大类编码',
  `dept_name` varchar(40) DEFAULT NULL COMMENT '大类名称',
  `product_code` varchar(40) DEFAULT NULL COMMENT '商品编码',
  `product_id` varchar(40) DEFAULT NULL COMMENT '商品id',
  `product_name` varchar(40) DEFAULT NULL COMMENT '商品名称',
  `sale_tax` double(20,3) DEFAULT NULL COMMENT '销项税率',
  `completed_amount` double(20,3) DEFAULT NULL COMMENT '签收金额',
  `completed_mul_amount` double(20,3) DEFAULT NULL COMMENT '签收差额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `franchisee_settlement` */

/*Table structure for table `im_abnormal_log` */

DROP TABLE IF EXISTS `im_abnormal_log`;

CREATE TABLE `im_abnormal_log` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '自增主键id',
  `mq_message` varchar(10000) DEFAULT NULL COMMENT '消息信息',
  `error_message` varchar(10000) DEFAULT NULL COMMENT '异常原因',
  `name` varchar(500) DEFAULT NULL COMMENT '业务名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8 COMMENT='入库单消息异常记录表';

/*Data for the table `im_abnormal_log` */

insert  into `im_abnormal_log`(`id`,`mq_message`,`error_message`,`name`) values (1,'{\"purchaseOrderNo\":\"1709130001\"}','查询收货单为空 {\"code\":\"200\",\"errorMessage\":\"请求成功\",\"resultObject\":{},\"success\":true}','CGTS'),(2,'{\"logisticsName\":\"logisticName\",\"orderType\":\"CGRK\",\"currentStatus\":\"FULFILLED\",\"operatorTime\":\"2017-09-11 17:56:10\",\"ownerCode\":\"YTXC\",\"dataType\":1,\"expressCode\":\"expressCode\",\"orderCode\":\"QXRUTEST100011\",\"remark\":\"remark\",\"operatorCode\":\"operatorCode\",\"operatorName\":\"operatorName\"}','调用dubbo服务出错，原因：反正就是出错了','CGJS'),(3,'{\"logisticsName\":\"logisticName\",\"orderType\":\"CGRK\",\"currentStatus\":\"FULFILLED\",\"operatorTime\":\"2017-09-11 17:56:10\",\"ownerCode\":\"YTXC\",\"dataType\":1,\"expressCode\":\"expressCode\",\"orderCode\":\"QXRUTEST100011\",\"remark\":\"remark\",\"operatorCode\":\"operatorCode\",\"operatorName\":\"operatorName\"}','调用dubbo服务出错，原因：反正就是出错了','CGJS'),(4,'{\"logisticsName\":\"logisticName\",\"orderType\":\"CGRK\",\"currentStatus\":\"FULFILLED\",\"operatorTime\":\"2017-09-11 17:56:10\",\"ownerCode\":\"YTXC\",\"dataType\":1,\"expressCode\":\"expressCode\",\"orderCode\":\"QXRUTEST100011\",\"remark\":\"remark\",\"operatorCode\":\"operatorCode\",\"operatorName\":\"operatorName\"}','调用dubbo服务出错，原因：反正就是出错了','CGJS'),(5,'{\"logisticsName\":\"logisticName\",\"orderType\":\"CGRK\",\"currentStatus\":\"FULFILLED\",\"operatorTime\":\"2017-09-11 17:56:10\",\"ownerCode\":\"YTXC\",\"dataType\":1,\"expressCode\":\"expressCode\",\"orderCode\":\"QXRUTEST100011\",\"remark\":\"remark\",\"operatorCode\":\"operatorCode\",\"operatorName\":\"operatorName\"}','调用dubbo服务出错，原因：反正就是出错了','CGJS'),(6,'{\"logisticsName\":\"logisticName\",\"orderType\":\"CGRK\",\"currentStatus\":\"FULFILLED\",\"operatorTime\":\"2017-09-11 17:56:10\",\"ownerCode\":\"YTXC\",\"dataType\":1,\"expressCode\":\"expressCode\",\"orderCode\":\"QXRUTEST100011\",\"remark\":\"remark\",\"operatorCode\":\"operatorCode\",\"operatorName\":\"operatorName\"}','调用dubbo服务出错，原因：反正就是出错了','CGJS'),(7,'{\"orderType\":\"CGRK\",\"orderCodeWMS\":\"SI20170819000126\",\"currentStatus\":\"FULFILLED\",\"operatorTime\":\"2017-08-19 00:00:00\",\"ownerCode\":\"YTXC\",\"dataType\":1,\"expressCode\":\"expressCode\",\"orderCode\":\"RK20170819001\",\"remark\":\"remark\",\"operatorCode\":\"operatorCode\",\"operatorName\":\"operatorName\"}','调用dubbo服务出错，原因：反正就是出错了','CGJS'),(8,'{\"orderType\":\"CGRK\",\"orderCodeWMS\":\"SI20170819000126\",\"currentStatus\":\"FULFILLED\",\"operatorTime\":\"2017-08-19 00:00:00\",\"ownerCode\":\"YTXC\",\"dataType\":1,\"expressCode\":\"expressCode\",\"orderCode\":\"RK20170819001\",\"remark\":\"remark\",\"operatorCode\":\"operatorCode\",\"operatorName\":\"operatorName\"}','调用dubbo服务出错，原因：反正就是出错了','CGJS'),(9,'{\"orderType\":\"CGRK\",\"orderCodeWMS\":\"SI20170819000126\",\"currentStatus\":\"FULFILLED\",\"operatorTime\":\"2017-08-19 00:00:00\",\"ownerCode\":\"YTXC\",\"dataType\":1,\"expressCode\":\"expressCode\",\"orderCode\":\"RK20170819001\",\"remark\":\"remark\",\"operatorCode\":\"operatorCode\",\"operatorName\":\"operatorName\"}','调用dubbo服务出错，原因：反正就是出错了','CGJS'),(10,'{\"orderType\":\"CGRK\",\"currentStatus\":\"FULFILLED\",\"operatorTime\":\"2017-08-12 11:13:56\",\"ownerCode\":\"YTXC\",\"dataType\":1,\"orderCode\":\"1708120003\"}','调用dubbo服务出错，原因：反正就是出错了','CGJS'),(11,'{\"orderType\":\"CGRK\",\"currentStatus\":\"FULFILLED\",\"operatorTime\":\"2017-08-12 11:13:56\",\"ownerCode\":\"YTXC\",\"dataType\":1,\"orderCode\":\"1708120003\"}','调用dubbo服务出错，原因：反正就是出错了','CGJS'),(12,'{\"orderType\":\"CGRK\",\"currentStatus\":\"FULFILLED\",\"operatorTime\":\"2017-08-12 11:13:56\",\"ownerCode\":\"YTXC\",\"dataType\":1,\"orderCode\":\"1708120003\"}','调用dubbo服务出错，原因：调用GLINK查询收货单明细没有查询到数据','CGJS'),(13,'{\"orderType\":\"CGRK\",\"orderCodeWMS\":\"RK1709150001\",\"currentStatus\":\"FULFILLED\",\"operatorTime\":\"2017-09-15 14:09:30\",\"ownerCode\":\"20041\",\"dataType\":1,\"orderCode\":\"1709150001\"}','调用dubbo服务出错，原因：反正就是出错了','CGJS'),(14,'{\"orderType\":\"CGRK\",\"orderCodeWMS\":\"RK1709150001\",\"currentStatus\":\"FULFILLED\",\"operatorTime\":\"2017-09-15 14:09:30\",\"ownerCode\":\"20041\",\"dataType\":1,\"orderCode\":\"1709150001\"}','调用dubbo服务出错，原因：反正就是出错了','CGJS'),(15,'{\"orderType\":\"CGRK\",\"orderCodeWMS\":\"RK1709150002\",\"currentStatus\":\"ACCEPT\",\"operatorTime\":\"2017-09-15 14:16:22\",\"ownerCode\":\"20041\",\"dataType\":1,\"orderCode\":\"1709150002\"}','调用dubbo服务出错，原因：服务器错误','CGJS'),(16,'{\"orderType\":\"CGRK\",\"orderCodeWMS\":\"RK1709150002\",\"currentStatus\":\"ACCEPT\",\"operatorTime\":\"2017-09-15 14:16:22\",\"ownerCode\":\"20041\",\"dataType\":1,\"orderCode\":\"1709150002\"}','调用dubbo服务出错，原因：服务器错误','CGJS'),(17,'{\"orderType\":\"CGRK\",\"orderCodeWMS\":\"RK1709150002\",\"currentStatus\":\"FULFILLED\",\"operatorTime\":\"2017-09-15 14:16:22\",\"ownerCode\":\"20041\",\"dataType\":1,\"orderCode\":\"1709150002\"}','调用dubbo服务出错，原因：反正就是出错了','CGJS');

/*Table structure for table `im_adjustment` */

DROP TABLE IF EXISTS `im_adjustment`;

CREATE TABLE `im_adjustment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `adjustment_no` varchar(255) DEFAULT NULL COMMENT '单据编号',
  `type` int(2) NOT NULL COMMENT '类型:0:物流丢失（WLDS）、1:仓库报溢（CKBY）、2:仓库报损（CKBS）、3:业务调增（YWTZ）、4:业务调减（YWTJ）、5:仓库同步调增（CKTBZ）、6:仓库同步调减（CKTBJ）',
  `status` int(2) NOT NULL COMMENT '状态:0:制单;1:已生效',
  `adjustment_time` datetime DEFAULT NULL COMMENT '调整日期',
  `warehouse_code` varchar(20) DEFAULT NULL COMMENT '调整仓库,逻辑仓',
  `total_quantity` bigint(19) DEFAULT NULL COMMENT '调整数量合计',
  `total_adjustment_cost` decimal(20,3) DEFAULT NULL COMMENT '调整成本合计',
  `description` varchar(500) DEFAULT NULL COMMENT '备注',
  `external_bill_no` varchar(255) DEFAULT NULL COMMENT '外部单据号',
  `failed_reason` varchar(500) DEFAULT NULL COMMENT '失败原因',
  `audit_user_name` varchar(20) DEFAULT NULL COMMENT '修改人名称',
  `audit_user_id` varchar(20) DEFAULT NULL COMMENT '批准人ID',
  `audit_time` datetime DEFAULT NULL COMMENT '批准日期',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL,
  `create_user_name` varchar(20) DEFAULT NULL COMMENT '创建人名称',
  `create_user_id` varchar(20) DEFAULT NULL COMMENT '创建人id',
  `modify_user_id` varchar(20) DEFAULT NULL,
  `modify_user_name` varchar(19) DEFAULT NULL COMMENT '修改人名称',
  `branch_company_id` varchar(50) DEFAULT NULL COMMENT '逻辑仓所属子公司id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `external_bill_no` (`external_bill_no`),
  KEY `INDEX_TEST` (`branch_company_id`,`status`,`type`)
) ENGINE=InnoDB AUTO_INCREMENT=1000070 DEFAULT CHARSET=utf8 COMMENT='库存调整单表';

/*Data for the table `im_adjustment` */

insert  into `im_adjustment`(`id`,`adjustment_no`,`type`,`status`,`adjustment_time`,`warehouse_code`,`total_quantity`,`total_adjustment_cost`,`description`,`external_bill_no`,`failed_reason`,`audit_user_name`,`audit_user_id`,`audit_time`,`create_time`,`modify_time`,`create_user_name`,`create_user_id`,`modify_user_id`,`modify_user_name`,`branch_company_id`) values (1000001,'100000003',0,1,NULL,'YT901',10,'160.800','库存调整主表1234',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'10000'),(1000002,'100000003',0,1,NULL,'YT901',10,'39.100','sajhgd ajshgd ajhs dh jas d hsad ajhsdjahsgd jadg jhasdjahs gdjdjashgdjags jhdgajsdg jahsgd ja gsjdhajhsdjhasdjh ajdja jjdagjshgdjagdjagsdjhgasjhdgasguyqwtriqroeywfuyrwqtffgwjush ni yi d osihdoahsfd iafsia hf','1001171',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'10000'),(1000006,'100000003',0,1,NULL,'YT901',10,'39.100',NULL,'INSST170819001052',NULL,NULL,NULL,NULL,'2017-08-19 11:08:00',NULL,NULL,NULL,NULL,NULL,'10000'),(1000034,NULL,4,1,'2017-08-19 11:08:00','YT901',-10,'-39.100',NULL,'checkCode2',NULL,NULL,NULL,NULL,'2017-09-11 09:00:24',NULL,'scm_admin',NULL,NULL,NULL,'10000'),(1000049,NULL,1,1,'2017-09-11 11:08:00','YT901',30,'141.000',NULL,'checkCode3',NULL,NULL,NULL,NULL,'2017-09-11 14:50:16',NULL,'scm_admin',NULL,NULL,NULL,'10000'),(1000051,NULL,2,1,'2017-09-11 15:08:00','YT901',-30,'-270.000',NULL,'checkCode4',NULL,NULL,NULL,NULL,'2017-09-11 15:28:16',NULL,'scm_admin',NULL,NULL,NULL,'10000'),(1000052,NULL,3,1,'2017-09-11 15:08:00','YT901',-20,'35.000',NULL,'checkCode5',NULL,NULL,NULL,NULL,'2017-09-11 16:09:16',NULL,'scm_admin',NULL,NULL,NULL,'10000'),(1000053,NULL,3,1,'2017-09-11 15:08:00','YT901',80,'505.000',NULL,'checkCode6',NULL,NULL,NULL,NULL,'2017-09-11 16:09:16',NULL,'scm_admin',NULL,NULL,NULL,'10000'),(1000054,NULL,4,1,'2017-08-19 11:08:00','YT901',14,'189.000',NULL,'checkCode7',NULL,NULL,NULL,NULL,'2017-09-11 17:14:16',NULL,'scm_admin',NULL,NULL,NULL,'10000'),(1000055,NULL,6,1,'2017-08-19 11:08:00','YT901',14,'189.000',NULL,'checkCode8',NULL,NULL,NULL,NULL,'2017-09-11 17:17:16',NULL,'scm_admin',NULL,NULL,NULL,'10000'),(1000056,NULL,5,1,'2017-09-12 11:08:00','YT901',22,'297.000',NULL,'checkCode9',NULL,NULL,NULL,NULL,'2017-09-12 10:54:16',NULL,'scm_admin',NULL,NULL,NULL,'10000'),(1000060,NULL,4,1,'2017-09-27 19:39:54','901200411',-20,'20.450','订单调整',NULL,NULL,NULL,NULL,NULL,'2017-09-27 19:39:54',NULL,NULL,NULL,NULL,NULL,'10000'),(1000061,NULL,3,1,'2017-09-27 20:45:13','901200411',1389,NULL,'调整',NULL,NULL,NULL,NULL,NULL,'2017-09-27 20:45:13',NULL,NULL,NULL,NULL,NULL,'10000'),(1000062,NULL,0,1,'2017-09-12 11:08:00','YT901',-30,'-117.300',NULL,'CK2017-10-11 20:29:40',NULL,NULL,NULL,NULL,'2017-10-11 20:30:01',NULL,'scm_admin',NULL,NULL,NULL,'10021'),(1000063,NULL,4,1,'2017-09-12 11:08:00','YT901',-10,'-315.900',NULL,'CK2018-1-9 11:9:54',NULL,NULL,NULL,NULL,'2018-01-09 11:10:03',NULL,'scm_admin',NULL,NULL,NULL,'10000'),(1000065,NULL,4,1,'2017-09-12 11:08:00','YT901',-10,'-315.900',NULL,'5DE67C2763834C2D97BD4A3CB2A5E1A5',NULL,NULL,NULL,NULL,'2018-01-09 11:31:02',NULL,'scm_admin',NULL,NULL,NULL,'10000'),(1000066,NULL,3,1,'2018-01-09 11:41:47','WLL',10,'330.300',NULL,'INWLL180109000003-1159050_1',NULL,NULL,NULL,NULL,'2018-01-09 13:40:39',NULL,'scm_admin',NULL,NULL,NULL,'10004'),(1000067,NULL,3,1,'2018-01-09 11:41:47','WLL',10,'330.300',NULL,'INWLL180109000003-1159050_I',NULL,NULL,NULL,NULL,'2018-01-09 13:47:37',NULL,'scm_admin',NULL,NULL,NULL,'10004'),(1000068,NULL,2,1,'2017-09-12 11:08:00','YT901',-10,'-314.200',NULL,'C4186390D9D94DF8883B25ABD9598F5A',NULL,NULL,NULL,NULL,'2018-02-05 14:06:18',NULL,'scm_admin',NULL,NULL,NULL,'10000'),(1000069,NULL,2,1,'2017-09-12 11:08:00','YT901',-10,'-314.200',NULL,'C4186390D9D94DF8883B25ABD9598F1C',NULL,NULL,NULL,NULL,'2018-02-05 14:23:07',NULL,'scm_admin',NULL,NULL,NULL,'10000');

/*Table structure for table `im_adjustment_item` */

DROP TABLE IF EXISTS `im_adjustment_item`;

CREATE TABLE `im_adjustment_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `adjustment_id` varchar(255) NOT NULL COMMENT '库存调整单表',
  `product_id` varchar(255) DEFAULT NULL COMMENT '商品id',
  `product_code` varchar(20) DEFAULT NULL COMMENT '商品编号',
  `product_name` varchar(20) DEFAULT NULL COMMENT '商品名称',
  `av_cost` decimal(20,3) DEFAULT NULL COMMENT '平均成本',
  `stock_on_hand` bigint(19) DEFAULT '0' COMMENT '现有库存',
  `quantity` bigint(19) DEFAULT NULL COMMENT '调整数量',
  `adjustment_cost` decimal(20,3) DEFAULT NULL COMMENT '调整成本额',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `create_user_id` varchar(20) DEFAULT NULL,
  `modify_user_id` varchar(20) DEFAULT NULL,
  `product_date` datetime DEFAULT NULL COMMENT '商品生产日期',
  `expire_date` datetime DEFAULT NULL COMMENT '商品过期日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8 COMMENT='库存调整单商品表';

/*Data for the table `im_adjustment_item` */

insert  into `im_adjustment_item`(`id`,`adjustment_id`,`product_id`,`product_code`,`product_name`,`av_cost`,`stock_on_hand`,`quantity`,`adjustment_cost`,`create_time`,`modify_time`,`create_user_id`,`modify_user_id`,`product_date`,`expire_date`) values (1,'1000001','61742','1001662','哈哈哈','16.080',40955,10,'160.800',NULL,NULL,NULL,NULL,NULL,NULL),(2,'1000002','xprod347938','1000404','stupid guy','3.910',28867,10,'39.100',NULL,NULL,NULL,NULL,NULL,NULL),(3,'1000003','xprod347938','1000404','stupid dog','3.910',28857,10,'39.100',NULL,NULL,NULL,NULL,NULL,NULL),(4,'1000004','xprod347938','1000404','巴啦啦小魔仙','3.910',28827,10,'39.100',NULL,NULL,NULL,NULL,NULL,NULL),(5,'1000005','xprod347938','1000404',NULL,'3.910',28817,10,'39.100',NULL,NULL,NULL,NULL,NULL,NULL),(6,'1000006','xprod347938','1000404',NULL,'3.910',28807,10,'39.100',NULL,NULL,NULL,NULL,NULL,NULL),(7,'1000007','xprod347938','1000404',NULL,'3.910',28797,10,'39.100',NULL,NULL,NULL,NULL,NULL,NULL),(8,'1000008','xprod347938','1000404',NULL,'3.910',28787,10,'39.100',NULL,NULL,NULL,NULL,NULL,NULL),(9,'1000009','xprod347938','1000404',NULL,'3.910',28787,10,'39.100',NULL,NULL,NULL,NULL,NULL,NULL),(10,'1000012','xprod347938','1000404',NULL,'3.910',28787,10,'39.100',NULL,NULL,NULL,NULL,'2017-09-05 19:37:28','2017-09-05 19:37:28'),(11,'1000013','xprod347938','1000404',NULL,'3.910',28747,-10,'-39.100',NULL,NULL,NULL,NULL,'2017-09-05 19:37:28','2017-09-05 19:37:28'),(12,'1000014','xprod347938','1000404',NULL,NULL,NULL,-10,NULL,NULL,NULL,NULL,NULL,'2017-09-05 19:37:28','2017-09-05 19:37:28'),(13,'1000015','xprod341331','1003603','邯陶釉中彩招财进宝大汤勺','5.200',10,30,'156.000',NULL,NULL,NULL,NULL,NULL,NULL),(14,'1000016','xprod341331','1003603','邯陶釉中彩招财进宝大汤勺','5.200',10,30,'156.000',NULL,NULL,NULL,NULL,NULL,NULL),(15,'1000017','xprod347938','1000404',NULL,NULL,NULL,-10,NULL,NULL,NULL,NULL,NULL,'2017-09-05 19:37:28','2017-09-05 19:37:28'),(16,'1000018','xprod347938','1000404',NULL,NULL,NULL,-10,NULL,NULL,NULL,NULL,NULL,'2017-09-05 19:37:28','2017-09-05 19:37:28'),(17,'1000019','xprod347938','1000404',NULL,NULL,NULL,-10,NULL,NULL,NULL,NULL,NULL,'2017-09-05 19:37:28','2017-09-05 19:37:28'),(18,'1000020','xprod347938','1000404',NULL,'3.910',28747,-10,'-39.100',NULL,NULL,NULL,NULL,'2017-09-05 19:37:28','2017-09-05 19:37:28'),(19,'1000021','xprod347938','1000404',NULL,NULL,NULL,-10,NULL,NULL,NULL,NULL,NULL,'2017-09-05 19:37:28','2017-09-05 19:37:28'),(20,'1000021','xprod347938','1000404',NULL,NULL,NULL,-10,NULL,NULL,NULL,NULL,NULL,'2017-09-05 19:37:28','2017-09-05 19:37:28'),(21,'1000022','xprod347938','1000404',NULL,'3.910',28737,-10,'-39.100',NULL,NULL,NULL,NULL,'2017-09-05 19:37:28','2017-09-05 19:37:28'),(22,'1000022','63647','1001839',NULL,'50.000',1944,-10,'-500.000',NULL,NULL,NULL,NULL,'2017-09-05 19:37:28','2017-09-05 19:37:28'),(23,'1000023','xprod347959','1004947','121',NULL,NULL,179,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(24,'1000023','xprod341320','1003606','邯陶釉中彩招财进宝调羹',NULL,NULL,112,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(25,'1000024','xprod341331','1003603','邯陶釉中彩招财进宝大汤勺1',NULL,NULL,-30,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(26,'1000025','xprod347959','1004947','121',NULL,NULL,179,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(27,'1000025','xprod341320','1003606','邯陶釉中彩招财进宝调羹',NULL,NULL,112,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(28,'1000026','xprod341331','1003603','邯陶釉中彩招财进宝大汤勺1',NULL,NULL,-30,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(29,'1000027','xprod347959','1004947','121',NULL,NULL,179,NULL,NULL,NULL,NULL,NULL,'2017-03-28 00:00:00',NULL),(30,'1000027','xprod341320','1003606','邯陶釉中彩招财进宝调羹',NULL,NULL,112,NULL,NULL,NULL,NULL,NULL,'2017-03-28 00:00:00',NULL),(31,'1000028','xprod341331','1003603','邯陶釉中彩招财进宝大汤勺1',NULL,NULL,-30,NULL,NULL,NULL,NULL,NULL,'2017-03-28 00:00:00',NULL),(32,'1000029','xprod347938','1000404',NULL,'3.910',28727,-10,'-39.100',NULL,NULL,NULL,NULL,'2017-09-05 19:37:28','2017-09-05 19:37:28'),(33,'1000030','xprod347938','1000404',NULL,'3.910',28717,-10,'-39.100',NULL,NULL,NULL,NULL,'2017-09-05 19:37:28','2017-09-05 19:37:28'),(34,'1000031','xprod347938','1000404',NULL,'3.910',28707,-10,'-39.100',NULL,NULL,NULL,NULL,'2017-09-05 19:37:28','2017-09-05 19:37:28'),(35,'1000032','xprod347938','1000404',NULL,'3.910',28697,-10,'-39.100',NULL,NULL,NULL,NULL,'2017-09-05 19:37:28','2017-09-05 19:37:28'),(36,'1000033','xprod347938','1000404',NULL,'3.910',28687,-10,'-39.100',NULL,NULL,NULL,NULL,'2017-09-05 19:37:28','2017-09-05 19:37:28'),(37,'1000034','xprod347938','1000404',NULL,'3.910',28677,-10,'-39.100',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(38,'1000035','xprod347938','1000404',NULL,'3.910',28667,-10,'-39.100',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(39,'1000036','xprod347938','1000404',NULL,'3.910',28657,-10,'-39.100',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(40,'1000037','xprod347938','1000404',NULL,'3.910',28647,-30,'-117.300',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(41,'1000038','xprod347938','1000404',NULL,'3.910',28617,-30,'-117.300',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(42,'1000039','xprod347938','1000404',NULL,'3.910',28587,-30,'-117.300',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(43,'1000040','xprod347938','1000404',NULL,'3.910',28557,-30,'-117.300',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(44,'1000041','xprod347938','1000404',NULL,'3.910',28497,-30,'-117.300',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(45,'1000042','xprod347938','1000404',NULL,'3.910',28467,-30,'-117.300',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(46,'1000043','xprod347938','1000404',NULL,'3.910',28467,-30,'-117.300',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(47,'1000044','xprod347938','1000404',NULL,'3.910',28407,-30,'-117.300',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(48,'1000045','xprod347938','1000404','桃李吉士排面包(110g)','3.910',28377,-30,'-117.300',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(49,'1000046','xprod347725','1000191','老灶花生72g','4.700',150,30,'141.000',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(50,'1000047','xprod347725','1000191','老灶花生72g','4.700',60,-30,'-141.000',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(51,'1000048','xprod347725','1000191','老灶花生72g','4.700',90,30,'141.000',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(52,'1000049','xprod347725','1000191','老灶花生72g','4.700',60,30,'141.000',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(53,'1000050','xprod347725','1000191','老灶花生72g','4.700',90,30,'141.000',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(54,'1000051','xprod347727','1000193','泰禾朵朵吸水杯TH-1017','9.000',120,-30,'-270.000',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(55,'1000052','xprod347727','1000193','泰禾朵朵吸水杯TH-1017','9.000',150,30,'270.000',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(56,'1000052','xprod347725','1000191','老灶花生72g','4.700',40,-50,'-235.000',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(57,'1000053','xprod347725','1000191','老灶花生72g','4.700',140,50,'235.000',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(58,'1000053','xprod347727','1000193','泰禾朵朵吸水杯TH-1017','9.000',150,30,'270.000',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(59,'1000054','xprod347728','1000194','泰禾炫彩背带水壶350MLTH-1102','13.500',50,14,'189.000',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(60,'1000055','xprod347728','1000194','泰禾炫彩背带水壶350MLTH-1102','13.500',36,14,'189.000',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(61,'1000056','xprod347728','1000194','泰禾炫彩背带水壶350MLTH-1102','13.500',30,22,'297.000',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(62,'1000057','xprod347728','1000194','泰禾炫彩背带水壶350MLTH-1102','13.500',52,22,'297.000',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(63,'1000058','xprod347728','1000194','泰禾炫彩背带水壶350MLTH-1102','13.500',74,22,'297.000',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(64,'1000059','xprod347728','1000194','泰禾炫彩背带水壶350MLTH-1102','13.500',96,22,'297.000',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(65,'1000060',NULL,'1001109',NULL,NULL,0,200,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(66,'1000062','xprod347927','1000393','桃李手撕面包','3.910',9952,-30,'-117.300',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(67,'1000063','xprod347938','1000404','桃李吉士排面包(110g)','31.590',999990,-10,'-315.900',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(68,'1000065','xprod347938','1000404','桃李吉士排面包(110g)','31.590',999980,-10,'-315.900',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(69,'1000066','xprod347938','1000404','桃李吉士排面包(110g)','33.030',10000789,10,'330.300',NULL,NULL,NULL,NULL,'2017-10-10 00:00:00',NULL),(70,'1000067','xprod347938','1000404','桃李吉士排面包(110g)','33.030',10000799,10,'330.300',NULL,NULL,NULL,NULL,'2017-10-10 00:00:00',NULL),(71,'1000068','xprod347938','1000404','桃李吉士排面包(110g)','31.420',1756,-10,'-314.200',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00'),(72,'1000069','xprod347938','1000404','桃李吉士排面包(110g)','31.420',1746,-10,'-314.200',NULL,NULL,NULL,NULL,'2017-04-02 00:00:00','2019-10-12 00:00:00');

/*Table structure for table `item_loc_soh` */

DROP TABLE IF EXISTS `item_loc_soh`;

CREATE TABLE `item_loc_soh` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `item_id` varchar(40) NOT NULL COMMENT '商品编码',
  `loc` varchar(40) NOT NULL COMMENT '仓库编码',
  `unit_cost` double(20,3) DEFAULT NULL COMMENT '商品成本',
  `av_cost` double(20,3) DEFAULT NULL COMMENT '移动加权平均成本',
  `stock_on_hand` bigint(19) DEFAULT '0' COMMENT '现有库存',
  `in_transit_qty` bigint(19) DEFAULT '0' COMMENT '在途数量',
  `order_reserved_qty` bigint(19) DEFAULT '0' COMMENT '销售保留',
  `tsf_reserved_qty` bigint(19) DEFAULT '0' COMMENT '调拨预留',
  `sell_in_transit_qty` bigint(19) DEFAULT '0' COMMENT '销售在途',
  `tsf_expected_qty` bigint(19) DEFAULT '0' COMMENT '预期到货',
  `rtv_qty` bigint(19) DEFAULT '0' COMMENT '退货预留',
  `ref_no_1` varchar(40) DEFAULT NULL,
  `ref_no_2` varchar(40) DEFAULT NULL,
  `ref_no_3` varchar(40) DEFAULT NULL,
  `ref_no_4` varchar(40) DEFAULT NULL,
  `ref_no_5` varchar(40) DEFAULT NULL,
  `last_update_datetime` timestamp NULL DEFAULT NULL COMMENT '最后一次更新时间',
  `last_update_id` varchar(40) NOT NULL COMMENT '最后一次更新人',
  `product_code` varchar(50) DEFAULT NULL COMMENT '商品code',
  PRIMARY KEY (`id`),
  KEY `item_id_and_loc` (`item_id`,`loc`)
) ENGINE=InnoDB AUTO_INCREMENT=1609 DEFAULT CHARSET=utf8 COMMENT='商品地点库存表';

/*Data for the table `item_loc_soh` */

insert  into `item_loc_soh`(`id`,`item_id`,`loc`,`unit_cost`,`av_cost`,`stock_on_hand`,`in_transit_qty`,`order_reserved_qty`,`tsf_reserved_qty`,`sell_in_transit_qty`,`tsf_expected_qty`,`rtv_qty`,`ref_no_1`,`ref_no_2`,`ref_no_3`,`ref_no_4`,`ref_no_5`,`last_update_datetime`,`last_update_id`,`product_code`) values (2,'157864','WHH01',50.000,50.000,1951,0,113,100,12,0,100,NULL,NULL,NULL,NULL,NULL,'2017-08-20 00:00:00','2','1001199'),(5,'59814','TJXQS',63.940,63.940,11180,0,0,0,0,0,17,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:01','12231','1000663'),(6,'xprod289969','903200451',8.330,8.330,11111,0,0,0,0,0,17,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:01','12231','1000846    '),(7,'xprod273400','YT901',6.670,6.670,100,0,18,0,0,0,17,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:01','12231','1001746    '),(8,'60682','901200411',4.200,4.200,120,0,0,0,0,0,17,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:01','12231','1001171'),(9,'60000228648','901200411',3.000,3.000,60,0,0,0,0,0,17,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:01','12231','1001171'),(10,'40068','YT901',3.000,3.000,1000000,0,0,0,0,0,17,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:01','12231','1001800'),(11,'60000228650','901200411',3.000,3.000,60,0,0,0,0,0,17,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:01','12231','1001171'),(12,'60000228651','901200411',3.000,3.000,48,0,0,0,13,13,17,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:01','12231','1001171'),(13,'xprod341331','901200411',25.870,25.870,20,0,0,0,0,0,0,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:01','12231','1001171'),(14,'48116','YT901',2.000,5.010,1740,0,403,0,0,0,13,NULL,NULL,NULL,NULL,NULL,'2017-11-17 19:25:03','test_modify_user','1001820'),(15,'295083 ','901200411',23.920,23.920,9,0,0,0,0,0,0,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:02','12231','1001171'),(16,'60000237296','901200411',29.120,29.120,9,0,0,0,0,0,0,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:02','12231','1001171'),(17,'60000134110','901200411',9.100,9.100,8,0,0,0,0,0,0,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:02','12231','1001171'),(18,'295086','901200411',22.620,22.620,9,0,0,0,0,0,0,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:02','12231','1001171'),(19,'60000124260','901200411',31.850,31.850,9,0,0,0,0,0,0,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:02','12231','1001171'),(20,'60001806107','901200411',14.240,14.240,9,0,0,0,0,0,0,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:02','12231','1001171'),(21,'60001054337','901200411',45.440,45.440,9,0,0,0,0,0,0,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:02','12231','1001171'),(22,'60001054307','901200411',48.690,48.690,9,0,0,0,0,0,0,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:02','12231','1001171'),(23,'59814','901200411',5.370,5.370,72,0,0,0,0,0,17,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:02','12231','1001171'),(24,'40062','901200411',6.750,6.750,72,0,0,0,0,0,17,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:02','12231','1001171'),(25,'40068','901200411',6.750,6.750,72,0,0,0,0,0,17,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:02','12231','1001171'),(26,'40075','901200411',6.750,6.750,72,0,0,0,0,0,17,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:02','12231','1001171'),(27,'xprod347914','WLL',2.240,2.240,1700,0,0,0,0,0,17,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:02','12231','1000380'),(28,'xprod267792','901200411',2.560,2.560,36,0,0,0,0,0,17,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:02','12231','1001171'),(29,'60001476574','901200411',10.390,10.390,12,0,0,0,0,0,17,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:02','12231','1001171'),(30,'xprod267801','901200411',1.750,1.750,24,0,0,0,0,0,17,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:02','12231','1001171'),(31,'60001476137','901200411',7.350,7.350,12,0,0,0,0,0,17,NULL,NULL,NULL,NULL,NULL,'2017-08-20 15:32:03','12231','1001171');

/*Table structure for table `kucun` */

DROP TABLE IF EXISTS `kucun`;

CREATE TABLE `kucun` (
  `tiaoma` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `kucun` */

insert  into `kucun`(`tiaoma`) values ('69021824'),('69025143'),('69028571'),('69029110'),('84501618711'),('84501825317'),('84501844318'),('84501950415'),('4547691239099'),('4547691239129'),('4547691239136'),('4547691239150'),('4547691689696'),('4547691689702'),('4547691689726'),('4547691770929'),('4547691770936'),('4547691770974'),('4891338005692'),('4891338007665'),('4891338008761'),('4891338010498'),('4893055006549'),('4893055101077'),('4893055201449'),('4893055300166'),('4893055301453'),('4893055400187'),('4893055800024'),('4893055810054'),('6901070600111'),('6901070600135'),('6901070600883'),('6901333100518'),('6901333110524'),('6901333980400'),('6901333980516'),('6901668002426'),('6901668002433'),('6901668002440'),('6901668002457'),('6901668002846'),('6901668004772'),('6901668004833'),('6901668004895'),('6901668004956'),('6901668005717'),('6901668005731'),('6901668005748'),('6901668005755'),('6901668005762'),('6901668005892'),('6901668005908'),('6901668005915'),('6901668005946'),('6901668005953'),('6901668053510'),('6901668053527'),('6901668053718'),('6901668053787'),('6901668053893'),('6901668053916'),('6901668054418'),('6901668054746'),('6901668062086'),('6901668062178'),('6901668200013'),('6901668200235'),('6901754040936'),('6901754050218'),('6901754071152'),('6901754131016'),('6901754131023'),('6901754160306'),('6901754160375'),('6901754799988'),('6902022130861'),('6902022135231'),('6902022135255'),('6902022135514'),('6902022137211'),('6902088705423'),('6902088708981'),('6902088714302'),('6902253862883'),('6902253868885'),('6902253888531'),('6902253888869'),('6902253888883'),('6902253888951'),('6902265111719'),('6902265170501'),('6902265210504'),('6902265360100'),('6902265540250'),('6902902007122'),('6902902009010'),('6902934987171'),('6902934990362'),('6903148030455'),('6903148030479'),('6903148044964'),('6903148044971'),('6903148045015'),('6903148045046'),('6903148045053'),('6903148047729'),('6903148047736'),('6903148047767'),('6903148047774'),('6903148047804'),('6903148048795'),('6903148048818'),('6903148048825'),('6903148048955'),('6903148048979'),('6903148048986'),('6903148070949'),('6903148082102'),('6903148082119'),('6903148091425'),('6903148116920'),('6903148144442'),('6903148144459'),('6903148144541'),('6903148157008'),('6903244370424'),('6903244370431'),('6903244370448'),('6903244370455'),('6903244370776'),('6903244370943'),('6903244370950'),('6903244370974'),('6903244370981'),('6903244370998'),('6903244371018'),('6903244984157'),('6903388512001'),('6903388710155'),('6904032072018'),('6904032073022'),('6904032073138'),('6904032075019'),('6904032510015'),('6904032878887'),('6907992400037'),('6907992631325'),('6907992631530'),('6909003102022'),('6909003361023'),('6909003888018'),('6909995103670'),('6909995103687'),('6909995103694'),('6911316540309'),('6911316600409'),('6911988006448'),('6911988006455'),('6911988006462'),('6911988006479'),('6911988009760'),('6911988009777'),('6911988009784'),('6911988013538'),('6911988013545'),('6911988014832'),('6911988014894'),('6914068011950'),('6914068013138'),('6914253431457'),('6914782082113'),('6914782082380'),('6914782202498'),('6914782202504'),('6914782202511'),('6914782203327'),('6914782203693'),('6914789001131'),('6914789016890'),('6914789018825'),('6914789018986'),('6914789066666'),('6918598028013'),('6918598028129'),('6918598028136'),('6918678423035'),('6919766000121'),('6919839601057'),('6919892633101'),('6919892633200'),('6919892633309'),('6920202866737'),('6920202888883'),('6920208900770'),('6920208904372'),('6920208909322'),('6920208909926'),('6920208914067'),('6920208915477'),('6920208933822'),('6920208942718'),('6920208957972'),('6920208983506'),('6920208984015'),('6920208985050'),('6920208985074'),('6920208985180'),('6920208985388'),('6920208988624'),('6920208993253'),('6920300700070'),('6920300700094'),('6920354800399'),('6920354803246'),('6920354809903'),('6920354810145'),('6920354810268'),('6920354811036'),('6920354811395'),('6920354811432'),('6920354811494'),('6920354812262'),('6920354813290'),('6920354813702'),('6920354814051'),('6920354815287'),('6920354815454'),('6920354816536'),('6920354816659'),('6920354817113'),('6920354817175'),('6920546800046'),('6920548862066'),('6920548862080'),('6920548862998'),('6920548867320'),('6920584471017'),('6920584471215'),('6920601700663'),('6920601711119'),('6920601711195'),('6920601711218'),('6920616313186'),('6920658210351'),('6920658210627'),('6920658281795'),('6920658281801'),('6920658284284'),('6920658284291'),('6920731704302'),('6920907800210'),('6920907800616'),('6920907800654'),('6920907800913'),('6920907800944'),('6920907803020'),('6920907808032'),('6920907808117'),('6920907808179'),('6920907808278'),('6920907808360'),('6920907808513'),('6920907808537'),('6920907808551'),('6920907808575'),('6920907808599'),('6920907808612'),('6920907809862'),('6920907809909'),('6920912342002'),('6920912348578'),('6920912349032'),('6920912353657'),('6920912353664'),('6920912357662'),('6920930307816'),('6920930342534'),('6920930375112'),('6921082800019'),('6921168500956'),('6921168500970'),('6921168502066'),('6921168502127'),('6921168504015'),('6921168504022'),('6921168504039'),('6921168508242'),('6921168509256'),('6921168509270'),('6921168511280'),('6921168520015'),('6921168532001'),('6921168532025'),('6921168532049'),('6921168550098'),('6921168550104'),('6921168550111'),('6921168550128'),('6921168550142'),('6921168558018'),('6921168558025'),('6921168558049'),('6921168559176'),('6921168559244'),('6921168593002'),('6921168593033'),('6921168593385'),('6921168593545'),('6921168593552'),('6921168593569'),('6921168593576'),('6921168593583'),('6921168593606'),('6921168593613'),('6921168593620'),('6921168593637'),('6921168593736'),('6921168593804'),('6921168593811'),('6921168593859'),('6921233902203'),('6921233902555'),('6921738000022'),('6921738000039'),('6921804700108'),('6921804700559'),('6921804700702'),('6921804700757'),('6921804700764'),('6921804700788'),('6921808620013'),('6921808620563'),('6921808621317'),('6921808621324'),('6921808621447'),('6921808622703'),('6921808622710'),('6921982960288'),('6922016500067'),('6922016500081'),('6922016500340'),('6922017022209'),('6922017027228'),('6922222020168'),('6922222020267'),('6922222021868'),('6922222098914'),('6922222099102'),('6922222099119'),('6922222099133'),('6922222099157'),('6922222099171'),('6922239700015'),('6922255447833'),('6922255451427'),('6922456805036'),('6922731800695'),('6922731800770'),('6922731898081'),('6922731898104'),('6922824001275'),('6922824055063'),('6922824075030'),('6922868281114'),('6922868281152'),('6922868284283'),('6922868285464'),('6922868285716'),('6922868286003'),('6922884610172'),('6922884616952'),('6923146100011'),('6923146100028'),('6923146102046'),('6923146105016'),('6923146112281'),('6923146198063'),('6923146198162'),('6923146199275'),('6923365700054'),('6923365700085'),('6923365772266'),('6923365777773'),('6923450603550'),('6923450603574'),('6923450605318'),('6923450605332'),('6923450656150'),('6923450657607'),('6923450657829'),('6923450657935'),('6923450658048'),('6923450658079'),('6923450658925'),('6923644223458'),('6923644268497'),('6923644268503'),('6923644268510'),('6923644270957'),('6923644278588'),('6924115086039'),('6924115086138'),('6924115086510'),('6924513907226'),('6924513907318'),('6924513907493'),('6924513908001'),('6924513908063'),('6924743915398'),('6924743915428'),('6924743915480'),('6924743915497'),('6924743915763'),('6924743915824'),('6924743915831'),('6924743915848'),('6924743918238'),('6924743918610'),('6924743918627'),('6924743918658'),('6924743918665'),('6924743919211'),('6924743919228'),('6924743919242'),('6924743919259'),('6924743919266'),('6924743919280'),('6924743919297'),('6925019900087'),('6925303711184'),('6925303711368'),('6925303714086'),('6925303716349'),('6925303740351'),('6925303740627'),('6925303740788'),('6925303770006'),('6925303770310'),('6925303770556'),('6925303770563'),('6925303773106'),('6925303790400'),('6925303793050'),('6925303793067'),('6925332603054'),('6925704426472'),('6925704459500'),('6925717024702'),('6925717025082'),('6925717025716'),('6925717025914'),('6925717026119'),('6925717026706'),('6925717082504'),('6925861571299'),('6925861571466'),('6925901420280'),('6925901420297'),('6925901420396'),('6926115989051'),('6926265306319'),('6926265329370'),('6926292923343'),('6926292934486'),('6926292934585'),('6926292934622'),('6926410320016'),('6926410320023'),('6926410320030'),('6926410320047'),('6926410320214'),('6926410320252'),('6926410321396'),('6926410321402'),('6926410330763'),('6926410330879'),('6926410330954'),('6926410330992'),('6926410331807'),('6926410331845'),('6926458810012'),('6926458876377'),('6926475200995'),('6926475201008'),('6926475201329'),('6926475201336'),('6926475201343'),('6926475201367'),('6927216910012'),('6927216910029'),('6927216910036'),('6927216920011'),('6927216920059'),('6927216920066'),('6928452500081'),('6928452508599'),('6928452510035'),('6928452520034'),('6928548200505'),('6928548200512'),('6928548200529'),('6928548202578'),('6928724923068'),('6930662077635'),('6930662098371'),('6930934424815'),('6931958014327'),('6932389998422'),('6932418821226'),('6932418851124'),('6932759723135'),('6932759723142'),('6932898717378'),('6932898720378'),('6932898789283'),('6933097130890'),('6933097131262'),('6933117201067'),('6933414100056'),('6933414100308'),('6933851600003'),('6933851676411'),('6933851676510'),('6933851676619'),('6933851691537'),('6934054855108'),('6934054855528'),('6934278802230'),('6934278807662'),('6934364800720'),('6934364800737'),('6934364801123'),('6934364801352'),('6934364801390'),('6934364807248'),('6934688578183'),('6934688578237'),('6934688578879'),('6934688579562'),('6934688579579'),('6934688579586'),('6935768922681'),('6935768922698'),('6935768922759'),('6935768924043'),('6935768924067'),('6935768924074'),('6935768924128'),('6935768924159'),('6935817301023'),('6935817301047'),('6935817301122'),('6935817301139'),('6935817303508'),('6936281402650'),('6936668540210'),('6937056100207'),('6937056100559'),('6937056100610'),('6937056100788'),('6938270511237'),('6938270511244'),('6938497700094'),('6938692900015'),('6938692900176'),('6939059251634'),('6939059251658'),('6939059251719'),('6939059251757'),('6939501804661'),('6939501806023'),('6940008580354'),('6940008580514'),('6940008580811'),('6940008582044'),('6940008582099'),('6940008583317'),('6940008583492'),('6940477401198'),('6940575006424'),('6940920929224'),('6940920939681'),('6940920969923'),('6941107200105'),('6941107200112'),('6941107200556'),('6941107220523'),('6941107220561'),('6941107230027'),('6941107230096'),('6941107230201'),('6941107230225'),('6941107230263'),('6941107230294'),('6941107231154'),('6941107231338'),('6941107232052'),('6941107232106'),('6941107232120'),('6941107232151'),('6941107235015'),('6941107235305'),('6941107238023'),('6941107238542'),('6941107238726'),('6941107238740'),('6941107239525'),('6941259000080'),('6941259000219'),('6941499100014'),('6941499100151'),('6941499100618'),('6941499100656'),('6941499100779'),('6941499100816'),('6941499101219'),('6941499101233'),('6941499104302'),('6941499112611'),('6941499112635'),('6942910300488'),('6943122300020'),('6943122300037'),('6943269210022'),('6943269210084'),('6943269245314'),('6943269260287'),('6943515500815'),('6943515501003'),('6943515501034'),('6943515501195'),('6943776601337'),('6943776601344'),('6943776601351'),('6943776640145'),('6943776640206'),('6943776640237'),('6943776651714'),('6943776680455'),('6943776680462'),('6943836500471'),('6943836503113'),('6944910318005'),('6944910318210'),('6944910318760'),('6944910323870'),('6944910334500'),('6944910334548'),('6944910334586'),('6944910334623'),('6944910334661'),('6945091701532'),('6946208422043'),('6946208422081'),('6946576300080'),('6946576310034'),('6946576310096'),('6946760010115'),('6946760010139'),('6946909765081'),('6946909766019'),('6946909766026'),('6946909785065'),('6947236030644'),('6947483338029'),('6948081405403'),('6949230402458'),('6949818303016'),('6949818350164'),('6949818360453'),('6949818370063'),('6950123162038'),('6950123191304'),('6950844923239'),('6950844961101'),('6950844961125'),('6950844981031'),('6950844981093'),('6951582499116'),('6951957202136'),('6951957202143'),('6951957202150'),('6951957202167'),('6951972008096'),('6953066185188'),('6953066185720'),('6953066185898'),('6953066185911'),('6953350000869'),('6954260700252'),('6954260700801'),('6954432710171'),('6954432710201'),('6954432710232'),('6954432710706'),('6954432710744'),('6955523525513'),('6955890010094'),('6955890010117'),('6955890030528'),('6955890030535'),('6956401205428'),('6956401206111'),('6956401259100'),('6956401264173'),('6956401281040'),('6956401285048'),('6957281100018'),('6957281102777'),('6970115980709'),('6970440825287'),('6970440828288'),('8853969008035');

/*Table structure for table `pm_purchase_order` */

DROP TABLE IF EXISTS `pm_purchase_order`;

CREATE TABLE `pm_purchase_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_order_no` varchar(255) DEFAULT NULL COMMENT '采购单号',
  `sp_id` varchar(255) DEFAULT NULL COMMENT '供应商ID',
  `sp_no` varchar(255) DEFAULT NULL COMMENT '供应商编码',
  `sp_name` varchar(255) DEFAULT NULL COMMENT '供应商名称',
  `sp_adr_id` varchar(255) DEFAULT NULL COMMENT '供应商地点ID',
  `sp_adr_no` varchar(255) DEFAULT NULL COMMENT '供应商地点编码',
  `sp_adr_name` varchar(255) DEFAULT NULL COMMENT '供应商地点名称',
  `estimated_delivery_date` datetime DEFAULT NULL COMMENT '预计送货日期',
  `settlement_period` int(11) DEFAULT NULL COMMENT '账期',
  `pay_type` int(11) DEFAULT NULL COMMENT '供应商付款方式',
  `adr_type` int(2) DEFAULT '0' COMMENT '地点类型:0:仓库;1:门店',
  `adr_type_code` varchar(255) DEFAULT NULL COMMENT '地点类型编码',
  `adr_type_name` varchar(255) DEFAULT NULL COMMENT '地点类型名称',
  `second_category_id` varchar(55) DEFAULT NULL COMMENT '二级分类id(大类)',
  `currency_code` varchar(20) DEFAULT NULL COMMENT '货币种类代码,CNY',
  `branch_company_id` varchar(11) DEFAULT NULL COMMENT '子公司ID',
  `purchase_order_type` int(2) DEFAULT '0' COMMENT '类型:0:普通采购单;1:赠品采购;2:促销釆购',
  `status` int(2) DEFAULT '0' COMMENT '状态:0:草稿;1:待审核;2:已审核;3:已拒绝;4:已关闭;5:已取消',
  `failed_reason` varchar(500) DEFAULT NULL COMMENT '失败原因',
  `audit_user_id` varchar(20) DEFAULT NULL COMMENT '审核人ID',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_user_id` varchar(20) DEFAULT NULL COMMENT '创建人',
  `modify_user_id` varchar(20) DEFAULT NULL COMMENT '修改人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核日期',
  `adr_name` varchar(255) DEFAULT NULL COMMENT '门店和仓库详细地址（收货地址）',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `second_category_name` varchar(255) DEFAULT NULL COMMENT '二级分类名称(大类)',
  `bar_code_url` varchar(255) DEFAULT NULL COMMENT '条码地址',
  `total_tax_amount` decimal(20,2) DEFAULT NULL COMMENT '总税额',
  `total_without_tax_amount` decimal(20,2) DEFAULT NULL COMMENT '不含税采购金额',
  `total_amount` decimal(20,2) DEFAULT NULL COMMENT '总采购金额，含税',
  `pay_condition` int(2) DEFAULT NULL COMMENT '付款条件(1:票到七天,2:票到十五天,3:票到三十天)',
  `business_mode` int(2) DEFAULT '0' COMMENT '经营模式:0:经销;1:代销;2:寄售',
  `purchasing_mode` int(2) DEFAULT '0' COMMENT '采购模式:0:地采;1:统采',
  `sale_order_id` varchar(40) DEFAULT NULL COMMENT '销售订单id',
  `sp_accept_status` int(2) DEFAULT NULL COMMENT '供应商接单状态:0未接单;1:已接单',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=685 DEFAULT CHARSET=utf8 COMMENT='采购单表';

/*Data for the table `pm_purchase_order` */

insert  into `pm_purchase_order`(`id`,`purchase_order_no`,`sp_id`,`sp_no`,`sp_name`,`sp_adr_id`,`sp_adr_no`,`sp_adr_name`,`estimated_delivery_date`,`settlement_period`,`pay_type`,`adr_type`,`adr_type_code`,`adr_type_name`,`second_category_id`,`currency_code`,`branch_company_id`,`purchase_order_type`,`status`,`failed_reason`,`audit_user_id`,`create_time`,`modify_time`,`create_user_id`,`modify_user_id`,`audit_time`,`adr_name`,`phone`,`second_category_name`,`bar_code_url`,`total_tax_amount`,`total_without_tax_amount`,`total_amount`,`pay_condition`,`business_mode`,`purchasing_mode`,`sale_order_id`,`sp_accept_status`) values (1,'1708180001','xprov542','10133','admin','59','59','四川 - admin','2017-08-22 10:07:47',0,0,0,'902200421','重庆仓重庆子公司常温正常仓',NULL,'CNY','10000',0,4,'超期关闭','10018','2017-08-18 10:08:29','2017-08-27 10:30:00','10018',NULL,'2017-08-18 11:16:34','礼环北路10号','',NULL,'group1/M00/01/38/rB4KPVmWTDiAdXA7AAABMTUozhw820.png','1055.45','6208.55','7264.00',NULL,1,1,'',NULL),(2,'1708180002','xprov542','10133','admin','59','59','四川 - admin','2017-08-22 10:08:41',0,0,0,'902200421','重庆仓重庆子公司常温正常仓',NULL,'CNY','10000',0,4,'超期关闭','10018','2017-08-18 10:09:32','2017-08-27 10:30:00','10018',NULL,'2017-08-18 17:20:08','礼环北路10号','',NULL,'group1/M00/01/38/rB4KPFmWTHSAI5K3AAABQv_zapY549.png','28993.57','170550.43','199544.00',NULL,1,1,NULL,NULL),(3,'1708120003','xprov542','10133','admin','59','59','四川 - admin','2017-08-22 18:05:30',0,0,0,'901200411','成都仓四川子公司常温正常仓',NULL,'CNY','10000',0,0,NULL,NULL,'2017-08-18 18:06:46',NULL,'10018',NULL,NULL,'汽车城大道17号普洛斯园区B1-1  ','13438234152\r\n',NULL,'group1/M00/01/39/rB4KPFmWvFCALx43AAABO7K7xN0137.png','11615658.43','74543349.07','86159007.50',NULL,0,0,NULL,NULL),(4,'1708180004','xprov545','10137','供应商8','65','100097','四川 - 供应商8','2017-08-23 19:46:45',0,0,0,'901200411','成都仓四川子公司常温正常仓',NULL,'CNY','10000',0,0,NULL,NULL,'2017-08-18 19:48:23','2017-08-18 19:50:50','10018',NULL,NULL,'汽车城大道17号普洛斯园区B1-1  ','13438234152\r\n',NULL,'group1/M00/01/39/rB4KPFmW1CCAViz5AAABOBQBh8w222.png','4.24','24.92','29.16',NULL,0,0,NULL,NULL),(5,'1708190001','xprov538','10131','YTtesting','56','56','福建 - YTtesting','2017-08-31 14:12:09',1,0,0,'YT901','测试',NULL,'CNY','10000',0,2,NULL,'10018','2017-08-19 14:12:39','2017-08-19 14:12:49','10018',NULL,'2017-08-19 15:31:27','礼环北路10号',NULL,NULL,'group1/M00/01/38/rB4KPlmX1vGAKm0VAAABNd2nDtw895.png','13.50','112.50','126.00',NULL,0,0,NULL,NULL),(6,'1708210001','xprov538','10131','YTtesting','56','56','福建 - YTtesting','2017-09-02 11:22:03',1,0,0,'YT901','测试',NULL,'CNY','10000',0,3,'butong','10027','2017-08-21 11:22:49','2017-08-24 11:06:08','10018',NULL,'2017-08-24 14:28:55','礼环北路10号',NULL,NULL,'group1/M00/01/3A/rB4KPlmaUiSAX2CNAAABN_R5Rdk215.png','13.50','112.50','126.00',NULL,0,0,NULL,NULL),(7,'1708220001','xprov538','10131','YTtesting','56','56','福建 - YTtesting','2017-09-03 15:12:02',1,0,0,'YT901','测试',NULL,'CNY','10000',0,2,NULL,'10018','2017-08-22 15:12:47','2017-08-24 11:04:52','10018',NULL,'2017-08-24 11:12:38','礼环北路10号',NULL,NULL,'group1/M00/01/3B/rB4KPVmb2YuAEBjzAAABPe7odUw887.png','24999.75','208331.25','233331.00',NULL,0,0,NULL,NULL),(8,'1708220002','xprov555','10169','红太阳','78','100121','天津 - 红太阳','2017-09-01 15:45:27',0,1,0,'901200411','成都仓四川子公司常温正常仓',NULL,'CNY','10000',0,0,NULL,NULL,'2017-08-22 15:45:34',NULL,'10018',NULL,NULL,'汽车城大道17号普洛斯园区B1-1  ','13438234152\r\n',NULL,'group1/M00/01/3B/rB4KPVmb4TiAdiexAAABStN6ATA105.png','3.05','17.95','21.00',NULL,0,0,NULL,NULL),(9,'1708220003','xprov555','10169','红太阳','78','100121','天津 - 红太阳','2017-09-01 16:13:41',0,1,0,'901200411','成都仓四川子公司常温正常仓',NULL,'CNY','10000',0,4,'超期关闭','10018','2017-08-22 16:15:47','2017-12-29 00:00:02','10018',NULL,'2017-08-22 16:20:50','汽车城大道17号普洛斯园区B1-1  ','13438234152\r\n',NULL,'group2/M00/00/24/rB4KPVmb6EyAJ5goAAABSg6UdoM106.png','29.48','173.42','202.90',NULL,0,0,NULL,NULL),(10,'1708230001','xprov008','10007','四川福仁缘农业开发有限公司','8','1000008','全国-四川福仁缘农业开发有限公司','2017-09-07 10:38:55',2,1,0,'YT901','测试',NULL,'CNY','10000',0,4,'超期关闭','10018','2017-08-23 10:39:40','2018-01-17 00:00:01','10018',NULL,'2017-08-23 10:39:51','礼环北路10号',NULL,NULL,'group1/M00/01/3D/rB4KPFmc6weAX5_JAAABPCU85Qo668.png','2905.98','17094.02','20000.00',NULL,0,0,'1010101010',NULL),(11,'1708230002','xprov008','10007','四川福仁缘农业开发有限公司','8','1000008','全国-四川福仁缘农业开发有限公司','2017-09-07 11:12:56',2,1,0,'YT901','测试',NULL,'CNY','10000',0,2,NULL,'10018','2017-08-23 11:13:43','2017-08-23 11:13:47','10018',NULL,'2017-08-23 11:13:53','礼环北路10号',NULL,NULL,'group1/M00/01/3D/rB4KPFmc8wCAYEf5AAABSxZ7zM4626.png','2905.98','17094.02','20000.00',NULL,0,0,NULL,NULL),(12,'1708230003','xprov008','10007','四川福仁缘农业开发有限公司','8','1000008','全国-四川福仁缘农业开发有限公司','2017-09-07 13:46:03',2,1,0,'YT901','测试',NULL,'CNY','10000',0,4,NULL,'10018','2017-08-23 13:46:59','2017-08-30 16:59:53','10018',NULL,'2017-08-23 13:47:23','礼环北路10号',NULL,NULL,'group1/M00/01/3D/rB4KPVmdFuyAXKqUAAABRc2pztQ557.png','2905.98','17094.02','20000.00',NULL,0,0,NULL,NULL),(13,'1708230004','xprov008','10007','四川福仁缘农业开发有限公司','8','1000008','全国-四川福仁缘农业开发有限公司','2017-09-07 14:29:10',2,1,0,'YT901','测试',NULL,'CNY','10000',0,4,NULL,'10018','2017-08-23 14:32:54','2017-08-30 16:59:53','10018',NULL,'2017-08-23 14:34:22','礼环北路10号',NULL,NULL,'group1/M00/01/3D/rB4KPVmdIbCAdH4JAAABQj2vYcE823.png','2905.98','17094.02','20000.00',NULL,0,0,NULL,NULL),(14,'1708230005','xprov008','10007','四川福仁缘农业开发有限公司','8','1000008','全国-四川福仁缘农业开发有限公司','2017-09-07 16:02:55',2,1,0,'YT901','测试',NULL,'CNY','10000',0,4,NULL,'10018','2017-08-23 16:04:22','2017-08-30 16:59:53','10018',NULL,'2017-08-23 16:04:34','礼环北路10号',NULL,NULL,'group1/M00/01/3E/rB4KPFmdNyCAJ5RzAAABSCjCpNU258.png','1452.99','8547.01','10000.00',NULL,0,0,NULL,NULL),(15,'1708230006','xprov008','10007','四川福仁缘农业开发有限公司','8','1000008','全国-四川福仁缘农业开发有限公司','2017-09-07 16:23:45',2,1,0,'YT901','测试',NULL,'CNY','10000',0,4,NULL,'10018','2017-08-23 16:24:35','2017-08-30 16:59:53','10018',NULL,'2017-08-23 16:24:43','礼环北路10号',NULL,NULL,'group1/M00/01/3E/rB4KPVmdO92ADuG2AAABS40GvvE267.png','1452.99','8547.01','10000.00',NULL,0,0,NULL,NULL);

/*Table structure for table `pm_purchase_order_item` */

DROP TABLE IF EXISTS `pm_purchase_order_item`;

CREATE TABLE `pm_purchase_order_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_order_id` varchar(255) DEFAULT NULL COMMENT '采购单号',
  `product_id` varchar(255) DEFAULT NULL COMMENT '商品id',
  `product_code` varchar(20) DEFAULT NULL COMMENT '商品编号',
  `product_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `international_code` varchar(20) DEFAULT NULL COMMENT '条码(国际码)',
  `packing_specifications` varchar(20) DEFAULT NULL COMMENT '规格',
  `produce_place` varchar(255) DEFAULT NULL COMMENT '产地',
  `purchase_inside_number` int(11) DEFAULT NULL COMMENT '采购内装数(默认箱规)',
  `unit_explanation` varchar(20) DEFAULT NULL COMMENT '单位',
  `input_tax_rate` decimal(4,2) DEFAULT NULL COMMENT '税率,进项税率',
  `purchase_price` decimal(20,2) DEFAULT NULL COMMENT '采购价格（含税）',
  `purchase_number` int(20) DEFAULT NULL COMMENT '采购数量,必须为采购内装数的倍数',
  `total_amount` decimal(20,2) DEFAULT NULL COMMENT '采购金额，含税',
  `received_number` int(20) DEFAULT NULL COMMENT '已收货数量,收货数量<=采购数量',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `create_user_id` varchar(20) DEFAULT NULL,
  `modify_user_id` varchar(20) DEFAULT NULL,
  `prod_purchase_id` varchar(255) DEFAULT NULL COMMENT '商品采购关系主键',
  `tax_amount` decimal(20,2) DEFAULT NULL COMMENT '税额',
  `total_without_tax_amount` decimal(20,2) DEFAULT NULL COMMENT '不含税采购金额',
  `is_valid` int(1) DEFAULT '1' COMMENT '是否有效:0,无效,1:有效',
  `product_brand_id` varchar(255) DEFAULT NULL COMMENT '商品品牌id',
  `in_validate_reason` varchar(255) DEFAULT NULL COMMENT '失败原因',
  `product_brand_name` varchar(255) DEFAULT NULL COMMENT '商品品牌名称',
  `return_type` int(2) DEFAULT '1' COMMENT '是否可以退货给供应商  1：是  0：否',
  `groups` varchar(40) DEFAULT NULL COMMENT '部类',
  `dept` varchar(40) DEFAULT NULL COMMENT '大类',
  `classs` varchar(40) DEFAULT NULL COMMENT '中类',
  `subclass` varchar(40) DEFAULT NULL COMMENT '小类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=861 DEFAULT CHARSET=utf8 COMMENT='采购单商品表';

/*Data for the table `pm_purchase_order_item` */

insert  into `pm_purchase_order_item`(`id`,`purchase_order_id`,`product_id`,`product_code`,`product_name`,`international_code`,`packing_specifications`,`produce_place`,`purchase_inside_number`,`unit_explanation`,`input_tax_rate`,`purchase_price`,`purchase_number`,`total_amount`,`received_number`,`create_time`,`modify_time`,`create_user_id`,`modify_user_id`,`prod_purchase_id`,`tax_amount`,`total_without_tax_amount`,`is_valid`,`product_brand_id`,`in_validate_reason`,`product_brand_name`,`return_type`,`groups`,`dept`,`classs`,`subclass`) values (1,'-11','60001883563','1001744','æ™ºåŠ›ç»å…¸åŽŸå‘³å¥¶é¦™éº¦ç‰‡480g','6925785605773','480g','æ¡‚æž—',1,'è¢‹','17.00','16.00',454,'7264.00',NULL,'2017-08-18 10:08:29',NULL,'10018',NULL,'1','1055.45','6208.55',1,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),(2,'2','60001883563','1001744','智力经典原味奶香麦片480g','6925785605773','480g','桂林',1,'袋','17.00','16.00',5654,'90464.00',NULL,'2017-08-18 10:09:32','2017-08-18 17:19:11','10018',NULL,'1','13144.34','77319.66',1,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),(3,'2','60001890144','1000790','康师傅优悦饮用纯净水560ml*24','6922456851521','560ml','成都',1,'件','17.00','24.00',4545,'109080.00',NULL,'2017-08-18 10:09:32','2017-08-18 17:19:11','10018',NULL,'2','15849.23','93230.77',1,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),(4,'3','60001890144','1000790','康师傅优悦饮用纯净水560ml*24','6922456851521','560ml','成都',1,'件','17.00','24.00',1236564,'29677536.00',NULL,'2017-08-18 18:06:46',NULL,'10018',NULL,'2','4312120.62','25365415.38',1,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),(5,'3','60001883563','1001744','智力经典原味奶香麦片480g','6925785605773','480g','桂林',1,'袋','17.00','16.00',1111111,'17777776.00',NULL,'2017-08-18 18:06:46',NULL,'10018',NULL,'1','2583095.66','15194680.34',1,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),(6,'3','60001943858','1000641','诺豪精品桃木梳NH-3850','6941107238504','1','黄岩',1,'个','17.00','4.30',4454545,'19154543.50',NULL,'2017-08-18 18:06:46',NULL,'10018',NULL,'4','2783138.80','16371404.70',1,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),(7,'3','60001943945','1000514','竹海精纯食用盐350g立袋*60','6934688578879','350g','宜宾',1,'件','11.00','134.40',145455,'19549152.00',NULL,'2017-08-18 18:06:46',NULL,'10018',NULL,'5','1937303.35','17611848.65',1,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),(8,'4','60001967222','1001413','益而高EG-005 液体胶','4893055810054','1','广州',1,'支','17.00','2.43',12,'29.16',NULL,'2017-08-18 19:48:23','2017-08-18 19:50:50','10018',NULL,'6','4.24','24.92',1,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),(9,'5','60000126412','1001868','YT宣传','999','1','沙漠',1,'碗','12.00','21.00',6,'126.00',NULL,'2017-08-19 14:12:39','2017-08-19 14:12:49','10018',NULL,'1509','13.50','112.50',1,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),(10,'6','60000126412','1001868','YT宣传','999','1','沙漠',1,'碗','12.00','21.00',6,'126.00',NULL,'2017-08-21 11:22:49','2017-08-24 11:06:08','10018',NULL,'1509','13.50','112.50',1,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),(11,'7','60000126412','1001868','YT宣传','999','1','沙漠',1,'碗','12.00','21.00',11111,'233331.00',NULL,'2017-08-22 15:12:47','2017-08-24 11:04:52','10018',NULL,'1509','24999.75','208331.25',1,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),(12,'8','xprod347782','1000249','华兴泰1.5厘6613#瓜果刨HXT-4014','6943776640145','1','揭阳',1,'个','17.00','2.10',10,'21.00',NULL,'2017-08-22 15:45:34',NULL,'10018',NULL,'1514','3.05','17.95',1,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL),(13,'9','xprod347785','1000251','易丽洁核桃夹YLG-8017','6940008580170','1','黄岩',1,'个','17.00','10.70',17,'181.90',NULL,'2017-08-22 16:15:47','2017-08-22 16:20:23','10018',NULL,'1516','26.43','155.47',1,'brand_001',NULL,'测试品牌01',1,NULL,NULL,NULL,NULL),(14,'9','xprod347782','1000249','华兴泰1.5厘6613#瓜果刨HXT-4014','6943776640145','1','揭阳',1,'个','17.00','2.10',10,'21.00',NULL,'2017-08-22 16:20:23',NULL,NULL,NULL,'1514','3.05','17.95',1,'brand_002',NULL,'测试品牌02',1,NULL,NULL,NULL,NULL);

/*Table structure for table `pm_purchase_receipt` */

DROP TABLE IF EXISTS `pm_purchase_receipt`;

CREATE TABLE `pm_purchase_receipt` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_receipt_no` varchar(255) DEFAULT NULL COMMENT '收货单号',
  `purchase_order_id` varchar(255) DEFAULT NULL COMMENT '采购单id',
  `asn` varchar(255) DEFAULT NULL COMMENT 'ASN',
  `estimated_received_date` datetime DEFAULT NULL COMMENT '预计到货日期,系统自动默认，值为当前日期.注：预留与供应商系统对接预发货通知',
  `received_time` datetime DEFAULT NULL COMMENT '收货日期,系统自动默认，值为创建收货单时间',
  `branch_company_id` int(11) DEFAULT NULL COMMENT '子公司ID',
  `status` int(2) DEFAULT NULL COMMENT '状态:0:待下发，1：已下发，2:已收货，3:已取消，4：异常',
  `exception_reason` varchar(255) DEFAULT NULL COMMENT '异常原因',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `create_user_id` varchar(20) DEFAULT NULL,
  `modify_user_id` varchar(20) DEFAULT NULL,
  `sale_order_id` varchar(40) DEFAULT NULL COMMENT '销售订单id',
  `ticket_url` varchar(500) DEFAULT NULL COMMENT '签收小票URL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=382 DEFAULT CHARSET=utf8 COMMENT='采购收货单\r\n采购收货单表';

/*Data for the table `pm_purchase_receipt` */

insert  into `pm_purchase_receipt`(`id`,`purchase_receipt_no`,`purchase_order_id`,`asn`,`estimated_received_date`,`received_time`,`branch_company_id`,`status`,`exception_reason`,`create_time`,`modify_time`,`create_user_id`,`modify_user_id`,`sale_order_id`,`ticket_url`) values (1,'1708180001','1',NULL,'2017-08-18 11:16:34','2017-10-18 19:15:22',9997,3,NULL,'2017-08-18 11:16:34','2017-08-18 11:16:34','10018','10018',NULL,NULL),(2,'1708180002','2',NULL,'2017-08-18 17:20:08','2017-08-18 17:20:08',10000,3,NULL,'2017-08-18 17:20:08','2017-08-18 17:20:08','10018','10018','',NULL),(3,'1708120003','3',NULL,'2017-08-19 15:31:27','2017-08-19 15:31:27',10000,1,NULL,'2017-08-19 15:31:27','2017-08-30 16:59:52','10018','10018',NULL,NULL),(4,'1708220001','9',NULL,'2017-08-22 16:20:50','2017-08-22 16:20:50',10000,2,NULL,'2017-08-22 16:20:50','2017-08-30 16:59:52','10018','10018',NULL,NULL),(5,'1708230001','10',NULL,'2017-08-23 10:39:51','2018-01-16 17:37:02',10000,2,NULL,'2017-08-23 10:39:51','2017-08-30 16:59:52','10018','10018',NULL,NULL),(6,'1708230002','11',NULL,'2017-08-23 11:13:54','2017-08-23 11:13:54',10000,1,NULL,'2017-08-23 11:13:54','2017-08-30 16:59:52','10018','10018',NULL,NULL),(7,'1708230003','12',NULL,'2017-08-23 13:47:23','2017-08-23 13:47:23',10000,2,NULL,'2017-08-23 13:47:23','2017-08-30 16:59:53','10018','10018',NULL,NULL),(8,'1708230004','13',NULL,'2017-08-23 14:34:22','2017-08-23 14:34:22',10000,2,NULL,'2017-08-23 14:34:22','2017-08-30 16:59:53','10018','10018',NULL,NULL),(9,'1708230005','14',NULL,'2017-08-23 16:04:34','2017-08-23 16:04:34',10000,2,NULL,'2017-08-23 16:04:34','2017-08-30 16:59:53','10018','10018',NULL,NULL),(10,'1708230006','15',NULL,'2017-08-23 16:24:43','2017-08-23 16:24:43',10000,2,NULL,'2017-08-23 16:24:43','2017-08-30 16:59:53','10018','10018',NULL,NULL),(11,'1708240001','7',NULL,'2017-08-24 11:12:38','2017-08-24 11:12:38',10000,1,NULL,'2017-08-24 11:12:38','2017-08-30 16:59:52','10018','10018',NULL,NULL),(12,'1708280001','34',NULL,'2017-08-28 14:23:09','2017-08-28 14:23:09',10000,1,NULL,'2017-08-28 14:23:09','2017-08-30 16:59:52','10018','10018',NULL,NULL),(13,'1708280002','35',NULL,'2017-08-28 14:40:12','2017-08-28 14:40:12',10000,1,NULL,'2017-08-28 14:40:12','2017-08-30 16:59:52','10018','10018',NULL,NULL),(14,'1708290001','39',NULL,'2017-08-29 09:53:02','2017-08-29 09:53:02',10000,0,NULL,'2017-08-29 09:53:02','2017-08-29 09:53:02','10018','10018',NULL,NULL),(15,'1708290002','40',NULL,'2017-09-13 10:26:43',NULL,10000,0,NULL,'2017-08-29 10:28:33','2017-08-29 10:28:33','10018','10018',NULL,NULL),(16,'1708290003','41',NULL,'2017-09-13 11:26:27',NULL,10000,0,NULL,'2017-08-29 11:27:27','2017-08-29 11:27:27','10018','10018',NULL,NULL),(17,'1708290004','42',NULL,'2017-09-13 13:51:06',NULL,10000,0,NULL,'2017-08-29 14:06:50','2017-08-29 14:06:50','10018','10018',NULL,NULL),(18,'1708290005','44',NULL,'2017-09-13 13:56:06',NULL,10000,0,NULL,'2017-08-29 14:11:35','2017-08-29 14:11:35','10018','10018',NULL,NULL),(19,'1708290006','45',NULL,'2017-09-13 14:10:47',NULL,10000,0,NULL,'2017-08-29 14:12:02','2017-08-29 14:12:02','10018','10018',NULL,NULL),(20,'1708290007','48',NULL,'2017-09-13 14:51:46',NULL,10000,1,NULL,'2017-08-29 14:52:43','2017-08-30 16:59:52','10018','10018',NULL,NULL),(21,'1708290008','49',NULL,'2017-09-13 15:02:05',NULL,10000,0,NULL,'2017-08-29 15:06:35','2017-08-29 15:06:35','10018','10018',NULL,NULL),(22,'1708290009','51',NULL,'2017-09-13 15:05:43',NULL,10000,1,NULL,'2017-08-29 15:06:54','2017-08-30 16:59:52','10018','10018',NULL,NULL),(23,'1708290010','36',NULL,'2017-09-12 14:42:07',NULL,10000,1,NULL,'2017-08-29 16:18:35','2017-08-30 16:59:52','10018','10018',NULL,NULL),(24,'1708290011','52',NULL,'2017-09-13 16:34:33','2017-08-29 16:41:20',10000,2,NULL,'2017-08-29 16:41:20','2017-08-31 15:29:48','10018','10018',NULL,NULL),(25,'1708290012','53',NULL,'2017-09-13 17:47:37',NULL,10000,3,NULL,'2017-08-29 17:48:31','2017-10-12 15:18:02','10018','10018',NULL,NULL);

/*Table structure for table `pm_purchase_receipt_items` */

DROP TABLE IF EXISTS `pm_purchase_receipt_items`;

CREATE TABLE `pm_purchase_receipt_items` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_receipt_id` varchar(255) DEFAULT NULL COMMENT '收货单id',
  `purchase_order_id` varchar(255) DEFAULT NULL COMMENT '采购单id',
  `purchase_order_items_id` varchar(20) DEFAULT NULL COMMENT '采购单明细id',
  `delivery_number` int(11) DEFAULT NULL COMMENT '供应商出库数量,默认等于采购数量,预留与供应商系统对接预发货通知',
  `received_number` int(11) DEFAULT NULL COMMENT '已收货数量,收货数量<=出货数量',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `create_user_id` varchar(20) DEFAULT NULL COMMENT '创建人',
  `modify_user_id` varchar(20) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=493 DEFAULT CHARSET=utf8 COMMENT='采购收货单商品表';

/*Data for the table `pm_purchase_receipt_items` */

insert  into `pm_purchase_receipt_items`(`id`,`purchase_receipt_id`,`purchase_order_id`,`purchase_order_items_id`,`delivery_number`,`received_number`,`create_time`,`modify_time`,`create_user_id`,`modify_user_id`) values (1,'1','1','1',454,0,'2017-08-18 11:16:34','2017-08-18 11:16:34','10018','10018'),(2,'2','2','2',5654,0,'2017-08-18 17:20:08','2017-08-18 17:20:08','10018','10018'),(3,'2','2','3',4545,0,'2017-08-18 17:20:08','2017-08-18 17:20:08','10018','10018'),(4,'3','5','9',6,0,'2017-08-19 15:31:29','2017-08-19 15:31:29','10018','10018'),(5,'4','9','13',17,0,'2017-08-22 16:20:51','2017-08-22 16:20:51','10018','10018'),(6,'4','9','14',10,0,'2017-08-22 16:20:51','2017-08-22 16:20:51','10018',NULL),(7,'5','10','15',1000,0,'2017-08-23 10:39:51','2018-01-16 17:37:02','10018','10018'),(8,'6','11','16',1000,0,'2017-08-23 11:13:54','2017-08-23 11:13:54','10018','10018'),(9,'7','12','17',1000,0,'2017-08-23 13:47:23','2017-08-30 16:59:53','10018','10018'),(10,'8','13','18',1000,0,'2017-08-23 14:34:23','2017-08-30 16:59:53','10018','10018'),(11,'9','14','19',1000,0,'2017-08-23 16:04:34','2017-08-30 16:59:53','10018','10018'),(12,'10','15','20',1000,0,'2017-08-23 16:24:43','2017-08-30 16:59:53','10018','10018'),(13,'11','7','11',11111,0,'2017-08-24 11:12:41','2017-08-24 11:12:41','10018','10018'),(14,'12','34','39',1000,0,'2017-08-28 14:23:09','2017-08-28 14:23:09','10018','10018'),(15,'13','35','40',1000,0,'2017-08-28 14:40:12','2017-08-28 14:40:12','10018','10018'),(16,'14','39','44',1000,0,'2017-08-29 09:53:02','2017-08-29 09:53:02','10018','10018'),(17,'15','40','45',1000,0,'2017-08-29 10:28:33','2017-08-29 10:28:33','10018','10018'),(18,'16','41','46',1000,0,'2017-08-29 11:27:27','2017-08-29 11:27:27','10018','10018'),(19,'17','42','47',1000,0,'2017-08-29 14:06:52','2017-08-29 14:06:52','10018','10018'),(20,'18','44','49',100,0,'2017-08-29 14:11:36','2017-08-29 14:11:36','10018','10018');

/*Table structure for table `pm_purchase_refund` */

DROP TABLE IF EXISTS `pm_purchase_refund`;

CREATE TABLE `pm_purchase_refund` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_refund_no` varchar(255) DEFAULT NULL COMMENT '退货单号',
  `sp_id` varchar(255) DEFAULT NULL COMMENT '供应商ID',
  `sp_no` varchar(255) DEFAULT NULL COMMENT '供应商编码',
  `sp_name` varchar(255) DEFAULT NULL COMMENT '供应商名称',
  `sp_adr_id` varchar(255) DEFAULT NULL COMMENT '供应商地点ID',
  `sp_adr_no` varchar(255) DEFAULT NULL COMMENT '供应商地点编码',
  `sp_adr_name` varchar(255) DEFAULT NULL COMMENT '供应商地点名称',
  `total_refund_amount` int(11) DEFAULT NULL COMMENT '合计退货数量',
  `total_refund_money` decimal(20,2) DEFAULT NULL COMMENT '合计退货金额(含税)',
  `total_refund_cost` decimal(20,2) DEFAULT NULL COMMENT '合计退货成本额',
  `total_real_refund_amount` int(11) DEFAULT NULL COMMENT '合计实际退货数量',
  `total_real_refund_money` decimal(20,2) DEFAULT NULL COMMENT '合计实际退货金额(含税)',
  `adr_type` int(2) DEFAULT '0' COMMENT '退货地点类型:0:仓库;1:门店',
  `refund_adr_code` varchar(255) DEFAULT NULL COMMENT '退货地点编码',
  `refund_adr_name` varchar(255) DEFAULT NULL COMMENT '退货地点名称',
  `second_category_id` varchar(55) DEFAULT NULL COMMENT '二级分类id(大类)',
  `second_category_name` varchar(255) DEFAULT NULL COMMENT '二级分类名称(大类)',
  `currency_code` varchar(20) DEFAULT NULL COMMENT '货币种类代码,CNY',
  `branch_company_id` varchar(11) DEFAULT NULL COMMENT '子公司ID',
  `status` int(2) DEFAULT '0' COMMENT '状态:0:制单;1:已提交;2:已审核;3:已拒绝;4:待退货;5:已退货;6:已取消;7:取消失败;8:异常',
  `refund_time` datetime DEFAULT NULL COMMENT '退货日期',
  `refund_time_early` datetime DEFAULT NULL COMMENT '退货日期早于(参数配置值加当前日期)',
  `failed_reason` varchar(500) DEFAULT NULL COMMENT '失败原因',
  `process_id` bigint(20) DEFAULT NULL COMMENT '流程ID',
  `current_process_id` bigint(20) DEFAULT NULL COMMENT '当前节点流程ID',
  `create_user_id` varchar(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_user_id` varchar(20) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `audit_user_id` varchar(20) DEFAULT NULL COMMENT '审核人ID',
  `audit_time` datetime DEFAULT NULL COMMENT '审核日期',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8 COMMENT='退货单表';

/*Data for the table `pm_purchase_refund` */

insert  into `pm_purchase_refund`(`id`,`purchase_refund_no`,`sp_id`,`sp_no`,`sp_name`,`sp_adr_id`,`sp_adr_no`,`sp_adr_name`,`total_refund_amount`,`total_refund_money`,`total_refund_cost`,`total_real_refund_amount`,`total_real_refund_money`,`adr_type`,`refund_adr_code`,`refund_adr_name`,`second_category_id`,`second_category_name`,`currency_code`,`branch_company_id`,`status`,`refund_time`,`refund_time_early`,`failed_reason`,`process_id`,`current_process_id`,`create_user_id`,`create_time`,`modify_user_id`,`modify_time`,`audit_user_id`,`audit_time`,`remark`) values (59,'17112800009','xprov008','10007','四川福仁缘农业开发有限公司','8','1000008','全国-四川福仁缘农业开发有限公司',5,'331.65','298.80',1,'66.33',0,'YT901','深圳仓深圳(广东子公司直营)常温正常仓',NULL,NULL,'CNY','10000',3,'2017-12-12 16:08:41','2017-12-05 11:36:00',NULL,NULL,NULL,'yaomuming','2017-11-28 11:37:37',NULL,NULL,'yinyuxin','2017-12-12 15:05:57',NULL);

/*Table structure for table `pm_purchase_refund_item` */

DROP TABLE IF EXISTS `pm_purchase_refund_item`;

CREATE TABLE `pm_purchase_refund_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `purchase_refund_id` bigint(20) DEFAULT NULL COMMENT '退货单ID',
  `purchase_order_no` varchar(255) DEFAULT NULL COMMENT '采购单号',
  `product_id` varchar(255) DEFAULT NULL COMMENT '商品id',
  `product_code` varchar(20) DEFAULT NULL COMMENT '商品编号',
  `product_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `international_code` varchar(20) DEFAULT NULL COMMENT '条码(国际码)',
  `packing_specifications` varchar(20) DEFAULT NULL COMMENT '规格',
  `produce_place` varchar(255) DEFAULT NULL COMMENT '产地',
  `purchase_inside_number` int(11) DEFAULT NULL COMMENT '采购内装数(默认箱规)',
  `unit_explanation` varchar(20) DEFAULT NULL COMMENT '单位',
  `input_tax_rate` decimal(4,2) DEFAULT NULL COMMENT '税率,进项税率',
  `purchase_price` decimal(20,2) DEFAULT NULL COMMENT '采购价格（含税）',
  `possible_num` int(20) DEFAULT NULL COMMENT '可退库存',
  `refund_amount` int(11) DEFAULT NULL COMMENT '退货数量',
  `refund_money` decimal(20,2) DEFAULT NULL COMMENT '退货金额(含税)',
  `refund_cost` decimal(20,2) DEFAULT NULL COMMENT '退货成本额',
  `real_refund_amount` int(11) DEFAULT NULL COMMENT '实际退货数量',
  `real_refund_money` decimal(20,2) DEFAULT NULL COMMENT '实际退货金额(含税)',
  `refund_reason` tinyint(255) DEFAULT NULL COMMENT '退货原因(0:破损;1:临期;2:库存过剩;3:其他)',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `create_user_id` varchar(20) DEFAULT NULL,
  `modify_user_id` varchar(20) DEFAULT NULL,
  `is_valid` int(1) DEFAULT '1' COMMENT '是否有效:0,无效,1:有效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=289 DEFAULT CHARSET=utf8 COMMENT='退货单商品表';

/*Data for the table `pm_purchase_refund_item` */

insert  into `pm_purchase_refund_item`(`id`,`purchase_refund_id`,`purchase_order_no`,`product_id`,`product_code`,`product_name`,`international_code`,`packing_specifications`,`produce_place`,`purchase_inside_number`,`unit_explanation`,`input_tax_rate`,`purchase_price`,`possible_num`,`refund_amount`,`refund_money`,`refund_cost`,`real_refund_amount`,`real_refund_money`,`refund_reason`,`create_time`,`modify_time`,`create_user_id`,`modify_user_id`,`is_valid`) values (138,59,'17111700007','60000117016','1000042','金龙鱼清香稻5KG','6948195860136','5KG','成都',1,'袋1','11.00','66.33',NULL,5,'331.65','298.80',1,'66.33',3,'2017-11-28 11:37:37','2017-11-17 19:11:03','yaomuming',NULL,1);

/*Table structure for table `process_audit_log` */

DROP TABLE IF EXISTS `process_audit_log`;

CREATE TABLE `process_audit_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `business_id` bigint(20) DEFAULT NULL COMMENT '业务单号ID',
  `business_type` int(1) DEFAULT NULL COMMENT '业务类型:0:采购单;1:退货单;2:商品采购维护;3商品销售维护',
  `process_id` bigint(20) DEFAULT NULL COMMENT '流程ID',
  `audit_user_id` varchar(20) DEFAULT NULL COMMENT '审核人ID',
  `audit_user` varchar(20) DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核日期',
  `audit_result` bigint(2) DEFAULT NULL COMMENT '审批结果:0:拒绝;1:通过',
  `audit_opinion` varchar(255) DEFAULT NULL COMMENT '审批意见',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=249 DEFAULT CHARSET=utf8 COMMENT='流程审批日志表';

/*Data for the table `process_audit_log` */

insert  into `process_audit_log`(`id`,`business_id`,`business_type`,`process_id`,`audit_user_id`,`audit_user`,`audit_time`,`audit_result`,`audit_opinion`) values (192,59,1,4,'10018','姚木明','2017-11-28 11:37:46',1,NULL),(193,59,1,5,'10018','姚木明','2017-11-28 11:37:54',1,NULL),(194,59,1,6,'11901','dada','2017-11-28 11:38:11',1,NULL),(195,60,1,4,'10018','姚木明','2017-11-28 11:56:02',1,NULL),(196,60,1,5,'10018','姚木明','2017-11-28 11:56:32',1,NULL),(197,60,1,6,'11901','dada','2017-11-28 11:56:50',1,NULL),(198,61,1,4,'10018','姚木明','2017-11-28 12:08:01',0,'tet'),(199,61,1,4,'10018','姚木明','2017-11-28 12:10:14',0,'ry4');

/*Table structure for table `process_definition` */

DROP TABLE IF EXISTS `process_definition`;

CREATE TABLE `process_definition` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `process_node_code` varchar(255) DEFAULT NULL COMMENT '流程节点编码',
  `process_node_name` varchar(255) DEFAULT NULL COMMENT '流程节点名称',
  `next_node_id` bigint(20) DEFAULT NULL COMMENT '下一个流程节点ID',
  `branch_company_id` varchar(11) DEFAULT NULL COMMENT '子公司ID',
  `type` bigint(2) DEFAULT NULL COMMENT '流程类型:0:采购单;1:退货单;2:商品采购维护;3商品销售维护',
  `create_time` datetime DEFAULT NULL,
  `modify_time` datetime DEFAULT NULL,
  `create_user_id` varchar(20) DEFAULT NULL,
  `modify_user_id` varchar(20) DEFAULT NULL,
  `is_first_node` int(1) DEFAULT '1' COMMENT '是否首节点:0,是,1:否',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COMMENT='流程定义表';

/*Data for the table `process_definition` */

insert  into `process_definition`(`id`,`process_node_code`,`process_node_name`,`next_node_id`,`branch_company_id`,`type`,`create_time`,`modify_time`,`create_user_id`,`modify_user_id`,`is_first_node`) values (4,'93','成都子公司总经理',5,'10000',1,'2017-11-14 10:16:02','2017-11-14 10:16:05','1','1',0),(5,'93','成都子公司总经理',6,'10000',1,'2017-11-14 10:23:22','2017-11-14 10:23:25','1','1',1),(6,'381','成都子公司财务经理（采购）',NULL,'10000',1,'2017-11-14 10:33:14','2017-11-14 10:33:17','1','1',1),(7,'382','重庆子公司采购主管',8,'10005',1,'2017-11-14 10:36:16','2017-11-14 10:36:19','1','1',0),(8,'120','重庆子公司总经理',9,'10005',1,'2017-11-14 10:36:25','2017-11-14 10:36:22','1','1',1),(9,'383','重庆子公司财务经理（采购）',NULL,'10005',1,'2017-11-14 10:37:30','2017-11-14 10:37:32','1','1',1),(55,'407','子公司财务经理(采购)',NULL,NULL,1,'2017-11-30 14:25:29','2017-11-30 14:25:29','1','1',1),(56,'22','子公司总经理',55,NULL,1,'2017-11-30 14:25:29','2017-11-30 14:25:29','1','1',1),(57,'382','子公司采购主管',56,NULL,1,'2017-11-30 14:25:29','2017-11-30 14:25:29','1','1',0);

/*Table structure for table `prod_adr_info` */

DROP TABLE IF EXISTS `prod_adr_info`;

CREATE TABLE `prod_adr_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_id` varchar(255) DEFAULT NULL COMMENT '商品id',
  `adr_type` tinyint(2) DEFAULT NULL COMMENT '地点类型（0：子公司；1：门店；2：区域组）',
  `adr_code` varchar(50) DEFAULT NULL COMMENT '地点编码',
  `adr_name` varchar(50) DEFAULT NULL COMMENT '地点名称',
  `supplier_id` varchar(50) DEFAULT NULL COMMENT '供应商id',
  `supplier_adr_id` varchar(50) DEFAULT NULL COMMENT '供应商地点id',
  `logistics_mode` tinyint(2) DEFAULT NULL COMMENT '物流模式（0：配送；1：直送）',
  `status` tinyint(2) DEFAULT '1' COMMENT '状态（0：失效；1：正常）',
  `create_user_id` varchar(20) DEFAULT NULL COMMENT '创建人',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `modify_user_id` varchar(20) DEFAULT NULL COMMENT '修改人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `audit_user_id` varchar(20) DEFAULT NULL COMMENT '审核人',
  `audit_time` datetime DEFAULT NULL COMMENT '审核时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品地点关系';

/*Data for the table `prod_adr_info` */

/*Table structure for table `prod_price_change` */

DROP TABLE IF EXISTS `prod_price_change`;

CREATE TABLE `prod_price_change` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `change_type` int(2) DEFAULT '0' COMMENT '变更类型:0:采购进价变更;1:售价变更',
  `sp_id` varchar(255) DEFAULT NULL COMMENT '供应商ID',
  `sp_adr_id` varchar(255) DEFAULT NULL COMMENT '供应商地点ID',
  `branch_company_id` varchar(50) DEFAULT NULL COMMENT '子公司id',
  `product_id` varchar(255) DEFAULT NULL COMMENT '商品ID',
  `gross_profit_margin` decimal(20,2) DEFAULT NULL COMMENT '毛利率',
  `price` decimal(20,2) DEFAULT NULL COMMENT '当前价格',
  `newest_price` decimal(20,2) DEFAULT NULL COMMENT '提交价格',
  `percentage` decimal(20,2) DEFAULT NULL COMMENT '调价百分比',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  `create_user_id` varchar(20) DEFAULT NULL COMMENT '操作人',
  `price_id` bigint(20) NOT NULL COMMENT '采购价ID，售价区间ID',
  `product_code` varchar(255) DEFAULT NULL COMMENT '商品编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=356 DEFAULT CHARSET=utf8 COMMENT='价格变更记录表';

/*Data for the table `prod_price_change` */

insert  into `prod_price_change`(`id`,`change_type`,`sp_id`,`sp_adr_id`,`branch_company_id`,`product_id`,`gross_profit_margin`,`price`,`newest_price`,`percentage`,`create_time`,`create_user_id`,`price_id`,`product_code`) values (1,1,'xprov009','9','10000','60000117016','1.00',NULL,NULL,NULL,NULL,'10001',1779,''),(2,1,'xprov009','9','10000','60000117016','3.00',NULL,NULL,'-1.00',NULL,'10001',1779,'');

/*Table structure for table `prod_purchase_info` */

DROP TABLE IF EXISTS `prod_purchase_info`;

CREATE TABLE `prod_purchase_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'pk',
  `sp_id` varchar(255) DEFAULT NULL COMMENT '供应商ID',
  `sp_adr_id` varchar(255) DEFAULT NULL COMMENT '供应商地点id',
  `product_id` varchar(255) DEFAULT NULL COMMENT '商品ID',
  `branch_company_id` varchar(50) DEFAULT NULL COMMENT '子公司编码',
  `supplier_type` int(1) DEFAULT '0' COMMENT '供应商类型:0：一般供应商,1:主供应商',
  `purchase_inside_number` int(11) DEFAULT NULL COMMENT '采购内装数(默认箱规)',
  `purchase_price` decimal(20,2) DEFAULT NULL COMMENT '采购价格',
  `international_code` varchar(20) DEFAULT NULL COMMENT '条码(国际码)',
  `distribute_warehouse_id` bigint(20) DEFAULT NULL COMMENT '配送仓库id',
  `status` int(1) DEFAULT '0' COMMENT '采购关系的状态:0,,失效,1启用',
  `delete_status` int(1) DEFAULT '0' COMMENT '删除状态:0,,未删除,1启删除',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  `modify_time` datetime DEFAULT NULL COMMENT '修改时间',
  `modify_user_id` varchar(50) DEFAULT NULL COMMENT '修改人',
  `modify_id` bigint(20) DEFAULT NULL,
  `failed_reason` varchar(500) DEFAULT NULL COMMENT '失败原因',
  `audit_user_id` varchar(20) DEFAULT NULL COMMENT '审核人ID',
  `audit_time` datetime DEFAULT NULL COMMENT '审核日期',
  `audit_status` int(2) DEFAULT '2' COMMENT '审核状态:1:已提交;2:已审核;3:已拒绝',
  `newest_price` decimal(20,2) DEFAULT NULL COMMENT '最新价格',
  `percentage` decimal(20,2) DEFAULT '0.00' COMMENT '调价百分比',
  `first_created` int(2) DEFAULT '0' COMMENT '第一次创建使用:1:是;0:否',
  `support_return` int(2) DEFAULT '1' COMMENT '是否支持退货:1:支持;0:不支持',
  PRIMARY KEY (`id`),
  KEY `index_ppi_product_id` (`product_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1891 DEFAULT CHARSET=utf8 COMMENT='商品采购关系';

/*Data for the table `prod_purchase_info` */

insert  into `prod_purchase_info`(`id`,`sp_id`,`sp_adr_id`,`product_id`,`branch_company_id`,`supplier_type`,`purchase_inside_number`,`purchase_price`,`international_code`,`distribute_warehouse_id`,`status`,`delete_status`,`create_time`,`create_user_id`,`modify_time`,`modify_user_id`,`modify_id`,`failed_reason`,`audit_user_id`,`audit_time`,`audit_status`,`newest_price`,`percentage`,`first_created`,`support_return`) values (7,NULL,'59','60001883563','10000',0,1,'16.00','4',2,1,0,'2017-08-18 00:00:00','12231',NULL,NULL,NULL,NULL,NULL,NULL,2,NULL,'0.00',0,1);

/*Table structure for table `prod_purchase_info_import` */

DROP TABLE IF EXISTS `prod_purchase_info_import`;

CREATE TABLE `prod_purchase_info_import` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据自增ID',
  `imports_id` bigint(20) NOT NULL COMMENT '上传ID',
  `line_number` bigint(20) DEFAULT NULL COMMENT '行号',
  `handle_result` int(2) DEFAULT NULL COMMENT '处理结果:0:错误;1:已验证;2:已提交',
  `handle_information` varchar(500) DEFAULT NULL COMMENT '处理信息',
  `product_id` varchar(255) DEFAULT NULL COMMENT '商品id',
  `product_information` varchar(500) DEFAULT NULL COMMENT '商品信息',
  `newest_price` decimal(20,2) DEFAULT NULL COMMENT '最新售价',
  `sp_id` varchar(255) DEFAULT NULL COMMENT '供应商id',
  `sp_adr_id` int(11) DEFAULT NULL COMMENT '供应商地点id',
  `price_id` bigint(20) DEFAULT NULL COMMENT '采购表Id',
  `sp_no` varchar(255) DEFAULT NULL COMMENT '供应商编号',
  `sp_adr_no` varchar(255) DEFAULT NULL COMMENT '供应商地点编码',
  `product_code` varchar(255) DEFAULT NULL COMMENT '商品编码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=112 DEFAULT CHARSET=utf8 COMMENT='商品采购价导入记录表';

/*Data for the table `prod_purchase_info_import` */

insert  into `prod_purchase_info_import`(`id`,`imports_id`,`line_number`,`handle_result`,`handle_information`,`product_id`,`product_information`,`newest_price`,`sp_id`,`sp_adr_id`,`price_id`,`sp_no`,`sp_adr_no`,`product_code`) values (1,10000015,1,0,'此商品和供应商没有采购关系，请检查',NULL,NULL,'100.00',NULL,NULL,NULL,NULL,NULL,NULL),(2,10000015,2,0,'此商品和供应商没有采购关系，请检查',NULL,NULL,'111.00',NULL,NULL,NULL,NULL,NULL,NULL);

/*Table structure for table `prod_purchase_info_imports` */

DROP TABLE IF EXISTS `prod_purchase_info_imports`;

CREATE TABLE `prod_purchase_info_imports` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '调价单ID(上传ID)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000058 DEFAULT CHARSET=utf8 COMMENT='商品采购价导入表(调价单)';

/*Data for the table `prod_purchase_info_imports` */

insert  into `prod_purchase_info_imports`(`id`,`create_time`,`create_user_id`) values (10000015,'2017-12-12 16:41:24','10000'),(10000016,'2017-12-12 16:41:49','10000'),(10000017,'2017-12-13 10:11:51','10000'),(10000019,'2017-12-13 10:47:12','10000'),(10000020,'2017-12-13 14:52:52','10000'),(10000021,'2017-12-13 16:25:23','10027'),(10000022,'2017-12-13 18:28:20','10027'),(10000023,'2017-12-19 11:31:07','10027'),(10000024,'2017-12-19 13:56:50','10000'),(10000025,'2017-12-19 13:59:14','10000'),(10000026,'2017-12-26 16:19:36','10027'),(10000027,'2017-12-27 16:31:42','10027'),(10000028,'2017-12-27 17:45:26','10027'),(10000029,'2017-12-27 17:46:59','10027'),(10000030,'2017-12-29 17:02:53','123'),(10000031,'2017-12-29 18:06:10','10027'),(10000033,'2018-01-03 17:43:18','10022'),(10000035,'2018-01-05 11:50:08','10021'),(10000036,'2018-01-05 17:36:14','10018'),(10000037,'2018-01-05 18:18:56','10027'),(10000038,'2018-01-05 19:01:06','10027'),(10000039,'2018-01-05 19:03:42','10027'),(10000040,'2018-01-05 19:07:24','10027'),(10000041,'2018-01-05 19:16:53','10027'),(10000042,'2018-01-05 19:17:02','10027'),(10000043,'2018-01-05 19:17:56','10027'),(10000044,'2018-01-05 19:50:16','10027'),(10000045,'2018-01-05 20:11:27','10027'),(10000046,'2018-01-06 13:57:57','10027'),(10000047,'2018-01-06 14:30:46','10027'),(10000048,'2018-01-06 15:31:18','10027'),(10000049,'2018-01-06 15:34:08','10027'),(10000050,'2018-01-06 17:34:51','10021'),(10000051,'2018-01-08 15:07:17','10021'),(10000052,'2018-01-08 15:08:12','10021'),(10000053,'2018-01-08 15:08:20','10021'),(10000054,'2018-01-08 15:20:19','10021'),(10000055,'2018-01-08 15:20:44','10021'),(10000056,'2018-01-08 16:38:37','10021'),(10000057,'2018-01-08 16:41:28','11753');

/*Table structure for table `prod_purchase_info_log` */

DROP TABLE IF EXISTS `prod_purchase_info_log`;

CREATE TABLE `prod_purchase_info_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `price_id` varchar(255) DEFAULT NULL COMMENT '采购价格ID',
  `purchase_price` decimal(20,2) DEFAULT NULL COMMENT '采购价格',
  `operate` varchar(255) DEFAULT NULL COMMENT '操作',
  `operate_date` datetime DEFAULT NULL COMMENT '操作时间',
  `operate_user_id` varchar(255) DEFAULT NULL COMMENT '操作用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=301 DEFAULT CHARSET=utf8;

/*Data for the table `prod_purchase_info_log` */

insert  into `prod_purchase_info_log`(`id`,`price_id`,`purchase_price`,`operate`,`operate_date`,`operate_user_id`) values (1,'null','3.45','新增商品采购关系','2017-12-07 13:52:45',NULL),(2,'null','3.45','新增商品采购关系','2017-12-07 13:58:47',NULL),(3,'null','3.45','新增商品采购关系','2017-12-07 14:03:45',NULL),(4,'1786','3.45','新增商品采购关系','2017-12-07 14:22:17',NULL),(5,'1787','3.45','新增商品采购关系','2017-12-07 14:26:24',NULL),(6,'1788','3.45','新增商品采购关系','2017-12-07 14:27:24',NULL),(7,'1789','3.45','新增商品采购关系','2017-12-07 14:30:50',NULL),(8,'1789','3.41','更新商品采购关系','2017-12-07 14:47:23',NULL),(9,'1789',NULL,'删除采购关系操作','2017-12-07 15:04:12','delete_user_id'),(10,'1789',NULL,'切换供应商类型操作','2017-12-07 15:35:23','change_supplier_type_user_id'),(11,'1789',NULL,'更改采购关系启禁用状态','2017-12-07 15:53:14','change_status_id'),(12,'1789',NULL,'更改采购关系启禁用状态','2017-12-07 15:54:58','change_status_id'),(13,'1789',NULL,'更新商品采购关系','2017-12-07 16:38:30',NULL),(16,'1798',NULL,'新增商品采购关系','2017-12-14 14:13:54','user_add_223'),(17,'1799',NULL,'新增商品采购关系','2017-12-14 14:21:28','user_add_223'),(18,'1800','10.23','新增商品采购关系','2017-12-14 14:22:34','user_add_223'),(19,'1800','10.23','更新商品采购关系','2017-12-14 14:33:09',NULL),(21,'1805','10.22','更新商品采购关系','2017-12-14 15:40:25',NULL),(22,'1805',NULL,'删除采购关系操作','2017-12-14 15:48:55','user_delete_123'),(23,'1805',NULL,'切换供应商类型操作','2017-12-14 16:14:51','user_changeSupplierType_123'),(24,'1805',NULL,'切换供应商类型操作','2017-12-14 16:16:23','user_changeSupplierType_123'),(25,'1805',NULL,'切换供应商类型操作','2017-12-14 16:16:58','user_changeSupplierType_123'),(26,'1805',NULL,'更改采购关系启禁用状态','2017-12-14 16:34:45','user_changeStatus_123'),(30,'1775',NULL,'切换供应商类型操作','2017-12-27 11:02:25','10018'),(31,'1775',NULL,'切换供应商类型操作','2017-12-27 11:02:27','10018'),(32,'1772','4.80','更新商品采购关系','2017-12-27 15:08:19','10027'),(33,'1773','35.50','更新商品采购关系','2017-12-27 15:22:09','10027'),(37,'1817','10.23','新增商品采购关系','2017-12-27 16:04:45','user_add_223'),(38,'1521',NULL,'更新商品采购关系','2017-12-27 16:19:58',NULL),(46,'1825',NULL,'新增商品采购关系','2017-12-28 10:21:52','10018'),(47,'1826',NULL,'新增商品采购关系','2017-12-28 10:26:38','10018'),(48,'1794',NULL,'切换供应商类型操作','2017-12-28 10:29:07','10027'),(50,'1794',NULL,'切换供应商类型操作','2017-12-28 10:29:55','10027'),(51,'1775',NULL,'切换供应商类型操作','2017-12-28 10:30:16','10027'),(52,'1828',NULL,'新增商品采购关系','2017-12-28 10:30:01','10018'),(53,'1793',NULL,'更改采购关系启禁用状态','2017-12-28 14:28:00','10027'),(54,'1793',NULL,'更改采购关系启禁用状态','2017-12-28 14:28:25','10027'),(55,'1793',NULL,'更新商品采购关系','2017-12-28 14:59:34',NULL),(56,'1510',NULL,'更新商品采购关系','2017-12-28 15:03:04',NULL),(57,'1793',NULL,'更新商品采购关系','2017-12-28 16:16:16',NULL),(58,'1829','15.00','新增商品采购关系','2017-12-29 11:12:03','10018'),(59,'1828',NULL,'切换供应商类型操作','2018-01-03 09:22:20','10027'),(60,'1828',NULL,'切换供应商类型操作','2018-01-03 09:22:24','10027'),(61,'1831','888888.00','新增商品采购关系','2018-01-03 09:55:39','10027'),(62,'1775','35.00','更新商品采购关系','2018-01-03 10:33:45','10018'),(63,'1832','16.00','新增商品采购关系','2018-01-03 14:10:37','10018'),(64,'1833','16.00','新增商品采购关系','2018-01-03 14:37:40','10018'),(65,'1833',NULL,'切换供应商类型操作','2018-01-03 14:43:33','10018'),(66,'1833',NULL,'切换供应商类型操作','2018-01-03 14:43:35','10018'),(67,'1834','30.00','新增商品采购关系','2018-01-03 14:50:16','10018'),(68,'1835','15.00','新增商品采购关系','2018-01-03 15:13:29','10018');

/*Table structure for table `prod_sell_info` */

DROP TABLE IF EXISTS `prod_sell_info`;

CREATE TABLE `prod_sell_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '销售价格主键id',
  `product_id` varchar(255) DEFAULT NULL COMMENT '商品id',
  `lowest_price` decimal(20,2) DEFAULT NULL COMMENT '最低销售价',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(20) DEFAULT NULL COMMENT '创建人',
  `modify_time` datetime DEFAULT NULL COMMENT '删除或修改时间',
  `modify_user_id` varchar(20) DEFAULT NULL COMMENT '删除或修改人',
  `status` int(1) DEFAULT '1' COMMENT '0为失效状态1为正常使用状态',
  `delete_status` int(1) DEFAULT '0' COMMENT '0为未删除状态1为删除状态',
  `branch_company_id` varchar(50) DEFAULT NULL COMMENT '子公司id',
  `branch_company_name` varchar(255) DEFAULT NULL COMMENT '子公司名字',
  `suggest_price` decimal(20,2) DEFAULT NULL COMMENT '建议零售价',
  `delivery_day` int(11) DEFAULT NULL COMMENT '承诺最迟发货时间（天）',
  `sales_inside_number` int(11) DEFAULT NULL COMMENT '销售内装数',
  `min_number` int(11) DEFAULT NULL COMMENT '起订量',
  `sku_id` varchar(20) DEFAULT NULL COMMENT 'skuID',
  `pre_harvest_pin_status` int(1) DEFAULT NULL COMMENT '是否先采后销  1：是  0：否',
  `max_number` int(11) DEFAULT NULL COMMENT '最大销售数量',
  `max_purchase` int(11) DEFAULT NULL COMMENT '最大购买数量',
  `failed_reason` varchar(500) DEFAULT NULL COMMENT '失败原因',
  `audit_user_id` varchar(20) DEFAULT NULL COMMENT '审核人ID',
  `audit_time` datetime DEFAULT NULL COMMENT '审核日期',
  `audit_status` int(2) DEFAULT '2' COMMENT '审核状态:1:已提交;2:已审核;3:已拒绝',
  `first_created` int(2) DEFAULT '0' COMMENT '第一次创建使用:1:是;0:否',
  `price_protection` tinyint(4) NOT NULL DEFAULT '0' COMMENT '价格保护',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=758 DEFAULT CHARSET=utf8 COMMENT='商品销售价格定价信息表';

/*Data for the table `prod_sell_info` */

insert  into `prod_sell_info`(`id`,`product_id`,`lowest_price`,`create_time`,`create_user_id`,`modify_time`,`modify_user_id`,`status`,`delete_status`,`branch_company_id`,`branch_company_name`,`suggest_price`,`delivery_day`,`sales_inside_number`,`min_number`,`sku_id`,`pre_harvest_pin_status`,`max_number`,`max_purchase`,`failed_reason`,`audit_user_id`,`audit_time`,`audit_status`,`first_created`,`price_protection`) values (654,'xprod347938','25.00','2017-12-19 15:28:24','10027','2018-01-05 17:45:13','10027',1,1,'10000','成都子公司','2.50',5,5,5,NULL,1,40,NULL,NULL,NULL,NULL,2,0,0),(655,'xprod347938','30.00','2017-12-19 15:28:50','10027','2018-01-05 17:45:17','10027',1,1,'10004','湖南子公司','2.50',5,10,10,NULL,1,50,NULL,NULL,NULL,NULL,2,0,0),(656,'60000335741','100.00','2017-12-19 16:48:02','10027',NULL,NULL,1,0,'10000','成都子公司','15.00',12,1,1,NULL,0,NULL,NULL,NULL,NULL,NULL,1,0,0),(657,'49957','500.00','2017-12-19 16:52:39','10027','2017-12-26 14:19:01','10027',1,0,'10004','湖南子公司','19.50',4,1,1,NULL,0,NULL,NULL,NULL,NULL,NULL,1,0,0),(658,'60000335741','120.00','2017-12-19 16:52:48','10027','2018-01-30 15:00:24','12064',1,0,'10004','湖南子公司','15.00',12,1,1,NULL,0,NULL,NULL,NULL,'12064','2018-01-30 15:00:38',2,0,0),(660,'49957','15.51','2017-12-19 17:10:31','10027','2018-01-15 14:23:33','10018',0,1,'10000','成都子公司','19.50',4,1,1,NULL,0,2147483647,NULL,NULL,'11753','2018-01-08 11:29:23',2,0,0),(661,'60000129911','17.50','2017-12-19 17:10:31','10027',NULL,NULL,1,0,'10000','成都子公司','21.90',4,1,1,NULL,0,2147483647,NULL,NULL,NULL,NULL,1,0,0),(662,'50026','17.50','2017-12-19 17:10:31','10027',NULL,NULL,1,0,'10000','成都子公司','21.90',4,1,1,NULL,0,2147483647,NULL,NULL,NULL,NULL,1,0,0),(663,'50027','20.00','2017-12-19 17:10:31','10027','2018-01-08 14:35:42','10027',1,0,'10000','成都子公司','21.66',4,3,3,NULL,1,2147483647,NULL,NULL,'11753','2018-01-08 14:38:40',2,0,0),(664,'xprod347913','12.00','2017-12-19 17:52:09','10027',NULL,NULL,1,0,'10004','湖南子公司','3.00',5,1,1,NULL,0,NULL,NULL,NULL,NULL,NULL,2,0,0),(666,'60000117016','1.50','2017-12-20 19:08:39','10027','2018-03-05 14:15:29','11753',0,0,'10000','成都子公司','42.00',7,10,100,NULL,0,NULL,NULL,NULL,NULL,NULL,1,0,0),(669,'60000117016','40.00','2017-12-26 14:16:14','10027','2018-01-06 10:47:06','10027',1,0,'100067','湖南省益阳子公司','42.00',7,10,10,NULL,0,100,NULL,NULL,NULL,NULL,1,0,0),(672,'60000117016','22.00','2017-12-27 17:02:19','987654','2018-01-06 09:54:27','10027',1,0,'123456','测试子公司2','12.00',10,10,10,NULL,1,NULL,NULL,NULL,NULL,NULL,1,1,0),(673,'60000117016','52.00','2017-12-28 14:38:13','10027','2017-12-29 10:08:04','10027',1,1,'100028','雅堂小超','42.00',7,1,45,NULL,0,NULL,NULL,NULL,'741',NULL,2,0,0),(674,'60000117016','2.00','2017-12-28 15:20:46','10027','2018-01-05 13:46:23','10027',1,1,'10004','湖南子公司','42.00',7,1,1,NULL,0,NULL,NULL,NULL,'12065','2018-01-04 21:06:56',3,1,0),(675,'xprod351374','100.00','2018-01-03 16:10:19','10027',NULL,NULL,1,0,'10000','成都子公司','50.00',2,1,1,NULL,0,10000,NULL,NULL,'11753','2018-01-03 16:45:46',2,0,0),(676,'xprod351374','100.00','2018-01-03 16:11:06','10027','2018-02-02 14:56:14','10027',1,0,'10004','湖南子公司','50.00',3,1,1,NULL,0,10000,NULL,NULL,'12064','2018-02-02 14:56:38',2,0,0),(677,'50503','100.00','2018-01-03 17:56:48','10018','2018-02-22 13:44:25','10027',1,0,'10000','成都子公司','1.50',12,1,3,NULL,1,NULL,NULL,NULL,'11753','2018-01-04 20:39:18',1,0,0),(678,'60001769798','100.00','2018-01-03 18:04:51','10018',NULL,NULL,1,0,'10000','成都子公司','5.80',6,1,3,NULL,0,NULL,NULL,NULL,'11753','2018-01-04 17:30:50',1,0,0),(679,'xprod347509','100.00','2018-01-03 18:06:58','10018',NULL,NULL,1,0,'10000','成都子公司','11.90',6,1,2,NULL,0,NULL,NULL,NULL,'11753','2018-01-04 11:54:34',1,1,0),(680,'60000380287','100.00','2018-01-03 18:08:16','10018',NULL,NULL,1,0,'10000','成都子公司','6.50',6,1,4,NULL,0,NULL,NULL,NULL,'11753','2018-01-04 16:59:11',2,0,0),(681,'xprod347510','100.00','2018-01-03 19:46:21','10018',NULL,NULL,1,0,'10000','成都子公司','8.90',9,1,1,NULL,1,2,NULL,NULL,'11753','2018-01-04 11:36:37',1,0,0),(682,'xprod347927','200.00','2018-01-04 10:32:33','10027','2018-01-31 17:02:28','10027',1,0,'10004','湖南子公司','6.00',5,2,2,NULL,0,20,NULL,NULL,'12064','2018-01-31 17:02:44',2,0,0);

/*Table structure for table `prod_sell_info_events` */

DROP TABLE IF EXISTS `prod_sell_info_events`;

CREATE TABLE `prod_sell_info_events` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据自增ID',
  `price_id` bigint(20) NOT NULL COMMENT '售价ID',
  `serialized_payload` longtext COMMENT '序列化',
  `create_time` datetime DEFAULT NULL COMMENT '操作时间',
  `create_user_id` varchar(20) DEFAULT NULL COMMENT '操作人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=228 DEFAULT CHARSET=utf8 COMMENT='售价修改事件表';

/*Data for the table `prod_sell_info_events` */

insert  into `prod_sell_info_events`(`id`,`price_id`,`serialized_payload`,`create_time`,`create_user_id`) values (2,668,'{\"auditStatus\":1,\"branchCompanyId\":\"9999\",\"branchCompanyName\":\"测试子公司\",\"createTime\":1513828286528,\"createUserId\":\"0000000\",\"deleteStatus\":0,\"deliveryDay\":10,\"firstCreated\":1,\"id\":668,\"lowestPrice\":22,\"minNumber\":10,\"preHarvestPinStatus\":1,\"productId\":\"xprod347938\",\"salesInsideNumber\":2,\"sellSectionPrices\":[{\"deleteStatus\":0,\"endNumber\":20,\"id\":3,\"price\":24,\"sellPriceId\":668,\"startNumber\":10},{\"deleteStatus\":0,\"endNumber\":30,\"id\":4,\"price\":23,\"sellPriceId\":668,\"startNumber\":21},{\"deleteStatus\":0,\"endNumber\":2147483647,\"id\":5,\"price\":22,\"sellPriceId\":668,\"startNumber\":31}],\"status\":1,\"suggestPrice\":12}','2017-12-21 11:51:32','0000000'),(5,668,'{\"auditStatus\":1,\"branchCompanyId\":\"9999\",\"branchCompanyName\":\"测试子公司\",\"deleteStatus\":0,\"deliveryDay\":10,\"firstCreated\":0,\"id\":668,\"lowestPrice\":23,\"minNumber\":8,\"modifyTime\":1513839418312,\"modifyUserId\":\"222222\",\"preHarvestPinStatus\":1,\"productId\":\"xprod347938\",\"salesInsideNumber\":2,\"sellSectionPrices\":[{\"deleteStatus\":0,\"endNumber\":21,\"id\":3,\"price\":24,\"sellPriceId\":668,\"startNumber\":8},{\"deleteStatus\":0,\"endNumber\":34,\"id\":4,\"price\":23,\"sellPriceId\":668,\"startNumber\":22},{\"deleteStatus\":0,\"endNumber\":2147483647,\"id\":5,\"price\":28,\"sellPriceId\":668,\"startNumber\":35}],\"status\":1,\"suggestPrice\":1000}','2017-12-21 14:57:09',NULL),(8,672,'{\"auditStatus\":1,\"branchCompanyId\":\"123456\",\"branchCompanyName\":\"测试子公司2\",\"createTime\":1514365339013,\"createUserId\":\"987654\",\"deleteStatus\":0,\"deliveryDay\":10,\"firstCreated\":1,\"id\":672,\"lowestPrice\":22,\"minNumber\":10,\"preHarvestPinStatus\":1,\"productId\":\"60000117016\",\"salesInsideNumber\":10,\"sellSectionPrices\":[{\"deleteStatus\":0,\"endNumber\":20,\"percentage\":1.2123,\"price\":24,\"sellPriceId\":672,\"startNumber\":10},{\"deleteStatus\":0,\"endNumber\":30,\"percentage\":1.2123,\"price\":23,\"sellPriceId\":672,\"startNumber\":21},{\"deleteStatus\":0,\"endNumber\":2147483647,\"percentage\":1.2123,\"price\":22,\"sellPriceId\":672,\"startNumber\":31}],\"status\":1,\"suggestPrice\":12}','2017-12-27 17:02:19','987654'),(9,666,'{\"auditStatus\":1,\"branchCompanyId\":\"10000\",\"branchCompanyName\":\"成都子公司\",\"createTime\":1513768119000,\"createUserId\":\"10027\",\"deleteStatus\":0,\"deliveryDay\":7,\"firstCreated\":0,\"handler\":{},\"id\":666,\"lowestPrice\":1.50,\"minNumber\":100,\"modifyTime\":1514442936664,\"preHarvestPinStatus\":0,\"productId\":\"60000117016\",\"salesInsideNumber\":10,\"sellSectionPrices\":[{\"deleteStatus\":0,\"endNumber\":2147483647,\"id\":1422,\"price\":2.50,\"sellPriceId\":666,\"startNumber\":100}],\"status\":1,\"suggestPrice\":42.00}','2017-12-28 14:35:37','10027'),(10,669,'{\"auditStatus\":1,\"branchCompanyId\":\"100067\",\"branchCompanyName\":\"湖南省益阳子公司\",\"createTime\":1514268974000,\"createUserId\":\"10027\",\"deleteStatus\":0,\"deliveryDay\":7,\"firstCreated\":0,\"handler\":{},\"id\":669,\"lowestPrice\":40.00,\"maxNumber\":100,\"minNumber\":10,\"modifyTime\":1514442942992,\"preHarvestPinStatus\":0,\"productId\":\"60000117016\",\"salesInsideNumber\":10,\"sellSectionPrices\":[{\"deleteStatus\":0,\"endNumber\":2147483647,\"id\":1429,\"price\":41.00,\"sellPriceId\":669,\"startNumber\":10}],\"status\":1,\"suggestPrice\":42.00}','2017-12-28 14:35:43','10027'),(23,684,'{\"auditStatus\":1,\"branchCompanyId\":\"10003\",\"branchCompanyName\":\"四川雅堂小超总部\",\"createTime\":1515033527740,\"createUserId\":\"10027\",\"deleteStatus\":0,\"deliveryDay\":5,\"firstCreated\":1,\"id\":684,\"lowestPrice\":100,\"minNumber\":1,\"preHarvestPinStatus\":0,\"priceProtection\":false,\"productId\":\"xprod347927\",\"salesInsideNumber\":1,\"sellSectionPrices\":[{\"deleteStatus\":0,\"endNumber\":2147483647,\"price\":100,\"sellPriceId\":684,\"startNumber\":1}],\"status\":1,\"suggestPrice\":6}','2018-01-04 10:38:48','10027'),(24,656,'{\"auditStatus\":1,\"branchCompanyId\":\"10000\",\"branchCompanyName\":\"成都子公司\",\"deleteStatus\":0,\"deliveryDay\":12,\"firstCreated\":0,\"id\":656,\"lowestPrice\":1001.00,\"minNumber\":1,\"modifyTime\":1515035824626,\"modifyUserId\":\"10027\",\"preHarvestPinStatus\":1,\"priceProtection\":false,\"productId\":\"60000335741\",\"salesInsideNumber\":1,\"sellSectionPrices\":[{\"deleteStatus\":0,\"endNumber\":2147483647,\"id\":1411,\"price\":1001.00,\"sellPriceId\":656,\"startNumber\":1}],\"status\":1,\"suggestPrice\":15}','2018-01-04 11:17:05',NULL);

/*Table structure for table `prod_sell_info_import` */

DROP TABLE IF EXISTS `prod_sell_info_import`;

CREATE TABLE `prod_sell_info_import` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据自增ID',
  `imports_id` bigint(20) NOT NULL COMMENT '上传ID',
  `line_number` bigint(20) DEFAULT NULL COMMENT '行号',
  `handle_result` int(2) DEFAULT NULL COMMENT '处理结果:0:错误;1:已验证;2:已提交',
  `handle_information` varchar(500) DEFAULT NULL COMMENT '处理信息',
  `product_id` varchar(255) DEFAULT NULL COMMENT '商品id',
  `product_information` varchar(500) DEFAULT NULL COMMENT '商品信息',
  `start_number` int(20) DEFAULT NULL COMMENT '区间起始数量',
  `end_number` int(20) DEFAULT NULL COMMENT '区间结束数量',
  `newest_price` decimal(20,2) DEFAULT NULL COMMENT '最新售价',
  `branch_company_id` varchar(50) DEFAULT NULL COMMENT '子公司id',
  `price_id` bigint(20) DEFAULT NULL COMMENT '售价表ID',
  `product_code` varchar(255) DEFAULT NULL COMMENT '商品编码',
  `section` varchar(255) DEFAULT NULL COMMENT '导入区间数据',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=402 DEFAULT CHARSET=utf8 COMMENT='商品售价导入记录表';

/*Data for the table `prod_sell_info_import` */

insert  into `prod_sell_info_import`(`id`,`imports_id`,`line_number`,`handle_result`,`handle_information`,`product_id`,`product_information`,`start_number`,`end_number`,`newest_price`,`branch_company_id`,`price_id`,`product_code`,`section`) values (1,10000001,1,NULL,NULL,NULL,NULL,100,200,'100.00','1',NULL,NULL,NULL),(2,10000001,2,NULL,NULL,NULL,NULL,200,300,'111.00','2',NULL,NULL,NULL);

/*Table structure for table `prod_sell_info_imports` */

DROP TABLE IF EXISTS `prod_sell_info_imports`;

CREATE TABLE `prod_sell_info_imports` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '调价单ID(上传ID)',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(50) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10000191 DEFAULT CHARSET=utf8 COMMENT='商品售价导入表(调价单)';

/*Data for the table `prod_sell_info_imports` */

insert  into `prod_sell_info_imports`(`id`,`create_time`,`create_user_id`) values (10000001,'2017-12-12 17:00:29','10000'),(10000002,'2017-12-13 14:07:28','10000'),(10000003,'2017-12-13 14:18:20','10000'),(10000004,'2017-12-13 15:33:24','10000'),(10000005,'2017-12-13 16:14:27','10027'),(10000006,'2017-12-13 16:20:58','10027'),(10000007,'2017-12-13 16:55:14','10000'),(10000008,'2017-12-13 16:56:39','10027'),(10000010,'2017-12-13 19:00:21','10027'),(10000011,'2017-12-14 13:47:51','10000'),(10000012,'2017-12-19 13:47:32','10000'),(10000013,'2017-12-19 13:48:00','10000'),(10000014,'2017-12-19 13:48:34','10000'),(10000015,'2017-12-19 14:11:21','10000'),(10000016,'2017-12-19 14:14:11','10000'),(10000017,'2017-12-19 14:20:00','10000'),(10000018,'2017-12-19 14:26:37','10000'),(10000019,'2017-12-28 10:30:02','10027'),(10000020,'2017-12-28 10:38:06','10027'),(10000021,'2017-12-28 11:35:18','10027'),(10000022,'2017-12-28 11:37:46','10027'),(10000023,'2017-12-28 12:14:09','10027'),(10000024,'2017-12-28 13:34:51','10027'),(10000025,'2017-12-28 13:41:36','10027'),(10000026,'2017-12-28 13:45:48','10027'),(10000027,'2017-12-28 14:05:21','10027'),(10000028,'2017-12-28 14:05:33','10027'),(10000029,'2017-12-28 14:11:43','10027'),(10000030,'2017-12-28 14:11:49','10027'),(10000031,'2017-12-28 14:29:08','10027'),(10000032,'2017-12-28 17:28:45','10000'),(10000033,'2017-12-28 17:30:21','10000'),(10000034,'2017-12-29 14:48:28','10000'),(10000035,'2017-12-29 16:03:23','10000'),(10000036,'2017-12-29 16:45:59','11994'),(10000037,'2017-12-29 17:03:53','10027'),(10000038,'2017-12-29 17:04:05','10027'),(10000039,'2017-12-29 17:04:05','10027'),(10000040,'2017-12-29 17:04:06','10027'),(10000041,'2017-12-29 17:04:07','10027'),(10000042,'2017-12-29 17:04:29','10027'),(10000043,'2017-12-29 17:04:49','10027'),(10000044,'2017-12-29 17:04:50','10027'),(10000045,'2017-12-29 17:04:50','10027'),(10000046,'2017-12-29 17:04:50','10027'),(10000047,'2017-12-29 17:04:51','10027'),(10000048,'2017-12-29 17:04:51','10027'),(10000049,'2017-12-29 17:05:40','10027'),(10000050,'2017-12-29 17:05:41','10027'),(10000051,'2017-12-29 17:05:41','10027'),(10000052,'2017-12-29 17:05:42','10027'),(10000053,'2017-12-29 17:05:55','10027'),(10000054,'2017-12-29 17:06:43','10027'),(10000055,'2017-12-29 17:07:04','10027'),(10000056,'2017-12-29 17:07:35','10027'),(10000057,'2017-12-29 17:08:44','123'),(10000058,'2017-12-29 17:09:04','10027'),(10000059,'2018-01-03 17:42:39','10022'),(10000060,'2018-01-05 11:36:16','10027'),(10000061,'2018-01-05 11:37:57','10027'),(10000062,'2018-01-05 13:35:44','10027'),(10000063,'2018-01-05 16:21:23','10027'),(10000064,'2018-01-05 16:22:03','10027'),(10000065,'2018-01-05 16:22:13','10027'),(10000066,'2018-01-05 16:22:39','10027'),(10000067,'2018-01-05 16:24:34','10027'),(10000068,'2018-01-05 16:42:04','10027'),(10000069,'2018-01-05 16:42:09','10027'),(10000070,'2018-01-05 16:42:24','10027'),(10000071,'2018-01-05 16:44:35','10027'),(10000072,'2018-01-05 16:44:47','10027'),(10000073,'2018-01-05 16:48:35','10027'),(10000074,'2018-01-05 17:09:36','10027'),(10000075,'2018-01-05 17:14:53','10027'),(10000076,'2018-01-05 17:15:15','10027'),(10000077,'2018-01-05 17:16:41','10027'),(10000078,'2018-01-05 17:16:51','10027'),(10000079,'2018-01-05 17:17:16','10027'),(10000080,'2018-01-05 17:20:59','10027'),(10000081,'2018-01-05 17:34:58','10027'),(10000082,'2018-01-05 17:42:10','10000'),(10000083,'2018-01-05 17:44:12','10000'),(10000084,'2018-01-05 17:45:20','10000'),(10000085,'2018-01-05 17:45:21','10000'),(10000086,'2018-01-05 17:45:22','10000'),(10000087,'2018-01-05 17:49:41','10027'),(10000088,'2018-01-05 17:51:51','10027'),(10000089,'2018-01-05 17:58:05','10000'),(10000090,'2018-01-05 18:01:16','10000'),(10000091,'2018-01-05 18:08:30','10000'),(10000092,'2018-01-05 18:14:15','10000'),(10000093,'2018-01-05 19:05:40','10027'),(10000094,'2018-01-05 19:05:49','10027'),(10000095,'2018-01-05 19:06:13','10027'),(10000096,'2018-01-05 19:10:57','10027'),(10000097,'2018-01-05 19:25:30','10027'),(10000098,'2018-01-05 19:26:20','10027'),(10000099,'2018-01-05 19:26:43','10027'),(10000100,'2018-01-05 19:27:31','10027'),(10000101,'2018-01-05 19:27:35','10027'),(10000102,'2018-01-05 19:35:48','10027'),(10000103,'2018-01-05 19:35:51','10027'),(10000104,'2018-01-05 19:35:55','10027'),(10000105,'2018-01-05 19:35:58','10027'),(10000106,'2018-01-05 19:36:01','10027'),(10000107,'2018-01-05 19:59:01','10027'),(10000108,'2018-01-05 20:01:03','10027'),(10000109,'2018-01-05 20:04:51','10027'),(10000110,'2018-01-05 20:08:24','10027'),(10000111,'2018-01-05 20:12:16','10027'),(10000112,'2018-01-05 20:12:21','10027'),(10000113,'2018-01-05 20:12:26','10027'),(10000114,'2018-01-05 20:13:37','10027'),(10000115,'2018-01-05 20:13:43','10027'),(10000116,'2018-01-05 20:13:46','10027'),(10000117,'2018-01-05 20:13:50','10027'),(10000118,'2018-01-05 20:13:59','10027'),(10000119,'2018-01-05 20:14:12','10027'),(10000120,'2018-01-05 20:18:06','10027'),(10000121,'2018-01-05 20:29:02','10027'),(10000122,'2018-01-05 20:29:05','10027'),(10000123,'2018-01-05 20:29:18','10027'),(10000124,'2018-01-05 20:34:14','10027'),(10000125,'2018-01-05 20:40:17','10027'),(10000126,'2018-01-05 20:40:22','10027'),(10000127,'2018-01-05 20:40:31','10027'),(10000128,'2018-01-05 20:41:07','10027'),(10000129,'2018-01-05 21:47:50','10022'),(10000130,'2018-01-05 21:50:20','10022'),(10000131,'2018-01-05 21:52:40','10022'),(10000132,'2018-01-05 21:54:06','10022'),(10000133,'2018-01-05 21:58:19','10027'),(10000134,'2018-01-05 22:01:31','10022'),(10000135,'2018-01-05 22:01:31','10027'),(10000136,'2018-01-08 09:08:25','10027'),(10000137,'2018-01-08 09:08:32','10027'),(10000138,'2018-01-08 09:08:36','10027'),(10000139,'2018-01-08 09:13:49','10027'),(10000140,'2018-01-08 09:13:59','10027'),(10000141,'2018-01-08 09:14:03','10027'),(10000142,'2018-01-08 09:14:07','10027'),(10000143,'2018-01-08 09:14:11','10027'),(10000144,'2018-01-08 09:14:13','10027'),(10000145,'2018-01-08 09:14:17','10027'),(10000146,'2018-01-08 09:14:20','10027'),(10000147,'2018-01-08 09:14:23','10027'),(10000148,'2018-01-08 09:14:27','10027'),(10000149,'2018-01-08 09:23:01','10027'),(10000150,'2018-01-08 09:28:14','10027'),(10000151,'2018-01-08 09:38:43','10027'),(10000152,'2018-01-08 09:48:21','10027'),(10000153,'2018-01-08 10:24:14','10027'),(10000154,'2018-01-08 10:29:47','10027'),(10000155,'2018-01-08 10:30:38','10027'),(10000156,'2018-01-08 10:32:12','10000'),(10000157,'2018-01-08 10:33:48','10000'),(10000158,'2018-01-08 10:33:56','10000'),(10000159,'2018-01-08 10:40:10','10027'),(10000160,'2018-01-08 10:40:16','10027'),(10000161,'2018-01-08 10:40:20','10027'),(10000162,'2018-01-08 10:40:24','10027'),(10000163,'2018-01-08 10:40:27','10027'),(10000164,'2018-01-08 10:40:31','10027'),(10000165,'2018-01-08 10:40:34','10027'),(10000166,'2018-01-08 10:40:37','10027'),(10000167,'2018-01-08 10:40:40','10027'),(10000168,'2018-01-08 10:41:33','10027'),(10000169,'2018-01-08 11:40:39','10027'),(10000170,'2018-01-08 11:40:48','10027'),(10000171,'2018-01-08 11:40:54','10027'),(10000172,'2018-01-08 11:55:27','10027'),(10000173,'2018-01-08 11:57:36','10027'),(10000174,'2018-01-08 15:17:03','10027'),(10000175,'2018-01-08 15:17:08','10027'),(10000176,'2018-01-08 15:17:13','10027'),(10000177,'2018-01-08 15:17:15','10027'),(10000178,'2018-01-08 15:17:20','10027'),(10000179,'2018-01-08 20:02:55','10027'),(10000180,'2018-01-08 20:03:02','10027'),(10000181,'2018-01-08 20:03:07','10027'),(10000182,'2018-01-08 20:49:02','10027'),(10000183,'2018-01-08 20:49:07','10027'),(10000184,'2018-01-08 20:49:11','10027'),(10000185,'2018-01-08 20:49:14','10027'),(10000186,'2018-01-08 20:49:17','10027'),(10000187,'2018-01-08 20:49:22','10027'),(10000188,'2018-01-08 20:49:24','10027'),(10000189,'2018-01-08 20:49:28','10027'),(10000190,'2018-01-08 20:49:31','10027');

/*Table structure for table `prod_sell_info_log` */

DROP TABLE IF EXISTS `prod_sell_info_log`;

CREATE TABLE `prod_sell_info_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `branch_company_id` varchar(255) DEFAULT NULL COMMENT '子公司id',
  `product_Id` varchar(255) DEFAULT NULL COMMENT '商品id',
  `lowest_price` decimal(20,2) DEFAULT NULL COMMENT '最低价',
  `operate` varchar(255) DEFAULT NULL COMMENT '操作',
  `operate_date` datetime DEFAULT NULL COMMENT '操作时间',
  `operate_user_id` varchar(255) DEFAULT NULL COMMENT '操作用户id',
  `operate_user_name` varchar(255) DEFAULT NULL COMMENT '操作用户名',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=772 DEFAULT CHARSET=utf8;


/*Table structure for table `prod_sell_section_price` */

DROP TABLE IF EXISTS `prod_sell_section_price`;

CREATE TABLE `prod_sell_section_price` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id主键',
  `price` decimal(20,2) DEFAULT NULL COMMENT '商品价格',
  `start_number` int(20) DEFAULT NULL COMMENT '区间起始数量',
  `end_number` int(20) DEFAULT NULL COMMENT '区间结束数量',
  `sell_price_id` bigint(20) DEFAULT NULL COMMENT '销售价格信息id',
  `delete_status` int(1) DEFAULT '0' COMMENT '0为未删除状态1为删除状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1768 DEFAULT CHARSET=utf8 COMMENT='商品价格区间表';

/*Data for the table `prod_sell_section_price` */

insert  into `prod_sell_section_price`(`id`,`price`,`start_number`,`end_number`,`sell_price_id`,`delete_status`) values (1411,'100.00',1,2147483647,656,0),(1416,'17.50',1,2147483647,661,0),(1417,'17.50',1,2147483647,662,0),(1419,'12.00',1,2147483647,664,0),(1421,'100.00',3,2147483647,659,1),(1422,'1.50',100,2147483647,666,0),(1426,'24.00',10,20,668,0),(1427,'23.00',21,30,668,0),(1428,'22.00',31,2147483647,668,0),(1429,'40.00',10,2147483647,669,0),(1432,'500.00',1,2147483647,657,0),(1433,'24.00',10,20,670,0),(1434,'23.00',21,30,670,0),(1435,'22.00',31,2147483647,670,0),(1436,'24.00',10,20,671,0),(1437,'23.00',21,30,671,0),(1438,'22.00',31,2147483647,671,0),(1439,'24.00',10,20,672,0),(1440,'23.00',21,30,672,0),(1441,'22.00',31,2147483647,672,0),(1443,'2.00',1,2147483647,674,1),(1444,'52.00',45,2147483647,673,1),(1447,'100.00',1,2147483647,675,0),(1452,'100.00',2,3,679,0),(1453,'100.00',4,2147483647,679,0),(1460,'100.00',1,2147483647,684,0),(1462,'100.00',1,2,681,0),(1463,'100.00',3,2147483647,681,0),(1464,'100.00',2,3,685,0),(1465,'100.00',4,2147483647,685,0),(1467,'100.00',3,2147483647,683,0),(1469,'1010.00',1,2147483647,687,0),(1472,'100.00',1,2000,688,0),(1473,'98.00',2001,2147483647,688,0),(1474,'100.00',1,2000,689,0),(1475,'98.00',2001,4000,689,0),(1476,'95.00',4001,2147483647,689,0),(1477,'100.00',4,5,680,0),(1478,'100.00',6,2147483647,680,0),(1479,'100.00',10,11,690,0),(1480,'95.00',12,2147483647,690,0),(1484,'100.00',3,4,678,0),(1485,'100.00',5,2147483647,678,0),(1490,'100.00',3,4,677,0),(1491,'100.00',5,2147483647,677,0),(1501,'10.00',1,2,695,0),(1502,'110.00',3,40,695,0),(1503,'1110.00',41,2147483647,695,0),(1508,'10.00',1,2,696,0),(1509,'100.00',3,40,696,0),(1510,'1100.00',41,600,696,0),(1511,'1230.00',601,2147483647,696,0),(1515,'10.00',1,30,697,0),(1516,'110.00',31,320,697,0),(1517,'1100.00',321,2147483647,697,0),(1518,'100.00',1,22,698,0),(1519,'100.00',23,2147483647,698,0),(1529,'120.00',1,200,699,0),(1530,'110.00',201,4000,699,0),(1531,'105.00',4001,2147483647,699,0),(1534,'10.00',1,2,701,0),(1535,'100.00',3,2147483647,701,0),(1536,'6.00',2,2147483647,702,0),(1537,'9.00',1,2147483647,703,0),(1538,'100.00',1,2000,704,0),(1539,'90.00',2001,2147483647,704,0),(1544,'55.00',1,2147483647,706,0),(1551,'80.00',1,2147483647,708,0),(1552,'80.00',2,200,700,0),(1553,'95.00',201,400,700,0),(1554,'80.00',401,2147483647,700,0),(1555,'50.00',3,400,709,0),(1556,'45.00',401,2147483647,709,0),(1557,'50.00',1,2000,705,0),(1558,'45.00',2001,4000,705,0),(1559,'40.00',4001,2147483647,705,0),(1562,'123123.00',1,2147483647,710,0),(1573,'10.00',1,12,707,0),(1574,'1000.00',13,2147483647,707,0),(1583,'111.00',1,2147483647,711,0),(1591,'100.00',1,4,712,0),(1592,'100.00',5,2147483647,712,0),(1596,'15.51',1,2147483647,660,1),(1598,'0.06',4,2147483647,713,0),(1599,'100.00',4,21647,693,0),(1600,'100.00',21648,21649,693,0),(1601,'100.00',21650,2147483647,693,0),(1605,'25.23',10,100,714,0),(1606,'25.14',101,2147483647,714,0),(1607,'20.00',3,2147483647,663,0),(1608,'5.00',6,7,691,0),(1609,'4.00',8,9,691,0),(1610,'3.00',10,2147483647,691,0),(1614,'10.00',3,40,715,0),(1615,'100.00',41,100,715,0),(1616,'1000.00',101,2147483647,715,0),(1623,'100.30',5,2147483647,717,0),(1626,'120.00',5,6,718,0),(1627,'120.00',7,2147483647,718,0),(1629,'300.00',4,2147483647,719,0),(1630,'20.15',5,2147483647,720,0),(1632,'1.00',2,2147483647,721,0),(1635,'9.80',3,2147483647,722,0),(1636,'10.50',3,5,716,0),(1637,'9.80',6,2147483647,716,0),(1641,'25.23',7,2147483647,723,0),(1642,'20.21',4,5,724,0),(1643,'20.17',6,2147483647,724,0),(1645,'15.22',5,2147483647,725,0),(1649,'111.00',1,20,726,0),(1650,'1110.00',21,220,726,0),(1651,'11100.00',221,2147483647,726,0),(1664,'50.00',1,2147483647,727,0),(1668,'2.00',1,10,728,0),(1669,'10.00',11,2147483647,728,0),(1673,'5.00',1,2147483647,729,0),(1674,'100.00',4,2147483647,730,0),(1678,'0.06',1,2,731,0),(1679,'0.06',3,2147483647,731,0),(1680,'10.00',10,20,694,0),(1681,'50.00',21,40,694,0),(1682,'300.00',41,2147483647,694,0),(1683,'30.00',1,100,732,0),(1684,'28.00',101,2147483647,732,0),(1685,'100.00',1,2147483647,733,0),(1686,'2.00',1,2147483647,734,0),(1689,'50.00',3,4000,735,0),(1690,'45.00',4001,2147483647,735,0),(1691,'12.00',1,20,736,0),(1692,'120.00',21,220,736,0),(1693,'122.00',221,2147483647,736,0),(1698,'10.00',1,100,737,0),(1699,'9.00',101,2147483647,737,0),(1700,'18.00',1,100,738,0),(1701,'16.00',101,2147483647,738,0),(1706,'80.00',1,2000,740,0),(1707,'70.00',2001,2147483647,740,0),(1708,'100.00',1,2000,739,0),(1709,'90.00',2001,2147483647,739,0),(1711,'0.00',3,4,741,0),(1712,'80.00',5,2147483647,741,0),(1714,'123.00',14,2147483647,742,0),(1715,'200.00',10,2147483647,686,0),(1720,'100.00',1,2000,743,0),(1721,'90.00',2001,2147483647,743,0),(1722,'100.00',1,200,744,0),(1723,'80.00',201,2147483647,744,0),(1725,'50.00',2,2147483647,745,0),(1736,'100.00',1,2147483647,747,0),(1737,'20.00',2,30,746,0),(1738,'32.00',31,320,746,0),(1739,'35.00',321,2147,746,0),(1740,'35.00',2148,2147483647,746,0),(1744,'44.00',2,2147483647,748,0),(1745,'10.00',1,2147483647,749,0),(1746,'10.00',1,2147483647,750,0),(1747,'12.00',1,2147483647,751,0),(1748,'100.00',1,2147483647,752,0),(1751,'23.00',2,2147483647,753,0),(1753,'100.00',1,2147483647,754,0),(1754,'120.00',1,2147483647,658,0),(1756,'60.00',1,2147483647,755,0),(1761,'200.00',2,2147483647,682,0),(1762,'100.00',2,2147483647,692,0),(1764,'100.00',1,2147483647,676,0),(1765,'6.40',1,2147483647,756,0),(1767,'100.00',1,2147483647,757,0);

/*Table structure for table `product_internationcode` */

DROP TABLE IF EXISTS `product_internationcode`;

CREATE TABLE `product_internationcode` (
  `id` varchar(255) DEFAULT NULL,
  `product_id` varchar(255) DEFAULT NULL,
  `international_code` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='商品条码关系表';

/*Data for the table `product_internationcode` */

insert  into `product_internationcode`(`id`,`product_id`,`international_code`) values ('xincode265845','52F2C95D555506F2E050007F01005B12','69021824'),('59311','59311','69025143'),('59814','59814','69028571');

/*Table structure for table `product_internationcode_copy` */

DROP TABLE IF EXISTS `product_internationcode_copy`;

CREATE TABLE `product_internationcode_copy` (
  `international_code` varchar(255) DEFAULT NULL,
  `product_id` varchar(255) DEFAULT NULL,
  `product_code` varchar(255) DEFAULT NULL,
  `first_category_id` varchar(255) DEFAULT NULL,
  `second_category_id` varchar(255) DEFAULT NULL,
  `third_category_id` varchar(255) DEFAULT NULL,
  `fourth_category_id` varchar(255) DEFAULT NULL,
  `input_tax_rate` int(11) DEFAULT NULL,
  `tax_rate` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='商品条码关系表';

/*Data for the table `product_internationcode_copy` */

insert  into `product_internationcode_copy`(`international_code`,`product_id`,`product_code`,`first_category_id`,`second_category_id`,`third_category_id`,`fourth_category_id`,`input_tax_rate`,`tax_rate`) values ('69021824','52F2C95D555506F2E050007F01005B12','1000954','xcate1','xcate8','xcate41','xcate178',17,17),('69025143','59311','1001258','xcate2','xcate12','xcate57','xcate255',17,17),('69028571','59814','1001248','xcate2','xcate12','xcate57','xcate255',17,17);

/*Table structure for table `qrtz_blob_triggers` */

DROP TABLE IF EXISTS `qrtz_blob_triggers`;

CREATE TABLE `qrtz_blob_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_blob_triggers` */

/*Table structure for table `qrtz_calendars` */

DROP TABLE IF EXISTS `qrtz_calendars`;

CREATE TABLE `qrtz_calendars` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_calendars` */

/*Table structure for table `qrtz_cron_triggers` */

DROP TABLE IF EXISTS `qrtz_cron_triggers`;

CREATE TABLE `qrtz_cron_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_cron_triggers` */

insert  into `qrtz_cron_triggers`(`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`CRON_EXPRESSION`,`TIME_ZONE_ID`) values ('serviceScheduler','timeoutClosePurchaseTrigger','DEFAULT','0 0 0 * * ?','Asia/Shanghai');

/*Table structure for table `qrtz_fired_triggers` */

DROP TABLE IF EXISTS `qrtz_fired_triggers`;

CREATE TABLE `qrtz_fired_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_fired_triggers` */

/*Table structure for table `qrtz_job_details` */

DROP TABLE IF EXISTS `qrtz_job_details`;

CREATE TABLE `qrtz_job_details` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_job_details` */

insert  into `qrtz_job_details`(`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`,`DESCRIPTION`,`JOB_CLASS_NAME`,`IS_DURABLE`,`IS_NONCONCURRENT`,`IS_UPDATE_DATA`,`REQUESTS_RECOVERY`,`JOB_DATA`) values ('serviceScheduler','timeoutClosePurchaseQuartsJobBean','DEFAULT',NULL,'com.yatang.sc.facade.scheduler.TimeoutClosePurchaseScheduler','1','1','1','1','��\0sr\0org.quartz.JobDataMap���迩��\0\0xr\0&org.quartz.utils.StringKeyDirtyFlagMap�����](\0Z\0allowsTransientDataxr\0org.quartz.utils.DirtyFlagMap�.�(v\n�\0Z\0dirtyL\0mapt\0Ljava/util/Map;xpsr\0java.util.HashMap���`�\0F\0\nloadFactorI\0	thresholdxp?@\0\0\0\0\0w\0\0\0\0\0\0t\0timeoutt\05x\0');

/*Table structure for table `qrtz_locks` */

DROP TABLE IF EXISTS `qrtz_locks`;

CREATE TABLE `qrtz_locks` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_locks` */

insert  into `qrtz_locks`(`SCHED_NAME`,`LOCK_NAME`) values ('serviceScheduler','STATE_ACCESS'),('serviceScheduler','TRIGGER_ACCESS');

/*Table structure for table `qrtz_paused_trigger_grps` */

DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;

CREATE TABLE `qrtz_paused_trigger_grps` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_paused_trigger_grps` */

/*Table structure for table `qrtz_scheduler_state` */

DROP TABLE IF EXISTS `qrtz_scheduler_state`;

CREATE TABLE `qrtz_scheduler_state` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_scheduler_state` */

insert  into `qrtz_scheduler_state`(`SCHED_NAME`,`INSTANCE_NAME`,`LAST_CHECKIN_TIME`,`CHECKIN_INTERVAL`) values ('serviceScheduler','sc-facade-dubbo-182759824-z6ksk1517821893527',1520995957502,20000);

/*Table structure for table `qrtz_simple_triggers` */

DROP TABLE IF EXISTS `qrtz_simple_triggers`;

CREATE TABLE `qrtz_simple_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_simple_triggers` */

/*Table structure for table `qrtz_simprop_triggers` */

DROP TABLE IF EXISTS `qrtz_simprop_triggers`;

CREATE TABLE `qrtz_simprop_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_simprop_triggers` */

/*Table structure for table `qrtz_triggers` */

DROP TABLE IF EXISTS `qrtz_triggers`;

CREATE TABLE `qrtz_triggers` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `qrtz_triggers` */

insert  into `qrtz_triggers`(`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`JOB_NAME`,`JOB_GROUP`,`DESCRIPTION`,`NEXT_FIRE_TIME`,`PREV_FIRE_TIME`,`PRIORITY`,`TRIGGER_STATE`,`TRIGGER_TYPE`,`START_TIME`,`END_TIME`,`CALENDAR_NAME`,`MISFIRE_INSTR`,`JOB_DATA`) values ('serviceScheduler','timeoutClosePurchaseTrigger','DEFAULT','timeoutClosePurchaseQuartsJobBean','DEFAULT',NULL,1521043200000,1520956800000,0,'WAITING','CRON',1510715231000,0,NULL,2,'');

/*Table structure for table `recommend_keywords` */

DROP TABLE IF EXISTS `recommend_keywords`;

CREATE TABLE `recommend_keywords` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `content` varchar(255) DEFAULT NULL COMMENT '参数内容',
  `input_key` int(2) DEFAULT NULL COMMENT '是否搜索框的内容1-是 2-不是',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COMMENT='搜索推荐页表';

/*Data for the table `recommend_keywords` */

insert  into `recommend_keywords`(`id`,`sort`,`content`,`input_key`) values (25,NULL,'',1),(26,1,'雪花啤酒',2),(27,0,'友臣肉松饼',2),(28,2,'红双喜',2),(29,3,'33',2),(30,4,'李锦记',2),(31,5,'HOT',2),(32,8,'21',2),(33,23,'操蛋',2);

/*Table structure for table `scp_inventory_log` */

DROP TABLE IF EXISTS `scp_inventory_log`;

CREATE TABLE `scp_inventory_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `order_id` varchar(40) DEFAULT NULL,
  `commerce_id` varchar(10) DEFAULT NULL,
  `product_id` varchar(40) DEFAULT NULL,
  `loc` varchar(40) DEFAULT NULL,
  `quantity` bigint(20) DEFAULT NULL,
  `type` varchar(10) DEFAULT NULL,
  `comment` varchar(1000) DEFAULT NULL,
  `date_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1516772 DEFAULT CHARSET=utf8;


/*Table structure for table `scp_order_refund` */

DROP TABLE IF EXISTS `scp_order_refund`;

CREATE TABLE `scp_order_refund` (
  `id` int(19) NOT NULL AUTO_INCREMENT COMMENT '主键id自动增长',
  `return_order_id` varchar(50) DEFAULT NULL COMMENT '退货单id',
  `refund_id` varchar(20) DEFAULT NULL COMMENT '退款单id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='退货单与退款单关系表';

/*Data for the table `scp_order_refund` */

/*Table structure for table `scp_promo_categories` */

DROP TABLE IF EXISTS `scp_promo_categories`;

CREATE TABLE `scp_promo_categories` (
  `promo_id` varchar(40) NOT NULL COMMENT '促销id',
  `category_id` varchar(40) NOT NULL COMMENT '分类ID',
  `category_name` varchar(50) NOT NULL COMMENT '类名',
  `category_level` int(11) DEFAULT NULL COMMENT '分类级别',
  PRIMARY KEY (`promo_id`,`category_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销品类';

/*Data for the table `scp_promo_categories` */

/*Table structure for table `scp_promo_companies` */

DROP TABLE IF EXISTS `scp_promo_companies`;

CREATE TABLE `scp_promo_companies` (
  `promo_id` varchar(40) NOT NULL COMMENT '促销id',
  `company_id` varchar(40) NOT NULL COMMENT '分公司ID',
  `company_name` varchar(40) NOT NULL COMMENT '分公司名称',
  PRIMARY KEY (`promo_id`,`company_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销区域';

/*Data for the table `scp_promo_companies` */

/*Table structure for table `scp_promo_records` */

DROP TABLE IF EXISTS `scp_promo_records`;

CREATE TABLE `scp_promo_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `promo_id` varchar(40) NOT NULL COMMENT '促销id',
  `order_id` varchar(40) NOT NULL COMMENT '订单ID',
  `discount` decimal(10,2) DEFAULT NULL COMMENT '折扣金额',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='（促销活动）订单记录';

/*Data for the table `scp_promo_records` */

insert  into `scp_promo_records`(`id`,`promo_id`,`order_id`,`discount`) values (8,'111','111','11111.00'),(9,'22','22','22.00');

/*Table structure for table `scp_promo_stores` */

DROP TABLE IF EXISTS `scp_promo_stores`;

CREATE TABLE `scp_promo_stores` (
  `promo_id` varchar(40) NOT NULL COMMENT '促销id',
  `store_id` varchar(3000) NOT NULL COMMENT '门店ID',
  PRIMARY KEY (`promo_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销指定门店';

/*Data for the table `scp_promo_stores` */

/*Table structure for table `scp_promotion` */

DROP TABLE IF EXISTS `scp_promotion`;

CREATE TABLE `scp_promotion` (
  `id` varchar(40) NOT NULL COMMENT '主键id',
  `status` varchar(10) NOT NULL COMMENT '状态',
  `promotion_type` varchar(20) NOT NULL COMMENT '促销类型（order_promo,item_promo,shipping_promo）',
  `promotion_name` varchar(200) DEFAULT NULL COMMENT '活动名称',
  `promotion_discription` varchar(500) DEFAULT NULL COMMENT '折扣描述',
  `start_date` datetime DEFAULT NULL COMMENT '开始时间',
  `end_date` datetime DEFAULT NULL COMMENT '结束时间',
  `discount_type` varchar(20) NOT NULL COMMENT '折扣类型(stander,percentage)',
  `quanify_amount` decimal(10,2) DEFAULT NULL COMMENT '条件金额',
  `discount` decimal(10,2) DEFAULT NULL COMMENT '折扣',
  `note` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='促销活动';

/*Data for the table `scp_promotion` */

/*Table structure for table `scp_region_group` */

DROP TABLE IF EXISTS `scp_region_group`;

CREATE TABLE `scp_region_group` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '数据自增ID',
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*Data for the table `scp_region_group` */

/*Table structure for table `sctest.scp_purchase_coupon_records` */

DROP TABLE IF EXISTS `sctest.scp_purchase_coupon_records`;

CREATE TABLE `sctest.scp_purchase_coupon_records` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `commerce_item_id` varchar(40) DEFAULT NULL COMMENT 'commerce item id',
  `order_id` varchar(40) DEFAULT NULL COMMENT 'order id',
  `quantity` bigint(10) DEFAULT NULL COMMENT 'quantity',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='优惠券购买记录';

/*Data for the table `sctest.scp_purchase_coupon_records` */

/*Table structure for table `service_commitments` */

DROP TABLE IF EXISTS `service_commitments`;

CREATE TABLE `service_commitments` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'pk',
  `promise_content` varchar(100) DEFAULT NULL COMMENT '承诺内容',
  `sort` int(11) DEFAULT NULL COMMENT '排序',
  `status` int(11) DEFAULT NULL COMMENT '启禁用状态:0禁用,1启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='服务承诺';

/*Data for the table `service_commitments` */

insert  into `service_commitments`(`id`,`promise_content`,`sort`,`status`) values (5,'一年质保',1,0),(6,'假一赔五',2,1),(7,'平台配送',3,0),(8,'服务保障',4,1);

/*Table structure for table `sp_adr_basic_info` */

DROP TABLE IF EXISTS `sp_adr_basic_info`;

CREATE TABLE `sp_adr_basic_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `provider_no` varchar(255) DEFAULT NULL COMMENT '供应商地点编码',
  `provider_name` varchar(255) DEFAULT NULL COMMENT '供应商地点名称',
  `goods_arrival_cycle` int(11) DEFAULT NULL COMMENT '到货周期',
  `org_id` varchar(50) DEFAULT NULL COMMENT '分公司ID',
  `grade` int(11) DEFAULT NULL COMMENT '供应地点级别:1:生产厂家;2:批发商;3:经销商;4:代销商;5:其他',
  `opera_status` int(2) DEFAULT '0' COMMENT '供应商地点经营状态0:禁用:1:启用',
  `settlement_period` int(11) DEFAULT NULL COMMENT '账期: 0:周结；1：半月结；2：月结；',
  `pay_condition` int(2) DEFAULT NULL COMMENT '付款条件(1:票到七天,2:票到十五天,3:票到三十天,4：票到付款)',
  `belong_area` int(11) DEFAULT NULL COMMENT '所属区域',
  `belong_area_name` varchar(50) DEFAULT NULL COMMENT '所属区域名字',
  `pay_type` int(11) DEFAULT NULL COMMENT '供应商付款方式：0：网银，1：银行转账，2：现金，3：支票',
  `audit_person` varchar(30) DEFAULT NULL COMMENT '审核人姓名',
  `audit_date` datetime DEFAULT NULL COMMENT '审核时间',
  `modify_id` int(11) DEFAULT NULL COMMENT '修改信息id,如有修改操作关联到修改记录',
  `status` int(2) DEFAULT '0' COMMENT '0：原记录, 1：修改记录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=556 DEFAULT CHARSET=utf8;

/*Data for the table `sp_adr_basic_info` */

insert  into `sp_adr_basic_info`(`id`,`provider_no`,`provider_name`,`goods_arrival_cycle`,`org_id`,`grade`,`opera_status`,`settlement_period`,`pay_condition`,`belong_area`,`belong_area_name`,`pay_type`,`audit_person`,`audit_date`,`modify_id`,`status`) values (1,'1000001','四川-康定民族贸易有限责任公司',7,NULL,3,1,0,1,510000,'四川',1,NULL,NULL,114,0),(2,'1000002','四川-成都蒙利天和商贸有限公司',7,NULL,3,1,0,1,510000,'四川',1,NULL,NULL,NULL,0),(4,'1000004','四川-成都择好商贸有限公司',7,'10000',3,1,1,0,510000,'四川',1,NULL,NULL,NULL,0),(5,'1000005','四川-四川康昊食品贸易有限公司',7,NULL,3,1,0,1,510000,'四川',1,NULL,NULL,NULL,0),(6,'1000006','四川-成都景中商贸有限公司',7,NULL,3,1,2,0,510000,'四川',1,NULL,NULL,NULL,0),(7,'1000007','全国-日加满饮品（上海）有限公司',15,NULL,1,1,2,0,510000,'全国',1,NULL,NULL,NULL,0),(10,'1000010','四川-成都乐镒商贸有限公司',7,NULL,4,1,0,1,510000,'四川',1,NULL,NULL,NULL,0),(12,'1000012','四川-成都市凌宇食品有限公司',7,NULL,3,1,0,1,510000,'四川',1,NULL,NULL,NULL,0),(13,'1000013','四川-成都张飞调味品有限公司',7,'10000',1,1,0,1,510000,'四川',1,NULL,NULL,NULL,0),(14,'1000014','四川-成都辰龙商贸有限公司',7,NULL,3,1,0,1,510000,'四川',1,NULL,NULL,NULL,0),(15,'1000015','四川-四川省天渠盐化有限公司广东分公司',7,NULL,1,1,2,1,510000,'四川',1,NULL,NULL,NULL,0),(17,'1000017','四川-四川瑛祥商贸有限公司',7,NULL,4,1,0,1,510000,'四川',1,NULL,NULL,NULL,0),(18,'1000018','四川-成都清润贸易发展有限公司',4,NULL,4,1,1,0,510000,'四川',1,NULL,NULL,139,0),(20,'1000020','四川-桂林智强食品开发有限公司',4,'10000',1,1,2,0,510000,'四川',1,NULL,NULL,21,0),(21,'1000021','四川-成都市富亿通贸易有限公司',4,'10003',1,1,1,0,510000,'四川',1,NULL,NULL,NULL,1),(24,'1000024','四川-成都天旺商贸有限公司',4,'10000',3,1,1,0,510000,'四川',1,NULL,NULL,NULL,0),(25,'1000025','四川-成都市永联兴贸易有限公司',4,NULL,4,1,1,0,510000,'四川',1,NULL,NULL,NULL,0),(27,'1000027','四川-成都凯斯原商贸有限公司',4,NULL,4,1,0,1,510000,'四川',1,NULL,NULL,NULL,0),(28,'1000028','四川-成都铭昊商贸有限公司',4,NULL,2,1,0,1,510000,'四川',1,NULL,NULL,NULL,0),(29,'1000029','四川-成都你好植物科技有限公司',5,NULL,1,1,2,0,510000,'四川',1,NULL,NULL,122,0),(30,'1000030','四川-成都兆廷商贸有限公司',4,'10000',2,1,2,0,510000,'四川',1,NULL,NULL,31,0),(31,'1000031','四川-成都蜀特商贸有限公司',4,'10003',4,1,1,0,510000,'四川',1,NULL,NULL,NULL,1),(32,'1000032','四川-成都宏旺达贸易有限公司',4,NULL,4,1,0,1,510000,'四川',1,NULL,NULL,NULL,0),(33,'1000033','四川-成都友程贸易有限公司',7,NULL,3,1,2,0,510000,'四川',1,NULL,NULL,NULL,0),(34,'1000034','四川-成都集佰汇科技有限公司',7,NULL,3,1,1,0,510000,'四川',1,NULL,NULL,132,0),(36,'1000036','四川-成都瑞琪达商贸有限公司',5,NULL,3,1,0,1,510000,'四川',1,NULL,NULL,NULL,0),(37,'1000037','四川-四川沃美进出口贸易有限公司',7,'10000',3,1,0,2,510000,'四川',1,NULL,NULL,167,0),(38,'1000038','四川-成都小超邦科技有限公司',5,NULL,3,1,0,2,510000,'四川',1,NULL,NULL,NULL,0),(39,'1000039','四川-成都金福洋贸易有限公司',7,NULL,3,1,0,1,510000,'四川',1,NULL,NULL,NULL,0),(40,'1000040','四川-成都百分百佳商贸有限公司 ',6,'10000',3,1,2,0,510000,'四川',1,NULL,NULL,NULL,0),(41,'1000041','四川-成都友程贸易有限公司',6,'10003',3,1,2,0,510000,'四川',1,NULL,NULL,NULL,0),(42,'1000042','四川-成都市鑫鑫越洋商贸有限公司',6,NULL,3,1,2,0,510000,'四川',1,NULL,NULL,NULL,0),(71,'55','辽宁 - sl供应商1',5,'10000',1,1,0,1,210000,'辽宁',0,'管理员','2017-08-16 13:52:41',NULL,0),(72,'53','青海 - 阿达',4,'10000',1,1,0,1,630000,'青海',0,'管理员','2017-08-16 13:43:48',NULL,0),(79,'58','四川 - 供应商6',5,'10000',1,1,0,1,510000,'四川',0,'管理员','2017-08-16 14:53:28',NULL,0),(84,'100091','上海 - sl供应商31',4,'10000',1,1,0,1,310000,'上海',0,NULL,NULL,NULL,0),(85,'100092','广东 - sl供应商1',12,'10003',1,1,0,1,440000,'广东',0,NULL,NULL,NULL,0),(88,'100095','山西 - 阿达',5,'10003',1,1,0,1,140000,'山西',0,NULL,NULL,NULL,0),(89,'100094','江苏 - 供应商7',6,'10000',1,1,0,1,320000,'江苏',0,NULL,NULL,NULL,0),(94,'100096','江苏 - 供应商7',7,'10003',1,1,0,2,320000,'江苏',0,'管理员','2017-08-16 16:00:34',NULL,0),(95,'100098','江苏 - 供应商7',7,'10004',1,1,0,1,320000,'江苏',0,NULL,NULL,NULL,0),(96,'100097','四川 - 供应商8',5,'10000',1,1,0,1,510000,'四川',0,'姚木明','2017-08-16 16:09:14',NULL,0),(99,'100101','四川 - 供应商9',5,'10000',1,1,0,1,510000,'四川',0,NULL,NULL,NULL,0),(100,'100102','重庆 - 供应商10',5,'10003',1,1,0,1,500000,'重庆',0,NULL,NULL,110,0),(101,'100103','四川 - 供应商10',5,'10000',1,1,0,1,510000,'四川',0,NULL,NULL,NULL,0),(110,'100102','重庆 - 供应商10',5,'10003',1,1,0,1,500000,'重庆',0,NULL,NULL,NULL,1),(111,'1000016','重庆 - 宜宾丰源盐业有限公司',6,'10003',2,0,0,4,500000,'重庆',0,'姚木明','2017-09-04 16:29:19',NULL,0),(114,'1000001','四川-康定民族贸易有限责任公司',7,'10003',3,1,0,1,510000,'四川',1,NULL,NULL,NULL,1),(117,'100109','安徽 - 撒旦',2,'10003',1,1,0,1,340000,'安徽',0,NULL,NULL,119,0),(119,'100109','安徽 - 撒旦',2,'10003',1,1,0,1,340000,'安徽',0,NULL,NULL,NULL,1),(120,'100110','四川 - 20170817 测试',10,'10000',1,1,0,1,510000,'四川',0,NULL,NULL,NULL,0),(122,'1000029','四川-成都你好植物科技有限公司',5,'10004',1,1,2,0,510000,'四川',1,NULL,NULL,NULL,1),(124,'100112','江苏 - 测试供应商1123',7,'10000',1,1,0,1,320000,'江苏',0,'管理员','2017-08-17 15:00:19',125,0),(125,'100112','江苏 - 测试供应商1123',7,'10000',1,1,0,1,320000,'江苏',0,NULL,NULL,NULL,1),(126,'59','四川 - admin',4,'10000',1,1,0,1,510000,'四川',0,'姚木明','2017-08-18 17:25:29',128,0),(127,'100108','广东 - 测试供应商11',7,'10004',1,1,0,2,440000,'广东',0,'管理员','2017-08-29 14:08:56',NULL,0),(128,'59','四川 - admin',4,'10003',1,1,0,1,510000,'四川',0,NULL,NULL,NULL,1),(131,'100119','广西 - CCCCCC',6,'10000',1,1,0,1,450000,'广西',0,'管理员','2017-08-21 10:27:52',NULL,0),(132,'1000034','四川-成都集佰汇科技有限公司',7,'10003',3,1,1,0,510000,'四川',1,NULL,NULL,NULL,1),(135,'54','四川 - 供应商5',5,'10004',1,1,0,1,510000,'四川',0,'管理员','2017-08-28 15:02:58',169,0),(138,'100121','天津 - 红太阳',10,'10000',3,1,0,1,120000,'天津',1,'管理员','2017-08-22 15:35:35',NULL,0),(139,'1000018','四川-成都清润贸易发展有限公司',4,'10000',4,1,1,0,510000,'四川',1,NULL,NULL,NULL,1),(142,'1000022','四川-四川鑫恒创贸易有限公司',4,'10000',4,1,1,0,510000,'四川',1,'姚木明','2017-08-22 17:57:42',NULL,0),(144,'100126','河北 - FFFFF',10,'10000',2,1,0,2,130000,'河北',1,NULL,NULL,NULL,0),(146,'57','四川 - 供应商5',2,'10003',1,1,1,2,510000,'四川',0,'姚木明','2017-09-04 16:32:59',NULL,0),(147,'100128','四川 - 供应商1',5,'10000',1,1,0,1,510000,'四川',0,NULL,NULL,150,0),(148,'100129','四川 - 供应商1',6,'10003',1,1,0,1,510000,'四川',1,NULL,NULL,NULL,0),(149,'100130','四川 - 供应商1',6,'10004',1,1,0,1,510000,'四川',2,NULL,NULL,NULL,0),(150,'100128','四川 - 供应商1',4,'10000',2,0,1,2,510000,'四川',1,NULL,NULL,NULL,1),(153,'56','福建 - YTtesting',12,'10004',1,1,1,2,350000,'福建',0,'姚木明','2017-08-29 14:26:33',NULL,0),(158,'100134','天津 - 哈哈哈哈哈哈',5,'10000',2,1,1,1,120000,'天津',0,'管理员','2017-12-25 17:20:17',NULL,0),(159,'100138','江苏 - 四川福仁缘农业开发有限公司',4,'10003',1,1,0,1,320000,'江苏',0,NULL,NULL,NULL,0),(160,'100142','四川 - 新酒',3,'10000',1,1,0,1,510000,'四川',0,NULL,NULL,NULL,0),(161,'100100','天津 - DDDDD',2,'10000',1,1,0,2,120000,'天津',1,'姚木明','2017-09-05 10:47:55',NULL,0),(165,'1000011','吉林 - 双流桂华鑫商贸有限公司',5,'10000',2,0,1,4,220000,'吉林',0,'姚木明','2017-09-07 09:48:17',NULL,0),(166,'1000023','四川-成都长发商贸有限责任公司',4,'10000',4,1,1,4,510000,'四川',1,'姚木明','2017-09-05 09:46:33',NULL,0),(167,'1000037','重庆 - 四川沃美进出口贸易有限公司',6,'10003',1,0,1,4,500000,'重庆',0,NULL,NULL,NULL,1),(168,'100106','天津 - DDDDD',5,'10003',1,1,1,4,120000,'天津',1,'姚木明','2017-09-05 09:46:46',177,0),(169,'54','四川 - 供应商5',5,'10000',2,1,2,4,510000,'四川',2,NULL,NULL,NULL,1),(170,'1000019','四川-成都宸晨丰汇商贸有限公司',4,'10000',4,1,1,4,510000,'四川',1,'姚木明','2017-09-04 16:34:42',NULL,0),(171,'1000026','重庆 - 成都富佑嘉商贸有限公司',5,'10003',1,0,0,4,500000,'重庆',1,'姚木明','2017-09-05 09:17:02',NULL,0),(173,'1000035','重庆 - 成都彼岸商贸有限公司new',6,'10003',2,0,0,4,500000,'重庆',0,'姚木明','2017-09-05 09:35:56',NULL,0),(175,'100177','四川 - xxh',3,'10000',1,1,2,4,510000,'四川',0,NULL,NULL,NULL,0),(176,'100179','四川 - 剑圣',3,'10000',2,1,0,1,510000,'四川',0,NULL,NULL,NULL,0),(177,'100106','天津 - DDDDD',5,'10003',1,1,1,4,120000,'天津',1,NULL,NULL,NULL,1),(193,'100184','福建 - 测试的供应商 100321231',5,'10000',1,1,0,1,350000,'福建',0,NULL,NULL,NULL,0),(200,'100125','山西 - BBBBBBBBB',6,'10004',1,1,1,1,140000,'山西',0,'管理员','2017-09-18 15:52:16',NULL,0),(201,'100249','北京 - haha ',1,'10000',1,1,0,1,110000,'北京',0,NULL,NULL,NULL,0),(202,'1000008','全国-四川福仁缘农业开发有限公司',15,'10000',1,1,2,1,510000,'全国',1,'管理员','2017-09-27 18:21:56',NULL,0),(204,'100295','重庆 - 家家乐1506505605402',6,'10000',1,1,0,1,500000,'重庆',0,NULL,NULL,NULL,0),(205,'100296','重庆 - 家家乐1506503487359',5,'10000',1,1,0,1,500000,'重庆',0,NULL,NULL,NULL,0),(206,'100290','重庆 - 家家乐1506602042316',4,'10000',1,1,0,1,500000,'重庆',0,'管理员','2017-09-29 15:05:33',NULL,0),(207,'100317','重庆 - 家家乐1506482627583',5,'10003',1,1,0,1,500000,'重庆',0,NULL,NULL,NULL,0),(208,'100326','天津 - 步步高',5,'10000',1,1,2,1,120000,'天津',0,NULL,NULL,NULL,0),(210,'100327','重庆 - 1506758042892-天府',1,'10000',1,1,0,1,500000,'重庆',0,NULL,NULL,NULL,0);

/*Table structure for table `sp_adr_contact_info` */

DROP TABLE IF EXISTS `sp_adr_contact_info`;

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
) ENGINE=InnoDB AUTO_INCREMENT=559 DEFAULT CHARSET=utf8;

/*Data for the table `sp_adr_contact_info` */

insert  into `sp_adr_contact_info`(`id`,`provider_name`,`provider_phone`,`provider_email`,`purchase_name`,`purchase_phone`,`purchase_email`,`modify_id`,`status`) values (1,'成福科','18064933007','18548127908@163.com','陈增荣','13438153820','chenzengrong@yatang.cn',114,0),(2,'李志军','13980889504','645393090@qq.com','陈增荣','13438153820','chenzengrong@yatang.cn',NULL,0),(4,'董万超','18980481506','2330438311@qq.com','陈增荣','13438153820','chenzengrong@yatang.cn',NULL,0),(5,'曾绍春','13808039879','304837280@qq.com','陈增荣','13438153820','chenzengrong@yatang.cn',NULL,0),(6,'陈汝源','13408579715','646828474@qq.com','陈增荣','13438153820','chenzengrong@yatang.cn',NULL,0),(7,'陈非梦','13818988201','feimeng.chen@tohkin.com','陈增荣','13438153820','chenzengrong@yatang.cn',NULL,0),(10,'钱克会','13678173763','','贺传宝','13880375573','hechuanbao@yatang.com',NULL,0),(12,'苟艳群','18628081536','2085897238@qq.com','贺传宝','13880375573','hechuanbao@yatang.com',NULL,0),(13,'杨昌卫','13908231605','','贺传宝','13880375573','hechuanbao@yatang.com',140,0),(14,'何磊','13980481170','287337848@qq.com','贺传宝','13880375573','hechuanbao@yatang.com',NULL,0),(15,'张泽勇','13902905046','235942310@qq.com','贺传宝','13880375573','hechuanbao@yatang.com',NULL,0),(17,'乔新','13981867141','413310315@qq.com','贺传宝','13880375573','hechuanbao@yatang.com',NULL,0),(18,'邓建 ','15202818868','1019798398@qq.com','杨妍','18982168776','yangyan@yatang.cn',139,0),(20,'王均华 ','13540635815','1012847154@qq.com','杨妍','18982168776','yangyan@yatang.cn',NULL,0),(21,'周姐 ','18982162548','294361724@qq.com','杨妍','18982168776','yangyan@yatang.cn',NULL,0),(24,'李淳 ','13308070868','442197150@qq.com','杨妍','18982168776','yangyan@yatang.cn',143,0),(25,'丁建','18030497322','578574552@qq.com','杨妍','18982168776','yangyan@yatang.cn',NULL,0),(27,'岳渤','15520751602','cdksy_2012@163.com','杨妍','18982168776','yangyan@yatang.cn',NULL,0),(28,' 杨国琼 ','13548150815','2733889096@qq.com ','杨妍','18982168776','yangyan@yatang.cn',NULL,0),(29,' 张经理 ','15680907787','zhangrui@nihaozhiwu.com','杨妍','18982168776','yangyan@yatang.cn',122,0),(30,'沈总','18581880657','2408322590@qq.com','杨妍','18982168776','yangyan@yatang.cn',NULL,0),(31,'黄春','15928727468','929926572@qq.com','杨妍','18982168776','yangyan@yatang.cn',NULL,0),(32,'李金欣','17380129325','361516108@qq.com','杨妍','18982168776','yangyan@yatang.cn',NULL,0),(33,'杨铭','13981706090','444249368@qq.com','亢小蓉','13438240424','kangxiaorong@yatang.cn',NULL,0),(34,'鲜洪','13308198419','','亢小蓉','13438240424','kangxiaorong@yatang.cn',132,0),(36,'邓天健','18980910233','','亢小蓉','13438240424','kangxiaorong@yatang.cn',NULL,0),(37,'江红丽','15982175052','11111111@163.com','亢小蓉','13438240424','kangxiaorong@yatang.cn',170,0),(38,'向忠林','13688340497','','亢小蓉','13438240424','kangxiaorong@yatang.cn',NULL,0),(39,'杜维','18200435996','','亢小蓉','13438240424','kangxiaorong@yatang.cn',NULL,0),(40,'唐印晖','13980054949','54487823@qq.com','尹朝军','17713549982','yinchaojun@yatang.cn',NULL,0),(41,'杨铭','13981706090','444249368@qq.con','尹朝军','17713549982','yinchaojun@yatang.cn',NULL,0),(42,'王林远','18980982318','1021695683@qq.com','尹朝军','17713549982','yinchaojun@yatang.cn',NULL,0),(71,'私房话','13111111111','23@s.com','官方','13581526891','44@s.com',NULL,0);

/*Table structure for table `sp_adr_delivery_info` */

DROP TABLE IF EXISTS `sp_adr_delivery_info`;

CREATE TABLE `sp_adr_delivery_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sp_adr_id` int(11) DEFAULT NULL COMMENT '供应商地点id',
  `w_id` int(11) DEFAULT NULL COMMENT '仓库ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=657 DEFAULT CHARSET=utf8;

/*Data for the table `sp_adr_delivery_info` */

insert  into `sp_adr_delivery_info`(`id`,`sp_adr_id`,`w_id`) values (12,2,1),(20,5,1),(21,6,2),(22,7,1),(30,53,4),(31,55,1),(38,58,1),(43,60,1),(44,61,4),(48,62,1),(49,63,1),(54,64,1),(55,66,3),(56,65,1),(59,68,1),(61,70,1),(73,69,1),(77,1,1),(81,74,1),(82,75,1),(83,72,1),(86,10,1),(88,12,1),(90,14,1),(91,15,1),(93,17,1),(96,20,1),(97,21,2),(101,25,2),(103,27,2),(104,28,2),(106,30,2),(107,31,2),(108,32,2),(109,33,2),(112,36,2),(114,38,2),(115,39,2),(117,41,2),(118,42,2),(121,29,2),(122,29,1),(128,76,1),(136,73,1),(137,59,1),(141,77,1),(157,78,3),(158,78,1),(160,18,1),(167,13,1),(170,22,2),(171,24,2),(179,80,1),(181,57,1),(184,82,3),(185,83,3),(186,81,1),(189,56,3),(196,84,1),(197,85,1),(198,86,1),(199,67,1),(200,67,2),(208,11,1),(209,23,2),(211,37,2),(212,16,1),(214,54,1),(215,19,1),(216,26,3),(219,35,2),(222,88,3),(223,89,3),(224,71,2),(228,90,1),(272,79,2),(274,93,1),(275,8,3),(276,8,1),(277,8,2),(280,95,1),(282,96,2),(283,94,1),(284,97,1),(285,98,1),(288,99,1),(290,101,1),(291,102,1),(292,103,1),(293,104,1),(294,105,1),(295,106,1),(296,107,1),(297,108,1),(298,109,1),(299,110,1),(300,111,1),(301,112,1),(302,113,1),(303,114,1),(304,115,1),(305,116,1),(306,117,1),(307,118,1),(308,119,1),(309,120,1),(310,121,1),(311,122,1),(312,123,1),(313,124,1),(314,125,1),(315,126,1),(316,127,1),(317,128,1),(318,129,1),(319,130,1),(320,131,1),(321,132,1),(322,133,1),(323,134,1),(324,136,1),(325,137,1),(326,135,1),(327,138,1),(328,139,1),(329,140,1),(330,141,1),(331,142,1),(332,143,1),(333,144,1),(334,146,1),(335,145,1),(336,147,1),(337,148,1),(338,149,1),(339,150,1),(340,151,1),(341,152,1),(342,153,1),(343,154,1),(344,155,1),(345,156,1),(346,157,1),(347,158,1),(348,159,1),(349,160,1),(350,161,1),(351,162,1),(352,163,1),(353,164,1),(354,165,1),(355,166,1),(356,167,1),(357,168,1),(358,169,1),(359,170,1),(360,171,1),(361,172,1),(362,173,1),(363,174,1),(364,175,1),(365,176,1),(366,177,1),(367,178,1),(368,179,1),(369,180,1),(370,181,1),(371,182,1),(372,183,1),(373,184,1),(374,185,1),(375,186,1),(376,187,1),(377,188,1),(378,189,1),(379,190,1),(380,191,1),(381,192,1),(382,193,1),(383,194,1),(384,195,1),(385,196,1),(386,197,1),(387,198,1),(388,199,1),(389,200,1),(392,100,1),(393,202,1),(394,203,1),(395,204,1),(396,205,1),(397,201,3),(399,207,1),(400,208,1),(401,206,1),(402,209,1),(404,210,1),(406,211,1),(408,212,1),(410,213,1),(412,214,1),(416,216,1),(418,217,1),(420,218,1),(422,219,1),(424,220,1),(426,221,1),(428,222,1),(430,223,1),(432,224,1),(434,225,1),(436,226,1),(438,227,1),(440,228,1),(442,229,1),(444,230,1),(446,231,1),(448,232,1),(450,233,1),(452,234,1),(454,235,1),(456,236,1),(458,237,1),(460,238,1),(462,239,1),(464,240,1),(466,241,1),(468,242,1),(470,243,1),(472,244,1),(474,245,1),(476,246,1),(478,247,1),(480,248,1),(482,249,1),(484,250,1),(486,251,1),(488,252,1),(490,253,1),(492,254,1),(494,255,1),(496,256,1),(498,257,1),(500,258,1),(502,259,1),(504,260,1),(506,261,1),(508,262,1),(510,263,1),(512,264,1),(514,265,1),(516,266,1),(518,267,1),(520,268,1),(522,269,1),(524,270,1),(526,271,1),(528,272,1),(530,273,1),(532,274,1),(534,275,1),(536,276,1),(538,277,1),(540,278,1),(542,279,1),(544,280,1),(546,281,1),(548,282,1),(550,283,1),(552,284,1),(554,285,1),(556,286,1),(558,287,1),(560,288,1),(562,289,1),(564,290,1),(566,291,1),(568,292,1),(570,293,1),(572,294,1),(574,295,1),(576,296,1),(577,297,1),(582,299,1),(584,300,1),(586,301,1),(588,302,1),(590,303,1),(592,304,1),(594,305,1),(596,306,1),(598,307,1),(600,308,1),(601,309,1),(602,310,1),(603,4,1),(604,215,2),(605,215,1),(609,312,1),(611,313,1),(613,314,1),(614,315,1),(615,315,2),(616,315,3),(617,34,2),(618,40,2),(620,91,2),(625,317,2),(626,318,3),(627,318,2),(628,318,1),(629,92,1),(630,319,2),(631,320,2),(632,321,2),(633,87,2),(634,87,12),(635,87,3),(636,298,3),(639,9,1),(641,316,3),(642,311,1),(645,327,3),(646,327,2),(653,3,12),(654,3,1),(655,3,3),(656,3,2);

/*Table structure for table `sp_bank_info` */

DROP TABLE IF EXISTS `sp_bank_info`;

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
) ENGINE=InnoDB AUTO_INCREMENT=758 DEFAULT CHARSET=utf8;

/*Data for the table `sp_bank_info` */

insert  into `sp_bank_info`(`id`,`account_name`,`open_bank`,`bank_account`,`bank_account_license`,`bank_loc_province_code`,`bank_loc_province`,`bank_loc_city_code`,`bank_loc_city`,`bank_loc_county_code`,`bank_loc_county`,`invoice_head`,`modify_id`,`status`) values (2,'成都蒙利天和商贸有限公司','中国银行股份有限公司成都晋阳支行','117191869228',NULL,'510000','四川','510100','成都','510107','武侯区','成都蒙利天和商贸有限公司',NULL,0),(3,'成都诚心诚意商贸有限公司','成都农商银行桂溪支行','021204000120010014065',NULL,'510000','四川','510100','成都','510104','锦江区','成都诚心诚意商贸有限公司',NULL,0),(4,'成都择好商贸有限公司','中国建设银行股份有限公司成都第二支行','51050142620800000360',NULL,'510000','四川','510100','成都','510104','锦江区','成都择好商贸有限公司',NULL,0),(5,'四川康昊食品贸易有限公司','中国工商银行股份有限公司成都四川大学支行','4402071209000008732',NULL,'510000','四川','510100','成都','510106','金牛区','四川康昊食品贸易有限公司',NULL,0),(6,'成都景中商贸有限公司','中国工商银行股份有限公司成都青龙支行东电集团分理处','4402238009000095630',NULL,'510000','四川','510100','成都','510106','金牛区','成都景中商贸有限公司',NULL,0),(7,'日加满饮品（上海）有限公司','中国农业银行股份有限公司上海封浜支行','13828808015007137',NULL,'310000','上海','310100','上海','310114','嘉定区','日加满饮品（上海）有限公司',NULL,0),(12,'成都市凌宇食品有限公司','中国银行股份有限公司成都机投支行','125267838308',NULL,'510000','四川','510100','成都','510107','武侯区','成都市凌宇食品有限公司',NULL,0),(13,'成都张飞调味品有限公司','中国农业银行股份有限公司成都杨柳店分理处','836001040007534',NULL,'510000','四川','510100','成都','510108','成华区','成都张飞调味品有限公司',NULL,0),(14,'成都辰龙商贸有限公司','中国建设银行股份有限公司成都航空路支行','51001895236052503635',NULL,'510000','四川','510100','成都','510107','武侯区','成都辰龙商贸有限公司',NULL,0),(15,'四川省天渠盐化有限公司广东分公司','中国农业银行股分有限公司深圳珠江广场支行','41024600040001300',NULL,'440000','广东','440300','深圳','440307','龙岗区','四川省天渠盐化有限公司广东分公司',NULL,0),(18,'成都清润贸易发展有限公司','中国银行','221208790928',NULL,'510000','四川','510100','成都','510107','武侯区','成都清润贸易发展有限公司',NULL,0),(20,'桂林智强食品开发有限公司','工商银行','2103236109245093874',NULL,'450000','广西','450300','桂林','450331','荔浦县','桂林智强食品开发有限公司 ',NULL,0),(21,'成都市富亿通贸易有限公司','中国银行','130658748911',NULL,'510000','四川','510100','成都','510104','锦江区','成都市富亿通贸易有限公司',NULL,0),(24,'成都天旺商贸有限公司','农村商业银行','021104000120010012590',NULL,'510000','四川','510100','成都','510112','龙泉驿区','成都天旺商贸有限公司',298,0),(25,'成都市永联兴贸易有限公司','中国银行','117155053203',NULL,'510000','四川','510100','成都','510107','武侯区','成都市永联兴贸易有限公司',NULL,0),(27,'成都凯斯原商贸有限公司','工商银行','4402298009100066884',NULL,'510000','四川','510100','成都','510104','锦江区','成都凯斯原商贸有限公司',NULL,0),(28,'成都铭昊商贸有限公司','农业银行','801801040003180',NULL,'510000','四川','510100','成都','510104','锦江区','成都铭昊商贸有限公司',NULL,0);

/*Table structure for table `sp_basic_info` */

DROP TABLE IF EXISTS `sp_basic_info`;

CREATE TABLE `sp_basic_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `company_name` varchar(255) DEFAULT NULL COMMENT '公司名称',
  `sp_no` varchar(255) DEFAULT NULL COMMENT '供应商编号',
  `grade` int(11) DEFAULT NULL COMMENT '供应商等级:1:战略供应商;2:核心供应商;3:可替代供应商',
  `settled_time` datetime DEFAULT NULL COMMENT '入驻时间',
  `modify_id` int(11) DEFAULT NULL COMMENT '修改信息id,如有修改操作关联到修改记录',
  `status` int(2) DEFAULT '0' COMMENT '0：原记录, 1：修改记录',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=758 DEFAULT CHARSET=utf8;

/*Data for the table `sp_basic_info` */

insert  into `sp_basic_info`(`id`,`company_name`,`sp_no`,`grade`,`settled_time`,`modify_id`,`status`) values (2,'成都蒙利天和商贸有限公司','10001',2,'2017-08-15 00:00:00',NULL,0),(3,'成都诚心诚意商贸有限公司','10002',2,'2017-08-15 00:00:00',NULL,0),(4,'成都择好商贸有限公司','10003',2,'2017-08-15 00:00:00',NULL,0),(5,'四川康昊食品贸易有限公司','10004',2,'2017-08-15 00:00:00',NULL,0),(6,'成都景中商贸有限公司','10005',2,'2017-08-15 00:00:00',NULL,0),(7,'日加满饮品（上海）有限公司','10006',1,'2017-08-15 00:00:00',NULL,0),(12,'成都市凌宇食品有限公司','10011',3,'2017-08-20 00:00:00',NULL,0),(13,'成都张飞调味品有限公司','10012',2,'2017-08-20 00:00:00',NULL,0),(14,'成都辰龙商贸有限公司','10013',3,'2017-08-20 00:00:00',NULL,0),(15,'四川省天渠盐化有限公司广东分公司','10014',2,'2017-08-20 00:00:00',NULL,0),(18,'成都清润贸易发展有限公司','10017',2,'2017-08-20 00:00:00',NULL,0),(20,'桂林智强食品开发有限公司 ','10019',2,'2017-08-20 00:00:00',NULL,0),(21,'成都市富亿通贸易有限公司','10020',3,'2017-08-20 00:00:00',NULL,0),(24,'成都天旺商贸有限公司','10023',2,'2017-08-20 00:00:00',298,0),(25,'成都市永联兴贸易有限公司','10024',2,'2017-08-20 00:00:00',NULL,0),(27,'成都凯斯原商贸有限公司','10026',2,'2017-08-20 00:00:00',NULL,0),(28,'成都铭昊商贸有限公司','10027',2,'2017-08-20 00:00:00',NULL,0),(29,'成都你好植物科技有限公司','10028',1,'2017-08-20 00:00:00',NULL,0),(30,'成都兆廷商贸有限公司','10029',3,'2017-08-20 00:00:00',31,0),(31,'成都蜀特商贸有限公司new','10030',1,'2017-08-19 00:00:00',NULL,1),(32,'成都宏旺达贸易有限公司','10031',2,'2017-08-20 00:00:00',NULL,0),(33,'成都友程贸易有限公司11','10032',2,'2017-08-20 00:00:00',324,0),(34,'成都集佰汇科技有限公司','10033',3,'2017-08-20 00:00:00',753,0),(36,'成都瑞琪达商贸有限公司','10035',3,'2017-08-20 00:00:00',NULL,0),(38,'成都小超邦科技有限公司','10037',2,'2017-08-20 00:00:00',NULL,0),(39,'成都金福洋贸易有限公司','10038',2,'2017-08-20 00:00:00',296,0),(40,'成都百分百佳商贸有限公司 ','10039',1,'2017-08-20 00:00:00',NULL,0),(41,'成都友程贸易有限公司','10040',1,'2017-08-20 00:00:00',NULL,0),(42,'成都市鑫鑫越洋商贸有限公司','10041',1,'2017-08-20 00:00:00',322,0);

/*Table structure for table `sp_license_info` */

DROP TABLE IF EXISTS `sp_license_info`;

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
) ENGINE=InnoDB AUTO_INCREMENT=758 DEFAULT CHARSET=utf8;

/*Data for the table `sp_license_info` */

insert  into `sp_license_info`(`id`,`company_name`,`regist_licence_number`,`legal_representative`,`legal_repre_card_num`,`legal_repre_card_pic1`,`legal_repre_card_pic2`,`license_loc_province_code`,`license_loc_province`,`license_loc_city_code`,`license_loc_city`,`license_loc_county_code`,`license_loc_county`,`license_address`,`establish_date`,`start_date`,`end_date`,`perpetual_management`,`registered_capital`,`business_scope`,`regist_licence_pic`,`guarantee_money`,`modify_id`,`status`) values (2,'成都蒙利天和商贸有限公司','91510107MA61U2TD9T','刘永艳',NULL,NULL,NULL,'510000','四川','510100','成都','510107','武侯区','成都市武侯区万盛路6号附1号','2006-03-31 00:00:00',NULL,NULL,1,'50.00','食品、饮料烟酒','undefined/undefined','5000.00',NULL,0),(3,'成都诚心诚意商贸有限公司','91510100667584315K','张文全',NULL,NULL,NULL,'510000','四川','510100','成都','510104','锦江区','成都市高新区肖家河东一巷6号','2007-11-05 00:00:00',NULL,NULL,1,'50.00','食品、饮料烟酒','undefined/undefined','5000.00',NULL,0),(4,'成都择好商贸有限公司','91510100MA61THH44E','贺开喜',NULL,NULL,NULL,'510000','四川','510100','成都','510104','锦江区','成都市高新区锦城大道666号4栋19层16号','2016-02-16 00:00:00',NULL,NULL,1,'50.00','食品、饮料烟酒','/group1/M00/01/4F/rB4KPFmvpBiAfP5dAAkVVGMybN8800.png','5000.00',NULL,0),(5,'四川康昊食品贸易有限公司','91510106562044152k','黄传东',NULL,NULL,NULL,'510000','四川','510100','成都','510106','金牛区','成都市金牛区一环路北二段3号','2010-10-19 00:00:00',NULL,NULL,1,'50.00','食品、饮料烟酒','/group1/M00/01/4F/rB4KPFmvo8yAIqypAAl5WLU-YRY279.png','5000.00',NULL,0),(6,'成都景中商贸有限公司','91510100572261169L','陈锡易',NULL,NULL,NULL,'510000','四川','510100','成都','510106','金牛区','成都市金牛区蜀兴西街187号4楼','2011-04-06 00:00:00',NULL,NULL,1,'200.00','食品、饮料烟酒','undefined/undefined','10000.00',NULL,0),(7,'日加满饮品（上海）有限公司','81310000607201386K','荣耀中',NULL,NULL,NULL,'310000','上海','310100','上海','310114','嘉定区','上海市嘉定区曹安路12号桥金园五路108号','1993-04-05 00:00:00',NULL,NULL,1,'7005.22','食品、饮料烟酒','/group1/M00/01/50/rB4KPlmw9ayATpNEAAkVVGMybN8134.png','30000.00',NULL,0),(12,'成都市凌宇食品有限公司','91510107590214825P','吴欣燕',NULL,NULL,NULL,'510000','四川','510100','成都','510107','武侯区','晋阳路218号1栋6单元11楼44号','2012-02-09 00:00:00',NULL,NULL,1,'100.00','粮油','undefined/undefined','5000.00',NULL,0),(13,'成都张飞调味品有限公司','91510108395771415C','陈光伟',NULL,NULL,NULL,'510000','四川','510100','成都','510108','成华区','迎晖路81号附3楼318号','2014-09-03 00:00:00',NULL,NULL,1,'100.00','粮油','/group1/M00/01/4E/rB4KPVmvRCyAYIWPAAzodQCbVVc681.png','0.00',NULL,0),(14,'成都辰龙商贸有限公司','915101075849797338','何磊',NULL,NULL,NULL,'510000','四川','510100','成都','510107','武侯区','科华中路新3号1幢11层1102号','2011-11-11 00:00:00',NULL,NULL,1,'50.00','粮油','/group1/M00/01/4E/rB4KPlmvRHiAYKMoAAl5WLU-YRY830.png','5000.00',NULL,0),(15,'四川省天渠盐化有限公司广东分公司','91440300MA5EGH7581','赵希元',NULL,NULL,NULL,'440000','广东','440300','深圳','440307','龙岗区','龙城街道清林路龙城工业园2号南区一楼111号','2007-04-25 00:00:00',NULL,NULL,1,'100.00','粮油','/group1/M00/01/50/rB4KPlmw_IKABchxAAvWFkcZHjA240.png','5000.00',NULL,0),(18,'成都清润贸易发展有限公司','510107000238653','池清平',NULL,NULL,NULL,'510000','四川','510100','成都','510107','武侯区','棕南正街2号3单元1楼2号','2006-03-03 00:00:00',NULL,NULL,1,'50.00','休闲食品','undefined/undefined','5000.00',NULL,0),(20,'桂林智强食品开发有限公司','91450331708694482P','吴志超',NULL,NULL,NULL,'450000','广西','450300','桂林','450331','荔浦县','荔城环东路45号','1996-05-05 00:00:00',NULL,NULL,1,'128.00','休闲食品','undefined/undefined','5000.00',NULL,0),(21,'成都市富亿通贸易有限公司','91510100681812820K','唐金凤',NULL,NULL,NULL,'510000','四川','510100','成都','510104','锦江区','天府大道北段1700号7栋1单元16层1623号 ','2008-11-21 00:00:00',NULL,NULL,1,'50.00','休闲食品','undefined/undefined','5000.00',NULL,0),(24,'成都天旺商贸有限公司','91510108567180794G','晏焱',NULL,NULL,NULL,'510000','四川','510100','成都','510112','龙泉驿区','建设北路三段88号麦理基大厦714房','2011-01-16 00:00:00',NULL,NULL,1,'50.00','休闲食品','/group1/M00/01/4B/rB4KPlmoxkOADHTWAAhPl88t620324.png','5000.00',298,0),(25,'成都市永联兴贸易有限公司','915101002019693016','白全美',NULL,NULL,NULL,'510000','四川','510100','成都','510107','武侯区','总府街15号王府井百货商品房B座十一层','1996-01-05 00:00:00',NULL,NULL,1,'150.00','休闲食品','undefined/undefined','5000.00',NULL,0),(27,'成都凯斯原商贸有限公司','9151010058759183D','岳渤',NULL,NULL,NULL,'510000','四川','510100','成都','510104','锦江区','濯锦北路124号1层','2012-01-12 00:00:00',NULL,NULL,1,'200.00','休闲食品','undefined/undefined','5000.00',NULL,0),(28,'成都铭昊商贸有限公司','9151010767715057x8','谭子良',NULL,NULL,NULL,'510000','四川','510100','成都','510104','锦江区','三环路西一段外出四川西南食品城33幢7号','2008-06-23 00:00:00',NULL,NULL,1,'100.00','休闲食品','undefined/undefined','5000.00',NULL,0),(29,'成都你好植物科技有限公司','91510106064327459U','张小伟',NULL,NULL,NULL,'510000','四川','510100','成都','510104','锦江区','黄忠街8号3楼11号','2013-04-08 00:00:00',NULL,NULL,1,'360.82','休闲食品','/group1/M00/01/4F/rB4KPVmvo-mAa8-LAAl5WLU-YRY177.png','5000.00',NULL,0),(30,'成都兆廷商贸有限公司','91510108MA61RR4B5L','冷书云',NULL,NULL,NULL,'510000','四川省','510100','成都市','510116','成华区','建设北路三段1号2幢1-2层15号','2015-12-02 00:00:00',NULL,NULL,1,'100.00','休闲食品','bbb.jpg','5000.00',31,0),(31,'成都蜀特商贸有限公司new','915101085535676725hh','彭海东哈哈',NULL,NULL,NULL,'510000','四川','510100','成都','510116','双流区','建材路66号1栋2楼哈哈','2010-04-13 00:00:00',NULL,NULL,1,'500.00','休闲食品','/abc.jpg','0.00',NULL,1),(32,'成都宏旺达贸易有限公司','915101007497326334','孙继红',NULL,NULL,NULL,'510000','四川','510100','成都','510107','武侯区','天顺路291号1层','2003-06-27 00:00:00',NULL,NULL,1,'50.00','休闲食品','/group1/M00/01/4F/rB4KPVmvps-ADIfuAAvWFkcZHjA487.png','5000.00',NULL,0),(33,'成都友程贸易有限公司11','91510106052503398H','曾友全',NULL,NULL,NULL,'510000','四川','510100','成都','510106','金牛区','五块石蓉北商贸大道1段2号','2012-08-10 00:00:00',NULL,NULL,1,'100.00','针纺服饰','/group1/M00/01/51/rB4KPVmxEhuAf2r_AAvea_OGt2M152.png','3000.00',324,0),(34,'成都集佰汇科技有限公司','91510108597283145T','鲜洪',NULL,NULL,NULL,'510000','四川','510100','成都','510108','成华区','二环路东一段29号','2012-06-15 00:00:00',NULL,NULL,1,'100.00','洗涤日化','/group1/M00/01/4D/rB4KPVmsv2eAAJWzAAFXJuyTuys279.png','5000.00',753,0),(36,'成都瑞琪达商贸有限公司','91510100350514415K','邓天建',NULL,NULL,NULL,'510000','四川','510100','成都','510104','锦江区','锦城大道666号','2015-08-31 00:00:00',NULL,NULL,1,'90.00','洗涤日化','undefined/undefined','1000.00',NULL,0),(38,'成都小超邦科技有限公司','91510107MA6DF5697N','向忠林',NULL,NULL,NULL,'510000','四川','510100','成都','510107','武侯区','郭家桥北街11号','2017-07-28 00:00:00',NULL,NULL,1,'100.00','洗涤日化','undefined/undefined','3000.00',NULL,0),(39,'成都金福洋贸易有限公司','91510108558999079P','杜维',NULL,NULL,NULL,'510000','四川','510100','成都','510106','金牛区','建设北路三段89号','2010-08-23 00:00:00',NULL,NULL,1,'500.00','洗涤日化','undefined/undefined','0.00',296,0),(40,'成都百分百佳商贸有限公司','adfsadf12313131212asdfa','兰燕',NULL,NULL,NULL,'510000','四川','510100','成都','510108','成华区','成都市成华区天祥街59号-1幢3层27号','2015-02-06 00:00:00',NULL,NULL,1,'30.00','日杂','undefined/undefined','3000.00',NULL,0),(41,'成都友程贸易有限公司','4545545454','曾友全',NULL,NULL,NULL,'510000','四川','510100','成都','510106','金牛区','成都金牛区五块石蓉北商贸大道2号1栋601室','2012-08-10 00:00:00',NULL,NULL,1,'100.00','日杂','/group1/M00/01/51/rB4KPlm3pLeAWMDlAAD9Zzqp4oc528.png','3000.00',NULL,0),(42,'成都市鑫鑫越洋商贸有限公司','adfsadf1231313112','米成敏',NULL,NULL,NULL,'510000','四川','510100','成都','510105','青羊区','成都市青羊区金盾路52号9层1号','2017-07-25 00:00:00',NULL,NULL,1,'200.00','日杂','undefined/undefined','5000.00',322,0),(65,'sl供应商2','3425','实得分数',NULL,NULL,NULL,'120000','天津','120100','天津','120102','河东区','法国恢复','2017-08-16 00:00:00',NULL,NULL,1,'345645.00','好','/group1/M00/01/35/rB4KPlmTu6aAMm_JAAhPl88t620702.png','56345.00',67,0),(66,'阿达','23423234645','阿萨德',NULL,NULL,NULL,'360000','江西','360300','萍乡','360313','湘东区','阿斯达斯大所','2017-08-01 00:00:00',NULL,NULL,1,'500.00','阿斯达斯大','/group1/M00/01/36/rB4KPFmTu16Ab6AhAAl5WLU-YRY778.png','336341.00',158,0),(67,'sl供应商2','3425','实得分数',NULL,NULL,NULL,'120000','天津','120100','天津','120102','河东区','法国恢复','2017-08-16 00:00:00',NULL,NULL,1,'345645.00','好','/group1/M00/01/35/rB4KPlmTu6aAMm_JAAhPl88t620702.png','56345.00',NULL,1),(70,'sl供应商1','3456345','使得法国',NULL,NULL,NULL,'150000','内蒙古','150200','包头','150202','东河区','官方还将','2017-08-16 00:00:00',NULL,NULL,1,'3456.00','环境','/group2/M00/00/24/rB4KPFmTut-AWZe1AAhPl88t620385.png','345345.00',149,0),(78,'YTtesting','152','撒旦','518125415125125','/group2/M00/00/24/rB4KPVmT4f6AfI5JAAAl9GLuGY4536.png',NULL,'120000','天津','120100','天津','120101','和平区','撒旦','2017-08-16 00:00:00','2017-08-16 00:00:00','2017-09-12 00:00:00',0,'12.00','阿斯顿','/group1/M00/01/36/rB4KPVmT4gKAOeQZAABffyKgwUo482.png','2.00',181,0);

/*Table structure for table `sp_operating_taxation_info` */

DROP TABLE IF EXISTS `sp_operating_taxation_info`;

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
) ENGINE=InnoDB AUTO_INCREMENT=760 DEFAULT CHARSET=utf8;

/*Data for the table `sp_operating_taxation_info` */

insert  into `sp_operating_taxation_info`(`id`,`company_loc_province_code`,`company_loc_province`,`company_loc_city_code`,`company_loc_city`,`company_loc_county_code`,`company_loc_county`,`company_detail_address`,`registration_certificate`,`reg_cer_expiring_date`,`quality_identification`,`qua_ide_expiring_date`,`food_business_license`,`business_license_expiring_date`,`general_taxpayer_qualifi_certi`,`taxpayer_cert_expiring_date`,`modify_id`,`status`,`failed_reason`) values (2,'510000','四川','510100','成都','510107','武侯区','成都市武侯区万盛路6号附1号',NULL,NULL,NULL,NULL,NULL,NULL,'undefined/undefined',NULL,NULL,0,NULL),(3,'510000','四川','510100','成都','510104','锦江区','成都市高新区肖家河东一巷7号',NULL,NULL,NULL,NULL,NULL,NULL,'undefined/undefined',NULL,NULL,0,NULL),(4,'510000','四川','510100','成都','510104','锦江区','成都市高新区锦城大道666号4栋19层16号',NULL,NULL,NULL,NULL,NULL,NULL,'/group1/M00/01/4E/rB4KPlmvpBaAA6xhAAvWFkcZHjA637.png',NULL,NULL,0,NULL),(5,'510000','四川','510100','成都','510106','金牛区','成都市金牛区一环路北二段3号',NULL,NULL,NULL,NULL,NULL,NULL,'/group1/M00/01/4E/rB4KPlmvo8uADZDtAAvqH_kipG8792.png',NULL,NULL,0,NULL),(6,'510000','四川','510100','成都','510106','金牛区','成都市金牛区蜀兴西街187号5楼',NULL,NULL,NULL,NULL,NULL,NULL,'undefined/undefined',NULL,NULL,0,NULL),(7,'310000','上海','310100','上海','310114','嘉定区','上海市嘉定区曹安路12号桥金园五路108号',NULL,NULL,NULL,NULL,NULL,NULL,'/group1/M00/01/51/rB4KPFmw9aqAfuQQAAvqH_kipG8239.png',NULL,NULL,0,NULL),(12,'510000','四川','510100','成都','510107','武侯区','晋阳路218号1栋6单元11楼44号',NULL,NULL,NULL,NULL,NULL,NULL,'undefined/undefined',NULL,NULL,0,NULL),(13,'510000','四川','510100','成都','510108','成华区','迎晖路81号附3楼318号',NULL,NULL,NULL,NULL,NULL,NULL,'/group1/M00/01/4E/rB4KPVmvRB6APjjdAAvWFkcZHjA162.png',NULL,NULL,0,NULL),(14,'510000','四川','510100','成都','510107','武侯区','科华中路新3号1幢11层1102号',NULL,NULL,NULL,NULL,NULL,NULL,'/group1/M00/01/4E/rB4KPlmvRHWAQkh_AAvqH_kipG8933.png',NULL,NULL,0,NULL),(15,'440000','广东','440300','深圳','440307','龙岗区','龙城街道清林路龙城工业园2号南区一楼111号',NULL,NULL,NULL,NULL,NULL,NULL,'/group1/M00/01/51/rB4KPVmw_H6ADxLoAAiQfKHDaaQ914.png',NULL,NULL,0,NULL),(18,'510000','四川','510100','成都','510107','武侯区','星狮路511号大合仓4楼A区409号',NULL,NULL,NULL,NULL,NULL,NULL,'undefined/undefined',NULL,NULL,0,NULL),(20,'510000','四川','510100','成都','510107','武侯区','二环科华北路153号棕榈花园瑞景阁16F',NULL,NULL,NULL,NULL,NULL,NULL,'undefined/undefined',NULL,NULL,0,NULL),(21,'510000','四川','510100','成都','510104','锦江区','益州大道中段772号复城国际T2-1401',NULL,NULL,NULL,NULL,NULL,NULL,'undefined/undefined',NULL,NULL,0,NULL),(24,'510000','四川','510100','成都','510112','龙泉驿区','西河镇阙家村12组54号',NULL,NULL,NULL,NULL,NULL,NULL,'/group1/M00/01/4C/rB4KPFmoxkCAC6APAAhPl88t620163.png',NULL,299,0,NULL),(25,'510000','四川','510100','成都','510107','武侯区','新光路1号观南上域5栋1702号',NULL,NULL,NULL,NULL,NULL,NULL,'undefined/undefined',NULL,NULL,0,NULL),(27,'510000','四川','510100','成都','510104','锦江区','锦华万达3单元1604',NULL,NULL,NULL,NULL,NULL,NULL,'undefined/undefined',NULL,NULL,0,NULL),(28,'510000','四川','510100','成都','510104','锦江区','盛和一路88号康普雷斯A座1709',NULL,NULL,NULL,NULL,NULL,NULL,'undefined/undefined',NULL,NULL,0,NULL),(29,'510000','四川','510100','成都','510104','锦江区','合作路89号龙湖时代天街19栋1515号',NULL,NULL,NULL,NULL,NULL,NULL,'/group1/M00/01/4F/rB4KPFmvo-aADsHyAAiQfKHDaaQ761.png',NULL,NULL,0,NULL),(30,'510000','四川省','510100','成都市','510116','双流区','成白路88号华丰食品城202栋5号兆廷食品',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL),(31,'510000','四川','510100','成都','510112','龙泉驿区','十陵镇来龙村19组362号',NULL,NULL,NULL,NULL,NULL,NULL,'/group1/M00/01/4E/rB4KPVmt-2eABN1gAAhPl88t620577.png',NULL,248,0,NULL),(32,'510000','四川','510100','成都','510107','武侯区','武侯大道双楠段100月光诚品10层1006',NULL,NULL,NULL,NULL,NULL,NULL,'/group1/M00/01/4F/rB4KPFmvps2AeB0XAAvqH_kipG8017.png',NULL,NULL,0,NULL),(33,'510000','四川','510100','成都','510106','金牛区','五块石蓉北商贸大道1段2号',NULL,NULL,NULL,NULL,NULL,NULL,'/group1/M00/01/51/rB4KPVmxEhWAAqNSAABfmYTj0bY755.png',NULL,325,0,NULL),(34,'510000','四川','510100','成都','510108','成华区','二环路东一段29号',NULL,NULL,NULL,NULL,NULL,NULL,'/group1/M00/01/4D/rB4KPVmsv1yAEYccAAJlg4YYczE638.png',NULL,755,0,NULL),(36,'510000','四川','510100','成都','510104','锦江区','三圣乡幸福路西段',NULL,NULL,NULL,NULL,NULL,NULL,'undefined/undefined',NULL,NULL,0,NULL),(38,'510000','四川','510100','成都','510107','武侯区','郭家桥北街11号',NULL,NULL,NULL,NULL,NULL,NULL,'undefined/undefined',NULL,NULL,0,NULL),(39,'510000','四川','510100','成都','510106','金牛区','星辉西路9号',NULL,NULL,NULL,NULL,NULL,NULL,'undefined/undefined',NULL,297,0,NULL),(40,'510000','四川','510100','成都','510108','成华区','成都市成华区天祥街59号-2幢3层27号',NULL,NULL,NULL,NULL,NULL,NULL,'undefined/undefined',NULL,NULL,0,NULL),(41,'510000','四川','510100','成都','510106','金牛区','成都金牛区五块石蓉北商贸大道2号1栋601室',NULL,NULL,NULL,NULL,NULL,NULL,'/group1/M00/01/52/rB4KPVm3pLSAB131AAFXJuyTuys836.png',NULL,NULL,0,NULL);

/*Table structure for table `sp_sale_region` */

DROP TABLE IF EXISTS `sp_sale_region`;

CREATE TABLE `sp_sale_region` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `json` longtext COMMENT 'json树形结构数据(以json键值对方式方式存储)',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=465 DEFAULT CHARSET=utf8;

/*Data for the table `sp_sale_region` */

insert  into `sp_sale_region`(`id`,`json`) values (1,'[{\"code\":\"50\",\"regionName\":\"西南地区\",\"regions\":[{\"code\":\"500000\",\"regionName\":\"重庆\",\"regions\":[{\"code\":\"500100\",\"regionName\":\"重庆\"},{\"code\":\"500200\",\"regionName\":\"重庆郊县\"}]},{\"code\":\"510000\",\"regionName\":\"四川\",\"regions\":[{\"code\":\"510100\",\"regionName\":\"成都\"},{\"code\":\"510300\",\"regionName\":\"自贡\"},{\"code\":\"510400\",\"regionName\":\"攀枝花\"},{\"code\":\"510500\",\"regionName\":\"泸州\"},{\"code\":\"510600\",\"regionName\":\"德阳\"},{\"code\":\"510700\",\"regionName\":\"绵阳\"},{\"code\":\"510800\",\"regionName\":\"广元\"},{\"code\":\"510900\",\"regionName\":\"遂宁\"},{\"code\":\"511000\",\"regionName\":\"内江\"},{\"code\":\"511100\",\"regionName\":\"乐山\"},{\"code\":\"511300\",\"regionName\":\"南充\"},{\"code\":\"511400\",\"regionName\":\"眉山\"},{\"code\":\"511500\",\"regionName\":\"宜宾\"},{\"code\":\"511600\",\"regionName\":\"广安\"},{\"code\":\"511700\",\"regionName\":\"达州\"},{\"code\":\"511800\",\"regionName\":\"雅安\"},{\"code\":\"511900\",\"regionName\":\"巴中\"},{\"code\":\"512000\",\"regionName\":\"资阳\"},{\"code\":\"513200\",\"regionName\":\"阿坝藏族羌族自治州\"},{\"code\":\"513300\",\"regionName\":\"甘孜藏族自治州\"},{\"code\":\"513400\",\"regionName\":\"凉山彝族自治州\"}]}]}]'),(2,'[{\"code\":\"31\",\"regionName\":\"华东地区\",\"regions\":[{\"code\":\"310000\",\"regionName\":\"上海\",\"regions\":[{\"code\":\"310100\",\"regionName\":\"上海\"}]},{\"code\":\"320000\",\"regionName\":\"江苏\",\"regions\":[{\"code\":\"320100\",\"regionName\":\"南京\"},{\"code\":\"320200\",\"regionName\":\"无锡\"},{\"code\":\"320300\",\"regionName\":\"徐州\"},{\"code\":\"320400\",\"regionName\":\"常州\"},{\"code\":\"320500\",\"regionName\":\"苏州\"},{\"code\":\"320600\",\"regionName\":\"南通\"},{\"code\":\"320700\",\"regionName\":\"连云港\"},{\"code\":\"320800\",\"regionName\":\"淮安\"},{\"code\":\"320900\",\"regionName\":\"盐城\"},{\"code\":\"321000\",\"regionName\":\"扬州\"},{\"code\":\"321100\",\"regionName\":\"镇江\"},{\"code\":\"321200\",\"regionName\":\"泰州\"},{\"code\":\"321300\",\"regionName\":\"宿迁\"}]},{\"code\":\"330000\",\"regionName\":\"浙江\",\"regions\":[{\"code\":\"330100\",\"regionName\":\"杭州\"},{\"code\":\"330200\",\"regionName\":\"宁波\"},{\"code\":\"330300\",\"regionName\":\"温州\"},{\"code\":\"330400\",\"regionName\":\"嘉兴\"},{\"code\":\"330500\",\"regionName\":\"湖州\"},{\"code\":\"330600\",\"regionName\":\"绍兴\"},{\"code\":\"330700\",\"regionName\":\"金华\"},{\"code\":\"330800\",\"regionName\":\"衢州\"},{\"code\":\"330900\",\"regionName\":\"舟山\"},{\"code\":\"331000\",\"regionName\":\"台州\"},{\"code\":\"331100\",\"regionName\":\"丽水\"}]},{\"code\":\"340000\",\"regionName\":\"安徽\",\"regions\":[{\"code\":\"340100\",\"regionName\":\"合肥\"},{\"code\":\"340200\",\"regionName\":\"芜湖\"},{\"code\":\"340300\",\"regionName\":\"蚌埠\"},{\"code\":\"340400\",\"regionName\":\"淮南\"},{\"code\":\"340500\",\"regionName\":\"马鞍山\"},{\"code\":\"340600\",\"regionName\":\"淮北\"},{\"code\":\"340700\",\"regionName\":\"铜陵\"},{\"code\":\"340800\",\"regionName\":\"安庆\"},{\"code\":\"341000\",\"regionName\":\"黄山\"},{\"code\":\"341100\",\"regionName\":\"滁州\"},{\"code\":\"341200\",\"regionName\":\"阜阳\"},{\"code\":\"341300\",\"regionName\":\"宿州\"},{\"code\":\"341500\",\"regionName\":\"六安\"},{\"code\":\"341600\",\"regionName\":\"亳州\"},{\"code\":\"341700\",\"regionName\":\"池州\"},{\"code\":\"341800\",\"regionName\":\"宣城\"}]},{\"code\":\"350000\",\"regionName\":\"福建\",\"regions\":[{\"code\":\"350100\",\"regionName\":\"福州\"},{\"code\":\"350200\",\"regionName\":\"厦门\"},{\"code\":\"350300\",\"regionName\":\"莆田\"},{\"code\":\"350400\",\"regionName\":\"三明\"},{\"code\":\"350500\",\"regionName\":\"泉州\"},{\"code\":\"350600\",\"regionName\":\"漳州\"},{\"code\":\"350700\",\"regionName\":\"南平\"},{\"code\":\"350800\",\"regionName\":\"龙岩\"},{\"code\":\"350900\",\"regionName\":\"宁德\"}]},{\"code\":\"370000\",\"regionName\":\"山东\",\"regions\":[{\"code\":\"370100\",\"regionName\":\"济南\"},{\"code\":\"370200\",\"regionName\":\"青岛\"},{\"code\":\"370300\",\"regionName\":\"淄博\"},{\"code\":\"370400\",\"regionName\":\"枣庄\"},{\"code\":\"370500\",\"regionName\":\"东营\"},{\"code\":\"370600\",\"regionName\":\"烟台\"},{\"code\":\"370700\",\"regionName\":\"潍坊\"},{\"code\":\"370800\",\"regionName\":\"济宁\"},{\"code\":\"370900\",\"regionName\":\"泰安\"},{\"code\":\"371000\",\"regionName\":\"威海\"},{\"code\":\"371100\",\"regionName\":\"日照\"},{\"code\":\"371200\",\"regionName\":\"莱芜\"},{\"code\":\"371300\",\"regionName\":\"临沂\"},{\"code\":\"371400\",\"regionName\":\"德州\"},{\"code\":\"371500\",\"regionName\":\"聊城\"},{\"code\":\"371600\",\"regionName\":\"滨州\"},{\"code\":\"371700\",\"regionName\":\"菏泽\"}]}]}]'),(3,'[{\"code\":\"31\",\"regionName\":\"华东地区\",\"regions\":[{\"code\":\"320000\",\"regionName\":\"江苏\",\"regions\":[{\"code\":\"320100\",\"regionName\":\"南京\"},{\"code\":\"320200\",\"regionName\":\"无锡\"},{\"code\":\"320300\",\"regionName\":\"徐州\"},{\"code\":\"320400\",\"regionName\":\"常州\"},{\"code\":\"320500\",\"regionName\":\"苏州\"},{\"code\":\"320600\",\"regionName\":\"南通\"},{\"code\":\"320700\",\"regionName\":\"连云港\"},{\"code\":\"320800\",\"regionName\":\"淮安\"},{\"code\":\"320900\",\"regionName\":\"盐城\"},{\"code\":\"321000\",\"regionName\":\"扬州\"},{\"code\":\"321100\",\"regionName\":\"镇江\"},{\"code\":\"321200\",\"regionName\":\"泰州\"},{\"code\":\"321300\",\"regionName\":\"宿迁\"}]}]}]'),(4,'[{\"code\":\"31\",\"regionName\":\"华东地区\",\"regions\":[{\"code\":\"310000\",\"regionName\":\"上海\",\"regions\":[{\"code\":\"310100\",\"regionName\":\"上海\"}]},{\"code\":\"320000\",\"regionName\":\"江苏\",\"regions\":[{\"code\":\"320100\",\"regionName\":\"南京\"},{\"code\":\"320200\",\"regionName\":\"无锡\"},{\"code\":\"320300\",\"regionName\":\"徐州\"},{\"code\":\"320400\",\"regionName\":\"常州\"},{\"code\":\"320500\",\"regionName\":\"苏州\"},{\"code\":\"320600\",\"regionName\":\"南通\"},{\"code\":\"320700\",\"regionName\":\"连云港\"},{\"code\":\"320800\",\"regionName\":\"淮安\"},{\"code\":\"320900\",\"regionName\":\"盐城\"},{\"code\":\"321000\",\"regionName\":\"扬州\"},{\"code\":\"321100\",\"regionName\":\"镇江\"},{\"code\":\"321200\",\"regionName\":\"泰州\"},{\"code\":\"321300\",\"regionName\":\"宿迁\"}]},{\"code\":\"330000\",\"regionName\":\"浙江\",\"regions\":[{\"code\":\"330100\",\"regionName\":\"杭州\"},{\"code\":\"330200\",\"regionName\":\"宁波\"},{\"code\":\"330300\",\"regionName\":\"温州\"},{\"code\":\"330400\",\"regionName\":\"嘉兴\"},{\"code\":\"330500\",\"regionName\":\"湖州\"},{\"code\":\"330600\",\"regionName\":\"绍兴\"},{\"code\":\"330700\",\"regionName\":\"金华\"},{\"code\":\"330800\",\"regionName\":\"衢州\"},{\"code\":\"330900\",\"regionName\":\"舟山\"},{\"code\":\"331000\",\"regionName\":\"台州\"},{\"code\":\"331100\",\"regionName\":\"丽水\"}]},{\"code\":\"340000\",\"regionName\":\"安徽\",\"regions\":[{\"code\":\"340100\",\"regionName\":\"合肥\"},{\"code\":\"340200\",\"regionName\":\"芜湖\"},{\"code\":\"340300\",\"regionName\":\"蚌埠\"},{\"code\":\"340400\",\"regionName\":\"淮南\"},{\"code\":\"340500\",\"regionName\":\"马鞍山\"},{\"code\":\"340600\",\"regionName\":\"淮北\"},{\"code\":\"340700\",\"regionName\":\"铜陵\"},{\"code\":\"340800\",\"regionName\":\"安庆\"},{\"code\":\"341000\",\"regionName\":\"黄山\"},{\"code\":\"341100\",\"regionName\":\"滁州\"},{\"code\":\"341200\",\"regionName\":\"阜阳\"},{\"code\":\"341300\",\"regionName\":\"宿州\"},{\"code\":\"341500\",\"regionName\":\"六安\"},{\"code\":\"341600\",\"regionName\":\"亳州\"},{\"code\":\"341700\",\"regionName\":\"池州\"},{\"code\":\"341800\",\"regionName\":\"宣城\"}]},{\"code\":\"350000\",\"regionName\":\"福建\",\"regions\":[{\"code\":\"350100\",\"regionName\":\"福州\"},{\"code\":\"350200\",\"regionName\":\"厦门\"},{\"code\":\"350300\",\"regionName\":\"莆田\"},{\"code\":\"350400\",\"regionName\":\"三明\"},{\"code\":\"350500\",\"regionName\":\"泉州\"},{\"code\":\"350600\",\"regionName\":\"漳州\"},{\"code\":\"350700\",\"regionName\":\"南平\"},{\"code\":\"350800\",\"regionName\":\"龙岩\"},{\"code\":\"350900\",\"regionName\":\"宁德\"}]},{\"code\":\"370000\",\"regionName\":\"山东\",\"regions\":[{\"code\":\"370100\",\"regionName\":\"济南\"},{\"code\":\"370200\",\"regionName\":\"青岛\"},{\"code\":\"370300\",\"regionName\":\"淄博\"},{\"code\":\"370400\",\"regionName\":\"枣庄\"},{\"code\":\"370500\",\"regionName\":\"东营\"},{\"code\":\"370600\",\"regionName\":\"烟台\"},{\"code\":\"370700\",\"regionName\":\"潍坊\"},{\"code\":\"370800\",\"regionName\":\"济宁\"},{\"code\":\"370900\",\"regionName\":\"泰安\"},{\"code\":\"371000\",\"regionName\":\"威海\"},{\"code\":\"371100\",\"regionName\":\"日照\"},{\"code\":\"371200\",\"regionName\":\"莱芜\"},{\"code\":\"371300\",\"regionName\":\"临沂\"},{\"code\":\"371400\",\"regionName\":\"德州\"},{\"code\":\"371500\",\"regionName\":\"聊城\"},{\"code\":\"371600\",\"regionName\":\"滨州\"},{\"code\":\"371700\",\"regionName\":\"菏泽\"}]}]},{\"code\":\"11\",\"regionName\":\"华北地区\",\"regions\":[{\"code\":\"110000\",\"regionName\":\"北京\",\"regions\":[{\"code\":\"110100\",\"regionName\":\"北京\"}]},{\"code\":\"120000\",\"regionName\":\"天津\",\"regions\":[{\"code\":\"120100\",\"regionName\":\"天津\"}]},{\"code\":\"130000\",\"regionName\":\"河北\",\"regions\":[{\"code\":\"130100\",\"regionName\":\"石家庄\"},{\"code\":\"130200\",\"regionName\":\"唐山\"},{\"code\":\"130300\",\"regionName\":\"秦皇岛\"},{\"code\":\"130400\",\"regionName\":\"邯郸\"},{\"code\":\"130500\",\"regionName\":\"邢台\"},{\"code\":\"130600\",\"regionName\":\"保定\"},{\"code\":\"130700\",\"regionName\":\"张家口\"},{\"code\":\"130800\",\"regionName\":\"承德\"},{\"code\":\"130900\",\"regionName\":\"沧州\"},{\"code\":\"131000\",\"regionName\":\"廊坊\"},{\"code\":\"131100\",\"regionName\":\"衡水\"}]},{\"code\":\"140000\",\"regionName\":\"山西\",\"regions\":[{\"code\":\"140100\",\"regionName\":\"太原\"},{\"code\":\"140200\",\"regionName\":\"大同\"},{\"code\":\"140300\",\"regionName\":\"阳泉\"},{\"code\":\"140400\",\"regionName\":\"长治\"},{\"code\":\"140500\",\"regionName\":\"晋城\"},{\"code\":\"140600\",\"regionName\":\"朔州\"},{\"code\":\"140700\",\"regionName\":\"晋中\"},{\"code\":\"140800\",\"regionName\":\"运城\"},{\"code\":\"140900\",\"regionName\":\"忻州\"},{\"code\":\"141000\",\"regionName\":\"临汾\"},{\"code\":\"141100\",\"regionName\":\"吕梁\"}]},{\"code\":\"150000\",\"regionName\":\"内蒙古\",\"regions\":[{\"code\":\"150100\",\"regionName\":\"呼和浩特\"},{\"code\":\"150200\",\"regionName\":\"包头\"},{\"code\":\"150300\",\"regionName\":\"乌海\"},{\"code\":\"150400\",\"regionName\":\"赤峰\"},{\"code\":\"150500\",\"regionName\":\"通辽\"},{\"code\":\"150600\",\"regionName\":\"鄂尔多斯\"},{\"code\":\"150700\",\"regionName\":\"呼伦贝尔\"},{\"code\":\"150800\",\"regionName\":\"巴彦淖尔\"},{\"code\":\"150900\",\"regionName\":\"乌兰察布\"},{\"code\":\"152200\",\"regionName\":\"兴安盟\"},{\"code\":\"152500\",\"regionName\":\"锡林郭勒盟\"},{\"code\":\"152900\",\"regionName\":\"阿拉善盟\"}]}]}]');

/*Table structure for table `static_page_info` */

DROP TABLE IF EXISTS `static_page_info`;

CREATE TABLE `static_page_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(100) DEFAULT NULL COMMENT '静态页名称',
  `link_url` varchar(100) DEFAULT NULL COMMENT '链接地址',
  `description` varchar(500) DEFAULT NULL COMMENT '描述',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(19) DEFAULT NULL COMMENT '创建人id',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  `update_user_id` varchar(19) DEFAULT NULL COMMENT '修改人id',
  `page_content` text COMMENT '编辑器编辑的静态页内容',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='静态页管理表';

/*Data for the table `static_page_info` */

insert  into `static_page_info`(`id`,`name`,`link_url`,`description`,`create_time`,`create_user_id`,`update_time`,`update_user_id`,`page_content`) values (1,'上线','/sc/html/2146436444.html','上线','2017-08-08 16:00:59',NULL,'2017-08-19 17:10:33','11775','<p><img alt=\"\" src=\"http://sit.image.com/group1/M00/01/38/rB4KPlmW0z2ABKGpAA1YcBiKPvU294.png\" /></p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<p>我的这个东西是乱码吗？告诉我答案</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<p><img alt=\"\" src=\"http://sit.image.com/group1/M00/01/39/rB4KPFmXnyWALbVoAA1YcBiKPvU073.png\" style=\"height:1920px; width:1080px\" /></p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n'),(9,'哈哈哈',NULL,'哈安庆市多','2017-08-21 10:07:12','10027','2017-08-22 14:41:59','10027',NULL),(10,'手动返萨达',NULL,'暗室逢灯三方','2017-08-29 09:54:08','10027',NULL,NULL,NULL),(11,'hdym','/sc/html/-2028414320.html','hhhh','2017-09-07 16:35:13','10027','2018-01-05 17:44:47','10027','<p>222222222222222222222222222222222222222222222222222222222<img alt=\"\" src=\"http://sit.image.com/group2/M00/00/29/rB4KPVnN5RiAF1wWAAA3e0QhChw494.png\" style=\"height:300px; width:533px\" /></p>\n');

/*Table structure for table `store_permission` */

DROP TABLE IF EXISTS `store_permission`;

CREATE TABLE `store_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '表 id',
  `franchisee_id` varchar(255) DEFAULT NULL COMMENT '加盟商id',
  `store_id` varchar(255) DEFAULT NULL COMMENT '门店id',
  `accessibled` int(2) DEFAULT NULL COMMENT '0-可进入 1-不可进入',
  PRIMARY KEY (`id`),
  UNIQUE KEY `store_id` (`store_id`)
) ENGINE=MyISAM AUTO_INCREMENT=211 DEFAULT CHARSET=utf8;

/*Data for the table `store_permission` */

insert  into `store_permission`(`id`,`franchisee_id`,`store_id`,`accessibled`) values (12,NULL,'A000521',0),(13,NULL,'A002916',0),(14,NULL,'A001934',0),(15,NULL,'A000811',0),(16,NULL,'A000427',0),(17,NULL,'A003878',0),(18,NULL,'Z000999',0),(19,NULL,'A002262',0),(20,NULL,'A003583',0),(21,NULL,'A900003',0),(22,NULL,'A017203',0),(23,NULL,'A900005',0),(26,NULL,'A901004',0),(27,NULL,'A901114 ',0),(28,NULL,'A901204',0),(29,NULL,'A003012',0),(30,NULL,'A009629',0),(31,NULL,'A023595',0),(32,NULL,'A009351',0),(33,NULL,'A008519',0),(34,NULL,'A016870',0),(35,NULL,'A012321',0),(36,NULL,'A017391',0),(37,NULL,'A018240',0),(38,NULL,'A024212',0),(39,NULL,'A010372',0),(40,NULL,'A000256',0),(41,NULL,'A011772',0),(42,NULL,'A007393',0),(43,NULL,'A019812',0),(44,NULL,'A009109',0),(45,NULL,'A022801',0),(46,NULL,'A000161',0),(47,NULL,'A008416',0),(48,NULL,'A005368',0),(49,NULL,'A020809',0),(50,NULL,'A007738',0),(51,NULL,'A018212',0),(52,NULL,'A022482',0),(53,NULL,'A010328',0),(54,NULL,'A016369',0),(55,NULL,'A010212',0),(56,NULL,'A011583',0),(57,NULL,'A001738',0),(58,NULL,'A006780',0),(59,NULL,'A022423',0),(60,NULL,'A019961',0),(61,NULL,'A007529',0),(62,NULL,'A016370',0),(63,NULL,'A011517',0),(64,NULL,'A001239',0),(65,NULL,'A004905',0),(66,NULL,'A001052',0),(67,NULL,'A011773',0),(68,NULL,'A000202',0),(69,NULL,'A009825',0),(70,NULL,'A000086',0),(71,NULL,'A004697',0),(72,NULL,'A001167',0),(73,NULL,'A000838',0),(74,NULL,'A018102',0),(75,NULL,'A020269',0),(76,NULL,'A002429',0),(77,NULL,'A019691',0),(78,NULL,'A019269',0),(79,NULL,'A009570',0),(80,NULL,'A016840',0),(81,NULL,'A019993',0),(82,NULL,'A024671',0),(83,NULL,'A005730',0),(84,NULL,'A002515',0),(85,NULL,'A007331',0),(86,NULL,'A001702',0),(87,NULL,'A007186',0),(88,NULL,'A005570',0),(89,NULL,'A002969',0),(90,NULL,'A009813',0),(91,NULL,'A003593',0),(92,NULL,'A005808',0),(93,NULL,'A005672',0),(94,NULL,'A012711',0),(95,NULL,'A005145',0),(96,NULL,'A016122',0),(97,NULL,'A002886',0),(98,NULL,'A001237',0),(99,NULL,'A000926',0),(100,NULL,'A007537',0),(101,NULL,'A001388',0),(102,NULL,'A018280',0),(103,NULL,'A000215',0),(104,NULL,'A005939',0),(105,NULL,'A002519',0),(106,NULL,'A000425',0),(107,NULL,'A020792',0),(108,NULL,'A004567',0),(109,NULL,'A002867',0),(110,NULL,'A012822',0),(111,NULL,'A019779',0),(112,NULL,'A019889',0),(113,NULL,'A023899',0),(114,NULL,'A001036',0),(115,NULL,'A007033',0),(116,NULL,'A007665',0),(117,NULL,'A009830',0),(118,NULL,'A008703',0),(119,NULL,'A018151',0),(120,NULL,'A012889',0),(121,NULL,'A016356',0),(122,NULL,'A009281',0),(123,NULL,'A007660',0),(124,NULL,'A006843',0),(125,NULL,'A000977',0),(126,NULL,'A003732',0),(127,NULL,'A007176',0),(128,NULL,'A011757',0),(129,NULL,'A000001',0),(130,NULL,'A000069',0),(131,NULL,'A000092',0),(132,NULL,'A000159',0),(133,NULL,'A000176',0),(134,NULL,'A000236',0),(135,NULL,'A000283',0),(136,NULL,'A000286',0),(137,NULL,'A000311',0),(138,NULL,'A000333',0),(139,NULL,'A000351',0),(140,NULL,'A000352',0),(141,NULL,'A000355',0),(142,NULL,'A000356',0),(143,NULL,'A000368',0),(144,NULL,'A000396',0),(145,NULL,'A000528',0),(146,NULL,'A000541',0),(147,NULL,'A000543',0),(148,NULL,'A000788',0),(149,NULL,'A000920',0),(150,NULL,'A000998',0),(151,NULL,'A001199',0),(152,NULL,'A001351',0),(153,NULL,'A001354',0),(154,NULL,'A001431',0),(155,NULL,'A001461',0),(156,NULL,'A001503',0),(157,NULL,'A001645',0),(158,NULL,'A001652',0),(159,NULL,'A001694',0),(160,NULL,'A002040',0),(161,NULL,'A002060',0),(162,NULL,'A002201',0),(163,NULL,'A002202',0),(164,NULL,'A002360',0),(165,NULL,'A002819',0),(166,NULL,'A003476',0),(167,NULL,'A003661',0),(168,NULL,'A004519',0),(169,NULL,'A004527',0),(170,NULL,'A005391',0),(171,NULL,'A005449',0),(172,NULL,'A005450',0),(173,NULL,'A005577',0),(174,NULL,'A005845',0),(175,NULL,'A006101',0),(176,NULL,'A006150',0),(177,NULL,'A006549',0),(178,NULL,'A006553',0),(179,NULL,'A006558',0),(180,NULL,'A006666',0),(181,NULL,'A006686',0),(182,NULL,'A006701',0),(183,NULL,'A006705',0),(184,NULL,'A007049',0),(185,NULL,'A007167',0),(186,NULL,'A007191',0),(187,NULL,'A007288',0),(188,NULL,'A007572',0),(189,NULL,'A007655',0),(190,NULL,'A007711',0),(191,NULL,'A008151',0),(192,NULL,'A008535',0),(193,NULL,'A009371',0),(194,NULL,'A010545',0),(195,NULL,'A010782',0),(196,NULL,'A011161',0),(197,NULL,'A011823',0),(198,NULL,'A012166',0),(199,NULL,'A012922',0),(200,NULL,'A016443',0),(201,NULL,'A020313',0),(202,NULL,'A001704',0),(203,NULL,'A002974',0),(204,NULL,'A028001',0),(205,NULL,'A028008',0),(206,NULL,'A028002',0),(207,NULL,'A028057',0),(208,NULL,'A000122',0);

/*Table structure for table `supplier_adr_info` */

DROP TABLE IF EXISTS `supplier_adr_info`;

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
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_user` varchar(20) DEFAULT NULL,
  `modify_user` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=328 DEFAULT CHARSET=utf8 COMMENT='供应商地点表';

/*Data for the table `supplier_adr_info` */

insert  into `supplier_adr_info`(`id`,`parent_id`,`basic_id`,`cont_id`,`status`,`failed_reason`,`audit_user_id`,`audit_time`,`create_time`,`modify_time`,`create_user`,`modify_user`) values (1,'xprov001',1,1,4,NULL,'10018','2017-09-01 16:23:20','2017-08-15 19:43:45','2017-08-15 19:43:45','admin','admin'),(2,'xprov002',2,2,0,NULL,NULL,NULL,'2017-08-15 19:43:45','2017-08-15 19:43:45','admin','admin'),(3,'xprov003',554,558,2,NULL,'10027','2018-01-25 11:20:29','2017-08-15 19:43:45','2018-01-19 16:21:46','admin','admin'),(4,'xprov004',4,4,2,NULL,NULL,NULL,'2017-08-15 19:43:45','2018-01-23 16:24:43','admin','min'),(5,'xprov005',5,5,0,NULL,NULL,NULL,'2017-08-15 19:43:45','2017-08-15 19:43:45','admin','admin'),(6,'xprov006',6,6,0,NULL,NULL,NULL,'2017-08-15 19:43:45','2017-08-15 19:43:45','admin','admin'),(7,'xprov007',7,7,0,NULL,NULL,NULL,'2017-08-15 19:43:45','2017-08-15 19:43:45','admin','admin'),(8,'xprov008',202,205,2,NULL,'10027','2017-09-27 18:21:56','2017-08-15 19:43:45','2017-08-15 19:43:45','admin','admin'),(9,'xprov009',548,552,2,NULL,'11753','2018-01-08 18:17:20','2017-08-15 19:43:45','2017-08-15 19:43:45','admin','zhoujianying'),(10,'xprov010',10,10,0,NULL,NULL,NULL,'2017-08-15 19:43:45','2017-08-15 19:43:45','admin','admin'),(11,'xprov011',165,168,4,NULL,'10018','2017-09-07 09:48:17','2017-08-15 19:43:45','2017-08-15 19:43:45','admin','yaomuming'),(12,'xprov012',12,12,0,NULL,NULL,NULL,'2017-08-15 19:43:45','2017-08-15 19:43:45','admin','admin'),(13,'xprov013',13,13,1,NULL,NULL,NULL,'2017-08-15 19:43:45','2017-08-15 19:43:45','admin','admin'),(14,'xprov014',14,14,0,NULL,NULL,NULL,'2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin'),(15,'xprov015',15,15,0,NULL,NULL,NULL,'2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin'),(16,'xprov016',111,111,4,NULL,'10018','2017-09-04 16:29:19','2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin'),(17,'xprov017',17,17,0,NULL,NULL,NULL,'2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin'),(18,'xprov018',18,18,1,NULL,NULL,NULL,'2017-08-15 19:43:46','2017-08-15 19:43:46','admin','yaomuming'),(19,'xprov019',170,173,4,NULL,'10018','2017-09-04 16:34:42','2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin'),(20,'xprov020',20,20,0,NULL,NULL,NULL,'2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin'),(21,'xprov021',21,21,0,NULL,NULL,NULL,'2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin'),(22,'xprov022',142,142,4,NULL,'10018','2017-08-22 17:57:42','2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin'),(23,'xprov023',166,169,4,NULL,'10018','2017-09-05 09:46:33','2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin'),(24,'xprov024',24,24,0,NULL,NULL,NULL,'2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin'),(25,'xprov025',25,25,0,NULL,NULL,NULL,'2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin'),(26,'xprov026',171,174,4,NULL,'10018','2017-09-05 09:17:02','2017-08-15 19:43:46','2017-08-15 19:43:46','admin','yaomuming'),(27,'xprov027',27,27,0,NULL,NULL,NULL,'2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin'),(28,'xprov028',28,28,0,NULL,NULL,NULL,'2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin'),(29,'xprov029',29,29,0,NULL,NULL,NULL,'2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin'),(30,'xprov030',30,30,4,NULL,NULL,NULL,'2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin'),(31,'xprov031',31,31,0,NULL,NULL,NULL,'2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin'),(32,'xprov032',32,32,0,NULL,NULL,NULL,'2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin'),(33,'xprov033',33,33,0,NULL,NULL,NULL,'2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin'),(34,'xprov034',34,34,4,NULL,'10018','2017-12-01 14:08:04','2017-08-15 19:43:46','2018-01-08 17:25:28','admin','yaomuming'),(35,'xprov035',173,176,4,NULL,'10018','2017-09-05 09:35:56','2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin'),(36,'xprov036',36,36,0,NULL,NULL,NULL,'2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin'),(37,'xprov037',37,37,1,NULL,'10018','2017-09-04 13:58:15','2017-08-15 19:43:46','2017-08-15 19:43:46','admin','yaomuming'),(38,'xprov038',38,38,0,NULL,NULL,NULL,'2017-08-15 19:43:46','2017-08-15 19:43:46','admin','admin');

/*Table structure for table `supplier_info` */

DROP TABLE IF EXISTS `supplier_info`;

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
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_user` varchar(20) DEFAULT NULL,
  `modify_user` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='供应商表';

/*Data for the table `supplier_info` */

insert  into `supplier_info`(`id`,`basic_id`,`operat_taxat_id`,`bank_id`,`license_id`,`sr_id`,`status`,`failed_reason`,`audit_user_id`,`audit_time`,`create_time`,`modify_time`,`create_user`,`modify_user`) values ('xprov001',321,322,321,321,1,2,NULL,'10018','2017-09-09 14:41:28','2017-08-15 19:41:19','2017-08-15 19:41:19','admin','yaomuming'),('xprov002',2,2,2,2,2,1,NULL,NULL,NULL,'2017-08-15 19:41:19','2017-08-15 19:41:19','admin','admin'),('xprov003',3,3,3,3,3,2,NULL,NULL,NULL,'2017-08-15 19:41:19','2018-01-19 16:23:58','admin','yaomuming'),('xprov004',4,4,4,4,4,2,'qweqw','10027','2017-11-24 14:59:39','2017-08-15 19:41:19','2018-01-23 17:00:02','admin','yaomuming'),('xprov005',5,5,5,5,5,1,NULL,NULL,NULL,'2017-08-15 19:41:19','2017-08-15 19:41:19','admin','yaomuming'),('xprov006',6,6,6,6,6,1,NULL,NULL,NULL,'2017-08-15 19:41:19','2017-08-15 19:41:19','admin','yaomuming'),('xprov007',7,7,7,7,7,1,NULL,NULL,NULL,'2017-08-15 19:41:19','2017-08-15 19:41:19','admin','yaomuming'),('xprov008',388,389,388,388,8,2,'东莞','10027','2017-09-27 18:20:24','2017-08-15 19:41:19','2017-08-15 19:41:19','admin','admin'),('xprov009',318,319,318,318,9,2,'阿萨德','10018','2017-12-01 15:26:29','2017-08-15 19:41:19','2017-08-15 19:41:19','admin','yaomuming'),('xprov010',213,213,213,213,10,1,NULL,'10027','2017-09-04 17:04:29','2017-08-15 19:41:19','2017-08-15 19:41:19','admin','admin'),('xprov011',299,300,299,299,11,1,NULL,'10018','2017-09-07 09:35:29','2017-08-15 19:41:19','2017-08-15 19:41:19','admin','yaomuming'),('xprov012',12,12,12,12,12,1,NULL,NULL,NULL,'2017-08-15 19:41:19','2017-08-15 19:41:19','admin','admin'),('xprov013',13,13,13,13,43,1,NULL,NULL,NULL,'2017-08-15 19:41:19','2017-08-15 19:41:19','admin','admin'),('xprov014',14,14,14,14,14,1,NULL,NULL,NULL,'2017-08-15 19:41:19','2017-08-15 19:41:19','admin','admin'),('xprov015',15,15,15,15,44,1,NULL,NULL,NULL,'2017-08-15 19:41:19','2017-08-15 19:41:19','admin','yaomuming'),('xprov016',285,286,285,285,45,2,NULL,'10027','2017-09-05 19:00:42','2017-08-15 19:41:19','2017-08-15 19:41:19','admin','admin'),('xprov017',227,227,227,227,46,1,NULL,'10027','2017-09-04 17:04:39','2017-08-15 19:41:19','2017-08-15 19:41:19','admin','yaomuming'),('xprov018',18,18,18,18,47,1,NULL,NULL,NULL,'2017-08-15 19:41:19','2017-08-15 19:41:19','admin','yaomuming'),('xprov019',294,295,294,294,19,1,NULL,'10018','2017-09-07 09:48:27','2017-08-15 19:41:19','2017-08-15 19:41:19','admin','yaomuming'),('xprov020',20,20,20,20,20,1,NULL,NULL,NULL,'2017-08-15 19:41:19','2017-08-15 19:41:19','admin','yaomuming'),('xprov021',21,21,21,21,21,1,NULL,NULL,NULL,'2017-08-15 19:41:19','2017-08-15 19:41:19','admin','yaomuming'),('xprov022',226,226,226,226,22,1,NULL,'10027','2017-09-04 17:04:20','2017-08-15 19:41:19','2017-08-15 19:41:19','admin','yaomuming'),('xprov023',236,236,236,236,23,1,NULL,'10027','2017-09-04 17:04:25','2017-08-15 19:41:19','2017-08-15 19:41:19','admin','admin'),('xprov024',24,24,24,24,24,1,NULL,'10018','2017-09-06 09:53:37','2017-08-15 19:41:19','2017-08-15 19:41:19','admin','yaomuming'),('xprov025',25,25,25,25,25,2,NULL,'10018','2018-01-08 17:54:16','2017-08-15 19:41:19','2018-01-08 17:53:01','admin','yaomuming'),('xprov026',312,313,312,312,26,3,'adedeg','10018','2017-09-18 14:39:20','2017-08-15 19:41:19','2017-08-15 19:41:19','admin','admin'),('xprov027',27,27,27,27,27,1,NULL,NULL,NULL,'2017-08-15 19:41:19','2017-08-15 19:41:19','admin','yaomuming'),('xprov028',28,28,28,28,28,1,NULL,NULL,NULL,'2017-08-15 19:41:19','2017-08-15 19:41:19','admin','admin'),('xprov029',29,29,29,29,29,1,NULL,NULL,NULL,'2017-08-15 19:41:19','2017-08-15 19:41:19','admin','yaomuming'),('xprov030',30,30,30,30,30,4,NULL,NULL,NULL,'2017-08-15 19:41:19','2017-08-15 19:41:19','admin','admin'),('xprov031',31,31,31,31,31,1,NULL,'10018','2017-09-05 09:18:21','2017-08-15 19:41:19','2017-08-15 19:41:19','admin','yaomuming'),('xprov032',32,32,32,32,32,1,NULL,NULL,NULL,'2017-08-15 19:41:19','2017-08-15 19:41:19','admin','yaomuming'),('xprov033',33,33,33,33,33,1,NULL,NULL,NULL,'2017-08-15 19:41:19','2017-08-15 19:41:19','admin','yaomuming'),('xprov034',34,34,34,34,34,3,'测试测试','10018','2018-01-08 17:27:01','2017-08-15 19:41:19','2018-01-08 17:25:46','admin','yaomuming'),('xprov035',248,249,248,248,35,1,NULL,'10018','2017-09-05 09:34:13','2017-08-15 19:41:19','2017-08-15 19:41:19','admin','admin'),('xprov036',36,36,36,36,36,1,NULL,NULL,NULL,'2017-08-15 19:41:19','2017-08-15 19:41:19','admin','yaomuming'),('xprov037',214,214,214,214,37,1,NULL,'10027','2017-09-04 17:04:34','2017-08-15 19:41:19','2017-08-15 19:41:19','admin','admin'),('xprov038',38,38,38,38,38,1,NULL,NULL,NULL,'2017-08-15 19:41:19','2017-08-15 19:41:19','admin','admin'),('xprov039',39,39,39,39,39,1,NULL,NULL,NULL,'2017-08-15 19:41:19','2017-08-15 19:41:19','admin','admin'),('xprov040',40,40,40,40,40,2,NULL,'10018','2017-09-09 14:41:17','2017-08-15 19:41:20','2017-08-15 19:41:20','admin','yaomuming'),('xprov041',41,41,41,41,41,0,NULL,NULL,NULL,'2017-08-15 19:41:20','2017-08-15 19:41:20','admin','admin'),('xprov042',42,42,42,42,42,1,NULL,NULL,NULL,'2017-08-15 19:41:20','2017-08-15 19:41:20','admin','yaomuming'),('xprov1031',406,407,406,406,148,0,NULL,NULL,NULL,'2017-10-18 14:51:31','2017-10-18 14:51:31','admin',NULL);

/*Table structure for table `supplier_settled` */

DROP TABLE IF EXISTS `supplier_settled`;

CREATE TABLE `supplier_settled` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增ID',
  `purchase_receipt_no` varchar(255) DEFAULT NULL COMMENT '收货单号',
  `asn` varchar(255) DEFAULT NULL COMMENT 'ASN号',
  `purchase_order_no` varchar(255) DEFAULT NULL COMMENT '采购单号',
  `sp_no` varchar(255) DEFAULT NULL COMMENT '供应商编号',
  `sp_name` varchar(255) DEFAULT NULL COMMENT '供应商名称',
  `sp_adr_no` varchar(255) DEFAULT NULL COMMENT '供应商地点编号',
  `branch_company_code` varchar(255) DEFAULT NULL COMMENT '子公司编码',
  `branch_company_name` varchar(255) DEFAULT NULL COMMENT '子公司名称',
  `adr_type` int(2) DEFAULT NULL COMMENT '地点类型（0:仓库;1:门店）',
  `adr_type_code` varchar(255) DEFAULT NULL COMMENT '地点编码',
  `adr_type_name` varchar(255) DEFAULT NULL COMMENT '地点名称',
  `settlement_period` int(11) DEFAULT NULL COMMENT '账期 (0:周结；1：半月结；2：月结；)',
  `pay_type` int(11) DEFAULT NULL COMMENT '付款方式 (0：网银，1：银行转账，2：现金，3：支票)',
  `groups` varchar(40) DEFAULT NULL COMMENT '部类',
  `groups_desc` varchar(255) DEFAULT NULL COMMENT '部类描述',
  `dept` varchar(40) DEFAULT NULL COMMENT '大类',
  `dept_desc` varchar(255) DEFAULT NULL COMMENT '大类描述',
  `product_code` varchar(20) DEFAULT NULL COMMENT '商品编号',
  `product_name` varchar(255) DEFAULT NULL COMMENT '商品名称',
  `input_tax_rate` decimal(4,2) DEFAULT NULL COMMENT '税率',
  `received_time` datetime DEFAULT NULL COMMENT '收货日期',
  `received_number` int(20) DEFAULT NULL COMMENT '已收货数量',
  `received_money_without_tax` decimal(20,2) DEFAULT NULL COMMENT '收货金额（未税)',
  `received_money_with_tax` decimal(20,2) DEFAULT NULL COMMENT '收货金额 （含税）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=161 DEFAULT CHARSET=utf8 COMMENT='供应商结算数据逻辑描述';

/*Data for the table `supplier_settled` */

insert  into `supplier_settled`(`id`,`purchase_receipt_no`,`asn`,`purchase_order_no`,`sp_no`,`sp_name`,`sp_adr_no`,`branch_company_code`,`branch_company_name`,`adr_type`,`adr_type_code`,`adr_type_name`,`settlement_period`,`pay_type`,`groups`,`groups_desc`,`dept`,`dept_desc`,`product_code`,`product_name`,`input_tax_rate`,`received_time`,`received_number`,`received_money_without_tax`,`received_money_with_tax`) values (1,'1708290011',NULL,'1708290016','10007','四川福仁缘农业开发有限公司','1000008','10000','成都子公司',0,'YT901','测试',2,1,'xcate4','生鲜日配','xcate26','面包主食','1000404','桃李吉士排面包(110g)','17.00','2017-08-29 16:41:20',1000,'3320.00','4000.00'),(2,'1708290012',NULL,'1708290017','10008','湖南福仁缘农业开发有限公司','1000009','10003','湖南子公司',1,'YT902','测试1',0,1,'xcate4','生鲜日配','xcate26','面包主食','1000404','桃李吉士排面包(110g)','17.00','2017-08-31 16:41:20',1000,'3320.00','4000.00'),(3,'1708290013',NULL,'1708290018','10009','广东福仁缘农业开发有限公司','1000010','10004','广东子公司',1,'YT902','测试1',1,1,'xcate4','生鲜日配','xcate26','面包主食','1000404','桃李吉士排面包(110g)','17.00','2017-09-01 21:41:20',1000,'3320.00','4000.00'),(4,'1708290014',NULL,'1708290019','10010','重庆福仁缘农业开发有限公司','1000011','10005','重庆子公司',1,'YT902','测试1',2,1,'xcate4','生鲜日配','xcate26','面包主食','1000404','桃李吉士排面包(110g)','17.00','2017-09-05 16:41:20',1000,'3320.00','4000.00'),(5,'1709090002',NULL,'1708290019','10007','四川福仁缘农业开发有限公司','1000008','10000','成都子公司',0,'901200411','成都仓四川子公司常温正常仓',2,1,'xcate2','休闲食品','xcate12','糖果','1003467','其辉3合1鱼底料300g','1.00','2017-09-09 13:49:04',3,'0.00','0.00'),(6,'1709150001',NULL,'1709150003','10007','四川福仁缘农业开发有限公司','1000008','10000','成都子公司',0,'901200411','成都仓四川子公司常温正常仓',2,1,'xcate3','粮油副食','xcate18','酱菜类','1004846','康师傅新酸萝卜老鸭汤五入109g*5','1.00','2017-09-15 14:09:30',1,'4.95','5.00'),(7,'1709150002',NULL,'1709150004','10007','四川福仁缘农业开发有限公司','1000008','10000','成都子公司',0,'901200411','成都仓四川子公司常温正常仓',2,1,'xcate3','粮油副食','xcate18','酱菜类','1004846','康师傅新酸萝卜老鸭汤五入109g*5','1.00','2017-09-15 14:16:22',1,'4.95','5.00'),(8,'1709150001',NULL,'1709150003','10007','四川福仁缘农业开发有限公司','1000008','10000','成都子公司',0,'901200411','成都仓四川子公司常温正常仓',2,1,'xcate3','粮油副食','xcate18','酱菜类','1004846','康师傅新酸萝卜老鸭汤五入109g*5','1.00','2017-09-15 14:09:30',1,'4.95','5.00'),(9,'1709150002',NULL,'1709150004','10007','四川福仁缘农业开发有限公司','1000008','10000','成都子公司',0,'901200411','成都仓四川子公司常温正常仓',2,1,'xcate3','粮油副食','xcate18','酱菜类','1004846','康师傅新酸萝卜老鸭汤五入109g*5','1.00','2017-09-15 14:16:22',1,'4.95','5.00'),(10,'1709150002',NULL,'1709150004','10007','四川福仁缘农业开发有限公司','1000008','10000','成都子公司',0,'901200411','成都仓四川子公司常温正常仓',2,1,'xcate3','粮油副食','xcate18','酱菜类','1004846','康师傅新酸萝卜老鸭汤五入109g*5','1.00','2017-09-15 14:16:22',1,'4.95','5.00'),(11,'1709150002',NULL,'1709150004','10007','四川福仁缘农业开发有限公司','1000008','10000','成都子公司',0,'901200411','成都仓四川子公司常温正常仓',2,1,'xcate3','粮油副食','xcate18','酱菜类','1004846','康师傅新酸萝卜老鸭汤五入109g*5','1.00','2017-09-15 14:16:22',1,'4.95','5.00');

/*Table structure for table `sys_dictionary` */

DROP TABLE IF EXISTS `sys_dictionary`;

CREATE TABLE `sys_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dictionary` varchar(50) DEFAULT NULL COMMENT '字典名称',
  `code` varchar(50) DEFAULT NULL COMMENT '字典编号',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `dictionaryType` int(4) DEFAULT '0' COMMENT '字典类型（0：业务级字典   1：系统级字段）',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='数据字典类型表';

/*Data for the table `sys_dictionary` */

insert  into `sys_dictionary`(`id`,`dictionary`,`code`,`remark`,`dictionaryType`) values (48,'12','11','1',0),(50,'1111111','1213','未',0),(51,'545','545454','54546455',0);

/*Table structure for table `sys_dictionary_content` */

DROP TABLE IF EXISTS `sys_dictionary_content`;

CREATE TABLE `sys_dictionary_content` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dictionary_id` int(11) DEFAULT NULL,
  `contentName` varchar(50) DEFAULT NULL COMMENT '内容名称',
  `contentCode` varchar(25) DEFAULT NULL COMMENT '内容值',
  `state` int(4) DEFAULT '1' COMMENT '状态:0停用，1启用，默认启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8 COMMENT='数据字典内容表';

/*Data for the table `sys_dictionary_content` */

insert  into `sys_dictionary_content`(`id`,`dictionary_id`,`contentName`,`contentCode`,`state`) values (74,48,'三快1211',NULL,1),(75,48,'sdf',NULL,1),(76,50,'JSAHFDSG',NULL,1),(77,48,'sdfsdfsdfsdf',NULL,1),(78,48,'sdf',NULL,1),(79,48,'asd',NULL,1),(80,48,'sadfsd',NULL,1);

/*Table structure for table `sys_home_area_ad` */

DROP TABLE IF EXISTS `sys_home_area_ad`;

CREATE TABLE `sys_home_area_ad` (
  `id` varchar(255) NOT NULL COMMENT '数据ID',
  `code` varchar(255) DEFAULT NULL COMMENT '区域代码',
  `name` varchar(255) NOT NULL COMMENT '区域名称',
  `sequence` double DEFAULT NULL COMMENT '排序序号',
  `is_enabled` tinyint(4) DEFAULT NULL COMMENT '是否启用',
  `area_type` int(11) DEFAULT NULL COMMENT 'area类型字段，1=banner；2=floor；3=carousel；4=quick-nav; 5=hot',
  `company_id` varchar(255) NOT NULL COMMENT '所属(子)公司id',
  `is_using_nation` tinyint(1) NOT NULL COMMENT '该区域是否使用全国区域',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首页配置区域表';

/*Data for the table `sys_home_area_ad` */

insert  into `sys_home_area_ad`(`id`,`code`,`name`,`sequence`,`is_enabled`,`area_type`,`company_id`,`is_using_nation`) values ('10000-banner-1',NULL,'横幅广告1',11,0,1,'10000',1),('10000-banner-2',NULL,'横幅广告2',12,0,1,'10000',1),('10000-banner-3',NULL,'横幅广告3',13,0,1,'10000',1),('10000-carousel',NULL,'轮播广告区',9,0,3,'10000',1),('10000-floor-1',NULL,'推荐管理楼层1',14,0,2,'10000',1),('10000-floor-2',NULL,'推荐管理楼层2',15,0,2,'10000',1),('10000-floor-3',NULL,'推荐管理楼层3',16,0,2,'10000',1),('10000-hot',NULL,'热门推荐',13.5,1,5,'10000',1),('10000-quick-nav',NULL,'快捷功能区',10,0,4,'10000',1),('100028-banner-1',NULL,'横幅广告1',29,0,1,'100028',1),('100028-banner-2',NULL,'横幅广告2',30,0,1,'100028',1),('100028-banner-3',NULL,'横幅广告3',31,0,1,'100028',1),('100028-carousel',NULL,'轮播广告区',27,0,3,'100028',1),('100028-floor-1',NULL,'推荐管理楼层1',32,0,2,'100028',1),('100028-floor-2',NULL,'推荐管理楼层2',33,0,2,'100028',1),('100028-floor-3',NULL,'推荐管理楼层3',34,0,2,'100028',1),('100028-hot',NULL,'热门推荐',35,0,5,'100028',1),('100028-quick-nav',NULL,'快捷功能区',28,0,4,'100028',1),('10003-banner-1',NULL,'横幅广告1',38,0,1,'10003',1),('10003-banner-2',NULL,'横幅广告2',39,0,1,'10003',1),('10003-banner-3',NULL,'横幅广告3',40,0,1,'10003',1),('10003-carousel',NULL,'轮播广告区',36,0,3,'10003',1),('10003-floor-1',NULL,'推荐管理楼层1',41,0,2,'10003',1),('10003-floor-2',NULL,'推荐管理楼层2',42,0,2,'10003',1),('10003-floor-3',NULL,'推荐管理楼层3',43,0,2,'10003',1),('10003-hot',NULL,'热门推荐',44,0,5,'10003',1),('10003-quick-nav',NULL,'快捷功能区',37,0,4,'10003',1),('10004-banner-1',NULL,'横幅广告1',20,0,1,'10004',1),('10004-banner-2',NULL,'横幅广告2',21,0,1,'10004',1),('10004-banner-3',NULL,'横幅广告3',22.75,1,1,'10004',1),('10004-carousel',NULL,'轮播广告区',18,0,3,'10004',1),('10004-floor-1',NULL,'推荐管理楼层1',23,0,2,'10004',1),('10004-floor-2',NULL,'推荐管理楼层2',24,0,2,'10004',1),('10004-floor-3',NULL,'推荐管理楼层3',25,0,2,'10004',1),('10004-hot',NULL,'热门推荐',22.5,1,5,'10004',1),('10004-quick-nav',NULL,'快捷功能区',19,0,4,'10004',1),('100067-banner-1',NULL,'横幅广告1',47,0,1,'100067',1),('100067-banner-2',NULL,'横幅广告2',48,0,1,'100067',1),('100067-banner-3',NULL,'横幅广告3',49,0,1,'100067',1),('100067-carousel',NULL,'轮播广告区',45,0,3,'100067',1),('100067-floor-1',NULL,'推荐管理楼层1',44.5,1,2,'100067',1),('100067-floor-2',NULL,'推荐管理楼层2',51,0,2,'100067',1),('100067-floor-3',NULL,'推荐管理楼层3',52,0,2,'100067',1),('100067-hot',NULL,'热门推荐',53,0,5,'100067',1),('100067-quick-nav',NULL,'快捷功能区',46.75,0,4,'100067',1),('banner-1',NULL,'横幅广告1',1.4875878569080214,1,1,'headquarters',1),('banner-2',NULL,'横幅广告2',7.132080078125,1,1,'headquarters',1),('banner-3',NULL,'横幅广告3',2.3378876745700836,1,1,'headquarters',1),('carousel',NULL,'轮播广告区',-0.8874121430919786,1,3,'headquarters',1),('floor-1',NULL,'推荐管理楼层1',6.632080078125,1,2,'headquarters',1),('floor-2',NULL,'推荐管理楼层2',1.9752377657390525,1,2,'headquarters',1),('floor-3',NULL,'推荐管理楼层3',1.6125878569080214,0,2,'headquarters',1),('quick-nav',NULL,'快捷功能区',1.3625878569080214,1,4,'headquarters',1);

/*Table structure for table `sys_home_carousel_ad` */

DROP TABLE IF EXISTS `sys_home_carousel_ad`;

CREATE TABLE `sys_home_carousel_ad` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `sorting` int(11) DEFAULT NULL COMMENT '序号',
  `link_type` int(2) DEFAULT NULL COMMENT '链接类型，1：详情链接，2：分类链接，3：列表链接，4：页面链接，5：外部链接，6：活动链接',
  `goods_id` varchar(20) DEFAULT NULL COMMENT '商品编号',
  `link_address` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `pic_address` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `status` int(2) DEFAULT NULL COMMENT '状态（0：停用，1：已启用）',
  `create_person` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_person` varchar(50) DEFAULT NULL COMMENT '修改人',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_update` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `area_id` varchar(255) NOT NULL COMMENT '所属areaID',
  `link_keyword` varchar(255) DEFAULT NULL COMMENT '链接关键字',
  `link_id` varchar(255) DEFAULT NULL COMMENT '链接ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=480 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='轮播广告';

/*Data for the table `sys_home_carousel_ad` */

insert  into `sys_home_carousel_ad`(`id`,`sorting`,`link_type`,`goods_id`,`link_address`,`pic_address`,`status`,`create_person`,`update_person`,`gmt_create`,`gmt_update`,`area_id`,`link_keyword`,`link_id`) values (471,3,2,'','','/group1/M00/01/3A/rB4KPFmY9l-ATc1ZAAolkl8f_PY614.png',1,'admin','admin','2017-12-05 17:52:38','2018-03-05 17:45:56','carousel','',''),(472,1,4,'','nianhuo/index.html','/group1/M00/01/B0/rB4KPlonUpKAEPAaAAMA0OsPZls236.jpg',1,'admin','admin','2017-12-06 10:13:52','2018-01-24 14:04:58','carousel','','');

/*Table structure for table `sys_home_carousel_param` */

DROP TABLE IF EXISTS `sys_home_carousel_param`;

CREATE TABLE `sys_home_carousel_param` (
  `id` int(2) NOT NULL AUTO_INCREMENT,
  `carousel_interval` int(2) DEFAULT NULL COMMENT '轮播间隔（秒）',
  `status` int(2) DEFAULT NULL COMMENT '状态（0：未设置间隔，1：当前设置间隔）',
  `area_id` varchar(255) NOT NULL COMMENT '所属areaID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8 COMMENT='轮播参数';

/*Data for the table `sys_home_carousel_param` */

insert  into `sys_home_carousel_param`(`id`,`carousel_interval`,`status`,`area_id`) values (1,6,1,'carousel'),(26,5,1,'10000-carousel'),(27,5,1,'10004-carousel'),(28,5,1,'100028-carousel'),(29,5,1,'10003-carousel'),(30,5,1,'100067-carousel');

/*Table structure for table `sys_home_item_ad` */

DROP TABLE IF EXISTS `sys_home_item_ad`;

CREATE TABLE `sys_home_item_ad` (
  `id` varchar(255) NOT NULL COMMENT '数据ID',
  `area_id` varchar(255) DEFAULT NULL COMMENT '区域ID',
  `name` varchar(255) NOT NULL COMMENT '广告位名称',
  `title` varchar(255) DEFAULT NULL COMMENT '标题',
  `sub_title` varchar(255) DEFAULT NULL COMMENT '副标题',
  `product_no` varchar(255) DEFAULT NULL COMMENT '链接',
  `url_type` int(11) DEFAULT NULL COMMENT '链接类型，1：详情链接，2：分类链接，3：列表链接，4：页面链接，5：外部链接，6：活动链接',
  `url` varchar(255) DEFAULT NULL COMMENT '链接',
  `icon` varchar(255) DEFAULT NULL COMMENT '广告图标',
  `ad_type` varchar(255) DEFAULT NULL COMMENT '广告类型',
  `product_class_code` varchar(255) DEFAULT NULL COMMENT '商品分类编码 ',
  `link_keyword` varchar(255) DEFAULT NULL COMMENT '链接关键字',
  `link_id` varchar(255) DEFAULT NULL COMMENT '链接ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='首页配置横幅广告表';

/*Data for the table `sys_home_item_ad` */

insert  into `sys_home_item_ad`(`id`,`area_id`,`name`,`title`,`sub_title`,`product_no`,`url_type`,`url`,`icon`,`ad_type`,`product_class_code`,`link_keyword`,`link_id`) values ('10000-banner-1','10000-banner-1','横幅广告1',NULL,NULL,NULL,NULL,NULL,NULL,'BANNER',NULL,NULL,NULL),('10000-banner-2','10000-banner-2','横幅广告2',NULL,NULL,NULL,NULL,NULL,NULL,'BANNER',NULL,NULL,NULL),('10000-banner-3','10000-banner-3','横幅广告3',NULL,NULL,NULL,NULL,NULL,NULL,'BANNER',NULL,NULL,NULL),('10000-floor-1-1','10000-floor-1','推荐管理楼层1-1号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-floor-1-2','10000-floor-1','推荐管理楼层1-2号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-floor-1-3','10000-floor-1','推荐管理楼层1-3号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-floor-1-4','10000-floor-1','推荐管理楼层1-4号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-floor-1-5','10000-floor-1','推荐管理楼层1-5号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-floor-1-6','10000-floor-1','推荐管理楼层1-6号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-floor-1-title','10000-floor-1','推荐管理楼层1-栏目标题',NULL,NULL,NULL,NULL,NULL,NULL,'COLUMN_TITLE',NULL,NULL,NULL),('10000-floor-2-1','10000-floor-2','推荐管理楼层2-1号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-floor-2-2','10000-floor-2','推荐管理楼层2-2号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-floor-2-3','10000-floor-2','推荐管理楼层2-3号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-floor-2-4','10000-floor-2','推荐管理楼层2-4号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-floor-2-5','10000-floor-2','推荐管理楼层2-5号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-floor-2-6','10000-floor-2','推荐管理楼层2-6号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-floor-2-title','10000-floor-2','推荐管理楼层2-栏目标题',NULL,NULL,NULL,NULL,NULL,NULL,'COLUMN_TITLE',NULL,NULL,NULL),('10000-floor-3-1','10000-floor-3','推荐管理楼层3-1号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-floor-3-2','10000-floor-3','推荐管理楼层3-2号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-floor-3-3','10000-floor-3','推荐管理楼层3-3号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-floor-3-4','10000-floor-3','推荐管理楼层3-4号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-floor-3-5','10000-floor-3','推荐管理楼层3-5号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-floor-3-6','10000-floor-3','推荐管理楼层3-6号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-floor-3-title','10000-floor-3','推荐管理楼层3-栏目标题',NULL,NULL,NULL,NULL,NULL,NULL,'COLUMN_TITLE',NULL,NULL,NULL),('10000-hot-1','10000-hot','热门推荐-1号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-hot-2','10000-hot','热门推荐-2号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-hot-3','10000-hot','热门推荐-3号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-hot-4','10000-hot','热门推荐-4号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-hot-5','10000-hot','热门推荐-5号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10000-hot-6','10000-hot','热门推荐-6号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-banner-1','100028-banner-1','横幅广告1',NULL,NULL,NULL,NULL,NULL,NULL,'BANNER',NULL,NULL,NULL),('100028-banner-2','100028-banner-2','横幅广告2',NULL,NULL,NULL,NULL,NULL,NULL,'BANNER',NULL,NULL,NULL),('100028-banner-3','100028-banner-3','横幅广告3',NULL,NULL,NULL,NULL,NULL,NULL,'BANNER',NULL,NULL,NULL),('100028-floor-1-1','100028-floor-1','推荐管理楼层1-1号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-floor-1-2','100028-floor-1','推荐管理楼层1-2号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-floor-1-3','100028-floor-1','推荐管理楼层1-3号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-floor-1-4','100028-floor-1','推荐管理楼层1-4号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-floor-1-5','100028-floor-1','推荐管理楼层1-5号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-floor-1-6','100028-floor-1','推荐管理楼层1-6号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-floor-1-title','100028-floor-1','推荐管理楼层1-栏目标题',NULL,NULL,NULL,NULL,NULL,NULL,'COLUMN_TITLE',NULL,NULL,NULL),('100028-floor-2-1','100028-floor-2','推荐管理楼层2-1号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-floor-2-2','100028-floor-2','推荐管理楼层2-2号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-floor-2-3','100028-floor-2','推荐管理楼层2-3号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-floor-2-4','100028-floor-2','推荐管理楼层2-4号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-floor-2-5','100028-floor-2','推荐管理楼层2-5号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-floor-2-6','100028-floor-2','推荐管理楼层2-6号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-floor-2-title','100028-floor-2','推荐管理楼层2-栏目标题',NULL,NULL,NULL,NULL,NULL,NULL,'COLUMN_TITLE',NULL,NULL,NULL),('100028-floor-3-1','100028-floor-3','推荐管理楼层3-1号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-floor-3-2','100028-floor-3','推荐管理楼层3-2号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-floor-3-3','100028-floor-3','推荐管理楼层3-3号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-floor-3-4','100028-floor-3','推荐管理楼层3-4号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-floor-3-5','100028-floor-3','推荐管理楼层3-5号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-floor-3-6','100028-floor-3','推荐管理楼层3-6号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-floor-3-title','100028-floor-3','推荐管理楼层3-栏目标题',NULL,NULL,NULL,NULL,NULL,NULL,'COLUMN_TITLE',NULL,NULL,NULL),('100028-hot-1','100028-hot','热门推荐-1号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-hot-2','100028-hot','热门推荐-2号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-hot-3','100028-hot','热门推荐-3号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-hot-4','100028-hot','热门推荐-4号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-hot-5','100028-hot','热门推荐-5号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100028-hot-6','100028-hot','热门推荐-6号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-banner-1','10003-banner-1','横幅广告1',NULL,NULL,NULL,NULL,NULL,NULL,'BANNER',NULL,NULL,NULL),('10003-banner-2','10003-banner-2','横幅广告2',NULL,NULL,NULL,NULL,NULL,NULL,'BANNER',NULL,NULL,NULL),('10003-banner-3','10003-banner-3','横幅广告3',NULL,NULL,NULL,NULL,NULL,NULL,'BANNER',NULL,NULL,NULL),('10003-floor-1-1','10003-floor-1','推荐管理楼层1-1号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-floor-1-2','10003-floor-1','推荐管理楼层1-2号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-floor-1-3','10003-floor-1','推荐管理楼层1-3号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-floor-1-4','10003-floor-1','推荐管理楼层1-4号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-floor-1-5','10003-floor-1','推荐管理楼层1-5号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-floor-1-6','10003-floor-1','推荐管理楼层1-6号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-floor-1-title','10003-floor-1','推荐管理楼层1-栏目标题',NULL,NULL,NULL,NULL,NULL,NULL,'COLUMN_TITLE',NULL,NULL,NULL),('10003-floor-2-1','10003-floor-2','推荐管理楼层2-1号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-floor-2-2','10003-floor-2','推荐管理楼层2-2号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-floor-2-3','10003-floor-2','推荐管理楼层2-3号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-floor-2-4','10003-floor-2','推荐管理楼层2-4号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-floor-2-5','10003-floor-2','推荐管理楼层2-5号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-floor-2-6','10003-floor-2','推荐管理楼层2-6号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-floor-2-title','10003-floor-2','推荐管理楼层2-栏目标题',NULL,NULL,NULL,NULL,NULL,NULL,'COLUMN_TITLE',NULL,NULL,NULL),('10003-floor-3-1','10003-floor-3','推荐管理楼层3-1号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-floor-3-2','10003-floor-3','推荐管理楼层3-2号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-floor-3-3','10003-floor-3','推荐管理楼层3-3号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-floor-3-4','10003-floor-3','推荐管理楼层3-4号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-floor-3-5','10003-floor-3','推荐管理楼层3-5号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-floor-3-6','10003-floor-3','推荐管理楼层3-6号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-floor-3-title','10003-floor-3','推荐管理楼层3-栏目标题',NULL,NULL,NULL,NULL,NULL,NULL,'COLUMN_TITLE',NULL,NULL,NULL),('10003-hot-1','10003-hot','热门推荐-1号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-hot-2','10003-hot','热门推荐-2号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-hot-3','10003-hot','热门推荐-3号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-hot-4','10003-hot','热门推荐-4号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-hot-5','10003-hot','热门推荐-5号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10003-hot-6','10003-hot','热门推荐-6号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-banner-1','10004-banner-1','横幅广告1',NULL,NULL,'329',1,'','/group1/M00/01/BA/rB4KPVpNiSyAaomZAAGBcTD_bzY343.png','BANNER',NULL,'',''),('10004-banner-2','10004-banner-2','横幅广告2',NULL,NULL,NULL,NULL,NULL,NULL,'BANNER',NULL,NULL,NULL),('10004-banner-3','10004-banner-3','横幅广告3',NULL,NULL,NULL,NULL,NULL,NULL,'BANNER',NULL,NULL,NULL),('10004-floor-1-1','10004-floor-1','推荐管理楼层1-1号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-floor-1-2','10004-floor-1','推荐管理楼层1-2号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-floor-1-3','10004-floor-1','推荐管理楼层1-3号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-floor-1-4','10004-floor-1','推荐管理楼层1-4号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-floor-1-5','10004-floor-1','推荐管理楼层1-5号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-floor-1-6','10004-floor-1','推荐管理楼层1-6号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-floor-1-title','10004-floor-1','推荐管理楼层1-栏目标题',NULL,NULL,NULL,NULL,NULL,NULL,'COLUMN_TITLE',NULL,NULL,NULL),('10004-floor-2-1','10004-floor-2','推荐管理楼层2-1号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-floor-2-2','10004-floor-2','推荐管理楼层2-2号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-floor-2-3','10004-floor-2','推荐管理楼层2-3号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-floor-2-4','10004-floor-2','推荐管理楼层2-4号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-floor-2-5','10004-floor-2','推荐管理楼层2-5号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-floor-2-6','10004-floor-2','推荐管理楼层2-6号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-floor-2-title','10004-floor-2','推荐管理楼层2-栏目标题',NULL,NULL,NULL,NULL,NULL,NULL,'COLUMN_TITLE',NULL,NULL,NULL),('10004-floor-3-1','10004-floor-3','推荐管理楼层3-1号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-floor-3-2','10004-floor-3','推荐管理楼层3-2号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-floor-3-3','10004-floor-3','推荐管理楼层3-3号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-floor-3-4','10004-floor-3','推荐管理楼层3-4号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-floor-3-5','10004-floor-3','推荐管理楼层3-5号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-floor-3-6','10004-floor-3','推荐管理楼层3-6号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-floor-3-title','10004-floor-3','推荐管理楼层3-栏目标题',NULL,NULL,NULL,NULL,NULL,NULL,'COLUMN_TITLE',NULL,NULL,NULL),('10004-hot-1','10004-hot','热门推荐-1号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-hot-2','10004-hot','热门推荐-2号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-hot-3','10004-hot','热门推荐-3号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-hot-4','10004-hot','热门推荐-4号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-hot-5','10004-hot','热门推荐-5号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('10004-hot-6','10004-hot','热门推荐-6号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-banner-1','100067-banner-1','横幅广告1',NULL,NULL,NULL,NULL,NULL,NULL,'BANNER',NULL,NULL,NULL),('100067-banner-2','100067-banner-2','横幅广告2',NULL,NULL,NULL,NULL,NULL,NULL,'BANNER',NULL,NULL,NULL),('100067-banner-3','100067-banner-3','横幅广告3',NULL,NULL,NULL,NULL,NULL,NULL,'BANNER',NULL,NULL,NULL),('100067-floor-1-1','100067-floor-1','推荐管理楼层1-1号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-floor-1-2','100067-floor-1','推荐管理楼层1-2号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-floor-1-3','100067-floor-1','推荐管理楼层1-3号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-floor-1-4','100067-floor-1','推荐管理楼层1-4号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-floor-1-5','100067-floor-1','推荐管理楼层1-5号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-floor-1-6','100067-floor-1','推荐管理楼层1-6号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-floor-1-title','100067-floor-1','推荐管理楼层1-栏目标题',NULL,NULL,NULL,NULL,NULL,NULL,'COLUMN_TITLE',NULL,NULL,NULL),('100067-floor-2-1','100067-floor-2','推荐管理楼层2-1号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-floor-2-2','100067-floor-2','推荐管理楼层2-2号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-floor-2-3','100067-floor-2','推荐管理楼层2-3号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-floor-2-4','100067-floor-2','推荐管理楼层2-4号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-floor-2-5','100067-floor-2','推荐管理楼层2-5号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-floor-2-6','100067-floor-2','推荐管理楼层2-6号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-floor-2-title','100067-floor-2','推荐管理楼层2-栏目标题',NULL,NULL,NULL,NULL,NULL,NULL,'COLUMN_TITLE',NULL,NULL,NULL),('100067-floor-3-1','100067-floor-3','推荐管理楼层3-1号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-floor-3-2','100067-floor-3','推荐管理楼层3-2号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-floor-3-3','100067-floor-3','推荐管理楼层3-3号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-floor-3-4','100067-floor-3','推荐管理楼层3-4号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-floor-3-5','100067-floor-3','推荐管理楼层3-5号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-floor-3-6','100067-floor-3','推荐管理楼层3-6号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-floor-3-title','100067-floor-3','推荐管理楼层3-栏目标题',NULL,NULL,NULL,NULL,NULL,NULL,'COLUMN_TITLE',NULL,NULL,NULL),('100067-hot-1','100067-hot','热门推荐-1号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-hot-2','100067-hot','热门推荐-2号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-hot-3','100067-hot','热门推荐-3号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-hot-4','100067-hot','热门推荐-4号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-hot-5','100067-hot','热门推荐-5号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('100067-hot-6','100067-hot','热门推荐-6号位',NULL,NULL,NULL,NULL,NULL,NULL,'FLOOR',NULL,NULL,NULL),('banner-1','banner-1','横幅广告1',NULL,NULL,'',6,'331','/group1/M00/01/39/rB4KPlmY9ViASf5uAAcqqXqmRz8861.png','BANNER',NULL,'',''),('banner-2','banner-2','横幅广告2',NULL,NULL,'',2,'list/index.html?text=%E6%B0%B4','/group1/M00/01/39/rB4KPlmY9ZGAPx-CAAekLhPd8KY279.png','BANNER',NULL,NULL,NULL),('banner-3','banner-3','横幅广告3',NULL,NULL,'1211',1,'1211','/group1/M00/01/3A/rB4KPFmY9cOAUB3uAATxRVC6Hec066.png','BANNER',NULL,NULL,NULL),('floor-1-1','floor-1','哈哈1510965211159','呼呼1510965211159','呵呵1510965211159','1',NULL,'http://www.yatang.com/products/1','/group1/M00/01/7C/rB4KPVnxpaWAWR55AAFKo_xCjCM089.png','FLOOR',NULL,NULL,NULL),('floor-1-2','floor-1','楼层1-2号位','楼层1广告3号位','楼层1广告3号位副标题','1000393',1,'undefined','/group1/M00/01/3A/rB4KPFmY9W6AAkbzAAEfv0C4KRw246.png','FLOOR',NULL,NULL,NULL),('floor-1-3','floor-1','楼层1-3号位','楼层1广告4号位','楼层1广告4号位副标题','1000393',1,'undefined','/group1/M00/01/3A/rB4KPFmY9XWATtzFAAD5Kl8RmjA352.png','FLOOR',NULL,NULL,NULL),('floor-1-4','floor-1','floor-1-4号位','妹纸','好妹纸','1000393',1,'undefined','/group1/M00/01/3A/rB4KPVmY9XuAFvUeAAJZQEeIQeE876.png','FLOOR',NULL,NULL,NULL),('floor-1-5','floor-1','floor-1-5号位','12','123','1000393',1,NULL,'/group1/M00/01/3A/rB4KPVmY9YGAWHp_AACYn1PIzvQ641.png','FLOOR',NULL,NULL,NULL),('floor-1-6','floor-1','floor-1-6号位','12','12','1001671',1,NULL,'/group1/M00/01/39/rB4KPlmY9YeAO4xHAAC3JM2-wls526.png','FLOOR',NULL,NULL,NULL),('floor-1-title','floor-1','??1510965211109','12300023',NULL,NULL,2,'http://www.yatang.com/products/1',NULL,'COLUMN_TITLE',NULL,NULL,NULL),('floor-2-1','floor-2','floor-2-1号位','热门推荐广告2号','123hhh','',2,'http://www.baidu.com1','/group1/M00/01/3A/rB4KPVmY9vmAEvnXAAHe3v083Mk777.png','FLOOR',NULL,NULL,NULL),('floor-2-2','floor-2','floor-2-2号位','12','12','',2,'http://www.baidu.com','/group1/M00/01/3A/rB4KPVmY9eOAJn-_AAD4ja7rvvY482.png','FLOOR',NULL,NULL,NULL),('floor-2-3','floor-2','floor-2-3号位','123','123','123',1,'123','/group1/M00/01/3A/rB4KPVmY9eiASj8iAAEYN8kpiGs533.png','FLOOR',NULL,NULL,NULL),('floor-2-4','floor-2','floor-2-4号位','123','hhh','1003606',1,'1003606','/group1/M00/01/39/rB4KPlmY9e6ARoO8AADWmbf_bVY577.png','FLOOR',NULL,NULL,NULL),('floor-2-5','floor-2','floor-2-5号位','123','123','1000193',1,'undefined','/group1/M00/01/39/rB4KPlmY9fWAFH3yAACLoM4M2Ag567.png','FLOOR',NULL,NULL,NULL),('floor-2-6','floor-2','floor-2-6号位','123','12','1001634',1,'1001634','/group1/M00/01/3A/rB4KPFmY9fqABMacAACMyaOZjbc712.png','FLOOR',NULL,NULL,NULL),('floor-2-title','floor-2','楼层2-栏目标题','洗化针纺',NULL,NULL,2,'list/index.html?text=%E6%B4%97%E5%8C%96%E9%92%88%E7%BB%87',NULL,'COLUMN_TITLE',NULL,NULL,NULL),('floor-3-1','floor-3','floor-3-1号位','123','hhh11123','',6,'330','/group1/M00/01/3A/rB4KPFmY9ZqAEHwUAAIdQJfAWA4637.png','FLOOR',NULL,'',''),('floor-3-2','floor-3','floor-3-2号位','123','123',NULL,2,'undefined','/group1/M00/01/3A/rB4KPFmY9Z-APSv-AADjd_Q9Bkc808.png','FLOOR',NULL,NULL,NULL),('floor-3-3','floor-3','floor-3-3号位','12','12','1000097',1,'1000097','/group1/M00/01/3A/rB4KPVmY9aiAB52iAAEChBpcSNY747.png','FLOOR',NULL,NULL,NULL),('floor-3-4','floor-3','floor-3-4号位','12','12','',2,'list/index.html?text=%E9%A5%AE%E6%96%99','/group1/M00/01/3A/rB4KPVmY9a-AaYH1AAHuL_1Bsdc903.png','FLOOR',NULL,NULL,NULL),('floor-3-5','floor-3','floor-3-5号位','123','12',NULL,2,'http://www.baidu.com','/group1/M00/01/39/rB4KPlmY9bSAZylwAAELefSmQTA439.png','FLOOR',NULL,NULL,NULL),('floor-3-6','floor-3','floor-3-6号位','78','78','',2,'index.html?tab=classify&id=xcate12','/group1/M00/01/39/rB4KPlmY9bmAfEdeAADmhOfRGoo603.png','FLOOR',NULL,NULL,NULL),('floor-3-title','floor-3','楼层3-栏目标题','休闲食品',NULL,NULL,NULL,'https://ant.design/components/carousel-cn/',NULL,'COLUMN_TITLE',NULL,NULL,NULL),('hot-1','hot','热门推荐-1号位','热门推荐广告1号位','热门推荐广告1号位副标题','',6,'330','/group1/M00/01/39/rB4KPlmY8v2AbWB4AALS-o_Kf2w592.png','FLOOR',NULL,'',''),('hot-2','hot','热门推荐-2号位','热门推荐广告2号位','热门推荐广告2号位副标题','1000115',1,'','/group1/M00/01/39/rB4KPlmY9S2AIzsQAAFe-XE7mYU941.png','FLOOR',NULL,'',''),('hot-3','hot','hot-3号位','12','123','',6,'354','/group1/M00/01/3A/rB4KPFmY9TSAeVHzAAElH_Ql9g0217.png','FLOOR',NULL,'',''),('hot-4','hot','hot-4号位','12','12','',6,'286','/group1/M00/01/3A/rB4KPFmY9TuAcaH7AADfxN79YeY086.png','FLOOR',NULL,'',''),('hot-5','hot','hot-5号位','123','12','1000393',1,NULL,'/group1/M00/01/3A/rB4KPVmY9UGAbheBAAD_uqNcYTI305.png','FLOOR',NULL,NULL,NULL),('hot-6','hot','hot-6号位','123','12','',6,'1212','/group1/M00/01/3A/rB4KPVmY9UeABYiCAAECEZAHADs951.png','FLOOR',NULL,'',''),('quick-nav-1','quick-nav-1','????1520243219652',NULL,NULL,'',3,'','http://image.yatang.com/carousel-1.png','QUICK_NAV',NULL,NULL,NULL);

/*Table structure for table `sys_home_quick_navigation` */

DROP TABLE IF EXISTS `sys_home_quick_navigation`;

CREATE TABLE `sys_home_quick_navigation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `navigation_position` varchar(20) DEFAULT NULL COMMENT '位置',
  `navigation_type` int(2) DEFAULT NULL COMMENT '链接类型，1：详情链接，2：分类链接，3：列表链接，4：页面链接，5：外部链接，6：活动链接',
  `goods_id` varchar(20) DEFAULT NULL COMMENT '商品编号',
  `navigation_name` varchar(20) DEFAULT NULL COMMENT '名称',
  `link_address` varchar(255) DEFAULT NULL COMMENT '链接地址',
  `pic_address` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `create_person` varchar(50) DEFAULT NULL COMMENT '创建人',
  `update_person` varchar(50) DEFAULT NULL COMMENT '修改人',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_update` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `status` int(2) DEFAULT '1' COMMENT '状态（0：停用，1：已启用）',
  `area_id` varchar(255) NOT NULL DEFAULT 'quick_nav' COMMENT '所属areaID',
  `link_keyword` varchar(255) DEFAULT NULL COMMENT '链接关键字',
  `link_id` varchar(255) DEFAULT NULL COMMENT '链接ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='快捷导航';

/*Data for the table `sys_home_quick_navigation` */

insert  into `sys_home_quick_navigation`(`id`,`navigation_position`,`navigation_type`,`goods_id`,`navigation_name`,`link_address`,`pic_address`,`create_person`,`update_person`,`gmt_create`,`gmt_update`,`status`,`area_id`,`link_keyword`,`link_id`) values (1,'1号位',6,'','五金家电','25','/group1/M00/01/B0/rB4KPlop6MWAZ5A9AACMlZeBHSU203.png','1','admin','2017-06-09 10:28:27','2018-03-05 17:45:57',1,'quick-nav','',''),(2,'2号位',6,'','面包主食','328','/group1/M00/01/49/rB4KPVmmZxOAH1BaAACMlZeBHSU088.png','1','admin','2017-06-09 10:28:29','2018-03-05 17:45:57',1,'quick-nav','',''),(3,'3号位',2,'','领券中心','','/group1/M00/01/49/rB4KPVmmZxOAH1BaAACMlZeBHSU088.png','2','admin','2017-06-09 10:28:33','2018-03-05 17:45:57',1,'quick-nav','',''),(4,'4号位',3,'','针纺服饰','','/group1/M00/01/49/rB4KPVmmZxOAH1BaAACMlZeBHSU088.png','2','admin','2017-06-09 10:28:36','2018-03-05 17:45:57',1,'quick-nav','11',''),(5,'5号位',3,'','列表链接','','/group1/M00/01/49/rB4KPVmmZxOAH1BaAACMlZeBHSU088.png','1','admin','2017-06-09 10:28:39','2018-03-05 17:45:57',1,'quick-nav','','8935'),(6,'6号位',2,'','领券中心','','/group1/M00/01/49/rB4KPVmmZxOAH1BaAACMlZeBHSU088.png','2','admin','2017-06-28 09:43:18','2018-03-05 17:45:57',1,'quick-nav','',NULL),(7,'7号位',5,'','外部链接','http://www.baidu.com','/group1/M00/01/49/rB4KPVmmZxOAH1BaAACMlZeBHSU088.png','1','admin','2017-06-13 09:44:19','2018-03-05 17:45:57',1,'quick-nav','',''),(8,'8号位',4,'','分类链接','reservationsArea/index.html','/group1/M00/01/49/rB4KPVmmZxOAH1BaAACMlZeBHSU088.png','2','admin','2017-06-12 09:45:11','2018-03-05 17:45:57',1,'quick-nav','',''),(9,'9号位',5,'','版本','23/index.html','/group1/M00/01/49/rB4KPVmmZxOAH1BaAACMlZeBHSU088.png','1','admin','2017-06-19 09:46:01','2018-03-05 17:45:57',1,'quick-nav','',''),(10,'10号位',4,NULL,'领券中心','couponCenter/index.html','/group1/M00/01/49/rB4KPVmmZxOAH1BaAACMlZeBHSU088.png','2','admin','2017-06-23 09:46:38','2018-03-05 17:45:57',1,'quick-nav',NULL,NULL);

/*Table structure for table `sys_resource` */

DROP TABLE IF EXISTS `sys_resource`;

CREATE TABLE `sys_resource` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `permission_code` varchar(100) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `type` varchar(50) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  `parent_ids` varchar(100) DEFAULT NULL,
  `available` tinyint(1) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `menu_url` varchar(100) DEFAULT NULL,
  `display_name` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `sequence` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_sys_resource_parent_id` (`parent_id`),
  KEY `idx_sys_resource_parent_ids` (`parent_ids`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

/*Data for the table `sys_resource` */

insert  into `sys_resource`(`id`,`permission_code`,`name`,`type`,`parent_id`,`parent_ids`,`available`,`create_time`,`update_time`,`menu_url`,`display_name`,`description`,`sequence`) values (54,'systemManagement','系统管理','menu',0,NULL,1,'2018-01-29 14:20:16','2018-01-29 14:20:16',NULL,'系统管理',NULL,1),(55,'userManagement','用户管理','page',54,NULL,1,'2018-01-29 14:20:16','2018-01-29 14:20:16',NULL,'用户管理',NULL,1),(56,'order','订单管理','menu',0,NULL,1,'2018-01-29 14:20:16','2018-01-29 14:20:16',NULL,'订单管理',NULL,2),(57,'orderList','采购订单列表','page',56,NULL,1,'2018-01-29 14:20:16','2018-01-29 14:20:16',NULL,'采购订单列表',NULL,1);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `available` tinyint(1) DEFAULT '0',
  `create_time` timestamp NULL DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  `role_type` tinyint(1) DEFAULT NULL,
  `role_code` varchar(100) NOT NULL COMMENT '角色编码，保留字段，用于区分地域权限',
  `create_user` varchar(100) DEFAULT NULL,
  `update_user` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`role`,`description`,`available`,`create_time`,`update_time`,`role_type`,`role_code`,`create_user`,`update_user`) values (15,'管理员','管理员拥有所有权限',1,'2018-01-29 14:55:44','2018-01-29 14:55:44',NULL,'admin','admin','admin'),(16,'供应商','',1,'2018-01-29 14:55:44','2018-01-29 14:55:44',NULL,'provider','admin','admin');

/*Table structure for table `sys_role_resource` */

DROP TABLE IF EXISTS `sys_role_resource`;

CREATE TABLE `sys_role_resource` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `resource_id` bigint(20) DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8;

/*Data for the table `sys_role_resource` */

insert  into `sys_role_resource`(`id`,`role_id`,`resource_id`) values (81,15,54),(82,15,56),(83,15,55),(84,15,57),(85,16,56),(86,16,57);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `salt` varchar(100) DEFAULT NULL,
  `locked` tinyint(1) DEFAULT '0',
  `full_name` varchar(100) DEFAULT NULL,
  `position` varchar(100) DEFAULT NULL,
  `mobile` varchar(15) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `type` tinyint(1) DEFAULT '2' COMMENT '类型(1:管理员2:供应商)',
  `p_id` varchar(10) DEFAULT NULL COMMENT '供应商ID',
  `create_user` varchar(20) DEFAULT NULL,
  `create_time` timestamp NULL DEFAULT NULL,
  `update_user` varchar(20) DEFAULT NULL,
  `update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_sys_user_user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`user_name`,`password`,`salt`,`locked`,`full_name`,`position`,`mobile`,`email`,`type`,`p_id`,`create_user`,`create_time`,`update_user`,`update_time`) values (4,'wangwu','e10adc3949ba59abbe56e057f20f883e','bcbac809d06d87bcb6d6e9bd34857b50',0,'王五1',NULL,'18523123252',NULL,2,'xprov003','admin','2018-01-19 16:49:04','admin','2018-01-30 15:38:54'),(5,'zhaoliu','1b75245bf51baa2207175e8141f84758','01948acb6477143f1161e7f3b82e2ab4',0,'赵六1',NULL,'18523152553',NULL,2,'xprov009','admin','2018-01-19 16:49:07','admin','2018-01-29 17:12:01'),(8,'ssuser','e10adc3949ba59abbe56e057f20f883e','0bd6eab6b2b1418e8ecae7ed67d612b5',0,'瘦瘦食品有限公司',NULL,NULL,NULL,2,'xprov007','admin','2018-01-19 16:49:24','admin','2018-01-19 16:49:18'),(9,'zhoutest','7ade1aa895df662857f7875a08b7458b','fc2a0e64a2850b7f7fe4776ea27f9293',0,'c食品有限公司',NULL,'18523553',NULL,1,'xprov001','admin','2018-01-19 16:49:26','admin','2018-01-19 16:49:28'),(10,'mengniu','6f79cbea53e09d486c8ad2992a52fd11','bd17384fdfbd37565c1108d97b6bc9ca',0,'蒙牛食品有限公司',NULL,NULL,NULL,2,'xprov010','admin','2018-01-19 16:49:30','admin','2018-01-22 15:05:18'),(28,'jiajia','9b7538e3a4b715f0247f43dee4eb832b','db7d49489c55585798f6a10a1c1d30da',0,'家家食品有限公司',NULL,NULL,NULL,2,'xprov011','admin','2018-01-22 16:33:50',NULL,NULL),(29,'jiajia1','47ad71f842e9c526c3c370d47b2b8707','311b5353b06057f1b0dd1af9b6b4659d',0,'家家1食品有限公司',NULL,NULL,NULL,2,'xprov012','admin','2018-01-22 16:41:08',NULL,NULL),(31,'jiajia3','2ab6207fb48470b4fdd59e42dfbf8750','6af5c1f8aa84be6a168eb5780462e472',0,'家家3食品有限公司',NULL,NULL,NULL,2,'xprov012','admin','2018-01-22 16:41:37',NULL,NULL),(34,'cheng','3b34fde3dd6b34c1b509e7d7add8a62d','66c8f75853845b4f14e3fcfbb739192d',0,'老干妈',NULL,NULL,NULL,2,'xprov001','admin','2018-01-22 16:54:48',NULL,NULL),(38,'pzc','e10adc3949ba59abbe56e057f20f883e',NULL,0,'pzc食品有限公司',NULL,NULL,NULL,2,'xprov033','admin','2018-01-23 17:45:16',NULL,NULL),(39,'zz',NULL,NULL,0,NULL,NULL,NULL,NULL,2,NULL,NULL,NULL,NULL,NULL),(40,'admin','5bcb81ed243a4551926fef8ac1e70802','05321a6c9f6b0a8feeb4385b7bf6fad2',0,'王五1',NULL,'18523123252',NULL,2,'xprov004','admin','2018-01-19 16:49:04','admin','2018-01-30 13:05:59'),(51,'liufei233','e10adc3949ba59abbe56e057f20f883e',NULL,0,'刘',NULL,NULL,NULL,2,'xprov001','admin','2018-01-29 11:14:34','admin','2018-01-29 13:53:12'),(52,'qiaoxinjiu','e10adc3949ba59abbe56e057f20f883e',NULL,0,'星空久',NULL,NULL,NULL,2,'xprov008','admin','2018-01-29 14:34:47',NULL,NULL),(53,'chengxinchengyi','caa5a1ee9e5ec5a9ece5a3c40483c385','d5453552f04eee772b2a2c53d9fb6c1e',0,'xixi',NULL,NULL,NULL,2,'xprov003','admin','2018-01-29 15:09:13',NULL,NULL),(54,'liufei','e33f6e199de3f0332bd8d0e73511cb5d','bf7c2e217f252931e310a73fbcb70f94',0,'哈哈',NULL,NULL,NULL,2,'xprov009','admin','2018-01-29 15:22:06','admin','2018-02-01 19:42:35'),(55,'wenai','e10adc3949ba59abbe56e057f20f883e',NULL,0,'wenai',NULL,NULL,NULL,2,'xprov016','admin','2018-01-29 15:22:43',NULL,NULL),(56,'fanxiaohuan','e10adc3949ba59abbe56e057f20f883e',NULL,0,'fanxiaohuan',NULL,NULL,NULL,2,'xprov537','admin','2018-01-29 15:23:12',NULL,NULL),(57,'fanxiaohuan233','e10adc3949ba59abbe56e057f20f883e',NULL,0,'fanxiaohuan',NULL,NULL,NULL,2,'xprov558','admin','2018-01-29 15:23:35',NULL,NULL),(58,'qiaoxinjiu233','e10adc3949ba59abbe56e057f20f883e',NULL,0,'qiaoxinjiu',NULL,NULL,NULL,2,'xprov1416','admin','2018-01-29 15:24:02',NULL,NULL),(59,'yangshaung','e10adc3949ba59abbe56e057f20f883e',NULL,0,'yangshaung',NULL,NULL,NULL,2,'xprov566','admin','2018-01-29 15:24:39',NULL,NULL),(60,'tan','f897cab6fd94ab08ebe3c88774ff48bf','a69ec55f0580725c8107934b5e6556d7',0,'tkj',NULL,NULL,NULL,1,NULL,'admin','2018-02-02 15:59:30',NULL,NULL),(64,'guanliyuan','d5b4e138218661b01821e4e2a01669f5','97b9bdbc20ea6fd7febaec542eae2027',0,'管理员账号',NULL,NULL,NULL,1,NULL,'admin','2018-02-05 10:21:48',NULL,NULL),(65,'tan001','e10adc3949ba59abbe56e057f20f883e',NULL,0,'tkj',NULL,NULL,NULL,1,NULL,'admin','2018-02-05 10:30:30',NULL,NULL),(66,'guanliyuan1','e10adc3949ba59abbe56e057f20f883e',NULL,0,'管理员1',NULL,NULL,NULL,1,NULL,'admin','2018-02-05 10:31:18',NULL,NULL),(67,'zhangsanfeng','4297f44b13955235245b2497399d7a93',NULL,0,'张三丰食品有限公司',NULL,NULL,NULL,2,'xprov1042','admin','2018-02-05 10:34:28',NULL,NULL),(68,'gongyingshang','e10adc3949ba59abbe56e057f20f883e',NULL,0,'测试供应商',NULL,NULL,NULL,2,'xprov040','admin','2018-02-05 10:34:55',NULL,NULL),(69,'lilili','859a5f5c5e2a9ac3ab31642b230a4d23','f187d4058909530fc76b46cfb47282e2',0,'lili有限公司',NULL,NULL,NULL,2,'xprov034','admin','2018-02-05 11:23:58',NULL,NULL),(70,'Mr.H','231a4472fd46be23bcf08514a5a4e816','43ab75a720b0d8392cdf265711f17b1d',0,'Mr.H有限公司',NULL,NULL,NULL,2,'xprov035','admin','2018-02-05 11:40:32',NULL,NULL);

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8;

/*Data for the table `sys_user_role` */

insert  into `sys_user_role`(`id`,`user_id`,`role_id`) values (41,51,13),(43,40,15),(44,53,15),(45,53,16),(46,50,13),(52,4,16),(55,54,16),(56,70,16),(57,72,16),(59,40,1),(60,77,1);



/*Table structure for table `tran_data` */

DROP TABLE IF EXISTS `tran_data`;

CREATE TABLE `tran_data` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `item` varchar(40) NOT NULL COMMENT '商品ID',
  `groups` varchar(40) DEFAULT NULL COMMENT '部类',
  `dept` varchar(40) DEFAULT NULL COMMENT '大类',
  `classs` varchar(40) DEFAULT NULL COMMENT '中类',
  `subclass` varchar(40) DEFAULT NULL COMMENT '小类',
  `location` varchar(40) DEFAULT NULL COMMENT '仓库编码',
  `tran_date` timestamp NULL DEFAULT NULL COMMENT '事务日期',
  `tran_code` varchar(10) DEFAULT NULL COMMENT '事务类型',
  `units` bigint(19) DEFAULT '0' COMMENT '交易数量',
  `total_cost` double(20,3) DEFAULT NULL COMMENT '总成本',
  `total_retail` double(20,3) DEFAULT NULL COMMENT '销售总额',
  `ref_no_1` varchar(40) DEFAULT NULL,
  `ref_no_2` varchar(40) DEFAULT NULL,
  `ref_no_3` varchar(40) DEFAULT NULL,
  `attr_1` varchar(40) DEFAULT NULL,
  `attr_2` varchar(40) DEFAULT NULL,
  `vat_rate` varchar(40) DEFAULT NULL COMMENT '销项税税率',
  `product_code` varchar(50) DEFAULT NULL COMMENT '商品code',
  `taxes` double(20,3) DEFAULT NULL COMMENT '税额',
  PRIMARY KEY (`id`),
  KEY `index_refno1_item` (`ref_no_1`,`item`)
) ENGINE=InnoDB AUTO_INCREMENT=1052 DEFAULT CHARSET=utf8 COMMENT='库存交易流水表';

/*Data for the table `tran_data` */

insert  into `tran_data`(`id`,`item`,`groups`,`dept`,`classs`,`subclass`,`location`,`tran_date`,`tran_code`,`units`,`total_cost`,`total_retail`,`ref_no_1`,`ref_no_2`,`ref_no_3`,`attr_1`,`attr_2`,`vat_rate`,`product_code`,`taxes`) values (2,'60000742839','xcate3','xcate17','xcate53','xcate428','YT901','2017-08-21 16:25:24','1',9,NULL,810.000,'10000201708217312',NULL,NULL,NULL,NULL,'17.00','1001171',NULL);

/*Table structure for table `warehouse_logic_info` */

DROP TABLE IF EXISTS `warehouse_logic_info`;

CREATE TABLE `warehouse_logic_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '仓库id',
  `physical_warehouse_id` int(11) NOT NULL COMMENT '物理仓库id',
  `warehouse_code` varchar(55) DEFAULT NULL COMMENT '仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `branch_company_id` varchar(11) DEFAULT NULL COMMENT '子公司ID',
  `branch_company_code` varchar(255) DEFAULT NULL COMMENT '子公司编码',
  `branch_company_name` varchar(255) DEFAULT NULL COMMENT '子公司名称',
  `logic_type` int(2) DEFAULT NULL COMMENT '逻辑仓类型',
  `storage_type` int(2) DEFAULT NULL COMMENT '存储类型(1:常温;2:冷藏;3冷冻)',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `create_user` varchar(20) DEFAULT NULL,
  `modify_user` varchar(20) DEFAULT NULL,
  `tms_code` varchar(55) DEFAULT NULL,
  `tms_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='逻辑仓库表';

/*Data for the table `warehouse_logic_info` */

insert  into `warehouse_logic_info`(`id`,`physical_warehouse_id`,`warehouse_code`,`warehouse_name`,`branch_company_id`,`branch_company_code`,`branch_company_name`,`logic_type`,`storage_type`,`create_time`,`modify_time`,`create_user`,`modify_user`,`tms_code`,`tms_name`) values (1,1,'TJXQ','成都仓四川子公司常温正常仓','10000','YTXC','成都子公司',1,1,'2017-08-03 14:11:26','2017-08-03 14:11:33','admin','admin','OTHER','其他'),(2,2,'WLL','重庆仓重庆子公司常温正常仓','10004','20042','湖南子公司',1,1,'2017-08-03 14:11:29','2017-08-03 14:11:35','admin','admin','STO','申通'),(3,3,'YT901','深圳仓深圳(广东子公司直营)常温正常仓','10000','YTXC','成都子公司',NULL,NULL,'2017-08-03 14:11:29','2017-08-03 14:11:35','admin','admin','OTHER','其他'),(9,12,'TJXQS','济南仓山东子公司常温正常仓','10007','20047','山东子公司',1,1,'2017-10-10 18:45:56','2017-10-10 18:45:56','admin','admin','STO','申通'),(10,1,'TJXQ1','西藏仓拉萨子公司常温正常仓','10008','20048','西藏子公司',1,1,'2017-11-09 19:54:32','2017-11-09 19:54:35','admin','admin','STO','申通'),(11,12,'TJXQ2','西藏仓拉萨子公司常温正常仓','10007','20047','山东子公司',1,1,'2017-10-10 18:45:56','2017-10-10 18:45:56','admin','admin','STO','申通'),(12,13,'WHH01','桔瓣的测试仓库','10000','WHH','桔瓣子公司',1,1,'2017-12-11 16:43:09','2017-12-11 16:43:09','admin','admin','SF','申通');

/*Table structure for table `warehouse_physical_info` */

DROP TABLE IF EXISTS `warehouse_physical_info`;

CREATE TABLE `warehouse_physical_info` (
  `id` int(11) unsigned zerofill NOT NULL AUTO_INCREMENT COMMENT '仓库id',
  `warehouse_code` varchar(55) DEFAULT NULL COMMENT '仓库编码',
  `third_warehouse_code` varchar(55) DEFAULT NULL COMMENT '第三方仓库编码',
  `warehouse_name` varchar(255) DEFAULT NULL COMMENT '仓库名称',
  `warehouse_service` varchar(255) DEFAULT NULL COMMENT '仓库服务方',
  `province_code` varchar(55) DEFAULT NULL COMMENT '省份编码',
  `province` varchar(55) DEFAULT NULL COMMENT '省份',
  `city_code` varchar(50) DEFAULT NULL COMMENT '城市编码',
  `city` varchar(50) DEFAULT NULL COMMENT '城市',
  `county_code` varchar(50) DEFAULT NULL COMMENT '区域编码',
  `county` varchar(50) DEFAULT NULL COMMENT '区域名称',
  `address` varchar(255) DEFAULT NULL COMMENT '仓库地址',
  `contact_person` varchar(20) DEFAULT NULL COMMENT '仓库联系人',
  `contact_mode` varchar(20) DEFAULT NULL COMMENT '仓库联系方式',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modify_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `create_user` varchar(20) DEFAULT NULL,
  `modify_user` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='物理仓库表';

/*Data for the table `warehouse_physical_info` */

insert  into `warehouse_physical_info`(`id`,`warehouse_code`,`third_warehouse_code`,`warehouse_name`,`warehouse_service`,`province_code`,`province`,`city_code`,`city`,`county_code`,`county`,`address`,`contact_person`,`contact_mode`,`create_time`,`modify_time`,`create_user`,`modify_user`) values (00000000001,'901','SZSM','心怡成都仓','际链','510000','四川','510100','成都','510112','龙泉驿区','汽车城大道17号普洛斯园区B1-1  ','张志荣\r\n','13438234152\r\n','2017-07-31 15:10:04','2017-07-28 14:45:53','admin','admin'),(00000000002,'902','STORE_CQYT','心怡重庆仓','心怡','500000','重庆','500100','重庆\r\n','500112','渝北区','空港普洛斯（际链仓）','徐春鑫','13817464003','2017-07-28 16:57:02','2017-07-28 16:57:02','admin','admins'),(00000000003,'902','YT901','际链测试仓','际链','520000','重庆','500100','重庆\r\n','500112','渝北区','空港普洛斯（际链仓）','徐春鑫','13817464003','2017-08-17 09:42:26','2017-08-17 09:42:26','admin','admin'),(00000000012,'906','SZSMS','际链济南仓','际链','370000','山东','370100','济南','370112','历城区','大桥路1号盖世物流第四库区','高远','13388056768','2017-10-10 18:45:56','2017-10-10 18:45:56','admin','admin'),(00000000013,'JB001','JB','桔瓣测试仓','桔瓣','20000',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2017-12-11 16:45:22','2017-12-11 16:45:22',NULL,NULL);

/*Table structure for table `ziptoftp_info` */

DROP TABLE IF EXISTS `ziptoftp_info`;

CREATE TABLE `ziptoftp_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增主键',
  `root_path` varchar(255) NOT NULL COMMENT '访问根路径',
  `upload_time` datetime NOT NULL COMMENT '上传时间',
  `description` varchar(255) DEFAULT NULL COMMENT '描述',
  `effect_time` datetime DEFAULT NULL COMMENT '生效时间',
  `invalid_time` datetime DEFAULT NULL COMMENT '失效时间',
  `activity_name` varchar(255) DEFAULT NULL COMMENT '活动名称',
  `zip_name` varchar(255) DEFAULT NULL COMMENT 'ZIP文件名称',
  `upload_user_id` varchar(255) NOT NULL COMMENT '上传用户ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=358 DEFAULT CHARSET=utf8 COMMENT='ZIP文件上传信息表';

/*Data for the table `ziptoftp_info` */

insert  into `ziptoftp_info`(`id`,`root_path`,`upload_time`,`description`,`effect_time`,`invalid_time`,`activity_name`,`zip_name`,`upload_user_id`) values (22,'http://sit.db.com/html/1511331513686','2017-11-22 14:20:18',NULL,NULL,NULL,NULL,'1511331513686.zip','123123'),(23,'http://sit.db.com/html/5a129ab642bc7cfac191d6bd1511337064380','2017-11-22 15:53:25',NULL,NULL,NULL,NULL,'5a129ab642bc7cfac191d6bd1511337064380.zip','123123');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
