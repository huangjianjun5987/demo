package com.yatang.sc.order.domain;

import com.yatang.sc.order.states.OrderFrom;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private String id;

    private String profileId;

    private String branchCompanyId;

    private String branchCompanyArehouse;

    private String franchiseeId;

    private String franchiseeStoreId;

    private String orderType;

    private Short state;

    private String stateDetail;

    private String orderState;

    private String shippingState;

    private String paymentState;

    private Date submitTime;

    private Date creationTime;

    private Date lastModifiedTime;

    private Date completedTime;

    private Long priceInfo;

    private String createdByOrderId;

    private String salesChannel;

    private String agentId;

    private String siteId;

    private String description;

    private long version;

    private Long invoiceInfo;

    private String shippingGroup;

    private String paymentGroup;

    private String cancelReason;

    private Double rawSubtotal;

    private Double total;

    private Boolean interactivePendingRes;

    private String cancelStatus;

    private String thirdPartOrderNo;

    private boolean pendingSplit;

    private String shippingModes;//配送模式 yinyuxin 用于查询结果显示

    private String spName;//配送方名称 yinyuxin 用于查询结果显示

    private String receiptNo;//收货单号

    private String purchaseOrderNo;//采购单号

    private String from = OrderFrom.DEFAULT.getValue();//订单来源

    private static final long serialVersionUID = 1L;

    public String getThirdPartOrderNo() {
        return thirdPartOrderNo;
    }

    public void setThirdPartOrderNo(String thirdPartOrderNo) {
        this.thirdPartOrderNo = thirdPartOrderNo;
    }

    public Double getRawSubtotal() {
        return rawSubtotal;
    }

    public void setRawSubtotal(Double rawSubtotal) {
        this.rawSubtotal = rawSubtotal;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getBranchCompanyArehouse() {
        return branchCompanyArehouse;
    }

    public void setBranchCompanyArehouse(String branchCompanyArehouse) {
        this.branchCompanyArehouse = branchCompanyArehouse;
    }

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

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType == null ? null : orderType.trim();
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

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState == null ? null : orderState.trim();
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(String shippingState) {
        this.shippingState = shippingState == null ? null : shippingState.trim();
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState == null ? null : paymentState.trim();
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Date getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(Date completedTime) {
        this.completedTime = completedTime;
    }

    public Long getPriceInfo() {
        return priceInfo;
    }

    public void setPriceInfo(Long priceInfo) {
        this.priceInfo = priceInfo;
    }

    public String getCreatedByOrderId() {
        return createdByOrderId;
    }

    public void setCreatedByOrderId(String createdByOrderId) {
        this.createdByOrderId = createdByOrderId == null ? null : createdByOrderId.trim();
    }

    public String getSalesChannel() {
        return salesChannel;
    }

    public void setSalesChannel(String salesChannel) {
        this.salesChannel = salesChannel == null ? null : salesChannel.trim();
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId == null ? null : agentId.trim();
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId == null ? null : siteId.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public Long getInvoiceInfo() {
        return invoiceInfo;
    }

    public void setInvoiceInfo(Long invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }

    public String getShippingGroup() {
        return shippingGroup;
    }

    public void setShippingGroup(String shippingGroup) {
        this.shippingGroup = shippingGroup == null ? null : shippingGroup.trim();
    }

    public String getPaymentGroup() {
        return paymentGroup;
    }

    public void setPaymentGroup(String paymentGroup) {
        this.paymentGroup = paymentGroup == null ? null : paymentGroup.trim();
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String pCancelReason) {
        cancelReason = pCancelReason;
    }

    public Boolean getInteractivePendingRes() {
        return interactivePendingRes;
    }

    public void setInteractivePendingRes(Boolean pInteractivePendingRes) {
        interactivePendingRes = pInteractivePendingRes;
    }

    public String getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(String pCancelStatus) {
        cancelStatus = pCancelStatus;
    }

    public boolean isPendingSplit() {
        return pendingSplit;
    }

    public void setPendingSplit(boolean pendingSplit) {
        this.pendingSplit = pendingSplit;
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

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String pReceiptNo) {
        receiptNo = pReceiptNo;
    }

    public String getPurchaseOrderNo() {
        return purchaseOrderNo;
    }

    public void setPurchaseOrderNo(String pPurchaseOrderNo) {
        purchaseOrderNo = pPurchaseOrderNo;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String pFrom) {
        from = pFrom;
    }
}