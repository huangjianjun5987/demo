package com.yatang.sc.payment.flow;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.dto.UpdateOrderPaymentStatusDto;
import com.yatang.sc.order.dubboservice.OrderPaymentDubboService;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.states.OrderTotalStates;
import com.yatang.sc.order.states.PaymentStates;
import com.yatang.sc.payment.domain.PayRefundPO;
import com.yatang.sc.payment.domain.PayRefundRecordsPO;
import com.yatang.sc.payment.domain.PaymentConfigPO;
import com.yatang.sc.payment.dto.request.ApplyRefundRequestDto;
import com.yatang.sc.payment.dto.request.AuditRefundRequestDto;
import com.yatang.sc.payment.dto.request.RefundConfirmDto;
import com.yatang.sc.payment.dto.request.RefundRequestDto;
import com.yatang.sc.payment.dto.response.RefundResponseDto;
import com.yatang.sc.payment.enums.RefundStatus;
import com.yatang.sc.payment.exception.RefundException;
import com.yatang.sc.payment.exception.ValidationSignException;
import com.yatang.sc.payment.service.PayRefundRecordsService;
import com.yatang.sc.payment.service.PayRefundService;
import com.yatang.sc.payment.service.PaymentConfigService;
import com.yatang.sc.payment.service.PaymentRecordService;
import com.yatang.sc.payment.service.impl.SnowflakeSequenceGeneratorService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Transactional(rollbackFor = Exception.class)
public abstract class AbstractRefundFacedService implements RefundFacedeService {

    protected Logger mLogger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected PaymentConfigService mPaymentConfigService;

    @Resource(name = "paymentRecordService")
    protected PaymentRecordService mPaymentRecordService;

    @Resource(name = "snowflakeSequenceGeneratorService")
    protected SnowflakeSequenceGeneratorService mSequenceGeneratorService;

    @Resource(name = "payRefundService")
    protected PayRefundService mPayRefundService;

    @Resource(name = "payRefundRecordsService")
    protected PayRefundRecordsService mPayRefundRecordsService;

    @Autowired
    protected OrderPaymentDubboService mOrderPaymentDubboService;

    @Autowired
    OrderMessageSender orderMessageSender;

