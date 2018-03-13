package com.yatang.sc.facade.dto;

import java.io.Serializable;
import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * <class description>
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年11月11日
 */
public class PurchaseRefundOrderDto implements Serializable {
	private static final long	serialVersionUID	= 1L;
	private Integer				totalOrderLines;			// 单据总行数
	private String				entryOrderCode;				// 入库单编码
	private String				ownerCode;					// 货主编码
	private String				warehouseCode;				// 仓库编码
	private String				entryOrderId;				// 仓储系统入库单ID
	private String				entryOrderType;				// 入库单类型
	private String				outBizCode;					// 外部业务编码,
															// 消息ID,
															// 用于去重
	private Integer				confirmType;				// 支持出入库单多次收货;0
															// 表示入库单最终状态确认；1
															// 表示入库单中间状态确认；
	private String				status;						// 入库单状态
	@JSONField(format = "yyyy-MM-dd HH:mm:ss")
	private Date				operateTime;				// 操作时间
	private String				remark;						// 备注



	public void setTotalOrderLines(Integer totalOrderLines) {
		this.totalOrderLines = totalOrderLines;
	}



	public void setEntryOrderCode(String entryOrderCode) {
		this.entryOrderCode = entryOrderCode;
	}



	public void setOwnerCode(String ownerCode) {
		this.ownerCode = ownerCode;
	}



	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}



	public void setEntryOrderId(String entryOrderId) {
		this.entryOrderId = entryOrderId;
	}



	public void setEntryOrderType(String entryOrderType) {
		this.entryOrderType = entryOrderType;
	}



	public void setOutBizCode(String outBizCode) {
		this.outBizCode = outBizCode;
	}



	public void setConfirmType(Integer confirmType) {
		this.confirmType = confirmType;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}



	public void setRemark(String remark) {
		this.remark = remark;
	}



	public Integer getTotalOrderLines() {
		return totalOrderLines;
	}



	public String getEntryOrderCode() {
		return entryOrderCode;
	}



	public String getOwnerCode() {
		return ownerCode;
	}



	public String getWarehouseCode() {
		return warehouseCode;
	}



	public String getEntryOrderId() {
		return entryOrderId;
	}



	public String getEntryOrderType() {
		return entryOrderType;
	}



	public String getOutBizCode() {
		return outBizCode;
	}



	public Integer getConfirmType() {
		return confirmType;
	}



	public String getStatus() {
		return status;
	}



	public Date getOperateTime() {
		return operateTime;
	}



	public String getRemark() {
		return remark;
	}
}
