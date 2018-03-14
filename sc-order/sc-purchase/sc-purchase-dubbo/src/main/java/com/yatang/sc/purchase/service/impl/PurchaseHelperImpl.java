package com.yatang.sc.purchase.service.impl;

import com.alibaba.fastjson.JSON;
import com.busi.common.resp.Response;
import com.busi.common.utils.BeanConvertUtils;
import com.yatang.sc.facade.dto.LogicWarehouseDto;
import com.yatang.sc.inventory.dto.AreawarehouseDto;
import com.yatang.sc.inventory.dubboservice.AreaWarehouseQueryDubboService;
import com.yatang.sc.order.domain.*;
import com.yatang.sc.order.service.*;
import com.yatang.sc.order.service.ShippingPriceAdjService;
import com.yatang.sc.order.states.*;
import com.yatang.sc.product.service.ProductHelper;
import com.yatang.sc.purchase.dto.*;
import com.yatang.sc.purchase.dubboservice.util.IdGeneratorUtil;
import com.yatang.sc.purchase.enums.InvoiceType;
import com.yatang.sc.purchase.exception.PurchaseException;
import com.yatang.sc.purchase.service.PurchaseHelper;
import com.yatang.sc.purchase.service.PurchaseService;
import com.yatang.sc.purchase.service.WarehouseHelper;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by xiangyonghong on 2017/7/11.
 */
@Service("purchaseHelper")
public class PurchaseHelperImpl implements PurchaseHelper {
    private final Logger mLogger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IdGeneratorUtil idGeneratorUtil;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentGroupService paymentGroupService;

    @Autowired
    private OrderPriceService orderPriceService;

    @Autowired
    private PriceAdjustmentService priceAdjustmentService;

    @Autowired
    private OrderPriceAdjService orderPriceAdjService;

    @Autowired
    private CommerceItemService commerceItemService;

    @Autowired
    private ItemPriceService itemPriceService;

    @Autowired
    private ItemPriceAdjService itemPriceAdjService;

    @Autowired
    private ShippingGroupService shippingGroupService;

    @Autowired
    private ShippingPriceService shippingPriceService;

    @Autowired
    private ShippingPriceAdjService shippingPriceAdjService;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private OrderItemsService orderItemsService;

    @Autowired
    private OrderPaymentsService orderPaymentsService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private CouponRecordService couponRecordService;

    @Autowired
    private CouponActivityService couponActivityService;

    @Autowired
    private CouponsService couponsService;

    @Autowired
    private ProductHelper productHelper;

    @Autowired
    private WarehouseHelper warehouseHelper;

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private AreaWarehouseQueryDubboService areaWarehouseQueryDubboService;

    @Override
    public void saveOrder(Order order) {
        saveOrder(order, true);
    }

    @Override
    public void saveOrder(Order order, boolean geneId) {
        if (geneId) {
            order.setId(getOrderId(order.getBranchCompanyId(), order.getSubmitTime()));
        }
        mLogger.info("persist order:{}, geneId:{}", order.getId(), geneId);
        orderService.save(order);
    }

    @Override
    public String getOrderId(String branchCompanyId,Date nowTime){
        SimpleDateFormat sp = new SimpleDateFormat("yyyyMMdd");
//        String idgen = idGeneratorUtil.getIdGeneratorId("o");
//        DecimalFormat df = new DecimalFormat("0000");
//        if(idgen.length()<4){
//            int id = Integer.parseInt(idgen);
//            idgen = df.format(id);
//        }else{
//            int len = idgen.length();
//            idgen = idgen.substring(len-4,len);
//        }
//
//        return branchCompanyId+sp.format(nowTime)+idgen;

        return idGeneratorUtil.generateScSaleOrderId(branchCompanyId,sp.format(nowTime));

    }

    @Override
    public void savePaymentGroup(PaymentGroup paymentGroup) {
        paymentGroup.setId(idGeneratorUtil.getIdGeneratorId("pg"));
        paymentGroupService.save(paymentGroup);
    }

    @Override
    public void saveOrderPrice(OrderPrice orderPrice) {
//        orderPrice.setId(idGeneratorUtil.getIdGeneratorId("op"));
        orderPriceService.save(orderPrice);
    }

    @Override
    public void savePriceAdjustment(PriceAdjustment priceAdjustment) {
//        priceAdjustment.setId(idGeneratorUtil.getIdGeneratorId("p"));
        priceAdjustmentService.save(priceAdjustment);
    }

