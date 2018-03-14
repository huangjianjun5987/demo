package com.yatang.sc.purchase.dto;

import java.io.Serializable;

/**
 * @描述:使用优惠之后的退款金额封装类
 * @类名:OrderRefundPriceDto
 * @作者: lvheping
 * @创建时间: 2017/11/3 14:08
 * @版本: v1.0
 */

public class OrderRefundPriceDto implements Serializable {
    private Double amount;//退款金额
    private String paytype;//支付方式
    private String payTradeNo;//第三方支付平台支付交易流水
    private Double totalAmount;//订单总金额

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getPayTradeNo() {
        return payTradeNo;
    }

    public void setPayTradeNo(String payTradeNo) {
        this.payTradeNo = payTradeNo;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "OrderRefundPriceDto{" +
                "amount=" + amount +
                ", paytype='" + paytype + '\'' +
                ", payTradeNo='" + payTradeNo + '\'' +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
