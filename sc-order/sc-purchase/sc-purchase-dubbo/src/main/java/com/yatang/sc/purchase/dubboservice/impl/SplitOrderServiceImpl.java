package com.yatang.sc.purchase.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.jcraft.jsch.MAC;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptDetailDto;
import com.yatang.sc.facade.dto.pm.PmPurchaseReceiptItemsDto;
import com.yatang.sc.facade.dubboservice.PmPurchaseReceiptQueryDubboService;
import com.yatang.sc.facade.dubboservice.PmPurchaseReceiptWriteDubboService;
import com.yatang.sc.order.domain.CommerceItem;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.ShippingGroup;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.service.OrderLogService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.service.ShippingGroupService;
import com.yatang.sc.order.split.SplitOrderRequestDto;
import com.yatang.sc.order.split.enums.SubOrderType;
import com.yatang.sc.order.states.OrderFrom;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.OrderTotalStates;
import com.yatang.sc.order.states.OrderTypes;
import com.yatang.sc.order.states.PaymentStates;
import com.yatang.sc.order.states.ShippingModes;
import com.yatang.sc.order.states.ShippingStates;
import com.yatang.sc.purchase.dubboservice.SplitOrderService;
import com.yatang.sc.purchase.service.ProviderOrderHelper;
import com.yatang.sc.purchase.service.SplitOrderHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.ArrayList;
import java.util.List;

@Service("splitOrderService")
public class SplitOrderServiceImpl implements SplitOrderService {

    private Logger mLogger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SplitOrderHelper mSplitOrderHelper;

    @Autowired
    private OrderService mOrderService;

    @Autowired
    OrderMessageSender orderMessageSender;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    OrderLogService orderLogService;

    @Autowired
    private ShippingGroupService shippingGroupService;

    @Autowired
    private CommerceItemService mCommerceItemService;

    @Autowired
    private PmPurchaseReceiptWriteDubboService mPmPurchaseReceiptWriteDubboService;

    @Autowired
    private PmPurchaseReceiptQueryDubboService mPmPurchaseReceiptQueryDubboService;

    @Autowired
    OrderService orderService;

    @Autowired
    private ProviderOrderHelper mProviderOrderHelper;
    @Override
    public Response<Boolean> splitOrderByInventory(String mainOrder, SplitOrderRequestDto groups) {
        Response<Boolean> response = new Response<Boolean>();
        Order order = mOrderService.selectByPrimaryKey(mainOrder);
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = transactionManager.getTransaction(transactionDefinition);
        String enoughOrderId = null;
        boolean isRollback = false;
        try {
            enoughOrderId = mSplitOrderHelper.splitOrderByInventory(order, groups);
            if (StringUtils.isEmpty(enoughOrderId)) {
                mLogger.warn("拆单无库存充足的订单:{}", mainOrder);
                return response;
            }
            mLogger.info("拆单已完成：{}--》{}", mainOrder, enoughOrderId);
            response.setSuccess(true);
        } catch (Exception pE) {
            isRollback = true;
            mLogger.error("库存拆分订单异常", pE);
            response.setSuccess(false);
            response.setErrorMessage("订单拆分异常");
            response.setCode("500");
            return response;
        } finally {
            if (isRollback) {
                transactionManager.rollback(txStatus);
            } else {
                transactionManager.commit(txStatus);
            }
        }
        Order subOrder = mOrderService.selectByPrimaryKey(enoughOrderId);
        if (subOrder == null) {
            mLogger.warn("未查询到子订单:{}", enoughOrderId);
            return response;
        }
        if (subOrder.getPaymentState().equals(PaymentStates.DEBITED)
                || subOrder.getPaymentState().equals(PaymentStates.GSN)) {
            mLogger.info("拆单库存充足订单支付状态:{}-->{}", enoughOrderId, subOrder.getPaymentState());
            OrderMessage wmsMessage = new OrderMessage();
            wmsMessage.setMssageType(OrderMessageType.SendWMSDeliveryMessage);
            wmsMessage.setOrderId(subOrder.getId());
            orderMessageSender.sendMsg(wmsMessage);
        }

        return response;
    }

