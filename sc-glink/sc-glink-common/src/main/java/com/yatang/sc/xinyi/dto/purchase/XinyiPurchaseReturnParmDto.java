package com.yatang.sc.xinyi.dto.purchase;

import com.thoughtworks.xstream.annotations.XStreamAliasType;

import java.io.Serializable;
import java.util.List;

/**
 * @description: 采购退货单 心怡入参之一dto
 * @author: yinyuxin
 * @date: 2017/11/9 19:10
 * @version: v1.0
 */
@XStreamAliasType("deliveryOrder")
public class XinyiPurchaseReturnParmDto implements Serializable{

	private static final long serialVersionUID = -3076675461666781957L;

	/**
	 * 退货单号
	 */
	private String deliveryOrderCode;

	/**
	 * 出库单类型  值:CGTH=采购退货出库单
	 */
	private String orderType;

	/**
	 * 退货单仓库编码(逻辑仓编码)
	 */
	private String warehouseCode;

	/**
	 * 退货单创建时间
	 */
	private String createTime;

	/**
	 * 收件人信息
	 */
	private XinyiPurchaseReturnReceiveInfoDto receiverInfo;




	public String getDeliveryOrderCode() {
		return deliveryOrderCode;
	}



	public void setDeliveryOrderCode(String deliveryOrderCode) {
		this.deliveryOrderCode = deliveryOrderCode;
	}



	public String getOrderType() {
		return orderType;
	}



	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}



	public String getWarehouseCode() {
		return warehouseCode;
	}



	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}



	public String getCreateTime() {
		return createTime;
	}



	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}



	public XinyiPurchaseReturnReceiveInfoDto getReceiverInfo() {
		return receiverInfo;
	}



	public void setReceiverInfo(XinyiPurchaseReturnReceiveInfoDto receiverInfo) {
		this.receiverInfo = receiverInfo;
	}



}