    @Override
    public void saveOrderPriceAdj(Long orderPriceId, Long priceAdjustmentId, Byte sequence) {
        OrderPriceAdj orderPriceAdj = new OrderPriceAdj();
//        orderPriceAdj.setId(idGeneratorUtil.getIdGeneratorId("opa"));
        orderPriceAdj.setOrderPriceId(orderPriceId);
        orderPriceAdj.setAdjustmentId(priceAdjustmentId);
        orderPriceAdj.setSequence(sequence);
        orderPriceAdjService.save(orderPriceAdj);
    }

    @Override
    public void saveCommerceItem(CommerceItem commerceItem, ItemPriceInfoDto itemPriceDto) {

        ItemPrice itemPrice = commerceItem.getItemPrice();
//        itemPrice.setId(idGeneratorUtil.getIdGeneratorId("ip"));
        itemPriceService.save(itemPrice);
        commerceItem.setItemPriceInfo(itemPrice.getId());
//        commerceItem.setId(idGeneratorUtil.getIdGeneratorId("ci"));
        commerceItemService.save(commerceItem);
        List<PriceAdjustmentDto> adjustmentDtos = itemPriceDto.getAdjustments();
        List<PriceAdjustment> adjustments = BeanConvertUtils.convertList(adjustmentDtos, PriceAdjustment.class);
        for (int i = 0; i < adjustments.size(); i++) {
            PriceAdjustment priceAdjustment = adjustments.get(i);
//            priceAdjustment.setId(idGeneratorUtil.getIdGeneratorId("p"));
            savePriceAdjustment(priceAdjustment);
            ItemPriceAdj itemPriceAdj = new ItemPriceAdj();
//            itemPriceAdj.setId(idGeneratorUtil.getIdGeneratorId("ipa"));
            itemPriceAdj.setItemPriceId(itemPrice.getId());
            itemPriceAdj.setAdjustmentId(priceAdjustment.getId());
            itemPriceAdj.setSequence(new Byte(i + ""));
            itemPriceAdjService.save(itemPriceAdj);
        }
    }

    @Override
    public void saveShippingGroup(ShippingGroup shippingGroup) {
        shippingGroup.setId(idGeneratorUtil.getIdGeneratorId("sg"));
        shippingGroupService.save(shippingGroup);
    }

    @Override
    public void saveShippingPrice(ShippingPrice shippingPrice) {
//        shippingPrice.setId(idGeneratorUtil.getIdGeneratorId("sp"));
        shippingPriceService.save(shippingPrice);
    }

    @Override
    public void saveShippingPriceAdj(Long shippingPriceId, Long adjustmentId, Byte seq) {
        ShippingPriceAdj shippingPriceAdj = new ShippingPriceAdj();
//        shippingPriceAdj.setId(idGeneratorUtil.getIdGeneratorId("spa"));
        shippingPriceAdj.setShippingPriceId(shippingPriceId);
        shippingPriceAdj.setAdjustmentId(adjustmentId);
        shippingPriceAdj.setSequence(seq);
        shippingPriceAdjService.save(shippingPriceAdj);
    }


    @Override
    public void saveInvoice(Invoice invoice) {
        invoiceService.save(invoice);
    }

    @Override
    public void saveOrderItems(OrderItems orderItems) {
//        orderItems.setId(idGeneratorUtil.getIdGeneratorId("oi"));
        orderItemsService.save(orderItems);

    }

