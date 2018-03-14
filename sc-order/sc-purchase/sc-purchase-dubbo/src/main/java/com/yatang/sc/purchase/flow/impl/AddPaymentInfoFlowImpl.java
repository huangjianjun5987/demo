package com.yatang.sc.purchase.flow.impl;

import com.busi.common.resp.Response;
import com.busi.common.utils.StringUtils;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.OrderPrice;
import com.yatang.sc.order.domain.PaymentGroup;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.OrderPriceService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.service.PaymentGroupService;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.OrderTotalStates;
import com.yatang.sc.order.states.PaymentGroupStates;
import com.yatang.sc.order.states.PaymentStates;
import com.yatang.sc.payment.dto.RefundDto;
import com.yatang.sc.payment.dubbo.service.RefundDubboService;
import com.yatang.sc.payment.enums.RefundStatus;
import com.yatang.sc.purchase.dto.AddPaymentInfoDto;
import com.yatang.sc.purchase.dto.ConfirmPaymentDto;
import com.yatang.sc.purchase.flow.AddPaymentInfoFlow;
import com.yatang.sc.purchase.service.PurchaseHelper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddPaymentInfoFlowImpl implements AddPaymentInfoFlow {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    @Autowired
    private PurchaseHelper purchaseHelper;

    @Autowired
    private OrderLogService orderLogService;

    @Autowired
    private PaymentGroupService paymentGroupService;

    @Autowired
    private RefundDubboService mRefundDubboService;

    @Autowired
    private OrderPriceService orderPriceService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String addPaymentInfo(AddPaymentInfoDto addPaymentInfoDto) throws Exception {
        Order orderRecord = orderService.selectByPrimaryKey(addPaymentInfoDto.getOrderId());
        if (orderRecord == null) {
            throw new Exception("订单不存在");
        }
        if (OrderStates.SPLITED_ORDER.equalsIgnoreCase(orderRecord.getOrderState())) {
            throw new Exception("订单已拆单，不允许添加执行信息");
        }
        OrderPrice orderPrice = orderPriceService.getOrderPriceForId(orderRecord.getPriceInfo());
        if (orderPrice == null) {
            throw new Exception("订单价格信息不存在");
        }

        List<PaymentGroup> paymentGroups = paymentGroupService.getPaymentGroupForOrderId(addPaymentInfoDto.getOrderId());
        PaymentGroup tmpPaymentGroup = new PaymentGroup();
        Double settledAmount = 0D;
        Double refundedAmount = 0D;
        if (CollectionUtils.isNotEmpty(paymentGroups)) {
            for (PaymentGroup paymentGroup : paymentGroups) {
                if (PaymentGroupStates.INITIAL.getStateValue().equals(paymentGroup.getState())) {
                    tmpPaymentGroup = paymentGroup;
                    break;
                }
            }
            for (PaymentGroup paymentGroup : paymentGroups) {
                if (PaymentGroupStates.SETTLED.getStateValue().equals(paymentGroup.getState())) {
                    settledAmount += paymentGroup.getAmount();
                }
            }
        }
        //退款信息
        Response<List<RefundDto>> refundDtos = mRefundDubboService.getRefundByOrderId(addPaymentInfoDto.getOrderId());
        if (CollectionUtils.isNotEmpty(refundDtos.getResultObject())) {
            for (RefundDto refundDto : refundDtos.getResultObject()) {
                if (refundDto.getStatus().equals(RefundStatus.REFUNDED) || refundDto.getStatus().equals(RefundStatus.REFUNDED_CONFIRM)) {
                    refundedAmount += refundDto.getRefundAmount();
                }
            }
        }
        double leftShouldPayAmount = orderPrice.getTotal() - settledAmount + refundedAmount - addPaymentInfoDto.getAmount();
        tmpPaymentGroup.setAmountDebited(addPaymentInfoDto.getAmount());
        tmpPaymentGroup.setAmount(addPaymentInfoDto.getAmount());
        tmpPaymentGroup.setAmountCredited(addPaymentInfoDto.getAmount());
        tmpPaymentGroup.setAmountAuthorized(addPaymentInfoDto.getAmount());
        tmpPaymentGroup.setOrderId(addPaymentInfoDto.getOrderId());
        tmpPaymentGroup.setPayDate(addPaymentInfoDto.getPayDate());
        tmpPaymentGroup.setPayRecordNo("XianXia");
        tmpPaymentGroup.setState(PaymentGroupStates.AUTHORIZED.getStateValue());
        tmpPaymentGroup.setStateDetail(PaymentGroupStates.AUTHORIZED.getDescription());
        tmpPaymentGroup.setChannel(addPaymentInfoDto.getChannel());
        tmpPaymentGroup.setTransNum(addPaymentInfoDto.getTranNum());
        tmpPaymentGroup.setPaymentMethod("offlinePayment");
        tmpPaymentGroup.setComment(addPaymentInfoDto.getComment());
        tmpPaymentGroup.setPayAccount(addPaymentInfoDto.getPayAccount());
        tmpPaymentGroup.setLastOperator(addPaymentInfoDto.getOperator());
        if (StringUtils.isEmpty(tmpPaymentGroup.getId())) {
            logger.info("新增paymentGroup:{}",orderRecord.getId());
            purchaseHelper.savePaymentGroup(tmpPaymentGroup);
            purchaseHelper.saveOrderPayments(tmpPaymentGroup.getId(), addPaymentInfoDto.getOrderId());
        } else {
            logger.info("更新{}paymentGroup:{}",tmpPaymentGroup.getId(),orderRecord.getId());
            paymentGroupService.updatePaymentGroup(tmpPaymentGroup);
        }

        orderLogService.saveOrderLog(addPaymentInfoDto.getOrderId(), orderRecord.getState(), "新增线下支付！", addPaymentInfoDto.getOperator());
        if (leftShouldPayAmount != 0D) {
            logger.info("支付金额与订单总金额不一致：{}", leftShouldPayAmount);
            return "支付金额与订单总金额不一致";
        }
        return "";
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean confirmOfflinePayment(ConfirmPaymentDto confirmPaymentDto) throws Exception {
        Order orderRecord = orderService.selectByPrimaryKey(confirmPaymentDto.getOrderId());
        if (orderRecord == null) {
            throw new Exception("订单不存在");
        }
        PaymentGroup paymentGroup = paymentGroupService.getPaymentGroupForId(confirmPaymentDto.getPaymentId());
        if (paymentGroup == null) {
            throw new Exception("支付信息不存在");
        }
        if (!PaymentGroupStates.AUTHORIZED.getStateValue().equals(paymentGroup.getState())) {
            throw new Exception("支付记录状态错误");
        }

        if (PaymentStates.PENDING_PAYMENT.equalsIgnoreCase(orderRecord.getPaymentState())) {
            orderRecord.setPaymentState(PaymentStates.DEBITED);
            orderRecord.setState(OrderTotalStates.PENDING_PROCESS.getStateValue());
            orderRecord.setStateDetail(OrderTotalStates.PENDING_PROCESS.getDescription());
            orderService.updateOrderSelective(orderRecord);
        }
        paymentGroup.setStateDetail(PaymentGroupStates.SETTLED.getDescription());
        paymentGroup.setState(PaymentGroupStates.SETTLED.getStateValue());
        paymentGroupService.updatePaymentGroup(paymentGroup);

        orderLogService.saveOrderLog(orderRecord.getId(), orderRecord.getState(), "确认支付", confirmPaymentDto.getOperator());
        return true;
    }
}
