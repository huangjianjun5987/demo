package com.yatang.sc.glink.dto.saleOrder;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述: 销售订单物品DTO
 * @作者: wangcheng
 * @创建时间: 2017年7月31日20:50:50
 */
public class SaleCargoDto  implements Serializable {
    private String orderLineNo;//商品行号
    private String itemCode;//商品编码
    private String name;//商品名称
    private String type;//商品类型
    private String unit;//规格单位
    private int quantity;//商品数量
    private String inventoryType;//库存类型
    private double retailPrice;//零售价
    private String prodPrice;//优惠前商品行小计
    private double actualPrice;//实际成交价
    private String balancePrice;//优惠后商品行小计
    private double discountAmount;//单件商品折扣金额
    @JSONField(format = "YYYY-MM-DD")
    private Date productDate;//商品生产日期
    @JSONField(format = "YYYY-MM-DD")
    private Date expireDate;//商品过期日期
    private String produceCode;//生产批号
    private String batchCode;//批次编码
    private String remark1;//扩展1（赠品数量）
    private String remark2;//扩展2（货款）
    private String remark3;//扩展3
    private Integer actualQty;//实际发货数量

    public String getOrderLineNo() {
        return orderLineNo;
    }

    public void setOrderLineNo(String orderLineNo) {
        this.orderLineNo = orderLineNo;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public double getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(double retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getBalancePrice() {
        return balancePrice;
    }

    public void setBalancePrice(String balancePrice) {
        this.balancePrice = balancePrice;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Date getProductDate() {
        return productDate;
    }

    public void setProductDate(Date productDate) {
        this.productDate = productDate;
    }

    public Date getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }

    public String getProduceCode() {
        return produceCode;
    }

    public void setProduceCode(String produceCode) {
        this.produceCode = produceCode;
    }

    public String getBatchCode() {
        return batchCode;
    }

    public void setBatchCode(String batchCode) {
        this.batchCode = batchCode;
    }

    public String getRemark1() {
        return remark1;
    }

    public void setRemark1(String remark1) {
        this.remark1 = remark1;
    }

    public String getRemark2() {
        return remark2;
    }

    public void setRemark2(String remark2) {
        this.remark2 = remark2;
    }

    public String getRemark3() {
        return remark3;
    }

    public void setRemark3(String remark3) {
        this.remark3 = remark3;
    }

    public Integer getActualQty() {
        return actualQty;
    }

    public void setActualQty(Integer actualQty) {
        this.actualQty = actualQty;
    }
}
