package com.busi.kidd.processor.caller;

import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.InterfaceProviderTypeEnum;
import com.busi.kidd.bean.KiddCallerBean;

/**
 * @描述: 同步调用逻辑处理类<br>基于md5+json+http组成
 * @类名:* JubanSyncCallerProcessor
 * @作者: lvheping
 * @创建时间: 2017/11/24 14:07
 * @版本: v1.0
 */

public class JubanSyncCallerProcessor extends XinyiSyncCallerProcessor {
    @Override
    public boolean isProcess(KiddCallerBean callerBean) {
        if (!InterfaceProviderTypeEnum.JUBAN.equals(callerBean.getInterfaceProviderTypeEnum())) {
            return false;
        }
        if (CallTypeEnum.SYNC_CALL.equals(callerBean.getCallType())) {
            return true;
        }
        return false;
    }
}