    @Override
    public Order saveShoppingCart(ShoppingCart shoppingCart) {
        OrderDto orderDto = shoppingCart.getCurrentOrder();
        //配送，配送价格，配送价格明细关联，配送价格明细
        String shippingGroupId = saveShippingGroupAndShippingPrice(orderDto.getShippingGroupDto());

        //发票信息
        InvoiceInfoDto invoiceInfoDto = orderDto.getInvoiceInfoDto();
        Invoice invoice = new Invoice();
        if(invoiceInfoDto !=null && invoiceInfoDto.getInvoiceType()!= null){
            invoice.setInvoiceType(invoiceInfoDto.getInvoiceType());
            if(invoiceInfoDto.getInvoiceType().equals(InvoiceType.common.getValue())){
                invoice.setInvoiceTitle(invoiceInfoDto.getInvoiceTitle());
            }else if(invoiceInfoDto.getInvoiceType().equals(InvoiceType.valueAdded.getValue())){
                invoice = BeanConvertUtils.convert(invoiceInfoDto,Invoice.class);
            }
        }else{
            invoice.setInvoiceType(InvoiceType.none.getValue());
        }
        invoiceService.save(invoice);

        //订单，订单金额，金额价格明细关联，价格明细
        long orderPriceId = saveOrderPriceAndOrderPriceAdj(orderDto.getPriceInfoDto(), orderDto.getCouponActivities());

        //保存订单对象
        Order order = BeanConvertUtils.convert(orderDto, Order.class);
        Date nowTime = new Date();
        order.setSubmitTime(nowTime);
        order.setCreationTime(nowTime);
        order.setOrderState(OrderStates.PENDING_APPROVE);
        if(OrderTypes.DIRECT_STORE.equals(order.getOrderType())){
            order.setPaymentState(PaymentStates.GSN);
            //公司内销单，直接变更订单状态为已支付（小B端状态）
            order.setState(OrderTotalStates.PENDING_PROCESS.getStateValue());
            order.setStateDetail(OrderTotalStates.PENDING_PROCESS.getDescription());
        }else{
            order.setPaymentState(PaymentStates.PENDING_PAYMENT);
            order.setState(OrderTotalStates.PENDING_PAYMENT.getStateValue());
            order.setStateDetail(OrderTotalStates.PENDING_PAYMENT.getDescription());
        }
        order.setShippingState(ShippingStates.PENDING_PROCESS);
        order.setBranchCompanyId(shoppingCart.getBranchCompanyId());
        order.setFranchiseeId(shoppingCart.getUserId());
        order.setFranchiseeStoreId(shoppingCart.getStoreId());

        order.setPriceInfo(orderPriceId);
        order.setInvoiceInfo(invoice.getInvoiceId());
        order.setShippingGroup(shippingGroupId);
        saveOrder(order);
        shoppingCart.setLastOrderId(order.getId());
        //订单商品关联，订单商品价格，商品价格明细关联，价格明细
        List<CommerceItemDto> commerceItemDtos = orderDto.getItems();
        for (int j = 0; j < commerceItemDtos.size(); j++) {
            CommerceItemDto commerceItemDto = commerceItemDtos.get(j);
            if (commerceItemDto.isSelected()) {
                if(CommerceItemTypes.BUNDLE.equals(commerceItemDto.getType())){
                    for(CommerceItemDto subItem : commerceItemDto.getSubItems()){
                        CommerceItem subCommerceItem = BeanConvertUtils.convert(subItem, CommerceItem.class);
                        saveCommerceItem(subCommerceItem, subItem.getItemPrice());
                        OrderItems subOrderItems = new OrderItems();
                        subOrderItems.setOrderId(order.getId());
                        subOrderItems.setCommerceItemId(subCommerceItem.getId());
                        saveOrderItems(subOrderItems);
                    }
                } else {
                    CommerceItem commerceItem = BeanConvertUtils.convert(commerceItemDto, CommerceItem.class);
                    saveCommerceItem(commerceItem, commerceItemDto.getItemPrice());
                    OrderItems orderItems = new OrderItems();
                    orderItems.setOrderId(order.getId());
                    orderItems.setCommerceItemId(commerceItem.getId());
                    saveOrderItems(orderItems);
                }
            }
        }

        //订单支付
        List<PaymentGroupDto> paymentGroupDtos = orderDto.getPaymentGroupDtos();
        for (PaymentGroupDto paymentGroupDto: paymentGroupDtos){
            PaymentGroup paymentGroup = BeanConvertUtils.convert(paymentGroupDto, PaymentGroup.class);
            paymentGroup.setOrderId(order.getId());
            if(OrderTypes.DIRECT_STORE.equals(order.getOrderType())){
                paymentGroup.setState(PaymentGroupStates.GSN.getStateValue());
                paymentGroup.setStateDetail(PaymentGroupStates.GSN.getDescription());
            } else {
                paymentGroup.setState(PaymentGroupStates.INITIAL.getStateValue());
                paymentGroup.setStateDetail(PaymentGroupStates.INITIAL.getDescription());
            }
            savePaymentGroup(paymentGroup);
            saveOrderPayments(paymentGroup.getId(), order.getId());
        }
        //保存订单促销信息
        updateCouponActivityToUsed(orderDto);
        savePromotionRecrods(order.getId(), orderDto);
        return order;
    }


    @Override
    public void saveOrderPayments(String id,String orderId) {
        OrderPayments orderPayments = new OrderPayments();
//        orderPayments.setId(idGeneratorUtil.getIdGeneratorId("op"));
        orderPayments.setOrderId(orderId);
        orderPayments.setPaymentGroupId(id);
        orderPaymentsService.saveOrderPayments(orderPayments);
    }


