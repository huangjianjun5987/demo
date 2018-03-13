package com.yatang.sc.juban.service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.common.utils.StringUtils;
import com.busi.mq.producer.SimpleMQProducer;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.juban.dto.ResponseDto;
import com.yatang.sc.juban.dto.common.JubanOrderLineDto;
import com.yatang.sc.juban.dto.common.JubanSenderInfoDto;
import com.yatang.sc.juban.dto.returnrequest.JubanReturnOrderDto;
import com.yatang.sc.juban.dto.returnrequest.JubanReturnOrderRequestDto;
import com.yatang.sc.juban.service.kidd.JubanReturnOrderProxyService;
import com.yatang.sc.kidd.dto.common.KiddOrderLinesDto;
import com.yatang.sc.kidd.dto.common.KiddSenderInfoDto;
import com.yatang.sc.kidd.dto.returnrequest.KiddReturnOrderDto;
import com.yatang.sc.kidd.dto.returnrequest.KiddReturnOrderMqDto;
import com.yatang.sc.kidd.service.KiddFacadeService;
import com.yatang.sc.kidd.service.KiddUtils;
import com.yatang.sc.staticvalue.KiddOrderLogisticsType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @描述:调用桔瓣退货接口实现类
 * @类名:JubanReturnOrderServiceImpl
 * @作者: lvheping
 * @创建时间: 2017/10/18 14:03
 * @版本: v1.0
 */
@Service
@Slf4j
public class JubanReturnOrderServiceImpl {
    @Resource(name = "returnOrderMQProducer")
    private SimpleMQProducer returnOrderMQProducer;//退货换货单
    @Autowired
    private KiddFacadeService kiddFacadeService;
    @Autowired
    private JubanReturnOrderProxyService jubanReturnOrderProxyService;
    @EventListener
    public Response<String> create(KiddReturnOrderDto kiddReturnOrderDto) {
        log.info("推送采购单：KiddEntryOrderDto {}", JSON.toJSONString(kiddReturnOrderDto));


        if (null == kiddReturnOrderDto || StringUtils.isEmpty(kiddReturnOrderDto.getWareHouseCode())) {
            log.error("service--add>>请求参数出错：{}", JSON.toJSONString(kiddReturnOrderDto));
            throw new RuntimeException("service--add>>请求参数出错" + JSON.toJSONString(kiddReturnOrderDto));
        }
        KiddUtils.checkJubanListening(kiddFacadeService.warehouseInterfaceProvider(kiddReturnOrderDto.getWareHouseCode()));
        //逻辑判断
        Response<String> response = new Response<>();
        try {
            JubanReturnOrderRequestDto jubanReturnOrderRequestDto = new JubanReturnOrderRequestDto();
            JubanReturnOrderDto convert = BeanConvertUtils.convert(kiddReturnOrderDto, JubanReturnOrderDto.class);
            convert.setOrderType(KiddOrderLogisticsType.KIDD_THRK);//设置传送桔瓣类型（因为心怡暂时不支持HHRK）
            convert.setReturnOrderCode(kiddReturnOrderDto.getEntryOrderCode());
            //设置发货人信息
            JubanSenderInfoDto jubanSenderInfoDto = new JubanSenderInfoDto();
            KiddSenderInfoDto senderInfo = kiddReturnOrderDto.getSenderInfo();
            if (null!=senderInfo){//设置发货人信息
                jubanSenderInfoDto.setCompany(senderInfo.getSenderCompany());//设置公司名称
                jubanSenderInfoDto.setName(senderInfo.getSenderName());//发货人姓名
                jubanSenderInfoDto.setCity(senderInfo.getSenderCity());//发货人城市
                jubanSenderInfoDto.setEmail(senderInfo.getSenderEmail());//发货人电子邮箱
                jubanSenderInfoDto.setMobile(senderInfo.getSenderMobile());//发货人移动电话
                jubanSenderInfoDto.setDetailAddress(senderInfo.getSenderDetailAddress());//发货人详细地址
                jubanSenderInfoDto.setProvince(senderInfo.getSenderProvince());//发货人省份
                jubanSenderInfoDto.setArea(senderInfo.getSenderArea());//发货人区域
            }
           convert.setSenderInfo(jubanSenderInfoDto);//设置发货人信息
            List<JubanOrderLineDto> jubanOrderLineDtos = BeanConvertUtils.convertList(kiddReturnOrderDto.getOrderLines(), JubanOrderLineDto.class);
            for (JubanOrderLineDto jubanOrderLineDto:jubanOrderLineDtos){
                jubanOrderLineDto.setOwnerCode(kiddReturnOrderDto.getOwnerCode());//设置子公司编码
            }
            jubanReturnOrderRequestDto.setOrderLines(jubanOrderLineDtos);//设置商品信息
            jubanReturnOrderRequestDto.setReturnOrder(convert);//设置基本信息
            log.info("推送退货单调用桔瓣接口参数：jubanReturnOrderRequestDto {}", JSON.toJSONString(jubanReturnOrderRequestDto));
            ResponseDto jubanEntryOrderResponseDto = jubanReturnOrderProxyService.create(jubanReturnOrderRequestDto);
            log.info("推送退货单调用桔瓣接口返回结果：xinyiEntryOrderResponseDto {}", JSON.toJSONString(jubanEntryOrderResponseDto));
            if (Objects.equal(jubanEntryOrderResponseDto.getFlag(), ResponseDto.SUCCESS)) {
                response.setCode(CommonsEnum.RESPONSE_200.getCode());
                response.setSuccess(true);
            } else {
                response.setCode(CommonsEnum.RESPONSE_5001.getCode());
                response.setSuccess(false);
                response.setErrorMessage(jubanEntryOrderResponseDto.getMessage());
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
     * @param jubanReturnOrderRequestDto
     * @return
     */
    @EventListener
    public Response<String> returnOrderConfirm(JubanReturnOrderRequestDto jubanReturnOrderRequestDto){
        log.info("桔瓣退货单确认回调参数 kiddPurchaseOrderConfirmDto{}",JSON.toJSONString(jubanReturnOrderRequestDto));
        Response response = new Response();
        try {
            List<JubanOrderLineDto> orderLines = jubanReturnOrderRequestDto.getOrderLines();
            JubanReturnOrderDto returnOrder = jubanReturnOrderRequestDto.getReturnOrder();
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
            log.info("发送桔瓣的退货结果MQ消息:{}", JSON.toJSONString(kiddReturnOrderMqDto));
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
