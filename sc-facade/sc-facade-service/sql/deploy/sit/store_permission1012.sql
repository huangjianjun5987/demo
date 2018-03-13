
-- 给表加上唯一索引
ALTER TABLE `store_permission` ADD UNIQUE (`store_id`)

-- 修改mysql的存储引擎
ALTER TABLE `store_permission` ENGINE=MYISAM;