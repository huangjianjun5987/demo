package com.yatang.sc.purchase.dto.returned;

import java.io.Serializable;
import java.util.List;

import com.yatang.sc.purchase.dto.OrderProductListDto;

import lombok.Getter;
import lombok.Setter;

/**
 * @描述: APP端退换货列表
 * @类名: ReturnRequestListDto
 * @作者: kangdong
 * @创建时间: 2017/10/17 14:38
 * @版本: v1.0
 */
public class ReturnRequestListDto implements Serializable {

	private static final long			serialVersionUID	= -9060361119124981815L;

	private String						id;											// 退货单号
	// private Date creationTime; // 申请日期
	private String						orderId;									// 原订单号id
	// private String branchCompanyId; // 子公司
	// private String franchiseeId; // 加盟商
	// private String branchCompanyName; // 子公司
	// private String franchiseeName; // 加盟名称
	private List<OrderProductListDto>	productList;								// 购物车商品
	private Integer						productCount;								// 商品数量
	private Double						amount;										// 总金额
	private Short						state;										// 退货单状态
	private String						stateDetail;								// 退货单状态描述
	private Double						shipping;									// 运费
	private String						returnRequestType;							// 退货单类型:ZCTH(正常退货),JSTH(拒收退货),ZCHH(正常换货)



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getOrderId() {
		return orderId;
	}



	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public List<OrderProductListDto> getProductList() {
		return productList;
	}



	public void setProductList(List<OrderProductListDto> productList) {
		this.productList = productList;
	}



	public Integer getProductCount() {
		return productCount;
	}



	public void setProductCount(Integer productCount) {
		this.productCount = productCount;
	}



	public Double getAmount() {
		return amount;
	}



	public void setAmount(Double amount) {
		this.amount = amount;
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



	public Double getShipping() {
		return shipping;
	}



	public void setShipping(Double shipping) {
		this.shipping = shipping;
	}



	public String getReturnRequestType() {
		return returnRequestType;
	}



	public void setReturnRequestType(String returnRequestType) {
		this.returnRequestType = returnRequestType;
	}
}
