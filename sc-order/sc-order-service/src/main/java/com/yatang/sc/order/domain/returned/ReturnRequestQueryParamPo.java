package com.yatang.sc.order.domain.returned;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.yatang.sc.common.BasePo;
import lombok.Getter;
import lombok.Setter;

/**
 * @描述: 查询退货单条件
 * @类名: ReturnRequestQueryParamDto
 * @作者: kangdong
 * @创建时间: 2017/10/17 15:11
 * @版本: v1.0
 */
public class ReturnRequestQueryParamPo extends BasePo implements Serializable {

	private static final long	serialVersionUID	= -5793167033543491628L;
	private String				id;											// 退货单号
	private String				profileId;									// 用户id
	private String				returnRequestType;							// 退货单类型:ZCTH(正常退货),JSTH(拒收退货),ZCHH(正常换货)
	private String				orderId;									// 原订单号id
	private String				branchCompanyId;							// 子公司//编码和子公司名称
	private String				franchiseeId;								// 加盟商//编码和小超名称
	private Date				startCreateTime;							// 开始日期日期
	private Date				endCreateTime;								// 结束日期
	private Short				state;										// 退货单状态
	private Short				shippingState;								// 收货总状态
	private String				paymentState;								// 退款状态
	private Short				productState;								// 商品状态
	private String				orderType;									// 订单类型
	private List<String>		returnRequestTypes;							// 订单类型List,过滤退换货
	private List<String>		branchCompanyIds;							// 子公司列表



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getProfileId() {
		return profileId;
	}



	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}



	public String getReturnRequestType() {
		return returnRequestType;
	}



	public void setReturnRequestType(String returnRequestType) {
		this.returnRequestType = returnRequestType;
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



	public Date getStartCreateTime() {
		return startCreateTime;
	}



	public void setStartCreateTime(Date startCreateTime) {
		this.startCreateTime = startCreateTime;
	}



	public Date getEndCreateTime() {
		return endCreateTime;
	}



	public void setEndCreateTime(Date endCreateTime) {
		this.endCreateTime = endCreateTime;
	}



	public Short getState() {
		return state;
	}



	public void setState(Short state) {
		this.state = state;
	}



	public Short getShippingState() {
		return shippingState;
	}



	public void setShippingState(Short shippingState) {
		this.shippingState = shippingState;
	}



	public String getPaymentState() {
		return paymentState;
	}



	public void setPaymentState(String paymentState) {
		this.paymentState = paymentState;
	}



	public Short getProductState() {
		return productState;
	}



	public void setProductState(Short productState) {
		this.productState = productState;
	}



	public String getOrderType() {
		return orderType;
	}



	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}



	public List<String> getReturnRequestTypes() {
		return returnRequestTypes;
	}



	public void setReturnRequestTypes(List<String> returnRequestTypes) {
		this.returnRequestTypes = returnRequestTypes;
	}



	public List<String> getBranchCompanyIds() {
		return branchCompanyIds;
	}



	public void setBranchCompanyIds(List<String> branchCompanyIds) {
		this.branchCompanyIds = branchCompanyIds;
	}
}
