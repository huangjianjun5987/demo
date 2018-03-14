package com.yatang.sc.order.handler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.yatang.sc.facade.dto.pm.PmPurchaseOrderDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderExtDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseOrderItemDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseOrderWriteDubboService;
import com.yatang.sc.facade.enums.PmAdrType;
import com.yatang.sc.facade.enums.PmPurchaseBusinessType;
import com.yatang.sc.facade.enums.PmPurchaseOrderType;
import com.yatang.sc.order.domain.ProviderShippingGroup;
import com.yatang.sc.order.service.ProviderShippingGroupService;
import com.yatang.sc.order.states.ShippingModes;
import com.yatang.xc.mbd.org.es.dubboservice.OrganizationIndexSCDubboService;
import com.yatang.xc.mbd.org.es.sc.dto.StoreScOrderCodeDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.yatang.sc.exception.MqBizFailureException;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.inventory.common.CommonsEnum;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderCargoDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderDeliveryInfoDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderReceiverInfoDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderRelateOrderDto;
import com.yatang.sc.kidd.service.KiddSaleOrderService;
import com.yatang.sc.order.domain.CommerceItem;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.OrderDictionary;
import com.yatang.sc.order.domain.OrderPrice;
import com.yatang.sc.order.domain.PaymentGroup;
import com.yatang.sc.order.domain.ShippingGroup;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.service.OrderDictionaryPropertiesService;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.OrderPriceService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.service.PaymentGroupService;
import com.yatang.sc.order.service.ShippingGroupService;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.OrderTypes;
import com.yatang.sc.order.states.PaymentStates;
import com.yatang.sc.order.states.ShippingStates;
import com.yatang.sc.vo.UpdateOrderVO;
import com.yatang.xc.mbd.biz.prod.dubboservice.ProductDubboService;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.ProductDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 新增销售订单mq发送中间件服务
 * Created by qiugang on 7/26/2017.
 */
@Service("sendWMSDeliveryMessage")
public class SendWMSDeliveryMessage extends OrderMsgListener {
    protected Logger log = LoggerFactory.getLogger(SendWMSDeliveryMessage.class);

    @Autowired
    OrderService orderService;

    @Autowired
    PaymentGroupService paymentGroupService;

    @Autowired
    ShippingGroupService shippingGroupService;

    @Autowired
    CommerceItemService commerceItemService;

    @Autowired
    KiddSaleOrderService kiddSaleOrderService;

    @Autowired
    ProductDubboService productDubboService;

    @Autowired
    WarehouseLogicQueryDubboService warehouseLogicQueryDubboService;

    @Autowired
    OrderPriceService orderPriceService;

    @Autowired
    OrderLogService orderLogService;

    @Autowired
    private OrderDictionaryPropertiesService orderDictionaryPropertiesService;

    @Autowired
    private OrganizationIndexSCDubboService organizationIndexSCDubboService;

    @Autowired
    private PmPurchaseOrderWriteDubboService mPmPurchaseOrderWriteDubboService;

    @Autowired
    ProviderShippingGroupService mProviderShippingGroupService;

