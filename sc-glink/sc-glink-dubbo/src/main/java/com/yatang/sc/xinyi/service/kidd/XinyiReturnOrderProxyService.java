package com.yatang.sc.xinyi.service.kidd;

import org.springframework.stereotype.Service;

import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.InterfaceProviderTypeEnum;
import com.busi.kidd.annotation.KiddCaller;
import com.yatang.sc.xinyi.dto.ResponseDto;
import com.yatang.sc.xinyi.dto.returnrequest.XinyiReturnOrderRequestDto;

/**
 * @描述:调用心怡退货接口
 * @类名:XinyiReturnOrderProxyService
 * @作者: lvheping
 * @创建时间: 2017/10/18 13:57
 * @版本: v1.0
 */
@Service("xinyiReturnOrderProxyService")
public class XinyiReturnOrderProxyService {

    @KiddCaller(providerType = InterfaceProviderTypeEnum.XINYI, method = "returnorder.create", callType = CallTypeEnum.SYNC_CALL)
    public ResponseDto create(XinyiReturnOrderRequestDto xinyiReturnOrderRequestDto) {
        return null;
    }

}
