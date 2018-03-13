package com.yatang.sc.glink.dto.purchase;

import java.io.Serializable;
import java.util.List;

public class PurchaseReturnOrderDto implements Serializable {
	private static final long					serialVersionUID	= 1L;
	private String								deliveryOrderCode;			// 是
																			// 出库单号（ERP分配）必填
	private String								orderType;					// 是
																			// PTCK=普通出库单（退仓），DBCK=调拨出库
																			// ，QTCK=其他出库
	private String								warehouseCode;				// 是
																			// 仓库自定义编码
	private String								ownerCode;					// 是
																			// 货主自定义编码
	private String								createTime;					// 是
																			// 货主出库单创建时间YYYY-MM-DDHH

	private String								logisticsCode;				// OTHER
	private PurchaseReturnGetInfoDto			getInfo;					//
	private List<PurchaseReturnOrderLinesDto>	orderLines;					// 订单详情



	public String getLogisticsCode() {
		return logisticsCode;
	}



	public void setLogisticsCode(String logisticsCode) {
		this.logisticsCode = logisticsCode;
	}



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



	public PurchaseReturnGetInfoDto getGetInfo() {
		return getInfo;
	}



	public void setGetInfo(PurchaseReturnGetInfoDto getInfo) {
		this.getInfo = getInfo;
	}



	public List<PurchaseReturnOrderLinesDto> getOrderLines() {
		return orderLines;
	}



	public void setOrderLines(List<PurchaseReturnOrderLinesDto> orderLines) {
		this.orderLines = orderLines;
	}

}
