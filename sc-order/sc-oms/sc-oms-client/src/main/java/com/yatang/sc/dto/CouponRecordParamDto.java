package com.yatang.sc.dto;

import com.yatang.sc.common.BaseDto;

import java.io.Serializable;
import java.util.Date;

/**
 * @description: 优惠券参与数据  （已使用） 查询参数
 * @author: yinyuxin
 * @date: 2017/9/20 15:21
 * @version: v1.0
 */
public class CouponRecordParamDto extends BaseDto implements Serializable{

	private static final long serialVersionUID = 8362017498049212143L;

	private String promoId;                                 //优惠券id

	private String orderId;                                //订单编号

	private String orderState;                             //订单状态

	private String paymentState;                           //支付状态

	private String shippingState;                          //配送状态

	private String storeId;                                //门店iD

	private String storeName;                              //门店名称

	private Date recordTimeStart;                          //使用时间(开始)

	private Date recordTimeEnd;                            //使用时间（结束）

	private String franchiseeId;                           //加盟商编号

	private String franchinessController;                  //加盟商名称

	private String branchCompanyId;                         //分公司id

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



	public String getPromoId() {
		return promoId;
	}



	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}



	public String getBranchCompanyId() {
		return branchCompanyId;
	}



	public void setBranchCompanyId(String branchCompanyId) {
		this.branchCompanyId = branchCompanyId;
	}
}
