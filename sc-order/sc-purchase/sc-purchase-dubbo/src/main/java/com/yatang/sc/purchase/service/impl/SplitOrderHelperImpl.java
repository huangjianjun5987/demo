package com.yatang.sc.purchase.service.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.busi.common.utils.StringUtils;
import com.yatang.sc.facade.dto.prod.place.ProdPlaceDto;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.facade.dubboservice.prodplace.ProdPlaceQueryDubboService;
import com.yatang.sc.order.domain.CommerceItem;
import com.yatang.sc.order.domain.Invoice;
import com.yatang.sc.order.domain.ItemPrice;
import com.yatang.sc.order.domain.ItemPriceAdj;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.OrderItems;
import com.yatang.sc.order.domain.OrderPrice;
import com.yatang.sc.order.domain.OrderPriceAdj;
import com.yatang.sc.order.domain.PaymentGroup;
import com.yatang.sc.order.domain.PriceAdjustment;
import com.yatang.sc.order.domain.ProviderShippingGroup;
import com.yatang.sc.order.domain.ShippingGroup;
import com.yatang.sc.order.domain.ShippingPrice;
import com.yatang.sc.order.domain.ShippingPriceAdj;
import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.service.InvoiceService;
import com.yatang.sc.order.service.ItemPriceAdjService;
import com.yatang.sc.order.service.ItemPriceService;
import com.yatang.sc.order.service.OrderItemsService;
import com.yatang.sc.order.service.OrderPriceAdjService;
import com.yatang.sc.order.service.OrderPriceService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.service.PaymentGroupService;
import com.yatang.sc.order.service.PriceAdjustmentService;
import com.yatang.sc.order.service.ProviderShippingGroupService;
import com.yatang.sc.order.service.ShippingGroupService;
import com.yatang.sc.order.service.ShippingPriceAdjService;
import com.yatang.sc.order.service.ShippingPriceService;
import com.yatang.sc.order.split.SplitOrderItemDto;
import com.yatang.sc.order.split.SplitOrderRequestDto;
import com.yatang.sc.order.split.SplitOrderSubGroupDto;
import com.yatang.sc.order.split.enums.SplitOrderFromType;
import com.yatang.sc.order.split.enums.SubOrderType;
import com.yatang.sc.order.states.CommerceItemStatus;
import com.yatang.sc.order.states.OrderFrom;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.OrderTotalStates;
import com.yatang.sc.order.states.ShippingModes;
import com.yatang.sc.order.states.ShippingStates;
import com.yatang.sc.purchase.dto.CommerceItemDto;
import com.yatang.sc.purchase.dto.ItemPriceInfoDto;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.dto.PriceAdjustmentDto;
import com.yatang.sc.purchase.dto.ShippingGroupDto;
import com.yatang.sc.purchase.dubboservice.util.PricingUtil;
import com.yatang.sc.purchase.service.PricingEngineService;
import com.yatang.sc.purchase.service.ProdSellPriceInfoHelper;
import com.yatang.sc.purchase.service.PurchaseHelper;
import com.yatang.sc.purchase.service.PurchaseService;
import com.yatang.sc.purchase.service.SplitOrderHelper;
import com.yatang.sc.vo.UpdateOrderVO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("splitOrderHelper")
public class SplitOrderHelperImpl implements SplitOrderHelper {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentGroupService paymentGroupService;

    @Autowired
    private OrderPriceService orderPriceService;

    @Autowired
    private OrderPriceAdjService orderPriceAdjService;

    @Autowired
    private PriceAdjustmentService mPriceAdjustmentService;

    @Autowired
    private CommerceItemService commerceItemService;

    @Autowired
    private ItemPriceService mItemPriceService;

    @Autowired
    private ItemPriceAdjService mItemPriceAdjService;

    @Autowired
    private ShippingGroupService shippingGroupService;

    @Autowired
    private ShippingPriceService shippingPriceService;

    @Autowired
    private ShippingPriceAdjService mShippingPriceAdjService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private PurchaseHelper mPurchaseHelper;

    @Autowired
    PricingEngineService mPricingEngineService;

    @Autowired
    private ProdSellPriceInfoHelper mProdSellPriceInfoHelper;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    OrderItemsService orderItemsService;

    private String subOrderDelimiter = "";

    @Autowired
    private ProdPlaceQueryDubboService mProdPlaceQueryDubboService;

    @Autowired
    private ProviderShippingGroupService mProviderShippingGroupService;

    @Autowired
    private ProviderShippingGroupService providerShippingGroupService;

    @Override
    public String splitOrderByInventory(Order order, SplitOrderRequestDto groups) throws Exception {
        logger.info("{}->拆单分组信息:{}",order.getId(),JSON.toJSONString(groups));
        if (OrderStates.SPLITED_ORDER.equalsIgnoreCase(order.getOrderState())) {
            logger.error("订单已拆单:{}", order.getId());
            throw new Exception("订单已拆单");
        }
        boolean horizontalSplit = StringUtils.isNotEmpty(order.getCreatedByOrderId());
        logger.info("{}: 订单拆单方式:{}", order.getId(), horizontalSplit ? "水平拆单" : "垂直拆单");
        String enoughSubOrderId = "";
        List<Order> subOrders = orderService.getSubOrders(horizontalSplit ? order.getCreatedByOrderId() : order.getId());
        if (horizontalSplit) {
            logger.info("水平拆单：{}", order.getId());
            String lessSubOrderId = saveOrderInfo(order, groups.getGroupByType(SubOrderType.LESS_STOCK), subOrders, false, OrderFrom.INVENTORY_SPLIT);
            logger.info("{}订单拆分保存库存不足子订单:{}成功", order.getId(), lessSubOrderId);
            updateParentSplitOrder(order, groups.getGroupByType(SubOrderType.ENOUGH_STOCK), OrderFrom.INVENTORY_SPLIT);
            order.setShippingState(ShippingStates.PENDING_TRANSFER);
            enoughSubOrderId = order.getId();
            logger.info("{}订单拆分保存库存充足子订单:{}成功", order.getId(), enoughSubOrderId);
        } else {
            logger.info("垂直拆单：{}", order.getId());
            enoughSubOrderId = saveOrderInfo(order, groups.getGroupByType(SubOrderType.ENOUGH_STOCK), subOrders, true, OrderFrom.INVENTORY_SPLIT);
            logger.info("{}订单拆分保存库存充足子订单:{}成功", order.getId(), enoughSubOrderId);
            String lessSubOrderId = saveOrderInfo(order, groups.getGroupByType(SubOrderType.LESS_STOCK), subOrders, false,OrderFrom.INVENTORY_SPLIT);
            logger.info("{}订单拆分保存库存不足子订单:{}成功", order.getId(), lessSubOrderId);

            order.setOrderState(OrderStates.SPLITED_ORDER);
            order.setShippingState("");
            order.setPaymentState("");
            order.setState(OrderTotalStates.SPLIT_ORDER.getStateValue());
            order.setStateDetail(OrderTotalStates.SPLIT_ORDER.getDescription());
            order.setPendingSplit(false);
        }
        UpdateOrderVO updateOrderVO = new UpdateOrderVO();
        updateOrderVO.setOrder(order);
        updateOrderVO.setPreOrderState(order.getState());
        updateOrderVO.setDescription("部分商品库存不足，拆分订单");
        updateOrderVO.setOperator("系统");
        orderService.updateAndSaveLog(updateOrderVO);
        logger.info("更新主订单{}状态为“已拆单”", order.getId());
        return enoughSubOrderId;
    }

