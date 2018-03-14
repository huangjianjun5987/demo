package com.yatang.sc.purchase.service.impl;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.StringUtils;
import com.yatang.sc.common.OrderTypeEnum;
import com.yatang.sc.common.staticvalue.AdviceMessage;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.facade.dubboservice.GoodsPriceQueryDubboService;
import com.yatang.sc.inventory.dto.ItemInventoryDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import com.yatang.sc.order.domain.AvailableCouponActivityPo;
import com.yatang.sc.order.domain.CommerceItem;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.UserInvoiceInfo;
import com.yatang.sc.order.msg.OrderMessage;
import com.yatang.sc.order.msg.OrderMessageSender;
import com.yatang.sc.order.msg.OrderMessageType;
import com.yatang.sc.order.service.InvoiceInfoService;
import com.yatang.sc.order.states.CommerceItemTypes;
import com.yatang.sc.product.service.ProductHelper;
import com.yatang.sc.purchase.dto.*;
import com.yatang.sc.purchase.dubboservice.util.IdGeneratorUtil;
import com.yatang.sc.purchase.enums.InvoiceType;
import com.yatang.sc.purchase.enums.PricingOrderType;
import com.yatang.sc.purchase.exception.*;
import com.yatang.sc.purchase.redis.OrderHolderRedis;
import com.yatang.sc.purchase.service.*;
import com.yatang.xc.mbd.biz.org.dto.StoreDto;
import com.yatang.xc.mbd.biz.org.dubboservice.OrganizationService;
import com.yatang.xc.mbd.biz.region.dto.RegionDTO;
import com.yatang.xc.mbd.biz.region.dubboservice.RegionDubboService;
import com.yatang.xc.mbd.pi.es.dto.BundleProductDto;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.*;

/**
 * Created by qiugang on 7/10/2017.
 */
@Service("purchaseService")
public class PurchaseServiceImpl implements PurchaseService{
    Logger log = LoggerFactory.getLogger(PurchaseServiceImpl.class);

    public static String DEFAULT_WAREHOURSE_CODE = "YTXC";

    @Autowired
    OrderHolderRedis orderHolderRedis;

    @Autowired
    PricingEngineService pricingEngineService;

    @Autowired
    ItemPricingEngine itemPricingEngine;

    @Autowired
    PurchaseHelper purchaseHelper;

    @Autowired
    IdGeneratorUtil idGeneratorUtil;

    @Autowired
    InvoiceInfoService invoiceInfoService;

    @Autowired
    ProductHelper productHelper;

    @Autowired
    GoodsPriceQueryDubboService goodsPriceDubboService;

    @Autowired
    OrderMessageSender orderMessageSender;

    @Autowired
    OrganizationService organizationService;

    @Autowired
    ItemLocInventoryDubboService inventoryDubboService;

    @Autowired
    ShippingPricingEngine shippingPricingEngine;


    @Autowired
    RegionDubboService regionDubboService;

    @Autowired
    private DataSourceTransactionManager transactionManager;

    @Autowired
    private ProdSellPriceInfoHelper mProdSellPriceInfoHelper;

    @Override
    public ShoppingCart initalShoppingCart(String userId, Map params) throws PurchaseException {

        ShoppingCart shoppingCart = orderHolderRedis.getShoppingCart(userId);
        if(shoppingCart == null){
            shoppingCart = createShoppingCart(userId, false);
        }
        OrderDto orderDto = shoppingCart.getCurrentOrder();
        if (orderDto != null){
            List<CommerceItemDto> commerceItemDtos=orderDto.getItems();
            if (commerceItemDtos != null){
                Iterator<CommerceItemDto> ite = commerceItemDtos.iterator();
                while(ite.hasNext()){
                    CommerceItemDto ci = ite.next();
                    if(StringUtils.isEmpty(ci.getProductId()) || ci.getQuantity() == 0){
                        ite.remove();
                    }
                }
                for (CommerceItemDto commerceItemDto:commerceItemDtos){
                    if (commerceItemDto.getProductId() != null){
                        ProdSellPriceInfoDto sellPrice = getProductPriceInfo(commerceItemDto.getProductId(), params.get("branchCompanyId").toString());
                        if(sellPrice == null || ( sellPrice.getStatus() != null && sellPrice.getStatus() ==0) ){
                            commerceItemDto.setAvailable(false);
                            if (commerceItemDto.isSelected()){
                                commerceItemDto.setSelected(false);
                            }
                        }else{
                            if (!commerceItemDto.isAvailable()){
                                commerceItemDto.setAvailable(true);
                            }
                        }
                    }
                }
            }
        }
        if(!userId.equals(shoppingCart.getUserId())){
            shoppingCart.setUserId(userId);
        }
        OrderDto currentOrder = shoppingCart.getCurrentOrder();
        if(currentOrder != null && !userId.equals(currentOrder.getProfileId())){
            currentOrder.setProfileId(userId);
        }
        shoppingCart.setStoreId((String)params.get("storeId"));
        shoppingCart.setBranchCompanyId((String)params.get("branchCompanyId"));
        shoppingCart.setDeliveryWarehouseCode((String)params.get("deliveryWarehouseCode"));
        updateShoppingCart(shoppingCart);
        return shoppingCart;
    }

    /**
     * 加载购物车
     * @param userId
     * @return
     * @throws PurchaseException
     */
    @Override
    public ShoppingCart loadShoppingCart(String userId) throws PurchaseException, PricingException {

        ShoppingCart shoppingCart = orderHolderRedis.getShoppingCart(userId);
        if(shoppingCart == null){
            shoppingCart = createShoppingCart(userId, true);
        }
        fillOrderProductInfo(shoppingCart.getCurrentOrder(),shoppingCart.getBranchCompanyId());
        return shoppingCart;
    }

    /**
     * 创建购物车
     * @param userId
     * @return
     */
    @Override
    public ShoppingCart createShoppingCart(String userId, boolean persist) {
        log.info("create shopping cart for user:" + userId);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUserId(userId);
        shoppingCart.setVersion(0);
        OrderDto order = createOrder(userId, "default");
        shoppingCart.setCurrentOrder(order);
        if(persist){
            try {
                orderHolderRedis.putShoppingCart(userId, shoppingCart);
            } catch (VersionException e) {
                log.warn("create ShoppingCart error!!");
                e.printStackTrace();
            }
        }
        return shoppingCart;
    }

    @Override
    public void updateShoppingCart(ShoppingCart shoppingCart) throws PurchaseException{

        try {
            orderHolderRedis.putShoppingCart(shoppingCart.getUserId(), shoppingCart);
        } catch (VersionException e) {
            ShoppingCart newCart = orderHolderRedis.getShoppingCart(shoppingCart.getUserId());
            shoppingCart.setVersion(newCart.getVersion());
            shoppingCart.setCurrentOrder(newCart.getCurrentOrder());
            throw new PurchaseException(e.getMessage());
        }
    }

