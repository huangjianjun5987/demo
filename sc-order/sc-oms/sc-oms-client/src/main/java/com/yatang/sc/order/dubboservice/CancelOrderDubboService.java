package com.yatang.sc.order.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.dto.CancelOrderRequestDto;

import java.util.List;
import java.util.Map;

/**
 * Created by yuwei on 2017/7/13.
 * 取消订单接口
 */
public interface CancelOrderDubboService {
    Response<String> cancelOrder(CancelOrderRequestDto pCancelOrderRequestDto);

    Response<Map<String, String>> batchCancelOrder(List<CancelOrderRequestDto> pCancelOrderRequestDtos);
}
