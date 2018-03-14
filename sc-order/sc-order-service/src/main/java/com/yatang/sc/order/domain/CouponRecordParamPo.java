package com.yatang.sc.order.domain;

import com.yatang.sc.common.BasePo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @description: 优惠券使用记录查询参数po
 * @author: yinyuxin
 * @date: 2017/9/20 13:50
 * @version: v1.0
 */
public class CouponRecordParamPo extends BasePo implements Serializable {

	private static final long serialVersionUID = 6702360982969312757L;

	private Long id;                            		   //id

	private String promoId;                      		   //优惠券id

	private String orderId;                                //订单编号

	private String orderState;                             //订单状态

	private String paymentState;                           //支付状态

	private String shippingState;                          //配送状态

	private List<String> storeIds;                                //门店iD

	private Date recordTimeStart;                          //使用时间(开始)

	private Date recordTimeEnd;                            //使用时间（结束）

	private List<String> franchiseeIds;                           //加盟商编号




	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getPromoId() {
		return promoId;
	}



	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}



	public String getOrderId() {
		return orderId;
	}



	public void setOrderId(String orderId) {
		this.orderId = orderId;
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



	public List<String> getStoreIds() {
		return storeIds;
	}



	public void setStoreIds(List<String> storeIds) {
		this.storeIds = storeIds;
	}



	public Date getRecordTimeStart() {
		return recordTimeStart;
	}



	public void setRecordTimeStart(Date recordTimeStart) {
		this.recordTimeStart = recordTimeStart;
	}



	public Date getRecordTimeEnd() {
		return recordTimeEnd;
	}



	public void setRecordTimeEnd(Date recordTimeEnd) {
		this.recordTimeEnd = recordTimeEnd;
	}



	public List<String> getFranchiseeIds() {
		return franchiseeIds;
	}



	public void setFranchiseeIds(List<String> franchiseeIds) {
		this.franchiseeIds = franchiseeIds;
	}




}
