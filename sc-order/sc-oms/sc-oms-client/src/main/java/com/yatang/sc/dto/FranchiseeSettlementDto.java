package com.yatang.sc.dto;

import com.yatang.sc.common.utils.excel.ExcelFieldName;
import com.yatang.sc.common.utils.excel.ExcelName;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.OrderTypes;
import com.yatang.sc.order.states.PaymentStates;
import com.yatang.sc.order.states.ShippingStates;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by xiangyonghong on 2017/8/31.
 */
@ExcelName(name="JMSJS")
public class FranchiseeSettlementDto implements Serializable{

    private static final long serialVersionUID = 2071632253909812834L;
    /**
     * 订单编号
     */
    @ExcelFieldName(name="订单编号")
    private String id;

    private String orderType;

    @ExcelFieldName(name="订单类型")
    private String orderTypeDesc;

    private String orderState;
    @ExcelFieldName(name="订单状态")
    private String orderStateDesc;
    /**
     * 支付状态
     */
    private String shippingState;
    @ExcelFieldName(name="物流状态")
    private String shippingStateDesc;
    /**
     * 物流状态
     */
    private String paymentState;
    @ExcelFieldName(name="支付状态")
    private String paymentStateDesc;
    /**
     * 子公司
     */
    private String branchCompanyId;
    @ExcelFieldName(name="子公司")
    private String branchCompanyName;
    /**
     * 加盟商编号
     */
    @ExcelFieldName(name="加盟商编号")
    private String franchiseeId;
    /**
     * 加盟商名称
     */
    @ExcelFieldName(name="加盟商名称")
    private String franchiseeName;
    /**
     * 出货仓名称
     */
    @ExcelFieldName(name="出货仓")
    private String branchCompanyArehouseCode;
    /**
     * 出货仓名称
     */
    @ExcelFieldName(name="出货仓名称")
    private String branchCompanyArehouseName;
    /**
     * 部类编码
     */
    @ExcelFieldName(name="部类编码")
    private String groupsCode;
    /**
     * 部类名称
     */
    @ExcelFieldName(name="部类名称")
    private String groupsName;
    /**
     * 大类编码
     */
    @ExcelFieldName(name="大类编码")
    private String deptCode;
    /**
     * 大类名称
     */
    @ExcelFieldName(name="大类名称")
    private String deptName;
    /**
     * 商品id
     */
    private String productId;

    /**
     * 商品编号
     */
    @ExcelFieldName(name="商品编号")
    private String productCode;
    /**
     * 商品名称
     */
    @ExcelFieldName(name="商品名称")
    private String productName;
    /**
     * 销项税率
     */
    @ExcelFieldName(name="销项税率")
    private String saleTax;
    /**
     * 签收金额
     */
    @ExcelFieldName(name="签收金额")
    private String completedAmount;
    /**
     * 签收差额(计算)
     */
    @ExcelFieldName(name="签收差额")
    private String completedMulAmount;

    private double amount;

    private long quantity;
    private double orderDiscountShare;

    /**
     * 商品签收数量
     */
    private long completedQuantity;

    /**
     * 销售价格
     */
    private double salePrice;

    private double total;

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

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderStateDesc() {
        return OrderStates.orderStateDesc.get(orderState);
    }

    public void setOrderStateDesc(String orderStateDesc) {
        this.orderStateDesc = orderStateDesc;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderTypeDesc() {
        return OrderTypes.orderTypeDesc.get(orderType);
    }

    public void setOrderTypeDesc(String orderTypeDesc) {
        this.orderTypeDesc = orderTypeDesc;
    }

    public String getShippingStateDesc() {
        return ShippingStates.shippingStateDesc.get(shippingState);
    }

    public void setShippingStateDesc(String shippingStateDesc) {
        this.shippingStateDesc = shippingStateDesc;
    }

    public String getPaymentStateDesc() {
        return PaymentStates.paymentStateDesc.get(paymentState);
    }

    public void setPaymentStateDesc(String paymentStateDesc) {
        this.paymentStateDesc = paymentStateDesc;
    }

    public String getBranchCompanyName() {
        return branchCompanyName;
    }

    public void setBranchCompanyName(String branchCompanyName) {
        this.branchCompanyName = branchCompanyName;
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
        this.saleTax = saleTax+"%";
    }

    public String getCompletedAmount() {
        return completedAmount;
    }

    public void setCompletedAmount(String completedAmount) {
        this.completedAmount = completedAmount;
    }

    public String getCompletedMulAmount() {

        return completedMulAmount;
    }

    public void setCompletedMulAmount(String completedMulAmount) {
        this.completedMulAmount = completedMulAmount;
    }

    public long getCompletedQuantity() {
        return completedQuantity;
    }

    public void setCompletedQuantity(long completedQuantity) {
        this.completedQuantity = completedQuantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
