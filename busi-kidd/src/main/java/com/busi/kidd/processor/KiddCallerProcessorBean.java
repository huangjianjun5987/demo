package com.busi.kidd.processor;

import com.busi.kidd.bean.KiddCallerBean;

/**
 * 调用第三方协议Bean顶级类
 * 
 * @author yangqingsong
 *
 */
public class KiddCallerProcessorBean {
	// 调用对象
	private KiddCallerBean callerBean;
	private Object data;

	public KiddCallerBean getCallerBean() {
		return callerBean;
	}

	public void setCallerBean(KiddCallerBean callerBean) {
		this.callerBean = callerBean;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