    @Override
    public void checkout(ShoppingCart shoppingCart, AddressDto address) throws PurchaseException {

        log.info("checkout address:{}", JSONObject.toJSON(address));
        OrderDto order = shoppingCart.getCurrentOrder();
        ShippingGroupDto shippingGroup = order.getShippingGroupDto();
        if(shippingGroup == null){
            shippingGroup  = createDefaultShippingGroup("YTS");
            order.setShippingGroupDto(shippingGroup);
        }
        shippingGroup.setProvince(address.getProvince());
        shippingGroup.setCity(address.getCity());
        shippingGroup.setDistrict(address.getDistrict());
        shippingGroup.setDetailAddress(address.getDetailAddress());
        shippingGroup.setConsigneeName(address.getConsigneeName());
        shippingGroup.setCellphone(address.getPhoneNumber());
        shippingGroup.setPostcode(address.getPostcode());
        Map contentMap = createPriceContentMap(shoppingCart);
//        List<AvailableCouponActivityPo> couponActivities = pricingEngineService.getAvailableCouponActivities(shoppingCart.getStoreId());
//        contentMap.put("activeCoupons", getActivities(couponActivities));
//        contentMap.put("couponPos", couponActivities);
        repriceOrder(order, PricingOrderType.TOTAL, contentMap);
        log.info("order coupons:{}", order.getCouponActivities());
        updateShoppingCart(shoppingCart);
    }
    private List<String> getActivities( List<AvailableCouponActivityPo> couponActivityPoList){
    	 List<String> result = new ArrayList<String>();
    	 if(couponActivityPoList==null||couponActivityPoList.isEmpty()){
    		 return result;
    	 }
         for(AvailableCouponActivityPo act : couponActivityPoList){
             result.add(act.getCouponActivityId());
         }
		return result;
    }
    /**
     * 创建订单
     * @param userId
     * @param orderType
     * @return
     */
    @Override
    public OrderDto createOrder(String userId, String orderType) {
        OrderDto order = new OrderDto();
        order.setProfileId(userId);
        order.setOrderType(orderType);
        order.setSiteId("default");
        order.setState((short) 0);
        order.setStateDetail("未提交");
        //设置默认发票信息
        order.setInvoiceInfoDto(createDefaultInvoiceInfo(userId));
        //默认配送方式为雅堂到家
        ShippingGroupDto shippingGroup = createDefaultShippingGroup("YTS");
        order.setShippingGroupDto(shippingGroup);
        //默认支付方式为线上付款
        PaymentGroupDto paymentGroup = createDefaultPaymentGroup("onlinePayment");
        order.getPaymentGroupDtos().add(paymentGroup);
        return order;
    }

    /**
     * 创建ShippingGroup
     * @param shippingMethod
     * @return
     */
    protected ShippingGroupDto createDefaultShippingGroup(String shippingMethod){
        ShippingGroupDto shippingGroup = new ShippingGroupDto();
        shippingGroup.setShippingMethod(shippingMethod);
        return shippingGroup;
    }

    protected PaymentGroupDto createDefaultPaymentGroup(String paymentMethod){
        PaymentGroupDto paymentGroup = new PaymentGroupDto();
        paymentGroup.setPaymentMethod(paymentMethod);
        return paymentGroup;
    }

    protected InvoiceInfoDto createDefaultInvoiceInfo(String profileId){
        InvoiceInfoDto invoiceInfo = new InvoiceInfoDto();
        invoiceInfo.setInvoiceType(InvoiceType.none.getValue());
        UserInvoiceInfo userInvoiceInfo = invoiceInfoService.getInvoiceByProfileId(profileId);
        if(userInvoiceInfo != null){
            copyInvoiceInfo(userInvoiceInfo, invoiceInfo);
        }
        return invoiceInfo;
    }



    /**
     * 添加商品到订单
     * @param shoppingCart
     * @param itemInfo
     * @throws PurchaseException
     */
    @Override
    public void addItemToShoppingCart(ShoppingCart shoppingCart, AddItemDto itemInfo) throws PurchaseException, ItemException {
        if(itemInfo == null){
            throw new PurchaseException("add item to order the item info is null");
        }
        if(StringUtils.isEmpty(itemInfo.getSkuId())){
            throw new PurchaseException("add item to order the sku id is empty");
        }
        if(itemInfo.getQuantity() <= 0){
            throw new PurchaseException("add item to order the quantity can't  below 0");
        }
        OrderDto order = shoppingCart.getCurrentOrder();
        CommerceItemDto cItem = order.getCommerceItemBySkuId(itemInfo.getSkuId());
        ProdSellPriceInfoDto prodSellPriceInfoDto = itemInfo.getSellPriceInfo();
        long maxQuantity = Integer.MAX_VALUE;
        if (prodSellPriceInfoDto != null && prodSellPriceInfoDto.getMaxNumber()!= null){
            maxQuantity = prodSellPriceInfoDto.getMaxNumber();
        }
        if (cItem != null) {

            long saleQty = cItem.getSaleQuantity() + itemInfo.getSaleQuantity();
            long quantity = saleQty;
            if(itemInfo.getSellFullCase() != null && itemInfo.getSellFullCase() == 1){
                quantity =  saleQty * itemInfo.getUnitQuantity();
            }
            if (maxQuantity > 0){
                if (quantity > maxQuantity ){
                    log.info("订购数量大于最大订购量,订购数量是:{},最大订购数量是:{},仓库是:{},商品id:{}",quantity ,maxQuantity,shoppingCart.getDeliveryWarehouseCode(),itemInfo.getSkuId());
                    throw new ItemException("亲，定购数量大于最大订购量啦~");
                }
            }
            cItem.setUnitQuantity(itemInfo.getUnitQuantity());
            cItem.setQuantity(quantity);
            cItem.setSaleQuantity(saleQty);
            cItem.setSellFullCase(itemInfo.getSellFullCase());
            setSaleItemProperty(cItem, prodSellPriceInfoDto);
            if(productHelper.isBundleProduct(itemInfo.getProduct().getProductType())) {
                configBundleCommerceItem(cItem, itemInfo.getProduct(), shoppingCart.getBranchCompanyId());
            }
            cItem.setSelected(true);
        } else {
            CommerceItemDto newItem = createCommerceItem(itemInfo, prodSellPriceInfoDto, shoppingCart.getBranchCompanyId());
            order.addItem(newItem);
            log.info("添加商品后商品的属性:" +JSON.toJSONString(newItem));
        }

        repriceOrder(order, PricingOrderType.AMOUNT, createPriceContentMap(shoppingCart));
        updateShoppingCart(shoppingCart);

    }


    public CommerceItemDto createCommerceItem(AddItemDto itemInfo, ProdSellPriceInfoDto prodSellPriceInfoDto, String branchCompanyId) throws PurchaseException, ItemException {
        CommerceItemDto newItem = new CommerceItemDto();
        newItem.setTimestamp(System.currentTimeMillis());
        ProductIndexDto product = itemInfo.getProduct();
        if(product == null){
            throw new PurchaseException("商品不存在！");
        }
        long maxQuantity = Integer.MAX_VALUE;
        if (prodSellPriceInfoDto != null && prodSellPriceInfoDto.getMaxNumber()!= null){
            maxQuantity = prodSellPriceInfoDto.getMaxNumber();
        }
        if (maxQuantity > 0){
            if (itemInfo.getQuantity() > maxQuantity ){
                log.info("订购数量大于最大订购量,订购数量是:{},最大订购数量是:{},商品id:{}",itemInfo.getQuantity() ,maxQuantity,itemInfo.getSkuId());
                throw new ItemException("亲，定购数量大于最大订购量啦~");
            }
        }
        newItem.setProductName(product.getName());
        newItem.setProductCode(product.getProductCode());
        newItem.setProductImg(product.getThumbnailImage());
        newItem.setSkuId(itemInfo.getSkuId());
        newItem.setProductId(itemInfo.getProductId());
        newItem.setQuantity(itemInfo.getQuantity());
        newItem.setSaleQuantity(itemInfo.getSaleQuantity());
        newItem.setUnitQuantity(itemInfo.getUnitQuantity());
        newItem.setSellFullCase(itemInfo.getSellFullCase());
        if(productHelper.isCouponProduct(product.getProductType())){
            newItem.setType(CommerceItemTypes.COUPON);
        }else if(productHelper.isBundleProduct(product.getProductType())){
            newItem.setType(CommerceItemTypes.BUNDLE);
            configBundleCommerceItem(newItem, product, branchCompanyId);
        } else{
            newItem.setType(CommerceItemTypes.DEFAULT);
        }
        setSaleItemProperty(newItem, prodSellPriceInfoDto);
        newItem.setSelected(true);
        return newItem;
    }


