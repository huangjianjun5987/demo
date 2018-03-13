
CREATE TABLE `cf_area_warehouse` (
  `id` BIGINT(21) NOT NULL AUTO_INCREMENT,
  `province` VARCHAR(20) DEFAULT NULL COMMENT '省份',
  `warehouse_code` VARCHAR(20) DEFAULT NULL COMMENT '仓库编码',
  `branch_company_id` VARCHAR(20) DEFAULT NULL COMMENT '分公司id',
  PRIMARY KEY (`id`),
  UNIQUE KEY `province` (`province`,`warehouse_code`)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (1,"四川","901200411","10000");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (2,"四川省","901200411","10000");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (3,"重庆","902200421","10005");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (4,"重庆市","902200421","10005");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (5,"湖北","904200501","10014");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (6,"湖北省","904200501","10014");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (7,"湖南","903200451","10014");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (8,"湖南省","903200451","10004");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (9,"广东","905200461","10021");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (10,"广东省","905200461","10021");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (11,"山东","906200471","10007");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (12,"山东省","906200471","10007");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (13,"浙江","908200551","10020");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (14,"浙江省","908200551","10020");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (15,"河北","909200441","10008");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (16,"河北省","909200441","10008");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (17,"河南","911200561","10012");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (18,"河南省","911200561","10012");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (19,"陕西","912200521","10010");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (20,"陕西省","912200521","10010");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (21,"北京","907200431","10006");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (22,"北京省","907200431","10006");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (23,"江西","913200611","100045");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (24,"江西省","913200611","100045");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (25,"香港特别行政区","WLL","10004");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (26,"香港","WLL","10004");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (27,"台湾特别行政区","YT901","10000");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (28,"台湾省","YT901","10000");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (29,"台湾","YT901","10000");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (30,"西藏自治区","TJXQ","10003");
INSERT INTO cf_area_warehouse(id,province,warehouse_code,branch_company_id) VALUES (31,"西藏","TJXQ","10003");