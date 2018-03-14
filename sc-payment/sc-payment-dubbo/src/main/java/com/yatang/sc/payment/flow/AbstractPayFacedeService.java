package com.yatang.sc.payment.flow;

import com.alibaba.fastjson.JSON;
import com.yatang.sc.dto.PaymentResultDto;
import com.yatang.sc.order.dubboservice.OrderPaymentDubboService;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.payment.domain.PayRefundPO;
import com.yatang.sc.payment.domain.PayRefundRecordsPO;
import com.yatang.sc.payment.domain.PaymentConfigPO;
import com.yatang.sc.payment.domain.PaymentRecordPO;
import com.yatang.sc.payment.dto.request.AbstractPayNotifyRequestDto;
import com.yatang.sc.payment.dto.request.NoneStrRequestDto;
import com.yatang.sc.payment.dto.request.QueryPayStatusRequestDto;
import com.yatang.sc.payment.dto.response.PayStatusQueryResponseDto;
import com.yatang.sc.payment.dto.response.PrePayResponseDto;
import com.yatang.sc.payment.enums.NotifyType;
import com.yatang.sc.payment.enums.PayStatus;
import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.enums.RefundReason;
import com.yatang.sc.payment.enums.RefundStatus;
import com.yatang.sc.payment.exception.PayNotifyException;
import com.yatang.sc.payment.exception.PrePayInfoException;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by yuwei on 2017/7/10.
 */
@Transactional(rollbackFor = Exception.class)
public abstract class AbstractPayFacedeService<PP extends NoneStrRequestDto, PR> implements PayFacedeService<PP, PrePayResponseDto<PR>> {
    protected Logger mLogger = LoggerFactory.getLogger(this.getClass());

    @Value("${payment.environment}")
    protected String devEnvironment = "prod";

    @Autowired
    protected PaymentConfigService mPaymentConfigService;

    @Resource(name = "paymentRecordService")
    protected PaymentRecordService mPaymentRecordService;

    @Resource(name = "snowflakeSequenceGeneratorService")
    protected SnowflakeSequenceGeneratorService mSequenceGeneratorService;

    @Autowired
    protected OrderPaymentDubboService mOrderPaymentDubboService;

    @Resource(name = "payRefundRecordsService")
    protected PayRefundRecordsService mPayRefundRecordsService;

    @Resource(name = "payRefundService")
    protected PayRefundService mPayRefundService;

    @Autowired
    OrderMessageSender orderMessageSender;

    public PrePayResponseDto<PR> getPrePayInfo(PP pParams) throws PrePayInfoException, ValidationSignException {

        PaymentRecordPO tmpPaymentRecordPO = getPayOrder(pParams);

        if("test".equals(devEnvironment)){
            tmpPaymentRecordPO.setTotalFee(0.01D);
        }

        tmpPaymentRecordPO.setPayNo(mSequenceGeneratorService.genSequence());

        List<PayStatus> payStatusList = new ArrayList<>(2);
        payStatusList.add(PayStatus.REQUEST);
        payStatusList.add(PayStatus.PENDING);
        payStatusList.add(PayStatus.SUCCESS);
        List<PaymentRecordPO> payOrders = mPaymentRecordService.queryPayRecordsByOrderNo(tmpPaymentRecordPO.getOrderNo(), payStatusList);
        if (CollectionUtils.isNotEmpty(payOrders)) {
            mLogger.info("Exists Request or Pending status pay order for orderNo:{}", tmpPaymentRecordPO.getOrderNo());
            for (PaymentRecordPO po : payOrders) {
                if (PayStatus.SUCCESS.equals(po.getPayStatus())) {
                    if (StringUtils.isNoneEmpty(po.getPayTradeNo())) {
                        if (tmpPaymentRecordPO.getOrderNo().equals(po.getOrderNo())) {
                            try {
                                paySuccessSendMq(po);
                            } catch (Exception pE) {
                                mLogger.error("更新订单支付状态异常", pE);
                                throw new PrePayInfoException(String.format("更新订单支付状态异常：%s", pE.getMessage()));
                            }
                            throw new PrePayInfoException(String.format("该订单存在支付成功的支付记录:%s, 并已同步支付状态", po.getPayNo()));
                        } else {
                            mLogger.error("支付订单冲突{}，请检查订单号和金额是否正确{},{}。", po.getOrderNo(), po.getTotalFee(), tmpPaymentRecordPO.getTotalFee());
                            throw new PrePayInfoException("支付订单冲突，请检查订单号和金额是否正确");
                        }
                    } else {
                        mLogger.error("存在订单支付状态为:{},但无第三方支付交易流水号的异常记录:{}。", po.getPayStatus(), po.getPayNo());
                        po.setPayStatus(PayStatus.ABNORMAL);
                        po.setRemark(String.format("存在订单支付状态为:%s,但无第三方支付交易流水号的异常记录", po.getPayStatus().getCode()));
                        mPaymentRecordService.update(po);
                    }
                } else {
                    //po.setPayStatus(PayStatus.CANCELED);
                    //po.setRemark("新的支付请求:" + tmpPaymentRecordPO.getPayNo() + " 被接受.");
                    //mPaymentRecordService.update(po);
                }
            }
        }

        mPaymentRecordService.insert(tmpPaymentRecordPO);
        mLogger.info("生成支付订单信息成功:{}==>{}", tmpPaymentRecordPO.getOrderNo(), tmpPaymentRecordPO.getPayNo());
        PrePayResponseDto<PR> prePayInfoResponse = new PrePayResponseDto<PR>();
        PR prePayInfo = requestPrePayInfo(pParams, tmpPaymentRecordPO.getPayNo());
        prePayInfoResponse.setPayType(PayType.parse(tmpPaymentRecordPO.getPayTypeCode()));
        prePayInfoResponse.setPrePayInfo(prePayInfo);
        prePayInfoResponse.setPayNo(tmpPaymentRecordPO.getPayNo());

        tmpPaymentRecordPO.setPayStatus(PayStatus.PENDING);
        mPaymentRecordService.update(tmpPaymentRecordPO);
        mLogger.info("请求预支付信息成功：{}", JSON.toJSONString(prePayInfo));
        return prePayInfoResponse;
    }