    public void updateCouponActivityToUsed(OrderDto orderDto){
        List<String> couponActivityIds = orderDto.getCouponActivities();
//        List<String> couponActivityIds =  new ArrayList<String>();
//        couponActivityIds.add(orderDto.getCouponActivity());

        if(CollectionUtils.isEmpty(couponActivityIds)){
            return;
        }
        List<PriceAdjustmentDto> adjustments = orderDto.getPriceInfoDto().getAdjustments();
        if(CollectionUtils.isEmpty(adjustments)){
            return;
        }
        List<String> promoIds = new ArrayList<String>();
        for(PriceAdjustmentDto adjustment : adjustments) {
            if (adjustment != null
                    && !StringUtils.isEmpty(adjustment.getPricingModel())) {
                if (adjustment.isCouponAdjument()) {
                    promoIds.add(adjustment.getPricingModel());
                }
            }
        }
        List<String> activityIds = new ArrayList<String>();
        for(String id : couponActivityIds){
            Long cid = Long.parseLong(id);
            CouponActivityPo activityPo = couponActivityService.selectByPrimaryKey(cid);
            if(activityPo != null && promoIds.contains(activityPo.getPromoId())){
                activityIds.add(id);
            }
        }
        couponActivityService.updateStateToUsed(activityIds);
    }


    public void savePromotionRecrods(String orderId, OrderDto orderDto){
        if(orderDto.getPriceInfoDto() == null){
            return;
        }
       List<PriceAdjustmentDto> adjustments = orderDto.getPriceInfoDto().getAdjustments();
       if(CollectionUtils.isEmpty(adjustments)){
           return;
       }
       for(PriceAdjustmentDto adjustment : adjustments){
            if(adjustment != null
                    && !StringUtils.isEmpty(adjustment.getPricingModel())){
                if(adjustment.isCouponAdjument()){
                    mLogger.info("insert COUPON records:{},COUPON:{}" , orderId, adjustment.getPricingModel());
                    CouponRecordPo couponRecordPo = new CouponRecordPo();
                    couponRecordPo.setOrderId(orderId);
                    couponRecordPo.setPromoId(adjustment.getPricingModel());
                    couponRecordPo.setDiscountAmount(adjustment.getAdjustment());
                    couponRecordPo.setRecordTime(new Date());
                    couponRecordService.insertSelective(couponRecordPo);
                    couponsService.updateUsedQty(adjustment.getPricingModel());

                }else{
                    mLogger.info("insert promotion records:" + orderId);
                    PromoRecordsPo recordsPo = new PromoRecordsPo();
                    recordsPo.setOrderId(orderId);
                    recordsPo.setPromoId(adjustment.getPricingModel());
                    recordsPo.setDiscount(new BigDecimal(adjustment.getAdjustment()));
                    promotionService.insertPromoOrderRecord(recordsPo);
                }
            }
       }
    }

    @Override
    public boolean saveThirdOrder(OrderDto orderDto) throws PurchaseException {
        mLogger.info("保存第三方订单 saveThirdOrder,start.");
        if(orderDto.getProfileId() == null){
            mLogger.info("第三方订单用户id为空，返回。");
            throw new PurchaseException("用户id为空");
        }
        if(orderDto.getThirdPartOrderNo() == null){
            mLogger.info("第三方订单id为空，返回。");
            throw new PurchaseException("订单id为空");
        }
//        String logicWarehouseCode = AreaToWarehouseConfig.getWarehouseCode(orderDto.getShippingGroupDto().getProvince());
        Response<AreawarehouseDto> response = areaWarehouseQueryDubboService.queryAreawarehouseByProvince(orderDto.getShippingGroupDto().getProvince());
        String logicWarehouseCode = null;
        if(response.isSuccess()){
            logicWarehouseCode = response.getResultObject().getWarehouseCode();
        }
        mLogger.info("保存第三方订单,province{}", orderDto.getShippingGroupDto().getProvince());
        if(StringUtils.isEmpty(logicWarehouseCode)){
            throw new PurchaseException("不支持配送到:" + orderDto.getShippingGroupDto().getProvince());
        }

        Order order3 = orderService.queryThirdOrderByThirdOrderNo(orderDto.getThirdPartOrderNo());
        if (order3 != null) {
            mLogger.info("第三方订单已经存在，返回。");
            throw new PurchaseException("第三方订单已经存在");
        }

        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setTimeout(300);
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = transactionManager.getTransaction(transactionDefinition);
        boolean isRollback = false;
        Order order = null;
        try {
            //配送，配送价格，配送价格明细关联，配送价格明细
            String shippingGroupId = saveShippingGroupAndShippingPrice(orderDto.getShippingGroupDto());
            //发票信息
            long invoiceId = saveThirdInvoice(orderDto.getInvoiceInfoDto());
            //保存订单对象
            order = saveSimpleThirdOrder(orderDto, invoiceId, shippingGroupId, logicWarehouseCode);
            //保存订单相关商品，订单商品关联，订单商品价格，商品价格明细关联，价格明细
            saveThirdCommerceItem(orderDto.getItems(), order.getId());
            //保存支付信息
            saveThirdPaymentGroup(orderDto.getPaymentGroupDtos(), order.getId());
            mLogger.info("保存第三方订单,saveThirdOrder end orderId{}",order.getId());
        } catch (Exception ex) {
            mLogger.error("提交订单失败：", ex);
            isRollback = true;
        } finally {
            if (isRollback) {
                transactionManager.rollback(txStatus);
            } else {
                transactionManager.commit(txStatus);
            }
        }

        if (!isRollback) {
            purchaseService.sendSubmitOrderMsg(order);
        }

        return true;
    }

