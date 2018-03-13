package com.yatang.sc.xinyi.service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.common.utils.StringUtils;
import com.busi.mq.producer.SimpleMQProducer;
import com.google.common.base.Objects;
import com.google.common.base.Throwables;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.kidd.dto.common.KiddSenderInfoDto;
import com.yatang.sc.kidd.dto.purchase.KiddConfirmEntryOrderDto;
import com.yatang.sc.kidd.dto.purchase.KiddEntryOrderDto;
import com.yatang.sc.kidd.dto.purchase.KiddPurchaseOrderConfirmDto;
import com.yatang.sc.kidd.service.KiddFacadeService;
import com.yatang.sc.kidd.service.KiddUtils;
import com.yatang.sc.staticvalue.KiddOrderLogisticsType;
import com.yatang.sc.xinyi.dto.ResponseDto;
import com.yatang.sc.xinyi.dto.common.XinyiOrderLineDto;
import com.yatang.sc.xinyi.dto.common.XinyiSenderInfoDto;
import com.yatang.sc.xinyi.dto.purchase.XinyiConfirmEntryOrderDto;
import com.yatang.sc.xinyi.dto.purchase.XinyiEntryOrderDto;
import com.yatang.sc.xinyi.dto.purchase.XinyiEntryOrderRequestDto;
import com.yatang.sc.xinyi.dto.purchase.XinyiEntryOrderResponseDto;
import com.yatang.sc.xinyi.dto.purchase.XinyiPurchaseOrderConfirmRequestDto;
import com.yatang.sc.xinyi.service.kidd.XinyiPurchaseProxyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @描述:新增采购订单调用心怡仓库的接口（入库单）
 * @类名:XinyiPurchaseServiceImpl
 * @作者: lvheping
 * @创建时间: 2017/9/25 10:20
 * @版本: v1.0
 */
@Slf4j
@Service
public class XinyiPurchaseServiceImpl {
    @Resource(name = "entryOrderMQProducer")
    private SimpleMQProducer entryOrderMQProducer;//采购订单

     @Autowired
    private XinyiPurchaseProxyService xinyiPurchaseProxyService;
    @Autowired
    private KiddFacadeService kiddFacadeService;
    @Autowired
    private WarehouseLogicQueryDubboService warehouseLogicQueryDubboService;
    @EventListener
     public Response<String> add(KiddEntryOrderDto kiddEntryOrderDto){
         log.info("推送采购单：KiddEntryOrderDto {}", JSON.toJSONString(kiddEntryOrderDto));


        if (null == kiddEntryOrderDto || StringUtils.isEmpty(kiddEntryOrderDto.getWareHouseCode())) {
            log.error("service--add>>请求参数出错：{}", JSON.toJSONString(kiddEntryOrderDto));
            throw new RuntimeException("service--add>>请求参数出错" + JSON.toJSONString(kiddEntryOrderDto));
        }
        KiddUtils.checkXinyiListening(kiddFacadeService.warehouseInterfaceProvider(kiddEntryOrderDto.getWareHouseCode()));
         //逻辑判断
         Response<String> response = new Response<>();
         try {

             XinyiEntryOrderRequestDto xinyiEntryOrderRequestDto = new XinyiEntryOrderRequestDto();
             XinyiEntryOrderDto convert = BeanConvertUtils.convert(kiddEntryOrderDto, XinyiEntryOrderDto.class);//采购单基本信息
             convert.setOrderCreateTime(kiddEntryOrderDto.getCreateTimeERP());//采购单创建时间
             //发货人信息（供应商信息）
             XinyiSenderInfoDto senderInfo = new XinyiSenderInfoDto();
             KiddSenderInfoDto senderInfoDto = kiddEntryOrderDto.getSenderInfoDto();
             senderInfo.setArea(senderInfoDto.getSenderArea());//供应商证照信息的公司所在区
             senderInfo.setCity(senderInfoDto.getSenderCity());//供应商证照信息的公司所在市
             senderInfo.setCompany(senderInfoDto.getSenderCompany());//供应商公司名称
             senderInfo.setDetailAddress(senderInfoDto.getSenderDetailAddress());//供应商证照信息的公司详细地址
             senderInfo.setEmail(senderInfoDto.getSenderEmail());//供应商地点的联系信息的供应商邮箱
             senderInfo.setMobile(senderInfoDto.getSenderMobile());//供应商地点的联系信息的供应商电话
             senderInfo.setProvince(senderInfoDto.getSenderProvince());//供应商证照信息的公司所在省
             senderInfo.setName(senderInfoDto.getSenderName());//供应商地点的联系信息的供应商姓名
             convert.setSenderInfo(senderInfo);
             xinyiEntryOrderRequestDto.setEntryOrder(convert);
             //商品详细信息
             List<XinyiOrderLineDto> xinyiOrderLineDtos = BeanConvertUtils.convertList(kiddEntryOrderDto.getOrderLines(), XinyiOrderLineDto.class);
             for (XinyiOrderLineDto xinyiOrderLineDto:xinyiOrderLineDtos){
                 xinyiOrderLineDto.setOwnerCode(kiddEntryOrderDto.getOwnerCode());//设置货主编码
             }
             xinyiEntryOrderRequestDto.setOrderLines(xinyiOrderLineDtos);

             XinyiEntryOrderResponseDto xinyiEntryOrderResponseDto = xinyiPurchaseProxyService.create(xinyiEntryOrderRequestDto);
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
                 log.info("仓库接收收货单成功发送接单MQ信息 kiddPurchaseOrderConfirmDto{} ",JSON.toJSONString(kiddPurchaseOrderConfirmDto));
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
    public Response<String> purchaseOrderConfirm(XinyiPurchaseOrderConfirmRequestDto xinyiPurchaseOrderConfirmRequestDto){
     log.info("心怡入库单确认回调参数 kiddPurchaseOrderConfirmDto:{}",JSON.toJSONString(xinyiPurchaseOrderConfirmRequestDto));
        Response response = new Response();
     try {
         XinyiConfirmEntryOrderDto entryOrder = xinyiPurchaseOrderConfirmRequestDto.getEntryOrder();
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
         KiddPurchaseOrderConfirmDto convert = BeanConvertUtils.convert(xinyiPurchaseOrderConfirmRequestDto, KiddPurchaseOrderConfirmDto.class);
         entryOrderMQProducer.sendMsg(convert);
         response.setCode("001");
         response.setSuccess(true);
     }catch (Exception e){
         response.setCode("002");
         response.setSuccess(false);
         response.setErrorMessage("采购订单确认失败");
     }
        return response;
    }

}
