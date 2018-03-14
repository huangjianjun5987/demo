package com.yatang.sc.purchase.dubboservice.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.busi.common.resp.Response;
import com.busi.common.utils.StringUtils;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.yatang.sc.common.staticvalue.AdviceMessage;
import com.yatang.sc.facade.common.CommonsEnum;
import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.facade.dto.prod.place.ProdPlaceDto;
import com.yatang.sc.facade.dubboservice.prodplace.ProdPlaceQueryDubboService;
import com.yatang.sc.inventory.dto.BathCheckInventoryDto;
import com.yatang.sc.inventory.dto.ItemInventoryDto;
import com.yatang.sc.inventory.dubboservice.ItemLocInventoryDubboService;
import com.yatang.sc.order.domain.CommerceItem;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.order.domain.OrderDictionary;
import com.yatang.sc.order.service.CommerceItemService;
import com.yatang.sc.order.service.OrderDictionaryPropertiesService;
import com.yatang.sc.order.service.OrderService;
import com.yatang.sc.order.states.CommerceItemTypes;
import com.yatang.sc.product.service.ProductHelper;
import com.yatang.sc.purchase.comparator.ShoppingCartItemComparator;
import com.yatang.sc.purchase.dto.*;
import com.yatang.sc.purchase.dubboservice.PurchaseDubboService;
import com.yatang.sc.purchase.enums.PricingOrderType;
import com.yatang.sc.purchase.exception.ItemException;
import com.yatang.sc.purchase.exception.PricingException;
import com.yatang.sc.purchase.exception.PurchaseException;
import com.yatang.sc.purchase.service.PurchaseService;
import com.yatang.xc.mbd.biz.region.dubboservice.RegionDubboService;
import com.yatang.xc.mbd.pi.es.dto.BundleProductDto;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;
import com.yatang.xc.oc.biz.adapter.RedisAdapterServie;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by qiugang on 7/10/2017.
 */
@Service("purchaseDubboService")
public class PurchaseDubboServiceImpl implements PurchaseDubboService {

    protected Logger log = LoggerFactory.getLogger(this.getClass());

    public static final String REDIS_SHOPING_CART_KEY_PREFIX = "redis_sc_shopping_cart:";

    public static final double LIMIT_MIN_AMOUNT = 500;

    public static final Long ITEM_TOTAL_COUNT = 500L;

    @Autowired
    PurchaseService purchaseService;

    @Autowired
    RegionDubboService regionDubboService;

    @Autowired
    RedisAdapterServie<String, String> redisDubboServie;

    @Autowired
    ItemLocInventoryDubboService inventoryDubboService;

    @Autowired
    private ProductHelper productHelper;

    @Autowired
    private OrderDictionaryPropertiesService orderDictionaryPropertiesService;

    private Map cartLockMap = new HashMap();

    @Autowired
    private OrderService orderService;

    @Autowired
    private CommerceItemService commerceItemService;

    @Autowired
    private ProdPlaceQueryDubboService prodPlaceQueryDubboService;

    @Override
    public Response<Boolean> initalShoppingCart(String userId, Map intialParams) {
        log.info("initalShoppingCart:userId:{},intialParams:{}", userId, JSON.toJSONString(intialParams));
        Response<Boolean> res = new Response<Boolean>();
        try {
            synchronized (getCartLock(userId)) {
                purchaseService.initalShoppingCart(userId, intialParams);
            }
            res.setResultObject(true);
            res.setSuccess(true);
        } catch (PurchaseException e) {
            res.setErrorMessage(e.getMessage());
            res.setSuccess(false);
        }
        return res;
    }

