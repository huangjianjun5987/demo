package com.busi.kidd.bean;

import java.io.Serializable;

import com.busi.kidd.annotation.ProviderTypeEnum;

/**
 * Caller对象
 * 
 * @author yangqingsong
 *
 */
public class KiddProviderBean implements Serializable {
	private static final long serialVersionUID = 1693901408527867876L;

	private ProviderTypeEnum providerType;
	// 接口方法名
	private String interfaceName;

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public ProviderTypeEnum getProviderType() {
		return providerType;
	}

	public void setProviderType(ProviderTypeEnum providerType) {
		this.providerType = providerType;
	}

	/**
	 * 表示null的接口<br>
	 */
	public static class Null extends KiddProviderBean {
		private static final long serialVersionUID = -7582433090026756973L;

		@Override
		public String toString() {
			return "Null []";
		}

	}
}