    @Override
    public Response<Boolean> manualSplitOrderByInventory(String mainOrder, SplitOrderRequestDto groups) {
        Response<Boolean> response = new Response<Boolean>();
        Order order = mOrderService.selectByPrimaryKey(mainOrder);
        String shippingState = order.getShippingState();
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = transactionManager.getTransaction(transactionDefinition);
        List<String> subOrderIds = new ArrayList<>();
        boolean isRollback = false;
        try {
            subOrderIds = mSplitOrderHelper.manualSplitOrderByInventory(order, groups,OrderFrom.MANUAL_SPLIT);
            response.setSuccess(CollectionUtils.isNotEmpty(subOrderIds));
            updateParentOrderForSplit(order);
            orderLogService.saveOrderLog(order.getId(), order.getState(),"手动拆分订单", "系统");
        } catch (Exception pE) {
            isRollback = true;
            mLogger.error("拆分订单异常", pE);
            response.setSuccess(false);
            response.setErrorMessage("订单拆分异常");
            response.setCode("500");
            return response;
        } finally {
            if (isRollback) {
                transactionManager.rollback(txStatus);
            } else {
                transactionManager.commit(txStatus);
            }
        }

        /**
         * 未传送订单拆单成功过后直接发送传送订单到WMS的mq消息
         */
        boolean isPendingTransferOrder = ShippingStates.PENDING_TRANSFER.equalsIgnoreCase(shippingState);
        for (String subOrderId : subOrderIds) {
            mLogger.info("发送预留库存消息：{}==>{}", subOrderId, isPendingTransferOrder);
            OrderMessage wmsMessage = new OrderMessage();
            wmsMessage.setMssageType(isPendingTransferOrder ? OrderMessageType.SendWMSDeliveryMessage : OrderMessageType.Reserve_Order_Inventory);
            wmsMessage.setOrderId(subOrderId);
            orderMessageSender.sendMsg(wmsMessage);
        }
        return response;
    }

