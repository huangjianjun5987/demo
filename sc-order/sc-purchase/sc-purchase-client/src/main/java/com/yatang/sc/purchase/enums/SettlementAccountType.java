package com.yatang.sc.purchase.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @描述: 金融账户类型
 * @作者: kangdong
 * @创建时间: 2017年05月23日09:31:22
 * @版本: 1.0 .
 */
@Getter
@AllArgsConstructor
public enum SettlementAccountType {
	YATANG_FINANCE_ACCOUNT("商家雅堂金融账户"),
	BUSINESS_BANK_ACCOUNT("商家公司银行账户"),
	;

	private final String description;

}
