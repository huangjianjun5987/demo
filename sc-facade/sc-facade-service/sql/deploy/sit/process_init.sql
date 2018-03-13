/*
SQLyog Ultimate v11.26 (32 bit)
MySQL - 5.7.18-log 
*********************************************************************
*/
/*!40101 SET NAMES utf8 */;
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1892','河北子公司财务审核(供应链)',null,'10008','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1893','河北子公司总经理(供应链)',(select id from process_definition p where p.process_node_code = '1892'),'10008','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1891','河北子公司供应链部审核',(select id from process_definition p where p.process_node_code = '1893'),'10008','1',now(),now(),'1','1','0');

INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1889','湖南子公司财务审核(供应链)',null,'10004','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1890','湖南子公司总经理(供应链)',(SELECT id FROM process_definition p WHERE p.process_node_code = '1889'),'10004','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1888','湖南子公司供应链部审核',(SELECT id FROM process_definition p WHERE p.process_node_code = '1890'),'10004','1',now(),now(),'1','1','0');


INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1895','深圳(广东子公司直营)财务审核(供应链)',null,'10021','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1896','深圳(广东子公司直营)总经理(供应链)',(SELECT id FROM process_definition p WHERE p.process_node_code = '1895'),'10021','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1894','深圳(广东子公司直营)子公司供应链部审核',(SELECT id FROM process_definition p WHERE p.process_node_code = '1896'),'10021','1',now(),now(),'1','1','0');


INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1898','广州(广东子公司直营)财务审核(供应链)',null,'10022','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1899','广州(广东子公司直营)总经理(供应链)',(SELECT id FROM process_definition p WHERE p.process_node_code = '1898'),'10022','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1897','广州(广东子公司直营)子公司供应链部审核',(SELECT id FROM process_definition p WHERE p.process_node_code = '1899'),'10022','1',now(),now(),'1','1','0');


INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1901','重庆子公司财务审核(供应链)',null,'10005','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1902','重庆子公司总经理(供应链)',(SELECT id FROM process_definition p WHERE p.process_node_code = '1901'),'10005','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1900','重庆子公司供应链部审核',(SELECT id FROM process_definition p WHERE p.process_node_code = '1902'),'10005','1',now(),now(),'1','1','0');


INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1904','陕西子公司财务审核(供应链)',null,'10010','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1905','陕西子公司总经理(供应链)',(SELECT id FROM process_definition p WHERE p.process_node_code = '1904'),'10010','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1903','陕西子公司供应链部审核',(SELECT id FROM process_definition p WHERE p.process_node_code = '1905'),'10010','1',now(),now(),'1','1','0');


INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1907','河南子公司财务审核(供应链)',null,'10012','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1908','河南子公司总经理(供应链)',(SELECT id FROM process_definition p WHERE p.process_node_code = '1907'),'10012','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1906','河南子公司供应链部审核',(SELECT id FROM process_definition p WHERE p.process_node_code = '1908'),'10012','1',now(),now(),'1','1','0');


INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1910','浙江子公司财务审核(供应链)',null,'10020','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1911','浙江子公司总经理(供应链)',(SELECT id FROM process_definition p WHERE p.process_node_code = '1910'),'10020','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1909','浙江子公司供应链部审核',(SELECT id FROM process_definition p WHERE p.process_node_code = '1911'),'10020','1',now(),now(),'1','1','0');


INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1913','湖北子公司财务审核(供应链)',null,'10014','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1914','湖北子公司总经理(供应链)',(SELECT id FROM process_definition p WHERE p.process_node_code = '1913'),'10014','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1912','湖北子公司供应链部审核',(SELECT id FROM process_definition p WHERE p.process_node_code = '1914'),'10014','1',now(),now(),'1','1','0');


INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1916','山东子公司财务审核(供应链)',null,'10007','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1917','山东子公司总经理(供应链)',(SELECT id FROM process_definition p WHERE p.process_node_code = '1916'),'10007','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1915','山东子公司供应链部审核',(SELECT id FROM process_definition p WHERE p.process_node_code = '1917'),'10007','1',now(),now(),'1','1','0');


INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1919','四川子公司财务审核(供应链)',null,'10000','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1920','四川子公司总经理(供应链)',(SELECT id FROM process_definition p WHERE p.process_node_code = '1919'),'10000','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1918','四川子公司供应链部审核',(SELECT id FROM process_definition p WHERE p.process_node_code = '1920'),'10000','1',now(),now(),'1','1','0');


INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1922','北京子公司财务审核(供应链)',null,'10006','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1923','北京子公司总经理(供应链)',(SELECT id FROM process_definition p WHERE p.process_node_code = '1922'),'10006','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1921','北京子公司供应链部审核',(SELECT id FROM process_definition p WHERE p.process_node_code = '1923'),'10006','1',now(),now(),'1','1','0');


INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1925','江西子公司财务审核(供应链)',null,'100045','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1926','江西子公司总经理(供应链)',(SELECT id FROM process_definition p WHERE p.process_node_code = '1925'),'100045','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1924','江西子公司供应链部审核',(SELECT id FROM process_definition p WHERE p.process_node_code = '1926'),'100045','1',now(),now(),'1','1','0');

INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1928','云南子公司财务审核(供应链)',null,'100052','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1929','云南子公司总经理(供应链)',(SELECT id FROM process_definition p WHERE p.process_node_code = '1928'),'100052','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1927','云南子公司供应链部审核',(SELECT id FROM process_definition p WHERE p.process_node_code = '1929'),'100052','1',now(),now(),'1','1','0');

INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1931','湖南省益阳子公司财务审核(供应链)',null,'100067','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1932','湖南省益阳子公司总经理(供应链)',(SELECT id FROM process_definition p WHERE p.process_node_code = '1931'),'100067','1',now(),now(),'1','1','1');
INSERT INTO `process_definition` (`process_node_code`, `process_node_name`, `next_node_id`, `branch_company_id`, `type`, `create_time`, `modify_time`, `create_user_id`, `modify_user_id`, `is_first_node`) VALUES('1930','湖南省益阳子公司供应链部审核',(SELECT id FROM process_definition p WHERE p.process_node_code = '1932'),'100067','1',now(),now(),'1','1','0');


