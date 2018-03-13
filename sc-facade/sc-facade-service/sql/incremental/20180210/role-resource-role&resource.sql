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
)

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
)

/*Table structure for table `sys_role_resource` */

DROP TABLE IF EXISTS `sys_role_resource`;

CREATE TABLE `sys_role_resource` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  `resource_id` bigint(20) DEFAULT NULL COMMENT '资源ID',
  PRIMARY KEY (`id`)
)


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

CREATE TABLE `sys_user_role` (
  `id` int(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint(20) DEFAULT NULL COMMENT '用户ID',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8;

/*权限初始化数据 sys_resource*/
INSERT INTO sys_resource(permission_code,`name`,`type`,parent_id,available,create_time,update_time,display_name,sequence)
VALUES("systemManagement","系统管理","menu",0,1,NOW(),NOW(),"系统管理",1);
INSERT INTO sys_resource(permission_code,`name`,`type`,parent_id,available,create_time,update_time,display_name,sequence)
VALUES("userManagement","用户管理","page",(SELECT a.id FROM (SELECT id FROM sys_resource WHERE permission_code='systemManagement') a),1,NOW(),NOW(),"用户管理",1);
INSERT INTO sys_resource(permission_code,`name`,`type`,parent_id,available,create_time,update_time,display_name,sequence)
VALUES("orderManagement","订单管理","menu",0,1,NOW(),NOW(),"订单管理",2);
INSERT INTO sys_resource(permission_code,`name`,`type`,parent_id,available,create_time,update_time,display_name,sequence)
VALUES("purOrderList","采购订单列表","page",(SELECT a.id FROM (SELECT id FROM sys_resource WHERE permission_code='orderManagement') a),1,NOW(),NOW(),"采购订单列表",1);

/*角色初始化数据 sys_role*/
INSERT INTO sys_role(role,description,available,create_time,update_time,role_code,create_user,update_user) VALUES("管理员","管理员拥有所有权限",1,NOW(),NOW(),"admin","admin","admin");
INSERT INTO sys_role(role,description,available,create_time,update_time,role_code,create_user,update_user) VALUES("供应商","",1,NOW(),NOW(),"provider","admin","admin");

/*权限角色关系初始化数据 sys_role_resource*/
INSERT INTO sys_role_resource(role_id, resource_id) VALUES((SELECT id FROM sys_role WHERE role_code="admin"), (SELECT id FROM sys_resource WHERE permission_code="systemManagement"));
INSERT INTO sys_role_resource(role_id, resource_id) VALUES((SELECT id FROM sys_role WHERE role_code="admin"), (SELECT id FROM sys_resource WHERE permission_code="orderManagement"));
INSERT INTO sys_role_resource(role_id, resource_id) VALUES((SELECT id FROM sys_role WHERE role_code="admin"), (SELECT id FROM sys_resource WHERE permission_code="userManagement"));
INSERT INTO sys_role_resource(role_id, resource_id) VALUES((SELECT id FROM sys_role WHERE role_code="admin"), (SELECT id FROM sys_resource WHERE permission_code="purOrderList"));

INSERT INTO sys_role_resource(role_id, resource_id) VALUES((SELECT id FROM sys_role WHERE role_code="provider"), (SELECT id FROM sys_resource WHERE permission_code="orderManagement"));
INSERT INTO sys_role_resource(role_id, resource_id) VALUES((SELECT id FROM sys_role WHERE role_code="provider"), (SELECT id FROM sys_resource WHERE permission_code="purOrderList"));


insert sys_user values(1,'admin','8703b1de0c2b2c66603373a0771f30dd','bcbac809d06d87bcb6d6e9bd34857b50',0,'admin',null,'18523123252',null,2,null,'admin','2018-01-19 16:49:04','admin','2018-01-30 13:05:59');

insert sys_user_role values(1,1,1);