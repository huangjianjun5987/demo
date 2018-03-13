package com.yatang.sc.glink.service.kidd;

import com.busi.kidd.annotation.CallTypeEnum;
import com.busi.kidd.annotation.KiddCaller;
import com.yatang.sc.glink.dto.CancelAllOrdersDto;
import com.yatang.sc.glink.dto.CancelAllOrdersRepDto;
import com.yatang.sc.glink.dto.GlinkResponseDto;
import org.springframework.stereotype.Service;

/**
 * @描述:际链订单取消(采购单和销售单)关闭服务实现
 * @类名:GLinkOrderCancelProxyService
 * @作者: lvheping
 * @创建时间: 2017/9/26 14:35
 * @版本: v1.0
 */
@Service(value = "gLinkOrderCancelProxyService")
public class GLinkOrderCancelProxyService {

    /**
     * 订单取消(采购单和销售单)
     *
     * @param cancelAllOrdersDto
     * @return
     */
    @KiddCaller(method = "g2matrix.supplydistribute.cancelAllOrders", callType = CallTypeEnum.SYNC_CALL)
    public GlinkResponseDto<CancelAllOrdersRepDto> cancel(CancelAllOrdersDto cancelAllOrdersDto) {
        return null;
    }


}