    @Override
    public RefundResponseDto refund(RefundRequestDto pParams) throws RefundException, ValidationSignException {
        PaymentConfigPO paymentConfigPO = mPaymentConfigService.getConfigByType(pParams.getPayType());
        if (paymentConfigPO == null) {
            mLogger.error("Can not get any WeiXin pay config form database.");
            throw new RefundException("微信支付未配置支付信息.");
        }

        PayRefundPO payRefundPO = mPayRefundService.queryRefundsByRefundNo(pParams.getRefundNo());
        if (payRefundPO == null) {
            mLogger.error("退款记录不存在:{}", pParams.getRefundNo());
            throw new RefundException("退款记录不存在.");
        }

        RefundStatus refundStatus = payRefundPO.getStatus();
        if (refundStatus != RefundStatus.APPROVED && refundStatus != RefundStatus.REFUNDING) {
            mLogger.info("退款记录状态不正确,当前状态{}", refundStatus);
            throw new RefundException(String.format("退款记录状态不正确,当前状态:%s.", refundStatus.getName()));
        }

        RefundStatus preRefundStatus = RefundStatus.REFUNDING;

        payRefundPO.setRefundOperatorId(pParams.getOperatorId());
        payRefundPO.setRefundOperatorName(pParams.getOperatorName());
        payRefundPO.setRemark(pParams.getRemark());
        payRefundPO.setTryRefundCount(payRefundPO.getTryRefundCount() + 1);
        payRefundPO.setStatus(RefundStatus.REFUNDING);

        mPayRefundService.update(payRefundPO);
        mLogger.debug("更新退款记录状态为退款中");

        addRefundRecord(pParams.getOperatorId(), pParams.getOperatorName(), payRefundPO.getId(), preRefundStatus, payRefundPO.getStatus(), "更新退款记录状态为退款中[" + pParams.getRemark() + "]");

        preRefundStatus = payRefundPO.getStatus();

        RefundResponseDto refundResponseDto = requestRefund(payRefundPO, paymentConfigPO, payRefundPO);

        if (refundResponseDto.isSuccess()) {
            mLogger.info("退款成功:{}", refundResponseDto.toString());

            payRefundPO.setRefundAmount(refundResponseDto.getRefundAmount());
            payRefundPO.setRefundTradeNo(refundResponseDto.getRefundTradeNo());
            payRefundPO.setRefundTime(new Date());
            payRefundPO.setRefundNo(refundResponseDto.getRefundNo());
            payRefundPO.setStatus(RefundStatus.REFUNDED);
            payRefundPO.setRemark("退款成功");
            mPayRefundService.update(payRefundPO);

            updateOrderPaymentStatus(payRefundPO, PaymentStates.RF_COMPLETED, OrderTotalStates.REFUND_SUCCESS);

            OrderMessage wmsMessage = new OrderMessage();
            wmsMessage.setMssageType(OrderMessageType.REFUND_COMPLETED);
            wmsMessage.setOrderId(payRefundPO.getOrderNo());
            orderMessageSender.sendMsg(wmsMessage);

            OrderMessage indexMsg = new OrderMessage();
            indexMsg.setOrderId(payRefundPO.getOrderNo());
            orderMessageSender.sendOrderUpdateMsg(indexMsg);

        } else {
            if (paymentConfigPO.getMaxTryRefundCount() <= payRefundPO.getTryRefundCount()) {
                mLogger.info("退款参数达到最大次数：{}, 更新退款状态为退款失败", paymentConfigPO.getMaxTryRefundCount());
                payRefundPO.setStatus(RefundStatus.REFUND_FAILED);
                payRefundPO.setRemark(refundResponseDto.getMsg());
                mPayRefundService.update(payRefundPO);

                updateOrderPaymentStatus(payRefundPO, PaymentStates.DEBITED, null);
            } else {
                payRefundPO.setRemark("最近一次尝试退款处理结果：" + refundResponseDto.getMsg());
                mPayRefundService.update(payRefundPO);
            }
            mLogger.info("退款失败:{}", refundResponseDto.toString());
        }
        addRefundRecord(pParams.getOperatorId(), pParams.getOperatorName(), payRefundPO.getId(), preRefundStatus, payRefundPO.getStatus(), payRefundPO.getRemark());
        return refundResponseDto;
    }

    protected abstract RefundResponseDto requestRefund(PayRefundPO pRefundPO, PaymentConfigPO pPaymentConfigPO, PayRefundPO payRefundPO) throws RefundException, ValidationSignException;

    @Override
    public Boolean confirmRefund(RefundConfirmDto pRefundConfirmDto) throws RefundException {
        PayRefundPO payRefundPO = mPayRefundService.queryRefundsByRefundNo(pRefundConfirmDto.getRefundNo());
        if (payRefundPO == null) {
            mLogger.error("退款记录不存在:{}", pRefundConfirmDto.getRefundNo());
            throw new RefundException("退款记录不存在.");
        }

        RefundStatus refundStatus = payRefundPO.getStatus();
        if (refundStatus != RefundStatus.APPROVED && refundStatus != RefundStatus.REFUNDING) {
            mLogger.info("退款记录状态不正确,当前状态{}", refundStatus);
            throw new RefundException(String.format("退款记录状态不正确,当前状态:%s.", refundStatus.getName()));
        }
        RefundStatus preRefundStatus = refundStatus;

        payRefundPO.setRefundOperatorId(pRefundConfirmDto.getOperatorId());
        payRefundPO.setRefundOperatorName(pRefundConfirmDto.getOperatorName());
        payRefundPO.setRemark(pRefundConfirmDto.getRemark());
        payRefundPO.setRefundTime(new Date());
        payRefundPO.setStatus(pRefundConfirmDto.getPassed() ? RefundStatus.REFUNDED_CONFIRM : RefundStatus.REQUEST);

        mPayRefundService.update(payRefundPO);

        addRefundRecord(pRefundConfirmDto.getOperatorId(), pRefundConfirmDto.getOperatorName(), payRefundPO.getId(), preRefundStatus, payRefundPO.getStatus(), payRefundPO.getRemark());

        //TODO 目前业务不能够完全支持被拒绝过后的逻辑，IBM 第二阶段考虑具体业务实现
        updateOrderPaymentStatus(payRefundPO, pRefundConfirmDto.getPassed() ? PaymentStates.RF_COMPLETED : PaymentStates.RF_PENDING_APPROVE, pRefundConfirmDto.getPassed() ? OrderTotalStates.REFUND_SUCCESS : OrderTotalStates.PENDING_PROCESS);

        if (pRefundConfirmDto.getPassed()) {
            OrderMessage wmsMessage = new OrderMessage();
            wmsMessage.setMssageType(OrderMessageType.REFUND_COMPLETED);
            wmsMessage.setOrderId(payRefundPO.getOrderNo());
            orderMessageSender.sendMsg(wmsMessage);
            OrderMessage indexMsg = new OrderMessage();
            indexMsg.setOrderId(payRefundPO.getOrderNo());
            orderMessageSender.sendOrderUpdateMsg(indexMsg);
        }
        return true;
    }


