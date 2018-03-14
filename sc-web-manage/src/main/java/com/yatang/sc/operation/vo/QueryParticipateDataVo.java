package com.yatang.sc.operation.vo;

import com.yatang.sc.operation.common.BaseVo;

import java.io.Serializable;
import java.util.Date;

public class QueryParticipateDataVo extends BaseVo implements Serializable {

    private static final long serialVersionUID = 7043153693212601961L;

    /**
     * 促销id
     */
    private String promoId;

    /**
     * 订单编号
     */
    private String orderId;

    /**
     * 订单状态
     */
    private String stateDetail;

    /**
     * 门店编号
     */
    private String franchiseeStoreId;

    /**
     * 门店名称
     */
    private String franchiseeStoreName;
    /**
     * 子公司名称
     */
    private String  branchCompanyName;

    /**
     * 子公司id
     */
    private String branchCompanyId;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

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


    public String getFranchiseeStoreName() { return franchiseeStoreName; }

    public void setFranchiseeStoreName(String franchiseeStoreName) { this.franchiseeStoreName = franchiseeStoreName; }

    public String getPromoId() {
        return promoId;
    }

    public void setPromoId(String promoId) {
        this.promoId = promoId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getStateDetail() {
        return stateDetail;
    }

    public void setStateDetail(String stateDetail) {
        this.stateDetail = stateDetail;
    }

    public String getFranchiseeStoreId() {
        return franchiseeStoreId;
    }

    public void setFranchiseeStoreId(String franchiseeStoreId) {
        this.franchiseeStoreId = franchiseeStoreId;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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
}
