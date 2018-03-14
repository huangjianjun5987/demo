package com.yatang.sc.service.impl;

import com.yatang.sc.order.OrderInventoryHelper;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.OrderItems;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.service.OrderItemsService;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.OrderPriceService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.OrderTypes;
import com.yatang.sc.order.states.PaymentStates;
import com.yatang.sc.order.states.ShippingStates;
import com.yatang.sc.service.ReserveOrderInventoryHelper;
import com.yatang.sc.vo.UpdateOrderVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ReserveOrderInventoryHelperImpl implements ReserveOrderInventoryHelper {
    protected Logger log = LoggerFactory.getLogger(ReserveOrderInventoryHelperImpl.class);

    @Autowired
    OrderService orderService;

    @Autowired
    OrderMessageSender orderMessageSender;

    @Autowired
    OrderLogService orderLogService;

    @Autowired
    OrderInventoryHelper orderInventoryHelper;

    @Autowired
    OrderItemsService orderItemsService;

    @Autowired
    CommerceItemService commerceItemService;

    @Autowired
    OrderPriceService orderPriceService;

    @Override
    public boolean reserve(Order order, boolean splitByInventory, String trigger) {
        log.info("订单:{} 预留库存：{}", order.getId(), splitByInventory);
        List<Long> itemIds = new ArrayList<Long>();
        List<OrderItems> orderItems = orderItemsService.getOrderItemsForOrderId(order.getId());
        for (OrderItems orderItem : orderItems) {
            itemIds.add(orderItem.getCommerceItemId());
        }
        if (OrderTypes.ELECTRONIC_ORDER.equals(order.getOrderType())) {
            log.info("虚拟订单：{}，无需预留库存", order.getId());
            return true;
        }
        UpdateOrderVO updateOrderVO = new UpdateOrderVO();
        updateOrderVO.setOrder(order);
        updateOrderVO.setPreOrderState(order.getState());
        if ((ShippingStates.PENDING_PROCESS.equals(order.getShippingState())
                || ShippingStates.NO_INVENTORY.equals(order.getShippingState()))) {
            orderInventoryHelper.reserveOrderInventory(order, splitByInventory);
            log.info("订单：{} 库存预留完", order.getId());
        } else {
            log.info("{}订单状态不正确：{},{}", order.getId(), order.getOrderState(), order.getShippingState());
            return false;
        }
        return true;
    }
}
