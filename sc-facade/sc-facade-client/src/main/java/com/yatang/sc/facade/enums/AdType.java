package com.yatang.sc.facade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @描述: 广告类型
 * @作者: yipeng
 * @创建时间: 2017年06月09日17:04:08
 * @版本: 1.0 .
 */
@Getter
@AllArgsConstructor
public enum AdType {
	BANNER("横幅广告"),
	FLOOR("楼层广告"),
	COLUMN_TITLE("栏目标题"),
	QUICK_NAV("快捷导航"),
	CAROUSEL("轮播图广告"),
	;

	private final String description;

}
