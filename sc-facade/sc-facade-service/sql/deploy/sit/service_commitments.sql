

-- 正在导出表  scsit.service_commitments 的数据：~4 rows (大约)
DELETE FROM `service_commitments`;

INSERT INTO `service_commitments` (`id`, `promise_content`, `sort`, `status`) VALUES
	(1, '一年质保', 1, 1),
	(2, '假一赔五', 2, 1),
	(3, '平台配送', 3, 1),
	(4, '服务保障', 4, 1);

