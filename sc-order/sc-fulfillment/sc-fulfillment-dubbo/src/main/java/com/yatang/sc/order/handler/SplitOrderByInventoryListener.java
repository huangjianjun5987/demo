package com.yatang.sc.order.handler;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.exception.MqBizFailureException;
import com.yatang.sc.order.OrderInventoryHelper;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.split.SplitOrderItemDto;
import com.yatang.sc.order.split.SplitOrderRequestDto;
import com.yatang.sc.order.split.SplitOrderSubGroupDto;
import com.yatang.sc.order.split.enums.SubOrderType;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.ShippingStates;
import com.yatang.sc.purchase.dubboservice.SplitOrderService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("splitOrderListener")
public class SplitOrderByInventoryListener extends OrderMsgListener {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SplitOrderService mSplitOrderService;

    @Autowired
    private OrderService mOrderService;

    @Autowired
    CommerceItemService commerceItemService;

    @Autowired
    OrderInventoryHelper orderInventoryHelper;

    @Override
    public void handle(OrderMessage msg) throws MqBizFailureException {
        log.info("拆单消息：{}", JSON.toJSONString(msg));

        String mainOrderId = msg.getOrderId();
        Order order = mOrderService.selectByPrimaryKey(mainOrderId);
        if (order == null) {
            log.info("主订单不存在:{}", msg.getOrderId());
            return;
        }
        if (!validate(order, OrderMessageType.SPLIT_ORDER_BY_INVENTORY_ONLY_UPDATE_STATE.equals(msg.getMssageType()))) {
            log.info("当前订单:{} 不满足拆单要求", msg.getOrderId());
            return;
        }

        SplitOrderRequestDto groups = SplitOrderRequestDto.fromJson(msg.getBody());
        if (groups == null) {
            log.warn("消息反序列化失败:{}", msg.getBody());
            return;
        }

        SplitOrderSubGroupDto enoughItems = groups.getGroupByType(SubOrderType.ENOUGH_STOCK);
        SplitOrderSubGroupDto lessItems = groups.getGroupByType(SubOrderType.LESS_STOCK);
        List<Long> enoughItemIds = new ArrayList<Long>();
        List<Long> lessItemIds = new ArrayList<Long>();
        if (enoughItems != null && CollectionUtils.isNotEmpty(enoughItems.getItems())) {
            for (SplitOrderItemDto item : enoughItems.getItems()) {
                enoughItemIds.add(item.getCommerceId());
            }
        }
        if (lessItems != null && CollectionUtils.isNotEmpty(lessItems.getItems())) {
            for (SplitOrderItemDto item : lessItems.getItems()) {
                lessItemIds.add(item.getCommerceId());
            }
        }
        boolean canSplit = orderInventoryHelper.canSplitOrder(order);
        log.info("库存拆单处理:订单是否可再拆单:{}->{}", order.getId(), canSplit);
        commerceItemService.updateState(enoughItemIds, lessItemIds);
        if (OrderMessageType.SPLIT_ORDER_BY_INVENTORY.equals(msg.getMssageType()) && canSplit) {
            Response<Boolean> response = mSplitOrderService.splitOrderByInventory(mainOrderId, groups);
            log.info("{}:库存拆单处理结果：{}", mainOrderId, JSON.toJSONString(response));
            if (!response.isSuccess()) {
                throw new MqBizFailureException("库存拆单失败");
            }
        } else {
            log.info("less items:{}",JSON.toJSONString(lessItemIds));
            if (CollectionUtils.isNotEmpty(lessItemIds)) {
                order.setShippingState(ShippingStates.NO_INVENTORY);
            }
            order.setPendingSplit(false);
            mOrderService.update(order);
        }
    }

    @Override
    public boolean isCare(OrderMessage msg) {
        return OrderMessageType.SPLIT_ORDER_BY_INVENTORY.equals(msg.getMssageType()) || OrderMessageType.SPLIT_ORDER_BY_INVENTORY_ONLY_UPDATE_STATE.equals(msg.getMssageType());
    }

    protected boolean validate(Order pOrder,boolean onlyUpdateState) {
        if (OrderStates.SPLITED_ORDER.equalsIgnoreCase(pOrder.getOrderState())) {
            log.info("订单已经拆过单，不允许再拆:{}", pOrder.getOrderState());
            return false;
        }
        if(onlyUpdateState){
            return true;
        }
        if (ShippingStates.NO_INVENTORY.equalsIgnoreCase(pOrder.getShippingState())) {
            return true;
        }
        return false;
    }
}