    @Override
    public void handle(OrderMessage msg) throws Exception {
        log.info("handle msg order id: " + msg.getOrderId());
        Order order = orderService.selectByPrimaryKey(msg.getOrderId());
        if (order == null) {
            log.error("can't find order by id: " + msg.getOrderId());
            return;
        }

        String currentShippingState = order.getShippingState();
        if (ShippingStates.PENDING_TRANSFER.equals(currentShippingState)
                && OrderStates.APPROVED.equals(order.getOrderState())
                && (PaymentStates.DEBITED.equals(order.getPaymentState()) || PaymentStates.GSN.equals(order.getPaymentState()))) {

            if (order.getInteractivePendingRes()) {
                log.info("订单已发送到际联，等待接单通知:{}", order.getId());
                return;
            }
            ShippingGroup shippingGroup = shippingGroupService.selectByPrimaryKey(order.getShippingGroup());
            if (ShippingModes.PROVIDER_SHIPPING_MODE.getCode().equals(shippingGroup.getShippingModes())) {
                log.info("仓库直配订单，创建采购单:{}", order.getId());
                handleProviderOrder(order);
                return;
            }

            if (forbidSendOrderToWMS(order)) {
                log.info("该子公司{}订单不允许传送到仓库:{}", order.getBranchCompanyId(), order.getId());
                orderLogService.saveOrderLog(order.getId(), null, "订单不允许传送到仓库[被拦截]", "SYSTEM");
                return;
            }


            if (OrderMessageType.SendWMSDeliveryMessageCheck.equals(msg.getMssageType()) && shouldHoldupOrder(order)) {
                log.info("库存预留成功，但订单总金额超过最大拦截阈值[被拦截]:{}", order.getId());
                orderLogService.saveOrderLog(order.getId(), null, "库存预留成功，但订单总金额超过最大拦截阈值[被拦截]", "SYSTEM");
                return;
            }
            SimpleDateFormat sb = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
            // send glink msg
            //todo 组装调用中间件参数
            KiddSaleOrderDto kiddSaleOrderDto = new KiddSaleOrderDto();//订单信息
            KiddSaleOrderDeliveryInfoDto deliveryInfoDto = new KiddSaleOrderDeliveryInfoDto();//物流相关信息
            if (OrderTypes.DIRECT_STORE.equals(order.getOrderType()) || OrderTypes.NORMAL_SALE.equals(order.getOrderType())) {//门店要货
                Response<StoreScOrderCodeDto> orderCodeDtoResponse = organizationIndexSCDubboService.queryStoreScOrderCodeById(order.getFranchiseeStoreId());//获取门店信息
                if (null == orderCodeDtoResponse || null == orderCodeDtoResponse.getResultObject()) {
                    log.warn("根据门店id【{}】,查询门店信息返回结果失败", JSON.toJSONString(orderCodeDtoResponse));
                    return;
                }
                StoreScOrderCodeDto resultObject = orderCodeDtoResponse.getResultObject();
                String storeCode = resultObject.getOrderCode();//门店编码
                String simpleName = resultObject.getName();//门店简称
                String remarkAddress = "雅堂小超_" + storeCode + "(" + simpleName + ")";
                deliveryInfoDto.setRemarkAddress(remarkAddress);//传加盟商名称，规则如下:雅堂小超_No.000999(凯华丽景便利店)
            }
            deliveryInfoDto.setScOrderType(order.getOrderType());

            deliveryInfoDto.setDeliveryOrderCode(order.getId());

            if (StringUtils.isEmpty(order.getBranchCompanyArehouse())) {
                log.warn("{}订单逻辑仓CODE为空", order.getId());
                return;
            }
            deliveryInfoDto.setWarehouseCode(StringUtils.trimToEmpty(order.getBranchCompanyArehouse()));
            Response<LogicWarehouseDto> logicWarehouseDtoResponse = warehouseLogicQueryDubboService.selectLogicWarehouseByPrimaryKey(order.getBranchCompanyArehouse());
            log.info("出货仓dubbo服务调用相应:{}", JSON.toJSONString(logicWarehouseDtoResponse));
            if (logicWarehouseDtoResponse.getCode().equals(CommonsEnum.RESPONSE_200.getCode())) {
                deliveryInfoDto.setOwnerCode(StringUtils.trimToEmpty(logicWarehouseDtoResponse.getResultObject().getBranchCompanyCode()));
                deliveryInfoDto.setLogisticsCode(StringUtils.trimToEmpty(logicWarehouseDtoResponse.getResultObject().getTmsCode()));
                deliveryInfoDto.setLogisticsName(StringUtils.trimToEmpty(logicWarehouseDtoResponse.getResultObject().getTmsName()));
            } else {
                log.warn("出货仓dubbo服务调用失败:{}", order.getBranchCompanyArehouse());
                throw new RuntimeException("出货仓dubbo服务调用失败。");
            }

            try {
                deliveryInfoDto.setCreateTime(order.getSubmitTime() != null ? sb.parse(sb.format(order.getSubmitTime())) : order.getSubmitTime());
            } catch (ParseException e) {
                log.warn("订单提交时间转换异常", e);
            }
            //params.setOperTimeERP(null);//货主订单审核通过时间
            List<PaymentGroup> paymentGroupList = paymentGroupService.getPaymentGroupForOrderId(order.getId());
            if (paymentGroupList.size() > 0) {
                PaymentGroup paymentGroup = paymentGroupList.get(0);
                try {
                    //todo   该字段在心怡不存在
                    deliveryInfoDto.setPayTime(paymentGroup.getPayDate() != null ? sb.parse(sb.format(paymentGroup.getPayDate())) : paymentGroup.getPayDate());
                } catch (ParseException e) {
                    log.warn("订单支付时间转换异常", e);
                }
            }

            //收货人信息

            KiddSaleOrderReceiverInfoDto receiverInfoDto = new KiddSaleOrderReceiverInfoDto();

            receiverInfoDto.setName(StringUtils.trimToEmpty(shippingGroup.getConsigneeName()));
            receiverInfoDto.setMobile(StringUtils.trimToEmpty(shippingGroup.getCellphone()));
            receiverInfoDto.setProvince(StringUtils.trimToEmpty(shippingGroup.getProvince()));
            receiverInfoDto.setCity(StringUtils.trimToEmpty(shippingGroup.getCity()));
            receiverInfoDto.setDetailAddress(StringUtils.trimToEmpty(shippingGroup.getDetailAddress()));
            receiverInfoDto.setArea(StringUtils.trimToEmpty(shippingGroup.getDistrict()));
            receiverInfoDto.setCustmoerCode(StringUtils.trimToEmpty(order.getFranchiseeId()));
            deliveryInfoDto.setReceiverInfo(receiverInfoDto);

            OrderPrice orderPrice = orderPriceService.getOrderPriceForId(order.getPriceInfo());
            deliveryInfoDto.setActualOrderPrice(orderPrice.getTotal());

            kiddSaleOrderDto.setDeliveryOrder(deliveryInfoDto);

            //商品信息

            List<KiddSaleOrderCargoDto> cargoDtoList = new ArrayList<KiddSaleOrderCargoDto>();
            List<CommerceItem> commerceItemList = commerceItemService.getCommerceItemAndPriceForOrderId(order.getId());
            for (CommerceItem commerceItem : commerceItemList) {
                KiddSaleOrderCargoDto saleOrderCargoDto = new KiddSaleOrderCargoDto();
                saleOrderCargoDto.setOrderLineNo(String.valueOf(commerceItem.getId()));
                Response<ProductDto> productDtoRep = productDubboService.queryById(commerceItem.getProductId());
                if (!productDtoRep.isSuccess()) {
                    throw new RuntimeException("商品dubbo服务调用失败。");
                }

                saleOrderCargoDto.setOwnerCode(StringUtils.trimToEmpty(logicWarehouseDtoResponse.getResultObject().getBranchCompanyCode()));
                saleOrderCargoDto.setItemCode(StringUtils.trimToEmpty(productDtoRep.getResultObject().getProductCode()));
                saleOrderCargoDto.setItemName(StringUtils.trimToEmpty(productDtoRep.getResultObject().getName()));
                saleOrderCargoDto.setInventoryType("ZP");
                saleOrderCargoDto.setPlanQty(commerceItem.getQuantity().intValue());
                saleOrderCargoDto.setUnit(productDtoRep.getResultObject().getMinUnit());

                // 把价格敏感信息屏蔽 不传给第三方
                Double listPrice = commerceItem.getItemPrice().getListPrice();
                saleOrderCargoDto.setRetailPrice(listPrice == null ? 0.0 : listPrice);
//                saleOrderCargoDto.setRetailPrice(0.0);
                Double salePrice = commerceItem.getItemPrice().getSalePrice();
                saleOrderCargoDto.setActualPrice(salePrice == null ? 0.0 : salePrice);
//                saleOrderCargoDto.setActualPrice(0.0);

                Double discountShare = commerceItem.getItemPrice().getOrderDiscountShare();
                saleOrderCargoDto.setDiscountAmount(discountShare == null ? 0.0 : discountShare);

                cargoDtoList.add(saleOrderCargoDto);
            }
            kiddSaleOrderDto.setOrderLines(cargoDtoList);
            log.info("kiddSaleOrderService 请求参数:{}",JSON.toJSONString(kiddSaleOrderDto));
            //todo  调用中间件服务
            Response<String> responseDto = kiddSaleOrderService.add(kiddSaleOrderDto);
            log.info("调用第三方订单请求返回:{}", JSON.toJSONString(responseDto));
            if (responseDto == null) {
                throw new MqBizFailureException("发送订单失败！ 订单号：" + msg.getOrderId());
            }
            UpdateOrderVO updateOrderVO = new UpdateOrderVO();
            if (!"200".equalsIgnoreCase(responseDto.getCode()) && !"20101".equalsIgnoreCase(responseDto.getCode())) {//20101 重复添加错误
                log.error("第三方处理订单失败:{}", JSON.toJSONString(responseDto));
                order.setShippingState(ShippingStates.WMS_REJECT);
                updateOrderVO.setDescription(responseDto.getErrorMessage());
            } else {
                order.setInteractivePendingRes(true);
                updateOrderVO.setDescription("订单已传送到第三方,等待通知");
            }
            updateOrderVO.setOperator("系统");
            updateOrderVO.setPreOrderState(order.getState());
            updateOrderVO.setOrder(order);
            orderService.updateAndSaveLog(updateOrderVO);

            log.info("发送订单成功！ order id: " + msg.getOrderId());
        }
    }

