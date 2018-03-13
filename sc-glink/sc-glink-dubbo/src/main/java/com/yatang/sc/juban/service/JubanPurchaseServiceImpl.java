package com.yatang.sc.juban.service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.common.utils.StringUtils;
import com.busi.mq.producer.SimpleMQProducer;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.juban.dto.ResponseDto;
import com.yatang.sc.juban.dto.common.JubanOrderLineDto;
import com.yatang.sc.juban.dto.common.JubanSenderInfoDto;
import com.yatang.sc.juban.dto.purchase.JubanConfirmEntryOrderDto;
import com.yatang.sc.juban.dto.purchase.JubanEntryOrderDto;
import com.yatang.sc.juban.dto.purchase.JubanEntryOrderRequestDto;
import com.yatang.sc.juban.dto.purchase.JubanEntryOrderResponseDto;
import com.yatang.sc.juban.dto.purchase.JubanPurchaseOrderConfirmRequestDto;
import com.yatang.sc.juban.service.kidd.JubanPurchaseProxyService;
import com.yatang.sc.kidd.dto.common.KiddSenderInfoDto;
import com.yatang.sc.kidd.dto.purchase.KiddConfirmEntryOrderDto;
import com.yatang.sc.kidd.dto.purchase.KiddEntryOrderDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseOrderConfirmDto;
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
 * @描述:新增采购订单调用桔瓣仓库的接口（入库单）
 * @类名:JubanPurchaseServiceImpl
 * @作者: lvheping
 * @创建时间: 2017/9/25 10:20
 * @版本: v1.0
 */
@Slf4j
@Service
public class JubanPurchaseServiceImpl {
    @Resource(name = "entryOrderMQProducer")
    private SimpleMQProducer entryOrderMQProducer;//采购订单

     @Autowired
    private JubanPurchaseProxyService jubanPurchaseProxyService;
    @Autowired
    private KiddFacadeService kiddFacadeService;
    @Autowired
    private WarehouseLogicQueryDubboService warehouseLogicQueryDubboService;
    @EventListener
     public Response<String> add(KiddEntryOrderDto kiddEntryOrderDto){
         log.info("推送采购单：KiddEntryOrderDto {}", JSON.toJSONString(kiddEntryOrderDto));


        if (kiddEntryOrderDto==null || StringUtils.isEmpty(kiddEntryOrderDto.getWareHouseCode())) {
            log.error("service--add>>请求参数出错：{}", JSON.toJSONString(kiddEntryOrderDto));
            throw new RuntimeException("service--add>>请求参数出错" + JSON.toJSONString(kiddEntryOrderDto));
        }
        KiddUtils.checkJubanListening(kiddFacadeService.warehouseInterfaceProvider(kiddEntryOrderDto.getWareHouseCode()));
         //逻辑判断
         Response<String> response = new Response<>();
         try {

             JubanEntryOrderRequestDto xinyiEntryOrderRequestDto = new JubanEntryOrderRequestDto();
             JubanEntryOrderDto convert = BeanConvertUtils.convert(kiddEntryOrderDto, JubanEntryOrderDto.class);//采购单基本信息
             convert.setOrderCreateTime(kiddEntryOrderDto.getCreateTimeERP());//采购单创建时间
             //发货人信息（供应商信息）
             JubanSenderInfoDto jubanSenderInfoDto = new JubanSenderInfoDto();
             KiddSenderInfoDto senderInfoDto = kiddEntryOrderDto.getSenderInfoDto();
             jubanSenderInfoDto.setArea(senderInfoDto.getSenderArea());//供应商证照信息的公司所在区
             jubanSenderInfoDto.setCity(senderInfoDto.getSenderCity());//供应商证照信息的公司所在市
             jubanSenderInfoDto.setCompany(senderInfoDto.getSenderCompany());//供应商公司名称
             jubanSenderInfoDto.setDetailAddress(senderInfoDto.getSenderDetailAddress());//供应商证照信息的公司详细地址
             jubanSenderInfoDto.setEmail(senderInfoDto.getSenderEmail());//供应商地点的联系信息的供应商邮箱
             jubanSenderInfoDto.setMobile(senderInfoDto.getSenderMobile());//供应商地点的联系信息的供应商电话
             jubanSenderInfoDto.setProvince(senderInfoDto.getSenderProvince());//供应商证照信息的公司所在省
             jubanSenderInfoDto.setName(senderInfoDto.getSenderName());//供应商地点的联系信息的供应商姓名
             convert.setSenderInfo(jubanSenderInfoDto);
             xinyiEntryOrderRequestDto.setEntryOrder(convert);
             //商品详细信息
             List<JubanOrderLineDto> xinyiOrderLineDtos = BeanConvertUtils.convertList(kiddEntryOrderDto.getOrderLines(), JubanOrderLineDto.class);
             for (JubanOrderLineDto xinyiOrderLineDto:xinyiOrderLineDtos){
                 xinyiOrderLineDto.setOwnerCode(kiddEntryOrderDto.getOwnerCode());//设置货主编码
             }
             xinyiEntryOrderRequestDto.setOrderLines(xinyiOrderLineDtos);

             JubanEntryOrderResponseDto xinyiEntryOrderResponseDto = jubanPurchaseProxyService.create(xinyiEntryOrderRequestDto);
             if (Objects.equal(xinyiEntryOrderResponseDto.getFlag(), ResponseDto.SUCCESS)) {
                 //心怡仓库接单成功自己修改状态为已下发
                 KiddPurchaseOrderConfirmDto kiddPurchaseOrderConfirmDto = new KiddPurchaseOrderConfirmDto();
                 KiddConfirmEntryOrderDto kiddConfirmEntryOrderDto = new KiddConfirmEntryOrderDto();
                 kiddConfirmEntryOrderDto.setEntryOrderCode(convert.getEntryOrderCode());//入库单编码
                 kiddConfirmEntryOrderDto.setOwnerCode(convert.getOwnerCode());//货主编码
                 kiddConfirmEntryOrderDto.setWarehouseCode(convert.getWarehouseCode());//仓库编码
                 kiddConfirmEntryOrderDto.setEntryOrderType(convert.getOrderType());//单据类型
                 kiddConfirmEntryOrderDto.setStatus(KiddOrderLogisticsType.KIDD_ACCEPT);//状态
                 kiddPurchaseOrderConfirmDto.setEntryOrder(kiddConfirmEntryOrderDto);
                 log.info("仓库接收收货单成功发送接单MQ信息 kiddPurchaseOrderConfirmDto{} "+JSON.toJSONString(kiddPurchaseOrderConfirmDto));
                 entryOrderMQProducer.sendMsg(kiddPurchaseOrderConfirmDto);
                 response.setSuccess(true);
             } else {
                 response.setSuccess(false);
                 response.setErrorMessage(xinyiEntryOrderResponseDto.getMessage());
             }
         } catch (Exception e) {
//             log.error("推送采购单失败", e);

             log.error(ExceptionUtils.getFullStackTrace(e));
             response.setSuccess(false);
             response.setErrorMessage(Throwables.getRootCause(e).getMessage());
         }
             return response;
     };

