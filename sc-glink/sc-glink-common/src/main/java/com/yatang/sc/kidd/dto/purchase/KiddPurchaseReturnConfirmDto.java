package com.yatang.sc.kidd.dto.purchase;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * <class description> 采购退货单确认Dto
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年11月9日
 */
public class KiddPurchaseReturnConfirmDto implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private Date				startTime;					// 查询开始时间值：当前系统时间-1月
	private Date				endTime;					// 查询截止时间值：当前系统时间+1月
	private String				serverOrgCode;				// 退货单单仓库编码(逻辑仓编码)在“仓库信息表—逻辑仓”中的所属子公司编码
	private String				warehouseCode;				// 退货单仓库编码(逻辑仓编码)
	private String				orderType;					// 出库库单业务类型值：CGTH=采购退货出库单
	private String				deliveryOrderCode;			// 退货单号



	public Date getStartTime() {
		return startTime;
	}



	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}



	public Date getEndTime() {
		return endTime;
	}



	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}



	public String getServerOrgCode() {
		return serverOrgCode;
	}



	public void setServerOrgCode(String serverOrgCode) {
		this.serverOrgCode = serverOrgCode;
	}



	public String getWarehouseCode() {
		return warehouseCode;
	}



	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}



	public String getOrderType() {
		return orderType;
	}



	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}



	public String getDeliveryOrderCode() {
		return deliveryOrderCode;
	}



	public void setDeliveryOrderCode(String deliveryOrderCode) {
		this.deliveryOrderCode = deliveryOrderCode;
	}

}
