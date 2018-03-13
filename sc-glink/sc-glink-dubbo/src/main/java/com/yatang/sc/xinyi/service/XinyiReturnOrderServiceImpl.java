package com.yatang.sc.xinyi.service;

import java.util.List;

import javax.annotation.Resource;

import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.staticvalue.KiddOrderLogisticsType;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.common.utils.StringUtils;
import com.busi.mq.producer.SimpleMQProducer;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.yatang.sc.kidd.dto.common.KiddOrderLinesDto;
import com.yatang.sc.kidd.dto.common.KiddSenderInfoDto;
import com.yatang.sc.kidd.dto.returnrequest.KiddReturnOrderDto;
import com.yatang.sc.kidd.dto.returnrequest.KiddReturnOrderMqDto;
import com.yatang.sc.kidd.service.KiddFacadeService;
import com.yatang.sc.kidd.service.KiddUtils;
import com.yatang.sc.xinyi.dto.ResponseDto;
import com.yatang.sc.xinyi.dto.common.XinyiOrderLineDto;
import com.yatang.sc.xinyi.dto.common.XinyiSenderInfoDto;
import com.yatang.sc.xinyi.dto.returnrequest.XinyiReturnOrderDto;
import com.yatang.sc.xinyi.dto.returnrequest.XinyiReturnOrderRequestDto;
import com.yatang.sc.xinyi.service.kidd.XinyiReturnOrderProxyService;

import lombok.extern.slf4j.Slf4j;

/**
 * @描述:调用心怡退货接口实现类
 * @类名:XinyiReturnOrderServiceImpl
 * @作者: lvheping
 * @创建时间: 2017/10/18 14:03
 * @版本: v1.0
 */
@Service
@Slf4j
public class XinyiReturnOrderServiceImpl {
    @Resource(name = "returnOrderMQProducer")
    private SimpleMQProducer returnOrderMQProducer;//退货换货单
    @Autowired
    private KiddFacadeService kiddFacadeService;
    @Autowired
    private XinyiReturnOrderProxyService xinyiReturnOrderProxyService;
    @EventListener
    public Response<String> create(KiddReturnOrderDto kiddReturnOrderDto) {
        log.info("推送采购单：KiddEntryOrderDto "+ JSON.toJSONString(kiddReturnOrderDto));


        if (null == kiddReturnOrderDto || StringUtils.isEmpty(kiddReturnOrderDto.getWareHouseCode())) {
            log.error("service--add>>请求参数出错：{}", JSON.toJSONString(kiddReturnOrderDto));
            throw new RuntimeException("service--add>>请求参数出错" + JSON.toJSONString(kiddReturnOrderDto));
        }
        KiddUtils.checkXinyiListening(kiddFacadeService.warehouseInterfaceProvider(kiddReturnOrderDto.getWareHouseCode()));
        //逻辑判断
        Response<String> response = new Response<>();
        try {
            XinyiReturnOrderRequestDto xinyiReturnOrderRequestDto = new XinyiReturnOrderRequestDto();
            XinyiReturnOrderDto convert = BeanConvertUtils.convert(kiddReturnOrderDto, XinyiReturnOrderDto.class);
            convert.setOrderType(KiddOrderLogisticsType.KIDD_THRK);//设置传送心怡类型（因为心怡暂时不支持HHRK）
            convert.setReturnOrderCode(kiddReturnOrderDto.getEntryOrderCode());
            //设置发货人信息
            XinyiSenderInfoDto xinyiSenderInfoDto = new XinyiSenderInfoDto();
            KiddSenderInfoDto senderInfo = kiddReturnOrderDto.getSenderInfo();
            if (null!=senderInfo){//设置发货人信息
                xinyiSenderInfoDto.setCompany(senderInfo.getSenderCompany());//设置公司名称
                xinyiSenderInfoDto.setName(senderInfo.getSenderName());//发货人姓名
                xinyiSenderInfoDto.setCity(senderInfo.getSenderCity());//发货人城市
                xinyiSenderInfoDto.setEmail(senderInfo.getSenderEmail());//发货人电子邮箱
                xinyiSenderInfoDto.setMobile(senderInfo.getSenderMobile());//发货人移动电话
                xinyiSenderInfoDto.setDetailAddress(senderInfo.getSenderDetailAddress());//发货人详细地址
                xinyiSenderInfoDto.setProvince(senderInfo.getSenderProvince());//发货人省份
                xinyiSenderInfoDto.setArea(senderInfo.getSenderArea());//发货人区域
            }
           convert.setSenderInfo(xinyiSenderInfoDto);//设置发货人信息
            List<XinyiOrderLineDto> xinyiOrderLineDtos = BeanConvertUtils.convertList(kiddReturnOrderDto.getOrderLines(), XinyiOrderLineDto.class);
            for (XinyiOrderLineDto xinyiOrderLineDto:xinyiOrderLineDtos){
                xinyiOrderLineDto.setOwnerCode(kiddReturnOrderDto.getOwnerCode());//设置子公司编码
            }
            xinyiReturnOrderRequestDto.setOrderLines(xinyiOrderLineDtos);//设置商品信息
            xinyiReturnOrderRequestDto.setReturnOrder(convert);//设置基本信息
            log.info("推送退货单调用心怡接口参数：xinyiReturnOrderRequestDto {}", JSON.toJSONString(xinyiReturnOrderRequestDto));
            ResponseDto xinyiEntryOrderResponseDto = xinyiReturnOrderProxyService.create(xinyiReturnOrderRequestDto);
            log.info("推送退货单调用心怡接口返回结果：xinyiEntryOrderResponseDto {}", JSON.toJSONString(xinyiEntryOrderResponseDto));
            if (Objects.equal(xinyiEntryOrderResponseDto.getFlag(), ResponseDto.SUCCESS)) {
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                response.setSuccess(true);
            } else {
                response.setCode(CommonsEnum.RESPONSE_5001.getCode());
                response.setSuccess(false);
                response.setErrorMessage(xinyiEntryOrderResponseDto.getMessage());
            }

        }catch (Exception e){
             response.setCode(CommonsEnum.RESPONSE_500.getCode());
            log.error(ExceptionUtils.getFullStackTrace(e));
            response.setSuccess(false);
            response.setErrorMessage(Throwables.getRootCause(e).getMessage());
        }
        return response;
    }