    @Override
    public Long applyRefund(ApplyRefundRequestDto pParams) throws RefundException, ValidationSignException {
        /*if (!canCreateRefund(pParams.getPayNo(), pParams.getTotalAmount(), pParams.getRefundAmount())) {
            throw new RefundException("退款金额大于可被退款金额，退款失败");
        }*/
        PayRefundPO payRefundPO = new PayRefundPO();
        payRefundPO.setRefundNo(mSequenceGeneratorService.genSequence());
        payRefundPO.setPayNo(pParams.getPayNo());
        payRefundPO.setPayTradeNo(pParams.getPayTradeNo());
        payRefundPO.setRefundAmount(pParams.getRefundAmount());
        payRefundPO.setTotalPaiedAmount(pParams.getTotalAmount());
        payRefundPO.setRequestUserId(pParams.getRefundUserId());
        payRefundPO.setRequestUserName(pParams.getRefundUserName());
        payRefundPO.setStatus(RefundStatus.REQUEST);
        payRefundPO.setOrderNo(pParams.getOrderNo());
        payRefundPO.setReason(pParams.getRefundReason());
        payRefundPO.setRemark(pParams.getRemark());
        payRefundPO.setPayType(pParams.getPayType());

        mPayRefundService.insert(payRefundPO);
        addRefundRecord(pParams.getRefundUserId(), pParams.getRefundUserName(), payRefundPO.getId(), RefundStatus.REQUEST, RefundStatus.REQUEST, payRefundPO.getRemark());
        if (payRefundPO.getId() > 0) {
            return payRefundPO.getId();
        }
        throw new RefundException("退款失败");
    }

    private boolean canCreateRefund(String pPayNo, Double pTotalAmount, Double pRefundAmount) {
        List<PayRefundPO> payRefundPOList = mPayRefundService.queryRefundsByPayNo(pPayNo);
        Double pendingRefundAmount = 0D;
        Double refundedAmount = 0D;
        if (CollectionUtils.isNotEmpty(payRefundPOList)) {
            for (PayRefundPO payRefund : payRefundPOList) {
                if (RefundStatus.REFUNDED == payRefund.getStatus()) {
                    refundedAmount += payRefund.getRefundAmount();
                } else if (RefundStatus.REQUEST == payRefund.getStatus()
                        || RefundStatus.APPROVED == payRefund.getStatus()) {
                    pendingRefundAmount += payRefund.getRefundAmount();
                }
            }
        }

        Double leftRefundAmount = pTotalAmount - (refundedAmount + pendingRefundAmount);
        if (leftRefundAmount < pRefundAmount) {
            mLogger.info("{}:忽略当前退款，可被退款的金额【{}】小于当前申请退款金额：【{}】",pPayNo, leftRefundAmount, pRefundAmount);
            return false;
        }
        return true;
    }

    protected void addRefundRecord(String pRefundUserId, String pRefundUserName, Long pRefundId, RefundStatus pPreStatus, RefundStatus pCurrentStatus, String pRemark) {
        PayRefundRecordsPO payRefundRecordsPO = new PayRefundRecordsPO();
        payRefundRecordsPO.setOperatorId(pRefundUserId);
        payRefundRecordsPO.setOperatorName(pRefundUserName);
        payRefundRecordsPO.setRefundId(pRefundId);
        payRefundRecordsPO.setCreateTime(new Date());
        payRefundRecordsPO.setPreStatus(pPreStatus);
        payRefundRecordsPO.setCurrentStatus(pCurrentStatus);
        payRefundRecordsPO.setRemark(pRemark);
        mPayRefundRecordsService.insert(payRefundRecordsPO);
    }