    @Override
    public String splitOrderIgnoreInventory(Order order) throws Exception {
        if (OrderStates.SPLITED_ORDER.equalsIgnoreCase(order.getOrderState())) {
            logger.error("订单已拆单:{}", order.getId());
            throw new Exception("订单已拆单");
        }

        List<Order> subOrders = orderService.getSubOrders(order.getId());

        List<CommerceItem> commerceItems = commerceItemService.getCommerceItemForOrderId(order.getId());
        ProdSellPriceInfoDto prodSellPriceInfoDto;
        Map<Long, CommerceItem> itemIdMaps = new HashMap<Long, CommerceItem>();
        SplitOrderSubGroupDto preSplitOrderSubGroupDto = SplitOrderSubGroupDto.newInstance(SubOrderType.YT_SHIPPING);
        SplitOrderSubGroupDto notPresplitOrderSubGroupDto = SplitOrderSubGroupDto.newInstance(SubOrderType.YT_SHIPPING);
        //1. split by sale model
        for (CommerceItem commerceItem : commerceItems) {
            itemIdMaps.put(commerceItem.getId(), commerceItem);
            prodSellPriceInfoDto = mProdSellPriceInfoHelper.getGoodsSellPrice(commerceItem.getProductId(), order.getBranchCompanyId());
            if (prodSellPriceInfoDto == null) {//默认先采后销
                logger.info("商品销售价格不存在:{}->{}", commerceItem.getProductId(), order.getBranchCompanyId());
                preSplitOrderSubGroupDto.getItems().add(new SplitOrderItemDto(commerceItem.getId(), commerceItem.getQuantity()));
                continue;
            }
            logger.info("商品销售模式:{}->{}", commerceItem.getProductId(), prodSellPriceInfoDto.getPreHarvestPinStatus());
            if (prodSellPriceInfoDto != null && prodSellPriceInfoDto.getPreHarvestPinStatus() == 1) {
                preSplitOrderSubGroupDto.getItems().add(new SplitOrderItemDto(commerceItem.getId(), commerceItem.getQuantity()));
            } else {
                notPresplitOrderSubGroupDto.getItems().add(new SplitOrderItemDto(commerceItem.getId(), commerceItem.getQuantity()));
            }
        }
        //1.1 save 先销后采商品
        //TODO 是否需要判断检查库存再传送订单到际联？？？？
        String splitOrderId = saveOrderInfo(order, notPresplitOrderSubGroupDto, subOrders, false, OrderFrom.DEFAULT);
        logger.info("{}订单拆分【先销后采商品】保存库存充足子订单:{}成功", order.getId(), splitOrderId);

        SplitOrderSubGroupDto giftSplitOrderSubGroupDto = SplitOrderSubGroupDto.newInstance(SubOrderType.YT_SHIPPING);
        SplitOrderSubGroupDto normalPresplitOrderSubGroupDto = SplitOrderSubGroupDto.newInstance(SubOrderType.YT_SHIPPING);
        //2 拆出赠品
        CommerceItem commerceItem;
        for (SplitOrderItemDto item : preSplitOrderSubGroupDto.getItems()) {
            commerceItem = itemIdMaps.get(item.getCommerceId());
            if (commerceItem == null) {
                logger.info("{}：CommerceItem:{}不存在", order.getId(), item.getCommerceId());
                continue;
            }
            if (true) {//TODO 如果是赠品
                giftSplitOrderSubGroupDto.getItems().add(new SplitOrderItemDto(commerceItem.getId(), commerceItem.getQuantity()));
            } else {
                normalPresplitOrderSubGroupDto.getItems().add(new SplitOrderItemDto(commerceItem.getId(), commerceItem.getQuantity()));
            }
        }
        //2.1 save 赠品
        //todo 赠品价格？？？是否单独传送到际联？？？
        splitOrderId = saveOrderInfo(order, giftSplitOrderSubGroupDto, subOrders, true,OrderFrom.DEFAULT);
        logger.info("{}订单拆分【赠品】保存库存充足子订单:{}成功", order.getId(), splitOrderId);

        //3 拆运输属性
        SplitOrderSubGroupDto coldSplitOrderSubGroupDto = SplitOrderSubGroupDto.newInstance(SubOrderType.YT_SHIPPING);
        SplitOrderSubGroupDto roomPresplitOrderSubGroupDto = SplitOrderSubGroupDto.newInstance(SubOrderType.YT_SHIPPING);
        for (SplitOrderItemDto item : normalPresplitOrderSubGroupDto.getItems()) {
            commerceItem = itemIdMaps.get(item.getCommerceId());
            if (commerceItem == null) {
                logger.info("{}：CommerceItem:{}不存在", order.getId(), item.getCommerceId());
                continue;
            }
            if (true) {//TODO 商品运输属性是冷藏运输
                coldSplitOrderSubGroupDto.getItems().add(new SplitOrderItemDto(commerceItem.getId(), commerceItem.getQuantity()));
            } else {
                roomPresplitOrderSubGroupDto.getItems().add(new SplitOrderItemDto(commerceItem.getId(), commerceItem.getQuantity()));
            }
        }
        return null;
    }

    protected String saveOrderInfo(Order order, SplitOrderSubGroupDto items, List<Order> subOrders, boolean enough, OrderFrom pOrderFrom) throws Exception {
        return saveOrderInfo(order, items, subOrders, enough, true,pOrderFrom);
    }