    /**
     * 退货确认单
     * @param xinyiReturnOrderRequestDto
     * @return
     */
    @EventListener
    public Response<String> returnOrderConfirm(XinyiReturnOrderRequestDto xinyiReturnOrderRequestDto){
        log.info("心怡退货单确认回调参数 kiddPurchaseOrderConfirmDto{}",JSON.toJSONString(xinyiReturnOrderRequestDto));
        Response response = new Response();
        try {
            List<XinyiOrderLineDto> orderLines = xinyiReturnOrderRequestDto.getOrderLines();
            XinyiReturnOrderDto returnOrder = xinyiReturnOrderRequestDto.getReturnOrder();
            if (returnOrder==null||orderLines==null||orderLines.size()<1){
                log.info("退货确认传入数据为空");
                throw new RuntimeException("退货确认传入数据为空");
            }

            KiddReturnOrderMqDto kiddReturnOrderMqDto = new KiddReturnOrderMqDto();
            kiddReturnOrderMqDto.setReturnOrderCode(returnOrder.getReturnOrderCode());//ERP的退货入库单编码
            kiddReturnOrderMqDto.setOrderType(returnOrder.getOrderType());//单据类型,THRK=退货入库，HHRK=换货入库
            kiddReturnOrderMqDto.setWarehouseCode(returnOrder.getWarehouseCode());//仓库编码
            kiddReturnOrderMqDto.setStatus(KiddOrderLogisticsType.KIDD_FULFILLED);//单据状态
            List<KiddOrderLinesDto> kiddOrderLinesDtos = BeanConvertUtils.convertList(orderLines, KiddOrderLinesDto.class);
            kiddReturnOrderMqDto.setOrderLinesDtos(kiddOrderLinesDtos);//设置商品信息
            log.info("发送心怡的退货结果MQ消息:{}", JSON.toJSONString(kiddReturnOrderMqDto));
            returnOrderMQProducer.sendMsg(kiddReturnOrderMqDto);
            response.setCode("001");
            response.setSuccess(true);
        }catch (Exception e){
            response.setCode("002");
            response.setSuccess(false);
            response.setErrorMessage("退货单确认失败");
        }
        return response;
    }
}
