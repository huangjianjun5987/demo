package com.yatang.sc.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 优惠券参与数据 （已使用）
 * @author: yinyuxin
 * @date: 2017/9/20 14:32
 * @version: v1.0
 */
public class CouponRecordDto implements Serializable{

	private static final long serialVersionUID = -3654304064712192382L;

	private Long id;                                       //主键id

	private String branchCompanyName;                      //所属子公司名称

	private String franchiseeId;                           //加盟商编号

	private String franchinessController;                  //加盟商名称

	private String storeId;                                //门店iD

	private String storeName;                              //门店名称

	private String promoId;                                //优惠券id

	private String promotionName;                          //优惠券名称

	private Date   activityDate;                           //领取时间

	private String orderId;                                //订单编号

	private Date   recordTime;                             //使用时间

	private String orderState;                             //订单状态

	private String shippingState;                          //配送状态

	private String paymentState;                           //支付状态

	private Double orderPrice;                             //订单金额





	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getBranchCompanyName() {
		return branchCompanyName;
	}



	public void setBranchCompanyName(String branchCompanyName) {
		this.branchCompanyName = branchCompanyName;
	}



	public String getFranchiseeId() {
		return franchiseeId;
	}



	public void setFranchiseeId(String franchiseeId) {
		this.franchiseeId = franchiseeId;
	}



	public String getFranchinessController() {
		return franchinessController;
	}



	public void setFranchinessController(String franchinessController) {
		this.franchinessController = franchinessController;
	}



	public String getStoreId() {
		return storeId;
	}



	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}



	public String getStoreName() {
		return storeName;
	}



	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}



	public String getPromoId() {
		return promoId;
	}



	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}



	public String getPromotionName() {
		return promotionName;
	}



	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}



	public Date getActivityDate() {
		return activityDate;
	}



	public void setActivityDate(Date activityDate) {
		this.activityDate = activityDate;
	}



	public String getOrderId() {
		return orderId;
	}



	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public Date getRecordTime() {
		return recordTime;
	}



	public void setRecordTime(Date recordTime) {
		this.recordTime = recordTime;
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



	public Double getOrderPrice() {
		return orderPrice;
	}



	public void setOrderPrice(Double orderPrice) {
		this.orderPrice = orderPrice;
	}




}
