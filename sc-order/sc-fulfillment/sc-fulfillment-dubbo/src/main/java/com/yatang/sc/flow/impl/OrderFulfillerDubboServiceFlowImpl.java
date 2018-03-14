package com.yatang.sc.flow.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.yatang.sc.common.CommonEnum;
import com.yatang.sc.common.utils.BigDemicalUtil;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptDetailDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptSHDto;
import com.yatang.sc.facade.dto.pm.PurchaseConfirmOrderLinesDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseOrderWriteDubboService;
import com.yatang.sc.facade.dubboservice.PmPurchaseReceiptQueryDubboService;
import com.yatang.sc.facade.dubboservice.PmPurchaseReceiptWriteDubboService;
import com.yatang.sc.flow.OrderFulfillerDubboServiceFlow;
import com.yatang.sc.fulfillment.dto.*;
import com.yatang.sc.glink.dto.saleOrder.SaleCargoDto;
import com.yatang.sc.inventory.dto.ItemInventoryDto;
import com.yatang.sc.inventory.dto.ItemInventoryQueryParamDto;
import com.yatang.sc.inventory.dto.ItemLocSohDto;
import com.yatang.sc.inventory.dto.OrderInventoryDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import com.yatang.sc.order.OrderInventoryHelper;
import com.yatang.sc.order.domain.CommerceItem;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.OrderItems;
import com.yatang.sc.order.domain.OrderPayments;
import com.yatang.sc.order.domain.PaymentGroup;
import com.yatang.sc.order.domain.ShippingGroup;
import com.yatang.sc.order.dubboservice.WebReturnRequestDubboService;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.service.ItemPriceService;
import com.yatang.sc.order.service.OrderItemsService;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.OrderPaymentsService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.service.PaymentGroupService;
import com.yatang.sc.order.service.ShippingGroupService;
import com.yatang.sc.order.split.SplitOrderRequestDto;
import com.yatang.sc.order.states.CancelOrderStates;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.OrderTotalStates;
import com.yatang.sc.order.states.OrderTypes;
import com.yatang.sc.order.states.PaymentGroupStates;
import com.yatang.sc.order.states.PaymentStates;
import com.yatang.sc.order.states.ShippingModes;
import com.yatang.sc.order.states.ShippingStates;
import com.yatang.sc.payment.dto.request.ApplyRefundRequestDto;
import com.yatang.sc.payment.dubbo.service.RefundDubboService;
import com.yatang.sc.payment.enums.PayType;
import com.yatang.sc.payment.enums.RefundReason;
import com.yatang.sc.purchase.dto.ReturnRequestDetailDto;
import com.yatang.sc.purchase.dubboservice.SplitOrderService;
import com.yatang.sc.thirdorder.busi.BusinessOrderService;
import com.yatang.sc.thirdorder.busi.dto.BusiOrderDto;
import com.yatang.sc.vo.UpdateOrderVO;
import com.yatang.xc.mbd.biz.prod.dubboservice.ProductDubboService;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.ProductDto;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.mbd.pi.es.dubboservice.ProductScIndexDubboService;
import com.yatang.xc.oc.b.member.biz.core.dto.LevelPrivilegeDto;
import com.yatang.xc.oc.b.member.biz.core.dto.PrivilegeInfoDto;
import com.yatang.xc.oc.b.member.biz.core.dubboservice.PrivilegeInfoDubboService;
import com.yatang.xcsm.remote.api.dubboxservice.OutViperOrderDubboxService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderFulfillerDubboServiceFlow")
public class OrderFulfillerDubboServiceFlowImpl implements OrderFulfillerDubboServiceFlow {

    protected Logger log = LoggerFactory.getLogger( OrderFulfillerDubboServiceFlowImpl.class );

    @Autowired
    OrderService orderService;

    @Autowired
    OrderMessageSender orderMessageSender;

    @Autowired
    OrderLogService orderLogService;

    @Autowired
    OrderItemsService orderItemsService;

    @Autowired
    CommerceItemService commerceItemService;

    @Autowired
    ItemPriceService mItemPriceService;

    @Autowired
    ItemLocInventoryDubboService itemLocInventoryDubboService;

    @Autowired
    private ShippingGroupService shippingGroupService;

    @Autowired
    private ProductDubboService productDubboService;


    @Autowired
    OrderInventoryHelper orderInventoryHelper;

    @Autowired
    private OrderPaymentsService mOrderPaymentsService;

    @Autowired
    private PaymentGroupService paymentGroupService;

    @Autowired
    private RefundDubboService mRefundDubboService;

    @Autowired
    private PrivilegeInfoDubboService privilegeInfoDubboService;

    private static final DateTimeFormatter DATE_FROMAT = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private BusinessOrderService businessOrder;

    @Autowired
    private WebReturnRequestDubboService webReturnRequestDubboService;

    @Autowired
    private OutViperOrderDubboxService outViperOrderDubboxService;

    @Autowired
    private PmPurchaseOrderWriteDubboService pmPurchaseOrderWriteDubboService;

    @Autowired
    private SplitOrderService mSplitOrderService;

    @Autowired
    private ProductScIndexDubboService productScIndexDubboService;

    /**
     * 发送订单到WMS
     *
     * @param orderTransferDto
     * @return
     */
    public Response<Boolean> orderTransfer(OrderTransferDto orderTransferDto) {
        Response<Boolean> response = new Response<Boolean>();
        String orderId = orderTransferDto.getOrderId();
        Order order = orderService.selectByPrimaryKey(orderId);
        if (order == null) {
            response.setSuccess(false);
            response.setErrorMessage("订单不存在！");
            return response;
        }
        //todo invoke glink to send order

        return response;
    }

