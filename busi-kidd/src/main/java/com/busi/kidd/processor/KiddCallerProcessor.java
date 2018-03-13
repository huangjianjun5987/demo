package com.busi.kidd.processor;

import com.busi.kidd.KiddException;
import com.busi.kidd.bean.KiddCallerBean;

/**
 * 调用处理类<br>
 * 此接口实现调用第三方的逻辑,包含安全认证,序列化,重试等机制
 * 
 * @author yangqingsong
 *
 */
public interface KiddCallerProcessor<T extends KiddCallerProcessorBean> {

	public boolean isProcess(KiddCallerBean callerBean);

	public void init();

	public Object process(T wrapper) throws KiddException;
}
