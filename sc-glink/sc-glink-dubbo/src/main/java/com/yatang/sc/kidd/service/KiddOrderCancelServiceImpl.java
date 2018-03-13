package com.yatang.sc.kidd.service;

import com.busi.common.resp.Response;
import com.yatang.sc.kidd.dto.orderCancel.KiddCancelAllOrdersDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ResultEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @描述:订单取消服务实现类
 * @类名:KiddOrderCancelServiceImpl
 * @作者: lvheping
 * @创建时间: 2017/9/26 13:44
 * @版本: v1.0
 */
@Service(value = "kiddOrderCancelService")
public class KiddOrderCancelServiceImpl implements KiddOrderCancelService {
    @Autowired
    private ResultEventPublisher publisher;
    /**
     * 取消订单
     * @param saleOrderCancelDto
     * @return
     */
    @Override
    public Response<String> cancel(KiddCancelAllOrdersDto saleOrderCancelDto) {
        return (Response<String>) publisher.publishEvent(saleOrderCancelDto, false);
    }
}
