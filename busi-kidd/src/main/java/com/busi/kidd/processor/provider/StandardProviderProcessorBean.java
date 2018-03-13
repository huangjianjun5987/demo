package com.busi.kidd.processor.provider;

import com.busi.kidd.processor.KiddProviderProcessorBean;

public class StandardProviderProcessorBean extends KiddProviderProcessorBean {
	// 数字签名
	private String sign;
	private String appKey;
	private String timestamp;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

}