    @Override
    public Response<Boolean> splitOrderByShippingModes(String mainOrder) {
        Response<Boolean> response = new Response<Boolean>();
        Order order = mOrderService.selectByPrimaryKey(mainOrder);
        if (order == null) {
            mLogger.warn("订单不存在:{}", mainOrder);
            response.setSuccess(false);
            response.setErrorMessage("订单不存在");
            return response;
        }
        SplitOrderRequestDto groups = null;
        if (OrderTypes.NORMAL_SALE.equals(order.getOrderType()) || OrderTypes.DIRECT_STORE.equals(order.getOrderType())) {
            try {
                groups = mSplitOrderHelper.genSplitOrderRequestByShippingModes(order);
            } catch (Exception pE) {
                mLogger.error(mainOrder + ":根据配送模式生成拆分订单请求失败", pE);
                response.setErrorMessage("根据配送模拆分异常");
                response.setCode("500");
                return response;
            }
        }
        if (groups == null || CollectionUtils.isEmpty(groups.getGroups()) ) {
            mLogger.info("订单无需根据配送方式拆单:{}", order.getId());
            OrderMessage wmsMessage = new OrderMessage();
            wmsMessage.setMssageType(OrderMessageType.Reserve_Order_Inventory);
            wmsMessage.setOrderId(mainOrder);
            orderMessageSender.sendMsg(wmsMessage);
            response.setSuccess(true);
            return response;
        } else if (groups.getGroups().size() == 1) {
            if (SubOrderType.PROVIDER_SHIPPING.equals(groups.getGroups().get(0).getSubOrderType())) {
                DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
                transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
                TransactionStatus txStatus = transactionManager.getTransaction(transactionDefinition);
                boolean isRollback = false;
                try {
                    ShippingGroup shippingGroup = shippingGroupService.selectByPrimaryKey(order.getShippingGroup());
                    mSplitOrderHelper.addProviderShippingGroup(groups.getGroups().get(0).getProdPlaceId(), shippingGroup);
                    shippingGroup.setShippingModes(ShippingModes.PROVIDER_SHIPPING_MODE.getCode());
                    shippingGroupService.update(shippingGroup);

                    order.setShippingState(ShippingStates.PENDING_TRANSFER);
                    orderService.update(order);

                    response.setSuccess(true);
                } catch (Exception pE) {
                    mLogger.error(mainOrder, pE);
                    response.setErrorMessage("根据配送模拆分异常");
                    response.setCode("500");
                    return response;
                } finally {
                    if (isRollback) {
                        transactionManager.rollback(txStatus);
                    } else {
                        transactionManager.commit(txStatus);
                    }
                }
            }
            OrderMessage wmsMessage = new OrderMessage();
            wmsMessage.setMssageType(OrderMessageType.Reserve_Order_Inventory);
            wmsMessage.setOrderId(order.getId());
            orderMessageSender.sendMsg(wmsMessage);
        } else {
            DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
            transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
            TransactionStatus txStatus = transactionManager.getTransaction(transactionDefinition);
            List<String> subOrderIds;
            boolean isRollback = false;
            try {
                subOrderIds = mSplitOrderHelper.manualSplitOrderByInventory(order, groups, OrderFrom.SHIPPING_MODE_SPLIT);
                updateParentOrderForSplit(order);
                orderLogService.saveOrderLog(order.getId(), order.getState(),"根据配送模式拆分订单", "系统");
                response.setSuccess(true);
            } catch (Exception pE) {
                isRollback = true;
                mLogger.error("根据配送模拆分异常", pE);
                response.setSuccess(false);
                response.setErrorMessage("根据配送模拆分异常");
                response.setCode("500");
                return response;
            } finally {
                if (isRollback) {
                    transactionManager.rollback(txStatus);
                } else {
                    transactionManager.commit(txStatus);
                }
            }

            if (CollectionUtils.isEmpty(subOrderIds)) {
                mLogger.info("根据配送模未拆单：{}", order.getId());
                response.setSuccess(true);
                return response;
            }
            for (String subOrderId : subOrderIds) {
                OrderMessage wmsMessage = new OrderMessage();
                wmsMessage.setMssageType(OrderMessageType.Reserve_Order_Inventory);
                wmsMessage.setOrderId(subOrderId);
                orderMessageSender.sendMsg(wmsMessage);
            }
        }
        return response;
    }

