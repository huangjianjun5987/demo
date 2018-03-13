package com.yatang.sc.kidd.service;

import com.busi.common.resp.Response;
import com.yatang.sc.kidd.dto.orderCancel.KiddCancelAllOrdersDto;
import com.yatang.sc.kidd.dto.purchase.KiddEntryOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ResultEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @描述:推送采购订单中间服务实现类
 * @类名:KiddPurchaseServiceImpl
 * @作者: lvheping
 * @创建时间: 2017/9/25 10:37
 * @版本: v1.0
 */
@Service(value = "kiddPurchaseService")
public class KiddPurchaseServiceImpl implements KiddPurchaseService {
    @Autowired
    private ResultEventPublisher publisher;
    /**
     * 新增采购订单（入库单）
     *
     * @param kiddEntryOrderDto
     * @return
     */
    @Override
    public Response<String> add(KiddEntryOrderDto kiddEntryOrderDto) {
        Response<String> response = (Response<String>) publisher.publishEvent(kiddEntryOrderDto);
        return response;
    }

}
