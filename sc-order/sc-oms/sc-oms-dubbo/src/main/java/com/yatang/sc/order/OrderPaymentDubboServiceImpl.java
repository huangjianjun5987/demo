package com.yatang.sc.order;

import com.busi.common.resp.Response;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.dto.PaymentResultDto;
import com.yatang.sc.dto.UpdateOrderPaymentStatusDto;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.dubboservice.OrderPaymentDubboService;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.OrderPaymentsService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.service.PaymentGroupService;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.OrderTotalStates;
import com.yatang.sc.order.states.PaymentStates;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service("orderPaymentDubboService")
public class OrderPaymentDubboServiceImpl implements OrderPaymentDubboService {
    private Logger mLogger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderPaymentsService mOrderPaymentsService;

    @Autowired
    OrderLogService orderLogService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Response<Boolean> confirmOrderPayment(PaymentResultDto paymentResult) {
        mLogger.info("start process order payment with  order id:{} ,transId:{} ,payChannel:{}", paymentResult.getOrderId(), paymentResult.getTransNum(), paymentResult.getPayChannel());
        Response<Boolean> response = new Response<Boolean>();
        response.setSuccess(true);

        Order orderRecord = orderService.selectByPrimaryKey(paymentResult.getOrderId());
        if (orderRecord == null) {
            response.setSuccess(false);
            response.setErrorMessage("订单不存在");
            return response;
        }
        if (!PaymentStates.PENDING_PAYMENT.equals(orderRecord.getPaymentState())
                && !OrderStates.SPLITED_ORDER.equalsIgnoreCase(orderRecord.getOrderState())) {
            mLogger.info("订单状态不正确！当前订单：{} 状态为:{},支付状态：{}", orderRecord.getId(), orderRecord.getOrderState(), orderRecord.getPaymentState());
            response.setSuccess(false);
            response.setErrorMessage("订单状态不正确！当前订单状态为:" + orderRecord.getOrderState());
            return response;
        }

        mOrderPaymentsService.savePaymentInfo(orderRecord.getId(), paymentResult.getPayAmount(), paymentResult.getPayDate(), paymentResult.getPayChannel(), paymentResult.getTransNum(), paymentResult.getPayRecordNo(), paymentResult.getOperator());

        orderRecord.setPaymentState(PaymentStates.DEBITED);
        orderRecord.setState(OrderTotalStates.PENDING_PROCESS.getStateValue());
        orderRecord.setStateDetail(OrderTotalStates.PENDING_PROCESS.getDescription());
        orderService.updateOrderSelective(orderRecord);
        orderLogService.saveOrderLog(orderRecord.getId(), orderRecord.getState(), "订单已支付！", "system");
        return response;
    }

    @Override
    public Response<Boolean> updateOrderPaymentStatus(UpdateOrderPaymentStatusDto pUpdateOrderPaymentStatusDto) {
        mLogger.info("update order payment status:{}", pUpdateOrderPaymentStatusDto);
        Response<Boolean> response = new Response<Boolean>();
        response.setSuccess(true);

        Order orderRecord = orderService.selectByPrimaryKey(pUpdateOrderPaymentStatusDto.getOrderNo());
        if (orderRecord == null) {
            response.setSuccess(false);
            response.setErrorMessage("订单不存在");
            return response;
        }
        if(pUpdateOrderPaymentStatusDto.getOrderTotalStates() != null){
            orderRecord.setState(pUpdateOrderPaymentStatusDto.getOrderTotalStates().getStateValue());
            orderRecord.setStateDetail(pUpdateOrderPaymentStatusDto.getOrderTotalStates().getDescription());
        }
        if(StringUtils.isNoneEmpty(pUpdateOrderPaymentStatusDto.getPaymentStates())) {
            orderRecord.setPaymentState(pUpdateOrderPaymentStatusDto.getPaymentStates());
        }
        orderService.updateOrderSelective(orderRecord);
        return response;
    }


}
