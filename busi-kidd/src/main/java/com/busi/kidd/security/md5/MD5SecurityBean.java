package com.busi.kidd.security.md5;

import com.busi.kidd.security.SecurityBean;

/**
 * MD5数字签名bean
 * 
 * @author yangqingsong
 *
 */
public class MD5SecurityBean implements SecurityBean {
	// 签名信息
	private String sign;
	// 待签名数据
	private Object data;

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
