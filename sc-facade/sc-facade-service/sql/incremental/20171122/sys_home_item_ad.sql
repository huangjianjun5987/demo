ALTER TABLE sys_home_item_ad MODIFY COLUMN url_type INT(11) COMMENT '链接类型，1：详情链接，2：分类链接，3：列表链接，4：页面链接，5：外部链接，6：活动链接';
ALTER TABLE sys_home_item_ad ADD COLUMN link_keyword VARCHAR(255) COMMENT '链接关键字';
ALTER TABLE sys_home_item_ad ADD COLUMN link_id VARCHAR(255) COMMENT '链接ID';