    public void configBundleCommerceItem(CommerceItemDto bundleItem, ProductIndexDto product, String branchCompanyId) throws PurchaseException, ItemException{

        List<BundleProductDto> bundleProducts =  product.getBundleProducts();
        if(CollectionUtils.isEmpty(bundleProducts)){
            throw new PurchaseException("套餐商品不存在！");
        }
        bundleItem.getSubItems().clear();
        for(BundleProductDto bp : bundleProducts){
            ProductIndexDto subProduct = productHelper.findProductById(bp.getProductId());
            if(subProduct == null){
                throw new PurchaseException("商品不存在！");
            }
            ProdSellPriceInfoDto subProdSellInfo = getProductPriceInfo(bp.getProductId(), branchCompanyId);
            if (subProdSellInfo == null) {
                throw new PurchaseException("商品不支持该地区销售！");
            }
            CommerceItemDto subItem = new CommerceItemDto();
            subItem.setSkuId(subProduct.getId());
            subItem.setProductId(subProduct.getId());
            subItem.setProductName(subProduct.getName());
            subItem.setProductCode(subProduct.getProductCode());
            subItem.setProductImg(subProduct.getThumbnailImage());
            subItem.setType(CommerceItemTypes.SUB_ITEM);
            int perQty = 1;
            if( bp.getProdCount() !=null){
                perQty = bp.getProdCount().intValue();
            }
            long qty = bundleItem.getQuantity() * perQty;
            subItem.setQuantity(qty);
            subItem.setSaleQuantity(qty);
            subItem.setUnitQuantity(perQty);
            subItem.setSellFullCase(subProdSellInfo.getSellFullCase());
            subItem.setSelected(true);
            setSaleItemProperty(subItem, subProdSellInfo);
            try {
                fillItemProperties(subItem, subProduct, branchCompanyId);
            } catch (PricingException e) {
               log.error(e.getMessage(), e);
            }
            bundleItem.getSubItems().add(subItem);
        }


    }

    public void fillOrderProductInfo(OrderDto order,String branchCompanyId) throws PricingException {

        List<CommerceItemDto> items = order.getItems();
        if(items == null || items.size() == 0){
            return;
        }
        List<String> productIds = new ArrayList<String>();
        for(CommerceItemDto item : items){
            if(!productIds.contains(item.getProductId())){
                productIds.add(item.getProductId());
            }
            if(CollectionUtils.isNotEmpty(item.getSubItems())){
                for(CommerceItemDto subItem : item.getSubItems()){
                    if(!productIds.contains(subItem.getProductId())){
                        productIds.add(subItem.getProductId());
                    }
                }
            }
        }
        Map<String, ProductIndexDto> productMaps = productHelper.queryProductByIds(productIds);
        for(CommerceItemDto item : items){
            ProductIndexDto product = productMaps.get(item.getProductId());
            fillItemProperties(item, product, branchCompanyId);
            if(CollectionUtils.isNotEmpty(item.getSubItems())){
                for(CommerceItemDto subItem : item.getSubItems()){
                    ProductIndexDto subProduct = productMaps.get(subItem.getProductId());
                    fillItemProperties(subItem, subProduct, branchCompanyId);
                }
            }
        }
    }


    public void fillItemProperties(CommerceItemDto item, ProductIndexDto product, String branchCompanyId) throws PricingException {
        if(product != null){
            item.setProductName(product.getName());
            item.setProductImg(product.getThumbnailImage());
            if(item.getProperties() == null){
                item.setProperties(new HashMap<String, String>());
            }
            item.getProperties().put("fullCaseUnit",product.getFullCaseUnit()); //整箱销售单位
            item.getProperties().put("minUnit",product.getMinUnit());  // 最小销售单位
            item.getProperties().put("saleName",product.getSaleName());  //// 销售名称
            item.getProperties().put("packingSpecifications",product.getPackingSpecifications());  // 标准包装规格参考值，例如：1*2*6',

            //根据商品和子公司id 给商品设置属性
            if(!item.getProperties().containsKey("salesInsideNumber")) {
                getSaleItemProperty(item, branchCompanyId);
            }

        }else {
            log.error("can't find product in es:" + item.getProductId());
        }
    }

    public void setCommerceItemProductInfo(CommerceItemDto item,String branchCompanyId) throws PricingException {

        ProductIndexDto product = productHelper.findProductById(item.getProductId());
        if(product != null){
            item.setProductName(product.getName());
            item.setProductImg(product.getThumbnailImage());
            if(item.getProperties() == null){
                item.setProperties(new HashMap<String, String>());
            }
            item.getProperties().put("fullCaseUnit",product.getFullCaseUnit()); //整箱销售单位
            item.getProperties().put("minUnit",product.getMinUnit());  // 最小销售单位
            item.getProperties().put("saleName",product.getSaleName());  //// 销售名称
            item.getProperties().put("packingSpecifications",product.getPackingSpecifications());  // 标准包装规格参考值，例如：1*2*6',
            //根据商品和子公司id 给商品设置属性
            if(!item.getProperties().containsKey("salesInsideNumber")){
                getSaleItemProperty(item,branchCompanyId);
            }

        }

    }

    /**
     * 获取属性
     * @param item
     * @param branchCompanyId
     * @return
     * @throws PricingException
     */
    @Deprecated
    public void getSaleItemProperty(CommerceItemDto item, String branchCompanyId) throws PricingException {
        ProdSellPriceInfoDto sellPrice = getProductPriceInfo(item.getProductId(), branchCompanyId);
        if(sellPrice == null){
            log.info("productId为：" + item.getProductId() + "," + "branchCompanyId为:" + branchCompanyId);
            return;
        }
        log.info("sellPrcie:{}",JSONObject.toJSONString(sellPrice));
        Integer	salesInsideNumber = sellPrice.getSalesInsideNumber();
        Integer	minNumber = sellPrice.getMinNumber();
        Integer maxNumber = sellPrice.getMaxNumber();
        if (salesInsideNumber == null ){
            log.info("itemId:{},branchCompanyId:{}",item.getSkuId(),branchCompanyId);
            salesInsideNumber = 1;
        }
        if (minNumber == null ){
            log.info("itemId:{},branchCompanyId:{}",item.getSkuId(),branchCompanyId);
            minNumber = 1;
        }
        if(maxNumber == null){
            log.info("itemId:{},branchCompanyId:{}",item.getSkuId(),branchCompanyId);
            maxNumber = Integer.MAX_VALUE;
        }
        if (item.getSellFullCase()!= null && item.getSellFullCase() == 1){
            minNumber = minNumber/salesInsideNumber;
            maxNumber = maxNumber/salesInsideNumber;
        }
        Map<String,String> property=item.getProperties();
        property.put("minNumber",minNumber.toString());
        property.put("maxNumber",maxNumber.toString());
        property.put("salesInsideNumber",salesInsideNumber.toString());
    }


    public void setSaleItemProperty(CommerceItemDto item, ProdSellPriceInfoDto sellPrice){

        Integer	salesInsideNumber = sellPrice.getSalesInsideNumber();
        Integer	minNumber = sellPrice.getMinNumber();
        Integer maxNumber = sellPrice.getMaxNumber();
        if (salesInsideNumber == null ){
            salesInsideNumber = 1;
        }
        if (minNumber == null ){
            minNumber = 1;
        }
        if(maxNumber == null){
            maxNumber = Integer.MAX_VALUE;
        }
        if (item.getSellFullCase()!= null && item.getSellFullCase() == 1){
            minNumber = minNumber/salesInsideNumber;
            maxNumber = maxNumber/salesInsideNumber;
        }
        if(item.getProperties() == null){
            item.setProperties(new HashMap<String, String>());
        }
        item.getProperties().put("minNumber",minNumber.toString());
        item.getProperties().put("maxNumber",maxNumber.toString());
        item.getProperties().put("salesInsideNumber",salesInsideNumber.toString());
    }