    protected String saveOrderInfo(Order order, SplitOrderSubGroupDto items, List<Order> subOrders, boolean enough, boolean saveInventoryStatus, OrderFrom pOrderFrom) throws Exception {

        OrderDto tempSubOrderDto = BeanConvertUtils.convert(order, OrderDto.class);
        String parentId = StringUtils.isEmpty(order.getCreatedByOrderId()) ? order.getId() : order.getCreatedByOrderId();
        logger.info("拆单订单：{} 父订单ID：{}", order.getId(), parentId);
        tempSubOrderDto.setCreatedByOrderId(parentId);
        tempSubOrderDto.setFrom(pOrderFrom.getValue());

        List<CommerceItemDto> commerceItemDtos = generateItems(items);
        tempSubOrderDto.setItems(commerceItemDtos);

        Double subOrderRawTotal = 0D;
        for (CommerceItemDto commerceItemDto : commerceItemDtos) {
            subOrderRawTotal += commerceItemDto.getItemPrice().getRawTotalPrice();
        }


        ShippingGroup parentShippingGroup = shippingGroupService.selectByPrimaryKey(order.getShippingGroup());
        if (parentShippingGroup == null) {
            logger.error("{}订单物流信息为空:{},拆单失败", order.getId(), order.getShippingGroup());
            throw new Exception("订单物流信息为空:" + order.getId());
        }
        ShippingGroupDto shippingGroupDto = generateShippingGroup(parentShippingGroup);
        tempSubOrderDto.setShippingGroupDto(shippingGroupDto);


        //计算子订单ID
        tempSubOrderDto.setId(getSubOrderId(parentId, subOrders));

        OrderPrice parentOrderPrice = orderPriceService.getOrderPriceForId(order.getPriceInfo());
        if (parentOrderPrice == null) {
            logger.error("父订单：{}的订单价格信息为空:{},订单拆分失败", order.getId(), parentOrderPrice.getId());
            throw new Exception("父订单的订单价格信息为空");
        }

        Double amountRatio = subOrderRawTotal / parentOrderPrice.getRawSubtotal();
        logger.info("{}:子订单金额:{}，父订单金额:{}，占比:{}", tempSubOrderDto.getId(), subOrderRawTotal, parentOrderPrice.getRawSubtotal(), amountRatio);

        OrderPrice orderPrice = BeanConvertUtils.convert(parentOrderPrice, OrderPrice.class);
        orderPrice.setAmount(PricingUtil.roundPrice(parentOrderPrice.getAmount() * amountRatio));
        orderPrice.setDiscountAmount(PricingUtil.roundPrice(parentOrderPrice.getDiscountAmount() * amountRatio));
        orderPrice.setRawSubtotal(PricingUtil.roundPrice(parentOrderPrice.getRawSubtotal() * amountRatio));
        orderPrice.setUserDiscountAmount(PricingUtil.roundPrice(parentOrderPrice.getUserDiscountAmount() * amountRatio));
        orderPrice.setCouponDiscountAmount(PricingUtil.roundPrice(parentOrderPrice.getCouponDiscountAmount() * amountRatio));
        orderPrice.setTotal(PricingUtil.roundPrice(parentOrderPrice.getTotal() * amountRatio));
        orderPrice.setManualAdjustmentTotal(PricingUtil.roundPrice(parentOrderPrice.getManualAdjustmentTotal() * amountRatio));
        orderPrice.setShipping(PricingUtil.roundPrice(parentOrderPrice.getShipping() * amountRatio));
        mPurchaseHelper.saveOrderPrice(orderPrice);

        List<OrderPriceAdj> adjustments = orderPriceAdjService.selectByOrderPriceId(parentOrderPrice.getId());
        PriceAdjustment parentPriceAjustment;
        for (int i = 0; i < adjustments.size(); i++) {
            /**
             * 金额价格明细关联，价格明细
             */
            parentPriceAjustment = mPriceAdjustmentService.selectByPrimaryKey(adjustments.get(i).getAdjustmentId());
            if (parentPriceAjustment == null) {
                logger.error("PriceAjustment is null:{}", JSON.toJSONString(adjustments.get(i)));
                continue;
            }
            PriceAdjustment priceAdjustment = BeanConvertUtils.convert(parentPriceAjustment, PriceAdjustment.class);
            priceAdjustment.setAdjustment(PricingUtil.roundPrice(parentPriceAjustment.getAdjustment() * amountRatio));
            priceAdjustment.setTotalAdjustment(PricingUtil.roundPrice(parentPriceAjustment.getTotalAdjustment() * amountRatio));
            priceAdjustment.setAdjustment(PricingUtil.roundPrice(parentPriceAjustment.getAdjustment() * amountRatio));

            mPurchaseHelper.savePriceAdjustment(priceAdjustment);
            mPurchaseHelper.saveOrderPriceAdj(orderPrice.getId(), priceAdjustment.getId(), Byte.valueOf(i + ""));
        }

        logger.info("拆单--》 保存子订单:{} 价格完成:{}", tempSubOrderDto.getId(), orderPrice.getId());

        ShippingPrice parentShippingPrice = shippingPriceService.selectByPrimaryKey(parentShippingGroup.getShippingPriceInfo());
        ShippingPrice shippingPrice = BeanConvertUtils.convert(parentShippingPrice, ShippingPrice.class);
        if (parentShippingPrice == null) {
            logger.info("{}:父订单物流价格信息不存在:{}->{}", order.getId(), parentShippingGroup.getId(), parentShippingGroup.getShippingPriceInfo());
            throw new Exception("父订单物流价格信息不存在");
        }
        shippingPrice.setAmount(PricingUtil.roundPrice(parentShippingPrice.getAmount() * amountRatio));
        shippingPrice.setRawShipping(PricingUtil.roundPrice(parentShippingPrice.getRawShipping() * amountRatio));
        mPurchaseHelper.saveShippingPrice(shippingPrice);

        ShippingGroup shippingGroup = BeanConvertUtils.convert(tempSubOrderDto.getShippingGroupDto(), ShippingGroup.class);
        shippingGroup.setShippingPriceInfo(shippingPrice.getId());
        boolean isProviderSplit = false;
        switch (items.getSubOrderType()) {
            case PROVIDER_SHIPPING: {//拆单直供订单
                isProviderSplit = true;
                if (ShippingStates.PENDING_PROCESS.equals(tempSubOrderDto.getShippingState())) {
                    shippingGroup.setShippingModes(ShippingModes.PROVIDER_SHIPPING_MODE.getCode());
                    tempSubOrderDto.setShippingState(ShippingStates.PENDING_TRANSFER);
                }
                break;
            }
            case PROVIDER_SHIPPING_ASN: {//已有ASN直供订单拆单
                isProviderSplit = true;
                tempSubOrderDto.setShippingState(ShippingStates.PENDING_RECEIVE);
                break;
            }
            case LESS_STOCK:
            case YT_SHIPPING:
            case ENOUGH_STOCK:
            default: {
                shippingGroup.setShippingModes(ShippingModes.UNIFIED_SHIPPING_MODE.getCode());
            }
        }


        mPurchaseHelper.saveShippingGroup(shippingGroup);
        if (isProviderSplit) {
            saveProviderShippingGroup(order, items, shippingGroup, parentShippingGroup.getShippingModes());
        }
        logger.info("拆单--》 保存子订单：{}物流信息完成:{}", tempSubOrderDto.getId(), shippingGroup.getId());
        List<ShippingPriceAdj> spadjustments = mShippingPriceAdjService.selectByShippingPriceId(parentShippingPrice.getId());
        PriceAdjustment parentShippingPriceAdjustment;
        for (int k = 0; k < spadjustments.size(); k++) {
            parentShippingPriceAdjustment = mPriceAdjustmentService.selectByPrimaryKey(spadjustments.get(k).getAdjustmentId());
            if (parentShippingPriceAdjustment == null) {
                logger.error("PriceAjustment is null:{}", JSON.toJSONString(adjustments.get(k)));
                continue;
            }
            /**
             * 配送价格明细
             */
            PriceAdjustment shippingPriceAdjustment = BeanConvertUtils.convert(parentShippingPriceAdjustment, PriceAdjustment.class);
            shippingPriceAdjustment.setTotalAdjustment(PricingUtil.roundPrice(parentShippingPriceAdjustment.getTotalAdjustment() * amountRatio));
            shippingPriceAdjustment.setAdjustment(PricingUtil.roundPrice(parentShippingPriceAdjustment.getAdjustment() * amountRatio));
            mPurchaseHelper.savePriceAdjustment(shippingPriceAdjustment);
            /**
             * 配送价格明细关联
             */
            mPurchaseHelper.saveShippingPriceAdj(shippingPrice.getId(), shippingPriceAdjustment.getId(), new Byte(k + ""));
        }
        logger.info("拆单--》 保存子订单：{}物流运费信息完成:{}", tempSubOrderDto.getId(), shippingPrice.getId());

        Invoice invoice = invoiceService.getInvoiceForId(order.getInvoiceInfo());
        if (invoice == null) {
            logger.warn("{}订单发票信息为空:{}", order.getId(), order.getInvoiceInfo());
        } else {
            Invoice tempInvoice = BeanConvertUtils.convert(invoice, Invoice.class);
            mPurchaseHelper.saveInvoice(tempInvoice);
            logger.info("拆单--》保存发票信息成功：{}", tempInvoice.getInvoiceId());
        }

        Order tmpSubOrder = BeanConvertUtils.convert(tempSubOrderDto, Order.class);
        tmpSubOrder.setPriceInfo(orderPrice.getId());
        tmpSubOrder.setInvoiceInfo(invoice.getInvoiceId());
        tmpSubOrder.setShippingGroup(shippingGroup.getId());
        if (saveInventoryStatus) {
            if (!enough) {
                tmpSubOrder.setShippingState(ShippingStates.NO_INVENTORY);
            } else {
                tmpSubOrder.setShippingState(ShippingStates.PENDING_TRANSFER);
            }
        }

        mPurchaseHelper.saveOrder(tmpSubOrder, false);
        logger.info("拆单--》保存订单完成：{}", tmpSubOrder.getId());

        /**
         * 订单商品关联，订单商品价格，商品价格明细关联，价格明细
         */
        CommerceItemDto commerceItemDto;
        CommerceItem commerceItem;
        ItemPrice itemPrice;
        Integer unitQuantity;
        for (int j = 0; j < commerceItemDtos.size(); j++) {
            commerceItemDto = commerceItemDtos.get(j);
            logger.info("Dto:{}", JSON.toJSONString(commerceItemDto));
            logger.info("拆单--》commerceItem：{}，是否选中：{}", commerceItemDto.getId(), commerceItemDto.isSelected());
            if (commerceItemDto.isSelected()) {
                commerceItem = BeanConvertUtils.convert(commerceItemDto, CommerceItem.class);
                unitQuantity = commerceItem.getUnitQuantity();
                if (unitQuantity == null) {
                    unitQuantity = 1;
                }
                commerceItem.setSaleQuantity(commerceItem.getQuantity() / unitQuantity);
                if (saveInventoryStatus) {
                    if (enough) {
                        commerceItem.setState(CommerceItemStatus.STOCK_ENOUGH.getStateValue());
                        commerceItem.setStateDetail(CommerceItemStatus.STOCK_ENOUGH.getDescription());
                    } else {
                        commerceItem.setState(CommerceItemStatus.STOCK_NOT_ENOUGH.getStateValue());
                        commerceItem.setStateDetail(CommerceItemStatus.STOCK_NOT_ENOUGH.getDescription());
                    }
                }
                if(SubOrderType.PROVIDER_SHIPPING_ASN.equals(items.getSubOrderType())){
                    commerceItem.setShippedQuantity(commerceItemDto.getQuantity());
                }
                itemPrice = BeanConvertUtils.convert(commerceItemDto.getItemPrice(), ItemPrice.class);
                if (null == itemPrice) {
                    logger.error("{}:CommerceItem price is null:{}", commerceItem.getId(), commerceItem.getItemPriceInfo());
                }
                commerceItem.setItemPrice(itemPrice);
                itemPrice.setAdjustments(BeanConvertUtils.convertList(commerceItemDto.getItemPrice().getAdjustments(), PriceAdjustment.class));
                logger.info("Save:{}", JSON.toJSONString(commerceItem));
                mPurchaseHelper.saveCommerceItem(commerceItem, commerceItemDto.getItemPrice());

                OrderItems orderItems = new OrderItems();
                orderItems.setOrderId(tmpSubOrder.getId());
                orderItems.setCommerceItemId(commerceItem.getId());
                mPurchaseHelper.saveOrderItems(orderItems);
            }
        }

        /**
         * 订单支付
         */
        List<PaymentGroup> parentPaymentGroups = paymentGroupService.getPaymentGroupForOrderId(order.getId());
        PaymentGroup tmpPaymentGroup;
        for (PaymentGroup paymentGroup : parentPaymentGroups) {//TODO 最终拆单完成过后可能存在金额差异
            tmpPaymentGroup = BeanConvertUtils.convert(paymentGroup, PaymentGroup.class);
            tmpPaymentGroup.setAmountCredited(PricingUtil.roundPrice(paymentGroup.getAmountCredited() * amountRatio));
            tmpPaymentGroup.setAmount(PricingUtil.roundPrice(paymentGroup.getAmount() * amountRatio));
            tmpPaymentGroup.setAmountAuthorized(PricingUtil.roundPrice(paymentGroup.getAmountAuthorized() * amountRatio));
            tmpPaymentGroup.setAmountDebited(PricingUtil.roundPrice(paymentGroup.getAmountDebited() * amountRatio));
            tmpPaymentGroup.setOrderId(tmpSubOrder.getId());

            mPurchaseHelper.savePaymentGroup(tmpPaymentGroup);
            mPurchaseHelper.saveOrderPayments(tmpPaymentGroup.getId(), tmpSubOrder.getId());
            logger.info("拆单--》保存订单{}支付记录：{}成功", tmpSubOrder.getId(), tmpPaymentGroup.getId());
        }

        subOrders.add(tmpSubOrder);
        logger.info("拆单--》当前订单支付状态:{}", order.getPaymentState());
        return tmpSubOrder.getId();
    }

