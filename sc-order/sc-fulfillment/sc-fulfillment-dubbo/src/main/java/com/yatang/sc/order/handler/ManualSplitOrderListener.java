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
import com.yatang.sc.order.split.SplitOrderRequestDto;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.PaymentStates;
import com.yatang.sc.order.states.ShippingStates;
import com.yatang.sc.purchase.dubboservice.SplitOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("manualSplitOrderListener")
public class ManualSplitOrderListener extends OrderMsgListener {
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
        if (!validate(order)) {
            log.info("当前订单:{} 不满足拆单要求", msg.getOrderId());
            return;
        }
        SplitOrderRequestDto groups = SplitOrderRequestDto.fromJson(msg.getBody());

        boolean canSplit = orderInventoryHelper.canSplitOrder(order);
        log.info("手动拆单处理:订单是否可拆单:{}->{}", order.getId(), canSplit);
        if (canSplit) {
            Response<Boolean> response = mSplitOrderService.manualSplitOrderByInventory(mainOrderId, groups);
            log.info("{}:手动拆单处理结果：{}", mainOrderId, JSON.toJSONString(response));
            if (!response.isSuccess()) {
                throw new MqBizFailureException("手动拆单处理结果");
            }
        } else {
            order.setPendingSplit(false);
            mOrderService.update(order);
        }
    }

    @Override
    public boolean isCare(OrderMessage msg) {
        return OrderMessageType.MANUAL_SPLIT_ORDER.equals(msg.getMssageType());
    }

    protected boolean validate(Order pOrder) {
        if (OrderStates.SPLITED_ORDER.equalsIgnoreCase(pOrder.getOrderState())) {
            log.info("订单已经拆过单，不允许再拆:{}", pOrder.getOrderState());
            return false;
        }
        if (ShippingStates.NO_INVENTORY.equalsIgnoreCase(pOrder.getShippingState())) {
            return true;
        }
        if (ShippingStates.PENDING_TRANSFER.equalsIgnoreCase(pOrder.getShippingState()) && !pOrder.getInteractivePendingRes()//物流：未传送
                && OrderStates.APPROVED.equalsIgnoreCase(pOrder.getOrderState()) //订单：已审核
                && (PaymentStates.DEBITED.equalsIgnoreCase(pOrder.getPaymentState()) || PaymentStates.GSN.equalsIgnoreCase(pOrder.getPaymentState()))) {//支付：已支付
            return true;
        }
        return false;
    }
}
