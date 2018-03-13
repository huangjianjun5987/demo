package com.busi.kidd.processor.caller;

import com.busi.kidd.KiddException;
import com.busi.kidd.KiddFrameKernel;
import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.bean.KiddCallerBean;
import com.busi.kidd.processor.KiddCallerProcessorBean;

/**
 * 异步调用逻辑处理类
 * 
 * @author yangqingsong
 *
 */
public class AsyncCallerProcessor extends SyncCallerProcessor {

	@Override
	public void init() {
		KiddFrameKernel.INSTANCE.registry(this);
	}

	@Override
	public boolean isProcess(KiddCallerBean callerBean) {
		if (CallTypeEnum.ASYNC_CALL.equals(callerBean.getCallType())) {
			return true;
		}
		return false;
	}

	/**
	 * 在原有基础上添加监控
	 */
	@Override
	public Object process(KiddCallerProcessorBean wrapper) throws KiddException {
		Object result = super.process(wrapper);
		return result;
	}

}