    @Override
    public Response<ShoppingCart> loadShoppingCart(String userId, boolean checkInventory) {
        log.info("loadShoppingCart:userId {}", userId);
        Response<ShoppingCart> res = new Response<ShoppingCart>();
        ShoppingCart cart = null;
        try {
            synchronized (getCartLock(userId)) {
                cart = purchaseService.loadShoppingCart(userId);
                repriceShoppingCart(cart, PricingOrderType.AMOUNT);
                List<CommerceItemDto> items = cart.getCurrentOrder().getItems();
                if(checkInventory){
                    List<String> outStockList = validateInventoryForCart(cart, false);
                    for(CommerceItemDto item : items){
                        if(outStockList.contains(item.getProductId())){
                            item.setStockEnough(false);
                        }else{
                            item.setStockEnough(true);
                        }
                    }
                } else {
                    for(CommerceItemDto item : items){
                       item.setStockEnough(true);
                    }
                }
            }
            cart.getCurrentOrder().setItems(new ArrayList<CommerceItemDto>(FluentIterable.from(cart.getCurrentOrder().getItems()).toSortedList(new ShoppingCartItemComparator())));
            res.setResultObject(cart);
            res.setCode(CommonsEnum.RESPONSE_200.getCode());
            res.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            res.setSuccess(true);
        } catch (PurchaseException e) {
            log.error("load shopping cart error. userId:{}", userId);
            e.printStackTrace();
            res.setSuccess(false);
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setErrorMessage("加载订单失败！");
            log.error(ExceptionUtils.getFullStackTrace(e));
        } catch (PricingException e) {
            log.error("load shopping cart error. userId:{}", userId);
            e.printStackTrace();
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return res;
    }

    @Override
    public Response<ShoppingCart> loadShoppingCart(String userId) {
        log.info("loadShoppingCart:userId {}", userId);
        Response<ShoppingCart> res = new Response<ShoppingCart>();
        ShoppingCart cart = null;
        try {
            synchronized (getCartLock(userId)) {
                cart = purchaseService.loadShoppingCart(userId);
            }
            cart.getCurrentOrder().setItems(new ArrayList<CommerceItemDto>(FluentIterable.from(cart.getCurrentOrder().getItems()).toSortedList(new ShoppingCartItemComparator())));
            res.setResultObject(cart);
            res.setCode(CommonsEnum.RESPONSE_200.getCode());
            res.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            res.setSuccess(true);
        } catch (PurchaseException e) {
            log.error("load shopping cart error. userId:{}", userId);
            e.printStackTrace();
            res.setSuccess(false);
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setErrorMessage("加载订单失败！");
            log.error(ExceptionUtils.getFullStackTrace(e));
        } catch (PricingException e) {
            log.error("load shopping cart error. userId:{}", userId);
            e.printStackTrace();
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return res;
    }
    
    @Override
    public Response<ShoppingCart> addItemToShoppingCart(String userId, AddItemDto addItem) {
        log.info("addItemToShoppingCart:userId {}", userId);
        log.info("addItem :" + JSONObject.toJSONString(addItem));

        Response<ShoppingCart> res = new Response<ShoppingCart>();
        try {
            synchronized (getCartLock(userId)) {
                ShoppingCart cart = purchaseService.loadShoppingCart(userId);
                if (cart != null) {
                    //购物车中商品数量的校验
                    Long itemTotal = getItemTotal();
                    Long cartSelectCount = cart.getCurrentOrder().getSelectOrderCount();
                    log.info("数据字典中所配的商品总种类是:{}", itemTotal);
                    log.info("订单中的总种类是:{}", cartSelectCount);
                    if (itemTotal <= cartSelectCount) {
                        res.setSuccess(false);
                        res.setCode(CommonsEnum.RESPONSE_500.getCode());
                        res.setErrorMessage("购物车最大允许选择商品种类为:" + itemTotal + "请先提交订单。");
                        res.setResultObject(cart);
                        return res;
                    }
                    //首先根据商品id 查询商品的是不是整箱销售
                    ProductIndexDto productDto = productHelper.findProductById(addItem.getSkuId());
                    if (productDto == null ) {
                        res.setSuccess(false);
                        res.setCode(CommonsEnum.RESPONSE_500.getCode());
                        res.setErrorMessage(AdviceMessage.PRODUCT_MISS);
                        res.setResultObject(cart);
                        return res;
                    }
                    addItem.setProduct(productDto);
                    ProdSellPriceInfoDto prodSellPriceInfoDto = purchaseService.getProductPriceInfo(addItem.getSkuId(), cart.getBranchCompanyId());
                    if (prodSellPriceInfoDto == null || (prodSellPriceInfoDto.getStatus() != null && prodSellPriceInfoDto.getStatus() == 0)) {
                        res.setSuccess(false);
                        res.setCode(CommonsEnum.RESPONSE_500.getCode());
                        res.setErrorMessage(AdviceMessage.NOT_SALE_PLACE);
                        res.setResultObject(cart);
                        return res;
                    }
                    addItem.setSellPriceInfo(prodSellPriceInfoDto);
                    Integer minNumber = 1;
                    if (prodSellPriceInfoDto.getMinNumber() != null) {
                        minNumber = prodSellPriceInfoDto.getMinNumber();
                    }

                    //销售内装数
                    Integer salesInsideNumber = 1;
                    if (prodSellPriceInfoDto != null && prodSellPriceInfoDto.getSalesInsideNumber() != null && prodSellPriceInfoDto.getSalesInsideNumber() > 0) {
                        salesInsideNumber = prodSellPriceInfoDto.getSalesInsideNumber();
                        log.info("can't find prodSellPriceInfoDto,skuId{},branchCompanyId{},SalesInsideNumber{}", addItem.getSkuId(), cart.getBranchCompanyId(), prodSellPriceInfoDto.getSalesInsideNumber());
                    }
                    boolean isSaleQuantity = false;
                    if (productDto.getSellFullCase() != null && productDto.getSellFullCase() == 1) {
                        isSaleQuantity = true;
                    }
                    long addQuantiy = addItem.getQuantity();
                    addItem.setSaleQuantity(addQuantiy);
                    long itemQuantiy = addItem.getQuantity();
                    if (isSaleQuantity) {
                        itemQuantiy = addItem.getQuantity() * salesInsideNumber;
                        addItem.setSellFullCase(1);
                    }
                    addItem.setQuantity(itemQuantiy);
                    addItem.setUnitQuantity(salesInsideNumber);
                    if (addItem.getQuantity() % salesInsideNumber != 0) {
                        res.setSuccess(false);
                        res.setCode(CommonsEnum.RESPONSE_500.getCode());
                        res.setErrorMessage(AdviceMessage.INTEGER_MULTIPLE);
                        res.setResultObject(cart);
                        return res;
                    }
                    if (addItem.getQuantity() < minNumber) {
                        res.setSuccess(false);
                        res.setCode(CommonsEnum.RESPONSE_500.getCode());
                        res.setErrorMessage(AdviceMessage.ADD_LESS_THAN_MIN_NUMBER);
                        res.setResultObject(cart);
                        return res;
                    }
                    if (!productHelper.isCouponProduct(productDto.getProductType())) {
                        if (!productHelper.isProviderProduct(productDto.getId(), cart.getStoreId()) && !validateInventoryForAddItem(addItem, cart)) {
                            log.info("{}:检查库存返回false", productDto.getId());
                            res.setSuccess(false);
                            res.setCode(CommonsEnum.RESPONSE_10041.getCode());
                            res.setErrorMessage(productDto.getSaleName() + CommonsEnum.RESPONSE_10041.getName() + ",请删除!");
                            return res;
                        }
                    }
                    //判断商品的最大购买数量
                    log.info("购物车:{},需要添加的商品:{}", JSONObject.toJSONString(cart), JSONObject.toJSONString(addItem));
                    purchaseService.addItemToShoppingCart(cart, addItem);
                    res.setSuccess(true);
                    res.setCode(CommonsEnum.RESPONSE_200.getCode());
                    res.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                    res.setResultObject(cart);
                } else {
                    res.setCode(CommonsEnum.RESPONSE_500.getCode());
                    res.setSuccess(false);
                    res.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
                    res.setResultObject(null);
                }
            }
        } catch (PurchaseException e) {
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setSuccess(false);
            res.setErrorMessage("添加购物车失败！");
            log.error(ExceptionUtils.getFullStackTrace(e));
        } catch (PricingException e) {
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setSuccess(false);
            res.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            log.error(ExceptionUtils.getFullStackTrace(e));
        } catch (ItemException e) {
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setSuccess(false);
            res.setErrorMessage(e.getMessage());
        }
        return res;
    }


    public boolean validateInventoryForAddItem(AddItemDto addItem, ShoppingCart cart){

        OrderDto order = cart.getCurrentOrder();
        long totalQty = addItem.getQuantity();
        log.info("validateInventoryForAddItem：{}",JSON.toJSONString(order));
        totalQty += order.getTotalQuantityBySkuId(addItem.getSkuId());
        ProductIndexDto product = addItem.getProduct();
        return validateProductInventory(product, cart.getDeliveryWarehouseCode(), cart.getBranchCompanyId(), totalQty);
    }

    @Override
    public Response<ShoppingCart> removeItem(String userId, String skuId) {
        log.info("userId :" + userId);
        log.info("skuId :" + skuId);
        Response<ShoppingCart> res = new Response<ShoppingCart>();
        try {
            synchronized (getCartLock(userId)) {
                ShoppingCart cart = purchaseService.loadShoppingCart(userId);
                if (cart != null) {
                    purchaseService.removeItem(cart, skuId);
                    //根据前端优化需求修改购物车时候不需要items,在controller层修改
                    //cart.getCurrentOrder().getItems().clear();
                    res.setCode(CommonsEnum.RESPONSE_200.getCode());
                    res.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                    res.setSuccess(true);
                    res.setResultObject(cart);
                } else {
                    res.setCode(CommonsEnum.RESPONSE_500.getCode());
                    res.setSuccess(false);
                    res.setErrorMessage(CommonsEnum.RESPONSE_500.getCode());
                    res.setResultObject(null);
                }
            }
        } catch (PurchaseException e) {
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setSuccess(false);
            res.setErrorMessage("删除购物车商品失败！");
            log.error(ExceptionUtils.getFullStackTrace(e));
        } catch (PricingException e) {
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setSuccess(false);
            res.setErrorMessage(CommonsEnum.RESPONSE_500.getCode());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return res;
    }

    @Override
    public Response<ShoppingCart> updateCommerceItem(String userId, String skuId, long quantity) {
        log.info("userId :" + userId + "skuId:" + skuId + "quantity :" + quantity);
        Response<ShoppingCart> res = new Response<ShoppingCart>();
        try {
            synchronized (getCartLock(userId)) {
                ShoppingCart cart = purchaseService.loadShoppingCart(userId);
                if (cart != null) {
                    /*Long itemTotal = getItemTotal();
                    Long cartSelectCount = cart.getCurrentOrder().getSelectOrderCount();
                    log.info("数据字典中所配的商品总种类:{}",itemTotal);
                    log.info("订单中的总种类:{}",cartSelectCount);
                    if (itemTotal <= cartSelectCount){
                        res.setSuccess(false);
                        res.setCode(CommonsEnum.RESPONSE_500.getCode());
                        res.setErrorMessage("购物车最大允许选择商品种类为:"+itemTotal+"请先提交订单。");
                        res.setResultObject(cart);
                        return res;
                    }*/
                    //检查库存
                    OrderDto order = cart.getCurrentOrder();
                    CommerceItemDto cItem = order.getCommerceItemBySkuId(skuId);
                    if (cItem == null) {
                        res.setSuccess(false);
                        res.setErrorMessage("数据错误！");
                        res.setCode(CommonsEnum.RESPONSE_500.getCode());
                        return res;
                    }
//                    ProdSellPriceInfoDto prodSellPriceInfoDto = purchaseService.getProductPriceInfo(skuId,cart.getBranchCompanyId());
                    ProductIndexDto productDto = productHelper.findProductById(skuId);
                    //销售内装数
                    Integer salesInsideNumber = cItem.getUnitQuantity();
                    //此处应该从主数据获取这个商品是否是整箱销售
                    boolean isSaleQuantity = false;
                    if (productDto.getSellFullCase() != null && productDto.getSellFullCase() == 1) {
                        isSaleQuantity = true;
                    }
                    long saleQuantity = quantity;
                    //如果是整箱销售
                    if (isSaleQuantity) {
                        log.info("整箱销售 数量是:{}", quantity);
                        quantity = quantity * salesInsideNumber;
                        log.info("整箱销售数量 * 销售内装数 :{}", quantity);
                    }
                    if (!productHelper.isProviderProduct(productDto.getId(), cart.getStoreId())) {
                        log.info("updateCommerceItem 库存检查.");
                        boolean checkInventory = validateProductInventory(productDto, cart.getDeliveryWarehouseCode(), cart.getBranchCompanyId(), quantity);
                        if (!checkInventory) {
                            res.setSuccess(false);
                            res.setCode(CommonsEnum.RESPONSE_10041.getCode());
                            res.setErrorMessage(cItem.getProductName() + CommonsEnum.RESPONSE_10041.getName() + ",请删除!");
                            return res;
                        }
                    }
                    purchaseService.updateCommerceItem(cart, skuId, quantity, saleQuantity);
                    //根据前端优化需求修改购物车时候不需要items,在controller层修改
                    //cart.getCurrentOrder().getItems().clear();
                    res.setCode(CommonsEnum.RESPONSE_200.getCode());
                    res.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                    res.setSuccess(true);
                    res.setResultObject(cart);
                } else {
                    res.setSuccess(false);
                    res.setCode(CommonsEnum.RESPONSE_500.getCode());
                    res.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
                    res.setResultObject(null);
                }
            }
        } catch (PurchaseException e) {
            res.setSuccess(false);
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setErrorMessage("修改商品数量失败！");
        } catch (PricingException e) {
            res.setSuccess(false);
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
        } catch (ItemException e) {
            res.setSuccess(false);
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setErrorMessage(e.getMessage());
        }
        return res;
    }


    public ItemInventoryDto createItemInventoryDto(String productId, String warehouseCode, String branchCompanyId, long quantity, String productName){

        ItemInventoryDto itemInventoryDto = new ItemInventoryDto();
        itemInventoryDto.setProductId(productId);
        itemInventoryDto.setItemQty(quantity);
        itemInventoryDto.setLoc(warehouseCode);
        itemInventoryDto.setBranchCompanyId(branchCompanyId);
        itemInventoryDto.setProductName(productName);
        return itemInventoryDto;
    }

    /**
     * 校验commerceItem 库存
     *
     */
    public boolean validateProductInventory(ProductIndexDto product, String warehouseCode, String branchCompanyId, long quantity){
        boolean result = true;
        if(productHelper.isBundleProduct(product.getProductType())){
            List<ItemInventoryDto> params = new ArrayList<ItemInventoryDto>();
            List<BundleProductDto> subProducts = product.getBundleProducts();
            if(CollectionUtils.isEmpty(subProducts)){
                return false;
            }

            for(BundleProductDto sp : subProducts){
                long subQty = quantity * sp.getProdCount().longValue();
                log.info("validate subItem {} qty {}", sp.getProductId(), subQty);
                ItemInventoryDto itemInventoryDto = createItemInventoryDto(sp.getProductId(), warehouseCode, branchCompanyId, subQty, sp.getName());
                params.add(itemInventoryDto);
            }
            Response<BathCheckInventoryDto> response = inventoryDubboService.batchCheckInventory(params);
            if(response == null || !response.isSuccess()){
                result = false;
            }
            if(result){
                BathCheckInventoryDto checkDto = response.getResultObject();
                if(checkDto != null && CollectionUtils.isNotEmpty(checkDto.getOutOfStock())){
                    result = false;
                }
            }
        } else {
            ItemInventoryDto itemInventoryDto = createItemInventoryDto(product.getId(), warehouseCode, branchCompanyId, quantity, product.getName());
            Response<Boolean> repCK = inventoryDubboService.checkInventory(itemInventoryDto);
            if (repCK == null || !repCK.isSuccess() || !repCK.getResultObject()) {
                result = false;
            }
        }
        return result;

    }

    @Override
    public Response<ShoppingCart> repriceShoppingCart(ShoppingCart cart, PricingOrderType type) {
        Response<ShoppingCart> res = new Response<ShoppingCart>();
        try {
            Map priceParams = purchaseService.createPriceContentMap(cart);
            purchaseService.repriceOrder(cart.getCurrentOrder(), type, priceParams);
            res.setSuccess(true);
            res.setResultObject(cart);
            res.setCode(CommonsEnum.RESPONSE_200.getCode());
            res.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
        } catch (PurchaseException e) {
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setSuccess(false);
            res.setErrorMessage("价格计算失败！");
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return res;
    }

    @Override
    public Response<ShoppingCart> checkout(String userId, AddressDto address) {
        log.info("applyShippingAddress :" + JSONObject.toJSONString(address));
        Response<ShoppingCart> res = new Response<ShoppingCart>();

        // 移除未选中的商品
        try {
            synchronized (getCartLock(userId)) {
                ShoppingCart cart = purchaseService.loadShoppingCart(userId);
//                log.info("load cart:{}",JSON.toJSONString(cart));
                purchaseService.checkout(cart, address);
//                log.info("result:{}",JSON.toJSONString(cart));
                res.setResultObject(cart);
                res.setSuccess(true);
            }
        } catch (Exception e) {
            res.setSuccess(false);
            res.setErrorMessage("应用地址失败！");
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return res;
    }

    @Override
    public Response<ShoppingCart> applyShippingAddress(String userId, AddressDto address) {
        log.info("applyShippingAddress :" + JSONObject.toJSONString(address));
        Response<ShoppingCart> res = new Response<ShoppingCart>();

        // 移除未选中的商品
        try {
            synchronized (getCartLock(userId)) {
                ShoppingCart cart = purchaseService.loadShoppingCart(userId);
                purchaseService.applyShippingAddress(cart, address);
                res.setResultObject(cart);
                res.setSuccess(true);
            }
        } catch (Exception e) {
            res.setSuccess(false);
            res.setErrorMessage("应用地址失败！");
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return res;
    }

    @Override
    public Response<ShoppingCart> setPaymentMethod(ShoppingCart cart, String paymentMethod) {
        Response<ShoppingCart> res = new Response<ShoppingCart>();
        try {
            purchaseService.setPaymentMethod(cart, paymentMethod);
            res.setSuccess(true);
            res.setResultObject(cart);
        } catch (PurchaseException e) {
            log.error("异常", e);
            res.setSuccess(false);
        }
        return res;
    }


    public Response<ShoppingCart> validateCommitOrder(ShoppingCart cart, boolean autoRemoveGift) {

        Response<ShoppingCart> res = new Response<ShoppingCart>();
        res.setSuccess(true);
        res.setResultObject(cart);
        OrderDictionary orderDictionary = orderDictionaryPropertiesService.selectByPrimaryKey("LIMIT_MIN_AMOUNT");
        Double limitMinAmount = 500d;
        if (orderDictionary != null) {
            limitMinAmount = Double.valueOf(orderDictionary.getPropertyValue());
        }
        if(cart.getCurrentOrder().getPriceInfoDto().getAmount()<=0){
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setSuccess(false);
            res.setErrorMessage("订单金额无效，不能提交订单~");
            return res;
        }
        if (cart.getCurrentOrder().getPriceInfoDto().getRawSubtotal() < limitMinAmount) {
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setSuccess(false);
            res.setErrorMessage(AdviceMessage.MONEY_LESS_THAN+limitMinAmount+"元哦~");
            return res;
        }
        Response<List<String>> resValidate = validateShoppingCart(cart,false);
        log.info("购物车提交订单时校验结果：{}", JSON.toJSONString(resValidate));
        if (!resValidate.isSuccess()) {
            res.setSuccess(false);
            res.setErrorMessage(resValidate.getErrorMessage());
            res.setCode(resValidate.getCode());
            return res;
        }

        //校验赠品
        try {
            Set<String>  lackGiftSet = new HashSet<String>();
            List<String> offLineGiftItems =  validateGiftSellInfo(cart);
            List<String> outOfStockList = validateInventoryForCart(cart, true);
            if(CollectionUtils.isNotEmpty(offLineGiftItems)){
                lackGiftSet.addAll(offLineGiftItems);
            }
            if(CollectionUtils.isNotEmpty(outOfStockList)){
                lackGiftSet.addAll(outOfStockList);
            }
            if (CollectionUtils.isNotEmpty(lackGiftSet)) {
                StringBuilder sb = new StringBuilder();
                OrderDto order = cart.getCurrentOrder();
                Iterator<CommerceItemDto> iterator = order.getItems().iterator();
                while (iterator.hasNext()) {
                    CommerceItemDto item = iterator.next();
                    if (CommerceItemTypes.PROMOTION.equals(item.getType()) && lackGiftSet.contains(item.getProductId())) {
                        if(autoRemoveGift) {
                            iterator.remove();
                        } else {
                            sb.append(item.getProductName()).append("\n");
                        }
                    }
                }
                if(sb.length() > 0){
                    res.setSuccess(false);
                    res.setCode(CommonsEnum.RESPONSE_10052.getCode());
                    res.setErrorMessage(AdviceMessage.GIFT_LACK_OF_STOCK_PRE + sb.toString() + AdviceMessage.GIFT_LACK_OF_STOCK_SUF);
                    return res;
                }
            }
        } catch (PurchaseException e) {
            res.setSuccess(false);
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setErrorMessage("校验库存失败！");
            return res;
        }
        return res;
    }


    public List<String> validateGiftSellInfo(ShoppingCart cart){
        List<String> result = new ArrayList<String>();
        for (CommerceItemDto item : cart.getCurrentOrder().getItems()) {
            if (CommerceItemTypes.PROMOTION.equals(item.getType())) {
                ProdSellPriceInfoDto prodSellPriceInfoDto = purchaseService.getProductPriceInfo(item.getSkuId(), cart.getBranchCompanyId());
                if (prodSellPriceInfoDto == null || (prodSellPriceInfoDto.getStatus() != null && prodSellPriceInfoDto.getStatus() == 0)) {
                    result.add(item.getSkuId());
                }
            }
        }
        return result;
    }
    /**
     * 结算和提交订单时都需要做的验证
     *
     * @param cart
     * @return
     */
    public Response<List<String>> validateShoppingCart(ShoppingCart cart,boolean ck) {
        OrderDto currentOrder = cart.getCurrentOrder();
        Response<List<String>> res = new Response<>();
        if (isMixedOrder(currentOrder)) {
            res.setSuccess(false);
            res.setErrorMessage("优惠券和商品不支持同时购买，请分别下单");
            return res;
        }

        Long cartSelectCount = cart.getCurrentOrder().getSelectOrderCount();
        log.info("订单中的总种类:{}", cartSelectCount);
        if (cartSelectCount == 0) {
            res.setSuccess(false);
            res.setErrorMessage("请选择要购买的商品！");
            return res;
        }
        Long itemTotal = getItemTotal();
        log.info("数据字典中所配的商品总种类:{}", itemTotal);
        if (cartSelectCount > itemTotal) {
            res.setSuccess(false);
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setErrorMessage("单笔订单最大允许商品种类为" + itemTotal + "请回购物车重新选择商品。");
            return res;
        }
        try {
            List<String> outOfStockList = validateInventoryForCart(cart, ck);
            if (CollectionUtils.isNotEmpty(outOfStockList)) {
                res.setSuccess(false);
                res.setCode(CommonsEnum.RESPONSE_10042.getCode());
                res.setResultObject(outOfStockList);
                res.setErrorMessage(AdviceMessage.SORRY + AdviceMessage.PRODUCT_LACK_OF_STOCK);
                return res;
            }
        } catch (PurchaseException e) {
            res.setSuccess(false);
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setErrorMessage("校验库存失败！");
            return res;
        }
        for (CommerceItemDto item : currentOrder.getItems()) {
            //赠品不校验
            if(!ck && CommerceItemTypes.PROMOTION.equals(item.getType())){
                continue;
            }
            if (item.isSelected()) {
                String productName = item.getProductName();
                ProdSellPriceInfoDto prodSellPriceInfoDto = purchaseService.getProductPriceInfo(item.getSkuId(), cart.getBranchCompanyId());
                log.info("commit order prodSellPriceInfoDto:{}",JSON.toJSONString(prodSellPriceInfoDto));
                if (prodSellPriceInfoDto == null || (prodSellPriceInfoDto.getStatus() != null && prodSellPriceInfoDto.getStatus() == 0)) {
                    res.setSuccess(false);
                    res.setErrorMessage("抱歉，您进货单中的" + productName + "暂时缺货或已下架，请结算其它商品");
                    res.setCode(CommonsEnum.RESPONSE_500.getCode());
                    return res;
                }

                //判断商品的最大购买数量
                if (prodSellPriceInfoDto.getMaxNumber() != null && item.getQuantity() > prodSellPriceInfoDto.getMaxNumber()) {
                    res.setSuccess(false);
                    res.setCode(CommonsEnum.RESPONSE_500.getCode());
                    log.error("要订购的数量{},所允许的最大订购数量{}", item.getQuantity(), prodSellPriceInfoDto.getMaxNumber());
                    res.setErrorMessage(productName + "订购数量大于最大订购数量");
                    return res;
                }
                //判断商品的最小购买数量
                if (prodSellPriceInfoDto.getMinNumber() != null && item.getQuantity() < prodSellPriceInfoDto.getMinNumber()) {
                    res.setSuccess(false);
                    res.setCode(CommonsEnum.RESPONSE_500.getCode());
                    log.error("要订购的数量{},所允许的最小订购数量{}", item.getQuantity(), prodSellPriceInfoDto.getMinNumber());
                    res.setErrorMessage(AdviceMessage.DEAR + productName + AdviceMessage.VILIDATE_LESS_THAN_MIN_NUMBER);
                    return res;
                }
            }
        }
        res.setSuccess(true);
        return res;
    }



    public  List<String> validateInventoryForCart(ShoppingCart cart, boolean checkGift) throws PurchaseException {

        List<CommerceItemDto> items = FluentIterable.from(cart.getCurrentOrder().getItems()).toSortedList(new ShoppingCartItemComparator());
        Map<String, ItemInventoryDto> itemInventoryDtoMap = new HashMap<String, ItemInventoryDto>();

        Set<String> productId = new HashSet<>();
        for (CommerceItemDto item : items) {
            if(!item.isSelected()){
                continue;
            }
            if (item.isElectronicItem()) {
                continue;
            }
            if(!checkGift && CommerceItemTypes.PROMOTION.equals(item.getType())){
                continue;
            }

            if(CommerceItemTypes.BUNDLE.equals(item.getType())){
                for(CommerceItemDto subItem : item.getSubItems()){
                    productId.add(subItem.getProductId());
                    if(itemInventoryDtoMap.containsKey(subItem.getProductId())){
                        ItemInventoryDto itemInventoryDto = itemInventoryDtoMap.get(subItem.getProductId());
                        itemInventoryDto.setItemQty(itemInventoryDto.getItemQty() + subItem.getQuantity());
                    }else{
                        ItemInventoryDto itemInventoryDto = createItemInventoryDto(subItem.getProductId(), cart.getDeliveryWarehouseCode(), cart.getBranchCompanyId(), subItem.getQuantity(), subItem.getProductName());
                        itemInventoryDtoMap.put(subItem.getProductId(), itemInventoryDto);
                    }
                }
            } else {
                productId.add(item.getProductId());
                //组装参数对象
                if(itemInventoryDtoMap.containsKey(item.getProductId())){
                    ItemInventoryDto itemInventoryDto = itemInventoryDtoMap.get(item.getProductId());
                    itemInventoryDto.setItemQty(itemInventoryDto.getItemQty() + item.getQuantity());
                }else{
                    ItemInventoryDto itemInventoryDto = createItemInventoryDto(item.getProductId(), cart.getDeliveryWarehouseCode(), cart.getBranchCompanyId(), item.getQuantity(), item.getProductName());
                    itemInventoryDtoMap.put(item.getProductId(), itemInventoryDto);
                }
            }
        }
        Response<BathCheckInventoryDto> response = inventoryDubboService.batchCheckInventory(Lists.newArrayList(itemInventoryDtoMap.values()));
        if (response == null || !response.isSuccess()) {
           throw  new PurchaseException("校验库存失败！");
        }

        List<String> isProviderProduct = new ArrayList<>();
        Response<List<ProdPlaceDto>> prodPlaceDtos = prodPlaceQueryDubboService.queryDirectDeliveryProduct(cart.getStoreId(), new ArrayList<String>(productId));
        if(prodPlaceDtos!= null && prodPlaceDtos.getResultObject()!= null) {
            for (ProdPlaceDto prodPlaceDto : prodPlaceDtos.getResultObject()) {
                if(prodPlaceDto.getLogisticsModel() == 0) {
                    isProviderProduct.add(prodPlaceDto.getProductId());//送货类型：0:直送，1:配送
                }
            }
        }
        Set<String> outOfStock = response.getResultObject().getOutOfStock();
        log.info("validateShoppingCart 库存检查：{}",JSON.toJSONString(response));
        List<String> outOfStockList = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(outOfStock)) {
            for (CommerceItemDto item : items) {
                if(isProviderProduct.contains(item.getProductId())){
                    log.info("供应商直送商品:{}",item.getProductId());
                    continue;
                }
                if (outOfStock.contains(item.getProductId())) {
                    outOfStockList.add(item.getProductId());
                } else {
                    for (CommerceItemDto sub : item.getSubItems()) {
                        if(isProviderProduct.contains(item.getProductId())){
                            log.info("供应商直送商品:{}",item.getProductId());
                            continue;
                        }
                        if (outOfStock.contains(sub.getProductId())) {
                            outOfStockList.add(sub.getProductId());
                            break;
                        }
                    }
                }
            }
        }
        return outOfStockList;
    }


    public boolean isMixedOrder(OrderDto order) {
        boolean result = false;
        List<CommerceItemDto> commerceItems = order.getItems();
        if (commerceItems == null || commerceItems.size() == 0) {
            return false;
        }
        boolean electronicOrder = false;
        boolean hardgoodOrder = false;
        for (CommerceItemDto commerceItemDto : commerceItems) {
            if(CommerceItemTypes.PROMOTION.equals(commerceItemDto.getType())){
                continue;
            }
            if (commerceItemDto.isSelected()) {
                if (commerceItemDto.isElectronicItem()) {
                    electronicOrder = true;
                } else {
                    hardgoodOrder = true;
                }
            }
            if (electronicOrder && hardgoodOrder) {
                break;
            }
        }
        if (electronicOrder && hardgoodOrder) {
            result = true;
        }
        return result;
    }

    @Override
    public Response<OrderSubmitDto> commitOrder(String userId, boolean autoRemoveGiftItem) {
        Response<OrderSubmitDto> res = new Response<OrderSubmitDto>();

        try {
            synchronized (getCartLock(userId)) {
                ShoppingCart shoppingCart = purchaseService.loadShoppingCart(userId);
                Map priceParams = purchaseService.createPriceContentMap(shoppingCart);
                List<String> activeCoupons = new ArrayList<String>();
                activeCoupons.addAll(shoppingCart.getCurrentOrder().getCouponActivities());
                priceParams.put("activeCoupons", activeCoupons);
                log.info("{} commitOrder activeCoupons list:{}", userId, activeCoupons);
                purchaseService.repriceOrder(shoppingCart.getCurrentOrder(), PricingOrderType.TOTAL, priceParams);
                Response<ShoppingCart> resValidate = validateCommitOrder(shoppingCart, autoRemoveGiftItem);
                if (!resValidate.isSuccess()) {
                    log.info("提交订单时校验购物车失败。");
                    res.setSuccess(false);
                    res.setErrorMessage(resValidate.getErrorMessage());
                    res.setCode(resValidate.getCode());
                    return res;
                }
                OrderDto orderDto = purchaseService.commitOrder(shoppingCart);
                OrderSubmitDto orderSubmitDto = convertToOrderSubmitDto(orderDto);
                res.setSuccess(true);
                res.setResultObject(orderSubmitDto);
            }
        } catch (Exception e) {
            log.error("commit order error. userId:" + userId, e);
            res.setCode("5001");
            res.setSuccess(false);
            res.setErrorMessage(e.getMessage());
        }

        return res;
    }

    private OrderSubmitDto convertToOrderSubmitDto(OrderDto orderDto) {
        OrderSubmitDto orderSubmitDto = new OrderSubmitDto();

        orderSubmitDto.setId(orderDto.getId());
        orderSubmitDto.setOrderState(orderDto.getOrderState());
        orderSubmitDto.setPaymentState(orderDto.getPaymentState());
        orderSubmitDto.setShippingState(orderDto.getShippingState());
        orderSubmitDto.setState(orderDto.getState());
        orderSubmitDto.setTotal(orderDto.getPriceInfoDto().getTotal());

        return orderSubmitDto;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Response<ShoppingCart> updateInvoiceInfo(String userId, InvoiceInfoDto invoiceInfoDto) {
        Response<ShoppingCart> res = new Response<ShoppingCart>();
        try {
            synchronized (getCartLock(userId)) {
                ShoppingCart sc = purchaseService.loadShoppingCart(userId);
                purchaseService.updateInvoiceInfo(sc, invoiceInfoDto);
                res.setSuccess(true);
                res.setResultObject(sc);
            }
        } catch (Exception e) {
            log.error("更新发票信息异常", e);
            res.setSuccess(false);
            res.setErrorMessage("新增或更新发票信息失败！");
            log.error("update invoice info error. userId:{}", userId);
        }

        return res;
    }

    /**
     * 购物车中商品全选和全不选
     *
     * @param itemSelectDtos
     * @return
     */
    public Response<ShoppingCart> selectOrNot(String userId, List<ItemSelectDto> itemSelectDtos) {
        Response<ShoppingCart> res = new Response<ShoppingCart>();
        try {
            synchronized (getCartLock(userId)) {
                ShoppingCart cart = purchaseService.loadShoppingCart(userId);
                if (cart != null) {
                    purchaseService.selectOrNot(cart, itemSelectDtos);
                    //根据前端优化需求修改购物车时候不需要items,在controller层修改
                    //cart.getCurrentOrder().getItems().clear();
                    res.setCode(CommonsEnum.RESPONSE_200.getCode());
                    res.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                    res.setSuccess(true);
                    res.setResultObject(cart);
                } else {
                    res.setSuccess(false);
                    res.setCode(CommonsEnum.RESPONSE_500.getCode());
                    res.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
                    res.setResultObject(null);
                }
            }
        } catch (Exception e) {
            log.error("购物车中商品全选和全不选异常", e);
            res.setSuccess(false);
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
        }
        return res;
    }

    @Override
    public Response<ShoppingCart> applyCoupons(String userId, List<String> couponActivityIds) {

        Response<ShoppingCart> res = new Response<ShoppingCart>();
        try {
            synchronized (getCartLock(userId)) {
                ShoppingCart cart = purchaseService.loadShoppingCart(userId);
                Map priceParams = purchaseService.createPriceContentMap(cart);
                priceParams.put("activeCoupons", couponActivityIds);
                if(couponActivityIds==null||couponActivityIds.isEmpty()){
                	cart.getCurrentOrder().setUseCoupon(false);
                }
                else{
                	cart.getCurrentOrder().setUseCoupon(true);
                }
                purchaseService.repriceOrder(cart.getCurrentOrder(), PricingOrderType.TOTAL, priceParams);
                purchaseService.updateShoppingCart(cart);
                res.setSuccess(true);
                res.setResultObject(cart);
                res.setCode(CommonsEnum.RESPONSE_200.getCode());
                res.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            }
        } catch (Exception e) {
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setSuccess(false);
            res.setErrorMessage("价格计算失败！");
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return res;
    }


    private synchronized Object getCartLock(String userId) {
        Object lock = cartLockMap.get(userId);
        if (lock == null) {
            lock = new Object();
            cartLockMap.put(userId, lock);
        }
        return lock;
    }

    /**
     * 字符串转换成Long类型并捕获异常
     *
     * @return
     */
    public Long getItemTotal() {
        OrderDictionary orderDictionary = orderDictionaryPropertiesService.selectByPrimaryKey("totalItem");
        log.info("数据字典中没有配商品总数量,自定义的数是:{}", JSON.toJSONString(orderDictionary));
        if (orderDictionary == null || StringUtils.isEmpty(orderDictionary.getPropertyValue())) {
            return ITEM_TOTAL_COUNT;
        }
        Long totalItemLo = ITEM_TOTAL_COUNT;
        try {
            totalItemLo = Long.valueOf(orderDictionary.getPropertyValue());
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return totalItemLo;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Response<String> directCommitOrder(DirectCommitOrderDto directCommitOrderDto) {
        log.info("/directCommitOrder start 直营店 {} 后端下单 ", directCommitOrderDto.getStoreId());
        Response<String> res = new Response<>();
        String storeId = directCommitOrderDto.getStoreId();
        try {
            synchronized (getCartLock(storeId)) {
                String orderId = purchaseService.directCommitOrder(directCommitOrderDto);
                res.setCode(CommonsEnum.RESPONSE_200.getCode());
                res.setSuccess(true);
                res.setResultObject(orderId);
                res.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                log.info("/directCommitOrder end 直营店 {} 后端下单成功", storeId);
            }
        } catch (Exception e) {
            log.error("/directCommitOrder,直营店 {} 后端下单失败，异常:{}", storeId, e.getMessage());
            res.setCode("5001");
            res.setSuccess(false);
            res.setErrorMessage(e.getMessage());
            e.printStackTrace();
        }

        return res;
    }

    /**
     * 结算时做的一些校验
     *
     * @param userId
     * @return
     */
    @Override
    public Response<List<String>> moveToCheckout(String userId) {
        Response<List<String>> res = new Response<>();
        try {
            synchronized (getCartLock(userId)) {
                ShoppingCart cart = purchaseService.loadShoppingCart(userId);
                //校验商品种类数
                Response<List<String>> resValidate = validateToCheckOut(cart);
                if (!resValidate.isSuccess()) {
                    log.info("购物车结算时校验失败。");
                    res.setSuccess(false);
                    res.setErrorMessage(resValidate.getErrorMessage());
                    res.setCode(resValidate.getCode());
                    res.setResultObject(resValidate.getResultObject());
                    return res;
                }
                res.setSuccess(true);
                res.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                res.setCode(CommonsEnum.RESPONSE_200.getCode());
                res.setResultObject(resValidate.getResultObject());
                return res;
            }
        } catch (Exception e) {
            log.error("购物车中结算异常", e);
            res.setSuccess(false);
            res.setResultObject(res.getResultObject());
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
        }
        return res;
    }

    /**
     * 结算时的验证
     *
     * @param cart
     * @return
     */
    private Response<List<String>> validateToCheckOut(ShoppingCart cart) {
        Response<List<String>> res = new Response<>();
        res.setSuccess(true);
        //与提交订单时一样的验证
        Response<List<String>> resValidate = validateShoppingCart(cart,false);
        log.info("购物车结算时的验证结果：{}", JSON.toJSONString(resValidate));
        if (!resValidate.isSuccess()) {
            res.setSuccess(false);
            res.setErrorMessage(resValidate.getErrorMessage());
            if(CommonsEnum.RESPONSE_10042.getCode().equals(resValidate.getCode())){
                res.setCode(CommonsEnum.RESPONSE_5001.getCode());
            }else{
                res.setCode(resValidate.getCode());
            }
            res.setResultObject(resValidate.getResultObject());
            return res;
        }
        return res;
    }

    @Override
    public Response<ShoppingCart> batchDeletetItem(String userId, List<DeleteCartItemDto> deleteCartItems) {
        log.info("batchDeletetItem userId :{}", userId);
//        log.info("deleteCartItems :{}" ,JSONObject.toJSONString(deleteCartItems));
        Response<ShoppingCart> res = new Response<ShoppingCart>();
        try {
            synchronized (getCartLock(userId)) {
                ShoppingCart cart = purchaseService.loadShoppingCart(userId);
                if (cart != null) {
                    purchaseService.batchDeletetItem(cart, deleteCartItems);
                    //根据前端优化需求修改购物车时候不需要items,在controller层修改
                    //cart.getCurrentOrder().getItems().clear();
                    res.setCode(CommonsEnum.RESPONSE_200.getCode());
                    res.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                    res.setSuccess(true);
                    res.setResultObject(cart);
                } else {
                    res.setCode(CommonsEnum.RESPONSE_500.getCode());
                    res.setSuccess(false);
                    res.setErrorMessage(CommonsEnum.RESPONSE_500.getCode());
                    res.setResultObject(null);
                }
            }
        } catch (PurchaseException e) {
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setSuccess(false);
            res.setErrorMessage(e.getMessage());
            log.error(ExceptionUtils.getFullStackTrace(e));
        } catch (PricingException e) {
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setSuccess(false);
            res.setErrorMessage(CommonsEnum.RESPONSE_500.getCode());
            log.error(ExceptionUtils.getFullStackTrace(e));
        } catch (Exception e) {
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setSuccess(false);
            res.setErrorMessage(CommonsEnum.RESPONSE_500.getCode());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return res;
    }

    /**
     * 一键清空所有库存不足的商品
     */
    public Response<ShoppingCart> oneKeyToDelete(String userId) {
        log.info("userId :{}", userId);
        Response<ShoppingCart> res = new Response<ShoppingCart>();
        try {
            synchronized (getCartLock(userId)) {
                ShoppingCart cart = purchaseService.loadShoppingCart(userId);
                if (cart != null) {
                    purchaseService.oneKeyToDelete(cart);
                    //根据前端优化需求修改购物车时候不需要items,在controller层修改
                    //cart.getCurrentOrder().getItems().clear();
                    res.setCode(CommonsEnum.RESPONSE_200.getCode());
                    res.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
                    res.setSuccess(true);
                    res.setResultObject(cart);
                } else {
                    res.setCode(CommonsEnum.RESPONSE_500.getCode());
                    res.setSuccess(false);
                    res.setErrorMessage(CommonsEnum.RESPONSE_500.getCode());
                    res.setResultObject(null);
                }
            }
        } catch (PurchaseException e) {
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setSuccess(false);
            res.setErrorMessage(e.getMessage());
            log.error(ExceptionUtils.getFullStackTrace(e));
        } catch (PricingException e) {
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setSuccess(false);
            res.setErrorMessage(CommonsEnum.RESPONSE_500.getCode());
            log.error(ExceptionUtils.getFullStackTrace(e));
        } catch (Exception e) {
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setSuccess(false);
            res.setErrorMessage(CommonsEnum.RESPONSE_500.getCode());
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return res;
    }

    @Override
    public Response<Boolean> buyItemsAgain(String orderId, String userId) {
        Response<Boolean> response = new Response<>();
        log.info("buyItemsAgain,orderId:{}",orderId);
        try{
            if(StringUtils.isEmpty(orderId)){
                log.info("buyItemsAgain,orderId can not be empty,orderId:{}",orderId);
                response.setSuccess(false);
                response.setResultObject(false);
                response.setCode(CommonsEnum.RESPONSE_400.getCode());
                response.setErrorMessage(CommonsEnum.RESPONSE_400.getName());
                return response;
            }
            synchronized (getCartLock(userId)) {
                ShoppingCart cart = purchaseService.loadShoppingCart(userId);
                if (cart != null) {
                    purchaseAgain(cart,orderId);
                }
            }
            response.setSuccess(true);
            response.setResultObject(true);
            response.setCode(CommonsEnum.RESPONSE_200.getCode());
        }catch (Exception e){
            log.error(e.getMessage(),e);
            response.setSuccess(false);
            response.setResultObject(false);
            response.setCode(CommonsEnum.RESPONSE_500.getCode());
            response.setErrorMessage(CommonsEnum.RESPONSE_500.getName());
            return response;
        }

        return response;
    }

    private void purchaseAgain(ShoppingCart cart, String orderId) throws Exception {

        Order oldOrder = orderService.selectByPrimaryKey(orderId);
        if(oldOrder == null){
            log.error("buyItemsAgain,order not exist,orderId:{}",orderId);
            throw new RuntimeException("order dose not exist");
        }
        List<CommerceItem> list = commerceItemService.getCommerceItemForOrderId(orderId);
        for(CommerceItem commerceItem : list){
            if(StringUtils.isEmpty(commerceItem.getSkuId())){
                log.error("add item to order the sku id is empty,orderId:{},commerceItemId:{},skuId:{}",orderId,commerceItem.getId(),commerceItem.getSkuId());
                continue;
            }
            if(commerceItem.getQuantity() <= 0){
                log.error("add item to order the quantity can't  below 0,orderId:{},commerceItemId:{}",orderId,commerceItem.getId());
                continue;
            }
            purchaseService.addItemBuyAgain(commerceItem,cart);
        }
        purchaseService.repriceOrder(cart.getCurrentOrder(), PricingOrderType.AMOUNT, purchaseService.createPriceContentMap(cart));
        purchaseService.updateShoppingCart(cart);


    }

	@Override
	public Response<ShoppingCart> setUseCoupon(String userId, boolean useCoupon) {
		log.info("setUseCoupon:userId {}", userId);
        Response<ShoppingCart> res = new Response<ShoppingCart>();
        ShoppingCart cart = null;
        try {
            synchronized (getCartLock(userId)) {
                cart = purchaseService.loadShoppingCart(userId);
                cart.getCurrentOrder().setUseCoupon(useCoupon);
                purchaseService.updateShoppingCart(cart);
            }
            res.setResultObject(cart);
            res.setCode(CommonsEnum.RESPONSE_200.getCode());
            res.setErrorMessage(CommonsEnum.RESPONSE_200.getName());
            res.setSuccess(true);
        } catch (PurchaseException e) {
            log.error("set Use Coupon error. userId:{}", userId);
            e.printStackTrace();
            res.setSuccess(false);
            res.setCode(CommonsEnum.RESPONSE_500.getCode());
            res.setErrorMessage("设置使用优惠券失败！");
            log.error(ExceptionUtils.getFullStackTrace(e));
        } catch (PricingException e) {
            log.error("set Use Coupon error. userId:{}", userId);
            e.printStackTrace();
            log.error(ExceptionUtils.getFullStackTrace(e));
        }
        return res;
	}
}
