package com.yatang.sc.sorder.vo;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @description: 供应商直送 已签收待确认订单的确认收货接口参数vo
 * @author: yinyuxin
 * @date: 2018/1/17 15:30
 * @version: v1.0
 */
public class QsdqrOrderComfirmParamVo implements Serializable{

	private static final long serialVersionUID = -3835346288279450102L;

	/**
	 * 订单id
	 */
	@NotNull(message = "{msg.notEmpty.message}")
	private String orderId;

	/**
	 * 签收的商品数据（code+sum）
	 */
	private List<QsdqrOrderItemsConfirmVo> commerceItemDatas;



	public String getOrderId() {
		return orderId;
	}



	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	public List<QsdqrOrderItemsConfirmVo> getCommerceItemDatas() {
		return commerceItemDatas;
	}



	public void setCommerceItemDatas(List<QsdqrOrderItemsConfirmVo> commerceItemDatas) {
		this.commerceItemDatas = commerceItemDatas;
	}
}
