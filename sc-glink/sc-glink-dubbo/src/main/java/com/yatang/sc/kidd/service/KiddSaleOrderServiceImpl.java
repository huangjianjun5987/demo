package com.yatang.sc.kidd.service;

import com.busi.common.resp.Response;
import com.yatang.sc.kidd.dto.orderCancel.KiddCancelAllOrdersDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderCancelDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ResultEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @描述: 订单中间监听事件转发服务
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/9/25 10:58
 * @版本: v1.0
 */
@Service("kiddSaleOrderService")
public class KiddSaleOrderServiceImpl implements KiddSaleOrderService {

    @Autowired
    private ResultEventPublisher publisher;

    @Override
    public Response<String> add(KiddSaleOrderDto saleOrderDto) {
        return (Response<String>) publisher.publishEvent(saleOrderDto, false);
    }
}
