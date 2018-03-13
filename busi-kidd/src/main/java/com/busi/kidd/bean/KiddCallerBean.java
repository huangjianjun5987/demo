package com.busi.kidd.bean;

import java.io.Serializable;
import java.lang.reflect.Type;

import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.InterfaceProviderTypeEnum;

/**
 * Caller对象
 * 
 * @author yangqingsong
 *
 */
public class KiddCallerBean implements Serializable {
	private static final long serialVersionUID = 1693901408527867876L;

	// 调用类型
	private InterfaceProviderTypeEnum interfaceProviderTypeEnum;
	// 调用类型
	private CallTypeEnum callType;
	// 接口地址
	private String url;
	// 接口方法名
	private String method;
	// 重试次数
	private int reCallCount;
	// -- 后续实现
	// 异步请求时,回执消息最大等待时间
	// private int asyncReceiptWaitTime;
	// 超出最大回执等待时间,调用的方法名,必须在同一个类中
	// private String callbackMethod;
	// 安全协议单向/双向
	// private SecurityProtocolEnum security;
	// 返回类型
	private Type resultClass;

	public InterfaceProviderTypeEnum getInterfaceProviderTypeEnum() {
		return interfaceProviderTypeEnum;
	}

	public void setInterfaceProviderTypeEnum(InterfaceProviderTypeEnum interfaceProviderTypeEnum) {
		this.interfaceProviderTypeEnum = interfaceProviderTypeEnum;
	}

	public CallTypeEnum getCallType() {
		return callType;
	}

	public void setCallType(CallTypeEnum callType) {
		this.callType = callType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public int getReCallCount() {
		return reCallCount;
	}

	public void setReCallCount(int reCallCount) {
		this.reCallCount = reCallCount;
	}
	// -- 后续实现
	// public int getAsyncReceiptWaitTime() {
	// return asyncReceiptWaitTime;
	// }
	//
	// public void setAsyncReceiptWaitTime(int asyncReceiptWaitTime) {
	// this.asyncReceiptWaitTime = asyncReceiptWaitTime;
	// }
	//
	// public String getCallbackMethod() {
	// return callbackMethod;
	// }
	//
	// public void setCallbackMethod(String callbackMethod) {
	// this.callbackMethod = callbackMethod;
	// }

	public Type getResultClass() {
		return resultClass;
	}

	public void setResultClass(Type resultClass) {
		this.resultClass = resultClass;
	}

	// public SecurityProtocolEnum getSecurity() {
	// return security;
	// }
	//
	// public void setSecurity(SecurityProtocolEnum security) {
	// this.security = security;
	// }

	/**
	 * 表示null的接口<br>
	 */
	public static class Null extends KiddCallerBean {
		private static final long serialVersionUID = -7582433090026756973L;

		@Override
		public String toString() {
			return "Null []";
		}

	}
}
