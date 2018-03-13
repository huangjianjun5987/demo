package com.busi.kidd.annotation;

/**
 * 安全协议枚举
 * 
 * @author yangqingsong
 *
 */
public enum SecurityProtocolEnum {

	ONEWAY("oneWay"), BOTHWAY("bothWay");

	private final String value;

	private SecurityProtocolEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
