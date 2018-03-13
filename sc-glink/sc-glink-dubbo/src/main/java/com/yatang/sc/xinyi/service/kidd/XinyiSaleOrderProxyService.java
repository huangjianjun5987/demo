package com.yatang.sc.xinyi.service.kidd;

import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.InterfaceProviderTypeEnum;
import com.busi.kidd.annotation.KiddCaller;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderRequestDto;
import com.yatang.sc.xinyi.dto.saleOrder.SaleOrderResponseDto;
import org.springframework.stereotype.Service;

/**
 * @描述:
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/23 9:42
 * @版本: v1.0
 */
@Service("XinyiSaleOrderService")
public class XinyiSaleOrderProxyService {

    /**
     * http://api.cangyibao.com/doc/?docs=api%E6%96%87%E6%A1%A3%E8%AF%B4%E6%98%8E/2-%E6%8E%A5%E5%8F%A3%E8%AF%B4%E6%98%8E/2-5%E5%87%BA%E5%BA%93%E5%8D%95%E5%88%9B%E5%BB%BA%E6%8E%A5%E5%8F%A3
     * 新增销售订单
     *
     * @param requestDto
     * @return
     */
    @KiddCaller(providerType = InterfaceProviderTypeEnum.XINYI, method = "stockout.create", callType = CallTypeEnum.SYNC_CALL)
    public SaleOrderResponseDto add(SaleOrderRequestDto requestDto) {
        return null;
    }


    /**
     * 发货接口
     * @param requestDto
     * @return
     */
    @KiddCaller(providerType = InterfaceProviderTypeEnum.XINYI, method = "deliveryorder.create", callType = CallTypeEnum.SYNC_CALL)
    public SaleOrderResponseDto commonAdd(SaleOrderRequestDto requestDto) {
        return null;
    }

}
