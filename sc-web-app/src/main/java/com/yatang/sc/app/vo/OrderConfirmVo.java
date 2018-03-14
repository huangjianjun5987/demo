package com.yatang.sc.app.vo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liusongjie on 2017/7/25.
 */

public class OrderConfirmVo implements Serializable {

    private static final long serialVersionUID = 151482211949820738L;
    private ShippingGroupVo shippingGroupVo;

    private List<CommerceItemOrderConfirmVo> commerceItemVos;

    private List<CommerceItemOrderConfirmVo> giftItemVos;

    private InvoiceInfoVo invoiceInfoVo;

    private OrderPriceInfoVo orderPriceInfoVo;

    private String lastOrderId;

    private List<String> selectedCouponActivities;

    public ShippingGroupVo getShippingGroupVo() {
        return shippingGroupVo;
    }

    public void setShippingGroupVo(ShippingGroupVo shippingGroupVo) {
        this.shippingGroupVo = shippingGroupVo;
    }

    public List<CommerceItemOrderConfirmVo> getCommerceItemVos() {
        return commerceItemVos;
    }

    public void setCommerceItemVos(List<CommerceItemOrderConfirmVo> commerceItemVos) {
        this.commerceItemVos = commerceItemVos;
    }

    public List<CommerceItemOrderConfirmVo> getGiftItemVos() {
        return giftItemVos;
    }

    public void setGiftItemVos(List<CommerceItemOrderConfirmVo> giftItemVos) {
        this.giftItemVos = giftItemVos;
    }

    public InvoiceInfoVo getInvoiceInfoVo() {
        return invoiceInfoVo;
    }

    public void setInvoiceInfoVo(InvoiceInfoVo invoiceInfoVo) {
        this.invoiceInfoVo = invoiceInfoVo;
    }

    public OrderPriceInfoVo getOrderPriceInfoVo() {
        return orderPriceInfoVo;
    }

    public void setOrderPriceInfoVo(OrderPriceInfoVo orderPriceInfoVo) {
        this.orderPriceInfoVo = orderPriceInfoVo;
    }

    public String getLastOrderId() {
        return lastOrderId;
    }

    public void setLastOrderId(String lastOrderId) {
        this.lastOrderId = lastOrderId;
    }

    public List<String> getSelectedCouponActivities() {
        return selectedCouponActivities;
    }

    public void setSelectedCouponActivities(List<String> selectedCouponActivities) {
        this.selectedCouponActivities = selectedCouponActivities;
    }
}
