package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author baiyun
 */
public class OrderEnhancedPo implements Serializable {

    private static final long serialVersionUID = 3927060759767584114L;

    //订单id
    private String id;

    //父订单id
    private String createdByOrderId;

    //创建时间
    private Date submitTime;

    //物流单号
    private String shippingNo;

    //订单类型
    private String orderType;

    //订单状态
    private String orderState;

    //支付状态
    private String paymentState;

    //第三方订单编号
    private String thirdParyOrderNo;

    //物流状态
    private String shippingState;

    //分公司id
    private String branchCompanyId;

    //加盟商id
    private String franchiseeId;

    //加盟商门店id
    private String franchiseeStoreId;

    //订单总额
    private Double rawSubtotal;

    //订单实付额
    private Double total;

    //会员优惠金额
    private Double userDiscountAmount;

    //优惠券优惠
    private Double couponDiscountAmount;

    //活动优惠
    private Double discountAmount;

    //优惠总金额=优惠相加
    private Double discountTotalAmount;

    //运营备注
    private String description;

    //订单取消原因
    private String cancelReason;

    //收货人姓名
    private String consigneeName;

    //收货人省
    private String province;

    //收货人市
    private String city;

    //收货人区
    private String district;

    //收货人街道
    private String detailAddress;

    //收货人邮编
    private String postcode;

    //收货人手机号
    private String cellphone;

    //商品id
    private String productId;

    //商品数量
    private Long quantity;

    //商品单价
    private Double salePrice;

    //单个商品总价
    private Double amount;

    //总金额
    private Double orderAmount;

    //商品移动加权价
    private Double avCost;

    //商品成本
    private Double cost;

    //商品状态
    private String stateDetail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShippingNo() {
        return shippingNo;
    }

    public void setShippingNo(String shippingNo) {
        this.shippingNo = shippingNo;
    }

    public Double getUserDiscountAmount() {
        return userDiscountAmount;
    }

    public void setUserDiscountAmount(Double userDiscountAmount) {
        this.userDiscountAmount = userDiscountAmount;
    }

    public Double getCouponDiscountAmount() {
        return couponDiscountAmount;
    }

    public void setCouponDiscountAmount(Double couponDiscountAmount) {
        this.couponDiscountAmount = couponDiscountAmount;
    }

    public Double getDiscountAmount() {
        return discountAmount;
    }

    public Double getRawSubtotal() {
        return rawSubtotal;
    }

    public void setRawSubtotal(Double rawSubtotal) {
        this.rawSubtotal = rawSubtotal;
    }

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getDiscountTotalAmount() {
        return discountTotalAmount;
    }

    public void setDiscountTotalAmount(Double discountTotalAmount) {
        this.discountTotalAmount = discountTotalAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAvCost() {
        return avCost;
    }

    public void setAvCost(Double avCost) {
        this.avCost = avCost;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public String getCreatedByOrderId() {
        return createdByOrderId;
    }

    public void setCreatedByOrderId(String createdByOrderId) {
        this.createdByOrderId = createdByOrderId;
    }

    public Date getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
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

    public String getFranchiseeStoreId() {
        return franchiseeStoreId;
    }

    public void setFranchiseeStoreId(String franchiseeStoreId) {
        this.franchiseeStoreId = franchiseeStoreId;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
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

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Double orderAmount) {
        this.orderAmount = orderAmount;
    }

    public String getStateDetail() {
        return stateDetail;
    }

    public void setStateDetail(String stateDetail) {
        this.stateDetail = stateDetail;
    }

    public String getThirdParyOrderNo() {
        return thirdParyOrderNo;
    }

    public void setThirdParyOrderNo(String thirdParyOrderNo) {
        this.thirdParyOrderNo = thirdParyOrderNo;
    }
}
