package com.yatang.sc.order.domain.orderIndex;


import java.io.Serializable;

public class OrderItemIndex implements Serializable{

    private static final long serialVersionUID = -5706417597719990185L;
    private String id;
    private String productId;//产品id
    private String productName;//产品名称
    private Long quantity;//下单时的数量
    private Long saleQuantity;//销售数量
    private Long shippedQuantity;//配送的数量
    private Long completedQuantity;//签收数量
    private Long unitQuantity;//销售内装数
    private Long returnQuantity;//发生了退换货的数量
    private Double orderDiscountShare;//订单级别促销所分摊的折扣价
    private Long quantityAsQualifier;//作为促销接受者所占的数量
    private Long quantityDiscounted;//作为促销接受者所占的数量
    private Double amount;//订单商品的价格计算结果
    private Double listPrice;//基础价格
    private Double rawTotalPrice;//商品净额
    private Double salePrice;//订单商品售价
    private String orderId;//订单id
    private Double avCost;//商品移动加权平均成本
    private Double cost;//成本金额
    private String stateDetail;//购物车状态
    private String groupsCode;//部类编码
    private String groupsName;//名称
    private String deptCode;//大类编码
    private String deptName;//大类名称
    private String internationalCode;//商品条码
    private String firstLevelCategoryName;//一级商品分类名称
    private String secondLevelCategoryName;//二级商品分类名称
    private String thirdLevelCategoryName;//三级商品分类名称
    private String productCode;//商品编号
    private String saleTax;//销项税率
    private String completedAmount;//签收金额
    private String completedMulAmount;//签收差额
    private double total;//最终总额

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getOrderDiscountShare() {
        return orderDiscountShare;
    }

    public void setOrderDiscountShare(Double orderDiscountShare) {
        this.orderDiscountShare = orderDiscountShare;
    }

    public Long getQuantityAsQualifier() {
        return quantityAsQualifier;
    }

    public void setQuantityAsQualifier(Long quantityAsQualifier) {
        this.quantityAsQualifier = quantityAsQualifier;
    }

    public String getStateDetail() {
        return stateDetail;
    }

    public void setStateDetail(String stateDetail) {
        this.stateDetail = stateDetail;
    }

    public Double getAvCost() {
        return avCost;
    }

    public void setAvCost(Double avCost) {
        this.avCost = avCost;
    }

    public String getInternationalCode() {
        return internationalCode;
    }

    public void setInternationalCode(String internationalCode) {
        this.internationalCode = internationalCode;
    }

    public String getFirstLevelCategoryName() {
        return firstLevelCategoryName;
    }

    public void setFirstLevelCategoryName(String firstLevelCategoryName) {
        this.firstLevelCategoryName = firstLevelCategoryName;
    }

    public String getSecondLevelCategoryName() {
        return secondLevelCategoryName;
    }

    public void setSecondLevelCategoryName(String secondLevelCategoryName) {
        this.secondLevelCategoryName = secondLevelCategoryName;
    }

    public String getThirdLevelCategoryName() {
        return thirdLevelCategoryName;
    }

    public void setThirdLevelCategoryName(String thirdLevelCategoryName) {
        this.thirdLevelCategoryName = thirdLevelCategoryName;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCompletedMulAmount() {
        return completedMulAmount;
    }

    public void setCompletedMulAmount(String completedMulAmount) {
        this.completedMulAmount = completedMulAmount;
    }

    public String getGroupsCode() {
        return groupsCode;
    }

    public void setGroupsCode(String groupsCode) {
        this.groupsCode = groupsCode;
    }

    public String getGroupsName() {
        return groupsName;
    }

    public void setGroupsName(String groupsName) {
        this.groupsName = groupsName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getSaleTax() {
        return saleTax;
    }

    public void setSaleTax(String saleTax) {
        this.saleTax = saleTax;
    }

    public String getCompletedAmount() {
        return completedAmount;
    }

    public void setCompletedAmount(String completedAmount) {
        this.completedAmount = completedAmount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getSaleQuantity() {
        return saleQuantity;
    }

    public void setSaleQuantity(Long saleQuantity) {
        this.saleQuantity = saleQuantity==null?0:saleQuantity;
    }

    public Long getShippedQuantity() {
        return shippedQuantity;
    }

    public void setShippedQuantity(Long shippedQuantity) {
        this.shippedQuantity = shippedQuantity==null?0:shippedQuantity;
    }

    public Long getCompletedQuantity() {
        return completedQuantity;
    }

    public void setCompletedQuantity(Long completedQuantity) {
        this.completedQuantity = completedQuantity==null?0:completedQuantity;
    }

    public Long getUnitQuantity() {
        return unitQuantity;
    }

    public void setUnitQuantity(Long unitQuantity) {
        this.unitQuantity = unitQuantity==null?0:unitQuantity;
    }

    public Long getReturnQuantity() {
        return returnQuantity;
    }

    public void setReturnQuantity(Long returnQuantity) {
        this.returnQuantity = returnQuantity==null?0:returnQuantity;
    }

    public Long getQuantityDiscounted() {
        return quantityDiscounted;
    }

    public void setQuantityDiscounted(Long quantityDiscounted) {
        this.quantityDiscounted = quantityDiscounted==null?0:quantityDiscounted;
    }


    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount==null?0:amount;
    }

    public Double getListPrice() {
        return listPrice;
    }

    public void setListPrice(Double listPrice) {
        this.listPrice = listPrice==null?0:listPrice;
    }

    public Double getRawTotalPrice() {
        return rawTotalPrice;
    }

    public void setRawTotalPrice(Double rawTotalPrice) {
        this.rawTotalPrice = rawTotalPrice==null?0:rawTotalPrice;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice==null?0:salePrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity==null?0:quantity;
    }

}
