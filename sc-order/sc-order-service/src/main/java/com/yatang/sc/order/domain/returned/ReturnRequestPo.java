package com.yatang.sc.order.domain.returned;

import java.io.Serializable;
import java.util.Date;

public class ReturnRequestPo implements Serializable {
    private String id;

    private String profileId;//用户id

    private String branchCompanyId;//分公司ID

    private String branchCompanyArehouse;//逻辑仓CODE

    private String franchiseeId;//加盟商ID

    private String franchiseeStoreId;//加盟店ID

    private String returnRequestType;//退货单类型

    private Date completedTime;//完成时间

    private String orderId;//当前订单附属的主订单id

    private String description;//订单的描述

    private Short state;//退货单总状态

    private String stateDetail;//退货单总状态描述

    private Short shippingState;//收货总状态

    private String shippingStateDetail;//收货状总态描述

    private Short productState;//商品状态

    private String productStateDetail;//商品状态描述

    private Date creationTime;//creation_time

    private Short returnReasonType;//退货类型

    private String returnReason;//return_reason

    private String shippingGroup;//退货单的收货信息

    private Double amount;//最终退货总额

    private Double actualAmount;//实际退换货总总额

    private Double refundAmount;//退款金额

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId == null ? null : profileId.trim();
    }

    public String getBranchCompanyId() {
        return branchCompanyId;
    }

    public void setBranchCompanyId(String branchCompanyId) {
        this.branchCompanyId = branchCompanyId == null ? null : branchCompanyId.trim();
    }

    public String getBranchCompanyArehouse() {
        return branchCompanyArehouse;
    }

    public void setBranchCompanyArehouse(String branchCompanyArehouse) {
        this.branchCompanyArehouse = branchCompanyArehouse == null ? null : branchCompanyArehouse.trim();
    }

    public String getFranchiseeId() {
        return franchiseeId;
    }

    public void setFranchiseeId(String franchiseeId) {
        this.franchiseeId = franchiseeId == null ? null : franchiseeId.trim();
    }

    public String getFranchiseeStoreId() {
        return franchiseeStoreId;
    }

    public void setFranchiseeStoreId(String franchiseeStoreId) {
        this.franchiseeStoreId = franchiseeStoreId == null ? null : franchiseeStoreId.trim();
    }

    public String getReturnRequestType() {
        return returnRequestType;
    }

    public void setReturnRequestType(String returnRequestType) {
        this.returnRequestType = returnRequestType == null ? null : returnRequestType.trim();
    }

    public Date getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(Date completedTime) {
        this.completedTime = completedTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Short getState() {
        return state;
    }

    public void setState(Short state) {
        this.state = state;
    }

    public String getStateDetail() {
        return stateDetail;
    }

    public void setStateDetail(String stateDetail) {
        this.stateDetail = stateDetail == null ? null : stateDetail.trim();
    }

    public Short getProductState() {
        return productState;
    }

    public void setProductState(Short productState) {
        this.productState = productState;
    }

    public String getProductStateDetail() {
        return productStateDetail;
    }

    public void setProductStateDetail(String productStateDetail) {
        this.productStateDetail = productStateDetail == null ? null : productStateDetail.trim();
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Short getReturnReasonType() {
        return returnReasonType;
    }

    public void setReturnReasonType(Short returnReasonType) {
        this.returnReasonType = returnReasonType;
    }

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason == null ? null : returnReason.trim();
    }

    public Short getShippingState() {
        return shippingState;
    }

    public void setShippingState(Short shippingState) {
        this.shippingState = shippingState;
    }

    public String getShippingStateDetail() {
        return shippingStateDetail;
    }

    public void setShippingStateDetail(String shippingStateDetail) {
        this.shippingStateDetail = shippingStateDetail;
    }

    public String getShippingGroup() {
        return shippingGroup;
    }

    public void setShippingGroup(String shippingGroup) {
        this.shippingGroup = shippingGroup;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getActualAmount() {
        return actualAmount;
    }

    public void setActualAmount(Double actualAmount) {
        this.actualAmount = actualAmount;
    }

    public Double getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Double refundAmount) {
        this.refundAmount = refundAmount;
    }
}