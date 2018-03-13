/*
SQLyog Ultimate v11.26 (32 bit)
MySQL - 5.7.1-TiDB-v1.0.8-1-gaacba4a 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;

insert into `sys_user` (`user_name`, `password`, `salt`, `locked`, `full_name`, `position`, `mobile`, `email`, `type`, `p_id`, `create_user`, `create_time`, `update_user`, `update_time`) values('admin','e3d25bc92f3270a3e8a0f2462d144373','7a7d79a66738c1731b7099a42182022b','0','王五1',NULL,'18523123252',NULL,'2','xprov004','admin','2018-01-19 16:49:04','admin','2018-01-30 13:05:59');

insert into `sys_role` (`role`, `description`, `available`, `create_time`, `update_time`, `role_type`, `role_code`, `create_user`, `update_user`) values('管理员','管理员拥有所有权限','1','2018-02-06 19:01:37','2018-02-06 19:01:37',NULL,'admin','admin','admin');
insert into `sys_role` (`role`, `description`, `available`, `create_time`, `update_time`, `role_type`, `role_code`, `create_user`, `update_user`) values('供应商','','1','2018-02-06 19:01:37','2018-02-06 19:01:37',NULL,'provider','admin','admin');


