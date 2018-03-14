package com.yatang.sc.purchase.service;

import com.yatang.sc.order.domain.*;
import com.yatang.sc.purchase.dto.ItemPriceInfoDto;
import com.yatang.sc.purchase.dto.OrderDto;
import com.yatang.sc.purchase.dto.ShoppingCart;
import com.yatang.sc.purchase.exception.PurchaseException;

import java.util.Date;
import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/11.
 */
public interface PurchaseHelper {

    void saveCommerceItem(CommerceItem commerceItem, ItemPriceInfoDto itemPriceDto);

    void saveShippingGroup(ShippingGroup shippingGroup);

    void saveShippingPrice(ShippingPrice shippingPrice);

    void saveShippingPriceAdj(Long shippingPriceId, Long adjustmentId, Byte seq);

    Order saveShoppingCart(ShoppingCart shoppingCart);

    void saveOrder(Order order);

    void saveOrder(Order order,boolean geneId);

    void savePriceAdjustment(PriceAdjustment priceAdjustment);

    void saveOrderPrice(OrderPrice orderPrice);

    void saveOrderPriceAdj(Long orderPriceId, Long priceAdjustmentId, Byte sequence);

    String getOrderId(String words, Date nowTime);

    void savePaymentGroup(PaymentGroup paymentGroup);

    void saveInvoice(Invoice invoice);

    void saveOrderItems(OrderItems orderItems);

    void saveOrderPayments(String id, String orderId);

    boolean saveThirdOrder(OrderDto orderDto) throws PurchaseException;
}
