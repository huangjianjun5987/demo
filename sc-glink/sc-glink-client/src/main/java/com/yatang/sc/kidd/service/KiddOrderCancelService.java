package com.yatang.sc.kidd.service;

import com.busi.common.resp.Response;
import com.yatang.sc.kidd.dto.orderCancel.KiddCancelAllOrdersDto;

/**
 * @描述:订单取消服务（支持批量）
 * @类名:OrderCancelService
 * @作者: lvheping
 * @创建时间: 2017/9/26 13:41
 * @版本: v1.0
 */

public interface KiddOrderCancelService {

    /**
     * 取消订单
     * @param saleOrderCancelDto
     * @return
     */
    Response<String> cancel(KiddCancelAllOrdersDto saleOrderCancelDto);
}