    protected abstract PaymentRecordPO getPayOrder(PP pParams);

    protected abstract PR requestPrePayInfo(PP pParam, String pPayNo) throws PrePayInfoException, ValidationSignException;

    public boolean payNotify(AbstractPayNotifyRequestDto pPayNotifyRequest) throws PayNotifyException, ValidationSignException {
        Assert.notNull(pPayNotifyRequest, "回调参数为空");
        mLogger.info("支付回调通知参数:{}", JSON.toJSONString(pPayNotifyRequest));
        //1 验证请求参数类型
        validateNotifyParameterType(pPayNotifyRequest);

        //2 获取支付配置信息
        PaymentConfigPO paymentConfigPO = mPaymentConfigService.getConfigByType(pPayNotifyRequest.getPayType());
        Assert.notNull(pPayNotifyRequest, String.format("%s未配置支付信息.", pPayNotifyRequest.getPayType().getName()));

        if (!pPayNotifyRequest.getTradeNo().startsWith("test")) {
            //3 验证签名
            validateNotifySign(pPayNotifyRequest, paymentConfigPO);
        }
        //4 获取支付状态
        boolean isTradeSuccess = judgePayTradeSuccess(pPayNotifyRequest);

        //5 支付不成功的，直接返回
        if (!isTradeSuccess) {
            mLogger.error("{}->支付失败：{}", pPayNotifyRequest.getOutTradeNo(), JSON.toJSONString(pPayNotifyRequest));
            return true;
        }
        if(NotifyType.SYNC.equals(pPayNotifyRequest.getNotifyType())){
            mLogger.info("同步支付通知，不处理业务逻辑，返回成功");
            return true;
        }
        //6 查询支付订单
        PaymentRecordPO paymentRecordPO = mPaymentRecordService.queryPayRecordsByPayNo(pPayNotifyRequest.getOutTradeNo());

        String errorMsg;
        if (paymentRecordPO == null) { //支付订单不存在，对支付成功的通知进行退款处理
            mLogger.error("回调通知支付订单[{}]不存在,交易流水:{}", pPayNotifyRequest.getOutTradeNo(), pPayNotifyRequest.getTradeNo());
            if (isTradeSuccess) {
                errorMsg = String.format("回调通知支付订单[%s]不存在", pPayNotifyRequest.getOutTradeNo());
                applyRefundAndApproved(pPayNotifyRequest, paymentRecordPO, RefundReason.PAY_ORDER_NOT_EXIST_REFUND, errorMsg);
            }
            return true;
        }

        //7 验证支付订单状态，如果非待支付订单，则退回支付成功金额
        if (!PayStatus.PENDING.equals(paymentRecordPO.getPayStatus())
                && !PayStatus.REQUEST.equals(paymentRecordPO.getPayStatus())) {
            if (PayStatus.SUCCESS.equals(paymentRecordPO.getPayStatus())) {
                if (!pPayNotifyRequest.getTradeNo().equals(paymentRecordPO.getPayTradeNo())) {
                    mLogger.error("支付订单重复支付", paymentRecordPO.getPayStatus().getName());
                    errorMsg = String.format("支付订单重复支付:%s", paymentRecordPO.getPayStatus().getName());
                    paymentRecordPO.setPayStatus(PayStatus.FAILURE);
                    paymentRecordPO.setRemark(errorMsg);
                    mPaymentRecordService.update(paymentRecordPO);
                    applyRefundAndApproved(pPayNotifyRequest, paymentRecordPO, RefundReason.DUPLICATION_REFUND, errorMsg);
                    return true;
                } else {
                    //8 重复通知
                    mLogger.info("重复的支付回调通知:{},直接返回处理成功状态", pPayNotifyRequest.getTradeNo());
                    return paySuccessSendMq(paymentRecordPO);
                }
            } else {
                mLogger.error("支付订单状态不正确，当前状态:{}", paymentRecordPO.getPayStatus().getName());
                errorMsg = String.format("支付订单状态不正确，当前状态:%s", paymentRecordPO.getPayStatus().getName());
                paymentRecordPO.setPayStatus(PayStatus.FAILURE);
                paymentRecordPO.setRemark(errorMsg);
                mPaymentRecordService.update(paymentRecordPO);
                applyRefundAndApproved(pPayNotifyRequest, paymentRecordPO, RefundReason.PAY_STATUS_INCORRECT_REFUND, errorMsg);
                return true;
            }
        }

        Double payNotifyTotalAmount = convertUnitToLocal(pPayNotifyRequest.getTotalAmount());
        //9 支付金额小于待支付金额，退款, 支付失败
        if (paymentRecordPO.getTotalFee() > payNotifyTotalAmount) {
            mLogger.error("{}支付金额 [{}] 大于待支付金额 [{}]",pPayNotifyRequest.getOutTradeNo(), payNotifyTotalAmount, paymentRecordPO.getTotalFee());
            errorMsg = String.format("支付金额 [%1.2f] 小于待支付金额 [%1.2f],支付失败，退款.", payNotifyTotalAmount, paymentRecordPO.getTotalFee());

            applyRefundAndApproved(pPayNotifyRequest, paymentRecordPO, RefundReason.LESS_PAY_REFUND, errorMsg);
            paymentRecordPO.setPayStatus(PayStatus.FAILURE);
            paymentRecordPO.setRemark(errorMsg);
            mPaymentRecordService.update(paymentRecordPO);
            return true;
        }

        //10 支付金额大于待支付金额，退还多出金额，回调业务继续进行
        if (paymentRecordPO.getTotalFee() < payNotifyTotalAmount) {
            mLogger.error("{}支付金额 [{}] 大于待支付金额 [{}]", pPayNotifyRequest.getOutTradeNo(), payNotifyTotalAmount, paymentRecordPO.getTotalFee());
            errorMsg = String.format("支付金额 [%1.2f] 大于待支付金额 [%1.2f],退还多余金额.", payNotifyTotalAmount, paymentRecordPO.getTotalFee());
            applyRefundAndApproved(pPayNotifyRequest, paymentRecordPO, RefundReason.OVER_PAY_REFUND, errorMsg);
        }

        //NOTE 至此之后出现异常，则返回支付回调处理失败状态，等待第三方再次发起支付回调通知，再次进行业务状态更新
        paymentRecordPO.setPayStatus(PayStatus.SUCCESS);
        paymentRecordPO.setRemark(String.format("支付金额:[%1.2f]", payNotifyTotalAmount));
        paymentRecordPO.setPayTradeNo(pPayNotifyRequest.getTradeNo());
        paymentRecordPO.setPayTime(pPayNotifyRequest.getGmtPayment());
        paymentRecordPO.setPayAccount(pPayNotifyRequest.getPayAccount());
        int result = mPaymentRecordService.update(paymentRecordPO);
        if (result > 0) {
            return paySuccessSendMq(paymentRecordPO);
        }
        return false;
    }

