package com.yatang.sc.kidd.dto.purchase;

import java.io.Serializable;
import java.util.List;

/**
 * @description: Kidd采购退货dto(际链和心怡共用dto)(所有字段必填)
 * @author: yinyuxin
 * @date: 2017/11/8 16:46
 * @version: v1.0
 */
public class KiddPurchaseReturnDto implements Serializable {

	private static final long						serialVersionUID	= 4547443703347481411L;

	/*------------------------------------------采购退货心怡接口入参--------------------------------------*/

	/**
	 * 退货单号
	 */
	private String									deliveryOrderCode;

	/**
	 * 出库单类型 值:CGTH=采购退货出库单
	 */
	private String									orderType;

	/**
	 * 退货单仓库编码(逻辑仓编码)
	 */
	private String									warehouseCode;

	/**
	 * 退货单仓库编码(逻辑仓编码)在“仓库信息表—逻辑仓”中的所属子公司编码--际链专用
	 */
	private String									ownerCode;
	/**
	 * 退货单创建时间--际链专用
	 */
	private String									createTime;
	/**
	 * 收件人信息--心怡
	 */
	private KiddPurchaseReturnReceiverInfoDto		receiverInfo;
	/**
	 * 收件人信息--际链
	 */
	private KiddPurchaseReturnReceiverGetInfoDto	getInfo;
	/**
	 * 货品详情--际链
	 */
	private List<KiddPurchaseRefundOrderLinesDto>	orderLines;



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



	public String getOwnerCode() {
		return ownerCode;
	}



	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}



	public String getCreateTime() {
		return createTime;
	}



	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}



	public KiddPurchaseReturnReceiverInfoDto getReceiverInfo() {
		return receiverInfo;
	}



	public void setReceiverInfo(KiddPurchaseReturnReceiverInfoDto receiverInfo) {
		this.receiverInfo = receiverInfo;
	}



	public KiddPurchaseReturnReceiverGetInfoDto getGetInfo() {
		return getInfo;
	}



	public void setGetInfo(KiddPurchaseReturnReceiverGetInfoDto getInfo) {
		this.getInfo = getInfo;
	}



	public List<KiddPurchaseRefundOrderLinesDto> getOrderLines() {
		return orderLines;
	}



	public void setOrderLines(List<KiddPurchaseRefundOrderLinesDto> orderLines) {
		this.orderLines = orderLines;
	}

}
