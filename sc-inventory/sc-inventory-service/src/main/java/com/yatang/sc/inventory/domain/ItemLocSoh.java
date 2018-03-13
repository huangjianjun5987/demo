package com.yatang.sc.inventory.domain;

import java.io.Serializable;
import java.util.Date;

public class ItemLocSoh implements Serializable {
    private Long id;

    private String itemId;

    private String loc;

    private Double unitCost;

    private Double avCost;

    private Long stockOnHand;

    private Long inTransitQty;

    private Long orderReservedQty;

    private Long tsfReservedQty;

    private Long sellInTransitQty;

    private Long tsfExpectedQty;

    private Long rtvQty;

    private String refNo1;

    private String refNo2;

    private String refNo3;

    private String refNo4;

    private String refNo5;

    private Date lastUpdateDatetime;

    private String lastUpdateId;

    private String productCode;//商品Code

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc == null ? null : loc.trim();
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public Double getAvCost() {
        return avCost;
    }

    public void setAvCost(Double avCost) {
        this.avCost = avCost;
    }

    public Long getStockOnHand() {
        if(stockOnHand==null){
            return 0L;
        }
        return stockOnHand;
    }

    public void setStockOnHand(Long stockOnHand) {
        this.stockOnHand = stockOnHand;
    }

    public Long getInTransitQty() {
        return inTransitQty;
    }

    public void setInTransitQty(Long inTransitQty) {
        this.inTransitQty = inTransitQty;
    }

    public Long getOrderReservedQty() {
        if(orderReservedQty==null){
            return 0L;
        }
        return orderReservedQty;
    }

    public void setOrderReservedQty(Long orderReservedQty) {
        this.orderReservedQty = orderReservedQty;
    }

    public Long getTsfReservedQty() {
        if(tsfReservedQty==null){
            return 0L;
        }
        return tsfReservedQty;
    }

    public void setTsfReservedQty(Long tsfReservedQty) {
        this.tsfReservedQty = tsfReservedQty;
    }

    public Long getSellInTransitQty() {
        return sellInTransitQty;
    }

    public void setSellInTransitQty(Long sellInTransitQty) {
        this.sellInTransitQty = sellInTransitQty;
    }

    public Long getTsfExpectedQty() {
        return tsfExpectedQty;
    }

    public void setTsfExpectedQty(Long tsfExpectedQty) {
        this.tsfExpectedQty = tsfExpectedQty;
    }

    public Long getRtvQty() {
        if(rtvQty==null){
            return 0L;
        }
        return rtvQty;
    }

    public void setRtvQty(Long rtvQty) {
        this.rtvQty = rtvQty;
    }

    public String getRefNo1() {
        return refNo1;
    }

    public void setRefNo1(String refNo1) {
        this.refNo1 = refNo1 == null ? null : refNo1.trim();
    }

    public String getRefNo2() {
        return refNo2;
    }

    public void setRefNo2(String refNo2) {
        this.refNo2 = refNo2 == null ? null : refNo2.trim();
    }

    public String getRefNo3() {
        return refNo3;
    }

    public void setRefNo3(String refNo3) {
        this.refNo3 = refNo3 == null ? null : refNo3.trim();
    }

    public String getRefNo4() {
        return refNo4;
    }

    public void setRefNo4(String refNo4) {
        this.refNo4 = refNo4 == null ? null : refNo4.trim();
    }

    public String getRefNo5() {
        return refNo5;
    }

    public void setRefNo5(String refNo5) {
        this.refNo5 = refNo5 == null ? null : refNo5.trim();
    }

    public Date getLastUpdateDatetime() {
        return lastUpdateDatetime;
    }

    public void setLastUpdateDatetime(Date lastUpdateDatetime) {
        this.lastUpdateDatetime = lastUpdateDatetime;
    }

    public String getLastUpdateId() {
        return lastUpdateId;
    }

    public void setLastUpdateId(String lastUpdateId) {
        this.lastUpdateId = lastUpdateId == null ? null : lastUpdateId.trim();
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }
}