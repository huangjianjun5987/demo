package com.busi.kidd.annotation;

/**
 * 调用类型枚举
 * 
 * @author yangqingsong
 *
 */
public enum CallTypeEnum {

	SYNC_CALL("syncCall"), ASYNC_CALL("asyncCall");

	private final String value;

	private CallTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
