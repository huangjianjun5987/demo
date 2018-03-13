package com.busi.kidd.protocol;

import com.busi.kidd.bean.KiddCallerBean;

/**
 * 接口调用bean
 * 
 * @author yangqingsong
 *
 */
public class CallerProtocolBean<T> {

	private KiddCallerBean kiddCallerBean;
	private T data;

	public KiddCallerBean getKiddCallerBean() {
		return kiddCallerBean;
	}

	public void setKiddCallerBean(KiddCallerBean kiddCallerBean) {
		this.kiddCallerBean = kiddCallerBean;
	}

	public T getData() {
		return data;
	}

	public void setData(T date) {
		this.data = date;
	}

}
