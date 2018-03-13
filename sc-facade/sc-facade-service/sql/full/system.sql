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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;


insert  into `sys_resource`(`id`,`permission_code`,`name`,`type`,`parent_id`,`parent_ids`,`available`,`create_time`,`update_time`,`menu_url`,`display_name`,`description`,`sequence`) values (1,'systemManagement','系统管理','menu',0,NULL,1,'2018-02-06 19:01:37','2018-02-06 19:01:37',NULL,'系统管理',NULL,1),(2,'userManagement','用户管理','page',1,NULL,1,'2018-02-06 19:01:37','2018-02-06 19:01:37',NULL,'用户管理',NULL,1),(3,'order','订单管理','menu',0,NULL,1,'2018-02-06 19:01:37','2018-02-06 19:01:37',NULL,'订单管理',NULL,2),(4,'orderList','采购订单列表','page',3,NULL,1,'2018-02-06 19:01:37','2018-02-06 19:01:37',NULL,'采购订单列表',NULL,1);


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;


insert  into `sys_role`(`id`,`role`,`description`,`available`,`create_time`,`update_time`,`role_type`,`role_code`,`create_user`,`update_user`) values (1,'管理员','管理员拥有所有权限',1,'2018-02-06 19:01:37','2018-02-06 19:01:37',NULL,'admin','admin','admin'),(2,'供应商','',1,'2018-02-06 19:01:37','2018-02-06 19:01:37',NULL,'provider','admin','admin');


DROP TABLE IF EXISTS `sys_role_resource`;

CREATE TABLE `sys_role_resource` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `resource_id` bigint(20) DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;


insert  into `sys_role_resource`(`id`,`role_id`,`resource_id`) values (1,1,1),(2,1,3),(3,1,2),(4,1,4),(5,2,3),(6,2,4);


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;


insert  into `sys_user`(`id`,`user_name`,`password`,`salt`,`locked`,`full_name`,`position`,`mobile`,`email`,`type`,`p_id`,`create_user`,`create_time`,`update_user`,`update_time`) values (1,'admin','8703b1de0c2b2c66603373a0771f30dd','bcbac809d06d87bcb6d6e9bd34857b50',0,'王五1',NULL,'18523123252',NULL,2,'xprov004','admin','2018-01-19 16:49:04','admin','2018-01-30 13:05:59'),(73,'chengxinchengyi','6425cd170fe07a7683cad576c4ae5b79','90fcb369c1eab182f922977755aa8794',0,'诚心诚意',NULL,NULL,NULL,2,'xcsupp003','admin','2018-02-07 10:53:49',NULL,NULL);


DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=1;


insert  into `sys_user_role`(`id`,`user_id`,`role_id`) values (1,1,1),(58,73,2);

