package com.yatang.sc.flow.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.common.staticvalue.CommonsEnum;
import com.yatang.sc.exception.OrderReturnedException;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.facade.dubboservice.WarehouseLogicQueryDubboService;
import com.yatang.sc.flow.OrderReturnRequestFlowService;
import com.yatang.sc.fulfillment.dto.OrderReceiveDto;
import com.yatang.sc.fulfillment.dto.OrderRejectDto;
import com.yatang.sc.fulfillment.dto.OrderReturnOperateMessageDto;
import com.yatang.sc.fulfillment.dubboservice.OrderFulfillerDubboService;
import com.yatang.sc.inventory.dto.ItemInventoryDto;
import com.yatang.sc.inventory.dto.OrderInventoryDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import com.yatang.sc.kidd.dto.common.KiddOrderLinesDto;
import com.yatang.sc.kidd.dto.common.KiddSenderInfoDto;
import com.yatang.sc.kidd.dto.returnrequest.KiddReturnOrderDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderCargoDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderDeliveryInfoDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderReceiverInfoDto;
import com.yatang.sc.kidd.dto.saleOrder.KiddSaleOrderRelateOrderDto;
import com.yatang.sc.kidd.service.KiddReturnOrderService;
import com.yatang.sc.kidd.service.KiddSaleOrderService;
import com.yatang.sc.order.domain.CommerceItem;
import com.yatang.sc.order.domain.ItemPrice;
import com.yatang.sc.order.domain.OrderItems;
import com.yatang.sc.order.domain.ShippingGroup;
import com.yatang.sc.order.domain.returned.OrderReturnOperateMessagePo;
import com.yatang.sc.order.domain.returned.ReturnRequestItemPo;
import com.yatang.sc.order.domain.returned.ReturnRequestPo;
import com.yatang.sc.order.domain.returned.ReturnRequestReceiptPo;
import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.service.ItemPriceService;
import com.yatang.sc.order.service.OrderItemsService;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.ReturnRequestItemService;
import com.yatang.sc.order.service.ReturnRequestService;
import com.yatang.sc.order.service.ShippingGroupService;
import com.yatang.sc.order.states.KiddOrderReturnedTypes;
import com.yatang.sc.order.states.OrderReturnRequestTypes;
import com.yatang.sc.order.states.OrderReturnedItemStates;
import com.yatang.sc.order.states.OrderReturnedProductSates;
import com.yatang.sc.order.states.OrderReturnedStates;
import com.yatang.sc.purchase.dto.returned.ReturnRequestDto;
import com.yatang.sc.purchase.dto.returned.ReturnRequestItemDto;
import com.yatang.sc.purchase.dto.returned.ReturnRequestReceiptDto;
import com.yatang.xc.mbd.biz.org.dto.BranchCompanyDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.biz.prod.dubboservice.ProductDubboService;
import com.yatang.xc.mbd.biz.prod.dubboservice.dto.ProductDto;
import com.yatang.xc.mbd.org.es.dubboservice.OrganizationIndexSCDubboService;
import com.yatang.xc.mbd.org.es.sc.dto.StoreScOrderCodeDto;
import org.apache.commons.lang3.StringUtils;
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

/**
 * @描述: 退换货的flow服务接口定义
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/10/19 17:22
 * @版本: v1.0
 */