    /**
     * 保存直配订单扩展物流信息
     * @param orderSubGroup
     * @param pShippingGroup
     */
    public void saveProviderShippingGroup(Order parentOrder, SplitOrderSubGroupDto orderSubGroup, ShippingGroup pShippingGroup, String parentShippingModes) throws Exception {
        if (ShippingModes.PROVIDER_SHIPPING_MODE.getCode().equals(parentShippingModes)) {
            ProviderShippingGroup providerShippingGroup = providerShippingGroupService.selectByShippingGroupId(parentOrder.getShippingGroup());
            if (providerShippingGroup != null) {//父订单是直送订单，直接复制，如果不存在，则查询商品地点关系
                providerShippingGroup.setShippingGroupId(pShippingGroup.getId());
                providerShippingGroupService.insert(providerShippingGroup);
                logger.info("保存直配订单扩展物流信息成功：{}", pShippingGroup.getId());
                return;
            }
        }

        addProviderShippingGroup(orderSubGroup.getProdPlaceId(), pShippingGroup);
        logger.info("保存直配订单扩展物流信息成功：{}", pShippingGroup.getId());
    }

    public void addProviderShippingGroup(String prodPlaceId,  ShippingGroup pShippingGroup) throws Exception {
        Response<ProdPlaceDto> prodPlaceResponse = mProdPlaceQueryDubboService.queryDetail(prodPlaceId);
        if (prodPlaceResponse == null || !prodPlaceResponse.isSuccess()) {
            logger.error("商品地点服务调用失败{}", JSON.toJSONString(prodPlaceResponse));
            throw new Exception("商品地点服务调用失败");
        }
        ProviderShippingGroup providerShippingGroup = new ProviderShippingGroup();
        providerShippingGroup.setShippingGroupId(pShippingGroup.getId());
        providerShippingGroup.setSpAdrId(prodPlaceResponse.getResultObject().getAdrSupId());
        providerShippingGroup.setSpAdrName(prodPlaceResponse.getResultObject().getAdrSupName());
        providerShippingGroup.setSpAdrNo(prodPlaceResponse.getResultObject().getAdrSupCode());
        providerShippingGroup.setSpId(prodPlaceResponse.getResultObject().getSupplierId());
        providerShippingGroup.setSpName(prodPlaceResponse.getResultObject().getSupplierName());
        providerShippingGroup.setSpNo(prodPlaceResponse.getResultObject().getSupplierCode());
        mProviderShippingGroupService.insert(providerShippingGroup);
    }

