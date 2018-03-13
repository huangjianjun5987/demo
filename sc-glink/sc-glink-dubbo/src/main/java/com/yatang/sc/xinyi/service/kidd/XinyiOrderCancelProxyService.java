package com.yatang.sc.xinyi.service.kidd;

import org.springframework.stereotype.Service;

import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.InterfaceProviderTypeEnum;
import com.busi.kidd.annotation.KiddCaller;
import com.yatang.sc.xinyi.dto.ResponseDto;
import com.yatang.sc.xinyi.dto.orderCancel.OrderCancelRequestDto;

/**
 * @描述:心怡仓库取消订单
 * @类名:XinyiOrderCancelProxyService
 * @作者: lvheping
 * @创建时间: 2017/9/26 13:51
 * @版本: v1.0
 */
@Service(value = "xinyiOrderCancelProxyService")
public class XinyiOrderCancelProxyService {
    /**
     * http://api.cangyibao.com/doc/?docs=api%E6%96%87%E6%A1%A3%E8%AF%B4%E6%98%8E/2-%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E/2-12%E5%8D%95%E6%8D%AE%E5%8F%96%E6%B6%88%E6%8E%A5%E5%8F%A3
     * 取消销售订单
     *
     * @param requestDto
     * @return
     */
    @KiddCaller(providerType = InterfaceProviderTypeEnum.XINYI, method = "order.cancel", callType = CallTypeEnum.SYNC_CALL)
    public ResponseDto cancel(OrderCancelRequestDto requestDto) {
        return null;
    }
}
