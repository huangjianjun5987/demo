package com.yatang.sc.dto;

import com.yatang.sc.common.utils.excel.ExcelFieldName;
import com.yatang.sc.common.utils.excel.ExcelName;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.PaymentStates;
import com.yatang.sc.order.states.ShippingStates;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@ExcelName( name="cysj" )
public class ParticipateDataDto implements Serializable {

    private static final long serialVersionUID = 1037466919228483055L;

    /**
     * 订单编号
     */
    @ExcelFieldName(name="订单号")
    private String orderId;

    /**
     * 订单创建时间
     */
    @ExcelFieldName(name="订单时间")
    private Date submitTime;

    /**
     * 订单状态
     */
    private String stateDetail;
    /**
     * 订单状态描述
     */
    @ExcelFieldName(name="订单状态")
    private String orderStateDesc;

    /**
     * 支付状态描述
     */
    @ExcelFieldName(name="支付状态")
    private String paymentStateDesc;

    /**
     * 物流状态描述
     */
    @ExcelFieldName(name="物流状态")
    private String shippingStateDesc;
    /**
     * 订单总额
     */
    @ExcelFieldName(name="订单金额")
    private Double total;

    /**
     * 折扣金额
     * */
    @ExcelFieldName(name="优惠金额")
    private BigDecimal discount;

    /**
     * 门店编号
     */
    @ExcelFieldName(name="门店编号")
    private String franchiseeStoreId;

    /**
     * 门店名称
     */
    @ExcelFieldName(name="门店名称")
    private String franchiseeStoreName;

    /**
     * 子公司名称
     */
    @ExcelFieldName(name="所属子公司")
    private String  branchCompanyName;

    /**
     * 子公司id
     */
    private String branchCompanyId;

    /**
     * 订单状态
     */
    private String orderState;
    /**
     * 支付状态
     */
    private String paymentState;

    /**
     * 物流状态
     */
    private String shippingState;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getStateDetail() {
        return stateDetail;
    }

    public void setStateDetail(String stateDetail) {
        this.stateDetail = stateDetail;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public String getFranchiseeStoreId() {
        return franchiseeStoreId;
    }

    public void setFranchiseeStoreId(String franchiseeStoreId) {
        this.franchiseeStoreId = franchiseeStoreId;
    }

    public String getFranchiseeStoreName() {
        return franchiseeStoreName;
    }

    public void setFranchiseeStoreName(String franchiseeStoreName) {
        this.franchiseeStoreName = franchiseeStoreName;
    }

    public String getBranchCompanyName() {
        return branchCompanyName;
    }

    public void setBranchCompanyName(String branchCompanyName) {
        this.branchCompanyName = branchCompanyName;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getOrderState() { return orderState; }

    public void setOrderState(String orderState) { this.orderState = orderState; }

    public String getPaymentState() { return paymentState; }

    public void setPaymentState(String paymentState) { this.paymentState = paymentState; }

    public String getShippingState() { return shippingState; }

    public void setShippingState(String shippingState) { this.shippingState = shippingState; }

    public String getOrderStateDesc() {
        return OrderStates.orderStateDesc.get(this.orderState);
    }

    public void setOrderStateDesc(String orderStateDesc) {
        this.orderStateDesc = orderStateDesc;
    }

    public String getPaymentStateDesc() {
        return PaymentStates.paymentStateDesc.get(this.paymentState);
    }

    public void setPaymentStateDesc(String paymentStateDesc) {
        this.paymentStateDesc = paymentStateDesc;
    }

    public String getShippingStateDesc() {
        return ShippingStates.shippingStateDesc.get(this.shippingState);
    }

    public void setShippingStateDesc(String shippingStateDesc) {
        this.shippingStateDesc = shippingStateDesc;
    }
}