    private long saveThirdInvoice(InvoiceInfoDto invoiceInfoDto) {
        Invoice invoice = new Invoice();
        if(invoiceInfoDto !=null && invoiceInfoDto.getInvoiceType()!= null){
            invoice.setInvoiceType(invoiceInfoDto.getInvoiceType());
            if(invoiceInfoDto.getInvoiceType().equals(InvoiceType.common.getValue())){
                invoice.setInvoiceTitle(invoiceInfoDto.getInvoiceTitle());
            }else if(invoiceInfoDto.getInvoiceType().equals(InvoiceType.valueAdded.getValue())){
                invoice = BeanConvertUtils.convert(invoiceInfoDto,Invoice.class);
            }
        }else{
            invoice.setInvoiceType(InvoiceType.none.getValue());
        }
        invoiceService.save(invoice);
        return invoice.getInvoiceId();
    }

    private void saveThirdPaymentGroup(List<PaymentGroupDto> paymentGroupDtos, String orderId) {
        Date nowTime = new Date();
        for (PaymentGroupDto paymentGroupDto: paymentGroupDtos){
            PaymentGroup paymentGroup = BeanConvertUtils.convert(paymentGroupDto, PaymentGroup.class);
            paymentGroup.setOrderId(orderId);
            paymentGroup.setPayDate(nowTime);
            paymentGroup.setState(PaymentGroupStates.SETTLED.getStateValue());//已付款
            paymentGroup.setStateDetail(PaymentGroupStates.SETTLED.getDescription());//已付款
            mLogger.info("保存第三方订单,save paymentGroup.");
            savePaymentGroup(paymentGroup);
            saveOrderPayments(paymentGroup.getId(), orderId);
        }
    }

    private void saveThirdCommerceItem(List<CommerceItemDto> commerceItemDtos, String orderId) throws PurchaseException{
        for (int j = 0; j < commerceItemDtos.size(); j++) {
            CommerceItemDto commerceItemDto = commerceItemDtos.get(j);
            CommerceItem commerceItem = BeanConvertUtils.convert(commerceItemDto, CommerceItem.class);
            //设置商品ID
            ProductIndexDto productDto =  productHelper.findProductByCode(commerceItemDto.getProductCode());
            if(productDto ==null){
                throw new PurchaseException("查询商品失败，商品编码：" + commerceItemDto.getProductCode());
            }
            commerceItem.setSelected(true);
            commerceItem.setSkuId(productDto.getId());
            commerceItem.setProductId(productDto.getId());
            commerceItem.setType(CommerceItemTypes.DEFAULT);
            saveCommerceItem(commerceItem, commerceItemDto.getItemPrice());
            OrderItems orderItems = new OrderItems();
            orderItems.setOrderId(orderId);
            orderItems.setCommerceItemId(commerceItem.getId());
            saveOrderItems(orderItems);
        }
    }

