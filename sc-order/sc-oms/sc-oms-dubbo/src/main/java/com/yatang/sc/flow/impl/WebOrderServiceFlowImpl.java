package com.yatang.sc.flow.impl;

import com.busi.common.utils.StringUtils;
import com.yatang.sc.common.CommonsEnum;
import com.yatang.sc.flow.WebOrderServiceFlow;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.states.OrderStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class WebOrderServiceFlowImpl implements WebOrderServiceFlow {
    private Logger log = LoggerFactory.getLogger(WebOrderServiceFlow.class);

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderLogService orderLogService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean manualSplitOrder(String parentOrderId, List<Map<String, Long>> groups, String operator) throws Exception {
        if (StringUtils.isEmpty(parentOrderId)) {
            return false;
        }

        //根据订单id找到对应的订单
        Order order = orderService.selectByPrimaryKey(parentOrderId);

        if (order.isPendingSplit()) {
            log.error("正在拆单，请勿重复提交拆单请求:{}", order.getId());
            throw new Exception("正在拆单，请勿重复提交拆单请求");
        }

        //如果是已拆单的订单  那么直接return了
        if (OrderStates.SPLITED_ORDER.equalsIgnoreCase(order.getOrderState())) {
            log.error("订单已拆单:{}", order.getId());
            throw new Exception("订单已拆单");
        }
        order.setPendingSplit(true);
        orderService.update(order);

        //保存日志//
        orderLogService.saveOrderLog(parentOrderId, order.getState(), "根据手动分组拆单", operator);
        return true;
    }
}
