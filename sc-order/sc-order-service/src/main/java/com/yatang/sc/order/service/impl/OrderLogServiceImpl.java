package com.yatang.sc.order.service.impl;

import com.yatang.sc.order.dao.OrderLogDao;
import com.yatang.sc.order.domain.OrderLog;
import com.yatang.sc.order.service.OrderLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by xiangyonghong on 2017/7/18.
 */
@Service
public class OrderLogServiceImpl implements OrderLogService {

    @Autowired
    private OrderLogDao dao;

    @Override
    public void save(OrderLog orderLog) {
        dao.insert(orderLog);
    }

    @Override
    public void saveOrderLog(String orderId, Short orderState, String description, String operator) {
        OrderLog log = new OrderLog();
        log.setOrderId(orderId);
        log.setOrderState(orderState);
        log.setCreateDate(new Date());
        log.setDescription(description);
        log.setOperater(operator);
        dao.insert(log);
    }


}
