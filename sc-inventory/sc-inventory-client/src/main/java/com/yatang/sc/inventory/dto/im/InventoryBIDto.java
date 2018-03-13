package com.yatang.sc.inventory.dto.im;

import java.io.Serializable;

/**
 * 库存实时查询dto类
 */

public class InventoryBIDto implements Serializable {


    private static final long serialVersionUID = 2475215157008603178L;

    //库存中的数据
    private String productId;//商品id

    private String productCode;//商品Code

    private String productName;//商品名称

    private String groups;//部类

    private String dept;//大类

    private String classs;//中类

    private String subclass;//小类

    private String brandName;//品牌名


    private String branchCompanyId;//分公司id

    private String branchCompanyName;//分公司名称


    private String warehouseCode;//仓库id

    private String warehouseName;//仓库名称


    private Double unitCost;//上商品成本 主供应商的采购价

    private Double avCost;//移动加权成本

    private Long stockOnHand;//现有库存

    private Long availableStock;// 可用库存  可用库存=现有库存-销售保留-调拨预留-退货预留

    private Long inTransitQty;//在途数量

    private Long orderReservedQty;//销售保留

    private Long tsfReservedQty;//调拨预留

//    private Long tsfExpectedQty;//预期到货

    private Long rtvQty;//退货预留

    private Long lastWeekSaleQty;//上周日均销量  取值当前查询系统日期至往前倒推7天间的商品地点销售出库单返回的确认收货数量合计除以7 todo

    private Double safeStockDay;//安全库存天数 安全库存天数=商品地点可用库存/上周日均销

    private Long saleUnArriveQty;//销售未达数量 取值销售订单状态为“已审核”销售订单物流状态为“库存不足”销售的销售订单汇总单据上商品地点的商品订单数量  todo

    // 国际码信息
    private String internationalCode;


    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public void setClasss(String classs) {
        this.classs = classs;
    }

    public void setSubclass(String subclass) {
        this.subclass = subclass;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public void setBranchCompanyName(String branchCompanyName) {
        this.branchCompanyName = branchCompanyName;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public void setAvCost(Double avCost) {
        this.avCost = avCost;
    }

    public void setStockOnHand(Long stockOnHand) {
        this.stockOnHand = stockOnHand;
    }

    public void setAvailableStock(Long availableStock) {
        this.availableStock = availableStock;
    }

    public void setInTransitQty(Long inTransitQty) {
        this.inTransitQty = inTransitQty;
    }

    public void setOrderReservedQty(Long orderReservedQty) {
        this.orderReservedQty = orderReservedQty;
    }

    public void setTsfReservedQty(Long tsfReservedQty) {
        this.tsfReservedQty = tsfReservedQty;
    }

    public void setRtvQty(Long rtvQty) {
        this.rtvQty = rtvQty;
    }

    public void setLastWeekSaleQty(Long lastWeekSaleQty) {
        this.lastWeekSaleQty = lastWeekSaleQty;
    }

    public void setSafeStockDay(Double safeStockDay) {
        this.safeStockDay = safeStockDay;
    }

    public void setSaleUnArriveQty(Long saleUnArriveQty) {
        this.saleUnArriveQty = saleUnArriveQty;
    }

    public String getProductId() {
        return productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public String getGroups() {
        return groups;
    }

    public String getDept() {
        return dept;
    }

    public String getClasss() {
        return classs;
    }

    public String getSubclass() {
        return subclass;
    }

    public String getBrandName() {
        return brandName;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public String getBranchCompanyName() {
        return branchCompanyName;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public Double getAvCost() {
        return avCost;
    }

    public Long getStockOnHand() {
        return stockOnHand;
    }

    public Long getAvailableStock() {
        return availableStock;
    }

    public Long getInTransitQty() {
        return inTransitQty;
    }

    public Long getOrderReservedQty() {
        return orderReservedQty;
    }

    public Long getTsfReservedQty() {
        return tsfReservedQty;
    }

    public Long getRtvQty() {
        return rtvQty;
    }

    public Long getLastWeekSaleQty() {
        return lastWeekSaleQty;
    }

    public Double getSafeStockDay() {
        return safeStockDay;
    }

    public Long getSaleUnArriveQty() {
        return saleUnArriveQty;
    }

    public String getInternationalCode() {
        return internationalCode;
    }

    public void setInternationalCode(String internationalCode) {
        this.internationalCode = internationalCode;
    }
}
