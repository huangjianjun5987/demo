package com.yatang.sc.purchase.dubboservice;

import com.busi.common.resp.Response;
import com.yatang.sc.purchase.dto.*;
import com.yatang.sc.purchase.enums.PricingOrderType;
import java.util.List;
import java.util.Map;

/**
 * @描述: 采购dubbo服务接口.
 * @作者: qiugang
 * @创建时间: 7/10/2017.
 * @版本: 1.0 .
 */
public interface PurchaseDubboService {

    public Response<Boolean> initalShoppingCart(String userId, Map intialParams);

    public Response<ShoppingCart> loadShoppingCart(String userId, boolean checkInventory);
    
    public Response<ShoppingCart> setUseCoupon(String userId, boolean useCoupon);

    public Response<ShoppingCart> addItemToShoppingCart(String userId, AddItemDto addItem);

    public Response<ShoppingCart> removeItem(String userId, String skuId);

    public Response<ShoppingCart> updateCommerceItem(String userId, String skuId, long quantity);

    public Response<ShoppingCart> repriceShoppingCart(ShoppingCart shoppingCart, PricingOrderType type);

    public Response<ShoppingCart> checkout(String userId, AddressDto address);
    /**
     * @Description: 将选中的地址信息应用到订单上
     * @author liusongjie
     * @date 2017/7/19- 14:13
     * @param
     */
    public Response<ShoppingCart> applyShippingAddress(String userId, AddressDto address);

    public Response<ShoppingCart> setPaymentMethod(ShoppingCart shoppingCart, String paymentMethod);

    /**
     * @Description: 提交订单
     * @author liusongjie
     * @date 2017/7/19- 14:13
     * @param
     */
    public Response<OrderSubmitDto> commitOrder(String userId, boolean autoRemoveGiftItem);

    /**
     * @Description: 更新/保存发票信息
     * @author liusongjie
     * @date 2017/7/19- 14:13
     * @param
     */
    public Response<ShoppingCart> updateInvoiceInfo(String userId, InvoiceInfoDto invoiceInfoDto);

    public Response<ShoppingCart> selectOrNot(String userId, List<ItemSelectDto> itemSelectDtos);

    public Response<ShoppingCart> applyCoupons(String userId, List<String> couponActivityIds);


    Response<String> directCommitOrder(DirectCommitOrderDto directCommitOrderDto);

    /**
     * 结算时做的校验
     * @return
     */
    Response<List<String>> moveToCheckout(String userId);

    /**
     * 批量删除购物车中的商品
     * @param userId
     * @param deleteCartItems
     * @return
     */
    Response<ShoppingCart> batchDeletetItem(String userId, List<DeleteCartItemDto> deleteCartItems);

    /**
     * 一键删除购物车中库存不足的商品
     * @param userId
     * @return
     */
    Response<ShoppingCart>  oneKeyToDelete(String userId);

    /**
     * 重新购买已完成、已取消的订单
     * @param orderId
     * @return
     */
    Response<Boolean> buyItemsAgain(String orderId,String userId);

	Response<ShoppingCart> loadShoppingCart(String userId);
}
