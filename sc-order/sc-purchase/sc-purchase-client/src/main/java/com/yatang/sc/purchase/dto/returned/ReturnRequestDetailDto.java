package com.yatang.sc.purchase.dto.returned;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @描述: 移动端退货单详情Dto
 * @类名: ReturnRequestDetailDto
 * @作者: kangdong
 * @创建时间: 2017/10/18 13:49
 * @版本: v1.0
 */
public class ReturnRequestDetailDto implements Serializable {

	private static final long						serialVersionUID	= 5858939767191754053L;

	private Short									state;										// 退换货状态

	private String									stateDetail;								// 退货单状态描述

	private String									id;											// 退换货单号

	// 退换货金额
	private List<ReturnRequestOrderProductListDto>	productList;								// 购物车商品

	private Date									creationTime;								// 申请日期

	// 客户电话
	private String									telephone;									// 电话

	// 总金额
	private Double									amount;										// 订单商品总额(去除优惠后的值)

	// 退货单类型:ZCTH(正常退货),JSTH(拒收退货),ZCHH(正常换货)
	private String									returnRequestType;
	// 原因类型
	private String									returnReasonType;
	// 原因
	private String									returnReason;
	// 商品状态描述
	private String									productStateDetail;
	// 退款状态
	private String									paymentStateDetail;
	// 实际退款总额
	private Double									actualRefundAmount;
	// 退款金额
	private Double									refundAmount;



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



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public List<ReturnRequestOrderProductListDto> getProductList() {
		return productList;
	}



	public void setProductList(List<ReturnRequestOrderProductListDto> productList) {
		this.productList = productList;
	}



	public Date getCreationTime() {
		return creationTime;
	}



	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}



	public String getTelephone() {
		return telephone;
	}



	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}



	public Double getAmount() {
		return amount;
	}



	public void setAmount(Double amount) {
		this.amount = amount;
	}



	public Double getRefundAmount() {
		return refundAmount;
	}



	public void setReturnRequestType(String returnRequestType) {
		this.returnRequestType = returnRequestType;
	}



	public String getReturnReasonType() {
		return returnReasonType;
	}



	public void setReturnReasonType(String returnReasonType) {
		this.returnReasonType = returnReasonType;
	}



	public String getReturnReason() {
		return returnReason;
	}



	public void setReturnReason(String returnReason) {
		this.returnReason = returnReason;
	}



	public String getProductStateDetail() {
		return productStateDetail;
	}



	public void setProductStateDetail(String productStateDetail) {
		this.productStateDetail = productStateDetail;
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



	public Double getActualRefundAmount() {
		return actualRefundAmount;
	}



	public void setActualRefundAmount(Double actualRefundAmount) {
		this.actualRefundAmount = actualRefundAmount;
	}



	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}
}
