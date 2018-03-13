package com.yatang.sc.kidd.service;

import com.busi.common.resp.Response;
import com.busi.kidd.annotation.InterfaceProviderTypeEnum;
import com.google.common.base.Objects;
import org.springframework.context.event.ResultEventNoListeningException;

public abstract class KiddUtils {

    public static void checkGlinkListening(Response<String> interfaceProvider) {
        checkInterfaceProvider(interfaceProvider);
        if (!Objects.equal(interfaceProvider.getResultObject(), InterfaceProviderTypeEnum.GLINK.getValue())) {
            throw new ResultEventNoListeningException();
        }
    }

    public static void checkXinyiListening(Response<String> interfaceProvider) {
        checkInterfaceProvider(interfaceProvider);
        if (!Objects.equal(interfaceProvider.getResultObject(), InterfaceProviderTypeEnum.XINYI.getValue())) {
            throw new ResultEventNoListeningException();
        }
    }

    public static void checkJubanListening(Response<String> interfaceProvider) {
        checkInterfaceProvider(interfaceProvider);
        if (!Objects.equal(interfaceProvider.getResultObject(), InterfaceProviderTypeEnum.JUBAN.getValue())) {
            throw new ResultEventNoListeningException();
        }
    }


    private static void checkInterfaceProvider(Response<String> interfaceProvider) {
        if (!interfaceProvider.isSuccess()) {
            throw new RuntimeException("获取仓库第三方接口提供商失败");
        }
        if (interfaceProvider.getResultObject() == null) {
            throw new RuntimeException("仓库未配置接口提供商");
        }
    }


}


