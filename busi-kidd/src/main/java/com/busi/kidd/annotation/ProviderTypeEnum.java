package com.busi.kidd.annotation;

/**
 * 调用类型枚举
 * 
 * @author yangqingsong
 *
 */
public enum ProviderTypeEnum {

	MD5("md5");

	private final String value;

	private ProviderTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
