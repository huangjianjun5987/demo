DROP TABLE IF EXISTS `franchisee_level_discount_cfg`;
CREATE TABLE `franchisee_level_discount_cfg` (
  `id` BIGINT(19) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `f_level` VARCHAR(40) NOT NULL COMMENT '等级',
  `discount` DECIMAL(10,2) DEFAULT NULL COMMENT '折扣',
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COMMENT='加盟商等级折扣配置';

insert into `franchisee_level_discount_cfg` (`id`, `f_level`, `discount`) values('1','A','12');
insert into `franchisee_level_discount_cfg` (`id`, `f_level`, `discount`) values('2','B','11');
insert into `franchisee_level_discount_cfg` (`id`, `f_level`, `discount`) values('3','C','10');
insert into `franchisee_level_discount_cfg` (`id`, `f_level`, `discount`) values('4','D','9');
insert into `franchisee_level_discount_cfg` (`id`, `f_level`, `discount`) values('5','E','8');
insert into `franchisee_level_discount_cfg` (`id`, `f_level`, `discount`) values('6','F','7');
insert into `franchisee_level_discount_cfg` (`id`, `f_level`, `discount`) values('7','G','6');
insert into `franchisee_level_discount_cfg` (`id`, `f_level`, `discount`) values('8','H','5');
insert into `franchisee_level_discount_cfg` (`id`, `f_level`, `discount`) values('9','I','4');
insert into `franchisee_level_discount_cfg` (`id`, `f_level`, `discount`) values('10','J','3');
insert into `franchisee_level_discount_cfg` (`id`, `f_level`, `discount`) values('11','K','2');
insert into `franchisee_level_discount_cfg` (`id`, `f_level`, `discount`) values('12','L','1');