@Service("orderReturnRequestFlowService")
public class OrderReturnRequestFlowServiceImpl implements OrderReturnRequestFlowService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private OrganizationIndexSCDubboService organizationIndexSCDubboService;


    @Autowired
    private ReturnRequestService returnRequestService;

    @Autowired
    private ReturnRequestItemService returnRequestItemService;

    @Autowired
    private KiddReturnOrderService returnOrderService;


    @Autowired
    private ItemLocInventoryDubboService inventoryDubboService;

    @Autowired
    private OrderLogService orderLogService;

    @Autowired
    private KiddSaleOrderService kiddSaleOrderService;

    @Autowired
    private ShippingGroupService shippingGroupService;


    @Autowired
    private CommerceItemService commerceItemService;

    @Autowired
    private OrderItemsService orderItemsService;

    @Autowired
    private ProductDubboService productDubboService;


    @Autowired
    private ItemPriceService itemPriceService;

    @Autowired
    private WarehouseLogicQueryDubboService logicQueryDubboService;

    @Autowired
    private OrderFulfillerDubboService fulfillerDubboService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<Void> confirmOrderReturnedReceipt(OrderReturnOperateMessageDto operateMessageDto) {
        logger.info("flow--confirmOrderReturnedReceipt>>退还货单确认参数:{}", JSON.toJSONString(operateMessageDto));

        Response<Void> response = new Response<>();
        //1.获取主表信息和原订单信息
        ReturnRequestPo returnRequestPo = returnRequestService.selectByPrimaryKey(operateMessageDto.getReturnId());
        List<ReturnRequestItemPo> returnRequestItemPos = returnRequestItemService.queryReturnRequestItem(operateMessageDto.getReturnId());


        String shippingGroup = returnRequestPo.getShippingGroup();//获取物流信息
        ShippingGroup shippingGroupPo = shippingGroupService.selectByPrimaryKey(shippingGroup);
        //2.更新退换货单dto2vo
        OrderReturnOperateMessagePo operateMessagePo = BeanConvertUtils.convert(operateMessageDto, OrderReturnOperateMessagePo.class);
        boolean confirmSuccess = returnRequestService.operateOrderReturnedReceipt(operateMessagePo) == 1;
        if (!confirmSuccess) {
            logger.error("flow--confirmOrderReturnedReceipt>>单号为【{}】退货单更新确认状态失败", operateMessageDto.getReturnId());
            response.setCode(CommonsEnum.RESPONSE_20111.getCode());
            response.setErrorMessage("单号为:" + operateMessageDto.getReturnId() + CommonsEnum.RESPONSE_20111.getName());
            response.setSuccess(false);
            return response;
        }

        //2.1 原订单相关签收 拒签操作
        if (OrderReturnRequestTypes.REJECT_RETURN.getStateValue().equals(returnRequestPo.getReturnRequestType())) {//判定是否是拒收退货
            //改变订单状态以及签收数量 签收数量等于元订单数量-返回数量
            updateOriginOrder(returnRequestPo, returnRequestItemPos);
        }

        //3.转换为调用第三方数据
        KiddReturnOrderDto kiddReturnOrderDto = new KiddReturnOrderDto();//主表信息
        kiddReturnOrderDto.setWareHouseCode(returnRequestPo.getBranchCompanyArehouse());

        //根据雅堂分公司id查询第三方仓库对应的branchCompanyCode
        Response<LogicWarehouseDto> logicWarehouseDtoResponse = logicQueryDubboService.selectLogicWarehouseByPrimaryKey(returnRequestPo.getBranchCompanyArehouse());
        logger.info("根据仓库编码【{}】,查询仓库信息返回结果：{}", returnRequestPo.getBranchCompanyArehouse(), JSON.toJSONString(logicWarehouseDtoResponse));
        if (null == logicWarehouseDtoResponse || !logicWarehouseDtoResponse.isSuccess() || null == logicWarehouseDtoResponse.getResultObject()) {
            logger.error("根据仓库编码【{}】,查询仓库信息返回结果失败", returnRequestPo.getBranchCompanyArehouse());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_20007.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_20007.getName());
            return response;
        }

        kiddReturnOrderDto.setOwnerCode(logicWarehouseDtoResponse.getResultObject().getBranchCompanyCode());//分公司编码
        kiddReturnOrderDto.setEntryOrderCode(returnRequestPo.getId());
        kiddReturnOrderDto.setPreDeliveryOrderCode(returnRequestPo.getOrderId());

        String returnRequestType = returnRequestPo.getReturnRequestType();//退换货类型
        switch (OrderReturnRequestTypes.parse(returnRequestType)) {//设置退换货单主键
            case NORMAL_EXCHANGE://换货
                kiddReturnOrderDto.setOrderType(KiddOrderReturnedTypes.ORDER_EXCHANGE);//
                break;
            case NORMAL_RETURN://正常退回
            case REJECT_RETURN://拒收退货
            /*    if (OrderReturnRequestTypes.REJECT_RETURN.getStateValue().equals( returnRequestType )) {//判定是否是拒收退货
                    //1 改变订单状态以及签收数量 签收数量等于元订单数量-返回数量
                    updateOriginOrder( returnRequestPo, returnRequestItemPos );
                }*/
                kiddReturnOrderDto.setOrderType(KiddOrderReturnedTypes.ORDER_RETURN);//
                break;
            default:
                logger.error("flow--confirmOrderReturnedReceipt>>退换货单类型判断失败,当前类型:{}", returnRequestType);
                response.setSuccess(false);
                response.setCode(CommonsEnum.RESPONSE_20104.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_20104.getName() + OrderReturnRequestTypes.parse(returnRequestType).getDescription());
                return response;

        }
        kiddReturnOrderDto.setLogisticsCode("OTHER");

        //从原订单获取相关信息

        // 发货人信息
        KiddSenderInfoDto kiddSenderInfoDto = new KiddSenderInfoDto();
        kiddReturnOrderDto.setSenderInfo(kiddSenderInfoDto);
        Response<BranchCompanyDto> branchCompanyDtoResponse = organizationService.querySimpleByBranchCompanyId(returnRequestPo.getBranchCompanyId());
        if (branchCompanyDtoResponse.isSuccess()) {
            if (branchCompanyDtoResponse.getResultObject() != null) {
                kiddSenderInfoDto.setSenderName(branchCompanyDtoResponse.getResultObject().getName());//获取公司名称
            }
        }
        kiddSenderInfoDto.setSenderName(shippingGroupPo.getConsigneeName());
        kiddSenderInfoDto.setSenderZipcode(shippingGroupPo.getPostcode());
        kiddSenderInfoDto.setSenderTel(shippingGroupPo.getCellphone());
        kiddSenderInfoDto.setSenderMobile(shippingGroupPo.getCellphone());
        kiddSenderInfoDto.setSenderProvince(shippingGroupPo.getProvince());
        kiddSenderInfoDto.setSenderCity(shippingGroupPo.getCity());
        kiddSenderInfoDto.setSenderArea(shippingGroupPo.getDistrict());
        kiddSenderInfoDto.setSenderDetailAddress(shippingGroupPo.getDetailAddress());


        //获取商品信息
        Integer count = 1;
        List<KiddOrderLinesDto> orderLinesDtos = new ArrayList<>();
        for (ReturnRequestItemPo requestItemPo : returnRequestItemPos) {
            KiddOrderLinesDto kiddOrderLinesDto = new KiddOrderLinesDto();
            kiddOrderLinesDto.setOrderLineNo(String.valueOf((count)));
            kiddOrderLinesDto.setReturnOrderLineNo(returnRequestPo.getOrderId());
            kiddOrderLinesDto.setItemCode(requestItemPo.getProductCode());//
//            kiddOrderLinesDto.setItemId(requestItemPo.getProductId());
            kiddOrderLinesDto.setItemName(requestItemPo.getProductName());
            kiddOrderLinesDto.setPlanQty(requestItemPo.getReturnQuantity().intValue());
//            kiddOrderLinesDto.setInventoryType("ZP");//正品
            kiddOrderLinesDto.setPurchasePrice(0.0);//设置采购单价 为0.0
            orderLinesDtos.add(kiddOrderLinesDto);
            count++;

        }
        kiddReturnOrderDto.setOrderLines(orderLinesDtos);

        Response<String> kiddResponse = returnOrderService.create(kiddReturnOrderDto);
        logger.info("flow--confirmOrderReturnedReceipt>>调用第三方创建确认退换货单结果:{}", JSON.toJSONString(kiddResponse));
        if (null == kiddResponse || !kiddResponse.isSuccess()) {
            logger.error("flow--confirmOrderReturnedReceipt>>调用第三方创建确认退换失败:{}", JSON.toJSONString(kiddResponse));
            throw new OrderReturnedException(CommonsEnum.RESPONSE_20124.getName());
        }
        orderLogService.saveOrderLog(operateMessageDto.getReturnId(), operateMessagePo.getOperateType(), "退换货单推送第三方成功", operateMessageDto.getUserId());
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        response.setSuccess(true);
        return response;
    }


    @Override
    public Response<Void> cancelOrderReturnedReceipt(OrderReturnOperateMessageDto operateMessageDto) {
        logger.info("flow--cancelOrderReturnedReceipt>>退换货单更新取消状态请求参数：{}", JSON.toJSONString(operateMessageDto));
        Response<Void> response = new Response<>();
        //dto2vo
        OrderReturnOperateMessagePo operateMessagePo = BeanConvertUtils.convert(operateMessageDto, OrderReturnOperateMessagePo.class);
        boolean cancelSuccess = returnRequestService.operateOrderReturnedReceipt(operateMessagePo) == 1;
        if (!cancelSuccess) {
            logger.error("flow--cancelOrderReturnedReceipt>>单号为【{}】退换货单更新取消状态失败", operateMessageDto.getReturnId());
            response.setCode(CommonsEnum.RESPONSE_20111.getCode());
            response.setErrorMessage("单号为:" + operateMessageDto.getReturnId() + CommonsEnum.RESPONSE_20111.getName());
            orderLogService.saveOrderLog(operateMessageDto.getReturnId(), operateMessagePo.getOperateType(), "退换货单取消失败", operateMessageDto.getUserId());
            response.setSuccess(false);
            return response;
        }
        orderLogService.saveOrderLog(operateMessageDto.getReturnId(), operateMessagePo.getOperateType(), "退换货单取消成功", operateMessageDto.getUserId());
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        response.setSuccess(true);
        return response;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<Void> completeReturnedOrderTReceipt(ReturnRequestReceiptDto requestReceiptDto) {
        logger.info("flow---completeReturnedOrderTReceipt>>退货单处理参数:{}", JSON.toJSONString(requestReceiptDto));

        Response<Void> response = new Response<>();
        List<ReturnRequestItemDto> requestItems = requestReceiptDto.getRequestItems();//退换货商品item
        ReturnRequestDto returnRequest = requestReceiptDto.getReturnRequest();//主表
        String returnId = returnRequest.getId();//退货单号
        //获取当前退货单的状态
        Short orderReturnedReceiptState = returnRequestService.getOrderReturnedReceiptState(returnId);
        if (null == orderReturnedReceiptState) {
            logger.error("flow--completeReturnedOrderTReceipt>>单号为【{}】单据不存在", returnId);
            response.setCode(CommonsEnum.RESPONSE_20117.getCode());
            response.setErrorMessage("单号为:" + returnId + "不存在");
            response.setSuccess(false);
            return response;
        }
        if (OrderReturnedStates.COMPLETED.getStateValue() == orderReturnedReceiptState) {//判定订单状态 如果已完成则不能操作
            logger.error("flow--completeReturnedOrderTReceipt>>单号为【{}】退货单已完成不能再执行完成操作", returnId);
            response.setCode(CommonsEnum.RESPONSE_20115.getCode());
            response.setErrorMessage("单号为:" + returnId + CommonsEnum.RESPONSE_20115.getName());
            response.setSuccess(false);
            return response;

        }
        List<ReturnRequestItemPo> returnRequestItemPos = returnRequestItemService.queryReturnRequestItem(returnId);
        boolean fulfillment = false;//退换货总 全部收货总状态
        Double actualAmount = 0.0;
        for (ReturnRequestItemDto requestItemDto : requestItems) {
            for (ReturnRequestItemPo returnRequestItemPo : returnRequestItemPos) {
                if (requestItemDto.getProductCode().equals(returnRequestItemPo.getProductCode())) {//判定是否是当前的Id
                    requestItemDto.setReturnId(returnId);
                    requestItemDto.setProductId(returnRequestItemPo.getProductId());
                    requestItemDto.setId(returnRequestItemPo.getId());//获取itemId
                    Long returnQuantity = returnRequestItemPo.getReturnQuantity() == null ? 0 : returnRequestItemPo.getReturnQuantity();//创建单子时退货数量
                    Long actualReturnQuantity = requestItemDto.getActualReturnQuantity() == null ? 0 : requestItemDto.getActualReturnQuantity();//完成数量
                    if (actualReturnQuantity < returnQuantity) {//完成数量
                        //设置商品级别的状态 部分收货,
                        requestItemDto.setState(OrderReturnedItemStates.PART_COMPLETE.getStateValue());
                        requestItemDto.setStateDetail(OrderReturnedItemStates.PART_COMPLETE.getDescription());
                    } else {//设置商品级别的状态 全部收货
                        fulfillment = true;
                        requestItemDto.setState(OrderReturnedItemStates.FULL_COMPLETE.getStateValue());
                        requestItemDto.setStateDetail(OrderReturnedItemStates.FULL_COMPLETE.getDescription());
                    }

                    //价格
                    ItemPrice itemPrice = itemPriceService.getItemPriceForId(returnRequestItemPo.getItemPriceInfo());
                    Double salePrice = itemPrice.getSalePrice();
                    BigDecimal bigDecimal = new BigDecimal(salePrice * actualReturnQuantity).setScale(2, BigDecimal.ROUND_HALF_UP);// 四舍五入
                    returnRequestItemPo.setActualRawTotalPrice(bigDecimal.doubleValue());//设置单品销售价格
                    actualAmount += bigDecimal.doubleValue();
                    returnRequestItemPos.remove(returnRequestItemPo);//移除当前的item
                    break;
                }

            }
        }
        returnRequest.setActualAmount(actualAmount);//设置实际退货总金额
        if (fulfillment) {//全部收货
            returnRequest.setShippingState(OrderReturnedItemStates.FULL_COMPLETE.getStateValue());
            returnRequest.setShippingStateDetail(OrderReturnedItemStates.FULL_COMPLETE.getDescription());
        } else {
            returnRequest.setShippingState(OrderReturnedItemStates.PART_COMPLETE.getStateValue());
            returnRequest.setShippingStateDetail(OrderReturnedItemStates.PART_COMPLETE.getDescription());
        }
        returnRequest.setState(OrderReturnedStates.COMPLETED.getStateValue());//退货单单总状态已完成
        returnRequest.setStateDetail(OrderReturnedStates.COMPLETED.getDescription());


        //执行更新退货单操作
        //dto2po
        ReturnRequestReceiptPo receiptPo = BeanConvertUtils.convert(requestReceiptDto, ReturnRequestReceiptPo.class);
        boolean updateReceipt = returnRequestService.updateReturnedOrderReceipt(receiptPo);
        if (!updateReceipt) {
            logger.error("flow--completeReturnedOrderTReceipt>>单号为【{}】退换货单更新完成状态失败", returnId);
            orderLogService.saveOrderLog(returnId, OrderReturnedStates.COMPLETED.getStateValue(), "退货单完成操作执行失败", "系统");
            response.setCode(CommonsEnum.RESPONSE_20112.getCode());
            response.setErrorMessage("单号为:" + returnId + CommonsEnum.RESPONSE_20112.getName());
            response.setSuccess(false);
            return response;
        }

        // 执行库存调整操作
        OrderInventoryDto orderInventoryDto = new OrderInventoryDto();
        List<ItemInventoryDto> itemInventoryDtos = new ArrayList<>();
        for (ReturnRequestItemDto requestItem : requestItems) {
            ItemInventoryDto itemInventoryDto = new ItemInventoryDto();
            itemInventoryDto.setProductId(requestItem.getProductId());
            itemInventoryDto.setLoc(returnRequest.getBranchCompanyArehouse());
            itemInventoryDto.setItemQty(requestItem.getActualReturnQuantity());
            itemInventoryDtos.add(itemInventoryDto);
        }
        orderInventoryDto.setItemInventoryDtos(itemInventoryDtos);
        orderInventoryDto.setId(returnRequest.getId());//设置退货单id
        Response<Boolean> inventoryResponse = inventoryDubboService.saleReturn(orderInventoryDto);
        logger.info("flow--completeReturnedOrderTReceipt>>调用退货库存调整返回结果：{}", JSON.toJSONString(inventoryResponse));
        if (!CommonsEnum.RESPONSE_200.getCode().equals(inventoryResponse.getCode())) {
            logger.error("flow--completeReturnedOrderTReceipt>>调用退货库存调整返回结果：{}", JSON.toJSONString(inventoryResponse));
            throw new OrderReturnedException(CommonsEnum.RESPONSE_20125.getName());
        }

        //
        updateOderCommerceItemService(returnId, requestItems);
        orderLogService.saveOrderLog(returnId, OrderReturnedStates.COMPLETED.getStateValue(), "退货单完成操作执行成功", "系统");
        response.setSuccess(true);
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response<Void> completeReturnedOrderHReceipt(ReturnRequestReceiptDto requestReceiptDto) {
        logger.info("flow---completeReturnedOrderHReceipt>>换货单处理参数:{}", JSON.toJSONString(requestReceiptDto));

        Response<Void> response = new Response<>();
        List<ReturnRequestItemDto> requestItemDtos = requestReceiptDto.getRequestItems();//退换货商品item
        ReturnRequestDto returnRequestDto = requestReceiptDto.getReturnRequest();//主表
        String returnId = returnRequestDto.getId();//退货单号

        //更新订单上退换货数量
        ReturnRequestPo requestPo = returnRequestService.selectByPrimaryKey(returnId);
        if (OrderReturnedProductSates.UN_PICKED.getStateValue() != requestPo.getProductState()) {//判定当前换货单状态
            logger.error("flow--completeReturnedOrderHReceipt>>单号为【{}】换货单不能再执行行完成操作,操作状态:{},当前换货单状态", returnId, requestPo.getProductState());
            response.setCode(CommonsEnum.RESPONSE_20116.getCode());
            response.setErrorMessage("单号为:" + returnId + CommonsEnum.RESPONSE_20116.getName());
            response.setSuccess(false);
            return response;
        }

        List<ReturnRequestItemPo> returnRequestItemPos = returnRequestItemService.queryReturnRequestItem(returnId);
        for (ReturnRequestItemDto requestItemDto : requestItemDtos) {
            for (ReturnRequestItemPo returnRequestItemPo : returnRequestItemPos) {
                if (requestItemDto.getProductCode().equals(returnRequestItemPo.getProductCode())) {//判定是否是当前的Id
                    requestItemDto.setReturnId(returnId);
                    requestItemDto.setId(returnRequestItemPo.getId());//获取itemId
                    requestItemDto.setProductId(returnRequestItemPo.getProductId());
                    Long returnQuantity = returnRequestItemPo.getReturnQuantity() == null ? 0 : returnRequestItemPo.getReturnQuantity();//创建单子时退货数量
                    Long actualReturnQuantity = requestItemDto.getActualReturnQuantity() == null ? 0 : requestItemDto.getActualReturnQuantity();//完成数量
                    returnRequestItemPo.setActualReturnQuantity(actualReturnQuantity);
                    if (actualReturnQuantity < returnQuantity) {//完成数量
                        //设置商品级别的状态 部分收货,
                        requestItemDto.setState(OrderReturnedItemStates.PART_COMPLETE.getStateValue());
                        requestItemDto.setStateDetail(OrderReturnedItemStates.PART_COMPLETE.getDescription());
                    } else {//设置商品级别的状态 全部收货
                        requestItemDto.setState(OrderReturnedItemStates.FULL_COMPLETE.getStateValue());
                        requestItemDto.setStateDetail(OrderReturnedItemStates.FULL_COMPLETE.getDescription());
                    }
                }

            }
        }
        returnRequestDto.setProductState(OrderReturnedProductSates.PICKED.getStateValue());
        returnRequestDto.setProductStateDetail(OrderReturnedProductSates.PICKED.getDescription());//更新为已经取货
        //dto2po
        ReturnRequestReceiptPo receiptPo = BeanConvertUtils.convert(requestReceiptDto, ReturnRequestReceiptPo.class);
        boolean updateReceipt = returnRequestService.updateReturnedOrderReceipt(receiptPo);
        if (!updateReceipt) {
            logger.error("flow--completeReturnedOrderTReceipt>>单号为【{}】换货单更新完成状态失败", returnId);
            response.setCode(CommonsEnum.RESPONSE_20112.getCode());
            response.setErrorMessage("单号为:" + returnId + CommonsEnum.RESPONSE_20112.getName());
            orderLogService.saveOrderLog(returnId, OrderReturnedProductSates.PICKED.getStateValue(), "换货单更新已取货失败 ", "系统");
            response.setSuccess(false);
            return response;
        }
        orderLogService.saveOrderLog(returnId, OrderReturnedProductSates.PICKED.getStateValue(), "换货单更新已取货成功 ", "系统");
        //更新订单上退换货数量
        updateOderCommerceItemService(returnId, requestItemDtos);
        //调用第三方创建换货单的销售单
        Response<Void> sendResponse = sendSaleOrder(requestPo, returnRequestItemPos);
        if (!sendResponse.isSuccess()) {
            orderLogService.saveOrderLog(returnId, OrderReturnedProductSates.PICKED.getStateValue(), "换货单更新创建销售订单失败 ", "系统");
        }
        response.setSuccess(true);
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        return response;
    }


    /**
     * 更新订单的退换货数量
     *
     * @param returnId
     * @param requestItems
     */
    public void updateOderCommerceItemService(String returnId, List<ReturnRequestItemDto> requestItems) {
        ReturnRequestPo requestPo = returnRequestService.selectByPrimaryKey(returnId);
        List<OrderItems> orderItems = orderItemsService.getOrderItemsForOrderId(requestPo.getOrderId());
        for (OrderItems orderItem : orderItems) {
            CommerceItem commerceItem = commerceItemService.getCommerceItemForId(orderItem.getCommerceItemId());
            for (ReturnRequestItemDto requestItem : requestItems) {
                if (commerceItem.getProductId().equals(requestItem.getProductId())) {
                    commerceItem.setReturnQuantity(requestItem.getActualReturnQuantity());
                    commerceItemService.updateByPrimaryKeySelective(commerceItem);
                    break;
                }
            }
        }
    }


    /**
     * 发送销售订单
     *
     * @param requestPo            退货单
     * @param returnRequestItemPos
     */
    public Response<Void> sendSaleOrder(ReturnRequestPo requestPo, List<ReturnRequestItemPo> returnRequestItemPos) {
        Response<Void> response = new Response<>();

        KiddSaleOrderDto kiddSaleOrderDto = new KiddSaleOrderDto();//订单信息
        KiddSaleOrderDeliveryInfoDto deliveryInfoDto = new KiddSaleOrderDeliveryInfoDto();//物流相关信息
        //拼接发送单号
        String orderId = requestPo.getId() + "F";
        KiddSaleOrderRelateOrderDto kiddSaleOrderRelateOrderDto = new KiddSaleOrderRelateOrderDto();//订单相关信息
        kiddSaleOrderRelateOrderDto.setOrderCode(orderId);
        deliveryInfoDto.setDeliveryOrderCode(orderId);
        deliveryInfoDto.setWarehouseCode(StringUtils.trimToEmpty(requestPo.getBranchCompanyArehouse()));
        //根据仓库查询对应的第三方branchCompanyCode

        //根据雅堂奋分公司id查询第三方仓库对应的branchCompanyCode
        Response<LogicWarehouseDto> logicWarehouseDtoResponse = logicQueryDubboService.selectLogicWarehouseByPrimaryKey(requestPo.getBranchCompanyArehouse());
        logger.info("根据仓库编码【{}】,查询仓库信息返回结果：{}", requestPo.getBranchCompanyArehouse(), JSON.toJSONString(logicWarehouseDtoResponse));
        if (null == logicWarehouseDtoResponse || !logicWarehouseDtoResponse.isSuccess() || null == logicWarehouseDtoResponse.getResultObject()) {
            logger.error("根据仓库编码【{}】,查询仓库信息返回结果失败", requestPo.getBranchCompanyArehouse());
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_20007.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_20007.getName());
            return response;
        }
        deliveryInfoDto.setOwnerCode(logicWarehouseDtoResponse.getResultObject().getBranchCompanyCode());
        deliveryInfoDto.setCreateTime(new Date());
        deliveryInfoDto.setPayTime(new Date());
        Response<StoreScOrderCodeDto> orderCodeDtoResponse = organizationIndexSCDubboService.queryStoreScOrderCodeById(requestPo.getFranchiseeStoreId());//获取门店信息
        if (null == orderCodeDtoResponse || null == orderCodeDtoResponse.getResultObject()) {
            logger.error("根据门店id【{}】,查询门店信息返回结果失败", orderCodeDtoResponse);
            response.setSuccess(false);
            response.setCode(CommonsEnum.RESPONSE_10039.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_10039.getName());
            return response;
        }
        StoreScOrderCodeDto resultObject = orderCodeDtoResponse.getResultObject();
        String storeCode = resultObject.getOrderCode();//门店编码
        String simpleName = resultObject.getName();//门店简称
        String remarkAddress = "雅堂小超_" + storeCode + "(" + simpleName + ")";
        deliveryInfoDto.setRemarkAddress(remarkAddress);//传加盟商名称，规则如下:雅堂小超_No.000999(凯华丽景便利店)
        //收货人信息
        KiddSaleOrderReceiverInfoDto receiverInfoDto = new KiddSaleOrderReceiverInfoDto();
        ShippingGroup shippingGroup = shippingGroupService.selectByPrimaryKey(requestPo.getShippingGroup());
        receiverInfoDto.setName(StringUtils.trimToEmpty(shippingGroup.getConsigneeName()));
        receiverInfoDto.setMobile(StringUtils.trimToEmpty(shippingGroup.getCellphone()));
        receiverInfoDto.setProvince(StringUtils.trimToEmpty(shippingGroup.getProvince()));
        receiverInfoDto.setCity(StringUtils.trimToEmpty(shippingGroup.getCity()));
        receiverInfoDto.setDetailAddress(StringUtils.trimToEmpty(shippingGroup.getDetailAddress()));
        receiverInfoDto.setArea(StringUtils.trimToEmpty(shippingGroup.getDistrict()));
        deliveryInfoDto.setReceiverInfo(receiverInfoDto);
        kiddSaleOrderDto.setDeliveryOrder(deliveryInfoDto);


        //商品信息
        List<KiddSaleOrderCargoDto> cargoDtoList = new ArrayList<>();
        List<CommerceItem> commerceItemList = commerceItemService.getCommerceItemAndPriceForOrderId(requestPo.getOrderId());
        for (int i = 0; i < returnRequestItemPos.size(); i++) {
            ReturnRequestItemPo returnRequestItemPo = returnRequestItemPos.get(i);
            for (CommerceItem item : commerceItemList) {
                if (returnRequestItemPo.getProductId().equals(item.getProductId())) {
                    KiddSaleOrderCargoDto saleOrderCargoDto = new KiddSaleOrderCargoDto();
                    saleOrderCargoDto.setOrderLineNo(String.valueOf(i));//
                    Response<ProductDto> productDtoRep = productDubboService.queryById(item.getProductId());
                    if (!productDtoRep.isSuccess()) {
                        throw new RuntimeException("商品dubbo服务调用失败。");
                    }
                    saleOrderCargoDto.setItemCode(StringUtils.trimToEmpty(returnRequestItemPo.getProductCode()));
                    saleOrderCargoDto.setItemName(StringUtils.trimToEmpty(returnRequestItemPo.getProductName()));
                    saleOrderCargoDto.setInventoryType("ZP");
                    saleOrderCargoDto.setPlanQty(returnRequestItemPo.getActualReturnQuantity().intValue());
                    saleOrderCargoDto.setUnit(productDtoRep.getResultObject().getMinUnit());

                    Double salePrice = item.getItemPrice().getSalePrice();
                    saleOrderCargoDto.setRetailPrice(salePrice == null ? 0.0 : salePrice);

                    Double amount = item.getItemPrice().getAmount();
                    saleOrderCargoDto.setActualPrice(amount == null ? 0.0 : amount);
                    Double discountShare = item.getItemPrice().getOrderDiscountShare();
                    saleOrderCargoDto.setDiscountAmount(discountShare == null ? 0.0 : discountShare);
                    cargoDtoList.add(saleOrderCargoDto);
                    commerceItemList.remove(item);
                    break;
                }
            }
        }
        kiddSaleOrderDto.setOrderLines(cargoDtoList);
        Response<String> responseDto = kiddSaleOrderService.add(kiddSaleOrderDto);
        logger.info("发送换货单的返回：{}", JSON.toJSONString(responseDto));
        if (responseDto == null || !responseDto.getCode().equals("200")) {
            logger.error("发货订单[{}]创建失败", orderId);

            throw new OrderReturnedException(CommonsEnum.RESPONSE_20126.getName());
        } else {
            orderLogService.saveOrderLog(orderId, OrderReturnedProductSates.PICKED.getStateValue(), "销售订单发货单创建成功  ", "系统");
        }
        response.setSuccess(true);
        response.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        return response;
    }


    /**
     * 更新原订单 签收状态以及以及数量
     *
     * @param returnRequestPo
     * @param returnRequestItemPos
     */
    public void updateOriginOrder(ReturnRequestPo returnRequestPo, List<ReturnRequestItemPo> returnRequestItemPos) {
        logger.info("flow--updateOriginOrder>>更新订单状态以及签收数量=>>退货单主表:{},退货单item：{}", JSON.toJSONString(returnRequestPo), JSON.toJSONString(returnRequestItemPos));
        //首先判定当前的退货数量是否等于原订单数量
        //如果是部分拒收，更新原销售订单的物流状态为”已签收”，如果是全部拒收，更新原销售订单的物流状态为”未送达”。
        String orderId = returnRequestPo.getOrderId();//订单id
        List<OrderItems> orderItems = orderItemsService.getOrderItemsForOrderId(returnRequestPo.getOrderId());
        int orderItemNum = orderItems.size();//原定单商品数量
        int returnItemNum = returnRequestItemPos.size();//返回的商品数量
        boolean fullReturn = true;//全部拒收

        Map<Long, Long> signedMap = new HashMap<>();//签收数量(k:商品id,v签收数量)
        for (OrderItems orderItem : orderItems) {
            CommerceItem commerceItem = commerceItemService.getCommerceItemForId(orderItem.getCommerceItemId());
            for (ReturnRequestItemPo requestItem : returnRequestItemPos) {//
                if (commerceItem.getProductId().equals(requestItem.getProductId())) {
                    Long quantity = (commerceItem.getShippedQuantity() == null||commerceItem.getShippedQuantity()==0) ? commerceItem.getQuantity() : commerceItem.getShippedQuantity();//原定单数量  getShippedQuantity
                    Long returnQuantity = requestItem.getReturnQuantity() == null ? 0 : requestItem.getReturnQuantity();//退换数量
                    Long completedQuantity = quantity - returnQuantity;//签收数量
                    signedMap.put(commerceItem.getId()==null?null:commerceItem.getId(), completedQuantity);

                    if (0 != completedQuantity) {//签收数量不为零
                        fullReturn = false;
                    }
                    break;
                }
            }
        }
        //更新物物流状态

        if (orderItemNum == returnItemNum && fullReturn) {//更新原订单为拒绝状态
            OrderRejectDto orderRejectDto = new OrderRejectDto();
            orderRejectDto.setOrderId(orderId);//
            Response<Boolean> booleanResponse = fulfillerDubboService.orderReject(orderRejectDto);
            if (!booleanResponse.isSuccess()) {
                logger.error("订单[{}]拒绝失败状态更新失败", orderId);
                throw new OrderReturnedException("订单拒绝失败状态更新失败");
            }
        } else {//更新为签收状态
            OrderReceiveDto orderReceiveDto = new OrderReceiveDto();
            orderReceiveDto.setOrderId(orderId);
            orderReceiveDto.setSignedMap(signedMap);//签收数量
            Response<Boolean> booleanResponse = fulfillerDubboService.orderReceive(orderReceiveDto);
            if (!booleanResponse.isSuccess()) {
                logger.error("订单[{}]更新为签收状态失败", orderId);
                throw new OrderReturnedException("订单更新为签收状态失败");
            }


        }

    }
}
