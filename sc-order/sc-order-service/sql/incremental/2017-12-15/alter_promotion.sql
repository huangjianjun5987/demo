ALTER TABLE scp_promotion ADD COLUMN `promotion_rule_json` LONGTEXT NULL COMMENT '促銷条件配置';
ALTER TABLE scp_promotion ADD COLUMN `priority` bigint(20)  COMMENT '促銷优先级';
ALTER TABLE scp_promotion ADD COLUMN `simpleDescription` VARCHAR(255)  COMMENT '简易描述';
ALTER TABLE scp_promotion ADD COLUMN `detailDescription` VARCHAR(255)  COMMENT '详细描述';