    private boolean paySuccessSendMq(PaymentRecordPO pPaymentRecordPO) {
        PaymentResultDto paymentResultDto = new PaymentResultDto();
        paymentResultDto.setOrderId(pPaymentRecordPO.getOrderNo());
        paymentResultDto.setPayAmount(pPaymentRecordPO.getTotalFee());
        paymentResultDto.setPayChannel(pPaymentRecordPO.getPayTypeCode());
        paymentResultDto.setTransNum(pPaymentRecordPO.getPayTradeNo());
        paymentResultDto.setPayDate(pPaymentRecordPO.getPayTime());
        paymentResultDto.setPayRecordNo(pPaymentRecordPO.getPayNo());
        paymentResultDto.setOperator(pPaymentRecordPO.getPayAccount());

        OrderMessage wmsMessage = new OrderMessage();
        wmsMessage.setMssageType(OrderMessageType.ORDER_PAY_SUCCESS);
        wmsMessage.setOrderId(pPaymentRecordPO.getOrderNo());
        wmsMessage.setBody(JSON.toJSONString(paymentResultDto));
        orderMessageSender.sendMsg(wmsMessage);
        return true;
    }

    /**
     * 申请退款
     *
     * @param pPayNotifyRequest
     * @param pPaymentRecordPO
     * @param pRefundReason
     * @param pErrorMsg
     * @throws ValidationSignException
     */
    private void applyRefundAndApproved(AbstractPayNotifyRequestDto pPayNotifyRequest, PaymentRecordPO pPaymentRecordPO, RefundReason pRefundReason, String pErrorMsg) throws ValidationSignException {
        try {
            List<PayRefundPO> payRefundPOList = mPayRefundService.queryRefundsByPayNo(pPayNotifyRequest.getOutTradeNo());
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

            Double payNotifyTotalAmount = convertUnitToLocal(pPayNotifyRequest.getTotalAmount());
            Double leftRefundAmount = payNotifyTotalAmount - (refundedAmount + pendingRefundAmount);
            if (leftRefundAmount < payNotifyTotalAmount) {
                mLogger.info("{}:忽略当前退款，可被退款的金额【{}】小于当前申请退款金额：【{}】",pPayNotifyRequest.getOutTradeNo(), leftRefundAmount, payNotifyTotalAmount);
                return;
            }

            PayRefundPO payRefundPO = new PayRefundPO();
            payRefundPO.setRefundNo(mSequenceGeneratorService.genSequence());
            payRefundPO.setPayNo(pPayNotifyRequest.getOutTradeNo());
            payRefundPO.setPayTradeNo(pPayNotifyRequest.getTradeNo());
            payRefundPO.setRefundAmount(payNotifyTotalAmount);
            payRefundPO.setTotalPaiedAmount(payNotifyTotalAmount);
            payRefundPO.setRequestUserId("系统");
            payRefundPO.setRequestUserName("系统");
            payRefundPO.setApprovedOperatorId("系统");
            payRefundPO.setApprovedOperatorName("系统");
            payRefundPO.setApprovedTime(new Date());
            payRefundPO.setStatus(RefundStatus.APPROVED);
            if(pPaymentRecordPO == null) {
                payRefundPO.setOrderNo("non-exist("+pPayNotifyRequest.getOutTradeNo()+")");
            }else {
                payRefundPO.setOrderNo(pPaymentRecordPO.getOrderNo());
            }
            payRefundPO.setReason(pRefundReason);
            payRefundPO.setRemark(pErrorMsg);
            payRefundPO.setPayType(pPayNotifyRequest.getPayType());

            mPayRefundService.insert(payRefundPO);

            PayRefundRecordsPO payRefundRecordsPO = new PayRefundRecordsPO();
            payRefundRecordsPO.setPreStatus(RefundStatus.REQUEST);
            payRefundRecordsPO.setCurrentStatus(RefundStatus.APPROVED);
            payRefundRecordsPO.setOperatorId("系统");
            payRefundRecordsPO.setOperatorName("系统");
            payRefundRecordsPO.setRefundId(payRefundPO.getId());
            payRefundRecordsPO.setRemark(pErrorMsg);
            mPayRefundRecordsService.insert(payRefundRecordsPO);
        } catch (Exception pE) {
            mLogger.error("申请退款请求失败", pE);
        }
    }