    @Override
    public boolean isCare(OrderMessage msg) {
        return OrderMessageType.SendWMSDeliveryMessage.equals(msg.getMssageType())||OrderMessageType.SendWMSDeliveryMessageCheck.equals(msg.getMssageType());
    }

    protected boolean shouldHoldupOrder(Order pOrder) {
        OrderPrice orderPrice = orderPriceService.getOrderPriceForId(pOrder.getPriceInfo());
        if (orderPrice == null) {
            log.error("订单价格信息不存在:{}->{}", pOrder.getId(), pOrder.getPriceInfo());
            return false;
        }
        OrderDictionary orderDictionary = orderDictionaryPropertiesService.selectByPrimaryKey("holdUpAmountThreshold");
        log.debug("订单最大拦截配置:{}", JSON.toJSONString(orderDictionary));
        boolean shouldHoldUp;
        if (orderDictionary == null || StringUtils.isNotEmpty(pOrder.getCreatedByOrderId())
                || OrderTypes.ONLINE_RETAILERS_SALE.equals(pOrder.getOrderType())) {//子订单,电商订单不再拦截
            shouldHoldUp = false;
        } else {
            shouldHoldUp = orderPrice.getRawSubtotal() > Double.valueOf(orderDictionary.getPropertyValue());
        }

        log.info("订单总金额超过最大拦截阈值:{}->{}:{},shouldHoldUp:{}", pOrder.getId(), orderPrice.getRawSubtotal(), orderDictionary.getPropertyValue(), shouldHoldUp);
        return shouldHoldUp;
    }


