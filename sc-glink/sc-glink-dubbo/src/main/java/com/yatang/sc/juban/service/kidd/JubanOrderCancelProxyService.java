package com.yatang.sc.juban.service.kidd;

import org.springframework.stereotype.Service;

import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.InterfaceProviderTypeEnum;
import com.busi.kidd.annotation.KiddCaller;
import com.yatang.sc.juban.dto.ResponseDto;
import com.yatang.sc.juban.dto.orderCancel.JubanOrderCancelRequestDto;

/**
 * @描述:桔瓣仓库取消订单
 * @类名:XinyiOrderCancelProxyService
 * @作者: lvheping
 * @创建时间: 2017/9/26 13:51
 * @版本: v1.0
 */
@Service(value = "jubanOrderCancelProxyService")
public class JubanOrderCancelProxyService {
    /**
     * 取消销售订单
     *
     * @param requestDto
     * @return
     */
    @KiddCaller(providerType = InterfaceProviderTypeEnum.JUBAN, method = "order.cancel", callType = CallTypeEnum.SYNC_CALL)
    public ResponseDto cancel(JubanOrderCancelRequestDto requestDto) {
        return null;
    }
}