    /**
     * 水平拆单时，更新被拆订单数据
     *
     * @param order
     * @param pSplitOrderSubGroupDto
     */
    protected void updateParentSplitOrder(Order order, SplitOrderSubGroupDto pSplitOrderSubGroupDto, OrderFrom pOrderFrom) throws Exception {
        List<CommerceItem> items = commerceItemService.getCommerceItemForOrderId(order.getId());
        Double orderRawTotal = 0.0;
        List<CommerceItem> zeroItems = new ArrayList<>();
        List<CommerceItem> updatedItems = new ArrayList<>();
        SplitOrderSubGroupDto updatedSplitGroup = new SplitOrderSubGroupDto(SubOrderType.YT_SHIPPING);
        Map<String, CommerceItem> commerceItemMap = new HashMap<>();
        for (CommerceItem item : items) {
            if (item == null) {
                continue;
            }
            orderRawTotal += item.getItemPrice().getRawTotalPrice();
            commerceItemMap.put(String.valueOf(item.getId()), item);

            SplitOrderItemDto findItem = null;
            for (SplitOrderItemDto splitOrderItemDto : pSplitOrderSubGroupDto.getItems()) {
                if (splitOrderItemDto.getCommerceId().equals(item.getId())) {//查找拆单分组中的commerceItem
                    findItem = splitOrderItemDto;
                    break;
                }
            }
            if (findItem == null) {//未找到commerceItem,这commerceItem已被被分组到其他子订单
                zeroItems.add(item);
            } else {
                updatedItems.add(item);
                updatedSplitGroup.getItems().add(findItem);
            }
        }

        List<CommerceItemDto> commerceItemDtos = generateItems(updatedSplitGroup);
        CommerceItem commerceItem;
        ItemPrice itemPrice;
        Double subOrderRawTotal = 0.0;
        for (CommerceItemDto commerceItemDto : commerceItemDtos) {
            commerceItem = commerceItemMap.get(commerceItemDto.getId());
            if (commerceItem == null) {
                logger.warn("未找到commerceItem:{}", commerceItemDto.getId());
                continue;
            }
            itemPrice = BeanConvertUtils.convert(commerceItemDto.getItemPrice(), ItemPrice.class);
            if (null == itemPrice) {
                logger.error("{}:CommerceItem price is null:{}", commerceItemDto.getId(), commerceItemDto.getItemPriceInfo());
            }
            subOrderRawTotal += itemPrice.getRawTotalPrice();
            commerceItem.setItemPrice(itemPrice);
            commerceItem.setQuantity(commerceItemDto.getQuantity());
            for (PriceAdjustmentDto adj : commerceItemDto.getItemPrice().getAdjustments()) {
                if (adj == null) {
                    continue;
                }
                PriceAdjustment priceAdjustment = BeanConvertUtils.convert(adj, PriceAdjustment.class);
                mPriceAdjustmentService.updateByPrimaryKeySelective(priceAdjustment);
            }
            mItemPriceService.updateByPrimaryKeySelective(itemPrice);
            commerceItemService.updateCommerceItem(commerceItem);
        }

        List<OrderItems> orderItems = orderItemsService.getOrderItemsForOrderId(order.getId());
        Map<Long,OrderItems> orderItemsMap = new HashMap<>();
        for (OrderItems orderItem : orderItems) {
            orderItemsMap.put(orderItem.getCommerceItemId(),orderItem);
        }

        for (CommerceItem zeroItem : zeroItems) {
            if (zeroItem == null) {
                continue;
            }
            itemPrice = mItemPriceService.getItemPriceForId(zeroItem.getItemPriceInfo());
            if (itemPrice == null) {
                logger.error("generateItems->{}:CommerceItem price is null:{}", zeroItem.getId(), zeroItem.getItemPriceInfo());
                continue;
            }
            itemPrice.setAmount(0D);
            itemPrice.setRawTotalPrice(0D);
            mItemPriceService.updateByPrimaryKeySelective(itemPrice);
            zeroItem.setQuantity(0L);
            zeroItem.setShippedQuantity(0L);
            zeroItem.setUnitQuantity(0);
            commerceItemService.updateCommerceItem(zeroItem);
            OrderItems orderItem = orderItemsMap.get(zeroItem.getId());
            if (orderItem != null) {
                orderItem.setDelete(true);
                orderItemsService.deleteByPrimaryKey(orderItem.getId());
            }
        }

        double amountRatio = subOrderRawTotal / orderRawTotal;
        updateShippingInfoForSplitOrder(order, amountRatio);
        updateOrderPriceForSplitOrder(order, amountRatio);

        /**
         * 订单支付
         */
        List<PaymentGroup> parentPaymentGroups = paymentGroupService.getPaymentGroupForOrderId(order.getId());
        for (PaymentGroup paymentGroup : parentPaymentGroups) {//TODO 最终拆单完成过后可能存在金额差异
            paymentGroup.setAmountCredited(PricingUtil.roundPrice(paymentGroup.getAmountCredited() * amountRatio));
            paymentGroup.setAmount(PricingUtil.roundPrice(paymentGroup.getAmount() * amountRatio));
            paymentGroup.setAmountAuthorized(PricingUtil.roundPrice(paymentGroup.getAmountAuthorized() * amountRatio));
            paymentGroup.setAmountDebited(PricingUtil.roundPrice(paymentGroup.getAmountDebited() * amountRatio));

            paymentGroupService.updateByPrimaryKeySelective(paymentGroup);
            logger.info("拆单--》更新订单{}支付记录：{}成功", order.getId(), paymentGroup.getId());
        }

        order.setFrom(pOrderFrom.getValue());
    }

