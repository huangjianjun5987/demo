package com.yatang.sc.kidd.service;

import com.busi.common.resp.Response;
import com.yatang.sc.kidd.dto.returnrequest.KiddReturnOrderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ResultEventPublisher;
import org.springframework.stereotype.Service;

/**
 * @描述:调用际链心怡退货接口
 * @类名:KiddReturnOrderServiceImpl
 * @作者: lvheping
 * @创建时间: 2017/10/18 11:47
 * @版本: v1.0
 */
@Service(value = "kiddReturnOrderService")
public class KiddReturnOrderServiceImpl implements KiddReturnOrderService {
    @Autowired
    private ResultEventPublisher publisher;
    /**
     * 创建退货单给仓库
     *
     * @param kiddReturnOrderDto
     * @return
     */
    @Override
    public Response<String> create(KiddReturnOrderDto kiddReturnOrderDto) {
        Response<String> response = (Response<String>) publisher.publishEvent(kiddReturnOrderDto);
        return response;
    }
}