    /**
     *
     * @param pOrder
     * @return
     */
    protected boolean forbidSendOrderToWMS(Order pOrder) {
        OrderDictionary orderDictionary = orderDictionaryPropertiesService.selectByPrimaryKey("companyForbidSendOrderToWMS");
        if (orderDictionary == null) {
            return false;
        }
        String[] companyIds = orderDictionary.getPropertyValue().split(",");
        List<String> companyIdsList = Arrays.asList(companyIds);
        if (CollectionUtils.isEmpty(companyIdsList)) {
            return false;
        }

        return companyIdsList.contains(pOrder.getBranchCompanyId());
    }

    protected boolean handleProviderOrder(Order order) throws Exception {
        Response<String> response = mPmPurchaseOrderWriteDubboService.addPmPurchaseOrder(genPmPurchaseOrder(order));
        log.info("添加采购订单结果：{}", JSON.toJSONString(response));
        if(response.isSuccess()) {
            UpdateOrderVO updateOrderVO = new UpdateOrderVO();
            order.setInteractivePendingRes(true);
            order.setPurchaseOrderNo(response.getResultObject());
            updateOrderVO.setDescription("订单已传送到供应商协同平台,等待接单");
            updateOrderVO.setOperator("系统");
            updateOrderVO.setPreOrderState(order.getState());
            updateOrderVO.setOrder(order);
            orderService.updateAndSaveLog(updateOrderVO);
        }
        return true;
    }