    private void updateOrderPriceForSplitOrder(Order order, double pAmountRatio) throws Exception {
        OrderPrice orderPrice = orderPriceService.getOrderPriceForId(order.getPriceInfo());
        if (orderPrice == null) {
            logger.error("父订单：{}的订单价格信息为空:{},订单拆分失败", order.getId(), orderPrice.getId());
            throw new Exception("父订单的订单价格信息为空");
        }


        orderPrice.setAmount(PricingUtil.roundPrice(orderPrice.getAmount() * pAmountRatio));
        orderPrice.setDiscountAmount(PricingUtil.roundPrice(orderPrice.getDiscountAmount() * pAmountRatio));
        orderPrice.setUserDiscountAmount(PricingUtil.roundPrice(orderPrice.getUserDiscountAmount() * pAmountRatio));
        orderPrice.setCouponDiscountAmount(PricingUtil.roundPrice(orderPrice.getCouponDiscountAmount() * pAmountRatio));
        orderPrice.setRawSubtotal(PricingUtil.roundPrice(orderPrice.getRawSubtotal() * pAmountRatio));
        orderPrice.setTotal(PricingUtil.roundPrice(orderPrice.getTotal() * pAmountRatio));
        orderPrice.setManualAdjustmentTotal(PricingUtil.roundPrice(orderPrice.getManualAdjustmentTotal() * pAmountRatio));
        orderPrice.setShipping(PricingUtil.roundPrice(orderPrice.getShipping() * pAmountRatio));
        orderPriceService.updateByPrimaryKeySelective(orderPrice);
        logger.info("保存订单价格信息完成:{}", order.getId());


        List<OrderPriceAdj> adjustments = orderPriceAdjService.selectByOrderPriceId(orderPrice.getId());
        PriceAdjustment priceAdjustment;
        for (int i = 0; i < adjustments.size(); i++) {

            //金额价格明细关联，价格明细
            priceAdjustment = mPriceAdjustmentService.selectByPrimaryKey(adjustments.get(i).getAdjustmentId());
            if (priceAdjustment == null) {
                logger.error("PriceAjustment is null:{}", JSON.toJSONString(adjustments.get(i)));
                continue;
            }
            priceAdjustment.setAdjustment(PricingUtil.roundPrice(priceAdjustment.getAdjustment() * pAmountRatio));
            priceAdjustment.setTotalAdjustment(PricingUtil.roundPrice(priceAdjustment.getTotalAdjustment() * pAmountRatio));
            priceAdjustment.setAdjustment(PricingUtil.roundPrice(priceAdjustment.getAdjustment() * pAmountRatio));
            mPriceAdjustmentService.updateByPrimaryKeySelective(priceAdjustment);
        }

        logger.info("保存订单调价信息完成:{}", order.getId());
    }