    private Order saveSimpleThirdOrder(OrderDto orderDto,long invoiceId,String shippingGroupId,String logicWarehouseCode)throws PurchaseException {

        long orderPriceId = saveOrderPriceAndOrderPriceAdj(orderDto.getPriceInfoDto(),null);
        Order order = BeanConvertUtils.convert(orderDto, Order.class);
        Date nowTime = new Date();
        order.setSubmitTime(nowTime);
        order.setCreationTime(nowTime);
        order.setLastModifiedTime(nowTime);
        order.setOrderState(OrderStates.PENDING_APPROVE);

        order.setPaymentState(PaymentStates.DEBITED);//已支付
        order.setState(OrderTotalStates.PENDING_PROCESS.getStateValue());//已支付
        order.setStateDetail(OrderTotalStates.PENDING_PROCESS.getDescription());

        order.setShippingState(ShippingStates.PENDING_PROCESS);
        order.setBranchCompanyId(orderDto.getBranchCompanyId());
        order.setFranchiseeId(orderDto.getProfileId());
        order.setFranchiseeStoreId(orderDto.getProfileId());
        order.setPriceInfo(orderPriceId);
        order.setInvoiceInfo(invoiceId);
        order.setShippingGroup(shippingGroupId);

        LogicWarehouseDto logicWarehouseDto = warehouseHelper.findLogicWarehouseByCode(logicWarehouseCode);
        if(logicWarehouseDto == null){
            throw new PurchaseException("未查询到仓库:" + logicWarehouseCode);
        }
        mLogger.info("保存第三方订单,arehousePhyReponse logicWarehouseCode {},branchCompanyId {}",logicWarehouseDto.getWarehouseCode(),logicWarehouseDto.getBranchCompanyId());
        order.setBranchCompanyArehouse(logicWarehouseDto.getWarehouseCode());
        order.setBranchCompanyId(logicWarehouseDto.getBranchCompanyId());
        mLogger.info("保存第三方订单,save order.");
        saveOrder(order);
        return order;
    }

    private String saveShippingGroupAndShippingPrice(ShippingGroupDto shippingGroupDto) {
        ShippingPriceInfoDto shippingPriceInfoDto = shippingGroupDto.getShippingPriceInfoDto();
        //配送价格
        ShippingPrice shippingPrice = BeanConvertUtils.convert(shippingPriceInfoDto, ShippingPrice.class);
        mLogger.info("save shippingPrice.");
        saveShippingPrice(shippingPrice);

        ShippingGroup shippingGroup = BeanConvertUtils.convert(shippingGroupDto, ShippingGroup.class);
        shippingGroup.setShippingPriceInfo(shippingPrice.getId());
        shippingGroup.setState(ShippingGroupStates.INITIAL.getStateValue());
        shippingGroup.setStateDetail(ShippingGroupStates.INITIAL.getDescription());
        mLogger.info("save shippingGroup{}", JSON.toJSONString(shippingGroup));
        saveShippingGroup(shippingGroup);

        List<PriceAdjustmentDto> spadjustments = shippingPriceInfoDto.getAdjustments();
        for (int k = 0; k < spadjustments.size(); k++) {
            PriceAdjustmentDto priceAdjustmentDto = spadjustments.get(k);
            //配送价格明细
            PriceAdjustment priceAdjustment = BeanConvertUtils.convert(priceAdjustmentDto, PriceAdjustment.class);

            mLogger.info("save shippingPrice,priceAdjustment.");
            savePriceAdjustment(priceAdjustment);
            //配送价格明细关联
            saveShippingPriceAdj(shippingPrice.getId(), priceAdjustment.getId(), new Byte(k + ""));
        }
        return shippingGroup.getId();
    }

    private Long saveOrderPriceAndOrderPriceAdj(OrderPriceInfoDto priceInfoDto, List<String> couponActivities) {
        OrderPrice orderPrice = BeanConvertUtils.convert(priceInfoDto, OrderPrice.class);
        mLogger.info("save orderPrice.");
        saveOrderPrice(orderPrice);
        List<PriceAdjustmentDto> adjustments = priceInfoDto.getAdjustments();
        for (int i = 0; i < adjustments.size(); i++) {
            //金额价格明细关联，价格明细
            PriceAdjustment priceAdjustment = BeanConvertUtils.convert(adjustments.get(i), PriceAdjustment.class);
            mLogger.info("save orderPrice,priceAdjustment.");
            if (!CollectionUtils.isEmpty(couponActivities)) {
                priceAdjustment.setActivityIds(org.apache.commons.lang3.StringUtils.join(couponActivities, ","));
            }
            savePriceAdjustment(priceAdjustment);
            saveOrderPriceAdj(orderPrice.getId(), priceAdjustment.getId(), Byte.valueOf(i + ""));
        }
        return orderPrice.getId();
    }

}