    @Override
    public Response<String> splitOrderByASN(String mainOrder, String receiptId) throws Exception {
        Response<String> response = new Response<String>();
        Order order = fetchOrderForAsnSplit(mainOrder);
        if (!ShippingStates.PENDING_DELIVERY.equals(order.getShippingState())) {
            response.setSuccess(false);
            response.setErrorMessage("订单状态不正确:" + order.getShippingState());
            return response;
        }

        boolean isRollback = false;
        String asnSubOrderId = order.getId();
        List<CommerceItem> commerceItems = mCommerceItemService.getCommerceItemAndPriceForOrderId(order.getId());
        if (CollectionUtils.isEmpty(commerceItems)) {
            mLogger.info("采购订单商品行为空:{}", order.getId());
            response.setSuccess(false);
            response.setErrorMessage("采购订单商品行为空");
            return response;
        }
        Response<PmPurchaseReceiptDetailDto> receiptDetailDtoResponse = mPmPurchaseReceiptQueryDubboService.queryReceiptDetailById(Long.valueOf(receiptId));
        if (receiptDetailDtoResponse == null || !receiptDetailDtoResponse.isSuccess() || receiptDetailDtoResponse.getResultObject() == null) {
            mLogger.info("采购订单不存在:{}", receiptId);
            response.setSuccess(false);
            response.setErrorMessage("采购订单不存在");
            return response;
        }

        PmPurchaseReceiptDetailDto pmPurchaseReceiptDetailDto = receiptDetailDtoResponse.getResultObject();
        String receiptNo = pmPurchaseReceiptDetailDto.getPmPurchaseReceipt().getPurchaseReceiptNo();
        Order receiptOrder = orderService.getOrderByReceiptNo(receiptNo);
        if (receiptOrder != null) {
            mLogger.info("已处理ASN 通知:{}->{}", receiptOrder.getId(), receiptId);
            return response;
        }


        if (CollectionUtils.isEmpty(pmPurchaseReceiptDetailDto.getReceiptPruducts())) {
            mLogger.info("ASN通知信息无发货商品数据:{}", receiptId);
            response.setSuccess(false);
            response.setErrorMessage("ASN通知信息无发货商品数据");
            return response;
        }
        boolean isMatch = isRecieptMatchPurchaseOrderCount(commerceItems, pmPurchaseReceiptDetailDto.getReceiptPruducts());
        mLogger.info("收货单数量是否和销售单数量一致:{}", isMatch);

        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = transactionManager.getTransaction(transactionDefinition);
        try {
            if (!isMatch) {
                SplitOrderRequestDto splitOrderRequestDto = mProviderOrderHelper.genSplitOrderRequest(commerceItems, pmPurchaseReceiptDetailDto.getReceiptPruducts());
                mLogger.info("ASN收货单信息导致销售订单分组情况:{}", JSON.toJSONString(splitOrderRequestDto));
                asnSubOrderId = mSplitOrderHelper.splitOrderByASN(order, splitOrderRequestDto);
                updateParentOrderForSplit(order);
                orderLogService.saveOrderLog(order.getId(), order.getState(), "根据ASN拆分订单: " + receiptId, "系统");

                Order asnOrder = mOrderService.selectByPrimaryKey(asnSubOrderId);
                asnOrder.setReceiptNo(receiptNo);
                asnOrder.setState(OrderTotalStates.PENDING_RECEIVING.getStateValue());
                asnOrder.setStateDetail(OrderTotalStates.PENDING_RECEIVING.getDescription());
                mOrderService.update(asnOrder);
                updateShippingGroupByASN(pmPurchaseReceiptDetailDto, asnOrder);

                response.setSuccess(StringUtils.isNotEmpty(asnSubOrderId));
                response.setResultObject(asnSubOrderId);
            }else {
                order.setShippingState(ShippingStates.PENDING_RECEIVE);
                order.setState(OrderTotalStates.PENDING_RECEIVING.getStateValue());
                order.setStateDetail(OrderTotalStates.PENDING_RECEIVING.getDescription());
                order.setReceiptNo(receiptNo);
                mOrderService.updateOrderSelective(order);
                updateShippingGroupByASN(pmPurchaseReceiptDetailDto, order);
                for (CommerceItem commerceItem : commerceItems) {//更新发货数量
                    for (PmPurchaseReceiptItemsDto receiptItemsDto : pmPurchaseReceiptDetailDto.getReceiptPruducts()) {
                        if (commerceItem.getProductId().equals(receiptItemsDto.getProductId())) {
                            commerceItem.setShippedQuantity(Long.valueOf(receiptItemsDto.getDeliveryNumber()));
                            mCommerceItemService.updateCommerceItem(commerceItem);
                        }
                    }
                }
                response.setResultObject(order.getId());
                response.setSuccess(true);
            }
            orderLogService.saveOrderLog(asnSubOrderId, OrderTotalStates.PENDING_DELIVERY.getStateValue(), "订单出库，等待收货！", "系统");

        } catch (Exception pE) {
            isRollback = true;
            mLogger.error("ASN拆分订单异常", pE);
            response.setSuccess(false);
            response.setErrorMessage("ASN拆分异常");
            response.setCode("500");
            return response;
        } finally {
            if (isRollback) {
                transactionManager.rollback(txStatus);
            } else {
                transactionManager.commit(txStatus);
            }
        }
        if (!isRollback) {
            Response<Void> pmResponse = mPmPurchaseReceiptWriteDubboService.updateSplitPurchaseReceipt(Long.valueOf(receiptId), asnSubOrderId);
            mLogger.info("ASN处理完成，调用销售收货单关系接口结果{}", JSON.toJSONString(pmResponse));
        }
        return response;
    }