    private void updateShippingInfoForSplitOrder(Order order, double amountRatio) throws Exception {
        ShippingGroup shippingGroup = shippingGroupService.selectByPrimaryKey(order.getShippingGroup());
        if (shippingGroup == null) {
            logger.error("{}订单物流信息为空:{},拆单失败", order.getId(), order.getShippingGroup());
            throw new Exception("订单物流信息为空:" + order.getId());
        }

        ShippingPrice shippingPrice = shippingPriceService.selectByPrimaryKey(shippingGroup.getShippingPriceInfo());
        if (shippingPrice == null) {
            logger.info("{}:父订单物流价格信息不存在:{}->{}", order.getId(), shippingPrice.getId(), shippingGroup.getShippingPriceInfo());
            throw new Exception("父订单物流价格信息不存在");
        }

        shippingPrice.setAmount(PricingUtil.roundPrice(shippingPrice.getAmount() * amountRatio));
        shippingPrice.setRawShipping(PricingUtil.roundPrice(shippingPrice.getRawShipping() * amountRatio));
        shippingPriceService.updateByPrimaryKeySelective(shippingPrice);
        logger.debug("updateParentSplitOrder->物流信息更新成功");

        List<ShippingPriceAdj> spadjustments = mShippingPriceAdjService.selectByShippingPriceId(shippingPrice.getId());
        PriceAdjustment shippingPriceAdjustment;
        for (int k = 0; k < spadjustments.size(); k++) {
            shippingPriceAdjustment = mPriceAdjustmentService.selectByPrimaryKey(spadjustments.get(k).getAdjustmentId());
            if (shippingPriceAdjustment == null) {
                logger.error("PriceAjustment is null:{}", JSON.toJSONString(spadjustments.get(k)));
                continue;
            }

            //配送价格明细
            shippingPriceAdjustment.setTotalAdjustment(PricingUtil.roundPrice(shippingPriceAdjustment.getTotalAdjustment() * amountRatio));
            shippingPriceAdjustment.setAdjustment(PricingUtil.roundPrice(shippingPriceAdjustment.getAdjustment() * amountRatio));
            mPriceAdjustmentService.updateByPrimaryKeySelective(shippingPriceAdjustment);
        }
        logger.info("{}-》物流调价信息保存完成", shippingGroup.getId());
    }

    @Override
    public String getSubOrderId(String mainOrderId, List<Order> subOrders) {
        if (StringUtils.isEmpty(mainOrderId)) {
            return null;
        }

        List<String> subOrderIds = new ArrayList<String>();
        for (Order order : subOrders) {
            subOrderIds.add(order.getId());
        }
        int subOrderIdSeq = 1;
        if (CollectionUtils.isNotEmpty(subOrders)) {
            subOrderIdSeq = subOrders.size() + 1;
        }
        String newSubOrderId;
        while (true) {
            if (subOrderIdSeq < 10) {
                newSubOrderId = mainOrderId + subOrderDelimiter + "0" + subOrderIdSeq;
            } else {
                newSubOrderId = mainOrderId + subOrderDelimiter + subOrderIdSeq;
            }
            if (!subOrderIds.contains(newSubOrderId)) {
                break;
            }
            subOrderIdSeq += 1;

        }
        return newSubOrderId;
    }

    @Override
    public List<String> manualSplitOrderByInventory(Order order, SplitOrderRequestDto groups, OrderFrom pOrderFrom) throws Exception {
        logger.info("开始手动拆单：{}->{}", order.getId(), JSON.toJSONString(groups));
        if (OrderStates.SPLITED_ORDER.equalsIgnoreCase(order.getOrderState())) {
            logger.error("订单已拆单:{}", order.getId());
            throw new Exception("订单已拆单");
        }

        boolean horizontalSplit = StringUtils.isNotEmpty(order.getCreatedByOrderId());

        String parentOrderId = order.getId();
        if (horizontalSplit) {
            parentOrderId = order.getCreatedByOrderId();
        }
        logger.info("{}:父订单ID：{}", order.getId(), parentOrderId);

        List<Order> subOrders = orderService.getSubOrders(parentOrderId);
        List<String> subOrderIds = new ArrayList<String>();
        String subOrderId;
        logger.info("{}: 订单拆单方式:{}", order.getId(), horizontalSplit ? "水平拆单" : "垂直拆单");
        int groupSize = groups.getGroups().size();
        for (int index = groupSize - 1; index >= 0; index--) {
            if (horizontalSplit && index == 0) {//NOTE:订单商品行需保证所有被分组，否则水平拆单中会出现拆单后商品行缺失
                updateParentSplitOrder(order, groups.getGroups().get(0), pOrderFrom);
                continue;
            }

            subOrderId = saveOrderInfo(order, groups.getGroups().get(index), subOrders, false, false, pOrderFrom);
            subOrderIds.add(subOrderId);
        }
        logger.info("手动拆单完成：{}", order.getId(), JSON.toJSONString(groups));
        return subOrderIds;
    }

    @Override
    public SplitOrderRequestDto genSplitOrderRequestByShippingModes(Order mainOrder) throws Exception {
        logger.info("根据配送模式拆单:{}", mainOrder.getId());
        if (mainOrder == null) {
            throw new IllegalArgumentException("订单为空");
        }

        SplitOrderRequestDto splitOrderRequestDto = new SplitOrderRequestDto();
        splitOrderRequestDto.setFromType(SplitOrderFromType.SPLIT_FROM_SHIPPING_MODELS);
        splitOrderRequestDto.setParentOrderId(mainOrder.getId());

        SplitOrderSubGroupDto ytShippingMethodGroup = SplitOrderSubGroupDto.newInstance(SubOrderType.YT_SHIPPING);

        List<CommerceItem> commerceItems = commerceItemService.getCommerceItemForOrderId(mainOrder.getId());
        if (CollectionUtils.isEmpty(commerceItems)) {
            logger.warn("订单上不存在商品信息:{}", mainOrder.getId());
            throw new Exception("订单上不存在商品信息");
        }
        List<String> products = new ArrayList<>();
        for (CommerceItem item : commerceItems) {
            products.add(item.getProductId());
        }
        Response<List<ProdPlaceDto>> prodPlaceResponse = mProdPlaceQueryDubboService.queryDirectDeliveryProduct(mainOrder.getFranchiseeStoreId(), products);
        if (prodPlaceResponse == null || !prodPlaceResponse.isSuccess()) {
            logger.error("{}-》{}商品地点服务调用失败{}", mainOrder.getFranchiseeStoreId(), products, JSON.toJSONString(prodPlaceResponse));
            throw new Exception("商品地点服务调用失败");
        }

        Map<String, SplitOrderSubGroupDto> providerSplitMap = new HashMap<>();
        for (CommerceItem item : commerceItems) {
            ProdPlaceDto prodPlaceDto = getProviderProdPlace(item, prodPlaceResponse.getResultObject());
            if (prodPlaceDto == null) {
                ytShippingMethodGroup.getItems().add(new SplitOrderItemDto(item.getId(), item.getQuantity()));
            } else {
                if (!providerSplitMap.keySet().contains(prodPlaceDto.getAdrSupId())) {
                    providerSplitMap.put(prodPlaceDto.getAdrSupId(), SplitOrderSubGroupDto.newInstance(SubOrderType.PROVIDER_SHIPPING, prodPlaceDto.getId()));
                }
                providerSplitMap.get(prodPlaceDto.getAdrSupId()).getItems().add(new SplitOrderItemDto(item.getId(), item.getQuantity()));
            }
        }
        if (CollectionUtils.isNotEmpty(ytShippingMethodGroup.getItems())) {
            splitOrderRequestDto.getGroups().add(ytShippingMethodGroup);
        }
        if (providerSplitMap.size() > 0) {
            for (SplitOrderSubGroupDto splitOrderSubGroup : providerSplitMap.values()) {
                if (CollectionUtils.isNotEmpty(splitOrderSubGroup.getItems())) {
                    splitOrderRequestDto.getGroups().add(splitOrderSubGroup);
                }
            }
        }
        return splitOrderRequestDto;
    }

