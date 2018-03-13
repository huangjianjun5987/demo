package com.busi.kidd.processor;

import com.busi.kidd.KiddException;
import com.busi.kidd.bean.KiddProviderBean;

/**
 * 提供方处理类<br>
 * 此接口实现公共业务逻辑,包含安全认证,行为记录
 * 
 * @author yangqingsong
 *
 */
public interface KiddProviderProcessor<T extends KiddProviderProcessorBean> {

	public boolean isProcess(KiddProviderBean providerBean);

	public void init();

	/**
	 * 调用业务逻辑方法前
	 * 
	 * @param wrapper
	 * @throws KiddException
	 */
	public void preProcess(T wrapper) throws KiddException;

	/**
	 * 调用业务逻辑方法后
	 * 
	 * @param wrapper
	 * @throws KiddException
	 */
	public void postProcess(T wrapper);
}