    @Override
    public void removeItem(ShoppingCart shoppingCart, String skuId) throws PurchaseException {

        if(StringUtils.isEmpty(skuId)){
            throw new PurchaseException("removeItem the sku id is empty");
        }
        OrderDto order = shoppingCart.getCurrentOrder();
        Iterator<CommerceItemDto> iterator = order.getItems().iterator();
        while (iterator.hasNext()){
            CommerceItemDto item = iterator.next();
            if(item.getSkuId().equals(skuId)){
                log.info("remove item sku id:" + item.getSkuId());
                iterator.remove();
                break;
            }
        }
        repriceOrder(order, PricingOrderType.AMOUNT, createPriceContentMap(shoppingCart));
        updateShoppingCart(shoppingCart);
    }

    @Override
    public void updateCommerceItem(ShoppingCart shoppingCart, String skuId, long quantity,long saleQuantity) throws PurchaseException, ItemException {
        if(StringUtils.isEmpty(skuId)){
            throw new PurchaseException("add item to order the sku id is empty");
        }
        ProductIndexDto productDto = productHelper.findProductById( skuId );
        String productName = "";
        if (productDto != null && productDto.getSaleName()!= null){
            productName = productDto.getSaleName();
        }
        if(quantity <= 0){
            throw new PurchaseException("add item to order the quantity can't  below 0");
        }
        OrderDto order = shoppingCart.getCurrentOrder();
        CommerceItemDto cItem = order.getCommerceItemBySkuId(skuId);
        if (cItem == null) {
            throw new PurchaseException("can't find item by skuId:" + skuId);
        }
        ProdSellPriceInfoDto prodSellPriceInfoDto = getProductPriceInfo(skuId, shoppingCart.getBranchCompanyId());
        if(prodSellPriceInfoDto == null || (prodSellPriceInfoDto.getStatus() != null && prodSellPriceInfoDto.getStatus() == 0)){
            log.info("该商品不支持当前区域销售skuId {},branchCompanyId {} prodSellPriceInfoDto 失效状态 status{},",skuId,shoppingCart.getBranchCompanyId());
            throw new ItemException(AdviceMessage.NOT_SALE_PLACE);
        }
        int maxQuantity = Integer.MAX_VALUE;
        Integer minNumber = 1;
        Integer salesInsideNumber = 1;
        if (prodSellPriceInfoDto.getMaxNumber() != null){
            maxQuantity = prodSellPriceInfoDto.getMaxNumber();
        }
        if (prodSellPriceInfoDto.getMinNumber() != null){
            minNumber = prodSellPriceInfoDto.getMinNumber();
        }
        if (prodSellPriceInfoDto.getSalesInsideNumber() != null){
            salesInsideNumber = prodSellPriceInfoDto.getSalesInsideNumber();
        }
        if (quantity > maxQuantity ){
            log.info("订购数量大于最大订购量,订购数量是:{},最大订购数量是:{},仓库是:{},商品id:{}",quantity ,maxQuantity,shoppingCart.getDeliveryWarehouseCode(),skuId);
            throw new ItemException(AdviceMessage.DEAR+productName+AdviceMessage.MORE_THAN_MAX_NUMBER);
        }
        if (quantity < minNumber){
            log.info("订购数量小于最小订购量,订购数量是:{},最小订购数量是:{},仓库是:{},商品id:{}",quantity ,minNumber,shoppingCart.getDeliveryWarehouseCode(),skuId);
            throw new ItemException(AdviceMessage.DEAR+productName+AdviceMessage.LESS_THAN_MIN_NUMBER);
        }

        if ( salesInsideNumber > 0){
            if (quantity % salesInsideNumber !=0){
                log.info("订购数量必须是内装数的整数倍,订购数量是:{},内装数量是:{},仓库是:{},商品id:{}",quantity ,salesInsideNumber,shoppingCart.getDeliveryWarehouseCode(),skuId);
                throw new ItemException(AdviceMessage.DEAR+productName+AdviceMessage.INTEGER_MULTIPLE);
            }
        }
        cItem.setQuantity(quantity);
        cItem.setSaleQuantity(saleQuantity);
        cItem.setSelected(true);
        if(productHelper.isBundleProduct(productDto.getProductType())) {
            configBundleCommerceItem(cItem, productDto, shoppingCart.getBranchCompanyId());
        }
        repriceOrder(order, PricingOrderType.AMOUNT, createPriceContentMap(shoppingCart));
        updateShoppingCart(shoppingCart);
    }

    @Override
    public ProdSellPriceInfoDto getProductPriceInfo(String productId, String branchCompanyId) {

        return itemPricingEngine.getProductPriceInfo(productId, branchCompanyId);
    }

    public Map createPriceContentMap(ShoppingCart shoppingCart){

        Map contentMap = new HashMap();
        contentMap.put("branchCompanyId", shoppingCart.getBranchCompanyId());
        contentMap.put("storeId", shoppingCart.getStoreId());
        contentMap.put("franchiseeId", shoppingCart.getUserId());
        contentMap.put("getAvailablePromotions","getAvailablePromotions");
        return contentMap;
    }

    /**
     *
     *
     *
     *
     * 计算订单价格
     * @param order
     * @param type
     * @throws PurchaseException
     */
    @Override
    public void repriceOrder(OrderDto order, PricingOrderType type, Map contentMap) throws PurchaseException {
        repriceOrder(order,type,contentMap,false);
    }

    @Override
    public void repriceOrder(OrderDto order, PricingOrderType type, Map contentMap, boolean isMock) throws PurchaseException {
        try {
            if (PricingOrderType.AMOUNT.equals(type)) {
                pricingEngineService.pricingOrderAmount(order, contentMap, isMock);
            } else {
                pricingEngineService.pricingOrderTotal(order, contentMap, isMock);
            }
        } catch (PricingException e) {
            throw new PurchaseException(e.getMessage());
        }
    }

    /**
     * 保存地址信息
     * @param shoppingCart
     * @param address
     */
    @Override
    public void applyShippingAddress(ShoppingCart shoppingCart, AddressDto address) throws PurchaseException{

        OrderDto order = shoppingCart.getCurrentOrder();
        ShippingGroupDto shippingGroup = order.getShippingGroupDto();
        shippingGroup.setProvince(address.getProvince());
        shippingGroup.setCity(address.getCity());
        shippingGroup.setDistrict(address.getDistrict());
        shippingGroup.setDetailAddress(address.getDetailAddress());
        shippingGroup.setConsigneeName(address.getConsigneeName());
        shippingGroup.setCellphone(address.getPhoneNumber());
        shippingGroup.setPostcode(address.getPostcode());
        Map contentMap = createPriceContentMap(shoppingCart);
        contentMap.put("activeCoupons", order.getCouponActivities());
        repriceOrder(order, PricingOrderType.TOTAL, createPriceContentMap(shoppingCart));
        updateShoppingCart(shoppingCart);

    }

    @Override
    public void setPaymentMethod(ShoppingCart shoppingCart, String paymentMethod) {
        OrderDto order = shoppingCart.getCurrentOrder();
        if(order.getPaymentGroupDtos().size()>0 && order.getPaymentGroupDtos().get(0)!=null){
            PaymentGroupDto paymentGroup = order.getPaymentGroupDtos().get(0);
            paymentGroup.setAmount(order.getPriceInfoDto().getTotal());
            paymentGroup.setPaymentMethod(paymentMethod);
        }

    }

