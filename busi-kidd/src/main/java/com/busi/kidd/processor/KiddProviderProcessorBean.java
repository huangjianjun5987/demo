package com.busi.kidd.processor;

import com.busi.kidd.bean.KiddProviderBean;

/**
 * 调用第三方协议Bean顶级类
 * 
 * @author yangqingsong
 *
 */
public class KiddProviderProcessorBean {
	// 调用对象
	private KiddProviderBean providerBean;
	// 处理的内容
	private Object request;
	// 返回的内容
	private Object response;
	// 异常内容
	private String errorMsg;

	public KiddProviderBean getProviderBean() {
		return providerBean;
	}

	public void setProviderBean(KiddProviderBean providerBean) {
		this.providerBean = providerBean;
	}

	public Object getRequest() {
		return request;
	}

	public void setRequest(Object request) {
		this.request = request;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
