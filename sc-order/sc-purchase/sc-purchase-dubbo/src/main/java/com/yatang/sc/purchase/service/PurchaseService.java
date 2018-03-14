package com.yatang.sc.purchase.service;


import com.yatang.sc.facade.dto.prod.ProdSellPriceInfoDto;
import com.yatang.sc.inventory.dto.ItemInventoryDto;
import com.yatang.sc.order.domain.CommerceItem;
import com.yatang.sc.order.domain.Order;
import com.yatang.sc.purchase.dto.*;
import com.yatang.sc.purchase.enums.PricingOrderType;
import com.yatang.sc.purchase.exception.ItemException;
import com.yatang.sc.purchase.exception.PricingException;
import com.yatang.sc.purchase.exception.PurchaseException;
import com.yatang.xc.mbd.pi.es.dto.ProductIndexDto;

import java.util.List;
import java.util.Map;

/**
 * Created by qiugang on 7/8/2017.
 */
public interface PurchaseService {

    public ShoppingCart initalShoppingCart(String userId, Map initParams) throws PurchaseException;

    public ShoppingCart loadShoppingCart(String userId) throws PurchaseException, PricingException;

    public ShoppingCart createShoppingCart(String userId, boolean persist);

    public void updateShoppingCart(ShoppingCart shoppingCart) throws PurchaseException;

    public void checkout(ShoppingCart shoppingCart, AddressDto address) throws PurchaseException;

    public OrderDto createOrder(String userId, String orderType);

    public void addItemToShoppingCart(ShoppingCart shoppingCart, AddItemDto itemInfo) throws PurchaseException, ItemException;

    public void removeItem(ShoppingCart shoppingCart, String skuId) throws PurchaseException;

    public void updateCommerceItem(ShoppingCart shoppingCart, String skuId, long quantity,long saleQuantity) throws PurchaseException, ItemException;

    public ProdSellPriceInfoDto getProductPriceInfo(String productId, String branchCompanyId);

    public Map createPriceContentMap(ShoppingCart shoppingCart);

    public void repriceOrder(OrderDto order, PricingOrderType type, Map contentMap) throws PurchaseException;

    public void repriceOrder(OrderDto order, PricingOrderType type, Map contentMap,boolean isMock) throws PurchaseException;

    public void applyShippingAddress(ShoppingCart shoppingCart, AddressDto address) throws PurchaseException;

    public void setPaymentMethod(ShoppingCart shoppingCart, String paymentMethod) throws PurchaseException;

    public OrderDto commitOrder(ShoppingCart shoppingCart) throws PurchaseException;

    public void selectOne(ShoppingCart shoppingCart, String skuId, boolean b) throws PurchaseException;

    public void selectAllOrNone(ShoppingCart shoppingCart, boolean b) throws PurchaseException;

    public void updateInvoiceInfo(ShoppingCart shoppingCart, InvoiceInfoDto invoiceInfoDto) throws PurchaseException;

    public void selectOrNot(ShoppingCart shoppingCart, List<ItemSelectDto> itemSelectDtos) throws PurchaseException;

    String directCommitOrder(DirectCommitOrderDto directCommitOrderDto) throws PurchaseException;

    public void batchDeletetItem(ShoppingCart cart, List<DeleteCartItemDto> deleteCartItems) throws PurchaseException;

    void sendSubmitOrderMsg(Order order);

    void oneKeyToDelete(ShoppingCart cart) throws PurchaseException;

    CommerceItemDto addItemBuyAgain(CommerceItem commerceItem,ShoppingCart cart) throws Exception;

    ItemInventoryDto createItemInventoryDto(String productId, String warehouseCode, String branchCompanyId, long quantity, String productName);
}
