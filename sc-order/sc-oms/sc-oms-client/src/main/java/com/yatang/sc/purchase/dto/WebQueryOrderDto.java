package com.yatang.sc.purchase.dto;

import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.OrderTypes;
import com.yatang.sc.order.states.PaymentStates;
import com.yatang.sc.order.states.ShippingStates;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by xiangyonghong on 2017/7/17.
 */
public class WebQueryOrderDto implements Serializable{

    /**
     * 订单编号
     */
    private String id;
    /**
     * 父订单编号
     */
    private String createdByOrderId;
    /**
     * 用户id
     */
    private String profileId;
    /**
     * 订单类型
     */
    private String orderType;

    /**
     * 订单类型描述
     */
    private String orderTypeDesc;
    /**
     * 加盟商ID
     */
    private String franchiseeId;

    /**
     * 加盟商名称
     */
    private String franchiseeName;
    /**
     * 加盟商编号（取消不用就是加盟商ID）
     */
    private String franchiseeNo;

    private String franchiseeStoreId;

    private String franchiseeStoreName;
    /**
     * 所属子公司
     */
    private String branchCompanyId;
    /**
     * 子公司名称
     */
    private String branchCompanyName;
    /**
     * 下单时间
     */
    private Date submitTime;
    /**
     * 买家用户名
     */
    private String userName;
    /**
     * 订单总金额
     */
    private String rawSubtotal;
    /**
     * 订单实付金额
     */
    private String total;
    /**
     * 订单状态
     */
    private String orderState;

    /**
     * 订单状态描述
     */
    private String orderStateDesc;

    /**
     * 支付状态
     */
    private String paymentState;

    /**
     * 支付状态描述
     */
    private String paymentStateDesc;
    /**
     * 物流状态
     */
    private String shippingState;
    /**
     * 物流状态描述
     */
    private String shippingStateDesc;
    /**
     * 优惠金额
     */
    private String discountAmount;
    /**
     * 购买渠道
     */
    private String channel;
    /**
     * 运费
     */
    private String shipping;
    /**
     * 收货人电话
     */
    private String cellphone;

    /**
     * 审核不通过原因
     */
    private String cancelReason;

    private String shippingModes;//配送模式 yinyuxin 用于查询结果显示
    private String spName;//配送方名称 yinyuxin 用于查询结果显示

    public String getFranchiseeStoreName() {
        return franchiseeStoreName;
    }

    public void setFranchiseeStoreName(String franchiseeStoreName) {
        this.franchiseeStoreName = franchiseeStoreName;
    }

    public String getFranchiseeStoreId() {
        return franchiseeStoreId;
    }

    public void setFranchiseeStoreId(String franchiseeStoreId) {
        this.franchiseeStoreId = franchiseeStoreId;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedByOrderId() {
        return createdByOrderId;
    }

    public void setCreatedByOrderId(String createdByOrderId) {
        this.createdByOrderId = createdByOrderId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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

    public void setFranchiseeName(String pFranchiseeName) {
        franchiseeName = pFranchiseeName;
    }

    public String getFranchiseeNo() {
        return franchiseeNo;
    }

    public void setFranchiseeNo(String franchiseeNo) {
        this.franchiseeNo = franchiseeNo;
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId;
    }

    public String getBranchCompanyName() {
        return branchCompanyName;
    }

    public void setBranchCompanyName(String branchCompanyName) {
        this.branchCompanyName = branchCompanyName;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRawSubtotal() {
        return rawSubtotal;
    }

    public void setRawSubtotal(String rawSubtotal) {
        this.rawSubtotal = rawSubtotal;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }


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

    public String getOrderTypeDesc() {
        return OrderTypes.orderTypeDesc.get(this.orderType);
    }

    public void setOrderTypeDesc(String orderTypeDesc) {
        this.orderTypeDesc = orderTypeDesc;
    }



    public String getShippingModes() {
        return shippingModes;
    }



    public void setShippingModes(String shippingModes) {
        this.shippingModes = shippingModes;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }
}
