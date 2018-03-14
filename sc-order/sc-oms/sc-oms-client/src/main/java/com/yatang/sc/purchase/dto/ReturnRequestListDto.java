package com.yatang.sc.purchase.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 退货单列表
 * @类名: ReturnRequestItemDto
 * @作者: kangdong
 * @创建时间: 2017/10/17 14:38
 * @版本: v1.0
 */
public class ReturnRequestListDto implements Serializable {

	private static final long	serialVersionUID	= -8673999704059804510L;
	private String				id;											// 退货单号
	private String				returnRequestType;							// 退货单类型:ZCTH(正常退货),JSTH(拒收退货),ZCHH(正常换货)
	private Date				creationTime;								// 申请日期
	private String				orderId;									// 原订单号id
	private String				branchCompanyId;							// 子公司
	private String				franchiseeId;								// 加盟商
	private String				branchCompanyName;							// 子公司
	private String				franchiseeName;								// 加盟名称
	private Short				state;										// 退货单状态
	private String				stateDetail;								// 退货单状态描述
	private Short				shippingState;								// 收货状态
	private String				shippingStateDetail;						// 收货状态描述
	private Short				productState;								// 商品状态
	private String				productStateDetail;							// 商品状态描述
	private String				orderType;									// 订单类型

	private Double				amount;										// 最终退货总额
	private String				paymentState;								// 退款状态
	private String				paymentStateDetail;							// 退款状态描述
	// private Double actualAmount;//实际退换货总总额



	// private Double refundAmount;//实际退款总总额

	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getReturnRequestType() {
		return returnRequestType;
	}



	public void setReturnRequestType(String returnRequestType) {
		this.returnRequestType = returnRequestType;
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
		this.productStateDetail = productStateDetail;
	}



	public String getOrderType() {
		return orderType;
	}



	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}



	public Double getAmount() {
		return amount;
	}



	public void setAmount(Double amount) {
		this.amount = amount;
	}



	public String getPaymentState() {
		return paymentState;
	}



	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
	}



	public String getPaymentStateDetail() {
		return paymentStateDetail;
	}



	public void setPaymentStateDetail(String paymentStateDetail) {
		this.paymentStateDetail = paymentStateDetail;
	}

	// public Double getActualAmount() {
	// return actualAmount;
	// }

	// public void setActualAmount(Double actualAmount) {
	// this.actualAmount = actualAmount;
	// }

	// public Double getRefundAmount() {
	// return refundAmount;
	// }

	// public void setRefundAmount(Double refundAmount) {
	// this.refundAmount = refundAmount;
	// }
}
