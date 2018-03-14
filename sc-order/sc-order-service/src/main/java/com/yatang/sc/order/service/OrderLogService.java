package com.yatang.sc.order.service;

import com.yatang.sc.order.domain.OrderLog;

/**
 * Created by xiangyonghong on 2017/7/18.
 */
public interface OrderLogService {

    void save(OrderLog orderLog);

    void saveOrderLog(String orderId, Short orderState, String description, String operator);
}
