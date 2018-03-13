package com.yatang.sc.juban.service.kidd;

import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.InterfaceProviderTypeEnum;
import com.busi.kidd.annotation.KiddCaller;
import com.yatang.sc.juban.dto.saleOrder.JubanSaleOrderRequestDto;
import com.yatang.sc.juban.dto.saleOrder.JubanSaleOrderResponseDto;
import org.springframework.stereotype.Service;

/**
 * @描述:
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/23 9:42
 * @版本: v1.0
 */
@Service
public class JubanSaleOrderProxyService {

    /**
     *
     * 新增销售订单
     *
     * @param requestDto
     * @return
     */
    @KiddCaller(providerType = InterfaceProviderTypeEnum.JUBAN, method = "stockout.create", callType = CallTypeEnum.SYNC_CALL)
    public JubanSaleOrderResponseDto add(JubanSaleOrderRequestDto requestDto) {
        return null;
    }


    /**
     * 发货接口
     * @param requestDto
     * @return
     */
    @KiddCaller(providerType = InterfaceProviderTypeEnum.JUBAN, method = "deliveryorder.create", callType = CallTypeEnum.SYNC_CALL)
    public JubanSaleOrderResponseDto commonAdd(JubanSaleOrderRequestDto requestDto) {
        return null;
    }

}
