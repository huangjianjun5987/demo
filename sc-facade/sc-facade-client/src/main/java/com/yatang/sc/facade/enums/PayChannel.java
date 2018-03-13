package com.yatang.sc.facade.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @描述: 支付渠道
 * @作者: yipeng
 * @创建时间: 2017年4月11日-下午3:32:35 .
 * @版本: 1.0 .
 */
@Getter
@AllArgsConstructor
public enum PayChannel {
	ALIPAY("支付宝"),
	WXPAY("微信"),
	;

	private final String description;

}
