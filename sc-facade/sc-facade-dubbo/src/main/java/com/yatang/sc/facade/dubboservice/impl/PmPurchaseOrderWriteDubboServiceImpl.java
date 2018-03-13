package com.yatang.sc.facade.dubboservice.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.mq.producer.SimpleMQProducer;
import com.google.common.base.Throwables;
import com.yatang.sc.common.lock.RedisDistributedLockFactory;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.domain.PmPurchaseOrderPo;
import com.yatang.sc.facade.domain.pm.PmPurchaseOrderInfoPo;
import com.yatang.sc.facade.domain.pm.SendMqInfo;
import com.yatang.sc.facade.dto.pm.ClosePurchaseDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderExtDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptSHDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseOrderWriteDubboService;
import com.yatang.sc.facade.dubboservice.PmPurchaseReceiptWriteDubboService;
import com.yatang.sc.facade.enums.PmAdrType;
import com.yatang.sc.facade.enums.PmPurchaseBusinessType;
import com.yatang.sc.facade.enums.PmPurchaseOrderAcceptStatus;
import com.yatang.sc.facade.enums.PmPurchaseOrderStatus;
import com.yatang.sc.facade.exception.SaveAuditProcessException;
import com.yatang.sc.facade.flow.PmPurchaseOrderQueryFlowService;
import com.yatang.sc.facade.flow.PmPurchaseOrderWriteFlowService;
import com.yatang.sc.facade.service.PmPurchaseOrderService;
import com.yatang.sc.facade.service.PmPurchaseReceiptService;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.provider.order.ProviderOrderMsg;
import com.yatang.sc.order.provider.order.ProviderOrderStatus;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @描述: 商品采购单的writedubbo实现类
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/7/26 10:20
 * @版本: v1.0
 */
