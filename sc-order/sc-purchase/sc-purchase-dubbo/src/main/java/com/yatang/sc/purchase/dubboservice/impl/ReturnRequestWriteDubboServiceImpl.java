package com.yatang.sc.purchase.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.order.domain.returned.ReturnRequestReceiptPo;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.ReturnRequestService;
import com.yatang.sc.order.states.OrderReturnRequestTypes;
import com.yatang.sc.purchase.dto.returned.ReturnRequestReceiptDto;
import com.yatang.sc.purchase.dubboservice.ReturnRequestWriteDubboService;
import com.yatang.sc.purchase.flow.ReturnRequestFlowService;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @描述: 退换货dubboWrite服务实现类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/17 13:52
 * @版本: v1.0
 */
@Service("returnRequestWriteDubboService")
public class ReturnRequestWriteDubboServiceImpl implements ReturnRequestWriteDubboService {
    protected Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ReturnRequestService returnRequestService;

    @Autowired
    private ReturnRequestFlowService returnRequestFlowService;//

    @Autowired
    private OrderLogService orderLogService;

    @Override
    public Response<String> createOrderReturnedReceipt(ReturnRequestReceiptDto receiptDto) {

        log.info("dubbo--createOrderReturnedReceipt>>参数:{}", JSON.toJSONString(receiptDto));
        Response<String> response = new Response<>();
        try {
            Response<ReturnRequestReceiptPo> flowResponse = returnRequestFlowService.addOrderReturnReceiptDetail(receiptDto);
            if (null == flowResponse || !flowResponse.isSuccess() || null == flowResponse.getResultObject()) {
                log.error("createOrderReturnedReceipt--addOrderReturnReceiptDetail failed.");
                return BeanConvertUtils.convert(flowResponse, Response.class);
            }
            boolean createSuccess = returnRequestService.createOrderReturnedReceipt(flowResponse.getResultObject());
            if (!createSuccess) {
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_20102.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_20102.getName());
            } else {
                response.setSuccess(true);
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                response.setResultObject(flowResponse.getResultObject().getReturnRequest().getId());
                orderLogService.saveOrderLog(receiptDto.getReturnRequest().getOrderId(), receiptDto.getReturnRequest().getState(), "退货单已创建！", receiptDto.getReturnRequest().getProfileId());
            }
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<String> createRejectOrderReturnedReceipt(ReturnRequestReceiptDto receiptDto) {

        log.info("dubbo--createRejectOrderReturnedReceipt>>参数:{}", JSON.toJSONString(receiptDto));
        Response<String> response = new Response<>();
        try {

            receiptDto.getReturnRequest().setReturnRequestType(OrderReturnRequestTypes.REJECT_RETURN.getStateValue());
            Response<ReturnRequestReceiptPo> flowResponse = returnRequestFlowService.addOrderReturnReceiptDetail(receiptDto);
            if (null == flowResponse || !flowResponse.isSuccess() || null == flowResponse.getResultObject()) {
                log.error("createOrderReturnedReceipt--addOrderReturnReceiptDetail failed.");
                return BeanConvertUtils.convert(flowResponse, Response.class);
            }
            boolean createSuccess = returnRequestService.createOrderReturnedReceipt(flowResponse.getResultObject());
            if (!createSuccess) {
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_20102.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_20102.getName());
            } else {
                response.setSuccess(true);
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                response.setResultObject(flowResponse.getResultObject().getReturnRequest().getId());
                orderLogService.saveOrderLog(receiptDto.getReturnRequest().getOrderId(), receiptDto.getReturnRequest().getState(), "拒收退货单已创建！", receiptDto.getReturnRequest().getProfileId());
            }
        } catch (Exception e) {
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }
}

