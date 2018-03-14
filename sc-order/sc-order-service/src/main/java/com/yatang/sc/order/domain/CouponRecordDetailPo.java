package com.yatang.sc.order.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 参与数据（已使用） 显示用po
 * @author: yinyuxin
 * @date: 2017/9/20 15:50
 * @version: v1.0
 */
public class CouponRecordDetailPo implements Serializable{

	private static final long serialVersionUID = -1458257034110444616L;

	private Long id;                                       //主键id

	private String franchiseeId;                           //加盟商编号

	private String branchCompanyId;                      //分公司id

	private String storeId;                                //门店iD

	private String promoId;                                //优惠券id

	private String promotionName;                          //优惠券名称

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



	public String getFranchiseeId() {
		return franchiseeId;
	}



	public void setFranchiseeId(String franchiseeId) {
		this.franchiseeId = franchiseeId;
	}



	public String getStoreId() {
		return storeId;
	}



	public void setStoreId(String storeId) {
		this.storeId = storeId;
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



	public String getBranchCompanyId() {
		return branchCompanyId;
	}



	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}
}