@Slf4j
@Service("pmPurchaseOrderWriteDubboService")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PmPurchaseOrderWriteDubboServiceImpl implements PmPurchaseOrderWriteDubboService {


    private final PmPurchaseOrderService pmPurchaseOrderService;

    private final PmPurchaseOrderQueryFlowService pmPurchaseOrderQueryFlowService;//flow

    private final PmPurchaseOrderWriteFlowService pmPurchaseOrderWriteFlowService;

    private final PmPurchaseReceiptService pmPurchaseReceiptService;
    private final RedisDistributedLockFactory mRedisDistributedLockFactory;
    @Resource(name = "purchaseOrderMQProducer")
    private SimpleMQProducer purchaseOrderMQProducer;//MQ消息发送


    @Autowired
    OrderMessageSender orderMessageSender;

    private final PmPurchaseReceiptWriteDubboService pmPurchaseReceiptWriteDubboService;


    @Override
    public Response<String> addPmPurchaseOrder(PmPurchaseOrderExtDto pmPurchaseOrderExtDto) {
        Response<String> response = new Response<>();
        try {
            log.info("start----addPmPurchaseOrder---pmPurchaseOrderExtDto:{}", JSON.toJSONString(pmPurchaseOrderExtDto));
            //赋值操作
            if (pmPurchaseOrderExtDto.getPmPurchaseOrderItems().size() > 0) {//存在订单
                Response<PmPurchaseOrderExtDto> pmPurchaseResponse = pmPurchaseOrderQueryFlowService.checkAndResetPmPurchaseOrderExt(pmPurchaseOrderExtDto);
                if (!CommonsEnum.RESPONSE_200.getCode().equals(pmPurchaseResponse.getCode())) {
                    log.error("获取拼接的dto失败");
                    return BeanConvertUtils.convert(pmPurchaseResponse, Response.class);
                } else {
                    pmPurchaseOrderExtDto = pmPurchaseResponse.getResultObject();
                }

            }
            //校验成功的
            Response<String> stringResponse = pmPurchaseOrderWriteFlowService.addPmPurchaseOrder(pmPurchaseOrderExtDto);

            if (stringResponse.isSuccess()) {//插入成功
                response.setResultObject(stringResponse.getResultObject());
                response.setSuccess(true);
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());

            } else {//插入失败
                response.setCode(CommonsEnum.RESPONSE_10025.getCode());
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_10025.getName());
            }
        } catch (SaveAuditProcessException e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(Throwables.getRootCause(e).getMessage());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<Void> batchDeletePmPurchaseOrderByIds(List<String> pmPurchaseOrderIdList) {
        Response<Void> response = new Response<>();
        try {
            //dto2po

            log.info("start----batchDeletePmPurchaseOrderByIds---pmPurchaseOrderIdList:{}", JSON.toJSONString(pmPurchaseOrderIdList));
            boolean batchDeleteSuccess = pmPurchaseOrderService.batchDeletePmPurchaseOrderByIds(pmPurchaseOrderIdList);
            if (batchDeleteSuccess) {//插入成功
                response.setSuccess(true);
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            } else {
                response.setCode(CommonsEnum.RESPONSE_10026.getCode());
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_10026.getName());
            }
            log.info("end----batchDeletePmPurchaseOrderByIds");
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<Void> updatePmPurchaseOrder(PmPurchaseOrderExtDto pmPurchaseOrderExtDto) {
        Response<Void> response = new Response<>();
        try {
            log.info("start----updatePmPurchaseOrder>>更新采购订单>>pmPurchaseOrderExtDto:{}", JSON.toJSONString(pmPurchaseOrderExtDto));
            //当订单子项为空删除单
            if (pmPurchaseOrderExtDto.getPmPurchaseOrderItems().size() > 0) {
                //校验并拼接
                Response<PmPurchaseOrderExtDto> pmPurchaseResponse = pmPurchaseOrderQueryFlowService.checkAndResetPmPurchaseOrderExt(pmPurchaseOrderExtDto);
                if (!CommonsEnum.RESPONSE_200.getCode().equals(pmPurchaseResponse.getCode())) {
                    return BeanConvertUtils.convert(pmPurchaseResponse, Response.class);
                } else {
                    pmPurchaseOrderExtDto = pmPurchaseResponse.getResultObject();
                }
            }

            boolean updateSuccess = pmPurchaseOrderWriteFlowService.updatePmPurchaseOrder(pmPurchaseOrderExtDto);
            if (updateSuccess) {//更新成功
                response.setSuccess(true);
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            } else {
                response.setCode(CommonsEnum.RESPONSE_10024.getCode());
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_10024.getName());
            }
            log.info("end----updatePmPurchaseOrder");
        } catch (SaveAuditProcessException e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(Throwables.getRootCause(e).getMessage());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    /**
     * 审批流回调接口
     *
     * @param keyId         采购单主键id
     * @param auditorId     审核人id
     * @param auditorName   审核人姓名
     * @param auditorResult 审核结果 true 同意  false 拒绝
     * @return
     */
    @Override
    public Response<Boolean> auditCallbackPurchaseOrderInfo(Long keyId, String auditorId, String auditorName, Boolean auditorResult) {
        Response response = new Response();
        try {
            log.info("start----auditCallbackPurchaseOrderInfo---keyId:{},auditorId:{},auditorName:{},auditorResult:{}", keyId, auditorId, auditorName, auditorResult);
            if (auditorId == null || auditorResult == null) {
                throw new RuntimeException("参数不合法！");
            }
            PmPurchaseOrderInfoPo pmPurchaseOrderInfoPo = pmPurchaseOrderService.getPurchaseOrderInfoById(keyId);
            if (pmPurchaseOrderInfoPo == null) {
                throw new RuntimeException("查询采购单失败！");
            }

            PmPurchaseOrderDto purchaseOrderDto = new PmPurchaseOrderDto();
            purchaseOrderDto.setId(keyId);
            purchaseOrderDto.setAuditUserId(auditorId);
            if (auditorResult) {

                //仓库类型：审核通过后修改为已接单
                if (PmAdrType.ADR_WAREHOUSE.getCode().equals(pmPurchaseOrderInfoPo.getAdrType())) {
                    purchaseOrderDto.setSpAcceptStatus(PmPurchaseOrderAcceptStatus.ORDER_ACCEPT.getCode());
                }
                purchaseOrderDto.setStatus(PmPurchaseOrderStatus.AUDITED_STATUS.getCode());

            } else {
                purchaseOrderDto.setStatus(PmPurchaseOrderStatus.REJECT_STATUS.getCode());
            }
            purchaseOrderDto.setAuditTime(new Date());

            response = auditPurchaseOrder(purchaseOrderDto);
            if (!response.isSuccess()) {
                return response;
            }

            if (PmPurchaseOrderStatus.AUDITED_STATUS.getCode().equals(purchaseOrderDto.getStatus())) {
                PmPurchaseOrderInfoPo purchaseOrderInfoById = pmPurchaseOrderService.getPurchaseOrderInfoById(purchaseOrderDto.getId());
                if (purchaseOrderInfoById.getAdrType() == 0) {
                    SendMqInfo sendMqInfo = new SendMqInfo();
                    sendMqInfo.setPurchaseOrderNo(purchaseOrderInfoById.getPurchaseOrderNo());
                    // 设置延迟级别2 延迟5S
                    SendResult sendResult = purchaseOrderMQProducer.sendDelayMsg(sendMqInfo, 2);
                    log.info("mq消息:{}", JSON.toJSONString(sendResult));
                }
            }
            log.info("end----auditPurchaseOrderInfo");
        } catch (Exception e) {
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    public Response<Boolean> auditPurchaseOrder(PmPurchaseOrderDto convert) {
        Response response = new Response();

        boolean b = true;
        PmPurchaseOrderPo purchaseOrderPo = BeanConvertUtils.convert(convert, PmPurchaseOrderPo.class);
        if (PmPurchaseOrderStatus.AUDITED_STATUS.getCode().equals(convert.getStatus())) {
            PmPurchaseOrderInfoPo purchaseOrderInfoById = pmPurchaseOrderService.getPurchaseOrderInfoById(convert.getId());
            PmPurchaseOrderDto convert2 = BeanConvertUtils.convert(purchaseOrderInfoById, PmPurchaseOrderDto.class);
            Response<PmPurchaseOrderDto> pmPurchaseOrderDtoResponse = pmPurchaseOrderQueryFlowService.auditPurchaseOrderInfo(convert2);
            if (!pmPurchaseOrderDtoResponse.isSuccess()) {
                return BeanConvertUtils.convert(pmPurchaseOrderDtoResponse, Response.class);
            }
            purchaseOrderPo.setEstimatedDeliveryDate(pmPurchaseOrderDtoResponse.getResultObject().getEstimatedDeliveryDate());
            Response<Boolean> receipt = pmPurchaseReceiptWriteDubboService.createReceipt(convert2.getPurchaseOrderNo());
            if (!receipt.isSuccess()) {
                return BeanConvertUtils.convert(receipt, Response.class);
            }
        }
        purchaseOrderPo.setPurchaseOrderNo(null);
        b = pmPurchaseOrderService.auditPurchaseOrderInfo(purchaseOrderPo);
        response.setSuccess(b);
        response.setCode(CommonsEnum.RESPONSE_200.getCode());
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());

        return response;
    }

    @Override
    public Response<Boolean> batchClosePurcharse(ClosePurchaseDto closePurchaseDto) {
        Response<Boolean> response = new Response<>();
        try {

            log.info("start----batchClosePurcharse---closePurchaseDto:{}", closePurchaseDto);
            if (null == closePurchaseDto || null == closePurchaseDto.getModifyUserId()
                    || closePurchaseDto.getIds().isEmpty()) {
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
                response.setResultObject(false);
                return response;
            }
            int result = pmPurchaseOrderService.closePurchaseListByPrimaryKey(closePurchaseDto.getIds(),
                    closePurchaseDto.getModifyUserId());
            if (result > 0) {
                response.setSuccess(true);
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                response.setResultObject(true);
                return response;
            }
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setResultObject(false);
            log.info("end----batchClosePurcharse");
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setResultObject(false);
        }
        return response;
    }

    @Override
    public Response<Void> deletePmPurchaseOrderById(Long orderId) {
        Response<Void> response = new Response<>();
        try {
            //dto2po
            log.info("start----deletePmPurchaseOrderById---orderId:{}", orderId);
            boolean batchDeleteSuccess = pmPurchaseOrderService.deletePmPurchaseOrderById(orderId);
            if (batchDeleteSuccess) {//插入成功
                response.setSuccess(true);
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            } else {
                response.setCode(CommonsEnum.RESPONSE_10026.getCode());
                response.setSuccess(false);
                response.setErrorMessage(CommonsEnum.RESPONSE_10026.getName());
            }
            log.info("start----deletePmPurchaseOrderById");
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setSuccess(false);
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;
    }

    @Override
    public Response<Boolean> updateStatus(String orderNo, Integer status) {
        Response<Boolean> response = new Response<>();
        try {
            log.info("start----spAcceptStatus---orderNo:{},status:{}", orderNo, status);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
            response.setSuccess(pmPurchaseOrderService.updateStatus(orderNo, status));
            response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            response.setResultObject(false);
            log.info("end----spAcceptStatus");
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            response.setResultObject(false);
        }
        return response;
    }

    @Override
    public Response<Void> updateSpAcceptStatus(Long orderId, String updateUserId) {
        log.info("dubbo----updateSpAcceptStatus--更新供应商接单状态>>orderId:{},updateUserId:{}，spAcceptStatus：{}", orderId);
        Response<Void> response = new Response<>();

        try {

            //1.根据订单id查询订单信息
            PmPurchaseOrderPo pmPurchaseOrderPo = pmPurchaseOrderService.selectByPrimaryKey(orderId);
            if (!PmPurchaseBusinessType. CONSIGNMENT_SALE_TYPE.getCode().equals(pmPurchaseOrderPo.getBusinessMode())) {
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setSuccess(false);
                log.error("dubbo-updateSpAcceptStatus>>非寄售商品不能执行确认接单:{}", pmPurchaseOrderPo.getBusinessMode());
                response.setErrorMessage("非寄售商品不能执行确认接单");
                return response;
            }
            //判断为门店类型
            if (!PmAdrType.ADR_DIRECT_STORE.getCode().equals(pmPurchaseOrderPo.getAdrType())) {
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setSuccess(false);
                log.error("dubbo-updateSpAcceptStatus>>当前收货地点类型不能执行确认接单:{}", pmPurchaseOrderPo.getAdrType());
                response.setErrorMessage("当前收货地点类型不能执行确认接单");
                return response;
            }
            //确认接单状态
            if (!PmPurchaseOrderStatus.AUDITED_STATUS.getCode().equals(pmPurchaseOrderPo.getStatus())) {
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setSuccess(false);
                log.error("dubbo-updateSpAcceptStatus>>当前采购单状态为:{}不能执行接单操作", PmPurchaseOrderStatus.parse(pmPurchaseOrderPo.getStatus()).getDesc());
                response.setErrorMessage("当前采购单不能执行接单操作");
                return response;
            }
            if (!PmPurchaseOrderAcceptStatus.ORDER_UN_ACCEPT.getCode().equals(pmPurchaseOrderPo.getSpAcceptStatus())) {
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setSuccess(false);
                log.error("dubbo-updateSpAcceptStatus>>当前接单状态:{}不能执行接单操作", pmPurchaseOrderPo.getSpAcceptStatus());
                response.setErrorMessage("当前订单已确认接单不能执行重复操作");
                return response;
            }


            //2.执行更新操作，
            // 2.1同时更新采购订单的供应商接单状态
            PmPurchaseOrderPo infoPo = new PmPurchaseOrderPo();
            infoPo.setSpAcceptStatus(PmPurchaseOrderAcceptStatus.ORDER_ACCEPT.getCode());
            infoPo.setModifyUserId(updateUserId);
            infoPo.setModifyTime(new Date());
            infoPo.setId(pmPurchaseOrderPo.getId());
            if (pmPurchaseOrderService.updateByPrimaryKeySelective(infoPo) == 1) {
                response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                response.setSuccess(true);
            } else {
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setSuccess(false);
                response.setErrorMessage("更新当前采购单为接单状态失败");
                return response;
            }
            //2.2创建收货单
            pmPurchaseReceiptService.insertReceiptAndItmsByOrderCode(pmPurchaseOrderPo.getPurchaseOrderNo());
            // 2.3根据采购单对应的销售单号编号更改销售订单物流状态为"待出库";  发送mq
            String saleOrderId = pmPurchaseOrderPo.getSaleOrderId();
            if (StringUtils.isBlank(saleOrderId)) {
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setSuccess(false);
                log.error("dubbo-updateSpAcceptStatus>>采购单号为：{}的对应的销售单号为空", pmPurchaseOrderPo.getPurchaseOrderNo());
                response.setErrorMessage("当前收货地点类型不能执行确认接单");
                return response;
            }
            //发送mq

            ProviderOrderMsg providerOrderMsg = new ProviderOrderMsg();
            providerOrderMsg.setStatus(ProviderOrderStatus.ACCEPT);
            providerOrderMsg.setBody(saleOrderId);
            orderMessageSender.sendProviderOrderMsg(saleOrderId, providerOrderMsg);
        } catch (Exception e) {
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setSuccess(false);
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return response;


    }

    /**
     * 新增直送商品采购订单（供应商直送）
     *
     * @param pmPurchaseOrderExtDto
     * @return
     */
    @Override
    public Response<Void> addDirectSendingPmPurchaseOrder(PmPurchaseOrderExtDto pmPurchaseOrderExtDto) {
        return null;
    }

    @Override
    public Response<Boolean> updatePurchaseReceiptSHStatus(PmPurchaseReceiptSHDto pmPurchaseReceiptSHDto) {
        //
        Response<Boolean> response = new Response<>();
        RLock lock = mRedisDistributedLockFactory.getLock("updatePurchaseReceiptSHStatus" + pmPurchaseReceiptSHDto.getPurchaseReceiptNo());
        try {
            boolean tryLock = lock.tryLock();
            if (!tryLock) {
                response.setSuccess(true);
                response.setCode(CommonsEnum.RESPONSE_500.getCode());
                response.setErrorMessage("请不要重复请求");
                return response;
            }
            return pmPurchaseOrderWriteFlowService.updatePmPurchaseOrderReceipt(pmPurchaseReceiptSHDto);
        } finally {
            lock.unlock();
        }

    }
}
