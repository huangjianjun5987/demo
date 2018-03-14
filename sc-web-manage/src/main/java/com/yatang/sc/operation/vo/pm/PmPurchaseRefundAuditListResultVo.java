package com.yatang.sc.operation.vo.pm;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * <class description>退货单审批列表查询结果VO
 * 
 * @author: zhoubaiyun
 * @version: 1.0, 2017年10月23日
 */
public class PmPurchaseRefundAuditListResultVo implements Serializable {

	private static final long	serialVersionUID	= 1L;
	/**
	 * 退货单ID
	 */
	private Long				id;
	/**
	 * 退货单号
	 */
	private String				purchaseRefundNo;
	/**
	 * 退货地点类型:0:仓库;1:门店
	 */
	private String				adrType;

	/**
	 * 退货地点编码
	 */
	private String				refundAdrCode;

	/**
	 * 退货地点名称
	 */
	private String				refundAdrName;

	/**
	 * 供应商
	 */
	private String				supplier;
	/**
	 * 供应商地点
	 */
	private String				supplierAddress;

	/**
	 * 退货数量
	 */
	private Integer				totalRefundAmount;
	/**
	 * 退货成本额
	 */
	private BigDecimal			totalRefundCost;
	/**
	 * 退货金额(含税)
	 */
	private BigDecimal			totalRefundMoney;
	/**
	 * 创建者
	 */
	private String				createUserId;
	/**
	 * 退货单创建时间
	 */

	private Date				createTime;

	/**
	 * 流程开始时间
	 */
	private Date				processEndTime;
	/**
	 * 流程结束时间
	 */
	private Date				processStartTime;
	/**
	 * 当前节点
	 */
	private String				processNodeName;

	private String				processNodeId;



	public String getProcessNodeId() {
		return processNodeId;
	}



	public void setProcessNodeId(String processNodeId) {
		this.processNodeId = processNodeId;
	}



	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getPurchaseRefundNo() {
		return purchaseRefundNo;
	}



	public void setPurchaseRefundNo(String purchaseRefundNo) {
		this.purchaseRefundNo = purchaseRefundNo;
	}



	public String getAdrType() {
		return adrType;
	}



	public void setAdrType(String adrType) {
		this.adrType = adrType;
	}



	public String getRefundAdrCode() {
		return refundAdrCode;
	}



	public void setRefundAdrCode(String refundAdrCode) {
		this.refundAdrCode = refundAdrCode;
	}



	public String getRefundAdrName() {
		return refundAdrName;
	}



	public void setRefundAdrName(String refundAdrName) {
		this.refundAdrName = refundAdrName;
	}



	public String getSupplier() {
		return supplier;
	}



	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}



	public String getSupplierAddress() {
		return supplierAddress;
	}



	public void setSupplierAddress(String supplierAddress) {
		this.supplierAddress = supplierAddress;
	}



	public Integer getTotalRefundAmount() {
		return totalRefundAmount;
	}



	public void setTotalRefundAmount(Integer totalRefundAmount) {
		this.totalRefundAmount = totalRefundAmount;
	}



	public BigDecimal getTotalRefundCost() {
		return totalRefundCost;
	}



	public void setTotalRefundCost(BigDecimal totalRefundCost) {
		this.totalRefundCost = totalRefundCost;
	}



	public BigDecimal getTotalRefundMoney() {
		return totalRefundMoney;
	}



	public void setTotalRefundMoney(BigDecimal totalRefundMoney) {
		this.totalRefundMoney = totalRefundMoney;
	}



	public String getCreateUserId() {
		return createUserId;
	}



	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}



	public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public Date getProcessEndTime() {
		return processEndTime;
	}



	public void setProcessEndTime(Date processEndTime) {
		this.processEndTime = processEndTime;
	}



	public Date getProcessStartTime() {
		return processStartTime;
	}



	public void setProcessStartTime(Date processStartTime) {
		this.processStartTime = processStartTime;
	}



	public String getProcessNodeName() {
		return processNodeName;
	}



	public void setProcessNodeName(String processNodeName) {
		this.processNodeName = processNodeName;
	}

}
