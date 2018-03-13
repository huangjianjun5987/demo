package com.yatang.sc.facade.dto.pm;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @描述: 嵌套子表格ASN信息
 * @类名: ASNListDto
 * @作者: kangdong
 * @创建时间: 2018/1/9 17:11
 * @版本: v1.0
 */

public class ASNListDto implements Serializable {

	private static final long	serialVersionUID	= -5801241866496938388L;

	private String				purchaseOrderNo;							// 采购单号

	private Long				purchaseReceiptId;							// 收货ID

	private String				purchaseReceiptNo;							// 收货单号

	private String				asnNo;										// ASN单号

	private Integer				orderCount;									// 订货数量合计

	private Double				orderTotalAmount;							// 订货金额合计

	private Integer				estimatedCount;								// 预计发货数量合计

	private Double				estimatedTotalAmount;						// 预计发货金额合计

	private Integer				status;										// 状态:1：待收货，2:已收货



	public String getPurchaseOrderNo() {
		return purchaseOrderNo;
	}



	public void setPurchaseOrderNo(String purchaseOrderNo) {
		this.purchaseOrderNo = purchaseOrderNo;
	}



	public Long getPurchaseReceiptId() {
		return purchaseReceiptId;
	}



	public void setPurchaseReceiptId(Long purchaseReceiptId) {
		this.purchaseReceiptId = purchaseReceiptId;
	}



	public String getPurchaseReceiptNo() {
		return purchaseReceiptNo;
	}



	public void setPurchaseReceiptNo(String purchaseReceiptNo) {
		this.purchaseReceiptNo = purchaseReceiptNo;
	}



	public String getAsnNo() {
		return asnNo;
	}



	public void setAsnNo(String asnNo) {
		this.asnNo = asnNo;
	}



	public Integer getOrderCount() {
		return orderCount;
	}



	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}



	public Double getOrderTotalAmount() {
		return orderTotalAmount;
	}



	public void setOrderTotalAmount(Double orderTotalAmount) {
		this.orderTotalAmount = orderTotalAmount;
	}



	public Integer getEstimatedCount() {
		return estimatedCount;
	}



	public void setEstimatedCount(Integer estimatedCount) {
		this.estimatedCount = estimatedCount;
	}



	public Double getEstimatedTotalAmount() {
		return estimatedTotalAmount;
	}



	public void setEstimatedTotalAmount(Double estimatedTotalAmount) {
		this.estimatedTotalAmount = estimatedTotalAmount;
	}



	public Integer getStatus() {
		return status;
	}



	public void setStatus(Integer status) {
		this.status = status;
	}
}