package com.yatang.sc.purchase.dubboservice;
import com.busi.common.resp.Response;
import com.yatang.sc.purchase.dto.OrderDto;

import java.util.Map;

/**
 * @描述:
 * @类名:
 * @作者:baiyun
 * @创建时间:2017/7/10 14:42
 * @版本:v1.0
 */
public interface QueryOrderDetailDubboService {

    Response<OrderDto> queryOrderDetaiById(String id);

    /**
     * 根据订单id 查询订单的总金额
     * @param id
     * @return
     */
    Response<Map<String,Object>> queryPaymentTotal(String id);


    Response<String> queryUserIdByOrderId(String id);
}
