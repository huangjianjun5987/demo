package com.busi.kidd.protocol;

import com.busi.kidd.KiddException;

/**
 * 接口调用组件<br>
 * 实现常用的接口调用逻辑
 * 
 * @author yangqingsong
 *
 */
public interface KiddCallerProtocol<T, K> {

	/**
	 * 调用接口
	 * 
	 * @param wrapper
	 */
	public K call(CallerProtocolBean<T> interfaceCallerBean) throws KiddException;
}