    /**
     * 提交订单
     * @param shoppingCart
     */
    @Override
    public OrderDto commitOrder(ShoppingCart shoppingCart) throws PurchaseException {

        preCommitOrder(shoppingCart);
        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setTimeout(300);
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = transactionManager.getTransaction(transactionDefinition);
        boolean isRollback = false;
        OrderDto orderDto = null;
        Order order = null;
        try {
            order = purchaseHelper.saveShoppingCart(shoppingCart);
            orderDto = convertToOrderDto(order, shoppingCart.getCurrentOrder().getPriceInfoDto().getTotal());
            postCommitOrder(shoppingCart);
        } catch (Exception ex) {
            log.error("提交订单失败：", ex);
            isRollback = true;
        } finally {
            if (isRollback) {
                transactionManager.rollback(txStatus);
            } else {
                transactionManager.commit(txStatus);
            }
        }

        if (!isRollback) {
            sendSubmitOrderMsg(order);
        }
        return orderDto;
    }

    private OrderDto convertToOrderDto(Order order, Double total) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setState(order.getState());
        orderDto.setShippingState(order.getShippingState());
        orderDto.setPaymentState(order.getPaymentState());
        orderDto.setOrderState(order.getOrderState());

        OrderPriceInfoDto orderPriceInfoDto = new OrderPriceInfoDto();
        orderPriceInfoDto.setTotal(total);

