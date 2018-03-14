package com.yatang.sc.purchase.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @描述: 广告类型
 * @作者: yipeng
 * @创建时间: 2017年06月09日17:04:08
 * @版本: 1.0 .
 */

public enum AdType {
	BANNER("横幅广告"),
	FLOOR("楼层广告"),
	COLUMN_TITLE("栏目标题"),
	;

	private final String description;

	AdType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
