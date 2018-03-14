package com.yatang.sc.order.sender;


import com.busi.mq.message.annotation.MQMessageKey;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangyonghong on 9/30/2017.
 */
@MQMessageKey(fields = "id")
public class OrderIndexMessage implements Serializable {

    private static final long serialVersionUID = 7645332834498217999L;
    private String id;//订单id
    private String orderType;//订单类型
    private String franchiseeId;//加盟商id
    private String franchiseeName;//加盟商名称
    private String franchiseeStoreId;//加盟商门店id
    private String franchiseeStoreName;//加盟商门店名称
    private String operationCenterId;//运营中心Id
    private String branchCompanyId;//分公司id
    private String branchCompanyName;//分公司名称
    private String branchCompanyArehouseCode;//出货仓
    private String branchCompanyArehouseName;//出货仓名称
    private String orderState;//订单状态
    private String shippingState;//物流状态
    private String paymentState;//支付状态
    private String createdByOrderId;//父订单编号
    private String profileId;//用户id
    private String cancelReason;//审核不通过原因
    private String thirdPartOrderNo;//第三方订单编号
    private String submitTime;//提交时间
    private String completedTime;//完成时间
    private String description;//运营备注

    /**
     * 订单价格信息
     */
    private Double rawSubtotal;//未执行任何折扣的净额
    private Double amount;//计算结果最终总额
    private Double userDiscountAmount;//会员折扣
    private Double couponDiscountAmount;//优惠券折扣
    private Double discountAmount;//活动优惠
    private Double discountTotalAmount;//优惠总金额
    private Double manualAdjustmentTotal;//手工调价总额
    private Double shipping;//运费
    private Double total;//总额amount+shipping
    private Double actualTotal;//订单实付金额

    /**
     * 物流信息
     */
    private String actualShipDate;//实际发货时间
    private String shippingMethod;//配送方式
    private String shipOnDate;//指定发货时间
    private String province;//省
    private String city;//市
    private String district;//区
    private String detailAddress;//详细地址
    private String consigneeName;//收货人姓名
    private String postcode;//收货邮编
    private String cellphone;//收货人手机
    private String deliveryer;//送货人
    private String deliveryerPhone;//送货人联系方式
    private String shippingNo;//运单号
    private String estimatedArrivalDate;//预计到达时间
    private String shippingModes;//配送模式
    private String singedCertImg;//签收凭证图片路径
    private String shippingRemarks;//备注/异议

    /**
     * 供应商配送信息
     */
    private String spId;//供应商ID
    private String spNo;//供应商编码
    private String spName;//供应商名称
    private String spAdrId;//供应商地点ID
    private String spAdrNo;//供应商地点编码
    private String spAdrName;//供应商地点名称
    /**
     * 发票信息
     */
    private String invoiceType;//发票类型
    private String invoiceTitle;//发票抬头
    private String companyName;//发票公司名称
    private String telePhone;//发票固定电话

    private List<OrderProductIndexMessage> orderItemPoList;//订单商品详情
    private List<PaymentGroupMessage> paymentGroupList;//付款记录
    private List<RefundMessage> refundList;//退款记录


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

    public String getShippingRemarks() {
        return shippingRemarks;
    }

    public void setShippingRemarks(String shippingRemarks) {
        this.shippingRemarks = shippingRemarks;
    }

    public String getSpId() {
        return spId;
    }

    public void setSpId(String spId) {
        this.spId = spId;
    }

    public String getSpNo() {
        return spNo;
    }

    public void setSpNo(String spNo) {
        this.spNo = spNo;
    }

    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public String getSpAdrId() {
        return spAdrId;
    }

    public void setSpAdrId(String spAdrId) {
        this.spAdrId = spAdrId;
    }

    public String getSpAdrNo() {
        return spAdrNo;
    }

    public void setSpAdrNo(String spAdrNo) {
        this.spAdrNo = spAdrNo;
    }

    public String getSpAdrName() {
        return spAdrName;
    }

    public void setSpAdrName(String spAdrName) {
        this.spAdrName = spAdrName;
    }

    public Double getActualTotal() {
        return actualTotal;
    }

