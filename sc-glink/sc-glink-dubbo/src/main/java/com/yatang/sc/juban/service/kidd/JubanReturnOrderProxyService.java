package com.yatang.sc.juban.service.kidd;

import org.springframework.stereotype.Service;

import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.InterfaceProviderTypeEnum;
import com.busi.kidd.annotation.KiddCaller;
import com.yatang.sc.juban.dto.returnrequest.JubanReturnOrderRequestDto;
import com.yatang.sc.juban.dto.ResponseDto;

/**
 * @描述:调用桔瓣退货接口
 * @类名:XinyiReturnOrderProxyService
 * @作者: lvheping
 * @创建时间: 2017/10/18 13:57
 * @版本: v1.0
 */
@Service("jubanReturnOrderProxyService")
public class JubanReturnOrderProxyService {

    @KiddCaller(providerType = InterfaceProviderTypeEnum.JUBAN, method = "returnorder.create", callType = CallTypeEnum.SYNC_CALL)
    public ResponseDto create(JubanReturnOrderRequestDto jubanReturnOrderRequestDto) {
        return null;
    }

}
