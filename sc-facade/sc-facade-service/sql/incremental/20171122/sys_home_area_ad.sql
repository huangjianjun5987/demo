ALTER TABLE sys_home_area_ad ADD COLUMN area_type INT (11) NOT NULL COMMENT 'area类型字段，1=banner；2=floor；3=carousel；4=quick-nav';
ALTER TABLE `sys_home_area_ad` ADD COLUMN company_id VARCHAR(255) NOT NULL COMMENT '所属(子)公司id';
ALTER TABLE `sys_home_area_ad` ADD COLUMN is_using_nation BOOLEAN NOT NULL COMMENT '该区域是否使用全国区域';