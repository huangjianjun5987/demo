package com.yatang.sc.purchase.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @描述: 退货单详情Dto
 * @类名: ReturnRequestDetailDto
 * @作者: kangdong
 * @创建时间: 2017/10/18 13:49
 * @版本: v1.0
 */
public class ReturnRequestDetailDto implements Serializable {
	private static final long				serialVersionUID	= -7487803178186003927L;
	// 单据信息
	private String							id;											// 退货单号
	private Date							creationTime;								// 申请日期
	private String							orderId;									// 原订单号id
	// private String branchCompanyId; // 子公司
	// private String franchiseeId; // 加盟商
	private String							branchCompanyName;							// 子公司
	private String							franchiseeName;								// 加盟名称
	// private Double total; // 总金额
	// private Short state; // 退货单状态

	private String							stateDetail;								// 退货单状态描述

	// private Short productState; // 商品状态

	private String							productStateDetail;							// 商品状态描述

	private String							shippingStateDetail;						// 收货状态描述

	private Short							returnReasonType;							// 退货原因类型

	private String							paymentStateDetail;							// 退款状态

	private String							returnRequestType;							// 退货单类型:ZCTH(正常退货),JSTH(拒收退货),ZCHH(正常换货)

	// 收货信息
	private String							province;									// 省

	private String							city;										// 市

	private String							district;									// 区

	private String							detailAddress;								// 详细地址

	private String							postcode;									// 邮编

	private String							consigneeName;								// 收货人

	private String							telephone;									// 电话

	private String							cellphone;									// 手机
	// 商品总数
	private Integer							commodityTotal;
	// 总金额
	private Double							amount;										// 退货单总额

	//private Double							actualAmount;								// 实际退换货总总额

	private Double							refundAmount;								// 实际退款总总额
	// 商品信息
	// private List<ReturnRequestItemDto> items; // 购物车商品
	private List<ReturnRequestProductDto>	items;										// 购物车商品

	// 退货理由
	private String							returnReason;								// 退货理由

	// 订单的描述
	private String							description;



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public Date getCreationTime() {
		return creationTime;
	}



	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}



	public String getOrderId() {
		return orderId;
	}



	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public String getBranchCompanyName() {
		return branchCompanyName;
	}



	public void setBranchCompanyName(String branchCompanyName) {
		this.branchCompanyName = branchCompanyName;
	}



	public String getFranchiseeName() {
		return franchiseeName;
	}



	public void setFranchiseeName(String franchiseeName) {
		this.franchiseeName = franchiseeName;
	}



	public String getStateDetail() {
		return stateDetail;
	}



	public void setStateDetail(String stateDetail) {
		this.stateDetail = stateDetail;
	}



	public String getProductStateDetail() {
		return productStateDetail;
	}



	public void setProductStateDetail(String productStateDetail) {
		this.productStateDetail = productStateDetail;
	}



	public String getShippingStateDetail() {
		return shippingStateDetail;
	}



	public void setShippingStateDetail(String shippingStateDetail) {
		this.shippingStateDetail = shippingStateDetail;
	}



	public Short getReturnReasonType() {
		return returnReasonType;
	}



	public void setReturnReasonType(Short returnReasonType) {
		this.returnReasonType = returnReasonType;
	}



	public String getPaymentStateDetail() {
		return paymentStateDetail;
	}



	public void setPaymentStateDetail(String paymentStateDetail) {
		this.paymentStateDetail = paymentStateDetail;
	}



	public String getReturnRequestType() {
		return returnRequestType;
	}



	public void setReturnRequestType(String returnRequestType) {
		this.returnRequestType = returnRequestType;
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



	public Integer getCommodityTotal() {
		return commodityTotal;
	}



	public void setCommodityTotal(Integer commodityTotal) {
		this.commodityTotal = commodityTotal;
	}



	public List<ReturnRequestProductDto> getItems() {
		return items;
	}



	public void setItems(List<ReturnRequestProductDto> items) {
		this.items = items;
	}



	public String getReturnReason() {
		return returnReason;
	}



	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Double getAmount() {
		return amount;
	}



	public void setAmount(Double amount) {
		this.amount = amount;
	}



//	public Double getActualAmount() {
//		return actualAmount;
//	}
//
//
//
//	public void setActualAmount(Double actualAmount) {
//		this.actualAmount = actualAmount;
//	}



	public Double getRefundAmount() {
		return refundAmount;
	}



	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}
}
