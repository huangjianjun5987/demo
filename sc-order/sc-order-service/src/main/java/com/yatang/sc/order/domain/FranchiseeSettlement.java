package com.yatang.sc.order.domain;

import java.io.Serializable;

/**
 * Created by xiangyonghong on 2017/9/4.
 */
public class FranchiseeSettlement implements Serializable{
    private static final long serialVersionUID = -5772299108532303820L;
    /**
     * 订单编号
     */
    private String id;
    /**
     * 订单类型
     */
    private String orderType;
    private String orderState;
    /**
     * 支付状态
     */
    private String shippingState;
    /**
     * 物流状态
     */
    private String paymentState;
    /**
     * 子公司
     */
    private String branchCompanyId;
    /**
     * 加盟商编号
     */
    private String franchiseeId;
    /**
     * 加盟商名称
     */
    private String franchiseeName;
    /**
     * 出货仓
     */
    private String branchCompanyArehouseCode;
    /**
     * 出货仓名称
     */
    private String branchCompanyArehouseName;
    /**
     * 部类编码
     */
    private String groupsCode;
    /**
     * 部类名称
     */
    private String groupsName;
    /**
     * 大类编码
     */
    private String deptCode;
    /**
     * 大类名称
     */
    private String deptName;
    /**
     * 商品编号
     */
    private String productCode;
    private String productId;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 销项税率
     */
    private String saleTax;
    /**
     * 签收金额
     */
    private double completedAmount;
    /**
     * 签收差额(计算)
     */
    private double completedMulAmount;

    /**
     * 商品签收数量
     */
    private long completedQuantity;

    private long quantity;
    private double orderDiscountShare;
    private double amount;

    /**
     * 销售价格
     */
    private double salePrice;

    private double total;


    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getOrderDiscountShare() {
        return orderDiscountShare;
    }

    public void setOrderDiscountShare(double orderDiscountShare) {
        this.orderDiscountShare = orderDiscountShare;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public long getCompletedQuantity() {
        return completedQuantity;
    }

    public void setCompletedQuantity(long completedQuantity) {
        this.completedQuantity = completedQuantity;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getFranchiseeId() {
        return franchiseeId;
    }

    public void setFranchiseeId(String franchiseeId) {
        this.franchiseeId = franchiseeId;
    }

    public String getFranchiseeName() {
        return franchiseeName;
    }

    public void setFranchiseeName(String franchiseeName) {
        this.franchiseeName = franchiseeName;
    }

    public String getBranchCompanyArehouseCode() {
        return branchCompanyArehouseCode;
    }

    public void setBranchCompanyArehouseCode(String branchCompanyArehouseCode) {
        this.branchCompanyArehouseCode = branchCompanyArehouseCode;
    }

    public String getBranchCompanyArehouseName() {
        return branchCompanyArehouseName;
    }

    public void setBranchCompanyArehouseName(String branchCompanyArehouseName) {
        this.branchCompanyArehouseName = branchCompanyArehouseName;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSaleTax() {
        return saleTax;
    }

    public void setSaleTax(String saleTax) {
        this.saleTax = saleTax;
    }

    public double getCompletedAmount() {
        return completedAmount;
    }

    public void setCompletedAmount(double completedAmount) {
        this.completedAmount = completedAmount;
    }

    public double getCompletedMulAmount() {
        return completedMulAmount;
    }

    public void setCompletedMulAmount(double completedMulAmount) {
        this.completedMulAmount = completedMulAmount;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
