package com.yatang.sc.purchase.dto;


import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.OrderTypes;
import com.yatang.sc.order.states.PaymentStates;
import com.yatang.sc.order.states.ShippingStates;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OrderManageDetailDto implements Serializable {

    private static final long serialVersionUID = -8614109938944018786L;

    private String id;    //订单编号

    private String createdByOrderId;  //父订单编号

    private String orderType;   //订单类型
    /**
     * 订单类型描述
     */
    private String orderTypeDesc;

    private Short state;      //订单总状态

    private String stateDetail;   //订单状态描述

    private String branchCompanyId; //子公司code

    private String branchCompanyName; //子公司名称

    private String branchCompanyArehouse; //出货仓

    private String franchiseeId;     //加盟商ID

    private String franchiseeName; // 加盟商名字

    private String franchiseeStoreId;      //加盟店ID

    private String franchiseeStoreName; //雅堂小超

    private String profileId; //用户id

    private String description; //订单备注

    private Date creationTime;   //下单时间

    private String paymentState;   //支付状态
    /**
     * 支付状态描述
     */
    private String paymentStateDesc;

    private String shippingState;    //配送状态
    /**
     * 物流状态描述
     */
    private String shippingStateDesc;

    private String orderState;   //订单状态
    /**
     * 订单状态描述
     */
    private String orderStateDesc;

    /**
     * 小超名称
     */
    private String storeName;

    /**
     * 电商订单编号
     */
    private String thirdPartOrderNo;



    private String province;          //省

    private String city;          //市

    private String district;         //区

    private String detailAddress;       //详细地址

    private String postcode;            //邮编

    private String consigneeName;          //收货人

    private String telephone;           //电话

    private String cellphone;           //手机

    private List<OMSCommerceItemDto> items;        //购物车商品

    private double  rawSubtotal;        //订单商品未执行任何折扣之前的净额

    private double amount;          // 订单商品总额(去除优惠后的值)

    private Integer countOfItem;        //购物车商品的数量

    private boolean canSplitByInventory;//是否可根据库存实时拆单

    private boolean canSplitManual;// 是否可人工拆单

    //优惠券优惠
    private double couponDiscountAmount;

    // 活动优惠
    private double discount;

    // 会员等级优惠
    private double userDiscountAmount;

    // 运费
    private double shipping;

    //总价
    private double total;

    //该订单是否存在有效的退换货单（0：存在1：不存在）
    private Integer returnOrder;

    private String cancelReason; //订单取消原因 yinyuxin

    private String shippingModes;//配送模式

    private String spName; //配送方名称（用于页面显示，来自sc_provider_shipping_group表） yinyuxin

    private String singedCertImg;//签收凭证图片路径
    private String remarks;//备注/异议

    public String getShippingModes() {
        return shippingModes;
    }

    public void setShippingModes(String shippingModes) {
        this.shippingModes = shippingModes;
    }

    public String getSingedCertImg() {
        return singedCertImg;
    }

    public void setSingedCertImg(String singedCertImg) {
        this.singedCertImg = singedCertImg;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getFranchiseeStoreName() {
        return franchiseeStoreName;
    }

    public void setFranchiseeStoreName(String franchiseeStoreName) {
        this.franchiseeStoreName = franchiseeStoreName;
    }
    public OrderManageDetailDto() {
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

    public String getCreatedByOrderId() {
        return createdByOrderId;
    }

    public void setCreatedByOrderId(String createdByOrderId) {
        this.createdByOrderId = createdByOrderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderTypeDesc() {
        return OrderTypes.orderTypeDesc.get(this.orderType);
    }

    public void setOrderTypeDesc(String orderTypeDesc) {
        this.orderTypeDesc = orderTypeDesc;
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
        this.stateDetail = stateDetail;
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

    public String getBranchCompanyArehouse() {
        return branchCompanyArehouse;
    }

    public void setBranchCompanyArehouse(String branchCompanyArehouse) {
        this.branchCompanyArehouse = branchCompanyArehouse;
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

    public String getFranchiseeStoreId() {
        return franchiseeStoreId;
    }

    public void setFranchiseeStoreId(String franchiseeStoreId) {
        this.franchiseeStoreId = franchiseeStoreId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    public String getPaymentStateDesc() {
        return PaymentStates.paymentStateDesc.get(this.paymentState);
    }

    public void setPaymentStateDesc(String paymentStateDesc) {
        this.paymentStateDesc = paymentStateDesc;
    }

    public String getShippingState() {
        return shippingState;
    }

    public void setShippingState(String shippingState) {
        this.shippingState = shippingState;
    }

    public String getShippingStateDesc() {
        return ShippingStates.shippingStateDesc.get(this.shippingState);
    }

    public void setShippingStateDesc(String shippingStateDesc) {
        this.shippingStateDesc = shippingStateDesc;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderStateDesc() {
        return OrderStates.orderStateDesc.get(this.orderState);
    }

    public void setOrderStateDesc(String orderStateDesc) {
        this.orderStateDesc = orderStateDesc;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDetailAddress() {
        return detailAddress;
    }

    public void setDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public List<OMSCommerceItemDto> getItems() {
        return items;
    }

    public void setItems(List<OMSCommerceItemDto> items) {
        this.items = items;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Integer getCountOfItem() {
        return countOfItem;
    }

    public void setCountOfItem(Integer countOfItem) {
        this.countOfItem = countOfItem;
    }

    public boolean isCanSplitByInventory() {
        return canSplitByInventory;
    }

    public void setCanSplitByInventory(boolean canSplitByInventory) {
        this.canSplitByInventory = canSplitByInventory;
    }

    public boolean isCanSplitManual() {
        return canSplitManual;
    }

    public void setCanSplitManual(boolean canSplitManual) {
        this.canSplitManual = canSplitManual;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getThirdPartOrderNo() {
        return thirdPartOrderNo;
    }

    public void setThirdPartOrderNo(String thirdPartOrderNo) {
        this.thirdPartOrderNo = thirdPartOrderNo;
    }

    public double getCouponDiscountAmount() {
        return couponDiscountAmount;
    }

    public void setCouponDiscountAmount(double couponDiscountAmount) {
        this.couponDiscountAmount = couponDiscountAmount;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getUserDiscountAmount() {
        return userDiscountAmount;
    }

    public void setUserDiscountAmount(double userDiscountAmount) {
        this.userDiscountAmount = userDiscountAmount;
    }

    public double getShipping() {
        return shipping;
    }

    public void setShipping(double shipping) {
        this.shipping = shipping;
    }

    public double getRawSubtotal() {
        return rawSubtotal;
    }

    public void setRawSubtotal(double rawSubtotal) {
        this.rawSubtotal = rawSubtotal;
    }

    public Integer getReturnOrder() {
        return returnOrder;
    }

    public void setReturnOrder(Integer returnOrder) {
        this.returnOrder = returnOrder;
    }



    public String getCancelReason() {
        return cancelReason;
    }



    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }


    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }
}