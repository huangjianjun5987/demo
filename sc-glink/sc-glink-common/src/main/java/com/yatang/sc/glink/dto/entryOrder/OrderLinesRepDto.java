package com.yatang.sc.glink.dto.entryOrder;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.Date;

/**
 * create by chensiqiang
 * time 2017/8/1 10:21
 * 获取入库单响应订单信息DTO
 */
public class OrderLinesRepDto implements Serializable {

    private String orderLineNo;     //入库单行号
    private String orderLineReturn; //退回订单对应行号
    private String itemIdWMS;       //仓储系统商品ID
    private String itemCode;        //SKU编码
    private String itemName;        //商品名称
    private String inventoryType;   //库存类型 ZP=正品,CC=残次,JS=机损,XS=箱损
    private int planQty;         //商品计划入库数量
    private int acualQty;        //商品实际入库数量(际链名称写错了，际链专用)
    @JSONField(format = "yyyy-MM-dd")
    private Date productDate;     //商品生产日期
    @JSONField(format = "yyyy-MM-dd")
    private Date expireDate;      //商品过期日期
    private String produceCode;     //生产批号
    private String batchCode;       //批次编码
    private String string1;
    private String string2;
    private String string3;
    private String string4;
    private String string5;

    public String getOrderLineNo() {
        return orderLineNo;
    }

    public void setOrderLineNo(String orderLineNo) {
        this.orderLineNo = orderLineNo;
    }

    public String getOrderLineReturn() {
        return orderLineReturn;
    }

    public void setOrderLineReturn(String orderLineReturn) {
        this.orderLineReturn = orderLineReturn;
    }

    public String getItemIdWMS() {
        return itemIdWMS;
    }

    public void setItemIdWMS(String itemIdWMS) {
        this.itemIdWMS = itemIdWMS;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getInventoryType() {
        return inventoryType;
    }

    public void setInventoryType(String inventoryType) {
        this.inventoryType = inventoryType;
    }

    public int getPlanQty() {
        return planQty;
    }

    public void setPlanQty(int planQty) {
        this.planQty = planQty;
    }

    public int getAcualQty() {
        return acualQty;
    }

    public void setAcualQty(int acualQty) {
        this.acualQty = acualQty;
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

    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public String getString2() {
        return string2;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }

    public String getString3() {
        return string3;
    }

    public void setString3(String string3) {
        this.string3 = string3;
    }

    public String getString4() {
        return string4;
    }

    public void setString4(String string4) {
        this.string4 = string4;
    }

    public String getString5() {
        return string5;
    }

    public void setString5(String string5) {
        this.string5 = string5;
    }
}
