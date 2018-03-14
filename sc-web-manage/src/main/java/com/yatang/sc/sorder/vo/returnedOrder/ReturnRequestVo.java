package com.yatang.sc.sorder.vo.returnedOrder;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.yatang.sc.validgroup.DefaultGroup;
import com.yatang.sc.validgroup.GroupOne;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;


public class ReturnRequestVo implements Serializable {

    private static final long serialVersionUID = 6841359608973319987L;
    private String id;

    private String profileId;//用户id

    private String branchCompanyId;//分公司ID

    private String branchCompanyArehouse;//逻辑仓CODE

    private String franchiseeId;//加盟商ID--默认直营店

    private String franchiseeStoreId;//加盟店ID--默认直营店


    private String returnRequestType;//退货单类型 退货单类型:ZCTH(正常退货),JSTH(拒收退货),ZCHH(正常换货)

    private Date completedTime;//完成时间

    @NotBlank(message = "{msg.notEmpty.message}", groups = {GroupOne.class, DefaultGroup.class})
    private String orderId;//当前订单附属的主订单id

    private String description;//订单的描述

    private Short state;//退货单总状态

    private String stateDetail;//退货单总状态描述

    private String shippingState;//收货总状态
    private String shippingStateDetail;//收货状总态描述

    private Short productState;//商品状态

    private String productStateDetail;//商品状态描述


    private Short returnReasonType;//退货类型


    private Date creationTime;

    private String returnReason;
    private String shippingGroup;//退货单的收货信息

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

    public String getReturnReason() {
        return returnReason;
    }

    public void setReturnReason(String returnReason) {
        this.returnReason = returnReason == null ? null : returnReason.trim();
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }

    public String getShippingGroup() {
        return shippingGroup;
    }

    public void setShippingGroup(String shippingGroup) {
        this.shippingGroup = shippingGroup;
    }

    public Short getReturnReasonType() {
        return returnReasonType;
    }

    public void setReturnReasonType(Short returnReasonType) {
        this.returnReasonType = returnReasonType;
    }

    public String getShippingStateDetail() {
        return shippingStateDetail;
    }

    public void setShippingStateDetail(String shippingStateDetail) {
        this.shippingStateDetail = shippingStateDetail;
    }
}