package com.yatang.sc.inventory.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @描述: 审批状态
 * @作者: kangdong
 * @创建时间: 2017年05月23日09:31:22
 * @版本: 1.0 .
 */
@Getter
@AllArgsConstructor
public enum Status {
	VOUCHING("制单"),
	BECAME_EFFECTIVE("已生效"),
	;

	private final String description;

}