    public void setActualTotal(Double actualTotal) {
        this.actualTotal = actualTotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void setFranchiseeName(String franchiseeName) {
        this.franchiseeName = franchiseeName;
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

    public String getOperationCenterId() {
        return operationCenterId;
    }

    public void setOperationCenterId(String operationCenterId) {
        this.operationCenterId = operationCenterId;
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

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
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

    public String getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(String cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getThirdPartOrderNo() {
        return thirdPartOrderNo;
    }

    public void setThirdPartOrderNo(String thirdPartOrderNo) {
        this.thirdPartOrderNo = thirdPartOrderNo;
    }

    public String getSubmitTime() {
        return submitTime;
    }

    public void setSubmitTime(String submitTime) {
        this.submitTime = submitTime;
    }

    public String getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(String completedTime) {
        this.completedTime = completedTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getRawSubtotal() {
        return rawSubtotal;
    }

    public void setRawSubtotal(Double rawSubtotal) {
        this.rawSubtotal = rawSubtotal;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public void setDiscountAmount(Double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public Double getDiscountTotalAmount() {
        return discountTotalAmount;
    }

    public void setDiscountTotalAmount(Double discountTotalAmount) {
        this.discountTotalAmount = discountTotalAmount;
    }

    public Double getManualAdjustmentTotal() {
        return manualAdjustmentTotal;
    }

    public void setManualAdjustmentTotal(Double manualAdjustmentTotal) {
        this.manualAdjustmentTotal = manualAdjustmentTotal;
    }

    public Double getShipping() {
        return shipping;
    }

    public void setShipping(Double shipping) {
        this.shipping = shipping;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getActualShipDate() {
        return actualShipDate;
    }

    public void setActualShipDate(String actualShipDate) {
        this.actualShipDate = actualShipDate;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getShipOnDate() {
        return shipOnDate;
    }

    public void setShipOnDate(String shipOnDate) {
        this.shipOnDate = shipOnDate;
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

    public String getConsigneeName() {
        return consigneeName;
    }

    public void setConsigneeName(String consigneeName) {
        this.consigneeName = consigneeName;
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

    public String getDeliveryer() {
        return deliveryer;
    }

    public void setDeliveryer(String deliveryer) {
        this.deliveryer = deliveryer;
    }

    public String getDeliveryerPhone() {
        return deliveryerPhone;
    }

    public void setDeliveryerPhone(String deliveryerPhone) {
        this.deliveryerPhone = deliveryerPhone;
    }

    public String getShippingNo() {
        return shippingNo;
    }

    public void setShippingNo(String shippingNo) {
        this.shippingNo = shippingNo;
    }

    public String getEstimatedArrivalDate() {
        return estimatedArrivalDate;
    }

    public void setEstimatedArrivalDate(String estimatedArrivalDate) {
        this.estimatedArrivalDate = estimatedArrivalDate;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTelePhone() {
        return telePhone;
    }

    public void setTelePhone(String telePhone) {
        this.telePhone = telePhone;
    }

    public List<OrderProductIndexMessage> getOrderItemPoList() {
        return orderItemPoList;
    }

    public List<PaymentGroupMessage> getPaymentGroupList() {
        return paymentGroupList;
    }

    public void setOrderItemPoList(List<OrderProductIndexMessage> orderItemPoList) {
        if (CollectionUtils.isEmpty(orderItemPoList)) {
            this.orderItemPoList = new ArrayList<OrderProductIndexMessage>();
        } else {
            this.orderItemPoList = orderItemPoList;
        }
    }

    public void setPaymentGroupList(List<PaymentGroupMessage> paymentGroupList) {
        if (CollectionUtils.isEmpty(paymentGroupList)) {
            this.paymentGroupList = new ArrayList<PaymentGroupMessage>();
        } else {
            this.paymentGroupList = paymentGroupList;
        }
    }

    public List<RefundMessage> getRefundList() {
        return refundList;
    }

    public void setRefundList(List<RefundMessage> refundList) {
        if (CollectionUtils.isEmpty(refundList)) {
            this.refundList = new ArrayList<RefundMessage>();
        } else {
            this.refundList = refundList;
        }
    }
}