    /**
     * 订单发送到WMS
     *
     * @param orderTransferBackDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Response<Boolean> orderTransferBack(OrderTransferBackDto orderTransferBackDto) {
        Response<Boolean> response = new Response<Boolean>();
        String orderId = orderTransferBackDto.getOrderId();
        Order order = orderService.selectByPrimaryKey(orderId);
        if (!checkOrder(response, order)) {
            return response;
        }

        if (!ShippingStates.PENDING_TRANSFER.equals(order.getShippingState())) {
            log.warn("orderTransferBack-->{}:订单物流状态不正确:{},返回处理成功丢弃该消息", order.getId(), order.getShippingState());
            response.setSuccess(false);
            response.setCode(CommonEnum.ORDER_STATUS_INVALID.getCode());
            response.setErrorMessage(CommonEnum.ORDER_STATUS_INVALID.getDesc());
            return response;
        }
        order.setInteractivePendingRes(false);
        if (orderTransferBackDto.isSuccess()) {
            order.setShippingState(ShippingStates.PENDING_DELIVERY);
            order.setState(OrderTotalStates.PENDING_DELIVERY.getStateValue());
            order.setStateDetail(OrderTotalStates.PENDING_DELIVERY.getDescription());
            orderLogService.saveOrderLog(order.getId(), order.getState(), "出库单已创建！", "系统");
        }
        orderService.update(order);

        response.setSuccess(true);
        response.setResultObject(Boolean.TRUE);

        return response;
    }

    /**
     * 订单开始配送
     *
     * @param orderDeliveryDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Response<Boolean> orderDelivery(OrderDeliveryDto orderDeliveryDto) throws Exception {
        Response<Boolean> response = new Response<Boolean>();
        String orderId = orderDeliveryDto.getOrderId();
        Order order = orderService.selectByPrimaryKey(orderId);
        if (!checkOrder(response, order)) {
            return response;
        }

        log.info("orderDelivery:{}", JSON.toJSONString(order));

        /**
         * 1. 待出库订单收到GLink出库通知
         * 2. 未送达订单再次收到GLink出库通知
         */
        if (!(ShippingStates.PENDING_DELIVERY.equalsIgnoreCase(order.getShippingState()) || ShippingStates.NO_ARRIVED.equalsIgnoreCase(order.getShippingState())
                || ShippingStates.PENDING_TRANSFER.equalsIgnoreCase(order.getShippingState()))) {
            log.warn("orderDelivery-->{}:订单物流状态不正确:{},返回处理成功丢弃该消息", order.getId(), order.getShippingState());
            response.setSuccess(false);
            response.setCode(CommonEnum.ORDER_STATUS_INVALID.getCode());
            response.setErrorMessage(CommonEnum.ORDER_STATUS_INVALID.getDesc());
            return response;
        }

        //保存物流信息
        List<SaleCargoDto> cargoDtos = saveShippingInfo(order, orderDeliveryDto);
        Map<String, Integer> cargoDeliveredMap = new HashMap<String, Integer>();
        for (SaleCargoDto saleCargoDto : cargoDtos) {
            cargoDeliveredMap.put(saleCargoDto.getOrderLineNo(), saleCargoDto.getActualQty());
        }
        log.info("发货数量：{}", JSON.toJSONString(cargoDeliveredMap));
        order.setShippingState(ShippingStates.PENDING_RECEIVE);
        order.setState(OrderTotalStates.PENDING_RECEIVING.getStateValue());
        orderService.updateOrderSelective(order);
        orderLogService.saveOrderLog(order.getId(), order.getState(), "订单出库，等待收货！", "系统");

        // 记录平均价
        this.updateItemAvCost(order);

        OrderInventoryDto oid = createOrderInventoryDto(order, false);
        for (ItemInventoryDto itemInventoryDto : oid.getItemInventoryDtos()) {
            if (cargoDeliveredMap.get(itemInventoryDto.getId()) != null) {
                itemInventoryDto.setStock(cargoDeliveredMap.get(itemInventoryDto.getId()));
            }
        }

        //更新发货数量
        updateOrderItemsForDelivered(order, cargoDeliveredMap);

        log.info("sale order out:" + oid.getId());
        Response<Boolean> soRes = itemLocInventoryDubboService.saleOutOrder(oid);

        if (soRes == null || !soRes.isSuccess() || !soRes.getResultObject()) {
            throw new Exception("扣减库存失败！ orderId:" + order.getId());
        }

        response.setSuccess(true);
        response.setResultObject(Boolean.TRUE);

        //如果是第三方物流，则更新发货信息
        if (!StringUtils.isEmpty(order.getThirdPartOrderNo())) {
            thirdOrderDelivery(order, orderDeliveryDto);
        }