        orderDto.setPriceInfoDto(orderPriceInfoDto);
        return orderDto;
    }


    public void preCommitOrder(ShoppingCart shoppingCart){
        OrderDto currentOrder = shoppingCart.getCurrentOrder();
        Response<StoreDto> storeRes = organizationService.queryStoreById(shoppingCart.getStoreId());
        if(storeRes != null && storeRes.isSuccess() && storeRes.getResultObject() != null){
            StoreDto store = storeRes.getResultObject();
            String warehouseCode = store.getDeliveryWarehouseCode();
            if(StringUtils.isEmpty(warehouseCode)){
                warehouseCode = DEFAULT_WAREHOURSE_CODE;
            }
            currentOrder.setBranchCompanyArehouse(warehouseCode);
            if(currentOrder.isElectronicOrder()){
                currentOrder.setOrderType(OrderTypeEnum.ElectronicOrder.getOrderType());
            }else {
                if (store.getStoreType() != null && store.getStoreType() == 1) {
                    currentOrder.setOrderType(OrderTypeEnum.DirectSaleStore.getOrderType());
                } else {
                    currentOrder.setOrderType(OrderTypeEnum.FranchiseStore.getOrderType());
                }
            }
        }
        OrderPriceInfoDto priceInfo = currentOrder.getPriceInfoDto();
        List<PaymentGroupDto> paymentGroups = currentOrder.getPaymentGroupDtos();
        if(CollectionUtils.isEmpty(paymentGroups)){
            PaymentGroupDto paymentGroupDto = createDefaultPaymentGroup("onlinePayment");
            paymentGroups.add(paymentGroupDto);
        }
        paymentGroups.get(0).setAmount(priceInfo.getTotal());

    }


    @Override
    public void sendSubmitOrderMsg(Order order) {
        log.info("start send submit order msg: " + order.getId());
        OrderMessage msg = new OrderMessage();
        msg.setMssageType(OrderMessageType.SubmitOrder);
        msg.setOrderId(order.getId());
        orderMessageSender.sendMsg(msg);
        log.info("end send submit order msg: " + order.getId());
    }

    public  void postCommitOrder(ShoppingCart shoppingCart) throws PurchaseException {
        clearCartAfterCommit(shoppingCart);
        repriceOrder(shoppingCart.getCurrentOrder(), PricingOrderType.TOTAL, createPriceContentMap(shoppingCart));
        updateShoppingCart(shoppingCart);

    }


    public void clearCartAfterCommit(ShoppingCart shoppingCart){
        OrderDto currentOrder = shoppingCart.getCurrentOrder();
        List<CommerceItemDto> items = currentOrder.getItems();
        if(items != null){
            Iterator<CommerceItemDto> iter = items.iterator();
            while (iter.hasNext()){
                CommerceItemDto item = iter.next();
                if(item.isSelected()){
                    iter.remove();
                }
            }
        }
        //初始化价格信息
        currentOrder.setPriceInfoDto(new OrderPriceInfoDto());
        //设置默认发票信息
        currentOrder.setInvoiceInfoDto(createDefaultInvoiceInfo(shoppingCart.getUserId()));
        //默认配送方式为雅堂到家
        ShippingGroupDto shippingGroup = createDefaultShippingGroup("YTS");
        currentOrder.setShippingGroupDto(shippingGroup);
        currentOrder.getCouponActivities().clear();
        //默认支付方式为线上付款
        PaymentGroupDto paymentGroup = createDefaultPaymentGroup("onlinePayment");
        currentOrder.setPaymentGroupDtos(new ArrayList<PaymentGroupDto>());
        currentOrder.getPaymentGroupDtos().add(paymentGroup);
        //重置是否使用优惠券设置
        currentOrder.setUseCoupon(false);
        currentOrder.setHasNotSuperposedPromotion(false);
    }

    /**
     * 新增或修改发票信息并更新购物车
     * @param cart
     */
    @Override
    public void updateInvoiceInfo(ShoppingCart cart,InvoiceInfoDto invoiceInfoDto) throws PurchaseException{

        String invoiceId = invoiceInfoDto.getInvoiceId();
        InvoiceInfoDto currentInvoice = cart.getCurrentOrder().getInvoiceInfoDto();
        currentInvoice.setInvoiceType(invoiceInfoDto.getInvoiceType());
        currentInvoice.setInvoiceId(invoiceId);
        if(InvoiceType.common.getValue().equals(invoiceInfoDto.getInvoiceType())){
            currentInvoice.setInvoiceTitle(invoiceInfoDto.getInvoiceTitle());
        }else if(InvoiceType.valueAdded.getValue().equals(invoiceInfoDto.getInvoiceType())){
            currentInvoice.setCompanyName(invoiceInfoDto.getCompanyName());
            currentInvoice.setTaxpayerIdentificationNumber(invoiceInfoDto.getTaxpayerIdentificationNumber());
            currentInvoice.setRegisteredAddress(invoiceInfoDto.getRegisteredAddress());
            currentInvoice.setCompanyPhone(invoiceInfoDto.getCompanyPhone());
            currentInvoice.setDepositBank(invoiceInfoDto.getDepositBank());
            currentInvoice.setAccountNumber(invoiceInfoDto.getAccountNumber());
        }
        log.info("shopping cart:{}", JSON.toJSONString(cart));

        updateShoppingCart(cart);
        updateUserInvoiceInfo(cart.getUserId(), invoiceInfoDto);
    }


    public void updateUserInvoiceInfo(String profileId, InvoiceInfoDto invoiceInfoDto){
        UserInvoiceInfo invoiceInfoValidate = invoiceInfoService.getInvoiceByProfileId(profileId);
        if(invoiceInfoValidate != null){
            copyInvoiceInfo(invoiceInfoDto, invoiceInfoValidate);
            invoiceInfoValidate.setUpdateDatetime(new Date());
            invoiceInfoService.updateByPrimaryKeySelective(invoiceInfoValidate);
        }else{
            UserInvoiceInfo invoiceInfo = new UserInvoiceInfo();
            invoiceInfo.setProfileId(profileId);
//            invoiceInfo.setInvoiceId(invoiceInfoDto.getInvoiceId());
            invoiceInfo.setEntryDatetime(new Date());
            copyInvoiceInfo(invoiceInfoDto, invoiceInfo);
            invoiceInfoService.insertSelective(invoiceInfo);
        }

    }

    public void copyInvoiceInfo(InvoiceInfoDto src, UserInvoiceInfo dest){

        dest.setInvoiceType(src.getInvoiceType());
        if(InvoiceType.common.getValue().equals(src.getInvoiceType())){
            dest.setInvoiceTitle(src.getInvoiceTitle());
        }else if(InvoiceType.valueAdded.getValue().equals(src.getInvoiceType())){
            dest.setCompanyName(src.getCompanyName());
            dest.setCompanyName(src.getCompanyName());
            dest.setTaxpayerIdentificationNumber(src.getTaxpayerIdentificationNumber());
            dest.setRegisteredAddress(src.getRegisteredAddress());
            dest.setCompanyPhone(src.getCompanyPhone());
            dest.setDepositBank(src.getDepositBank());
            dest.setAccountNumber(src.getAccountNumber());
        }
    }

    public void copyInvoiceInfo(UserInvoiceInfo src, InvoiceInfoDto dest){

        dest.setInvoiceTitle(src.getInvoiceTitle());
        dest.setCompanyName(src.getCompanyName());
        dest.setCompanyName(src.getCompanyName());
        dest.setTaxpayerIdentificationNumber(src.getTaxpayerIdentificationNumber());
        dest.setRegisteredAddress(src.getRegisteredAddress());
        dest.setCompanyPhone(src.getCompanyPhone());
        dest.setDepositBank(src.getDepositBank());
        dest.setAccountNumber(src.getAccountNumber());
    }



    /**
     * 只选择一个或者不选
     * @param shoppingCart
     * @param skuId
     * @param b
     */
    @Override
    public void selectOne(ShoppingCart shoppingCart, String skuId, boolean b) throws PurchaseException{
        if(StringUtils.isEmpty(skuId)){
            throw new PurchaseException("SelectItem the sku id is empty");
        }
        OrderDto order = shoppingCart.getCurrentOrder();
        CommerceItemDto cItem = order.getCommerceItemBySkuId(skuId);
        if (cItem == null) {
            throw new PurchaseException("can't find item by skuId:" + skuId);
        }
        cItem.setSelected( b );
        repriceOrder(order, PricingOrderType.AMOUNT, createPriceContentMap(shoppingCart));
        updateShoppingCart(shoppingCart);
    }

    /**
     * 全选或者全不选
     * @param shoppingCart
     * @param b
     */
    @Override
    public void selectAllOrNone(ShoppingCart shoppingCart, boolean b)throws PurchaseException{

        OrderDto order = shoppingCart.getCurrentOrder();
        List<CommerceItemDto> cItems = order.getItems();
        for (CommerceItemDto commerceItemDto:cItems){
            commerceItemDto.setSelected( b );
        }
        repriceOrder(order, PricingOrderType.AMOUNT, createPriceContentMap(shoppingCart));
        updateShoppingCart(shoppingCart);

    }

    /**
     * 勾选购物车中的选中或者不选中
     * @param shoppingCart
     * @param itemSelectDtos
     */
    public void selectOrNot(ShoppingCart shoppingCart, List<ItemSelectDto> itemSelectDtos)throws PurchaseException{

        OrderDto order = shoppingCart.getCurrentOrder();
        List<CommerceItemDto> cItems = order.getItems();
        if (cItems != null && itemSelectDtos !=null){
            for (CommerceItemDto commerceItemDto:cItems){
                for (ItemSelectDto itemSelectDto:itemSelectDtos){
                    if (commerceItemDto.getSkuId().equals(itemSelectDto.getSkuId())){
                        if (itemSelectDto.getSelect()!=1 || !commerceItemDto.isAvailable()){
                            commerceItemDto.setSelected( false);
                        }else{
                            commerceItemDto.setSelected(true);
                        }
                    }
                }
            }
        }
        repriceOrder(order, PricingOrderType.AMOUNT, createPriceContentMap(shoppingCart));
        updateShoppingCart(shoppingCart);
    }

    @Override
    public String directCommitOrder(DirectCommitOrderDto directCommitOrderDto) throws PurchaseException{

        //根据门店id查询加盟商信息
        Response<StoreDto> storeRes = organizationService.queryStoreById(directCommitOrderDto.getStoreId());
        if(storeRes == null || !storeRes.isSuccess() || storeRes.getResultObject()==null){
            log.info("直营店 {} 后端下单，can't find store",directCommitOrderDto.getStoreId());
            throw new PurchaseException("门店不存在");
        }
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setBranchCompanyId(storeRes.getResultObject().getBranchCompanyId());
        shoppingCart.setDeliveryWarehouseCode(storeRes.getResultObject().getDeliveryWarehouseCode());
        shoppingCart.setStoreId(directCommitOrderDto.getStoreId());
        shoppingCart.setUserId(storeRes.getResultObject().getFranchiseeId());
        OrderDto orderDto = createOrder(storeRes.getResultObject().getFranchiseeId(), shoppingCart.getDeliveryWarehouseCode());
        orderDto.setBranchCompanyId(shoppingCart.getBranchCompanyId());
        orderDto.setFranchiseeId(storeRes.getResultObject().getFranchiseeId());
        orderDto.setFranchiseeStoreId(shoppingCart.getStoreId());
        orderDto.setBranchCompanyArehouse(shoppingCart.getDeliveryWarehouseCode());

        AddressDto address = convertToAddress(storeRes.getResultObject());
        ShippingGroupDto shippingGroup = orderDto.getShippingGroupDto();
        shippingGroup.setProvince(address.getProvince());
        shippingGroup.setCity(address.getCity());
        shippingGroup.setDistrict(address.getDistrict());
        shippingGroup.setDetailAddress(address.getDetailAddress());
        shippingGroup.setConsigneeName(address.getConsigneeName());
        shippingGroup.setCellphone(address.getPhoneNumber());
        shippingGroup.setPostcode(address.getPostcode());

        orderDto.setShippingGroupDto(shippingGroup);
        shoppingCart.setCurrentOrder(orderDto);
        preWebDirectOrder(shoppingCart,directCommitOrderDto.getDirectStoreCommerItemList());
        preCommitOrder(shoppingCart);

        DefaultTransactionDefinition transactionDefinition = new DefaultTransactionDefinition();
        transactionDefinition.setTimeout(300);
        transactionDefinition.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus txStatus = transactionManager.getTransaction(transactionDefinition);
        boolean isRollback = false;
        Order order = null;
        try {
            order = purchaseHelper.saveShoppingCart(shoppingCart);
            log.info("直营店 {} 后端下单，保存订单 orderId:{}", shoppingCart.getStoreId(), order.getId());
        } catch (Exception ex) {
            log.error("提交订单失败：", ex);
            isRollback = true;
        } finally {
            if (isRollback) {
                transactionManager.rollback(txStatus);
            } else {
                transactionManager.commit(txStatus);
            }
        }
        if (!isRollback) {
            sendSubmitOrderMsg(order);
        }
        return order.getId();
    }


    private void preWebDirectOrder(ShoppingCart cart, List<DirectStoreCommerItemDto> directStoreCommerItemDtoList) throws PurchaseException{

        Map contentMap = createPriceContentMap(cart);
        OrderDto orderDto = cart.getCurrentOrder();
        boolean hasCouponProduct=false;
        boolean hasDefaultProduct=false;
        for (DirectStoreCommerItemDto dto : directStoreCommerItemDtoList) {
            if (!checkInventory(dto.getProductId(), dto.getQuantity(), cart.getDeliveryWarehouseCode(), cart.getBranchCompanyId())) {
                log.info("后台直营店下单{}库存不足",dto.getProductId());
                throw new PurchaseException("商品库存不足 "+dto.getProductId());
            }
            CommerceItemDto commmerceItemDto = createCommerceItem(dto.getProductId(),dto.getQuantity(), cart.getBranchCompanyId());
            if (CommerceItemTypes.COUPON.equals(commmerceItemDto.getType())) {
                hasCouponProduct = true;
            }else {
                hasDefaultProduct = true;
            }
            orderDto.addItem(commmerceItemDto);
        }
        if (hasDefaultProduct && hasCouponProduct) {
            throw new PurchaseException("虚拟商品和实物商品不能在同一个订单内生成，请删除其中一类商品。");
        }

        try {
            pricingEngineService.pricingOrderTotal(cart.getCurrentOrder(),contentMap,false);
        }catch (PricingException e){
            e.printStackTrace();
            log.info("直营店 {} 后端下单 商品价格计算异常 {}",cart.getStoreId(),e.getMessage());
            throw new PurchaseException("商品价格计算异常"+e.getMessage());
        }
        log.info("直营店 {} 后端下单，订单执行优惠后总价 {}",cart.getStoreId(),cart.getCurrentOrder().getPriceInfoDto().getTotal());
    }

    private AddressDto convertToAddress(StoreDto store) {
        log.info("直营店{}地址信息转换.",store.getId());
        List<String> codes = new ArrayList<String>();
        List<RegionDTO> regionDTOs = new ArrayList<RegionDTO>();
        AddressDto addressDto = new AddressDto();

        String proviceId = store.getProvinceId();
        String cityId = store.getCityId();
        String district = store.getDistrictId();
        codes.add(proviceId);
        codes.add(cityId);
        codes.add(district);
        Response<List<RegionDTO>> regionResp = regionDubboService.selectRegionListByMultiCode(codes);
        if (regionResp.isSuccess()) {
            regionDTOs = regionResp.getResultObject();
        } else {
            regionResp.setErrorMessage("获取省市区失败！");
        }

        for (RegionDTO regionDTO : regionDTOs) {
            if (proviceId.equals(regionDTO.getCode())) {
                addressDto.setProvince(regionDTO.getRegionName());
            } else if (cityId.equals(regionDTO.getCode())) {
                addressDto.setCity(regionDTO.getRegionName());
            } else if (district.equals(regionDTO.getCode())) {
                addressDto.setDistrict(regionDTO.getRegionName());
            }
        }

        addressDto.setDetailAddress(store.getAddress());
        addressDto.setConsigneeName(store.getContact());
        addressDto.setPhoneNumber(store.getMobilePhone());

        return addressDto;
    }

    public boolean checkInventory(String productId, long quantity, String deliveryWarehouseCode, String branchCompanyId) {

        //检查库存
        ItemInventoryDto itemInventoryDto = new ItemInventoryDto();
        itemInventoryDto.setProductId(productId);
        itemInventoryDto.setItemQty(quantity);
        itemInventoryDto.setLoc(deliveryWarehouseCode);
        itemInventoryDto.setBranchCompanyId(branchCompanyId);
        Response<Boolean> repCK = inventoryDubboService.checkInventory(itemInventoryDto);
        if (repCK.isSuccess() && !repCK.getResultObject()) {
            return false;
        }
        return true;

    }

    /**
     * 后台下单，创建commerceItem
     * @param productId
     * @param quantity
     * @param branchCompanyId
     * @return
     */
    private CommerceItemDto createCommerceItem(String productId, long quantity, String branchCompanyId) {
        CommerceItemDto result = null;
        ProductIndexDto productDto = productHelper.findProductById(productId);
        if (productDto == null) {
            log.info("createCommerceItem，skuId :{} 未查询到商品信息。", productId);
            return result;
        }
        ProdSellPriceInfoDto prodSellPriceInfoDto = getProductPriceInfo(productId, branchCompanyId);
        if (prodSellPriceInfoDto == null || (prodSellPriceInfoDto.getStatus() != null && prodSellPriceInfoDto.getStatus() == 0)) {
            log.info("createCommerceItem，branchCompanyId:{}, skuId :{} 未查询到商品 {} 销售关系。", branchCompanyId, productId, productDto.getSaleName());
            return result;
        }
        //销售内装数
        int salesInsideNumber = 1;
        if (prodSellPriceInfoDto != null && prodSellPriceInfoDto.getSalesInsideNumber() != null && prodSellPriceInfoDto.getSalesInsideNumber() > 0) {
            salesInsideNumber = prodSellPriceInfoDto.getSalesInsideNumber();
        }
        log.info("createCommerceItem，branchCompanyId:{} skuId :{} 商品 {} 销售内装数 {}", branchCompanyId, productId, productDto.getSaleName(), salesInsideNumber);
        CommerceItemDto commerceItemDto = new CommerceItemDto();
        commerceItemDto.setQuantity(quantity);
        commerceItemDto.setSaleQuantity(quantity);
        if (productDto.getSellFullCase() != null && productDto.getSellFullCase() == 1) {
            commerceItemDto.setQuantity(quantity * salesInsideNumber);
            commerceItemDto.setSellFullCase(1);
        }
        commerceItemDto.setProductName(productDto.getSaleName());
        commerceItemDto.setProductCode(productDto.getProductCode());
        commerceItemDto.setProductImg(productDto.getThumbnailImage());
        commerceItemDto.setSkuId(productId);
        commerceItemDto.setProductId(productId);
        commerceItemDto.setUnitQuantity(salesInsideNumber);
        commerceItemDto.setSelected(true);
        String productType = productDto.getProductType();
        if (productHelper.isCouponProduct(productType)) {
            commerceItemDto.setType(CommerceItemTypes.COUPON);
        } else {
            commerceItemDto.setType(CommerceItemTypes.DEFAULT);
        }
        return commerceItemDto;

    }


    /**
     * 批量删除购物车中的商品
     * @param cart
     * @param deleteCartItems
     */
    @Override
    public void batchDeletetItem(ShoppingCart cart, List<DeleteCartItemDto> deleteCartItems) throws PurchaseException {
        if(CollectionUtils.isEmpty(deleteCartItems)){
            throw new PurchaseException("批量删除购物车中的商品个数是0");
        }
        OrderDto order = cart.getCurrentOrder();
        Iterator<CommerceItemDto> iterator = order.getItems().iterator();
        while (iterator.hasNext()){
            CommerceItemDto item = iterator.next();
            Iterator<DeleteCartItemDto> deleteCartItemsIterator = deleteCartItems.iterator();
            while (deleteCartItemsIterator.hasNext()){
                DeleteCartItemDto deleteCartItemDto = deleteCartItemsIterator.next();
                if(item.getSkuId().equals(deleteCartItemDto.getSkuId())){
                    iterator.remove();
                    break;
                }
            }
        }
        repriceOrder(order, PricingOrderType.AMOUNT, createPriceContentMap(cart));
        updateShoppingCart(cart);
    }

    @Override
    public void oneKeyToDelete(ShoppingCart cart) throws PurchaseException {
        OrderDto orderDto = cart.getCurrentOrder();
        if (orderDto != null){
            if (CollectionUtils.isNotEmpty(orderDto.getItems())){
                Iterator<CommerceItemDto> iterator = orderDto.getItems().iterator();
                while (iterator.hasNext()){
                    CommerceItemDto item = iterator.next();
                    ItemInventoryDto itemInventoryDto = new ItemInventoryDto();
                    itemInventoryDto.setProductId(item.getProductId());
                    itemInventoryDto.setItemQty(item.getQuantity());
                    itemInventoryDto.setLoc(cart.getDeliveryWarehouseCode());
                    itemInventoryDto.setBranchCompanyId(cart.getBranchCompanyId());
                    if (inventoryDubboService == null){
                        log.error("oneKeyToDelete 时调用库存服务失败");
                        throw new PurchaseException("调用库存服务失败");
                    }
                    Response<Boolean> repCK = inventoryDubboService.checkInventory(itemInventoryDto);
                    if (repCK.isSuccess() && !repCK.getResultObject()) {
                        iterator.remove();
                    }
                }
            }
        }
        repriceOrder(orderDto, PricingOrderType.AMOUNT, createPriceContentMap(cart));
        updateShoppingCart(cart);
    }

    @Override
    public CommerceItemDto addItemBuyAgain(CommerceItem commerceItem,ShoppingCart cart) throws Exception{

        OrderDto order = cart.getCurrentOrder();
        CommerceItemDto commerceItemDto = order.getCommerceItemBySkuId(commerceItem.getSkuId());
        //再次购买如果购物车中已经有该商品，则不管
        if(commerceItemDto != null){
            return null;
        }
        long quantity = 1;
        ProductIndexDto productIndexDto = productHelper.findProductById(commerceItem.getProductId());
        //校验商品能否被添加：失效、下架商品不能被添加
        ProdSellPriceInfoDto prodSellPriceInfoDto = mProdSellPriceInfoHelper.getGoodsSellPrice(commerceItem.getProductId(),cart.getBranchCompanyId());

        //再次购买的商品数量，取最小起订量
        if(prodSellPriceInfoDto.getMinNumber() != null && prodSellPriceInfoDto.getMinNumber() != 0){
            quantity = prodSellPriceInfoDto.getMinNumber();
        }
        if(!checkProductEnable(quantity,commerceItem,productIndexDto,prodSellPriceInfoDto,cart.getDeliveryWarehouseCode(),cart.getBranchCompanyId())){
            return null;
        }

        int salesInsideNumber = prodSellPriceInfoDto.getSalesInsideNumber();
        int isSellFullCase = 0;//是否按箱销售1是，0否

        long saleQuantity = quantity;
        if(productIndexDto.getSellFullCase() != null && productIndexDto.getSellFullCase() == 1){
            isSellFullCase = 1;
            saleQuantity = quantity/salesInsideNumber;
        }

        CommerceItemDto commerceItemDto2 = new CommerceItemDto();
        commerceItemDto2.setSellFullCase(isSellFullCase);
        commerceItemDto2.setSkuId(productIndexDto.getId());
        commerceItemDto2.setProductId(productIndexDto.getId());
        commerceItemDto2.setProductName(productIndexDto.getSaleName());
        commerceItemDto2.setProductCode(productIndexDto.getProductCode());
        commerceItemDto2.setProductImg(productIndexDto.getThumbnailImage());
        commerceItemDto2.setQuantity(quantity);
        commerceItemDto2.setSaleQuantity(saleQuantity);
        commerceItemDto2.setUnitQuantity(salesInsideNumber);
        commerceItemDto2.setSelected(true);
        setSaleItemProperty(commerceItemDto2,prodSellPriceInfoDto);
        if(productHelper.isCouponProduct(productIndexDto.getProductType())){
            commerceItemDto2.setType(CommerceItemTypes.COUPON);
        }else if(productHelper.isBundleProduct(productIndexDto.getProductType())){
            commerceItemDto2.setType(CommerceItemTypes.BUNDLE);
            configBundleCommerceItem(commerceItemDto2, productIndexDto, cart.getBranchCompanyId());
        } else{
            commerceItemDto2.setType(CommerceItemTypes.DEFAULT);
        }
        if("1".equals(productIndexDto.getProductType())){
            commerceItemDto2.setType(CommerceItemTypes.DEFAULT);
        }else if("2".equals(productIndexDto.getProductType())){
            commerceItemDto2.setType(CommerceItemTypes.BUNDLE);
        }
        cart.getCurrentOrder().addItem(commerceItemDto2);
        return commerceItemDto2;
    }

    @Override
    public ItemInventoryDto createItemInventoryDto(String productId, String warehouseCode, String branchCompanyId, long quantity, String productName){

        ItemInventoryDto itemInventoryDto = new ItemInventoryDto();
        itemInventoryDto.setProductId(productId);
        itemInventoryDto.setItemQty(quantity);
        itemInventoryDto.setLoc(warehouseCode);
        itemInventoryDto.setBranchCompanyId(branchCompanyId);
        itemInventoryDto.setProductName(productName);
        return itemInventoryDto;
    }



    private boolean checkProductEnable(long quantity,CommerceItem commerceItem,ProductIndexDto productIndexDto, ProdSellPriceInfoDto prodSellPriceInfoDto,String warehouseCode,String branchCompanyId) {
        if(productIndexDto == null){
            log.info("getBuyAgainAddItemDto,未查询到商品,productId:{}",commerceItem.getProductId());
            return false;
        }
        if(prodSellPriceInfoDto == null){
            log.info("getBuyAgainAddItemDto,未查询到商品销售关系,productId:{}",commerceItem.getProductId());
            return false;
        }
        if(!"2".equals(productIndexDto.getSupplyChainStatus())){
            log.info("getBuyAgainAddItemDto,下架商品不添加,productId:{}",commerceItem.getProductId());
            return false;
        }
        //赠品不添加
        if(CommerceItemTypes.PROMOTION.equals(commerceItem.getType())){
            log.info("getBuyAgainAddItemDto,赠品不添加,productId:{}",commerceItem.getProductId());
            return false;
        }
        //失效商品不添加
        if(prodSellPriceInfoDto.getStatus() == 0 || prodSellPriceInfoDto.getDeleteStatus() == 1){
            log.info("getBuyAgainAddItemDto,失效商品不添加,productId:{}",commerceItem.getProductId());
            return false;
        }
        //最小起订量为空或者0、购买数量为0、销售内装数为空或者0，不添加
        if(prodSellPriceInfoDto.getMinNumber() == null || prodSellPriceInfoDto.getMinNumber() == 0
                || quantity == 0 || prodSellPriceInfoDto.getSalesInsideNumber() == 0){
            log.info("getBuyAgainAddItemDto,销售关系有误商品不添加,productId:{}",commerceItem.getProductId());
            return false;
        }

        ItemInventoryDto itemInventoryDto = createItemInventoryDto(productIndexDto.getId(), warehouseCode, branchCompanyId, quantity, productIndexDto.getSaleName());
        Response<Boolean> repCK = inventoryDubboService.checkInventory(itemInventoryDto);
        if (repCK == null || !repCK.isSuccess() || !repCK.getResultObject()) {
            log.info("buyItemsAgain purchaseAgain failed:库存不足 ,productId:{},warehouseCode:{},branchCompanyId:{}",productIndexDto.getId(),warehouseCode,branchCompanyId);
            return false;
        }
        return true;
    }

    private long calcuQuantity(long quantity, ProdSellPriceInfoDto prodSellPriceInfoDto) {
        int salesInsideNumber = prodSellPriceInfoDto.getSalesInsideNumber();
        //如果数量小于等于内装数，数量取内装数
        if(quantity <= salesInsideNumber){
            quantity = salesInsideNumber;
        }else{
            //如果数量不是箱数的整数倍，就取向上+1箱的数量
            long remainder = quantity % salesInsideNumber;
            if(remainder != 0){
                quantity = quantity - remainder + salesInsideNumber;
            }
        }
        //1,数量小于最起订量，数量直接取最小起订量,
        //2,最大订购量为空，直接往下执行；最大订购量不为空且数量大于最大订购量，数量取最大订购量
        if(quantity <= prodSellPriceInfoDto.getMinNumber()){
            quantity = prodSellPriceInfoDto.getMinNumber();
        }else if(prodSellPriceInfoDto.getMaxNumber() != null && quantity > prodSellPriceInfoDto.getMaxNumber()){
            //如果最大订购数量不是箱数的整数倍，就取向上+1箱的数量
            long remainder = prodSellPriceInfoDto.getMaxNumber() % salesInsideNumber;
            if(remainder != 0){
                quantity = prodSellPriceInfoDto.getMaxNumber() - remainder + salesInsideNumber;
            }else{
                quantity = prodSellPriceInfoDto.getMaxNumber();
            }
        }
        return quantity;
    }
}
