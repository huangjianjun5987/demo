package com.yatang.sc.facade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @描述: 缴纳类型
 * @作者: yipeng
 * @创建时间: 2017年05月23日09:31:22
 * @版本: 1.0 .
 */
@Getter
@AllArgsConstructor
public enum PaymentType {
	FIRST_PAYMENT("首次缴纳"),
	REFUND("退还"),
	PAYMENT("补缴"),
	;

	private final String description;

}
