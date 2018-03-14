package com.yatang.sc.service.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.common.utils.PricingUtil;
import com.yatang.sc.dto.PaymentResultDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderExtDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderItemDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseOrderWriteDubboService;
import com.yatang.sc.order.domain.CommerceItem;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.OrderGiveCouponToStoreLog;
import com.yatang.sc.order.domain.OrderPayments;
import com.yatang.sc.order.domain.OrderPrice;
import com.yatang.sc.order.domain.PaymentGroup;
import com.yatang.sc.order.domain.ProviderShippingGroup;
import com.yatang.sc.order.domain.ShippingGroup;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.service.OrderGiveCouponToStoreLogService;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.OrderPaymentsService;
import com.yatang.sc.order.service.OrderPriceService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.service.PaymentGroupService;
import com.yatang.sc.order.service.ProviderShippingGroupService;
import com.yatang.sc.order.service.ShippingGroupService;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.OrderTotalStates;
import com.yatang.sc.order.states.OrderTypes;
import com.yatang.sc.order.states.PayMethod;
import com.yatang.sc.order.states.PaymentStates;
import com.yatang.sc.order.states.ShippingModes;
import com.yatang.sc.order.states.ShippingStates;
import com.yatang.sc.service.PaySuccessHelper;
import com.yatang.xc.oc.b.member.biz.core.dto.LevelPrivilegeDto;
import com.yatang.xc.oc.b.member.biz.core.dto.PrivilegeInfoDto;
import com.yatang.xc.oc.b.member.biz.core.dubboservice.PrivilegeInfoDubboService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PaySuccessHelperImpl implements PaySuccessHelper {

    protected Logger log = LoggerFactory.getLogger(PaySuccessHelperImpl.class);

    @Autowired
    OrderService orderService;

    @Autowired
    OrderMessageSender orderMessageSender;

    @Autowired
    private OrderPaymentsService mOrderPaymentsService;

    @Autowired
    private PaymentGroupService paymentGroupService;

    @Autowired
    OrderLogService orderLogService;

    @Autowired
    private OrderPriceService orderPriceService;

    @Autowired
    private PrivilegeInfoDubboService privilegeInfoDubboService;

    @Autowired
    private OrderGiveCouponToStoreLogService couponToStoreLogService;

    @Autowired
    private ShippingGroupService mShippingGroupService;

    @Autowired
    CommerceItemService commerceItemService;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Override
    public boolean processForPay(PaymentResultDto paymentResult) throws Exception {
        log.info("支付成功通知报文:{}", JSON.toJSONString(paymentResult));

        Order order = orderService.selectByPrimaryKey(paymentResult.getOrderId());
        if (order == null) {
            log.info("订单不存在:{}", paymentResult.getOrderId());
            return false;
        }
        boolean res = saveOrderPaymentInfo(paymentResult, order);
        if (!res) {
            log.info("更新订单支付信息失败:{}", paymentResult.getOrderId());
            return false;
        }
        confirmPay(order.getId());
        return true;

    }

    @Override
    public boolean confirmPay(String orderId) throws Exception {
        Order order = orderService.selectByPrimaryKey(orderId);
        if (order == null) {
            log.info("订单不存在:{}", orderId);
            return false;
        }
        if (OrderTypes.ELECTRONIC_ORDER.equals(order.getOrderType())) {
            handElectronicOrder(order);
            return true;
        }

        if (OrderStates.SPLITED_ORDER.equalsIgnoreCase(order.getOrderState())) {//已拆单主订单支付状态置空
            processForSplitedOrder(orderId);
        } else {
            log.info("发送未拆分的订到WMS:{}",order.getId());
            sendOrderToWms(order);
        }
        saveOrderDiscount(order);
        return false;
    }

    protected void handElectronicOrder(Order pOrder) {
        log.info("handElectronicOrder:{},{},{}", pOrder.getOrderState(), pOrder.getShippingState(), pOrder.getPaymentState());
        if (OrderStates.APPROVED.equals(pOrder.getOrderState())
                && ShippingStates.PENDING_PROCESS.equals(pOrder.getShippingState())
                && pOrder.getPaymentState().equals(PaymentStates.DEBITED)
                && !pOrder.getInteractivePendingRes()) {
            OrderMessage deoMessage = new OrderMessage();
            deoMessage.setMssageType(OrderMessageType.DeliveryElectronicOrder);
            deoMessage.setOrderId(pOrder.getId());
            orderMessageSender.sendMsg(deoMessage);
        }
    }

    protected boolean saveOrderPaymentInfo(PaymentResultDto paymentResult, Order order) {
        log.info("start process order payment with  order id:{} ,transId:{} ,payChannel:{}", paymentResult.getOrderId(), paymentResult.getTransNum(), paymentResult.getPayChannel());
        boolean rollback = false;
        if (PaymentStates.DEBITED.equals(order.getPaymentState())) {
            log.info("订单已支付，直接返回成功:{}", order.getId());
            return true;
        }

        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setTimeout(300);
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = transactionManager.getTransaction(transactionDefinition);
        try {
            if (!PaymentStates.PENDING_PAYMENT.equals(order.getPaymentState())
                    && !OrderStates.SPLITED_ORDER.equalsIgnoreCase(order.getOrderState())) {
                log.info("订单状态不正确！当前订单：{} 状态为:{},支付状态：{}", order.getId(), order.getOrderState(), order.getPaymentState());
                return false;
            }

            mOrderPaymentsService.savePaymentInfo(order.getId(), paymentResult.getPayAmount(), paymentResult.getPayDate(), paymentResult.getPayChannel(), paymentResult.getTransNum(), paymentResult.getPayRecordNo(), paymentResult.getOperator());

            order.setPaymentState(PaymentStates.DEBITED);
            order.setState(OrderTotalStates.PENDING_PROCESS.getStateValue());
            order.setStateDetail(OrderTotalStates.PENDING_PROCESS.getDescription());
            orderService.updateOrderSelective(order);
            orderLogService.saveOrderLog(order.getId(), order.getState(), "订单已支付！", "system");
            return true;
        } catch (Exception ex) {
            rollback = true;
            log.error("库存预留异常", ex);
            throw ex;
        } finally {
            if (rollback) {
                transactionManager.rollback(txStatus);
            } else {
                transactionManager.commit(txStatus);
            }
        }
    }

    protected void processForSplitedOrder(String orderId) throws Exception {
        Order order = orderService.selectByPrimaryKey(orderId);
        log.info("订单{}状态为已拆单状态，拆分支付信息", order.getId());
        boolean rollback = false;

        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setTimeout(300);
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = transactionManager.getTransaction(transactionDefinition);
        List<Order> subOrders = orderService.getSubOrders(order.getId());
        try {
            order.setShippingState("");
            order.setPaymentState("");
            orderService.update(order);

            if (CollectionUtils.isEmpty(subOrders)) {
                log.error("订单{}状态为已拆单，但是未查询到任何子订单", order.getId());
                return;
            }
            List<OrderPayments> paymentGroups = mOrderPaymentsService.selectByOrderId(order.getId());
            if (CollectionUtils.isEmpty(paymentGroups)) {
                log.info("订单支付信息不存在:{}", order.getId());
            } else {
                List<OrderPayments> orderPayments = paymentGroups;
                PaymentGroup paymentGroup = null;
                for (OrderPayments orderPayment : orderPayments) {
                    paymentGroup = paymentGroupService.getPaymentGroupForId(orderPayment.getPaymentGroupId());
                    if (PayMethod.PYT_ONLINE.equals(paymentGroup.getPaymentMethod())) {//找到在线支付类型的paymentGroup
                        break;
                    }
                }

                if (paymentGroup == null) {
                    log.info("订单PaymentGroup不存在:{}", order.getId());
                    return;
                }

                OrderPrice parentOrderPrice;
                OrderPrice subOrderPrice;
                Double amountRatio;
                for (Order subOrder : subOrders) {
                    if (PaymentStates.DEBITED.equals(subOrder.getPaymentState())) {
                        log.info("子订单已支付状态，跳过:{}", subOrder.getId());
                        continue;
                    }
                    parentOrderPrice = orderPriceService.getOrderPriceForId(order.getPriceInfo());
                    subOrderPrice = orderPriceService.getOrderPriceForId(subOrder.getPriceInfo());
                    amountRatio = subOrderPrice.getRawSubtotal() / parentOrderPrice.getRawSubtotal();
                    log.info("订单支付成功，拆单，子订单占比金额：{}:{}->{}", parentOrderPrice.getRawSubtotal(), subOrderPrice.getRawSubtotal(), amountRatio);
                    mOrderPaymentsService.savePaymentInfo(subOrder.getId(), PricingUtil.roundPrice(paymentGroup.getAmount() * amountRatio), paymentGroup.getPayDate(), paymentGroup.getChannel(), paymentGroup.getTransNum(), paymentGroup.getPayRecordNo(), paymentGroup.getPayAccount());
                    subOrder.setPaymentState(PaymentStates.DEBITED);
                    subOrder.setState(OrderTotalStates.PENDING_PROCESS.getStateValue());
                    subOrder.setStateDetail(OrderTotalStates.PENDING_PROCESS.getDescription());
                    orderService.updateOrderSelective(subOrder);
                    orderLogService.saveOrderLog(subOrder.getId(), subOrder.getState(), "订单已支付！", "system");
                }
            }
        } catch (Exception ex) {
            rollback = true;
            log.error("库存预留异常", ex);
            throw ex;
        } finally {
            if (rollback) {
                transactionManager.rollback(txStatus);
            } else {
                transactionManager.commit(txStatus);
            }
        }
        subOrders = orderService.getSubOrders(order.getId());
        for (Order subOrder : subOrders) {
            sendOrderToWms(subOrder);
        }
    }

    protected void sendOrderToWms(Order pOrder) throws Exception {
        log.info("发送订单到wms:{},{},{},{}",pOrder.getId(), pOrder.getOrderState(), pOrder.getShippingState(), pOrder.getPaymentState());
        OrderMessage wmsMessage = new OrderMessage();
        if (OrderStates.APPROVED.equals(pOrder.getOrderState())
                && ShippingStates.PENDING_TRANSFER.equals(pOrder.getShippingState())
                && pOrder.getPaymentState().equals(PaymentStates.DEBITED)
                && !pOrder.getInteractivePendingRes()) {

            wmsMessage.setMssageType(OrderMessageType.SendWMSDeliveryMessageCheck);
            wmsMessage.setOrderId(pOrder.getId());
            orderMessageSender.sendMsg(wmsMessage);
        }
    }

    protected void saveOrderDiscount(Order order) {
        Response<LevelPrivilegeDto> privilegeInfo = privilegeInfoDubboService.getPrivilegeInfoByMemberCode(order.getProfileId(), "QY_system_gyl");
        if (privilegeInfo == null || privilegeInfo.getResultObject() == null || privilegeInfo.getResultObject().getPrivilegeInfoDtoList() == null || privilegeInfo.getResultObject().getPrivilegeInfoDtoList().isEmpty()) {
            return;
        }
        PrivilegeInfoDto privilegeInfoDto = privilegeInfo.getResultObject().getPrivilegeInfoDtoList().get(0);
        if (privilegeInfoDto == null) {
            return;
        }
        float discountNumeric = privilegeInfoDto.getDiscountNumeric();
        OrderGiveCouponToStoreLog orderLog = new OrderGiveCouponToStoreLog();
        orderLog.setDiscount(discountNumeric);
        orderLog.setPriceInfoId(order.getPriceInfo());
        orderLog.setCreationTime(new Date());
        couponToStoreLogService.save(orderLog);
    }
}