    /**
     * 入库单确认接口
     * @return
     */
    @EventListener
    public Response<String> purchaseOrderConfirm(JubanPurchaseOrderConfirmRequestDto jubanPurchaseOrderConfirmRequestDto){
     log.info("桔瓣入库单确认回调参数 kiddPurchaseOrderConfirmDto:{}",JSON.toJSONString(jubanPurchaseOrderConfirmRequestDto));
        Response response = new Response();
     try {
         JubanConfirmEntryOrderDto entryOrder = jubanPurchaseOrderConfirmRequestDto.getEntryOrder();
         if (null==entryOrder||null==entryOrder.getEntryOrderCode()){
             response.setCode("002");
             response.setSuccess(false);
             response.setErrorMessage("收货单号不能为空");
         }
         Response<LogicWarehouseDto> logicWarehouseDtoResponse = warehouseLogicQueryDubboService.selectLogicWarehouseByPrimaryKey(entryOrder.getWarehouseCode());
         if (!logicWarehouseDtoResponse.isSuccess()||null==logicWarehouseDtoResponse.getResultObject()){
             response.setCode("002");
             response.setSuccess(false);
             response.setErrorMessage("采购订单确认失败仓库编码在系统中不存在 "+entryOrder.getWarehouseCode());
         }
         KiddPurchaseOrderConfirmDto convert = BeanConvertUtils.convert(jubanPurchaseOrderConfirmRequestDto, KiddPurchaseOrderConfirmDto.class);
         entryOrderMQProducer.sendMsg(convert);
         response.setCode("001");
         response.setSuccess(true);
     }catch (Exception e){
         log.error(ExceptionUtils.getFullStackTrace(e));
         response.setCode("002");
         response.setSuccess(false);
         response.setErrorMessage("采购订单确认失败");
     }
        return response;
    }

}