        return response;
    }

    private void thirdOrderDelivery(Order order, OrderDeliveryDto orderDeliveryDto) throws Exception {
        switch (order.getOrderType()) {
            case OrderTypes.ONLINE_RETAILERS_SALE:
                log.info("thirdOrderDelivery,通知电商订单发货,orderId:{},thirdPartOrderNo:{}", order.getId(), order.getThirdPartOrderNo());
                //通知电商物流状态变为已出库
                BusiOrderDto busiOrderDto = new BusiOrderDto();
                busiOrderDto.setMerchantLogisticsInfo(orderDeliveryDto.getLogisticsName());// 物流公司名字
                busiOrderDto.setMerchantLogisticsCompanyCode(orderDeliveryDto.getLogisticsCode());// 物流公司编码
                busiOrderDto.setMerchantLogisticsNumber(orderDeliveryDto.getExpressCode());// 物流单号
                busiOrderDto.setOrderId(order.getThirdPartOrderNo());
                busiOrderDto.setState("PENDING_RECEIVING");
                businessOrder.updateShippingStates(busiOrderDto);
            case OrderTypes.XC_SALE:
                Map<String,String> logistics = new HashMap<>();
                if(!StringUtils.isEmpty(orderDeliveryDto.getExpressCode())){
                    String[] expressCodes = orderDeliveryDto.getExpressCode().split(";");
                    for(String expressCode : expressCodes){
                        logistics.put(expressCode, orderDeliveryDto.getLogisticsCode()+"_"+orderDeliveryDto.getLogisticsName());
                    }
                }
                log.info("thirdOrderDelivery,通知小超C端订单发货,orderId:{},thirdPartOrderNo:{},logistics:{}",order.getId(),order.getThirdPartOrderNo(), JSONObject.toJSON(logistics));
                com.yatang.xcsm.common.response.Response<String> response = outViperOrderDubboxService.notifyLogisticsInfo(order.getThirdPartOrderNo(),logistics);
                log.info("thirdOrderDelivery,通知小超C端订单发货,orderId:{},thirdPartOrderNo:{},result:{}",order.getId(),order.getThirdPartOrderNo(),JSON.toJSON(response));
        }
    }

    private void updateItemAvCost(Order order) {
        // 1. 订单的所有商品
        log.info("orderId:{}", order.getId());
        List<CommerceItem> commerceItemList = commerceItemService.getCommerceItemAndPriceForOrderId(order.getId());
        for (CommerceItem item : commerceItemList) {
            ItemInventoryQueryParamDto queryParamDto = new ItemInventoryQueryParamDto();
            queryParamDto.setProductId(item.getProductId());
            queryParamDto.setLogicWareHouseCode(order.getBranchCompanyArehouse());

            // 2. 根据订单productId 和 loc从item_loc_soh表获取
            Response<List<ItemLocSohDto>> res = itemLocInventoryDubboService.queryItemInventoryListByParam(queryParamDto);
            log.info("根据订单productId 和 loc从item_loc_soh表获取:{}", JSON.toJSONString(res));
            if (!res.isSuccess() || CollectionUtils.isEmpty(res.getResultObject())) {
                log.info("queryParamDto:{}, success:{}", JSON.toJSONString(queryParamDto), res.isSuccess());
                continue;
            }

            // 3. 更新scp_commerce_item的av_cost字段
            item.setAvCost(res.getResultObject().get(0).getAvCost());
            log.info("更新scp_commerce_item的av_cost字段:{}", JSON.toJSONString(item));
            commerceItemService.updateByPrimaryKeySelective(item);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<Boolean> orderSendCar(OrderSendCarDto pOrderSendCarDto) throws Exception {
        log.info("order send car:{}", pOrderSendCarDto);

        Response<Boolean> response = new Response<Boolean>();
        String orderId = pOrderSendCarDto.getOrderId();
        Order order = orderService.selectByPrimaryKey(orderId);
        if (!checkOrder(response, order)) {
            return response;
        }

        //物流信息不进行状态判断
       /* if (!ShippingStates.PENDING_RECEIVE.equals(order.getShippingState())) {
            log.warn("orderSendCar-->{}:订单物流状态不正确:{},返回处理成功丢弃该消息", order.getId(), order.getShippingState());
            response.setSuccess(false);
            response.setCode(CommonEnum.ORDER_STATUS_INVALID.getCode());
            response.setErrorMessage(CommonEnum.ORDER_STATUS_INVALID.getDesc());
            return response;
        }*/

        ShippingGroup shippingGroup = shippingGroupService.selectByPrimaryKey(order.getShippingGroup());
        if (shippingGroup == null) {
            log.warn("订单shippingGroup为空:{},{}", order.getId(), order.getShippingGroup());
            response.setSuccess(false);
            response.setErrorMessage("订单shippingGroup为空");
            return response;
        }

        if (StringUtils.isNoneEmpty(pOrderSendCarDto.getDeliveryTime())) {
            shippingGroup.setEstimatedArrivalDate(DateTime.parse(pOrderSendCarDto.getDeliveryTime(), DATE_FROMAT).toDate());
        }
        shippingGroup.setDeliveryer(pOrderSendCarDto.getDriverName());
        shippingGroup.setDeliveryerPhone(pOrderSendCarDto.getDriverPhone());
        shippingGroupService.update(shippingGroup);

        response.setSuccess(true);
        response.setResultObject(Boolean.TRUE);
        return response;
    }

    protected List<SaleCargoDto> saveShippingInfo(Order order, OrderDeliveryDto orderDeliveryDto) {
        List<SaleCargoDto> cargoDtos = orderDeliveryDto.getCargoDtos();
        try {
            //todo 物流信息
            ShippingGroup shippingGroup = shippingGroupService.selectByPrimaryKey(order.getShippingGroup());
            if (shippingGroup == null) {
                log.warn("订单shippingGroup为空:{},{}", order.getId(), order.getShippingGroup());
                return cargoDtos;
            }
            //TODO 目前GLink只返回部分有效字段，后面提供了再保存其他字段
            shippingGroup.setShippingNo(orderDeliveryDto.getExpressCode());
            String shippingMethod = orderDeliveryDto.getLogisticsName();
            if (StringUtils.isEmpty(shippingMethod)) {
                shippingMethod = orderDeliveryDto.getLogisticsCode();
            }

            shippingGroup.setShippingMethod(shippingMethod);
            shippingGroup.setActualShipDate(new Date());
            shippingGroupService.update(shippingGroup);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return cargoDtos;
    }

    /**
     * 订单签收
     *
     * @param orderReceiveDto
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Response<Boolean> orderReceive(OrderReceiveDto orderReceiveDto) throws Exception {
        Response<Boolean> response = new Response<Boolean>();
        String orderId = orderReceiveDto.getOrderId();
        Order order = orderService.selectByPrimaryKey(orderId);
        if (!checkOrder(response, order)) {
            return response;
        }


        //只有已签收待确认的订单才允许签收收货 yinyuxin
        if (!ShippingStates.PENDING_COMPLETED.equals(order.getShippingState()) && !ShippingStates.PENDING_RECEIVE.equals(order.getShippingState())) {
            log.warn( "orderReceive-->{}:订单物流状态不正确:{},返回处理成功丢弃该消息", order.getId(), order.getShippingState() );
            response.setSuccess( false );
            response.setCode( CommonEnum.ORDER_STATUS_INVALID.getCode() );
            response.setErrorMessage( CommonEnum.ORDER_STATUS_INVALID.getDesc() );
            return response;
        }

        updateOrderItemsForSigned(order, orderReceiveDto.getSignedMap() );

        order.setCompletedTime( new Date() );//TODO 际联传送？？
        order.setShippingState( ShippingStates.COMPLETED );
        order.setState( OrderTotalStates.COMPLETED.getStateValue() );
        order.setOrderState( OrderStates.COMPLETED );
        order.setStateDetail( OrderTotalStates.COMPLETED.getDescription() );
//        List<OrderItems> orderItems = orderItemsService.getOrderItemsForOrderId( orderId );

        orderService.update( order );
        orderLogService.saveOrderLog( order.getId(), null, "订单已确认收货！", StringUtils.isEmpty( orderReceiveDto.getOperatorId() ) ? "系统" : orderReceiveDto.getOperatorId() );

        //订单返券
        //giveCoupons( order );


        OrderInventoryDto oid = createOrderInventoryDto( order, orderReceiveDto.getSignedMap());
        Response<Boolean> soRes = itemLocInventoryDubboService.orderArrived( oid );
        if (soRes == null || !soRes.isSuccess() || !soRes.getResultObject()) {
            throw new Exception( "扣减库存在途数量失败" );
        }
        log.info("orderId:{},orderType:{}",order.getId(),order.getOrderType());
        if(order.getOrderType().equals(OrderTypes.DIRECT_STORE) || order.getOrderType().equals(OrderTypes.NORMAL_SALE)){
            //thirdInventoryHelper.sendXCSignNumber(order.getId());
        }

        response.setSuccess( true );
        response.setResultObject( Boolean.TRUE );
        return response;
    }

    private void giveCoupons(Order order) {
        Response<LevelPrivilegeDto> privilegeInfo = privilegeInfoDubboService.getPrivilegeInfoByMemberCode( order.getProfileId(), "QY_system_gyl" );
        if(privilegeInfo==null||privilegeInfo.getResultObject() == null||privilegeInfo.getResultObject().getPrivilegeInfoDtoList()==null||privilegeInfo.getResultObject().getPrivilegeInfoDtoList().isEmpty()){
        	return;
        }
        PrivilegeInfoDto privilegeInfoDto = privilegeInfo.getResultObject().getPrivilegeInfoDtoList().get(0);
        if (privilegeInfoDto == null) {
                return;
        }
        Response<ReturnRequestDetailDto> returnRequestDetailDto = webReturnRequestDubboService.queryReturnRequestByOrderId( order.getId() );
        double refuseAmount = 0d;
        if (returnRequestDetailDto != null && returnRequestDetailDto.getResultObject() != null) {
            refuseAmount = returnRequestDetailDto.getResultObject().getRefundAmount();
        }
        //会员等级返券
        Response<String> giveCouponToStore = orderService.giveCouponToStore( order.getFranchiseeStoreId(), refuseAmount, order.getPriceInfo() );
        if (giveCouponToStore != null) {
            log.debug( "订单确认返券： order Id-->:" + order.getId() + " give coupons->: " + giveCouponToStore.getResultObject() );
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<Boolean> orderCancelConfirm(OrderCancelConfirmDto pOrderCancelConfirmDto) throws Exception {
        Response<Boolean> response = new Response<Boolean>();
        Order order = orderService.selectByPrimaryKey( pOrderCancelConfirmDto.getOrderId() );
        if (order == null) {
            log.error( "can't find order by id: " + pOrderCancelConfirmDto.getOrderId() );
            return response;
        }
        if (order.getOrderState().equals( OrderStates.CANCELLED )) {
            log.warn( "{}订单状态【{}】不正确，取消处理成功通知处理失败", pOrderCancelConfirmDto.getOrderId(), order.getOrderState() );
            return response;
        }

        ShippingGroup shippingGroup = shippingGroupService.selectByPrimaryKey(order.getShippingGroup());
        //如果库存状态为“未传送”或“待出库”，则调用库存接口，更新库存表中“销售保留量”= “销售保留量”- “订单确认数量”，同时更新销售订单行中的 “订单确认数量” 为0
        //统配订单才需要操作库存信息
        if ((ShippingStates.PENDING_TRANSFER.equals( order.getShippingState() )
                || ShippingStates.PENDING_DELIVERY.equals( order.getShippingState()))
                && ShippingModes.UNIFIED_SHIPPING_MODE.getCode().equals(shippingGroup.getShippingModes())) {
            if (orderInventoryHelper.cancelReserveOrder( order )) {
                orderLogService.saveOrderLog( order.getId(), order.getState(), " 取消销售订单更新库存成功!", "系统" );
            } else {
                throw new Exception( " 取消销售订单更新库存失败" );
            }
        }
        order.setCancelStatus( CancelOrderStates.QXCG.getStateValue() );
        order.setDescription( "取消成功" );
        orderService.update( order );

        applyRefund( pOrderCancelConfirmDto );
        response.setSuccess( true );
        return response;
    }

    @Override
    public Response<Boolean> orderCancelFail(OrderCancelFailDto pOrderCancelFailDto) {
        Response<Boolean> response = new Response<Boolean>();
        Order order = orderService.selectByPrimaryKey( pOrderCancelFailDto.getOrderId() );
        if (order == null) {
            log.error( "can't find order by id: " + pOrderCancelFailDto.getOrderId() );
            return response;
        }
        order.setCancelStatus( CancelOrderStates.QXSB.getStateValue() );
        order.setDescription( pOrderCancelFailDto.getMessage() );
        UpdateOrderVO updateOrderVO = new UpdateOrderVO();
        updateOrderVO.setOrder( order );
        updateOrderVO.setPreOrderState( order.getState() );
        updateOrderVO.setDescription( pOrderCancelFailDto.getMessage() );
        updateOrderVO.setOperator( "际联通知" );
        orderService.updateAndSaveLog( updateOrderVO );
        response.setSuccess( true );
        return response;
    }

    /**
     * 申请退款
     *
     * @param pOrderCancelConfirmDto
     * @return
     */
    protected boolean applyRefund(OrderCancelConfirmDto pOrderCancelConfirmDto) {
        try {
            Order order = orderService.selectByPrimaryKey( pOrderCancelConfirmDto.getOrderId() );

            List<OrderPayments> paymentGroups = mOrderPaymentsService.selectByOrderId( order.getId() );
            if (CollectionUtils.isEmpty( paymentGroups )) {
                log.warn( "当前订单支付信息不存在:{},{}", pOrderCancelConfirmDto.getOrderId(), JSON.toJSONString( paymentGroups ) );
                return false;
            }

            PaymentGroup paymentGroupRecord = null;
            Double paiedAmount = 0D;
            for (OrderPayments orderPayments : paymentGroups) {
                paymentGroupRecord = paymentGroupService.getPaymentGroupForId( orderPayments.getPaymentGroupId() );
                if (paymentGroupRecord == null) {
                    log.warn( "当前订单支付信息不存在:{},", pOrderCancelConfirmDto.getOrderId(), orderPayments.getPaymentGroupId() );
                    continue;
                }
                if (PaymentGroupStates.SETTLED.getStateValue().equals( paymentGroupRecord.getState() )) {
                    paiedAmount += paymentGroupRecord.getAmountDebited();
                }
            }

            boolean isSettled = PaymentStates.DEBITED.equalsIgnoreCase( order.getPaymentState() ) ? true : false;
            if (isSettled) {
                log.debug( "订单状态为已支付:{}", order.getId() );
                ApplyRefundRequestDto applyRefundRequestDto = new ApplyRefundRequestDto();
                applyRefundRequestDto.setNonceStr(RandomStringUtils.randomAlphanumeric(32));
                applyRefundRequestDto.setOrderNo(order.getId());
                applyRefundRequestDto.setTotalAmount(BigDemicalUtil.round(paiedAmount, 4));
                applyRefundRequestDto.setRemark("订单取消");
                applyRefundRequestDto.setRefundUserName("查看订单日志");
                applyRefundRequestDto.setRefundUserId("查看订单日志");
                applyRefundRequestDto.setRefundAmount(BigDemicalUtil.round(paiedAmount, 4));
                applyRefundRequestDto.setRefundReason(RefundReason.CANCEL_REFUND);
                PayType payType = PayType.weixin;
                if (paymentGroupRecord != null) {
                    payType = PayType.parse( paymentGroupRecord.getChannel() );
                    if (!PayType.weixin.equals( payType ) && !PayType.alipay.equals( payType )) {
                        payType = PayType.alipay;
                    }
                    applyRefundRequestDto.setPayType( payType );
                } else {
                    applyRefundRequestDto.setPayType(PayType.alipay);
                }
                applyRefundRequestDto.setPayTradeNo( StringUtils.isEmpty( paymentGroupRecord.getTransNum() ) ? "XianXia" : paymentGroupRecord.getTransNum() );
                applyRefundRequestDto.setPayNo( StringUtils.isEmpty( paymentGroupRecord.getPayRecordNo() ) ? "XianXia" : paymentGroupRecord.getPayRecordNo() );
                Response<Long> refundResult = mRefundDubboService.applyRefund( applyRefundRequestDto );
                log.info( "创建退款申请：{}", JSON.toJSONString( refundResult ) );
                if (!refundResult.isSuccess() || refundResult.getResultObject() == null || refundResult.getResultObject() == 0) {
                    log.error(refundResult.getErrorMessage());
                    throw new Exception("订单退款申请处理失败:" + order.getId());
                }
                log.info( "取消申请流水:{}", refundResult.getResultObject() );
            }

            order.setState( isSettled ? OrderTotalStates.PENDING_REFUND.getStateValue() : OrderTotalStates.CANCELLED.getStateValue() );
            order.setStateDetail( isSettled ? OrderTotalStates.PENDING_REFUND.getDescription() : OrderTotalStates.CANCELLED.getDescription() );
            order.setOrderState( OrderStates.CANCELLED );
            order.setPaymentState( isSettled ? PaymentStates.RF_PENDING_APPROVE : PaymentStates.CANCELLED );
            order.setShippingState( ShippingStates.CANNCELD );

            UpdateOrderVO updateOrderVO = new UpdateOrderVO();
            updateOrderVO.setOrder( order );
            updateOrderVO.setPreOrderState( order.getState() );

            updateOrderVO.setDescription( "取消订单成功，id：" + order.getId() );
            orderService.updateAndSaveLog( updateOrderVO );
            log.debug( "取消订单更新订单状态完成..." );
            //取消时回滚优惠券
            String createdByOrderId = order.getCreatedByOrderId();
            if(StringUtils.isEmpty(createdByOrderId)){
                Response<String> revertCoupons = orderService.revertCoupons( order.getId(), order.getPriceInfo() );
                log.info( "回滚优惠券完成,order Id {} revert coupons:{}", order.getId(), JSON.toJSONString( revertCoupons ) );
            }

            return true;

        } catch (Exception pE) {
            log.error( "取消订单异常", pE );
            orderLogService.saveOrderLog( pOrderCancelConfirmDto.getOrderId(), null, "取消订单异常!", "GLink通知" );
            return false;
        }
    }

    /**
     * 订单未送达
     *
     * @param orderNotReceiveDto
     * @return
     */
    @Transactional
    public Response<Boolean> orderNotReceive(OrderNotReceiveDto orderNotReceiveDto) throws Exception {
        Response<Boolean> response = new Response<Boolean>();
        String orderId = orderNotReceiveDto.getOrderId();
        Order order = orderService.selectByPrimaryKey( orderId );
        if (!checkOrder( response, order )) {
            return response;
        }

        if (!ShippingStates.PENDING_RECEIVE.equals( order.getShippingState() )) {
            log.warn( "orderNotReceive-->{}:订单物流状态不正确:{},返回处理成功丢弃该消息", order.getId(), order.getShippingState() );
            response.setSuccess( false );
            response.setCode( CommonEnum.ORDER_STATUS_INVALID.getCode() );
            response.setErrorMessage( CommonEnum.ORDER_STATUS_INVALID.getDesc() );
            return response;
        }

        order.setShippingState( ShippingStates.NO_ARRIVED );
        order.setState( OrderTotalStates.PENDING_DELIVERY.getStateValue() );
        order.setStateDetail( OrderTotalStates.PENDING_DELIVERY.getDescription() );
        orderService.update( order );
        orderLogService.saveOrderLog( order.getId(), null, "订单未送达！", "系统" );

        OrderInventoryDto oid = createOrderInventoryDto( order, false );
        Response<Boolean> soRes = itemLocInventoryDubboService.orderUnArrive( oid );
        if (soRes == null || !soRes.isSuccess() || !soRes.getResultObject()) {
            throw new Exception( "扣减库存在途数量失败" );
        }

        response.setSuccess( true );
        response.setResultObject( Boolean.TRUE );
        return response;
    }

    /**
     * 订单取消配送
     *
     * @param orderCancelShippingDto
     * @return
     */
    @Transactional
    public Response<Boolean> orderCancelShipping(OrderCancelShippingDto orderCancelShippingDto) throws Exception {
        Response<Boolean> response = new Response<Boolean>();
        String orderId = orderCancelShippingDto.getOrderId();
        Order order = orderService.selectByPrimaryKey( orderId );
        if (!checkOrder( response, order )) {
            return response;
        }

        order.setShippingState( ShippingStates.CANNCELD );
        orderService.update( order );
        orderLogService.saveOrderLog( order.getId(), null, "订单取消配送！", "系统" );

        OrderInventoryDto oid = createOrderInventoryDto( order, false );
        Response<Boolean> soRes = itemLocInventoryDubboService.cancelReserveOrder( oid );
        if (soRes == null || !soRes.isSuccess() || !soRes.getResultObject()) {
            throw new Exception( "取消预留失败" );
        }

        response.setSuccess( true );
        response.setResultObject( Boolean.TRUE );
        return response;
    }

    /**
     * 仓库拒收
     *
     * @param orderWMSRejectDto
     * @return
     */
    public Response<Boolean> orderWMSReject(OrderWMSRejectDto orderWMSRejectDto) {
        Response<Boolean> response = new Response<Boolean>();
        Order order = orderService.selectByPrimaryKey( orderWMSRejectDto.getOrderId() );
        if (!checkOrder( response, order )) {
            return response;
        }

        //TODO 订单商品不足，拒单状态的销售订单需要再次调用订单新增接口传送
        order.setInteractivePendingRes( false );
        order.setShippingState( ShippingStates.WMS_REJECT );
        orderService.update( order );
        orderLogService.saveOrderLog( order.getId(), null, "订单仓库拒收！", "系统" );
        response.setSuccess( true );
        response.setResultObject( Boolean.TRUE );
        return response;
    }

    @Override
    public Response<Boolean> orderReject(OrderRejectDto orderRejectDto) throws Exception {
        Response<Boolean> response = new Response<Boolean>();
        Order order = orderService.selectByPrimaryKey( orderRejectDto.getOrderId() );
        if (!checkOrder( response, order )) {
            return response;
        }
        //更新订单为拒签状态
        order.setShippingState( ShippingStates.USER_REJECT );
        order.setState( OrderTotalStates.COMPLETED.getStateValue() );
        order.setStateDetail( OrderTotalStates.COMPLETED.getDescription() );
        order.setOrderState( OrderStates.COMPLETED );
        orderService.update( order );
        orderLogService.saveOrderLog( order.getId(), null, "订单仓库拒收！", "系统" );


//        OrderInventoryDto oid = createOrderInventoryDto( order, false );
//        Response<Boolean> soRes = itemLocInventoryDubboService.orderUnArrive( oid );
//        if (soRes == null || !soRes.isSuccess() || !soRes.getResultObject()) {
//            throw new Exception( "扣减库存在途数量失败" );
//        }

        response.setSuccess( true );
        response.setResultObject( Boolean.TRUE );
        return response;
    }

    @Override
    public Response<Boolean> providerOrderASN(String orderId, String receiptId) {
        Response<Boolean> response = new Response<>();
        response.setSuccess(true);
        try {
            Response<String> splitResponse = mSplitOrderService.splitOrderByASN(orderId, receiptId);

            log.info("{}->{} 更新拆分的收货单信息结果：{}", orderId, receiptId, JSON.toJSONString(splitResponse));
            if (!splitResponse.isSuccess() || StringUtils.isEmpty(splitResponse.getResultObject())) {
                log.error("ASN销售订单拆单失败:{}", splitResponse.getErrorMessage());
                response.setSuccess(false);
                response.setErrorMessage(splitResponse.getErrorMessage());
                return response;
            }

        } catch (Exception pE) {
            log.error(orderId + " receiptNo" + receiptId + "ASN销售订单拆单失败", pE);
            response.setSuccess(false);
            response.setErrorMessage("ASN销售订单拆单异常");
        }
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<Boolean> providerOrderSingReq(ProviderOrderSingReqDto pProviderOrderSingReqDto) {
        Response<Boolean> response = new Response<>();
        Order order = orderService.selectByPrimaryKey(pProviderOrderSingReqDto.getOrderId());
        if (order == null) {
            log.error("订单不存在:{}", pProviderOrderSingReqDto.getOrderId());
            response.setSuccess(false);
            response.setCode(CommonEnum.ORDER_NONEXIST.getCode());
            response.setErrorMessage(CommonEnum.ORDER_NONEXIST.getDesc());
            return response;
        }

        if (!ShippingStates.PENDING_RECEIVE.equals(order.getShippingState()) && !ShippingStates.PENDING_COMPLETED.equals(order.getShippingState())) {
            log.error("订单物流状态不正确:{}", pProviderOrderSingReqDto.getOrderId());
            response.setSuccess(false);
            response.setErrorMessage("订单物流状态不正确");
            return response;
        }

        ShippingGroup shippingGroup = shippingGroupService.selectByPrimaryKey(order.getShippingGroup());
        if (shippingGroup == null) {
            log.error("物流信息不存在:{}", order.getShippingGroup());
            response.setSuccess(false);
            response.setErrorMessage("物流信息不存在");
            return response;
        }
        order.setShippingState(ShippingStates.PENDING_COMPLETED);
        shippingGroup.setSingedCertImg(pProviderOrderSingReqDto.getSignedMsg());
        shippingGroupService.update(shippingGroup);

        UpdateOrderVO updateOrderVO = new UpdateOrderVO();
        updateOrderVO.setOrder(order);
        updateOrderVO.setPreOrderState(order.getState());
        updateOrderVO.setDescription("收货待确认");
        updateOrderVO.setOperator("系统");
        orderService.updateAndSaveLog(updateOrderVO);
        response.setSuccess(true);
        return response;
    }

    private boolean checkOrder(Response<Boolean> pResponse, Order pOrder) {
        if (pOrder == null) {
            pResponse.setSuccess( false );
            pResponse.setCode( CommonEnum.ORDER_NONEXIST.getCode() );
            pResponse.setErrorMessage( CommonEnum.ORDER_NONEXIST.getDesc() );
            return false;
        }
        return true;
    }

    public OrderInventoryDto createOrderInventoryDto(Order order, boolean arrived) {
        return createOrderInventoryDto(order, arrived, null);
    }

    public OrderInventoryDto createOrderInventoryDto(Order order, Map<?, Long> signedMap) {
        return createOrderInventoryDto(order, true, signedMap);
    }

    public OrderInventoryDto createOrderInventoryDto(Order order, boolean arrived, Map<?, Long> signedMap) {

        List<ItemInventoryDto> itemInventoryDtos = new ArrayList<ItemInventoryDto>();
        List<CommerceItem> commerceItemList = commerceItemService.getCommerceItemAndPriceForOrderId( order.getId() );
        for (CommerceItem commerceItem : commerceItemList) {
            ItemInventoryDto itemInventoryDto = new ItemInventoryDto();
            itemInventoryDto.setId( String.valueOf( commerceItem.getId() ) );
            itemInventoryDto.setProductId( commerceItem.getProductId() );
            itemInventoryDto.setItemQty( commerceItem.getQuantity() );
            itemInventoryDto.setStock( commerceItem.getQuantity() );
            itemInventoryDto.setLoc( order.getBranchCompanyArehouse() );
            itemInventoryDto.setAvCost( commerceItem.getAvCost() );
            if (arrived) {//TODO 后期调整为GLink返回的签收数量
                Long shippedQuantity = MapUtils.isEmpty(signedMap) || null == signedMap.get( commerceItem.getId() ) ?
                        commerceItem.getShippedQuantity() : signedMap.get( commerceItem.getId() );
                itemInventoryDto.setCompleteQty( shippedQuantity );
            }


            Response<ProductDto> result = productDubboService.queryById( commerceItem.getProductId() );
            if ("500".equals( result.getCode() )) {
                log.error( "调用主数据接口根据商品ID查询商品详情出错:{}", JSON.toJSONString( result ) );
                throw new RuntimeException( "调用主数据接口根据商品ID查询商品详情出错" );
            }
            ProductDto productDto = result.getResultObject();
            itemInventoryDto.setProductCode( productDto.getProductCode() );
            itemInventoryDto.setVatRate( productDto.getTaxRate() != null ? productDto.getTaxRate().setScale( 2, BigDecimal.ROUND_HALF_UP ).toString() : null );
            itemInventoryDto.setGroups( productDto.getFirstLevelCategoryId() );
            itemInventoryDto.setDept( productDto.getSecondLevelCategoryId() );
            itemInventoryDto.setClasss( productDto.getThirdLevelCategoryId() );
            itemInventoryDto.setSubclass( productDto.getFourthCategoryId() );

            itemInventoryDto.setActualPrice( commerceItem.getItemPrice().getSalePrice() );

            itemInventoryDtos.add( itemInventoryDto );
        }
        OrderInventoryDto oid = new OrderInventoryDto();
        oid.setId( order.getId() );
        oid.setItemInventoryDtos( itemInventoryDtos );
        return oid;
    }

    private boolean updateOrderItemsForDelivered(Order order, Map<String, Integer> deliveredMap) {
        List<OrderItems> orderItems = orderItemsService.getOrderItemsForOrderId( order.getId() );
        Integer deliveredCount;
        CommerceItem commerceItem;
        for (OrderItems orderItem : orderItems) {
            commerceItem = commerceItemService.getCommerceItemForId( orderItem.getCommerceItemId() );
            deliveredCount = deliveredMap.get( String.valueOf( commerceItem.getId() ) );
            if (deliveredCount == null) {
                log.error( "际联未传回{}:订单里的commerceItem:{}的发货数量", order.getId(), commerceItem.getId() );
            }
            commerceItem.setShippedQuantity( Long.valueOf( deliveredCount == null ? commerceItem.getQuantity() : deliveredCount ) );
            commerceItemService.updateCommerceItem( commerceItem );
        }
        return true;
    }

    //更新订单
    private boolean updateOrderItemsForSigned(Order order, Map<?, Long> signedMap) throws Exception {
        //补充：供应商直送的单子，将签收数量传给采购收货 yinyuxin
        ShippingGroup shippingGroup=shippingGroupService.selectByPrimaryKey(order.getShippingGroup());
        PmPurchaseReceiptSHDto pmPurchaseReceiptSHDto=new PmPurchaseReceiptSHDto();
        List<PurchaseConfirmOrderLinesDto>	orderLines=new ArrayList<>();

        List<OrderItems> orderItems = orderItemsService.getOrderItemsForOrderId( order.getId() );
        List<String> productIds=new ArrayList<>();
        for (OrderItems orderItem : orderItems) {
            CommerceItem commerceItem = commerceItemService.getCommerceItemForId(orderItem.getCommerceItemId());
            // for signedMap to update completed quantity
            //默认签收数量为发货数量
            Long signedQuantity = commerceItem.getShippedQuantity() == null ? 0L : commerceItem.getShippedQuantity();
            if (MapUtils.isNotEmpty(signedMap) && signedMap.get(commerceItem.getId()) != null) {
                signedQuantity = signedMap.get(commerceItem.getId());
            }
            Long quantity = commerceItem.getQuantity() == null ? 0L : commerceItem.getQuantity();
            if (signedQuantity > quantity) {
                log.error("{}-{}签收数量 {} 大于购买数量{}", order.getId(), commerceItem.getId(), signedQuantity, quantity);
                throw new Exception("签收数量大于购买数量");
            }
            commerceItem.setCompletedQuantity(signedQuantity);
            commerceItemService.updateCommerceItem(commerceItem);

            //封装采购收货单需要的数据
            PurchaseConfirmOrderLinesDto purchaseConfirmOrderLinesDto = new PurchaseConfirmOrderLinesDto();
            purchaseConfirmOrderLinesDto.setActualQty(signedQuantity == null ? 0 : signedQuantity.intValue());
            purchaseConfirmOrderLinesDto.setItemId(commerceItem.getProductId());
            productIds.add(commerceItem.getProductId());
            orderLines.add(purchaseConfirmOrderLinesDto);
        }
        //如果是供应商直送的单子 ，才去调用接口 yinyuxin
        if (null != shippingGroup && ShippingModes.PROVIDER_SHIPPING_MODE.getCode().equals(shippingGroup.getShippingModes())) {
            if (CollectionUtils.isNotEmpty(orderLines)) {
                //因为采购收货接口只支持商品code，不支持商品id yinyuxin
                Response<Map<String, ProductIndexDto>> mapResponse = productScIndexDubboService
                        .queryByProductIds(productIds);
                if (null != mapResponse && mapResponse.isSuccess() && null != mapResponse.getResultObject()) {
                    for (PurchaseConfirmOrderLinesDto purchaseConfirmOrderLinesDto : orderLines) {
                        purchaseConfirmOrderLinesDto.setItemCode(mapResponse.getResultObject().get(purchaseConfirmOrderLinesDto.getItemId()).getProductCode());
                    }
                }
            }
            pmPurchaseReceiptSHDto.setPurchaseReceiptNo(order.getReceiptNo());
            pmPurchaseReceiptSHDto.setOrderLines(orderLines);
            log.info("orderFulfillerDubboServiceFlow-->orderReceive()-->传数据给采购收货,param:{}", JSONObject.toJSONString(pmPurchaseReceiptSHDto));
            Response<Boolean> response = pmPurchaseOrderWriteDubboService
                    .updatePurchaseReceiptSHStatus(pmPurchaseReceiptSHDto);
            log.info("orderFulfillerDubboServiceFlow-->orderReceive()-->传数据给采购收货,result:{}", JSONObject.toJSONString(response));
            if (null == response || !response.isSuccess()) {
                throw new RuntimeException("供应商直送订单，传送给采收收货时出错");
            }
        }

        return true;
    }




}
