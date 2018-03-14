package com.yatang.sc.order.service.impl;

import com.yatang.sc.common.staticvalue.DefaultNoPayEnum;
import com.yatang.sc.order.dao.OrderCancelNoPayDao;
import com.yatang.sc.order.dao.OrderPaymentsDao;
import com.yatang.sc.order.domain.CompanyCancelNoPayPo;
import com.yatang.sc.order.domain.OrderPayments;

import com.yatang.sc.order.domain.PaymentGroup;
import com.yatang.sc.order.service.OrderPaymentsService;
import com.yatang.sc.order.service.PaymentGroupService;
import com.yatang.sc.order.states.PaymentGroupStates;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/24.
 */
@Service
public class OrderPaymentsServiceImpl implements OrderPaymentsService {
    private final Logger mLogger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private OrderPaymentsDao orderPaymentsDao;

    @Autowired
    private PaymentGroupService paymentGroupService;

    @Autowired
    private OrderPaymentsService orderPaymentsService;

    @Autowired
    private OrderCancelNoPayDao orderCancelNoPayDao;

    @Override
    public void saveOrderPayments(OrderPayments orderPayments) {
        orderPaymentsDao.insert(orderPayments);
    }

    @Override
    public List<OrderPayments> selectByOrderId(String orderId) {
        return orderPaymentsDao.selectByOrderId(orderId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int savePaymentInfo(String orderId, Double amount, Date payDate, String channel, String transNum, String payRecordNo, String payAccount) {
        List<OrderPayments> paymentGroups = selectByOrderId(orderId);
        PaymentGroup paymentGroupRecord = null;
        if (CollectionUtils.isNotEmpty(paymentGroups)) {
            for (OrderPayments orderPayment : paymentGroups) {
                paymentGroupRecord = paymentGroupService.getPaymentGroupForId(orderPayment.getPaymentGroupId());
                if (channel.equalsIgnoreCase(paymentGroupRecord.getChannel()) && transNum.equalsIgnoreCase(paymentGroupRecord.getTransNum())) {
                    mLogger.info("重复的支付信息:{}：{}->{}", paymentGroupRecord.getId(), paymentGroupRecord.getChannel(), paymentGroupRecord.getTransNum());
                    return paymentGroups.size();
                }
                if (PaymentGroupStates.INITIAL.getStateValue().equals(paymentGroupRecord.getState())) {//找到初始化的paymentGroup更新支付信息
                    break;
                }
                paymentGroupRecord = null;
            }

            boolean isAdd = false;
            if (paymentGroupRecord == null) {
                isAdd = true;
                paymentGroupRecord = new PaymentGroup();
                paymentGroupRecord.setId("P" + RandomStringUtils.randomAlphanumeric(20));
            }
            paymentGroupRecord.setAmountCredited(amount);
            paymentGroupRecord.setAmountDebited(amount);
            paymentGroupRecord.setPayDate(payDate);
            paymentGroupRecord.setDebitStatus("debited");
            paymentGroupRecord.setChannel(channel);
            paymentGroupRecord.setTransNum(transNum);
            paymentGroupRecord.setState(PaymentGroupStates.SETTLED.getStateValue());
            paymentGroupRecord.setStateDetail(PaymentGroupStates.SETTLED.getDescription());
            paymentGroupRecord.setPayRecordNo(payRecordNo);
            paymentGroupRecord.setPayDate(payDate);
            paymentGroupRecord.setOrderId(orderId);
            paymentGroupRecord.setPayAccount(payAccount);

            if (isAdd) {
                mLogger.info("添加支付信息：{}，{}", orderId, paymentGroupRecord.getId());
                paymentGroupService.save(paymentGroupRecord);
                OrderPayments orderPayments = new OrderPayments();
                orderPayments.setOrderId(orderId);
                orderPayments.setPaymentGroupId(paymentGroupRecord.getId());
                orderPaymentsService.saveOrderPayments(orderPayments);
            } else {
                mLogger.info("更新支付信息：{}，{}", orderId, paymentGroupRecord.getId());
                paymentGroupService.updatePaymentGroup(paymentGroupRecord);
            }
        }
        return paymentGroups.size();
    }

    @Override
    public List<CompanyCancelNoPayPo> getCompanyCancelNoPay() {
        return orderCancelNoPayDao.getCompanyCancelNoPay();
    }

    @Override
    public Long getNoPayByCompanyId(String companyId) {
        CompanyCancelNoPayPo noPayByCompany = orderCancelNoPayDao.getNoPayByCompanyId(companyId);
        long cancelTime;
        if (noPayByCompany == null || noPayByCompany.getCancelTime() == 0){
            cancelTime = DefaultNoPayEnum.CANCEL_MINUTE.getTime();
        }else {
            cancelTime = noPayByCompany.getCancelTime();
        }
        mLogger.info("子公司的Id：{}，设置的最大未付款时间上限：{} 分钟",companyId,cancelTime);
        //子公司设置的最大未付款时间转换成分钟数
        long noPayMinutes = cancelTime;
        return noPayMinutes;
    }
}