    public Boolean auditRefund(AuditRefundRequestDto pRequestDto) throws RefundException {
        PayRefundPO payRefundPO = mPayRefundService.queryRefundsByRefundNo(pRequestDto.getRefundNo());
        if (payRefundPO == null) {
            mLogger.error("退款记录不存在:{}", pRequestDto.getRefundNo());
            throw new RefundException("退款记录不存在.");
        }
        RefundStatus preRefundStatus = payRefundPO.getStatus();
        List<PayRefundPO> payRefundPOList = mPayRefundService.queryRefundsByPayNo(payRefundPO.getPayNo());
        Double pendingRefundAmount = 0D;
        Double refundedAmount = 0D;
        if (CollectionUtils.isNotEmpty(payRefundPOList)) {
            for (PayRefundPO payRefund : payRefundPOList) {
                if (payRefund.getRefundNo().equals(pRequestDto.getRefundNo())) {
                    continue;
                }
                if (RefundStatus.REFUNDED == payRefund.getStatus()) {
                    refundedAmount += payRefund.getRefundAmount();
                } else if (RefundStatus.REQUEST == payRefund.getStatus()
                        || RefundStatus.APPROVED == payRefund.getStatus()) {
                    pendingRefundAmount += payRefund.getRefundAmount();
                }
            }
        }
       /* Double leftRefundAmount = payRefundPO.getTotalPaiedAmount() - (refundedAmount + pendingRefundAmount);
        if (leftRefundAmount < pRequestDto.getFinalRefundAmount()) {
            mLogger.error("{}退款金额{}大于可退款金额{}", pRequestDto.getRefundNo(), pRequestDto.getFinalRefundAmount(), leftRefundAmount);
            throw new RefundException("退款金额大于可退款金额");
        }*/

        payRefundPO.setApprovedTime(new Date());
        payRefundPO.setApprovedOperatorName(pRequestDto.getAuditorName());
        payRefundPO.setApprovedOperatorId(pRequestDto.getAuditorId());
        payRefundPO.setRefundAmount(pRequestDto.getFinalRefundAmount());
        payRefundPO.setRemark(pRequestDto.getRemark());
        payRefundPO.setStatus(pRequestDto.getPassed() ? RefundStatus.APPROVED : RefundStatus.REJECT);

        mPayRefundService.update(payRefundPO);
        String remark = payRefundPO.getRemark();
        if (StringUtils.isEmpty(remark)) {
            remark = "最终退款金额:" + pRequestDto.getFinalRefundAmount();
        } else {
            remark += "最终退款金额:" + pRequestDto.getFinalRefundAmount();
        }

        addRefundRecord(pRequestDto.getAuditorId(), pRequestDto.getAuditorName(), payRefundPO.getId(), preRefundStatus, payRefundPO.getStatus(), remark);

        //记录状态改为退款待确认，订单支付状态改为退款待确认
        updateOrderPaymentStatus(payRefundPO, pRequestDto.getPassed() ? PaymentStates.RF_PENDING_CONFIRM : PaymentStates.DEBITED, pRequestDto.getPassed() ? OrderTotalStates.PENDING_REFUND : OrderTotalStates.PENDING_PROCESS);
        return true;
    }

    /**
     * 调用dubbo服务更新订单支付状态
     *
     * @param pPayRefundPO
     * @param paymentStatus
     * @param orderStatus
     * @throws RefundException
     */
    protected void updateOrderPaymentStatus(PayRefundPO pPayRefundPO, String paymentStatus, OrderTotalStates orderStatus) throws RefundException {
        UpdateOrderPaymentStatusDto updateOrderPaymentStatusDto = new UpdateOrderPaymentStatusDto();
        updateOrderPaymentStatusDto.setOrderNo(pPayRefundPO.getOrderNo());
        updateOrderPaymentStatusDto.setPaymentStates(paymentStatus);
        updateOrderPaymentStatusDto.setOrderTotalStates(orderStatus);
        Response<Boolean> response = mOrderPaymentDubboService.updateOrderPaymentStatus(updateOrderPaymentStatusDto);
        mLogger.info("{}更新订单支付状态结果:{}", pPayRefundPO.getOrderNo(), JSON.toJSONString(response));
        if (!response.isSuccess()) {
            throw new RefundException(response.getErrorMessage());
        }
    }

}
