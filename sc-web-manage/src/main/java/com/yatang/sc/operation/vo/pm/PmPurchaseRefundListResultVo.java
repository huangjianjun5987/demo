package com.yatang.sc.operation.vo.pm;

import java.math.BigDecimal;
import java.util.Date;

import com.yatang.sc.web.view.XlsHealder;
import com.yatang.sc.web.view.XlsSheet;

@XlsSheet("退货单列表")
public class PmPurchaseRefundListResultVo {
	/**
	 * 主键
	 */
	private Long		id;
	/**
	 * 退货单号
	 */
	@XlsHealder(value = "退货单号")
	private String		purchaseRefundNo;

	/**
	 * 供应商
	 */
	@XlsHealder(value = "供应商")
	private String		supplier;
	/**
	 * 供应商地点
	 */
	@XlsHealder(value = "供应商地点")
	private String		supplierAddress;
	/**
	 * 退货地点类型:0:仓库;1:门店
	 */
	@XlsHealder(value = "退货地点类型")
	private String		adrTypeName;

	private Integer		adrType;

	/**
	 * 退货地点编码
	 */
	@XlsHealder(value = "退货地点编码")
	private String		refundAdrCode;

	/**
	 * 退货地点名称
	 */
	@XlsHealder(value = "退货地点名称")
	private String		refundAdrName;

	/**
	 * 退货数量
	 */
	@XlsHealder(value = "退货数量")
	private Integer		totalRefundAmount;
	/**
	 * 退货成本额
	 */
	@XlsHealder(value = "退货成本额")
	private BigDecimal	totalRefundCost;
	/**
	 * 退货金额(含税)
	 */
	@XlsHealder(value = "退货金额(含税)")
	private BigDecimal	totalRefundMoney;

	/**
	 * 实际退货数量
	 */
	@XlsHealder(value = "实际退货数量")
	private Integer		totalRealRefundAmount;

	/**
	 * 实际退货金额(含税)
	 */
	@XlsHealder(value = "实际退货金额(含税)")
	private BigDecimal	totalRealRefundMoney;
	/**
	 * 创建时间
	 */
	@XlsHealder(value = "创建时间")
	private Date		createTime;

	/**
	 * 状态:0:制单;1:已提交;2:已审核;3:已拒绝;4:待退货;5:已退货;6:已取消;7:取消失败;8:异常
	 */
	@XlsHealder(value = "状态")
	private String		statusName;

	private Integer 		status;


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



	public Integer getAdrType() {
		return adrType;
	}



	public void setAdrType(Integer adrType) {
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



	public Integer getTotalRealRefundAmount() {
		return totalRealRefundAmount;
	}



	public void setTotalRealRefundAmount(Integer totalRealRefundAmount) {
		this.totalRealRefundAmount = totalRealRefundAmount;
	}



	public BigDecimal getTotalRealRefundMoney() {
		return totalRealRefundMoney;
	}



	public void setTotalRealRefundMoney(BigDecimal totalRealRefundMoney) {
		this.totalRealRefundMoney = totalRealRefundMoney;
	}



	public Date getCreateTime() {
		return createTime;
	}



	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}



	public Integer getStatus() {
		return status;
	}



	public void setStatus(Integer status) {
		this.status = status;
	}



	public String getAdrTypeName() {
		return adrTypeName;
	}



	public void setAdrTypeName(String adrTypeName) {
		this.adrTypeName = adrTypeName;
	}



	public String getStatusName() {
		return statusName;
	}



	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