    private Order fetchOrderForAsnSplit(String orderNo){
        Order originOrder = orderService.selectByPrimaryKey(orderNo);
        mLogger.info("fetchOrderForAsnSplit:originOrder:{}-->{}", originOrder.getId(), originOrder.getShippingState());
        if(!OrderStates.SPLITED_ORDER.equals(originOrder.getOrderState())){
            return originOrder;
        }
        ShippingGroup shippingGroup = shippingGroupService.selectByPrimaryKey(originOrder.getShippingGroup());
        if(ShippingModes.PROVIDER_SHIPPING_MODE.getCode().equals(shippingGroup.getShippingModes())){//已被拆单的直配订单
            List<Order> subOrders = orderService.getSubOrders(orderNo);
            for (Order subOrder : subOrders) {//找到待出库的子订单作为ASN消息处理的依赖销售订单
                mLogger.info("fetchOrderForAsnSplit:{}-->{}",subOrder.getId(),subOrder.getShippingState());
                if(ShippingStates.PENDING_DELIVERY.equals(subOrder.getShippingState())){
                    return subOrder;
                }
            }
        }
        return originOrder;
    }
    /**
     *
     * @param pPmPurchaseReceiptDetailDto
     * @param pAsnOrder
     */
    private void updateShippingGroupByASN(PmPurchaseReceiptDetailDto pPmPurchaseReceiptDetailDto, Order pAsnOrder) {
        ShippingGroup shippingGroup = shippingGroupService.selectByPrimaryKey(pAsnOrder.getShippingGroup());
        shippingGroup.setShipOnDate(pPmPurchaseReceiptDetailDto.getPmPurchaseReceipt().getEstimatedDeliveryDate());
        shippingGroup.setActualShipDate(pPmPurchaseReceiptDetailDto.getPmPurchaseReceipt().getEstimatedDeliveryDate());
        shippingGroup.setEstimatedArrivalDate(pPmPurchaseReceiptDetailDto.getPmPurchaseReceipt().getEstimatedReceivedDate());
        shippingGroupService.update(shippingGroup);
    }

    private void updateParentOrderForSplit(Order pOrder) {
        if (StringUtils.isEmpty(pOrder.getCreatedByOrderId())) {
            pOrder.setShippingState("");
            pOrder.setPaymentState("");
            pOrder.setOrderState(OrderStates.SPLITED_ORDER);
            pOrder.setPendingSplit(false);
        }
        mOrderService.update(pOrder);
        mLogger.info("更新主订单{}”", pOrder.getId());
    }

    public boolean isRecieptMatchPurchaseOrderCount(List<CommerceItem> pCommerceItems, List<PmPurchaseReceiptItemsDto> pReceiptItemsDtoList) {
        boolean isMatch = true;
        for (CommerceItem item : pCommerceItems) {
            if (!isMatch) {
                break;
            }
            PmPurchaseReceiptItemsDto findReceiptItemsDto = null;
            for (PmPurchaseReceiptItemsDto receiptItemsDto : pReceiptItemsDtoList) {
                if (item.getProductId().equals(receiptItemsDto.getProductId())) {
                    findReceiptItemsDto = receiptItemsDto;
                }
            }
            if (findReceiptItemsDto == null || item.getQuantity().intValue() != findReceiptItemsDto.getDeliveryNumber()) {
                isMatch = false;
                break;
            }
        }
        return isMatch;
    }
}