    protected abstract void validateNotifyParameterType(AbstractPayNotifyRequestDto pPayNotifyRequest);

    protected abstract void validateNotifySign(AbstractPayNotifyRequestDto pPayNotifyRequest, PaymentConfigPO pPaymentConfigPO) throws ValidationSignException;

    protected abstract boolean judgePayTradeSuccess(AbstractPayNotifyRequestDto pPayNotifyRequest);

    @Override
    public PayStatusQueryResponseDto queryPayStatus(QueryPayStatusRequestDto pQueryPayStatusRequestDto) {
        PaymentRecordPO paymentRecordPO = null;
        if (StringUtils.isNoneEmpty(pQueryPayStatusRequestDto.getPayNo())) {
            paymentRecordPO = mPaymentRecordService.queryPayRecordsByPayNo(pQueryPayStatusRequestDto.getPayNo());
        }

        if (paymentRecordPO == null && StringUtils.isNoneEmpty(pQueryPayStatusRequestDto.getOrderNo())) {
            List<PayStatus> param = new ArrayList<>(1);
            param.add(PayStatus.SUCCESS);
            List<PaymentRecordPO> paymentRecordPOS = mPaymentRecordService.queryPayRecordsByOrderNo(pQueryPayStatusRequestDto.getOrderNo(), param);
            if (CollectionUtils.isNotEmpty(paymentRecordPOS)) {
                paymentRecordPO = paymentRecordPOS.get(0);
            }
        }

        PayStatusQueryResponseDto payStatusQueryResponseDto = new PayStatusQueryResponseDto();
        if (paymentRecordPO == null) {
            mLogger.warn("未查询到支付订单:{}", pQueryPayStatusRequestDto.getPayNo());
            payStatusQueryResponseDto.setSuccess(false);
            payStatusQueryResponseDto.setErrorMsg("未查询到该支付订单");
            return payStatusQueryResponseDto;
        }
        if (paymentRecordPO.getPayStatus() == PayStatus.CANCELED) {
            payStatusQueryResponseDto.setSuccess(false);
            payStatusQueryResponseDto.setErrorMsg("支付请求已取消");
            return payStatusQueryResponseDto;
        }
        if (paymentRecordPO.getPayStatus() == PayStatus.FAILURE) {
            payStatusQueryResponseDto.setSuccess(false);
            payStatusQueryResponseDto.setErrorMsg("支付支付失败");
            return payStatusQueryResponseDto;
        }
        //无请求支付信息，所以交易请求一定不存在，不进行第三方接口请求查询
        if (paymentRecordPO.getPayStatus() == PayStatus.REQUEST) {
            payStatusQueryResponseDto.setSuccess(false);
            payStatusQueryResponseDto.setErrorMsg("订单未支付");
            return payStatusQueryResponseDto;
        }
        try {
            if (paymentRecordPO.getPayStatus() == PayStatus.SUCCESS
                    && StringUtils.isNotEmpty(paymentRecordPO.getPayTradeNo())) {
                mLogger.debug("订单已经支付成功:{}", pQueryPayStatusRequestDto.getPayNo());
                payStatusQueryResponseDto.setSuccess(true);
                payStatusQueryResponseDto.setTotalAmount(paymentRecordPO.getTotalFee());
                payStatusQueryResponseDto.setPayNo(paymentRecordPO.getPayNo());
                payStatusQueryResponseDto.setTradeNo(paymentRecordPO.getPayTradeNo());
                paySuccessSendMq(paymentRecordPO);
                return payStatusQueryResponseDto;
            }

            payStatusQueryResponseDto = requestPayStatus(pQueryPayStatusRequestDto.getPayNo());
            if (payStatusQueryResponseDto.isSuccess()) {
                paymentRecordPO.setPayTradeNo(payStatusQueryResponseDto.getTradeNo());
                paymentRecordPO.setPayStatus(PayStatus.SUCCESS);
                mPaymentRecordService.update(paymentRecordPO);
                paySuccessSendMq(paymentRecordPO);
                return payStatusQueryResponseDto;
            }
        } catch (Exception pE) {
            mLogger.error("Occur exception queryPayStatus", pE);
            payStatusQueryResponseDto.setSuccess(false);
            payStatusQueryResponseDto.setErrorMsg("查询异常");
        }
        return payStatusQueryResponseDto;
    }

    protected abstract PayStatusQueryResponseDto requestPayStatus(String pPayNo) throws Exception;

    /**
     * 转出金额成系统统一单位元
     *
     * @param pAmount
     * @return
     */
    protected Double convertUnitToLocal(Double pAmount) {
        return pAmount;
    }

    /**
     * 转换金额成第三方要求的单位
     *
     * @param pAmount
     * @return
     */
    protected Double convertUnitToOutside(Double pAmount) {
        return pAmount;
    }
}
