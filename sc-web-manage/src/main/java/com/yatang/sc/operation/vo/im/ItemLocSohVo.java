package com.yatang.sc.operation.vo.im;

import java.io.Serializable;
import java.util.Date;

/**
 * @描述: 库存的dto
 * @类名:
 * @作者: yangshuang
 * @创建时间: 2017/8/5 18:43
 * @版本: v1.0
 */
public class ItemLocSohVo implements Serializable {

    private static final long serialVersionUID = 4528328738982873470L;
    private Long id; //pk

    private String itemId;//商品id

    private String loc;//仓库id

    private Double unitCost;//上商品成本 主供应商的采购价

    private Double avCost;//移动加权成本

    private Long stockOnHand;//现有库存

    private Long inTransitQty;//在途数量

    private Long orderReservedQty;//销售保留

    private Long tsfReservedQty;//调拨预留

    private Long sellInTransitQty;//销售在途

    private Long tsfExpectedQty;//预期到货

    private Long rtvQty;//退货预留

    private String productCode;//商品Code


    private String refNo1;

    private String refNo2;

    private String refNo3;

    private String refNo4;

    private String refNo5;

    private Date lastUpdateDatetime;

    private String lastUpdateId;


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
        return orderReservedQty;
    }

    public void setOrderReservedQty(Long orderReservedQty) {
        this.orderReservedQty = orderReservedQty;
    }

    public Long getTsfReservedQty() {
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

    @Override
    public String toString() {
        return "ItemLocSohDto{" +
                "id=" + id +
                ", itemId='" + itemId + '\'' +
                ", loc='" + loc + '\'' +
                ", unitCost=" + unitCost +
                ", avCost=" + avCost +
                ", stockOnHand=" + stockOnHand +
                ", inTransitQty=" + inTransitQty +
                ", orderReservedQty=" + orderReservedQty +
                ", tsfReservedQty=" + tsfReservedQty +
                ", sellInTransitQty=" + sellInTransitQty +
                ", tsfExpectedQty=" + tsfExpectedQty +
                ", rtvQty=" + rtvQty +
                ", productCode='" + productCode + '\'' +
                ", refNo1='" + refNo1 + '\'' +
                ", refNo2='" + refNo2 + '\'' +
                ", refNo3='" + refNo3 + '\'' +
                ", refNo4='" + refNo4 + '\'' +
                ", refNo5='" + refNo5 + '\'' +
                ", lastUpdateDatetime=" + lastUpdateDatetime +
                ", lastUpdateId='" + lastUpdateId + '\'' +
                '}';
    }
}