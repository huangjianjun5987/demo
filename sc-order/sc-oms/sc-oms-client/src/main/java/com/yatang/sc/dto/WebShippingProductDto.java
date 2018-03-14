package com.yatang.sc.dto;

import com.yatang.xc.mbd.biz.prod.dubboservice.dto.InternationalCodeDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by xiangyonghong on 2017/7/26.
 */

public class WebShippingProductDto implements Serializable{

    /**
     * 商品id
     */
    private String id;
    /**
     * 商品编码
     */
    private String skuId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 订单数量
     */
    private long quantity;
    /**
     * 配送数量
     */
    private long shippedQuantity;

    /**
     * 单位
     */
    private String unit;
    /**
     * 单价
     */
    private double salePrice;
    /**
     * 签收数量
     */
    private long completedQuantity;
    /**
     * 签收差数
     */
    private long completedMulQuantity;
    /**
     * 签收差额
     */
    private double completedMulAmount;

    /**
     * 条码
     */
    private List<InternationalCodeDto> internationalCodes;

    public WebShippingProductDto() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getShippedQuantity() {
        return shippedQuantity;
    }

    public void setShippedQuantity(long shippedQuantity) {
        this.shippedQuantity = shippedQuantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public long getCompletedQuantity() {
        return completedQuantity;
    }

    public void setCompletedQuantity(long completedQuantity) {
        this.completedQuantity = completedQuantity;
    }

    public long getCompletedMulQuantity() {
        return completedMulQuantity;
    }

    public void setCompletedMulQuantity(long completedMulQuantity) {
        this.completedMulQuantity = completedMulQuantity;
    }

    public double getCompletedMulAmount() {
        return completedMulAmount;
    }

    public void setCompletedMulAmount(double completedMulAmount) {
        this.completedMulAmount = completedMulAmount;
    }

    public List<InternationalCodeDto> getInternationalCodes() {
        return internationalCodes;
    }

    public void setInternationalCodes(List<InternationalCodeDto> pInternationalCodes) {
        internationalCodes = pInternationalCodes;
    }
}
