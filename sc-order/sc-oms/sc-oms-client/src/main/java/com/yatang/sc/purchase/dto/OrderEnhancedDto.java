package com.yatang.sc.purchase.dto;

import com.yatang.sc.common.utils.excel.ExcelFieldName;
import com.yatang.sc.common.utils.excel.ExcelName;
import com.yatang.sc.order.states.OrderStates;
import com.yatang.sc.order.states.OrderTypes;
import com.yatang.sc.order.states.PaymentStates;
import com.yatang.sc.order.states.ShippingStates;

import java.io.Serializable;
import java.util.Date;

@ExcelName(name="dingdandaochu")
public class OrderEnhancedDto implements Serializable {

    private static final long serialVersionUID = 8809954286355748514L;

    //订单id
    @ExcelFieldName(name="订单编号")
    private String id;

    //父订单id
    @ExcelFieldName(name="父订单编号")
    private String createdByOrderId;

    //创建时间
    @ExcelFieldName(name="订单日期")
    private Date submitTime;

    private String orderType;

    //订单类型描述
    @ExcelFieldName(name="订单类型")
    private String orderTypeDesc;

    //第三方订单编号
    @ExcelFieldName(name="第三方订单编号")
    private String thirdParyOrderNo;

    //物流单号
    @ExcelFieldName(name="物流单号")
    private String shippingNo;

    //订单状态
    private String orderState;

    // 订单状态描述
    @ExcelFieldName(name="订单状态")
    private String orderStateDesc;

    //支付状态
    private String paymentState;

    // 支付状态描述
    @ExcelFieldName(name="支付状态")
    private String paymentStateDesc;

    //物流状态
    private String shippingState;

    // 物流状态描述
    @ExcelFieldName(name="物流状态")
    private String shippingStateDesc;

    //分公司id
    private String branchCompanyId;

    //分公司名字
    @ExcelFieldName(name="子公司")
    private String BranchCompanyName;

    //加盟商id
    @ExcelFieldName(name="加盟商编号")
    private String franchiseeId;

    //加盟商门店id
    @ExcelFieldName(name="门店编号")
    private String franchiseeStoreId;

    // 默认门店名字
    @ExcelFieldName(name="小超名称")
    private String	franchiseeStoreName;

    @ExcelFieldName(name="订单总金额")
    private Double rawSubtotal;

    //订单总额
    @ExcelFieldName(name="订单实付金额")
    private Double total;

    //会员优惠金额
    @ExcelFieldName(name="会员优惠金额")
    private Double userDiscountAmount;

    //优惠券优惠
    @ExcelFieldName(name="优惠券优惠")
    private Double couponDiscountAmount;

    //活动优惠
    @ExcelFieldName(name="活动优惠")
    private Double discountAmount;

    //优惠总金额=优惠相加
    @ExcelFieldName(name="优惠总金额")
    private Double discountTotalAmount;

    //运营备注
    @ExcelFieldName(name="运营备注")
    private String description;

    //订单取消原因
    @ExcelFieldName(name="商家取消原因")
    private String cancelReason;

    @ExcelFieldName(name="配送方")
    private String spName;

    //收货人姓名
    @ExcelFieldName(name="收货人")
    private String consigneeName;

    //收货人省
    @ExcelFieldName(name="省")
    private String province;

    //收货人市
    @ExcelFieldName(name="市")
    private String city;

    //收货人区
    @ExcelFieldName(name="区")
    private String district;

    //收货人街道
    @ExcelFieldName(name="街道地址")
    private String detailAddress;

    //收货人手机号
    @ExcelFieldName(name="手机号码")
    private String cellphone;

    //固定电话
    @ExcelFieldName(name="固定电话")
    private String telePhone;

    //收货人邮编
    @ExcelFieldName(name="邮编")
    private String postcode;

    //商品编码
    @ExcelFieldName(name="商品编码")
    private String productCode;

    //商品id
    private String productId;

    //商品条码
    @ExcelFieldName(name="商品条码")
    private String internationalCode;

    //商品名称
    @ExcelFieldName(name="商品名称")
    private String productName;

    // 一级商品分类名称
    @ExcelFieldName(name="一级商品分类名称")
    private String firstLevelCategoryName;

    // 二级商品分类名称
    @ExcelFieldName(name="二级商品分类名称")
    private String secondLevelCategoryName;

    // 三级商品分类名称
    @ExcelFieldName(name="三级商品分类名称")
    private String thirdLevelCategoryName;

    //商品数量
    @ExcelFieldName(name="数量")
    private Long quantity;

    //商品单价
    @ExcelFieldName(name="商品销售单价")
    private Double salePrice;

    //单个商品总价
    @ExcelFieldName(name="商品销售金额")
    private Double amount;

    //商品移动加权价
//    @ExcelFieldName(name="商品移动加权价")
    private Double avCost;

    //订单总金额(计算的最终结果  不含运费)
//    @ExcelFieldName(name="总金额")
    private Double orderAmount;

    //成本金额 =数量*移动加权
//    @ExcelFieldName(name="成本金额")
    private Double cost;

    //商品状态
    @ExcelFieldName(name="库存是否充足")
    private String stateDetail;

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getId() {
        return id;
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

    public Double getRawSubtotal() {
        return rawSubtotal;
    }

    public void setRawSubtotal(Double rawSubtotal) {
        this.rawSubtotal = rawSubtotal;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getDiscountAmount() {
        return discountAmount;
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

    public void setId(String id) {
        this.id = id;
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

    public String getBranchCompanyName() {
        return BranchCompanyName;
    }

    public void setBranchCompanyName(String branchCompanyName) {
        BranchCompanyName = branchCompanyName;
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

    public String getInternationalCode() {
        return internationalCode;
    }

    public void setInternationalCode(String internationalCode) {
        this.internationalCode = internationalCode;
    }

    public String getFirstLevelCategoryName() {
        return firstLevelCategoryName;
    }

    public void setFirstLevelCategoryName(String firstLevelCategoryName) {
        this.firstLevelCategoryName = firstLevelCategoryName;
    }

    public String getSecondLevelCategoryName() {
        return secondLevelCategoryName;
    }

    public void setSecondLevelCategoryName(String secondLevelCategoryName) {
        this.secondLevelCategoryName = secondLevelCategoryName;
    }

    public String getThirdLevelCategoryName() {
        return thirdLevelCategoryName;
    }

    public void setThirdLevelCategoryName(String thirdLevelCategoryName) {
        this.thirdLevelCategoryName = thirdLevelCategoryName;
    }

    public String getOrderTypeDesc() {
        return OrderTypes.orderTypeDesc.get(this.orderType);
    }

    public void setOrderTypeDesc(String orderTypeDesc) {
        this.orderTypeDesc = orderTypeDesc;
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

    public String getFranchiseeStoreName() {
        return franchiseeStoreName;
    }

    public void setFranchiseeStoreName(String franchiseeStoreName) {
        this.franchiseeStoreName = franchiseeStoreName;
    }

    public String getThirdParyOrderNo() {
        return thirdParyOrderNo;
    }

    public void setThirdParyOrderNo(String thirdParyOrderNo) {
        this.thirdParyOrderNo = thirdParyOrderNo;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }
}