    protected PmPurchaseOrderExtDto genPmPurchaseOrder(Order pOrder) throws Exception {
        PmPurchaseOrderExtDto pmPurchaseOrderExtDto = new PmPurchaseOrderExtDto();
        PmPurchaseOrderDto pmPurchaseOrderDto = new PmPurchaseOrderDto();
        ProviderShippingGroup providerShippingGroup = mProviderShippingGroupService.selectByShippingGroupId(pOrder.getShippingGroup());
        if (providerShippingGroup == null) {
            log.error("直配订单物流信息无正确:{}->{}", pOrder.getId(), pOrder.getShippingGroup());
            throw new Exception("直配订单物流信息无正确");
        }

        pmPurchaseOrderDto.setSaleOrderId(pOrder.getId());
        pmPurchaseOrderDto.setSpId(providerShippingGroup.getSpId());
        pmPurchaseOrderDto.setSpNo(providerShippingGroup.getSpNo());
        pmPurchaseOrderDto.setSpName(providerShippingGroup.getSpName());
        pmPurchaseOrderDto.setSpAdrId(providerShippingGroup.getSpAdrId());
        pmPurchaseOrderDto.setSpAdrName(providerShippingGroup.getSpAdrName());
        pmPurchaseOrderDto.setAdrTypeCode(pOrder.getFranchiseeStoreId());
        pmPurchaseOrderDto.setPurchaseOrderType(PmPurchaseOrderType.NORMAL_PURCHASE.getCode());//类型:0:普通采购单;1:赠品采购;2:促销釆购
        pmPurchaseOrderDto.setBusinessMode(PmPurchaseBusinessType.CONSIGNMENT_SALE_TYPE.getCode());// 经营模式:0:经销;1:代销;2:寄售
        pmPurchaseOrderDto.setAdrType(PmAdrType.ADR_DIRECT_STORE.getCode());// 地点类型:0:仓库;1:门店
        pmPurchaseOrderDto.setBranchCompanyId(pOrder.getBranchCompanyId());
        List<CommerceItem> commerceItems = commerceItemService.getCommerceItemForOrderId(pOrder.getId());
        if (org.apache.commons.collections4.CollectionUtils.isEmpty(commerceItems)) {
            log.error("商品信息不存在：{}", pOrder.getId());
            return null;
        }
        List<PmPurchaseOrderItemDto> pmPurchaseOrderItemDtos = new ArrayList<>();
        PmPurchaseOrderItemDto pmPurchaseOrderItemDto;
        for (CommerceItem item : commerceItems) {
            pmPurchaseOrderItemDto = new PmPurchaseOrderItemDto();
            pmPurchaseOrderItemDto.setProductId(item.getProductId());
            pmPurchaseOrderItemDto.setPurchaseNumber(item.getQuantity().intValue());
            pmPurchaseOrderItemDtos.add(pmPurchaseOrderItemDto);
        }
        pmPurchaseOrderExtDto.setPmPurchaseOrder(pmPurchaseOrderDto);
        pmPurchaseOrderExtDto.setPmPurchaseOrderItems(pmPurchaseOrderItemDtos);
        return pmPurchaseOrderExtDto;
    }

}