    @Override
    public String splitOrderByASN(Order order, SplitOrderRequestDto groups) throws Exception {
        logger.info("直配订单ASN拆单：{}->{}", order.getId(), JSON.toJSONString(groups));
        if (OrderStates.SPLITED_ORDER.equalsIgnoreCase(order.getOrderState())) {
            logger.error("订单已拆单:{}", order.getId());
            throw new Exception("订单已拆单");
        }

        boolean horizontalSplit = StringUtils.isNotEmpty(order.getCreatedByOrderId());

        String parentOrderId = order.getId();
        if (horizontalSplit) {
            parentOrderId = order.getCreatedByOrderId();
        }
        List<Order> subOrders = orderService.getSubOrders(parentOrderId);
        SplitOrderSubGroupDto asnGroup = groups.getGroupByType(SubOrderType.PROVIDER_SHIPPING_ASN);
        SplitOrderSubGroupDto group = groups.getGroupByType(SubOrderType.PROVIDER_SHIPPING);
        String asnOrderId = saveOrderInfo(order, asnGroup, subOrders, false, false, OrderFrom.ASN_SPLIT);
        if (horizontalSplit) {
            updateParentSplitOrder(order, group,OrderFrom.ASN_SPLIT);
            logger.info("{}:水平拆单，已更新数量", order.getId());
        } else {
            String subOrderId = saveOrderInfo(order, group, subOrders, false, false, OrderFrom.ASN_SPLIT);
            logger.info("{}:已拆出子订单:{}", order.getId(), subOrderId);
        }
        logger.info("{}:已拆出子订单:{}", order.getId(), asnOrderId);
        logger.info("直配订单拆单ASN完成：{}", order.getId(), JSON.toJSONString(groups));
        return asnOrderId;
    }

    protected ProdPlaceDto getProviderProdPlace(CommerceItem item, List<ProdPlaceDto> pProdPlaceDtos) {
        if (CollectionUtils.isEmpty(pProdPlaceDtos)) {
            return null;
        }
        for (ProdPlaceDto prodPlaceDto : pProdPlaceDtos) {
            if (prodPlaceDto.getProductId().equals(item.getProductId()) && 0 == prodPlaceDto.getLogisticsModel()) {//送货类型：0:直送，1:配送
                return prodPlaceDto;
            }
        }
        return null;
    }

    protected List<CommerceItemDto> generateItems(SplitOrderSubGroupDto items) {
        List<CommerceItemDto> commerceItemDtos = new ArrayList<CommerceItemDto>(items.getItems().size());
        CommerceItem commerceItem;
        CommerceItemDto commerceItemDto;
        Double itemAmountRatio = 0D;
        ItemPriceInfoDto itemPriceInfoDto;
        ItemPrice itemPrice;
        PriceAdjustment priceAdjustment;
        PriceAdjustmentDto priceAdjustmentDto;
        for (SplitOrderItemDto item : items.getItems()) {
            commerceItem = commerceItemService.getCommerceItemForId(item.getCommerceId());
            logger.info("CommerceItem:{}",JSON.toJSONString(commerceItem));
            if (commerceItem == null) {
                logger.info("CommerceItem 不存在：{}", item.getCommerceId());
                continue;
            }
            itemPrice = mItemPriceService.getItemPriceForId(commerceItem.getItemPriceInfo());
            if (itemPrice == null) {
                logger.error("generateItems->{}:CommerceItem price is null:{}", commerceItem.getId(), commerceItem.getItemPriceInfo());
                continue;
            }
            itemAmountRatio = Double.valueOf(item.getQuantity()) / Double.valueOf(commerceItem.getQuantity());
            commerceItemDto = BeanConvertUtils.convert(commerceItem, CommerceItemDto.class);
            commerceItemDto.setQuantity(item.getQuantity());
            if (Double.isInfinite(itemAmountRatio)) {
                logger.info("Infinity ratio:{}:{}->{}:{}", item.getCommerceId(), item.getQuantity(), commerceItem.getId(), commerceItem.getQuantity());
            }
            logger.info("{}:comerceImte ratio:{}", commerceItem.getId(), itemAmountRatio);
            itemPriceInfoDto = BeanConvertUtils.convert(itemPrice, ItemPriceInfoDto.class);

            itemPriceInfoDto.setRawTotalPrice(PricingUtil.roundPrice(itemAmountRatio * itemPrice.getRawTotalPrice()));
            logger.info("{}:newComerceImte rawTotalPrice:{}", commerceItem.getId(), itemPriceInfoDto.getRawTotalPrice());
            itemPriceInfoDto.setAmount(PricingUtil.roundPrice(itemAmountRatio * itemPrice.getAmount()));
            logger.info("{}:newComerceImte amount:{}", commerceItem.getId(), itemPriceInfoDto.getAmount());

            itemPriceInfoDto.setAdjustments(new ArrayList<PriceAdjustmentDto>());
            List<ItemPriceAdj> itemPriceAdjs = mItemPriceAdjService.selectByItemPriceId(commerceItem.getItemPriceInfo());
            for (ItemPriceAdj itemPriceAdj : itemPriceAdjs) {
                priceAdjustment = mPriceAdjustmentService.selectByPrimaryKey(itemPriceAdj.getAdjustmentId());
                if (priceAdjustment == null) {
                    continue;
                }
                priceAdjustmentDto = BeanConvertUtils.convert(priceAdjustment, PriceAdjustmentDto.class);
                priceAdjustmentDto.setTotalAdjustment(PricingUtil.roundPrice(itemAmountRatio * itemPrice.getAmount()));
                itemPriceInfoDto.getAdjustments().add(priceAdjustmentDto);
            }

            commerceItemDto.setItemPrice(itemPriceInfoDto);
            commerceItemDtos.add(commerceItemDto);
        }
        return commerceItemDtos;
    }

    protected ShippingGroupDto generateShippingGroup(ShippingGroup pParentShippingGroup) {
        //TODO 运费分摊,目前
        return BeanConvertUtils.convert(pParentShippingGroup, ShippingGroupDto.class);
    }

}
