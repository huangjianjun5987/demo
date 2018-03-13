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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='ZIP文件上传信息表'